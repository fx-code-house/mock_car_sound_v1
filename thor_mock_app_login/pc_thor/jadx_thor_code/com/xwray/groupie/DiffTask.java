package com.xwray.groupie;

import android.os.AsyncTask;
import androidx.recyclerview.widget.DiffUtil;
import java.lang.ref.WeakReference;

/* loaded from: classes3.dex */
class DiffTask extends AsyncTask<Void, Void, DiffUtil.DiffResult> {
    private final WeakReference<AsyncDiffUtil> asyncListDiffer;
    private Exception backgroundException = null;
    private final boolean detectMoves;
    private final DiffUtil.Callback diffCallback;
    private WeakReference<OnAsyncUpdateListener> onAsyncUpdateListener;
    private final int runGeneration;

    DiffTask(AsyncDiffUtil asyncDiffUtil, DiffUtil.Callback callback, int i, boolean z, OnAsyncUpdateListener onAsyncUpdateListener) {
        this.diffCallback = callback;
        this.asyncListDiffer = new WeakReference<>(asyncDiffUtil);
        this.runGeneration = i;
        this.detectMoves = z;
        if (onAsyncUpdateListener != null) {
            this.onAsyncUpdateListener = new WeakReference<>(onAsyncUpdateListener);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    public DiffUtil.DiffResult doInBackground(Void... voidArr) {
        try {
            return DiffUtil.calculateDiff(this.diffCallback, this.detectMoves);
        } catch (Exception e) {
            this.backgroundException = e;
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    public void onPostExecute(DiffUtil.DiffResult diffResult) {
        if (this.backgroundException != null) {
            throw new RuntimeException(this.backgroundException);
        }
        AsyncDiffUtil asyncDiffUtil = this.asyncListDiffer.get();
        if (shouldDispatchResult(diffResult, asyncDiffUtil)) {
            asyncDiffUtil.getAsyncDiffUtilCallback().onDispatchAsyncResult(asyncDiffUtil.getGroups());
            diffResult.dispatchUpdatesTo(asyncDiffUtil.getAsyncDiffUtilCallback());
            WeakReference<OnAsyncUpdateListener> weakReference = this.onAsyncUpdateListener;
            if (weakReference == null || weakReference.get() == null) {
                return;
            }
            this.onAsyncUpdateListener.get().onUpdateComplete();
        }
    }

    private boolean shouldDispatchResult(DiffUtil.DiffResult diffResult, AsyncDiffUtil asyncDiffUtil) {
        return (diffResult == null || asyncDiffUtil == null || this.runGeneration != asyncDiffUtil.getMaxScheduledGeneration()) ? false : true;
    }
}
