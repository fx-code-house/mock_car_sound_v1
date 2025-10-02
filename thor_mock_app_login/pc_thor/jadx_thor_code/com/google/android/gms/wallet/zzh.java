package com.google.android.gms.wallet;

import com.google.android.gms.common.Feature;

/* compiled from: com.google.android.gms:play-services-wallet@@18.0.0 */
/* loaded from: classes2.dex */
public final class zzh {
    private static final Feature zzbe;
    private static final Feature zzbf;
    public static final Feature[] zzbg;

    static {
        Feature feature = new Feature("wallet", 1L);
        zzbe = feature;
        Feature feature2 = new Feature("wallet_biometric_auth_keys", 1L);
        zzbf = feature2;
        zzbg = new Feature[]{feature, feature2};
    }
}
