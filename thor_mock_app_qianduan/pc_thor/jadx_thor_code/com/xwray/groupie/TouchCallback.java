package com.xwray.groupie;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

/* loaded from: classes3.dex */
public abstract class TouchCallback extends ItemTouchHelper.SimpleCallback {
    public TouchCallback() {
        super(0, 0);
    }

    @Override // androidx.recyclerview.widget.ItemTouchHelper.SimpleCallback
    public int getSwipeDirs(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        return ((GroupieViewHolder) viewHolder).getSwipeDirs();
    }

    @Override // androidx.recyclerview.widget.ItemTouchHelper.SimpleCallback
    public int getDragDirs(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        return ((GroupieViewHolder) viewHolder).getDragDirs();
    }
}
