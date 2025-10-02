package org.mapstruct.ap.shaded.freemarker.template.utility;

import java.util.ArrayList;
import java.util.HashMap;
import org.mapstruct.ap.shaded.freemarker.core.Environment;
import org.mapstruct.ap.shaded.freemarker.ext.util.WrapperTemplateModel;
import org.mapstruct.ap.shaded.freemarker.template.AdapterTemplateModel;
import org.mapstruct.ap.shaded.freemarker.template.ObjectWrapper;
import org.mapstruct.ap.shaded.freemarker.template.TemplateBooleanModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateCollectionModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateDateModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateHashModelEx;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModelException;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModelIterator;
import org.mapstruct.ap.shaded.freemarker.template.TemplateNumberModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateScalarModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateSequenceModel;

/* loaded from: classes3.dex */
public class DeepUnwrap {
    private static final Class OBJECT_CLASS;
    static /* synthetic */ Class class$java$lang$Object;

    static {
        Class clsClass$ = class$java$lang$Object;
        if (clsClass$ == null) {
            clsClass$ = class$("java.lang.Object");
            class$java$lang$Object = clsClass$;
        }
        OBJECT_CLASS = clsClass$;
    }

    static /* synthetic */ Class class$(String str) throws Throwable {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError().initCause(e);
        }
    }

    public static Object unwrap(TemplateModel templateModel) throws TemplateModelException {
        return unwrap(templateModel, false);
    }

    public static Object permissiveUnwrap(TemplateModel templateModel) throws TemplateModelException {
        return unwrap(templateModel, true);
    }

    public static Object premissiveUnwrap(TemplateModel templateModel) throws TemplateModelException {
        return unwrap(templateModel, true);
    }

    private static Object unwrap(TemplateModel templateModel, boolean z) throws TemplateModelException {
        ObjectWrapper objectWrapper;
        Environment currentEnvironment = Environment.getCurrentEnvironment();
        TemplateModel templateModelWrap = null;
        if (currentEnvironment != null && (objectWrapper = currentEnvironment.getObjectWrapper()) != null) {
            templateModelWrap = objectWrapper.wrap(null);
        }
        return unwrap(templateModel, templateModelWrap, z);
    }

    private static Object unwrap(TemplateModel templateModel, TemplateModel templateModel2, boolean z) throws TemplateModelException {
        if (templateModel instanceof AdapterTemplateModel) {
            return ((AdapterTemplateModel) templateModel).getAdaptedObject(OBJECT_CLASS);
        }
        if (templateModel instanceof WrapperTemplateModel) {
            return ((WrapperTemplateModel) templateModel).getWrappedObject();
        }
        if (templateModel == templateModel2) {
            return null;
        }
        if (templateModel instanceof TemplateScalarModel) {
            return ((TemplateScalarModel) templateModel).getAsString();
        }
        if (templateModel instanceof TemplateNumberModel) {
            return ((TemplateNumberModel) templateModel).getAsNumber();
        }
        if (templateModel instanceof TemplateDateModel) {
            return ((TemplateDateModel) templateModel).getAsDate();
        }
        if (templateModel instanceof TemplateBooleanModel) {
            return Boolean.valueOf(((TemplateBooleanModel) templateModel).getAsBoolean());
        }
        if (templateModel instanceof TemplateSequenceModel) {
            TemplateSequenceModel templateSequenceModel = (TemplateSequenceModel) templateModel;
            ArrayList arrayList = new ArrayList(templateSequenceModel.size());
            for (int i = 0; i < templateSequenceModel.size(); i++) {
                arrayList.add(unwrap(templateSequenceModel.get(i), templateModel2, z));
            }
            return arrayList;
        }
        if (templateModel instanceof TemplateCollectionModel) {
            ArrayList arrayList2 = new ArrayList();
            TemplateModelIterator it = ((TemplateCollectionModel) templateModel).iterator();
            while (it.hasNext()) {
                arrayList2.add(unwrap(it.next(), templateModel2, z));
            }
            return arrayList2;
        }
        if (!(templateModel instanceof TemplateHashModelEx)) {
            if (z) {
                return templateModel;
            }
            throw new TemplateModelException(new StringBuffer("Cannot deep-unwrap model of type ").append(templateModel.getClass().getName()).toString());
        }
        TemplateHashModelEx templateHashModelEx = (TemplateHashModelEx) templateModel;
        HashMap map = new HashMap();
        TemplateModelIterator it2 = templateHashModelEx.keys().iterator();
        while (it2.hasNext()) {
            String str = (String) unwrap(it2.next(), templateModel2, z);
            map.put(str, unwrap(templateHashModelEx.get(str), templateModel2, z));
        }
        return map;
    }
}
