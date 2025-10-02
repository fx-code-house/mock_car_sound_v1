package org.mapstruct.ap.internal.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;
import javax.annotation.processing.Messager;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;
import org.mapstruct.ap.spi.AccessorNamingStrategy;
import org.mapstruct.ap.spi.AstModifyingAnnotationProcessor;
import org.mapstruct.ap.spi.BuilderProvider;
import org.mapstruct.ap.spi.DefaultAccessorNamingStrategy;
import org.mapstruct.ap.spi.DefaultBuilderProvider;
import org.mapstruct.ap.spi.DefaultEnumMappingStrategy;
import org.mapstruct.ap.spi.EnumMappingStrategy;
import org.mapstruct.ap.spi.EnumTransformationStrategy;
import org.mapstruct.ap.spi.FreeBuilderAccessorNamingStrategy;
import org.mapstruct.ap.spi.ImmutablesAccessorNamingStrategy;
import org.mapstruct.ap.spi.ImmutablesBuilderProvider;
import org.mapstruct.ap.spi.MapStructProcessingEnvironment;

/* loaded from: classes3.dex */
public class AnnotationProcessorContext implements MapStructProcessingEnvironment {
    private AccessorNamingUtils accessorNaming;
    private AccessorNamingStrategy accessorNamingStrategy;
    private List<AstModifyingAnnotationProcessor> astModifyingAnnotationProcessors;
    private BuilderProvider builderProvider;
    private Elements elementUtils;
    private EnumMappingStrategy enumMappingStrategy;
    private Map<String, EnumTransformationStrategy> enumTransformationStrategies;
    private boolean initialized;
    private Messager messager;
    private Types typeUtils;
    private boolean verbose;

    public AnnotationProcessorContext(Elements elements, Types types, Messager messager, boolean z) {
        this.astModifyingAnnotationProcessors = java.util.Collections.unmodifiableList(findAstModifyingAnnotationProcessors(messager));
        this.elementUtils = elements;
        this.typeUtils = types;
        this.messager = messager;
        this.verbose = z;
    }

    private void initialize() {
        Object defaultAccessorNamingStrategy;
        Object defaultBuilderProvider;
        if (this.initialized) {
            return;
        }
        if (this.elementUtils.getTypeElement(ImmutablesConstants.IMMUTABLE_FQN) != null) {
            defaultAccessorNamingStrategy = new ImmutablesAccessorNamingStrategy();
            defaultBuilderProvider = new ImmutablesBuilderProvider();
            if (this.verbose) {
                this.messager.printMessage(Diagnostic.Kind.NOTE, "MapStruct: Immutables found on classpath");
            }
        } else if (this.elementUtils.getTypeElement(FreeBuilderConstants.FREE_BUILDER_FQN) != null) {
            defaultAccessorNamingStrategy = new FreeBuilderAccessorNamingStrategy();
            defaultBuilderProvider = new DefaultBuilderProvider();
            if (this.verbose) {
                this.messager.printMessage(Diagnostic.Kind.NOTE, "MapStruct: Freebuilder found on classpath");
            }
        } else {
            defaultAccessorNamingStrategy = new DefaultAccessorNamingStrategy();
            defaultBuilderProvider = new DefaultBuilderProvider();
        }
        AccessorNamingStrategy accessorNamingStrategy = (AccessorNamingStrategy) Services.get(AccessorNamingStrategy.class, defaultAccessorNamingStrategy);
        this.accessorNamingStrategy = accessorNamingStrategy;
        accessorNamingStrategy.init(this);
        if (this.verbose) {
            this.messager.printMessage(Diagnostic.Kind.NOTE, "MapStruct: Using accessor naming strategy: " + this.accessorNamingStrategy.getClass().getCanonicalName());
        }
        BuilderProvider builderProvider = (BuilderProvider) Services.get(BuilderProvider.class, defaultBuilderProvider);
        this.builderProvider = builderProvider;
        builderProvider.init(this);
        if (this.verbose) {
            this.messager.printMessage(Diagnostic.Kind.NOTE, "MapStruct: Using builder provider: " + this.builderProvider.getClass().getCanonicalName());
        }
        this.accessorNaming = new AccessorNamingUtils(this.accessorNamingStrategy);
        EnumMappingStrategy enumMappingStrategy = (EnumMappingStrategy) Services.get(EnumMappingStrategy.class, new DefaultEnumMappingStrategy());
        this.enumMappingStrategy = enumMappingStrategy;
        enumMappingStrategy.init(this);
        if (this.verbose) {
            this.messager.printMessage(Diagnostic.Kind.NOTE, "MapStruct: Using enum naming strategy: " + this.enumMappingStrategy.getClass().getCanonicalName());
        }
        this.enumTransformationStrategies = new LinkedHashMap();
        Iterator it = ServiceLoader.load(EnumTransformationStrategy.class, AnnotationProcessorContext.class.getClassLoader()).iterator();
        while (it.hasNext()) {
            EnumTransformationStrategy enumTransformationStrategy = (EnumTransformationStrategy) it.next();
            String strategyName = enumTransformationStrategy.getStrategyName();
            if (this.enumTransformationStrategies.containsKey(strategyName)) {
                throw new IllegalStateException("Multiple EnumTransformationStrategies are using the same ma,e. Found: " + this.enumTransformationStrategies.get(strategyName) + MessageConstants.AND + enumTransformationStrategy + " for name " + strategyName);
            }
            enumTransformationStrategy.init(this);
            this.enumTransformationStrategies.put(strategyName, enumTransformationStrategy);
        }
        this.initialized = true;
    }

