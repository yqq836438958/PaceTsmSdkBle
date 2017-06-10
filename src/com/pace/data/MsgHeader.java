
package com.pace.data;

import com.pace.config.RunEnv.IRunType;

public abstract class MsgHeader {
    private int mHeaderLength = -1;
    private int mContentLength = -1;
    protected byte[] bsSource = null;
    private byte[] bsHeader = null;

    public MsgHeader(byte[] source) {
        bsSource = source;
    }

    public static MsgHeader findHeader(IRunType type, byte[] data) {
        MsgHeader header = type.genHeader(data);
        if (header.isValid()) {
            return header;
        }
        return null;
    }

    public final int getHeaderLength() {
        return mHeaderLength;
    }

    public final int getContentLength() {
        return mContentLength;
    }

    public byte[] getHeaderContent() {
        return bsHeader;
    }

    protected abstract boolean isValid();
}
