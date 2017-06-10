
package com.pace.tsm.action;

import com.pace.ble.BleTransmit;
import com.pace.data.Result;
import com.pace.tsm.TsmData.BaseReq;

public abstract class TsmAction {

    public Result invoke(byte[] data) {
        byte[] wrapData = onEncodeReqDat(data);
        byte[] rsp = new BleTransmit().transmit(wrapData);
        return onDecodeRspDat(rsp);
    }

    protected BaseReq.Builder getCommonReq() {
        BaseReq.Builder base = BaseReq.newBuilder();
        return base;
    }

    protected abstract byte[] onEncodeReqDat(byte[] src);

    protected abstract Result onDecodeRspDat(byte[] rsp);
}
