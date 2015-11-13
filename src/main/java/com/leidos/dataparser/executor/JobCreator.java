package com.leidos.dataparser.executor;

import com.leidos.dataparser.appcommon.Constants;
import com.leidos.dataparser.appcommon.MessageType;
import com.leidos.dataparser.appcommon.SettingsManager;
import com.leidos.dataparser.ui.UIManager;
import org.apache.commons.io.FilenameUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class responsible for the creation of Job objects for an input set of files. Handles the renaming of files based on
 * message type being parsed, change of file extension, and handles filename conflict behavior by invoking the appropriate
 * UI elements to get user input.
 */
public class JobCreator {

    private Logger log = LogManager.getLogger();

    @Autowired
    private UIManager uiManager;

    @Autowired
    private SettingsManager settingsManager;

    public enum ConflictHandlingStrategy {
        ALWAYS_ASK,
        ALWAYS_SKIP,
        ALWAYS_RENAME,
        ALWAYS_OVERWRITE,
        SKIP_ONCE,
        RENAME_ONCE,
        OVERWRITE_ONCE
    }

    private ConflictHandlingStrategy conflictHandlingStrategy = ConflictHandlingStrategy.ALWAYS_ASK;

    public JobCreator() {

    }

    /**
     * Generate a list of Jobs for a given input folder and output folder combination. Handles conflict resolution and
     * file name management.
     *
     * @param inputFolder The File object to read a list of files from. May be either a single file or a folder. In the
     *                    case of a single file only 1 job is generated. In the case of a folder jobs are generated
     * @param outputFolder The folder or file in which to store the resulting output of the processing step. Only valid
     *                     to be a single file in the event that the input is a single file instead of a directory.
     * @return A List of Job objects corresponding to all the necessary tasks in the input set. Contains one job per
     *         file per message type with all filename conflicts resolved.
     */
    public List<Job> generateJobs(File inputFolder, File outputFolder) {
        log.trace("Generating jobs for " + inputFolder + " => " + outputFolder + ".");
        if (inputFolder == null) {
            return null;
        }

        List<Job> jobs = new ArrayList<>();

        if (inputFolder.isDirectory() && outputFolder.isDirectory()) {

            // Walk the folder contents and generate jobs for all interesting files, resolve conflicts where needed.
            for (File f : inputFolder.listFiles()) {
                if (FilenameUtils.getExtension(f.getName()).equalsIgnoreCase(Constants.INPUT_FILE_EXTENSION)) {
                    for (MessageType messageType : settingsManager.getSelectedMessageTypes()) {
                        Job job = generateJob(f, outputFolder, messageType, jobs);
                        if (job != null) {
                            jobs.add(job);
                        }
                    }
                }
            }

        } else if (inputFolder.isFile() && !outputFolder.exists()) {
            // Just check to see if there's a conflict and try to resolve it
            for (MessageType messageType : settingsManager.getSelectedMessageTypes()) {
                File outputFile = getOutputFile(outputFolder, outputFolder, messageType);
                if (checkConflict(outputFile, jobs)) {
                    outputFile = resolveConflict(inputFolder, outputFile, jobs);
                }

                if (outputFile != null) {
                    jobs.add(new Job(inputFolder, outputFile, messageType));
                }
            }
        } else if (inputFolder.isFile() && outputFolder.isDirectory()) {
            for (MessageType messageType : settingsManager.getSelectedMessageTypes()) {
                File outputFile = getOutputFile(inputFolder, outputFolder, messageType);
                if (checkConflict(outputFile, jobs)) {
                    outputFile = resolveConflict(inputFolder, outputFile, jobs);
                }

                if (outputFile != null) {
                    jobs.add(new Job(inputFolder, outputFile, messageType));
                }
            }
        }

        // Reset our conflict handling strategy before the next run of the parser.
        conflictHandlingStrategy = ConflictHandlingStrategy.ALWAYS_ASK;

        return jobs;
    }

    /**
     * Checks last known conflict resolution strategy and determines whether or not we should prompt the user again.
     * @return True if we should ask the user, false otherwise.
     */
    private boolean shouldPromptUser() {
        return (conflictHandlingStrategy == ConflictHandlingStrategy.SKIP_ONCE ||
                conflictHandlingStrategy == ConflictHandlingStrategy.RENAME_ONCE ||
                conflictHandlingStrategy == ConflictHandlingStrategy.OVERWRITE_ONCE ||
                conflictHandlingStrategy == ConflictHandlingStrategy.ALWAYS_ASK);
    }


