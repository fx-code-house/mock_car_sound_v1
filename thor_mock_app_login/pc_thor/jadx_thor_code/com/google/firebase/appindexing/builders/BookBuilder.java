package com.google.firebase.appindexing.builders;

/* compiled from: com.google.firebase:firebase-appindexing@@19.1.0 */
/* loaded from: classes2.dex */
public class BookBuilder extends IndexableBuilder<BookBuilder> {
    BookBuilder() {
        super("Book");
    }

    public BookBuilder setAuthor(PersonBuilder... personBuilderArr) {
        return put("author", personBuilderArr);
    }
}
