package com.fourksoft.base.ui.adapter.extensions;

import com.fourksoft.base.ui.adapter.listener.OnRecyclerActionListener;
import com.fourksoft.base.ui.adapter.type.ActionRecycler;
import com.google.android.exoplayer2.source.rtsp.SessionDescription;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: Add missing generic type declarations: [M] */
/* compiled from: RecyclerAdapter.kt */
@Metadata(d1 = {"\u0000\u0019\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00028\u00000\u0001J\u001d\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010\u0007¨\u0006\b"}, d2 = {"com/fourksoft/base/ui/adapter/extensions/RecyclerAdapterKt$doOnAction$listener$1", "Lcom/fourksoft/base/ui/adapter/listener/OnRecyclerActionListener;", "onAction", "", SessionDescription.ATTR_TYPE, "Lcom/fourksoft/base/ui/adapter/type/ActionRecycler;", "model", "(Lcom/fourksoft/base/ui/adapter/type/ActionRecycler;Ljava/lang/Object;)V", "basemodule_release"}, k = 1, mv = {1, 8, 0}, xi = 176)
/* loaded from: classes.dex */
public final class RecyclerAdapterKt$doOnAction$listener$1<M> implements OnRecyclerActionListener<M> {
    final /* synthetic */ Function2<ActionRecycler, M, Unit> $action;

    /* JADX WARN: Multi-variable type inference failed */
    public RecyclerAdapterKt$doOnAction$listener$1(Function2<? super ActionRecycler, ? super M, Unit> function2) {
        this.$action = function2;
    }

    @Override // com.fourksoft.base.ui.adapter.listener.OnRecyclerActionListener
    public void onAction(ActionRecycler type, M model) {
        Intrinsics.checkNotNullParameter(type, "type");
        this.$action.invoke(type, model);
    }
}
