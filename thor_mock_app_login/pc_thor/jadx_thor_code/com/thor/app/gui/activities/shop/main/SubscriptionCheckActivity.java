package com.thor.app.gui.activities.shop.main;

import android.os.Bundle;
import com.android.billingclient.api.Purchase;
import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.thor.app.billing.BillingManager;
import com.thor.app.settings.Settings;
import com.thor.app.utils.theming.ThemingUtil;
import com.thor.businessmodule.settings.Constants;
import dagger.hilt.android.AndroidEntryPoint;
import java.util.List;
import javax.inject.Inject;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import timber.log.Timber;

/* compiled from: SubscriptionCheckActivity.kt */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u0003\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0017\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\t\u001a\u00020\nH\u0002J\u0010\u0010\u000b\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\rH\u0002J\u0012\u0010\u000e\u001a\u00020\n2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010H\u0014J\b\u0010\u0011\u001a\u00020\nH\u0014R\u001e\u0010\u0003\u001a\u00020\u00048\u0006@\u0006X\u0087.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\b¨\u0006\u0012"}, d2 = {"Lcom/thor/app/gui/activities/shop/main/SubscriptionCheckActivity;", "Lcom/thor/basemodule/gui/activity/BaseActivity;", "()V", "billingManager", "Lcom/thor/app/billing/BillingManager;", "getBillingManager", "()Lcom/thor/app/billing/BillingManager;", "setBillingManager", "(Lcom/thor/app/billing/BillingManager;)V", "checkSubscription", "", "handleError", "throwable", "", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onResume", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@AndroidEntryPoint
/* loaded from: classes3.dex */
public class SubscriptionCheckActivity extends Hilt_SubscriptionCheckActivity {

    @Inject
    public BillingManager billingManager;

    public final BillingManager getBillingManager() {
        BillingManager billingManager = this.billingManager;
        if (billingManager != null) {
            return billingManager;
        }
        Intrinsics.throwUninitializedPropertyAccessException("billingManager");
        return null;
    }

    public final void setBillingManager(BillingManager billingManager) {
        Intrinsics.checkNotNullParameter(billingManager, "<set-?>");
        this.billingManager = billingManager;
    }

    @Override // com.thor.app.gui.activities.shop.main.Hilt_SubscriptionCheckActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ThemingUtil.INSTANCE.onActivityCreateSetTheme(this);
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        checkSubscription();
    }

    private final void checkSubscription() {
        getBillingManager().queryActivePurchases(new BillingManager.OnQueryActivePurchasesListener() { // from class: com.thor.app.gui.activities.shop.main.SubscriptionCheckActivity.checkSubscription.1
            @Override // com.thor.app.billing.BillingManager.OnQueryActivePurchasesListener
            public void onSuccess(List<? extends Purchase> activePurchases) {
                Intrinsics.checkNotNullParameter(activePurchases, "activePurchases");
                if (activePurchases.isEmpty()) {
                    Settings.INSTANCE.saveSubscriptionActive(false);
                }
                for (Purchase purchase : activePurchases) {
                    if (purchase.getSkus().contains(Constants.THOR_PLUS_MONTHLY_SUB_ID) || purchase.getSkus().contains(Constants.THOR_PLUS_ANNUAL_SUB_ID)) {
                        Settings.INSTANCE.saveSubscriptionActive(true);
                    }
                }
            }

            @Override // com.thor.app.billing.BillingManager.OnQueryActivePurchasesListener
            public void onFailure(BillingManager.Error error) {
                Intrinsics.checkNotNullParameter(error, "error");
                SubscriptionCheckActivity.this.handleError(new Exception(error.toString()));
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void handleError(Throwable throwable) {
        Timber.INSTANCE.e(throwable);
        FirebaseCrashlytics.getInstance().recordException(throwable);
    }
}
