package org.mapstruct.ap.internal.model.source;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import org.mapstruct.ap.internal.gem.MappingGem;
import org.mapstruct.ap.internal.gem.MappingsGem;
import org.mapstruct.ap.internal.gem.NullValueCheckStrategyGem;
import org.mapstruct.ap.internal.gem.NullValuePropertyMappingStrategyGem;
import org.mapstruct.ap.internal.model.common.FormattingParameters;
import org.mapstruct.ap.internal.util.FormattingMessager;
import org.mapstruct.ap.internal.util.Message;
import org.mapstruct.ap.shaded.org.mapstruct.tools.gem.GemValue;

/* loaded from: classes3.dex */
public class MappingOptions extends DelegatingOptions {
    private static final Pattern JAVA_EXPRESSION = Pattern.compile("^java\\((.*)\\)$");
    private final String constant;
    private final String defaultJavaExpression;
    private final String defaultValue;
    private final Set<String> dependsOn;
    private final Element element;
    private final FormattingParameters formattingParameters;
    private final InheritContext inheritContext;
    private final boolean isIgnored;
    private final String javaExpression;
    private final MappingGem mapping;
    private final SelectionParameters selectionParameters;
    private final AnnotationValue sourceAnnotationValue;
    private final String sourceName;
    private final AnnotationValue targetAnnotationValue;
    private final String targetName;

    public static /* synthetic */ LinkedHashSet $r8$lambda$GMX4HiFPZkfx8KWQZMqhiH4Cz8w() {
        return new LinkedHashSet();
    }

    public static class InheritContext {
        private final boolean isForwarded;
        private final boolean isReversed;
        private final Method templateMethod;

        public InheritContext(boolean z, boolean z2, Method method) {
            this.isReversed = z;
            this.isForwarded = z2;
            this.templateMethod = method;
        }

        public boolean isReversed() {
            return this.isReversed;
        }

        public boolean isForwarded() {
            return this.isForwarded;
        }

        public Method getTemplateMethod() {
            return this.templateMethod;
        }
    }

