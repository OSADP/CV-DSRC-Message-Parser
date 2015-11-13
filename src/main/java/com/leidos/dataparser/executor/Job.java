package com.leidos.dataparser.executor;

import com.leidos.dataparser.appcommon.MessageType;

import java.io.File;

/**
 * Represents a job to be used in the executor job queue pattern.
 */
public class Job {
    private File inputFile;
    private File outputFile;
    private MessageType type;

    public Job(File inputFile, File outputFile, MessageType type) {
        this.inputFile = inputFile;
        this.outputFile = outputFile;
        this.type = type;
    }

    public File getInputFile() {
        return inputFile;
    }

    public void setInputFile(File inputFile) {
        this.inputFile = inputFile;
    }

    public File getOutputFile() {
        return outputFile;
    }

    public void setOutputFile(File outputFile) {
        this.outputFile = outputFile;
    }

    public MessageType getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Job job = (Job) o;

        if (inputFile != null ? !inputFile.equals(job.inputFile) : job.inputFile != null) return false;
        return !(outputFile != null ? !outputFile.equals(job.outputFile) : job.outputFile != null);

    }

    @Override
    public int hashCode() {
        int result = inputFile != null ? inputFile.hashCode() : 0;
        result = 31 * result + (outputFile != null ? outputFile.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Job{" +
                "inputFile=" + inputFile +
                ", outputFile=" + outputFile +
                ", type=" + type +
                '}';
    }
}
