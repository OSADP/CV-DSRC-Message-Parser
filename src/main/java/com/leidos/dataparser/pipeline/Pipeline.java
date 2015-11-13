package com.leidos.dataparser.pipeline;

/**
 * Describes a general purpose data processing pipeline.
 */
public interface Pipeline {
    void execute() throws StageException;
}
