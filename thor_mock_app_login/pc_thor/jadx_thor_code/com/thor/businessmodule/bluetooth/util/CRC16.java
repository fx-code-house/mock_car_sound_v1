package com.thor.businessmodule.bluetooth.util;

/* loaded from: classes3.dex */
public class CRC16 {
    public int value = 65535;

    public void update(byte b) {
        int i = 7;
        int i2 = b;
        while (i >= 0) {
            int i3 = this.value;
            int i4 = i3 & 1;
            int i5 = i3 >> 1;
            this.value = i5;
            int i6 = i2 & 1;
            int i7 = i2 >> 1;
            if ((i4 ^ i6) == 1) {
                this.value = i5 ^ 40961;
            }
            i--;
            i2 = i7;
        }
    }

    public void reset() {
        this.value = 0;
    }
}
