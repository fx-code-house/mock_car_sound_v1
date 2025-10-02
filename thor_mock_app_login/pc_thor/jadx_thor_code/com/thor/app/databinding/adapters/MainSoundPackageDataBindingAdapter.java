package com.thor.app.databinding.adapters;

import android.graphics.PorterDuff;
import android.os.Handler;
import android.text.TextUtils;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.BindingAdapter;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.carsystems.thor.app.R;
import com.google.android.gms.measurement.api.AppMeasurementSdk;
import com.thor.app.bus.events.CheckPlaceholderHintEvent;
import com.thor.app.glide.GlideApp;
import com.thor.app.utils.extensions.StringKt;
import com.thor.businessmodule.bluetooth.model.other.DriveMode;
import com.thor.businessmodule.model.MainPresetPackage;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import org.greenrobot.eventbus.EventBus;
import timber.log.Timber;

/* compiled from: MainSoundPackageDataBindingAdapter.kt */
@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001a\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\nH\u0007J\u001a\u0010\u000b\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\nH\u0007J\u001a\u0010\f\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\nH\u0007J\u001a\u0010\f\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u000f2\b\u0010\u000e\u001a\u0004\u0018\u00010\nH\u0007J$\u0010\u0010\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00112\b\u0010\u0012\u001a\u0004\u0018\u00010\u00042\b\u0010\u0013\u001a\u0004\u0018\u00010\u0004H\u0007J \u0010\u0014\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00112\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0016H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u0018"}, d2 = {"Lcom/thor/app/databinding/adapters/MainSoundPackageDataBindingAdapter;", "", "()V", "IDENTIFIER_DRAWABLE", "", "mainSoundPackageShowCover", "", "view", "Landroid/widget/ImageView;", "soundPackage", "Lcom/thor/businessmodule/model/MainPresetPackage;", "mainSoundPackageShowDrawable", "setColorByTypePreset", "Landroid/widget/CheckedTextView;", "presetPackage", "Landroidx/appcompat/widget/AppCompatSeekBar;", "showDriveSelectMode", "Landroid/widget/TextView;", AppMeasurementSdk.ConditionalUserProperty.NAME, NotificationCompat.CATEGORY_STATUS, "showPlaceholder", "isBleConnected", "", "isInstalledPresets", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes2.dex */
public final class MainSoundPackageDataBindingAdapter {
    private static final String IDENTIFIER_DRAWABLE = "drawable";
    public static final MainSoundPackageDataBindingAdapter INSTANCE = new MainSoundPackageDataBindingAdapter();

    private MainSoundPackageDataBindingAdapter() {
    }

    @BindingAdapter({"mainSoundPackageShowCover"})
    @JvmStatic
    public static final void mainSoundPackageShowCover(ImageView view, MainPresetPackage soundPackage) {
        Intrinsics.checkNotNullParameter(view, "view");
        if (soundPackage == null) {
            Timber.INSTANCE.e("MainSoundPackage cannot be null", new Object[0]);
        } else {
            String imageUrl = soundPackage.getImageUrl();
            GlideApp.with(view.getContext()).load(imageUrl != null ? StringKt.getFullFileUrl(imageUrl) : null).dontTransform().diskCacheStrategy(DiskCacheStrategy.ALL).into(view);
        }
    }

    @BindingAdapter({"mainSoundPackageShowDrawable"})
    @JvmStatic
    public static final void mainSoundPackageShowDrawable(ImageView view, MainPresetPackage soundPackage) {
        Intrinsics.checkNotNullParameter(view, "view");
        if (soundPackage == null) {
            Timber.INSTANCE.e("MainSoundPackage cannot be null", new Object[0]);
        } else {
            if (TextUtils.isEmpty(soundPackage.getImageUrl())) {
                return;
            }
            view.setImageResource(view.getResources().getIdentifier(soundPackage.getImageUrl(), IDENTIFIER_DRAWABLE, view.getContext().getPackageName()));
        }
    }

