package org.mapstruct.ap.spi;

import javax.lang.model.type.TypeMirror;

/* loaded from: classes3.dex */
public class NoOpBuilderProvider implements BuilderProvider {
    @Override // org.mapstruct.ap.spi.BuilderProvider
    public BuilderInfo findBuilderInfo(TypeMirror typeMirror) {
        return null;
    }
}
