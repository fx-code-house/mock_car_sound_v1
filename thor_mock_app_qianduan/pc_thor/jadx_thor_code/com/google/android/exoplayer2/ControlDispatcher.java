package com.google.android.exoplayer2;

@Deprecated
/* loaded from: classes.dex */
public interface ControlDispatcher {
    boolean dispatchFastForward(Player player);

    boolean dispatchNext(Player player);

    boolean dispatchPrepare(Player player);

    boolean dispatchPrevious(Player player);

    boolean dispatchRewind(Player player);

    boolean dispatchSeekTo(Player player, int windowIndex, long positionMs);

    boolean dispatchSetPlayWhenReady(Player player, boolean playWhenReady);

    boolean dispatchSetPlaybackParameters(Player player, PlaybackParameters playbackParameters);

    boolean dispatchSetRepeatMode(Player player, int repeatMode);

    boolean dispatchSetShuffleModeEnabled(Player player, boolean shuffleModeEnabled);

    boolean dispatchStop(Player player, boolean reset);

    boolean isFastForwardEnabled();

    boolean isRewindEnabled();
}
