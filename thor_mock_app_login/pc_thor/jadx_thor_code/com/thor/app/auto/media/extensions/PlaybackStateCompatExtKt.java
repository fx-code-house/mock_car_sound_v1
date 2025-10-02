package com.thor.app.auto.media.extensions;

import android.os.SystemClock;
import android.support.v4.media.session.PlaybackStateCompat;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: PlaybackStateCompatExt.kt */
@Metadata(d1 = {"\u0000\u001e\n\u0000\n\u0002\u0010\t\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0010\u000e\n\u0002\b\u0003\"\u0016\u0010\u0000\u001a\u00020\u0001*\u00020\u00028Æ\u0002¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004\"\u0016\u0010\u0005\u001a\u00020\u0006*\u00020\u00028Æ\u0002¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0007\"\u0016\u0010\b\u001a\u00020\u0006*\u00020\u00028Æ\u0002¢\u0006\u0006\u001a\u0004\b\b\u0010\u0007\"\u0016\u0010\t\u001a\u00020\u0006*\u00020\u00028Æ\u0002¢\u0006\u0006\u001a\u0004\b\t\u0010\u0007\"\u0016\u0010\n\u001a\u00020\u0006*\u00020\u00028Æ\u0002¢\u0006\u0006\u001a\u0004\b\n\u0010\u0007\"\u0016\u0010\u000b\u001a\u00020\u0006*\u00020\u00028Æ\u0002¢\u0006\u0006\u001a\u0004\b\u000b\u0010\u0007\"\u0016\u0010\f\u001a\u00020\u0006*\u00020\u00028Æ\u0002¢\u0006\u0006\u001a\u0004\b\f\u0010\u0007\"\u0016\u0010\r\u001a\u00020\u000e*\u00020\u00028Æ\u0002¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010¨\u0006\u0011"}, d2 = {"currentPlayBackPosition", "", "Landroid/support/v4/media/session/PlaybackStateCompat;", "getCurrentPlayBackPosition", "(Landroid/support/v4/media/session/PlaybackStateCompat;)J", "isPauseEnabled", "", "(Landroid/support/v4/media/session/PlaybackStateCompat;)Z", "isPlayEnabled", "isPlaying", "isPrepared", "isSkipToNextEnabled", "isSkipToPreviousEnabled", "stateName", "", "getStateName", "(Landroid/support/v4/media/session/PlaybackStateCompat;)Ljava/lang/String;", "thor-1.8.7_release"}, k = 2, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes.dex */
public final class PlaybackStateCompatExtKt {
    public static final boolean isPrepared(PlaybackStateCompat playbackStateCompat) {
        Intrinsics.checkNotNullParameter(playbackStateCompat, "<this>");
        return playbackStateCompat.getState() == 6 || playbackStateCompat.getState() == 3 || playbackStateCompat.getState() == 2;
    }

    public static final boolean isPlaying(PlaybackStateCompat playbackStateCompat) {
        Intrinsics.checkNotNullParameter(playbackStateCompat, "<this>");
        return playbackStateCompat.getState() == 6 || playbackStateCompat.getState() == 3;
    }

    public static final boolean isPlayEnabled(PlaybackStateCompat playbackStateCompat) {
        Intrinsics.checkNotNullParameter(playbackStateCompat, "<this>");
        return (playbackStateCompat.getActions() & 4) != 0 || ((playbackStateCompat.getActions() & 512) != 0 && playbackStateCompat.getState() == 2);
    }

    public static final boolean isPauseEnabled(PlaybackStateCompat playbackStateCompat) {
        Intrinsics.checkNotNullParameter(playbackStateCompat, "<this>");
        return (playbackStateCompat.getActions() & 2) != 0 || ((playbackStateCompat.getActions() & 512) != 0 && (playbackStateCompat.getState() == 6 || playbackStateCompat.getState() == 3));
    }

    public static final boolean isSkipToNextEnabled(PlaybackStateCompat playbackStateCompat) {
        Intrinsics.checkNotNullParameter(playbackStateCompat, "<this>");
        return (playbackStateCompat.getActions() & 32) != 0;
    }

    public static final boolean isSkipToPreviousEnabled(PlaybackStateCompat playbackStateCompat) {
        Intrinsics.checkNotNullParameter(playbackStateCompat, "<this>");
        return (playbackStateCompat.getActions() & 16) != 0;
    }

    public static final String getStateName(PlaybackStateCompat playbackStateCompat) {
        Intrinsics.checkNotNullParameter(playbackStateCompat, "<this>");
        switch (playbackStateCompat.getState()) {
            case 0:
                return "STATE_NONE";
            case 1:
                return "STATE_STOPPED";
            case 2:
                return "STATE_PAUSED";
            case 3:
                return "STATE_PLAYING";
            case 4:
                return "STATE_FAST_FORWARDING";
            case 5:
                return "STATE_REWINDING";
            case 6:
                return "STATE_BUFFERING";
            case 7:
                return "STATE_ERROR";
            default:
                return "UNKNOWN_STATE";
        }
    }

    public static final long getCurrentPlayBackPosition(PlaybackStateCompat playbackStateCompat) {
        Intrinsics.checkNotNullParameter(playbackStateCompat, "<this>");
        if (playbackStateCompat.getState() == 3) {
            return (long) (playbackStateCompat.getPosition() + ((SystemClock.elapsedRealtime() - playbackStateCompat.getLastPositionUpdateTime()) * playbackStateCompat.getPlaybackSpeed()));
        }
        return playbackStateCompat.getPosition();
    }
}
