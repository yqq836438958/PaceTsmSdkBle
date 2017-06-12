
package com.pace.ble;

import android.bluetooth.BluetoothGattCharacteristic;

import com.clj.fastble.conn.BleCharacterCallback;
import com.clj.fastble.exception.BleException;
import com.pace.ble.session.IBleSession;
import com.pace.data.MsgMeta;
import com.pace.data.MsgWrap;

import java.util.concurrent.Semaphore;

public class BleWirter {
    private IBleSession mBleSession = null;
    private MsgWrap mMsgWrap = null;
    private SemaphoreControl mSemaphoreControl = null;

    class SemaphoreControl {
        private Semaphore mSemaphore = null;
        private int iRet = -1;

        public SemaphoreControl() {
            mSemaphore = new Semaphore(0);
        }

        public int waitResult(long timeout) {
            try {
                mSemaphore.wait(timeout);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return iRet;
        }

        public void notify(int ret) {
            iRet = ret;
            mSemaphore.release();
        }
    }

    private BleCharacterCallback mWriteCallback = new BleCharacterCallback() {

        @Override
        public void onSuccess(BluetoothGattCharacteristic characteristic) {
            if (mSemaphoreControl != null) {
                mSemaphoreControl.notify(0);
            }
        }

        @Override
        public void onFailure(BleException exception) {
            if (mSemaphoreControl != null) {
                mSemaphoreControl.notify(exception.getCode());
            }
        }

    };

    public BleWirter(IBleSession session) {
        mBleSession = session;
    }

    public boolean invoke(byte[] data) {
        boolean writeSuc = false;
        mMsgWrap = new MsgWrap(data);
        mSemaphoreControl = new SemaphoreControl();
        for (MsgMeta item : mMsgWrap.getItems()) {
            writeSuc = writeDataInternal(item.getData()) && mSemaphoreControl.waitResult(500) == 0;
            if (!writeSuc) {
                break;
            }
        }
        return writeSuc;
    }

    private boolean writeDataInternal(byte[] realData) {
        return mBleSession.write(realData, mWriteCallback);
    }
}
