package com.google.firebase.appindexing.builders;

import com.google.android.gms.common.internal.Preconditions;
import java.util.Date;

/* compiled from: com.google.firebase:firebase-appindexing@@19.1.0 */
/* loaded from: classes2.dex */
public final class MessageBuilder extends IndexableBuilder<MessageBuilder> {
    MessageBuilder() {
        super("Message");
    }

    MessageBuilder(String str) {
        super(str);
    }

    public final MessageBuilder setText(String str) {
        return put("text", str);
    }

    public final MessageBuilder setDateSent(Date date) {
        Preconditions.checkNotNull(date);
        return put("dateSent", date.getTime());
    }

    public final MessageBuilder setDateReceived(Date date) {
        Preconditions.checkNotNull(date);
        return put("dateReceived", date.getTime());
    }

    public final MessageBuilder setDateRead(Date date) {
        Preconditions.checkNotNull(date);
        return put("dateRead", date.getTime());
    }

    public final MessageBuilder setSender(PersonBuilder personBuilder) {
        return put("sender", personBuilder);
    }

    public final MessageBuilder setRecipient(PersonBuilder... personBuilderArr) {
        return put("recipient", personBuilderArr);
    }

    public final MessageBuilder setMessageAttachment(IndexableBuilder<?>... indexableBuilderArr) {
        return put("messageAttachment", indexableBuilderArr);
    }

    public final MessageBuilder setIsPartOf(ConversationBuilder... conversationBuilderArr) {
        return put("isPartOf", conversationBuilderArr);
    }
}
