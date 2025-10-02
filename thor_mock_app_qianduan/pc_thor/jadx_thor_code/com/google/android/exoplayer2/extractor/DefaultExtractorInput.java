package com.google.android.exoplayer2.extractor;

import com.google.android.exoplayer2.upstream.DataReader;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import java.io.EOFException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.util.Arrays;

/* loaded from: classes.dex */
public final class DefaultExtractorInput implements ExtractorInput {
    private static final int PEEK_MAX_FREE_SPACE = 524288;
    private static final int PEEK_MIN_FREE_SPACE_AFTER_RESIZE = 65536;
    private static final int SCRATCH_SPACE_SIZE = 4096;
    private final DataReader dataReader;
    private int peekBufferLength;
    private int peekBufferPosition;
    private long position;
    private final long streamLength;
    private byte[] peekBuffer = new byte[65536];
    private final byte[] scratchSpace = new byte[4096];

    public DefaultExtractorInput(DataReader dataReader, long position, long length) {
        this.dataReader = dataReader;
        this.position = position;
        this.streamLength = length;
    }

    @Override // com.google.android.exoplayer2.extractor.ExtractorInput, com.google.android.exoplayer2.upstream.DataReader
    public int read(byte[] buffer, int offset, int length) throws IOException {
        int fromPeekBuffer = readFromPeekBuffer(buffer, offset, length);
        if (fromPeekBuffer == 0) {
            fromPeekBuffer = readFromUpstream(buffer, offset, length, 0, true);
        }
        commitBytesRead(fromPeekBuffer);
        return fromPeekBuffer;
    }

    @Override // com.google.android.exoplayer2.extractor.ExtractorInput
    public boolean readFully(byte[] target, int offset, int length, boolean allowEndOfInput) throws IOException {
        int fromPeekBuffer = readFromPeekBuffer(target, offset, length);
        while (fromPeekBuffer < length && fromPeekBuffer != -1) {
            fromPeekBuffer = readFromUpstream(target, offset, length, fromPeekBuffer, allowEndOfInput);
        }
        commitBytesRead(fromPeekBuffer);
        return fromPeekBuffer != -1;
    }

    @Override // com.google.android.exoplayer2.extractor.ExtractorInput
    public void readFully(byte[] target, int offset, int length) throws IOException {
        readFully(target, offset, length, false);
    }

    @Override // com.google.android.exoplayer2.extractor.ExtractorInput
    public int skip(int length) throws IOException {
        int iSkipFromPeekBuffer = skipFromPeekBuffer(length);
        if (iSkipFromPeekBuffer == 0) {
            byte[] bArr = this.scratchSpace;
            iSkipFromPeekBuffer = readFromUpstream(bArr, 0, Math.min(length, bArr.length), 0, true);
        }
        commitBytesRead(iSkipFromPeekBuffer);
        return iSkipFromPeekBuffer;
    }

    @Override // com.google.android.exoplayer2.extractor.ExtractorInput
    public boolean skipFully(int length, boolean allowEndOfInput) throws IOException {
        int iSkipFromPeekBuffer = skipFromPeekBuffer(length);
        while (iSkipFromPeekBuffer < length && iSkipFromPeekBuffer != -1) {
            iSkipFromPeekBuffer = readFromUpstream(this.scratchSpace, -iSkipFromPeekBuffer, Math.min(length, this.scratchSpace.length + iSkipFromPeekBuffer), iSkipFromPeekBuffer, allowEndOfInput);
        }
        commitBytesRead(iSkipFromPeekBuffer);
        return iSkipFromPeekBuffer != -1;
    }

    @Override // com.google.android.exoplayer2.extractor.ExtractorInput
    public void skipFully(int length) throws IOException {
        skipFully(length, false);
    }

    @Override // com.google.android.exoplayer2.extractor.ExtractorInput
    public int peek(byte[] target, int offset, int length) throws IOException {
        int iMin;
        ensureSpaceForPeek(length);
        int i = this.peekBufferLength;
        int i2 = this.peekBufferPosition;
        int i3 = i - i2;
        if (i3 == 0) {
            iMin = readFromUpstream(this.peekBuffer, i2, length, 0, true);
            if (iMin == -1) {
                return -1;
            }
            this.peekBufferLength += iMin;
        } else {
            iMin = Math.min(length, i3);
        }
        System.arraycopy(this.peekBuffer, this.peekBufferPosition, target, offset, iMin);
        this.peekBufferPosition += iMin;
        return iMin;
    }

