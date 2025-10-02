package com.google.android.exoplayer2.ext.mediasession;

import android.os.Bundle;
import android.os.ResultReceiver;
import android.support.v4.media.MediaDescriptionCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ControlDispatcher;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.ext.mediasession.MediaSessionConnector;
import com.google.android.exoplayer2.util.Assertions;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;

/* loaded from: classes.dex */
public abstract class TimelineQueueNavigator implements MediaSessionConnector.QueueNavigator {
    public static final int DEFAULT_MAX_QUEUE_SIZE = 10;
    private long activeQueueItemId;
    private final int maxQueueSize;
    private final MediaSessionCompat mediaSession;
    private final Timeline.Window window;

    public abstract MediaDescriptionCompat getMediaDescription(Player player, int windowIndex);

    @Override // com.google.android.exoplayer2.ext.mediasession.MediaSessionConnector.CommandReceiver
    public boolean onCommand(Player player, @Deprecated ControlDispatcher controlDispatcher, String command, Bundle extras, ResultReceiver cb) {
        return false;
    }

    public TimelineQueueNavigator(MediaSessionCompat mediaSession) {
        this(mediaSession, 10);
    }

    public TimelineQueueNavigator(MediaSessionCompat mediaSession, int maxQueueSize) {
        Assertions.checkState(maxQueueSize > 0);
        this.mediaSession = mediaSession;
        this.maxQueueSize = maxQueueSize;
        this.activeQueueItemId = -1L;
        this.window = new Timeline.Window();
    }

    @Override // com.google.android.exoplayer2.ext.mediasession.MediaSessionConnector.QueueNavigator
    public long getSupportedQueueNavigatorActions(Player player) {
        boolean z;
        boolean z2;
        Timeline currentTimeline = player.getCurrentTimeline();
        if (currentTimeline.isEmpty() || player.isPlayingAd()) {
            z = false;
            z2 = false;
        } else {
            currentTimeline.getWindow(player.getCurrentWindowIndex(), this.window);
            boolean z3 = currentTimeline.getWindowCount() > 1;
            z2 = player.isCommandAvailable(4) || !this.window.isLive() || player.isCommandAvailable(5);
            z = (this.window.isLive() && this.window.isDynamic) || player.isCommandAvailable(7);
            z = z3;
        }
        long j = z ? PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM : 0L;
        if (z2) {
            j |= 16;
        }
        return z ? j | 32 : j;
    }

    @Override // com.google.android.exoplayer2.ext.mediasession.MediaSessionConnector.QueueNavigator
    public final void onTimelineChanged(Player player) {
        publishFloatingQueueWindow(player);
    }

    @Override // com.google.android.exoplayer2.ext.mediasession.MediaSessionConnector.QueueNavigator
    public final void onCurrentWindowIndexChanged(Player player) {
        if (this.activeQueueItemId == -1 || player.getCurrentTimeline().getWindowCount() > this.maxQueueSize) {
            publishFloatingQueueWindow(player);
        } else {
            if (player.getCurrentTimeline().isEmpty()) {
                return;
            }
            this.activeQueueItemId = player.getCurrentWindowIndex();
        }
    }

    @Override // com.google.android.exoplayer2.ext.mediasession.MediaSessionConnector.QueueNavigator
    public final long getActiveQueueItemId(Player player) {
        return this.activeQueueItemId;
    }

    @Override // com.google.android.exoplayer2.ext.mediasession.MediaSessionConnector.QueueNavigator
    public void onSkipToPrevious(Player player, @Deprecated ControlDispatcher controlDispatcher) {
        controlDispatcher.dispatchPrevious(player);
    }

    @Override // com.google.android.exoplayer2.ext.mediasession.MediaSessionConnector.QueueNavigator
    public void onSkipToQueueItem(Player player, @Deprecated ControlDispatcher controlDispatcher, long id) {
        int i;
        Timeline currentTimeline = player.getCurrentTimeline();
        if (currentTimeline.isEmpty() || player.isPlayingAd() || (i = (int) id) < 0 || i >= currentTimeline.getWindowCount()) {
            return;
        }
        controlDispatcher.dispatchSeekTo(player, i, C.TIME_UNSET);
    }

    @Override // com.google.android.exoplayer2.ext.mediasession.MediaSessionConnector.QueueNavigator
    public void onSkipToNext(Player player, @Deprecated ControlDispatcher controlDispatcher) {
        controlDispatcher.dispatchNext(player);
    }

    private void publishFloatingQueueWindow(Player player) {
        Timeline currentTimeline = player.getCurrentTimeline();
        if (currentTimeline.isEmpty()) {
            this.mediaSession.setQueue(Collections.emptyList());
            this.activeQueueItemId = -1L;
            return;
        }
        ArrayDeque arrayDeque = new ArrayDeque();
        int iMin = Math.min(this.maxQueueSize, currentTimeline.getWindowCount());
        int currentWindowIndex = player.getCurrentWindowIndex();
        long j = currentWindowIndex;
        arrayDeque.add(new MediaSessionCompat.QueueItem(getMediaDescription(player, currentWindowIndex), j));
        boolean shuffleModeEnabled = player.getShuffleModeEnabled();
        int nextWindowIndex = currentWindowIndex;
        while (true) {
            if ((currentWindowIndex == -1 && nextWindowIndex == -1) || arrayDeque.size() >= iMin) {
                break;
            }
            if (nextWindowIndex != -1 && (nextWindowIndex = currentTimeline.getNextWindowIndex(nextWindowIndex, 0, shuffleModeEnabled)) != -1) {
                arrayDeque.add(new MediaSessionCompat.QueueItem(getMediaDescription(player, nextWindowIndex), nextWindowIndex));
            }
            if (currentWindowIndex != -1 && arrayDeque.size() < iMin && (currentWindowIndex = currentTimeline.getPreviousWindowIndex(currentWindowIndex, 0, shuffleModeEnabled)) != -1) {
                arrayDeque.addFirst(new MediaSessionCompat.QueueItem(getMediaDescription(player, currentWindowIndex), currentWindowIndex));
            }
        }
        this.mediaSession.setQueue(new ArrayList(arrayDeque));
        this.activeQueueItemId = j;
    }
}
