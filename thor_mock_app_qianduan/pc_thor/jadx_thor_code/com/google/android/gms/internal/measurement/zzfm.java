package com.google.android.gms.internal.measurement;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@18.0.0 */
/* loaded from: classes2.dex */
final class zzfm<E> extends zzfb<E> {
    static final zzfb<Object> zza = new zzfm(new Object[0], 0);
    private final transient Object[] zzb;
    private final transient int zzc;

    zzfm(Object[] objArr, int i) {
        this.zzb = objArr;
        this.zzc = i;
    }

    @Override // com.google.android.gms.internal.measurement.zzey
    final int zzc() {
        return 0;
    }

    @Override // com.google.android.gms.internal.measurement.zzey
    final boolean zzf() {
        return false;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final int size() {
        return this.zzc;
    }

    @Override // com.google.android.gms.internal.measurement.zzey
    final Object[] zzb() {
        return this.zzb;
    }

    @Override // com.google.android.gms.internal.measurement.zzey
    final int zzd() {
        return this.zzc;
    }

    @Override // com.google.android.gms.internal.measurement.zzfb, com.google.android.gms.internal.measurement.zzey
    final int zza(Object[] objArr, int i) {
        System.arraycopy(this.zzb, 0, objArr, i, this.zzc);
        return i + this.zzc;
    }

    @Override // java.util.List
    public final E get(int i) {
        zzeb.zza(i, this.zzc);
        return (E) this.zzb[i];
    }
}
