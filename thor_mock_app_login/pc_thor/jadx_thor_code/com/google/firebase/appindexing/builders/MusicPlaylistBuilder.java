package com.google.firebase.appindexing.builders;

/* compiled from: com.google.firebase:firebase-appindexing@@19.1.0 */
/* loaded from: classes2.dex */
public class MusicPlaylistBuilder extends IndexableBuilder<MusicPlaylistBuilder> {
    MusicPlaylistBuilder() {
        super("MusicPlaylist");
    }

    public MusicPlaylistBuilder setNumTracks(int i) {
        return put("numTracks", i);
    }

    public MusicPlaylistBuilder setTrack(MusicRecordingBuilder... musicRecordingBuilderArr) {
        return put("track", musicRecordingBuilderArr);
    }
}
