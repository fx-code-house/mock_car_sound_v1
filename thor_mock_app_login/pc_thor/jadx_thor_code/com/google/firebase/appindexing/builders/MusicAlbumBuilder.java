package com.google.firebase.appindexing.builders;

/* compiled from: com.google.firebase:firebase-appindexing@@19.1.0 */
/* loaded from: classes2.dex */
public final class MusicAlbumBuilder extends IndexableBuilder<MusicAlbumBuilder> {
    MusicAlbumBuilder() {
        super("MusicAlbum");
    }

    public final MusicAlbumBuilder setByArtist(MusicGroupBuilder musicGroupBuilder) {
        return put("byArtist", musicGroupBuilder);
    }

    public final MusicAlbumBuilder setNumTracks(int i) {
        return put("numTracks", i);
    }

    public final MusicAlbumBuilder setTrack(MusicRecordingBuilder... musicRecordingBuilderArr) {
        return put("track", musicRecordingBuilderArr);
    }
}
