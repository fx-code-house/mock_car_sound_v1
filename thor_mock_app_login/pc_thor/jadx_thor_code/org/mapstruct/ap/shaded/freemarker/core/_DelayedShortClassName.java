package org.mapstruct.ap.shaded.freemarker.core;

import org.mapstruct.ap.shaded.freemarker.template.utility.ClassUtil;

/* loaded from: classes3.dex */
public class _DelayedShortClassName extends _DelayedConversionToString {
    public _DelayedShortClassName(Class cls) {
        super(cls);
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core._DelayedConversionToString
    protected String doConversion(Object obj) {
        return ClassUtil.getShortClassName((Class) obj, true);
    }
}
