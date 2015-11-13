package com.leidos.dataparser.data.spat;

import com.leidos.dataparser.data.pcap.PCAPPacket;
import com.leidos.dataparser.pipeline.GenericStageResult;
import com.leidos.dataparser.pipeline.Stage;
import com.leidos.dataparser.pipeline.StageException;
import com.leidos.dataparser.pipeline.StageResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Stage to convert a list of PCAP packets containing SPaT messages into a parsed list of SpatMessage
 * objects.
 */
public class SPATParser implements Stage<List<PCAPPacket>, List<SpatMessage>> {

    Logger log = LogManager.getLogger();

    public static final int SPAT_ID = 0x8D;
    private static final int LEADING_BYTES = 256;

    /**
     * Wrapper class around byte array to enable null checking for empty/invalid SPAT packets
     */
    private class SPATBuffer {
        private byte[] buf;

        public SPATBuffer(byte[] buf) {
            this.buf = buf;
        }

        public byte[] getBuffer() {
            return buf;
        }
    }

    @Override
    public StageResult<List<SpatMessage>> process(StageResult<List<PCAPPacket>> input) throws StageException {
        log.trace("Entering FHWA SPaT Parse Step.");

        log.debug("Processing " + input.get().size() + " PCAPPackets for FHWA SPaT messages.");

        return new GenericStageResult<>(input.get().stream()
            .map(pcapPacket -> {
                try {
                    SpatMessage spat = new SpatMessage();
                    SPATBuffer buf = findSPAT(pcapPacket.getData());

                    if (buf == null) {
                        return null;
                    }

                    return (spat.parse(buf.getBuffer()) ? spat : null);
                } catch (Exception e) {
                    log.warn("Exception " + e.toString() + " caught parsing SPaT. Skipping message...");
                    return null;
                }
            })
            .filter(spatMessage -> spatMessage != null)
            .collect(Collectors.toList()));
    }

    private SPATBuffer findSPAT(byte[] buf) {
        for (int start = 0; start < Math.min(buf.length, LEADING_BYTES);  ++start) {

            //if it is the MAP or SPAT message identifier then
            if ((buf[start] & 0x000000ff) == SPAT_ID) {

                //if it does look like we have a whole, valid message here then indicate success and break out of the loop
                if (validateMessage(buf, start)) {
                    return new SPATBuffer(Arrays.copyOfRange(buf, start, buf.length));
                }
            }
        }

        return null; // We didn't find anything looking like a valid MAP message.
    }

    /**
     * buf contains 4 initial bytes, a payload, and an end byte (and optionally a valid CRC word) that indicate a valid message : true
     * buf is too short, or the indicated bytes don't match expectations of a valid message : false
     *
     * @param buf: buffer to be validated
     * @param byte0: first byte in the buffer to start validating (ignore anything before this byte)
     */
    private boolean validateMessage(byte[] buf, int byte0) {

        boolean valid = false;

        //if at least 2 of the first 4 bytes have a non-zero value then
        int nonZero = 0;
        for (int i = byte0;  i < byte0 + 4;  ++i) {
            if (buf[i] != 0) {
                ++nonZero;
            }
        }
        if (nonZero >= 2) {

            //store the content version
            int contentVersion_ = buf[byte0 + 1] & 0x000000ff;

            //determine the message length from bytes 2 & 3
            int payloadLength_ = ((buf[byte0+2] << 8) & 0x0000ff00) | (buf[byte0+3] & 0x000000ff);

            //if the buffer has enough content to hold the indicated message length then
            if (buf.length >= byte0 + payloadLength_ + 4) {

                //if the indicated last byte of the message is a proper ending byte then
                if ((buf[byte0 + payloadLength_ + 3] & 0x000000ff) == 0xff) {

                    //indicate that we have a valid message
                    valid = true;
                }
            }
        }

        return valid;
    }
}
