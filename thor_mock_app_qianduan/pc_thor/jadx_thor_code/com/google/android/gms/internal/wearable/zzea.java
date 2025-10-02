package com.google.android.gms.internal.wearable;

import java.util.Iterator;

/* compiled from: com.google.android.gms:play-services-wearable@@17.1.0 */
/* loaded from: classes2.dex */
final class zzea implements Iterator<String> {
    final Iterator<String> zza;
    final /* synthetic */ zzeb zzb;

    zzea(zzeb zzebVar) {
        this.zzb = zzebVar;
        this.zza = zzebVar.zza.iterator();
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.zza.hasNext();
    }

    @Override // java.util.Iterator
    public final /* bridge */ /* synthetic */ String next() {
        return this.zza.next();
    }

    @Override // java.util.Iterator
    public final void remove() {
        throw new UnsupportedOperationException();
    }
}
