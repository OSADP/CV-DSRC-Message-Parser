package com.leidos.dataparser.data.pcap;

import com.leidos.dataparser.io.formatting.Output;
import com.leidos.dataparser.io.formatting.OutputData;

/**
 * Header fields for PCAP file format, extracted to an object so that it can be
 * passed forward through the parsing process.
 */
public class PCAPHeader implements OutputData {
    @Output
    private long ts_sec;
    @Output
    private long ts_usec;
    @Output
    private long incl_len;
    @Output
    private long orig_len;

    public PCAPHeader(long ts_sec, long ts_usec, long incl_len, long orig_len) {
        this.ts_sec = ts_sec;
        this.ts_usec = ts_usec;
        this.incl_len = incl_len;
        this.orig_len = orig_len;
    }

    public long getTs_sec() {
        return ts_sec;
    }

    public long getTs_usec() {
        return ts_usec;
    }

    public long getIncl_len() {
        return incl_len;
    }

    public long getOrig_len() {
        return orig_len;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PCAPHeader that = (PCAPHeader) o;

        if (getTs_sec() != that.getTs_sec()) return false;
        if (getTs_usec() != that.getTs_usec()) return false;
        if (getIncl_len() != that.getIncl_len()) return false;
        return getOrig_len() == that.getOrig_len();

    }

    @Override
    public int hashCode() {
        int result = (int) (getTs_sec() ^ (getTs_sec() >>> 32));
        result = 31 * result + (int) (getTs_usec() ^ (getTs_usec() >>> 32));
        result = 31 * result + (int) (getIncl_len() ^ (getIncl_len() >>> 32));
        result = 31 * result + (int) (getOrig_len() ^ (getOrig_len() >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "PCAPHeader{" +
                "ts_sec=" + ts_sec +
                ", ts_usec=" + ts_usec +
                ", incl_len=" + incl_len +
                ", orig_len=" + orig_len +
                '}';
    }
}
