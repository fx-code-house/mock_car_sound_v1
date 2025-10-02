package com.google.android.gms.internal.fido;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.List;
import javax.annotation.CheckForNull;

/* compiled from: com.google.android.gms:play-services-fido@@20.1.0 */
/* loaded from: classes2.dex */
final class zzax extends zzaz {
    private final transient zzaz zza;

    zzax(zzaz zzazVar) {
        this.zza = zzazVar;
    }

    private final int zzl(int i) {
        return (this.zza.size() - 1) - i;
    }

    @Override // com.google.android.gms.internal.fido.zzaz, com.google.android.gms.internal.fido.zzav, java.util.AbstractCollection, java.util.Collection
    public final boolean contains(@CheckForNull Object obj) {
        return this.zza.contains(obj);
    }

    @Override // java.util.List
    public final Object get(int i) {
        zzap.zza(i, this.zza.size(), FirebaseAnalytics.Param.INDEX);
        return this.zza.get(zzl(i));
    }

    @Override // com.google.android.gms.internal.fido.zzaz, java.util.List
    public final int indexOf(@CheckForNull Object obj) {
        int iLastIndexOf = this.zza.lastIndexOf(obj);
        if (iLastIndexOf >= 0) {
            return zzl(iLastIndexOf);
        }
        return -1;
    }

    @Override // com.google.android.gms.internal.fido.zzaz, java.util.List
    public final int lastIndexOf(@CheckForNull Object obj) {
        int iIndexOf = this.zza.indexOf(obj);
        if (iIndexOf >= 0) {
            return zzl(iIndexOf);
        }
        return -1;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final int size() {
        return this.zza.size();
    }

    @Override // com.google.android.gms.internal.fido.zzaz, java.util.List
    public final /* bridge */ /* synthetic */ List subList(int i, int i2) {
        return subList(i, i2);
    }

    @Override // com.google.android.gms.internal.fido.zzaz
    public final zzaz zzf() {
        return this.zza;
    }

    @Override // com.google.android.gms.internal.fido.zzaz
    /* renamed from: zzg */
    public final zzaz subList(int i, int i2) {
        zzap.zze(i, i2, this.zza.size());
        zzaz zzazVar = this.zza;
        return zzazVar.subList(zzazVar.size() - i2, this.zza.size() - i).zzf();
    }
}
