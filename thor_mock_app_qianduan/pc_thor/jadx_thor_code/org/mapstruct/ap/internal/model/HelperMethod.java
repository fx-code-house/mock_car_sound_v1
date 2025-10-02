package org.mapstruct.ap.internal.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.lang.model.element.ExecutableElement;
import org.mapstruct.ap.internal.model.common.Accessibility;
import org.mapstruct.ap.internal.model.common.ConversionContext;
import org.mapstruct.ap.internal.model.common.Parameter;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.source.Method;
import org.mapstruct.ap.internal.model.source.ParameterProvidedMethods;
import org.mapstruct.ap.internal.util.Strings;

/* loaded from: classes3.dex */
public abstract class HelperMethod implements Method {
    public boolean doTypeVarsMatch(Type type, Type type2) {
        return true;
    }

    public String getContextParameter(ConversionContext conversionContext) {
        return null;
    }

    @Override // org.mapstruct.ap.internal.model.source.Method
    public final Type getDeclaringMapper() {
        return null;
    }

    @Override // org.mapstruct.ap.internal.model.source.Method
    public Type getDefiningType() {
        return null;
    }

    @Override // org.mapstruct.ap.internal.model.source.Method
    public ExecutableElement getExecutable() {
        return null;
    }

    @Override // org.mapstruct.ap.internal.model.source.Method
    public Parameter getMappingTargetParameter() {
        return null;
    }

    public abstract Parameter getParameter();

    @Override // org.mapstruct.ap.internal.model.source.Method
    public Parameter getTargetTypeParameter() {
        return null;
    }

    @Override // org.mapstruct.ap.internal.model.source.Method
    public boolean isDefault() {
        return false;
    }

    @Override // org.mapstruct.ap.internal.model.source.Method
    public boolean isLifecycleCallbackMethod() {
        return false;
    }

    @Override // org.mapstruct.ap.internal.model.source.Method
    public boolean isObjectFactory() {
        return false;
    }

    @Override // org.mapstruct.ap.internal.model.source.Method
    public boolean isStatic() {
        return false;
    }

    @Override // org.mapstruct.ap.internal.model.source.Method
    public boolean isUpdateMethod() {
        return false;
    }

    @Override // org.mapstruct.ap.internal.model.source.Method
    public boolean overridesMethod() {
        return false;
    }

    @Override // org.mapstruct.ap.internal.model.source.Method
    public String getName() {
        return Strings.decapitalize(getClass().getSimpleName());
    }

    public Set<Type> getImportTypes() {
        return Collections.emptySet();
    }

    @Override // org.mapstruct.ap.internal.model.source.Method
    public boolean matches(List<Type> list, Type type) {
        throw new IllegalStateException("Irrelevant.");
    }

    @Override // org.mapstruct.ap.internal.model.source.Method
    public List<Parameter> getSourceParameters() {
        return getParameters();
    }

    @Override // org.mapstruct.ap.internal.model.source.Method
    public List<Parameter> getContextParameters() {
        return Collections.emptyList();
    }

    @Override // org.mapstruct.ap.internal.model.source.Method
    public ParameterProvidedMethods getContextProvidedMethods() {
        return ParameterProvidedMethods.empty();
    }

    @Override // org.mapstruct.ap.internal.model.source.Method
    public List<Parameter> getParameters() {
        return Arrays.asList(getParameter());
    }

    public int hashCode() {
        return getClass().hashCode();
    }

    public boolean equals(Object obj) {
        return obj != null && getClass() == obj.getClass();
    }

    @Override // org.mapstruct.ap.internal.model.source.Method
    public Accessibility getAccessibility() {
        return Accessibility.PRIVATE;
    }

    @Override // org.mapstruct.ap.internal.model.source.Method
    public List<Type> getThrownTypes() {
        return Collections.emptyList();
    }

    @Override // org.mapstruct.ap.internal.model.source.Method
    public Type getResultType() {
        return getReturnType();
    }

    @Override // org.mapstruct.ap.internal.model.source.Method
    public List<String> getParameterNames() {
        ArrayList arrayList = new ArrayList(getParameters().size());
        Iterator<Parameter> it = getParameters().iterator();
        while (it.hasNext()) {
            arrayList.add(it.next().getName());
        }
        return arrayList;
    }
}
