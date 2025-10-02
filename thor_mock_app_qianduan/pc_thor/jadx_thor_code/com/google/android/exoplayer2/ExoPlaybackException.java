package com.google.android.exoplayer2;

import android.os.Bundle;
import android.os.SystemClock;
import android.text.TextUtils;
import com.google.android.exoplayer2.Bundleable;
import com.google.android.exoplayer2.source.MediaPeriodId;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes.dex */
public final class ExoPlaybackException extends PlaybackException {
    public static final Bundleable.Creator<ExoPlaybackException> CREATOR = new Bundleable.Creator() { // from class: com.google.android.exoplayer2.ExoPlaybackException$$ExternalSyntheticLambda0
        @Override // com.google.android.exoplayer2.Bundleable.Creator
        public final Bundleable fromBundle(Bundle bundle) {
            return ExoPlaybackException.$r8$lambda$mXbXdGG_PHMarv0ObcHmIhB4uIw(bundle);
        }
    };
    private static final int FIELD_IS_RECOVERABLE = 1006;
    private static final int FIELD_RENDERER_FORMAT = 1004;
    private static final int FIELD_RENDERER_FORMAT_SUPPORT = 1005;
    private static final int FIELD_RENDERER_INDEX = 1003;
    private static final int FIELD_RENDERER_NAME = 1002;
    private static final int FIELD_TYPE = 1001;
    public static final int TYPE_REMOTE = 3;
    public static final int TYPE_RENDERER = 1;
    public static final int TYPE_SOURCE = 0;
    public static final int TYPE_UNEXPECTED = 2;
    final boolean isRecoverable;
    public final MediaPeriodId mediaPeriodId;
    public final Format rendererFormat;
    public final int rendererFormatSupport;
    public final int rendererIndex;
    public final String rendererName;
    public final int type;

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface Type {
    }

    public static /* synthetic */ ExoPlaybackException $r8$lambda$mXbXdGG_PHMarv0ObcHmIhB4uIw(Bundle bundle) {
        return new ExoPlaybackException(bundle);
    }

    public static ExoPlaybackException createForSource(IOException cause, int errorCode) {
        return new ExoPlaybackException(0, cause, errorCode);
    }

    public static ExoPlaybackException createForRenderer(Throwable cause, String rendererName, int rendererIndex, Format rendererFormat, int rendererFormatSupport, boolean isRecoverable, int errorCode) {
        return new ExoPlaybackException(1, cause, null, errorCode, rendererName, rendererIndex, rendererFormat, rendererFormat == null ? 4 : rendererFormatSupport, isRecoverable);
    }

    @Deprecated
    public static ExoPlaybackException createForUnexpected(RuntimeException cause) {
        return createForUnexpected(cause, 1000);
    }

    public static ExoPlaybackException createForUnexpected(RuntimeException cause, int errorCode) {
        return new ExoPlaybackException(2, cause, errorCode);
    }

    public static ExoPlaybackException createForRemote(String message) {
        return new ExoPlaybackException(3, null, message, 1001, null, -1, null, 4, false);
    }

    private ExoPlaybackException(int type, Throwable cause, int errorCode) {
        this(type, cause, null, errorCode, null, -1, null, 4, false);
    }

    private ExoPlaybackException(int type, Throwable cause, String customMessage, int errorCode, String rendererName, int rendererIndex, Format rendererFormat, int rendererFormatSupport, boolean isRecoverable) {
        this(deriveMessage(type, customMessage, rendererName, rendererIndex, rendererFormat, rendererFormatSupport), cause, errorCode, type, rendererName, rendererIndex, rendererFormat, rendererFormatSupport, null, SystemClock.elapsedRealtime(), isRecoverable);
    }

    private ExoPlaybackException(Bundle bundle) {
        super(bundle);
        this.type = bundle.getInt(keyForField(1001), 2);
        this.rendererName = bundle.getString(keyForField(1002));
        this.rendererIndex = bundle.getInt(keyForField(1003), -1);
        this.rendererFormat = (Format) bundle.getParcelable(keyForField(1004));
        this.rendererFormatSupport = bundle.getInt(keyForField(1005), 4);
        this.isRecoverable = bundle.getBoolean(keyForField(1006), false);
        this.mediaPeriodId = null;
    }