    @BindingAdapter({"setColorByTypePreset"})
    @JvmStatic
    public static final void setColorByTypePreset(CheckedTextView view, MainPresetPackage presetPackage) {
        Intrinsics.checkNotNullParameter(view, "view");
        if (presetPackage == null || presetPackage.isOwnType()) {
            return;
        }
        view.setTextColor(ContextCompat.getColor(view.getContext(), R.color.colorOtherModeType));
        switch (view.getId()) {
            case R.id.text_view_type_city /* 2131362544 */:
                Short modeType = presetPackage.getModeType();
                if (modeType != null && modeType.shortValue() == 1) {
                    view.setTextColor(ContextCompat.getColor(view.getContext(), R.color.colorWhite));
                }
                view.setBackgroundResource(R.drawable.bg_selector_setting_type_city_other);
                break;
            case R.id.text_view_type_own /* 2131362545 */:
                view.setBackgroundResource(R.drawable.bg_selector_setting_type_own_other);
                break;
            case R.id.text_view_type_sport /* 2131362546 */:
                Short modeType2 = presetPackage.getModeType();
                if (modeType2 != null && modeType2.shortValue() == 2) {
                    view.setTextColor(ContextCompat.getColor(view.getContext(), R.color.colorWhite));
                }
                view.setBackgroundResource(R.drawable.bg_selector_setting_type_sport_other);
                break;
        }
    }

    @BindingAdapter({"setColorByTypePreset"})
    @JvmStatic
    public static final void setColorByTypePreset(AppCompatSeekBar view, MainPresetPackage presetPackage) {
        Intrinsics.checkNotNullParameter(view, "view");
        if (presetPackage == null || presetPackage.isOwnType()) {
            return;
        }
        view.getProgressDrawable().setColorFilter(view.getContext().getResources().getColor(R.color.colorOtherModeType), PorterDuff.Mode.SRC_IN);
    }

    @BindingAdapter({"isBleConnected", "isInstalledPresets"})
    @JvmStatic
    public static final void showPlaceholder(TextView view, final boolean isBleConnected, final boolean isInstalledPresets) {
        Intrinsics.checkNotNullParameter(view, "view");
        new Handler().postDelayed(new Runnable() { // from class: com.thor.app.databinding.adapters.MainSoundPackageDataBindingAdapter$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                MainSoundPackageDataBindingAdapter.showPlaceholder$lambda$2(isBleConnected, isInstalledPresets);
            }
        }, 5000L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showPlaceholder$lambda$2(boolean z, boolean z2) {
        Timber.INSTANCE.i("showPlaceholder.postDelayed: isConnected - %b, isPresets - %b", Boolean.valueOf(z), Boolean.valueOf(z2));
        EventBus.getDefault().post(new CheckPlaceholderHintEvent());
    }

    @BindingAdapter({"driveSelectName", "driveSelectStatus"})
    @JvmStatic
    public static final void showDriveSelectMode(TextView view, String name, String status) {
        Intrinsics.checkNotNullParameter(view, "view");
        if (name != null) {
            String str = name;
            if (!(str.length() == 0)) {
                view.setVisibility(0);
                view.setText(str);
                if (status != null) {
                    if (Intrinsics.areEqual(status, DriveMode.DRIVE_MODE.PLAY_CURRENT_PRESET.getValue())) {
                        view.setAlpha(0.4f);
                        if ((view.getPaintFlags() & 16) > 0) {
                            view.setPaintFlags(view.getPaintFlags() ^ 16);
                            return;
                        }
                        return;
                    }
                    if (Intrinsics.areEqual(status, DriveMode.DRIVE_MODE.PLAY_PRESET_AS_SELECTED_MODE.getValue())) {
                        view.setAlpha(1.0f);
                        if ((view.getPaintFlags() & 16) > 0) {
                            view.setPaintFlags(view.getPaintFlags() ^ 16);
                            return;
                        }
                        return;
                    }
                    if (Intrinsics.areEqual(status, DriveMode.DRIVE_MODE.MUTE.getValue())) {
                        view.setAlpha(0.4f);
                        view.setPaintFlags(view.getPaintFlags() | 16);
                        return;
                    } else {
                        Intrinsics.areEqual(status, DriveMode.DRIVE_MODE.RESERVE.getValue());
                        return;
                    }
                }
                return;
            }
        }
        view.setVisibility(8);
    }
}
