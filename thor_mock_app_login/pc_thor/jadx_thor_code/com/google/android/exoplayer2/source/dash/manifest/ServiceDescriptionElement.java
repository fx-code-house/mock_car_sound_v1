package com.google.android.exoplayer2.source.dash.manifest;

/* loaded from: classes.dex */
public final class ServiceDescriptionElement {
    public final long maxOffsetMs;
    public final float maxPlaybackSpeed;
    public final long minOffsetMs;
    public final float minPlaybackSpeed;
    public final long targetOffsetMs;

    public ServiceDescriptionElement(long targetOffsetMs, long minOffsetMs, long maxOffsetMs, float minPlaybackSpeed, float maxPlaybackSpeed) {
        this.targetOffsetMs = targetOffsetMs;
        this.minOffsetMs = minOffsetMs;
        this.maxOffsetMs = maxOffsetMs;
        this.minPlaybackSpeed = minPlaybackSpeed;
        this.maxPlaybackSpeed = maxPlaybackSpeed;
    }
}
