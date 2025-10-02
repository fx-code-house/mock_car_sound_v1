package com.xwray.groupie;

/* loaded from: classes3.dex */
public interface Group {
    Item getItem(int i);

    int getItemCount();

    int getPosition(Item item);

    void registerGroupDataObserver(GroupDataObserver groupDataObserver);

    void unregisterGroupDataObserver(GroupDataObserver groupDataObserver);
}
