package com.google.android.gms.internal.vision;

import java.util.NoSuchElementException;

/* JADX INFO: Add missing generic type declarations: [T] */
/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
/* loaded from: classes2.dex */
final class zzeo<T> extends zzfa<T> {
    private boolean zzni;
    private final /* synthetic */ Object zznj;

    zzeo(Object obj) {
        this.zznj = obj;
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return !this.zzni;
    }

    @Override // java.util.Iterator
    public final T next() {
        if (this.zzni) {
            throw new NoSuchElementException();
        }
        this.zzni = true;
        return (T) this.zznj;
    }
}
