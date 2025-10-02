package org.mapstruct.ap.internal.processor;

import java.util.Map;
import javax.annotation.processing.Filer;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import org.mapstruct.ap.internal.model.common.TypeFactory;
import org.mapstruct.ap.internal.option.Options;
import org.mapstruct.ap.internal.util.AccessorNamingUtils;
import org.mapstruct.ap.internal.util.FormattingMessager;
import org.mapstruct.ap.internal.version.VersionInformation;
import org.mapstruct.ap.spi.EnumMappingStrategy;
import org.mapstruct.ap.spi.EnumTransformationStrategy;

/* loaded from: classes3.dex */
public interface ModelElementProcessor<P, R> {

    public interface ProcessorContext {
        AccessorNamingUtils getAccessorNaming();

        Elements getElementUtils();

        EnumMappingStrategy getEnumMappingStrategy();

        Map<String, EnumTransformationStrategy> getEnumTransformationStrategies();

        Filer getFiler();

        FormattingMessager getMessager();

        Options getOptions();

        TypeFactory getTypeFactory();

        Types getTypeUtils();

        VersionInformation getVersionInformation();

        boolean isErroneous();
    }

    int getPriority();

    R process(ProcessorContext processorContext, TypeElement typeElement, P p);
}
