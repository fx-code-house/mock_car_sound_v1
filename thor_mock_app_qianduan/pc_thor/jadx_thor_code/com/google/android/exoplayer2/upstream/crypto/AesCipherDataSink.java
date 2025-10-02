package com.google.android.exoplayer2.upstream.crypto;

import com.google.android.exoplayer2.upstream.DataSink;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;

/* loaded from: classes.dex */
public final class AesCipherDataSink implements DataSink {
    private AesFlushingCipher cipher;
    private final byte[] scratch;
    private final byte[] secretKey;
    private final DataSink wrappedDataSink;

    public AesCipherDataSink(byte[] secretKey, DataSink wrappedDataSink) {
        this(secretKey, wrappedDataSink, null);
    }

    public AesCipherDataSink(byte[] secretKey, DataSink wrappedDataSink, byte[] scratch) {
        this.wrappedDataSink = wrappedDataSink;
        this.secretKey = secretKey;
        this.scratch = scratch;
    }

    @Override // com.google.android.exoplayer2.upstream.DataSink
    public void open(DataSpec dataSpec) throws IOException {
        this.wrappedDataSink.open(dataSpec);
        this.cipher = new AesFlushingCipher(1, this.secretKey, CryptoUtil.getFNV64Hash(dataSpec.key), dataSpec.uriPositionOffset + dataSpec.position);
    }

    @Override // com.google.android.exoplayer2.upstream.DataSink
    public void write(byte[] buffer, int offset, int length) throws IOException {
        if (this.scratch == null) {
            ((AesFlushingCipher) Util.castNonNull(this.cipher)).updateInPlace(buffer, offset, length);
            this.wrappedDataSink.write(buffer, offset, length);
            return;
        }
        int i = 0;
        while (i < length) {
            int iMin = Math.min(length - i, this.scratch.length);
            ((AesFlushingCipher) Util.castNonNull(this.cipher)).update(buffer, offset + i, iMin, this.scratch, 0);
            this.wrappedDataSink.write(this.scratch, 0, iMin);
            i += iMin;
        }
    }

    @Override // com.google.android.exoplayer2.upstream.DataSink
    public void close() throws IOException {
        this.cipher = null;
        this.wrappedDataSink.close();
    }
}
