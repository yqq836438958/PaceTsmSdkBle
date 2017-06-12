
package com.pace.ble;

import com.clj.fastble.exception.BleException;
import com.pace.ble.BleTransmit.IInvokeCallBack;
import com.pace.ble.session.IBleReceiver;
import com.pace.ble.session.IBleSession;
import com.pace.config.RunEnv;
import com.pace.config.RunEnv.IRunType;
import com.pace.data.MsgHeader;
import com.pace.data.MsgUnWrap;

import java.util.List;

public class BleReader implements IBleReceiver {
    private MsgUnWrap mMsgRecv = null;
    private List<IInvokeCallBack> mCallbackList = null;

    public BleReader(IBleSession session) {
        session.registReceiver(this);
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

    private void onHandleSucEvent(byte[] recv) {
        IRunType type = RunEnv.getType();
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

    @Override
    public void onSuccess(byte[] data) {
        onHandleSucEvent(data);
    }

    @Override
    public void onFail(BleException exception) {
        postCallback(exception.getCode(), null);
    }

}
