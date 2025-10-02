package com.google.android.exoplayer2.source;

import com.google.android.exoplayer2.decoder.CryptoInfo;
import com.google.android.exoplayer2.decoder.DecoderInputBuffer;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.source.SampleQueue;
import com.google.android.exoplayer2.upstream.Allocation;
import com.google.android.exoplayer2.upstream.Allocator;
import com.google.android.exoplayer2.upstream.DataReader;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.Util;
import java.io.EOFException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;

/* loaded from: classes.dex */
class SampleDataQueue {
    private static final int INITIAL_SCRATCH_SIZE = 32;
    private final int allocationLength;
    private final Allocator allocator;
    private AllocationNode firstAllocationNode;
    private AllocationNode readAllocationNode;
    private final ParsableByteArray scratch;
    private long totalBytesWritten;
    private AllocationNode writeAllocationNode;

    public SampleDataQueue(Allocator allocator) {
        this.allocator = allocator;
        int individualAllocationLength = allocator.getIndividualAllocationLength();
        this.allocationLength = individualAllocationLength;
        this.scratch = new ParsableByteArray(32);
        AllocationNode allocationNode = new AllocationNode(0L, individualAllocationLength);
        this.firstAllocationNode = allocationNode;
        this.readAllocationNode = allocationNode;
        this.writeAllocationNode = allocationNode;
    }

    public void reset() {
        clearAllocationNodes(this.firstAllocationNode);
        AllocationNode allocationNode = new AllocationNode(0L, this.allocationLength);
        this.firstAllocationNode = allocationNode;
        this.readAllocationNode = allocationNode;
        this.writeAllocationNode = allocationNode;
        this.totalBytesWritten = 0L;
        this.allocator.trim();
    }

    public void discardUpstreamSampleBytes(long totalBytesWritten) {
        this.totalBytesWritten = totalBytesWritten;
        if (totalBytesWritten == 0 || totalBytesWritten == this.firstAllocationNode.startPosition) {
            clearAllocationNodes(this.firstAllocationNode);
            AllocationNode allocationNode = new AllocationNode(this.totalBytesWritten, this.allocationLength);
            this.firstAllocationNode = allocationNode;
            this.readAllocationNode = allocationNode;
            this.writeAllocationNode = allocationNode;
            return;
        }
        AllocationNode allocationNode2 = this.firstAllocationNode;
        while (this.totalBytesWritten > allocationNode2.endPosition) {
            allocationNode2 = allocationNode2.next;
        }
        AllocationNode allocationNode3 = allocationNode2.next;
        clearAllocationNodes(allocationNode3);
        allocationNode2.next = new AllocationNode(allocationNode2.endPosition, this.allocationLength);
        this.writeAllocationNode = this.totalBytesWritten == allocationNode2.endPosition ? allocationNode2.next : allocationNode2;
        if (this.readAllocationNode == allocationNode3) {
            this.readAllocationNode = allocationNode2.next;
        }
    }

    public void rewind() {
        this.readAllocationNode = this.firstAllocationNode;
    }

    public void readToBuffer(DecoderInputBuffer buffer, SampleQueue.SampleExtrasHolder extrasHolder) {
        this.readAllocationNode = readSampleData(this.readAllocationNode, buffer, extrasHolder, this.scratch);
    }

    public void peekToBuffer(DecoderInputBuffer buffer, SampleQueue.SampleExtrasHolder extrasHolder) {
        readSampleData(this.readAllocationNode, buffer, extrasHolder, this.scratch);
    }

    public void discardDownstreamTo(long absolutePosition) {
        if (absolutePosition == -1) {
            return;
        }
        while (absolutePosition >= this.firstAllocationNode.endPosition) {
            this.allocator.release(this.firstAllocationNode.allocation);
            this.firstAllocationNode = this.firstAllocationNode.clear();
        }
        if (this.readAllocationNode.startPosition < this.firstAllocationNode.startPosition) {
            this.readAllocationNode = this.firstAllocationNode;
        }
    }

    public long getTotalBytesWritten() {
        return this.totalBytesWritten;
    }

