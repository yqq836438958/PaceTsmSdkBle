
package com.pace.tsm.action;

import com.google.protobuf.InvalidProtocolBufferException;
import com.pace.data.Result;
import com.pace.tsm.TsmData.CloseChannelReq;
import com.pace.tsm.TsmData.CloseChannelRsp;

public class CloseChannel extends TsmAction {

    @Override
    protected byte[] onEncodeReqDat(byte[] src) {
        CloseChannelReq.Builder builder = CloseChannelReq.newBuilder();
        builder.setBaseReq(getCommonReq());
        return builder.build().toByteArray();
    }

    @Override
    protected Result onDecodeRspDat(byte[] rsp) {
        try {
            CloseChannelRsp closeChannelRsp = CloseChannelRsp.parseFrom(rsp);
            int iRet = closeChannelRsp.getBaseRsp().getEmRetCode();
            return Result.newObj(iRet);
        } catch (InvalidProtocolBufferException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
}
