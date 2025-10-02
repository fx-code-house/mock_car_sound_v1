package com.google.android.exoplayer2.offline;

import java.io.IOException;

/* loaded from: classes.dex */
public interface WritableDownloadIndex extends DownloadIndex {
    void putDownload(Download download) throws IOException;

    void removeDownload(String id) throws IOException;

    void setDownloadingStatesToQueued() throws IOException;

    void setStatesToRemoving() throws IOException;

    void setStopReason(int stopReason) throws IOException;

    void setStopReason(String id, int stopReason) throws IOException;
}
