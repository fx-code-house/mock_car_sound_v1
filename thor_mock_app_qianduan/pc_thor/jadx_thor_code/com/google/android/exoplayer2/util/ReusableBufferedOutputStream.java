package com.google.android.exoplayer2.util;

import java.io.BufferedOutputStream;
import java.io.OutputStream;

/* loaded from: classes.dex */
public final class ReusableBufferedOutputStream extends BufferedOutputStream {
    private boolean closed;

    public ReusableBufferedOutputStream(OutputStream out) {
        super(out);
    }

    public ReusableBufferedOutputStream(OutputStream out, int size) {
        super(out, size);
    }

    @Override // java.io.FilterOutputStream, java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws Throwable {
        this.closed = true;
        try {
            flush();
            th = null;
        } catch (Throwable th) {
            th = th;
        }
        try {
            this.out.close();
        } catch (Throwable th2) {
            if (th == null) {
                th = th2;
            }
        }
        if (th != null) {
            Util.sneakyThrow(th);
        }
    }

    public void reset(OutputStream out) {
        Assertions.checkState(this.closed);
        this.out = out;
        this.count = 0;
        this.closed = false;
    }
}
