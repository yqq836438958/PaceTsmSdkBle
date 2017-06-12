
package com.pace.ble.session;

import com.clj.fastble.conn.BleCharacterCallback;
import com.pace.ble.BleReader;

public interface IBleSession {
    public boolean create();

    public void registReceiver(IBleReceiver receiver);

    public boolean isReady();

    public boolean write(byte[] data, BleCharacterCallback callback);

}
