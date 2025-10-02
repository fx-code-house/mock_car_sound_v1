package com.google.android.exoplayer2.drm;

import android.os.Handler;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

/* loaded from: classes.dex */
public interface DrmSessionEventListener {
    default void onDrmKeysLoaded(int windowIndex, MediaSource.MediaPeriodId mediaPeriodId) {
    }

    default void onDrmKeysRemoved(int windowIndex, MediaSource.MediaPeriodId mediaPeriodId) {
    }

    default void onDrmKeysRestored(int windowIndex, MediaSource.MediaPeriodId mediaPeriodId) {
    }

    @Deprecated
    default void onDrmSessionAcquired(int windowIndex, MediaSource.MediaPeriodId mediaPeriodId) {
    }

    default void onDrmSessionAcquired(int windowIndex, MediaSource.MediaPeriodId mediaPeriodId, int state) {
    }

    default void onDrmSessionManagerError(int windowIndex, MediaSource.MediaPeriodId mediaPeriodId, Exception error) {
    }

    default void onDrmSessionReleased(int windowIndex, MediaSource.MediaPeriodId mediaPeriodId) {
    }

    public static class EventDispatcher {
        private final CopyOnWriteArrayList<ListenerAndHandler> listenerAndHandlers;
        public final MediaSource.MediaPeriodId mediaPeriodId;
        public final int windowIndex;

        public EventDispatcher() {
            this(new CopyOnWriteArrayList(), 0, null);
        }

        private EventDispatcher(CopyOnWriteArrayList<ListenerAndHandler> listenerAndHandlers, int windowIndex, MediaSource.MediaPeriodId mediaPeriodId) {
            this.listenerAndHandlers = listenerAndHandlers;
            this.windowIndex = windowIndex;
            this.mediaPeriodId = mediaPeriodId;
        }

        public EventDispatcher withParameters(int windowIndex, MediaSource.MediaPeriodId mediaPeriodId) {
            return new EventDispatcher(this.listenerAndHandlers, windowIndex, mediaPeriodId);
        }

        public void addEventListener(Handler handler, DrmSessionEventListener eventListener) {
            Assertions.checkNotNull(handler);
            Assertions.checkNotNull(eventListener);
            this.listenerAndHandlers.add(new ListenerAndHandler(handler, eventListener));
        }

        public void removeEventListener(DrmSessionEventListener eventListener) {
            Iterator<ListenerAndHandler> it = this.listenerAndHandlers.iterator();
            while (it.hasNext()) {
                ListenerAndHandler next = it.next();
                if (next.listener == eventListener) {
                    this.listenerAndHandlers.remove(next);
                }
            }
        }

        public void drmSessionAcquired(final int state) {
            Iterator<ListenerAndHandler> it = this.listenerAndHandlers.iterator();
            while (it.hasNext()) {
                ListenerAndHandler next = it.next();
                final DrmSessionEventListener drmSessionEventListener = next.listener;
                Util.postOrRun(next.handler, new Runnable() { // from class: com.google.android.exoplayer2.drm.DrmSessionEventListener$EventDispatcher$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.m155x7aff32be(drmSessionEventListener, state);
                    }
                });
            }
        }

        /* renamed from: lambda$drmSessionAcquired$0$com-google-android-exoplayer2-drm-DrmSessionEventListener$EventDispatcher, reason: not valid java name */
        /* synthetic */ void m155x7aff32be(DrmSessionEventListener drmSessionEventListener, int i) {
            drmSessionEventListener.onDrmSessionAcquired(this.windowIndex, this.mediaPeriodId);
            drmSessionEventListener.onDrmSessionAcquired(this.windowIndex, this.mediaPeriodId, i);
        }

