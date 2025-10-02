package com.google.common.io;

import android.support.v4.media.session.PlaybackStateCompat;
import com.google.common.base.Preconditions;
import com.google.common.math.IntMath;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

/* loaded from: classes2.dex */
public final class ByteStreams {
    private static final int BUFFER_SIZE = 8192;
    private static final int MAX_ARRAY_LEN = 2147483639;
    private static final OutputStream NULL_OUTPUT_STREAM = new OutputStream() { // from class: com.google.common.io.ByteStreams.1
        public String toString() {
            return "ByteStreams.nullOutputStream()";
        }

        @Override // java.io.OutputStream
        public void write(int i) {
        }

        @Override // java.io.OutputStream
        public void write(byte[] bArr) {
            Preconditions.checkNotNull(bArr);
        }

        @Override // java.io.OutputStream
        public void write(byte[] bArr, int i, int i2) {
            Preconditions.checkNotNull(bArr);
        }
    };
    private static final int TO_BYTE_ARRAY_DEQUE_SIZE = 20;
    private static final int ZERO_COPY_CHUNK_SIZE = 524288;

    static byte[] createBuffer() {
        return new byte[8192];
    }

    private ByteStreams() {
    }

    public static long copy(InputStream inputStream, OutputStream outputStream) throws IOException {
        Preconditions.checkNotNull(inputStream);
        Preconditions.checkNotNull(outputStream);
        byte[] bArrCreateBuffer = createBuffer();
        long j = 0;
        while (true) {
            int i = inputStream.read(bArrCreateBuffer);
            if (i == -1) {
                return j;
            }
            outputStream.write(bArrCreateBuffer, 0, i);
            j += i;
        }
    }

    public static long copy(ReadableByteChannel readableByteChannel, WritableByteChannel writableByteChannel) throws IOException {
        Preconditions.checkNotNull(readableByteChannel);
        Preconditions.checkNotNull(writableByteChannel);
        long jWrite = 0;
        if (readableByteChannel instanceof FileChannel) {
            FileChannel fileChannel = (FileChannel) readableByteChannel;
            long jPosition = fileChannel.position();
            long j = jPosition;
            while (true) {
                long jTransferTo = fileChannel.transferTo(j, PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE_ENABLED, writableByteChannel);
                j += jTransferTo;
                fileChannel.position(j);
                if (jTransferTo <= 0 && j >= fileChannel.size()) {
                    return j - jPosition;
                }
            }
        } else {
            ByteBuffer byteBufferWrap = ByteBuffer.wrap(createBuffer());
            while (readableByteChannel.read(byteBufferWrap) != -1) {
                byteBufferWrap.flip();
                while (byteBufferWrap.hasRemaining()) {
                    jWrite += writableByteChannel.write(byteBufferWrap);
                }
                byteBufferWrap.clear();
            }
            return jWrite;
        }
    }

    private static byte[] toByteArrayInternal(InputStream inputStream, Deque<byte[]> deque, int i) throws IOException {
        int iSaturatedMultiply = 8192;
        while (i < MAX_ARRAY_LEN) {
            int iMin = Math.min(iSaturatedMultiply, MAX_ARRAY_LEN - i);
            byte[] bArr = new byte[iMin];
            deque.add(bArr);
            int i2 = 0;
            while (i2 < iMin) {
                int i3 = inputStream.read(bArr, i2, iMin - i2);
                if (i3 == -1) {
                    return combineBuffers(deque, i);
                }
                i2 += i3;
                i += i3;
            }
            iSaturatedMultiply = IntMath.saturatedMultiply(iSaturatedMultiply, 2);
        }
        if (inputStream.read() == -1) {
            return combineBuffers(deque, MAX_ARRAY_LEN);
        }
        throw new OutOfMemoryError("input is too large to fit in a byte array");
    }

    private static byte[] combineBuffers(Deque<byte[]> deque, int i) {
        byte[] bArr = new byte[i];
        int i2 = i;
        while (i2 > 0) {
            byte[] bArrRemoveFirst = deque.removeFirst();
            int iMin = Math.min(i2, bArrRemoveFirst.length);
            System.arraycopy(bArrRemoveFirst, 0, bArr, i - i2, iMin);
            i2 -= iMin;
        }
        return bArr;
    }

    public static byte[] toByteArray(InputStream inputStream) throws IOException {
        Preconditions.checkNotNull(inputStream);
        return toByteArrayInternal(inputStream, new ArrayDeque(20), 0);
    }

