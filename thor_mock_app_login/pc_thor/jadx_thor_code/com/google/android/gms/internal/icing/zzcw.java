package com.google.android.gms.internal.icing;

import java.util.NoSuchElementException;

/* compiled from: com.google.firebase:firebase-appindexing@@19.1.0 */
/* loaded from: classes2.dex */
final class zzcw extends zzcy {
    private final int limit;
    private int position = 0;
    private final /* synthetic */ zzct zzgl;

    zzcw(zzct zzctVar) {
        this.zzgl = zzctVar;
        this.limit = zzctVar.size();
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.position < this.limit;
    }

    @Override // com.google.android.gms.internal.icing.zzdc
    public final byte nextByte() {
        int i = this.position;
        if (i >= this.limit) {
            throw new NoSuchElementException();
        }
        this.position = i + 1;
        return this.zzgl.zzl(i);
    }
}
