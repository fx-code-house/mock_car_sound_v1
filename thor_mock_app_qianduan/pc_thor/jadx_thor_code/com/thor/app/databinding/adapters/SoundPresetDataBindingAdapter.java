package com.thor.app.databinding.adapters;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.databinding.BindingAdapter;
import com.bumptech.glide.TransitionOptions;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.carsystems.thor.app.R;
import com.thor.app.glide.GlideApp;
import com.thor.app.utils.extensions.StringKt;
import com.thor.businessmodule.model.MainPresetPackage;
import com.thor.networkmodule.model.ModeType;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import timber.log.Timber;

/* compiled from: SoundPresetDataBindingAdapter.kt */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001a\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\bH\u0007J\u001a\u0010\t\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\b\u0010\n\u001a\u0004\u0018\u00010\u000bH\u0007J\u001a\u0010\f\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\r2\b\u0010\n\u001a\u0004\u0018\u00010\u000bH\u0007¨\u0006\u000e"}, d2 = {"Lcom/thor/app/databinding/adapters/SoundPresetDataBindingAdapter;", "", "()V", "mainPresetShowModeType", "", "view", "Landroid/widget/TextView;", "presetPackage", "Lcom/thor/businessmodule/model/MainPresetPackage;", "soundPresetModeType", "modeType", "Lcom/thor/networkmodule/model/ModeType;", "soundPresetShowCover", "Landroid/widget/ImageView;", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes2.dex */
public final class SoundPresetDataBindingAdapter {
    public static final SoundPresetDataBindingAdapter INSTANCE = new SoundPresetDataBindingAdapter();

    private SoundPresetDataBindingAdapter() {
    }

    @BindingAdapter({"soundPresetShowCover"})
    @JvmStatic
    public static final void soundPresetShowCover(ImageView view, ModeType modeType) {
        Intrinsics.checkNotNullParameter(view, "view");
        if (modeType == null) {
            Timber.INSTANCE.e("ModeType cannot be null", new Object[0]);
        } else {
            String image = modeType.getImage();
            GlideApp.with(view.getContext()).load(image != null ? StringKt.getFullFileUrl(image) : null).diskCacheStrategy(DiskCacheStrategy.ALL).transition((TransitionOptions<?, ? super Drawable>) DrawableTransitionOptions.withCrossFade()).into(view);
        }
    }

    @BindingAdapter({"soundPresetModeType"})
    @JvmStatic
    public static final void soundPresetModeType(TextView view, ModeType modeType) {
        Intrinsics.checkNotNullParameter(view, "view");
        if (modeType == null) {
            Timber.INSTANCE.e("ModeType cannot be null", new Object[0]);
            return;
        }
        int type = modeType.getType();
        if (type == 1) {
            view.setText(view.getResources().getString(R.string.text_city));
        } else if (type == 2) {
            view.setText(view.getResources().getString(R.string.text_sport));
        } else {
            if (type != 3) {
                return;
            }
            view.setText(view.getResources().getString(R.string.text_own));
        }
    }

    @BindingAdapter({"mainPresetShowModeType"})
    @JvmStatic
    public static final void mainPresetShowModeType(TextView view, MainPresetPackage presetPackage) {
        Intrinsics.checkNotNullParameter(view, "view");
        if (presetPackage == null) {
            Timber.INSTANCE.e("ModeType cannot be null", new Object[0]);
            return;
        }
        Short modeType = presetPackage.getModeType();
        Integer numValueOf = modeType != null ? Integer.valueOf(modeType.shortValue()) : null;
        if (numValueOf != null && numValueOf.intValue() == 1) {
            view.setText(view.getResources().getString(R.string.text_city));
            return;
        }
        if (numValueOf != null && numValueOf.intValue() == 2) {
            view.setText(view.getResources().getString(R.string.text_sport));
        } else if (numValueOf != null && numValueOf.intValue() == 3) {
            view.setText(view.getResources().getString(R.string.text_own));
        }
    }
}
