package com.google.android.exoplayer2.trackselection;

import android.util.Pair;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.RendererCapabilities;
import com.google.android.exoplayer2.RendererConfiguration;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroup;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.Util;
import java.util.Arrays;

/* loaded from: classes.dex */
public abstract class MappingTrackSelector extends TrackSelector {
    private MappedTrackInfo currentMappedTrackInfo;

    protected abstract Pair<RendererConfiguration[], ExoTrackSelection[]> selectTracks(MappedTrackInfo mappedTrackInfo, int[][][] rendererFormatSupports, int[] rendererMixedMimeTypeAdaptationSupport, MediaSource.MediaPeriodId mediaPeriodId, Timeline timeline) throws ExoPlaybackException;

    public static final class MappedTrackInfo {
        public static final int RENDERER_SUPPORT_EXCEEDS_CAPABILITIES_TRACKS = 2;
        public static final int RENDERER_SUPPORT_NO_TRACKS = 0;
        public static final int RENDERER_SUPPORT_PLAYABLE_TRACKS = 3;
        public static final int RENDERER_SUPPORT_UNSUPPORTED_TRACKS = 1;
        private final int rendererCount;
        private final int[][][] rendererFormatSupports;
        private final int[] rendererMixedMimeTypeAdaptiveSupports;
        private final String[] rendererNames;
        private final TrackGroupArray[] rendererTrackGroups;
        private final int[] rendererTrackTypes;
        private final TrackGroupArray unmappedTrackGroups;

        MappedTrackInfo(String[] rendererNames, int[] rendererTrackTypes, TrackGroupArray[] rendererTrackGroups, int[] rendererMixedMimeTypeAdaptiveSupports, int[][][] rendererFormatSupports, TrackGroupArray unmappedTrackGroups) {
            this.rendererNames = rendererNames;
            this.rendererTrackTypes = rendererTrackTypes;
            this.rendererTrackGroups = rendererTrackGroups;
            this.rendererFormatSupports = rendererFormatSupports;
            this.rendererMixedMimeTypeAdaptiveSupports = rendererMixedMimeTypeAdaptiveSupports;
            this.unmappedTrackGroups = unmappedTrackGroups;
            this.rendererCount = rendererTrackTypes.length;
        }

        public int getRendererCount() {
            return this.rendererCount;
        }

        public String getRendererName(int rendererIndex) {
            return this.rendererNames[rendererIndex];
        }

        public int getRendererType(int rendererIndex) {
            return this.rendererTrackTypes[rendererIndex];
        }

        public TrackGroupArray getTrackGroups(int rendererIndex) {
            return this.rendererTrackGroups[rendererIndex];
        }

        public int getRendererSupport(int rendererIndex) {
            int iMax = 0;
            for (int[] iArr : this.rendererFormatSupports[rendererIndex]) {
                for (int i : iArr) {
                    int formatSupport = RendererCapabilities.getFormatSupport(i);
                    int i2 = 1;
                    if (formatSupport != 0 && formatSupport != 1 && formatSupport != 2) {
                        if (formatSupport != 3) {
                            if (formatSupport == 4) {
                                return 3;
                            }
                            throw new IllegalStateException();
                        }
                        i2 = 2;
                    }
                    iMax = Math.max(iMax, i2);
                }
            }
            return iMax;
        }

        public int getTypeSupport(int trackType) {
            int iMax = 0;
            for (int i = 0; i < this.rendererCount; i++) {
                if (this.rendererTrackTypes[i] == trackType) {
                    iMax = Math.max(iMax, getRendererSupport(i));
                }
            }
            return iMax;
        }

        public int getTrackSupport(int rendererIndex, int groupIndex, int trackIndex) {
            return RendererCapabilities.getFormatSupport(this.rendererFormatSupports[rendererIndex][groupIndex][trackIndex]);
        }

        public int getAdaptiveSupport(int rendererIndex, int groupIndex, boolean includeCapabilitiesExceededTracks) {
            int i = this.rendererTrackGroups[rendererIndex].get(groupIndex).length;
            int[] iArr = new int[i];
            int i2 = 0;
            for (int i3 = 0; i3 < i; i3++) {
                int trackSupport = getTrackSupport(rendererIndex, groupIndex, i3);
                if (trackSupport == 4 || (includeCapabilitiesExceededTracks && trackSupport == 3)) {
                    iArr[i2] = i3;
                    i2++;
                }
            }
            return getAdaptiveSupport(rendererIndex, groupIndex, Arrays.copyOf(iArr, i2));
        }

        public int getAdaptiveSupport(int rendererIndex, int groupIndex, int[] trackIndices) {
            int i = 0;
            int iMin = 16;
            String str = null;
            boolean z = false;
            int i2 = 0;
            while (i < trackIndices.length) {
                String str2 = this.rendererTrackGroups[rendererIndex].get(groupIndex).getFormat(trackIndices[i]).sampleMimeType;
                int i3 = i2 + 1;
                if (i2 == 0) {
                    str = str2;
                } else {
                    z |= !Util.areEqual(str, str2);
                }
                iMin = Math.min(iMin, RendererCapabilities.getAdaptiveSupport(this.rendererFormatSupports[rendererIndex][groupIndex][i]));
                i++;
                i2 = i3;
            }
            return z ? Math.min(iMin, this.rendererMixedMimeTypeAdaptiveSupports[rendererIndex]) : iMin;
        }

