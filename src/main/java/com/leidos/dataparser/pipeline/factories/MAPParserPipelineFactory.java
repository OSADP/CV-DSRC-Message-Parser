package com.leidos.dataparser.pipeline.factories;

import com.leidos.dataparser.data.map.MAPFormatter;
import com.leidos.dataparser.data.map.MapMessage;
import com.leidos.dataparser.data.map.MapParser;
import com.leidos.dataparser.data.pcap.PCAPPacket;
import com.leidos.dataparser.data.pcap.PCAPParser;
import com.leidos.dataparser.executor.Job;
import com.leidos.dataparser.io.FileInputStage;
import com.leidos.dataparser.io.FileOutputStage;
import com.leidos.dataparser.pipeline.*;

import java.util.List;

/**
 * Represents an end to end (entry to exit) MAP processing pipeline.
 */
public class MAPParserPipelineFactory implements PipelineFactory {

    @Override
    /**
     * Create a pipeline for parsing MAP messages.
     *
     * Stages:
     * 1) PCAP Parser
     * 2) MAP Parser
     * 3) MAP Formatter
     * 4) File Output
     *
     * @param j The job the pipeline should be created for.
     */
    public Pipeline createPipeline(Job j) {
        Stage<PipelineEntryPoint, List<PCAPPacket>> cmp1 = new CompositeStage<>(
                new FileInputStage(j.getInputFile()),
                new PCAPParser());

        Stage<PipelineEntryPoint, List<MapMessage>> cmp2 = new CompositeStage<>(
                cmp1,
                new MapParser()
        );

        Stage<PipelineEntryPoint, List<String>> cmp3 = new CompositeStage<>(
                cmp2,
                new MAPFormatter()
        );

        Stage<PipelineEntryPoint, PipelineExitPoint> cmp4 = new CompositeStage<>(
                cmp3,
                new FileOutputStage(j.getOutputFile())
        );

        return new DataProcessingPipeline(cmp4);
    }
}
