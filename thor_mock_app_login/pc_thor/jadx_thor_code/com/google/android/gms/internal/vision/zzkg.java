package com.google.android.gms.internal.vision;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/* JADX INFO: Add missing generic type declarations: [FieldDescriptorType] */
/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
/* loaded from: classes2.dex */
final class zzkg<FieldDescriptorType> extends zzkh<FieldDescriptorType, Object> {
    zzkg(int i) {
        super(i, null);
    }

    @Override // com.google.android.gms.internal.vision.zzkh
    public final void zzej() {
        if (!isImmutable()) {
            for (int i = 0; i < zzis(); i++) {
                Map.Entry<FieldDescriptorType, Object> entryZzcc = zzcc(i);
                if (((zzhv) entryZzcc.getKey()).zzgo()) {
                    entryZzcc.setValue(Collections.unmodifiableList((List) entryZzcc.getValue()));
                }
            }
            for (Map.Entry<FieldDescriptorType, Object> entry : zzit()) {
                if (((zzhv) entry.getKey()).zzgo()) {
                    entry.setValue(Collections.unmodifiableList((List) entry.getValue()));
                }
            }
        }
        super.zzej();
    }
}
