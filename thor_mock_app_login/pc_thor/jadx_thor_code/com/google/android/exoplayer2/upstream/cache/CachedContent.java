package com.google.android.exoplayer2.upstream.cache;

import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import java.io.File;
import java.util.ArrayList;
import java.util.TreeSet;

/* loaded from: classes.dex */
final class CachedContent {
    private static final String TAG = "CachedContent";
    private final TreeSet<SimpleCacheSpan> cachedSpans;
    public final int id;
    public final String key;
    private final ArrayList<Range> lockedRanges;
    private DefaultContentMetadata metadata;

    public CachedContent(int id, String key) {
        this(id, key, DefaultContentMetadata.EMPTY);
    }

    public CachedContent(int id, String key, DefaultContentMetadata metadata) {
        this.id = id;
        this.key = key;
        this.metadata = metadata;
        this.cachedSpans = new TreeSet<>();
        this.lockedRanges = new ArrayList<>();
    }

    public DefaultContentMetadata getMetadata() {
        return this.metadata;
    }

    public boolean applyMetadataMutations(ContentMetadataMutations mutations) {
        this.metadata = this.metadata.copyWithMutationsApplied(mutations);
        return !r2.equals(r0);
    }

    public boolean isFullyUnlocked() {
        return this.lockedRanges.isEmpty();
    }

    public boolean isFullyLocked(long position, long length) {
        for (int i = 0; i < this.lockedRanges.size(); i++) {
            if (this.lockedRanges.get(i).contains(position, length)) {
                return true;
            }
        }
        return false;
    }

    public boolean lockRange(long position, long length) {
        for (int i = 0; i < this.lockedRanges.size(); i++) {
            if (this.lockedRanges.get(i).intersects(position, length)) {
                return false;
            }
        }
        this.lockedRanges.add(new Range(position, length));
        return true;
    }

    public void unlockRange(long position) {
        for (int i = 0; i < this.lockedRanges.size(); i++) {
            if (this.lockedRanges.get(i).position == position) {
                this.lockedRanges.remove(i);
                return;
            }
        }
        throw new IllegalStateException();
    }

    public void addSpan(SimpleCacheSpan span) {
        this.cachedSpans.add(span);
    }

    public TreeSet<SimpleCacheSpan> getSpans() {
        return this.cachedSpans;
    }

    public SimpleCacheSpan getSpan(long position, long length) {
        SimpleCacheSpan simpleCacheSpanCreateLookup = SimpleCacheSpan.createLookup(this.key, position);
        SimpleCacheSpan simpleCacheSpanFloor = this.cachedSpans.floor(simpleCacheSpanCreateLookup);
        if (simpleCacheSpanFloor != null && simpleCacheSpanFloor.position + simpleCacheSpanFloor.length > position) {
            return simpleCacheSpanFloor;
        }
        SimpleCacheSpan simpleCacheSpanCeiling = this.cachedSpans.ceiling(simpleCacheSpanCreateLookup);
        if (simpleCacheSpanCeiling != null) {
            long j = simpleCacheSpanCeiling.position - position;
            length = length == -1 ? j : Math.min(j, length);
        }
        return SimpleCacheSpan.createHole(this.key, position, length);
    }

    public long getCachedBytesLength(long position, long length) {
        Assertions.checkArgument(position >= 0);
        Assertions.checkArgument(length >= 0);
        SimpleCacheSpan span = getSpan(position, length);
        if (span.isHoleSpan()) {
            return -Math.min(span.isOpenEnded() ? Long.MAX_VALUE : span.length, length);
        }
        long j = position + length;
        long j2 = j >= 0 ? j : Long.MAX_VALUE;
        long jMax = span.position + span.length;
        if (jMax < j2) {
            for (SimpleCacheSpan simpleCacheSpan : this.cachedSpans.tailSet(span, false)) {
                if (simpleCacheSpan.position > jMax) {
                    break;
                }
                jMax = Math.max(jMax, simpleCacheSpan.position + simpleCacheSpan.length);
                if (jMax >= j2) {
                    break;
                }
            }
        }
        return Math.min(jMax - position, length);
    }

    public SimpleCacheSpan setLastTouchTimestamp(SimpleCacheSpan cacheSpan, long lastTouchTimestamp, boolean updateFile) {
        Assertions.checkState(this.cachedSpans.remove(cacheSpan));
        File file = (File) Assertions.checkNotNull(cacheSpan.file);
        if (updateFile) {
            File cacheFile = SimpleCacheSpan.getCacheFile((File) Assertions.checkNotNull(file.getParentFile()), this.id, cacheSpan.position, lastTouchTimestamp);
            if (file.renameTo(cacheFile)) {
                file = cacheFile;
            } else {
                String strValueOf = String.valueOf(file);
                String strValueOf2 = String.valueOf(cacheFile);
                Log.w(TAG, new StringBuilder(String.valueOf(strValueOf).length() + 21 + String.valueOf(strValueOf2).length()).append("Failed to rename ").append(strValueOf).append(" to ").append(strValueOf2).toString());
            }
        }
        SimpleCacheSpan simpleCacheSpanCopyWithFileAndLastTouchTimestamp = cacheSpan.copyWithFileAndLastTouchTimestamp(file, lastTouchTimestamp);
        this.cachedSpans.add(simpleCacheSpanCopyWithFileAndLastTouchTimestamp);
        return simpleCacheSpanCopyWithFileAndLastTouchTimestamp;
    }

    public boolean isEmpty() {
        return this.cachedSpans.isEmpty();
    }

    public boolean removeSpan(CacheSpan span) {
        if (!this.cachedSpans.remove(span)) {
            return false;
        }
        if (span.file == null) {
            return true;
        }
        span.file.delete();
        return true;
    }

    public int hashCode() {
        return (((this.id * 31) + this.key.hashCode()) * 31) + this.metadata.hashCode();
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CachedContent cachedContent = (CachedContent) o;
        return this.id == cachedContent.id && this.key.equals(cachedContent.key) && this.cachedSpans.equals(cachedContent.cachedSpans) && this.metadata.equals(cachedContent.metadata);
    }

    private static final class Range {
        public final long length;
        public final long position;

        public Range(long position, long length) {
            this.position = position;
            this.length = length;
        }

        public boolean contains(long otherPosition, long otherLength) {
            long j = this.length;
            if (j == -1) {
                return otherPosition >= this.position;
            }
            if (otherLength == -1) {
                return false;
            }
            long j2 = this.position;
            return j2 <= otherPosition && otherPosition + otherLength <= j2 + j;
        }

        public boolean intersects(long otherPosition, long otherLength) {
            long j = this.position;
            if (j > otherPosition) {
                return otherLength == -1 || otherPosition + otherLength > j;
            }
            long j2 = this.length;
            return j2 == -1 || j + j2 > otherPosition;
        }
    }
}
