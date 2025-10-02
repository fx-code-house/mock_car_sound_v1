package com.thor.app.utils.extensions;

import com.fourksoft.base.ui.dialog.listener.OnConfirmDialogListener;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* compiled from: Dialog.kt */
@Metadata(d1 = {"\u0000\u0017\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\u0012\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005H\u0016¨\u0006\u0006"}, d2 = {"com/thor/app/utils/extensions/DialogKt$doOnConfirm$listener$1", "Lcom/fourksoft/base/ui/dialog/listener/OnConfirmDialogListener;", "onConfirm", "", "info", "", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 176)
/* loaded from: classes3.dex */
public final class DialogKt$doOnConfirm$listener$1 implements OnConfirmDialogListener {
    final /* synthetic */ Function1<String, Unit> $action;

    /* JADX WARN: Multi-variable type inference failed */
    public DialogKt$doOnConfirm$listener$1(Function1<? super String, Unit> function1) {
        this.$action = function1;
    }

    @Override // com.fourksoft.base.ui.dialog.listener.OnConfirmDialogListener
    public void onConfirm(String info) {
        this.$action.invoke(info);
    }
}
