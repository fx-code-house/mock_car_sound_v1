package com.google.android.exoplayer2.source;

import android.os.Handler;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

/* loaded from: classes.dex */
public interface MediaSourceEventListener {
    default void onDownstreamFormatChanged(int windowIndex, MediaSource.MediaPeriodId mediaPeriodId, MediaLoadData mediaLoadData) {
    }

    default void onLoadCanceled(int windowIndex, MediaSource.MediaPeriodId mediaPeriodId, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData) {
    }

    default void onLoadCompleted(int windowIndex, MediaSource.MediaPeriodId mediaPeriodId, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData) {
    }

    default void onLoadError(int windowIndex, MediaSource.MediaPeriodId mediaPeriodId, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData, IOException error, boolean wasCanceled) {
    }

    default void onLoadStarted(int windowIndex, MediaSource.MediaPeriodId mediaPeriodId, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData) {
    }

    default void onUpstreamDiscarded(int windowIndex, MediaSource.MediaPeriodId mediaPeriodId, MediaLoadData mediaLoadData) {
    }

    public static class EventDispatcher {
        private final CopyOnWriteArrayList<ListenerAndHandler> listenerAndHandlers;
        public final MediaSource.MediaPeriodId mediaPeriodId;
        private final long mediaTimeOffsetMs;
        public final int windowIndex;

        public EventDispatcher() {
            this(new CopyOnWriteArrayList(), 0, null, 0L);
        }

        private EventDispatcher(CopyOnWriteArrayList<ListenerAndHandler> listenerAndHandlers, int windowIndex, MediaSource.MediaPeriodId mediaPeriodId, long mediaTimeOffsetMs) {
            this.listenerAndHandlers = listenerAndHandlers;
            this.windowIndex = windowIndex;
            this.mediaPeriodId = mediaPeriodId;
            this.mediaTimeOffsetMs = mediaTimeOffsetMs;
        }

        public EventDispatcher withParameters(int windowIndex, MediaSource.MediaPeriodId mediaPeriodId, long mediaTimeOffsetMs) {
            return new EventDispatcher(this.listenerAndHandlers, windowIndex, mediaPeriodId, mediaTimeOffsetMs);
        }

        public void addEventListener(Handler handler, MediaSourceEventListener eventListener) {
            Assertions.checkNotNull(handler);
            Assertions.checkNotNull(eventListener);
            this.listenerAndHandlers.add(new ListenerAndHandler(handler, eventListener));
        }

        public void removeEventListener(MediaSourceEventListener eventListener) {
            Iterator<ListenerAndHandler> it = this.listenerAndHandlers.iterator();
            while (it.hasNext()) {
                ListenerAndHandler next = it.next();
                if (next.listener == eventListener) {
                    this.listenerAndHandlers.remove(next);
                }
            }
        }

        public void loadStarted(LoadEventInfo loadEventInfo, int dataType) {
            loadStarted(loadEventInfo, dataType, -1, null, 0, null, C.TIME_UNSET, C.TIME_UNSET);
        }

        public void loadStarted(LoadEventInfo loadEventInfo, int dataType, int trackType, Format trackFormat, int trackSelectionReason, Object trackSelectionData, long mediaStartTimeUs, long mediaEndTimeUs) {
            loadStarted(loadEventInfo, new MediaLoadData(dataType, trackType, trackFormat, trackSelectionReason, trackSelectionData, adjustMediaTime(mediaStartTimeUs), adjustMediaTime(mediaEndTimeUs)));
        }

        public void loadStarted(final LoadEventInfo loadEventInfo, final MediaLoadData mediaLoadData) {
            Iterator<ListenerAndHandler> it = this.listenerAndHandlers.iterator();
            while (it.hasNext()) {
                ListenerAndHandler next = it.next();
                final MediaSourceEventListener mediaSourceEventListener = next.listener;
                Util.postOrRun(next.handler, new Runnable() { // from class: com.google.android.exoplayer2.source.MediaSourceEventListener$EventDispatcher$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.m178xa95b4272(mediaSourceEventListener, loadEventInfo, mediaLoadData);
                    }
                });
            }
        }

        /* renamed from: lambda$loadStarted$0$com-google-android-exoplayer2-source-MediaSourceEventListener$EventDispatcher, reason: not valid java name */
        /* synthetic */ void m178xa95b4272(MediaSourceEventListener mediaSourceEventListener, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData) {
            mediaSourceEventListener.onLoadStarted(this.windowIndex, this.mediaPeriodId, loadEventInfo, mediaLoadData);
        }

