package com.google.firebase.appindexing.builders;

/* compiled from: com.google.firebase:firebase-appindexing@@19.1.0 */
/* loaded from: classes2.dex */
public final class PersonBuilder extends IndexableBuilder<PersonBuilder> {
    PersonBuilder() {
        super("Person");
    }

    public final PersonBuilder setEmail(String str) {
        return put("email", str);
    }

    public final PersonBuilder setTelephone(String str) {
        return put("telephone", str);
    }

    public final PersonBuilder setIsSelf(boolean z) {
        return put("isSelf", z);
    }
}
