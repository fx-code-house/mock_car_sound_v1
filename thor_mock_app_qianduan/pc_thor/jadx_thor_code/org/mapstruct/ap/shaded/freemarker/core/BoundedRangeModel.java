package org.mapstruct.ap.shaded.freemarker.core;

/* loaded from: classes3.dex */
final class BoundedRangeModel extends RangeModel {
    private final boolean affectedByStringSlicingBug;
    private final boolean rightAdaptive;
    private final int size;
    private final int step;

    @Override // org.mapstruct.ap.shaded.freemarker.core.RangeModel
    boolean isRightUnbounded() {
        return false;
    }

    BoundedRangeModel(int i, int i2, boolean z, boolean z2) {
        super(i);
        this.step = i <= i2 ? 1 : -1;
        this.size = Math.abs(i2 - i) + (z ? 1 : 0);
        this.rightAdaptive = z2;
        this.affectedByStringSlicingBug = z;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateSequenceModel
    public int size() {
        return this.size;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.RangeModel
    int getStep() {
        return this.step;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.RangeModel
    boolean isRightAdaptive() {
        return this.rightAdaptive;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.RangeModel
    boolean isAffactedByStringSlicingBug() {
        return this.affectedByStringSlicingBug;
    }
}
