package com.thor.app.gui.views.soundpackage;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import com.android.billingclient.api.SkuDetails;
import com.carsystems.thor.app.R;
import com.carsystems.thor.app.databinding.ViewShopSoundPackageBinding;
import com.thor.app.bus.events.shop.main.DeleteSoundPackageEvent;
import com.thor.app.gui.activities.preset.AddPresetActivity;
import com.thor.app.gui.activities.shop.main.SoundPackageDescriptionActivity;
import com.thor.app.gui.activities.shop.main.SubscribtionPromoActivity;
import com.thor.app.gui.dialog.DialogManager;
import com.thor.app.managers.BleManager;
import com.thor.app.settings.Settings;
import com.thor.basemodule.extensions.ContextKt;
import com.thor.basemodule.gui.view.listener.OnSwipeTouchListener;
import com.thor.businessmodule.settings.Variables;
import com.thor.networkmodule.model.shop.ShopSoundPackage;
import java.lang.ref.WeakReference;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.greenrobot.eventbus.EventBus;
import timber.log.Timber;

/* compiled from: ShopSoundPackageView.kt */
@Metadata(d1 = {"\u0000`\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000 /2\u00020\u00012\u00020\u0002:\u0002/0B\u000f\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005B\u0019\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\u0002\u0010\bB!\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\u0006\u0010\t\u001a\u00020\n¢\u0006\u0002\u0010\u000bJ\u0006\u0010\u0013\u001a\u00020\u0014J\n\u0010\u0015\u001a\u0004\u0018\u00010\u0016H\u0002J\b\u0010\u0017\u001a\u00020\u0014H\u0002J\u0012\u0010\u0018\u001a\u00020\u00142\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007H\u0002J\u0012\u0010\u0019\u001a\u00020\u00142\b\u0010\u001a\u001a\u0004\u0018\u00010\u001bH\u0016J\b\u0010\u001c\u001a\u00020\u0014H\u0002J\b\u0010\u001d\u001a\u00020\u0014H\u0002J\u0016\u0010\u001e\u001a\u00020\u00142\u0006\u0010\u001f\u001a\u00020\n2\u0006\u0010 \u001a\u00020\nJ\u001e\u0010\u001e\u001a\u00020\u00142\u0006\u0010\u001f\u001a\u00020\n2\u0006\u0010 \u001a\u00020\n2\u0006\u0010!\u001a\u00020\nJ\u0006\u0010\"\u001a\u00020\u0014J\u0006\u0010#\u001a\u00020\u0014J\b\u0010$\u001a\u00020\u0014H\u0002J\b\u0010%\u001a\u00020\u0014H\u0002J\b\u0010&\u001a\u00020\u0014H\u0002J\u000e\u0010'\u001a\u00020\u00142\u0006\u0010(\u001a\u00020\nJ\u0010\u0010)\u001a\u00020\u00142\b\u0010*\u001a\u0004\u0018\u00010+J\u0010\u0010,\u001a\u00020\u00142\b\u0010-\u001a\u0004\u0018\u00010.R\u000e\u0010\f\u001a\u00020\rX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n\u0000¨\u00061"}, d2 = {"Lcom/thor/app/gui/views/soundpackage/ShopSoundPackageView;", "Landroidx/cardview/widget/CardView;", "Landroid/view/View$OnClickListener;", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "attrs", "Landroid/util/AttributeSet;", "(Landroid/content/Context;Landroid/util/AttributeSet;)V", "defStyleAttr", "", "(Landroid/content/Context;Landroid/util/AttributeSet;I)V", "binding", "Lcom/carsystems/thor/app/databinding/ViewShopSoundPackageBinding;", "mEnableToDelete", "", "mShiftOfPosition", "", "mSwiping", "clean", "", "createDeleteConfirmAlertDialog", "Landroidx/appcompat/app/AlertDialog;", "initSwipe", "initView", "onClick", "v", "Landroid/view/View;", "onClickMainLayout", "onDeleteSoundPackage", "onNestedScrollChanged", "scrollY", "oldScrollY", "height", "onSwipeToLeftAnimation", "onSwipeToRightAnimation", "openAddPresetScreen", "openShopSoundDescriptionScreen", "openShopSoundPackageScreen", "setShiftOfPosition", "fullHeight", "setShopSoundPackage", "soundPackage", "Lcom/thor/networkmodule/model/shop/ShopSoundPackage;", "setSkuDetails", "skuDetails", "Lcom/android/billingclient/api/SkuDetails;", "Companion", "OnSwipeListener", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class ShopSoundPackageView extends CardView implements View.OnClickListener {
    private static final long ANIMATION_DURATION = 240;
    private ViewShopSoundPackageBinding binding;
    private boolean mEnableToDelete;
    private float mShiftOfPosition;
    private boolean mSwiping;

    public final void onNestedScrollChanged(int scrollY, int oldScrollY) {
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public ShopSoundPackageView(Context context) {
        this(context, null);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public ShopSoundPackageView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ShopSoundPackageView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        Intrinsics.checkNotNullParameter(context, "context");
        initView(attributeSet);
    }

    private final void initView(AttributeSet attrs) {
        LayoutInflater layoutInflater = (LayoutInflater) new WeakReference(LayoutInflater.from(getContext())).get();
        Intrinsics.checkNotNull(layoutInflater);
        ViewShopSoundPackageBinding viewShopSoundPackageBindingInflate = ViewShopSoundPackageBinding.inflate(layoutInflater, this, true);
        Intrinsics.checkNotNullExpressionValue(viewShopSoundPackageBindingInflate, "inflate(layoutInflater!!, this, true)");
        this.binding = viewShopSoundPackageBindingInflate;
        isInEditMode();
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v) {
        AlertDialog alertDialogCreateDeleteConfirmAlertDialog;
        Integer numValueOf = v != null ? Integer.valueOf(v.getId()) : null;
        if (numValueOf != null && numValueOf.intValue() == R.id.main_layout) {
            onClickMainLayout();
        } else {
            if (numValueOf == null || numValueOf.intValue() != R.id.layout_delete || (alertDialogCreateDeleteConfirmAlertDialog = createDeleteConfirmAlertDialog()) == null) {
                return;
            }
            alertDialogCreateDeleteConfirmAlertDialog.show();
        }
    }

    public final void setShopSoundPackage(ShopSoundPackage soundPackage) {
        ViewShopSoundPackageBinding viewShopSoundPackageBinding = this.binding;
        if (viewShopSoundPackageBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            viewShopSoundPackageBinding = null;
        }
        viewShopSoundPackageBinding.setSoundPackage(soundPackage);
        initSwipe();
    }

    public final void setSkuDetails(SkuDetails skuDetails) {
        ViewShopSoundPackageBinding viewShopSoundPackageBinding = this.binding;
        if (viewShopSoundPackageBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            viewShopSoundPackageBinding = null;
        }
        viewShopSoundPackageBinding.setSkuDetails(skuDetails);
    }

    public final void onNestedScrollChanged(int scrollY, int oldScrollY, int height) {
        setShiftOfPosition(height);
        onNestedScrollChanged(scrollY, oldScrollY);
    }

    public final void setShiftOfPosition(int fullHeight) {
        ViewShopSoundPackageBinding viewShopSoundPackageBinding = this.binding;
        if (viewShopSoundPackageBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            viewShopSoundPackageBinding = null;
        }
        this.mShiftOfPosition = (viewShopSoundPackageBinding.mainLayout.getHeight() / fullHeight) / 3.0f;
    }

    public final void clean() {
        this.mEnableToDelete = false;
        ViewShopSoundPackageBinding viewShopSoundPackageBinding = this.binding;
        ViewShopSoundPackageBinding viewShopSoundPackageBinding2 = null;
        if (viewShopSoundPackageBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            viewShopSoundPackageBinding = null;
        }
        viewShopSoundPackageBinding.layoutCover.setX(0.0f);
        ViewShopSoundPackageBinding viewShopSoundPackageBinding3 = this.binding;
        if (viewShopSoundPackageBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            viewShopSoundPackageBinding2 = viewShopSoundPackageBinding3;
        }
        viewShopSoundPackageBinding2.layoutDelete.setVisibility(4);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void onClickMainLayout() {
        ViewShopSoundPackageBinding viewShopSoundPackageBinding = this.binding;
        ViewShopSoundPackageBinding viewShopSoundPackageBinding2 = null;
        if (viewShopSoundPackageBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            viewShopSoundPackageBinding = null;
        }
        if (viewShopSoundPackageBinding.getSoundPackage() == null) {
            return;
        }
        ViewShopSoundPackageBinding viewShopSoundPackageBinding3 = this.binding;
        if (viewShopSoundPackageBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            viewShopSoundPackageBinding3 = null;
        }
        ShopSoundPackage soundPackage = viewShopSoundPackageBinding3.getSoundPackage();
        Boolean boolValueOf = soundPackage != null ? Boolean.valueOf(soundPackage.isLoadedOnBoard()) : null;
        Intrinsics.checkNotNull(boolValueOf);
        if (boolValueOf.booleanValue()) {
            openAddPresetScreen();
            return;
        }
        if (Variables.INSTANCE.getSUBSCRIPTION_FEATURE_REQUIREMENTS_SATISFIED()) {
            ViewShopSoundPackageBinding viewShopSoundPackageBinding4 = this.binding;
            if (viewShopSoundPackageBinding4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
            } else {
                viewShopSoundPackageBinding2 = viewShopSoundPackageBinding4;
            }
            ShopSoundPackage soundPackage2 = viewShopSoundPackageBinding2.getSoundPackage();
            boolean z = false;
            if (soundPackage2 != null && !soundPackage2.isPurchased()) {
                z = true;
            }
            if (z && !Settings.INSTANCE.isSubscriptionActive()) {
                openShopSoundPackageScreen();
                return;
            }
        }
        openShopSoundDescriptionScreen();
    }

    private final void openAddPresetScreen() {
        Intent intent = new Intent(getContext(), (Class<?>) AddPresetActivity.class);
        String bundle_name = ShopSoundPackage.INSTANCE.getBUNDLE_NAME();
        ViewShopSoundPackageBinding viewShopSoundPackageBinding = this.binding;
        if (viewShopSoundPackageBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            viewShopSoundPackageBinding = null;
        }
        intent.putExtra(bundle_name, viewShopSoundPackageBinding.getSoundPackage());
        getContext().startActivity(intent);
    }

    private final void openShopSoundPackageScreen() {
        Intent intent = new Intent(getContext(), (Class<?>) SubscribtionPromoActivity.class);
        String bundle_name = ShopSoundPackage.INSTANCE.getBUNDLE_NAME();
        ViewShopSoundPackageBinding viewShopSoundPackageBinding = this.binding;
        if (viewShopSoundPackageBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            viewShopSoundPackageBinding = null;
        }
        intent.putExtra(bundle_name, viewShopSoundPackageBinding.getSoundPackage());
        Activity parentActivity = ContextKt.getParentActivity(getContext());
        if (parentActivity != null) {
            parentActivity.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(parentActivity, new Pair[0]).toBundle());
        }
    }

    private final void openShopSoundDescriptionScreen() {
        Intent intent = new Intent(getContext(), (Class<?>) SoundPackageDescriptionActivity.class);
        String bundle_name = ShopSoundPackage.INSTANCE.getBUNDLE_NAME();
        ViewShopSoundPackageBinding viewShopSoundPackageBinding = this.binding;
        if (viewShopSoundPackageBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            viewShopSoundPackageBinding = null;
        }
        intent.putExtra(bundle_name, viewShopSoundPackageBinding.getSoundPackage());
        getContext().startActivity(intent);
    }

    private final AlertDialog createDeleteConfirmAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), 2131886083);
        builder.setTitle(R.string.text_delete).setMessage(R.string.message_delete_sound_package_confirm).setNegativeButton(android.R.string.no, (DialogInterface.OnClickListener) null).setPositiveButton(R.string.text_delete, new DialogInterface.OnClickListener() { // from class: com.thor.app.gui.views.soundpackage.ShopSoundPackageView$$ExternalSyntheticLambda2
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) throws Resources.NotFoundException {
                ShopSoundPackageView.createDeleteConfirmAlertDialog$lambda$1(this.f$0, dialogInterface, i);
            }
        });
        return builder.create();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void createDeleteConfirmAlertDialog$lambda$1(ShopSoundPackageView this$0, DialogInterface dialogInterface, int i) throws Resources.NotFoundException {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.onDeleteSoundPackage();
    }

    private final void onDeleteSoundPackage() throws Resources.NotFoundException {
        if (BleManager.INSTANCE.from(getContext()).getMStateConnected()) {
            Timber.INSTANCE.i("onDeleteSoundPackage", new Object[0]);
            EventBus eventBus = EventBus.getDefault();
            ViewShopSoundPackageBinding viewShopSoundPackageBinding = this.binding;
            if (viewShopSoundPackageBinding == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                viewShopSoundPackageBinding = null;
            }
            ShopSoundPackage soundPackage = viewShopSoundPackageBinding.getSoundPackage();
            Intrinsics.checkNotNull(soundPackage);
            eventBus.post(new DeleteSoundPackageEvent(soundPackage));
            onSwipeToRightAnimation();
            return;
        }
        DialogManager dialogManager = DialogManager.INSTANCE;
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "context");
        dialogManager.createNoConnectionToBoardAlertDialog(context).show();
    }

    public final void onSwipeToLeftAnimation() throws Resources.NotFoundException {
        Timber.INSTANCE.i("onSwipeToLeftAnimation", new Object[0]);
        ViewShopSoundPackageBinding viewShopSoundPackageBinding = this.binding;
        ViewShopSoundPackageBinding viewShopSoundPackageBinding2 = null;
        if (viewShopSoundPackageBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            viewShopSoundPackageBinding = null;
        }
        ShopSoundPackage soundPackage = viewShopSoundPackageBinding.getSoundPackage();
        Boolean boolValueOf = soundPackage != null ? Boolean.valueOf(soundPackage.isLoadedOnBoard()) : null;
        Intrinsics.checkNotNull(boolValueOf);
        if (!boolValueOf.booleanValue() || this.mEnableToDelete || this.mSwiping) {
            return;
        }
        this.mSwiping = true;
        int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.margin_dp_8);
        ViewShopSoundPackageBinding viewShopSoundPackageBinding3 = this.binding;
        if (viewShopSoundPackageBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            viewShopSoundPackageBinding3 = null;
        }
        int width = viewShopSoundPackageBinding3.mainLayout.getWidth() + dimensionPixelSize;
        ViewShopSoundPackageBinding viewShopSoundPackageBinding4 = this.binding;
        if (viewShopSoundPackageBinding4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            viewShopSoundPackageBinding4 = null;
        }
        viewShopSoundPackageBinding4.layoutDelete.setX(width);
        Timber.INSTANCE.i("New positionX: %s", Integer.valueOf(width));
        ViewShopSoundPackageBinding viewShopSoundPackageBinding5 = this.binding;
        if (viewShopSoundPackageBinding5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            viewShopSoundPackageBinding5 = null;
        }
        viewShopSoundPackageBinding5.layoutDelete.setVisibility(0);
        int dimensionPixelOffset = getResources().getDimensionPixelOffset(R.dimen.width_dp_120);
        ViewShopSoundPackageBinding viewShopSoundPackageBinding6 = this.binding;
        if (viewShopSoundPackageBinding6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            viewShopSoundPackageBinding6 = null;
        }
        float x = viewShopSoundPackageBinding6.layoutCover.getX();
        ViewShopSoundPackageBinding viewShopSoundPackageBinding7 = this.binding;
        if (viewShopSoundPackageBinding7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            viewShopSoundPackageBinding7 = null;
        }
        float x2 = viewShopSoundPackageBinding7.layoutDelete.getX();
        Timber.INSTANCE.i("X: %s, x: %s", Float.valueOf(x), Float.valueOf(x2));
        ViewShopSoundPackageBinding viewShopSoundPackageBinding8 = this.binding;
        if (viewShopSoundPackageBinding8 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            viewShopSoundPackageBinding8 = null;
        }
        float f = dimensionPixelOffset;
        viewShopSoundPackageBinding8.layoutDelete.animate().x(x2 - f).setDuration(ANIMATION_DURATION).start();
        ViewShopSoundPackageBinding viewShopSoundPackageBinding9 = this.binding;
        if (viewShopSoundPackageBinding9 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            viewShopSoundPackageBinding2 = viewShopSoundPackageBinding9;
        }
        viewShopSoundPackageBinding2.layoutCover.animate().x(x - f).setDuration(ANIMATION_DURATION).start();
        this.mEnableToDelete = true;
        postDelayed(new Runnable() { // from class: com.thor.app.gui.views.soundpackage.ShopSoundPackageView$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                ShopSoundPackageView.onSwipeToLeftAnimation$lambda$2(this.f$0);
            }
        }, ANIMATION_DURATION);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onSwipeToLeftAnimation$lambda$2(ShopSoundPackageView this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.mSwiping = false;
    }

    public final void onSwipeToRightAnimation() throws Resources.NotFoundException {
        Timber.INSTANCE.i("onSwipeToRightAnimation", new Object[0]);
        ViewShopSoundPackageBinding viewShopSoundPackageBinding = this.binding;
        ViewShopSoundPackageBinding viewShopSoundPackageBinding2 = null;
        if (viewShopSoundPackageBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            viewShopSoundPackageBinding = null;
        }
        ShopSoundPackage soundPackage = viewShopSoundPackageBinding.getSoundPackage();
        Boolean boolValueOf = soundPackage != null ? Boolean.valueOf(soundPackage.isLoadedOnBoard()) : null;
        Intrinsics.checkNotNull(boolValueOf);
        if (boolValueOf.booleanValue() && this.mEnableToDelete && !this.mSwiping) {
            this.mSwiping = true;
            int dimensionPixelOffset = getResources().getDimensionPixelOffset(R.dimen.width_dp_120);
            ViewShopSoundPackageBinding viewShopSoundPackageBinding3 = this.binding;
            if (viewShopSoundPackageBinding3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                viewShopSoundPackageBinding3 = null;
            }
            float x = viewShopSoundPackageBinding3.layoutCover.getX();
            ViewShopSoundPackageBinding viewShopSoundPackageBinding4 = this.binding;
            if (viewShopSoundPackageBinding4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                viewShopSoundPackageBinding4 = null;
            }
            float x2 = viewShopSoundPackageBinding4.layoutDelete.getX();
            ViewShopSoundPackageBinding viewShopSoundPackageBinding5 = this.binding;
            if (viewShopSoundPackageBinding5 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                viewShopSoundPackageBinding5 = null;
            }
            float f = dimensionPixelOffset;
            viewShopSoundPackageBinding5.layoutDelete.animate().x(x2 + f).setDuration(ANIMATION_DURATION).start();
            ViewShopSoundPackageBinding viewShopSoundPackageBinding6 = this.binding;
            if (viewShopSoundPackageBinding6 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
            } else {
                viewShopSoundPackageBinding2 = viewShopSoundPackageBinding6;
            }
            viewShopSoundPackageBinding2.layoutCover.animate().x(f + x).setDuration(ANIMATION_DURATION).start();
            Timber.INSTANCE.i("X: %s, x: %s", Float.valueOf(x), Float.valueOf(x2));
            this.mEnableToDelete = false;
            postDelayed(new Runnable() { // from class: com.thor.app.gui.views.soundpackage.ShopSoundPackageView$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    ShopSoundPackageView.onSwipeToRightAnimation$lambda$3(this.f$0);
                }
            }, ANIMATION_DURATION);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onSwipeToRightAnimation$lambda$3(ShopSoundPackageView this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.mSwiping = false;
    }

    private final void initSwipe() {
        Timber.INSTANCE.i("initSwipe", new Object[0]);
        ViewShopSoundPackageBinding viewShopSoundPackageBinding = this.binding;
        ViewShopSoundPackageBinding viewShopSoundPackageBinding2 = null;
        if (viewShopSoundPackageBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            viewShopSoundPackageBinding = null;
        }
        FrameLayout frameLayout = viewShopSoundPackageBinding.layoutCover;
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "context");
        frameLayout.setOnTouchListener(new OnSwipeListener(this, context));
        ViewShopSoundPackageBinding viewShopSoundPackageBinding3 = this.binding;
        if (viewShopSoundPackageBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            viewShopSoundPackageBinding3 = null;
        }
        ShopSoundPackage soundPackage = viewShopSoundPackageBinding3.getSoundPackage();
        Boolean boolValueOf = soundPackage != null ? Boolean.valueOf(soundPackage.isLoadedOnBoard()) : null;
        Intrinsics.checkNotNull(boolValueOf);
        if (boolValueOf.booleanValue()) {
            ViewShopSoundPackageBinding viewShopSoundPackageBinding4 = this.binding;
            if (viewShopSoundPackageBinding4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
            } else {
                viewShopSoundPackageBinding2 = viewShopSoundPackageBinding4;
            }
            viewShopSoundPackageBinding2.layoutDelete.setOnClickListener(this);
            return;
        }
        ViewShopSoundPackageBinding viewShopSoundPackageBinding5 = this.binding;
        if (viewShopSoundPackageBinding5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            viewShopSoundPackageBinding5 = null;
        }
        viewShopSoundPackageBinding5.layoutDelete.setVisibility(4);
        ViewShopSoundPackageBinding viewShopSoundPackageBinding6 = this.binding;
        if (viewShopSoundPackageBinding6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            viewShopSoundPackageBinding6 = null;
        }
        viewShopSoundPackageBinding6.layoutDelete.setOnClickListener(null);
    }

    /* compiled from: ShopSoundPackageView.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0005\b\u0086\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u0005\u001a\u00020\u0006H\u0016J\b\u0010\u0007\u001a\u00020\u0006H\u0016J\b\u0010\b\u001a\u00020\u0006H\u0016J\b\u0010\t\u001a\u00020\u0006H\u0016J\b\u0010\n\u001a\u00020\u0006H\u0016¨\u0006\u000b"}, d2 = {"Lcom/thor/app/gui/views/soundpackage/ShopSoundPackageView$OnSwipeListener;", "Lcom/thor/basemodule/gui/view/listener/OnSwipeTouchListener;", "context", "Landroid/content/Context;", "(Lcom/thor/app/gui/views/soundpackage/ShopSoundPackageView;Landroid/content/Context;)V", "onSingleTap", "", "onSwipeBottom", "onSwipeLeft", "onSwipeRight", "onSwipeTop", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public final class OnSwipeListener extends OnSwipeTouchListener {
        final /* synthetic */ ShopSoundPackageView this$0;

        @Override // com.thor.basemodule.gui.view.listener.OnSwipeTouchListener
        public void onSwipeBottom() {
        }

        @Override // com.thor.basemodule.gui.view.listener.OnSwipeTouchListener
        public void onSwipeTop() {
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public OnSwipeListener(ShopSoundPackageView shopSoundPackageView, Context context) {
            super(context);
            Intrinsics.checkNotNullParameter(context, "context");
            this.this$0 = shopSoundPackageView;
        }

        @Override // com.thor.basemodule.gui.view.listener.OnSwipeTouchListener
        public void onSwipeRight() throws Resources.NotFoundException {
            Timber.INSTANCE.i("onSwipeRight", new Object[0]);
            this.this$0.onSwipeToRightAnimation();
        }

        @Override // com.thor.basemodule.gui.view.listener.OnSwipeTouchListener
        public void onSwipeLeft() throws Resources.NotFoundException {
            Timber.INSTANCE.i("onSwipeLeft", new Object[0]);
            this.this$0.onSwipeToLeftAnimation();
        }

        @Override // com.thor.basemodule.gui.view.listener.OnSwipeTouchListener
        public void onSingleTap() {
            this.this$0.onClickMainLayout();
        }
    }
}
