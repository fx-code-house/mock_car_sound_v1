package org.mapstruct.ap.shaded.freemarker.core;

import org.mapstruct.ap.shaded.freemarker.template.TemplateCollectionModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateHashModelEx;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModelException;
import org.mapstruct.ap.shaded.freemarker.template.TemplateSequenceModel;

/* loaded from: classes3.dex */
class BuiltInsForHashes {

    static class keysBI extends BuiltInForHashEx {
        keysBI() {
        }

        @Override // org.mapstruct.ap.shaded.freemarker.core.BuiltInForHashEx
        TemplateModel calculateResult(TemplateHashModelEx templateHashModelEx, Environment environment) throws InvalidReferenceException, TemplateModelException {
            TemplateCollectionModel templateCollectionModelKeys = templateHashModelEx.keys();
            if (templateCollectionModelKeys != null) {
                return templateCollectionModelKeys instanceof TemplateSequenceModel ? templateCollectionModelKeys : new CollectionAndSequence(templateCollectionModelKeys);
            }
            throw newNullPropertyException("keys", templateHashModelEx, environment);
        }
    }

    static class valuesBI extends BuiltInForHashEx {
        valuesBI() {
        }

        @Override // org.mapstruct.ap.shaded.freemarker.core.BuiltInForHashEx
        TemplateModel calculateResult(TemplateHashModelEx templateHashModelEx, Environment environment) throws InvalidReferenceException, TemplateModelException {
            TemplateCollectionModel templateCollectionModelValues = templateHashModelEx.values();
            if (templateCollectionModelValues != null) {
                return templateCollectionModelValues instanceof TemplateSequenceModel ? templateCollectionModelValues : new CollectionAndSequence(templateCollectionModelValues);
            }
            throw newNullPropertyException("values", templateHashModelEx, environment);
        }
    }

    private BuiltInsForHashes() {
    }
}
