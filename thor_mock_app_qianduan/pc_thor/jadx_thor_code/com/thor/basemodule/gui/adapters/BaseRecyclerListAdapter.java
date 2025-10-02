package com.thor.basemodule.gui.adapters;

import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.thor.basemodule.gui.adapters.RecyclerCollectionAdapter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import timber.log.Timber;

/* loaded from: classes3.dex */
public abstract class BaseRecyclerListAdapter<M, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> implements RecyclerCollectionAdapter<M> {
    protected BaseDiffUtilCallback<M> mDiffUtilCallback;
    protected final List<M> mList;
    protected RecyclerCollectionAdapter.OnItemClickListener<M> mOnItemClickListener;
    protected RecyclerCollectionAdapter.OnRecyclerActionConfigListener<M> mOnItemConfigClickListener;
    protected RecyclerCollectionAdapter.OnItemLongClickListener<M> mOnItemLongClickListener;

    public BaseRecyclerListAdapter() {
        this.mList = new ArrayList();
    }

    public BaseRecyclerListAdapter(List<M> list) {
        this.mList = list;
    }

    public BaseRecyclerListAdapter(BaseDiffUtilCallback<M> baseDiffUtilCallback) {
        this.mList = new ArrayList();
        this.mDiffUtilCallback = baseDiffUtilCallback;
    }

    public BaseRecyclerListAdapter(List<M> list, BaseDiffUtilCallback<M> baseDiffUtilCallback) {
        this.mList = list;
        this.mDiffUtilCallback = baseDiffUtilCallback;
    }

    public void setDiffUtilCallback(BaseDiffUtilCallback<M> baseDiffUtilCallback) {
        this.mDiffUtilCallback = baseDiffUtilCallback;
    }

    public BaseDiffUtilCallback<M> getDiffUtilCallback() {
        return this.mDiffUtilCallback;
    }

