package com.example.myapplication;

public class CryptoManager {
    
    static {
        System.loadLibrary("bridge");
    }
    
    public native byte[] coreAesEncrypt(byte[] data);
    
    public native void coreAesInit(byte[] iv, int hardware_version, int firmware_version, int serial_number);
}