package com.fourksoft.base.ui.adapter.extensions;

import com.fourksoft.base.ui.adapter.listener.OnRecyclerActionDeleteListener;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* JADX INFO: Add missing generic type declarations: [M] */
/* compiled from: RecyclerAdapter.kt */
@Metadata(d1 = {"\u0000\u0013\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00028\u00000\u0001J\u0015\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010\u0005¨\u0006\u0006"}, d2 = {"com/fourksoft/base/ui/adapter/extensions/RecyclerAdapterKt$doOnActionDelete$listener$1", "Lcom/fourksoft/base/ui/adapter/listener/OnRecyclerActionDeleteListener;", "onDelete", "", "item", "(Ljava/lang/Object;)V", "basemodule_release"}, k = 1, mv = {1, 8, 0}, xi = 176)
/* loaded from: classes.dex */
public final class RecyclerAdapterKt$doOnActionDelete$listener$1<M> implements OnRecyclerActionDeleteListener<M> {
    final /* synthetic */ Function1<M, Unit> $action;

    /* JADX WARN: Multi-variable type inference failed */
    public RecyclerAdapterKt$doOnActionDelete$listener$1(Function1<? super M, Unit> function1) {
        this.$action = function1;
    }

    @Override // com.fourksoft.base.ui.adapter.listener.OnRecyclerActionDeleteListener
    public void onDelete(M item) {
        this.$action.invoke(item);
    }
}
