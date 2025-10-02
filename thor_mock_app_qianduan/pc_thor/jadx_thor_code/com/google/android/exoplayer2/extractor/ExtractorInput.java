package com.google.android.exoplayer2.extractor;

import com.google.android.exoplayer2.upstream.DataReader;
import java.io.IOException;

/* loaded from: classes.dex */
public interface ExtractorInput extends DataReader {
    void advancePeekPosition(int length) throws IOException;

    boolean advancePeekPosition(int length, boolean allowEndOfInput) throws IOException;

    long getLength();

    long getPeekPosition();

    long getPosition();

    int peek(byte[] target, int offset, int length) throws IOException;

    void peekFully(byte[] target, int offset, int length) throws IOException;

    boolean peekFully(byte[] target, int offset, int length, boolean allowEndOfInput) throws IOException;

    @Override // com.google.android.exoplayer2.upstream.DataReader
    int read(byte[] buffer, int offset, int length) throws IOException;

    void readFully(byte[] target, int offset, int length) throws IOException;

    boolean readFully(byte[] target, int offset, int length, boolean allowEndOfInput) throws IOException;

    void resetPeekPosition();

    <E extends Throwable> void setRetryPosition(long position, E e) throws Throwable;

    int skip(int length) throws IOException;

    void skipFully(int length) throws IOException;

    boolean skipFully(int length, boolean allowEndOfInput) throws IOException;
}
