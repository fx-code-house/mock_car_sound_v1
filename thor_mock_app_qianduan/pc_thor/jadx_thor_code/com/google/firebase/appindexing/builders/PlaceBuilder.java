package com.google.firebase.appindexing.builders;

/* compiled from: com.google.firebase:firebase-appindexing@@19.1.0 */
/* loaded from: classes2.dex */
public final class PlaceBuilder extends IndexableBuilder<PlaceBuilder> {
    PlaceBuilder() {
        super("Place");
    }

    public final PlaceBuilder setGeo(GeoShapeBuilder geoShapeBuilder) {
        return put("geo", geoShapeBuilder);
    }
}
