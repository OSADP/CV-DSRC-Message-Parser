package com.leidos.dataparser.pipeline.factories;

import com.leidos.dataparser.executor.Job;
import com.leidos.dataparser.pipeline.Pipeline;

/**
 * Generic interface used to provide Pipeline production capabilities. Any new Pipeline factory object should implement
 * this. Generally a PipelineFactory will take the starting parameters of the job, configure the initial state, compose
 * the intermediate stages, and configure and compose the terminal stage into a Pipeline implementation like
 * {@link com.leidos.dataparser.pipeline.DataProcessingPipeline}.
 */
public interface PipelineFactory {
    Pipeline createPipeline(Job j);
}
