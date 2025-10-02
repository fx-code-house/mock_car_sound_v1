package com.google.android.exoplayer2.offline;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import androidx.credentials.CredentialManager$$ExternalSyntheticLambda0;
import com.google.android.exoplayer2.database.DatabaseProvider;
import com.google.android.exoplayer2.offline.Downloader;
import com.google.android.exoplayer2.scheduler.Requirements;
import com.google.android.exoplayer2.scheduler.RequirementsWatcher;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.cache.Cache;
import com.google.android.exoplayer2.upstream.cache.CacheDataSource;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.Executor;

/* loaded from: classes.dex */
public final class DownloadManager {
    public static final int DEFAULT_MAX_PARALLEL_DOWNLOADS = 3;
    public static final int DEFAULT_MIN_RETRY_COUNT = 5;
    public static final Requirements DEFAULT_REQUIREMENTS = new Requirements(1);
    private static final int MSG_ADD_DOWNLOAD = 6;
    private static final int MSG_CONTENT_LENGTH_CHANGED = 10;
    private static final int MSG_DOWNLOAD_UPDATE = 2;
    private static final int MSG_INITIALIZE = 0;
    private static final int MSG_INITIALIZED = 0;
    private static final int MSG_PROCESSED = 1;
    private static final int MSG_RELEASE = 12;
    private static final int MSG_REMOVE_ALL_DOWNLOADS = 8;
    private static final int MSG_REMOVE_DOWNLOAD = 7;
    private static final int MSG_SET_DOWNLOADS_PAUSED = 1;
    private static final int MSG_SET_MAX_PARALLEL_DOWNLOADS = 4;
    private static final int MSG_SET_MIN_RETRY_COUNT = 5;
    private static final int MSG_SET_NOT_MET_REQUIREMENTS = 2;
    private static final int MSG_SET_STOP_REASON = 3;
    private static final int MSG_TASK_STOPPED = 9;
    private static final int MSG_UPDATE_PROGRESS = 11;
    private static final String TAG = "DownloadManager";
    private int activeTaskCount;
    private final Handler applicationHandler;
    private final Context context;
    private final WritableDownloadIndex downloadIndex;
    private List<Download> downloads;
    private boolean downloadsPaused;
    private boolean initialized;
    private final InternalHandler internalHandler;
    private final CopyOnWriteArraySet<Listener> listeners;
    private int maxParallelDownloads;
    private int minRetryCount;
    private int notMetRequirements;
    private int pendingMessages;
    private final RequirementsWatcher.Listener requirementsListener;
    private RequirementsWatcher requirementsWatcher;
    private boolean waitingForRequirements;

    public interface Listener {
        default void onDownloadChanged(DownloadManager downloadManager, Download download, Exception finalException) {
        }

        default void onDownloadRemoved(DownloadManager downloadManager, Download download) {
        }

        default void onDownloadsPausedChanged(DownloadManager downloadManager, boolean downloadsPaused) {
        }

        default void onIdle(DownloadManager downloadManager) {
        }

        default void onInitialized(DownloadManager downloadManager) {
        }

        default void onRequirementsStateChanged(DownloadManager downloadManager, Requirements requirements, int notMetRequirements) {
        }

        default void onWaitingForRequirementsChanged(DownloadManager downloadManager, boolean waitingForRequirements) {
        }
    }

    @Deprecated
    public DownloadManager(Context context, DatabaseProvider databaseProvider, Cache cache, DataSource.Factory upstreamFactory) {
        this(context, databaseProvider, cache, upstreamFactory, new CredentialManager$$ExternalSyntheticLambda0());
    }

    public DownloadManager(Context context, DatabaseProvider databaseProvider, Cache cache, DataSource.Factory upstreamFactory, Executor executor) {
        this(context, new DefaultDownloadIndex(databaseProvider), new DefaultDownloaderFactory(new CacheDataSource.Factory().setCache(cache).setUpstreamDataSourceFactory(upstreamFactory), executor));
    }

