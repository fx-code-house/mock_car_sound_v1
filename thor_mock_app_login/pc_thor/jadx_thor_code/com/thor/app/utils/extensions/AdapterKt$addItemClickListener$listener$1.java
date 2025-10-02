package com.thor.app.utils.extensions;

import android.view.View;
import com.thor.basemodule.gui.adapters.RecyclerCollectionAdapter;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

/* JADX INFO: Add missing generic type declarations: [M] */
/* compiled from: Adapter.kt */
@Metadata(d1 = {"\u0000#\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00028\u00000\u0001J\u0015\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010\u0005J\u001f\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00028\u00002\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007H\u0016¢\u0006\u0002\u0010\bJ\u001d\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00028\u00002\u0006\u0010\t\u001a\u00020\nH\u0016¢\u0006\u0002\u0010\u000bJ\u0012\u0010\u0002\u001a\u00020\u00032\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007H\u0016¨\u0006\f"}, d2 = {"com/thor/app/utils/extensions/AdapterKt$addItemClickListener$listener$1", "Lcom/thor/basemodule/gui/adapters/RecyclerCollectionAdapter$OnItemClickListener;", "onItemClick", "", "item", "(Ljava/lang/Object;)V", "view", "Landroid/view/View;", "(Ljava/lang/Object;Landroid/view/View;)V", "position", "", "(Ljava/lang/Object;I)V", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 176)
/* loaded from: classes3.dex */
public final class AdapterKt$addItemClickListener$listener$1<M> implements RecyclerCollectionAdapter.OnItemClickListener<M> {
    final /* synthetic */ Function1<M, Unit> $onItemClick;
    final /* synthetic */ Function2<M, Integer, Unit> $onItemPositionClick;
    final /* synthetic */ Function2<M, View, Unit> $onItemViewClick;
    final /* synthetic */ Function1<View, Unit> $onViewClick;

    /* JADX WARN: Multi-variable type inference failed */
    public AdapterKt$addItemClickListener$listener$1(Function1<? super M, Unit> function1, Function2<? super M, ? super Integer, Unit> function2, Function1<? super View, Unit> function12, Function2<? super M, ? super View, Unit> function22) {
        this.$onItemClick = function1;
        this.$onItemPositionClick = function2;
        this.$onViewClick = function12;
        this.$onItemViewClick = function22;
    }

    @Override // com.thor.basemodule.gui.adapters.RecyclerCollectionAdapter.OnItemClickListener
    public void onItemClick(M item) {
        this.$onItemClick.invoke(item);
    }

    @Override // com.thor.basemodule.gui.adapters.RecyclerCollectionAdapter.OnItemClickListener
    public void onItemClick(M item, int position) {
        this.$onItemPositionClick.invoke(item, Integer.valueOf(position));
    }

    @Override // com.thor.basemodule.gui.adapters.RecyclerCollectionAdapter.OnItemClickListener
    public void onItemClick(View view) {
        this.$onViewClick.invoke(view);
    }

    @Override // com.thor.basemodule.gui.adapters.RecyclerCollectionAdapter.OnItemClickListener
    public void onItemClick(M item, View view) {
        this.$onItemViewClick.invoke(item, view);
    }
}
