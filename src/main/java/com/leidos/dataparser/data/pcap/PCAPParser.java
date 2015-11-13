package com.leidos.dataparser.data.pcap;

import com.leidos.dataparser.pipeline.GenericStageResult;
import com.leidos.dataparser.pipeline.Stage;
import com.leidos.dataparser.pipeline.StageException;
import com.leidos.dataparser.pipeline.StageResult;
import com.leidos.dataparser.util.BitStreamUnpacker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Stage one of a PCAP file based parser.
 *
 * Decodes a buffer containing a PCAP encoded packet dump into a list of packet objects that associate packet payloads
 * and their metadata.
 */
public class PCAPParser implements Stage<byte[], List<PCAPPacket>> {
    private static final int MAGIC_NUMBER_BE = 0xa1b2c3d4;
    private static final int MAGIC_NUMBER_LE = 0xd4c3b2a1;

    Logger log = LogManager.getLogger();

    public StageResult<List<PCAPPacket>> process(StageResult<byte[]> input) throws StageException {
        log.trace("Entering PCAP Parse Step.");

        log.debug("Processing " + input.get().length + " bytes of data for PCAP packets.");

        BitStreamUnpacker u = new BitStreamUnpacker(input.get());

        // Determine the endianness of the file and set the BitStreamUnpacker to match
        long magicNumber = u.readInt();
        if (magicNumber == MAGIC_NUMBER_LE) {
            u.setLittleEndian();
            log.debug("Little endian PCAP format detected.");
        } else {
            log.debug("Big endian PCAP format detected.");
        }

        // Read the rest of the packet header
        long versionMajor = u.readShort();
        long versionMinor = u.readShort();
        long thisZone = u.readInt();
        long sigfigs = u.readInt();
        long snaplen = u.readInt();
        long network = u.readInt();

        List<PCAPPacket> packets = new ArrayList<PCAPPacket>();
        while (u.hasBits()) {
            try {
                packets.add(parsePacket(u));
            } catch (ParseException pe) {
                log.warn("Encountered corrupt packet after " + packets.size() + " packets. Stopping parse.");
                break;
            }
        }

        return new GenericStageResult<>(packets);
    }

    /**
     * Generates a PCAPPacket object out of a location in a BitStreamUnpacker
     * @param u A BitStreamUnpacker who's current location is at the start of a packet in PCAP format
     * @return A PCAPPacket object with that data
     */
    private PCAPPacket parsePacket(BitStreamUnpacker u) throws ParseException {
        try {
            long ts_sec = u.readInt();
            long ts_usec = u.readInt();

            // Validate packet size
            long incl_len = u.readInt();
            if (incl_len > Integer.MAX_VALUE || incl_len < 0) {
                throw new ParseException("Invalid length of packet.", 0);
            }

            long orig_len = u.readInt();
            byte[] data = u.readByteArray((int) incl_len);

            return new PCAPPacket(ts_sec, ts_usec, incl_len, orig_len, data);
        } catch (Exception e) {
            throw new ParseException("Error parsing packet" + e.getMessage(), 0);
        }
    }
}
