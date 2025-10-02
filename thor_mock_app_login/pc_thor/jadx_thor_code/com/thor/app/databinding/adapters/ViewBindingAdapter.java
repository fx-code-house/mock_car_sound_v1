package com.thor.app.databinding.adapters;

import android.view.View;
import androidx.databinding.BindingAdapter;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.thor.app.gui.views.RssiSignalView;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ViewBindingAdapter.kt */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0007J(\u0010\t\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\b2\u0006\u0010\f\u001a\u00020\b2\u0006\u0010\r\u001a\u00020\bH\u0007J\u0018\u0010\u000e\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\n2\u0006\u0010\u000f\u001a\u00020\bH\u0007J\u0018\u0010\u0010\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0007¨\u0006\u0014"}, d2 = {"Lcom/thor/app/databinding/adapters/ViewBindingAdapter;", "", "()V", "bindIsChecked", "", "view", "Lcom/google/android/material/switchmaterial/SwitchMaterial;", "checked", "", "bindIsNoInfoVisible", "Landroid/view/View;", "nativeControl", "driveSelect", "connectionPoint", "bindIsVisible", "visible", "setNewLevel", "Lcom/thor/app/gui/views/RssiSignalView;", FirebaseAnalytics.Param.LEVEL, "", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes2.dex */
public final class ViewBindingAdapter {
    public static final ViewBindingAdapter INSTANCE = new ViewBindingAdapter();

    private ViewBindingAdapter() {
    }

    @BindingAdapter({"isVisible"})
    @JvmStatic
    public static final void bindIsVisible(View view, boolean visible) {
        Intrinsics.checkNotNullParameter(view, "view");
        view.setVisibility(visible ? 0 : 8);
    }

    @BindingAdapter({"nativeControl", "driveSelect", "connectionPoint"})
    @JvmStatic
    public static final void bindIsNoInfoVisible(View view, boolean nativeControl, boolean driveSelect, boolean connectionPoint) {
        Intrinsics.checkNotNullParameter(view, "view");
        view.setVisibility((nativeControl && driveSelect && connectionPoint) ? 0 : 8);
    }

    @BindingAdapter({"setNewLevel"})
    @JvmStatic
    public static final void setNewLevel(RssiSignalView view, int level) {
        Intrinsics.checkNotNullParameter(view, "view");
        view.setBackgroundTintByLevel(level);
    }

    @BindingAdapter({"isChecked"})
    @JvmStatic
    public static final void bindIsChecked(SwitchMaterial view, boolean checked) {
        Intrinsics.checkNotNullParameter(view, "view");
        view.setChecked(checked);
    }
}
