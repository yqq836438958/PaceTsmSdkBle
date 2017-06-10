
package com.pace.ble;

import android.bluetooth.BluetoothGattCharacteristic;

import com.clj.fastble.BleManager;
import com.clj.fastble.conn.BleCharacterCallback;
import com.clj.fastble.exception.BleException;
import com.pace.data.MsgMeta;
import com.pace.data.MsgWrap;

public class BleWirter {
    private BleManager mBleManager = null;
    private BleAccessPoint mAccessPoint = null;
    private MsgWrap mMsgWrap = null;
    private BleCharacterCallback mWriteCallback = new BleCharacterCallback() {

        @Override
        public void onSuccess(BluetoothGattCharacteristic characteristic) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onFailure(BleException exception) {
            // TODO Auto-generated method stub

        }

    };

    public BleWirter(BleManager manager, BleAccessPoint point, byte[] data) {
        mBleManager = manager;
        mAccessPoint = point;
        mMsgWrap = new MsgWrap(data);
    }

    public boolean invoke() {
        boolean writeSuc = false;
        for (MsgMeta item : mMsgWrap.getItems()) {
            writeSuc = writeDataInternal(item.getData());
            if (!writeSuc) {
                break;
            }
        }
        return writeSuc;
    }

    private boolean writeDataInternal(byte[] realData) {
        return mBleManager.writeDevice(mAccessPoint.getUuidService(),
                mAccessPoint.getUuidCharacter(),
                realData, mWriteCallback);
    }
}
