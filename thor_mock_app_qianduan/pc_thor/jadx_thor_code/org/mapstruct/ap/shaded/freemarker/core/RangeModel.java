package org.mapstruct.ap.shaded.freemarker.core;

import java.io.Serializable;
import org.mapstruct.ap.shaded.freemarker.template.SimpleNumber;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModelException;
import org.mapstruct.ap.shaded.freemarker.template.TemplateSequenceModel;

/* loaded from: classes3.dex */
abstract class RangeModel implements TemplateSequenceModel, Serializable {
    private final int begin;

    abstract int getStep();

    abstract boolean isAffactedByStringSlicingBug();

    abstract boolean isRightAdaptive();

    abstract boolean isRightUnbounded();

    public RangeModel(int i) {
        this.begin = i;
    }

    final int getBegining() {
        return this.begin;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateSequenceModel
    public final TemplateModel get(int i) throws TemplateModelException {
        if (i < 0 || i >= size()) {
            throw new _TemplateModelException(new Object[]{"Range item index ", new Integer(i), " is out of bounds."});
        }
        long step = this.begin + (getStep() * i);
        return step <= 2147483647L ? new SimpleNumber((int) step) : new SimpleNumber(step);
    }
}
