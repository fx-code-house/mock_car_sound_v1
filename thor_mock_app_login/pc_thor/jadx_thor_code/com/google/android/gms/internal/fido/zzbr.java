package com.google.android.gms.internal.fido;

import java.util.Comparator;

/* compiled from: com.google.android.gms:play-services-fido@@20.1.0 */
/* loaded from: classes2.dex */
public abstract class zzbr implements Comparator {
    protected zzbr() {
    }

    @Override // java.util.Comparator
    public abstract int compare(Object obj, Object obj2);

    public zzbr zza() {
        return new zzbw(this);
    }
}
