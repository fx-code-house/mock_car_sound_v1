package com.google.android.exoplayer2.transformer;

import android.os.ParcelFileDescriptor;
import com.google.android.exoplayer2.Format;
import java.io.IOException;
import java.nio.ByteBuffer;

/* loaded from: classes.dex */
interface Muxer {

    public interface Factory {
        Muxer create(ParcelFileDescriptor parcelFileDescriptor, String outputMimeType) throws IOException;

        Muxer create(String path, String outputMimeType) throws IOException;

        boolean supportsOutputMimeType(String mimeType);
    }

    int addTrack(Format format);

    void release(boolean forCancellation);

    boolean supportsSampleMimeType(String mimeType);

    void writeSampleData(int trackIndex, ByteBuffer data, boolean isKeyFrame, long presentationTimeUs);
}
