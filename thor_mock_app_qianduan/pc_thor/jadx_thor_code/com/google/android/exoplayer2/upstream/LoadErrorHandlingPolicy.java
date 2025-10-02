package com.google.android.exoplayer2.upstream;

import com.google.android.exoplayer2.source.LoadEventInfo;
import com.google.android.exoplayer2.source.MediaLoadData;
import com.google.android.exoplayer2.util.Assertions;
import java.io.IOException;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes.dex */
public interface LoadErrorHandlingPolicy {
    public static final int FALLBACK_TYPE_LOCATION = 1;
    public static final int FALLBACK_TYPE_TRACK = 2;

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface FallbackType {
    }

    FallbackSelection getFallbackSelectionFor(FallbackOptions fallbackOptions, LoadErrorInfo loadErrorInfo);

    int getMinimumLoadableRetryCount(int dataType);

    long getRetryDelayMsFor(LoadErrorInfo loadErrorInfo);

    default void onLoadTaskConcluded(long loadTaskId) {
    }

    public static final class LoadErrorInfo {
        public final int errorCount;
        public final IOException exception;
        public final LoadEventInfo loadEventInfo;
        public final MediaLoadData mediaLoadData;

        public LoadErrorInfo(LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData, IOException exception, int errorCount) {
            this.loadEventInfo = loadEventInfo;
            this.mediaLoadData = mediaLoadData;
            this.exception = exception;
            this.errorCount = errorCount;
        }
    }

    public static final class FallbackOptions {
        public final int numberOfExcludedLocations;
        public final int numberOfExcludedTracks;
        public final int numberOfLocations;
        public final int numberOfTracks;

        public FallbackOptions(int numberOfLocations, int numberOfExcludedLocations, int numberOfTracks, int numberOfExcludedTracks) {
            this.numberOfLocations = numberOfLocations;
            this.numberOfExcludedLocations = numberOfExcludedLocations;
            this.numberOfTracks = numberOfTracks;
            this.numberOfExcludedTracks = numberOfExcludedTracks;
        }

        public boolean isFallbackAvailable(int type) {
            if (type == 1) {
                if (this.numberOfLocations - this.numberOfExcludedLocations <= 1) {
                    return false;
                }
            } else if (this.numberOfTracks - this.numberOfExcludedTracks <= 1) {
                return false;
            }
            return true;
        }
    }

    public static final class FallbackSelection {
        public final long exclusionDurationMs;
        public final int type;

        public FallbackSelection(int type, long exclusionDurationMs) {
            Assertions.checkArgument(exclusionDurationMs >= 0);
            this.type = type;
            this.exclusionDurationMs = exclusionDurationMs;
        }
    }
}
