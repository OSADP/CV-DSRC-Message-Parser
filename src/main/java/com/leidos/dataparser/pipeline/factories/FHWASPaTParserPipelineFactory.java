package com.leidos.dataparser.pipeline.factories;

import com.leidos.dataparser.data.pcap.PCAPPacket;
import com.leidos.dataparser.data.pcap.PCAPParser;
import com.leidos.dataparser.data.spat.SPATParser;
import com.leidos.dataparser.data.spat.SPaTFormatter;
import com.leidos.dataparser.data.spat.SpatMessage;
import com.leidos.dataparser.executor.Job;
import com.leidos.dataparser.io.FileInputStage;
import com.leidos.dataparser.io.FileOutputStage;
import com.leidos.dataparser.pipeline.*;

import java.util.List;

/**
 * Created by rushk1 on 5/13/2015.
 */
public class FHWASPaTParserPipelineFactory implements PipelineFactory {
    @Override
    public Pipeline createPipeline(Job j) {
        Stage<PipelineEntryPoint, List<PCAPPacket>> cmp1 = new CompositeStage<>(
                new FileInputStage(j.getInputFile()),
                new PCAPParser());

        Stage<PipelineEntryPoint, List<SpatMessage>> cmp2 = new CompositeStage<>(
                cmp1,
                new SPATParser()
        );

        Stage<PipelineEntryPoint, List<String>> cmp3 = new CompositeStage<>(
                cmp2,
                new SPaTFormatter()
        );

        Stage<PipelineEntryPoint, PipelineExitPoint> cmp4 = new CompositeStage<>(
                cmp3,
                new FileOutputStage(j.getOutputFile())
        );

        return new DataProcessingPipeline(cmp4);
    }
}
