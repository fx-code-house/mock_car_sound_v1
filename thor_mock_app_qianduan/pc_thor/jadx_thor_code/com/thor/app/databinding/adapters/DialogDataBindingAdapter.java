package com.thor.app.databinding.adapters;

import android.widget.TextView;
import androidx.appcompat.widget.AppCompatButton;
import androidx.databinding.BindingAdapter;
import com.carsystems.thor.app.R;
import com.google.android.exoplayer2.source.rtsp.SessionDescription;
import com.thor.businessmodule.model.TypeDialog;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: DialogDataBindingAdapter.kt */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0007J\u0018\u0010\t\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\n2\u0006\u0010\u0007\u001a\u00020\bH\u0007J\u0018\u0010\u000b\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\n2\u0006\u0010\u0007\u001a\u00020\bH\u0007¨\u0006\f"}, d2 = {"Lcom/thor/app/databinding/adapters/DialogDataBindingAdapter;", "", "()V", "bindingDialogPositiveBtn", "", "view", "Landroidx/appcompat/widget/AppCompatButton;", SessionDescription.ATTR_TYPE, "Lcom/thor/businessmodule/model/TypeDialog;", "bindingDialogSecondary", "Landroid/widget/TextView;", "bindingTitleDialog", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes2.dex */
public final class DialogDataBindingAdapter {
    public static final DialogDataBindingAdapter INSTANCE = new DialogDataBindingAdapter();

    /* compiled from: DialogDataBindingAdapter.kt */
    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[TypeDialog.values().length];
            try {
                iArr[TypeDialog.GOTO_SITE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[TypeDialog.GOTO_TELEGRAM.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[TypeDialog.NOTIFICATION_DELETE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[TypeDialog.SHOW_DRIVE_SELECT_TIP.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[TypeDialog.SHOW_NATIVE_CONTROL_TIP.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr[TypeDialog.SHOW_IDLE_TONE_TIP.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                iArr[TypeDialog.FORMAT_FLASH_TIP.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    private DialogDataBindingAdapter() {
    }

    @BindingAdapter({"dialogTitle"})
    @JvmStatic
    public static final void bindingTitleDialog(TextView view, TypeDialog type) {
        Intrinsics.checkNotNullParameter(view, "view");
        Intrinsics.checkNotNullParameter(type, "type");
        int i = WhenMappings.$EnumSwitchMapping$0[type.ordinal()];
        if (i == 1) {
            view.setText(view.getContext().getString(R.string.text_visit_our_site));
            return;
        }
        if (i == 2) {
            view.setText(view.getContext().getString(R.string.text_visit_telegram));
        } else if (i == 3) {
            view.setText(view.getContext().getString(R.string.text_delete_notifications));
        } else {
            view.setVisibility(8);
        }
    }

    @BindingAdapter({"dialogPositiveBtn"})
    @JvmStatic
    public static final void bindingDialogPositiveBtn(AppCompatButton view, TypeDialog type) {
        Intrinsics.checkNotNullParameter(view, "view");
        Intrinsics.checkNotNullParameter(type, "type");
        int i = WhenMappings.$EnumSwitchMapping$0[type.ordinal()];
        if (i == 1) {
            view.setText(view.getContext().getString(R.string.text_visit));
            return;
        }
        if (i == 2) {
            view.setText(view.getContext().getString(R.string.text_telegram));
        } else if (i == 3) {
            view.setText(view.getContext().getString(R.string.text_confirm));
        } else {
            view.setVisibility(8);
        }
    }

    @BindingAdapter({"dialogSecondary"})
    @JvmStatic
    public static final void bindingDialogSecondary(TextView view, TypeDialog type) {
        Intrinsics.checkNotNullParameter(view, "view");
        Intrinsics.checkNotNullParameter(type, "type");
        int i = WhenMappings.$EnumSwitchMapping$0[type.ordinal()];
        if (i == 4) {
            view.setText(view.getContext().getString(R.string.text_drive_select_tip));
            return;
        }
        if (i == 5) {
            view.setText(view.getContext().getString(R.string.text_native_control_tip));
            return;
        }
        if (i == 6) {
            view.setText(view.getContext().getString(R.string.text_idle_tone_tip));
        } else if (i == 7) {
            view.setText(view.getContext().getString(R.string.text_format_flash_tip));
        } else {
            view.setVisibility(8);
        }
    }
}
