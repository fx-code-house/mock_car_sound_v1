package org.mapstruct.ap.spi;

import com.google.firebase.messaging.Constants;
import javax.lang.model.element.ExecutableElement;

/* loaded from: classes3.dex */
public class ImmutablesAccessorNamingStrategy extends DefaultAccessorNamingStrategy {
    @Override // org.mapstruct.ap.spi.DefaultAccessorNamingStrategy
    protected boolean isFluentSetter(ExecutableElement executableElement) {
        return super.isFluentSetter(executableElement) && !executableElement.getSimpleName().toString().equals(Constants.MessagePayloadKeys.FROM);
    }
}
