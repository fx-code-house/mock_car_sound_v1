package com.google.android.exoplayer2.transformer;

import android.media.MediaCodec;
import android.media.MediaFormat;
import android.media.MediaMuxer;
import android.os.ParcelFileDescriptor;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.transformer.Muxer;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.MediaFormatUtil;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;

/* loaded from: classes.dex */
final class FrameworkMuxer implements Muxer {
    private final MediaCodec.BufferInfo bufferInfo;
    private boolean isStarted;
    private final MediaMuxer mediaMuxer;
    private final String outputMimeType;

    public static final class Factory implements Muxer.Factory {
        @Override // com.google.android.exoplayer2.transformer.Muxer.Factory
        public FrameworkMuxer create(String path, String outputMimeType) throws IOException {
            return new FrameworkMuxer(new MediaMuxer(path, FrameworkMuxer.mimeTypeToMuxerOutputFormat(outputMimeType)), outputMimeType);
        }

        @Override // com.google.android.exoplayer2.transformer.Muxer.Factory
        public FrameworkMuxer create(ParcelFileDescriptor parcelFileDescriptor, String outputMimeType) throws IOException {
            return new FrameworkMuxer(new MediaMuxer(parcelFileDescriptor.getFileDescriptor(), FrameworkMuxer.mimeTypeToMuxerOutputFormat(outputMimeType)), outputMimeType);
        }

        @Override // com.google.android.exoplayer2.transformer.Muxer.Factory
        public boolean supportsOutputMimeType(String mimeType) {
            try {
                FrameworkMuxer.mimeTypeToMuxerOutputFormat(mimeType);
                return true;
            } catch (IllegalStateException unused) {
                return false;
            }
        }
    }

    private FrameworkMuxer(MediaMuxer mediaMuxer, String outputMimeType) {
        this.mediaMuxer = mediaMuxer;
        this.outputMimeType = outputMimeType;
        this.bufferInfo = new MediaCodec.BufferInfo();
    }

    @Override // com.google.android.exoplayer2.transformer.Muxer
    public boolean supportsSampleMimeType(String mimeType) {
        boolean zIsAudio = MimeTypes.isAudio(mimeType);
        boolean zIsVideo = MimeTypes.isVideo(mimeType);
        if (this.outputMimeType.equals(MimeTypes.VIDEO_MP4)) {
            if (zIsVideo) {
                if (MimeTypes.VIDEO_H263.equals(mimeType) || MimeTypes.VIDEO_H264.equals(mimeType) || MimeTypes.VIDEO_MP4V.equals(mimeType)) {
                    return true;
                }
                return Util.SDK_INT >= 24 && MimeTypes.VIDEO_H265.equals(mimeType);
            }
            if (zIsAudio) {
                return MimeTypes.AUDIO_AAC.equals(mimeType) || MimeTypes.AUDIO_AMR_NB.equals(mimeType) || MimeTypes.AUDIO_AMR_WB.equals(mimeType);
            }
        } else if (this.outputMimeType.equals(MimeTypes.VIDEO_WEBM) && Util.SDK_INT >= 21) {
            if (zIsVideo) {
                if (MimeTypes.VIDEO_VP8.equals(mimeType)) {
                    return true;
                }
                return Util.SDK_INT >= 24 && MimeTypes.VIDEO_VP9.equals(mimeType);
            }
            if (zIsAudio) {
                return MimeTypes.AUDIO_VORBIS.equals(mimeType);
            }
        }
        return false;
    }

    @Override // com.google.android.exoplayer2.transformer.Muxer
    public int addTrack(Format format) {
        MediaFormat mediaFormatCreateVideoFormat;
        String str = (String) Assertions.checkNotNull(format.sampleMimeType);
        if (MimeTypes.isAudio(str)) {
            mediaFormatCreateVideoFormat = MediaFormat.createAudioFormat((String) Util.castNonNull(str), format.sampleRate, format.channelCount);
        } else {
            mediaFormatCreateVideoFormat = MediaFormat.createVideoFormat((String) Util.castNonNull(str), format.width, format.height);
            this.mediaMuxer.setOrientationHint(format.rotationDegrees);
        }
        MediaFormatUtil.setCsdBuffers(mediaFormatCreateVideoFormat, format.initializationData);
        return this.mediaMuxer.addTrack(mediaFormatCreateVideoFormat);
    }

    @Override // com.google.android.exoplayer2.transformer.Muxer
    public void writeSampleData(int i, ByteBuffer byteBuffer, boolean z, long j) {
        if (!this.isStarted) {
            this.isStarted = true;
            this.mediaMuxer.start();
        }
        int iPosition = byteBuffer.position();
        this.bufferInfo.set(iPosition, byteBuffer.limit() - iPosition, j, z ? 1 : 0);
        this.mediaMuxer.writeSampleData(i, byteBuffer, this.bufferInfo);
    }

    @Override // com.google.android.exoplayer2.transformer.Muxer
    public void release(boolean forCancellation) {
        if (this.isStarted) {
            this.isStarted = false;
            try {
                try {
                    this.mediaMuxer.stop();
                } catch (IllegalStateException e) {
                    if (Util.SDK_INT < 30) {
                        try {
                            Field declaredField = MediaMuxer.class.getDeclaredField("MUXER_STATE_STOPPED");
                            declaredField.setAccessible(true);
                            int iIntValue = ((Integer) Util.castNonNull((Integer) declaredField.get(this.mediaMuxer))).intValue();
                            Field declaredField2 = MediaMuxer.class.getDeclaredField("mState");
                            declaredField2.setAccessible(true);
                            declaredField2.set(this.mediaMuxer, Integer.valueOf(iIntValue));
                        } catch (Exception unused) {
                        }
                    }
                    if (!forCancellation) {
                        throw e;
                    }
                }
            } finally {
                this.mediaMuxer.release();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int mimeTypeToMuxerOutputFormat(String mimeType) {
        if (mimeType.equals(MimeTypes.VIDEO_MP4)) {
            return 0;
        }
        if (Util.SDK_INT >= 21 && mimeType.equals(MimeTypes.VIDEO_WEBM)) {
            return 1;
        }
        String strValueOf = String.valueOf(mimeType);
        throw new IllegalArgumentException(strValueOf.length() != 0 ? "Unsupported output MIME type: ".concat(strValueOf) : new String("Unsupported output MIME type: "));
    }
}
