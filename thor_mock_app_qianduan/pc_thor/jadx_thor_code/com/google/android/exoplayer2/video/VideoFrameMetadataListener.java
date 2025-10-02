package com.google.android.exoplayer2.video;

import android.media.MediaFormat;
import com.google.android.exoplayer2.Format;

/* loaded from: classes.dex */
public interface VideoFrameMetadataListener {
    void onVideoFrameAboutToBeRendered(long presentationTimeUs, long releaseTimeNs, Format format, MediaFormat mediaFormat);
}