        public void loadCompleted(LoadEventInfo loadEventInfo, int dataType) {
            loadCompleted(loadEventInfo, dataType, -1, null, 0, null, C.TIME_UNSET, C.TIME_UNSET);
        }

        public void loadCompleted(LoadEventInfo loadEventInfo, int dataType, int trackType, Format trackFormat, int trackSelectionReason, Object trackSelectionData, long mediaStartTimeUs, long mediaEndTimeUs) {
            loadCompleted(loadEventInfo, new MediaLoadData(dataType, trackType, trackFormat, trackSelectionReason, trackSelectionData, adjustMediaTime(mediaStartTimeUs), adjustMediaTime(mediaEndTimeUs)));
        }

        public void loadCompleted(final LoadEventInfo loadEventInfo, final MediaLoadData mediaLoadData) {
            Iterator<ListenerAndHandler> it = this.listenerAndHandlers.iterator();
            while (it.hasNext()) {
                ListenerAndHandler next = it.next();
                final MediaSourceEventListener mediaSourceEventListener = next.listener;
                Util.postOrRun(next.handler, new Runnable() { // from class: com.google.android.exoplayer2.source.MediaSourceEventListener$EventDispatcher$$ExternalSyntheticLambda3
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.m176x2b57f33d(mediaSourceEventListener, loadEventInfo, mediaLoadData);
                    }
                });
            }
        }

        /* renamed from: lambda$loadCompleted$1$com-google-android-exoplayer2-source-MediaSourceEventListener$EventDispatcher, reason: not valid java name */
        /* synthetic */ void m176x2b57f33d(MediaSourceEventListener mediaSourceEventListener, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData) {
            mediaSourceEventListener.onLoadCompleted(this.windowIndex, this.mediaPeriodId, loadEventInfo, mediaLoadData);
        }

        public void loadCanceled(LoadEventInfo loadEventInfo, int dataType) {
            loadCanceled(loadEventInfo, dataType, -1, null, 0, null, C.TIME_UNSET, C.TIME_UNSET);
        }

        public void loadCanceled(LoadEventInfo loadEventInfo, int dataType, int trackType, Format trackFormat, int trackSelectionReason, Object trackSelectionData, long mediaStartTimeUs, long mediaEndTimeUs) {
            loadCanceled(loadEventInfo, new MediaLoadData(dataType, trackType, trackFormat, trackSelectionReason, trackSelectionData, adjustMediaTime(mediaStartTimeUs), adjustMediaTime(mediaEndTimeUs)));
        }

        public void loadCanceled(final LoadEventInfo loadEventInfo, final MediaLoadData mediaLoadData) {
            Iterator<ListenerAndHandler> it = this.listenerAndHandlers.iterator();
            while (it.hasNext()) {
                ListenerAndHandler next = it.next();
                final MediaSourceEventListener mediaSourceEventListener = next.listener;
                Util.postOrRun(next.handler, new Runnable() { // from class: com.google.android.exoplayer2.source.MediaSourceEventListener$EventDispatcher$$ExternalSyntheticLambda4
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.m175xe6d0ecf2(mediaSourceEventListener, loadEventInfo, mediaLoadData);
                    }
                });
            }
        }

        /* renamed from: lambda$loadCanceled$2$com-google-android-exoplayer2-source-MediaSourceEventListener$EventDispatcher, reason: not valid java name */
        /* synthetic */ void m175xe6d0ecf2(MediaSourceEventListener mediaSourceEventListener, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData) {
            mediaSourceEventListener.onLoadCanceled(this.windowIndex, this.mediaPeriodId, loadEventInfo, mediaLoadData);
        }

        public void loadError(LoadEventInfo loadEventInfo, int dataType, IOException error, boolean wasCanceled) {
            loadError(loadEventInfo, dataType, -1, null, 0, null, C.TIME_UNSET, C.TIME_UNSET, error, wasCanceled);
        }

        public void loadError(LoadEventInfo loadEventInfo, int dataType, int trackType, Format trackFormat, int trackSelectionReason, Object trackSelectionData, long mediaStartTimeUs, long mediaEndTimeUs, IOException error, boolean wasCanceled) {
            loadError(loadEventInfo, new MediaLoadData(dataType, trackType, trackFormat, trackSelectionReason, trackSelectionData, adjustMediaTime(mediaStartTimeUs), adjustMediaTime(mediaEndTimeUs)), error, wasCanceled);
        }

        public void loadError(final LoadEventInfo loadEventInfo, final MediaLoadData mediaLoadData, final IOException error, final boolean wasCanceled) {
            Iterator<ListenerAndHandler> it = this.listenerAndHandlers.iterator();
            while (it.hasNext()) {
                ListenerAndHandler next = it.next();
                final MediaSourceEventListener mediaSourceEventListener = next.listener;
                Util.postOrRun(next.handler, new Runnable() { // from class: com.google.android.exoplayer2.source.MediaSourceEventListener$EventDispatcher$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.m177x4aa767fc(mediaSourceEventListener, loadEventInfo, mediaLoadData, error, wasCanceled);
                    }
                });
            }
        }

        /* renamed from: lambda$loadError$3$com-google-android-exoplayer2-source-MediaSourceEventListener$EventDispatcher, reason: not valid java name */
        /* synthetic */ void m177x4aa767fc(MediaSourceEventListener mediaSourceEventListener, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData, IOException iOException, boolean z) {
            mediaSourceEventListener.onLoadError(this.windowIndex, this.mediaPeriodId, loadEventInfo, mediaLoadData, iOException, z);
        }

        public void upstreamDiscarded(int trackType, long mediaStartTimeUs, long mediaEndTimeUs) {
            upstreamDiscarded(new MediaLoadData(1, trackType, null, 3, null, adjustMediaTime(mediaStartTimeUs), adjustMediaTime(mediaEndTimeUs)));
        }

        public void upstreamDiscarded(final MediaLoadData mediaLoadData) {
            final MediaSource.MediaPeriodId mediaPeriodId = (MediaSource.MediaPeriodId) Assertions.checkNotNull(this.mediaPeriodId);
            Iterator<ListenerAndHandler> it = this.listenerAndHandlers.iterator();
            while (it.hasNext()) {
                ListenerAndHandler next = it.next();
                final MediaSourceEventListener mediaSourceEventListener = next.listener;
                Util.postOrRun(next.handler, new Runnable() { // from class: com.google.android.exoplayer2.source.MediaSourceEventListener$EventDispatcher$$ExternalSyntheticLambda5
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.m179x3d69689d(mediaSourceEventListener, mediaPeriodId, mediaLoadData);
                    }
                });
            }
        }

        /* renamed from: lambda$upstreamDiscarded$4$com-google-android-exoplayer2-source-MediaSourceEventListener$EventDispatcher, reason: not valid java name */
        /* synthetic */ void m179x3d69689d(MediaSourceEventListener mediaSourceEventListener, MediaSource.MediaPeriodId mediaPeriodId, MediaLoadData mediaLoadData) {
            mediaSourceEventListener.onUpstreamDiscarded(this.windowIndex, mediaPeriodId, mediaLoadData);
        }

        public void downstreamFormatChanged(int trackType, Format trackFormat, int trackSelectionReason, Object trackSelectionData, long mediaTimeUs) {
            downstreamFormatChanged(new MediaLoadData(1, trackType, trackFormat, trackSelectionReason, trackSelectionData, adjustMediaTime(mediaTimeUs), C.TIME_UNSET));
        }

        public void downstreamFormatChanged(final MediaLoadData mediaLoadData) {
            Iterator<ListenerAndHandler> it = this.listenerAndHandlers.iterator();
            while (it.hasNext()) {
                ListenerAndHandler next = it.next();
                final MediaSourceEventListener mediaSourceEventListener = next.listener;
                Util.postOrRun(next.handler, new Runnable() { // from class: com.google.android.exoplayer2.source.MediaSourceEventListener$EventDispatcher$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.m174x2dc6fb7(mediaSourceEventListener, mediaLoadData);
                    }
                });
            }
        }

        /* renamed from: lambda$downstreamFormatChanged$5$com-google-android-exoplayer2-source-MediaSourceEventListener$EventDispatcher, reason: not valid java name */
        /* synthetic */ void m174x2dc6fb7(MediaSourceEventListener mediaSourceEventListener, MediaLoadData mediaLoadData) {
            mediaSourceEventListener.onDownstreamFormatChanged(this.windowIndex, this.mediaPeriodId, mediaLoadData);
        }

        private long adjustMediaTime(long mediaTimeUs) {
            long jUsToMs = C.usToMs(mediaTimeUs);
            return jUsToMs == C.TIME_UNSET ? C.TIME_UNSET : this.mediaTimeOffsetMs + jUsToMs;
        }

        private static final class ListenerAndHandler {
            public Handler handler;
            public MediaSourceEventListener listener;

            public ListenerAndHandler(Handler handler, MediaSourceEventListener listener) {
                this.handler = handler;
                this.listener = listener;
            }
        }
    }
}
