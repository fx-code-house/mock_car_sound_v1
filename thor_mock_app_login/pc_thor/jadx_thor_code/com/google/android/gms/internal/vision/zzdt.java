package com.google.android.gms.internal.vision;

import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Map;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* JADX INFO: Add missing generic type declarations: [V, K] */
/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
/* loaded from: classes2.dex */
final class zzdt<K, V> extends AbstractSet<Map.Entry<K, V>> {
    private final /* synthetic */ zzdp zzmo;

    zzdt(zzdp zzdpVar) {
        this.zzmo = zzdpVar;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final int size() {
        return this.zzmo.size();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final void clear() {
        this.zzmo.clear();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
    public final Iterator<Map.Entry<K, V>> iterator() {
        return this.zzmo.zzck();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final boolean contains(@NullableDecl Object obj) {
        Map<K, V> mapZzcf = this.zzmo.zzcf();
        if (mapZzcf != null) {
            return mapZzcf.entrySet().contains(obj);
        }
        if (obj instanceof Map.Entry) {
            Map.Entry entry = (Map.Entry) obj;
            int iIndexOf = this.zzmo.indexOf(entry.getKey());
            if (iIndexOf != -1 && zzcz.equal(this.zzmo.zzmj[iIndexOf], entry.getValue())) {
                return true;
            }
        }
        return false;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final boolean remove(@NullableDecl Object obj) {
        Map<K, V> mapZzcf = this.zzmo.zzcf();
        if (mapZzcf != null) {
            return mapZzcf.entrySet().remove(obj);
        }
        if (!(obj instanceof Map.Entry)) {
            return false;
        }
        Map.Entry entry = (Map.Entry) obj;
        if (this.zzmo.zzce()) {
            return false;
        }
        int iZzcg = this.zzmo.zzcg();
        int iZza = zzea.zza(entry.getKey(), entry.getValue(), iZzcg, this.zzmo.zzmg, this.zzmo.zzmh, this.zzmo.zzmi, this.zzmo.zzmj);
        if (iZza == -1) {
            return false;
        }
        this.zzmo.zzf(iZza, iZzcg);
        zzdp.zzd(this.zzmo);
        this.zzmo.zzch();
        return true;
    }
}
