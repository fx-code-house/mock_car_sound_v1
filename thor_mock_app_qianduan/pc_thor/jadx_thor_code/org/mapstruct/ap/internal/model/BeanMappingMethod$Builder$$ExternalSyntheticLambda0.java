package org.mapstruct.ap.internal.model;

import java.util.function.Function;
import org.mapstruct.ap.internal.model.source.SourceMethod;
import org.mapstruct.ap.internal.model.source.selector.SelectedMethod;

/* compiled from: D8$$SyntheticClass */
/* loaded from: classes3.dex */
public final /* synthetic */ class BeanMappingMethod$Builder$$ExternalSyntheticLambda0 implements Function {
    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        return (SourceMethod) ((SelectedMethod) obj).getMethod();
    }
}
