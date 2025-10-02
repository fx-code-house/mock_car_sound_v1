package org.mapstruct.ap.spi;

import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

/* loaded from: classes3.dex */
public interface MapStructProcessingEnvironment {
    Elements getElementUtils();

    Types getTypeUtils();
}
