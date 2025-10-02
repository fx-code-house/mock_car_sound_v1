package com.xwray.groupie;

import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import java.util.Map;

/* loaded from: classes3.dex */
public class GroupieViewHolder extends RecyclerView.ViewHolder {
    private Item item;
    private View.OnClickListener onClickListener;
    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;
    private View.OnLongClickListener onLongClickListener;

    public GroupieViewHolder(View view) {
        super(view);
        this.onClickListener = new View.OnClickListener() { // from class: com.xwray.groupie.GroupieViewHolder.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                if (GroupieViewHolder.this.onItemClickListener == null || GroupieViewHolder.this.getAdapterPosition() == -1) {
                    return;
                }
                GroupieViewHolder.this.onItemClickListener.onItemClick(GroupieViewHolder.this.getItem(), view2);
            }
        };
        this.onLongClickListener = new View.OnLongClickListener() { // from class: com.xwray.groupie.GroupieViewHolder.2
            @Override // android.view.View.OnLongClickListener
            public boolean onLongClick(View view2) {
                if (GroupieViewHolder.this.onItemLongClickListener == null || GroupieViewHolder.this.getAdapterPosition() == -1) {
                    return false;
                }
                return GroupieViewHolder.this.onItemLongClickListener.onItemLongClick(GroupieViewHolder.this.getItem(), view2);
            }
        };
    }

    public void bind(Item item, OnItemClickListener onItemClickListener, OnItemLongClickListener onItemLongClickListener) {
        this.item = item;
        if (onItemClickListener != null && item.isClickable()) {
            this.itemView.setOnClickListener(this.onClickListener);
            this.onItemClickListener = onItemClickListener;
        }
        if (onItemLongClickListener == null || !item.isLongClickable()) {
            return;
        }
        this.itemView.setOnLongClickListener(this.onLongClickListener);
        this.onItemLongClickListener = onItemLongClickListener;
    }

    public void unbind() {
        if (this.onItemClickListener != null && this.item.isClickable()) {
            this.itemView.setOnClickListener(null);
        }
        if (this.onItemLongClickListener != null && this.item.isLongClickable()) {
            this.itemView.setOnLongClickListener(null);
        }
        this.item = null;
        this.onItemClickListener = null;
        this.onItemLongClickListener = null;
    }

    public Map<String, Object> getExtras() {
        return this.item.getExtras();
    }

    public int getSwipeDirs() {
        return this.item.getSwipeDirs();
    }

    public int getDragDirs() {
        return this.item.getDragDirs();
    }

    public Item getItem() {
        return this.item;
    }

    public View getRoot() {
        return this.itemView;
    }
}
