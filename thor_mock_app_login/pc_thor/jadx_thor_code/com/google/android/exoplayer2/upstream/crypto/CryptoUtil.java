package com.google.android.exoplayer2.upstream.crypto;

/* loaded from: classes.dex */
final class CryptoUtil {
    private CryptoUtil() {
    }

    public static long getFNV64Hash(String input) {
        long j = 0;
        if (input == null) {
            return 0L;
        }
        for (int i = 0; i < input.length(); i++) {
            long jCharAt = j ^ input.charAt(i);
            j = jCharAt + (jCharAt << 1) + (jCharAt << 4) + (jCharAt << 5) + (jCharAt << 7) + (jCharAt << 8) + (jCharAt << 40);
        }
        return j;
    }
}
