package com.google.android.exoplayer2.upstream.cache;

import com.google.android.exoplayer2.extractor.ChunkIndex;
import com.google.android.exoplayer2.upstream.cache.Cache;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.Util;
import java.util.Arrays;
import java.util.Iterator;
import java.util.TreeSet;

/* loaded from: classes.dex */
public final class CachedRegionTracker implements Cache.Listener {
    public static final int CACHED_TO_END = -2;
    public static final int NOT_CACHED = -1;
    private static final String TAG = "CachedRegionTracker";
    private final Cache cache;
    private final String cacheKey;
    private final ChunkIndex chunkIndex;
    private final TreeSet<Region> regions = new TreeSet<>();
    private final Region lookupRegion = new Region(0, 0);

    @Override // com.google.android.exoplayer2.upstream.cache.Cache.Listener
    public void onSpanTouched(Cache cache, CacheSpan oldSpan, CacheSpan newSpan) {
    }

    public CachedRegionTracker(Cache cache, String cacheKey, ChunkIndex chunkIndex) {
        this.cache = cache;
        this.cacheKey = cacheKey;
        this.chunkIndex = chunkIndex;
        synchronized (this) {
            Iterator<CacheSpan> itDescendingIterator = cache.addListener(cacheKey, this).descendingIterator();
            while (itDescendingIterator.hasNext()) {
                mergeSpan(itDescendingIterator.next());
            }
        }
    }

    public void release() {
        this.cache.removeListener(this.cacheKey, this);
    }

    public synchronized int getRegionEndTimeMs(long byteOffset) {
        this.lookupRegion.startOffset = byteOffset;
        Region regionFloor = this.regions.floor(this.lookupRegion);
        if (regionFloor != null && byteOffset <= regionFloor.endOffset && regionFloor.endOffsetIndex != -1) {
            int i = regionFloor.endOffsetIndex;
            if (i == this.chunkIndex.length - 1) {
                if (regionFloor.endOffset == this.chunkIndex.offsets[i] + this.chunkIndex.sizes[i]) {
                    return -2;
                }
            }
            return (int) ((this.chunkIndex.timesUs[i] + ((this.chunkIndex.durationsUs[i] * (regionFloor.endOffset - this.chunkIndex.offsets[i])) / this.chunkIndex.sizes[i])) / 1000);
        }
        return -1;
    }

    @Override // com.google.android.exoplayer2.upstream.cache.Cache.Listener
    public synchronized void onSpanAdded(Cache cache, CacheSpan span) {
        mergeSpan(span);
    }

    @Override // com.google.android.exoplayer2.upstream.cache.Cache.Listener
    public synchronized void onSpanRemoved(Cache cache, CacheSpan span) {
        Region region = new Region(span.position, span.position + span.length);
        Region regionFloor = this.regions.floor(region);
        if (regionFloor == null) {
            Log.e(TAG, "Removed a span we were not aware of");
            return;
        }
        this.regions.remove(regionFloor);
        if (regionFloor.startOffset < region.startOffset) {
            Region region2 = new Region(regionFloor.startOffset, region.startOffset);
            int iBinarySearch = Arrays.binarySearch(this.chunkIndex.offsets, region2.endOffset);
            if (iBinarySearch < 0) {
                iBinarySearch = (-iBinarySearch) - 2;
            }
            region2.endOffsetIndex = iBinarySearch;
            this.regions.add(region2);
        }
        if (regionFloor.endOffset > region.endOffset) {
            Region region3 = new Region(region.endOffset + 1, regionFloor.endOffset);
            region3.endOffsetIndex = regionFloor.endOffsetIndex;
            this.regions.add(region3);
        }
    }

    private void mergeSpan(CacheSpan span) {
        Region region = new Region(span.position, span.position + span.length);
        Region regionFloor = this.regions.floor(region);
        Region regionCeiling = this.regions.ceiling(region);
        boolean zRegionsConnect = regionsConnect(regionFloor, region);
        if (regionsConnect(region, regionCeiling)) {
            if (zRegionsConnect) {
                regionFloor.endOffset = regionCeiling.endOffset;
                regionFloor.endOffsetIndex = regionCeiling.endOffsetIndex;
            } else {
                region.endOffset = regionCeiling.endOffset;
                region.endOffsetIndex = regionCeiling.endOffsetIndex;
                this.regions.add(region);
            }
            this.regions.remove(regionCeiling);
            return;
        }
        if (zRegionsConnect) {
            regionFloor.endOffset = region.endOffset;
            int i = regionFloor.endOffsetIndex;
            while (i < this.chunkIndex.length - 1) {
                int i2 = i + 1;
                if (this.chunkIndex.offsets[i2] > regionFloor.endOffset) {
                    break;
                } else {
                    i = i2;
                }
            }
            regionFloor.endOffsetIndex = i;
            return;
        }
        int iBinarySearch = Arrays.binarySearch(this.chunkIndex.offsets, region.endOffset);
        if (iBinarySearch < 0) {
            iBinarySearch = (-iBinarySearch) - 2;
        }
        region.endOffsetIndex = iBinarySearch;
        this.regions.add(region);
    }

    private boolean regionsConnect(Region lower, Region upper) {
        return (lower == null || upper == null || lower.endOffset != upper.startOffset) ? false : true;
    }

    private static class Region implements Comparable<Region> {
        public long endOffset;
        public int endOffsetIndex;
        public long startOffset;

        public Region(long position, long endOffset) {
            this.startOffset = position;
            this.endOffset = endOffset;
        }

        @Override // java.lang.Comparable
        public int compareTo(Region another) {
            return Util.compareLong(this.startOffset, another.startOffset);
        }
    }
}
