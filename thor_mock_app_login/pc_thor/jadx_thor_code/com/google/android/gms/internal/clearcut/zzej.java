package com.google.android.gms.internal.clearcut;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/* JADX INFO: Add missing generic type declarations: [FieldDescriptorType] */
/* loaded from: classes2.dex */
final class zzej<FieldDescriptorType> extends zzei<FieldDescriptorType, Object> {
    zzej(int i) {
        super(i, null);
    }

    @Override // com.google.android.gms.internal.clearcut.zzei
    public final void zzv() {
        if (!isImmutable()) {
            for (int i = 0; i < zzdr(); i++) {
                Map.Entry<FieldDescriptorType, Object> entryZzak = zzak(i);
                if (((zzca) entryZzak.getKey()).zzaw()) {
                    entryZzak.setValue(Collections.unmodifiableList((List) entryZzak.getValue()));
                }
            }
            for (Map.Entry<FieldDescriptorType, Object> entry : zzds()) {
                if (((zzca) entry.getKey()).zzaw()) {
                    entry.setValue(Collections.unmodifiableList((List) entry.getValue()));
                }
            }
        }
        super.zzv();
    }
}
