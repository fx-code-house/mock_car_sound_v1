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
import com.thor.app.gui.activities.shop.sgu.SguSubscriptionPromoActivity;
import com.thor.app.gui.dialog.DialogManager;
import com.thor.app.gui.dialog.UploadSguSoundSetDialogFragment;
import com.thor.app.managers.BleManager;
import com.thor.app.settings.Settings;
import com.thor.app.utils.extensions.ViewKt;
import com.thor.basemodule.extensions.ContextKt;
import com.thor.basemodule.extensions.NumberKt;
import com.thor.businessmodule.billing.BillingManager;
import com.thor.businessmodule.settings.Variables;
import com.thor.networkmodule.model.responses.sgu.SguSoundSet;
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
import org.apache.commons.lang3.StringUtils;
import timber.log.Timber;

/* compiled from: SguShopDataBindingAdapter.kt */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J$\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\b\u0010\r\u001a\u0004\u0018\u00010\u00042\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fH\u0007J\u001a\u0010\u0010\u001a\u00020\n2\u0006\u0010\u0011\u001a\u00020\u00122\b\u0010\r\u001a\u0004\u0018\u00010\u0004H\u0002J$\u0010\u0013\u001a\u00020\n2\u0006\u0010\u0011\u001a\u00020\u00122\b\u0010\r\u001a\u0004\u0018\u00010\u00042\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fH\u0002J\u001a\u0010\u0014\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u00152\b\u0010\r\u001a\u0004\u0018\u00010\u0004H\u0007R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\b¨\u0006\u0016"}, d2 = {"Lcom/thor/app/databinding/adapters/SguShopDataBindingAdapter;", "", "()V", "clickedItem", "Lcom/thor/networkmodule/model/responses/sgu/SguSoundSet;", "getClickedItem", "()Lcom/thor/networkmodule/model/responses/sgu/SguSoundSet;", "setClickedItem", "(Lcom/thor/networkmodule/model/responses/sgu/SguSoundSet;)V", "bindPackagePurchased", "", "view", "Landroid/widget/TextView;", "soundSet", "skuDetails", "Lcom/android/billingclient/api/SkuDetails;", "openShopSoundPackageScreen", "context", "Landroid/content/Context;", "purchaseSoundPackage", "shopSoundPackageShowButtonPrice", "Landroid/widget/Button;", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes2.dex */
public final class SguShopDataBindingAdapter {
    public static final SguShopDataBindingAdapter INSTANCE = new SguShopDataBindingAdapter();
    private static SguSoundSet clickedItem;

    private SguShopDataBindingAdapter() {
    }

    public final SguSoundSet getClickedItem() {
        return clickedItem;
    }

    public final void setClickedItem(SguSoundSet sguSoundSet) {
        clickedItem = sguSoundSet;
    }

