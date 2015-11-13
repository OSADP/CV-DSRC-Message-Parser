package com.leidos.dataparser.io;

import com.leidos.dataparser.pipeline.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Loads a File object into a byte[] buffer for usage in future stages.
 */
public class FileInputStage implements Stage<PipelineEntryPoint, byte[]> {

    private Logger log = LogManager.getLogger();

    private File input;

    public FileInputStage(File input) {
        this.input = input;
    }

    @Override
    public StageResult<byte[]> process(StageResult entry) throws StageException {
        log.trace("Entering File Input Step.");

        log.debug("Reading file " + input + "...");

        Path path = Paths.get(input.getPath());
        try {
            byte[] out = Files.readAllBytes(path);
            log.debug("Read " + out.length + " bytes from file.");

            log.trace("Exiting File Input Step.");
            return new GenericStageResult<>(out);
        } catch (IOException e) {
            log.error("Unable to load " + input.getPath() + "from disk.");
            throw new StageException(getClass(), "Unable to load " + input.getPath() + "from disk.");
        }
    }
}
