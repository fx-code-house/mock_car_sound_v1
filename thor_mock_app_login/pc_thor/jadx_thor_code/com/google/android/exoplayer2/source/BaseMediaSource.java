package com.google.android.exoplayer2.source;

import android.os.Handler;
import android.os.Looper;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.drm.DrmSessionEventListener;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.MediaSourceEventListener;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Assertions;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

/* loaded from: classes.dex */
public abstract class BaseMediaSource implements MediaSource {
    private Looper looper;
    private Timeline timeline;
    private final ArrayList<MediaSource.MediaSourceCaller> mediaSourceCallers = new ArrayList<>(1);
    private final HashSet<MediaSource.MediaSourceCaller> enabledMediaSourceCallers = new HashSet<>(1);
    private final MediaSourceEventListener.EventDispatcher eventDispatcher = new MediaSourceEventListener.EventDispatcher();
    private final DrmSessionEventListener.EventDispatcher drmEventDispatcher = new DrmSessionEventListener.EventDispatcher();

    protected void disableInternal() {
    }

    protected void enableInternal() {
    }

    protected abstract void prepareSourceInternal(TransferListener mediaTransferListener);

    protected abstract void releaseSourceInternal();

    protected final void refreshSourceInfo(Timeline timeline) {
        this.timeline = timeline;
        Iterator<MediaSource.MediaSourceCaller> it = this.mediaSourceCallers.iterator();
        while (it.hasNext()) {
            it.next().onSourceInfoRefreshed(this, timeline);
        }
    }

    protected final MediaSourceEventListener.EventDispatcher createEventDispatcher(MediaSource.MediaPeriodId mediaPeriodId) {
        return this.eventDispatcher.withParameters(0, mediaPeriodId, 0L);
    }

    protected final MediaSourceEventListener.EventDispatcher createEventDispatcher(MediaSource.MediaPeriodId mediaPeriodId, long mediaTimeOffsetMs) {
        Assertions.checkNotNull(mediaPeriodId);
        return this.eventDispatcher.withParameters(0, mediaPeriodId, mediaTimeOffsetMs);
    }

    protected final MediaSourceEventListener.EventDispatcher createEventDispatcher(int windowIndex, MediaSource.MediaPeriodId mediaPeriodId, long mediaTimeOffsetMs) {
        return this.eventDispatcher.withParameters(windowIndex, mediaPeriodId, mediaTimeOffsetMs);
    }

    protected final DrmSessionEventListener.EventDispatcher createDrmEventDispatcher(MediaSource.MediaPeriodId mediaPeriodId) {
        return this.drmEventDispatcher.withParameters(0, mediaPeriodId);
    }

    protected final DrmSessionEventListener.EventDispatcher createDrmEventDispatcher(int windowIndex, MediaSource.MediaPeriodId mediaPeriodId) {
        return this.drmEventDispatcher.withParameters(windowIndex, mediaPeriodId);
    }

    protected final boolean isEnabled() {
        return !this.enabledMediaSourceCallers.isEmpty();
    }

    @Override // com.google.android.exoplayer2.source.MediaSource
    public final void addEventListener(Handler handler, MediaSourceEventListener eventListener) {
        Assertions.checkNotNull(handler);
        Assertions.checkNotNull(eventListener);
        this.eventDispatcher.addEventListener(handler, eventListener);
    }

    @Override // com.google.android.exoplayer2.source.MediaSource
    public final void removeEventListener(MediaSourceEventListener eventListener) {
        this.eventDispatcher.removeEventListener(eventListener);
    }

    @Override // com.google.android.exoplayer2.source.MediaSource
    public final void addDrmEventListener(Handler handler, DrmSessionEventListener eventListener) {
        Assertions.checkNotNull(handler);
        Assertions.checkNotNull(eventListener);
        this.drmEventDispatcher.addEventListener(handler, eventListener);
    }

    @Override // com.google.android.exoplayer2.source.MediaSource
    public final void removeDrmEventListener(DrmSessionEventListener eventListener) {
        this.drmEventDispatcher.removeEventListener(eventListener);
    }

    @Override // com.google.android.exoplayer2.source.MediaSource
    public final void prepareSource(MediaSource.MediaSourceCaller caller, TransferListener mediaTransferListener) {
        Looper looperMyLooper = Looper.myLooper();
        Looper looper = this.looper;
        Assertions.checkArgument(looper == null || looper == looperMyLooper);
        Timeline timeline = this.timeline;
        this.mediaSourceCallers.add(caller);
        if (this.looper == null) {
            this.looper = looperMyLooper;
            this.enabledMediaSourceCallers.add(caller);
            prepareSourceInternal(mediaTransferListener);
        } else if (timeline != null) {
            enable(caller);
            caller.onSourceInfoRefreshed(this, timeline);
        }
    }

    @Override // com.google.android.exoplayer2.source.MediaSource
    public final void enable(MediaSource.MediaSourceCaller caller) {
        Assertions.checkNotNull(this.looper);
        boolean zIsEmpty = this.enabledMediaSourceCallers.isEmpty();
        this.enabledMediaSourceCallers.add(caller);
        if (zIsEmpty) {
            enableInternal();
        }
    }

    @Override // com.google.android.exoplayer2.source.MediaSource
    public final void disable(MediaSource.MediaSourceCaller caller) {
        boolean z = !this.enabledMediaSourceCallers.isEmpty();
        this.enabledMediaSourceCallers.remove(caller);
        if (z && this.enabledMediaSourceCallers.isEmpty()) {
            disableInternal();
        }
    }

    @Override // com.google.android.exoplayer2.source.MediaSource
    public final void releaseSource(MediaSource.MediaSourceCaller caller) {
        this.mediaSourceCallers.remove(caller);
        if (this.mediaSourceCallers.isEmpty()) {
            this.looper = null;
            this.timeline = null;
            this.enabledMediaSourceCallers.clear();
            releaseSourceInternal();
            return;
        }
        disable(caller);
    }
}
