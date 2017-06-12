
package com.pace.config;

import com.pace.data.TsmMsgHeader;
import com.pace.data.MsgHeader;

public class RunEnv {
    public static IRunType sRunType = null;
    public static IRunType sTsmType = new TsmType();

    public static void setRunType(IRunType type) {
        sRunType = type;
    }

    public static interface IRunType {
        public MsgHeader genHeader(byte[] data);
    }

    public static IRunType getType() {
        return sRunType;
    }

    public static class TsmType implements IRunType {

        @Override
        public MsgHeader genHeader(byte[] data) {
            return new TsmMsgHeader(data);
        }

    }

    public static class RPCType implements IRunType {

        @Override
        public MsgHeader genHeader(byte[] data) {
            // TODO Auto-generated method stub
            return null;
        }

    }
}
