package com.google.android.gms.internal.fido;

import java.util.Iterator;
import java.util.Set;

/* compiled from: com.google.android.gms:play-services-fido@@20.1.0 */
/* loaded from: classes2.dex */
public final class zzbx {
    static int zza(Set set) {
        Iterator it = set.iterator();
        int iHashCode = 0;
        while (it.hasNext()) {
            Object next = it.next();
            iHashCode += next != null ? next.hashCode() : 0;
        }
        return iHashCode;
    }
}
