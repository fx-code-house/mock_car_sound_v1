package com.google.android.exoplayer2.source;

import android.os.Handler;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.drm.DrmSessionEventListener;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.MediaSourceEventListener;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

/* loaded from: classes.dex */
public abstract class CompositeMediaSource<T> extends BaseMediaSource {
    private final HashMap<T, MediaSourceAndListener<T>> childSources = new HashMap<>();
    private Handler eventHandler;
    private TransferListener mediaTransferListener;

    protected MediaSource.MediaPeriodId getMediaPeriodIdForChildMediaPeriodId(T id, MediaSource.MediaPeriodId mediaPeriodId) {
        return mediaPeriodId;
    }

    protected long getMediaTimeForChildMediaTime(T id, long mediaTimeMs) {
        return mediaTimeMs;
    }

    protected int getWindowIndexForChildWindowIndex(T id, int windowIndex) {
        return windowIndex;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* renamed from: onChildSourceInfoRefreshed, reason: merged with bridge method [inline-methods] */
    public abstract void m172x365769cd(T id, MediaSource mediaSource, Timeline timeline);

    protected CompositeMediaSource() {
    }

    @Override // com.google.android.exoplayer2.source.BaseMediaSource
    protected void prepareSourceInternal(TransferListener mediaTransferListener) {
        this.mediaTransferListener = mediaTransferListener;
        this.eventHandler = Util.createHandlerForCurrentLooper();
    }

    @Override // com.google.android.exoplayer2.source.MediaSource
    public void maybeThrowSourceInfoRefreshError() throws IOException {
        Iterator<MediaSourceAndListener<T>> it = this.childSources.values().iterator();
        while (it.hasNext()) {
            it.next().mediaSource.maybeThrowSourceInfoRefreshError();
        }
    }

    @Override // com.google.android.exoplayer2.source.BaseMediaSource
    protected void enableInternal() {
        for (MediaSourceAndListener<T> mediaSourceAndListener : this.childSources.values()) {
            mediaSourceAndListener.mediaSource.enable(mediaSourceAndListener.caller);
        }
    }

    @Override // com.google.android.exoplayer2.source.BaseMediaSource
    protected void disableInternal() {
        for (MediaSourceAndListener<T> mediaSourceAndListener : this.childSources.values()) {
            mediaSourceAndListener.mediaSource.disable(mediaSourceAndListener.caller);
        }
    }

    @Override // com.google.android.exoplayer2.source.BaseMediaSource
    protected void releaseSourceInternal() {
        for (MediaSourceAndListener<T> mediaSourceAndListener : this.childSources.values()) {
            mediaSourceAndListener.mediaSource.releaseSource(mediaSourceAndListener.caller);
            mediaSourceAndListener.mediaSource.removeEventListener(mediaSourceAndListener.eventListener);
            mediaSourceAndListener.mediaSource.removeDrmEventListener(mediaSourceAndListener.eventListener);
        }
        this.childSources.clear();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final void prepareChildSource(final T id, MediaSource mediaSource) {
        Assertions.checkArgument(!this.childSources.containsKey(id));
        MediaSource.MediaSourceCaller mediaSourceCaller = new MediaSource.MediaSourceCaller() { // from class: com.google.android.exoplayer2.source.CompositeMediaSource$$ExternalSyntheticLambda0
            @Override // com.google.android.exoplayer2.source.MediaSource.MediaSourceCaller
            public final void onSourceInfoRefreshed(MediaSource mediaSource2, Timeline timeline) {
                this.f$0.m172x365769cd(id, mediaSource2, timeline);
            }
        };
        ForwardingEventListener forwardingEventListener = new ForwardingEventListener(id);
        this.childSources.put(id, new MediaSourceAndListener<>(mediaSource, mediaSourceCaller, forwardingEventListener));
        mediaSource.addEventListener((Handler) Assertions.checkNotNull(this.eventHandler), forwardingEventListener);
        mediaSource.addDrmEventListener((Handler) Assertions.checkNotNull(this.eventHandler), forwardingEventListener);
        mediaSource.prepareSource(mediaSourceCaller, this.mediaTransferListener);
        if (isEnabled()) {
            return;
        }
        mediaSource.disable(mediaSourceCaller);
    }

    protected final void enableChildSource(T id) {
        MediaSourceAndListener mediaSourceAndListener = (MediaSourceAndListener) Assertions.checkNotNull(this.childSources.get(id));
        mediaSourceAndListener.mediaSource.enable(mediaSourceAndListener.caller);
    }

    protected final void disableChildSource(T id) {
        MediaSourceAndListener mediaSourceAndListener = (MediaSourceAndListener) Assertions.checkNotNull(this.childSources.get(id));
        mediaSourceAndListener.mediaSource.disable(mediaSourceAndListener.caller);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final void releaseChildSource(T id) {
        MediaSourceAndListener mediaSourceAndListener = (MediaSourceAndListener) Assertions.checkNotNull(this.childSources.remove(id));
        mediaSourceAndListener.mediaSource.releaseSource(mediaSourceAndListener.caller);
        mediaSourceAndListener.mediaSource.removeEventListener(mediaSourceAndListener.eventListener);
        mediaSourceAndListener.mediaSource.removeDrmEventListener(mediaSourceAndListener.eventListener);
    }

    private static final class MediaSourceAndListener<T> {
        public final MediaSource.MediaSourceCaller caller;
        public final CompositeMediaSource<T>.ForwardingEventListener eventListener;
        public final MediaSource mediaSource;

        public MediaSourceAndListener(MediaSource mediaSource, MediaSource.MediaSourceCaller caller, CompositeMediaSource<T>.ForwardingEventListener eventListener) {
            this.mediaSource = mediaSource;
            this.caller = caller;
            this.eventListener = eventListener;
        }
    }

    private final class ForwardingEventListener implements MediaSourceEventListener, DrmSessionEventListener {
        private DrmSessionEventListener.EventDispatcher drmEventDispatcher;
        private final T id;
        private MediaSourceEventListener.EventDispatcher mediaSourceEventDispatcher;

        public ForwardingEventListener(T id) {
            this.mediaSourceEventDispatcher = CompositeMediaSource.this.createEventDispatcher(null);
            this.drmEventDispatcher = CompositeMediaSource.this.createDrmEventDispatcher(null);
            this.id = id;
        }

        @Override // com.google.android.exoplayer2.source.MediaSourceEventListener
        public void onLoadStarted(int windowIndex, MediaSource.MediaPeriodId mediaPeriodId, LoadEventInfo loadEventData, MediaLoadData mediaLoadData) {
            if (maybeUpdateEventDispatcher(windowIndex, mediaPeriodId)) {
                this.mediaSourceEventDispatcher.loadStarted(loadEventData, maybeUpdateMediaLoadData(mediaLoadData));
            }
        }

        @Override // com.google.android.exoplayer2.source.MediaSourceEventListener
        public void onLoadCompleted(int windowIndex, MediaSource.MediaPeriodId mediaPeriodId, LoadEventInfo loadEventData, MediaLoadData mediaLoadData) {
            if (maybeUpdateEventDispatcher(windowIndex, mediaPeriodId)) {
                this.mediaSourceEventDispatcher.loadCompleted(loadEventData, maybeUpdateMediaLoadData(mediaLoadData));
            }
        }

        @Override // com.google.android.exoplayer2.source.MediaSourceEventListener
        public void onLoadCanceled(int windowIndex, MediaSource.MediaPeriodId mediaPeriodId, LoadEventInfo loadEventData, MediaLoadData mediaLoadData) {
            if (maybeUpdateEventDispatcher(windowIndex, mediaPeriodId)) {
                this.mediaSourceEventDispatcher.loadCanceled(loadEventData, maybeUpdateMediaLoadData(mediaLoadData));
            }
        }

        @Override // com.google.android.exoplayer2.source.MediaSourceEventListener
        public void onLoadError(int windowIndex, MediaSource.MediaPeriodId mediaPeriodId, LoadEventInfo loadEventData, MediaLoadData mediaLoadData, IOException error, boolean wasCanceled) {
            if (maybeUpdateEventDispatcher(windowIndex, mediaPeriodId)) {
                this.mediaSourceEventDispatcher.loadError(loadEventData, maybeUpdateMediaLoadData(mediaLoadData), error, wasCanceled);
            }
        }

        @Override // com.google.android.exoplayer2.source.MediaSourceEventListener
        public void onUpstreamDiscarded(int windowIndex, MediaSource.MediaPeriodId mediaPeriodId, MediaLoadData mediaLoadData) {
            if (maybeUpdateEventDispatcher(windowIndex, mediaPeriodId)) {
                this.mediaSourceEventDispatcher.upstreamDiscarded(maybeUpdateMediaLoadData(mediaLoadData));
            }
        }

        @Override // com.google.android.exoplayer2.source.MediaSourceEventListener
        public void onDownstreamFormatChanged(int windowIndex, MediaSource.MediaPeriodId mediaPeriodId, MediaLoadData mediaLoadData) {
            if (maybeUpdateEventDispatcher(windowIndex, mediaPeriodId)) {
                this.mediaSourceEventDispatcher.downstreamFormatChanged(maybeUpdateMediaLoadData(mediaLoadData));
            }
        }

        @Override // com.google.android.exoplayer2.drm.DrmSessionEventListener
        public void onDrmSessionAcquired(int windowIndex, MediaSource.MediaPeriodId mediaPeriodId, int state) {
            if (maybeUpdateEventDispatcher(windowIndex, mediaPeriodId)) {
                this.drmEventDispatcher.drmSessionAcquired(state);
            }
        }

        @Override // com.google.android.exoplayer2.drm.DrmSessionEventListener
        public void onDrmKeysLoaded(int windowIndex, MediaSource.MediaPeriodId mediaPeriodId) {
            if (maybeUpdateEventDispatcher(windowIndex, mediaPeriodId)) {
                this.drmEventDispatcher.drmKeysLoaded();
            }
        }

        @Override // com.google.android.exoplayer2.drm.DrmSessionEventListener
        public void onDrmSessionManagerError(int windowIndex, MediaSource.MediaPeriodId mediaPeriodId, Exception error) {
            if (maybeUpdateEventDispatcher(windowIndex, mediaPeriodId)) {
                this.drmEventDispatcher.drmSessionManagerError(error);
            }
        }

        @Override // com.google.android.exoplayer2.drm.DrmSessionEventListener
        public void onDrmKeysRestored(int windowIndex, MediaSource.MediaPeriodId mediaPeriodId) {
            if (maybeUpdateEventDispatcher(windowIndex, mediaPeriodId)) {
                this.drmEventDispatcher.drmKeysRestored();
            }
        }

        @Override // com.google.android.exoplayer2.drm.DrmSessionEventListener
        public void onDrmKeysRemoved(int windowIndex, MediaSource.MediaPeriodId mediaPeriodId) {
            if (maybeUpdateEventDispatcher(windowIndex, mediaPeriodId)) {
                this.drmEventDispatcher.drmKeysRemoved();
            }
        }

        @Override // com.google.android.exoplayer2.drm.DrmSessionEventListener
        public void onDrmSessionReleased(int windowIndex, MediaSource.MediaPeriodId mediaPeriodId) {
            if (maybeUpdateEventDispatcher(windowIndex, mediaPeriodId)) {
                this.drmEventDispatcher.drmSessionReleased();
            }
        }

        private boolean maybeUpdateEventDispatcher(int childWindowIndex, MediaSource.MediaPeriodId childMediaPeriodId) {
            MediaSource.MediaPeriodId mediaPeriodIdForChildMediaPeriodId;
            if (childMediaPeriodId != null) {
                mediaPeriodIdForChildMediaPeriodId = CompositeMediaSource.this.getMediaPeriodIdForChildMediaPeriodId(this.id, childMediaPeriodId);
                if (mediaPeriodIdForChildMediaPeriodId == null) {
                    return false;
                }
            } else {
                mediaPeriodIdForChildMediaPeriodId = null;
            }
            int windowIndexForChildWindowIndex = CompositeMediaSource.this.getWindowIndexForChildWindowIndex(this.id, childWindowIndex);
            if (this.mediaSourceEventDispatcher.windowIndex != windowIndexForChildWindowIndex || !Util.areEqual(this.mediaSourceEventDispatcher.mediaPeriodId, mediaPeriodIdForChildMediaPeriodId)) {
                this.mediaSourceEventDispatcher = CompositeMediaSource.this.createEventDispatcher(windowIndexForChildWindowIndex, mediaPeriodIdForChildMediaPeriodId, 0L);
            }
            if (this.drmEventDispatcher.windowIndex == windowIndexForChildWindowIndex && Util.areEqual(this.drmEventDispatcher.mediaPeriodId, mediaPeriodIdForChildMediaPeriodId)) {
                return true;
            }
            this.drmEventDispatcher = CompositeMediaSource.this.createDrmEventDispatcher(windowIndexForChildWindowIndex, mediaPeriodIdForChildMediaPeriodId);
            return true;
        }

        private MediaLoadData maybeUpdateMediaLoadData(MediaLoadData mediaLoadData) {
            long mediaTimeForChildMediaTime = CompositeMediaSource.this.getMediaTimeForChildMediaTime(this.id, mediaLoadData.mediaStartTimeMs);
            long mediaTimeForChildMediaTime2 = CompositeMediaSource.this.getMediaTimeForChildMediaTime(this.id, mediaLoadData.mediaEndTimeMs);
            return (mediaTimeForChildMediaTime == mediaLoadData.mediaStartTimeMs && mediaTimeForChildMediaTime2 == mediaLoadData.mediaEndTimeMs) ? mediaLoadData : new MediaLoadData(mediaLoadData.dataType, mediaLoadData.trackType, mediaLoadData.trackFormat, mediaLoadData.trackSelectionReason, mediaLoadData.trackSelectionData, mediaTimeForChildMediaTime, mediaTimeForChildMediaTime2);
        }
    }
}
