package org.mapstruct.ap.internal.model.beanmapping;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.type.DeclaredType;
import org.mapstruct.ap.internal.model.common.Parameter;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.common.TypeFactory;
import org.mapstruct.ap.internal.model.source.MappingOptions;
import org.mapstruct.ap.internal.model.source.Method;
import org.mapstruct.ap.internal.util.Collections;
import org.mapstruct.ap.internal.util.Extractor;
import org.mapstruct.ap.internal.util.FormattingMessager;
import org.mapstruct.ap.internal.util.Message;
import org.mapstruct.ap.internal.util.Strings;
import org.mapstruct.ap.internal.util.accessor.Accessor;

/* loaded from: classes3.dex */
public class SourceReference extends AbstractReference {

    public static class BuilderFromMapping {
        private AnnotationMirror annotationMirror;
        private Method method;
        private AnnotationValue sourceAnnotationValue;
        private String sourceName;
        private TypeFactory typeFactory;
        private FormattingMessager messager = null;
        private boolean isForwarded = false;
        private Method templateMethod = null;

        public BuilderFromMapping messager(FormattingMessager formattingMessager) {
            this.messager = formattingMessager;
            return this;
        }

        public BuilderFromMapping mapping(MappingOptions mappingOptions) {
            this.sourceName = mappingOptions.getSourceName();
            this.annotationMirror = mappingOptions.getMirror();
            this.sourceAnnotationValue = mappingOptions.getSourceAnnotationValue();
            if (mappingOptions.getInheritContext() != null) {
                this.isForwarded = mappingOptions.getInheritContext().isForwarded();
                this.templateMethod = mappingOptions.getInheritContext().getTemplateMethod();
            }
            return this;
        }

        public BuilderFromMapping method(Method method) {
            this.method = method;
            return this;
        }

        public BuilderFromMapping typeFactory(TypeFactory typeFactory) {
            this.typeFactory = typeFactory;
            return this;
        }

