package org.mapstruct.ap.shaded.freemarker.core;

import java.util.List;
import org.mapstruct.ap.shaded.freemarker.ext.beans.BeansWrapper;
import org.mapstruct.ap.shaded.freemarker.template.ObjectWrapper;
import org.mapstruct.ap.shaded.freemarker.template.Template;
import org.mapstruct.ap.shaded.freemarker.template.TemplateException;
import org.mapstruct.ap.shaded.freemarker.template.TemplateMethodModelEx;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModelException;

/* loaded from: classes3.dex */
class NewBI extends BuiltIn {
    static final Class BEAN_MODEL_CLASS;
    static Class JYTHON_MODEL_CLASS;
    static /* synthetic */ Class class$freemarker$ext$beans$BeanModel;
    static /* synthetic */ Class class$freemarker$template$TemplateModel;

    NewBI() {
    }

    static {
        Class clsClass$ = class$freemarker$ext$beans$BeanModel;
        if (clsClass$ == null) {
            clsClass$ = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.BeanModel");
            class$freemarker$ext$beans$BeanModel = clsClass$;
        }
        BEAN_MODEL_CLASS = clsClass$;
        try {
            JYTHON_MODEL_CLASS = Class.forName("org.mapstruct.ap.shaded.freemarker.ext.jython.JythonModel");
        } catch (Throwable unused) {
            JYTHON_MODEL_CLASS = null;
        }
    }

    static /* synthetic */ Class class$(String str) throws Throwable {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError().initCause(e);
        }
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.Expression
    TemplateModel _eval(Environment environment) throws TemplateException {
        return new ConstructorFunction(this.target.evalAndCoerceToString(environment), environment, this.target.getTemplate());
    }

    class ConstructorFunction implements TemplateMethodModelEx {
        private final Class cl;
        private final Environment env;

        public ConstructorFunction(String str, Environment environment, Template template) throws Throwable {
            Class clsClass$;
            this.env = environment;
            Class<?> clsResolve = environment.getNewBuiltinClassResolver().resolve(str, environment, template);
            this.cl = clsResolve;
            if (NewBI.class$freemarker$template$TemplateModel == null) {
                clsClass$ = NewBI.class$("org.mapstruct.ap.shaded.freemarker.template.TemplateModel");
                NewBI.class$freemarker$template$TemplateModel = clsClass$;
            } else {
                clsClass$ = NewBI.class$freemarker$template$TemplateModel;
            }
            if (!clsClass$.isAssignableFrom(clsResolve)) {
                throw new _MiscTemplateException(NewBI.this, environment, new Object[]{"Class ", clsResolve.getName(), " does not implement freemarker.template.TemplateModel"});
            }
            if (NewBI.BEAN_MODEL_CLASS.isAssignableFrom(clsResolve)) {
                throw new _MiscTemplateException(NewBI.this, environment, new Object[]{"Bean Models cannot be instantiated using the ?", NewBI.this.key, " built-in"});
            }
            if (NewBI.JYTHON_MODEL_CLASS != null && NewBI.JYTHON_MODEL_CLASS.isAssignableFrom(clsResolve)) {
                throw new _MiscTemplateException(NewBI.this, environment, new Object[]{"Jython Models cannot be instantiated using the ?", NewBI.this.key, " built-in"});
            }
        }

        @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateMethodModelEx, org.mapstruct.ap.shaded.freemarker.template.TemplateMethodModel
        public Object exec(List list) throws TemplateModelException {
            ObjectWrapper objectWrapper = this.env.getObjectWrapper();
            return (objectWrapper instanceof BeansWrapper ? (BeansWrapper) objectWrapper : BeansWrapper.getDefaultInstance()).newInstance(this.cl, list);
        }
    }
}
