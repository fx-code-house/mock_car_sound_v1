package com.google.android.gms.internal.fido;

import java.util.Iterator;
import javax.annotation.CheckForNull;

/* compiled from: com.google.android.gms:play-services-fido@@20.1.0 */
/* loaded from: classes2.dex */
final class zzbt extends zzbc {
    static final zzbt zza;
    private static final Object[] zzd;
    final transient Object[] zzb;
    final transient Object[] zzc;
    private final transient int zze;
    private final transient int zzf;
    private final transient int zzg;

    static {
        Object[] objArr = new Object[0];
        zzd = objArr;
        zza = new zzbt(objArr, 0, objArr, 0, 0);
    }

    zzbt(Object[] objArr, int i, Object[] objArr2, int i2, int i3) {
        this.zzb = objArr;
        this.zze = i;
        this.zzc = objArr2;
        this.zzf = i2;
        this.zzg = i3;
    }

    @Override // com.google.android.gms.internal.fido.zzav, java.util.AbstractCollection, java.util.Collection
    public final boolean contains(@CheckForNull Object obj) {
        Object[] objArr = this.zzc;
        if (obj == null || objArr.length == 0) {
            return false;
        }
        int iZza = zzau.zza(obj.hashCode());
        while (true) {
            int i = iZza & this.zzf;
            Object obj2 = objArr[i];
            if (obj2 == null) {
                return false;
            }
            if (obj2.equals(obj)) {
                return true;
            }
            iZza = i + 1;
        }
    }

    @Override // com.google.android.gms.internal.fido.zzbc, java.util.Collection, java.util.Set
    public final int hashCode() {
        return this.zze;
    }

    @Override // com.google.android.gms.internal.fido.zzbc, com.google.android.gms.internal.fido.zzav, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
    public final /* synthetic */ Iterator iterator() {
        return zzi().listIterator(0);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final int size() {
        return this.zzg;
    }

    @Override // com.google.android.gms.internal.fido.zzav
    final int zza(Object[] objArr, int i) {
        System.arraycopy(this.zzb, 0, objArr, 0, this.zzg);
        return this.zzg;
    }

    @Override // com.google.android.gms.internal.fido.zzav
    final int zzb() {
        return this.zzg;
    }

    @Override // com.google.android.gms.internal.fido.zzav
    final int zzc() {
        return 0;
    }

    @Override // com.google.android.gms.internal.fido.zzbc, com.google.android.gms.internal.fido.zzav
    /* renamed from: zzd */
    public final zzcb iterator() {
        return zzi().listIterator(0);
    }

    @Override // com.google.android.gms.internal.fido.zzav
    final Object[] zze() {
        return this.zzb;
    }

    @Override // com.google.android.gms.internal.fido.zzbc
    final boolean zzg() {
        return true;
    }

    @Override // com.google.android.gms.internal.fido.zzbc
    final zzaz zzj() {
        return zzaz.zzh(this.zzb, this.zzg);
    }
}
