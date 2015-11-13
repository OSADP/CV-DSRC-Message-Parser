package com.leidos.dataparser.data.spat;

import com.leidos.dataparser.data.csv.CSVBuilder;
import com.leidos.dataparser.pipeline.GenericStageResult;
import com.leidos.dataparser.pipeline.Stage;
import com.leidos.dataparser.pipeline.StageException;
import com.leidos.dataparser.pipeline.StageResult;

import java.util.Arrays;
import java.util.List;

/**
 * CSV Formats a list of SpatMessages.
 *
 * Will produce one row per SpatMessage and one Column per field in the SpatMessage.
 */
public class SPaTFormatter implements Stage<List<SpatMessage>, List<String>> {
    @Override
    public StageResult<List<String>> process(StageResult<List<SpatMessage>> input) throws StageException {
        List<SpatMessage> messages = input.get();

        CSVBuilder builder = new CSVBuilder();

        for (SpatMessage spat : messages) {
            builder.formatOutputData("spat", spat);
        }

        return new GenericStageResult<>(Arrays.asList(builder.toString().split("\n")));
    }
}
