
package com.pace.tsm.action;

import com.pace.ble.BleTransmit;
import com.pace.ble.session.IBleSession;
import com.pace.config.RunEnv;
import com.pace.data.Result;
import com.pace.tsm.TsmData.BaseReq;

public abstract class TsmAction {

    public Result invoke(IBleSession session, byte[] data) {
        RunEnv.setRunType(RunEnv.sTsmType);
        byte[] wrapData = onEncodeReqDat(data);
        byte[] rsp = new BleTransmit(session).transmit(wrapData);
        return onDecodeRspDat(rsp);
    }

    protected BaseReq.Builder getCommonReq() {
        BaseReq.Builder base = BaseReq.newBuilder();
        return base;
    }

    protected abstract byte[] onEncodeReqDat(byte[] src);

    protected abstract Result onDecodeRspDat(byte[] rsp);
}
