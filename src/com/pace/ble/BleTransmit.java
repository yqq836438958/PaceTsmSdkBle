
package com.pace.ble;

import android.os.Looper;
import android.util.Log;

import com.pace.ble.session.IBleSession;
import com.pace.data.Result;

public class BleTransmit {
    public static interface IInvokeCallBack {
        public void onCallBack(int ret, byte[] data);
    }

    public static final String TAG = "BLE";
    private final long MS_BLE_INVOKE = 10 * 1000;
    private static Object sLockObj = new Object();
    private IBleSession mBleSession = null;

    public BleTransmit(IBleSession session) {
        mBleSession = session;
    }

    public byte[] transmit(byte[] content) {
        if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
            throw new IllegalThreadStateException("can not call in mainUI");
        }
        final Result result = new Result();
        IInvokeCallBack callBack = new IInvokeCallBack() {

            @Override
            public void onCallBack(int ret, byte[] data) {
                synchronized (sLockObj) {
                    sLockObj.notifyAll();
                }
                result.setCode(ret);
                result.setData(data);
            }
        };
        new BleReader(mBleSession).addCallback(callBack);
        if (!new BleWirter(mBleSession).invoke(content)) {
            Log.e(TAG, "write fail");
            return null;
        }

        synchronized (sLockObj) {
            try {
                sLockObj.wait(MS_BLE_INVOKE);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return result.getData();
    }

}
