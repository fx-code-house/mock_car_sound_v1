package com.example.myapplication;

/**
 * ThorBluetooth实例持有者
 * 用于在不同Activity之间共享ThorBluetooth实例
 */
public class ThorBluetoothHolder {
    private static ThorBluetooth instance;
    
    public static void setInstance(ThorBluetooth thorBluetooth) {
        instance = thorBluetooth;
    }
    
    public static ThorBluetooth getInstance() {
        return instance;
    }
    
    public static boolean hasInstance() {
        return instance != null;
    }
}