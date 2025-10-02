package com.google.android.exoplayer2;

@Deprecated
/* loaded from: classes.dex */
public class DefaultControlDispatcher implements ControlDispatcher {
    private final long fastForwardIncrementMs;
    private final boolean rewindAndFastForwardIncrementsSet;
    private final long rewindIncrementMs;

    public DefaultControlDispatcher() {
        this.fastForwardIncrementMs = C.TIME_UNSET;
        this.rewindIncrementMs = C.TIME_UNSET;
        this.rewindAndFastForwardIncrementsSet = false;
    }

    public DefaultControlDispatcher(long fastForwardIncrementMs, long rewindIncrementMs) {
        this.fastForwardIncrementMs = fastForwardIncrementMs;
        this.rewindIncrementMs = rewindIncrementMs;
        this.rewindAndFastForwardIncrementsSet = true;
    }

    @Override // com.google.android.exoplayer2.ControlDispatcher
    public boolean dispatchPrepare(Player player) {
        player.prepare();
        return true;
    }

    @Override // com.google.android.exoplayer2.ControlDispatcher
    public boolean dispatchSetPlayWhenReady(Player player, boolean playWhenReady) {
        player.setPlayWhenReady(playWhenReady);
        return true;
    }

    @Override // com.google.android.exoplayer2.ControlDispatcher
    public boolean dispatchSeekTo(Player player, int windowIndex, long positionMs) {
        player.seekTo(windowIndex, positionMs);
        return true;
    }

    @Override // com.google.android.exoplayer2.ControlDispatcher
    public boolean dispatchPrevious(Player player) {
        player.seekToPrevious();
        return true;
    }

    @Override // com.google.android.exoplayer2.ControlDispatcher
    public boolean dispatchNext(Player player) {
        player.seekToNext();
        return true;
    }

    @Override // com.google.android.exoplayer2.ControlDispatcher
    public boolean dispatchRewind(Player player) {
        if (!this.rewindAndFastForwardIncrementsSet) {
            player.seekBack();
            return true;
        }
        if (!isRewindEnabled() || !player.isCurrentWindowSeekable()) {
            return true;
        }
        seekToOffset(player, -this.rewindIncrementMs);
        return true;
    }

    @Override // com.google.android.exoplayer2.ControlDispatcher
    public boolean dispatchFastForward(Player player) {
        if (!this.rewindAndFastForwardIncrementsSet) {
            player.seekForward();
            return true;
        }
        if (!isFastForwardEnabled() || !player.isCurrentWindowSeekable()) {
            return true;
        }
        seekToOffset(player, this.fastForwardIncrementMs);
        return true;
    }

    @Override // com.google.android.exoplayer2.ControlDispatcher
    public boolean dispatchSetRepeatMode(Player player, int repeatMode) {
        player.setRepeatMode(repeatMode);
        return true;
    }

    @Override // com.google.android.exoplayer2.ControlDispatcher
    public boolean dispatchSetShuffleModeEnabled(Player player, boolean shuffleModeEnabled) {
        player.setShuffleModeEnabled(shuffleModeEnabled);
        return true;
    }

    @Override // com.google.android.exoplayer2.ControlDispatcher
    public boolean dispatchStop(Player player, boolean reset) {
        player.stop(reset);
        return true;
    }

    @Override // com.google.android.exoplayer2.ControlDispatcher
    public boolean dispatchSetPlaybackParameters(Player player, PlaybackParameters playbackParameters) {
        player.setPlaybackParameters(playbackParameters);
        return true;
    }

    @Override // com.google.android.exoplayer2.ControlDispatcher
    public boolean isRewindEnabled() {
        return !this.rewindAndFastForwardIncrementsSet || this.rewindIncrementMs > 0;
    }

    @Override // com.google.android.exoplayer2.ControlDispatcher
    public boolean isFastForwardEnabled() {
        return !this.rewindAndFastForwardIncrementsSet || this.fastForwardIncrementMs > 0;
    }

    public long getRewindIncrementMs(Player player) {
        return this.rewindAndFastForwardIncrementsSet ? this.rewindIncrementMs : player.getSeekBackIncrement();
    }

    public long getFastForwardIncrementMs(Player player) {
        if (this.rewindAndFastForwardIncrementsSet) {
            return this.fastForwardIncrementMs;
        }
        return player.getSeekForwardIncrement();
    }

    private static void seekToOffset(Player player, long offsetMs) {
        long currentPosition = player.getCurrentPosition() + offsetMs;
        long duration = player.getDuration();
        if (duration != C.TIME_UNSET) {
            currentPosition = Math.min(currentPosition, duration);
        }
        player.seekTo(Math.max(currentPosition, 0L));
    }
}