    public DownloadManager(Context context, WritableDownloadIndex downloadIndex, DownloaderFactory downloaderFactory) {
        this.context = context.getApplicationContext();
        this.downloadIndex = downloadIndex;
        this.maxParallelDownloads = 3;
        this.minRetryCount = 5;
        this.downloadsPaused = true;
        this.downloads = Collections.emptyList();
        this.listeners = new CopyOnWriteArraySet<>();
        Handler handlerCreateHandlerForCurrentOrMainLooper = Util.createHandlerForCurrentOrMainLooper(new Handler.Callback() { // from class: com.google.android.exoplayer2.offline.DownloadManager$$ExternalSyntheticLambda0
            @Override // android.os.Handler.Callback
            public final boolean handleMessage(Message message) {
                return this.f$0.handleMainMessage(message);
            }
        });
        this.applicationHandler = handlerCreateHandlerForCurrentOrMainLooper;
        HandlerThread handlerThread = new HandlerThread("ExoPlayer:DownloadManager");
        handlerThread.start();
        InternalHandler internalHandler = new InternalHandler(handlerThread, downloadIndex, downloaderFactory, handlerCreateHandlerForCurrentOrMainLooper, this.maxParallelDownloads, this.minRetryCount, this.downloadsPaused);
        this.internalHandler = internalHandler;
        RequirementsWatcher.Listener listener = new RequirementsWatcher.Listener() { // from class: com.google.android.exoplayer2.offline.DownloadManager$$ExternalSyntheticLambda1
            @Override // com.google.android.exoplayer2.scheduler.RequirementsWatcher.Listener
            public final void onRequirementsStateChanged(RequirementsWatcher requirementsWatcher, int i) {
                this.f$0.onRequirementsStateChanged(requirementsWatcher, i);
            }
        };
        this.requirementsListener = listener;
        RequirementsWatcher requirementsWatcher = new RequirementsWatcher(context, listener, DEFAULT_REQUIREMENTS);
        this.requirementsWatcher = requirementsWatcher;
        int iStart = requirementsWatcher.start();
        this.notMetRequirements = iStart;
        this.pendingMessages = 1;
        internalHandler.obtainMessage(0, iStart, 0).sendToTarget();
    }

    public Looper getApplicationLooper() {
        return this.applicationHandler.getLooper();
    }

    public boolean isInitialized() {
        return this.initialized;
    }

    public boolean isIdle() {
        return this.activeTaskCount == 0 && this.pendingMessages == 0;
    }

    public boolean isWaitingForRequirements() {
        return this.waitingForRequirements;
    }

    public void addListener(Listener listener) {
        Assertions.checkNotNull(listener);
        this.listeners.add(listener);
    }

    public void removeListener(Listener listener) {
        this.listeners.remove(listener);
    }

    public Requirements getRequirements() {
        return this.requirementsWatcher.getRequirements();
    }

    public int getNotMetRequirements() {
        return this.notMetRequirements;
    }

    public void setRequirements(Requirements requirements) {
        if (requirements.equals(this.requirementsWatcher.getRequirements())) {
            return;
        }
        this.requirementsWatcher.stop();
        RequirementsWatcher requirementsWatcher = new RequirementsWatcher(this.context, this.requirementsListener, requirements);
        this.requirementsWatcher = requirementsWatcher;
        onRequirementsStateChanged(this.requirementsWatcher, requirementsWatcher.start());
    }

    public int getMaxParallelDownloads() {
        return this.maxParallelDownloads;
    }

    public void setMaxParallelDownloads(int maxParallelDownloads) {
        Assertions.checkArgument(maxParallelDownloads > 0);
        if (this.maxParallelDownloads == maxParallelDownloads) {
            return;
        }
        this.maxParallelDownloads = maxParallelDownloads;
        this.pendingMessages++;
        this.internalHandler.obtainMessage(4, maxParallelDownloads, 0).sendToTarget();
    }

    public int getMinRetryCount() {
        return this.minRetryCount;
    }

    public void setMinRetryCount(int minRetryCount) {
        Assertions.checkArgument(minRetryCount >= 0);
        if (this.minRetryCount == minRetryCount) {
            return;
        }
        this.minRetryCount = minRetryCount;
        this.pendingMessages++;
        this.internalHandler.obtainMessage(5, minRetryCount, 0).sendToTarget();
    }

    public DownloadIndex getDownloadIndex() {
        return this.downloadIndex;
    }

    public List<Download> getCurrentDownloads() {
        return this.downloads;
    }

    public boolean getDownloadsPaused() {
        return this.downloadsPaused;
    }

    public void resumeDownloads() {
        setDownloadsPaused(false);
    }

    public void pauseDownloads() {
        setDownloadsPaused(true);
    }

    public void setStopReason(String id, int stopReason) {
        this.pendingMessages++;
        this.internalHandler.obtainMessage(3, stopReason, 0, id).sendToTarget();
    }