    @BindingAdapter({"packagePurchased", "skuDetails"})
    @JvmStatic
    public static final void bindPackagePurchased(final TextView view, final SguSoundSet soundSet, final SkuDetails skuDetails) {
        Intrinsics.checkNotNullParameter(view, "view");
        view.setOnClickListener(null);
        view.setText("");
        view.setBackground(null);
        if (soundSet == null) {
            Timber.INSTANCE.e("ShopSoundPackage cannot be null", new Object[0]);
            return;
        }
        if (!soundSet.getSetStatus() && !Settings.INSTANCE.isSubscriptionActive()) {
            if (!(soundSet.getPrice() == 0.0f)) {
                if (skuDetails != null) {
                    soundSet.setPrice(skuDetails.getPriceAmountMicros() / 1000000.0f);
                    String priceCurrencyCode = skuDetails.getPriceCurrencyCode();
                    Intrinsics.checkNotNullExpressionValue(priceCurrencyCode, "skuDetails.priceCurrencyCode");
                    soundSet.setCurrency(priceCurrencyCode);
                }
                StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
                String str = String.format(NumberKt.roundTo(soundSet.getPrice(), 2) + StringUtils.SPACE + soundSet.getCurrency(), Arrays.copyOf(new Object[0], 0));
                Intrinsics.checkNotNullExpressionValue(str, "format(...)");
                view.setText(str);
                view.setBackgroundResource(R.drawable.bg_shape_shop_sound_package_price);
                ViewKt.setOnClickListenerWithDebounce$default(view, 0L, new Function0<Unit>() { // from class: com.thor.app.databinding.adapters.SguShopDataBindingAdapter.bindPackagePurchased.2
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
                            SguShopDataBindingAdapter sguShopDataBindingAdapter = SguShopDataBindingAdapter.INSTANCE;
                            Context context = view.getContext();
                            Intrinsics.checkNotNullExpressionValue(context, "view.context");
                            sguShopDataBindingAdapter.openShopSoundPackageScreen(context, soundSet);
                            return;
                        }
                        SguShopDataBindingAdapter sguShopDataBindingAdapter2 = SguShopDataBindingAdapter.INSTANCE;
                        Context context2 = view.getContext();
                        Intrinsics.checkNotNullExpressionValue(context2, "view.context");
                        sguShopDataBindingAdapter2.purchaseSoundPackage(context2, soundSet, skuDetails);
                    }
                }, 1, null);
                return;
            }
        }
        view.setCompoundDrawables(null, null, null, null);
        if (soundSet.getIsLoadedOnBoard() && !soundSet.getIsNeedUpdate()) {
            view.setBackgroundResource(R.drawable.ic_present);
            return;
        }
        if (soundSet.getIsNeedUpdate()) {
            view.setText(R.string.text_update);
            view.setTextColor(ContextCompat.getColor(view.getContext(), R.color.colorWhite));
            view.setBackgroundResource(R.drawable.bg_shape_shop_sound_package_update);
        } else {
            view.setBackgroundResource(R.drawable.ic_shop_package_purchased);
        }
        ViewKt.setOnClickListenerWithDebounce$default(view, 0L, new Function0<Unit>() { // from class: com.thor.app.databinding.adapters.SguShopDataBindingAdapter.bindPackagePurchased.1
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
                    UploadSguSoundSetDialogFragment uploadSguSoundSetDialogFragmentNewInstance = UploadSguSoundSetDialogFragment.INSTANCE.newInstance(soundSet);
                    if (uploadSguSoundSetDialogFragmentNewInstance.isAdded()) {
                        return;
                    }
                    Activity parentActivity = ContextKt.getParentActivity(view.getContext());
                    Intrinsics.checkNotNull(parentActivity, "null cannot be cast to non-null type androidx.appcompat.app.AppCompatActivity");
                    uploadSguSoundSetDialogFragmentNewInstance.show(((AppCompatActivity) parentActivity).getSupportFragmentManager(), "UploadSguSoundSetDialogFragment");
                    return;
                }
                DialogManager dialogManager = DialogManager.INSTANCE;
                Context context = view.getContext();
                Intrinsics.checkNotNullExpressionValue(context, "view.context");
                dialogManager.createNoConnectionToBoardAlertDialog(context).show();
            }
        }, 1, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void openShopSoundPackageScreen(Context context, SguSoundSet soundSet) {
        Intent intent = new Intent(context, (Class<?>) SguSubscriptionPromoActivity.class);
        intent.putExtra(SguSoundSet.BUNDLE_NAME, soundSet);
        context.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(ContextKt.getParentActivity(context), new Pair[0]).toBundle());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void purchaseSoundPackage(Context context, SguSoundSet soundSet, SkuDetails skuDetails) {
        Activity parentActivity;
        clickedItem = soundSet;
        if (skuDetails == null || (parentActivity = ContextKt.getParentActivity(context)) == null) {
            return;
        }
        Single<List<Purchase>> singlePurchase = new BillingManager(parentActivity).purchase(skuDetails, ShopSoundPackageDataBindingAdapter.INSTANCE.getPurchaseBillingListener());
        final SguShopDataBindingAdapter$purchaseSoundPackage$1$1$1 sguShopDataBindingAdapter$purchaseSoundPackage$1$1$1 = new Function2<List<? extends Purchase>, Throwable, Unit>() { // from class: com.thor.app.databinding.adapters.SguShopDataBindingAdapter$purchaseSoundPackage$1$1$1
            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(List<? extends Purchase> list, Throwable th) {
            }

            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ Unit invoke(List<? extends Purchase> list, Throwable th) {
                invoke2(list, th);
                return Unit.INSTANCE;
            }
        };
        singlePurchase.subscribe(new BiConsumer() { // from class: com.thor.app.databinding.adapters.SguShopDataBindingAdapter$$ExternalSyntheticLambda0
            @Override // io.reactivex.functions.BiConsumer
            public final void accept(Object obj, Object obj2) {
                SguShopDataBindingAdapter.purchaseSoundPackage$lambda$2$lambda$1$lambda$0(sguShopDataBindingAdapter$purchaseSoundPackage$1$1$1, obj, obj2);
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
    public static final void shopSoundPackageShowButtonPrice(Button view, SguSoundSet soundSet) {
        Intrinsics.checkNotNullParameter(view, "view");
        if (soundSet == null) {
            Timber.INSTANCE.e("ShopSoundPackage cannot be null", new Object[0]);
            return;
        }
        Resources resources = view.getResources();
        StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
        String str = String.format(NumberKt.roundTo(soundSet.getPrice(), 2) + StringUtils.SPACE + soundSet.getCurrency(), Arrays.copyOf(new Object[0], 0));
        Intrinsics.checkNotNullExpressionValue(str, "format(...)");
        view.setText(resources.getString(R.string.text_sgu_sound_package_buy, str));
    }
}