        public void drmKeysLoaded() {
            Iterator<ListenerAndHandler> it = this.listenerAndHandlers.iterator();
            while (it.hasNext()) {
                ListenerAndHandler next = it.next();
                final DrmSessionEventListener drmSessionEventListener = next.listener;
                Util.postOrRun(next.handler, new Runnable() { // from class: com.google.android.exoplayer2.drm.DrmSessionEventListener$EventDispatcher$$ExternalSyntheticLambda4
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.m152x6262ec98(drmSessionEventListener);
                    }
                });
            }
        }

        /* renamed from: lambda$drmKeysLoaded$1$com-google-android-exoplayer2-drm-DrmSessionEventListener$EventDispatcher, reason: not valid java name */
        /* synthetic */ void m152x6262ec98(DrmSessionEventListener drmSessionEventListener) {
            drmSessionEventListener.onDrmKeysLoaded(this.windowIndex, this.mediaPeriodId);
        }

        public void drmSessionManagerError(final Exception error) {
            Iterator<ListenerAndHandler> it = this.listenerAndHandlers.iterator();
            while (it.hasNext()) {
                ListenerAndHandler next = it.next();
                final DrmSessionEventListener drmSessionEventListener = next.listener;
                Util.postOrRun(next.handler, new Runnable() { // from class: com.google.android.exoplayer2.drm.DrmSessionEventListener$EventDispatcher$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.m156x99ee26cd(drmSessionEventListener, error);
                    }
                });
            }
        }

        /* renamed from: lambda$drmSessionManagerError$2$com-google-android-exoplayer2-drm-DrmSessionEventListener$EventDispatcher, reason: not valid java name */
        /* synthetic */ void m156x99ee26cd(DrmSessionEventListener drmSessionEventListener, Exception exc) {
            drmSessionEventListener.onDrmSessionManagerError(this.windowIndex, this.mediaPeriodId, exc);
        }

        public void drmKeysRestored() {
            Iterator<ListenerAndHandler> it = this.listenerAndHandlers.iterator();
            while (it.hasNext()) {
                ListenerAndHandler next = it.next();
                final DrmSessionEventListener drmSessionEventListener = next.listener;
                Util.postOrRun(next.handler, new Runnable() { // from class: com.google.android.exoplayer2.drm.DrmSessionEventListener$EventDispatcher$$ExternalSyntheticLambda5
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.m154x8910d2ab(drmSessionEventListener);
                    }
                });
            }
        }

        /* renamed from: lambda$drmKeysRestored$3$com-google-android-exoplayer2-drm-DrmSessionEventListener$EventDispatcher, reason: not valid java name */
        /* synthetic */ void m154x8910d2ab(DrmSessionEventListener drmSessionEventListener) {
            drmSessionEventListener.onDrmKeysRestored(this.windowIndex, this.mediaPeriodId);
        }

        public void drmKeysRemoved() {
            Iterator<ListenerAndHandler> it = this.listenerAndHandlers.iterator();
            while (it.hasNext()) {
                ListenerAndHandler next = it.next();
                final DrmSessionEventListener drmSessionEventListener = next.listener;
                Util.postOrRun(next.handler, new Runnable() { // from class: com.google.android.exoplayer2.drm.DrmSessionEventListener$EventDispatcher$$ExternalSyntheticLambda3
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.m153xc24f0d8a(drmSessionEventListener);
                    }
                });
            }
        }

        /* renamed from: lambda$drmKeysRemoved$4$com-google-android-exoplayer2-drm-DrmSessionEventListener$EventDispatcher, reason: not valid java name */
        /* synthetic */ void m153xc24f0d8a(DrmSessionEventListener drmSessionEventListener) {
            drmSessionEventListener.onDrmKeysRemoved(this.windowIndex, this.mediaPeriodId);
        }

        public void drmSessionReleased() {
            Iterator<ListenerAndHandler> it = this.listenerAndHandlers.iterator();
            while (it.hasNext()) {
                ListenerAndHandler next = it.next();
                final DrmSessionEventListener drmSessionEventListener = next.listener;
                Util.postOrRun(next.handler, new Runnable() { // from class: com.google.android.exoplayer2.drm.DrmSessionEventListener$EventDispatcher$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.m157xbad440f2(drmSessionEventListener);
                    }
                });
            }
        }

        /* renamed from: lambda$drmSessionReleased$5$com-google-android-exoplayer2-drm-DrmSessionEventListener$EventDispatcher, reason: not valid java name */
        /* synthetic */ void m157xbad440f2(DrmSessionEventListener drmSessionEventListener) {
            drmSessionEventListener.onDrmSessionReleased(this.windowIndex, this.mediaPeriodId);
        }

        private static final class ListenerAndHandler {
            public Handler handler;
            public DrmSessionEventListener listener;

            public ListenerAndHandler(Handler handler, DrmSessionEventListener listener) {
                this.handler = handler;
                this.listener = listener;
            }
        }
    }
}
