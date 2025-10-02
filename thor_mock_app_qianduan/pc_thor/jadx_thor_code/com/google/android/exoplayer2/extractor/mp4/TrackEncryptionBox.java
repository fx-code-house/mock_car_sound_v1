package com.google.android.exoplayer2.extractor.mp4;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;

/* loaded from: classes.dex */
public final class TrackEncryptionBox {
    private static final String TAG = "TrackEncryptionBox";
    public final TrackOutput.CryptoData cryptoData;
    public final byte[] defaultInitializationVector;
    public final boolean isEncrypted;
    public final int perSampleIvSize;
    public final String schemeType;

    public TrackEncryptionBox(boolean isEncrypted, String schemeType, int perSampleIvSize, byte[] keyId, int defaultEncryptedBlocks, int defaultClearBlocks, byte[] defaultInitializationVector) {
        Assertions.checkArgument((defaultInitializationVector == null) ^ (perSampleIvSize == 0));
        this.isEncrypted = isEncrypted;
        this.schemeType = schemeType;
        this.perSampleIvSize = perSampleIvSize;
        this.defaultInitializationVector = defaultInitializationVector;
        this.cryptoData = new TrackOutput.CryptoData(schemeToCryptoMode(schemeType), keyId, defaultEncryptedBlocks, defaultClearBlocks);
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    private static int schemeToCryptoMode(String schemeType) {
        if (schemeType == null) {
            return 1;
        }
        schemeType.hashCode();
        char c = 65535;
        switch (schemeType.hashCode()) {
            case 3046605:
                if (schemeType.equals(C.CENC_TYPE_cbc1)) {
                    c = 0;
                    break;
                }
                break;
            case 3046671:
                if (schemeType.equals(C.CENC_TYPE_cbcs)) {
                    c = 1;
                    break;
                }
                break;
            case 3049879:
                if (schemeType.equals(C.CENC_TYPE_cenc)) {
                    c = 2;
                    break;
                }
                break;
            case 3049895:
                if (schemeType.equals(C.CENC_TYPE_cens)) {
                    c = 3;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
            case 1:
                return 2;
            default:
                Log.w(TAG, new StringBuilder(String.valueOf(schemeType).length() + 68).append("Unsupported protection scheme type '").append(schemeType).append("'. Assuming AES-CTR crypto mode.").toString());
            case 2:
            case 3:
                return 1;
        }
    }
}
