package org.mapstruct.ap.shaded.freemarker.template;

import java.io.IOException;

/* loaded from: classes3.dex */
public interface TransformControl {
    public static final int END_EVALUATION = 1;
    public static final int EVALUATE_BODY = 1;
    public static final int REPEAT_EVALUATION = 0;
    public static final int SKIP_BODY = 0;

    int afterBody() throws TemplateModelException, IOException;

    void onError(Throwable th) throws Throwable;

    int onStart() throws TemplateModelException, IOException;
}
