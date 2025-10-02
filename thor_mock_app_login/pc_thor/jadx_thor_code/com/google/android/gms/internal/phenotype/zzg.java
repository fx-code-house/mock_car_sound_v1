package com.google.android.gms.internal.phenotype;

import android.database.ContentObserver;
import android.os.Handler;

/* loaded from: classes2.dex */
final class zzg extends ContentObserver {
    zzg(Handler handler) {
        super(null);
    }

    @Override // android.database.ContentObserver
    public final void onChange(boolean z) {
        zzf.zzbh.set(true);
    }
}
