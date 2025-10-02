package com.thor.app.gui.fragments.signup;

import android.os.Bundle;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.core.internal.view.SupportMenu;
import androidx.fragment.app.Fragment;
import com.carsystems.thor.app.R;
import com.google.android.exoplayer2.text.ttml.TtmlNode;
import com.thor.app.gui.widget.ButtonWidget;
import com.thor.app.gui.widget.VehicleListViewDialog;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import timber.log.Timber;

/* compiled from: SetViecleFragment.kt */
@Metadata(d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0011\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u00012\u00020\u0002B\u0005¢\u0006\u0002\u0010\u0003J\u0012\u0010\"\u001a\u00020#2\b\u0010$\u001a\u0004\u0018\u00010%H\u0016J&\u0010&\u001a\u0004\u0018\u00010%2\u0006\u0010'\u001a\u00020(2\b\u0010)\u001a\u0004\u0018\u00010*2\b\u0010+\u001a\u0004\u0018\u00010,H\u0016J\u001a\u0010-\u001a\u00020#2\u0006\u0010.\u001a\u00020%2\b\u0010+\u001a\u0004\u0018\u00010,H\u0016J\u0012\u0010/\u001a\u00020#2\b\u00100\u001a\u0004\u0018\u000101H\u0002R\u001c\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u001a\u0010\n\u001a\u00020\u000bX\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR\u001a\u0010\u0010\u001a\u00020\u0011X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015R\u001a\u0010\u0016\u001a\u00020\u0011X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0013\"\u0004\b\u0018\u0010\u0015R\u001a\u0010\u0019\u001a\u00020\u0011X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010\u0013\"\u0004\b\u001b\u0010\u0015R\u001a\u0010\u001c\u001a\u00020\u0011X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u001d\u0010\u0013\"\u0004\b\u001e\u0010\u0015R\u001a\u0010\u001f\u001a\u00020\u0011X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b \u0010\u0013\"\u0004\b!\u0010\u0015¨\u00062"}, d2 = {"Lcom/thor/app/gui/fragments/signup/SetViecleFragment;", "Landroidx/fragment/app/Fragment;", "Landroid/view/View$OnClickListener;", "()V", "mBottomDialog", "Lcom/thor/app/gui/widget/VehicleListViewDialog;", "getMBottomDialog$thor_1_8_7_release", "()Lcom/thor/app/gui/widget/VehicleListViewDialog;", "setMBottomDialog$thor_1_8_7_release", "(Lcom/thor/app/gui/widget/VehicleListViewDialog;)V", "mCustomButtonWidget", "Lcom/thor/app/gui/widget/ButtonWidget;", "getMCustomButtonWidget", "()Lcom/thor/app/gui/widget/ButtonWidget;", "setMCustomButtonWidget", "(Lcom/thor/app/gui/widget/ButtonWidget;)V", "mTextViewDescription", "Landroid/widget/TextView;", "getMTextViewDescription", "()Landroid/widget/TextView;", "setMTextViewDescription", "(Landroid/widget/TextView;)V", "mTextViewGeneration", "getMTextViewGeneration", "setMTextViewGeneration", "mTextViewMake", "getMTextViewMake", "setMTextViewMake", "mTextViewModel", "getMTextViewModel", "setMTextViewModel", "mTextViewSeries", "getMTextViewSeries", "setMTextViewSeries", "onClick", "", "v", "Landroid/view/View;", "onCreateView", "inflater", "Landroid/view/LayoutInflater;", TtmlNode.RUBY_CONTAINER, "Landroid/view/ViewGroup;", "savedInstanceState", "Landroid/os/Bundle;", "onViewCreated", "view", "setDescription", "text", "", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class SetViecleFragment extends Fragment implements View.OnClickListener {
    private VehicleListViewDialog mBottomDialog;
    public ButtonWidget mCustomButtonWidget;
    public TextView mTextViewDescription;
    public TextView mTextViewGeneration;
    public TextView mTextViewMake;
    public TextView mTextViewModel;
    public TextView mTextViewSeries;

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreateView$lambda$0(View view) {
    }

    public final ButtonWidget getMCustomButtonWidget() {
        ButtonWidget buttonWidget = this.mCustomButtonWidget;
        if (buttonWidget != null) {
            return buttonWidget;
        }
        Intrinsics.throwUninitializedPropertyAccessException("mCustomButtonWidget");
        return null;
    }

    public final void setMCustomButtonWidget(ButtonWidget buttonWidget) {
        Intrinsics.checkNotNullParameter(buttonWidget, "<set-?>");
        this.mCustomButtonWidget = buttonWidget;
    }

    public final TextView getMTextViewDescription() {
        TextView textView = this.mTextViewDescription;
        if (textView != null) {
            return textView;
        }
        Intrinsics.throwUninitializedPropertyAccessException("mTextViewDescription");
        return null;
    }

    public final void setMTextViewDescription(TextView textView) {
        Intrinsics.checkNotNullParameter(textView, "<set-?>");
        this.mTextViewDescription = textView;
    }

    public final TextView getMTextViewMake() {
        TextView textView = this.mTextViewMake;
        if (textView != null) {
            return textView;
        }
        Intrinsics.throwUninitializedPropertyAccessException("mTextViewMake");
        return null;
    }

    public final void setMTextViewMake(TextView textView) {
        Intrinsics.checkNotNullParameter(textView, "<set-?>");
        this.mTextViewMake = textView;
    }

    public final TextView getMTextViewModel() {
        TextView textView = this.mTextViewModel;
        if (textView != null) {
            return textView;
        }
        Intrinsics.throwUninitializedPropertyAccessException("mTextViewModel");
        return null;
    }

    public final void setMTextViewModel(TextView textView) {
        Intrinsics.checkNotNullParameter(textView, "<set-?>");
        this.mTextViewModel = textView;
    }

    public final TextView getMTextViewGeneration() {
        TextView textView = this.mTextViewGeneration;
        if (textView != null) {
            return textView;
        }
        Intrinsics.throwUninitializedPropertyAccessException("mTextViewGeneration");
        return null;
    }

    public final void setMTextViewGeneration(TextView textView) {
        Intrinsics.checkNotNullParameter(textView, "<set-?>");
        this.mTextViewGeneration = textView;
    }

    public final TextView getMTextViewSeries() {
        TextView textView = this.mTextViewSeries;
        if (textView != null) {
            return textView;
        }
        Intrinsics.throwUninitializedPropertyAccessException("mTextViewSeries");
        return null;
    }

    public final void setMTextViewSeries(TextView textView) {
        Intrinsics.checkNotNullParameter(textView, "<set-?>");
        this.mTextViewSeries = textView;
    }

    /* renamed from: getMBottomDialog$thor_1_8_7_release, reason: from getter */
    public final VehicleListViewDialog getMBottomDialog() {
        return this.mBottomDialog;
    }

    public final void setMBottomDialog$thor_1_8_7_release(VehicleListViewDialog vehicleListViewDialog) {
        this.mBottomDialog = vehicleListViewDialog;
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Intrinsics.checkNotNullParameter(inflater, "inflater");
        View viewInflate = inflater.inflate(R.layout.fragment_set_viecle, container, false);
        View viewFindViewById = viewInflate.findViewById(R.id.btnContinue);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "view.findViewById(R.id.btnContinue)");
        setMCustomButtonWidget((ButtonWidget) viewFindViewById);
        getMCustomButtonWidget().setOnClickListener(new View.OnClickListener() { // from class: com.thor.app.gui.fragments.signup.SetViecleFragment$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SetViecleFragment.onCreateView$lambda$0(view);
            }
        });
        View viewFindViewById2 = viewInflate.findViewById(R.id.tvDescription);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "view.findViewById(R.id.tvDescription)");
        setMTextViewDescription((TextView) viewFindViewById2);
        View viewFindViewById3 = viewInflate.findViewById(R.id.tvMake);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById3, "view.findViewById(R.id.tvMake)");
        setMTextViewMake((TextView) viewFindViewById3);
        SetViecleFragment setViecleFragment = this;
        getMTextViewMake().setOnClickListener(setViecleFragment);
        View viewFindViewById4 = viewInflate.findViewById(R.id.tvModel);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById4, "view.findViewById(R.id.tvModel)");
        setMTextViewModel((TextView) viewFindViewById4);
        getMTextViewModel().setOnClickListener(setViecleFragment);
        View viewFindViewById5 = viewInflate.findViewById(R.id.tvGeneration);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById5, "view.findViewById(R.id.tvGeneration)");
        setMTextViewGeneration((TextView) viewFindViewById5);
        getMTextViewGeneration().setOnClickListener(setViecleFragment);
        View viewFindViewById6 = viewInflate.findViewById(R.id.tvSeries);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById6, "view.findViewById(R.id.tvSeries)");
        setMTextViewSeries((TextView) viewFindViewById6);
        getMTextViewSeries().setOnClickListener(setViecleFragment);
        return viewInflate;
    }

    @Override // androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Intrinsics.checkNotNullParameter(view, "view");
        super.onViewCreated(view, savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            setDescription(arguments.getString("deviceName"));
        }
    }

    private final void setDescription(String text) {
        Timber.INSTANCE.i("text = %s", text);
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        String string = getString(R.string.text_setup_fragment_description_prefix);
        Intrinsics.checkNotNullExpressionValue(string, "getString(R.string.text_…gment_description_prefix)");
        spannableStringBuilder.append((CharSequence) string);
        SpannableString spannableString = new SpannableString(text);
        if (text != null) {
            spannableString.setSpan(new ForegroundColorSpan(SupportMenu.CATEGORY_MASK), 0, text.length(), 0);
        }
        spannableStringBuilder.append((CharSequence) spannableString);
        String string2 = getString(R.string.text_setup_fragment_description);
        Intrinsics.checkNotNullExpressionValue(string2, "getString(R.string.text_…tup_fragment_description)");
        spannableStringBuilder.append((CharSequence) string2);
        getMTextViewDescription().setText(spannableStringBuilder);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v) {
        Integer numValueOf = v != null ? Integer.valueOf(v.getId()) : null;
        if (numValueOf != null && numValueOf.intValue() == R.id.tvMake) {
            return;
        }
        if (numValueOf != null && numValueOf.intValue() == R.id.tvModel) {
            return;
        }
        if ((numValueOf != null && numValueOf.intValue() == R.id.tvGeneration) || numValueOf == null) {
            return;
        }
        numValueOf.intValue();
    }
}
