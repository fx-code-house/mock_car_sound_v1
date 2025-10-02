package com.google.android.exoplayer2.upstream;

import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import java.util.Arrays;

/* loaded from: classes.dex */
public final class DefaultAllocator implements Allocator {
    private static final int AVAILABLE_EXTRA_CAPACITY = 100;
    private int allocatedCount;
    private Allocation[] availableAllocations;
    private int availableCount;
    private final int individualAllocationSize;
    private final byte[] initialAllocationBlock;
    private final Allocation[] singleAllocationReleaseHolder;
    private int targetBufferSize;
    private final boolean trimOnReset;

    public DefaultAllocator(boolean trimOnReset, int individualAllocationSize) {
        this(trimOnReset, individualAllocationSize, 0);
    }

    public DefaultAllocator(boolean trimOnReset, int individualAllocationSize, int initialAllocationCount) {
        Assertions.checkArgument(individualAllocationSize > 0);
        Assertions.checkArgument(initialAllocationCount >= 0);
        this.trimOnReset = trimOnReset;
        this.individualAllocationSize = individualAllocationSize;
        this.availableCount = initialAllocationCount;
        this.availableAllocations = new Allocation[initialAllocationCount + 100];
        if (initialAllocationCount > 0) {
            this.initialAllocationBlock = new byte[initialAllocationCount * individualAllocationSize];
            for (int i = 0; i < initialAllocationCount; i++) {
                this.availableAllocations[i] = new Allocation(this.initialAllocationBlock, i * individualAllocationSize);
            }
        } else {
            this.initialAllocationBlock = null;
        }
        this.singleAllocationReleaseHolder = new Allocation[1];
    }

    public synchronized void reset() {
        if (this.trimOnReset) {
            setTargetBufferSize(0);
        }
    }

    public synchronized void setTargetBufferSize(int targetBufferSize) {
        boolean z = targetBufferSize < this.targetBufferSize;
        this.targetBufferSize = targetBufferSize;
        if (z) {
            trim();
        }
    }

    @Override // com.google.android.exoplayer2.upstream.Allocator
    public synchronized Allocation allocate() {
        Allocation allocation;
        this.allocatedCount++;
        int i = this.availableCount;
        if (i > 0) {
            Allocation[] allocationArr = this.availableAllocations;
            int i2 = i - 1;
            this.availableCount = i2;
            allocation = (Allocation) Assertions.checkNotNull(allocationArr[i2]);
            this.availableAllocations[this.availableCount] = null;
        } else {
            allocation = new Allocation(new byte[this.individualAllocationSize], 0);
        }
        return allocation;
    }

    @Override // com.google.android.exoplayer2.upstream.Allocator
    public synchronized void release(Allocation allocation) {
        Allocation[] allocationArr = this.singleAllocationReleaseHolder;
        allocationArr[0] = allocation;
        release(allocationArr);
    }

    @Override // com.google.android.exoplayer2.upstream.Allocator
    public synchronized void release(Allocation[] allocations) {
        int i = this.availableCount;
        int length = allocations.length + i;
        Allocation[] allocationArr = this.availableAllocations;
        if (length >= allocationArr.length) {
            this.availableAllocations = (Allocation[]) Arrays.copyOf(allocationArr, Math.max(allocationArr.length * 2, i + allocations.length));
        }
        for (Allocation allocation : allocations) {
            Allocation[] allocationArr2 = this.availableAllocations;
            int i2 = this.availableCount;
            this.availableCount = i2 + 1;
            allocationArr2[i2] = allocation;
        }
        this.allocatedCount -= allocations.length;
        notifyAll();
    }

    @Override // com.google.android.exoplayer2.upstream.Allocator
    public synchronized void trim() {
        int i = 0;
        int iMax = Math.max(0, Util.ceilDivide(this.targetBufferSize, this.individualAllocationSize) - this.allocatedCount);
        int i2 = this.availableCount;
        if (iMax >= i2) {
            return;
        }
        if (this.initialAllocationBlock != null) {
            int i3 = i2 - 1;
            while (i <= i3) {
                Allocation allocation = (Allocation) Assertions.checkNotNull(this.availableAllocations[i]);
                if (allocation.data == this.initialAllocationBlock) {
                    i++;
                } else {
                    Allocation allocation2 = (Allocation) Assertions.checkNotNull(this.availableAllocations[i3]);
                    if (allocation2.data != this.initialAllocationBlock) {
                        i3--;
                    } else {
                        Allocation[] allocationArr = this.availableAllocations;
                        allocationArr[i] = allocation2;
                        allocationArr[i3] = allocation;
                        i3--;
                        i++;
                    }
                }
            }
            iMax = Math.max(iMax, i);
            if (iMax >= this.availableCount) {
                return;
            }
        }
        Arrays.fill(this.availableAllocations, iMax, this.availableCount, (Object) null);
        this.availableCount = iMax;
    }

    @Override // com.google.android.exoplayer2.upstream.Allocator
    public synchronized int getTotalBytesAllocated() {
        return this.allocatedCount * this.individualAllocationSize;
    }

    @Override // com.google.android.exoplayer2.upstream.Allocator
    public int getIndividualAllocationLength() {
        return this.individualAllocationSize;
    }
}
