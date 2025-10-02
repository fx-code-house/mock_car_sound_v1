package com.thor.app.gui.views.sgu;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import androidx.cardview.widget.CardView;
import com.carsystems.thor.app.R;
import com.carsystems.thor.app.databinding.ViewSguSoundFavBinding;
import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import com.thor.app.gui.dialog.DialogManager;
import com.thor.app.managers.BleManager;
import com.thor.networkmodule.model.responses.sgu.SguSound;
import java.lang.ref.WeakReference;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import timber.log.Timber;

/* compiled from: SguSoundFavView.kt */
@Metadata(d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u00012\u00020\u0002B\u000f\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005B\u0019\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\u0002\u0010\bB!\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\u0006\u0010\t\u001a\u00020\n¢\u0006\u0002\u0010\u000bJ\u0012\u0010\u0018\u001a\u00020\u00192\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007H\u0002J\u0012\u0010\u001a\u001a\u00020\u00192\b\u0010\u001b\u001a\u0004\u0018\u00010\u001cH\u0016J\u000e\u0010\u001d\u001a\u00020\u00192\u0006\u0010\u0016\u001a\u00020\u0017J\u000e\u0010\u001e\u001a\u00020\u00192\u0006\u0010\u001f\u001a\u00020 J\b\u0010!\u001a\u00020\u0019H\u0002R\u000e\u0010\f\u001a\u00020\rX\u0082.¢\u0006\u0002\n\u0000R\u001b\u0010\u000e\u001a\u00020\u000f8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0012\u0010\u0013\u001a\u0004\b\u0010\u0010\u0011R\u000e\u0010\u0014\u001a\u00020\u0015X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0016\u001a\u0004\u0018\u00010\u0017X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\""}, d2 = {"Lcom/thor/app/gui/views/sgu/SguSoundFavView;", "Landroidx/cardview/widget/CardView;", "Landroid/view/View$OnClickListener;", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "attrs", "Landroid/util/AttributeSet;", "(Landroid/content/Context;Landroid/util/AttributeSet;)V", "defStyleAttr", "", "(Landroid/content/Context;Landroid/util/AttributeSet;I)V", "binding", "Lcom/carsystems/thor/app/databinding/ViewSguSoundFavBinding;", "bleManager", "Lcom/thor/app/managers/BleManager;", "getBleManager", "()Lcom/thor/app/managers/BleManager;", "bleManager$delegate", "Lkotlin/Lazy;", "isFavourite", "", ServiceSpecificExtraArgs.CastExtraArgs.LISTENER, "Lcom/thor/app/gui/views/sgu/OnSguSoundClickListener;", "initView", "", "onClick", "v", "Landroid/view/View;", "setOnClickListener", "setSoundPackage", "soundPackage", "Lcom/thor/networkmodule/model/responses/sgu/SguSound;", "updateUi", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class SguSoundFavView extends CardView implements View.OnClickListener {
    private ViewSguSoundFavBinding binding;

    /* renamed from: bleManager$delegate, reason: from kotlin metadata */
    private final Lazy bleManager;
    private boolean isFavourite;
    private OnSguSoundClickListener listener;

    private final BleManager getBleManager() {
        return (BleManager) this.bleManager.getValue();
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public SguSoundFavView(Context context) {
        this(context, null);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public SguSoundFavView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SguSoundFavView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        Intrinsics.checkNotNullParameter(context, "context");
        this.bleManager = LazyKt.lazy(new Function0<BleManager>() { // from class: com.thor.app.gui.views.sgu.SguSoundFavView$bleManager$2
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final BleManager invoke() {
                return BleManager.INSTANCE.from(this.this$0.getContext());
            }
        });
        initView(attributeSet);
    }

    private final void initView(AttributeSet attrs) {
        LayoutInflater layoutInflater = (LayoutInflater) new WeakReference(LayoutInflater.from(getContext())).get();
        Intrinsics.checkNotNull(layoutInflater);
        ViewSguSoundFavBinding viewSguSoundFavBindingInflate = ViewSguSoundFavBinding.inflate(layoutInflater, this, true);
        Intrinsics.checkNotNullExpressionValue(viewSguSoundFavBindingInflate, "inflate(layoutInflater!!, this, true)");
        this.binding = viewSguSoundFavBindingInflate;
        if (isInEditMode()) {
            return;
        }
        ViewSguSoundFavBinding viewSguSoundFavBinding = this.binding;
        ViewSguSoundFavBinding viewSguSoundFavBinding2 = null;
        if (viewSguSoundFavBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            viewSguSoundFavBinding = null;
        }
        SguSoundFavView sguSoundFavView = this;
        viewSguSoundFavBinding.getRoot().setOnClickListener(sguSoundFavView);
        ViewSguSoundFavBinding viewSguSoundFavBinding3 = this.binding;
        if (viewSguSoundFavBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            viewSguSoundFavBinding2 = viewSguSoundFavBinding3;
        }
        viewSguSoundFavBinding2.imageFavourite.setOnClickListener(sguSoundFavView);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v) {
        if (getBleManager().isBleEnabledAndDeviceConnected()) {
            ViewSguSoundFavBinding viewSguSoundFavBinding = null;
            Integer numValueOf = v != null ? Integer.valueOf(v.getId()) : null;
            if (numValueOf != null && numValueOf.intValue() == R.id.image_favourite) {
                OnSguSoundClickListener onSguSoundClickListener = this.listener;
                if (onSguSoundClickListener != null) {
                    ViewSguSoundFavBinding viewSguSoundFavBinding2 = this.binding;
                    if (viewSguSoundFavBinding2 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("binding");
                    } else {
                        viewSguSoundFavBinding = viewSguSoundFavBinding2;
                    }
                    SguSound model = viewSguSoundFavBinding.getModel();
                    if (model == null) {
                        throw new IllegalArgumentException("Required value was null.".toString());
                    }
                    Intrinsics.checkNotNullExpressionValue(model, "requireNotNull(binding.model)");
                    onSguSoundClickListener.onFavClick(model);
                    return;
                }
                return;
            }
            OnSguSoundClickListener onSguSoundClickListener2 = this.listener;
            if (onSguSoundClickListener2 != null) {
                ViewSguSoundFavBinding viewSguSoundFavBinding3 = this.binding;
                if (viewSguSoundFavBinding3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("binding");
                } else {
                    viewSguSoundFavBinding = viewSguSoundFavBinding3;
                }
                SguSound model2 = viewSguSoundFavBinding.getModel();
                if (model2 == null) {
                    throw new IllegalArgumentException("Required value was null.".toString());
                }
                Intrinsics.checkNotNullExpressionValue(model2, "requireNotNull(binding.model)");
                onSguSoundClickListener2.onPlayClick(model2);
                return;
            }
            return;
        }
        DialogManager dialogManager = DialogManager.INSTANCE;
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "context");
        dialogManager.createNoConnectionToBoardAlertDialog(context).show();
    }

    public final void setSoundPackage(SguSound soundPackage) {
        Intrinsics.checkNotNullParameter(soundPackage, "soundPackage");
        Timber.INSTANCE.i("setSoundPackage: %s", soundPackage);
        ViewSguSoundFavBinding viewSguSoundFavBinding = this.binding;
        if (viewSguSoundFavBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            viewSguSoundFavBinding = null;
        }
        viewSguSoundFavBinding.setModel(soundPackage);
        this.isFavourite = soundPackage.isFavourite();
        updateUi();
    }

    private final void updateUi() {
        ViewSguSoundFavBinding viewSguSoundFavBinding = null;
        if (this.isFavourite) {
            ViewSguSoundFavBinding viewSguSoundFavBinding2 = this.binding;
            if (viewSguSoundFavBinding2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
            } else {
                viewSguSoundFavBinding = viewSguSoundFavBinding2;
            }
            viewSguSoundFavBinding.imageFavourite.setImageResource(R.drawable.ic_heart_filled);
            return;
        }
        ViewSguSoundFavBinding viewSguSoundFavBinding3 = this.binding;
        if (viewSguSoundFavBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            viewSguSoundFavBinding = viewSguSoundFavBinding3;
        }
        viewSguSoundFavBinding.imageFavourite.setImageResource(R.drawable.ic_heart_outline);
    }

    public final void setOnClickListener(OnSguSoundClickListener listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        this.listener = listener;
    }
}
