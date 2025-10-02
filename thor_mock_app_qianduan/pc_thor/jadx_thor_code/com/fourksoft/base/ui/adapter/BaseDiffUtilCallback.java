package com.fourksoft.base.ui.adapter;

import androidx.recyclerview.widget.DiffUtil;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.mapstruct.ap.shaded.freemarker.template.Template;

/* compiled from: BaseDiffUtilCallback.kt */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\u0010\u001e\n\u0002\b\u0003\b&\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u0002B\u0007\b\u0016¢\u0006\u0002\u0010\u0003B#\b\u0016\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005¢\u0006\u0002\u0010\u0007J\u001d\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00028\u00002\u0006\u0010\r\u001a\u00028\u0000H&¢\u0006\u0002\u0010\u000eJ\u0018\u0010\n\u001a\u00020\u000b2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0010H\u0016J\u001d\u0010\u0012\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00028\u00002\u0006\u0010\r\u001a\u00028\u0000H&¢\u0006\u0002\u0010\u000eJ\u0018\u0010\u0012\u001a\u00020\u000b2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0010H\u0016J\b\u0010\u0013\u001a\u00020\u0010H\u0016J\b\u0010\u0014\u001a\u00020\u0010H\u0016J\u0016\u0010\u0015\u001a\u00020\u00162\u000e\u0010\u0006\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u0017J\u0016\u0010\u0018\u001a\u00020\u00162\u000e\u0010\u0004\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u0017J&\u0010\u0019\u001a\u00020\u00162\u000e\u0010\u0004\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u00172\u000e\u0010\u0006\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u0017R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001a"}, d2 = {"Lcom/fourksoft/base/ui/adapter/BaseDiffUtilCallback;", Template.DEFAULT_NAMESPACE_PREFIX, "Landroidx/recyclerview/widget/DiffUtil$Callback;", "()V", "oldList", "", "newList", "(Ljava/util/List;Ljava/util/List;)V", "mNewList", "mOldList", "areContentsTheSame", "", "oldItem", "newItem", "(Ljava/lang/Object;Ljava/lang/Object;)Z", "oldItemPosition", "", "newItemPosition", "areItemsTheSame", "getNewListSize", "getOldListSize", "setNewList", "", "", "setOldList", "updateLists", "basemodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes.dex */
public abstract class BaseDiffUtilCallback<D> extends DiffUtil.Callback {
    private final List<D> mNewList;
    private final List<D> mOldList;

    public abstract boolean areContentsTheSame(D oldItem, D newItem);

    public abstract boolean areItemsTheSame(D oldItem, D newItem);

    public BaseDiffUtilCallback() {
        this.mOldList = new ArrayList();
        this.mNewList = new ArrayList();
    }

    public BaseDiffUtilCallback(List<D> oldList, List<D> newList) {
        Intrinsics.checkNotNullParameter(oldList, "oldList");
        Intrinsics.checkNotNullParameter(newList, "newList");
        this.mOldList = oldList;
        this.mNewList = newList;
    }

    public final void updateLists(Collection<? extends D> oldList, Collection<? extends D> newList) {
        setNewList(newList);
        setOldList(oldList);
    }

    public final void setNewList(Collection<? extends D> newList) {
        this.mNewList.clear();
        List<D> list = this.mNewList;
        Intrinsics.checkNotNull(newList);
        list.addAll(newList);
    }

    public final void setOldList(Collection<? extends D> oldList) {
        this.mOldList.clear();
        List<D> list = this.mOldList;
        Intrinsics.checkNotNull(oldList);
        list.addAll(oldList);
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
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return areItemsTheSame(this.mOldList.get(oldItemPosition), this.mNewList.get(newItemPosition));
    }

    @Override // androidx.recyclerview.widget.DiffUtil.Callback
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return areContentsTheSame(this.mOldList.get(oldItemPosition), this.mNewList.get(newItemPosition));
    }
}