    public void addDownload(DownloadRequest request) {
        addDownload(request, 0);
    }

    public void addDownload(DownloadRequest request, int stopReason) {
        this.pendingMessages++;
        this.internalHandler.obtainMessage(6, stopReason, 0, request).sendToTarget();
    }

    public void removeDownload(String id) {
        this.pendingMessages++;
        this.internalHandler.obtainMessage(7, id).sendToTarget();
    }

    public void removeAllDownloads() {
        this.pendingMessages++;
        this.internalHandler.obtainMessage(8).sendToTarget();
    }

    public void release() {
        synchronized (this.internalHandler) {
            if (this.internalHandler.released) {
                return;
            }
            this.internalHandler.sendEmptyMessage(12);
            boolean z = false;
            while (!this.internalHandler.released) {
                try {
                    this.internalHandler.wait();
                } catch (InterruptedException unused) {
                    z = true;
                }
            }
            if (z) {
                Thread.currentThread().interrupt();
            }
            this.applicationHandler.removeCallbacksAndMessages(null);
            this.downloads = Collections.emptyList();
            this.pendingMessages = 0;
            this.activeTaskCount = 0;
            this.initialized = false;
            this.notMetRequirements = 0;
            this.waitingForRequirements = false;
        }
    }

    private void setDownloadsPaused(boolean z) {
        if (this.downloadsPaused == z) {
            return;
        }
        this.downloadsPaused = z;
        this.pendingMessages++;
        this.internalHandler.obtainMessage(1, z ? 1 : 0, 0).sendToTarget();
        boolean zUpdateWaitingForRequirements = updateWaitingForRequirements();
        Iterator<Listener> it = this.listeners.iterator();
        while (it.hasNext()) {
            it.next().onDownloadsPausedChanged(this, z);
        }
        if (zUpdateWaitingForRequirements) {
            notifyWaitingForRequirementsChanged();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onRequirementsStateChanged(RequirementsWatcher requirementsWatcher, int notMetRequirements) {
        Requirements requirements = requirementsWatcher.getRequirements();
        if (this.notMetRequirements != notMetRequirements) {
            this.notMetRequirements = notMetRequirements;
            this.pendingMessages++;
            this.internalHandler.obtainMessage(2, notMetRequirements, 0).sendToTarget();
        }
        boolean zUpdateWaitingForRequirements = updateWaitingForRequirements();
        Iterator<Listener> it = this.listeners.iterator();
        while (it.hasNext()) {
            it.next().onRequirementsStateChanged(this, requirements, notMetRequirements);
        }
        if (zUpdateWaitingForRequirements) {
            notifyWaitingForRequirementsChanged();
        }
    }

    private boolean updateWaitingForRequirements() {
        boolean z;
        if (this.downloadsPaused || this.notMetRequirements == 0) {
            z = false;
        } else {
            for (int i = 0; i < this.downloads.size(); i++) {
                if (this.downloads.get(i).state == 0) {
                    z = true;
                    break;
                }
            }
            z = false;
        }
        boolean z2 = this.waitingForRequirements != z;
        this.waitingForRequirements = z;
        return z2;
    }

    private void notifyWaitingForRequirementsChanged() {
        Iterator<Listener> it = this.listeners.iterator();
        while (it.hasNext()) {
            it.next().onWaitingForRequirementsChanged(this, this.waitingForRequirements);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean handleMainMessage(Message message) {
        int i = message.what;
        if (i == 0) {
            onInitialized((List) message.obj);
        } else if (i == 1) {
            onMessageProcessed(message.arg1, message.arg2);
        } else if (i == 2) {
            onDownloadUpdate((DownloadUpdate) message.obj);
        } else {
            throw new IllegalStateException();
        }
        return true;
    }

    private void onInitialized(List<Download> downloads) {
        this.initialized = true;
        this.downloads = Collections.unmodifiableList(downloads);
        boolean zUpdateWaitingForRequirements = updateWaitingForRequirements();
        Iterator<Listener> it = this.listeners.iterator();
        while (it.hasNext()) {
            it.next().onInitialized(this);
        }
        if (zUpdateWaitingForRequirements) {
            notifyWaitingForRequirementsChanged();
        }
    }

    private void onDownloadUpdate(DownloadUpdate update) {
        this.downloads = Collections.unmodifiableList(update.downloads);
        Download download = update.download;
        boolean zUpdateWaitingForRequirements = updateWaitingForRequirements();
        if (update.isRemove) {
            Iterator<Listener> it = this.listeners.iterator();
            while (it.hasNext()) {
                it.next().onDownloadRemoved(this, download);
            }
        } else {
            Iterator<Listener> it2 = this.listeners.iterator();
            while (it2.hasNext()) {
                it2.next().onDownloadChanged(this, download, update.finalException);
            }
        }
        if (zUpdateWaitingForRequirements) {
            notifyWaitingForRequirementsChanged();
        }
    }

    private void onMessageProcessed(int processedMessageCount, int activeTaskCount) {
        this.pendingMessages -= processedMessageCount;
        this.activeTaskCount = activeTaskCount;
        if (isIdle()) {
            Iterator<Listener> it = this.listeners.iterator();
            while (it.hasNext()) {
                it.next().onIdle(this);
            }
        }
    }

    static Download mergeRequest(Download download, DownloadRequest request, int stopReason, long nowMs) {
        int i;
        int i2 = download.state;
        long j = (i2 == 5 || download.isTerminalState()) ? nowMs : download.startTimeMs;
        if (i2 == 5 || i2 == 7) {
            i = 7;
        } else {
            i = stopReason != 0 ? 1 : 0;
        }
        return new Download(download.request.copyWithMergedRequest(request), i, j, nowMs, -1L, stopReason, 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class InternalHandler extends Handler {
        private static final int UPDATE_PROGRESS_INTERVAL_MS = 5000;
        private int activeDownloadTaskCount;
        private final HashMap<String, Task> activeTasks;
        private final WritableDownloadIndex downloadIndex;
        private final DownloaderFactory downloaderFactory;
        private final ArrayList<Download> downloads;
        private boolean downloadsPaused;
        private final Handler mainHandler;
        private int maxParallelDownloads;
        private int minRetryCount;
        private int notMetRequirements;
        public boolean released;
        private final HandlerThread thread;

        public InternalHandler(HandlerThread thread, WritableDownloadIndex downloadIndex, DownloaderFactory downloaderFactory, Handler mainHandler, int maxParallelDownloads, int minRetryCount, boolean downloadsPaused) {
            super(thread.getLooper());
            this.thread = thread;
            this.downloadIndex = downloadIndex;
            this.downloaderFactory = downloaderFactory;
            this.mainHandler = mainHandler;
            this.maxParallelDownloads = maxParallelDownloads;
            this.minRetryCount = minRetryCount;
            this.downloadsPaused = downloadsPaused;
            this.downloads = new ArrayList<>();
            this.activeTasks = new HashMap<>();
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) throws IOException {
            int i = 0;
            switch (message.what) {
                case 0:
                    initialize(message.arg1);
                    i = 1;
                    this.mainHandler.obtainMessage(1, i, this.activeTasks.size()).sendToTarget();
                    return;
                case 1:
                    setDownloadsPaused(message.arg1 != 0);
                    i = 1;
                    this.mainHandler.obtainMessage(1, i, this.activeTasks.size()).sendToTarget();
                    return;
                case 2:
                    setNotMetRequirements(message.arg1);
                    i = 1;
                    this.mainHandler.obtainMessage(1, i, this.activeTasks.size()).sendToTarget();
                    return;
                case 3:
                    setStopReason((String) message.obj, message.arg1);
                    i = 1;
                    this.mainHandler.obtainMessage(1, i, this.activeTasks.size()).sendToTarget();
                    return;
                case 4:
                    setMaxParallelDownloads(message.arg1);
                    i = 1;
                    this.mainHandler.obtainMessage(1, i, this.activeTasks.size()).sendToTarget();
                    return;
                case 5:
                    setMinRetryCount(message.arg1);
                    i = 1;
                    this.mainHandler.obtainMessage(1, i, this.activeTasks.size()).sendToTarget();
                    return;
                case 6:
                    addDownload((DownloadRequest) message.obj, message.arg1);
                    i = 1;
                    this.mainHandler.obtainMessage(1, i, this.activeTasks.size()).sendToTarget();
                    return;
                case 7:
                    removeDownload((String) message.obj);
                    i = 1;
                    this.mainHandler.obtainMessage(1, i, this.activeTasks.size()).sendToTarget();
                    return;
                case 8:
                    removeAllDownloads();
                    i = 1;
                    this.mainHandler.obtainMessage(1, i, this.activeTasks.size()).sendToTarget();
                    return;
                case 9:
                    onTaskStopped((Task) message.obj);
                    this.mainHandler.obtainMessage(1, i, this.activeTasks.size()).sendToTarget();
                    return;
                case 10:
                    onContentLengthChanged((Task) message.obj, Util.toLong(message.arg1, message.arg2));
                    return;
                case 11:
                    updateProgress();
                    return;
                case 12:
                    release();
                    return;
                default:
                    throw new IllegalStateException();
            }
        }

        private void initialize(int notMetRequirements) throws IOException {
            this.notMetRequirements = notMetRequirements;
            DownloadCursor downloads = null;
            try {
                try {
                    this.downloadIndex.setDownloadingStatesToQueued();
                    downloads = this.downloadIndex.getDownloads(0, 1, 2, 5, 7);
                    while (downloads.moveToNext()) {
                        this.downloads.add(downloads.getDownload());
                    }
                } catch (IOException e) {
                    Log.e(DownloadManager.TAG, "Failed to load index.", e);
                    this.downloads.clear();
                }
                Util.closeQuietly(downloads);
                this.mainHandler.obtainMessage(0, new ArrayList(this.downloads)).sendToTarget();
                syncTasks();
            } catch (Throwable th) {
                Util.closeQuietly(downloads);
                throw th;
            }
        }

        private void setDownloadsPaused(boolean downloadsPaused) {
            this.downloadsPaused = downloadsPaused;
            syncTasks();
        }

        private void setNotMetRequirements(int notMetRequirements) {
            this.notMetRequirements = notMetRequirements;
            syncTasks();
        }

        private void setStopReason(String id, int stopReason) {
            if (id == null) {
                for (int i = 0; i < this.downloads.size(); i++) {
                    setStopReason(this.downloads.get(i), stopReason);
                }
                try {
                    this.downloadIndex.setStopReason(stopReason);
                } catch (IOException e) {
                    Log.e(DownloadManager.TAG, "Failed to set manual stop reason", e);
                }
            } else {
                Download download = getDownload(id, false);
                if (download != null) {
                    setStopReason(download, stopReason);
                } else {
                    try {
                        this.downloadIndex.setStopReason(id, stopReason);
                    } catch (IOException e2) {
                        String strValueOf = String.valueOf(id);
                        Log.e(DownloadManager.TAG, strValueOf.length() != 0 ? "Failed to set manual stop reason: ".concat(strValueOf) : new String("Failed to set manual stop reason: "), e2);
                    }
                }
            }
            syncTasks();
        }

        private void setStopReason(Download download, int stopReason) {
            if (stopReason == 0) {
                if (download.state == 1) {
                    putDownloadWithState(download, 0, 0);
                }
            } else if (stopReason != download.stopReason) {
                int i = download.state;
                if (i == 0 || i == 2) {
                    i = 1;
                }
                putDownload(new Download(download.request, i, download.startTimeMs, System.currentTimeMillis(), download.contentLength, stopReason, 0, download.progress));
            }
        }

        private void setMaxParallelDownloads(int maxParallelDownloads) {
            this.maxParallelDownloads = maxParallelDownloads;
            syncTasks();
        }

        private void setMinRetryCount(int minRetryCount) {
            this.minRetryCount = minRetryCount;
        }

        private void addDownload(DownloadRequest request, int stopReason) {
            Download download = getDownload(request.id, true);
            long jCurrentTimeMillis = System.currentTimeMillis();
            if (download != null) {
                putDownload(DownloadManager.mergeRequest(download, request, stopReason, jCurrentTimeMillis));
            } else {
                putDownload(new Download(request, stopReason == 0 ? 0 : 1, jCurrentTimeMillis, jCurrentTimeMillis, -1L, stopReason, 0));
            }
            syncTasks();
        }

        private void removeDownload(String id) {
            Download download = getDownload(id, true);
            if (download == null) {
                String strValueOf = String.valueOf(id);
                Log.e(DownloadManager.TAG, strValueOf.length() != 0 ? "Failed to remove nonexistent download: ".concat(strValueOf) : new String("Failed to remove nonexistent download: "));
            } else {
                putDownloadWithState(download, 5, 0);
                syncTasks();
            }
        }

        private void removeAllDownloads() {
            ArrayList arrayList = new ArrayList();
            try {
                DownloadCursor downloads = this.downloadIndex.getDownloads(3, 4);
                while (downloads.moveToNext()) {
                    try {
                        arrayList.add(downloads.getDownload());
                    } finally {
                    }
                }
                if (downloads != null) {
                    downloads.close();
                }
            } catch (IOException unused) {
                Log.e(DownloadManager.TAG, "Failed to load downloads.");
            }
            for (int i = 0; i < this.downloads.size(); i++) {
                ArrayList<Download> arrayList2 = this.downloads;
                arrayList2.set(i, copyDownloadWithState(arrayList2.get(i), 5, 0));
            }
            for (int i2 = 0; i2 < arrayList.size(); i2++) {
                this.downloads.add(copyDownloadWithState((Download) arrayList.get(i2), 5, 0));
            }
            Collections.sort(this.downloads, new DownloadManager$InternalHandler$$ExternalSyntheticLambda0());
            try {
                this.downloadIndex.setStatesToRemoving();
            } catch (IOException e) {
                Log.e(DownloadManager.TAG, "Failed to update index.", e);
            }
            ArrayList arrayList3 = new ArrayList(this.downloads);
            for (int i3 = 0; i3 < this.downloads.size(); i3++) {
                this.mainHandler.obtainMessage(2, new DownloadUpdate(this.downloads.get(i3), false, arrayList3, null)).sendToTarget();
            }
            syncTasks();
        }

        private void release() {
            Iterator<Task> it = this.activeTasks.values().iterator();
            while (it.hasNext()) {
                it.next().cancel(true);
            }
            try {
                this.downloadIndex.setDownloadingStatesToQueued();
            } catch (IOException e) {
                Log.e(DownloadManager.TAG, "Failed to update index.", e);
            }
            this.downloads.clear();
            this.thread.quit();
            synchronized (this) {
                this.released = true;
                notifyAll();
            }
        }

        private void syncTasks() {
            int i = 0;
            for (int i2 = 0; i2 < this.downloads.size(); i2++) {
                Download download = this.downloads.get(i2);
                Task taskSyncQueuedDownload = this.activeTasks.get(download.request.id);
                int i3 = download.state;
                if (i3 == 0) {
                    taskSyncQueuedDownload = syncQueuedDownload(taskSyncQueuedDownload, download);
                } else if (i3 == 1) {
                    syncStoppedDownload(taskSyncQueuedDownload);
                } else if (i3 == 2) {
                    Assertions.checkNotNull(taskSyncQueuedDownload);
                    syncDownloadingDownload(taskSyncQueuedDownload, download, i);
                } else if (i3 == 5 || i3 == 7) {
                    syncRemovingDownload(taskSyncQueuedDownload, download);
                } else {
                    throw new IllegalStateException();
                }
                if (taskSyncQueuedDownload != null && !taskSyncQueuedDownload.isRemove) {
                    i++;
                }
            }
        }

        private void syncStoppedDownload(Task activeTask) {
            if (activeTask != null) {
                Assertions.checkState(!activeTask.isRemove);
                activeTask.cancel(false);
            }
        }

        private Task syncQueuedDownload(Task activeTask, Download download) {
            if (activeTask == null) {
                if (!canDownloadsRun() || this.activeDownloadTaskCount >= this.maxParallelDownloads) {
                    return null;
                }
                Download downloadPutDownloadWithState = putDownloadWithState(download, 2, 0);
                Task task = new Task(downloadPutDownloadWithState.request, this.downloaderFactory.createDownloader(downloadPutDownloadWithState.request), downloadPutDownloadWithState.progress, false, this.minRetryCount, this);
                this.activeTasks.put(downloadPutDownloadWithState.request.id, task);
                int i = this.activeDownloadTaskCount;
                this.activeDownloadTaskCount = i + 1;
                if (i == 0) {
                    sendEmptyMessageDelayed(11, 5000L);
                }
                task.start();
                return task;
            }
            Assertions.checkState(!activeTask.isRemove);
            activeTask.cancel(false);
            return activeTask;
        }

        private void syncDownloadingDownload(Task activeTask, Download download, int accumulatingDownloadTaskCount) {
            Assertions.checkState(!activeTask.isRemove);
            if (!canDownloadsRun() || accumulatingDownloadTaskCount >= this.maxParallelDownloads) {
                putDownloadWithState(download, 0, 0);
                activeTask.cancel(false);
            }
        }

        private void syncRemovingDownload(Task activeTask, Download download) {
            if (activeTask == null) {
                Task task = new Task(download.request, this.downloaderFactory.createDownloader(download.request), download.progress, true, this.minRetryCount, this);
                this.activeTasks.put(download.request.id, task);
                task.start();
                return;
            }
            if (activeTask.isRemove) {
                return;
            }
            activeTask.cancel(false);
        }

        private void onContentLengthChanged(Task task, long contentLength) {
            Download download = (Download) Assertions.checkNotNull(getDownload(task.request.id, false));
            if (contentLength == download.contentLength || contentLength == -1) {
                return;
            }
            putDownload(new Download(download.request, download.state, download.startTimeMs, System.currentTimeMillis(), contentLength, download.stopReason, download.failureReason, download.progress));
        }

        private void onTaskStopped(Task task) {
            String str = task.request.id;
            this.activeTasks.remove(str);
            boolean z = task.isRemove;
            if (!z) {
                int i = this.activeDownloadTaskCount - 1;
                this.activeDownloadTaskCount = i;
                if (i == 0) {
                    removeMessages(11);
                }
            }
            if (task.isCanceled) {
                syncTasks();
                return;
            }
            Exception exc = task.finalException;
            if (exc != null) {
                String strValueOf = String.valueOf(task.request);
                Log.e(DownloadManager.TAG, new StringBuilder(String.valueOf(strValueOf).length() + 20).append("Task failed: ").append(strValueOf).append(", ").append(z).toString(), exc);
            }
            Download download = (Download) Assertions.checkNotNull(getDownload(str, false));
            int i2 = download.state;
            if (i2 == 2) {
                Assertions.checkState(!z);
                onDownloadTaskStopped(download, exc);
            } else if (i2 == 5 || i2 == 7) {
                Assertions.checkState(z);
                onRemoveTaskStopped(download);
            } else {
                throw new IllegalStateException();
            }
            syncTasks();
        }

        private void onDownloadTaskStopped(Download download, Exception finalException) {
            Download download2 = new Download(download.request, finalException == null ? 3 : 4, download.startTimeMs, System.currentTimeMillis(), download.contentLength, download.stopReason, finalException == null ? 0 : 1, download.progress);
            this.downloads.remove(getDownloadIndex(download2.request.id));
            try {
                this.downloadIndex.putDownload(download2);
            } catch (IOException e) {
                Log.e(DownloadManager.TAG, "Failed to update index.", e);
            }
            this.mainHandler.obtainMessage(2, new DownloadUpdate(download2, false, new ArrayList(this.downloads), finalException)).sendToTarget();
        }

        private void onRemoveTaskStopped(Download download) {
            if (download.state == 7) {
                putDownloadWithState(download, download.stopReason == 0 ? 0 : 1, download.stopReason);
                syncTasks();
            } else {
                this.downloads.remove(getDownloadIndex(download.request.id));
                try {
                    this.downloadIndex.removeDownload(download.request.id);
                } catch (IOException unused) {
                    Log.e(DownloadManager.TAG, "Failed to remove from database");
                }
                this.mainHandler.obtainMessage(2, new DownloadUpdate(download, true, new ArrayList(this.downloads), null)).sendToTarget();
            }
        }

        private void updateProgress() {
            for (int i = 0; i < this.downloads.size(); i++) {
                Download download = this.downloads.get(i);
                if (download.state == 2) {
                    try {
                        this.downloadIndex.putDownload(download);
                    } catch (IOException e) {
                        Log.e(DownloadManager.TAG, "Failed to update index.", e);
                    }
                }
            }
            sendEmptyMessageDelayed(11, 5000L);
        }

        private boolean canDownloadsRun() {
            return !this.downloadsPaused && this.notMetRequirements == 0;
        }

        private Download putDownloadWithState(Download download, int state, int stopReason) {
            Assertions.checkState((state == 3 || state == 4) ? false : true);
            return putDownload(copyDownloadWithState(download, state, stopReason));
        }

        private Download putDownload(Download download) {
            Assertions.checkState((download.state == 3 || download.state == 4) ? false : true);
            int downloadIndex = getDownloadIndex(download.request.id);
            if (downloadIndex == -1) {
                this.downloads.add(download);
                Collections.sort(this.downloads, new DownloadManager$InternalHandler$$ExternalSyntheticLambda0());
            } else {
                boolean z = download.startTimeMs != this.downloads.get(downloadIndex).startTimeMs;
                this.downloads.set(downloadIndex, download);
                if (z) {
                    Collections.sort(this.downloads, new DownloadManager$InternalHandler$$ExternalSyntheticLambda0());
                }
            }
            try {
                this.downloadIndex.putDownload(download);
            } catch (IOException e) {
                Log.e(DownloadManager.TAG, "Failed to update index.", e);
            }
            this.mainHandler.obtainMessage(2, new DownloadUpdate(download, false, new ArrayList(this.downloads), null)).sendToTarget();
            return download;
        }

        private Download getDownload(String id, boolean loadFromIndex) {
            int downloadIndex = getDownloadIndex(id);
            if (downloadIndex != -1) {
                return this.downloads.get(downloadIndex);
            }
            if (!loadFromIndex) {
                return null;
            }
            try {
                return this.downloadIndex.getDownload(id);
            } catch (IOException e) {
                String strValueOf = String.valueOf(id);
                Log.e(DownloadManager.TAG, strValueOf.length() != 0 ? "Failed to load download: ".concat(strValueOf) : new String("Failed to load download: "), e);
                return null;
            }
        }

        private int getDownloadIndex(String id) {
            for (int i = 0; i < this.downloads.size(); i++) {
                if (this.downloads.get(i).request.id.equals(id)) {
                    return i;
                }
            }
            return -1;
        }

        private static Download copyDownloadWithState(Download download, int state, int stopReason) {
            return new Download(download.request, state, download.startTimeMs, System.currentTimeMillis(), download.contentLength, stopReason, 0, download.progress);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static int compareStartTimes(Download first, Download second) {
            return Util.compareLong(first.startTimeMs, second.startTimeMs);
        }
    }

    private static class Task extends Thread implements Downloader.ProgressListener {
        private long contentLength;
        private final DownloadProgress downloadProgress;
        private final Downloader downloader;
        private Exception finalException;
        private volatile InternalHandler internalHandler;
        private volatile boolean isCanceled;
        private final boolean isRemove;
        private final int minRetryCount;
        private final DownloadRequest request;

        private Task(DownloadRequest request, Downloader downloader, DownloadProgress downloadProgress, boolean isRemove, int minRetryCount, InternalHandler internalHandler) {
            this.request = request;
            this.downloader = downloader;
            this.downloadProgress = downloadProgress;
            this.isRemove = isRemove;
            this.minRetryCount = minRetryCount;
            this.internalHandler = internalHandler;
            this.contentLength = -1L;
        }

        public void cancel(boolean released) {
            if (released) {
                this.internalHandler = null;
            }
            if (this.isCanceled) {
                return;
            }
            this.isCanceled = true;
            this.downloader.cancel();
            interrupt();
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() throws InterruptedException, IOException {
            try {
                if (this.isRemove) {
                    this.downloader.remove();
                } else {
                    long j = -1;
                    int i = 0;
                    while (!this.isCanceled) {
                        try {
                            this.downloader.download(this);
                            break;
                        } catch (IOException e) {
                            if (!this.isCanceled) {
                                long j2 = this.downloadProgress.bytesDownloaded;
                                if (j2 != j) {
                                    i = 0;
                                    j = j2;
                                }
                                i++;
                                if (i > this.minRetryCount) {
                                    throw e;
                                }
                                Thread.sleep(getRetryDelayMillis(i));
                            }
                        }
                    }
                }
            } catch (InterruptedException unused) {
                Thread.currentThread().interrupt();
            } catch (Exception e2) {
                this.finalException = e2;
            }
            InternalHandler internalHandler = this.internalHandler;
            if (internalHandler != null) {
                internalHandler.obtainMessage(9, this).sendToTarget();
            }
        }

        @Override // com.google.android.exoplayer2.offline.Downloader.ProgressListener
        public void onProgress(long contentLength, long bytesDownloaded, float percentDownloaded) {
            this.downloadProgress.bytesDownloaded = bytesDownloaded;
            this.downloadProgress.percentDownloaded = percentDownloaded;
            if (contentLength != this.contentLength) {
                this.contentLength = contentLength;
                InternalHandler internalHandler = this.internalHandler;
                if (internalHandler != null) {
                    internalHandler.obtainMessage(10, (int) (contentLength >> 32), (int) contentLength, this).sendToTarget();
                }
            }
        }

        private static int getRetryDelayMillis(int errorCount) {
            return Math.min((errorCount - 1) * 1000, 5000);
        }
    }

    private static final class DownloadUpdate {
        public final Download download;
        public final List<Download> downloads;
        public final Exception finalException;
        public final boolean isRemove;

        public DownloadUpdate(Download download, boolean isRemove, List<Download> downloads, Exception finalException) {
            this.download = download;
            this.isRemove = isRemove;
            this.downloads = downloads;
            this.finalException = finalException;
        }
    }
}
