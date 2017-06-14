
package com.pace.data;

import java.util.ArrayList;
import java.util.List;

public class MsgWrap {
    private List<MsgMeta> mMsgMetas = null;

    public MsgWrap(MsgHeader header, byte[] content) {
        onWrap(header, content);
    }

    private void onWrap(MsgHeader header, byte[] content) {
        mMsgMetas = new ArrayList<MsgMeta>();
        byte[] tmp = null;
        int headerLen = header.getHeaderLength();
        int offset_content = 0;
        boolean isFirstWrite = true;
        int writeLen = 0;
        int targeLenth = 0;
        while (offset_content < content.length) {
            if (content.length - offset_content > MsgMeta.MAX_LENGTH) {
                writeLen += MsgMeta.MAX_LENGTH;
            } else {
                writeLen += content.length - offset_content;
            }
            tmp = new byte[writeLen];
            if (isFirstWrite) {
                System.arraycopy(header.getHeaderContent(), 0, tmp, 0, headerLen);
                isFirstWrite = false;
            }
            System.arraycopy(content, offset_content, tmp, isFirstWrite ? headerLen : 0, writeLen);
            offset_content += writeLen;
            MsgMeta meta = genMsgMeta(tmp, isFirstWrite);
            mMsgMetas.add(meta);
            targeLenth += meta.getLength();
            writeLen = 0;
        }
        header.updateContentLength(targeLenth);
    }

    public MsgMeta genMsgMeta(byte[] data, boolean isFirstPack) {
        MsgMeta meta = new MsgMeta(data, isFirstPack);
        return meta;
    }

    public List<MsgMeta> getItems() {
        return mMsgMetas;
    }
}
