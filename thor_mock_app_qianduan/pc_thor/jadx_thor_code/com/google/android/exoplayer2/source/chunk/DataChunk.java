package com.google.android.exoplayer2.source.chunk;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;
import java.util.Arrays;

/* loaded from: classes.dex */
public abstract class DataChunk extends Chunk {
    private static final int READ_GRANULARITY = 16384;
    private byte[] data;
    private volatile boolean loadCanceled;

    protected abstract void consume(byte[] data, int limit) throws IOException;

    public DataChunk(DataSource dataSource, DataSpec dataSpec, int type, Format trackFormat, int trackSelectionReason, Object trackSelectionData, byte[] data) {
        DataChunk dataChunk;
        byte[] bArr;
        super(dataSource, dataSpec, type, trackFormat, trackSelectionReason, trackSelectionData, C.TIME_UNSET, C.TIME_UNSET);
        if (data == null) {
            bArr = Util.EMPTY_BYTE_ARRAY;
            dataChunk = this;
        } else {
            dataChunk = this;
            bArr = data;
        }
        dataChunk.data = bArr;
    }

    public byte[] getDataHolder() {
        return this.data;
    }

    @Override // com.google.android.exoplayer2.upstream.Loader.Loadable
    public final void cancelLoad() {
        this.loadCanceled = true;
    }

    @Override // com.google.android.exoplayer2.upstream.Loader.Loadable
    public final void load() throws IOException {
        try {
            this.dataSource.open(this.dataSpec);
            int i = 0;
            int i2 = 0;
            while (i != -1 && !this.loadCanceled) {
                maybeExpandData(i2);
                i = this.dataSource.read(this.data, i2, 16384);
                if (i != -1) {
                    i2 += i;
                }
            }
            if (!this.loadCanceled) {
                consume(this.data, i2);
            }
        } finally {
            Util.closeQuietly(this.dataSource);
        }
    }

    private void maybeExpandData(int limit) {
        byte[] bArr = this.data;
        if (bArr.length < limit + 16384) {
            this.data = Arrays.copyOf(bArr, bArr.length + 16384);
        }
    }
}
