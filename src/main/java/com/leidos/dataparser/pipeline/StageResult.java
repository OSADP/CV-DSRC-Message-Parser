package com.leidos.dataparser.pipeline;

/**
 * Stores the typed output/input values of a stage as well as carries along any extra data
 */
public interface StageResult<T> {
    T get();
}
