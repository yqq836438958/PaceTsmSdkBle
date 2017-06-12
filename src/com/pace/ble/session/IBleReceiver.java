
package com.pace.ble.session;

import com.clj.fastble.exception.BleException;

public interface IBleReceiver {
    public void onSuccess(byte[] data);

    public void onFail(BleException exception);
}