        public BuilderFromMapping sourceName(String str) {
            this.sourceName = str;
            return this;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public SourceReference build() {
            Parameter parameter = null;
            Object[] objArr = 0;
            if (this.sourceName == null) {
                return null;
            }
            Objects.requireNonNull(this.messager);
            String strTrim = this.sourceName.trim();
            boolean z = false;
            if (!this.sourceName.equals(strTrim)) {
                this.messager.printMessage(this.method.getExecutable(), this.annotationMirror, this.sourceAnnotationValue, Message.PROPERTYMAPPING_WHITESPACE_TRIMMED, this.sourceName, strTrim);
            }
            String[] strArrSplit = strTrim.split("\\.");
            SourceReference sourceReference = new SourceReference(parameter, new ArrayList(), z);
            if (this.method.getSourceParameters().size() > 1) {
                Parameter parameterFetchMatchingParameterFromFirstSegment = fetchMatchingParameterFromFirstSegment(strArrSplit);
                return parameterFetchMatchingParameterFromFirstSegment != null ? buildFromMultipleSourceParameters(strArrSplit, parameterFetchMatchingParameterFromFirstSegment) : sourceReference;
            }
            return buildFromSingleSourceParameters(strArrSplit, this.method.getSourceParameters().get(0));
        }

        private SourceReference buildFromSingleSourceParameters(String[] strArr, Parameter parameter) {
            List<PropertyEntry> listMatchWithSourceAccessorTypes = matchWithSourceAccessorTypes(parameter.getType(), strArr);
            boolean z = listMatchWithSourceAccessorTypes.size() == strArr.length;
            if (!z) {
                if (getSourceParameterFromMethodOrTemplate(strArr[0]) != null) {
                    strArr = (String[]) Arrays.copyOfRange(strArr, 1, strArr.length);
                    listMatchWithSourceAccessorTypes = matchWithSourceAccessorTypes(parameter.getType(), strArr);
                    z = listMatchWithSourceAccessorTypes.size() == strArr.length;
                } else {
                    parameter = null;
                }
            }
            if (!z) {
                reportErrorOnNoMatch(parameter, strArr, listMatchWithSourceAccessorTypes);
            }
            return new SourceReference(parameter, listMatchWithSourceAccessorTypes, z);
        }

        private SourceReference buildFromMultipleSourceParameters(String[] strArr, Parameter parameter) {
            String[] strArr2 = new String[0];
            List<PropertyEntry> arrayList = new ArrayList<>();
            boolean z = true;
            if (strArr.length > 1 && parameter != null) {
                strArr2 = (String[]) Arrays.copyOfRange(strArr, 1, strArr.length);
                arrayList = matchWithSourceAccessorTypes(parameter.getType(), strArr2);
                z = arrayList.size() == strArr2.length;
            }
            if (!z) {
                reportErrorOnNoMatch(parameter, strArr2, arrayList);
            }
            return new SourceReference(parameter, arrayList, z);
        }

        private Parameter fetchMatchingParameterFromFirstSegment(String[] strArr) {
            if (strArr.length <= 0) {
                return null;
            }
            String str = strArr[0];
            Parameter sourceParameterFromMethodOrTemplate = getSourceParameterFromMethodOrTemplate(str);
            if (sourceParameterFromMethodOrTemplate != null) {
                return sourceParameterFromMethodOrTemplate;
            }
            reportMappingError(Message.PROPERTYMAPPING_INVALID_PARAMETER_NAME, str, Strings.join(this.method.getSourceParameters(), ", ", new Extractor() { // from class: org.mapstruct.ap.internal.model.beanmapping.SourceReference$BuilderFromMapping$$ExternalSyntheticLambda0
                @Override // org.mapstruct.ap.internal.util.Extractor
                public final Object apply(Object obj) {
                    return ((Parameter) obj).getName();
                }
            }));
            return sourceParameterFromMethodOrTemplate;
        }

        private Parameter getSourceParameterFromMethodOrTemplate(String str) {
            if (this.isForwarded) {
                Parameter sourceParameter = Parameter.getSourceParameter(this.templateMethod.getParameters(), str);
                Parameter parameter = null;
                if (sourceParameter == null) {
                    return null;
                }
                for (Parameter parameter2 : this.method.getSourceParameters()) {
                    if (parameter2.getType().isAssignableTo(sourceParameter.getType())) {
                        if (parameter != null) {
                            return Parameter.getSourceParameter(this.method.getParameters(), str);
                        }
                        parameter = parameter2;
                    }
                }
                return parameter;
            }
            return Parameter.getSourceParameter(this.method.getParameters(), str);
        }

        private void reportErrorOnNoMatch(Parameter parameter, String[] strArr, List<PropertyEntry> list) {
            Type type;
            int size;
            if (parameter != null) {
                reportMappingError(Message.PROPERTYMAPPING_NO_PROPERTY_IN_PARAMETER, parameter.getName(), Strings.join(Arrays.asList(strArr), "."));
                return;
            }
            Type type2 = this.method.getParameters().get(0).getType();
            if (list.isEmpty()) {
                type = type2;
                size = 0;
            } else {
                size = list.size();
                type = ((PropertyEntry) Collections.last(list)).getType();
            }
            String mostSimilarWord = Strings.getMostSimilarWord(strArr[size], type.getPropertyReadAccessors().keySet());
            ArrayList arrayList = new ArrayList(Arrays.asList(strArr).subList(0, size));
            arrayList.add(mostSimilarWord);
            reportMappingError(Message.PROPERTYMAPPING_INVALID_PROPERTY_NAME, this.sourceName, Strings.join(arrayList, "."));
        }

        private List<PropertyEntry> matchWithSourceAccessorTypes(Type type, String[] strArr) {
            boolean z;
            ArrayList arrayList = new ArrayList();
            for (int i = 0; i < strArr.length; i++) {
                Map<String, Accessor> propertyReadAccessors = type.getPropertyReadAccessors();
                Map<String, Accessor> propertyPresenceCheckers = type.getPropertyPresenceCheckers();
                Iterator<Map.Entry<String, Accessor>> it = propertyReadAccessors.entrySet().iterator();
                while (true) {
                    if (!it.hasNext()) {
                        z = false;
                        break;
                    }
                    Map.Entry<String, Accessor> next = it.next();
                    if (next.getKey().equals(strArr[i])) {
                        type = this.typeFactory.getReturnType((DeclaredType) type.getTypeMirror(), next.getValue());
                        arrayList.add(PropertyEntry.forSourceReference((String[]) Arrays.copyOf(strArr, i + 1), next.getValue(), propertyPresenceCheckers.get(strArr[i]), type));
                        z = true;
                        break;
                    }
                }
                if (!z) {
                    break;
                }
            }
            return arrayList;
        }

        private void reportMappingError(Message message, Object... objArr) {
            this.messager.printMessage(this.method.getExecutable(), this.annotationMirror, this.sourceAnnotationValue, message, objArr);
        }
    }

