package com.fourksoft.base.ui.adapter;

import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.fourksoft.base.ui.adapter.listener.OnItemClickListener;
import com.fourksoft.base.ui.adapter.listener.OnItemLongClickListener;
import com.fourksoft.base.ui.adapter.listener.OnRecyclerActionDeleteListener;
import com.fourksoft.base.ui.adapter.listener.OnRecyclerActionEditListener;
import com.fourksoft.base.ui.adapter.listener.OnRecyclerActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.TypeIntrinsics;

/* compiled from: BaseRecyclerListAdapter.kt */
@Metadata(d1 = {"\u0000|\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010!\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\u001f\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u000f\b&\u0018\u0000*\u0004\b\u0000\u0010\u0001*\b\b\u0001\u0010\u0002*\u00020\u00032\b\u0012\u0004\u0012\u0002H\u00020\u00042\b\u0012\u0004\u0012\u0002H\u00010\u0005B\u0015\b\u0016\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00028\u00000\u0007¢\u0006\u0002\u0010\bB\u0015\b\u0016\u0012\f\u0010\t\u001a\b\u0012\u0004\u0012\u00028\u00000\n¢\u0006\u0002\u0010\u000bB#\b\u0016\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00028\u00000\u0007\u0012\f\u0010\t\u001a\b\u0012\u0004\u0012\u00028\u00000\n¢\u0006\u0002\u0010\fB\u0005¢\u0006\u0002\u0010\rJ\u0018\u00105\u001a\u0002062\u000e\u00107\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u000108H\u0016J\u0015\u00109\u001a\u0002062\u0006\u0010:\u001a\u00028\u0000H\u0002¢\u0006\u0002\u0010;J\u001d\u00109\u001a\u0002062\u0006\u0010<\u001a\u00020=2\u0006\u0010:\u001a\u00028\u0000H\u0002¢\u0006\u0002\u0010>J\u0015\u0010?\u001a\u0002062\u0006\u0010:\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010;J\u001d\u0010?\u001a\u0002062\u0006\u0010<\u001a\u00020=2\u0006\u0010:\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010>J\b\u0010@\u001a\u000206H\u0016J\u0018\u0010A\u001a\u0002062\u000e\u00107\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u000108H\u0016J\u0016\u0010B\u001a\u0002062\f\u0010C\u001a\b\u0012\u0004\u0012\u00028\u00000DH\u0016J$\u0010B\u001a\u0002062\f\u0010C\u001a\b\u0012\u0004\u0012\u00028\u00000D2\f\u0010E\u001a\b\u0012\u0004\u0012\u00028\u00000DH\u0016J\u0010\u0010F\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u000108H\u0016J\u0010\u0010G\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\nH\u0016J\u0015\u0010H\u001a\u00028\u00002\u0006\u0010<\u001a\u00020=H\u0016¢\u0006\u0002\u0010IJ\b\u0010J\u001a\u00020=H\u0016J\b\u0010K\u001a\u00020LH\u0016J\u0015\u0010M\u001a\u0002062\u0006\u0010:\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010;J\u0010\u0010M\u001a\u0002062\u0006\u0010<\u001a\u00020=H\u0016J\u001d\u0010N\u001a\u0002062\u0006\u0010:\u001a\u00028\u00002\u0006\u0010<\u001a\u00020=H\u0016¢\u0006\u0002\u0010OJ\u0016\u0010P\u001a\u0002062\f\u0010\t\u001a\b\u0012\u0004\u0012\u00028\u00000\nH\u0016J\u0018\u0010Q\u001a\u0002062\u000e\u0010R\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u0018H\u0016J\u0018\u0010S\u001a\u0002062\u000e\u0010R\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u001eH\u0016J\u0018\u0010T\u001a\u0002062\u000e\u0010R\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010$H\u0016J\u0018\u0010U\u001a\u0002062\u000e\u0010V\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010*H\u0016J\u0018\u0010W\u001a\u0002062\u000e\u0010X\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u000100H\u0016J\u0018\u0010Y\u001a\u0002062\u000e\u00107\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u000108H\u0016J\u0015\u0010Z\u001a\u0002062\u0006\u0010:\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010;R\"\u0010\u000e\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\nX\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u000bR \u0010\u0012\u001a\b\u0012\u0004\u0012\u00028\u00000\u0013X\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\bR\"\u0010\u0017\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u0018X\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u001a\"\u0004\b\u001b\u0010\u001cR\"\u0010\u001d\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u001eX\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001f\u0010 \"\u0004\b!\u0010\"R\"\u0010#\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010$X\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b%\u0010&\"\u0004\b'\u0010(R\"\u0010)\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010*X\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b+\u0010,\"\u0004\b-\u0010.R\"\u0010/\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u000100X\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b1\u00102\"\u0004\b3\u00104¨\u0006["}, d2 = {"Lcom/fourksoft/base/ui/adapter/BaseRecyclerListAdapter;", "M", "VH", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$Adapter;", "Lcom/fourksoft/base/ui/adapter/RecyclerCollectionAdapter;", "list", "", "(Ljava/util/List;)V", "diffUtilCallback", "Lcom/fourksoft/base/ui/adapter/BaseDiffUtilCallback;", "(Lcom/fourksoft/base/ui/adapter/BaseDiffUtilCallback;)V", "(Ljava/util/List;Lcom/fourksoft/base/ui/adapter/BaseDiffUtilCallback;)V", "()V", "mDiffUtilCallback", "getMDiffUtilCallback", "()Lcom/fourksoft/base/ui/adapter/BaseDiffUtilCallback;", "setMDiffUtilCallback", "mList", "", "getMList", "()Ljava/util/List;", "setMList", "mOnActionDeleteListener", "Lcom/fourksoft/base/ui/adapter/listener/OnRecyclerActionDeleteListener;", "getMOnActionDeleteListener", "()Lcom/fourksoft/base/ui/adapter/listener/OnRecyclerActionDeleteListener;", "setMOnActionDeleteListener", "(Lcom/fourksoft/base/ui/adapter/listener/OnRecyclerActionDeleteListener;)V", "mOnActionEditListener", "Lcom/fourksoft/base/ui/adapter/listener/OnRecyclerActionEditListener;", "getMOnActionEditListener", "()Lcom/fourksoft/base/ui/adapter/listener/OnRecyclerActionEditListener;", "setMOnActionEditListener", "(Lcom/fourksoft/base/ui/adapter/listener/OnRecyclerActionEditListener;)V", "mOnActionListener", "Lcom/fourksoft/base/ui/adapter/listener/OnRecyclerActionListener;", "getMOnActionListener", "()Lcom/fourksoft/base/ui/adapter/listener/OnRecyclerActionListener;", "setMOnActionListener", "(Lcom/fourksoft/base/ui/adapter/listener/OnRecyclerActionListener;)V", "mOnItemClickListener", "Lcom/fourksoft/base/ui/adapter/listener/OnItemClickListener;", "getMOnItemClickListener", "()Lcom/fourksoft/base/ui/adapter/listener/OnItemClickListener;", "setMOnItemClickListener", "(Lcom/fourksoft/base/ui/adapter/listener/OnItemClickListener;)V", "mOnItemLongClickListener", "Lcom/fourksoft/base/ui/adapter/listener/OnItemLongClickListener;", "getMOnItemLongClickListener", "()Lcom/fourksoft/base/ui/adapter/listener/OnItemLongClickListener;", "setMOnItemLongClickListener", "(Lcom/fourksoft/base/ui/adapter/listener/OnItemLongClickListener;)V", "addAll", "", "data", "", "addInternal", "item", "(Ljava/lang/Object;)V", "position", "", "(ILjava/lang/Object;)V", "addItem", "clear", "clearAndAddAll", "compareByDiffUtil", "oldData", "", "newData", "getAll", "getDiffUtilCallback", "getItem", "(I)Ljava/lang/Object;", "getItemCount", "isEmpty", "", "removeItem", "restoreItem", "(Ljava/lang/Object;I)V", "setDiffUtilCallback", "setOnActionDeleteListener", "onActionListener", "setOnActionEditListener", "setOnActionListener", "setOnItemClickListener", "onItemClickListener", "setOnItemLongClickListener", "onItemLongClickListener", "updateAll", "updateItem", "basemodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes.dex */
public abstract class BaseRecyclerListAdapter<M, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> implements RecyclerCollectionAdapter<M> {
    private BaseDiffUtilCallback<M> mDiffUtilCallback;
    private List<M> mList;
    private OnRecyclerActionDeleteListener<M> mOnActionDeleteListener;
    private OnRecyclerActionEditListener<M> mOnActionEditListener;
    private OnRecyclerActionListener<M> mOnActionListener;
    private OnItemClickListener<M> mOnItemClickListener;
    private OnItemLongClickListener<M> mOnItemLongClickListener;

    public BaseRecyclerListAdapter() {
        this.mList = new ArrayList();
        this.mList = new ArrayList();
    }

    protected final List<M> getMList() {
        return this.mList;
    }

    protected final void setMList(List<M> list) {
        Intrinsics.checkNotNullParameter(list, "<set-?>");
        this.mList = list;
    }

    protected final BaseDiffUtilCallback<M> getMDiffUtilCallback() {
        return this.mDiffUtilCallback;
    }

    protected final void setMDiffUtilCallback(BaseDiffUtilCallback<M> baseDiffUtilCallback) {
        this.mDiffUtilCallback = baseDiffUtilCallback;
    }

    protected final OnItemClickListener<M> getMOnItemClickListener() {
        return this.mOnItemClickListener;
    }

    protected final void setMOnItemClickListener(OnItemClickListener<M> onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    protected final OnItemLongClickListener<M> getMOnItemLongClickListener() {
        return this.mOnItemLongClickListener;
    }

    protected final void setMOnItemLongClickListener(OnItemLongClickListener<M> onItemLongClickListener) {
        this.mOnItemLongClickListener = onItemLongClickListener;
    }

    protected final OnRecyclerActionDeleteListener<M> getMOnActionDeleteListener() {
        return this.mOnActionDeleteListener;
    }

    protected final void setMOnActionDeleteListener(OnRecyclerActionDeleteListener<M> onRecyclerActionDeleteListener) {
        this.mOnActionDeleteListener = onRecyclerActionDeleteListener;
    }

    protected final OnRecyclerActionEditListener<M> getMOnActionEditListener() {
        return this.mOnActionEditListener;
    }

    protected final void setMOnActionEditListener(OnRecyclerActionEditListener<M> onRecyclerActionEditListener) {
        this.mOnActionEditListener = onRecyclerActionEditListener;
    }

    protected final OnRecyclerActionListener<M> getMOnActionListener() {
        return this.mOnActionListener;
    }

    protected final void setMOnActionListener(OnRecyclerActionListener<M> onRecyclerActionListener) {
        this.mOnActionListener = onRecyclerActionListener;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public BaseRecyclerListAdapter(List<? extends M> list) {
        this();
        Intrinsics.checkNotNullParameter(list, "list");
        this.mList.addAll(list);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public BaseRecyclerListAdapter(BaseDiffUtilCallback<M> diffUtilCallback) {
        this();
        Intrinsics.checkNotNullParameter(diffUtilCallback, "diffUtilCallback");
        this.mDiffUtilCallback = diffUtilCallback;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public BaseRecyclerListAdapter(List<? extends M> list, BaseDiffUtilCallback<M> diffUtilCallback) {
        this(list);
        Intrinsics.checkNotNullParameter(list, "list");
        Intrinsics.checkNotNullParameter(diffUtilCallback, "diffUtilCallback");
        this.mDiffUtilCallback = diffUtilCallback;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.fourksoft.base.ui.adapter.RecyclerCollectionAdapter
    public void compareByDiffUtil(Collection<M> oldData, Collection<M> newData) {
        Intrinsics.checkNotNullParameter(oldData, "oldData");
        Intrinsics.checkNotNullParameter(newData, "newData");
        BaseDiffUtilCallback<M> baseDiffUtilCallback = this.mDiffUtilCallback;
        if (baseDiffUtilCallback != null) {
            baseDiffUtilCallback.updateLists(oldData, newData);
        }
        BaseDiffUtilCallback<M> baseDiffUtilCallback2 = this.mDiffUtilCallback;
        DiffUtil.DiffResult diffResultCalculateDiff = baseDiffUtilCallback2 != null ? DiffUtil.calculateDiff(baseDiffUtilCallback2) : null;
        oldData.clear();
        oldData.addAll(newData);
        if (diffResultCalculateDiff != null) {
            diffResultCalculateDiff.dispatchUpdatesTo(this);
        }
    }

    @Override // com.fourksoft.base.ui.adapter.RecyclerCollectionAdapter
    public void compareByDiffUtil(Collection<M> oldData) {
        Intrinsics.checkNotNullParameter(oldData, "oldData");
        Collection<M> collectionUnmodifiableCollection = Collections.unmodifiableCollection(this.mList);
        Intrinsics.checkNotNullExpressionValue(collectionUnmodifiableCollection, "unmodifiableCollection(mList)");
        compareByDiffUtil(oldData, collectionUnmodifiableCollection);
    }

    @Override // com.fourksoft.base.ui.adapter.RecyclerCollectionAdapter
    public void setDiffUtilCallback(BaseDiffUtilCallback<M> diffUtilCallback) {
        Intrinsics.checkNotNullParameter(diffUtilCallback, "diffUtilCallback");
        this.mDiffUtilCallback = diffUtilCallback;
    }

    @Override // com.fourksoft.base.ui.adapter.RecyclerCollectionAdapter
    public BaseDiffUtilCallback<M> getDiffUtilCallback() {
        return this.mDiffUtilCallback;
    }

    @Override // com.fourksoft.base.ui.adapter.RecyclerCollectionAdapter
    public void setOnItemClickListener(OnItemClickListener<M> onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    @Override // com.fourksoft.base.ui.adapter.RecyclerCollectionAdapter
    public void setOnItemLongClickListener(OnItemLongClickListener<M> onItemLongClickListener) {
        this.mOnItemLongClickListener = onItemLongClickListener;
    }

    @Override // com.fourksoft.base.ui.adapter.RecyclerCollectionAdapter
    public void setOnActionEditListener(OnRecyclerActionEditListener<M> onActionListener) {
        this.mOnActionEditListener = onActionListener;
    }

    @Override // com.fourksoft.base.ui.adapter.RecyclerCollectionAdapter
    public void setOnActionDeleteListener(OnRecyclerActionDeleteListener<M> onActionListener) {
        this.mOnActionDeleteListener = onActionListener;
    }

    @Override // com.fourksoft.base.ui.adapter.RecyclerCollectionAdapter
    public void setOnActionListener(OnRecyclerActionListener<M> onActionListener) {
        this.mOnActionListener = onActionListener;
    }

    @Override // com.fourksoft.base.ui.adapter.RecyclerCollectionAdapter
    public void addItem(M item) {
        Unit unit;
        ArrayList arrayList = new ArrayList(this.mList);
        BaseDiffUtilCallback<M> baseDiffUtilCallback = this.mDiffUtilCallback;
        if (baseDiffUtilCallback != null) {
            int size = this.mList.size();
            int i = 0;
            while (true) {
                if (i >= size) {
                    break;
                }
                if (baseDiffUtilCallback.areItemsTheSame(this.mList.get(i), item)) {
                    this.mList.remove(i);
                    this.mList.add(i, item);
                    break;
                }
                i++;
            }
            compareByDiffUtil(TypeIntrinsics.asMutableCollection(arrayList));
            unit = Unit.INSTANCE;
        } else {
            unit = null;
        }
        if (unit == null) {
            addInternal(item);
            notifyItemInserted(this.mList.size());
        }
    }

    @Override // com.fourksoft.base.ui.adapter.RecyclerCollectionAdapter
    public void addItem(int position, M item) {
        Unit unit;
        ArrayList arrayList = new ArrayList(this.mList);
        addInternal(position, item);
        if (this.mDiffUtilCallback != null) {
            compareByDiffUtil(TypeIntrinsics.asMutableCollection(arrayList));
            unit = Unit.INSTANCE;
        } else {
            unit = null;
        }
        if (unit == null) {
            notifyItemInserted(position);
        }
    }

    @Override // com.fourksoft.base.ui.adapter.RecyclerCollectionAdapter
    public void addAll(Collection<? extends M> data) {
        Unit unit;
        if (data == null || data.isEmpty()) {
            return;
        }
        ArrayList arrayList = new ArrayList(this.mList);
        Iterator<? extends M> it = data.iterator();
        while (it.hasNext()) {
            addInternal(it.next());
        }
        if (this.mDiffUtilCallback != null) {
            compareByDiffUtil(TypeIntrinsics.asMutableCollection(arrayList));
            unit = Unit.INSTANCE;
        } else {
            unit = null;
        }
        if (unit == null) {
            int size = data.size();
            notifyItemRangeInserted(this.mList.size() - size, size);
        }
    }

    @Override // com.fourksoft.base.ui.adapter.RecyclerCollectionAdapter
    public void updateItem(M item) {
        Unit unit;
        int iIndexOf;
        ArrayList arrayList = new ArrayList(this.mList);
        BaseDiffUtilCallback<M> baseDiffUtilCallback = this.mDiffUtilCallback;
        if (baseDiffUtilCallback != null) {
            int size = this.mList.size();
            for (int i = 0; i < size; i++) {
                if (baseDiffUtilCallback.areItemsTheSame(this.mList.get(i), item)) {
                    this.mList.remove(i);
                    this.mList.add(i, item);
                }
            }
            compareByDiffUtil(TypeIntrinsics.asMutableCollection(arrayList));
            unit = Unit.INSTANCE;
        } else {
            unit = null;
        }
        if (unit != null || (iIndexOf = this.mList.indexOf(item)) < 0) {
            return;
        }
        this.mList.remove(iIndexOf);
        this.mList.add(iIndexOf, item);
        notifyItemChanged(iIndexOf);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.fourksoft.base.ui.adapter.RecyclerCollectionAdapter
    public void updateAll(Collection<? extends M> data) {
        Unit unit;
        if (data != 0) {
            TypeIntrinsics.asMutableCollection(data);
            if (this.mDiffUtilCallback != null) {
                compareByDiffUtil(this.mList, data);
                unit = Unit.INSTANCE;
            } else {
                unit = null;
            }
            if (unit == null) {
                clearAndAddAll(data);
            }
        }
    }

    @Override // com.fourksoft.base.ui.adapter.RecyclerCollectionAdapter
    public void removeItem(M item) {
        removeItem(this.mList.indexOf(item));
    }

    @Override // com.fourksoft.base.ui.adapter.RecyclerCollectionAdapter
    public void removeItem(int position) {
        Unit unit;
        ArrayList arrayList = new ArrayList(this.mList);
        if (position >= 0) {
            this.mList.remove(position);
            if (this.mDiffUtilCallback != null) {
                compareByDiffUtil(TypeIntrinsics.asMutableCollection(arrayList));
                unit = Unit.INSTANCE;
            } else {
                unit = null;
            }
            if (unit == null) {
                notifyItemRemoved(position);
            }
        }
        if (isEmpty()) {
            clear();
        }
    }

    @Override // com.fourksoft.base.ui.adapter.RecyclerCollectionAdapter
    public void clearAndAddAll(Collection<? extends M> data) {
        if (data != null) {
            this.mList.clear();
            Iterator<? extends M> it = data.iterator();
            while (it.hasNext()) {
                addInternal(it.next());
            }
            notifyDataSetChanged();
        }
    }

    @Override // com.fourksoft.base.ui.adapter.RecyclerCollectionAdapter
    public void clear() {
        Unit unit;
        ArrayList arrayList = new ArrayList(this.mList);
        this.mList.clear();
        if (this.mDiffUtilCallback != null) {
            compareByDiffUtil(TypeIntrinsics.asMutableCollection(arrayList));
            unit = Unit.INSTANCE;
        } else {
            unit = null;
        }
        if (unit == null) {
            notifyDataSetChanged();
        }
    }

    @Override // com.fourksoft.base.ui.adapter.RecyclerCollectionAdapter
    public boolean isEmpty() {
        return this.mList.isEmpty();
    }

    @Override // com.fourksoft.base.ui.adapter.RecyclerCollectionAdapter
    public void restoreItem(M item, int position) {
        Unit unit;
        ArrayList arrayList = new ArrayList(this.mList);
        this.mList.add(position, item);
        if (this.mDiffUtilCallback != null) {
            compareByDiffUtil(TypeIntrinsics.asMutableCollection(arrayList));
            unit = Unit.INSTANCE;
        } else {
            unit = null;
        }
        if (unit == null) {
            notifyItemInserted(position);
        }
    }

    @Override // com.fourksoft.base.ui.adapter.RecyclerCollectionAdapter
    public M getItem(int position) {
        return this.mList.get(position);
    }

    @Override // com.fourksoft.base.ui.adapter.RecyclerCollectionAdapter
    public Collection<M> getAll() {
        return this.mList;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.mList.size();
    }

    private final void addInternal(M item) {
        this.mList.add(item);
    }

    private final void addInternal(int position, M item) {
        this.mList.add(position, item);
    }
}
