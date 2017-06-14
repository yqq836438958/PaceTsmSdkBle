
package com.pace.data;

public class MsgMeta {
    public static final int MAX_LENGTH = 19;
    private MsgHeader mHeader = null;
    private byte[] mBsContent = null;

    public MsgMeta(byte[] data, boolean isFirstPack) {
        mBsContent = new byte[data.length + 1];
        byte flag = (byte) (isFirstPack ? 0x01 : 0x00);
        mBsContent[0] = flag;
        System.arraycopy(data, 0, mBsContent, 1, data.length);
    }

    public byte[] getData() {
        return mBsContent;
    }

    public int getLength() {
        return mBsContent.length;
    }
}