    @Override // com.google.android.exoplayer2.extractor.ExtractorInput
    public boolean peekFully(byte[] target, int offset, int length, boolean allowEndOfInput) throws IOException {
        if (!advancePeekPosition(length, allowEndOfInput)) {
            return false;
        }
        System.arraycopy(this.peekBuffer, this.peekBufferPosition - length, target, offset, length);
        return true;
    }

    @Override // com.google.android.exoplayer2.extractor.ExtractorInput
    public void peekFully(byte[] target, int offset, int length) throws IOException {
        peekFully(target, offset, length, false);
    }

    @Override // com.google.android.exoplayer2.extractor.ExtractorInput
    public boolean advancePeekPosition(int length, boolean allowEndOfInput) throws IOException {
        ensureSpaceForPeek(length);
        int fromUpstream = this.peekBufferLength - this.peekBufferPosition;
        while (fromUpstream < length) {
            fromUpstream = readFromUpstream(this.peekBuffer, this.peekBufferPosition, length, fromUpstream, allowEndOfInput);
            if (fromUpstream == -1) {
                return false;
            }
            this.peekBufferLength = this.peekBufferPosition + fromUpstream;
        }
        this.peekBufferPosition += length;
        return true;
    }

    @Override // com.google.android.exoplayer2.extractor.ExtractorInput
    public void advancePeekPosition(int length) throws IOException {
        advancePeekPosition(length, false);
    }

    @Override // com.google.android.exoplayer2.extractor.ExtractorInput
    public void resetPeekPosition() {
        this.peekBufferPosition = 0;
    }

    @Override // com.google.android.exoplayer2.extractor.ExtractorInput
    public long getPeekPosition() {
        return this.position + this.peekBufferPosition;
    }

    @Override // com.google.android.exoplayer2.extractor.ExtractorInput
    public long getPosition() {
        return this.position;
    }

    @Override // com.google.android.exoplayer2.extractor.ExtractorInput
    public long getLength() {
        return this.streamLength;
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: E extends java.lang.Throwable */
    @Override // com.google.android.exoplayer2.extractor.ExtractorInput
    public <E extends Throwable> void setRetryPosition(long position, E e) throws Throwable {
        Assertions.checkArgument(position >= 0);
        this.position = position;
        throw e;
    }

    private void ensureSpaceForPeek(int length) {
        int i = this.peekBufferPosition + length;
        byte[] bArr = this.peekBuffer;
        if (i > bArr.length) {
            this.peekBuffer = Arrays.copyOf(this.peekBuffer, Util.constrainValue(bArr.length * 2, 65536 + i, i + 524288));
        }
    }

    private int skipFromPeekBuffer(int length) {
        int iMin = Math.min(this.peekBufferLength, length);
        updatePeekBuffer(iMin);
        return iMin;
    }

    private int readFromPeekBuffer(byte[] target, int offset, int length) {
        int i = this.peekBufferLength;
        if (i == 0) {
            return 0;
        }
        int iMin = Math.min(i, length);
        System.arraycopy(this.peekBuffer, 0, target, offset, iMin);
        updatePeekBuffer(iMin);
        return iMin;
    }

    private void updatePeekBuffer(int bytesConsumed) {
        int i = this.peekBufferLength - bytesConsumed;
        this.peekBufferLength = i;
        this.peekBufferPosition = 0;
        byte[] bArr = this.peekBuffer;
        byte[] bArr2 = i < bArr.length - 524288 ? new byte[65536 + i] : bArr;
        System.arraycopy(bArr, bytesConsumed, bArr2, 0, i);
        this.peekBuffer = bArr2;
    }

    private int readFromUpstream(byte[] target, int offset, int length, int bytesAlreadyRead, boolean allowEndOfInput) throws IOException {
        if (Thread.interrupted()) {
            throw new InterruptedIOException();
        }
        int i = this.dataReader.read(target, offset + bytesAlreadyRead, length - bytesAlreadyRead);
        if (i != -1) {
            return bytesAlreadyRead + i;
        }
        if (bytesAlreadyRead == 0 && allowEndOfInput) {
            return -1;
        }
        throw new EOFException();
    }

    private void commitBytesRead(int bytesRead) {
        if (bytesRead != -1) {
            this.position += bytesRead;
        }
    }
}
