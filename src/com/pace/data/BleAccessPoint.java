
package com.pace.data;

public class BleAccessPoint {
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
}
