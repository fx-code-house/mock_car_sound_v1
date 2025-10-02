package com.google.android.gms.internal.vision;

import java.util.Iterator;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
/* loaded from: classes2.dex */
final class zzlb implements Iterator<String> {
    private final /* synthetic */ zzkz zzacj;
    private Iterator<String> zzadf;

    zzlb(zzkz zzkzVar) {
        this.zzacj = zzkzVar;
        this.zzadf = zzkzVar.zzack.iterator();
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.zzadf.hasNext();
    }

    @Override // java.util.Iterator
    public final void remove() {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Iterator
    public final /* synthetic */ String next() {
        return this.zzadf.next();
    }
}
