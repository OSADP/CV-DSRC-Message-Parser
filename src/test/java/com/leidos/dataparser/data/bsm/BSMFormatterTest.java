package com.leidos.dataparser.data.bsm;

import com.leidos.dataparser.data.csv.CSVBuilder;
import com.leidos.dataparser.data.pcap.PCAPParser;
import com.leidos.dataparser.pipeline.GenericStageResult;
import com.leidos.dataparser.pipeline.StageException;
import com.leidos.dataparser.pipeline.StageResult;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created by rushk1 on 5/20/2015.
 */
public class BSMFormatterTest {

    StageResult<List<BSM>> input;

    @Before
    public void simulatePipeline() {
        PCAPParser pcap = new PCAPParser();
        BSMParser bsm = new BSMParser();
        Path path = Paths.get("testdata/0x0123_dsrc1_all_2013_09_14_12_39_11.pcap");
        StageResult<byte[]> parserInput = null;
        try {
            parserInput = new GenericStageResult<>(Files.readAllBytes(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            input = bsm.process(pcap.process(parserInput));
        } catch (StageException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testProcess() throws Exception {
        CSVBuilder builder = new CSVBuilder();
        for (BSM bsm : input.get()) {
            builder.formatOutputData("", bsm);
        }

        System.out.println(builder);
    }
}