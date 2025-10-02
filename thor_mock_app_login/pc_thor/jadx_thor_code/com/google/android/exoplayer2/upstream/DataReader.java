package com.google.android.exoplayer2.upstream;

import java.io.IOException;

/* loaded from: classes.dex */
public interface DataReader {
    int read(byte[] buffer, int offset, int length) throws IOException;
}
