package com.leidos.dataparser.executor;

import com.leidos.dataparser.appcommon.SettingsManager;
import com.leidos.dataparser.pipeline.factories.PipelineFactoryLocator;
import com.leidos.dataparser.ui.UIManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Job queue/thread pool for the data processing process.
 * Uses a factory to generate worker thread instructions and generates a configurable number of worker threads to
 * process input files.
 */

public class ParserExecutor {
    @Autowired
    SettingsManager settingsManager;

    @Autowired
    JobCreator jobCreator;

    @Autowired
    UIManager uiManager;

    Logger log = LogManager.getLogger();

    private BlockingQueue<Job> jobQueue;

    @Autowired
    private PipelineFactoryLocator pipelineFactoryLocator;

    // Set number of max threads equal to available cores on host PC to enable 1 thread per core operation.
    // Alows for elegant and efficient scaling across different PC types and job sizes.
    private static int MAX_THREADS = Runtime.getRuntime().availableProcessors();

    private int filesProcessed = 0;
    private long bytesProcessed = 0;

    private int filesInProgress = 0;
    private int bytesInProgress = 0;

    private int unprocessableFiles = 0;
    private long unprocessableBytes = 0;

    private int totalFiles;
    private long totalBytes;

    private List<PipelineWorker> workerThreads;

    private boolean running = false;

    private long startTime;


    public ParserExecutor(int max_threads) {
        MAX_THREADS = max_threads;
    }

    /**
     * Begin the execution of the worker threads processing the input data.
     */
    public void execute() {
        // Grab the appropriate PipelineFactory based on the selected message type.
        // Using Spring here to support more generic behavior in the future.

        log.debug("", "ParserExecutor starting up...");

        // Initialize the executor's values for this run
        filesProcessed = 0;
        bytesProcessed = 0;
        filesInProgress = 0;
        bytesInProgress = 0;
        unprocessableFiles = 0;
        unprocessableBytes = 0;
        jobQueue = new LinkedBlockingQueue<>();

        // Pass the torch to the JobCreator to generate our jobQueue contents
        List<Job> jobs = jobCreator.generateJobs(settingsManager.getInputFolder(), settingsManager.getOutputFolder());
        jobQueue.addAll(jobs);

        // Compute files and total size
        totalBytes = jobs.stream().mapToLong(j -> j.getInputFile().length()).sum();
        totalFiles = jobs.size();
        long files = jobs.stream().map(Job::getInputFile).distinct().count();

        log.debug("Job statistics: Bytes: " + totalBytes + " Tasks: " + jobs.size() + " Files: " + files);

        // Get confirmation from the user of what will happen.
        if (uiManager.getParsingConfirmation(settingsManager.getInputFolder(), settingsManager.getOutputFolder(),
                files, jobs.size(), totalBytes)) {

            log.debug("User confirmation received.");
            startTime = System.currentTimeMillis(); // Start the clock

            // Spawn our worker threads and let them do their thing
            log.debug("Spawning " + MAX_THREADS + " PipelineWorker threads!");
            workerThreads = new ArrayList<>();
            for (int i = 0; i < MAX_THREADS; i++) {
                PipelineWorker pw = new PipelineWorker(this, pipelineFactoryLocator);
                workerThreads.add(pw);
                new Thread(pw).start();
            }
            log.debug("PipelineWorker threads started.");
        }
    }

    /**
     * Halts the executor. Tells all worker threads to stop iterating after their current job.
     */
    public void stop() {
        log.warn("Executor stopped!");
        running = false;
        workerThreads.stream().forEach(PipelineWorker::interrupt);
    }

    /**
     * Spin until a new job is exclusively available to calling thread then acquire that job exclusively.
     * @return The {@link Job} the thread is to perform.
     * @throws InterruptedException If the thread is interrupted while spinning to acquire a Job.
     */
    public synchronized Job requestJob() throws InterruptedException {
        Job j = jobQueue.take();

        bytesInProgress += j.getInputFile().length();
        filesInProgress++;

        log.debug("A PipelineWorker has requested a new job. " + bytesInProgress + " bytes in progress, " +
                filesInProgress + " in progress");

        return j;
    }

    private synchronized void checkIfFinished() {
        if (jobQueue.isEmpty() && filesInProgress == 0) {
            long timeElapsed = System.currentTimeMillis() - startTime;
            uiManager.notifyParsingCompletion(timeElapsed, getFilesProcessed(), getTotalFiles() - getFilesProcessed(),
                    getBytesProcessed(), getTotalBytes() - getBytesProcessed());
        }
    }

    /**
     * Report that a job has been completed. This enables the ParserExecutor to keep track of various progress metrics
     * during the processing steps.
     * @param job
     */
    public synchronized void finishJob(Job job) {

        log.debug(job + " reported successfully completed!");

        Long length = job.getInputFile().length();

        bytesInProgress -= length;
        filesInProgress--;

        bytesProcessed += length;
        filesProcessed++;

        updateProgressBar();
        checkIfFinished();
    }

    /**
     * Report that a job has failed, increment the associated metrics counters. A possible space to hook in retries in
     * the future.
     * @param job The failed job.
     */
    public synchronized void reportFailure(Job job) {

        log.warn(job + " reported failure.");

        Long length = job.getInputFile().length();

        bytesInProgress -= length;
        filesInProgress--;

        unprocessableBytes= length;
        unprocessableFiles++;

        updateProgressBar();
        checkIfFinished();
    }

    /**
     * Checks to see if there are more jobs to be performed.
     * @return True if the job queue is not empty, false otherwise.
     */
    public synchronized boolean hasJobs() {
        return !jobQueue.isEmpty();
    }


    /**
     * Returns the total number of files processed at the present point in time.
     * @return Then number of files finished.
     */
    public int getFilesProcessed() {
        return filesProcessed;
    }

    /**
     * The cumulative size of files processed at the present point in time, measured in bytes.
     * @return Number of bytes contained in all files finished.
     */
    public long getBytesProcessed() {
        return bytesProcessed;
    }

    /**
     * The number of files presently being processed by worker threads.
     * @return The number of files presently being processed by worker threads.
     */
    public int getFilesInProgress() {
        return filesInProgress;
    }

    /**
     * The total size of all files presently being processed by worker threads measured in bytes.
     * @return The total size of all files presently being processed by worker threads measured in bytes.
     */
    public int getBytesInProgress() {
        return bytesInProgress;
    }

    /**
     * The total number of files for which an unrecoverable error occurred during processing.
     * @return The total number of files for which an unrecoverable error occurred during processing.
     */
    public int getUnprocessableFiles() {
        return unprocessableFiles;
    }

    /**
     * The total size of all files for which an unrecoverable error occurred during processing measured in bytes.
     * @return The total size of all files for which an unrecoverable error occurred during processing measured in bytes.
     */
    public long getUnprocessableBytes() {
        return unprocessableBytes;
    }

    /**
     * The total number of files to be processed.
     * @return The total number of files to be processed.
     */
    public int getTotalFiles() {
        return totalFiles;
    }

    /**
     * The total size of files to be processed measured in bytes.
     * @return The total size of files to be processed measured in bytes.
     */
    public long getTotalBytes() {
        return totalBytes;
    }

    private void updateProgressBar() {
        uiManager.updateParsingProgressBar(getBytesProcessed(), getTotalBytes());
    }
}
