package com.thor.app.databinding.adapters;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;
import androidx.core.content.ContextCompat;
import androidx.databinding.BindingAdapter;
import com.carsystems.thor.app.R;
import com.thor.app.glide.GlideApp;
import com.thor.basemodule.extensions.ContextKt;
import com.thor.businessmodule.model.DemoSoundPackage;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import timber.log.Timber;

/* compiled from: DemoSoundPackageDataBindingAdapter.kt */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001a\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\nH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u000b"}, d2 = {"Lcom/thor/app/databinding/adapters/DemoSoundPackageDataBindingAdapter;", "", "()V", "IDENTIFIER_DRAWABLE", "", "demoSoundPackageShowCover", "", "view", "Landroid/widget/ImageView;", "soundPackage", "Lcom/thor/businessmodule/model/DemoSoundPackage;", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes2.dex */
public final class DemoSoundPackageDataBindingAdapter {
    private static final String IDENTIFIER_DRAWABLE = "drawable";
    public static final DemoSoundPackageDataBindingAdapter INSTANCE = new DemoSoundPackageDataBindingAdapter();

    private DemoSoundPackageDataBindingAdapter() {
    }

    @BindingAdapter({"demoSoundPackageShowCover"})
    @JvmStatic
    public static final void demoSoundPackageShowCover(ImageView view, DemoSoundPackage soundPackage) {
        Intrinsics.checkNotNullParameter(view, "view");
        if (soundPackage == null) {
            Timber.INSTANCE.e("DemoSoundPackage cannot be null", new Object[0]);
            return;
        }
        if (TextUtils.isEmpty(soundPackage.getDrawableName())) {
            return;
        }
        int identifier = view.getResources().getIdentifier(soundPackage.getDrawableName(), IDENTIFIER_DRAWABLE, view.getContext().getPackageName());
        Context context = view.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "view.context");
        if (ContextKt.isCarUIMode(context)) {
            view.setBackground(ContextCompat.getDrawable(view.getContext(), R.drawable.bg_shape_black));
        } else {
            GlideApp.with(view.getContext()).load(Integer.valueOf(identifier)).dontTransform().into(view);
        }
    }
}
