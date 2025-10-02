package com.google.android.exoplayer2.source.hls.playlist;

import android.net.Uri;
import com.google.android.exoplayer2.source.MediaSourceEventListener;
import com.google.android.exoplayer2.source.hls.HlsDataSourceFactory;
import com.google.android.exoplayer2.upstream.LoadErrorHandlingPolicy;
import java.io.IOException;

/* loaded from: classes.dex */
public interface HlsPlaylistTracker {

    public interface Factory {
        HlsPlaylistTracker createTracker(HlsDataSourceFactory dataSourceFactory, LoadErrorHandlingPolicy loadErrorHandlingPolicy, HlsPlaylistParserFactory playlistParserFactory);
    }

    public interface PlaylistEventListener {
        void onPlaylistChanged();

        boolean onPlaylistError(Uri url, LoadErrorHandlingPolicy.LoadErrorInfo loadErrorInfo, boolean forceRetry);
    }

    public interface PrimaryPlaylistListener {
        void onPrimaryPlaylistRefreshed(HlsMediaPlaylist mediaPlaylist);
    }

    void addListener(PlaylistEventListener listener);

    boolean excludeMediaPlaylist(Uri playlistUrl, long exclusionDurationMs);

    long getInitialStartTimeUs();

    HlsMasterPlaylist getMasterPlaylist();

    HlsMediaPlaylist getPlaylistSnapshot(Uri url, boolean isForPlayback);

    boolean isLive();

    boolean isSnapshotValid(Uri url);

    void maybeThrowPlaylistRefreshError(Uri url) throws IOException;

    void maybeThrowPrimaryPlaylistRefreshError() throws IOException;

    void refreshPlaylist(Uri url);

    void removeListener(PlaylistEventListener listener);

    void start(Uri initialPlaylistUri, MediaSourceEventListener.EventDispatcher eventDispatcher, PrimaryPlaylistListener primaryPlaylistListener);

    void stop();

    public static final class PlaylistStuckException extends IOException {
        public final Uri url;

        public PlaylistStuckException(Uri url) {
            this.url = url;
        }
    }

    public static final class PlaylistResetException extends IOException {
        public final Uri url;

        public PlaylistResetException(Uri url) {
            this.url = url;
        }
    }
}
