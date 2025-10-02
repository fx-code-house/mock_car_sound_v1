package com.thor.app.databinding.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.widget.ImageView;
import androidx.core.content.ContextCompat;
import androidx.databinding.BindingAdapter;
import com.bumptech.glide.TransitionOptions;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.carsystems.thor.app.R;
import com.thor.app.glide.GlideApp;
import com.thor.app.utils.extensions.StringKt;
import com.thor.basemodule.extensions.ContextKt;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import timber.log.Timber;

/* compiled from: ImageViewBindingAdapter.kt */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001a\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\u0004H\u0007J\u001a\u0010\n\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\b2\b\u0010\f\u001a\u0004\u0018\u00010\u0004H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000¨\u0006\r"}, d2 = {"Lcom/thor/app/databinding/adapters/ImageViewBindingAdapter;", "", "()V", "IDENTIFIER_DRAWABLE", "", "bindLoadImage", "", "imageView", "Landroid/widget/ImageView;", "relativeImageUrl", "bindLoadImageFromFiles", "view", "soundFileName", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes2.dex */
public final class ImageViewBindingAdapter {
    private static final String IDENTIFIER_DRAWABLE = "drawable";
    public static final ImageViewBindingAdapter INSTANCE = new ImageViewBindingAdapter();

    private ImageViewBindingAdapter() {
    }

    @BindingAdapter({"loadImage"})
    @JvmStatic
    public static final void bindLoadImage(ImageView imageView, String relativeImageUrl) {
        Intrinsics.checkNotNullParameter(imageView, "imageView");
        if (relativeImageUrl == null) {
            return;
        }
        GlideApp.with(imageView.getContext()).load(StringKt.getFullFileUrl(relativeImageUrl)).diskCacheStrategy(DiskCacheStrategy.ALL).transition((TransitionOptions<?, ? super Drawable>) DrawableTransitionOptions.withCrossFade()).into(imageView);
    }

    @BindingAdapter({"loadImageFromFiles"})
    @JvmStatic
    public static final void bindLoadImageFromFiles(ImageView view, String soundFileName) {
        Intrinsics.checkNotNullParameter(view, "view");
        if (soundFileName == null) {
            Timber.INSTANCE.e("DemoSoundPackage cannot be null", new Object[0]);
            return;
        }
        if (TextUtils.isEmpty(soundFileName)) {
            return;
        }
        int identifier = view.getResources().getIdentifier(soundFileName, IDENTIFIER_DRAWABLE, view.getContext().getPackageName());
        Context context = view.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "view.context");
        if (ContextKt.isCarUIMode(context)) {
            view.setBackground(ContextCompat.getDrawable(view.getContext(), R.drawable.bg_shape_black));
        } else {
            GlideApp.with(view.getContext()).load(Integer.valueOf(identifier)).dontTransform().into(view);
        }
    }
}