    /**
     * Checks to see if the output file already exists or there already exists a Job which will create it.
     *
     * @param outFile The file you want to check for conflicts
     * @param pending The list of currently planned jobs
     * @return True if there exists a confict for that file, false o.w.
     */
    private boolean checkConflict(File outFile, List<Job> pending) {
        boolean confictsWithPending = false;

        for (Job j : pending) {
            if (j.getOutputFile().equals(outFile)) {
                confictsWithPending = true;
            }
        }

        return outFile.exists() || confictsWithPending;
    }

    protected File getOutputFile(File inFile, File outputFolder, MessageType messageType) {
        String origFileName = inFile.getName();
        String directory = outputFolder.getAbsolutePath();
        String filename = FilenameUtils.getBaseName(origFileName);

        return new File(directory + "/" + filename + "_" + messageType.toString() + "." + Constants.OUTPUT_FILE_EXTENSION);
    }

    private File resolveConflict(File inputFile, File outputFile, List<Job> jobs) {
        if (shouldPromptUser()) {
            File suggestedOutputFile = incrementNumericalSuffix(outputFile);
            while (checkConflict(suggestedOutputFile, jobs)) {
                suggestedOutputFile = incrementNumericalSuffix(suggestedOutputFile);
            }

            log.debug("Requesting conflict management from user...");
            conflictHandlingStrategy = uiManager.requestConflictResolution(inputFile, outputFile, suggestedOutputFile);
        }

        if (conflictHandlingStrategy == ConflictHandlingStrategy.SKIP_ONCE ||
                conflictHandlingStrategy == ConflictHandlingStrategy.ALWAYS_SKIP) {
            // Skip that file
            log.debug("Skipping file " + outputFile + ".");
            return null;
        } else if (conflictHandlingStrategy == ConflictHandlingStrategy.ALWAYS_RENAME ||
                conflictHandlingStrategy == ConflictHandlingStrategy.RENAME_ONCE) {
            // Rename file to include incrementing (n) where n is the number of preceding versions
            // If a file of the newly incremented name exists, retry until a conflict is resolved.
            File newFile = incrementNumericalSuffix(outputFile);
            while (checkConflict(newFile, jobs)) {
                newFile = incrementNumericalSuffix(newFile);
            }

            log.debug("Renaming file " + outputFile + " to " + newFile);

            return newFile;
        } else if (conflictHandlingStrategy == ConflictHandlingStrategy.ALWAYS_OVERWRITE ||
                    conflictHandlingStrategy == ConflictHandlingStrategy.OVERWRITE_ONCE) {
            // Just return the output file, we'll overwrite it by default

            log.debug("Overwriting existing file " + outputFile);
            return outputFile;
        }

        log.warn("Conflict unable to be resolved for " + inputFile + " => " + outputFile + ".");
        return null;
    }

    private Job generateJob(File inputFile, File outputFolder, MessageType messageType, List<Job> jobs) {
        File outputFile = getOutputFile(inputFile, outputFolder, messageType);

        // Deal with output filename conflicts
        if (checkConflict(outputFile, jobs)) {
            outputFile = resolveConflict(inputFile, outputFile, jobs);
            if (outputFile == null) {
                // If skip was chosen, return a null job
                return null;
            }
        }

        // Instantiate the actual job
        return new Job(inputFile, outputFile, messageType);
    }

    /**
     * Increments the numerical suffix attached to a file name.
     *
     * Examples:
     * filename.jpg -> filename (1).jpg
     * filename (1).jpg -> filename (2).jpg
     * filename -> filename (1)
     * filename (1) -> filename(2)
     *
     * @param outputFile The File object for the file to be incremented
     * @return A string formatted as described above.
     */
    protected File incrementNumericalSuffix(File outputFile) {
        String origFileName = outputFile.getName();
        String directory = FilenameUtils.getFullPath(outputFile.getAbsolutePath());
        String filename = FilenameUtils.getBaseName(origFileName);
        String extension = FilenameUtils.getExtension(origFileName);

        // Use regular expressions to see if the file has already had previous iterations
        Pattern numericalSuffix = Pattern.compile("\\((\\d+)\\)$");
        Matcher matcher = numericalSuffix.matcher(filename);

        if (matcher.find()) {
            // Increment the existing suffix by one.
            int suffix = Integer.parseInt(matcher.group(1));
            String newSuffix = "(" + (suffix + 1) + ")";
            filename = matcher.replaceFirst(newSuffix);
        } else {
            filename += " (1)";
        }

        // Reconstruct the new filename
        return new File(directory + filename + (extension.length() > 0 ? "." + extension : ""));
    }
}
