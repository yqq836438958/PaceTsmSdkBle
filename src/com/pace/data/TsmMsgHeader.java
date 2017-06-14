
package com.pace.data;

public class TsmMsgHeader extends MsgHeader {
    public static final int TSM_HEADER_LENGTH = 6;

    // [0] 标识
    // [1] version
    // [2] total Length: contentLen + headerLen
    // [3] cmd type
    // [4] extra_info
    public TsmMsgHeader(byte[] data) {
        super(data);
    }

    @Override
    protected boolean isValid() {
        boolean valid = (bsSource[0] == 0x01);
        return valid;
    }

    @Override
    protected void onDecode() {
        System.arraycopy(bsSource, 0, bsHeader, 0, TSM_HEADER_LENGTH);
        mHeaderLength = TSM_HEADER_LENGTH;
        mContentLength = bsSource[2] - mHeaderLength;
    }

}
