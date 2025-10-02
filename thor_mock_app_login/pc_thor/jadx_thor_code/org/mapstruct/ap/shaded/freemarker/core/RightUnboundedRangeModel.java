package org.mapstruct.ap.shaded.freemarker.core;

/* loaded from: classes3.dex */
abstract class RightUnboundedRangeModel extends RangeModel {
    @Override // org.mapstruct.ap.shaded.freemarker.core.RangeModel
    final int getStep() {
        return 1;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.RangeModel
    final boolean isAffactedByStringSlicingBug() {
        return false;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.RangeModel
    final boolean isRightAdaptive() {
        return true;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.RangeModel
    final boolean isRightUnbounded() {
        return true;
    }

    RightUnboundedRangeModel(int i) {
        super(i);
    }
}
