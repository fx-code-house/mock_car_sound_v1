package org.mapstruct.ap.internal.processor;

import java.io.IOException;
import javax.annotation.processing.Filer;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.StandardLocation;
import org.mapstruct.ap.internal.model.Decorator;
import org.mapstruct.ap.internal.model.GeneratedType;
import org.mapstruct.ap.internal.model.Mapper;
import org.mapstruct.ap.internal.model.ServicesEntry;
import org.mapstruct.ap.internal.model.source.MapperOptions;
import org.mapstruct.ap.internal.processor.ModelElementProcessor;
import org.mapstruct.ap.internal.writer.ModelWriter;

/* loaded from: classes3.dex */
public class MapperServiceProcessor implements ModelElementProcessor<Mapper, Void> {
    @Override // org.mapstruct.ap.internal.processor.ModelElementProcessor
    public int getPriority() {
        return 10000;
    }

    @Override // org.mapstruct.ap.internal.processor.ModelElementProcessor
    public Void process(ModelElementProcessor.ProcessorContext processorContext, TypeElement typeElement, Mapper mapper) throws IOException {
        boolean zEquals = processorContext.getOptions().isAlwaysGenerateSpi() ? true : "default".equals(MapperOptions.getInstanceOn(typeElement, processorContext.getOptions()).componentModel());
        if (processorContext.isErroneous() || !zEquals || !mapper.hasCustomImplementation()) {
            return null;
        }
        writeToSourceFile(processorContext.getFiler(), mapper);
        return null;
    }

    private void writeToSourceFile(Filer filer, Mapper mapper) throws IOException {
        ModelWriter modelWriter = new ModelWriter();
        Decorator decorator = mapper.getDecorator();
        Decorator decorator2 = mapper;
        if (decorator != null) {
            decorator2 = mapper.getDecorator();
        }
        createSourceFile(getServicesEntry(decorator2), modelWriter, filer);
    }

    private ServicesEntry getServicesEntry(GeneratedType generatedType) {
        return new ServicesEntry(generatedType.getInterfacePackage(), generatedType.getInterfaceName() != null ? generatedType.getInterfaceName() : generatedType.getSuperClassName(), generatedType.getPackageName(), generatedType.getName());
    }

    private void createSourceFile(ServicesEntry servicesEntry, ModelWriter modelWriter, Filer filer) throws IOException {
        try {
            modelWriter.writeModel(filer.createResource(StandardLocation.CLASS_OUTPUT, "", "META-INF/services/" + (servicesEntry.getPackageName() + "." + servicesEntry.getName()), new Element[0]), servicesEntry);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
