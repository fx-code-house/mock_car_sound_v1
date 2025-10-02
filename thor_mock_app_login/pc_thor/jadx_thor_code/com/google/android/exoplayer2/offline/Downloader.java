package com.google.android.exoplayer2.offline;

import java.io.IOException;

/* loaded from: classes.dex */
public interface Downloader {

    public interface ProgressListener {
        void onProgress(long contentLength, long bytesDownloaded, float percentDownloaded);
    }

    void cancel();

    void download(ProgressListener progressListener) throws InterruptedException, IOException;

    void remove();
}
