package com.google.firebase.appindexing.builders;

import java.util.Date;

/* compiled from: com.google.firebase:firebase-appindexing@@19.1.0 */
/* loaded from: classes2.dex */
public final class DigitalDocumentBuilder extends IndexableBuilder<DigitalDocumentBuilder> {
    DigitalDocumentBuilder() {
        super("DigitalDocument");
    }

    DigitalDocumentBuilder(String str) {
        super(str);
    }

    public final DigitalDocumentBuilder setText(String str) {
        return put("text", str);
    }

    public final DigitalDocumentBuilder setAuthor(PersonBuilder... personBuilderArr) {
        return put("author", personBuilderArr);
    }

    public final DigitalDocumentBuilder setDateCreated(Date date) {
        return put("dateCreated", date.getTime());
    }

    public final DigitalDocumentBuilder setDateModified(Date date) {
        return put("dateModified", date.getTime());
    }

    public final DigitalDocumentBuilder setHasDigitalDocumentPermission(DigitalDocumentPermissionBuilder... digitalDocumentPermissionBuilderArr) {
        return put("hasDigitalDocumentPermission", digitalDocumentPermissionBuilderArr);
    }
}
