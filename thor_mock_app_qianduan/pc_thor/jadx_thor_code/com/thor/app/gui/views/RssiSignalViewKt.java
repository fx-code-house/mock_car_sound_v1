package com.thor.app.gui.views;

import android.content.Context;
import android.util.TypedValue;
import com.carsystems.thor.app.R;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: RssiSignalView.kt */
@Metadata(d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u000e\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003\u001a\u000e\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003¨\u0006\u0005"}, d2 = {"getThemeAccentColor", "", "context", "Landroid/content/Context;", "getThemeSecondaryColor", "thor-1.8.7_release"}, k = 2, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class RssiSignalViewKt {
    public static final int getThemeAccentColor(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.colorAccent, typedValue, true);
        return typedValue.data;
    }

    public static final int getThemeSecondaryColor(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.colorSecondary, typedValue, true);
        return typedValue.data;
    }
}
