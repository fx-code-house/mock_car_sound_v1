package org.mapstruct.ap.shaded.freemarker.ext.beans;

import java.util.Collections;
import java.util.List;
import org.mapstruct.ap.shaded.freemarker.template.SimpleNumber;
import org.mapstruct.ap.shaded.freemarker.template.TemplateMethodModelEx;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModelException;
import org.mapstruct.ap.shaded.freemarker.template.TemplateSequenceModel;

/* loaded from: classes3.dex */
public class OverloadedMethodsModel implements TemplateMethodModelEx, TemplateSequenceModel {
    private final Object object;
    private final OverloadedMethods overloadedMethods;
    private final BeansWrapper wrapper;

    OverloadedMethodsModel(Object obj, OverloadedMethods overloadedMethods, BeansWrapper beansWrapper) {
        this.object = obj;
        this.overloadedMethods = overloadedMethods;
        this.wrapper = beansWrapper;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateMethodModelEx, org.mapstruct.ap.shaded.freemarker.template.TemplateMethodModel
    public Object exec(List list) throws TemplateModelException {
        MemberAndArguments memberAndArguments = this.overloadedMethods.getMemberAndArguments(list, this.wrapper);
        try {
            return memberAndArguments.invokeMethod(this.wrapper, this.object);
        } catch (Exception e) {
            if (e instanceof TemplateModelException) {
                throw ((TemplateModelException) e);
            }
            throw _MethodUtil.newInvocationTemplateModelException(this.object, memberAndArguments.getCallableMemberDescriptor(), e);
        }
    }

    @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateSequenceModel
    public TemplateModel get(int i) throws TemplateModelException {
        return (TemplateModel) exec(Collections.singletonList(new SimpleNumber(new Integer(i))));
    }

    @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateSequenceModel
    public int size() throws TemplateModelException {
        throw new TemplateModelException(new StringBuffer("?size is unsupported for ").append(getClass().getName()).toString());
    }
}
