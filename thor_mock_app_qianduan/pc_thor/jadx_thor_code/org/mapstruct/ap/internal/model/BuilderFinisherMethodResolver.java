package org.mapstruct.ap.internal.model;

import java.util.Collection;
import java.util.Iterator;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;
import org.mapstruct.ap.internal.gem.BuilderGem;
import org.mapstruct.ap.internal.model.common.BuilderType;
import org.mapstruct.ap.internal.model.source.Method;
import org.mapstruct.ap.internal.util.Collections;
import org.mapstruct.ap.internal.util.Extractor;
import org.mapstruct.ap.internal.util.Message;
import org.mapstruct.ap.internal.util.Strings;

/* loaded from: classes3.dex */
public class BuilderFinisherMethodResolver {
    private static final String DEFAULT_BUILD_METHOD_NAME = "build";
    private static final Extractor<ExecutableElement, String> EXECUTABLE_ELEMENT_NAME_EXTRACTOR = new Extractor() { // from class: org.mapstruct.ap.internal.model.BuilderFinisherMethodResolver$$ExternalSyntheticLambda0
        @Override // org.mapstruct.ap.internal.util.Extractor
        public final Object apply(Object obj) {
            return BuilderFinisherMethodResolver.lambda$static$0((ExecutableElement) obj);
        }
    };

    static /* synthetic */ String lambda$static$0(ExecutableElement executableElement) {
        StringBuilder sb = new StringBuilder((CharSequence) executableElement.getSimpleName());
        sb.append('(');
        Iterator it = executableElement.getParameters().iterator();
        while (it.hasNext()) {
            sb.append((VariableElement) it.next());
        }
        sb.append(')');
        return sb.toString();
    }

    private BuilderFinisherMethodResolver() {
    }

    public static MethodReference getBuilderFinisherMethod(Method method, BuilderType builderType, MappingBuilderContext mappingBuilderContext) {
        Collection<ExecutableElement> buildMethods = builderType.getBuildMethods();
        if (buildMethods.isEmpty()) {
            return null;
        }
        BuilderGem builder = method.getOptions().getBeanMapping().getBuilder();
        if (builder == null && buildMethods.size() == 1) {
            return MethodReference.forMethodCall(((ExecutableElement) Collections.first(buildMethods)).getSimpleName().toString());
        }
        String str = builder != null ? builder.buildMethod().get() : DEFAULT_BUILD_METHOD_NAME;
        Iterator<ExecutableElement> it = buildMethods.iterator();
        while (it.hasNext()) {
            String string = it.next().getSimpleName().toString();
            if (string.matches(str)) {
                return MethodReference.forMethodCall(string);
            }
        }
        if (builder == null) {
            mappingBuilderContext.getMessager().printMessage(method.getExecutable(), Message.BUILDER_NO_BUILD_METHOD_FOUND_DEFAULT, str, builderType.getBuilder(), builderType.getBuildingType(), Strings.join(buildMethods, ", ", EXECUTABLE_ELEMENT_NAME_EXTRACTOR));
        } else {
            mappingBuilderContext.getMessager().printMessage(method.getExecutable(), builder.mirror(), Message.BUILDER_NO_BUILD_METHOD_FOUND, str, builderType.getBuilder(), builderType.getBuildingType(), Strings.join(buildMethods, ", ", EXECUTABLE_ELEMENT_NAME_EXTRACTOR));
        }
        return null;
    }
}
