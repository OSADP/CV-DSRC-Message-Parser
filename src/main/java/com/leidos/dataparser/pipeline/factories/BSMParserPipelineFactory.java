package com.leidos.dataparser.pipeline.factories;

import com.leidos.dataparser.data.bsm.BSM;
import com.leidos.dataparser.data.bsm.BSMFormatter;
import com.leidos.dataparser.data.bsm.BSMParser;
import com.leidos.dataparser.data.pcap.PCAPPacket;
import com.leidos.dataparser.data.pcap.PCAPParser;
import com.leidos.dataparser.executor.Job;
import com.leidos.dataparser.io.FileInputStage;
import com.leidos.dataparser.io.FileOutputStage;
import com.leidos.dataparser.pipeline.*;

import java.util.List;

/**
 * Generates a complete (entry to exit) pipeline to parse, format, and output BSM data.
 *
 * Stages:
 * 1. FileInputStage
 * 2. WSMPParser
 * 3. BSMParser
 * 4. BSMFormatter
 * 5. FileOutputStage
 */
public class BSMParserPipelineFactory implements PipelineFactory {
    public BSMParserPipelineFactory() {
    }

    @Override
    /**
     * Generate a BSM processing pipeline consisting of the following stages composed into a CompositeStage object:
     * 1. FileInputStage
     * 2. BSMParser
     * 3. BSMFormatter
     * 4. FileOutputStage
     */
    public Pipeline createPipeline(Job j) {
        Stage<PipelineEntryPoint, List<PCAPPacket>> cmp1 = new CompositeStage<>(
                new FileInputStage(j.getInputFile()),
                new PCAPParser());

        Stage<PipelineEntryPoint, List<BSM>> cmp2 = new CompositeStage<>(
                cmp1,
                new BSMParser()
        );

        Stage<PipelineEntryPoint, List<String>> cmp3 = new CompositeStage<>(
                cmp2,
                new BSMFormatter()
        );

        Stage<PipelineEntryPoint, PipelineExitPoint> cmp4 = new CompositeStage<>(
                cmp3,
                new FileOutputStage(j.getOutputFile())
        );

        return new DataProcessingPipeline(cmp4);
    }
}
