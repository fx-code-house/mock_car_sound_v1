package com.thor.app.gui.dialog.dialogs;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.carsystems.thor.app.databinding.DialogFragmentTestersBinding;
import com.fourksoft.base.ui.dialog.listener.OnConfirmDialogListener;
import com.thor.basemodule.dialog.BaseBindingDialogFragment;
import kotlin.Metadata;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: TestersDialogFragment.kt */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0004\u0018\u0000 \u000f2\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001\u000fB\u0005¢\u0006\u0002\u0010\u0003J\b\u0010\u000b\u001a\u00020\fH\u0016J\b\u0010\r\u001a\u00020\fH\u0014J\b\u0010\u000e\u001a\u00020\fH\u0014R.\u0010\u0004\u001a\u001c\u0012\u0004\u0012\u00020\u0006\u0012\u0006\u0012\u0004\u0018\u00010\u0007\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\u00020\u00058VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\n¨\u0006\u0010"}, d2 = {"Lcom/thor/app/gui/dialog/dialogs/TestersDialogFragment;", "Lcom/thor/basemodule/dialog/BaseBindingDialogFragment;", "Lcom/carsystems/thor/app/databinding/DialogFragmentTestersBinding;", "()V", "bindingInflater", "Lkotlin/Function3;", "Landroid/view/LayoutInflater;", "Landroid/view/ViewGroup;", "", "getBindingInflater", "()Lkotlin/jvm/functions/Function3;", "init", "", "initListeners", "initViews", "Companion", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class TestersDialogFragment extends BaseBindingDialogFragment<DialogFragmentTestersBinding> {
    public static final String PASSWORD = "THOR2022";

    @Override // com.thor.basemodule.dialog.BaseDialogFragment
    public void init() {
    }

    @Override // com.thor.basemodule.dialog.BaseDialogFragment
    protected void initViews() {
    }

    @Override // com.thor.basemodule.dialog.BaseBindingDialogFragment
    public Function3<LayoutInflater, ViewGroup, Boolean, DialogFragmentTestersBinding> getBindingInflater() {
        return TestersDialogFragment$bindingInflater$1.INSTANCE;
    }

    @Override // com.thor.basemodule.dialog.BaseDialogFragment
    protected void initListeners() {
        getBinding().positiveBtn.setOnClickListener(new View.OnClickListener() { // from class: com.thor.app.gui.dialog.dialogs.TestersDialogFragment$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                TestersDialogFragment.initListeners$lambda$0(this.f$0, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initListeners$lambda$0(TestersDialogFragment this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        OnConfirmDialogListener mOnConfirmListener = this$0.getMOnConfirmListener();
        if (mOnConfirmListener != null) {
            mOnConfirmListener.onConfirm(this$0.getBinding().dialogEditText.getText().toString());
        }
        this$0.dismiss();
    }
}
