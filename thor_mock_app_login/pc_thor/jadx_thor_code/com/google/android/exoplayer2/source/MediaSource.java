package com.google.android.exoplayer2.source;

import android.os.Handler;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.drm.DrmSessionEventListener;
import com.google.android.exoplayer2.upstream.Allocator;
import com.google.android.exoplayer2.upstream.TransferListener;
import java.io.IOException;

/* loaded from: classes.dex */
public interface MediaSource {

    public interface MediaSourceCaller {
        void onSourceInfoRefreshed(MediaSource source, Timeline timeline);
    }

    void addDrmEventListener(Handler handler, DrmSessionEventListener eventListener);

    void addEventListener(Handler handler, MediaSourceEventListener eventListener);

    MediaPeriod createPeriod(MediaPeriodId id, Allocator allocator, long startPositionUs);

    void disable(MediaSourceCaller caller);

    void enable(MediaSourceCaller caller);

    default Timeline getInitialTimeline() {
        return null;
    }

    MediaItem getMediaItem();

    default boolean isSingleWindow() {
        return true;
    }

    void maybeThrowSourceInfoRefreshError() throws IOException;

    void prepareSource(MediaSourceCaller caller, TransferListener mediaTransferListener);

    void releasePeriod(MediaPeriod mediaPeriod);

    void releaseSource(MediaSourceCaller caller);

    void removeDrmEventListener(DrmSessionEventListener eventListener);

    void removeEventListener(MediaSourceEventListener eventListener);

    public static final class MediaPeriodId extends com.google.android.exoplayer2.source.MediaPeriodId {
        public MediaPeriodId(Object periodUid) {
            super(periodUid);
        }

        public MediaPeriodId(Object periodUid, long windowSequenceNumber) {
            super(periodUid, windowSequenceNumber);
        }

        public MediaPeriodId(Object periodUid, long windowSequenceNumber, int nextAdGroupIndex) {
            super(periodUid, windowSequenceNumber, nextAdGroupIndex);
        }

        public MediaPeriodId(Object periodUid, int adGroupIndex, int adIndexInAdGroup, long windowSequenceNumber) {
            super(periodUid, adGroupIndex, adIndexInAdGroup, windowSequenceNumber);
        }

        public MediaPeriodId(com.google.android.exoplayer2.source.MediaPeriodId mediaPeriodId) {
            super(mediaPeriodId);
        }

        @Override // com.google.android.exoplayer2.source.MediaPeriodId
        public MediaPeriodId copyWithPeriodUid(Object newPeriodUid) {
            return new MediaPeriodId(super.copyWithPeriodUid(newPeriodUid));
        }

        @Override // com.google.android.exoplayer2.source.MediaPeriodId
        public MediaPeriodId copyWithWindowSequenceNumber(long windowSequenceNumber) {
            return new MediaPeriodId(super.copyWithWindowSequenceNumber(windowSequenceNumber));
        }
    }
}
