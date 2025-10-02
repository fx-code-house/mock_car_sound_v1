package com.google.android.gms.internal.vision;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
/* loaded from: classes2.dex */
final class zzed<E> extends zzdm<E> {
    private final zzee<E> zzmv;

    zzed(zzee<E> zzeeVar, int i) {
        super(zzeeVar.size(), i);
        this.zzmv = zzeeVar;
    }

    @Override // com.google.android.gms.internal.vision.zzdm
    protected final E get(int i) {
        return this.zzmv.get(i);
    }
}
