package com.leidos.dataparser.ui;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.leidos.dataparser.executor.JobCreator;

import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * Created by rushk1 on 10/14/2015.
 */
public class ConflictResolutionDialog {
    private JDialog parent;

    private JCheckBox resolveRemainingCheckBox;
    private JButton skipButton;
    private JButton overwriteButton;
    private JButton renameButton;
    private JPanel mainPanel;
    private JLabel conflictDescriptionTextArea;

    private File file1;
    private File file2;
    private File suggestedRename;

    // Set the default behavior when closing the window to skip the current file.
    private JobCreator.ConflictHandlingStrategy conflictHandlingStrategy = JobCreator.ConflictHandlingStrategy.SKIP_ONCE;

    public ConflictResolutionDialog(JDialog parent, File file1, File file2, File suggestedRename) {
        this.parent = parent;
        this.file1 = file1;
        this.file2 = file2;
        this.suggestedRename = suggestedRename;

        formatTextArea();
        setupActionListeners();
    }

    private void setupActionListeners() {
        renameButton.addActionListener(action -> {
            if (resolveRemainingCheckBox.isSelected()) {
                conflictHandlingStrategy = JobCreator.ConflictHandlingStrategy.ALWAYS_RENAME;
            } else {
                conflictHandlingStrategy = JobCreator.ConflictHandlingStrategy.RENAME_ONCE;
            }
            parent.dispose();
        });

        overwriteButton.addActionListener(action -> {
            if (resolveRemainingCheckBox.isSelected()) {
                conflictHandlingStrategy = JobCreator.ConflictHandlingStrategy.ALWAYS_OVERWRITE;
            } else {
                conflictHandlingStrategy = JobCreator.ConflictHandlingStrategy.OVERWRITE_ONCE;
            }
            parent.dispose();
        });

        skipButton.addActionListener(action -> {
            if (resolveRemainingCheckBox.isSelected()) {
                conflictHandlingStrategy = JobCreator.ConflictHandlingStrategy.ALWAYS_SKIP;
            } else {
                conflictHandlingStrategy = JobCreator.ConflictHandlingStrategy.SKIP_ONCE;
            }
            parent.dispose();
        });
    }

    public JobCreator.ConflictHandlingStrategy getUserConflictHandlingStrategy() {
        return conflictHandlingStrategy;
    }

    private void formatTextArea() {
        conflictDescriptionTextArea.setText("<html><h1>The following file conflict has occurred:</h1>" +
                "<b>File:</b><br><br>" + file2 + "<br><br>the processing output for <b>File:</b><br><br>" +
                file1 + "<br><br> conflicts with an existing/pending file.<br><br>" +
                "Please choose how to resolve this conflict:<br>" +
                "1) <u>Rename</u> this file to " + suggestedRename.getName() + ".<br>" +
                "2) <u>Overwrite</u> the existing/pending file with this one.<br>" +
                "3) <u>Skip</u> this file, omitting it from the output.<br></html>");
    }

    public JPanel getContent() {
        return mainPanel;
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayoutManager(5, 1, new Insets(0, 0, 0, 0), -1, -1));
        mainPanel.setMaximumSize(new Dimension(361, 185));
        mainPanel.setOpaque(false);
        skipButton = new JButton();
        skipButton.setText("Skip");
        mainPanel.add(skipButton, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        overwriteButton = new JButton();
        overwriteButton.setText("Overwrite");
        mainPanel.add(overwriteButton, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        renameButton = new JButton();
        renameButton.setText("Rename");
        mainPanel.add(renameButton, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        resolveRemainingCheckBox = new JCheckBox();
        resolveRemainingCheckBox.setText("Use this decision to resolve all remaining conflicts");
        mainPanel.add(resolveRemainingCheckBox, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 1, new Insets(5, 5, 5, 5), -1, -1));
        mainPanel.add(panel1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 2, false));
        conflictDescriptionTextArea = new JLabel();
        conflictDescriptionTextArea.setText("Label");
        panel1.add(conflictDescriptionTextArea, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return mainPanel;
    }
}
