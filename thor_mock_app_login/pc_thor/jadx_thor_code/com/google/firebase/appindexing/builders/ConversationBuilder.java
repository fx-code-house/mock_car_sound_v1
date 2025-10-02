package com.google.firebase.appindexing.builders;

import com.google.android.exoplayer2.text.ttml.TtmlNode;

/* compiled from: com.google.firebase:firebase-appindexing@@19.1.0 */
/* loaded from: classes2.dex */
public final class ConversationBuilder extends IndexableBuilder<ConversationBuilder> {
    ConversationBuilder() {
        super("Conversation");
    }

    public final ConversationBuilder setId(String str) {
        return put(TtmlNode.ATTR_ID, str);
    }
}
