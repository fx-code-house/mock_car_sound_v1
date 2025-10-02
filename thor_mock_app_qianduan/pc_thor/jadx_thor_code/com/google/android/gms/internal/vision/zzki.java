package com.google.android.gms.internal.vision;

import java.util.Iterator;
import java.util.Map;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
/* loaded from: classes2.dex */
final class zzki extends zzko {
    private final /* synthetic */ zzkh zzabx;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    private zzki(zzkh zzkhVar) {
        super(zzkhVar, null);
        this.zzabx = zzkhVar;
    }

    @Override // com.google.android.gms.internal.vision.zzko, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
    public final Iterator<Map.Entry<K, V>> iterator() {
        return new zzkj(this.zzabx, null);
    }

    /* synthetic */ zzki(zzkh zzkhVar, zzkg zzkgVar) {
        this(zzkhVar);
    }
}
