package com.google.firebase.appindexing.builders;

/* compiled from: com.google.firebase:firebase-appindexing@@19.1.0 */
/* loaded from: classes2.dex */
public final class MusicRecordingBuilder extends IndexableBuilder<MusicRecordingBuilder> {
    MusicRecordingBuilder() {
        super("MusicRecording");
    }

    public final MusicRecordingBuilder setDuration(int i) {
        return put("duration", i);
    }

    public final MusicRecordingBuilder setByArtist(MusicGroupBuilder musicGroupBuilder) {
        return put("byArtist", musicGroupBuilder);
    }

    public final MusicRecordingBuilder setInAlbum(MusicAlbumBuilder musicAlbumBuilder) {
        return put("inAlbum", musicAlbumBuilder);
    }

    public final MusicRecordingBuilder setInPlaylist(MusicPlaylistBuilder... musicPlaylistBuilderArr) {
        return put("inPlaylist", musicPlaylistBuilderArr);
    }
}
