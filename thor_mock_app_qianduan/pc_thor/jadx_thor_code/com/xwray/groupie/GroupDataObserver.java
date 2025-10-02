package com.xwray.groupie;

/* loaded from: classes3.dex */
public interface GroupDataObserver {
    void onChanged(Group group);

    void onDataSetInvalidated();

    void onItemChanged(Group group, int i);

    void onItemChanged(Group group, int i, Object obj);

    void onItemInserted(Group group, int i);

    void onItemMoved(Group group, int i, int i2);

    void onItemRangeChanged(Group group, int i, int i2);

    void onItemRangeChanged(Group group, int i, int i2, Object obj);

    void onItemRangeInserted(Group group, int i, int i2);

    void onItemRangeRemoved(Group group, int i, int i2);

    void onItemRemoved(Group group, int i);
}
