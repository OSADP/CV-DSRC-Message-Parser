package com.leidos.dataparser.pipeline;

/**
 * Sentinel value used to mark the starting stage of a pipeline.
 */
public class PipelineEntryPoint implements StageResult {
    @Override
    public Object get() {
        return null;
    }
}
