package com.google.android.exoplayer2.upstream.crypto;

import android.net.Uri;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/* loaded from: classes.dex */
public final class AesCipherDataSource implements DataSource {
    private AesFlushingCipher cipher;
    private final byte[] secretKey;
    private final DataSource upstream;

    public AesCipherDataSource(byte[] secretKey, DataSource upstream) {
        this.upstream = upstream;
        this.secretKey = secretKey;
    }

    @Override // com.google.android.exoplayer2.upstream.DataSource
    public void addTransferListener(TransferListener transferListener) {
        Assertions.checkNotNull(transferListener);
        this.upstream.addTransferListener(transferListener);
    }

    @Override // com.google.android.exoplayer2.upstream.DataSource
    public long open(DataSpec dataSpec) throws IOException {
        long jOpen = this.upstream.open(dataSpec);
        this.cipher = new AesFlushingCipher(2, this.secretKey, CryptoUtil.getFNV64Hash(dataSpec.key), dataSpec.uriPositionOffset + dataSpec.position);
        return jOpen;
    }

    @Override // com.google.android.exoplayer2.upstream.DataReader
    public int read(byte[] buffer, int offset, int length) throws IOException {
        if (length == 0) {
            return 0;
        }
        int i = this.upstream.read(buffer, offset, length);
        if (i == -1) {
            return -1;
        }
        ((AesFlushingCipher) Util.castNonNull(this.cipher)).updateInPlace(buffer, offset, i);
        return i;
    }

    @Override // com.google.android.exoplayer2.upstream.DataSource
    public Uri getUri() {
        return this.upstream.getUri();
    }

    @Override // com.google.android.exoplayer2.upstream.DataSource
    public Map<String, List<String>> getResponseHeaders() {
        return this.upstream.getResponseHeaders();
    }

    @Override // com.google.android.exoplayer2.upstream.DataSource
    public void close() throws IOException {
        this.cipher = null;
        this.upstream.close();
    }
}
