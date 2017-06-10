
package com.pace.tsm;

import com.pace.data.Result;
import com.pace.tsm.action.ApduTransmit;
import com.pace.tsm.action.CloseChannel;
import com.pace.tsm.action.OpenChannel;
import com.pace.tsm.action.TsmAction;

public class BleLauncher {
    public static int openChannel() {
        TsmAction openChannel = new OpenChannel();
        Result result = openChannel.invoke(null);
        return result.getCode();
    }

    public static int closeChannel() {
        TsmAction closeChannel = new CloseChannel();
        Result result = closeChannel.invoke(null);
        return result.getCode();
    }

    public static byte[] transmit(byte[] apdu) {
        TsmAction apduTransmit = new ApduTransmit();
        Result result = apduTransmit.invoke(apdu);
        return result.getData();
    }
}
