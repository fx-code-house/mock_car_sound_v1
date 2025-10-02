package com.google.android.gms.phenotype;

import android.content.SharedPreferences;
import android.util.Log;
import com.google.android.gms.phenotype.PhenotypeFlag;

/* loaded from: classes2.dex */
final class zzs extends PhenotypeFlag<String> {
    zzs(PhenotypeFlag.Factory factory, String str, String str2) {
        super(factory, str, str2, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @Override // com.google.android.gms.phenotype.PhenotypeFlag
    /* renamed from: zzb, reason: merged with bridge method [inline-methods] */
    public final String zza(SharedPreferences sharedPreferences) {
        try {
            return sharedPreferences.getString(this.zzap, null);
        } catch (ClassCastException e) {
            String strValueOf = String.valueOf(this.zzap);
            Log.e("PhenotypeFlag", strValueOf.length() != 0 ? "Invalid string value in SharedPreferences for ".concat(strValueOf) : new String("Invalid string value in SharedPreferences for "), e);
            return null;
        }
    }

    @Override // com.google.android.gms.phenotype.PhenotypeFlag
    public final /* synthetic */ String zza(String str) {
        return str;
    }
}
