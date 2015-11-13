package com.leidos.dataparser.ui;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import com.leidos.dataparser.appcommon.MessageType;
import com.leidos.dataparser.appcommon.SettingsManager;
import com.leidos.dataparser.executor.ParserExecutor;
import org.springframework.beans.factory.annotation.Autowired;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;

/**
 * Main User Interface for the DPA
 */
public class MainUiForm {
    private JProgressBar processingProgressBar;
    private JButton startProcessingButton;
    private JTextField inputFileTextField;
    private JTextField outputFileTextField;
    private JButton outputFileBrowseButton;
    private JButton inputFileBrowseButton;
    private JPanel mainPanel;
    private JCheckBox BSMCheckBox;
    private JCheckBox FHWASPaTCheckBox;
    private JCheckBox MAPCheckBox;
    private JCheckBox SAESPaTCheckBox;
    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenu helpMenu;
    private JFileChooser directoryChooser;
    private JFileChooser fileChooser;

    private static final double BYTE_CONVERSION_FACTOR = 1_000_000.0;

    @Autowired
    SettingsManager settingsManager;

    @Autowired
    ParserExecutor executor;

    @Autowired
    UIManager uiManager;


    public MainUiForm() {
        initializeMenuBar();
        directoryChooser = new JFileChooser();
        fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        directoryChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

        outputFileBrowseButton.addActionListener(e -> {
            int retval = directoryChooser.showOpenDialog(mainPanel);
            if (retval == JFileChooser.APPROVE_OPTION) {
                File file = directoryChooser.getSelectedFile();
                outputFileTextField.setText(file.getAbsolutePath());
            }
            resetProgress();
        });

        inputFileBrowseButton.addActionListener(e -> {
            int retval = directoryChooser.showOpenDialog(mainPanel);
            if (retval == JFileChooser.APPROVE_OPTION) {
                File file = directoryChooser.getSelectedFile();
                inputFileTextField.setText(file.getAbsolutePath());
            }
            resetProgress();
        });

        startProcessingButton.addActionListener(e -> {
            settingsManager.setOutputFolder(new File(outputFileTextField.getText()));
            settingsManager.setInputFolder(new File(inputFileTextField.getText()));
            if (settingsManager.validate().isEmpty()) {
                executor.execute();
            } else {
                String errors = "";
                for (String error : settingsManager.validate()) {
                    errors += error;
                    errors += "\n";
                }

                JOptionPane.showMessageDialog(mainPanel, "Please resolve the following errors:\n\n" + errors);
            }
        });

        BSMCheckBox.addActionListener(e -> {
            settingsManager.toggleMessageType(MessageType.BSM);
            resetProgress();
        });
        FHWASPaTCheckBox.addActionListener(e -> {
            settingsManager.toggleMessageType(MessageType.FHWA_SPAT);
            resetProgress();
        });
        MAPCheckBox.addActionListener(e -> {
            settingsManager.toggleMessageType(MessageType.MAP);
            resetProgress();
        });
    }

    private void initializeMenuBar() {
        // Initialize the JMenuBar since IDEA Gui Designer can't do it automatically
        menuBar = new JMenuBar();
        fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);
        fileMenu.getAccessibleContext().setAccessibleDescription("Operate on files and settings. The primary menubar entry for this app.");
        menuBar.add(fileMenu);

        /*JMenuItem loadItem = new JMenuItem("Load settings...");
        loadItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        loadItem.getAccessibleContext().setAccessibleDescription("Load presaved settings from a file.");
        loadItem.addActionListener(e -> fileChooser.showOpenDialog(mainPanel));
        fileMenu.add(loadItem);

        JMenuItem saveItem = new JMenuItem("Save settings...");
        saveItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        saveItem.getAccessibleContext().setAccessibleDescription("Save settings to a file for later use.");
        saveItem.addActionListener(e -> fileChooser.showSaveDialog(mainPanel));
        fileMenu.add(saveItem); */

        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.getAccessibleContext().setAccessibleDescription("Exit the application");
        exitItem.addActionListener(e -> System.exit(0));
        fileMenu.add(exitItem);

        helpMenu = new JMenu("Help");
        helpMenu.setMnemonic(KeyEvent.VK_H);
        helpMenu.getAccessibleContext().setAccessibleDescription("Access help and other information.");
        menuBar.add(helpMenu);