    private static List<AstModifyingAnnotationProcessor> findAstModifyingAnnotationProcessors(Messager messager) {
        ArrayList arrayList = new ArrayList();
        FaultyDelegatingIterator faultyDelegatingIterator = new FaultyDelegatingIterator(messager, ServiceLoader.load(AstModifyingAnnotationProcessor.class, AnnotationProcessorContext.class.getClassLoader()).iterator());
        while (faultyDelegatingIterator.hasNext()) {
            AstModifyingAnnotationProcessor next = faultyDelegatingIterator.next();
            if (next != null) {
                arrayList.add(next);
            }
        }
        return arrayList;
    }

    private static class FaultyDelegatingIterator implements Iterator<AstModifyingAnnotationProcessor> {
        private final Iterator<AstModifyingAnnotationProcessor> delegate;
        private final Messager messager;

        private FaultyDelegatingIterator(Messager messager, Iterator<AstModifyingAnnotationProcessor> it) {
            this.messager = messager;
            this.delegate = it;
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            int i = 5;
            while (i > 0) {
                try {
                    return this.delegate.hasNext();
                } catch (Throwable th) {
                    i--;
                    logFailure(th);
                }
            }
            return false;
        }

        @Override // java.util.Iterator
        public AstModifyingAnnotationProcessor next() {
            try {
                return this.delegate.next();
            } catch (Throwable th) {
                logFailure(th);
                return null;
            }
        }

        private void logFailure(Throwable th) {
            StringWriter stringWriter = new StringWriter();
            th.printStackTrace(new PrintWriter(stringWriter));
            this.messager.printMessage(Diagnostic.Kind.WARNING, "Failed to read AstModifyingAnnotationProcessor. Reading next processor. Reason: " + stringWriter.toString().replace(System.lineSeparator(), "  "));
        }
    }

    @Override // org.mapstruct.ap.spi.MapStructProcessingEnvironment
    public Elements getElementUtils() {
        return this.elementUtils;
    }

    @Override // org.mapstruct.ap.spi.MapStructProcessingEnvironment
    public Types getTypeUtils() {
        return this.typeUtils;
    }

    public List<AstModifyingAnnotationProcessor> getAstModifyingAnnotationProcessors() {
        return this.astModifyingAnnotationProcessors;
    }

    public AccessorNamingUtils getAccessorNaming() {
        initialize();
        return this.accessorNaming;
    }

    public AccessorNamingStrategy getAccessorNamingStrategy() {
        initialize();
        return this.accessorNamingStrategy;
    }

    public EnumMappingStrategy getEnumMappingStrategy() {
        initialize();
        return this.enumMappingStrategy;
    }

    public BuilderProvider getBuilderProvider() {
        initialize();
        return this.builderProvider;
    }

    public Map<String, EnumTransformationStrategy> getEnumTransformationStrategies() {
        initialize();
        return this.enumTransformationStrategies;
    }
}
