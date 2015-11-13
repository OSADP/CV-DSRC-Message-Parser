package com.leidos.dataparser.data.map;

import com.leidos.dataparser.data.pcap.PCAPPacket;
import com.leidos.dataparser.pipeline.GenericStageResult;
import com.leidos.dataparser.pipeline.Stage;
import com.leidos.dataparser.pipeline.StageException;
import com.leidos.dataparser.pipeline.StageResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * MAP Parser Stage
 *
 * Converts a list of PCAP encoded packets into a list of parsed MAP messages. If a packet does not contain a valid MAP
 * message, it is dropped from the output.
 */
public class MapParser implements Stage<List<PCAPPacket>, List<MapMessage>> {

    Logger log = LogManager.getLogger();

    /**
     * Wrapper class around byte[] to allow for null values to represent invalid messages.
     */
    private class MAPBuffer {
        private byte[] buf;

        public MAPBuffer(byte[] buf) {
            this.buf = buf;
        }

        public byte[] getBuffer() {
            return buf;
        }
    }

    private static final int MAP_ID = 0x87;
    private static final int LEADING_BYTES = 256;

    @Override
    public StageResult<List<MapMessage>> process(StageResult<List<PCAPPacket>> input) throws StageException {
        log.trace("Entering MAP Parse Step.");

        List<MapMessage> out = new ArrayList<>();

        log.trace("Processing " + input.get().size() + " PCAPPackets for MAP messages.");

        input.get().stream().forEach(pcapPacket -> {
            try {
                MapMessage m = new MapMessage();
                MAPBuffer buf = findMAP(pcapPacket.getData());
                if (buf != null && m.parse(buf.getBuffer())) {
                    out.add(m);
                } else {
                    log.warn("MAP message failed validation, skipping...");
                }
            } catch (Exception e) {
                // Just iterate past it.
                log.warn("Exception " + e.toString() + " caught parsing MAP message. Skipping...");
            }
        });

        log.trace("Exiting MAP Parse Step.");

        return new GenericStageResult<>(out);
    }


    private MAPBuffer findMAP(byte[] buf) {
        for (int start = 0; start < Math.min(buf.length, LEADING_BYTES);  ++start) {

            //if it is the MAP or SPAT message identifier then
            if ((buf[start] & 0x000000ff) == MAP_ID) {

                //if it does look like we have a whole, valid message here then indicate success and break out of the loop
                if (validateMessage(buf, start)) {
                    return new MAPBuffer(Arrays.copyOfRange(buf, start, buf.length));
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
