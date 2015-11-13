package com.leidos.dataparser.executor;

import com.leidos.dataparser.pipeline.Pipeline;
import com.leidos.dataparser.pipeline.StageException;
import com.leidos.dataparser.pipeline.factories.PipelineFactory;
import com.leidos.dataparser.pipeline.factories.PipelineFactoryLocator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Worker thread for use in the Parser Executor thread pool.
 */
public class PipelineWorker implements Runnable {

    Logger log = LogManager.getLogger();

    private ParserExecutor executor;
    private PipelineFactoryLocator pipelineFactoryLocator;

    public PipelineWorker(ParserExecutor executor, PipelineFactoryLocator pipelineFactoryLocator) {
        this.executor = executor;
        this.pipelineFactoryLocator = pipelineFactoryLocator;
    }

    private boolean running = false;

    @Override
    public void run() {

        log.debug("Pipeline worker started.");

        running = true;
        while (running && executor.hasJobs()) {
            // Iterate parsing our files until we're out of files to parse

            try {
                // Spin until we get a job to do.
                Job currentJob = executor.requestJob();

                log.debug("Pipeline worker received job: " + currentJob.toString());

                // Generate a pipeline for this job
                PipelineFactory pipelineFactory = pipelineFactoryLocator.getFactory(currentJob.getType());
                Pipeline pipeline = pipelineFactory.createPipeline(currentJob);

                // Run the pipeline and report back if it fails or succeeds.
                try {
                    log.debug("Started job!" + currentJob.toString());
                    pipeline.execute();
                    executor.finishJob(currentJob);
                    log.debug("Finished job!" + currentJob.toString());
                } catch (StageException e) {
                    executor.reportFailure(currentJob);
                    log.error("Failed job " + currentJob.toString() + " with exception " + e.getMessage());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                log.error("Pipeline worker interrupted with " + e.getMessage());
            }
        }
    }

    /**
     * Cause this worker to stop running after it finishes its current job.
     */
    public void interrupt() {
        running = false;
    }

}
