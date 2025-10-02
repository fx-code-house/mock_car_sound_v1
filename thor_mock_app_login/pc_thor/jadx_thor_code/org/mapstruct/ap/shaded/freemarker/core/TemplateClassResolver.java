package org.mapstruct.ap.shaded.freemarker.core;

import org.mapstruct.ap.shaded.freemarker.template.Template;
import org.mapstruct.ap.shaded.freemarker.template.TemplateException;
import org.mapstruct.ap.shaded.freemarker.template.utility.ClassUtil;

/* loaded from: classes3.dex */
public interface TemplateClassResolver {
    public static final TemplateClassResolver UNRESTRICTED_RESOLVER = new TemplateClassResolver() { // from class: org.mapstruct.ap.shaded.freemarker.core.TemplateClassResolver.1
        @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateClassResolver
        public Class resolve(String str, Environment environment, Template template) throws TemplateException {
            try {
                return ClassUtil.forName(str);
            } catch (ClassNotFoundException e) {
                throw new _MiscTemplateException(e, environment);
            }
        }
    };
    public static final TemplateClassResolver SAFER_RESOLVER = new TemplateClassResolver() { // from class: org.mapstruct.ap.shaded.freemarker.core.TemplateClassResolver.2
        @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateClassResolver
        public Class resolve(String str, Environment environment, Template template) throws Throwable {
            Class clsClass$;
            Class clsClass$2;
            if (AnonymousClass4.class$freemarker$template$utility$ObjectConstructor == null) {
                clsClass$ = AnonymousClass4.class$("org.mapstruct.ap.shaded.freemarker.template.utility.ObjectConstructor");
                AnonymousClass4.class$freemarker$template$utility$ObjectConstructor = clsClass$;
            } else {
                clsClass$ = AnonymousClass4.class$freemarker$template$utility$ObjectConstructor;
            }
            if (!str.equals(clsClass$.getName())) {
                if (AnonymousClass4.class$freemarker$template$utility$Execute == null) {
                    clsClass$2 = AnonymousClass4.class$("org.mapstruct.ap.shaded.freemarker.template.utility.Execute");
                    AnonymousClass4.class$freemarker$template$utility$Execute = clsClass$2;
                } else {
                    clsClass$2 = AnonymousClass4.class$freemarker$template$utility$Execute;
                }
                if (!str.equals(clsClass$2.getName()) && !str.equals("org.mapstruct.ap.shaded.freemarker.template.utility.JythonRuntime")) {
                    try {
                        return ClassUtil.forName(str);
                    } catch (ClassNotFoundException e) {
                        throw new _MiscTemplateException(e, environment);
                    }
                }
            }
            throw MessageUtil.newInstantiatingClassNotAllowedException(str, environment);
        }
    };
    public static final TemplateClassResolver ALLOWS_NOTHING_RESOLVER = new TemplateClassResolver() { // from class: org.mapstruct.ap.shaded.freemarker.core.TemplateClassResolver.3
        @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateClassResolver
        public Class resolve(String str, Environment environment, Template template) throws TemplateException {
            throw MessageUtil.newInstantiatingClassNotAllowedException(str, environment);
        }
    };

    Class resolve(String str, Environment environment, Template template) throws TemplateException;

    /* renamed from: org.mapstruct.ap.shaded.freemarker.core.TemplateClassResolver$4, reason: invalid class name */
    /* synthetic */ class AnonymousClass4 {
        static /* synthetic */ Class class$freemarker$template$utility$Execute;
        static /* synthetic */ Class class$freemarker$template$utility$ObjectConstructor;

        static /* synthetic */ Class class$(String str) throws Throwable {
            try {
                return Class.forName(str);
            } catch (ClassNotFoundException e) {
                throw new NoClassDefFoundError().initCause(e);
            }
        }
    }
}
