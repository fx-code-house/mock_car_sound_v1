package com.thor.app.auto.common;

import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: MusicServiceConnection.kt */
@Metadata(d1 = {"\u0000\u0012\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\"\u0017\u0010\u0000\u001a\u00020\u0001¢\u0006\u000e\n\u0000\u0012\u0004\b\u0002\u0010\u0003\u001a\u0004\b\u0004\u0010\u0005\"\u0017\u0010\u0006\u001a\u00020\u0007¢\u0006\u000e\n\u0000\u0012\u0004\b\b\u0010\u0003\u001a\u0004\b\t\u0010\n¨\u0006\u000b"}, d2 = {"EMPTY_PLAYBACK_STATE", "Landroid/support/v4/media/session/PlaybackStateCompat;", "getEMPTY_PLAYBACK_STATE$annotations", "()V", "getEMPTY_PLAYBACK_STATE", "()Landroid/support/v4/media/session/PlaybackStateCompat;", "NOTHING_PLAYING", "Landroid/support/v4/media/MediaMetadataCompat;", "getNOTHING_PLAYING$annotations", "getNOTHING_PLAYING", "()Landroid/support/v4/media/MediaMetadataCompat;", "thor-1.8.7_release"}, k = 2, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes.dex */
public final class MusicServiceConnectionKt {
    private static final PlaybackStateCompat EMPTY_PLAYBACK_STATE;
    private static final MediaMetadataCompat NOTHING_PLAYING;

    public static /* synthetic */ void getEMPTY_PLAYBACK_STATE$annotations() {
    }

    public static /* synthetic */ void getNOTHING_PLAYING$annotations() {
    }

    static {
        PlaybackStateCompat playbackStateCompatBuild = new PlaybackStateCompat.Builder().setState(0, 0L, 0.0f).build();
        Intrinsics.checkNotNullExpressionValue(playbackStateCompatBuild, "Builder()\n    .setState(…NONE, 0, 0f)\n    .build()");
        EMPTY_PLAYBACK_STATE = playbackStateCompatBuild;
        MediaMetadataCompat mediaMetadataCompatBuild = new MediaMetadataCompat.Builder().putString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID, "").putLong(MediaMetadataCompat.METADATA_KEY_DURATION, 0L).build();
        Intrinsics.checkNotNullExpressionValue(mediaMetadataCompatBuild, "Builder()\n    .putString…DURATION, 0)\n    .build()");
        NOTHING_PLAYING = mediaMetadataCompatBuild;
    }

    public static final PlaybackStateCompat getEMPTY_PLAYBACK_STATE() {
        return EMPTY_PLAYBACK_STATE;
    }

    public static final MediaMetadataCompat getNOTHING_PLAYING() {
        return NOTHING_PLAYING;
    }
}
