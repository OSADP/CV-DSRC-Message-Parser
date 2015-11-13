package com.leidos.dataparser.io;

import com.leidos.dataparser.pipeline.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.List;

/**
 * Implements final output to file on disk for a parsing pipeline.
 */
public class FileOutputStage implements Stage<List<String>, PipelineExitPoint> {

    private Logger log = LogManager.getLogger();

    private File outputFile;

    public FileOutputStage(File outputFile) {
        this.outputFile = outputFile;
    }

    @Override
    public StageResult<PipelineExitPoint> process(StageResult<List<String>> input) throws StageException {
        log.trace("Entering File Output Step.");

        if (input.get().size() == 0) {
            log.warn("No rows to write to file, skipping...");
            return new GenericStageResult<>(new PipelineExitPoint());
        }

        try {
            PrintStream ps = new PrintStream(outputFile);

            for (String line : input.get()) {
                ps.println(line);
            }

            ps.close();

            log.debug("Wrote " + input.get().size() + " lines to " + outputFile + ".");

            log.trace("Exiting File Output Step.");

            return new GenericStageResult<>(new PipelineExitPoint());
        } catch (FileNotFoundException e) {
            log.error("Unable to write to output file " + outputFile.getPath());
            throw new StageException(getClass(), "Unable to write to output file " + outputFile.getPath());
        }
    }
}