    public int sampleData(DataReader input, int length, boolean allowEndOfInput) throws IOException {
        int i = input.read(this.writeAllocationNode.allocation.data, this.writeAllocationNode.translateOffset(this.totalBytesWritten), preAppend(length));
        if (i != -1) {
            postAppend(i);
            return i;
        }
        if (allowEndOfInput) {
            return -1;
        }
        throw new EOFException();
    }

    public void sampleData(ParsableByteArray buffer, int length) {
        while (length > 0) {
            int iPreAppend = preAppend(length);
            buffer.readBytes(this.writeAllocationNode.allocation.data, this.writeAllocationNode.translateOffset(this.totalBytesWritten), iPreAppend);
            length -= iPreAppend;
            postAppend(iPreAppend);
        }
    }

    private void clearAllocationNodes(AllocationNode allocationNode) {
        if (allocationNode.wasInitialized) {
            boolean z = this.writeAllocationNode.wasInitialized;
            int i = (z ? 1 : 0) + (((int) (this.writeAllocationNode.startPosition - allocationNode.startPosition)) / this.allocationLength);
            Allocation[] allocationArr = new Allocation[i];
            for (int i2 = 0; i2 < i; i2++) {
                allocationArr[i2] = allocationNode.allocation;
                allocationNode = allocationNode.clear();
            }
            this.allocator.release(allocationArr);
        }
    }

    private int preAppend(int length) {
        if (!this.writeAllocationNode.wasInitialized) {
            this.writeAllocationNode.initialize(this.allocator.allocate(), new AllocationNode(this.writeAllocationNode.endPosition, this.allocationLength));
        }
        return Math.min(length, (int) (this.writeAllocationNode.endPosition - this.totalBytesWritten));
    }

    private void postAppend(int length) {
        long j = this.totalBytesWritten + length;
        this.totalBytesWritten = j;
        if (j == this.writeAllocationNode.endPosition) {
            this.writeAllocationNode = this.writeAllocationNode.next;
        }
    }

    private static AllocationNode readSampleData(AllocationNode allocationNode, DecoderInputBuffer buffer, SampleQueue.SampleExtrasHolder extrasHolder, ParsableByteArray scratch) {
        if (buffer.isEncrypted()) {
            allocationNode = readEncryptionData(allocationNode, buffer, extrasHolder, scratch);
        }
        if (buffer.hasSupplementalData()) {
            scratch.reset(4);
            AllocationNode data = readData(allocationNode, extrasHolder.offset, scratch.getData(), 4);
            int unsignedIntToInt = scratch.readUnsignedIntToInt();
            extrasHolder.offset += 4;
            extrasHolder.size -= 4;
            buffer.ensureSpaceForWrite(unsignedIntToInt);
            AllocationNode data2 = readData(data, extrasHolder.offset, buffer.data, unsignedIntToInt);
            extrasHolder.offset += unsignedIntToInt;
            extrasHolder.size -= unsignedIntToInt;
            buffer.resetSupplementalData(extrasHolder.size);
            return readData(data2, extrasHolder.offset, buffer.supplementalData, extrasHolder.size);
        }
        buffer.ensureSpaceForWrite(extrasHolder.size);
        return readData(allocationNode, extrasHolder.offset, buffer.data, extrasHolder.size);
    }

