package com.google.firebase.appindexing.builders;

/* compiled from: com.google.firebase:firebase-appindexing@@19.1.0 */
/* loaded from: classes2.dex */
public final class LocalBusinessBuilder extends IndexableBuilder<LocalBusinessBuilder> {
    LocalBusinessBuilder() {
        super("LocalBusiness");
    }

    LocalBusinessBuilder(String str) {
        super(str);
    }

    public final LocalBusinessBuilder setTelephone(String str) {
        return put("telephone", str);
    }

    public final LocalBusinessBuilder setPriceRange(String str) {
        return put("priceRange", str);
    }

    public final LocalBusinessBuilder setAddress(PostalAddressBuilder postalAddressBuilder) {
        return put("address", postalAddressBuilder);
    }

    public final LocalBusinessBuilder setAggregateRating(AggregateRatingBuilder aggregateRatingBuilder) {
        return put("aggregateRating", aggregateRatingBuilder);
    }

    public final LocalBusinessBuilder setGeo(GeoShapeBuilder geoShapeBuilder) {
        return put("geo", geoShapeBuilder);
    }
}
