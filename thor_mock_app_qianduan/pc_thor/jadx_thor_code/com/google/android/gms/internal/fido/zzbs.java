package com.google.android.gms.internal.fido;

import com.google.firebase.analytics.FirebaseAnalytics;

/* compiled from: com.google.android.gms:play-services-fido@@20.1.0 */
/* loaded from: classes2.dex */
final class zzbs extends zzaz {
    static final zzaz zza = new zzbs(new Object[0], 0);
    final transient Object[] zzb;
    private final transient int zzc;

    zzbs(Object[] objArr, int i) {
        this.zzb = objArr;
        this.zzc = i;
    }

    @Override // java.util.List
    public final Object get(int i) {
        zzap.zza(i, this.zzc, FirebaseAnalytics.Param.INDEX);
        Object obj = this.zzb[i];
        obj.getClass();
        return obj;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final int size() {
        return this.zzc;
    }

    @Override // com.google.android.gms.internal.fido.zzaz, com.google.android.gms.internal.fido.zzav
    final int zza(Object[] objArr, int i) {
        System.arraycopy(this.zzb, 0, objArr, 0, this.zzc);
        return this.zzc;
    }

    @Override // com.google.android.gms.internal.fido.zzav
    final int zzb() {
        return this.zzc;
    }

    @Override // com.google.android.gms.internal.fido.zzav
    final int zzc() {
        return 0;
    }

    @Override // com.google.android.gms.internal.fido.zzav
    final Object[] zze() {
        return this.zzb;
    }
}
