package com.google.android.gms.internal.icing;

import java.io.IOException;

/* compiled from: com.google.firebase:firebase-appindexing@@19.1.0 */
/* loaded from: classes2.dex */
public class zzeh extends IOException {
    private zzfh zzld;

    public zzeh(String str) {
        super(str);
        this.zzld = null;
    }

    static zzeg zzbz() {
        return new zzeg("Protocol message tag had invalid wire type.");
    }
}
