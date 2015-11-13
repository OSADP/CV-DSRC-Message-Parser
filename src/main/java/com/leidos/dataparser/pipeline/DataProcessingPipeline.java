package com.leidos.dataparser.pipeline;

/**
 * Generic DataProcessingPipeline accepting only a complete (entry to exit) pipeline.
 */
public class DataProcessingPipeline implements Pipeline {

    private Stage<PipelineEntryPoint, PipelineExitPoint> stage;

    public DataProcessingPipeline(Stage<PipelineEntryPoint, PipelineExitPoint> stage) {
        this.stage = stage;
    }

    @Override
    public void execute() throws StageException {
        stage.process(new GenericStageResult<>(new PipelineEntryPoint()));
    }
}