    static byte[] toByteArray(InputStream inputStream, long j) throws IOException {
        Preconditions.checkArgument(j >= 0, "expectedSize (%s) must be non-negative", j);
        if (j > 2147483639) {
            throw new OutOfMemoryError(j + " bytes is too large to fit in a byte array");
        }
        int i = (int) j;
        byte[] bArr = new byte[i];
        int i2 = i;
        while (i2 > 0) {
            int i3 = i - i2;
            int i4 = inputStream.read(bArr, i3, i2);
            if (i4 == -1) {
                return Arrays.copyOf(bArr, i3);
            }
            i2 -= i4;
        }
        int i5 = inputStream.read();
        if (i5 == -1) {
            return bArr;
        }
        ArrayDeque arrayDeque = new ArrayDeque(22);
        arrayDeque.add(bArr);
        arrayDeque.add(new byte[]{(byte) i5});
        return toByteArrayInternal(inputStream, arrayDeque, i + 1);
    }

    public static long exhaust(InputStream inputStream) throws IOException {
        byte[] bArrCreateBuffer = createBuffer();
        long j = 0;
        while (true) {
            long j2 = inputStream.read(bArrCreateBuffer);
            if (j2 == -1) {
                return j;
            }
            j += j2;
        }
    }

    public static ByteArrayDataInput newDataInput(byte[] bArr) {
        return newDataInput(new ByteArrayInputStream(bArr));
    }

    public static ByteArrayDataInput newDataInput(byte[] bArr, int i) {
        Preconditions.checkPositionIndex(i, bArr.length);
        return newDataInput(new ByteArrayInputStream(bArr, i, bArr.length - i));
    }

    public static ByteArrayDataInput newDataInput(ByteArrayInputStream byteArrayInputStream) {
        return new ByteArrayDataInputStream((ByteArrayInputStream) Preconditions.checkNotNull(byteArrayInputStream));
    }

    private static class ByteArrayDataInputStream implements ByteArrayDataInput {
        final DataInput input;

        ByteArrayDataInputStream(ByteArrayInputStream byteArrayInputStream) {
            this.input = new DataInputStream(byteArrayInputStream);
        }

