package com.google.firebase.appindexing.builders;

/* compiled from: com.google.firebase:firebase-appindexing@@19.1.0 */
/* loaded from: classes2.dex */
public final class MusicGroupBuilder extends IndexableBuilder<MusicGroupBuilder> {
    MusicGroupBuilder() {
        super("MusicGroup");
    }

    public final MusicGroupBuilder setGenre(String str) {
        return put("genre", str);
    }

    public final MusicGroupBuilder setTrack(MusicRecordingBuilder... musicRecordingBuilderArr) {
        return put("track", musicRecordingBuilderArr);
    }

    public final MusicGroupBuilder setAlbum(MusicAlbumBuilder... musicAlbumBuilderArr) {
        return put("album", musicAlbumBuilderArr);
    }
}
