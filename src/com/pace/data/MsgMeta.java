
package com.pace.data;

public class MsgMeta {
    private MsgHeader mHeader = null;
    private byte[] mBsContent = null;

    // for write
    public MsgMeta(MsgHeader header, byte[] data) {
        mHeader = header;
        mBsContent = data;
    }

    // for read
    public MsgMeta(byte[] data) {

    }

    public final boolean isHeader() {
        return mHeader != null;
    }

    public byte[] getData() {
        return mBsContent;
    }
}
