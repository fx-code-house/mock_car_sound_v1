package com.google.android.gms.internal.vision;

import java.util.NoSuchElementException;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
/* loaded from: classes2.dex */
final class zzgv extends zzgx {
    private final int limit;
    private int position = 0;
    private final /* synthetic */ zzgs zztw;

    zzgv(zzgs zzgsVar) {
        this.zztw = zzgsVar;
        this.limit = zzgsVar.size();
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.position < this.limit;
    }

    @Override // com.google.android.gms.internal.vision.zzhb
    public final byte nextByte() {
        int i = this.position;
        if (i >= this.limit) {
            throw new NoSuchElementException();
        }
        this.position = i + 1;
        return this.zztw.zzav(i);
    }
}
