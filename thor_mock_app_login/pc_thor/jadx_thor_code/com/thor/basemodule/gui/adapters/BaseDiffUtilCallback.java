package com.thor.basemodule.gui.adapters;

import androidx.recyclerview.widget.DiffUtil;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/* loaded from: classes3.dex */
public abstract class BaseDiffUtilCallback<D> extends DiffUtil.Callback {
    private final List<D> mNewList;
    private final List<D> mOldList;

    public abstract boolean areContentsTheSame(D d, D d2);

    public abstract boolean areItemsTheSame(D d, D d2);

    public BaseDiffUtilCallback() {
        this.mOldList = new ArrayList();
        this.mNewList = new ArrayList();
    }

    public BaseDiffUtilCallback(List<D> list, List<D> list2) {
        this.mOldList = list;
        this.mNewList = list2;
    }

    public void updateLists(Collection<D> collection, Collection<D> collection2) {
        setNewList(collection2);
        setOldList(collection);
    }

    public void setNewList(Collection<D> collection) {
        this.mNewList.clear();
        this.mNewList.addAll(collection);
    }

    public void setOldList(Collection<D> collection) {
        this.mOldList.clear();
        this.mOldList.addAll(collection);
    }

    @Override // androidx.recyclerview.widget.DiffUtil.Callback
    public int getOldListSize() {
        return this.mOldList.size();
    }

    @Override // androidx.recyclerview.widget.DiffUtil.Callback
    public int getNewListSize() {
        return this.mNewList.size();
    }

    @Override // androidx.recyclerview.widget.DiffUtil.Callback
    public boolean areItemsTheSame(int i, int i2) {
        return areItemsTheSame(this.mOldList.get(i), this.mNewList.get(i2));
    }

    @Override // androidx.recyclerview.widget.DiffUtil.Callback
    public boolean areContentsTheSame(int i, int i2) {
        return areContentsTheSame(this.mOldList.get(i), this.mNewList.get(i2));
    }
}
