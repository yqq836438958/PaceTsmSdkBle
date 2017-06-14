
package com.pace.tsm.action;

import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import com.pace.data.MsgWrap;
import com.pace.data.Result;
import com.pace.tsm.TsmData.ApduTransmitReq;
import com.pace.tsm.TsmData.ApduTransmitRsp;

public class ApduTransmit extends TsmAction {
    public ApduTransmit() {
    }

    @Override
    protected byte[] onEncodeReqDat(byte[] src) {
        ApduTransmitReq.Builder builder = ApduTransmitReq.newBuilder();
        builder.setBaseReq(getCommonReq());
        builder.setApduReq(ByteString.copyFrom(src));
        return builder.build().toByteArray();
    }

    @Override
    protected Result onDecodeRspDat(byte[] _rsp) {
        try {
            ApduTransmitRsp rsp = ApduTransmitRsp.parseFrom(_rsp);
            int iRet = rsp.getBaseRsp().getEmRetCode();
            return Result.newObj(iRet, rsp.getApduRsp().toByteArray());
        } catch (InvalidProtocolBufferException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected MsgWrap onWrapData(byte[] data) {
        // TODO Auto-generated method stub
        return null;
    }

}
