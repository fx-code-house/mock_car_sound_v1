package com.google.android.gms.internal.clearcut;

import android.content.SharedPreferences;
import android.util.Log;

/* loaded from: classes2.dex */
final class zzaj extends zzae<Boolean> {
    zzaj(zzao zzaoVar, String str, Boolean bool) {
        super(zzaoVar, str, bool, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @Override // com.google.android.gms.internal.clearcut.zzae
    /* renamed from: zzb, reason: merged with bridge method [inline-methods] */
    public final Boolean zza(SharedPreferences sharedPreferences) {
        try {
            return Boolean.valueOf(sharedPreferences.getBoolean(this.zzds, false));
        } catch (ClassCastException e) {
            String strValueOf = String.valueOf(this.zzds);
            Log.e("PhenotypeFlag", strValueOf.length() != 0 ? "Invalid boolean value in SharedPreferences for ".concat(strValueOf) : new String("Invalid boolean value in SharedPreferences for "), e);
            return null;
        }
    }

    @Override // com.google.android.gms.internal.clearcut.zzae
    protected final /* synthetic */ Boolean zzb(String str) {
        if (zzy.zzcr.matcher(str).matches()) {
            return true;
        }
        if (zzy.zzcs.matcher(str).matches()) {
            return false;
        }
        String str2 = this.zzds;
        Log.e("PhenotypeFlag", new StringBuilder(String.valueOf(str2).length() + 28 + String.valueOf(str).length()).append("Invalid boolean value for ").append(str2).append(": ").append(str).toString());
        return null;
    }
}
