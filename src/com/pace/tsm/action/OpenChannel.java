
package com.pace.tsm.action;

import com.google.protobuf.InvalidProtocolBufferException;
import com.pace.data.Result;
import com.pace.tsm.TsmData.OpenChannelReq;
import com.pace.tsm.TsmData.OpenChannelRsp;

public class OpenChannel extends TsmAction {

    @Override
    protected byte[] onEncodeReqDat(byte[] src) {
        OpenChannelReq.Builder builder = OpenChannelReq.newBuilder();
        builder.setBaseReq(getCommonReq());
        return builder.build().toByteArray();
    }

    @Override
    protected Result onDecodeRspDat(byte[] rsp) {
        try {
            OpenChannelRsp openChannelRsp = OpenChannelRsp.parseFrom(rsp);
            int iRet = openChannelRsp.getBaseRsp().getEmRetCode();
            return Result.newObj(iRet);
        } catch (InvalidProtocolBufferException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }
}
