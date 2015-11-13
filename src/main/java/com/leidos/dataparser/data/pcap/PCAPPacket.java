package com.leidos.dataparser.data.pcap;

import com.leidos.dataparser.io.formatting.OutputData;

import java.util.Arrays;

/**
 * Container for a parsed PCAP packet. Contains the metadata associated in the PCAP file with the data as well as a
 * buffer containing the associated data capture as well.
 */
public class PCAPPacket implements OutputData {
    private PCAPHeader header;
    private byte[] data;

    public PCAPPacket(long ts_sec, long ts_usec, long incl_len, long orig_len, byte[] data) {
        this.header = new PCAPHeader(ts_sec, ts_usec, incl_len, orig_len);
        this.data = data;
    }

    public long getTs_sec() {
        return header.getTs_sec();
    }

    public long getTs_usec() {
        return header.getTs_usec();
    }

    public long getIncl_len() {
        return header.getIncl_len();
    }

    public long getOrig_len() {
        return header.getOrig_len();
    }

    public PCAPHeader getHeader() {
        return header;
    }

    public byte[] getData() {
        return data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PCAPPacket that = (PCAPPacket) o;

        if (getHeader() != null ? !getHeader().equals(that.getHeader()) : that.getHeader() != null) return false;
        return Arrays.equals(getData(), that.getData());

    }

    @Override
    public int hashCode() {
        int result = getHeader() != null ? getHeader().hashCode() : 0;
        result = 31 * result + (getData() != null ? Arrays.hashCode(getData()) : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PCAPPacket{" +
                "header=" + header +
                ", data=" + Arrays.toString(data) +
                '}';
    }
}