    public static class BuilderFromProperty {
        private String name;
        private Accessor presenceChecker;
        private Accessor readAccessor;
        private Parameter sourceParameter;
        private Type type;

        public BuilderFromProperty name(String str) {
            this.name = str;
            return this;
        }

        public BuilderFromProperty readAccessor(Accessor accessor) {
            this.readAccessor = accessor;
            return this;
        }

        public BuilderFromProperty presenceChecker(Accessor accessor) {
            this.presenceChecker = accessor;
            return this;
        }

        public BuilderFromProperty type(Type type) {
            this.type = type;
            return this;
        }

        public BuilderFromProperty sourceParameter(Parameter parameter) {
            this.sourceParameter = parameter;
            return this;
        }

        public SourceReference build() {
            ArrayList arrayList = new ArrayList();
            Accessor accessor = this.readAccessor;
            boolean z = true;
            if (accessor != null) {
                arrayList.add(PropertyEntry.forSourceReference(new String[]{this.name}, accessor, this.presenceChecker, this.type));
            }
            return new SourceReference(this.sourceParameter, arrayList, z);
        }
    }

    public static class BuilderFromSourceReference {
        private Parameter sourceParameter;
        private SourceReference sourceReference;

        public BuilderFromSourceReference sourceReference(SourceReference sourceReference) {
            this.sourceReference = sourceReference;
            return this;
        }

        public BuilderFromSourceReference sourceParameter(Parameter parameter) {
            this.sourceParameter = parameter;
            return this;
        }

        public SourceReference build() {
            return new SourceReference(this.sourceParameter, this.sourceReference.getPropertyEntries(), true);
        }
    }

    private SourceReference(Parameter parameter, List<PropertyEntry> list, boolean z) {
        super(parameter, list, z);
    }

    public SourceReference pop() {
        if (getPropertyEntries().size() <= 1) {
            return null;
        }
        return new SourceReference(getParameter(), new ArrayList(getPropertyEntries().subList(1, getPropertyEntries().size())), isValid());
    }

    public List<SourceReference> push(TypeFactory typeFactory, FormattingMessager formattingMessager, Method method) {
        ArrayList arrayList = new ArrayList();
        PropertyEntry deepestProperty = getDeepestProperty();
        if (deepestProperty != null) {
            Iterator<Map.Entry<String, Accessor>> it = deepestProperty.getType().getPropertyReadAccessors().entrySet().iterator();
            while (it.hasNext()) {
                arrayList.add(new BuilderFromMapping().sourceName(deepestProperty.getFullName() + "." + it.next().getKey()).method(method).messager(formattingMessager).typeFactory(typeFactory).build());
            }
        }
        return arrayList;
    }
}
