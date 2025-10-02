package org.mapstruct.ap.shaded.freemarker.core;

import org.mapstruct.ap.shaded.freemarker.template.TemplateModel;
import org.mapstruct.ap.shaded.freemarker.template.utility.ClassUtil;

/* loaded from: classes3.dex */
public class _DelayedFTLTypeDescription extends _DelayedConversionToString {
    public _DelayedFTLTypeDescription(TemplateModel templateModel) {
        super(templateModel);
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core._DelayedConversionToString
    protected String doConversion(Object obj) {
        return ClassUtil.getFTLTypeDescription((TemplateModel) obj);
    }
}
