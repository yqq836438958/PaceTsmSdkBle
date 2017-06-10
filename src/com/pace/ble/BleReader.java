
package com.pace.ble;

import android.bluetooth.BluetoothGattCharacteristic;

import com.clj.fastble.BleManager;
import com.clj.fastble.conn.BleCharacterCallback;
import com.clj.fastble.exception.BleException;
import com.pace.ble.BleTransmit.IInvokeCallBack;
import com.pace.config.RunEnv;
import com.pace.config.RunEnv.IRunType;
import com.pace.data.MsgHeader;
import com.pace.data.MsgUnWrap;

import java.util.List;

public class BleReader {
    private MsgUnWrap mMsgRecv = null;
    private List<IInvokeCallBack> mCallbackList = null;

    public BleReader(BleManager manager, BleAccessPoint point) {
        manager.indicateDevice(point.getUuidService(), point.getUuidCharacter(),
                new BleCharacterCallback() {

                    @Override
                    public void onSuccess(BluetoothGattCharacteristic characteristic) {
                        onHandleSucEvent(characteristic);
                    }

                    @Override
                    public void onFailure(BleException exception) {
                        postCallback(exception.getCode(), null);
                    }
                });
    }

    public void addCallback(IInvokeCallBack callBack) {
        synchronized (BleReader.this) {
            mCallbackList.add(callBack);
        }
    }

    public void removeCallback(IInvokeCallBack callBack) {
        synchronized (BleReader.this) {
            mCallbackList.remove(callBack);
        }
    }

    private void postCallback(int ret, byte[] data) {
        if (mCallbackList == null || mCallbackList.size() <= 0) {
            return;
        }
        synchronized (BleReader.this) {
            for (IInvokeCallBack callBack : mCallbackList) {
                callBack.onCallBack(ret, data);
            }
        }
    }

    private void onHandleSucEvent(BluetoothGattCharacteristic characteristic) {
        IRunType type = RunEnv.getType();
        byte[] recv = characteristic.getValue();
        MsgHeader header = MsgHeader.findHeader(type, recv);
        boolean addSuc = true;
        if (header == null) {
            mMsgRecv = new MsgUnWrap(header, recv);
        } else {
            if (mMsgRecv == null) {
                postCallback(-1, null);
                return;
            }
            addSuc = mMsgRecv.add(recv);
        }
        if (!addSuc) {
            postCallback(-1, recv);
            return;
        }
        if (mMsgRecv.isComplete()) {
            postCallback(0, mMsgRecv.getTarget());
        }

    }

}
