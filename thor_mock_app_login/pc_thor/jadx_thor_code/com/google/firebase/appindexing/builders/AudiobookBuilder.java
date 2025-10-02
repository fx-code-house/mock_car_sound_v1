package com.google.firebase.appindexing.builders;

/* compiled from: com.google.firebase:firebase-appindexing@@19.1.0 */
/* loaded from: classes2.dex */
public class AudiobookBuilder extends IndexableBuilder<AudiobookBuilder> {
    AudiobookBuilder() {
        super("Audiobook");
    }

    public AudiobookBuilder setAuthor(PersonBuilder... personBuilderArr) {
        return put("author", personBuilderArr);
    }

    public AudiobookBuilder setReadBy(PersonBuilder... personBuilderArr) {
        return put("readBy", personBuilderArr);
    }
}
