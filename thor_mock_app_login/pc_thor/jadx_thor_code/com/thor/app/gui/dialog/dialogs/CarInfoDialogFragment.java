package com.thor.app.gui.dialog.dialogs;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.carsystems.thor.app.databinding.DialogFragmentCarInfoBinding;
import com.thor.basemodule.dialog.BaseBindingDialogFragment;
import com.thor.networkmodule.model.CanFile;
import kotlin.Metadata;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: CarInfoDialogFragment.kt */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0003\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\b\u0010\u000f\u001a\u00020\u0010H\u0016J\b\u0010\u0011\u001a\u00020\u0010H\u0014J\b\u0010\u0012\u001a\u00020\u0010H\u0014R.\u0010\u0006\u001a\u001c\u0012\u0004\u0012\u00020\b\u0012\u0006\u0012\u0004\u0018\u00010\t\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u00020\u00078VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u0013"}, d2 = {"Lcom/thor/app/gui/dialog/dialogs/CarInfoDialogFragment;", "Lcom/thor/basemodule/dialog/BaseBindingDialogFragment;", "Lcom/carsystems/thor/app/databinding/DialogFragmentCarInfoBinding;", "model", "Lcom/thor/networkmodule/model/CanFile;", "(Lcom/thor/networkmodule/model/CanFile;)V", "bindingInflater", "Lkotlin/Function3;", "Landroid/view/LayoutInflater;", "Landroid/view/ViewGroup;", "", "getBindingInflater", "()Lkotlin/jvm/functions/Function3;", "getModel", "()Lcom/thor/networkmodule/model/CanFile;", "init", "", "initListeners", "initViews", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class CarInfoDialogFragment extends BaseBindingDialogFragment<DialogFragmentCarInfoBinding> {
    private final CanFile model;

    @Override // com.thor.basemodule.dialog.BaseDialogFragment
    public void init() {
    }

    public final CanFile getModel() {
        return this.model;
    }

    public CarInfoDialogFragment(CanFile model) {
        Intrinsics.checkNotNullParameter(model, "model");
        this.model = model;
    }

    @Override // com.thor.basemodule.dialog.BaseBindingDialogFragment
    public Function3<LayoutInflater, ViewGroup, Boolean, DialogFragmentCarInfoBinding> getBindingInflater() {
        return CarInfoDialogFragment$bindingInflater$1.INSTANCE;
    }

    @Override // com.thor.basemodule.dialog.BaseDialogFragment
    protected void initViews() {
        getBinding().setModel(this.model);
    }

    @Override // com.thor.basemodule.dialog.BaseDialogFragment
    protected void initListeners() {
        getBinding().positiveBtn.setOnClickListener(new View.OnClickListener() { // from class: com.thor.app.gui.dialog.dialogs.CarInfoDialogFragment$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                CarInfoDialogFragment.initListeners$lambda$0(this.f$0, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initListeners$lambda$0(CarInfoDialogFragment this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.dismiss();
    }
}
