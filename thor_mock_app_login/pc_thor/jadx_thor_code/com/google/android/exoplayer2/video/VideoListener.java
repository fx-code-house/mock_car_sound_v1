package com.google.android.exoplayer2.video;

@Deprecated
/* loaded from: classes.dex */
public interface VideoListener {
    default void onRenderedFirstFrame() {
    }

    default void onSurfaceSizeChanged(int width, int height) {
    }

    @Deprecated
    default void onVideoSizeChanged(int width, int height, int unappliedRotationDegrees, float pixelWidthHeightRatio) {
    }

    default void onVideoSizeChanged(VideoSize videoSize) {
    }
}
