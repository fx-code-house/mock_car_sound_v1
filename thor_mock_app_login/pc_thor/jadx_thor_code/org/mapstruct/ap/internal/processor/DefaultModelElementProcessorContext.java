package org.mapstruct.ap.internal.processor;

import java.util.Map;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.stream.IntStream;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;
import org.mapstruct.ap.internal.model.common.TypeFactory;
import org.mapstruct.ap.internal.option.Options;
import org.mapstruct.ap.internal.processor.DefaultModelElementProcessorContext;
import org.mapstruct.ap.internal.processor.ModelElementProcessor;
import org.mapstruct.ap.internal.util.AccessorNamingUtils;
import org.mapstruct.ap.internal.util.FormattingMessager;
import org.mapstruct.ap.internal.util.Message;
import org.mapstruct.ap.internal.util.RoundContext;
import org.mapstruct.ap.internal.util.workarounds.TypesDecorator;
import org.mapstruct.ap.internal.version.VersionInformation;
import org.mapstruct.ap.spi.EnumMappingStrategy;
import org.mapstruct.ap.spi.EnumTransformationStrategy;

/* loaded from: classes3.dex */
public class DefaultModelElementProcessorContext implements ModelElementProcessor.ProcessorContext {
    private final AccessorNamingUtils accessorNaming;
    private final Types delegatingTypes;
    private final DelegatingMessager messager;
    private final Options options;
    private final ProcessingEnvironment processingEnvironment;
    private final RoundContext roundContext;
    private final TypeFactory typeFactory;
    private final VersionInformation versionInformation;

    public DefaultModelElementProcessorContext(ProcessingEnvironment processingEnvironment, Options options, RoundContext roundContext, Map<String, String> map) {
        this.processingEnvironment = processingEnvironment;
        DelegatingMessager delegatingMessager = new DelegatingMessager(processingEnvironment.getMessager(), options.isVerbose());
        this.messager = delegatingMessager;
        this.accessorNaming = roundContext.getAnnotationProcessorContext().getAccessorNaming();
        DefaultVersionInformation defaultVersionInformationFromProcessingEnvironment = DefaultVersionInformation.fromProcessingEnvironment(processingEnvironment);
        this.versionInformation = defaultVersionInformationFromProcessingEnvironment;
        TypesDecorator typesDecorator = new TypesDecorator(processingEnvironment, defaultVersionInformationFromProcessingEnvironment);
        this.delegatingTypes = typesDecorator;
        this.roundContext = roundContext;
        this.typeFactory = new TypeFactory(processingEnvironment.getElementUtils(), typesDecorator, delegatingMessager, roundContext, map, options.isVerbose());
        this.options = options;
    }

    @Override // org.mapstruct.ap.internal.processor.ModelElementProcessor.ProcessorContext
    public Filer getFiler() {
        return this.processingEnvironment.getFiler();
    }

    @Override // org.mapstruct.ap.internal.processor.ModelElementProcessor.ProcessorContext
    public Types getTypeUtils() {
        return this.delegatingTypes;
    }

    @Override // org.mapstruct.ap.internal.processor.ModelElementProcessor.ProcessorContext
    public Elements getElementUtils() {
        return this.processingEnvironment.getElementUtils();
    }

    @Override // org.mapstruct.ap.internal.processor.ModelElementProcessor.ProcessorContext
    public TypeFactory getTypeFactory() {
        return this.typeFactory;
    }

    @Override // org.mapstruct.ap.internal.processor.ModelElementProcessor.ProcessorContext
    public FormattingMessager getMessager() {
        return this.messager;
    }

    @Override // org.mapstruct.ap.internal.processor.ModelElementProcessor.ProcessorContext
    public AccessorNamingUtils getAccessorNaming() {
        return this.accessorNaming;
    }

    @Override // org.mapstruct.ap.internal.processor.ModelElementProcessor.ProcessorContext
    public Map<String, EnumTransformationStrategy> getEnumTransformationStrategies() {
        return this.roundContext.getAnnotationProcessorContext().getEnumTransformationStrategies();
    }

