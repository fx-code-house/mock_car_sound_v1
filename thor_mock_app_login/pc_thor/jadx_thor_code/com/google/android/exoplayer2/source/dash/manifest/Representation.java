package com.google.android.exoplayer2.source.dash.manifest;

import android.net.Uri;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.source.dash.DashSegmentIndex;
import com.google.android.exoplayer2.source.dash.manifest.SegmentBase;
import com.google.android.exoplayer2.util.Assertions;
import com.google.common.collect.ImmutableList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/* loaded from: classes.dex */
public abstract class Representation {
    public static final long REVISION_ID_DEFAULT = -1;
    public final ImmutableList<BaseUrl> baseUrls;
    public final Format format;
    public final List<Descriptor> inbandEventStreams;
    private final RangedUri initializationUri;
    public final long presentationTimeOffsetUs;
    public final long revisionId;

    public abstract String getCacheKey();

    public abstract DashSegmentIndex getIndex();

    public abstract RangedUri getIndexUri();

    public static Representation newInstance(long revisionId, Format format, List<BaseUrl> baseUrls, SegmentBase segmentBase) {
        return newInstance(revisionId, format, baseUrls, segmentBase, null);
    }

    public static Representation newInstance(long revisionId, Format format, List<BaseUrl> baseUrls, SegmentBase segmentBase, List<Descriptor> inbandEventStreams) {
        return newInstance(revisionId, format, baseUrls, segmentBase, inbandEventStreams, null);
    }

    public static Representation newInstance(long revisionId, Format format, List<BaseUrl> baseUrls, SegmentBase segmentBase, List<Descriptor> inbandEventStreams, String cacheKey) {
        if (segmentBase instanceof SegmentBase.SingleSegmentBase) {
            return new SingleSegmentRepresentation(revisionId, format, baseUrls, (SegmentBase.SingleSegmentBase) segmentBase, inbandEventStreams, cacheKey, -1L);
        }
        if (segmentBase instanceof SegmentBase.MultiSegmentBase) {
            return new MultiSegmentRepresentation(revisionId, format, baseUrls, (SegmentBase.MultiSegmentBase) segmentBase, inbandEventStreams);
        }
        throw new IllegalArgumentException("segmentBase must be of type SingleSegmentBase or MultiSegmentBase");
    }

    private Representation(long revisionId, Format format, List<BaseUrl> baseUrls, SegmentBase segmentBase, List<Descriptor> inbandEventStreams) {
        List<Descriptor> listUnmodifiableList;
        Assertions.checkArgument(!baseUrls.isEmpty());
        this.revisionId = revisionId;
        this.format = format;
        this.baseUrls = ImmutableList.copyOf((Collection) baseUrls);
        if (inbandEventStreams == null) {
            listUnmodifiableList = Collections.emptyList();
        } else {
            listUnmodifiableList = Collections.unmodifiableList(inbandEventStreams);
        }
        this.inbandEventStreams = listUnmodifiableList;
        this.initializationUri = segmentBase.getInitialization(this);
        this.presentationTimeOffsetUs = segmentBase.getPresentationTimeOffsetUs();
    }

    public RangedUri getInitializationUri() {
        return this.initializationUri;
    }

    public static class SingleSegmentRepresentation extends Representation {
        private final String cacheKey;
        public final long contentLength;
        private final RangedUri indexUri;
        private final SingleSegmentIndex segmentIndex;
        public final Uri uri;

        public static SingleSegmentRepresentation newInstance(long revisionId, Format format, String uri, long initializationStart, long initializationEnd, long indexStart, long indexEnd, List<Descriptor> inbandEventStreams, String cacheKey, long contentLength) {
            return new SingleSegmentRepresentation(revisionId, format, ImmutableList.of(new BaseUrl(uri)), new SegmentBase.SingleSegmentBase(new RangedUri(null, initializationStart, (initializationEnd - initializationStart) + 1), 1L, 0L, indexStart, (indexEnd - indexStart) + 1), inbandEventStreams, cacheKey, contentLength);
        }

