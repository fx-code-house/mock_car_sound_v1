package com.google.android.exoplayer2.upstream;

import android.os.Handler;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.util.Assertions;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

/* loaded from: classes.dex */
public interface BandwidthMeter {
    void addEventListener(Handler eventHandler, EventListener eventListener);

    long getBitrateEstimate();

    default long getTimeToFirstByteEstimateUs() {
        return C.TIME_UNSET;
    }

    TransferListener getTransferListener();

    void removeEventListener(EventListener eventListener);

    public interface EventListener {
        void onBandwidthSample(int elapsedMs, long bytesTransferred, long bitrateEstimate);

        public static final class EventDispatcher {
            private final CopyOnWriteArrayList<HandlerAndListener> listeners = new CopyOnWriteArrayList<>();

            public void addListener(Handler eventHandler, EventListener eventListener) {
                Assertions.checkNotNull(eventHandler);
                Assertions.checkNotNull(eventListener);
                removeListener(eventListener);
                this.listeners.add(new HandlerAndListener(eventHandler, eventListener));
            }

            public void removeListener(EventListener eventListener) {
                Iterator<HandlerAndListener> it = this.listeners.iterator();
                while (it.hasNext()) {
                    HandlerAndListener next = it.next();
                    if (next.listener == eventListener) {
                        next.release();
                        this.listeners.remove(next);
                    }
                }
            }

            public void bandwidthSample(final int elapsedMs, final long bytesTransferred, final long bitrateEstimate) {
                Iterator<HandlerAndListener> it = this.listeners.iterator();
                while (it.hasNext()) {
                    final HandlerAndListener next = it.next();
                    if (!next.released) {
                        next.handler.post(new Runnable() { // from class: com.google.android.exoplayer2.upstream.BandwidthMeter$EventListener$EventDispatcher$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                next.listener.onBandwidthSample(elapsedMs, bytesTransferred, bitrateEstimate);
                            }
                        });
                    }
                }
            }

            /* JADX INFO: Access modifiers changed from: private */
            static final class HandlerAndListener {
                private final Handler handler;
                private final EventListener listener;
                private boolean released;

                public HandlerAndListener(Handler handler, EventListener eventListener) {
                    this.handler = handler;
                    this.listener = eventListener;
                }

                public void release() {
                    this.released = true;
                }
            }
        }
    }
}
