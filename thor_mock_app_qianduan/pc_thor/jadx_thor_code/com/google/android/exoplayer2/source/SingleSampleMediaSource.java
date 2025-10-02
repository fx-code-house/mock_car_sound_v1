package com.google.android.exoplayer2.source;

import android.net.Uri;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.upstream.Allocator;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.DefaultLoadErrorHandlingPolicy;
import com.google.android.exoplayer2.upstream.LoadErrorHandlingPolicy;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Assertions;
import java.util.Collections;

/* loaded from: classes.dex */
public final class SingleSampleMediaSource extends BaseMediaSource {
    private final DataSource.Factory dataSourceFactory;
    private final DataSpec dataSpec;
    private final long durationUs;
    private final Format format;
    private final LoadErrorHandlingPolicy loadErrorHandlingPolicy;
    private final MediaItem mediaItem;
    private final Timeline timeline;
    private TransferListener transferListener;
    private final boolean treatLoadErrorsAsEndOfStream;

    @Override // com.google.android.exoplayer2.source.MediaSource
    public void maybeThrowSourceInfoRefreshError() {
    }

    @Override // com.google.android.exoplayer2.source.BaseMediaSource
    protected void releaseSourceInternal() {
    }

    public static final class Factory {
        private final DataSource.Factory dataSourceFactory;
        private Object tag;
        private String trackId;
        private LoadErrorHandlingPolicy loadErrorHandlingPolicy = new DefaultLoadErrorHandlingPolicy();
        private boolean treatLoadErrorsAsEndOfStream = true;

        public Factory(DataSource.Factory dataSourceFactory) {
            this.dataSourceFactory = (DataSource.Factory) Assertions.checkNotNull(dataSourceFactory);
        }

        public Factory setTag(Object tag) {
            this.tag = tag;
            return this;
        }

        public Factory setTrackId(String trackId) {
            this.trackId = trackId;
            return this;
        }

        public Factory setLoadErrorHandlingPolicy(LoadErrorHandlingPolicy loadErrorHandlingPolicy) {
            if (loadErrorHandlingPolicy == null) {
                loadErrorHandlingPolicy = new DefaultLoadErrorHandlingPolicy();
            }
            this.loadErrorHandlingPolicy = loadErrorHandlingPolicy;
            return this;
        }

        public Factory setTreatLoadErrorsAsEndOfStream(boolean treatLoadErrorsAsEndOfStream) {
            this.treatLoadErrorsAsEndOfStream = treatLoadErrorsAsEndOfStream;
            return this;
        }

        public SingleSampleMediaSource createMediaSource(MediaItem.Subtitle subtitle, long durationUs) {
            return new SingleSampleMediaSource(this.trackId, subtitle, this.dataSourceFactory, durationUs, this.loadErrorHandlingPolicy, this.treatLoadErrorsAsEndOfStream, this.tag);
        }

        @Deprecated
        public SingleSampleMediaSource createMediaSource(Uri uri, Format format, long durationUs) {
            return new SingleSampleMediaSource(format.id == null ? this.trackId : format.id, new MediaItem.Subtitle(uri, (String) Assertions.checkNotNull(format.sampleMimeType), format.language, format.selectionFlags), this.dataSourceFactory, durationUs, this.loadErrorHandlingPolicy, this.treatLoadErrorsAsEndOfStream, this.tag);
        }
    }

    private SingleSampleMediaSource(String trackId, MediaItem.Subtitle subtitle, DataSource.Factory dataSourceFactory, long durationUs, LoadErrorHandlingPolicy loadErrorHandlingPolicy, boolean treatLoadErrorsAsEndOfStream, Object tag) {
        this.dataSourceFactory = dataSourceFactory;
        this.durationUs = durationUs;
        this.loadErrorHandlingPolicy = loadErrorHandlingPolicy;
        this.treatLoadErrorsAsEndOfStream = treatLoadErrorsAsEndOfStream;
        MediaItem mediaItemBuild = new MediaItem.Builder().setUri(Uri.EMPTY).setMediaId(subtitle.uri.toString()).setSubtitles(Collections.singletonList(subtitle)).setTag(tag).build();
        this.mediaItem = mediaItemBuild;
        this.format = new Format.Builder().setId(trackId).setSampleMimeType(subtitle.mimeType).setLanguage(subtitle.language).setSelectionFlags(subtitle.selectionFlags).setRoleFlags(subtitle.roleFlags).setLabel(subtitle.label).build();
        this.dataSpec = new DataSpec.Builder().setUri(subtitle.uri).setFlags(1).build();
        this.timeline = new SinglePeriodTimeline(durationUs, true, false, false, (Object) null, mediaItemBuild);
    }

    @Override // com.google.android.exoplayer2.source.MediaSource
    public MediaItem getMediaItem() {
        return this.mediaItem;
    }

    @Override // com.google.android.exoplayer2.source.BaseMediaSource
    protected void prepareSourceInternal(TransferListener mediaTransferListener) {
        this.transferListener = mediaTransferListener;
        refreshSourceInfo(this.timeline);
    }

    @Override // com.google.android.exoplayer2.source.MediaSource
    public MediaPeriod createPeriod(MediaSource.MediaPeriodId id, Allocator allocator, long startPositionUs) {
        return new SingleSampleMediaPeriod(this.dataSpec, this.dataSourceFactory, this.transferListener, this.format, this.durationUs, this.loadErrorHandlingPolicy, createEventDispatcher(id), this.treatLoadErrorsAsEndOfStream);
    }

    @Override // com.google.android.exoplayer2.source.MediaSource
    public void releasePeriod(MediaPeriod mediaPeriod) {
        ((SingleSampleMediaPeriod) mediaPeriod).release();
    }
}