    @Override // org.mapstruct.ap.internal.processor.ModelElementProcessor.ProcessorContext
    public EnumMappingStrategy getEnumMappingStrategy() {
        return this.roundContext.getAnnotationProcessorContext().getEnumMappingStrategy();
    }

    @Override // org.mapstruct.ap.internal.processor.ModelElementProcessor.ProcessorContext
    public Options getOptions() {
        return this.options;
    }

    @Override // org.mapstruct.ap.internal.processor.ModelElementProcessor.ProcessorContext
    public VersionInformation getVersionInformation() {
        return this.versionInformation;
    }

    @Override // org.mapstruct.ap.internal.processor.ModelElementProcessor.ProcessorContext
    public boolean isErroneous() {
        return this.messager.isErroneous();
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class DelegatingMessager implements FormattingMessager {
        private final Messager delegate;
        private boolean isErroneous = false;
        private final boolean verbose;

        static /* synthetic */ String lambda$note$0(int i) {
            return "-";
        }

        DelegatingMessager(Messager messager, boolean z) {
            this.delegate = messager;
            this.verbose = z;
        }

        @Override // org.mapstruct.ap.internal.util.FormattingMessager
        public void printMessage(Message message, Object... objArr) {
            this.delegate.printMessage(message.getDiagnosticKind(), String.format(message.getDescription(), objArr));
            if (message.getDiagnosticKind() == Diagnostic.Kind.ERROR) {
                this.isErroneous = true;
            }
        }

        @Override // org.mapstruct.ap.internal.util.FormattingMessager
        public void printMessage(Element element, Message message, Object... objArr) {
            this.delegate.printMessage(message.getDiagnosticKind(), String.format(message.getDescription(), objArr), element);
            if (message.getDiagnosticKind() == Diagnostic.Kind.ERROR) {
                this.isErroneous = true;
            }
        }

        @Override // org.mapstruct.ap.internal.util.FormattingMessager
        public void printMessage(Element element, AnnotationMirror annotationMirror, Message message, Object... objArr) {
            if (annotationMirror == null) {
                printMessage(element, message, objArr);
                return;
            }
            this.delegate.printMessage(message.getDiagnosticKind(), String.format(message.getDescription(), objArr), element, annotationMirror);
            if (message.getDiagnosticKind() == Diagnostic.Kind.ERROR) {
                this.isErroneous = true;
            }
        }

        @Override // org.mapstruct.ap.internal.util.FormattingMessager
        public void printMessage(Element element, AnnotationMirror annotationMirror, AnnotationValue annotationValue, Message message, Object... objArr) {
            this.delegate.printMessage(message.getDiagnosticKind(), String.format(message.getDescription(), objArr), element, annotationMirror, annotationValue);
            if (message.getDiagnosticKind() == Diagnostic.Kind.ERROR) {
                this.isErroneous = true;
            }
        }

        @Override // org.mapstruct.ap.internal.util.FormattingMessager
        public void note(int i, Message message, Object... objArr) {
            if (this.verbose) {
                final StringBuilder sb = new StringBuilder();
                IntStream.range(0, i).mapToObj(new IntFunction() { // from class: org.mapstruct.ap.internal.processor.DefaultModelElementProcessorContext$DelegatingMessager$$ExternalSyntheticLambda0
                    @Override // java.util.function.IntFunction
                    public final Object apply(int i2) {
                        return DefaultModelElementProcessorContext.DelegatingMessager.lambda$note$0(i2);
                    }
                }).forEach(new Consumer() { // from class: org.mapstruct.ap.internal.processor.DefaultModelElementProcessorContext$DelegatingMessager$$ExternalSyntheticLambda1
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        sb.append((String) obj);
                    }
                });
                sb.append(" MapStruct: ").append(String.format(message.getDescription(), objArr));
                this.delegate.printMessage(Diagnostic.Kind.NOTE, sb.toString());
            }
        }

        @Override // org.mapstruct.ap.internal.util.FormattingMessager
        public boolean isErroneous() {
            return this.isErroneous;
        }
    }
}
