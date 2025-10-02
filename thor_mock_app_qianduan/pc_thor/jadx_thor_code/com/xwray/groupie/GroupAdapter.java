package com.xwray.groupie;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.xwray.groupie.AsyncDiffUtil;
import com.xwray.groupie.GroupieViewHolder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes3.dex */
public class GroupAdapter<VH extends GroupieViewHolder> extends RecyclerView.Adapter<VH> implements GroupDataObserver {
    private Item lastItemForViewTypeLookup;
    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;
    private final List<Group> groups = new ArrayList();
    private int spanCount = 1;
    private AsyncDiffUtil.Callback diffUtilCallbacks = new AsyncDiffUtil.Callback() { // from class: com.xwray.groupie.GroupAdapter.1
        @Override // com.xwray.groupie.AsyncDiffUtil.Callback
        public void onDispatchAsyncResult(Collection<? extends Group> collection) {
            GroupAdapter.this.setNewGroups(collection);
        }

        @Override // androidx.recyclerview.widget.ListUpdateCallback
        public void onInserted(int i, int i2) {
            GroupAdapter.this.notifyItemRangeInserted(i, i2);
        }

        @Override // androidx.recyclerview.widget.ListUpdateCallback
        public void onRemoved(int i, int i2) {
            GroupAdapter.this.notifyItemRangeRemoved(i, i2);
        }

        @Override // androidx.recyclerview.widget.ListUpdateCallback
        public void onMoved(int i, int i2) {
            GroupAdapter.this.notifyItemMoved(i, i2);
        }

        @Override // androidx.recyclerview.widget.ListUpdateCallback
        public void onChanged(int i, int i2, Object obj) {
            GroupAdapter.this.notifyItemRangeChanged(i, i2, obj);
        }
    };
    private AsyncDiffUtil asyncDiffUtil = new AsyncDiffUtil(this.diffUtilCallbacks);
    private final GridLayoutManager.SpanSizeLookup spanSizeLookup = new GridLayoutManager.SpanSizeLookup() { // from class: com.xwray.groupie.GroupAdapter.2
        @Override // androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
        public int getSpanSize(int i) {
            try {
                return GroupAdapter.this.getItem(i).getSpanSize(GroupAdapter.this.spanCount, i);
            } catch (IndexOutOfBoundsException unused) {
                return GroupAdapter.this.spanCount;
            }
        }
    };

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(VH vh, int i) {
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public /* bridge */ /* synthetic */ void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i, List list) {
        onBindViewHolder((GroupAdapter<VH>) viewHolder, i, (List<Object>) list);
    }

    public GridLayoutManager.SpanSizeLookup getSpanSizeLookup() {
        return this.spanSizeLookup;
    }

    public void setSpanCount(int i) {
        this.spanCount = i;
    }

    public int getSpanCount() {
        return this.spanCount;
    }

    public void updateAsync(List<? extends Group> list) {
        updateAsync(list, true, null);
    }

    public void updateAsync(List<? extends Group> list, OnAsyncUpdateListener onAsyncUpdateListener) {
        updateAsync(list, true, onAsyncUpdateListener);
    }

    public void updateAsync(List<? extends Group> list, boolean z, OnAsyncUpdateListener onAsyncUpdateListener) {
        if (this.groups.isEmpty()) {
            update(list, z);
            if (onAsyncUpdateListener != null) {
                onAsyncUpdateListener.onUpdateComplete();
                return;
            }
            return;
        }
        this.asyncDiffUtil.calculateDiff(list, new DiffCallback(new ArrayList(this.groups), list), onAsyncUpdateListener, z);
    }

    public void replaceAll(Collection<? extends Group> collection) {
        setNewGroups(collection);
        notifyDataSetChanged();
    }

    public void update(Collection<? extends Group> collection) {
        update(collection, true);
    }

    public void update(Collection<? extends Group> collection, boolean z) {
        DiffUtil.DiffResult diffResultCalculateDiff = DiffUtil.calculateDiff(new DiffCallback(new ArrayList(this.groups), collection), z);
        setNewGroups(collection);
        diffResultCalculateDiff.dispatchUpdatesTo(this.diffUtilCallbacks);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public VH onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflaterFrom = LayoutInflater.from(viewGroup.getContext());
        Item<VH> itemForViewType = getItemForViewType(i);
        return (VH) itemForViewType.createViewHolder(layoutInflaterFrom.inflate(itemForViewType.getLayout(), viewGroup, false));
    }

