
package com.pace.data;

public class MsgUnWrap {
    private byte[] target;
    private MsgHeader header;
    private int mReadCnt = 0;
    private boolean bReadSuc = false;

    public MsgUnWrap(MsgHeader header, byte[] recv) {
        this.header = header;
        target = new byte[header.getContentLength()];
        int headerLen = header.getHeaderLength();
        int leftlength = recv.length - headerLen;
        System.arraycopy(recv, headerLen, target, 0, leftlength);
        mReadCnt += leftlength;
        bReadSuc = true;
    }

    public boolean add(byte[] meta) {
        if (!bReadSuc || mReadCnt > header.getContentLength()) {
            return false;
        }
        // TODO 是否会乱序，完整性check
        int avalibleLen = meta.length - 2;
        System.arraycopy(meta, 2, target, mReadCnt, avalibleLen);
        mReadCnt += avalibleLen;
        return true;
    }

    public byte[] getTarget() {
        return target;
    }

    public boolean isComplete() {
        return mReadCnt == header.getContentLength() && bReadSuc;
    }
}
