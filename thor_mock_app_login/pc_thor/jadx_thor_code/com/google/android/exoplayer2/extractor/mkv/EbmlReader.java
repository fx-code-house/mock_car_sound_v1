package com.google.android.exoplayer2.extractor.mkv;

import com.google.android.exoplayer2.extractor.ExtractorInput;
import java.io.IOException;

/* loaded from: classes.dex */
interface EbmlReader {
    void init(EbmlProcessor processor);

    boolean read(ExtractorInput input) throws IOException;

    void reset();
}