    public static Set<String> getMappingTargetNamesBy(Predicate<MappingOptions> predicate, Set<MappingOptions> set) {
        return (Set) set.stream().filter(predicate).map(new Function() { // from class: org.mapstruct.ap.internal.model.source.MappingOptions$$ExternalSyntheticLambda6
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((MappingOptions) obj).getTargetName();
            }
        }).collect(Collectors.toCollection(new Supplier() { // from class: org.mapstruct.ap.internal.model.source.MappingOptions$$ExternalSyntheticLambda7
            @Override // java.util.function.Supplier
            public final Object get() {
                return MappingOptions.$r8$lambda$GMX4HiFPZkfx8KWQZMqhiH4Cz8w();
            }
        }));
    }

    public static void addInstances(MappingsGem mappingsGem, ExecutableElement executableElement, BeanMappingOptions beanMappingOptions, FormattingMessager formattingMessager, Types types, Set<MappingOptions> set) {
        Iterator<MappingGem> it = mappingsGem.value().getValue().iterator();
        while (it.hasNext()) {
            addInstance(it.next(), executableElement, beanMappingOptions, formattingMessager, types, set);
        }
    }

    public static void addInstance(MappingGem mappingGem, ExecutableElement executableElement, BeanMappingOptions beanMappingOptions, FormattingMessager formattingMessager, Types types, Set<MappingOptions> set) {
        Set setEmptySet;
        if (isConsistent(mappingGem, executableElement, formattingMessager)) {
            String value = mappingGem.source().getValue();
            String value2 = mappingGem.constant().getValue();
            String expression = getExpression(mappingGem, executableElement, formattingMessager);
            String defaultExpression = getDefaultExpression(mappingGem, executableElement, formattingMessager);
            String value3 = mappingGem.dateFormat().getValue();
            String value4 = mappingGem.numberFormat().getValue();
            String value5 = mappingGem.defaultValue().getValue();
            if (mappingGem.dependsOn().hasValue()) {
                setEmptySet = new LinkedHashSet(mappingGem.dependsOn().getValue());
            } else {
                setEmptySet = Collections.emptySet();
            }
            MappingOptions mappingOptions = new MappingOptions(mappingGem.target().getValue(), executableElement, mappingGem.target().getAnnotationValue(), value, mappingGem.source().getAnnotationValue(), value2, expression, defaultExpression, value5, mappingGem.ignore().get().booleanValue(), new FormattingParameters(value3, value4, mappingGem.mirror(), mappingGem.dateFormat().getAnnotationValue(), executableElement), new SelectionParameters(mappingGem.qualifiedBy().get(), mappingGem.qualifiedByName().get(), mappingGem.resultType().getValue(), types), setEmptySet, mappingGem, null, beanMappingOptions);
            if (set.contains(mappingOptions)) {
                formattingMessager.printMessage(executableElement, Message.PROPERTYMAPPING_DUPLICATE_TARGETS, mappingGem.target().get());
            } else {
                set.add(mappingOptions);
            }
        }
    }

    public static MappingOptions forIgnore(String str) {
        return new MappingOptions(str, null, null, null, null, null, null, null, null, true, null, null, Collections.emptySet(), null, null, null);
    }

    private static boolean isConsistent(MappingGem mappingGem, ExecutableElement executableElement, FormattingMessager formattingMessager) {
        Message message;
        if (!mappingGem.target().hasValue()) {
            formattingMessager.printMessage(executableElement, mappingGem.mirror(), mappingGem.target().getAnnotationValue(), Message.PROPERTYMAPPING_EMPTY_TARGET, new Object[0]);
            return false;
        }
        if (mappingGem.source().hasValue() && mappingGem.constant().hasValue()) {
            message = Message.PROPERTYMAPPING_SOURCE_AND_CONSTANT_BOTH_DEFINED;
        } else if (mappingGem.source().hasValue() && mappingGem.expression().hasValue()) {
            message = Message.PROPERTYMAPPING_SOURCE_AND_EXPRESSION_BOTH_DEFINED;
        } else if (mappingGem.expression().hasValue() && mappingGem.constant().hasValue()) {
            message = Message.PROPERTYMAPPING_EXPRESSION_AND_CONSTANT_BOTH_DEFINED;
        } else if (mappingGem.expression().hasValue() && mappingGem.defaultValue().hasValue()) {
            message = Message.PROPERTYMAPPING_EXPRESSION_AND_DEFAULT_VALUE_BOTH_DEFINED;
        } else if (mappingGem.constant().hasValue() && mappingGem.defaultValue().hasValue()) {
            message = Message.PROPERTYMAPPING_CONSTANT_AND_DEFAULT_VALUE_BOTH_DEFINED;
        } else if (mappingGem.expression().hasValue() && mappingGem.defaultExpression().hasValue()) {
            message = Message.PROPERTYMAPPING_EXPRESSION_AND_DEFAULT_EXPRESSION_BOTH_DEFINED;
        } else if (mappingGem.constant().hasValue() && mappingGem.defaultExpression().hasValue()) {
            message = Message.PROPERTYMAPPING_CONSTANT_AND_DEFAULT_EXPRESSION_BOTH_DEFINED;
        } else if (mappingGem.defaultValue().hasValue() && mappingGem.defaultExpression().hasValue()) {
            message = Message.PROPERTYMAPPING_DEFAULT_VALUE_AND_DEFAULT_EXPRESSION_BOTH_DEFINED;
        } else if (mappingGem.expression().hasValue() && (mappingGem.qualifiedByName().hasValue() || mappingGem.qualifiedBy().hasValue())) {
            message = Message.PROPERTYMAPPING_EXPRESSION_AND_QUALIFIER_BOTH_DEFINED;
        } else if (mappingGem.nullValuePropertyMappingStrategy().hasValue() && mappingGem.defaultValue().hasValue()) {
            message = Message.PROPERTYMAPPING_DEFAULT_VALUE_AND_NVPMS;
        } else if (mappingGem.nullValuePropertyMappingStrategy().hasValue() && mappingGem.constant().hasValue()) {
            message = Message.PROPERTYMAPPING_CONSTANT_VALUE_AND_NVPMS;
        } else if (mappingGem.nullValuePropertyMappingStrategy().hasValue() && mappingGem.expression().hasValue()) {
            message = Message.PROPERTYMAPPING_EXPRESSION_VALUE_AND_NVPMS;
        } else if (mappingGem.nullValuePropertyMappingStrategy().hasValue() && mappingGem.defaultExpression().hasValue()) {
            message = Message.PROPERTYMAPPING_DEFAULT_EXPERSSION_AND_NVPMS;
        } else {
            message = (mappingGem.nullValuePropertyMappingStrategy().hasValue() && mappingGem.ignore().hasValue() && mappingGem.ignore().getValue().booleanValue()) ? Message.PROPERTYMAPPING_IGNORE_AND_NVPMS : null;
        }
        if (message == null) {
            return true;
        }
        formattingMessager.printMessage(executableElement, mappingGem.mirror(), message, new Object[0]);
        return false;
    }

    private MappingOptions(String str, Element element, AnnotationValue annotationValue, String str2, AnnotationValue annotationValue2, String str3, String str4, String str5, String str6, boolean z, FormattingParameters formattingParameters, SelectionParameters selectionParameters, Set<String> set, MappingGem mappingGem, InheritContext inheritContext, DelegatingOptions delegatingOptions) {
        super(delegatingOptions);
        this.targetName = str;
        this.element = element;
        this.targetAnnotationValue = annotationValue;
        this.sourceName = str2;
        this.sourceAnnotationValue = annotationValue2;
        this.constant = str3;
        this.javaExpression = str4;
        this.defaultJavaExpression = str5;
        this.defaultValue = str6;
        this.isIgnored = z;
        this.formattingParameters = formattingParameters;
        this.selectionParameters = selectionParameters;
        this.dependsOn = set;
        this.mapping = mappingGem;
        this.inheritContext = inheritContext;
    }

    private static String getExpression(MappingGem mappingGem, ExecutableElement executableElement, FormattingMessager formattingMessager) {
        if (!mappingGem.expression().hasValue()) {
            return null;
        }
        Matcher matcher = JAVA_EXPRESSION.matcher(mappingGem.expression().get());
        if (!matcher.matches()) {
            formattingMessager.printMessage(executableElement, mappingGem.mirror(), mappingGem.expression().getAnnotationValue(), Message.PROPERTYMAPPING_INVALID_EXPRESSION, new Object[0]);
            return null;
        }
        return matcher.group(1).trim();
    }

    private static String getDefaultExpression(MappingGem mappingGem, ExecutableElement executableElement, FormattingMessager formattingMessager) {
        if (!mappingGem.defaultExpression().hasValue()) {
            return null;
        }
        Matcher matcher = JAVA_EXPRESSION.matcher(mappingGem.defaultExpression().get());
        if (!matcher.matches()) {
            formattingMessager.printMessage(executableElement, mappingGem.mirror(), mappingGem.defaultExpression().getAnnotationValue(), Message.PROPERTYMAPPING_INVALID_DEFAULT_EXPRESSION, new Object[0]);
            return null;
        }
        return matcher.group(1).trim();
    }

    public String getTargetName() {
        return this.targetName;
    }

    public AnnotationValue getTargetAnnotationValue() {
        return this.targetAnnotationValue;
    }

    public String getSourceName() {
        return this.sourceName;
    }

    public AnnotationValue getSourceAnnotationValue() {
        return this.sourceAnnotationValue;
    }

    public String getConstant() {
        return this.constant;
    }

    public String getJavaExpression() {
        return this.javaExpression;
    }

    public String getDefaultJavaExpression() {
        return this.defaultJavaExpression;
    }

    public String getDefaultValue() {
        return this.defaultValue;
    }

    public FormattingParameters getFormattingParameters() {
        return this.formattingParameters;
    }

    public SelectionParameters getSelectionParameters() {
        return this.selectionParameters;
    }

    public boolean isIgnored() {
        return this.isIgnored;
    }

    public AnnotationMirror getMirror() {
        return (AnnotationMirror) Optional.ofNullable(this.mapping).map(new Function() { // from class: org.mapstruct.ap.internal.model.source.MappingOptions$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((MappingGem) obj).mirror();
            }
        }).orElse(null);
    }

    public Element getElement() {
        return this.element;
    }

    public AnnotationValue getDependsOnAnnotationValue() {
        return (AnnotationValue) Optional.ofNullable(this.mapping).map(new Function() { // from class: org.mapstruct.ap.internal.model.source.MappingOptions$$ExternalSyntheticLambda4
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((MappingGem) obj).dependsOn();
            }
        }).map(new Function() { // from class: org.mapstruct.ap.internal.model.source.MappingOptions$$ExternalSyntheticLambda5
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((GemValue) obj).getAnnotationValue();
            }
        }).orElse(null);
    }

    public Set<String> getDependsOn() {
        return this.dependsOn;
    }

    public InheritContext getInheritContext() {
        return this.inheritContext;
    }

    @Override // org.mapstruct.ap.internal.model.source.DelegatingOptions
    public NullValueCheckStrategyGem getNullValueCheckStrategy() {
        return (NullValueCheckStrategyGem) Optional.ofNullable(this.mapping).map(new Function() { // from class: org.mapstruct.ap.internal.model.source.MappingOptions$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((MappingGem) obj).nullValueCheckStrategy();
            }
        }).filter(new BeanMappingOptions$$ExternalSyntheticLambda9()).map(new BeanMappingOptions$$ExternalSyntheticLambda15()).map(new BeanMappingOptions$$ExternalSyntheticLambda3()).orElse(next().getNullValueCheckStrategy());
    }

    @Override // org.mapstruct.ap.internal.model.source.DelegatingOptions
    public NullValuePropertyMappingStrategyGem getNullValuePropertyMappingStrategy() {
        return (NullValuePropertyMappingStrategyGem) Optional.ofNullable(this.mapping).map(new Function() { // from class: org.mapstruct.ap.internal.model.source.MappingOptions$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((MappingGem) obj).nullValuePropertyMappingStrategy();
            }
        }).filter(new BeanMappingOptions$$ExternalSyntheticLambda9()).map(new BeanMappingOptions$$ExternalSyntheticLambda15()).map(new BeanMappingOptions$$ExternalSyntheticLambda1()).orElse(next().getNullValuePropertyMappingStrategy());
    }

    @Override // org.mapstruct.ap.internal.model.source.DelegatingOptions
    public MappingControl getMappingControl(final Elements elements) {
        return (MappingControl) Optional.ofNullable(this.mapping).map(new Function() { // from class: org.mapstruct.ap.internal.model.source.MappingOptions$$ExternalSyntheticLambda8
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((MappingGem) obj).mappingControl();
            }
        }).filter(new BeanMappingOptions$$ExternalSyntheticLambda9()).map(new BeanMappingOptions$$ExternalSyntheticLambda10()).map(new Function() { // from class: org.mapstruct.ap.internal.model.source.MappingOptions$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return MappingControl.fromTypeMirror((TypeMirror) obj, elements);
            }
        }).orElse(next().getMappingControl(elements));
    }

    public boolean canInverse() {
        return this.constant == null && this.javaExpression == null && !(this.isIgnored && this.sourceName == null);
    }

    public MappingOptions copyForInverseInheritance(SourceMethod sourceMethod, BeanMappingOptions beanMappingOptions) {
        String str = this.sourceName;
        if (str == null) {
            str = this.targetName;
        }
        return new MappingOptions(str, sourceMethod.getExecutable(), this.targetAnnotationValue, this.sourceName != null ? this.targetName : null, this.sourceAnnotationValue, null, null, null, null, this.isIgnored, this.formattingParameters, this.selectionParameters, Collections.emptySet(), this.mapping, new InheritContext(true, false, sourceMethod), beanMappingOptions);
    }

    public MappingOptions copyForForwardInheritance(SourceMethod sourceMethod, BeanMappingOptions beanMappingOptions) {
        return new MappingOptions(this.targetName, sourceMethod.getExecutable(), this.targetAnnotationValue, this.sourceName, this.sourceAnnotationValue, this.constant, this.javaExpression, this.defaultJavaExpression, this.defaultValue, this.isIgnored, this.formattingParameters, this.selectionParameters, this.dependsOn, this.mapping, new InheritContext(false, true, sourceMethod), beanMappingOptions);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (".".equals(this.targetName) || obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return this.targetName.equals(((MappingOptions) obj).targetName);
    }

    public int hashCode() {
        return Objects.hash(this.targetName);
    }

    public String toString() {
        return "Mapping {\n    sourceName='" + this.sourceName + "',\n    targetName='" + this.targetName + "',\n}";
    }

    @Override // org.mapstruct.ap.internal.model.source.DelegatingOptions
    public boolean hasAnnotation() {
        return this.mapping != null;
    }
}
