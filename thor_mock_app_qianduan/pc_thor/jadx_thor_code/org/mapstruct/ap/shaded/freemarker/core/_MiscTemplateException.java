package org.mapstruct.ap.shaded.freemarker.core;

import org.mapstruct.ap.shaded.freemarker.template.TemplateException;

/* loaded from: classes3.dex */
public class _MiscTemplateException extends TemplateException {
    public _MiscTemplateException(String str) {
        super(str, (Environment) null);
    }

    public _MiscTemplateException(Environment environment, String str) {
        super(str, environment);
    }

    public _MiscTemplateException(Throwable th, String str) {
        this(th, (Environment) null, str);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public _MiscTemplateException(Throwable th, Environment environment) {
        this(th, environment, (String) null);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public _MiscTemplateException(Throwable th) {
        this(th, (Environment) null, (String) null);
    }

    public _MiscTemplateException(Throwable th, Environment environment, String str) {
        super(str, th, environment);
    }

    public _MiscTemplateException(_ErrorDescriptionBuilder _errordescriptionbuilder) {
        this((Environment) null, _errordescriptionbuilder);
    }

    public _MiscTemplateException(Environment environment, _ErrorDescriptionBuilder _errordescriptionbuilder) {
        this((Throwable) null, environment, _errordescriptionbuilder);
    }

    public _MiscTemplateException(Throwable th, Environment environment, _ErrorDescriptionBuilder _errordescriptionbuilder) {
        super(th, environment, null, _errordescriptionbuilder);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public _MiscTemplateException(Object[] objArr) {
        this((Environment) null, objArr);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public _MiscTemplateException(Environment environment, Object[] objArr) {
        this((Throwable) null, environment, objArr);
    }

    public _MiscTemplateException(Throwable th, Object[] objArr) {
        this(th, (Environment) null, objArr);
    }

    public _MiscTemplateException(Throwable th, Environment environment, Object[] objArr) {
        super(th, environment, null, new _ErrorDescriptionBuilder(objArr));
    }

    public _MiscTemplateException(Expression expression, Object[] objArr) {
        this(expression, (Environment) null, objArr);
    }

    public _MiscTemplateException(Expression expression, Environment environment, Object[] objArr) {
        this(expression, (Throwable) null, environment, objArr);
    }

    public _MiscTemplateException(Expression expression, Throwable th, Environment environment, Object[] objArr) {
        super(th, environment, expression, new _ErrorDescriptionBuilder(objArr).blame(expression));
    }

    public _MiscTemplateException(Expression expression, String str) {
        this(expression, (Environment) null, str);
    }

    public _MiscTemplateException(Expression expression, Environment environment, String str) {
        this(expression, (Throwable) null, environment, str);
    }

    public _MiscTemplateException(Expression expression, Throwable th, Environment environment, String str) {
        super(th, environment, expression, new _ErrorDescriptionBuilder(str).blame(expression));
    }
}
