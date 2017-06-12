
package com.pace.ble.session;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattService;
import android.content.Context;

import com.clj.fastble.BleManager;
import com.clj.fastble.bluetooth.BleGattCallback;
import com.clj.fastble.exception.BleException;
import com.pace.data.BleAccessPoint;

public class BleSessionManager {
    private static BleSessionManager sInstance = null;
    private IBleSession mBleSession = null;
    public static final long TIMEOUT_CONNECT = 20 * 1000;

    public static BleSessionManager getInstance() {
        if (sInstance == null) {
            synchronized (BleSessionManager.class) {
                if (sInstance == null) {
                    sInstance = new BleSessionManager();
                }
            }
        }
        return sInstance;
    }

    public IBleSession getSession() {
        return mBleSession;
    }

    private void connectDev(Context context, final BleAccessPoint point) {
        BleManager manager = BleManager.getInstance();
        manager.init(context);
        manager.connectDevice(point.getTargeMacAddr(), TIMEOUT_CONNECT, new BleGattCallback() {

            @Override
            public void onConnectSuccess(BluetoothGatt gatt, int status) {
                gatt.discoverServices();
            }

            @Override
            public void onServicesDiscovered(BluetoothGatt gatt, int status) {
                findTargeService(gatt, point);
            }

            @Override
            public void onConnectFailure(BleException exception) {
                // TODO Auto-generated method stub
                mBleSession = null;
            }

        });
    }

    private void findTargeService(BluetoothGatt gatt, BleAccessPoint point) {
        for (final BluetoothGattService service : gatt.getServices()) {
            if (service.getUuid().toString().equalsIgnoreCase(point.getServiceUUID())) {
                mBleSession = new BleSession(point.getServiceUUID(), point.getIndicateCharacter(),
                        point.getWriteCharacter());
                break;
            }
        }
    }

    public void creatSession(Context context, BleAccessPoint point) {
        connectDev(context, point);
    }

    public boolean hasValidSession() {
        return mBleSession != null;
    }
}
