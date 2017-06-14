
package com.pace.data;

import com.pace.config.RunEnv.IRunType;

public abstract class MsgHeader {
    protected int mHeaderLength = -1;
    protected int mContentLength = -1;
    protected byte[] bsSource = null;
    protected byte[] bsHeader = null;

    public MsgHeader(byte[] source) {
        bsSource = source;
    }

    public static MsgHeader findHeader(IRunType type, byte[] data) {
        MsgHeader header = type.genHeader(data);
        if (header.isValid()) {
            header.onDecode();
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

    public final void updateContentLength(int len) {
        mContentLength = len;
    }

    public byte[] getHeaderContent() {
        return bsHeader;
    }

    protected abstract boolean isValid();

    protected abstract void onDecode();
}
