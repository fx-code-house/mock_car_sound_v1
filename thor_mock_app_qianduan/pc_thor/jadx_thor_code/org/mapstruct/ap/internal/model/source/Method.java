package org.mapstruct.ap.internal.model.source;

import java.util.List;
import javax.lang.model.element.ExecutableElement;
import org.mapstruct.ap.internal.model.common.Accessibility;
import org.mapstruct.ap.internal.model.common.Parameter;
import org.mapstruct.ap.internal.model.common.Type;

/* loaded from: classes3.dex */
public interface Method {
    String describe();

    Accessibility getAccessibility();

    List<Parameter> getContextParameters();

    ParameterProvidedMethods getContextProvidedMethods();

    Type getDeclaringMapper();

    Type getDefiningType();

    ExecutableElement getExecutable();

    Parameter getMappingTargetParameter();

    String getName();

    MappingMethodOptions getOptions();

    List<String> getParameterNames();

    List<Parameter> getParameters();

    Type getResultType();

    Type getReturnType();

    List<Parameter> getSourceParameters();

    Parameter getTargetTypeParameter();

    List<Type> getThrownTypes();

    boolean isDefault();

    boolean isLifecycleCallbackMethod();

    boolean isObjectFactory();

    boolean isStatic();

    boolean isUpdateMethod();

    boolean matches(List<Type> list, Type type);

    boolean overridesMethod();

    default boolean isMappingTargetAssignableToReturnType() {
        return isUpdateMethod() && getResultType().isAssignableTo(getReturnType());
    }

    default Type getMappingSourceType() {
        return getSourceParameters().get(0).getType();
    }
}
