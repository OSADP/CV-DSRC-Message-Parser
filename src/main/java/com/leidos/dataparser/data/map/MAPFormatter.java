package com.leidos.dataparser.data.map;

import com.leidos.dataparser.data.csv.CSVBuilder;
import com.leidos.dataparser.pipeline.GenericStageResult;
import com.leidos.dataparser.pipeline.Stage;
import com.leidos.dataparser.pipeline.StageException;
import com.leidos.dataparser.pipeline.StageResult;

import java.util.Arrays;
import java.util.List;

/**
 * Created by rushk1 on 10/5/2015.
 */
public class MAPFormatter implements Stage<List<MapMessage>, List<String>> {
    @Override
    /**
     * Formats a list of MAP messages into a list of CSV formatted rows. The first row will be a header containing the
     * titles of the columns found in the messages.
     */
    public StageResult<List<String>> process(StageResult<List<MapMessage>> input) throws StageException {
        List<MapMessage> messages = input.get();

        CSVBuilder builder = new CSVBuilder();

        for (MapMessage map : messages) {
            builder.formatOutputData("map", map);
        }

        return new GenericStageResult<>(Arrays.asList(builder.toString().split("\n")));
    }
}
