
package com.pace.data;

import android.os.Parcel;
import android.os.Parcelable;

public class BleAccessPoint implements Parcelable {
    private String serviceUUID;
    private String writeCharater;
    private String indicatCharater;
    private String targetMacAddr;

    public BleAccessPoint(String srcUUID, String writeChar, String indicateChar, String targetMac) {
        serviceUUID = srcUUID;
        writeCharater = writeChar;
        indicatCharater = indicateChar;
        targetMacAddr = targetMac;
    }

    public BleAccessPoint(Parcel source) {
        serviceUUID = source.readString();
        writeCharater = source.readString();
        indicatCharater = source.readString();
        targetMacAddr = source.readString();
    }

    public String getServiceUUID() {
        return serviceUUID;
    }

    public String getWriteCharacter() {
        return writeCharater;
    }

    public String getIndicateCharacter() {
        return indicatCharater;
    }

    public String getTargeMacAddr() {
        return targetMacAddr;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(serviceUUID);
        dest.writeString(writeCharater);
        dest.writeString(indicatCharater);
        dest.writeString(targetMacAddr);
    }

    public static final Creator<BleAccessPoint> CREATOR = new Creator<BleAccessPoint>() {

        @Override
        public BleAccessPoint createFromParcel(Parcel source) {
            return new BleAccessPoint(source);
        }

        @Override
        public BleAccessPoint[] newArray(int size) {
            return new BleAccessPoint[size];
        }
    };
}
