package com.google.android.exoplayer2.trackselection;

import android.os.SystemClock;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.ExoTrackSelection;
import com.google.android.exoplayer2.upstream.LoadErrorHandlingPolicy;
import com.google.android.exoplayer2.util.MimeTypes;

/* loaded from: classes.dex */
public final class TrackSelectionUtil {

    public interface AdaptiveTrackSelectionFactory {
        ExoTrackSelection createAdaptiveTrackSelection(ExoTrackSelection.Definition trackSelectionDefinition);
    }

    private TrackSelectionUtil() {
    }

    public static ExoTrackSelection[] createTrackSelectionsForDefinitions(ExoTrackSelection.Definition[] definitions, AdaptiveTrackSelectionFactory adaptiveTrackSelectionFactory) {
        ExoTrackSelection[] exoTrackSelectionArr = new ExoTrackSelection[definitions.length];
        boolean z = false;
        for (int i = 0; i < definitions.length; i++) {
            ExoTrackSelection.Definition definition = definitions[i];
            if (definition != null) {
                if (definition.tracks.length > 1 && !z) {
                    exoTrackSelectionArr[i] = adaptiveTrackSelectionFactory.createAdaptiveTrackSelection(definition);
                    z = true;
                } else {
                    exoTrackSelectionArr[i] = new FixedTrackSelection(definition.group, definition.tracks[0], definition.type);
                }
            }
        }
        return exoTrackSelectionArr;
    }

    public static DefaultTrackSelector.Parameters updateParametersWithOverride(DefaultTrackSelector.Parameters parameters, int rendererIndex, TrackGroupArray trackGroupArray, boolean isDisabled, DefaultTrackSelector.SelectionOverride override) {
        DefaultTrackSelector.ParametersBuilder rendererDisabled = parameters.buildUpon().clearSelectionOverrides(rendererIndex).setRendererDisabled(rendererIndex, isDisabled);
        if (override != null) {
            rendererDisabled.setSelectionOverride(rendererIndex, trackGroupArray, override);
        }
        return rendererDisabled.build();
    }

    public static boolean hasTrackOfType(TrackSelectionArray trackSelections, int trackType) {
        for (int i = 0; i < trackSelections.length; i++) {
            TrackSelection trackSelection = trackSelections.get(i);
            if (trackSelection != null) {
                for (int i2 = 0; i2 < trackSelection.length(); i2++) {
                    if (MimeTypes.getTrackType(trackSelection.getFormat(i2).sampleMimeType) == trackType) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static LoadErrorHandlingPolicy.FallbackOptions createFallbackOptions(ExoTrackSelection trackSelection) {
        long jElapsedRealtime = SystemClock.elapsedRealtime();
        int length = trackSelection.length();
        int i = 0;
        for (int i2 = 0; i2 < length; i2++) {
            if (trackSelection.isBlacklisted(i2, jElapsedRealtime)) {
                i++;
            }
        }
        return new LoadErrorHandlingPolicy.FallbackOptions(1, 0, length, i);
    }
}