    private ExoPlaybackException(String message, Throwable cause, int errorCode, int type, String rendererName, int rendererIndex, Format rendererFormat, int rendererFormatSupport, MediaPeriodId mediaPeriodId, long timestampMs, boolean isRecoverable) {
        super(message, cause, errorCode, timestampMs);
        Assertions.checkArgument(!isRecoverable || type == 1);
        Assertions.checkArgument(cause != null || type == 3);
        this.type = type;
        this.rendererName = rendererName;
        this.rendererIndex = rendererIndex;
        this.rendererFormat = rendererFormat;
        this.rendererFormatSupport = rendererFormatSupport;
        this.mediaPeriodId = mediaPeriodId;
        this.isRecoverable = isRecoverable;
    }

    public IOException getSourceException() {
        Assertions.checkState(this.type == 0);
        return (IOException) Assertions.checkNotNull(getCause());
    }

    public Exception getRendererException() {
        Assertions.checkState(this.type == 1);
        return (Exception) Assertions.checkNotNull(getCause());
    }

    public RuntimeException getUnexpectedException() {
        Assertions.checkState(this.type == 2);
        return (RuntimeException) Assertions.checkNotNull(getCause());
    }

    @Override // com.google.android.exoplayer2.PlaybackException
    public boolean errorInfoEquals(PlaybackException that) {
        if (!super.errorInfoEquals(that)) {
            return false;
        }
        ExoPlaybackException exoPlaybackException = (ExoPlaybackException) Util.castNonNull(that);
        return this.type == exoPlaybackException.type && Util.areEqual(this.rendererName, exoPlaybackException.rendererName) && this.rendererIndex == exoPlaybackException.rendererIndex && Util.areEqual(this.rendererFormat, exoPlaybackException.rendererFormat) && this.rendererFormatSupport == exoPlaybackException.rendererFormatSupport && Util.areEqual(this.mediaPeriodId, exoPlaybackException.mediaPeriodId) && this.isRecoverable == exoPlaybackException.isRecoverable;
    }

    ExoPlaybackException copyWithMediaPeriodId(MediaPeriodId mediaPeriodId) {
        return new ExoPlaybackException((String) Util.castNonNull(getMessage()), getCause(), this.errorCode, this.type, this.rendererName, this.rendererIndex, this.rendererFormat, this.rendererFormatSupport, mediaPeriodId, this.timestampMs, this.isRecoverable);
    }

    private static String deriveMessage(int type, String customMessage, String rendererName, int rendererIndex, Format rendererFormat, int rendererFormatSupport) {
        String string;
        if (type == 0) {
            string = "Source error";
        } else if (type != 1) {
            string = type != 3 ? "Unexpected runtime error" : "Remote error";
        } else {
            String strValueOf = String.valueOf(rendererFormat);
            String formatSupportString = C.getFormatSupportString(rendererFormatSupport);
            string = new StringBuilder(String.valueOf(rendererName).length() + 53 + String.valueOf(strValueOf).length() + String.valueOf(formatSupportString).length()).append(rendererName).append(" error, index=").append(rendererIndex).append(", format=").append(strValueOf).append(", format_supported=").append(formatSupportString).toString();
        }
        if (TextUtils.isEmpty(customMessage)) {
            return string;
        }
        String strValueOf2 = String.valueOf(string);
        return new StringBuilder(String.valueOf(strValueOf2).length() + 2 + String.valueOf(customMessage).length()).append(strValueOf2).append(": ").append(customMessage).toString();
    }

    @Override // com.google.android.exoplayer2.PlaybackException, com.google.android.exoplayer2.Bundleable
    public Bundle toBundle() {
        Bundle bundle = super.toBundle();
        bundle.putInt(keyForField(1001), this.type);
        bundle.putString(keyForField(1002), this.rendererName);
        bundle.putInt(keyForField(1003), this.rendererIndex);
        bundle.putParcelable(keyForField(1004), this.rendererFormat);
        bundle.putInt(keyForField(1005), this.rendererFormatSupport);
        bundle.putBoolean(keyForField(1006), this.isRecoverable);
        return bundle;
    }
}
