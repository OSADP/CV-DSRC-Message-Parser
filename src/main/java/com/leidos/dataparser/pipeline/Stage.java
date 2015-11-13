package com.leidos.dataparser.pipeline;

/**
 * Stage interface to be implemented by each stage in the data processing pipeline.
 *
 * Stages act as transformers between two types, their input and output types. Their {@link Stage#process} method takes
 * in a {@link StageResult} of the stages input type and transforms it to a StageResult of the output type.
 */
public interface Stage<I, O> {
    StageResult<O> process(StageResult<I> input) throws StageException;
}
