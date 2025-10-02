package org.mapstruct.ap.shaded.freemarker.template;

import java.io.Serializable;

/* loaded from: classes3.dex */
public final class SimpleScalar implements TemplateScalarModel, Serializable {
    private final String value;

    public SimpleScalar(String str) {
        this.value = str;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateScalarModel
    public String getAsString() {
        String str = this.value;
        return str == null ? "" : str;
    }

    public String toString() {
        return this.value;
    }
}
