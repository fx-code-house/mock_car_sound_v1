package com.google.android.exoplayer2.source;

import java.util.Arrays;
import java.util.Random;

/* loaded from: classes.dex */
public interface ShuffleOrder {
    ShuffleOrder cloneAndClear();

    ShuffleOrder cloneAndInsert(int insertionIndex, int insertionCount);

    ShuffleOrder cloneAndRemove(int indexFrom, int indexToExclusive);

    int getFirstIndex();

    int getLastIndex();

    int getLength();

    int getNextIndex(int index);

    int getPreviousIndex(int index);

    public static class DefaultShuffleOrder implements ShuffleOrder {
        private final int[] indexInShuffled;
        private final Random random;
        private final int[] shuffled;

        public DefaultShuffleOrder(int length) {
            this(length, new Random());
        }

        public DefaultShuffleOrder(int length, long randomSeed) {
            this(length, new Random(randomSeed));
        }

        public DefaultShuffleOrder(int[] shuffledIndices, long randomSeed) {
            this(Arrays.copyOf(shuffledIndices, shuffledIndices.length), new Random(randomSeed));
        }

        private DefaultShuffleOrder(int length, Random random) {
            this(createShuffledList(length, random), random);
        }

        private DefaultShuffleOrder(int[] shuffled, Random random) {
            this.shuffled = shuffled;
            this.random = random;
            this.indexInShuffled = new int[shuffled.length];
            for (int i = 0; i < shuffled.length; i++) {
                this.indexInShuffled[shuffled[i]] = i;
            }
        }

        @Override // com.google.android.exoplayer2.source.ShuffleOrder
        public int getLength() {
            return this.shuffled.length;
        }

        @Override // com.google.android.exoplayer2.source.ShuffleOrder
        public int getNextIndex(int index) {
            int i = this.indexInShuffled[index] + 1;
            int[] iArr = this.shuffled;
            if (i < iArr.length) {
                return iArr[i];
            }
            return -1;
        }

        @Override // com.google.android.exoplayer2.source.ShuffleOrder
        public int getPreviousIndex(int index) {
            int i = this.indexInShuffled[index] - 1;
            if (i >= 0) {
                return this.shuffled[i];
            }
            return -1;
        }

        @Override // com.google.android.exoplayer2.source.ShuffleOrder
        public int getLastIndex() {
            int[] iArr = this.shuffled;
            if (iArr.length > 0) {
                return iArr[iArr.length - 1];
            }
            return -1;
        }

        @Override // com.google.android.exoplayer2.source.ShuffleOrder
        public int getFirstIndex() {
            int[] iArr = this.shuffled;
            if (iArr.length > 0) {
                return iArr[0];
            }
            return -1;
        }

        @Override // com.google.android.exoplayer2.source.ShuffleOrder
        public ShuffleOrder cloneAndInsert(int insertionIndex, int insertionCount) {
            int[] iArr = new int[insertionCount];
            int[] iArr2 = new int[insertionCount];
            int i = 0;
            int i2 = 0;
            while (i2 < insertionCount) {
                iArr[i2] = this.random.nextInt(this.shuffled.length + 1);
                int i3 = i2 + 1;
                int iNextInt = this.random.nextInt(i3);
                iArr2[i2] = iArr2[iNextInt];
                iArr2[iNextInt] = i2 + insertionIndex;
                i2 = i3;
            }
            Arrays.sort(iArr);
            int[] iArr3 = new int[this.shuffled.length + insertionCount];
            int i4 = 0;
            int i5 = 0;
            while (true) {
                int[] iArr4 = this.shuffled;
                if (i < iArr4.length + insertionCount) {
                    if (i4 < insertionCount && i5 == iArr[i4]) {
                        iArr3[i] = iArr2[i4];
                        i4++;
                    } else {
                        int i6 = i5 + 1;
                        int i7 = iArr4[i5];
                        iArr3[i] = i7;
                        if (i7 >= insertionIndex) {
                            iArr3[i] = i7 + insertionCount;
                        }
                        i5 = i6;
                    }
                    i++;
                } else {
                    return new DefaultShuffleOrder(iArr3, new Random(this.random.nextLong()));
                }
            }
        }

        @Override // com.google.android.exoplayer2.source.ShuffleOrder
        public ShuffleOrder cloneAndRemove(int indexFrom, int indexToExclusive) {
            int i = indexToExclusive - indexFrom;
            int[] iArr = new int[this.shuffled.length - i];
            int i2 = 0;
            int i3 = 0;
            while (true) {
                int[] iArr2 = this.shuffled;
                if (i2 < iArr2.length) {
                    int i4 = iArr2[i2];
                    if (i4 < indexFrom || i4 >= indexToExclusive) {
                        int i5 = i2 - i3;
                        if (i4 >= indexFrom) {
                            i4 -= i;
                        }
                        iArr[i5] = i4;
                    } else {
                        i3++;
                    }
                    i2++;
                } else {
                    return new DefaultShuffleOrder(iArr, new Random(this.random.nextLong()));
                }
            }
        }

        @Override // com.google.android.exoplayer2.source.ShuffleOrder
        public ShuffleOrder cloneAndClear() {
            return new DefaultShuffleOrder(0, new Random(this.random.nextLong()));
        }

        private static int[] createShuffledList(int length, Random random) {
            int[] iArr = new int[length];
            int i = 0;
            while (i < length) {
                int i2 = i + 1;
                int iNextInt = random.nextInt(i2);
                iArr[i] = iArr[iNextInt];
                iArr[iNextInt] = i;
                i = i2;
            }
            return iArr;
        }
    }

    public static final class UnshuffledShuffleOrder implements ShuffleOrder {
        private final int length;

        @Override // com.google.android.exoplayer2.source.ShuffleOrder
        public int getPreviousIndex(int index) {
            int i = index - 1;
            if (i >= 0) {
                return i;
            }
            return -1;
        }

        public UnshuffledShuffleOrder(int length) {
            this.length = length;
        }

        @Override // com.google.android.exoplayer2.source.ShuffleOrder
        public int getLength() {
            return this.length;
        }

        @Override // com.google.android.exoplayer2.source.ShuffleOrder
        public int getNextIndex(int index) {
            int i = index + 1;
            if (i < this.length) {
                return i;
            }
            return -1;
        }

        @Override // com.google.android.exoplayer2.source.ShuffleOrder
        public int getLastIndex() {
            int i = this.length;
            if (i > 0) {
                return i - 1;
            }
            return -1;
        }

        @Override // com.google.android.exoplayer2.source.ShuffleOrder
        public int getFirstIndex() {
            return this.length > 0 ? 0 : -1;
        }

        @Override // com.google.android.exoplayer2.source.ShuffleOrder
        public ShuffleOrder cloneAndInsert(int insertionIndex, int insertionCount) {
            return new UnshuffledShuffleOrder(this.length + insertionCount);
        }

        @Override // com.google.android.exoplayer2.source.ShuffleOrder
        public ShuffleOrder cloneAndRemove(int indexFrom, int indexToExclusive) {
            return new UnshuffledShuffleOrder((this.length - indexToExclusive) + indexFrom);
        }

        @Override // com.google.android.exoplayer2.source.ShuffleOrder
        public ShuffleOrder cloneAndClear() {
            return new UnshuffledShuffleOrder(0);
        }
    }
}
