package org.mapstruct.ap.internal.model.common;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javax.lang.model.element.Element;
import javax.lang.model.element.VariableElement;
import org.mapstruct.ap.internal.gem.ContextGem;
import org.mapstruct.ap.internal.gem.MappingTargetGem;
import org.mapstruct.ap.internal.gem.TargetTypeGem;
import org.mapstruct.ap.internal.util.Collections;

/* loaded from: classes3.dex */
public class Parameter extends ModelElement {
    private final Element element;
    private final boolean mappingContext;
    private final boolean mappingTarget;
    private final String name;
    private final String originalName;
    private final boolean targetType;
    private final Type type;
    private final boolean varArgs;

    private Parameter(Element element, Type type, boolean z) {
        this.element = element;
        String string = element.getSimpleName().toString();
        this.name = string;
        this.originalName = string;
        this.type = type;
        this.mappingTarget = MappingTargetGem.instanceOn(element) != null;
        this.targetType = TargetTypeGem.instanceOn(element) != null;
        this.mappingContext = ContextGem.instanceOn(element) != null;
        this.varArgs = z;
    }

    private Parameter(String str, Type type, boolean z, boolean z2, boolean z3, boolean z4) {
        this.element = null;
        this.name = str;
        this.originalName = str;
        this.type = type;
        this.mappingTarget = z;
        this.targetType = z2;
        this.mappingContext = z3;
        this.varArgs = z4;
    }

    public Parameter(String str, Type type) {
        this(str, type, false, false, false, false);
    }

    public Element getElement() {
        return this.element;
    }

    public String getName() {
        return this.name;
    }

    public String getOriginalName() {
        return this.originalName;
    }

    public Type getType() {
        return this.type;
    }

    public boolean isMappingTarget() {
        return this.mappingTarget;
    }

    public String toString() {
        return String.format(format(), this.type);
    }

    public String describe() {
        return String.format(format(), this.type.describe());
    }

    private String format() {
        return (this.mappingTarget ? "@MappingTarget " : "") + (this.targetType ? "@TargetType " : "") + (this.mappingContext ? "@Context " : "") + "%s " + this.name;
    }

    @Override // org.mapstruct.ap.internal.model.common.ModelElement, org.mapstruct.ap.internal.model.Constructor
    public Set<Type> getImportTypes() {
        return Collections.asSet(this.type);
    }

    public boolean isTargetType() {
        return this.targetType;
    }

    public boolean isMappingContext() {
        return this.mappingContext;
    }

    public boolean isVarArgs() {
        return this.varArgs;
    }

    public int hashCode() {
        String str = this.name;
        int iHashCode = (str != null ? str.hashCode() : 0) * 31;
        Type type = this.type;
        return iHashCode + (type != null ? type.hashCode() : 0);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Parameter parameter = (Parameter) obj;
        if (Objects.equals(this.name, parameter.name)) {
            return Objects.equals(this.type, parameter.type);
        }
        return false;
    }

    public static Parameter forElementAndType(VariableElement variableElement, Type type, boolean z) {
        return new Parameter(variableElement, type, z);
    }

    public static Parameter forForgedMappingTarget(Type type) {
        return new Parameter("mappingTarget", type, true, false, false, false);
    }

    public static List<Parameter> getSourceParameters(List<Parameter> list) {
        return (List) list.stream().filter(new Parameter$$ExternalSyntheticLambda0()).collect(Collectors.toList());
    }

    public static Parameter getSourceParameter(List<Parameter> list, final String str) {
        return list.stream().filter(new Parameter$$ExternalSyntheticLambda0()).filter(new Predicate() { // from class: org.mapstruct.ap.internal.model.common.Parameter$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((Parameter) obj).getName().equals(str);
            }
        }).findAny().orElse(null);
    }

    public static List<Parameter> getContextParameters(List<Parameter> list) {
        return (List) list.stream().filter(new Predicate() { // from class: org.mapstruct.ap.internal.model.common.Parameter$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((Parameter) obj).isMappingContext();
            }
        }).collect(Collectors.toList());
    }

    public static Parameter getMappingTargetParameter(List<Parameter> list) {
        return list.stream().filter(new Predicate() { // from class: org.mapstruct.ap.internal.model.common.Parameter$$ExternalSyntheticLambda4
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((Parameter) obj).isMappingTarget();
            }
        }).findAny().orElse(null);
    }

    public static Parameter getTargetTypeParameter(List<Parameter> list) {
        return list.stream().filter(new Parameter$$ExternalSyntheticLambda3()).findAny().orElse(null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isSourceParameter(Parameter parameter) {
        return (parameter.isMappingTarget() || parameter.isTargetType() || parameter.isMappingContext()) ? false : true;
    }
}
