package com.thor.businessmodule.crypto;

public final class CryptoManager {
    
    static {
        System.loadLibrary("bridge");
    }
    
    public final native byte[] coreAesEncrypt(byte[] data);
    
    public final native void coreAesInit(byte[] iv, int hardware_version, int firmware_version, int serial_number);
}