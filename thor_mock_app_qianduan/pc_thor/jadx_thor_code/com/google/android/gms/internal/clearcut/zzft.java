package com.google.android.gms.internal.clearcut;

import java.io.IOException;

/* loaded from: classes2.dex */
public final class zzft extends IOException {
    zzft(int i, int i2) {
        super(new StringBuilder(108).append("CodedOutputStream was writing to a flat byte array and ran out of space (pos ").append(i).append(" limit ").append(i2).append(").").toString());
    }
}