        JMenuItem helpItem = new JMenuItem("Help");
        helpItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, ActionEvent.CTRL_MASK));
        helpItem.getAccessibleContext().setAccessibleDescription("Access detailed usage instructions for this application.");
        helpMenu.add(helpItem);
        helpItem.addActionListener(e -> uiManager.displayHelpDialog());

        JMenuItem aboutItem = new JMenuItem("About");
        helpItem.getAccessibleContext().setAccessibleDescription("Access detailed information about this application");
        aboutItem.addActionListener(e -> {
            uiManager.displayAboutDialog();
        });
        helpMenu.add(aboutItem);
    }

    public JMenuBar getMenuBar() {
        return menuBar;
    }

    public JPanel getContent() {
        return mainPanel;
    }

    public void updateProgress(int progress) {
        processingProgressBar.setValue(progress);
        processingProgressBar.setString("" + progress + "%");
        processingProgressBar.setStringPainted(true);
    }

    public void resetProgress() {
        processingProgressBar.setValue(0);
        processingProgressBar.setString("");
        processingProgressBar.setStringPainted(false);
    }

    public void notifyCompletion(long timeElapsed, int successes, int errors, long byteSuccesses, long byteFailures) {
        JOptionPane.showMessageDialog(mainPanel,
                String.format("Data Processing Finished!\nSuccessfully processed %d of %d tasks (%s of %s MB) in %ds." +
                                "\n%d tasks resulted in errors.\nCheck the log files for more information.",
                        successes,
                        successes + errors,
                        String.format("%1.1f", byteSuccesses / BYTE_CONVERSION_FACTOR),
                        String.format("%1.1f", (byteSuccesses + byteFailures) / BYTE_CONVERSION_FACTOR),
                        (int) Math.ceil(timeElapsed / 1000.0),
                        errors));

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
        mainPanel.setLayout(new GridLayoutManager(4, 1, new Insets(10, 20, 10, 20), -1, -1));
        mainPanel.setOpaque(true);
        mainPanel.setPreferredSize(new Dimension(700, 225));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 4, new Insets(0, 0, 0, 0), -1, -1));
        mainPanel.add(panel1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        inputFileBrowseButton = new JButton();
        inputFileBrowseButton.setText("Browse...");
        inputFileBrowseButton.setToolTipText("Open a dialog window to select the input file or folder.");
        panel1.add(inputFileBrowseButton, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        inputFileTextField = new JTextField();
        inputFileTextField.setText("");
        inputFileTextField.setToolTipText("Enter the location of the file or folder containing the data to be processed.");
        panel1.add(inputFileTextField, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("Input Files:");
        panel1.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        panel1.add(spacer1, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_GROW, 1, new Dimension(30, -1), null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 4, new Insets(0, 0, 0, 0), -1, -1));
        mainPanel.add(panel2, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel2.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Select Message Type:"));
        BSMCheckBox = new JCheckBox();
        BSMCheckBox.setText("BSM");
        BSMCheckBox.setToolTipText("Process Basic Safety Messages in input files.");
        panel2.add(BSMCheckBox, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(58, 28), null, 0, false));
        FHWASPaTCheckBox = new JCheckBox();
        FHWASPaTCheckBox.setText("FHWA SPaT");
        FHWASPaTCheckBox.setToolTipText("Process FHWA Signal Phase and Timing messages in input files.");
        panel2.add(FHWASPaTCheckBox, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(106, 28), null, 0, false));
        MAPCheckBox = new JCheckBox();
        MAPCheckBox.setText("MAP");
        MAPCheckBox.setToolTipText("Process Geographic Intersection Description/MAP messages in input files.");
        panel2.add(MAPCheckBox, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(59, 28), null, 0, false));
        SAESPaTCheckBox = new JCheckBox();
        SAESPaTCheckBox.setEnabled(false);
        SAESPaTCheckBox.setText("SAE SPaT");
        SAESPaTCheckBox.setToolTipText("Process SAE Signal Phase and Timing messages in input files.");
        panel2.add(SAESPaTCheckBox, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(90, 28), null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        mainPanel.add(panel3, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        processingProgressBar = new JProgressBar();
        processingProgressBar.setFocusable(false);
        processingProgressBar.setStringPainted(false);
        processingProgressBar.setToolTipText("Displays progress of task completion.");
        panel3.add(processingProgressBar, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        startProcessingButton = new JButton();
        startProcessingButton.setText("Start Processing");
        startProcessingButton.setToolTipText("Begin processing the input files.");
        panel3.add(startProcessingButton, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        mainPanel.add(panel4, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        outputFileBrowseButton = new JButton();
        outputFileBrowseButton.setHorizontalAlignment(0);
        outputFileBrowseButton.setText("Browse...");
        outputFileBrowseButton.setToolTipText("Open a dialog window to select a folder to output processed files to.");
        panel4.add(outputFileBrowseButton, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("Output Location:");
        panel4.add(label2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        outputFileTextField = new JTextField();
        outputFileTextField.setToolTipText("Enter the location output files should be saved to.");
        panel4.add(outputFileTextField, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return mainPanel;
    }
}