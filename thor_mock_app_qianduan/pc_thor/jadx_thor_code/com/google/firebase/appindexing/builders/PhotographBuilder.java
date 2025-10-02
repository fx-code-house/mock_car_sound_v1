package com.google.firebase.appindexing.builders;

import java.util.Date;

/* compiled from: com.google.firebase:firebase-appindexing@@19.1.0 */
/* loaded from: classes2.dex */
public final class PhotographBuilder extends IndexableBuilder<PhotographBuilder> {
    PhotographBuilder() {
        super("Photograph");
    }

    public final PhotographBuilder setLocationCreated(PlaceBuilder placeBuilder) {
        return put("locationCreated", placeBuilder);
    }

    public final PhotographBuilder setDateCreated(Date date) {
        return put("dateCreated", date.getTime());
    }
}
