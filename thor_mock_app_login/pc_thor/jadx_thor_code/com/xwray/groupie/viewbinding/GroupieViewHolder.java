package com.xwray.groupie.viewbinding;

import androidx.viewbinding.ViewBinding;

/* loaded from: classes3.dex */
public class GroupieViewHolder<T extends ViewBinding> extends com.xwray.groupie.GroupieViewHolder {
    public final T binding;

    public GroupieViewHolder(T t) {
        super(t.getRoot());
        this.binding = t;
    }
}
