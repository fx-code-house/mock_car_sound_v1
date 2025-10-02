package com.xwray.groupie;

import android.view.View;
import com.xwray.groupie.GroupieViewHolder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/* loaded from: classes3.dex */
public abstract class Item<VH extends GroupieViewHolder> implements Group, SpanSizeProvider {
    private static AtomicLong ID_COUNTER = new AtomicLong(0);
    private Map<String, Object> extras;
    private final long id;
    protected GroupDataObserver parentDataObserver;

    public abstract void bind(VH vh, int i);

    public Object getChangePayload(Item item) {
        return null;
    }

    public int getDragDirs() {
        return 0;
    }

    @Override // com.xwray.groupie.Group
    public int getItemCount() {
        return 1;
    }

    public abstract int getLayout();

    @Override // com.xwray.groupie.Group
    public int getPosition(Item item) {
        return this == item ? 0 : -1;
    }

    @Override // com.xwray.groupie.SpanSizeProvider
    public int getSpanSize(int i, int i2) {
        return i;
    }

    public int getSwipeDirs() {
        return 0;
    }

    public boolean isClickable() {
        return true;
    }

    public boolean isLongClickable() {
        return true;
    }

    public boolean isRecyclable() {
        return true;
    }

    public void onViewAttachedToWindow(VH vh) {
    }

    public void onViewDetachedFromWindow(VH vh) {
    }

    public Item() {
        this(ID_COUNTER.decrementAndGet());
    }

    protected Item(long j) {
        this.extras = new HashMap();
        this.id = j;
    }

    public VH createViewHolder(View view) {
        return (VH) new GroupieViewHolder(view);
    }

    public void bind(VH vh, int i, List<Object> list, OnItemClickListener onItemClickListener, OnItemLongClickListener onItemLongClickListener) {
        vh.bind(this, onItemClickListener, onItemLongClickListener);
        bind(vh, i, list);
    }

    public void bind(VH vh, int i, List<Object> list) {
        bind(vh, i);
    }

    public void unbind(VH vh) {
        vh.unbind();
    }

    public int getViewType() {
        return getLayout();
    }

    @Override // com.xwray.groupie.Group
    public Item getItem(int i) {
        if (i == 0) {
            return this;
        }
        throw new IndexOutOfBoundsException("Wanted item at position " + i + " but an Item is a Group of size 1");
    }

    @Override // com.xwray.groupie.Group
    public void registerGroupDataObserver(GroupDataObserver groupDataObserver) {
        this.parentDataObserver = groupDataObserver;
    }

    @Override // com.xwray.groupie.Group
    public void unregisterGroupDataObserver(GroupDataObserver groupDataObserver) {
        this.parentDataObserver = null;
    }

    public void notifyChanged() {
        GroupDataObserver groupDataObserver = this.parentDataObserver;
        if (groupDataObserver != null) {
            groupDataObserver.onItemChanged(this, 0);
        }
    }

    public void notifyChanged(Object obj) {
        GroupDataObserver groupDataObserver = this.parentDataObserver;
        if (groupDataObserver != null) {
            groupDataObserver.onItemChanged(this, 0, obj);
        }
    }

    public Map<String, Object> getExtras() {
        return this.extras;
    }

    public long getId() {
        return this.id;
    }

    public boolean isSameAs(Item item) {
        return getViewType() == item.getViewType() && getId() == item.getId();
    }

    public boolean hasSameContentAs(Item item) {
        return equals(item);
    }
}
