package com.google.android.exoplayer2.source.dash;

import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.source.chunk.ChunkSource;
import com.google.android.exoplayer2.source.dash.PlayerEmsgHandler;
import com.google.android.exoplayer2.source.dash.manifest.DashManifest;
import com.google.android.exoplayer2.trackselection.ExoTrackSelection;
import com.google.android.exoplayer2.upstream.LoaderErrorThrower;
import com.google.android.exoplayer2.upstream.TransferListener;
import java.util.List;

/* loaded from: classes.dex */
public interface DashChunkSource extends ChunkSource {

    public interface Factory {
        DashChunkSource createDashChunkSource(LoaderErrorThrower manifestLoaderErrorThrower, DashManifest manifest, BaseUrlExclusionList baseUrlExclusionList, int periodIndex, int[] adaptationSetIndices, ExoTrackSelection trackSelection, int type, long elapsedRealtimeOffsetMs, boolean enableEventMessageTrack, List<Format> closedCaptionFormats, PlayerEmsgHandler.PlayerTrackEmsgHandler playerEmsgHandler, TransferListener transferListener);
    }

    void updateManifest(DashManifest newManifest, int periodIndex);

    void updateTrackSelection(ExoTrackSelection trackSelection);
}
