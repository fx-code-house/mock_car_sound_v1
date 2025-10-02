package com.thor.app.databinding.adapters;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.util.Pair;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.BindingAdapter;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.SkuDetails;
import com.carsystems.thor.app.R;
import com.thor.app.bus.events.PurchaseBillingSuccessEvent;
import com.thor.app.gui.activities.shop.main.SubscribtionPromoActivity;
import com.thor.app.gui.dialog.DialogManager;
import com.thor.app.gui.dialog.DownloadSoundPackageDialogFragment;
import com.thor.app.managers.BleManager;
import com.thor.app.settings.Settings;
import com.thor.app.utils.extensions.ViewKt;
import com.thor.basemodule.extensions.ContextKt;
import com.thor.businessmodule.billing.BillingManager;
import com.thor.businessmodule.settings.Variables;
import com.thor.networkmodule.model.shop.ShopSoundPackage;
import io.reactivex.Single;
import io.reactivex.functions.BiConsumer;
import java.util.Arrays;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import org.greenrobot.eventbus.EventBus;
import timber.log.Timber;

/* compiled from: ShopSoundPackageDataBindingAdapter.kt */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001a\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u0004H\u0002J$\u0010\u0014\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u00042\b\u0010\u0015\u001a\u0004\u0018\u00010\u0016H\u0002J\u001a\u0010\u0017\u001a\u00020\u00102\u0006\u0010\u0018\u001a\u00020\u00192\b\u0010\u0013\u001a\u0004\u0018\u00010\u0004H\u0007J$\u0010\u001a\u001a\u00020\u00102\u0006\u0010\u0018\u001a\u00020\u001b2\b\u0010\u0013\u001a\u0004\u0018\u00010\u00042\b\u0010\u0015\u001a\u0004\u0018\u00010\u0016H\u0007R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000e¨\u0006\u001c"}, d2 = {"Lcom/thor/app/databinding/adapters/ShopSoundPackageDataBindingAdapter;", "", "()V", "clickedItem", "Lcom/thor/networkmodule/model/shop/ShopSoundPackage;", "getClickedItem", "()Lcom/thor/networkmodule/model/shop/ShopSoundPackage;", "setClickedItem", "(Lcom/thor/networkmodule/model/shop/ShopSoundPackage;)V", "purchaseBillingListener", "Lcom/thor/businessmodule/billing/BillingManager$PurchaseBillingListener;", "getPurchaseBillingListener", "()Lcom/thor/businessmodule/billing/BillingManager$PurchaseBillingListener;", "setPurchaseBillingListener", "(Lcom/thor/businessmodule/billing/BillingManager$PurchaseBillingListener;)V", "openShopSoundPackageScreen", "", "context", "Landroid/content/Context;", "soundPackage", "purchaseSoundPackage", "skuDetails", "Lcom/android/billingclient/api/SkuDetails;", "shopSoundPackageShowButtonPrice", "view", "Landroid/widget/Button;", "shopSoundPackageShowPurchased", "Landroid/widget/TextView;", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes2.dex */
public final class ShopSoundPackageDataBindingAdapter {
    private static ShopSoundPackage clickedItem;
    public static final ShopSoundPackageDataBindingAdapter INSTANCE = new ShopSoundPackageDataBindingAdapter();
    private static BillingManager.PurchaseBillingListener purchaseBillingListener = new BillingManager.PurchaseBillingListener() { // from class: com.thor.app.databinding.adapters.ShopSoundPackageDataBindingAdapter$purchaseBillingListener$1
        @Override // com.thor.businessmodule.billing.BillingManager.PurchaseBillingListener
        public void handleError(Integer errorCode) {
            EventBus.getDefault().post(new PurchaseBillingSuccessEvent(true, errorCode));
        }

        @Override // com.thor.businessmodule.billing.BillingManager.PurchaseBillingListener
        public void handlePurchase(Purchase purchase) {
            Intrinsics.checkNotNullParameter(purchase, "purchase");
            EventBus.getDefault().post(new PurchaseBillingSuccessEvent(true, null));
        }

        @Override // com.thor.businessmodule.billing.BillingManager.PurchaseBillingListener
        public void handleCancelled() {
            EventBus.getDefault().post(new PurchaseBillingSuccessEvent(false, null));
        }
    };

