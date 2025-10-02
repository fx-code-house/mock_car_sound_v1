package com.google.android.exoplayer2.upstream;

import com.google.android.exoplayer2.util.Assertions;
import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes.dex */
public final class DataSourceInputStream extends InputStream {
    private final DataSource dataSource;
    private final DataSpec dataSpec;
    private long totalBytesRead;
    private boolean opened = false;
    private boolean closed = false;
    private final byte[] singleByteArray = new byte[1];

    public DataSourceInputStream(DataSource dataSource, DataSpec dataSpec) {
        this.dataSource = dataSource;
        this.dataSpec = dataSpec;
    }

    public long bytesRead() {
        return this.totalBytesRead;
    }

    public void open() throws IOException {
        checkOpened();
    }

    @Override // java.io.InputStream
    public int read() throws IOException {
        if (read(this.singleByteArray) == -1) {
            return -1;
        }
        return this.singleByteArray[0] & 255;
    }

    @Override // java.io.InputStream
    public int read(byte[] buffer) throws IOException {
        return read(buffer, 0, buffer.length);
    }

    @Override // java.io.InputStream
    public int read(byte[] buffer, int offset, int length) throws IOException {
        Assertions.checkState(!this.closed);
        checkOpened();
        int i = this.dataSource.read(buffer, offset, length);
        if (i == -1) {
            return -1;
        }
        this.totalBytesRead += i;
        return i;
    }

    @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        if (this.closed) {
            return;
        }
        this.dataSource.close();
        this.closed = true;
    }

    private void checkOpened() throws IOException {
        if (this.opened) {
            return;
        }
        this.dataSource.open(this.dataSpec);
        this.opened = true;
    }
}
