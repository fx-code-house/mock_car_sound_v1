package com.thor.app.utils.extensions;

import com.thor.basemodule.gui.adapters.RecyclerCollectionAdapter;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* compiled from: Adapter.kt */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u00022\u000e\u0010\u0003\u001a\n \u0004*\u0004\u0018\u0001H\u0002H\u0002H\n¢\u0006\u0004\b\u0005\u0010\u0006"}, d2 = {"<anonymous>", "", "M", "it", "kotlin.jvm.PlatformType", "onConfig", "(Ljava/lang/Object;)V"}, k = 3, mv = {1, 8, 0}, xi = 176)
/* loaded from: classes3.dex */
public final class AdapterKt$doOnActionConfig$listener$1<M> implements RecyclerCollectionAdapter.OnRecyclerActionConfigListener {
    final /* synthetic */ Function1<M, Unit> $action;

    /* JADX WARN: Multi-variable type inference failed */
    public AdapterKt$doOnActionConfig$listener$1(Function1<? super M, Unit> function1) {
        this.$action = function1;
    }

    @Override // com.thor.basemodule.gui.adapters.RecyclerCollectionAdapter.OnRecyclerActionConfigListener
    public final void onConfig(M m) {
        this.$action.invoke(m);
    }
}
