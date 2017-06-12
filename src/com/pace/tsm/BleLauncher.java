
package com.pace.tsm;

import android.content.Context;

import com.pace.ble.ErrCode;
import com.pace.ble.session.BleSessionManager;
import com.pace.ble.session.IBleSession;
import com.pace.data.BleAccessPoint;
import com.pace.data.Result;
import com.pace.tsm.action.ApduTransmit;
import com.pace.tsm.action.CloseChannel;
import com.pace.tsm.action.OpenChannel;
import com.pace.tsm.action.TsmAction;

public class BleLauncher {
    public static void setup(Context context, BleAccessPoint point) {
        BleSessionManager.getInstance().creatSession(context, point);
    }

    private static IBleSession getBleSession() {
        return BleSessionManager.getInstance().getSession();
    }

    public static int openChannel() {
        if (!BleSessionManager.getInstance().hasValidSession()) {
            return ErrCode.SESSION_NOT_READY;
        }
        TsmAction openChannel = new OpenChannel();
        Result result = openChannel.invoke(getBleSession(), null);
        return result.getCode();
    }

    public static int closeChannel() {
        if (!BleSessionManager.getInstance().hasValidSession()) {
            return ErrCode.SESSION_NOT_READY;
        }
        TsmAction closeChannel = new CloseChannel();
        Result result = closeChannel.invoke(getBleSession(), null);
        return result.getCode();
    }

    public static byte[] transmit(byte[] apdu) {
        if (!BleSessionManager.getInstance().hasValidSession()) {
            return null;
        }
        TsmAction apduTransmit = new ApduTransmit();
        Result result = apduTransmit.invoke(getBleSession(), apdu);
        return result.getData();
    }
}