    @Override // com.thor.basemodule.gui.adapters.RecyclerCollectionAdapter
    public void setOnItemClickListener(RecyclerCollectionAdapter.OnItemClickListener<M> onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    @Override // com.thor.basemodule.gui.adapters.RecyclerCollectionAdapter
    public void setOnItemLongClickListener(RecyclerCollectionAdapter.OnItemLongClickListener<M> onItemLongClickListener) {
        this.mOnItemLongClickListener = onItemLongClickListener;
    }

    @Override // com.thor.basemodule.gui.adapters.RecyclerCollectionAdapter
    public void setOnActionConfigListener(RecyclerCollectionAdapter.OnRecyclerActionConfigListener<M> onRecyclerActionConfigListener) {
        this.mOnItemConfigClickListener = onRecyclerActionConfigListener;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.mList.size();
    }

    @Override // com.thor.basemodule.gui.adapters.RecyclerCollectionAdapter
    public M getItem(int i) {
        return this.mList.get(i);
    }

    @Override // com.thor.basemodule.gui.adapters.RecyclerCollectionAdapter
    public void clearAndAddAll(Collection<M> collection) {
        Timber.i("clearAndAddAll", new Object[0]);
        if (collection == null) {
            Timber.e("Data cannot be null", new Object[0]);
            if (getItemCount() == 0) {
                notifyDataSetChanged();
                return;
            }
            return;
        }
        this.mList.clear();
        Iterator<M> it = collection.iterator();
        while (it.hasNext()) {
            addInternal(it.next());
        }
        notifyDataSetChanged();
    }

    @Override // com.thor.basemodule.gui.adapters.RecyclerCollectionAdapter
    public void addAll(Collection<M> collection) {
        Timber.i("addAll", new Object[0]);
        if (collection == null) {
            Timber.e("Data is null", new Object[0]);
            return;
        }
        if (collection.isEmpty()) {
            Timber.e("Data is empty", new Object[0]);
            return;
        }
        ArrayList arrayList = new ArrayList(this.mList);
        Iterator<M> it = collection.iterator();
        while (it.hasNext()) {
            addInternal(it.next());
        }
        if (this.mDiffUtilCallback == null) {
            int size = collection.size();
            notifyItemRangeInserted(this.mList.size() - size, size);
        } else {
            compareByDiffUtil(arrayList);
        }
    }

    @Override // com.thor.basemodule.gui.adapters.RecyclerCollectionAdapter
    public void updateAll(Collection<M> collection) {
        Timber.i("updateAll", new Object[0]);
        if (collection == null) {
            Timber.e("Data is null", new Object[0]);
            return;
        }
        if (collection.isEmpty()) {
            Timber.e("Data is empty", new Object[0]);
            return;
        }
        if (this.mList.isEmpty()) {
            clearAndAddAll(collection);
        }
        if (this.mDiffUtilCallback == null) {
            clearAndAddAll(collection);
        } else {
            compareByDiffUtil(this.mList, collection);
        }
    }

    @Override // com.thor.basemodule.gui.adapters.RecyclerCollectionAdapter
    public void updateAllNew(Collection<M> collection) {
        compareByDiffUtil(this.mList, collection);
        this.mList.clear();
        this.mList.addAll(collection);
    }

    @Override // com.thor.basemodule.gui.adapters.RecyclerCollectionAdapter
    public void compareByDiffUtil(Collection<M> collection) {
        compareByDiffUtil(collection, this.mList);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void compareByDiffUtil(Collection<M> collection, Collection<M> collection2) {
        this.mDiffUtilCallback.updateLists(collection, collection2);
        DiffUtil.DiffResult diffResultCalculateDiff = DiffUtil.calculateDiff(this.mDiffUtilCallback);
        collection.clear();
        collection.addAll(collection2);
        diffResultCalculateDiff.dispatchUpdatesTo(this);
    }

    public void addItem(M m) {
        ArrayList arrayList = new ArrayList(this.mList);
        if (this.mDiffUtilCallback == null) {
            addInternal(m);
            notifyItemInserted(this.mList.size());
            return;
        }
        int i = 0;
        while (true) {
            if (i >= this.mList.size()) {
                break;
            }
            if (this.mDiffUtilCallback.areItemsTheSame(this.mList.get(i), m)) {
                this.mList.remove(i);
                this.mList.add(i, m);
                break;
            }
            i++;
        }
        compareByDiffUtil(arrayList);
    }

    @Override // com.thor.basemodule.gui.adapters.RecyclerCollectionAdapter
    public void addItem(int i, M m) {
        ArrayList arrayList = new ArrayList(this.mList);
        addInternal(i, m);
        if (this.mDiffUtilCallback == null) {
            notifyItemInserted(i);
        } else {
            compareByDiffUtil(arrayList);
        }
    }

    @Override // com.thor.basemodule.gui.adapters.RecyclerCollectionAdapter
    public void updateItem(M m) {
        ArrayList arrayList = new ArrayList(this.mList);
        if (this.mDiffUtilCallback == null) {
            int itemPosition = getItemPosition(m);
            if (itemPosition >= 0) {
                this.mList.remove(itemPosition);
                this.mList.add(itemPosition, m);
                notifyItemChanged(itemPosition);
                return;
            }
            return;
        }
        for (int i = 0; i < this.mList.size(); i++) {
            if (this.mDiffUtilCallback.areItemsTheSame(this.mList.get(i), m)) {
                this.mList.remove(i);
                this.mList.add(i, m);
            }
        }
        compareByDiffUtil(arrayList);
    }

    @Override // com.thor.basemodule.gui.adapters.RecyclerCollectionAdapter
    public void removeItem(M m) {
        removeItem(getItemPosition(m));
    }

    @Override // com.thor.basemodule.gui.adapters.RecyclerCollectionAdapter
    public void removeItem(int i) {
        new ArrayList(this.mList);
        if (i >= 0) {
            this.mList.remove(i);
            notifyItemRemoved(i);
        }
        if (isEmpty()) {
            clear();
        }
    }

    @Override // com.thor.basemodule.gui.adapters.RecyclerCollectionAdapter
    public void restoreItem(M m, int i) {
        ArrayList arrayList = new ArrayList(this.mList);
        this.mList.add(i, m);
        if (this.mDiffUtilCallback == null) {
            notifyItemInserted(i);
        } else {
            compareByDiffUtil(arrayList);
        }
    }

    @Override // com.thor.basemodule.gui.adapters.RecyclerCollectionAdapter
    public Collection<M> getAll() {
        return this.mList;
    }

    public void clear() {
        this.mList.clear();
        notifyDataSetChanged();
    }

    @Override // com.thor.basemodule.gui.adapters.RecyclerCollectionAdapter
    public boolean isEmpty() {
        return this.mList.isEmpty();
    }

    protected int getItemPosition(M m) {
        for (int i = 0; i < this.mList.size(); i++) {
            if (this.mList.get(i).equals(m)) {
                return i;
            }
        }
        return -1;
    }

    private void addInternal(M m) {
        this.mList.add(m);
    }

    private void addInternal(int i, M m) {
        Timber.i("addInternal position %s, %s", Integer.valueOf(i), m);
        this.mList.add(i, m);
    }
}
