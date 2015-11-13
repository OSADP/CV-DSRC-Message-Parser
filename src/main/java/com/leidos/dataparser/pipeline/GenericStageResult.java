package com.leidos.dataparser.pipeline;


/**
 * Created by rushk1 on 5/12/2015.
 */
public class GenericStageResult<T> implements StageResult<T> {

    private T data;

    public GenericStageResult(T data) {
        this.data = data;
    }

    @Override
    public T get() {
        return data;
    }
}
