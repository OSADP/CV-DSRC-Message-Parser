package com.leidos.dataparser.data.bsm;

import com.leidos.dataparser.data.csv.CSVBuilder;
import com.leidos.dataparser.pipeline.GenericStageResult;
import com.leidos.dataparser.pipeline.Stage;
import com.leidos.dataparser.pipeline.StageException;
import com.leidos.dataparser.pipeline.StageResult;

import java.util.Arrays;
import java.util.List;

/**
 * Formats a list of BSM objects into human/application readable output for final presentation to user.
 */
public class BSMFormatter implements Stage<List<BSM>, List<String>>{
    public StageResult<List<String>> process(StageResult<List<BSM>> input) throws StageException {
        List<BSM> messages = input.get();

        CSVBuilder builder = new CSVBuilder();

        for (BSM bsm : messages) {
            builder.formatOutputData("bsm", bsm);
        }

        return new GenericStageResult<>(Arrays.asList(builder.toString().split("\n")));
    }
}
