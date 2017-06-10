
package com.pace.data;

import java.util.List;

public class MsgWrap {
    private List<MsgMeta> mMsgMetas = null;

    public MsgWrap(byte[] input) {

    }

    public List<MsgMeta> getItems() {
        return mMsgMetas;
    }
}
