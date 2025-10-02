package com.google.android.gms.internal.wearable;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/* compiled from: com.google.android.gms:play-services-wearable@@17.1.0 */
/* loaded from: classes2.dex */
final class zzdl extends zzds {
    zzdl(int i) {
        super(i, null);
    }

    @Override // com.google.android.gms.internal.wearable.zzds
    public final void zza() {
        if (!zzb()) {
            for (int i = 0; i < zzc(); i++) {
                Map.Entry entryZzd = zzd(i);
                if (((zzbk) entryZzd.getKey()).zzc()) {
                    entryZzd.setValue(Collections.unmodifiableList((List) entryZzd.getValue()));
                }
            }
            for (Map.Entry entry : zze()) {
                if (((zzbk) entry.getKey()).zzc()) {
                    entry.setValue(Collections.unmodifiableList((List) entry.getValue()));
                }
            }
        }
        super.zza();
    }
}