        public SingleSegmentRepresentation(long revisionId, Format format, List<BaseUrl> baseUrls, SegmentBase.SingleSegmentBase segmentBase, List<Descriptor> inbandEventStreams, String cacheKey, long contentLength) {
            super(revisionId, format, baseUrls, segmentBase, inbandEventStreams);
            this.uri = Uri.parse(baseUrls.get(0).url);
            RangedUri index = segmentBase.getIndex();
            this.indexUri = index;
            this.cacheKey = cacheKey;
            this.contentLength = contentLength;
            this.segmentIndex = index != null ? null : new SingleSegmentIndex(new RangedUri(null, 0L, contentLength));
        }

        @Override // com.google.android.exoplayer2.source.dash.manifest.Representation
        public RangedUri getIndexUri() {
            return this.indexUri;
        }

        @Override // com.google.android.exoplayer2.source.dash.manifest.Representation
        public DashSegmentIndex getIndex() {
            return this.segmentIndex;
        }

        @Override // com.google.android.exoplayer2.source.dash.manifest.Representation
        public String getCacheKey() {
            return this.cacheKey;
        }
    }

    public static class MultiSegmentRepresentation extends Representation implements DashSegmentIndex {
        final SegmentBase.MultiSegmentBase segmentBase;

        @Override // com.google.android.exoplayer2.source.dash.manifest.Representation
        public String getCacheKey() {
            return null;
        }

        @Override // com.google.android.exoplayer2.source.dash.manifest.Representation
        public DashSegmentIndex getIndex() {
            return this;
        }

        @Override // com.google.android.exoplayer2.source.dash.manifest.Representation
        public RangedUri getIndexUri() {
            return null;
        }

        public MultiSegmentRepresentation(long revisionId, Format format, List<BaseUrl> baseUrls, SegmentBase.MultiSegmentBase segmentBase, List<Descriptor> inbandEventStreams) {
            super(revisionId, format, baseUrls, segmentBase, inbandEventStreams);
            this.segmentBase = segmentBase;
        }

        @Override // com.google.android.exoplayer2.source.dash.DashSegmentIndex
        public RangedUri getSegmentUrl(long segmentNum) {
            return this.segmentBase.getSegmentUrl(this, segmentNum);
        }

        @Override // com.google.android.exoplayer2.source.dash.DashSegmentIndex
        public long getSegmentNum(long timeUs, long periodDurationUs) {
            return this.segmentBase.getSegmentNum(timeUs, periodDurationUs);
        }

        @Override // com.google.android.exoplayer2.source.dash.DashSegmentIndex
        public long getTimeUs(long segmentNum) {
            return this.segmentBase.getSegmentTimeUs(segmentNum);
        }

        @Override // com.google.android.exoplayer2.source.dash.DashSegmentIndex
        public long getDurationUs(long segmentNum, long periodDurationUs) {
            return this.segmentBase.getSegmentDurationUs(segmentNum, periodDurationUs);
        }

        @Override // com.google.android.exoplayer2.source.dash.DashSegmentIndex
        public long getFirstSegmentNum() {
            return this.segmentBase.getFirstSegmentNum();
        }

        @Override // com.google.android.exoplayer2.source.dash.DashSegmentIndex
        public long getFirstAvailableSegmentNum(long periodDurationUs, long nowUnixTimeUs) {
            return this.segmentBase.getFirstAvailableSegmentNum(periodDurationUs, nowUnixTimeUs);
        }

        @Override // com.google.android.exoplayer2.source.dash.DashSegmentIndex
        public long getSegmentCount(long periodDurationUs) {
            return this.segmentBase.getSegmentCount(periodDurationUs);
        }

        @Override // com.google.android.exoplayer2.source.dash.DashSegmentIndex
        public long getAvailableSegmentCount(long periodDurationUs, long nowUnixTimeUs) {
            return this.segmentBase.getAvailableSegmentCount(periodDurationUs, nowUnixTimeUs);
        }

        @Override // com.google.android.exoplayer2.source.dash.DashSegmentIndex
        public long getNextSegmentAvailableTimeUs(long periodDurationUs, long nowUnixTimeUs) {
            return this.segmentBase.getNextSegmentAvailableTimeUs(periodDurationUs, nowUnixTimeUs);
        }

        @Override // com.google.android.exoplayer2.source.dash.DashSegmentIndex
        public boolean isExplicit() {
            return this.segmentBase.isExplicit();
        }
    }
}
