package com.google.android.exoplayer2.mediacodec;

import android.media.MediaCodec;
import com.google.android.exoplayer2.decoder.DecoderException;
import com.google.android.exoplayer2.util.Util;

/* loaded from: classes.dex */
public class MediaCodecDecoderException extends DecoderException {
    public final MediaCodecInfo codecInfo;
    public final String diagnosticInfo;

    /* JADX WARN: Illegal instructions before constructor call */
    public MediaCodecDecoderException(Throwable cause, MediaCodecInfo codecInfo) {
        String strValueOf = String.valueOf(codecInfo == null ? null : codecInfo.name);
        super(strValueOf.length() != 0 ? "Decoder failed: ".concat(strValueOf) : new String("Decoder failed: "), cause);
        this.codecInfo = codecInfo;
        this.diagnosticInfo = Util.SDK_INT >= 21 ? getDiagnosticInfoV21(cause) : null;
    }

    private static String getDiagnosticInfoV21(Throwable cause) {
        if (cause instanceof MediaCodec.CodecException) {
            return ((MediaCodec.CodecException) cause).getDiagnosticInfo();
        }
        return null;
    }
}
