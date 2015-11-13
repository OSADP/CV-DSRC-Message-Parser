package com.leidos.dataparser.appcommon;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages the user defined settings for the application.
 */
public class SettingsManager {

    private File inputFolder;
    private File outputFolder;
    private List<MessageType> selectedMessageTypes = new ArrayList<>();

    private Logger log = LogManager.getLogger();

    public File getInputFolder() {
        return inputFolder;
    }

    public void setInputFolder(File inputFolder) {
        this.inputFolder = inputFolder;
    }

    public File getOutputFolder() {
        return outputFolder;
    }

    public void setOutputFolder(File outputFolder) {
        this.outputFolder = outputFolder;
    }

    public MessageType[] getSelectedMessageTypes() {
        MessageType[] out = new MessageType[selectedMessageTypes.size()];
        return selectedMessageTypes.toArray(out);
    }

    public void toggleMessageType(MessageType messageType) {
        if (selectedMessageTypes.contains(messageType)) {
            selectedMessageTypes.remove(messageType);
        } else {
            selectedMessageTypes.add(messageType);
        }
    }

    public List<String> validate() {
        List<String> out = new ArrayList<>();
        log.debug("Validating user-selected settings.");

        if (inputFolder == null) {
            log.debug("Settings invalid, no input folder selected.");
            out.add("No input folder selected.");
        }

        if (inputFolder != null && !inputFolder.exists()) {
            log.debug("Settings invalid, input folder " + inputFolder + " does not exist.");
            out.add("Input folder does not exist.");
        }

        if (outputFolder == null) {
            log.debug("Settings invalid, no output folder selected.");
            out.add("No output folder selected.");
        }

        if (outputFolder != null && !outputFolder.exists()) {
            log.debug("Settings invalid, output folder " + outputFolder + " does not exist.");
            out.add("Output folder does not exist.");
        }

        if (selectedMessageTypes.size() == 0) {
            log.debug("Settings invalid, please select at least one message type.");
            out.add("No message type selected.");
        }

        return out;
    }


    @Override
    public String toString() {
        return "SettingsManager{" +
                "inputFolder=" + inputFolder +
                ", outputFolder=" + outputFolder +
                ", selectedMessageTypes=" + selectedMessageTypes +
                '}';
    }

    public String getMissingElements() {
        String out = "";

        if (inputFolder == null) {
            out += "input folder,";
        }

        if (outputFolder == null) {
            out += "out folder,";
        }

        if (selectedMessageTypes.size() == 0) {
            out += "at least 1 message type,";
        }

        // Convert the last comma into a period
        return out.replaceFirst(",$", ".");
    }
}
