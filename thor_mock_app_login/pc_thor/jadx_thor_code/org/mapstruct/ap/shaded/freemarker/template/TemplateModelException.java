package org.mapstruct.ap.shaded.freemarker.template;

import org.mapstruct.ap.shaded.freemarker.core.Environment;
import org.mapstruct.ap.shaded.freemarker.core._ErrorDescriptionBuilder;

/* loaded from: classes3.dex */
public class TemplateModelException extends TemplateException {
    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public TemplateModelException() {
        this((String) null, (Exception) null);
    }

    public TemplateModelException(String str) {
        this(str, (Exception) null);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public TemplateModelException(Exception exc) {
        this((String) null, exc);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public TemplateModelException(Throwable th) {
        this((String) null, th);
    }

    public TemplateModelException(String str, Exception exc) {
        super(str, exc, (Environment) null);
    }

    public TemplateModelException(String str, Throwable th) {
        super(str, th, (Environment) null);
    }

    protected TemplateModelException(Throwable th, Environment environment, String str, boolean z) {
        super(str, th, environment);
    }

    protected TemplateModelException(Throwable th, Environment environment, _ErrorDescriptionBuilder _errordescriptionbuilder, boolean z) {
        super(th, environment, null, _errordescriptionbuilder);
    }
}
