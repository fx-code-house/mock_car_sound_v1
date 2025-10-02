package com.google.android.exoplayer2.util;

import java.util.Arrays;

/* loaded from: classes.dex */
public final class TimedValueQueue<V> {
    private static final int INITIAL_BUFFER_SIZE = 10;
    private int first;
    private int size;
    private long[] timestamps;
    private V[] values;

    public TimedValueQueue() {
        this(10);
    }

    public TimedValueQueue(int i) {
        this.timestamps = new long[i];
        this.values = (V[]) newArray(i);
    }

    public synchronized void add(long timestamp, V value) {
        clearBufferOnTimeDiscontinuity(timestamp);
        doubleCapacityIfFull();
        addUnchecked(timestamp, value);
    }

    public synchronized void clear() {
        this.first = 0;
        this.size = 0;
        Arrays.fill(this.values, (Object) null);
    }

    public synchronized int size() {
        return this.size;
    }

    public synchronized V pollFirst() {
        return this.size == 0 ? null : popFirst();
    }

    public synchronized V pollFloor(long timestamp) {
        return poll(timestamp, true);
    }

    public synchronized V poll(long timestamp) {
        return poll(timestamp, false);
    }

    private V poll(long timestamp, boolean onlyOlder) {
        V vPopFirst = null;
        long j = Long.MAX_VALUE;
        while (this.size > 0) {
            long j2 = timestamp - this.timestamps[this.first];
            if (j2 < 0 && (onlyOlder || (-j2) >= j)) {
                break;
            }
            vPopFirst = popFirst();
            j = j2;
        }
        return vPopFirst;
    }

    private V popFirst() {
        Assertions.checkState(this.size > 0);
        V[] vArr = this.values;
        int i = this.first;
        V v = vArr[i];
        vArr[i] = null;
        this.first = (i + 1) % vArr.length;
        this.size--;
        return v;
    }

    private void clearBufferOnTimeDiscontinuity(long timestamp) {
        if (this.size > 0) {
            if (timestamp <= this.timestamps[((this.first + r0) - 1) % this.values.length]) {
                clear();
            }
        }
    }

    private void doubleCapacityIfFull() {
        int length = this.values.length;
        if (this.size < length) {
            return;
        }
        int i = length * 2;
        long[] jArr = new long[i];
        V[] vArr = (V[]) newArray(i);
        int i2 = this.first;
        int i3 = length - i2;
        System.arraycopy(this.timestamps, i2, jArr, 0, i3);
        System.arraycopy(this.values, this.first, vArr, 0, i3);
        int i4 = this.first;
        if (i4 > 0) {
            System.arraycopy(this.timestamps, 0, jArr, i3, i4);
            System.arraycopy(this.values, 0, vArr, i3, this.first);
        }
        this.timestamps = jArr;
        this.values = vArr;
        this.first = 0;
    }

    private void addUnchecked(long timestamp, V value) {
        int i = this.first;
        int i2 = this.size;
        V[] vArr = this.values;
        int length = (i + i2) % vArr.length;
        this.timestamps[length] = timestamp;
        vArr[length] = value;
        this.size = i2 + 1;
    }

    private static <V> V[] newArray(int i) {
        return (V[]) new Object[i];
    }
}
