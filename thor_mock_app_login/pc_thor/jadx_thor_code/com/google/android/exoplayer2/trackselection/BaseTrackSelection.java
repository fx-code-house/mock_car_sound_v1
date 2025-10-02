package com.google.android.exoplayer2.trackselection;

import android.os.SystemClock;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.source.TrackGroup;
import com.google.android.exoplayer2.source.chunk.MediaChunk;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/* loaded from: classes.dex */
public abstract class BaseTrackSelection implements ExoTrackSelection {
    private final long[] excludeUntilTimes;
    private final Format[] formats;
    protected final TrackGroup group;
    private int hashCode;
    protected final int length;
    protected final int[] tracks;
    private final int type;

    @Override // com.google.android.exoplayer2.trackselection.ExoTrackSelection
    public void disable() {
    }

    @Override // com.google.android.exoplayer2.trackselection.ExoTrackSelection
    public void enable() {
    }

    @Override // com.google.android.exoplayer2.trackselection.ExoTrackSelection
    public void onPlaybackSpeed(float playbackSpeed) {
    }

    public BaseTrackSelection(TrackGroup group, int... tracks) {
        this(group, tracks, 0);
    }

    public BaseTrackSelection(TrackGroup group, int[] tracks, int type) {
        int i = 0;
        Assertions.checkState(tracks.length > 0);
        this.type = type;
        this.group = (TrackGroup) Assertions.checkNotNull(group);
        int length = tracks.length;
        this.length = length;
        this.formats = new Format[length];
        for (int i2 = 0; i2 < tracks.length; i2++) {
            this.formats[i2] = group.getFormat(tracks[i2]);
        }
        Arrays.sort(this.formats, new Comparator() { // from class: com.google.android.exoplayer2.trackselection.BaseTrackSelection$$ExternalSyntheticLambda0
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return BaseTrackSelection.lambda$new$0((Format) obj, (Format) obj2);
            }
        });
        this.tracks = new int[this.length];
        while (true) {
            int i3 = this.length;
            if (i < i3) {
                this.tracks[i] = group.indexOf(this.formats[i]);
                i++;
            } else {
                this.excludeUntilTimes = new long[i3];
                return;
            }
        }
    }

    static /* synthetic */ int lambda$new$0(Format format, Format format2) {
        return format2.bitrate - format.bitrate;
    }

    @Override // com.google.android.exoplayer2.trackselection.TrackSelection
    public final int getType() {
        return this.type;
    }

    @Override // com.google.android.exoplayer2.trackselection.TrackSelection
    public final TrackGroup getTrackGroup() {
        return this.group;
    }

    @Override // com.google.android.exoplayer2.trackselection.TrackSelection
    public final int length() {
        return this.tracks.length;
    }

    @Override // com.google.android.exoplayer2.trackselection.TrackSelection
    public final Format getFormat(int index) {
        return this.formats[index];
    }

    @Override // com.google.android.exoplayer2.trackselection.TrackSelection
    public final int getIndexInTrackGroup(int index) {
        return this.tracks[index];
    }

    @Override // com.google.android.exoplayer2.trackselection.TrackSelection
    public final int indexOf(Format format) {
        for (int i = 0; i < this.length; i++) {
            if (this.formats[i] == format) {
                return i;
            }
        }
        return -1;
    }

    @Override // com.google.android.exoplayer2.trackselection.TrackSelection
    public final int indexOf(int indexInTrackGroup) {
        for (int i = 0; i < this.length; i++) {
            if (this.tracks[i] == indexInTrackGroup) {
                return i;
            }
        }
        return -1;
    }

    @Override // com.google.android.exoplayer2.trackselection.ExoTrackSelection
    public final Format getSelectedFormat() {
        return this.formats[getSelectedIndex()];
    }

    @Override // com.google.android.exoplayer2.trackselection.ExoTrackSelection
    public final int getSelectedIndexInTrackGroup() {
        return this.tracks[getSelectedIndex()];
    }

    @Override // com.google.android.exoplayer2.trackselection.ExoTrackSelection
    public int evaluateQueueSize(long playbackPositionUs, List<? extends MediaChunk> queue) {
        return queue.size();
    }

    @Override // com.google.android.exoplayer2.trackselection.ExoTrackSelection
    public boolean blacklist(int index, long exclusionDurationMs) {
        long jElapsedRealtime = SystemClock.elapsedRealtime();
        boolean zIsBlacklisted = isBlacklisted(index, jElapsedRealtime);
        int i = 0;
        while (i < this.length && !zIsBlacklisted) {
            zIsBlacklisted = (i == index || isBlacklisted(i, jElapsedRealtime)) ? false : true;
            i++;
        }
        if (!zIsBlacklisted) {
            return false;
        }
        long[] jArr = this.excludeUntilTimes;
        jArr[index] = Math.max(jArr[index], Util.addWithOverflowDefault(jElapsedRealtime, exclusionDurationMs, Long.MAX_VALUE));
        return true;
    }

    @Override // com.google.android.exoplayer2.trackselection.ExoTrackSelection
    public boolean isBlacklisted(int index, long nowMs) {
        return this.excludeUntilTimes[index] > nowMs;
    }

    public int hashCode() {
        if (this.hashCode == 0) {
            this.hashCode = (System.identityHashCode(this.group) * 31) + Arrays.hashCode(this.tracks);
        }
        return this.hashCode;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        BaseTrackSelection baseTrackSelection = (BaseTrackSelection) obj;
        return this.group == baseTrackSelection.group && Arrays.equals(this.tracks, baseTrackSelection.tracks);
    }
}
