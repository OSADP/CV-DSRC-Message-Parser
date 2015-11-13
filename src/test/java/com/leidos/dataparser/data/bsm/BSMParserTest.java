package com.leidos.dataparser.data.bsm;

import com.leidos.dataparser.data.pcap.PCAPPacket;
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
 * Test the BSM parsing step of the parsing pipeline.
 */
public class BSMParserTest {

    private StageResult<List<PCAPPacket>> input;

    @Before
    public void setupPipeline() {
        PCAPParser pcap = new PCAPParser();
        Path path = Paths.get("testdata/0x0123_dsrc1_all_2013_09_14_12_39_11.pcap");
        StageResult<byte[]> parserInput = null;
        try {
            parserInput = new GenericStageResult<>(Files.readAllBytes(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            input = pcap.process(parserInput);
        } catch (StageException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testParse() throws Exception {
        BSMParser bsm = new BSMParser();
        bsm.process(input);
    }
}