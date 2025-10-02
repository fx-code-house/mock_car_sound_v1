package com.google.android.gms.internal.clearcut;

/* loaded from: classes2.dex */
final class zzfi extends IllegalArgumentException {
    zzfi(int i, int i2) {
        super(new StringBuilder(54).append("Unpaired surrogate at index ").append(i).append(" of ").append(i2).toString());
    }
}
