package com.thor.app.gui.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.FragmentKt;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import com.carsystems.thor.app.R;
import com.carsystems.thor.app.databinding.FragmentDialogFormatProgressBinding;
import com.google.android.exoplayer2.text.ttml.TtmlNode;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.thor.app.gui.dialog.dialogs.FullScreenDialogFragment;
import com.thor.basemodule.gui.view.CircleProgressView;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: FormatProgressDialogFragment.kt */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u0000 \u00172\u00020\u0001:\u0001\u0017B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0012\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u0016J\u0012\u0010\r\u001a\u00020\u000e2\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u0016J&\u0010\u000f\u001a\u0004\u0018\u00010\u00102\u0006\u0010\u0011\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u00142\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u0016J\u001a\u0010\u0015\u001a\u00020\n2\u0006\u0010\u0016\u001a\u00020\u00102\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u0016R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.¢\u0006\u0002\n\u0000R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\u0018"}, d2 = {"Lcom/thor/app/gui/dialog/FormatProgressDialogFragment;", "Lcom/thor/app/gui/dialog/dialogs/FullScreenDialogFragment;", "viewModel", "Lcom/thor/app/gui/dialog/FormatProgressViewModel;", "(Lcom/thor/app/gui/dialog/FormatProgressViewModel;)V", "binding", "Lcom/carsystems/thor/app/databinding/FragmentDialogFormatProgressBinding;", "getViewModel", "()Lcom/thor/app/gui/dialog/FormatProgressViewModel;", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "onCreateDialog", "Landroid/app/Dialog;", "onCreateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", TtmlNode.RUBY_CONTAINER, "Landroid/view/ViewGroup;", "onViewCreated", "view", "Companion", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class FormatProgressDialogFragment extends FullScreenDialogFragment {
    private static final String BUNDLE_NAME;

    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private FragmentDialogFormatProgressBinding binding;
    private final FormatProgressViewModel viewModel;

    public FormatProgressDialogFragment(FormatProgressViewModel viewModel) {
        Intrinsics.checkNotNullParameter(viewModel, "viewModel");
        this.viewModel = viewModel;
    }

    public final FormatProgressViewModel getViewModel() {
        return this.viewModel;
    }

    @Override // com.thor.app.gui.dialog.dialogs.FullScreenDialogFragment, androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Intrinsics.checkNotNullParameter(inflater, "inflater");
        ViewDataBinding viewDataBindingInflate = DataBindingUtil.inflate(inflater, R.layout.fragment_dialog_format_progress, container, false);
        Intrinsics.checkNotNullExpressionValue(viewDataBindingInflate, "inflate(\n            inf…          false\n        )");
        FragmentDialogFormatProgressBinding fragmentDialogFormatProgressBinding = (FragmentDialogFormatProgressBinding) viewDataBindingInflate;
        this.binding = fragmentDialogFormatProgressBinding;
        if (fragmentDialogFormatProgressBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            fragmentDialogFormatProgressBinding = null;
        }
        return fragmentDialogFormatProgressBinding.getRoot();
    }

    @Override // androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Intrinsics.checkNotNullParameter(view, "view");
        super.onViewCreated(view, savedInstanceState);
        LiveData<Integer> progress = this.viewModel.getProgress();
        LifecycleOwner viewLifecycleOwner = getViewLifecycleOwner();
        final Function1<Integer, Unit> function1 = new Function1<Integer, Unit>() { // from class: com.thor.app.gui.dialog.FormatProgressDialogFragment.onViewCreated.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Integer num) {
                invoke2(num);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(Integer it) {
                FragmentDialogFormatProgressBinding fragmentDialogFormatProgressBinding = FormatProgressDialogFragment.this.binding;
                if (fragmentDialogFormatProgressBinding == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("binding");
                    fragmentDialogFormatProgressBinding = null;
                }
                CircleProgressView circleProgressView = fragmentDialogFormatProgressBinding.updateProgress;
                Intrinsics.checkNotNullExpressionValue(it, "it");
                circleProgressView.setProgress(it.intValue());
                if (it.intValue() == 100) {
                    FormatProgressDialogFragment formatProgressDialogFragment = FormatProgressDialogFragment.this;
                    String bundle_name = FormatProgressDialogFragment.INSTANCE.getBUNDLE_NAME();
                    Bundle bundle = new Bundle();
                    bundle.putString("result", FirebaseAnalytics.Param.SUCCESS);
                    Unit unit = Unit.INSTANCE;
                    FragmentKt.setFragmentResult(formatProgressDialogFragment, bundle_name, bundle);
                    FormatProgressDialogFragment.this.dismiss();
                }
            }
        };
        progress.observe(viewLifecycleOwner, new Observer() { // from class: com.thor.app.gui.dialog.FormatProgressDialogFragment$$ExternalSyntheticLambda0
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                FormatProgressDialogFragment.onViewCreated$lambda$0(function1, obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onViewCreated$lambda$0(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    @Override // com.thor.app.gui.dialog.dialogs.FullScreenDialogFragment, androidx.fragment.app.DialogFragment
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new Dialog(requireContext(), getTheme()) { // from class: com.thor.app.gui.dialog.FormatProgressDialogFragment.onCreateDialog.1
            @Override // android.app.Dialog
            public void onBackPressed() {
            }
        };
    }

    /* compiled from: FormatProgressDialogFragment.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0006\u0010\u0007\u001a\u00020\bR\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\t"}, d2 = {"Lcom/thor/app/gui/dialog/FormatProgressDialogFragment$Companion;", "", "()V", "BUNDLE_NAME", "", "getBUNDLE_NAME", "()Ljava/lang/String;", "newInstance", "Lcom/thor/app/gui/dialog/FormatProgressDialogFragment;", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final String getBUNDLE_NAME() {
            return FormatProgressDialogFragment.BUNDLE_NAME;
        }

        public final FormatProgressDialogFragment newInstance() {
            return new FormatProgressDialogFragment(new FormatProgressViewModel());
        }
    }

    static {
        Intrinsics.checkNotNullExpressionValue("FormatProgressDialogFragment", "FormatProgressDialogFrag…nt::class.java.simpleName");
        BUNDLE_NAME = "FormatProgressDialogFragment";
    }
}
