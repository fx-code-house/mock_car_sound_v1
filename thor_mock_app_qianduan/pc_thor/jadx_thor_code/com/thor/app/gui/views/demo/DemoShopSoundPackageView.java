package com.thor.app.gui.views.demo;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import androidx.cardview.widget.CardView;
import com.carsystems.thor.app.R;
import com.carsystems.thor.app.databinding.ViewDemoShopSoundPackageBinding;
import com.thor.app.gui.activities.demo.DemoSoundPackageDescriptionActivity;
import com.thor.networkmodule.model.shop.ShopSoundPackage;
import java.lang.ref.WeakReference;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import timber.log.Timber;

/* compiled from: DemoShopSoundPackageView.kt */
@Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u00012\u00020\u0002B\u000f\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005B\u0019\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\u0002\u0010\bB!\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\u0006\u0010\t\u001a\u00020\n¢\u0006\u0002\u0010\u000bJ\u0012\u0010\u0010\u001a\u00020\u00112\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007H\u0002J\u0012\u0010\u0012\u001a\u00020\u00112\b\u0010\u0013\u001a\u0004\u0018\u00010\u0014H\u0016J\b\u0010\u0015\u001a\u00020\u0011H\u0002J\u0016\u0010\u0016\u001a\u00020\u00112\u0006\u0010\u0017\u001a\u00020\n2\u0006\u0010\u0018\u001a\u00020\nJ\u001e\u0010\u0016\u001a\u00020\u00112\u0006\u0010\u0017\u001a\u00020\n2\u0006\u0010\u0018\u001a\u00020\n2\u0006\u0010\u0019\u001a\u00020\nJ\u000e\u0010\u001a\u001a\u00020\u00112\u0006\u0010\u001b\u001a\u00020\nJ\u0010\u0010\u001c\u001a\u00020\u00112\b\u0010\u001d\u001a\u0004\u0018\u00010\u001eR\u000e\u0010\f\u001a\u00020\rX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u001f"}, d2 = {"Lcom/thor/app/gui/views/demo/DemoShopSoundPackageView;", "Landroidx/cardview/widget/CardView;", "Landroid/view/View$OnClickListener;", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "attrs", "Landroid/util/AttributeSet;", "(Landroid/content/Context;Landroid/util/AttributeSet;)V", "defStyleAttr", "", "(Landroid/content/Context;Landroid/util/AttributeSet;I)V", "binding", "Lcom/carsystems/thor/app/databinding/ViewDemoShopSoundPackageBinding;", "mShiftOfPosition", "", "initView", "", "onClick", "v", "Landroid/view/View;", "onClickMainLayout", "onNestedScrollChanged", "scrollY", "oldScrollY", "height", "setShiftOfPosition", "fullHeight", "setShopSoundPackage", "soundPackage", "Lcom/thor/networkmodule/model/shop/ShopSoundPackage;", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class DemoShopSoundPackageView extends CardView implements View.OnClickListener {
    private ViewDemoShopSoundPackageBinding binding;
    private float mShiftOfPosition;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public DemoShopSoundPackageView(Context context) {
        this(context, null);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public DemoShopSoundPackageView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DemoShopSoundPackageView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        Intrinsics.checkNotNullParameter(context, "context");
        initView(attributeSet);
    }

    private final void initView(AttributeSet attrs) {
        LayoutInflater layoutInflater = (LayoutInflater) new WeakReference(LayoutInflater.from(getContext())).get();
        Intrinsics.checkNotNull(layoutInflater);
        ViewDemoShopSoundPackageBinding viewDemoShopSoundPackageBindingInflate = ViewDemoShopSoundPackageBinding.inflate(layoutInflater, this, true);
        Intrinsics.checkNotNullExpressionValue(viewDemoShopSoundPackageBindingInflate, "inflate(layoutInflater!!, this, true)");
        this.binding = viewDemoShopSoundPackageBindingInflate;
        if (viewDemoShopSoundPackageBindingInflate == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            viewDemoShopSoundPackageBindingInflate = null;
        }
        viewDemoShopSoundPackageBindingInflate.mainLayout.setOnClickListener(this);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v) {
        Integer numValueOf = v != null ? Integer.valueOf(v.getId()) : null;
        if (numValueOf != null && numValueOf.intValue() == R.id.main_layout) {
            onClickMainLayout();
        }
    }

    public final void setShopSoundPackage(ShopSoundPackage soundPackage) {
        ViewDemoShopSoundPackageBinding viewDemoShopSoundPackageBinding = this.binding;
        if (viewDemoShopSoundPackageBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            viewDemoShopSoundPackageBinding = null;
        }
        viewDemoShopSoundPackageBinding.setSoundPackage(soundPackage);
    }

    public final void onNestedScrollChanged(int scrollY, int oldScrollY) {
        ViewDemoShopSoundPackageBinding viewDemoShopSoundPackageBinding = this.binding;
        if (viewDemoShopSoundPackageBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            viewDemoShopSoundPackageBinding = null;
        }
        ImageView imageView = viewDemoShopSoundPackageBinding.imageViewCover;
        int i = scrollY - oldScrollY;
        float y = imageView.getY();
        float f = (i * this.mShiftOfPosition) + y;
        if (i > 0 && y < 0.0f) {
            imageView.setY(f);
        } else {
            if (i >= 0 || y <= (-(imageView.getHeight() - getHeight()))) {
                return;
            }
            imageView.setY(f);
        }
    }

    public final void onNestedScrollChanged(int scrollY, int oldScrollY, int height) {
        setShiftOfPosition(height);
        onNestedScrollChanged(scrollY, oldScrollY);
    }

    public final void setShiftOfPosition(int fullHeight) {
        ViewDemoShopSoundPackageBinding viewDemoShopSoundPackageBinding = this.binding;
        if (viewDemoShopSoundPackageBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            viewDemoShopSoundPackageBinding = null;
        }
        this.mShiftOfPosition = (viewDemoShopSoundPackageBinding.mainLayout.getHeight() / fullHeight) / 3.0f;
    }

    private final void onClickMainLayout() {
        ViewDemoShopSoundPackageBinding viewDemoShopSoundPackageBinding = this.binding;
        ViewDemoShopSoundPackageBinding viewDemoShopSoundPackageBinding2 = null;
        if (viewDemoShopSoundPackageBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            viewDemoShopSoundPackageBinding = null;
        }
        if (viewDemoShopSoundPackageBinding.getSoundPackage() == null) {
            return;
        }
        Timber.INSTANCE.i("Clicked SoundPackage", new Object[0]);
        Intent intent = new Intent(getContext(), (Class<?>) DemoSoundPackageDescriptionActivity.class);
        String bundle_name = ShopSoundPackage.INSTANCE.getBUNDLE_NAME();
        ViewDemoShopSoundPackageBinding viewDemoShopSoundPackageBinding3 = this.binding;
        if (viewDemoShopSoundPackageBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            viewDemoShopSoundPackageBinding2 = viewDemoShopSoundPackageBinding3;
        }
        intent.putExtra(bundle_name, viewDemoShopSoundPackageBinding2.getSoundPackage());
        getContext().startActivity(intent);
    }
}
