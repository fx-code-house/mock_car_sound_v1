package com.google.android.exoplayer2;

import android.os.Handler;
import android.os.Looper;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Clock;
import java.util.concurrent.TimeoutException;

/* loaded from: classes.dex */
public final class PlayerMessage {
    private final Clock clock;
    private boolean isCanceled;
    private boolean isDelivered;
    private boolean isProcessed;
    private boolean isSent;
    private Looper looper;
    private Object payload;
    private final Sender sender;
    private final Target target;
    private final Timeline timeline;
    private int type;
    private int windowIndex;
    private long positionMs = C.TIME_UNSET;
    private boolean deleteAfterDelivery = true;

    public interface Sender {
        void sendMessage(PlayerMessage message);
    }

    public interface Target {
        void handleMessage(int messageType, Object message) throws ExoPlaybackException;
    }

    public PlayerMessage(Sender sender, Target target, Timeline timeline, int defaultWindowIndex, Clock clock, Looper defaultLooper) {
        this.sender = sender;
        this.target = target;
        this.timeline = timeline;
        this.looper = defaultLooper;
        this.clock = clock;
        this.windowIndex = defaultWindowIndex;
    }

    public Timeline getTimeline() {
        return this.timeline;
    }

    public Target getTarget() {
        return this.target;
    }

    public PlayerMessage setType(int messageType) {
        Assertions.checkState(!this.isSent);
        this.type = messageType;
        return this;
    }

    public int getType() {
        return this.type;
    }

    public PlayerMessage setPayload(Object payload) {
        Assertions.checkState(!this.isSent);
        this.payload = payload;
        return this;
    }

    public Object getPayload() {
        return this.payload;
    }

    @Deprecated
    public PlayerMessage setHandler(Handler handler) {
        return setLooper(handler.getLooper());
    }

    public PlayerMessage setLooper(Looper looper) {
        Assertions.checkState(!this.isSent);
        this.looper = looper;
        return this;
    }

    public Looper getLooper() {
        return this.looper;
    }

    public long getPositionMs() {
        return this.positionMs;
    }

    public PlayerMessage setPosition(long positionMs) {
        Assertions.checkState(!this.isSent);
        this.positionMs = positionMs;
        return this;
    }

    public PlayerMessage setPosition(int windowIndex, long positionMs) {
        Assertions.checkState(!this.isSent);
        Assertions.checkArgument(positionMs != C.TIME_UNSET);
        if (windowIndex < 0 || (!this.timeline.isEmpty() && windowIndex >= this.timeline.getWindowCount())) {
            throw new IllegalSeekPositionException(this.timeline, windowIndex, positionMs);
        }
        this.windowIndex = windowIndex;
        this.positionMs = positionMs;
        return this;
    }

    public int getWindowIndex() {
        return this.windowIndex;
    }

    public PlayerMessage setDeleteAfterDelivery(boolean deleteAfterDelivery) {
        Assertions.checkState(!this.isSent);
        this.deleteAfterDelivery = deleteAfterDelivery;
        return this;
    }

    public boolean getDeleteAfterDelivery() {
        return this.deleteAfterDelivery;
    }

    public PlayerMessage send() {
        Assertions.checkState(!this.isSent);
        if (this.positionMs == C.TIME_UNSET) {
            Assertions.checkArgument(this.deleteAfterDelivery);
        }
        this.isSent = true;
        this.sender.sendMessage(this);
        return this;
    }

    public synchronized PlayerMessage cancel() {
        Assertions.checkState(this.isSent);
        this.isCanceled = true;
        markAsProcessed(false);
        return this;
    }

    public synchronized boolean isCanceled() {
        return this.isCanceled;
    }

    public synchronized void markAsProcessed(boolean isDelivered) {
        this.isDelivered = isDelivered | this.isDelivered;
        this.isProcessed = true;
        notifyAll();
    }

    public synchronized boolean blockUntilDelivered() throws InterruptedException {
        Assertions.checkState(this.isSent);
        Assertions.checkState(this.looper.getThread() != Thread.currentThread());
        while (!this.isProcessed) {
            wait();
        }
        return this.isDelivered;
    }

    public synchronized boolean blockUntilDelivered(long timeoutMs) throws InterruptedException, TimeoutException {
        boolean z;
        Assertions.checkState(this.isSent);
        Assertions.checkState(this.looper.getThread() != Thread.currentThread());
        long jElapsedRealtime = this.clock.elapsedRealtime() + timeoutMs;
        while (true) {
            z = this.isProcessed;
            if (z || timeoutMs <= 0) {
                break;
            }
            this.clock.onThreadBlocked();
            wait(timeoutMs);
            timeoutMs = jElapsedRealtime - this.clock.elapsedRealtime();
        }
        if (!z) {
            throw new TimeoutException("Message delivery timed out.");
        }
        return this.isDelivered;
    }
}
