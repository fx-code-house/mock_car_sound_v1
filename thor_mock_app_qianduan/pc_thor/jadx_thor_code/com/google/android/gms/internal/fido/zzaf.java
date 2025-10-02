package com.google.android.gms.internal.fido;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

/* compiled from: com.google.android.gms:play-services-fido@@20.1.0 */
/* loaded from: classes2.dex */
public final class zzaf {
    public static final Appendable zza(Appendable appendable, Iterator it, zzag zzagVar, String str) throws IOException {
        if (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            appendable.append(zzag.zzd(entry.getKey()));
            appendable.append(" : ");
            appendable.append(zzag.zzd(entry.getValue()));
            while (it.hasNext()) {
                appendable.append(zzagVar.zza);
                Map.Entry entry2 = (Map.Entry) it.next();
                appendable.append(zzag.zzd(entry2.getKey()));
                appendable.append(" : ");
                appendable.append(zzag.zzd(entry2.getValue()));
            }
        }
        return appendable;
    }
}
