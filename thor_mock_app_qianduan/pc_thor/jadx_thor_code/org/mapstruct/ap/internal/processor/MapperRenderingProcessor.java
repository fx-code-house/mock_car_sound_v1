package org.mapstruct.ap.internal.processor;

import java.io.IOException;
import javax.annotation.processing.Filer;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import org.mapstruct.ap.internal.model.GeneratedType;
import org.mapstruct.ap.internal.model.Mapper;
import org.mapstruct.ap.internal.processor.ModelElementProcessor;
import org.mapstruct.ap.internal.writer.ModelWriter;

/* loaded from: classes3.dex */
public class MapperRenderingProcessor implements ModelElementProcessor<Mapper, Mapper> {
    @Override // org.mapstruct.ap.internal.processor.ModelElementProcessor
    public int getPriority() {
        return 9999;
    }

    @Override // org.mapstruct.ap.internal.processor.ModelElementProcessor
    public Mapper process(ModelElementProcessor.ProcessorContext processorContext, TypeElement typeElement, Mapper mapper) throws IOException {
        if (processorContext.isErroneous()) {
            return null;
        }
        writeToSourceFile(processorContext.getFiler(), mapper, typeElement);
        return mapper;
    }

    private void writeToSourceFile(Filer filer, Mapper mapper, TypeElement typeElement) throws IOException {
        ModelWriter modelWriter = new ModelWriter();
        createSourceFile(mapper, modelWriter, filer, typeElement);
        if (mapper.getDecorator() != null) {
            createSourceFile(mapper.getDecorator(), modelWriter, filer, typeElement);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void createSourceFile(GeneratedType generatedType, ModelWriter modelWriter, Filer filer, TypeElement typeElement) throws IOException {
        try {
            modelWriter.writeModel(filer.createSourceFile((generatedType.hasPackageName() ? "" + generatedType.getPackageName() + "." : "") + generatedType.getName(), new Element[]{typeElement}), generatedType);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
