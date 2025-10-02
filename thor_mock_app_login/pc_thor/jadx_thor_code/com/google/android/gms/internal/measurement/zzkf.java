package com.google.android.gms.internal.measurement;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/* JADX INFO: Add missing generic type declarations: [FieldDescriptorType] */
/* compiled from: com.google.android.gms:play-services-measurement-base@@18.0.0 */
/* loaded from: classes2.dex */
final class zzkf<FieldDescriptorType> extends zzkc<FieldDescriptorType, Object> {
    zzkf(int i) {
        super(i, null);
    }

    @Override // com.google.android.gms.internal.measurement.zzkc
    public final void zza() {
        if (!zzb()) {
            for (int i = 0; i < zzc(); i++) {
                Map.Entry<FieldDescriptorType, Object> entryZzb = zzb(i);
                if (((zzht) entryZzb.getKey()).zzd()) {
                    entryZzb.setValue(Collections.unmodifiableList((List) entryZzb.getValue()));
                }
            }
            for (Map.Entry<FieldDescriptorType, Object> entry : zzd()) {
                if (((zzht) entry.getKey()).zzd()) {
                    entry.setValue(Collections.unmodifiableList((List) entry.getValue()));
                }
            }
        }
        super.zza();
    }
}
