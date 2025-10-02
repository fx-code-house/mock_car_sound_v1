package org.mapstruct.ap.spi;

import java.util.List;
import javax.lang.model.type.TypeMirror;

/* loaded from: classes3.dex */
public class MoreThanOneBuilderCreationMethodException extends RuntimeException {
    private static final long serialVersionUID = 1;
    private final List<BuilderInfo> builderCreationMethods;
    private final TypeMirror type;

    public MoreThanOneBuilderCreationMethodException(TypeMirror typeMirror, List<BuilderInfo> list) {
        this.type = typeMirror;
        this.builderCreationMethods = list;
    }

    public TypeMirror getType() {
        return this.type;
    }

    public List<BuilderInfo> getBuilderInfo() {
        return this.builderCreationMethods;
    }
}
