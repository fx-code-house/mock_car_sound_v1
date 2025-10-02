package com.thor.basemodule.extensions;

import android.content.res.Resources;
import kotlin.Metadata;

/* compiled from: Measure.kt */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\"\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00018F¢\u0006\u0006\u001a\u0004\b\u0002\u0010\u0003\"\u0015\u0010\u0004\u001a\u00020\u0001*\u00020\u00018F¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0003¨\u0006\u0006"}, d2 = {"dp", "", "getDp", "(I)I", "px", "getPx", "basemodule_release"}, k = 2, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class MeasureKt {
    public static final int getPx(int i) {
        return (int) (i / Resources.getSystem().getDisplayMetrics().density);
    }

    public static final int getDp(int i) {
        return (int) (i * Resources.getSystem().getDisplayMetrics().density);
    }
}
