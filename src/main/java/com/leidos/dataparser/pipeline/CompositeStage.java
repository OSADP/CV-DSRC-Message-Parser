package com.leidos.dataparser.pipeline;

/**
 * Composes two states with identical output/input types into one Stage.
 */
public class CompositeStage<A, B, C> implements Stage<A, C> {

    private Stage<A, B> stage1;
    private Stage<B, C> stage2;

    public CompositeStage(Stage<A, B> stage1, Stage<B, C> stage2) {
        this.stage1 = stage1;
        this.stage2 = stage2;
    }

    @Override
    public StageResult<C> process(StageResult<A> input) throws StageException {
        return stage2.process(stage1.process(input));
    }
}
