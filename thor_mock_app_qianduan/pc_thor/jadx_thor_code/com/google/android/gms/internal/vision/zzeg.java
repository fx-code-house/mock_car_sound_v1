package com.google.android.gms.internal.vision;

import java.util.List;

/* JADX INFO: Add missing generic type declarations: [E] */
/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
/* loaded from: classes2.dex */
final class zzeg<E> extends zzee<E> {
    private final transient int length;
    private final transient int offset;
    private final /* synthetic */ zzee zznb;

    zzeg(zzee zzeeVar, int i, int i2) {
        this.zznb = zzeeVar;
        this.offset = i;
        this.length = i2;
    }

    @Override // com.google.android.gms.internal.vision.zzeb
    final boolean zzcu() {
        return true;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final int size() {
        return this.length;
    }

    @Override // com.google.android.gms.internal.vision.zzeb
    final Object[] zzcq() {
        return this.zznb.zzcq();
    }

    @Override // com.google.android.gms.internal.vision.zzeb
    final int zzcr() {
        return this.zznb.zzcr() + this.offset;
    }

    @Override // com.google.android.gms.internal.vision.zzeb
    final int zzcs() {
        return this.zznb.zzcr() + this.offset + this.length;
    }

    @Override // java.util.List
    public final E get(int i) {
        zzde.zzd(i, this.length);
        return this.zznb.get(i + this.offset);
    }

    @Override // com.google.android.gms.internal.vision.zzee
    /* renamed from: zzh */
    public final zzee<E> subList(int i, int i2) {
        zzde.zza(i, i2, this.length);
        zzee zzeeVar = this.zznb;
        int i3 = this.offset;
        return (zzee) zzeeVar.subList(i + i3, i2 + i3);
    }

    @Override // com.google.android.gms.internal.vision.zzee, java.util.List
    public final /* synthetic */ List subList(int i, int i2) {
        return subList(i, i2);
    }
}
