
package com.pace.config;

import com.pace.data.TsmMsgHeader;
import com.pace.data.MsgHeader;

public class RunEnv {
    public static interface IRunType {
        public MsgHeader genHeader(byte[] data);
    }

    public static IRunType getType() {
        return new TsmType();
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