    private ShopSoundPackageDataBindingAdapter() {
    }

    public final ShopSoundPackage getClickedItem() {
        return clickedItem;
    }

    public final void setClickedItem(ShopSoundPackage shopSoundPackage) {
        clickedItem = shopSoundPackage;
    }

    public final BillingManager.PurchaseBillingListener getPurchaseBillingListener() {
        return purchaseBillingListener;
    }

    public final void setPurchaseBillingListener(BillingManager.PurchaseBillingListener purchaseBillingListener2) {
        Intrinsics.checkNotNullParameter(purchaseBillingListener2, "<set-?>");
        purchaseBillingListener = purchaseBillingListener2;
    }

    @BindingAdapter({"shopSoundPackageShowPurchased", "setSkuDetails"})
    @JvmStatic
    public static final void shopSoundPackageShowPurchased(final TextView view, final ShopSoundPackage soundPackage, final SkuDetails skuDetails) {
        Intrinsics.checkNotNullParameter(view, "view");
        view.setOnClickListener(null);
        view.setText("");
        view.setBackground(null);
        if (soundPackage == null) {
            Timber.INSTANCE.e("ShopSoundPackage cannot be null", new Object[0]);
            return;
        }
        if (soundPackage.isPurchased() || Settings.INSTANCE.isSubscriptionActive()) {
            view.setCompoundDrawables(null, null, null, null);
            if (soundPackage.isLoadedOnBoard() && !soundPackage.isNeedUpdate()) {
                view.setBackgroundResource(R.drawable.ic_present);
                return;
            }
            if (soundPackage.isNeedUpdate()) {
                view.setText(R.string.text_update);
                view.setTextColor(ContextCompat.getColor(view.getContext(), R.color.colorWhite));
                view.setBackgroundResource(R.drawable.bg_shape_shop_sound_package_update);
            } else {
                view.setBackgroundResource(R.drawable.ic_shop_package_purchased);
            }
            ViewKt.setOnClickListenerWithDebounce$default(view, 0L, new Function0<Unit>() { // from class: com.thor.app.databinding.adapters.ShopSoundPackageDataBindingAdapter.shopSoundPackageShowPurchased.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public /* bridge */ /* synthetic */ Unit invoke() {
                    invoke2();
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2() {
                    if (BleManager.INSTANCE.from(view.getContext()).getMStateConnected()) {
                        DownloadSoundPackageDialogFragment downloadSoundPackageDialogFragmentNewInstance = DownloadSoundPackageDialogFragment.INSTANCE.newInstance(soundPackage);
                        if (downloadSoundPackageDialogFragmentNewInstance.isAdded()) {
                            return;
                        }
                        Activity parentActivity = ContextKt.getParentActivity(view.getContext());
                        Intrinsics.checkNotNull(parentActivity, "null cannot be cast to non-null type androidx.appcompat.app.AppCompatActivity");
                        downloadSoundPackageDialogFragmentNewInstance.show(((AppCompatActivity) parentActivity).getSupportFragmentManager(), "DownloadSoundPackageDialogFragment");
                        return;
                    }
                    DialogManager dialogManager = DialogManager.INSTANCE;
                    Context context = view.getContext();
                    Intrinsics.checkNotNullExpressionValue(context, "view.context");
                    dialogManager.createNoConnectionToBoardAlertDialog(context).show();
                }
            }, 1, null);
            return;
        }
        StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
        Context context = view.getContext();
        String string = context != null ? context.getString(R.string.text_sound_package_price) : null;
        Intrinsics.checkNotNull(string);
        String str = String.format(string, Arrays.copyOf(new Object[]{Float.valueOf(soundPackage.getPrice()), soundPackage.getCurrency()}, 2));
        Intrinsics.checkNotNullExpressionValue(str, "format(...)");
        view.setText(str);
        view.setBackgroundResource(R.drawable.bg_shape_shop_sound_package_price);
        ViewKt.setOnClickListenerWithDebounce$default(view, 0L, new Function0<Unit>() { // from class: com.thor.app.databinding.adapters.ShopSoundPackageDataBindingAdapter.shopSoundPackageShowPurchased.2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public /* bridge */ /* synthetic */ Unit invoke() {
                invoke2();
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2() {
                if (Variables.INSTANCE.getSUBSCRIPTION_FEATURE_REQUIREMENTS_SATISFIED()) {
                    ShopSoundPackageDataBindingAdapter shopSoundPackageDataBindingAdapter = ShopSoundPackageDataBindingAdapter.INSTANCE;
                    Context context2 = view.getContext();
                    Intrinsics.checkNotNullExpressionValue(context2, "view.context");
                    shopSoundPackageDataBindingAdapter.openShopSoundPackageScreen(context2, soundPackage);
                    return;
                }
                ShopSoundPackageDataBindingAdapter shopSoundPackageDataBindingAdapter2 = ShopSoundPackageDataBindingAdapter.INSTANCE;
                Context context3 = view.getContext();
                Intrinsics.checkNotNullExpressionValue(context3, "view.context");
                shopSoundPackageDataBindingAdapter2.purchaseSoundPackage(context3, soundPackage, skuDetails);
            }
        }, 1, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void openShopSoundPackageScreen(Context context, ShopSoundPackage soundPackage) {
        Intent intent = new Intent(context, (Class<?>) SubscribtionPromoActivity.class);
        intent.putExtra(ShopSoundPackage.INSTANCE.getBUNDLE_NAME(), soundPackage);
        context.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(ContextKt.getParentActivity(context), new Pair[0]).toBundle());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void purchaseSoundPackage(Context context, ShopSoundPackage soundPackage, SkuDetails skuDetails) {
        Activity parentActivity;
        clickedItem = soundPackage;
        if (skuDetails == null || (parentActivity = ContextKt.getParentActivity(context)) == null) {
            return;
        }
        Single<List<Purchase>> singlePurchase = new BillingManager(parentActivity).purchase(skuDetails, purchaseBillingListener);
        final ShopSoundPackageDataBindingAdapter$purchaseSoundPackage$1$1$1 shopSoundPackageDataBindingAdapter$purchaseSoundPackage$1$1$1 = new Function2<List<? extends Purchase>, Throwable, Unit>() { // from class: com.thor.app.databinding.adapters.ShopSoundPackageDataBindingAdapter$purchaseSoundPackage$1$1$1
            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(List<? extends Purchase> list, Throwable th) {
            }

            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ Unit invoke(List<? extends Purchase> list, Throwable th) {
                invoke2(list, th);
                return Unit.INSTANCE;
            }
        };
        singlePurchase.subscribe(new BiConsumer() { // from class: com.thor.app.databinding.adapters.ShopSoundPackageDataBindingAdapter$$ExternalSyntheticLambda0
            @Override // io.reactivex.functions.BiConsumer
            public final void accept(Object obj, Object obj2) {
                ShopSoundPackageDataBindingAdapter.purchaseSoundPackage$lambda$2$lambda$1$lambda$0(shopSoundPackageDataBindingAdapter$purchaseSoundPackage$1$1$1, obj, obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void purchaseSoundPackage$lambda$2$lambda$1$lambda$0(Function2 tmp0, Object obj, Object obj2) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj, obj2);
    }

    @BindingAdapter({"shopSoundPackageShowButtonPrice"})
    @JvmStatic
    public static final void shopSoundPackageShowButtonPrice(Button view, ShopSoundPackage soundPackage) {
        Intrinsics.checkNotNullParameter(view, "view");
        if (soundPackage == null) {
            Timber.INSTANCE.e("ShopSoundPackage cannot be null", new Object[0]);
            return;
        }
        Resources resources = view.getResources();
        Object[] objArr = new Object[1];
        StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
        Context context = view.getContext();
        String string = context != null ? context.getString(R.string.text_sound_package_price) : null;
        Intrinsics.checkNotNull(string);
        String str = String.format(string, Arrays.copyOf(new Object[]{Float.valueOf(soundPackage.getPrice()), soundPackage.getCurrency()}, 2));
        Intrinsics.checkNotNullExpressionValue(str, "format(...)");
        objArr[0] = str;
        view.setText(resources.getString(R.string.text_sound_package_buy, objArr));
    }
}
