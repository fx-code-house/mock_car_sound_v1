package com.xwray.groupie;

import androidx.recyclerview.widget.DiffUtil;
import java.util.Collection;

/* loaded from: classes3.dex */
class DiffCallback extends DiffUtil.Callback {
    private final int newBodyItemCount;
    private final Collection<? extends Group> newGroups;
    private final int oldBodyItemCount;
    private final Collection<? extends Group> oldGroups;

    DiffCallback(Collection<? extends Group> collection, Collection<? extends Group> collection2) {
        this.oldBodyItemCount = GroupUtils.getItemCount(collection);
        this.newBodyItemCount = GroupUtils.getItemCount(collection2);
        this.oldGroups = collection;
        this.newGroups = collection2;
    }

    @Override // androidx.recyclerview.widget.DiffUtil.Callback
    public int getOldListSize() {
        return this.oldBodyItemCount;
    }

    @Override // androidx.recyclerview.widget.DiffUtil.Callback
    public int getNewListSize() {
        return this.newBodyItemCount;
    }

    @Override // androidx.recyclerview.widget.DiffUtil.Callback
    public boolean areItemsTheSame(int i, int i2) {
        return GroupUtils.getItem(this.newGroups, i2).isSameAs(GroupUtils.getItem(this.oldGroups, i));
    }

    @Override // androidx.recyclerview.widget.DiffUtil.Callback
    public boolean areContentsTheSame(int i, int i2) {
        return GroupUtils.getItem(this.newGroups, i2).hasSameContentAs(GroupUtils.getItem(this.oldGroups, i));
    }

    @Override // androidx.recyclerview.widget.DiffUtil.Callback
    public Object getChangePayload(int i, int i2) {
        return GroupUtils.getItem(this.oldGroups, i).getChangePayload(GroupUtils.getItem(this.newGroups, i2));
    }
}
