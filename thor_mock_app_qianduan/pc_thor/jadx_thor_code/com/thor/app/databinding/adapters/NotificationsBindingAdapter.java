package com.thor.app.databinding.adapters;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.databinding.BindingAdapter;
import com.bumptech.glide.TransitionOptions;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.carsystems.thor.app.R;
import com.google.android.exoplayer2.source.rtsp.SessionDescription;
import com.thor.app.glide.GlideApp;
import com.thor.networkmodule.model.notifications.NotificationType;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: NotificationsBindingAdapter.kt */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0007J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\t2\u0006\u0010\u0007\u001a\u00020\bH\u0007J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\n2\u0006\u0010\u0007\u001a\u00020\bH\u0007J\u0018\u0010\u000b\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\f2\u0006\u0010\u0007\u001a\u00020\bH\u0007J\u001a\u0010\r\u001a\u00020\u00042\u0006\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u0007¨\u0006\u0012"}, d2 = {"Lcom/thor/app/databinding/adapters/NotificationsBindingAdapter;", "", "()V", "bindBtnDrawable", "", "view", "Landroid/widget/LinearLayout;", SessionDescription.ATTR_TYPE, "Lcom/thor/networkmodule/model/notifications/NotificationType;", "Landroidx/appcompat/widget/AppCompatImageButton;", "Landroidx/appcompat/widget/AppCompatTextView;", "bindBtnText", "Landroid/widget/TextView;", "bindLoadImage", "imageView", "Landroid/widget/ImageView;", "relativeImageUrl", "", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes2.dex */
public final class NotificationsBindingAdapter {
    public static final NotificationsBindingAdapter INSTANCE = new NotificationsBindingAdapter();

    /* compiled from: NotificationsBindingAdapter.kt */
    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[NotificationType.values().length];
            try {
                iArr[NotificationType.TYPE_INFORMATION.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[NotificationType.TYPE_FIRMWARE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[NotificationType.TYPE_SOUND_PACKAGE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    private NotificationsBindingAdapter() {
    }

    @BindingAdapter({"btnDrawable"})
    @JvmStatic
    public static final void bindBtnDrawable(AppCompatImageButton view, NotificationType type) {
        Intrinsics.checkNotNullParameter(view, "view");
        Intrinsics.checkNotNullParameter(type, "type");
        int i = WhenMappings.$EnumSwitchMapping$0[type.ordinal()];
        if (i == 1) {
            view.setBackground(ContextCompat.getDrawable(view.getContext(), R.drawable.bg_round_black_btn));
            view.setImageDrawable(ContextCompat.getDrawable(view.getContext(), R.drawable.ic_arrow_right));
        } else if (i == 2) {
            view.setImageDrawable(ContextCompat.getDrawable(view.getContext(), R.drawable.ic_arrow_down));
        } else {
            if (i != 3) {
                return;
            }
            view.setImageDrawable(ContextCompat.getDrawable(view.getContext(), R.drawable.ic_arrow_circle));
        }
    }

    @BindingAdapter({"btnText"})
    @JvmStatic
    public static final void bindBtnText(TextView view, NotificationType type) {
        Intrinsics.checkNotNullParameter(view, "view");
        Intrinsics.checkNotNullParameter(type, "type");
        int i = WhenMappings.$EnumSwitchMapping$0[type.ordinal()];
        if (i == 1) {
            view.setText(view.getResources().getText(R.string.text_confirm));
        } else {
            if (i != 3) {
                return;
            }
            view.setText(view.getResources().getText(R.string.text_download));
        }
    }

    @BindingAdapter({"notificationBackground"})
    @JvmStatic
    public static final void bindBtnDrawable(LinearLayout view, NotificationType type) {
        Intrinsics.checkNotNullParameter(view, "view");
        Intrinsics.checkNotNullParameter(type, "type");
        if (WhenMappings.$EnumSwitchMapping$0[type.ordinal()] == 1) {
            view.setBackground(ContextCompat.getDrawable(view.getContext(), R.drawable.bg_read_notification_rounded));
        } else {
            view.setBackground(ContextCompat.getDrawable(view.getContext(), R.drawable.bg_normal_notification_rounded));
        }
    }

    @BindingAdapter({"notificationTextColor"})
    @JvmStatic
    public static final void bindBtnDrawable(AppCompatTextView view, NotificationType type) {
        Intrinsics.checkNotNullParameter(view, "view");
        Intrinsics.checkNotNullParameter(type, "type");
        if (WhenMappings.$EnumSwitchMapping$0[type.ordinal()] == 1) {
            view.setTextColor(ContextCompat.getColor(view.getContext(), R.color.colorBlack));
        } else {
            view.setTextColor(ContextCompat.getColor(view.getContext(), R.color.colorWhite));
        }
    }

    @BindingAdapter({"loadNotificationImage"})
    @JvmStatic
    public static final void bindLoadImage(ImageView imageView, String relativeImageUrl) {
        Intrinsics.checkNotNullParameter(imageView, "imageView");
        if (relativeImageUrl == null) {
            return;
        }
        if (relativeImageUrl.length() == 0) {
            imageView.setImageDrawable(imageView.getContext().getDrawable(R.drawable.ic_transformer));
        } else {
            GlideApp.with(imageView.getContext()).load(relativeImageUrl).diskCacheStrategy(DiskCacheStrategy.ALL).transition((TransitionOptions<?, ? super Drawable>) DrawableTransitionOptions.withCrossFade()).error(R.drawable.ic_transformer).placeholder(ViewCompat.MEASURED_STATE_MASK).into(imageView);
        }
    }
}