    private static AllocationNode readEncryptionData(AllocationNode allocationNode, DecoderInputBuffer buffer, SampleQueue.SampleExtrasHolder extrasHolder, ParsableByteArray scratch) {
        long j = extrasHolder.offset;
        int unsignedShort = 1;
        scratch.reset(1);
        AllocationNode data = readData(allocationNode, j, scratch.getData(), 1);
        long j2 = j + 1;
        byte b = scratch.getData()[0];
        boolean z = (b & 128) != 0;
        int i = b & Byte.MAX_VALUE;
        CryptoInfo cryptoInfo = buffer.cryptoInfo;
        if (cryptoInfo.iv == null) {
            cryptoInfo.iv = new byte[16];
        } else {
            Arrays.fill(cryptoInfo.iv, (byte) 0);
        }
        AllocationNode data2 = readData(data, j2, cryptoInfo.iv, i);
        long j3 = j2 + i;
        if (z) {
            scratch.reset(2);
            data2 = readData(data2, j3, scratch.getData(), 2);
            j3 += 2;
            unsignedShort = scratch.readUnsignedShort();
        }
        int i2 = unsignedShort;
        int[] iArr = cryptoInfo.numBytesOfClearData;
        if (iArr == null || iArr.length < i2) {
            iArr = new int[i2];
        }
        int[] iArr2 = iArr;
        int[] iArr3 = cryptoInfo.numBytesOfEncryptedData;
        if (iArr3 == null || iArr3.length < i2) {
            iArr3 = new int[i2];
        }
        int[] iArr4 = iArr3;
        if (z) {
            int i3 = i2 * 6;
            scratch.reset(i3);
            data2 = readData(data2, j3, scratch.getData(), i3);
            j3 += i3;
            scratch.setPosition(0);
            for (int i4 = 0; i4 < i2; i4++) {
                iArr2[i4] = scratch.readUnsignedShort();
                iArr4[i4] = scratch.readUnsignedIntToInt();
            }
        } else {
            iArr2[0] = 0;
            iArr4[0] = extrasHolder.size - ((int) (j3 - extrasHolder.offset));
        }
        TrackOutput.CryptoData cryptoData = (TrackOutput.CryptoData) Util.castNonNull(extrasHolder.cryptoData);
        cryptoInfo.set(i2, iArr2, iArr4, cryptoData.encryptionKey, cryptoInfo.iv, cryptoData.cryptoMode, cryptoData.encryptedBlocks, cryptoData.clearBlocks);
        int i5 = (int) (j3 - extrasHolder.offset);
        extrasHolder.offset += i5;
        extrasHolder.size -= i5;
        return data2;
    }

    private static AllocationNode readData(AllocationNode allocationNode, long absolutePosition, ByteBuffer target, int length) {
        AllocationNode nodeContainingPosition = getNodeContainingPosition(allocationNode, absolutePosition);
        while (length > 0) {
            int iMin = Math.min(length, (int) (nodeContainingPosition.endPosition - absolutePosition));
            target.put(nodeContainingPosition.allocation.data, nodeContainingPosition.translateOffset(absolutePosition), iMin);
            length -= iMin;
            absolutePosition += iMin;
            if (absolutePosition == nodeContainingPosition.endPosition) {
                nodeContainingPosition = nodeContainingPosition.next;
            }
        }
        return nodeContainingPosition;
    }

    private static AllocationNode readData(AllocationNode allocationNode, long absolutePosition, byte[] target, int length) {
        AllocationNode nodeContainingPosition = getNodeContainingPosition(allocationNode, absolutePosition);
        int i = length;
        while (i > 0) {
            int iMin = Math.min(i, (int) (nodeContainingPosition.endPosition - absolutePosition));
            System.arraycopy(nodeContainingPosition.allocation.data, nodeContainingPosition.translateOffset(absolutePosition), target, length - i, iMin);
            i -= iMin;
            absolutePosition += iMin;
            if (absolutePosition == nodeContainingPosition.endPosition) {
                nodeContainingPosition = nodeContainingPosition.next;
            }
        }
        return nodeContainingPosition;
    }

    private static AllocationNode getNodeContainingPosition(AllocationNode allocationNode, long absolutePosition) {
        while (absolutePosition >= allocationNode.endPosition) {
            allocationNode = allocationNode.next;
        }
        return allocationNode;
    }

    private static final class AllocationNode {
        public Allocation allocation;
        public final long endPosition;
        public AllocationNode next;
        public final long startPosition;
        public boolean wasInitialized;

        public AllocationNode(long startPosition, int allocationLength) {
            this.startPosition = startPosition;
            this.endPosition = startPosition + allocationLength;
        }

        public void initialize(Allocation allocation, AllocationNode next) {
            this.allocation = allocation;
            this.next = next;
            this.wasInitialized = true;
        }

        public int translateOffset(long absolutePosition) {
            return ((int) (absolutePosition - this.startPosition)) + this.allocation.offset;
        }

        public AllocationNode clear() {
            this.allocation = null;
            AllocationNode allocationNode = this.next;
            this.next = null;
            return allocationNode;
        }
    }
}
