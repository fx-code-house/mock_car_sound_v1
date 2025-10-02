package com.google.android.gms.internal.vision;

import java.util.Map;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* JADX INFO: Add missing generic type declarations: [V, K] */
/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
/* loaded from: classes2.dex */
final class zzdy<K, V> extends zzdl<K, V> {
    private final /* synthetic */ zzdp zzmo;

    @NullableDecl
    private final K zzmr;
    private int zzms;

    zzdy(zzdp zzdpVar, int i) {
        this.zzmo = zzdpVar;
        this.zzmr = (K) zzdpVar.zzmi[i];
        this.zzms = i;
    }

    @Override // com.google.android.gms.internal.vision.zzdl, java.util.Map.Entry
    @NullableDecl
    public final K getKey() {
        return this.zzmr;
    }

    private final void zzco() {
        int i = this.zzms;
        if (i == -1 || i >= this.zzmo.size() || !zzcz.equal(this.zzmr, this.zzmo.zzmi[this.zzms])) {
            this.zzms = this.zzmo.indexOf(this.zzmr);
        }
    }

    @Override // com.google.android.gms.internal.vision.zzdl, java.util.Map.Entry
    @NullableDecl
    public final V getValue() {
        Map<K, V> mapZzcf = this.zzmo.zzcf();
        if (mapZzcf != null) {
            return mapZzcf.get(this.zzmr);
        }
        zzco();
        if (this.zzms == -1) {
            return null;
        }
        return (V) this.zzmo.zzmj[this.zzms];
    }

    @Override // com.google.android.gms.internal.vision.zzdl, java.util.Map.Entry
    public final V setValue(V v) {
        Map<K, V> mapZzcf = this.zzmo.zzcf();
        if (mapZzcf != null) {
            return mapZzcf.put(this.zzmr, v);
        }
        zzco();
        if (this.zzms == -1) {
            this.zzmo.put(this.zzmr, v);
            return null;
        }
        V v2 = (V) this.zzmo.zzmj[this.zzms];
        this.zzmo.zzmj[this.zzms] = v;
        return v2;
    }
}
