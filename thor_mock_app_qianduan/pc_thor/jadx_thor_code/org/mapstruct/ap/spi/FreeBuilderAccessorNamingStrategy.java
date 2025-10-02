package org.mapstruct.ap.spi;

import javax.lang.model.element.ExecutableElement;

/* loaded from: classes3.dex */
public class FreeBuilderAccessorNamingStrategy extends DefaultAccessorNamingStrategy {
    @Override // org.mapstruct.ap.spi.DefaultAccessorNamingStrategy
    protected boolean isFluentSetter(ExecutableElement executableElement) {
        return false;
    }
}
