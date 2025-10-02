package com.thor.app.gui.views.sgu;

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
import androidx.appcompat.widget.AppCompatImageView;
import androidx.cardview.widget.CardView;
import com.android.billingclient.api.SkuDetails;
import com.carsystems.thor.app.R;
import com.carsystems.thor.app.databinding.ViewSguShopSoundPackageBinding;
import com.thor.app.bus.events.shop.sgu.DeleteSoundPackageEvent;
import com.thor.app.gui.activities.preset.sgu.AddSguPresetActivity;
import com.thor.app.gui.activities.shop.sgu.SguSetDetailsActivity;
import com.thor.app.gui.activities.shop.sgu.SguSubscriptionPromoActivity;
import com.thor.app.gui.dialog.DialogManager;
import com.thor.app.managers.BleManager;
import com.thor.app.settings.Settings;
import com.thor.basemodule.extensions.ContextKt;
import com.thor.basemodule.gui.view.listener.OnSwipeTouchListener;
import com.thor.businessmodule.settings.Variables;
import com.thor.networkmodule.model.responses.sgu.SguSoundSet;
import java.lang.ref.WeakReference;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.greenrobot.eventbus.EventBus;
import timber.log.Timber;

/* compiled from: SguShopSoundPackageView.kt */
@Metadata(d1 = {"\u0000`\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000 /2\u00020\u00012\u00020\u0002:\u0002/0B\u000f\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005B\u0019\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\u0002\u0010\bB!\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\u0006\u0010\t\u001a\u00020\n¢\u0006\u0002\u0010\u000bJ\u0006\u0010\u0013\u001a\u00020\u0014J\n\u0010\u0015\u001a\u0004\u0018\u00010\u0016H\u0002J\b\u0010\u0017\u001a\u00020\u0014H\u0002J\u0012\u0010\u0018\u001a\u00020\u00142\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007H\u0002J\u0012\u0010\u0019\u001a\u00020\u00142\b\u0010\u001a\u001a\u0004\u0018\u00010\u001bH\u0016J\b\u0010\u001c\u001a\u00020\u0014H\u0002J\b\u0010\u001d\u001a\u00020\u0014H\u0002J\u0016\u0010\u001e\u001a\u00020\u00142\u0006\u0010\u001f\u001a\u00020\n2\u0006\u0010 \u001a\u00020\nJ\u001e\u0010\u001e\u001a\u00020\u00142\u0006\u0010\u001f\u001a\u00020\n2\u0006\u0010 \u001a\u00020\n2\u0006\u0010!\u001a\u00020\nJ\u0006\u0010\"\u001a\u00020\u0014J\u0006\u0010#\u001a\u00020\u0014J\b\u0010$\u001a\u00020\u0014H\u0002J\b\u0010%\u001a\u00020\u0014H\u0002J\b\u0010&\u001a\u00020\u0014H\u0002J\u0010\u0010'\u001a\u00020\u00142\b\u0010(\u001a\u0004\u0018\u00010)J\u000e\u0010*\u001a\u00020\u00142\u0006\u0010+\u001a\u00020\nJ\u0010\u0010,\u001a\u00020\u00142\b\u0010-\u001a\u0004\u0018\u00010.R\u000e\u0010\f\u001a\u00020\rX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n\u0000¨\u00061"}, d2 = {"Lcom/thor/app/gui/views/sgu/SguShopSoundPackageView;", "Landroidx/cardview/widget/CardView;", "Landroid/view/View$OnClickListener;", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "attrs", "Landroid/util/AttributeSet;", "(Landroid/content/Context;Landroid/util/AttributeSet;)V", "defStyleAttr", "", "(Landroid/content/Context;Landroid/util/AttributeSet;I)V", "binding", "Lcom/carsystems/thor/app/databinding/ViewSguShopSoundPackageBinding;", "mEnableToDelete", "", "mShiftOfPosition", "", "mSwiping", "clean", "", "createDeleteConfirmAlertDialog", "Landroidx/appcompat/app/AlertDialog;", "initSwipe", "initView", "onClick", "v", "Landroid/view/View;", "onClickMainLayout", "onDeleteSoundPackage", "onNestedScrollChanged", "scrollY", "oldScrollY", "height", "onSwipeToLeftAnimation", "onSwipeToRightAnimation", "openAddPresetScreen", "openShopSoundDescriptionScreen", "openShopSoundPackageScreen", "setModel", "model", "Lcom/thor/networkmodule/model/responses/sgu/SguSoundSet;", "setShiftOfPosition", "fullHeight", "setSkuDetails", "skuDetails", "Lcom/android/billingclient/api/SkuDetails;", "Companion", "OnSwipeListener", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class SguShopSoundPackageView extends CardView implements View.OnClickListener {
    private static final long ANIMATION_DURATION = 240;
    private ViewSguShopSoundPackageBinding binding;
    private boolean mEnableToDelete;
    private float mShiftOfPosition;
    private boolean mSwiping;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public SguShopSoundPackageView(Context context) {
        this(context, null);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public SguShopSoundPackageView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SguShopSoundPackageView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        Intrinsics.checkNotNullParameter(context, "context");
        initView(attributeSet);
    }

    private final void initView(AttributeSet attrs) {
        LayoutInflater layoutInflater = (LayoutInflater) new WeakReference(LayoutInflater.from(getContext())).get();
        Intrinsics.checkNotNull(layoutInflater);
        ViewSguShopSoundPackageBinding viewSguShopSoundPackageBindingInflate = ViewSguShopSoundPackageBinding.inflate(layoutInflater, this, true);
        Intrinsics.checkNotNullExpressionValue(viewSguShopSoundPackageBindingInflate, "inflate(layoutInflater!!, this, true)");
        this.binding = viewSguShopSoundPackageBindingInflate;
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

    public final void setModel(SguSoundSet model) {
        ViewSguShopSoundPackageBinding viewSguShopSoundPackageBinding = this.binding;
        if (viewSguShopSoundPackageBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            viewSguShopSoundPackageBinding = null;
        }
        viewSguShopSoundPackageBinding.setSoundPackage(model);
        initSwipe();
    }

    public final void setSkuDetails(SkuDetails skuDetails) {
        ViewSguShopSoundPackageBinding viewSguShopSoundPackageBinding = this.binding;
        if (viewSguShopSoundPackageBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            viewSguShopSoundPackageBinding = null;
        }
        viewSguShopSoundPackageBinding.setSkuDetails(skuDetails);
    }

    public final void onNestedScrollChanged(int scrollY, int oldScrollY) {
        ViewSguShopSoundPackageBinding viewSguShopSoundPackageBinding = this.binding;
        if (viewSguShopSoundPackageBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            viewSguShopSoundPackageBinding = null;
        }
        AppCompatImageView appCompatImageView = viewSguShopSoundPackageBinding.imageViewCover;
        int i = scrollY - oldScrollY;
        float y = appCompatImageView.getY();
        float f = (i * this.mShiftOfPosition) + y;
        if (i > 0 && y < 0.0f) {
            appCompatImageView.setY(f);
        } else {
            if (i >= 0 || y <= (-(appCompatImageView.getHeight() - getHeight()))) {
                return;
            }
            appCompatImageView.setY(f);
        }
    }

    public final void onNestedScrollChanged(int scrollY, int oldScrollY, int height) {
        setShiftOfPosition(height);
        onNestedScrollChanged(scrollY, oldScrollY);
    }

    public final void setShiftOfPosition(int fullHeight) {
        ViewSguShopSoundPackageBinding viewSguShopSoundPackageBinding = this.binding;
        if (viewSguShopSoundPackageBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            viewSguShopSoundPackageBinding = null;
        }
        this.mShiftOfPosition = (viewSguShopSoundPackageBinding.mainLayout.getHeight() / fullHeight) / 3.0f;
    }

    public final void clean() {
        this.mEnableToDelete = false;
        ViewSguShopSoundPackageBinding viewSguShopSoundPackageBinding = this.binding;
        ViewSguShopSoundPackageBinding viewSguShopSoundPackageBinding2 = null;
        if (viewSguShopSoundPackageBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            viewSguShopSoundPackageBinding = null;
        }
        viewSguShopSoundPackageBinding.layoutCover.setX(0.0f);
        ViewSguShopSoundPackageBinding viewSguShopSoundPackageBinding3 = this.binding;
        if (viewSguShopSoundPackageBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            viewSguShopSoundPackageBinding2 = viewSguShopSoundPackageBinding3;
        }
        viewSguShopSoundPackageBinding2.layoutDelete.setVisibility(4);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void onClickMainLayout() {
        ViewSguShopSoundPackageBinding viewSguShopSoundPackageBinding = this.binding;
        ViewSguShopSoundPackageBinding viewSguShopSoundPackageBinding2 = null;
        if (viewSguShopSoundPackageBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            viewSguShopSoundPackageBinding = null;
        }
        if (viewSguShopSoundPackageBinding.getSoundPackage() == null) {
            return;
        }
        ViewSguShopSoundPackageBinding viewSguShopSoundPackageBinding3 = this.binding;
        if (viewSguShopSoundPackageBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            viewSguShopSoundPackageBinding3 = null;
        }
        SguSoundSet soundPackage = viewSguShopSoundPackageBinding3.getSoundPackage();
        Boolean boolValueOf = soundPackage != null ? Boolean.valueOf(soundPackage.getIsLoadedOnBoard()) : null;
        Intrinsics.checkNotNull(boolValueOf);
        if (boolValueOf.booleanValue()) {
            openAddPresetScreen();
            return;
        }
        if (Variables.INSTANCE.getSUBSCRIPTION_FEATURE_REQUIREMENTS_SATISFIED()) {
            ViewSguShopSoundPackageBinding viewSguShopSoundPackageBinding4 = this.binding;
            if (viewSguShopSoundPackageBinding4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
            } else {
                viewSguShopSoundPackageBinding2 = viewSguShopSoundPackageBinding4;
            }
            SguSoundSet soundPackage2 = viewSguShopSoundPackageBinding2.getSoundPackage();
            boolean z = false;
            if (soundPackage2 != null && !soundPackage2.getSetStatus()) {
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
        Intent intent = new Intent(getContext(), (Class<?>) AddSguPresetActivity.class);
        ViewSguShopSoundPackageBinding viewSguShopSoundPackageBinding = this.binding;
        if (viewSguShopSoundPackageBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            viewSguShopSoundPackageBinding = null;
        }
        intent.putExtra(SguSoundSet.BUNDLE_NAME, viewSguShopSoundPackageBinding.getSoundPackage());
        getContext().startActivity(intent);
    }

    private final void openShopSoundPackageScreen() {
        Intent intent = new Intent(getContext(), (Class<?>) SguSubscriptionPromoActivity.class);
        ViewSguShopSoundPackageBinding viewSguShopSoundPackageBinding = this.binding;
        if (viewSguShopSoundPackageBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            viewSguShopSoundPackageBinding = null;
        }
        intent.putExtra(SguSoundSet.BUNDLE_NAME, viewSguShopSoundPackageBinding.getSoundPackage());
        Activity parentActivity = ContextKt.getParentActivity(getContext());
        if (parentActivity != null) {
            parentActivity.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(parentActivity, new Pair[0]).toBundle());
        }
    }

    private final void openShopSoundDescriptionScreen() {
        Intent intent = new Intent(getContext(), (Class<?>) SguSetDetailsActivity.class);
        ViewSguShopSoundPackageBinding viewSguShopSoundPackageBinding = this.binding;
        if (viewSguShopSoundPackageBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            viewSguShopSoundPackageBinding = null;
        }
        intent.putExtra(SguSoundSet.BUNDLE_NAME, viewSguShopSoundPackageBinding.getSoundPackage());
        getContext().startActivity(intent);
    }

    private final AlertDialog createDeleteConfirmAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), 2131886083);
        builder.setTitle(R.string.text_delete).setMessage(R.string.message_delete_sound_package_confirm).setNegativeButton(android.R.string.no, (DialogInterface.OnClickListener) null).setPositiveButton(R.string.text_delete, new DialogInterface.OnClickListener() { // from class: com.thor.app.gui.views.sgu.SguShopSoundPackageView$$ExternalSyntheticLambda1
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) throws Resources.NotFoundException {
                SguShopSoundPackageView.createDeleteConfirmAlertDialog$lambda$2(this.f$0, dialogInterface, i);
            }
        });
        return builder.create();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void createDeleteConfirmAlertDialog$lambda$2(SguShopSoundPackageView this$0, DialogInterface dialogInterface, int i) throws Resources.NotFoundException {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.onDeleteSoundPackage();
    }

    private final void onDeleteSoundPackage() throws Resources.NotFoundException {
        if (BleManager.INSTANCE.from(getContext()).getMStateConnected()) {
            Timber.INSTANCE.i("onDeleteSoundPackage", new Object[0]);
            EventBus eventBus = EventBus.getDefault();
            ViewSguShopSoundPackageBinding viewSguShopSoundPackageBinding = this.binding;
            if (viewSguShopSoundPackageBinding == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                viewSguShopSoundPackageBinding = null;
            }
            SguSoundSet soundPackage = viewSguShopSoundPackageBinding.getSoundPackage();
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
        ViewSguShopSoundPackageBinding viewSguShopSoundPackageBinding = this.binding;
        ViewSguShopSoundPackageBinding viewSguShopSoundPackageBinding2 = null;
        if (viewSguShopSoundPackageBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            viewSguShopSoundPackageBinding = null;
        }
        SguSoundSet soundPackage = viewSguShopSoundPackageBinding.getSoundPackage();
        Boolean boolValueOf = soundPackage != null ? Boolean.valueOf(soundPackage.getIsLoadedOnBoard()) : null;
        Intrinsics.checkNotNull(boolValueOf);
        if (!boolValueOf.booleanValue() || this.mEnableToDelete || this.mSwiping) {
            return;
        }
        this.mSwiping = true;
        int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.margin_dp_8);
        ViewSguShopSoundPackageBinding viewSguShopSoundPackageBinding3 = this.binding;
        if (viewSguShopSoundPackageBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            viewSguShopSoundPackageBinding3 = null;
        }
        int width = viewSguShopSoundPackageBinding3.mainLayout.getWidth() + dimensionPixelSize;
        ViewSguShopSoundPackageBinding viewSguShopSoundPackageBinding4 = this.binding;
        if (viewSguShopSoundPackageBinding4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            viewSguShopSoundPackageBinding4 = null;
        }
        viewSguShopSoundPackageBinding4.layoutDelete.setX(width);
        Timber.INSTANCE.i("New positionX: %s", Integer.valueOf(width));
        ViewSguShopSoundPackageBinding viewSguShopSoundPackageBinding5 = this.binding;
        if (viewSguShopSoundPackageBinding5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            viewSguShopSoundPackageBinding5 = null;
        }
        viewSguShopSoundPackageBinding5.layoutDelete.setVisibility(0);
        int dimensionPixelOffset = getResources().getDimensionPixelOffset(R.dimen.width_dp_120);
        ViewSguShopSoundPackageBinding viewSguShopSoundPackageBinding6 = this.binding;
        if (viewSguShopSoundPackageBinding6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            viewSguShopSoundPackageBinding6 = null;
        }
        float x = viewSguShopSoundPackageBinding6.layoutCover.getX();
        ViewSguShopSoundPackageBinding viewSguShopSoundPackageBinding7 = this.binding;
        if (viewSguShopSoundPackageBinding7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            viewSguShopSoundPackageBinding7 = null;
        }
        float x2 = viewSguShopSoundPackageBinding7.layoutDelete.getX();
        Timber.INSTANCE.i("X: %s, x: %s", Float.valueOf(x), Float.valueOf(x2));
        ViewSguShopSoundPackageBinding viewSguShopSoundPackageBinding8 = this.binding;
        if (viewSguShopSoundPackageBinding8 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            viewSguShopSoundPackageBinding8 = null;
        }
        float f = dimensionPixelOffset;
        viewSguShopSoundPackageBinding8.layoutDelete.animate().x(x2 - f).setDuration(ANIMATION_DURATION).start();
        ViewSguShopSoundPackageBinding viewSguShopSoundPackageBinding9 = this.binding;
        if (viewSguShopSoundPackageBinding9 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            viewSguShopSoundPackageBinding2 = viewSguShopSoundPackageBinding9;
        }
        viewSguShopSoundPackageBinding2.layoutCover.animate().x(x - f).setDuration(ANIMATION_DURATION).start();
        this.mEnableToDelete = true;
        postDelayed(new Runnable() { // from class: com.thor.app.gui.views.sgu.SguShopSoundPackageView$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                SguShopSoundPackageView.onSwipeToLeftAnimation$lambda$3(this.f$0);
            }
        }, ANIMATION_DURATION);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onSwipeToLeftAnimation$lambda$3(SguShopSoundPackageView this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.mSwiping = false;
    }

    public final void onSwipeToRightAnimation() throws Resources.NotFoundException {
        Timber.INSTANCE.i("onSwipeToRightAnimation", new Object[0]);
        ViewSguShopSoundPackageBinding viewSguShopSoundPackageBinding = this.binding;
        ViewSguShopSoundPackageBinding viewSguShopSoundPackageBinding2 = null;
        if (viewSguShopSoundPackageBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            viewSguShopSoundPackageBinding = null;
        }
        SguSoundSet soundPackage = viewSguShopSoundPackageBinding.getSoundPackage();
        Boolean boolValueOf = soundPackage != null ? Boolean.valueOf(soundPackage.getIsLoadedOnBoard()) : null;
        Intrinsics.checkNotNull(boolValueOf);
        if (boolValueOf.booleanValue() && this.mEnableToDelete && !this.mSwiping) {
            this.mSwiping = true;
            int dimensionPixelOffset = getResources().getDimensionPixelOffset(R.dimen.width_dp_120);
            ViewSguShopSoundPackageBinding viewSguShopSoundPackageBinding3 = this.binding;
            if (viewSguShopSoundPackageBinding3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                viewSguShopSoundPackageBinding3 = null;
            }
            float x = viewSguShopSoundPackageBinding3.layoutCover.getX();
            ViewSguShopSoundPackageBinding viewSguShopSoundPackageBinding4 = this.binding;
            if (viewSguShopSoundPackageBinding4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                viewSguShopSoundPackageBinding4 = null;
            }
            float x2 = viewSguShopSoundPackageBinding4.layoutDelete.getX();
            ViewSguShopSoundPackageBinding viewSguShopSoundPackageBinding5 = this.binding;
            if (viewSguShopSoundPackageBinding5 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                viewSguShopSoundPackageBinding5 = null;
            }
            float f = dimensionPixelOffset;
            viewSguShopSoundPackageBinding5.layoutDelete.animate().x(x2 + f).setDuration(ANIMATION_DURATION).start();
            ViewSguShopSoundPackageBinding viewSguShopSoundPackageBinding6 = this.binding;
            if (viewSguShopSoundPackageBinding6 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
            } else {
                viewSguShopSoundPackageBinding2 = viewSguShopSoundPackageBinding6;
            }
            viewSguShopSoundPackageBinding2.layoutCover.animate().x(f + x).setDuration(ANIMATION_DURATION).start();
            Timber.INSTANCE.i("X: %s, x: %s", Float.valueOf(x), Float.valueOf(x2));
            this.mEnableToDelete = false;
            postDelayed(new Runnable() { // from class: com.thor.app.gui.views.sgu.SguShopSoundPackageView$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    SguShopSoundPackageView.onSwipeToRightAnimation$lambda$4(this.f$0);
                }
            }, ANIMATION_DURATION);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onSwipeToRightAnimation$lambda$4(SguShopSoundPackageView this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.mSwiping = false;
    }

    private final void initSwipe() {
        Timber.INSTANCE.i("initSwipe", new Object[0]);
        ViewSguShopSoundPackageBinding viewSguShopSoundPackageBinding = this.binding;
        ViewSguShopSoundPackageBinding viewSguShopSoundPackageBinding2 = null;
        if (viewSguShopSoundPackageBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            viewSguShopSoundPackageBinding = null;
        }
        FrameLayout frameLayout = viewSguShopSoundPackageBinding.layoutCover;
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "context");
        frameLayout.setOnTouchListener(new OnSwipeListener(this, context));
        ViewSguShopSoundPackageBinding viewSguShopSoundPackageBinding3 = this.binding;
        if (viewSguShopSoundPackageBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            viewSguShopSoundPackageBinding3 = null;
        }
        SguSoundSet soundPackage = viewSguShopSoundPackageBinding3.getSoundPackage();
        Boolean boolValueOf = soundPackage != null ? Boolean.valueOf(soundPackage.getIsLoadedOnBoard()) : null;
        Intrinsics.checkNotNull(boolValueOf);
        if (boolValueOf.booleanValue()) {
            ViewSguShopSoundPackageBinding viewSguShopSoundPackageBinding4 = this.binding;
            if (viewSguShopSoundPackageBinding4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
            } else {
                viewSguShopSoundPackageBinding2 = viewSguShopSoundPackageBinding4;
            }
            viewSguShopSoundPackageBinding2.layoutDelete.setOnClickListener(this);
            return;
        }
        ViewSguShopSoundPackageBinding viewSguShopSoundPackageBinding5 = this.binding;
        if (viewSguShopSoundPackageBinding5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            viewSguShopSoundPackageBinding5 = null;
        }
        viewSguShopSoundPackageBinding5.layoutDelete.setVisibility(4);
        ViewSguShopSoundPackageBinding viewSguShopSoundPackageBinding6 = this.binding;
        if (viewSguShopSoundPackageBinding6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            viewSguShopSoundPackageBinding6 = null;
        }
        viewSguShopSoundPackageBinding6.layoutDelete.setOnClickListener(null);
    }

    /* compiled from: SguShopSoundPackageView.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0005\b\u0086\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u0005\u001a\u00020\u0006H\u0016J\b\u0010\u0007\u001a\u00020\u0006H\u0016J\b\u0010\b\u001a\u00020\u0006H\u0016J\b\u0010\t\u001a\u00020\u0006H\u0016J\b\u0010\n\u001a\u00020\u0006H\u0016¨\u0006\u000b"}, d2 = {"Lcom/thor/app/gui/views/sgu/SguShopSoundPackageView$OnSwipeListener;", "Lcom/thor/basemodule/gui/view/listener/OnSwipeTouchListener;", "context", "Landroid/content/Context;", "(Lcom/thor/app/gui/views/sgu/SguShopSoundPackageView;Landroid/content/Context;)V", "onSingleTap", "", "onSwipeBottom", "onSwipeLeft", "onSwipeRight", "onSwipeTop", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public final class OnSwipeListener extends OnSwipeTouchListener {
        final /* synthetic */ SguShopSoundPackageView this$0;

        @Override // com.thor.basemodule.gui.view.listener.OnSwipeTouchListener
        public void onSwipeBottom() {
        }

        @Override // com.thor.basemodule.gui.view.listener.OnSwipeTouchListener
        public void onSwipeTop() {
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public OnSwipeListener(SguShopSoundPackageView sguShopSoundPackageView, Context context) {
            super(context);
            Intrinsics.checkNotNullParameter(context, "context");
            this.this$0 = sguShopSoundPackageView;
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
