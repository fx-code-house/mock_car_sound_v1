package org.mapstruct.ap.internal.model.beanmapping;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import org.mapstruct.ap.internal.model.common.Parameter;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.common.TypeFactory;
import org.mapstruct.ap.internal.model.source.MappingOptions;
import org.mapstruct.ap.internal.model.source.Method;
import org.mapstruct.ap.internal.util.FormattingMessager;
import org.mapstruct.ap.internal.util.Message;
import org.mapstruct.ap.internal.util.Strings;

/* loaded from: classes3.dex */
public class TargetReference {
    private final Parameter parameter;
    private final List<String> pathProperties;
    private final List<String> propertyEntries;

    public TargetReference(Parameter parameter, List<String> list) {
        this(parameter, list, Collections.emptyList());
    }

    public TargetReference(Parameter parameter, List<String> list, List<String> list2) {
        this.pathProperties = list2;
        this.parameter = parameter;
        this.propertyEntries = list;
    }

    public List<String> getPathProperties() {
        return this.pathProperties;
    }

    public List<String> getPropertyEntries() {
        return this.propertyEntries;
    }

    public List<String> getElementNames() {
        ArrayList arrayList = new ArrayList();
        Parameter parameter = this.parameter;
        if (parameter != null) {
            arrayList.add(parameter.getName());
        }
        arrayList.addAll(this.propertyEntries);
        return arrayList;
    }

    public String getShallowestPropertyName() {
        if (this.propertyEntries.isEmpty()) {
            return null;
        }
        return (String) org.mapstruct.ap.internal.util.Collections.first(this.propertyEntries);
    }

    public boolean isNested() {
        return this.propertyEntries.size() > 1;
    }

    public String toString() {
        if (this.propertyEntries.isEmpty()) {
            Parameter parameter = this.parameter;
            return parameter != null ? String.format("parameter \"%s %s\"", parameter.getType(), this.parameter.getName()) : "";
        }
        if (this.propertyEntries.size() == 1) {
            return String.format("property \"%s\"", this.propertyEntries.get(0));
        }
        return String.format("property \"%s\"", Strings.join(getElementNames(), "."));
    }

    public static class Builder {
        private MappingOptions mapping;
        private FormattingMessager messager;
        private Method method;
        private Set<String> targetProperties;
        private Type targetType;
        private TypeFactory typeFactory;
        private String targetName = null;
        private AnnotationMirror annotationMirror = null;
        private AnnotationValue targetAnnotationValue = null;
        private AnnotationValue sourceAnnotationValue = null;

        public Builder messager(FormattingMessager formattingMessager) {
            this.messager = formattingMessager;
            return this;
        }

        public Builder mapping(MappingOptions mappingOptions) {
            this.mapping = mappingOptions;
            this.targetName = mappingOptions.getTargetName();
            this.annotationMirror = mappingOptions.getMirror();
            this.targetAnnotationValue = mappingOptions.getTargetAnnotationValue();
            this.sourceAnnotationValue = mappingOptions.getSourceAnnotationValue();
            return this;
        }

        public Builder typeFactory(TypeFactory typeFactory) {
            this.typeFactory = typeFactory;
            return this;
        }

        public Builder method(Method method) {
            this.method = method;
            return this;
        }

        public Builder targetProperties(Set<String> set) {
            this.targetProperties = set;
            return this;
        }

        public Builder targetType(Type type) {
            this.targetType = type;
            return this;
        }

        public TargetReference build() {
            Objects.requireNonNull(this.method);
            Objects.requireNonNull(this.typeFactory);
            Objects.requireNonNull(this.messager);
            Objects.requireNonNull(this.targetType);
            String str = this.targetName;
            if (str == null) {
                return null;
            }
            String strTrim = str.trim();
            if (!this.targetName.equals(strTrim)) {
                this.messager.printMessage(this.method.getExecutable(), this.annotationMirror, this.targetAnnotationValue, Message.PROPERTYMAPPING_WHITESPACE_TRIMMED, this.targetName, strTrim);
            }
            String[] strArrSplit = strTrim.split("\\.");
            Parameter mappingTargetParameter = this.method.getMappingTargetParameter();
            if (strArrSplit.length > 1) {
                String str2 = strArrSplit[0];
                if (!this.targetProperties.contains(str2) && matchesSourceOrTargetParameter(str2, mappingTargetParameter)) {
                    strArrSplit = (String[]) Arrays.copyOfRange(strArrSplit, 1, strArrSplit.length);
                }
            }
            return new TargetReference(mappingTargetParameter, new ArrayList(Arrays.asList(strArrSplit)));
        }

        private boolean matchesSourceOrTargetParameter(String str, Parameter parameter) {
            return (parameter != null && parameter.getName().equals(str)) || matchesSourceOnInverseSourceParameter(str);
        }

        private boolean matchesSourceOnInverseSourceParameter(String str) {
            MappingOptions.InheritContext inheritContext = this.mapping.getInheritContext();
            if (inheritContext == null || !inheritContext.isReversed()) {
                return false;
            }
            return ((Parameter) org.mapstruct.ap.internal.util.Collections.first(inheritContext.getTemplateMethod().getSourceParameters())).getName().equals(str);
        }
    }

    public TargetReference pop() {
        if (getPropertyEntries().size() <= 1) {
            return null;
        }
        ArrayList arrayList = new ArrayList(this.pathProperties);
        arrayList.add(getPropertyEntries().get(0));
        return new TargetReference(null, getPropertyEntries().subList(1, getPropertyEntries().size()), arrayList);
    }
}
