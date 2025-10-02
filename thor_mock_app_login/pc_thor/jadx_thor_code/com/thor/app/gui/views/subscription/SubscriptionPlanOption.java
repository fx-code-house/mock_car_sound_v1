package com.thor.app.gui.views.subscription;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import androidx.core.content.ContextCompat;
import com.carsystems.thor.app.R;
import com.carsystems.thor.app.databinding.LayoutSubscriptionPlanOptionBinding;
import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.thor.app.databinding.model.SubscriptionPlanType;
import com.thor.basemodule.extensions.NumberKt;
import java.lang.ref.WeakReference;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.apache.commons.lang3.StringUtils;

/* compiled from: SubscriptionPlanOption.kt */
@Metadata(d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004B\u0019\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\u0002\u0010\u0007B!\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ\u0012\u0010\u0011\u001a\u00020\u00122\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u0002J\u0006\u0010\r\u001a\u00020\u000eJ\u000e\u0010\u0013\u001a\u00020\u00122\u0006\u0010\u0014\u001a\u00020\u000eJ\u001e\u0010\u0015\u001a\u00020\u00122\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u0019J\u0012\u0010\u001b\u001a\u00020\u00122\b\u0010\u001c\u001a\u0004\u0018\u00010\u001dH\u0016J\u0016\u0010\u001e\u001a\u00020\u00122\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u001f\u001a\u00020\u0019J\u000e\u0010 \u001a\u00020\u00122\u0006\u0010\u000f\u001a\u00020\u0010J\b\u0010!\u001a\u00020\u0012H\u0002R\u000e\u0010\u000b\u001a\u00020\fX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\""}, d2 = {"Lcom/thor/app/gui/views/subscription/SubscriptionPlanOption;", "Landroid/widget/FrameLayout;", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "attrs", "Landroid/util/AttributeSet;", "(Landroid/content/Context;Landroid/util/AttributeSet;)V", "defStyleAttr", "", "(Landroid/content/Context;Landroid/util/AttributeSet;I)V", "binding", "Lcom/carsystems/thor/app/databinding/LayoutSubscriptionPlanOptionBinding;", "isChecked", "", "subscriptionPlanType", "Lcom/thor/app/databinding/model/SubscriptionPlanType;", "init", "", "setChecked", "checked", "setDiscountAmount", "currencyPrefix", "", "monthlyPrice", "", "annualPrice", "setOnClickListener", ServiceSpecificExtraArgs.CastExtraArgs.LISTENER, "Landroid/view/View$OnClickListener;", "setPriceString", FirebaseAnalytics.Param.PRICE, "setSubscriptionType", "updateState", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class SubscriptionPlanOption extends FrameLayout {
    private LayoutSubscriptionPlanOptionBinding binding;
    private boolean isChecked;
    private SubscriptionPlanType subscriptionPlanType;

    /* compiled from: SubscriptionPlanOption.kt */
    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[SubscriptionPlanType.values().length];
            try {
                iArr[SubscriptionPlanType.MONTHLY.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[SubscriptionPlanType.ANNUALLY.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public SubscriptionPlanOption(Context context) {
        this(context, null);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public SubscriptionPlanOption(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SubscriptionPlanOption(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        Intrinsics.checkNotNullParameter(context, "context");
        init(attributeSet);
    }

    private final void init(AttributeSet attrs) {
        LayoutInflater layoutInflater = (LayoutInflater) new WeakReference(LayoutInflater.from(getContext())).get();
        if (layoutInflater != null) {
            LayoutSubscriptionPlanOptionBinding layoutSubscriptionPlanOptionBindingInflate = LayoutSubscriptionPlanOptionBinding.inflate(layoutInflater, this, true);
            Intrinsics.checkNotNullExpressionValue(layoutSubscriptionPlanOptionBindingInflate, "inflate(it, this, true)");
            this.binding = layoutSubscriptionPlanOptionBindingInflate;
        }
    }

    @Override // android.view.View
    public void setOnClickListener(View.OnClickListener listener) {
        LayoutSubscriptionPlanOptionBinding layoutSubscriptionPlanOptionBinding = this.binding;
        if (layoutSubscriptionPlanOptionBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            layoutSubscriptionPlanOptionBinding = null;
        }
        layoutSubscriptionPlanOptionBinding.content.setOnClickListener(listener);
    }

    public final void setSubscriptionType(SubscriptionPlanType subscriptionPlanType) {
        Intrinsics.checkNotNullParameter(subscriptionPlanType, "subscriptionPlanType");
        this.subscriptionPlanType = subscriptionPlanType;
        int i = WhenMappings.$EnumSwitchMapping$0[subscriptionPlanType.ordinal()];
        LayoutSubscriptionPlanOptionBinding layoutSubscriptionPlanOptionBinding = null;
        if (i == 1) {
            LayoutSubscriptionPlanOptionBinding layoutSubscriptionPlanOptionBinding2 = this.binding;
            if (layoutSubscriptionPlanOptionBinding2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                layoutSubscriptionPlanOptionBinding2 = null;
            }
            layoutSubscriptionPlanOptionBinding2.prefix.setVisibility(8);
            LayoutSubscriptionPlanOptionBinding layoutSubscriptionPlanOptionBinding3 = this.binding;
            if (layoutSubscriptionPlanOptionBinding3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                layoutSubscriptionPlanOptionBinding3 = null;
            }
            layoutSubscriptionPlanOptionBinding3.discountBadge.setVisibility(8);
            LayoutSubscriptionPlanOptionBinding layoutSubscriptionPlanOptionBinding4 = this.binding;
            if (layoutSubscriptionPlanOptionBinding4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
            } else {
                layoutSubscriptionPlanOptionBinding = layoutSubscriptionPlanOptionBinding4;
            }
            layoutSubscriptionPlanOptionBinding.textTrialInfo.setVisibility(8);
            return;
        }
        if (i != 2) {
            return;
        }
        LayoutSubscriptionPlanOptionBinding layoutSubscriptionPlanOptionBinding5 = this.binding;
        if (layoutSubscriptionPlanOptionBinding5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            layoutSubscriptionPlanOptionBinding5 = null;
        }
        layoutSubscriptionPlanOptionBinding5.prefix.setVisibility(0);
        LayoutSubscriptionPlanOptionBinding layoutSubscriptionPlanOptionBinding6 = this.binding;
        if (layoutSubscriptionPlanOptionBinding6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            layoutSubscriptionPlanOptionBinding6 = null;
        }
        layoutSubscriptionPlanOptionBinding6.discountBadge.setVisibility(0);
        LayoutSubscriptionPlanOptionBinding layoutSubscriptionPlanOptionBinding7 = this.binding;
        if (layoutSubscriptionPlanOptionBinding7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            layoutSubscriptionPlanOptionBinding = layoutSubscriptionPlanOptionBinding7;
        }
        layoutSubscriptionPlanOptionBinding.textTrialInfo.setVisibility(0);
    }

    /* renamed from: isChecked, reason: from getter */
    public final boolean getIsChecked() {
        return this.isChecked;
    }

    public final void setChecked(boolean checked) {
        this.isChecked = checked;
        updateState();
    }

    public final void setPriceString(String currencyPrefix, double price) {
        Intrinsics.checkNotNullParameter(currencyPrefix, "currencyPrefix");
        SubscriptionPlanType subscriptionPlanType = this.subscriptionPlanType;
        int i = subscriptionPlanType == null ? -1 : WhenMappings.$EnumSwitchMapping$0[subscriptionPlanType.ordinal()];
        LayoutSubscriptionPlanOptionBinding layoutSubscriptionPlanOptionBinding = null;
        if (i == 1) {
            LayoutSubscriptionPlanOptionBinding layoutSubscriptionPlanOptionBinding2 = this.binding;
            if (layoutSubscriptionPlanOptionBinding2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                layoutSubscriptionPlanOptionBinding2 = null;
            }
            layoutSubscriptionPlanOptionBinding2.planName.setText(getContext().getString(R.string.text_subscription_monthly));
            LayoutSubscriptionPlanOptionBinding layoutSubscriptionPlanOptionBinding3 = this.binding;
            if (layoutSubscriptionPlanOptionBinding3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
            } else {
                layoutSubscriptionPlanOptionBinding = layoutSubscriptionPlanOptionBinding3;
            }
            layoutSubscriptionPlanOptionBinding.planPrice.setText(getContext().getString(R.string.text_form_subscription_per_month, price + StringUtils.SPACE + currencyPrefix));
            return;
        }
        if (i != 2) {
            return;
        }
        LayoutSubscriptionPlanOptionBinding layoutSubscriptionPlanOptionBinding4 = this.binding;
        if (layoutSubscriptionPlanOptionBinding4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            layoutSubscriptionPlanOptionBinding4 = null;
        }
        layoutSubscriptionPlanOptionBinding4.planName.setText(getContext().getString(R.string.text_form_subscription_annual, price + StringUtils.SPACE + currencyPrefix));
        LayoutSubscriptionPlanOptionBinding layoutSubscriptionPlanOptionBinding5 = this.binding;
        if (layoutSubscriptionPlanOptionBinding5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            layoutSubscriptionPlanOptionBinding = layoutSubscriptionPlanOptionBinding5;
        }
        layoutSubscriptionPlanOptionBinding.planPrice.setText(getContext().getString(R.string.text_form_subscription_per_month, NumberKt.roundTo(price / 12, 2) + StringUtils.SPACE + currencyPrefix));
    }

    public final void setDiscountAmount(String currencyPrefix, double monthlyPrice, double annualPrice) {
        Intrinsics.checkNotNullParameter(currencyPrefix, "currencyPrefix");
        double d = (monthlyPrice * 12) - annualPrice;
        LayoutSubscriptionPlanOptionBinding layoutSubscriptionPlanOptionBinding = this.binding;
        if (layoutSubscriptionPlanOptionBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            layoutSubscriptionPlanOptionBinding = null;
        }
        layoutSubscriptionPlanOptionBinding.discountBadge.setText(getContext().getString(R.string.text_form_save_on_subscription, NumberKt.roundTo(d, 2) + StringUtils.SPACE + currencyPrefix));
    }

    private final void updateState() {
        LayoutSubscriptionPlanOptionBinding layoutSubscriptionPlanOptionBinding = null;
        if (this.isChecked) {
            LayoutSubscriptionPlanOptionBinding layoutSubscriptionPlanOptionBinding2 = this.binding;
            if (layoutSubscriptionPlanOptionBinding2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                layoutSubscriptionPlanOptionBinding2 = null;
            }
            layoutSubscriptionPlanOptionBinding2.content.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.bg_subscription_plan_selected));
            LayoutSubscriptionPlanOptionBinding layoutSubscriptionPlanOptionBinding3 = this.binding;
            if (layoutSubscriptionPlanOptionBinding3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                layoutSubscriptionPlanOptionBinding3 = null;
            }
            layoutSubscriptionPlanOptionBinding3.planName.setTextColor(ContextCompat.getColor(getContext(), R.color.colorBlack));
            LayoutSubscriptionPlanOptionBinding layoutSubscriptionPlanOptionBinding4 = this.binding;
            if (layoutSubscriptionPlanOptionBinding4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                layoutSubscriptionPlanOptionBinding4 = null;
            }
            layoutSubscriptionPlanOptionBinding4.planPrice.setTextColor(ContextCompat.getColor(getContext(), R.color.colorBlack));
            LayoutSubscriptionPlanOptionBinding layoutSubscriptionPlanOptionBinding5 = this.binding;
            if (layoutSubscriptionPlanOptionBinding5 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                layoutSubscriptionPlanOptionBinding5 = null;
            }
            layoutSubscriptionPlanOptionBinding5.prefix.setTextColor(ContextCompat.getColor(getContext(), R.color.colorBlack));
            LayoutSubscriptionPlanOptionBinding layoutSubscriptionPlanOptionBinding6 = this.binding;
            if (layoutSubscriptionPlanOptionBinding6 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
            } else {
                layoutSubscriptionPlanOptionBinding = layoutSubscriptionPlanOptionBinding6;
            }
            layoutSubscriptionPlanOptionBinding.textTrialInfo.setTextColor(ContextCompat.getColor(getContext(), R.color.colorBlack));
            return;
        }
        LayoutSubscriptionPlanOptionBinding layoutSubscriptionPlanOptionBinding7 = this.binding;
        if (layoutSubscriptionPlanOptionBinding7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            layoutSubscriptionPlanOptionBinding7 = null;
        }
        layoutSubscriptionPlanOptionBinding7.content.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.bg_subscription_plan));
        LayoutSubscriptionPlanOptionBinding layoutSubscriptionPlanOptionBinding8 = this.binding;
        if (layoutSubscriptionPlanOptionBinding8 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            layoutSubscriptionPlanOptionBinding8 = null;
        }
        layoutSubscriptionPlanOptionBinding8.planName.setTextColor(ContextCompat.getColor(getContext(), R.color.colorWhite));
        LayoutSubscriptionPlanOptionBinding layoutSubscriptionPlanOptionBinding9 = this.binding;
        if (layoutSubscriptionPlanOptionBinding9 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            layoutSubscriptionPlanOptionBinding9 = null;
        }
        layoutSubscriptionPlanOptionBinding9.planPrice.setTextColor(ContextCompat.getColor(getContext(), R.color.colorWhite));
        LayoutSubscriptionPlanOptionBinding layoutSubscriptionPlanOptionBinding10 = this.binding;
        if (layoutSubscriptionPlanOptionBinding10 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            layoutSubscriptionPlanOptionBinding10 = null;
        }
        layoutSubscriptionPlanOptionBinding10.prefix.setTextColor(ContextCompat.getColor(getContext(), R.color.colorWhite));
        LayoutSubscriptionPlanOptionBinding layoutSubscriptionPlanOptionBinding11 = this.binding;
        if (layoutSubscriptionPlanOptionBinding11 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            layoutSubscriptionPlanOptionBinding = layoutSubscriptionPlanOptionBinding11;
        }
        layoutSubscriptionPlanOptionBinding.textTrialInfo.setTextColor(ContextCompat.getColor(getContext(), R.color.colorWhite));
    }
}
