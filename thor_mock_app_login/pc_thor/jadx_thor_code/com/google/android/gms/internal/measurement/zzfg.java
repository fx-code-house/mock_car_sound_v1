package com.google.android.gms.internal.measurement;

import java.util.Iterator;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@18.0.0 */
/* loaded from: classes2.dex */
public abstract class zzfg<E> extends zzey<E> implements Set<E> {

    @NullableDecl
    private transient zzfb<E> zza;

    static int zza(int i) {
        int iMax = Math.max(i, 2);
        if (iMax >= 751619276) {
            zzeb.zza(iMax < 1073741824, "collection too large");
            return 1073741824;
        }
        int iHighestOneBit = Integer.highestOneBit(iMax - 1) << 1;
        while (iHighestOneBit * 0.7d < iMax) {
            iHighestOneBit <<= 1;
        }
        return iHighestOneBit;
    }

    boolean zzg() {
        return false;
    }

    zzfg() {
    }

    @Override // java.util.Collection, java.util.Set
    public boolean equals(@NullableDecl Object obj) {
        if (obj == this) {
            return true;
        }
        if ((obj instanceof zzfg) && zzg() && ((zzfg) obj).zzg() && hashCode() != obj.hashCode()) {
            return false;
        }
        return zzfv.zza(this, obj);
    }

    @Override // java.util.Collection, java.util.Set
    public int hashCode() {
        return zzfv.zza(this);
    }

    @Override // com.google.android.gms.internal.measurement.zzey
    public zzfb<E> zze() {
        zzfb<E> zzfbVar = this.zza;
        if (zzfbVar != null) {
            return zzfbVar;
        }
        zzfb<E> zzfbVarZzh = zzh();
        this.zza = zzfbVarZzh;
        return zzfbVarZzh;
    }

    zzfb<E> zzh() {
        return zzfb.zza(toArray());
    }

    @Override // com.google.android.gms.internal.measurement.zzey, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
    public /* synthetic */ Iterator iterator() {
        return iterator();
    }
}
