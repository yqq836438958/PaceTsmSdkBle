
package com.pace.ble.session;

import android.bluetooth.BluetoothGattCharacteristic;

import com.clj.fastble.BleManager;
import com.clj.fastble.conn.BleCharacterCallback;
import com.clj.fastble.exception.BleException;

public class BleSession implements IBleSession {
    private BleManager mBleManager = null;
    private boolean mIsSessionReady = false;
    private IBleReceiver mBleReceiver = null;
    private String mServiceUuid = null;
    private String mIndicateCharacter = null;
    private String mWriteCharacter = null;

    public BleSession(String serviceUuid, String indicateCharacter, String writeCharacter) {
        mServiceUuid = serviceUuid;
        mIndicateCharacter = indicateCharacter;
        mWriteCharacter = writeCharacter;
        mBleManager = BleManager.getInstance();
    }

    @Override
    public void registReceiver(IBleReceiver receiver) {
        mBleReceiver = receiver;
    }

    @Override
    public boolean isReady() {
        return mIsSessionReady;
    }

    @Override
    public boolean create() {
        boolean indicateReady = mBleManager.indicateDevice(mServiceUuid, mIndicateCharacter,
                new BleCharacterCallback() {

                    @Override
                    public void onSuccess(BluetoothGattCharacteristic characteristic) {
                        if (mBleReceiver != null) {
                            mBleReceiver.onSuccess(characteristic.getValue());
                        }
                    }

                    @Override
                    public void onFailure(BleException exception) {
                        if (mBleReceiver != null) {
                            mBleReceiver.onFail(exception);
                        }
                    }

                });
        mIsSessionReady = indicateReady;
        return indicateReady;
    }

    @Override
    public boolean write(byte[] data, BleCharacterCallback callback) {
        return mBleManager.writeDevice(mServiceUuid, mWriteCharacter, data, callback);
    }
}
