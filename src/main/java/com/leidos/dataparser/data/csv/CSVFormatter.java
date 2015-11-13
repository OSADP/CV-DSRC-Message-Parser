package com.leidos.dataparser.data.csv;

import com.leidos.dataparser.io.formatting.OutputData;
import com.leidos.dataparser.pipeline.GenericStageResult;
import com.leidos.dataparser.pipeline.Stage;
import com.leidos.dataparser.pipeline.StageException;
import com.leidos.dataparser.pipeline.StageResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.List;

/**
 * Formats a list of output data into a list of CSV lines with a header at the top
 */
public class CSVFormatter implements Stage<List<OutputData>, List<String>> {

    private Logger log = LogManager.getLogger();

    @Override
    public StageResult<List<String>> process(StageResult<List<OutputData>> input) throws StageException {
        log.trace("Entering CSV Formatting Step.");

        CSVBuilder builder = new CSVBuilder();
        for (Object datum : input.get()) {
            if (datum instanceof OutputData) {
                builder.formatOutputData("", (OutputData) datum);
            }
        }

        String[] rows = builder.toString().split("\n");

        log.trace(rows.length + " CSV rows generated.");

        log.trace("Exiting CSV Formatting Step.");

        return new GenericStageResult<>(Arrays.asList(rows));
    }
}
