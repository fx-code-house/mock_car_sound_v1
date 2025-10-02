package org.mapstruct.ap.shaded.freemarker.template.utility;

import java.io.IOException;
import java.io.Writer;

/* loaded from: classes3.dex */
public final class NullWriter extends Writer {
    public static final NullWriter INSTANCE = new NullWriter();

    @Override // java.io.Writer, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
    }

    @Override // java.io.Writer, java.io.Flushable
    public void flush() throws IOException {
    }

    @Override // java.io.Writer
    public void write(int i) throws IOException {
    }

    @Override // java.io.Writer
    public void write(String str) throws IOException {
    }

    @Override // java.io.Writer
    public void write(String str, int i, int i2) throws IOException {
    }

    @Override // java.io.Writer
    public void write(char[] cArr) throws IOException {
    }

    @Override // java.io.Writer
    public void write(char[] cArr, int i, int i2) throws IOException {
    }

    private NullWriter() {
    }
}
