package com.leidos.dataparser.pipeline.factories;

import com.leidos.dataparser.appcommon.MessageType;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * A service locator class for the PipelineFactory implementations
 */
public class PipelineFactoryLocator {
    private Map<MessageType, PipelineFactory> factories = new HashMap<>();

    @Autowired
    BSMParserPipelineFactory bsmParserPipelineFactory;

    @Autowired
    FHWASPaTParserPipelineFactory fhwaSPaTParserPipelineFactory;

    @Autowired
    MAPParserPipelineFactory mapParserPipelineFactory;

    public PipelineFactoryLocator() {
    }

    @PostConstruct
    private void init() {
        factories.put(MessageType.BSM, bsmParserPipelineFactory);
        factories.put(MessageType.FHWA_SPAT, fhwaSPaTParserPipelineFactory);
        factories.put(MessageType.MAP, mapParserPipelineFactory);
    }

    /**
     * Return the correct PipelineFactory instance based on the input message type. If no such PipelineFactory is
     * available return null.
     * @param type The SettingsManager.MessageType enum value corresponding to the factory you want to get
     * @return If it exists, the PipelineFactory, null o.w.
     */
    public PipelineFactory getFactory(MessageType type) {
        return factories.get(type);
    }

}
