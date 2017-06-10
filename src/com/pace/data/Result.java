
package com.pace.data;

public class Result {
    private int iRet;
    private byte[] bsDat;

    public static Result newObj(int iRet) {
        return newObj(iRet, null);
    }

    public static Result newObj(int iRet, byte[] data) {
        Result result = new Result();
        result.iRet = iRet;
        result.bsDat = data;
        return result;
    }

    public int getCode() {
        return iRet;
    }

    public byte[] getData() {
        return bsDat;
    }

    public void setCode(int code) {
        iRet = code;
    }

    public void setData(byte[] data) {
        bsDat = data;
    }
}
