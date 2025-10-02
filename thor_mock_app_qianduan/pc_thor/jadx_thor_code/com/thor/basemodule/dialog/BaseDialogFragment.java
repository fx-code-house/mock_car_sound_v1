package com.thor.basemodule.dialog;

import android.os.Bundle;
import android.view.View;
import androidx.fragment.app.DialogFragment;
import com.fourksoft.base.ui.dialog.listener.OnCancelDialogListener;
import com.fourksoft.base.ui.dialog.listener.OnConfirmDialogListener;
import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import com.thor.basemodule.R;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: BaseDialogFragment.kt */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\b&\u0018\u00002\u00020\u00012\u00020\u0002B\u0005¢\u0006\u0002\u0010\u0003J\b\u0010\u0010\u001a\u00020\u0011H&J\b\u0010\u0012\u001a\u00020\u0011H\u0014J\b\u0010\u0013\u001a\u00020\u0011H\u0014J\b\u0010\u0014\u001a\u00020\u0011H\u0014J\u0012\u0010\u0015\u001a\u00020\u00112\b\u0010\u0016\u001a\u0004\u0018\u00010\u0017H\u0016J\u001a\u0010\u0018\u001a\u00020\u00112\u0006\u0010\u0019\u001a\u00020\u001a2\b\u0010\u0016\u001a\u0004\u0018\u00010\u0017H\u0016J\u0012\u0010\u001b\u001a\u00020\u00112\b\u0010\u001c\u001a\u0004\u0018\u00010\u0005H\u0016J\u0012\u0010\u001d\u001a\u00020\u00112\b\u0010\u001c\u001a\u0004\u0018\u00010\u000bH\u0016R\u001c\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u001c\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000f¨\u0006\u001e"}, d2 = {"Lcom/thor/basemodule/dialog/BaseDialogFragment;", "Landroidx/fragment/app/DialogFragment;", "Lcom/thor/basemodule/dialog/DialogInterface;", "()V", "mOnCancelListener", "Lcom/fourksoft/base/ui/dialog/listener/OnCancelDialogListener;", "getMOnCancelListener", "()Lcom/fourksoft/base/ui/dialog/listener/OnCancelDialogListener;", "setMOnCancelListener", "(Lcom/fourksoft/base/ui/dialog/listener/OnCancelDialogListener;)V", "mOnConfirmListener", "Lcom/fourksoft/base/ui/dialog/listener/OnConfirmDialogListener;", "getMOnConfirmListener", "()Lcom/fourksoft/base/ui/dialog/listener/OnConfirmDialogListener;", "setMOnConfirmListener", "(Lcom/fourksoft/base/ui/dialog/listener/OnConfirmDialogListener;)V", "init", "", "initListeners", "initViewModels", "initViews", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onViewCreated", "view", "Landroid/view/View;", "setOnCancelListener", ServiceSpecificExtraArgs.CastExtraArgs.LISTENER, "setOnConfirmListener", "basemodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public abstract class BaseDialogFragment extends DialogFragment implements DialogInterface {
    private OnCancelDialogListener mOnCancelListener;
    private OnConfirmDialogListener mOnConfirmListener;

    public abstract void init();

    protected void initListeners() {
    }

    protected void initViewModels() {
    }

    protected void initViews() {
    }

    protected final OnConfirmDialogListener getMOnConfirmListener() {
        return this.mOnConfirmListener;
    }

    protected final void setMOnConfirmListener(OnConfirmDialogListener onConfirmDialogListener) {
        this.mOnConfirmListener = onConfirmDialogListener;
    }

    protected final OnCancelDialogListener getMOnCancelListener() {
        return this.mOnCancelListener;
    }

    protected final void setMOnCancelListener(OnCancelDialogListener onCancelDialogListener) {
        this.mOnCancelListener = onCancelDialogListener;
    }

    @Override // com.thor.basemodule.dialog.DialogInterface
    public void setOnConfirmListener(OnConfirmDialogListener listener) {
        this.mOnConfirmListener = listener;
    }

    @Override // com.thor.basemodule.dialog.DialogInterface
    public void setOnCancelListener(OnCancelDialogListener listener) {
        this.mOnCancelListener = listener;
    }

    @Override // androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Intrinsics.checkNotNullParameter(view, "view");
        super.onViewCreated(view, savedInstanceState);
        initViews();
        initViewModels();
        initListeners();
        init();
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(2, R.style.BaseDialogTheme);
    }
}
