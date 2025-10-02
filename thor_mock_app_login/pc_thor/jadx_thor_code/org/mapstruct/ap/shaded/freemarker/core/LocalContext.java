package org.mapstruct.ap.shaded.freemarker.core;

import java.util.Collection;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModelException;

/* loaded from: classes3.dex */
public interface LocalContext {
    TemplateModel getLocalVariable(String str) throws TemplateModelException;

    Collection getLocalVariableNames() throws TemplateModelException;
}
