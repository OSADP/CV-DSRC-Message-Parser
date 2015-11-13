package com.leidos.dataparser.data.pcap;

import com.leidos.dataparser.pipeline.GenericStageResult;
import com.leidos.dataparser.pipeline.StageException;
import com.leidos.dataparser.pipeline.StageResult;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Test the PCAP parsing functionality
 */
public class PCAPParserTest {

    @Test
    public void testProcess() throws Exception {
        PCAPParser parser = new PCAPParser();
        String current = new java.io.File( "." ).getCanonicalPath();
        System.out.println("Current dir:"+current);
        StageResult<byte[]> parserInput = new GenericStageResult<>(Files.readAllBytes(Paths.get("testdata/0x0123_dsrc1_all_2013_09_14_12_39_11.pcap")));

        StageResult<List<PCAPPacket>> results = parser.process(parserInput);
        System.out.println(results);

    }
}