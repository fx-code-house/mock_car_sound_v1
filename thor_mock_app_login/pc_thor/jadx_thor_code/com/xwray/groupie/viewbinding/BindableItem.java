package com.xwray.groupie.viewbinding;

import android.view.View;
import androidx.viewbinding.ViewBinding;
import com.xwray.groupie.Item;
import java.util.List;

/* loaded from: classes3.dex */
public abstract class BindableItem<T extends ViewBinding> extends Item<GroupieViewHolder<T>> {
    public abstract void bind(T t, int i);

    protected abstract T initializeViewBinding(View view);

    @Override // com.xwray.groupie.Item
    public /* bridge */ /* synthetic */ void bind(com.xwray.groupie.GroupieViewHolder groupieViewHolder, int i, List list) {
        bind((GroupieViewHolder) groupieViewHolder, i, (List<Object>) list);
    }

    public BindableItem() {
    }

    protected BindableItem(long j) {
        super(j);
    }

    @Override // com.xwray.groupie.Item
    public GroupieViewHolder<T> createViewHolder(View view) {
        return new GroupieViewHolder<>(initializeViewBinding(view));
    }

    @Override // com.xwray.groupie.Item
    public void bind(GroupieViewHolder<T> groupieViewHolder, int i) {
        throw new RuntimeException("Doesn't get called");
    }

    public void bind(GroupieViewHolder<T> groupieViewHolder, int i, List<Object> list) {
        bind((BindableItem<T>) groupieViewHolder.binding, i, list);
    }

    public void bind(T t, int i, List<Object> list) {
        bind((BindableItem<T>) t, i);
    }
}
