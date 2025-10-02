package com.google.android.gms.internal.vision;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
/* loaded from: classes2.dex */
final class zzep<E> extends zzee<E> {
    static final zzee<Object> zznk = new zzep(new Object[0], 0);
    private final transient int size;
    private final transient Object[] zznl;

    zzep(Object[] objArr, int i) {
        this.zznl = objArr;
        this.size = i;
    }

    @Override // com.google.android.gms.internal.vision.zzeb
    final int zzcr() {
        return 0;
    }

    @Override // com.google.android.gms.internal.vision.zzeb
    final boolean zzcu() {
        return false;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final int size() {
        return this.size;
    }

    @Override // com.google.android.gms.internal.vision.zzeb
    final Object[] zzcq() {
        return this.zznl;
    }

    @Override // com.google.android.gms.internal.vision.zzeb
    final int zzcs() {
        return this.size;
    }

    @Override // com.google.android.gms.internal.vision.zzee, com.google.android.gms.internal.vision.zzeb
    final int zza(Object[] objArr, int i) {
        System.arraycopy(this.zznl, 0, objArr, i, this.size);
        return i + this.size;
    }

    @Override // java.util.List
    public final E get(int i) {
        zzde.zzd(i, this.size);
        return (E) this.zznl[i];
    }
}
