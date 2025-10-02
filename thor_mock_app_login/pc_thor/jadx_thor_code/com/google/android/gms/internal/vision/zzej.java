package com.google.android.gms.internal.vision;

import java.util.Iterator;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
/* loaded from: classes2.dex */
public abstract class zzej<E> extends zzeb<E> implements Set<E> {

    @NullableDecl
    private transient zzee<E> zznf;

    static int zzy(int i) {
        int iMax = Math.max(i, 2);
        if (iMax >= 751619276) {
            zzde.checkArgument(iMax < 1073741824, "collection too large");
            return 1073741824;
        }
        int iHighestOneBit = Integer.highestOneBit(iMax - 1) << 1;
        while (iHighestOneBit * 0.7d < iMax) {
            iHighestOneBit <<= 1;
        }
        return iHighestOneBit;
    }

    boolean zzcz() {
        return false;
    }

    zzej() {
    }

    @Override // java.util.Collection, java.util.Set
    public boolean equals(@NullableDecl Object obj) {
        if (obj == this) {
            return true;
        }
        if ((obj instanceof zzej) && zzcz() && ((zzej) obj).zzcz() && hashCode() != obj.hashCode()) {
            return false;
        }
        return zzey.zza(this, obj);
    }

    @Override // java.util.Collection, java.util.Set
    public int hashCode() {
        return zzey.zza(this);
    }

    @Override // com.google.android.gms.internal.vision.zzeb
    public zzee<E> zzct() {
        zzee<E> zzeeVar = this.zznf;
        if (zzeeVar != null) {
            return zzeeVar;
        }
        zzee<E> zzeeVarZzda = zzda();
        this.zznf = zzeeVarZzda;
        return zzeeVarZzda;
    }

    zzee<E> zzda() {
        return zzee.zza(toArray());
    }

    @Override // com.google.android.gms.internal.vision.zzeb, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
    public /* synthetic */ Iterator iterator() {
        return iterator();
    }
}
