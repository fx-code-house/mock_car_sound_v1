package org.mapstruct.ap.shaded.freemarker.ext.beans;

import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;
import org.mapstruct.ap.shaded.freemarker.core._UnexpectedTypeErrorExplainerTemplateModel;
import org.mapstruct.ap.shaded.freemarker.template.SimpleNumber;
import org.mapstruct.ap.shaded.freemarker.template.TemplateMethodModelEx;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModelException;
import org.mapstruct.ap.shaded.freemarker.template.TemplateSequenceModel;
import org.mapstruct.ap.shaded.freemarker.template.utility.ClassUtil;

/* loaded from: classes3.dex */
public final class SimpleMethodModel extends SimpleMethod implements TemplateMethodModelEx, TemplateSequenceModel, _UnexpectedTypeErrorExplainerTemplateModel {
    static /* synthetic */ Class class$java$lang$Void;
    private final Object object;
    private final BeansWrapper wrapper;

    SimpleMethodModel(Object obj, Method method, Class[] clsArr, BeansWrapper beansWrapper) {
        super(method, clsArr);
        this.object = obj;
        this.wrapper = beansWrapper;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateMethodModelEx, org.mapstruct.ap.shaded.freemarker.template.TemplateMethodModel
    public Object exec(List list) throws TemplateModelException {
        try {
            return this.wrapper.invokeMethod(this.object, (Method) getMember(), unwrapArguments(list, this.wrapper));
        } catch (TemplateModelException e) {
            throw e;
        } catch (Exception e2) {
            if (e2 instanceof TemplateModelException) {
                throw ((TemplateModelException) e2);
            }
            throw _MethodUtil.newInvocationTemplateModelException(this.object, getMember(), e2);
        }
    }

    @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateSequenceModel
    public TemplateModel get(int i) throws TemplateModelException {
        return (TemplateModel) exec(Collections.singletonList(new SimpleNumber(new Integer(i))));
    }

    @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateSequenceModel
    public int size() throws TemplateModelException {
        throw new TemplateModelException(new StringBuffer("Getting the number of items or enumerating the items is not supported on this ").append(ClassUtil.getFTLTypeDescription(this)).append(" value.\n(Hint 1: Maybe you wanted to call this method first and then do something with its return value. Hint 2: Getting items by intex possibly works, hence it's a \"+sequence\".)").toString());
    }

    public String toString() {
        return getMember().toString();
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core._UnexpectedTypeErrorExplainerTemplateModel
    public Object[] explainTypeError(Class[] clsArr) throws Throwable {
        Method method;
        Class<?> returnType;
        Member member = getMember();
        if ((member instanceof Method) && (returnType = (method = (Method) member).getReturnType()) != null && returnType != Void.TYPE) {
            Class<?> clsClass$ = class$java$lang$Void;
            if (clsClass$ == null) {
                clsClass$ = class$("java.lang.Void");
                class$java$lang$Void = clsClass$;
            }
            if (returnType != clsClass$) {
                String name = method.getName();
                if (name.startsWith("get") && name.length() > 3 && Character.isUpperCase(name.charAt(3)) && method.getParameterTypes().length == 0) {
                    return new Object[]{"Maybe using obj.something instead of obj.getSomething will yield the desired value."};
                }
                if (name.startsWith("is") && name.length() > 2 && Character.isUpperCase(name.charAt(2)) && method.getParameterTypes().length == 0) {
                    return new Object[]{"Maybe using obj.something instead of obj.isSomething will yield the desired value."};
                }
                Object[] objArr = new Object[3];
                objArr[0] = "Maybe using obj.something(";
                objArr[1] = method.getParameterTypes().length != 0 ? "params" : "";
                objArr[2] = ") instead of obj.something will yield the desired value";
                return objArr;
            }
        }
        return null;
    }

    static /* synthetic */ Class class$(String str) throws Throwable {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError().initCause(e);
        }
    }
}
