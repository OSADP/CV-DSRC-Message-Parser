package com.leidos.dataparser.appcommon;

/**
 * Defines the message types parseable by the DPA system.
 *
 * Also responsible for mapping logical message types to their string prefix representation for fetching beans from
 * the application context.
 */
public enum MessageType {

    // Strongly bound to factory bean names in DataProcessorContext.xml, DO NOT CHANGE
    BSM("bsm"),
    FHWA_SPAT("fhwaSpat"),
    MAP("map"),
    SAE_SPAT("saeSpat");

    final String type;

    MessageType(String s) {
        type = s;
    }

    public String toString() {
        return type;
    }
}
