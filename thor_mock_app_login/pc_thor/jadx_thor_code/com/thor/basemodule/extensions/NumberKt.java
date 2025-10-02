package com.thor.basemodule.extensions;

import kotlin.Metadata;
import kotlin.math.MathKt;

/* compiled from: Number.kt */
@Metadata(d1 = {"\u0000\u0018\n\u0000\n\u0002\u0010\u0006\n\u0000\n\u0002\u0010\b\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u000b\n\u0000\u001a\u0012\u0010\u0000\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003\u001a\u0012\u0010\u0000\u001a\u00020\u0004*\u00020\u00042\u0006\u0010\u0002\u001a\u00020\u0003\u001a\n\u0010\u0005\u001a\u00020\u0003*\u00020\u0006¨\u0006\u0007"}, d2 = {"roundTo", "", "numFractionDigits", "", "", "toInt", "", "basemodule_release"}, k = 2, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class NumberKt {
    public static final int toInt(boolean z) {
        return z ? 1 : 0;
    }

    public static final double roundTo(double d, int i) {
        return MathKt.roundToInt(d * r0) / Math.pow(10.0d, i);
    }

    public static final float roundTo(float f, int i) {
        return MathKt.roundToInt(f * r5) / ((float) Math.pow(10.0f, i));
    }
}
