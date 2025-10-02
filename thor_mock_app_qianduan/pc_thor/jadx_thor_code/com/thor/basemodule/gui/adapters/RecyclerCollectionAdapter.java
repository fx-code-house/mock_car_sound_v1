package com.thor.basemodule.gui.adapters;

import android.view.View;
import java.util.Collection;

/* loaded from: classes3.dex */
public interface RecyclerCollectionAdapter<M> {

    public interface OnItemClickListener<M> {
        void onItemClick(View view);

        void onItemClick(M m);

        void onItemClick(M m, int i);

        void onItemClick(M m, View view);
    }

    public interface OnItemLongClickListener<M> {
        void onItemLongClick(M m);
    }

    public interface OnRecyclerActionConfigListener<M> {
        void onConfig(M m);
    }

    void addAll(Collection<M> collection);

    void addItem(int i, M m);

    void addItem(M m);

    void clear();

    void clearAndAddAll(Collection<M> collection);

    void compareByDiffUtil(Collection<M> collection);

    void compareByDiffUtil(Collection<M> collection, Collection<M> collection2);

    Collection<M> getAll();

    M getItem(int i);

    boolean isEmpty();

    void removeItem(int i);

    void removeItem(M m);

    void restoreItem(M m, int i);

    void setOnActionConfigListener(OnRecyclerActionConfigListener<M> onRecyclerActionConfigListener);

    void setOnItemClickListener(OnItemClickListener<M> onItemClickListener);

    void setOnItemLongClickListener(OnItemLongClickListener<M> onItemLongClickListener);

    void updateAll(Collection<M> collection);

    void updateAllNew(Collection<M> collection);

    void updateItem(M m);
}
