package com.leidos.dataparser.ui;

import com.leidos.dataparser.executor.JobCreator;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.swing.*;
import java.io.File;

/**
 * Class responbsible for management of the various UI components needed by the Data Parser Application.
 *
 * Classes requesting access to the user interface should make requests against this object instead of against the
 * UI objects directly.
 */
public class UIManager {
    @Autowired
    MainUiForm mainUiForm;


    public synchronized void updateParsingProgressBar(double bytesProcessed, double totalBytes) {
        mainUiForm.updateProgress((int) ((bytesProcessed * 100) / totalBytes));
    }

    public void notifyParsingCompletion(long timeElapsed, int successes, int errors,
                                        long byteSuccesses, long byteFailures) {
        mainUiForm.notifyCompletion(timeElapsed, successes, errors, byteSuccesses, byteFailures);
    }

    /**
     * Poll the user for a conflict resolution strategy
     * @param file1
     * @param file2
     * @return
     */
    public JobCreator.ConflictHandlingStrategy requestConflictResolution(File file1, File file2, File suggestedFileName) {
        String filename = FilenameUtils.getName(suggestedFileName.getAbsolutePath());
        JFrame parent = null;
        JDialog conflictResolver = new JDialog(parent, "Resolve File Conflict...", true);
        ConflictResolutionDialog dialogContent = new ConflictResolutionDialog(conflictResolver,
                file1, file2, suggestedFileName);
        conflictResolver.setContentPane(dialogContent.getContent());

        conflictResolver.pack();
        conflictResolver.setLocationByPlatform(true);
        conflictResolver.setVisible(true);

        return dialogContent.getUserConflictHandlingStrategy();
    }

    public boolean getParsingConfirmation(File inputFolder, File outputFolder,
                                         long numFiles, int numJobs, long numBytes) {
        ParsingConfirmationDialog dialog = new ParsingConfirmationDialog(inputFolder, outputFolder,
                numFiles, numJobs, numBytes);

        dialog.pack();
        dialog.setLocationByPlatform(true);
        dialog.setVisible(true);

        return dialog.getConfirmed();
    }

    public void displayAboutDialog() {
        AboutDialog aboutDialog = new AboutDialog();
        aboutDialog.pack();
        aboutDialog.setLocationByPlatform(true);
        aboutDialog.setVisible(true);
    }

    public void displayHelpDialog() {
        HelpDialog helpDialog = new HelpDialog();
        helpDialog.pack();
        helpDialog.setLocationByPlatform(true);
        helpDialog.setVisible(true);
    }
}
