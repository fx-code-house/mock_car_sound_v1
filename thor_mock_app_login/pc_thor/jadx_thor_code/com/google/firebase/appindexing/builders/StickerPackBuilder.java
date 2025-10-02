package com.google.firebase.appindexing.builders;

/* compiled from: com.google.firebase:firebase-appindexing@@19.1.0 */
/* loaded from: classes2.dex */
public final class StickerPackBuilder extends IndexableBuilder<StickerPackBuilder> {
    StickerPackBuilder() {
        super("StickerPack");
    }

    public final StickerPackBuilder setHasSticker(StickerBuilder... stickerBuilderArr) {
        return put("hasSticker", stickerBuilderArr);
    }
}
