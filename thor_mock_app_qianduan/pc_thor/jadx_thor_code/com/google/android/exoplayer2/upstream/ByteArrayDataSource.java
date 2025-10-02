package com.google.android.exoplayer2.upstream;

import android.net.Uri;
import com.google.android.exoplayer2.util.Assertions;
import java.io.IOException;

/* loaded from: classes.dex */
public final class ByteArrayDataSource extends BaseDataSource {
    private int bytesRemaining;
    private final byte[] data;
    private boolean opened;
    private int readPosition;
    private Uri uri;

    public ByteArrayDataSource(byte[] data) {
        super(false);
        Assertions.checkNotNull(data);
        Assertions.checkArgument(data.length > 0);
        this.data = data;
    }

    @Override // com.google.android.exoplayer2.upstream.DataSource
    public long open(DataSpec dataSpec) throws IOException {
        this.uri = dataSpec.uri;
        transferInitializing(dataSpec);
        if (dataSpec.position > this.data.length) {
            throw new DataSourceException(2008);
        }
        this.readPosition = (int) dataSpec.position;
        this.bytesRemaining = this.data.length - ((int) dataSpec.position);
        if (dataSpec.length != -1) {
            this.bytesRemaining = (int) Math.min(this.bytesRemaining, dataSpec.length);
        }
        this.opened = true;
        transferStarted(dataSpec);
        return dataSpec.length != -1 ? dataSpec.length : this.bytesRemaining;
    }

    @Override // com.google.android.exoplayer2.upstream.DataReader
    public int read(byte[] buffer, int offset, int length) {
        if (length == 0) {
            return 0;
        }
        int i = this.bytesRemaining;
        if (i == 0) {
            return -1;
        }
        int iMin = Math.min(length, i);
        System.arraycopy(this.data, this.readPosition, buffer, offset, iMin);
        this.readPosition += iMin;
        this.bytesRemaining -= iMin;
        bytesTransferred(iMin);
        return iMin;
    }

    @Override // com.google.android.exoplayer2.upstream.DataSource
    public Uri getUri() {
        return this.uri;
    }

    @Override // com.google.android.exoplayer2.upstream.DataSource
    public void close() {
        if (this.opened) {
            this.opened = false;
            transferEnded();
        }
        this.uri = null;
    }
}
