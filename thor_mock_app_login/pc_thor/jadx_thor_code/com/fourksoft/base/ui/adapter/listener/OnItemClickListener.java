package com.fourksoft.base.ui.adapter.listener;

import android.view.View;
import kotlin.Metadata;

/* compiled from: OnItemClickListener.kt */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u0002J\u0015\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00028\u0000H&¢\u0006\u0002\u0010\u0006J\u001f\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00028\u00002\b\u0010\u0007\u001a\u0004\u0018\u00010\bH&¢\u0006\u0002\u0010\tJ\u0012\u0010\u0003\u001a\u00020\u00042\b\u0010\u0007\u001a\u0004\u0018\u00010\bH&¨\u0006\n"}, d2 = {"Lcom/fourksoft/base/ui/adapter/listener/OnItemClickListener;", "M", "", "onItemClick", "", "item", "(Ljava/lang/Object;)V", "view", "Landroid/view/View;", "(Ljava/lang/Object;Landroid/view/View;)V", "basemodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes.dex */
public interface OnItemClickListener<M> {
    void onItemClick(View view);

    void onItemClick(M item);

    void onItemClick(M item, View view);
}
