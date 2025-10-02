package com.example.myapplication;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class ThorCRC16 {
    private int[] crcTable;
    
    public ThorCRC16() {
        this.crcTable = generateCrcTable();
    }
    
    private int[] generateCrcTable() {
        int[] table = new int[256];
        for (int i = 0; i < 256; i++) {
            int crc = i;
            for (int j = 0; j < 8; j++) {
                if ((crc & 1) != 0) {
                    crc = (crc >> 1) ^ 0xA001;
                } else {
                    crc >>= 1;
                }
            }
            table[i] = crc & 0xFFFF;
        }
        return table;
    }
    
    public int calculate(byte[] data) {
        int crc = 0xFFFF;
        for (byte b : data) {
            int tblIdx = (crc ^ (b & 0xFF)) & 0xFF;
            crc = ((crc >> 8) ^ crcTable[tblIdx]) & 0xFFFF;
        }
        return crc & 0xFFFF;
    }
    
    public byte[] createChecksumPart(int crcValue) {
        return ByteBuffer.allocate(2).order(ByteOrder.LITTLE_ENDIAN).putShort((short)crcValue).array();
    }
}