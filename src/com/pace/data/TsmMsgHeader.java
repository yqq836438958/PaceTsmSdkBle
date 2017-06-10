
package com.pace.data;

public class TsmMsgHeader extends MsgHeader {

    public TsmMsgHeader(byte[] data) {
        super(data);
    }

    @Override
    protected boolean isValid() {
        boolean valid = (bsSource[0] == 0x01);
        return valid;
    }

}
