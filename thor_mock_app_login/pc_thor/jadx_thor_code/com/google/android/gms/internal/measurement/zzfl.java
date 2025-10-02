package com.google.android.gms.internal.measurement;

import java.util.NoSuchElementException;

/* JADX INFO: Add missing generic type declarations: [T] */
/* compiled from: com.google.android.gms:play-services-measurement-impl@@18.0.0 */
/* loaded from: classes2.dex */
final class zzfl<T> extends zzfx<T> {
    private boolean zza;
    private final /* synthetic */ Object zzb;

    zzfl(Object obj) {
        this.zzb = obj;
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return !this.zza;
    }

    @Override // java.util.Iterator
    public final T next() {
        if (this.zza) {
            throw new NoSuchElementException();
        }
        this.zza = true;
        return (T) this.zzb;
    }
}