        public TrackGroupArray getUnmappedTrackGroups() {
            return this.unmappedTrackGroups;
        }
    }

    public final MappedTrackInfo getCurrentMappedTrackInfo() {
        return this.currentMappedTrackInfo;
    }

    @Override // com.google.android.exoplayer2.trackselection.TrackSelector
    public final void onSelectionActivated(Object info) {
        this.currentMappedTrackInfo = (MappedTrackInfo) info;
    }

    @Override // com.google.android.exoplayer2.trackselection.TrackSelector
    public final TrackSelectorResult selectTracks(RendererCapabilities[] rendererCapabilities, TrackGroupArray trackGroups, MediaSource.MediaPeriodId periodId, Timeline timeline) throws ExoPlaybackException {
        int[] formatSupport;
        int[] iArr = new int[rendererCapabilities.length + 1];
        int length = rendererCapabilities.length + 1;
        TrackGroup[][] trackGroupArr = new TrackGroup[length][];
        int[][][] iArr2 = new int[rendererCapabilities.length + 1][][];
        for (int i = 0; i < length; i++) {
            trackGroupArr[i] = new TrackGroup[trackGroups.length];
            iArr2[i] = new int[trackGroups.length][];
        }
        int[] mixedMimeTypeAdaptationSupports = getMixedMimeTypeAdaptationSupports(rendererCapabilities);
        for (int i2 = 0; i2 < trackGroups.length; i2++) {
            TrackGroup trackGroup = trackGroups.get(i2);
            int iFindRenderer = findRenderer(rendererCapabilities, trackGroup, iArr, MimeTypes.getTrackType(trackGroup.getFormat(0).sampleMimeType) == 5);
            if (iFindRenderer == rendererCapabilities.length) {
                formatSupport = new int[trackGroup.length];
            } else {
                formatSupport = getFormatSupport(rendererCapabilities[iFindRenderer], trackGroup);
            }
            int i3 = iArr[iFindRenderer];
            trackGroupArr[iFindRenderer][i3] = trackGroup;
            iArr2[iFindRenderer][i3] = formatSupport;
            iArr[iFindRenderer] = i3 + 1;
        }
        TrackGroupArray[] trackGroupArrayArr = new TrackGroupArray[rendererCapabilities.length];
        String[] strArr = new String[rendererCapabilities.length];
        int[] iArr3 = new int[rendererCapabilities.length];
        for (int i4 = 0; i4 < rendererCapabilities.length; i4++) {
            int i5 = iArr[i4];
            trackGroupArrayArr[i4] = new TrackGroupArray((TrackGroup[]) Util.nullSafeArrayCopy(trackGroupArr[i4], i5));
            iArr2[i4] = (int[][]) Util.nullSafeArrayCopy(iArr2[i4], i5);
            strArr[i4] = rendererCapabilities[i4].getName();
            iArr3[i4] = rendererCapabilities[i4].getTrackType();
        }
        MappedTrackInfo mappedTrackInfo = new MappedTrackInfo(strArr, iArr3, trackGroupArrayArr, mixedMimeTypeAdaptationSupports, iArr2, new TrackGroupArray((TrackGroup[]) Util.nullSafeArrayCopy(trackGroupArr[rendererCapabilities.length], iArr[rendererCapabilities.length])));
        Pair<RendererConfiguration[], ExoTrackSelection[]> pairSelectTracks = selectTracks(mappedTrackInfo, iArr2, mixedMimeTypeAdaptationSupports, periodId, timeline);
        return new TrackSelectorResult((RendererConfiguration[]) pairSelectTracks.first, (ExoTrackSelection[]) pairSelectTracks.second, mappedTrackInfo);
    }

    private static int findRenderer(RendererCapabilities[] rendererCapabilities, TrackGroup group, int[] rendererTrackGroupCounts, boolean preferUnassociatedRenderer) throws ExoPlaybackException {
        int length = rendererCapabilities.length;
        int i = 0;
        boolean z = true;
        for (int i2 = 0; i2 < rendererCapabilities.length; i2++) {
            RendererCapabilities rendererCapabilities2 = rendererCapabilities[i2];
            int iMax = 0;
            for (int i3 = 0; i3 < group.length; i3++) {
                iMax = Math.max(iMax, RendererCapabilities.getFormatSupport(rendererCapabilities2.supportsFormat(group.getFormat(i3))));
            }
            boolean z2 = rendererTrackGroupCounts[i2] == 0;
            if (iMax > i || (iMax == i && preferUnassociatedRenderer && !z && z2)) {
                length = i2;
                z = z2;
                i = iMax;
            }
        }
        return length;
    }

    private static int[] getFormatSupport(RendererCapabilities rendererCapabilities, TrackGroup group) throws ExoPlaybackException {
        int[] iArr = new int[group.length];
        for (int i = 0; i < group.length; i++) {
            iArr[i] = rendererCapabilities.supportsFormat(group.getFormat(i));
        }
        return iArr;
    }

    private static int[] getMixedMimeTypeAdaptationSupports(RendererCapabilities[] rendererCapabilities) throws ExoPlaybackException {
        int length = rendererCapabilities.length;
        int[] iArr = new int[length];
        for (int i = 0; i < length; i++) {
            iArr[i] = rendererCapabilities[i].supportsMixedMimeTypeAdaptation();
        }
        return iArr;
    }
}