        @Override // com.google.common.io.ByteArrayDataInput, java.io.DataInput
        public void readFully(byte[] bArr) throws IOException {
            try {
                this.input.readFully(bArr);
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }

        @Override // com.google.common.io.ByteArrayDataInput, java.io.DataInput
        public void readFully(byte[] bArr, int i, int i2) throws IOException {
            try {
                this.input.readFully(bArr, i, i2);
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }

        @Override // com.google.common.io.ByteArrayDataInput, java.io.DataInput
        public int skipBytes(int i) {
            try {
                return this.input.skipBytes(i);
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }

        @Override // com.google.common.io.ByteArrayDataInput, java.io.DataInput
        public boolean readBoolean() {
            try {
                return this.input.readBoolean();
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }

        @Override // com.google.common.io.ByteArrayDataInput, java.io.DataInput
        public byte readByte() {
            try {
                return this.input.readByte();
            } catch (EOFException e) {
                throw new IllegalStateException(e);
            } catch (IOException e2) {
                throw new AssertionError(e2);
            }
        }

        @Override // com.google.common.io.ByteArrayDataInput, java.io.DataInput
        public int readUnsignedByte() {
            try {
                return this.input.readUnsignedByte();
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }

        @Override // com.google.common.io.ByteArrayDataInput, java.io.DataInput
        public short readShort() {
            try {
                return this.input.readShort();
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }

        @Override // com.google.common.io.ByteArrayDataInput, java.io.DataInput
        public int readUnsignedShort() {
            try {
                return this.input.readUnsignedShort();
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }

        @Override // com.google.common.io.ByteArrayDataInput, java.io.DataInput
        public char readChar() {
            try {
                return this.input.readChar();
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }

        @Override // com.google.common.io.ByteArrayDataInput, java.io.DataInput
        public int readInt() {
            try {
                return this.input.readInt();
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }

        @Override // com.google.common.io.ByteArrayDataInput, java.io.DataInput
        public long readLong() {
            try {
                return this.input.readLong();
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }

        @Override // com.google.common.io.ByteArrayDataInput, java.io.DataInput
        public float readFloat() {
            try {
                return this.input.readFloat();
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }

        @Override // com.google.common.io.ByteArrayDataInput, java.io.DataInput
        public double readDouble() {
            try {
                return this.input.readDouble();
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }

        @Override // com.google.common.io.ByteArrayDataInput, java.io.DataInput
        public String readLine() {
            try {
                return this.input.readLine();
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }

        @Override // com.google.common.io.ByteArrayDataInput, java.io.DataInput
        public String readUTF() {
            try {
                return this.input.readUTF();
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }
    }

    public static ByteArrayDataOutput newDataOutput() {
        return newDataOutput(new ByteArrayOutputStream());
    }

    public static ByteArrayDataOutput newDataOutput(int i) {
        if (i < 0) {
            throw new IllegalArgumentException(String.format("Invalid size: %s", Integer.valueOf(i)));
        }
        return newDataOutput(new ByteArrayOutputStream(i));
    }

    public static ByteArrayDataOutput newDataOutput(ByteArrayOutputStream byteArrayOutputStream) {
        return new ByteArrayDataOutputStream((ByteArrayOutputStream) Preconditions.checkNotNull(byteArrayOutputStream));
    }

    private static class ByteArrayDataOutputStream implements ByteArrayDataOutput {
        final ByteArrayOutputStream byteArrayOutputSteam;
        final DataOutput output;

        ByteArrayDataOutputStream(ByteArrayOutputStream byteArrayOutputStream) {
            this.byteArrayOutputSteam = byteArrayOutputStream;
            this.output = new DataOutputStream(byteArrayOutputStream);
        }

        @Override // com.google.common.io.ByteArrayDataOutput, java.io.DataOutput
        public void write(int i) throws IOException {
            try {
                this.output.write(i);
            } catch (IOException e) {
                throw new AssertionError(e);
            }
        }

        @Override // com.google.common.io.ByteArrayDataOutput, java.io.DataOutput
        public void write(byte[] bArr) throws IOException {
            try {
                this.output.write(bArr);
            } catch (IOException e) {
                throw new AssertionError(e);
            }
        }

        @Override // com.google.common.io.ByteArrayDataOutput, java.io.DataOutput
        public void write(byte[] bArr, int i, int i2) throws IOException {
            try {
                this.output.write(bArr, i, i2);
            } catch (IOException e) {
                throw new AssertionError(e);
            }
        }

        @Override // com.google.common.io.ByteArrayDataOutput, java.io.DataOutput
        public void writeBoolean(boolean z) throws IOException {
            try {
                this.output.writeBoolean(z);
            } catch (IOException e) {
                throw new AssertionError(e);
            }
        }

        @Override // com.google.common.io.ByteArrayDataOutput, java.io.DataOutput
        public void writeByte(int i) throws IOException {
            try {
                this.output.writeByte(i);
            } catch (IOException e) {
                throw new AssertionError(e);
            }
        }

        @Override // com.google.common.io.ByteArrayDataOutput, java.io.DataOutput
        public void writeBytes(String str) throws IOException {
            try {
                this.output.writeBytes(str);
            } catch (IOException e) {
                throw new AssertionError(e);
            }
        }

        @Override // com.google.common.io.ByteArrayDataOutput, java.io.DataOutput
        public void writeChar(int i) throws IOException {
            try {
                this.output.writeChar(i);
            } catch (IOException e) {
                throw new AssertionError(e);
            }
        }

        @Override // com.google.common.io.ByteArrayDataOutput, java.io.DataOutput
        public void writeChars(String str) throws IOException {
            try {
                this.output.writeChars(str);
            } catch (IOException e) {
                throw new AssertionError(e);
            }
        }

        @Override // com.google.common.io.ByteArrayDataOutput, java.io.DataOutput
        public void writeDouble(double d) throws IOException {
            try {
                this.output.writeDouble(d);
            } catch (IOException e) {
                throw new AssertionError(e);
            }
        }

        @Override // com.google.common.io.ByteArrayDataOutput, java.io.DataOutput
        public void writeFloat(float f) throws IOException {
            try {
                this.output.writeFloat(f);
            } catch (IOException e) {
                throw new AssertionError(e);
            }
        }

        @Override // com.google.common.io.ByteArrayDataOutput, java.io.DataOutput
        public void writeInt(int i) throws IOException {
            try {
                this.output.writeInt(i);
            } catch (IOException e) {
                throw new AssertionError(e);
            }
        }

        @Override // com.google.common.io.ByteArrayDataOutput, java.io.DataOutput
        public void writeLong(long j) throws IOException {
            try {
                this.output.writeLong(j);
            } catch (IOException e) {
                throw new AssertionError(e);
            }
        }

        @Override // com.google.common.io.ByteArrayDataOutput, java.io.DataOutput
        public void writeShort(int i) throws IOException {
            try {
                this.output.writeShort(i);
            } catch (IOException e) {
                throw new AssertionError(e);
            }
        }

        @Override // com.google.common.io.ByteArrayDataOutput, java.io.DataOutput
        public void writeUTF(String str) throws IOException {
            try {
                this.output.writeUTF(str);
            } catch (IOException e) {
                throw new AssertionError(e);
            }
        }

        @Override // com.google.common.io.ByteArrayDataOutput
        public byte[] toByteArray() {
            return this.byteArrayOutputSteam.toByteArray();
        }
    }

    public static OutputStream nullOutputStream() {
        return NULL_OUTPUT_STREAM;
    }

    public static InputStream limit(InputStream inputStream, long j) {
        return new LimitedInputStream(inputStream, j);
    }

    private static final class LimitedInputStream extends FilterInputStream {
        private long left;
        private long mark;

        LimitedInputStream(InputStream inputStream, long j) {
            super(inputStream);
            this.mark = -1L;
            Preconditions.checkNotNull(inputStream);
            Preconditions.checkArgument(j >= 0, "limit must be non-negative");
            this.left = j;
        }

        @Override // java.io.FilterInputStream, java.io.InputStream
        public int available() throws IOException {
            return (int) Math.min(this.in.available(), this.left);
        }

        @Override // java.io.FilterInputStream, java.io.InputStream
        public synchronized void mark(int i) {
            this.in.mark(i);
            this.mark = this.left;
        }

        @Override // java.io.FilterInputStream, java.io.InputStream
        public int read() throws IOException {
            if (this.left == 0) {
                return -1;
            }
            int i = this.in.read();
            if (i != -1) {
                this.left--;
            }
            return i;
        }

        @Override // java.io.FilterInputStream, java.io.InputStream
        public int read(byte[] bArr, int i, int i2) throws IOException {
            long j = this.left;
            if (j == 0) {
                return -1;
            }
            int i3 = this.in.read(bArr, i, (int) Math.min(i2, j));
            if (i3 != -1) {
                this.left -= i3;
            }
            return i3;
        }

        @Override // java.io.FilterInputStream, java.io.InputStream
        public synchronized void reset() throws IOException {
            if (!this.in.markSupported()) {
                throw new IOException("Mark not supported");
            }
            if (this.mark == -1) {
                throw new IOException("Mark not set");
            }
            this.in.reset();
            this.left = this.mark;
        }

        @Override // java.io.FilterInputStream, java.io.InputStream
        public long skip(long j) throws IOException {
            long jSkip = this.in.skip(Math.min(j, this.left));
            this.left -= jSkip;
            return jSkip;
        }
    }

    public static void readFully(InputStream inputStream, byte[] bArr) throws IOException {
        readFully(inputStream, bArr, 0, bArr.length);
    }

    public static void readFully(InputStream inputStream, byte[] bArr, int i, int i2) throws IOException {
        int i3 = read(inputStream, bArr, i, i2);
        if (i3 != i2) {
            throw new EOFException("reached end of stream after reading " + i3 + " bytes; " + i2 + " bytes expected");
        }
    }

    public static void skipFully(InputStream inputStream, long j) throws IOException {
        long jSkipUpTo = skipUpTo(inputStream, j);
        if (jSkipUpTo < j) {
            throw new EOFException("reached end of stream after skipping " + jSkipUpTo + " bytes; " + j + " bytes expected");
        }
    }

    static long skipUpTo(InputStream inputStream, long j) throws IOException {
        byte[] bArrCreateBuffer = createBuffer();
        long j2 = 0;
        while (j2 < j) {
            long j3 = j - j2;
            long jSkipSafely = skipSafely(inputStream, j3);
            if (jSkipSafely == 0) {
                jSkipSafely = inputStream.read(bArrCreateBuffer, 0, (int) Math.min(j3, bArrCreateBuffer.length));
                if (jSkipSafely == -1) {
                    break;
                }
            }
            j2 += jSkipSafely;
        }
        return j2;
    }

    private static long skipSafely(InputStream inputStream, long j) throws IOException {
        int iAvailable = inputStream.available();
        if (iAvailable == 0) {
            return 0L;
        }
        return inputStream.skip(Math.min(iAvailable, j));
    }

    public static <T> T readBytes(InputStream inputStream, ByteProcessor<T> byteProcessor) throws IOException {
        int i;
        Preconditions.checkNotNull(inputStream);
        Preconditions.checkNotNull(byteProcessor);
        byte[] bArrCreateBuffer = createBuffer();
        do {
            i = inputStream.read(bArrCreateBuffer);
            if (i == -1) {
                break;
            }
        } while (byteProcessor.processBytes(bArrCreateBuffer, 0, i));
        return byteProcessor.getResult();
    }

    public static int read(InputStream inputStream, byte[] bArr, int i, int i2) throws IOException {
        Preconditions.checkNotNull(inputStream);
        Preconditions.checkNotNull(bArr);
        if (i2 < 0) {
            throw new IndexOutOfBoundsException("len is negative");
        }
        int i3 = 0;
        while (i3 < i2) {
            int i4 = inputStream.read(bArr, i + i3, i2 - i3);
            if (i4 == -1) {
                break;
            }
            i3 += i4;
        }
        return i3;
    }
}
