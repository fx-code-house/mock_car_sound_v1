package com.google.android.exoplayer2;

import android.os.Bundle;
import com.google.android.exoplayer2.Bundleable;
import com.google.android.exoplayer2.util.Assertions;
import com.google.common.base.Objects;

/* loaded from: classes.dex */
public final class PercentageRating extends Rating {
    public static final Bundleable.Creator<PercentageRating> CREATOR = new Bundleable.Creator() { // from class: com.google.android.exoplayer2.PercentageRating$$ExternalSyntheticLambda0
        @Override // com.google.android.exoplayer2.Bundleable.Creator
        public final Bundleable fromBundle(Bundle bundle) {
            return PercentageRating.fromBundle(bundle);
        }
    };
    private static final int FIELD_PERCENT = 1;
    private static final int TYPE = 1;
    private final float percent;

    public PercentageRating() {
        this.percent = -1.0f;
    }

    public PercentageRating(float percent) {
        Assertions.checkArgument(percent >= 0.0f && percent <= 100.0f, "percent must be in the range of [0, 100]");
        this.percent = percent;
    }

    @Override // com.google.android.exoplayer2.Rating
    public boolean isRated() {
        return this.percent != -1.0f;
    }

    public float getPercent() {
        return this.percent;
    }

    public int hashCode() {
        return Objects.hashCode(Float.valueOf(this.percent));
    }

    public boolean equals(Object obj) {
        return (obj instanceof PercentageRating) && this.percent == ((PercentageRating) obj).percent;
    }

    @Override // com.google.android.exoplayer2.Bundleable
    public Bundle toBundle() {
        Bundle bundle = new Bundle();
        bundle.putInt(keyForField(0), 1);
        bundle.putFloat(keyForField(1), this.percent);
        return bundle;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static PercentageRating fromBundle(Bundle bundle) {
        Assertions.checkArgument(bundle.getInt(keyForField(0), -1) == 1);
        float f = bundle.getFloat(keyForField(1), -1.0f);
        return f == -1.0f ? new PercentageRating() : new PercentageRating(f);
    }

    private static String keyForField(int field) {
        return Integer.toString(field, 36);
    }
}