    public void onBindViewHolder(VH vh, int i, List<Object> list) {
        getItem(i).bind(vh, i, list, this.onItemClickListener, this.onItemLongClickListener);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onViewRecycled(VH vh) {
        vh.getItem().unbind(vh);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public boolean onFailedToRecycleView(VH vh) {
        return vh.getItem().isRecyclable();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onViewAttachedToWindow(VH vh) {
        super.onViewAttachedToWindow((GroupAdapter<VH>) vh);
        getItem((GroupAdapter<VH>) vh).onViewAttachedToWindow(vh);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onViewDetachedFromWindow(VH vh) {
        super.onViewDetachedFromWindow((GroupAdapter<VH>) vh);
        getItem((GroupAdapter<VH>) vh).onViewDetachedFromWindow(vh);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int i) {
        Item item = getItem(i);
        this.lastItemForViewTypeLookup = item;
        if (item == null) {
            throw new RuntimeException("Invalid position " + i);
        }
        return item.getViewType();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public long getItemId(int i) {
        return getItem(i).getId();
    }

    public Item getItem(VH vh) {
        return vh.getItem();
    }

    public Item getItem(int i) {
        return GroupUtils.getItem(this.groups, i);
    }

    public int getAdapterPosition(Item item) {
        int itemCount = 0;
        for (Group group : this.groups) {
            int position = group.getPosition(item);
            if (position >= 0) {
                return position + itemCount;
            }
            itemCount += group.getItemCount();
        }
        return -1;
    }

    public int getAdapterPosition(Group group) {
        int iIndexOf = this.groups.indexOf(group);
        if (iIndexOf == -1) {
            return -1;
        }
        int itemCount = 0;
        for (int i = 0; i < iIndexOf; i++) {
            itemCount += this.groups.get(i).getItemCount();
        }
        return itemCount;
    }

    public int getGroupCount() {
        return this.groups.size();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return GroupUtils.getItemCount(this.groups);
    }

    public int getItemCountForGroup(int i) {
        if (i >= this.groups.size()) {
            throw new IndexOutOfBoundsException("Requested group index " + i + " but there are " + this.groups.size() + " groups");
        }
        return this.groups.get(i).getItemCount();
    }

    @Deprecated
    public int getItemCount(int i) {
        return getItemCountForGroup(i);
    }

    public void clear() {
        Iterator<Group> it = this.groups.iterator();
        while (it.hasNext()) {
            it.next().unregisterGroupDataObserver(this);
        }
        this.groups.clear();
        notifyDataSetChanged();
    }

    public void add(Group group) {
        if (group == null) {
            throw new RuntimeException("Group cannot be null");
        }
        int itemCount = getItemCount();
        group.registerGroupDataObserver(this);
        this.groups.add(group);
        notifyItemRangeInserted(itemCount, group.getItemCount());
    }

    public void addAll(Collection<? extends Group> collection) {
        if (collection.contains(null)) {
            throw new RuntimeException("List of groups can't contain null!");
        }
        int itemCount = getItemCount();
        int itemCount2 = 0;
        for (Group group : collection) {
            itemCount2 += group.getItemCount();
            group.registerGroupDataObserver(this);
        }
        this.groups.addAll(collection);
        notifyItemRangeInserted(itemCount, itemCount2);
    }

    public void remove(Group group) {
        if (group == null) {
            throw new RuntimeException("Group cannot be null");
        }
        remove(this.groups.indexOf(group), group);
    }

    public void removeAll(Collection<? extends Group> collection) {
        Iterator<? extends Group> it = collection.iterator();
        while (it.hasNext()) {
            remove(it.next());
        }
    }

    public void removeGroupAtAdapterPosition(int i) {
        remove(i, getGroupAtAdapterPosition(i));
    }

    @Deprecated
    public void removeGroup(int i) {
        removeGroupAtAdapterPosition(i);
    }

    private void remove(int i, Group group) {
        int itemCountBeforeGroup = getItemCountBeforeGroup(i);
        group.unregisterGroupDataObserver(this);
        this.groups.remove(i);
        notifyItemRangeRemoved(itemCountBeforeGroup, group.getItemCount());
    }

    public void add(int i, Group group) {
        if (group == null) {
            throw new RuntimeException("Group cannot be null");
        }
        group.registerGroupDataObserver(this);
        this.groups.add(i, group);
        notifyItemRangeInserted(getItemCountBeforeGroup(i), group.getItemCount());
    }

    public Group getTopLevelGroup(int i) {
        return this.groups.get(i);
    }

    public Group getGroupAtAdapterPosition(int i) {
        int itemCount = 0;
        for (Group group : this.groups) {
            if (i - itemCount < group.getItemCount()) {
                return group;
            }
            itemCount += group.getItemCount();
        }
        throw new IndexOutOfBoundsException("Requested position " + i + " in group adapter but there are only " + itemCount + " items");
    }

    @Deprecated
    public Group getGroup(int i) {
        return getGroupAtAdapterPosition(i);
    }

    public Group getGroup(Item item) {
        for (Group group : this.groups) {
            if (group.getPosition(item) >= 0) {
                return group;
            }
        }
        throw new IndexOutOfBoundsException("Item is not present in adapter or in any group");
    }

    @Override // com.xwray.groupie.GroupDataObserver
    public void onChanged(Group group) {
        notifyItemRangeChanged(getAdapterPosition(group), group.getItemCount());
    }

    @Override // com.xwray.groupie.GroupDataObserver
    public void onItemInserted(Group group, int i) {
        notifyItemInserted(getAdapterPosition(group) + i);
    }

    @Override // com.xwray.groupie.GroupDataObserver
    public void onItemChanged(Group group, int i) {
        notifyItemChanged(getAdapterPosition(group) + i);
    }

    @Override // com.xwray.groupie.GroupDataObserver
    public void onItemChanged(Group group, int i, Object obj) {
        notifyItemChanged(getAdapterPosition(group) + i, obj);
    }

    @Override // com.xwray.groupie.GroupDataObserver
    public void onItemRemoved(Group group, int i) {
        notifyItemRemoved(getAdapterPosition(group) + i);
    }

    @Override // com.xwray.groupie.GroupDataObserver
    public void onItemRangeChanged(Group group, int i, int i2) {
        notifyItemRangeChanged(getAdapterPosition(group) + i, i2);
    }

    @Override // com.xwray.groupie.GroupDataObserver
    public void onItemRangeChanged(Group group, int i, int i2, Object obj) {
        notifyItemRangeChanged(getAdapterPosition(group) + i, i2, obj);
    }

    @Override // com.xwray.groupie.GroupDataObserver
    public void onItemRangeInserted(Group group, int i, int i2) {
        notifyItemRangeInserted(getAdapterPosition(group) + i, i2);
    }

    @Override // com.xwray.groupie.GroupDataObserver
    public void onItemRangeRemoved(Group group, int i, int i2) {
        notifyItemRangeRemoved(getAdapterPosition(group) + i, i2);
    }

    @Override // com.xwray.groupie.GroupDataObserver
    public void onItemMoved(Group group, int i, int i2) {
        int adapterPosition = getAdapterPosition(group);
        notifyItemMoved(i + adapterPosition, adapterPosition + i2);
    }

    @Override // com.xwray.groupie.GroupDataObserver
    public void onDataSetInvalidated() {
        notifyDataSetChanged();
    }

    private Item<VH> getItemForViewType(int i) {
        Item item = this.lastItemForViewTypeLookup;
        if (item != null && item.getViewType() == i) {
            return this.lastItemForViewTypeLookup;
        }
        for (int i2 = 0; i2 < getItemCount(); i2++) {
            Item<VH> item2 = getItem(i2);
            if (item2.getViewType() == i) {
                return item2;
            }
        }
        throw new IllegalStateException("Could not find model for view type: " + i);
    }

    private int getItemCountBeforeGroup(int i) {
        int itemCount = 0;
        Iterator<Group> it = this.groups.subList(0, i).iterator();
        while (it.hasNext()) {
            itemCount += it.next().getItemCount();
        }
        return itemCount;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setNewGroups(Collection<? extends Group> collection) {
        Iterator<Group> it = this.groups.iterator();
        while (it.hasNext()) {
            it.next().unregisterGroupDataObserver(this);
        }
        this.groups.clear();
        this.groups.addAll(collection);
        Iterator<? extends Group> it2 = collection.iterator();
        while (it2.hasNext()) {
            it2.next().registerGroupDataObserver(this);
        }
    }
}
