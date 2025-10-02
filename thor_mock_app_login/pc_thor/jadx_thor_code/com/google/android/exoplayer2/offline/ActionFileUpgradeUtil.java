package com.google.android.exoplayer2.offline;

import android.database.SQLException;
import java.io.File;
import java.io.IOException;

/* loaded from: classes.dex */
public final class ActionFileUpgradeUtil {

    public interface DownloadIdProvider {
        String getId(DownloadRequest downloadRequest);
    }

    private ActionFileUpgradeUtil() {
    }

    public static void upgradeAndDelete(File actionFilePath, DownloadIdProvider downloadIdProvider, DefaultDownloadIndex downloadIndex, boolean deleteOnFailure, boolean addNewDownloadsAsCompleted) throws IOException {
        ActionFile actionFile = new ActionFile(actionFilePath);
        if (actionFile.exists()) {
            try {
                long jCurrentTimeMillis = System.currentTimeMillis();
                for (DownloadRequest downloadRequestCopyWithId : actionFile.load()) {
                    if (downloadIdProvider != null) {
                        downloadRequestCopyWithId = downloadRequestCopyWithId.copyWithId(downloadIdProvider.getId(downloadRequestCopyWithId));
                    }
                    mergeRequest(downloadRequestCopyWithId, downloadIndex, addNewDownloadsAsCompleted, jCurrentTimeMillis);
                }
                actionFile.delete();
            } catch (Throwable th) {
                if (deleteOnFailure) {
                    actionFile.delete();
                }
                throw th;
            }
        }
    }

    static void mergeRequest(DownloadRequest request, DefaultDownloadIndex downloadIndex, boolean addNewDownloadAsCompleted, long nowMs) throws IOException, SQLException {
        Download download;
        Download download2 = downloadIndex.getDownload(request.id);
        if (download2 != null) {
            download = DownloadManager.mergeRequest(download2, request, download2.stopReason, nowMs);
        } else {
            download = new Download(request, addNewDownloadAsCompleted ? 3 : 0, nowMs, nowMs, -1L, 0, 0);
        }
        downloadIndex.putDownload(download);
    }
}
