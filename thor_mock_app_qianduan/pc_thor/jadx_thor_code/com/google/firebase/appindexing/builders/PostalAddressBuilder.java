package com.google.firebase.appindexing.builders;

/* compiled from: com.google.firebase:firebase-appindexing@@19.1.0 */
/* loaded from: classes2.dex */
public final class PostalAddressBuilder extends IndexableBuilder<PostalAddressBuilder> {
    PostalAddressBuilder() {
        super("PostalAddress");
    }

    public final PostalAddressBuilder setAddressCountry(String str) {
        return put("addressCountry", str);
    }

    public final PostalAddressBuilder setAddressLocality(String str) {
        return put("addressLocality", str);
    }

    public final PostalAddressBuilder setPostalCode(String str) {
        return put("postalCode", str);
    }

    public final PostalAddressBuilder setStreetAddress(String str) {
        return put("streetAddress", str);
    }
}
