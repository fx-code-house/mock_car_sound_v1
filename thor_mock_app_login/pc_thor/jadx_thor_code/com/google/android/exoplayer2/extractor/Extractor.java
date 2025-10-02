package com.google.android.exoplayer2.extractor;

import java.io.IOException;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes.dex */
public interface Extractor {
    public static final int RESULT_CONTINUE = 0;
    public static final int RESULT_END_OF_INPUT = -1;
    public static final int RESULT_SEEK = 1;

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface ReadResult {
    }

    void init(ExtractorOutput output);

    int read(ExtractorInput input, PositionHolder seekPosition) throws IOException;

    void release();

    void seek(long position, long timeUs);

    boolean sniff(ExtractorInput input) throws IOException;
}
