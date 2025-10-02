package com.xwray.groupie;

import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListUpdateCallback;
import java.util.Collection;

/* loaded from: classes3.dex */
class AsyncDiffUtil {
    private final Callback asyncDiffUtilCallback;
    private Collection<? extends Group> groups;
    private int maxScheduledGeneration;

    interface Callback extends ListUpdateCallback {
        void onDispatchAsyncResult(Collection<? extends Group> collection);
    }

    AsyncDiffUtil(Callback callback) {
        this.asyncDiffUtilCallback = callback;
    }

    Callback getAsyncDiffUtilCallback() {
        return this.asyncDiffUtilCallback;
    }

    Collection<? extends Group> getGroups() {
        return this.groups;
    }

    int getMaxScheduledGeneration() {
        return this.maxScheduledGeneration;
    }

    void calculateDiff(Collection<? extends Group> collection, DiffUtil.Callback callback, OnAsyncUpdateListener onAsyncUpdateListener, boolean z) {
        this.groups = collection;
        int i = this.maxScheduledGeneration + 1;
        this.maxScheduledGeneration = i;
        new DiffTask(this, callback, i, z, onAsyncUpdateListener).execute(new Void[0]);
    }
}
