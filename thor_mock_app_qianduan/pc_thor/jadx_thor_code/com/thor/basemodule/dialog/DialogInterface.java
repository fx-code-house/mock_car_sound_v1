package com.thor.basemodule.dialog;

import com.fourksoft.base.ui.dialog.listener.OnCancelDialogListener;
import com.fourksoft.base.ui.dialog.listener.OnConfirmDialogListener;
import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import kotlin.Metadata;

/* compiled from: DialogInterface.kt */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u0012\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005H&J\u0012\u0010\u0006\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u0007H&¨\u0006\b"}, d2 = {"Lcom/thor/basemodule/dialog/DialogInterface;", "", "setOnCancelListener", "", ServiceSpecificExtraArgs.CastExtraArgs.LISTENER, "Lcom/fourksoft/base/ui/dialog/listener/OnCancelDialogListener;", "setOnConfirmListener", "Lcom/fourksoft/base/ui/dialog/listener/OnConfirmDialogListener;", "basemodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public interface DialogInterface {
    void setOnCancelListener(OnCancelDialogListener listener);

    void setOnConfirmListener(OnConfirmDialogListener listener);
}
