package com.leidos.dataparser.pipeline;

/**
 * Setinel type used to mark the end stage of a pipeline.
 */
public class PipelineExitPoint implements StageResult {
    @Override
    public Object get() {
        return null;
    }
}
