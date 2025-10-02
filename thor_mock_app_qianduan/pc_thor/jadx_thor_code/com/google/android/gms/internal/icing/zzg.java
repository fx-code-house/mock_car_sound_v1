package com.google.android.gms.internal.icing;

import android.accounts.Account;
import java.util.ArrayList;
import java.util.List;

/* compiled from: com.google.firebase:firebase-appindexing@@19.1.0 */
/* loaded from: classes2.dex */
public final class zzg {
    private Account account;
    private List<zzk> zzi;
    private String zzj;
    private boolean zzk;

    public final zzg zza(zzk zzkVar) {
        if (this.zzi == null && zzkVar != null) {
            this.zzi = new ArrayList();
        }
        if (zzkVar != null) {
            this.zzi.add(zzkVar);
        }
        return this;
    }

    public final zzg zza(String str) {
        this.zzj = str;
        return this;
    }

    public final zzg zza(boolean z) {
        this.zzk = true;
        return this;
    }

    public final zzg zza(Account account) {
        this.account = account;
        return this;
    }

    public final zzh zzb() {
        String str = this.zzj;
        boolean z = this.zzk;
        Account account = this.account;
        List<zzk> list = this.zzi;
        return new zzh(str, z, account, list != null ? (zzk[]) list.toArray(new zzk[list.size()]) : null);
    }
}
