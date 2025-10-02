package org.mapstruct.ap.shaded.freemarker.core;

import org.mapstruct.ap.shaded.freemarker.template.TemplateModelException;

/* loaded from: classes3.dex */
final class NonListableRightUnboundedRangeModel extends RightUnboundedRangeModel {
    @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateSequenceModel
    public int size() throws TemplateModelException {
        return 0;
    }

    NonListableRightUnboundedRangeModel(int i) {
        super(i);
    }
}
