package com.google.android.exoplayer2.transformer;

/* loaded from: classes.dex */
final class Transformation {
    public final boolean flattenForSlowMotion;
    public final String outputMimeType;
    public final boolean removeAudio;
    public final boolean removeVideo;

    public Transformation(boolean removeAudio, boolean removeVideo, boolean flattenForSlowMotion, String outputMimeType) {
        this.removeAudio = removeAudio;
        this.removeVideo = removeVideo;
        this.flattenForSlowMotion = flattenForSlowMotion;
        this.outputMimeType = outputMimeType;
    }
}
