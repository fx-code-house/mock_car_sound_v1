package com.google.android.exoplayer2.source.hls.playlist;

import com.google.android.exoplayer2.offline.FilteringManifestParser;
import com.google.android.exoplayer2.offline.StreamKey;
import com.google.android.exoplayer2.upstream.ParsingLoadable;
import java.util.List;

/* loaded from: classes.dex */
public final class FilteringHlsPlaylistParserFactory implements HlsPlaylistParserFactory {
    private final HlsPlaylistParserFactory hlsPlaylistParserFactory;
    private final List<StreamKey> streamKeys;

    public FilteringHlsPlaylistParserFactory(HlsPlaylistParserFactory hlsPlaylistParserFactory, List<StreamKey> streamKeys) {
        this.hlsPlaylistParserFactory = hlsPlaylistParserFactory;
        this.streamKeys = streamKeys;
    }

    @Override // com.google.android.exoplayer2.source.hls.playlist.HlsPlaylistParserFactory
    public ParsingLoadable.Parser<HlsPlaylist> createPlaylistParser() {
        return new FilteringManifestParser(this.hlsPlaylistParserFactory.createPlaylistParser(), this.streamKeys);
    }

    @Override // com.google.android.exoplayer2.source.hls.playlist.HlsPlaylistParserFactory
    public ParsingLoadable.Parser<HlsPlaylist> createPlaylistParser(HlsMasterPlaylist masterPlaylist, HlsMediaPlaylist previousMediaPlaylist) {
        return new FilteringManifestParser(this.hlsPlaylistParserFactory.createPlaylistParser(masterPlaylist, previousMediaPlaylist), this.streamKeys);
    }
}
