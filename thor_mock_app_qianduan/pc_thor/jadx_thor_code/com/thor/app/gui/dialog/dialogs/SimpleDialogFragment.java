package com.thor.app.gui.dialog.dialogs;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.carsystems.thor.app.databinding.DialogFragmentSimpleBinding;
import com.fourksoft.base.ui.dialog.listener.OnConfirmDialogListener;
import com.google.android.exoplayer2.source.rtsp.SessionDescription;
import com.thor.basemodule.dialog.BaseBindingDialogFragment;
import com.thor.businessmodule.model.TypeDialog;
import kotlin.Metadata;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: SimpleDialogFragment.kt */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0003\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\b\u0010\u000f\u001a\u00020\u0010H\u0016J\b\u0010\u0011\u001a\u00020\u0010H\u0014J\b\u0010\u0012\u001a\u00020\u0010H\u0014R.\u0010\u0006\u001a\u001c\u0012\u0004\u0012\u00020\b\u0012\u0006\u0012\u0004\u0018\u00010\t\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u00020\u00078VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u0013"}, d2 = {"Lcom/thor/app/gui/dialog/dialogs/SimpleDialogFragment;", "Lcom/thor/basemodule/dialog/BaseBindingDialogFragment;", "Lcom/carsystems/thor/app/databinding/DialogFragmentSimpleBinding;", SessionDescription.ATTR_TYPE, "Lcom/thor/businessmodule/model/TypeDialog;", "(Lcom/thor/businessmodule/model/TypeDialog;)V", "bindingInflater", "Lkotlin/Function3;", "Landroid/view/LayoutInflater;", "Landroid/view/ViewGroup;", "", "getBindingInflater", "()Lkotlin/jvm/functions/Function3;", "getType", "()Lcom/thor/businessmodule/model/TypeDialog;", "init", "", "initListeners", "initViews", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class SimpleDialogFragment extends BaseBindingDialogFragment<DialogFragmentSimpleBinding> {
    private final TypeDialog type;

    @Override // com.thor.basemodule.dialog.BaseDialogFragment
    public void init() {
    }

    public final TypeDialog getType() {
        return this.type;
    }

    public SimpleDialogFragment(TypeDialog type) {
        Intrinsics.checkNotNullParameter(type, "type");
        this.type = type;
    }

    @Override // com.thor.basemodule.dialog.BaseBindingDialogFragment
    public Function3<LayoutInflater, ViewGroup, Boolean, DialogFragmentSimpleBinding> getBindingInflater() {
        return SimpleDialogFragment$bindingInflater$1.INSTANCE;
    }

    @Override // com.thor.basemodule.dialog.BaseDialogFragment
    protected void initViews() {
        getBinding().setType(this.type);
    }

    @Override // com.thor.basemodule.dialog.BaseDialogFragment
    protected void initListeners() {
        getBinding().negativeBtn.setOnClickListener(new View.OnClickListener() { // from class: com.thor.app.gui.dialog.dialogs.SimpleDialogFragment$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SimpleDialogFragment.initListeners$lambda$0(this.f$0, view);
            }
        });
        getBinding().positiveBtn.setOnClickListener(new View.OnClickListener() { // from class: com.thor.app.gui.dialog.dialogs.SimpleDialogFragment$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SimpleDialogFragment.initListeners$lambda$1(this.f$0, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initListeners$lambda$0(SimpleDialogFragment this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initListeners$lambda$1(SimpleDialogFragment this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        OnConfirmDialogListener mOnConfirmListener = this$0.getMOnConfirmListener();
        if (mOnConfirmListener != null) {
            mOnConfirmListener.onConfirm(null);
        }
        this$0.dismiss();
    }
}
