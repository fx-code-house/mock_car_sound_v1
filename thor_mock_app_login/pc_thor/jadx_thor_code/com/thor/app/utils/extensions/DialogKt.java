package com.thor.app.utils.extensions;

import com.fourksoft.base.ui.dialog.listener.OnConfirmDialogListener;
import com.google.android.gms.measurement.api.AppMeasurementSdk;
import com.thor.basemodule.dialog.BaseDialogFragment;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: Dialog.kt */
@Metadata(d1 = {"\u0000\"\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\u001a7\u0010\u0000\u001a\u00020\u0001*\u00020\u00022%\b\u0006\u0010\u0003\u001a\u001f\u0012\u0015\u0012\u0013\u0018\u00010\u0005¢\u0006\f\b\u0006\u0012\b\b\u0007\u0012\u0004\b\b(\b\u0012\u0004\u0012\u00020\t0\u0004H\u0086\bø\u0001\u0000\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\n"}, d2 = {"doOnConfirm", "Lcom/fourksoft/base/ui/dialog/listener/OnConfirmDialogListener;", "Lcom/thor/basemodule/dialog/BaseDialogFragment;", "action", "Lkotlin/Function1;", "", "Lkotlin/ParameterName;", AppMeasurementSdk.ConditionalUserProperty.NAME, "info", "", "thor-1.8.7_release"}, k = 2, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class DialogKt {
    public static /* synthetic */ OnConfirmDialogListener doOnConfirm$default(BaseDialogFragment baseDialogFragment, Function1 action, int i, Object obj) {
        if ((i & 1) != 0) {
            action = new Function1<String, Unit>() { // from class: com.thor.app.utils.extensions.DialogKt.doOnConfirm.1
                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(String str) {
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(String str) {
                    invoke2(str);
                    return Unit.INSTANCE;
                }
            };
        }
        Intrinsics.checkNotNullParameter(baseDialogFragment, "<this>");
        Intrinsics.checkNotNullParameter(action, "action");
        DialogKt$doOnConfirm$listener$1 dialogKt$doOnConfirm$listener$1 = new DialogKt$doOnConfirm$listener$1(action);
        baseDialogFragment.setOnConfirmListener(dialogKt$doOnConfirm$listener$1);
        return dialogKt$doOnConfirm$listener$1;
    }

    public static final OnConfirmDialogListener doOnConfirm(BaseDialogFragment baseDialogFragment, Function1<? super String, Unit> action) {
        Intrinsics.checkNotNullParameter(baseDialogFragment, "<this>");
        Intrinsics.checkNotNullParameter(action, "action");
        DialogKt$doOnConfirm$listener$1 dialogKt$doOnConfirm$listener$1 = new DialogKt$doOnConfirm$listener$1(action);
        baseDialogFragment.setOnConfirmListener(dialogKt$doOnConfirm$listener$1);
        return dialogKt$doOnConfirm$listener$1;
    }
}
