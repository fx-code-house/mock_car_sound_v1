package com.google.firebase.appindexing.builders;

/* compiled from: com.google.firebase:firebase-appindexing@@19.1.0 */
/* loaded from: classes2.dex */
public final class AggregateRatingBuilder extends IndexableBuilder<AggregateRatingBuilder> {
    AggregateRatingBuilder() {
        super("AggregateRating");
    }

    public final AggregateRatingBuilder setRatingCount(long j) {
        return put("ratingCount", j);
    }

    public final AggregateRatingBuilder setRatingValue(String str) {
        return put("ratingValue", str);
    }
}
