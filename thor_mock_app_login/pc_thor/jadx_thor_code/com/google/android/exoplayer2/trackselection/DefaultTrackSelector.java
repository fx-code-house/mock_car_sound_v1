package com.google.android.exoplayer2.trackselection;

import android.content.Context;
import android.graphics.Point;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Pair;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.RendererCapabilities;
import com.google.android.exoplayer2.RendererConfiguration;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroup;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.ExoTrackSelection;
import com.google.android.exoplayer2.trackselection.MappingTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionParameters;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import com.google.common.collect.ComparisonChain;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Ordering;
import com.google.common.primitives.Ints;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes.dex */
public class DefaultTrackSelector extends MappingTrackSelector {
    private static final float FRACTION_TO_CONSIDER_FULLSCREEN = 0.98f;
    private final AtomicReference<Parameters> parametersReference;
    private final ExoTrackSelection.Factory trackSelectionFactory;
    private static final int[] NO_TRACKS = new int[0];
    private static final Ordering<Integer> FORMAT_VALUE_ORDERING = Ordering.from(new Comparator() { // from class: com.google.android.exoplayer2.trackselection.DefaultTrackSelector$$ExternalSyntheticLambda0
        @Override // java.util.Comparator
        public final int compare(Object obj, Object obj2) {
            return DefaultTrackSelector.lambda$static$0((Integer) obj, (Integer) obj2);
        }
    });
    private static final Ordering<Integer> NO_ORDER = Ordering.from(new Comparator() { // from class: com.google.android.exoplayer2.trackselection.DefaultTrackSelector$$ExternalSyntheticLambda1
        @Override // java.util.Comparator
        public final int compare(Object obj, Object obj2) {
            return DefaultTrackSelector.lambda$static$1((Integer) obj, (Integer) obj2);
        }
    });

    static /* synthetic */ int lambda$static$1(Integer num, Integer num2) {
        return 0;
    }

    public static final class ParametersBuilder extends TrackSelectionParameters.Builder {
        private boolean allowAudioMixedChannelCountAdaptiveness;
        private boolean allowAudioMixedMimeTypeAdaptiveness;
        private boolean allowAudioMixedSampleRateAdaptiveness;
        private boolean allowMultipleAdaptiveSelections;
        private boolean allowVideoMixedMimeTypeAdaptiveness;
        private boolean allowVideoNonSeamlessAdaptiveness;
        private int disabledTextTrackSelectionFlags;
        private boolean exceedAudioConstraintsIfNecessary;
        private boolean exceedRendererCapabilitiesIfNecessary;
        private boolean exceedVideoConstraintsIfNecessary;
        private final SparseBooleanArray rendererDisabledFlags;
        private final SparseArray<Map<TrackGroupArray, SelectionOverride>> selectionOverrides;
        private boolean tunnelingEnabled;

        @Deprecated
        public ParametersBuilder() {
            this.selectionOverrides = new SparseArray<>();
            this.rendererDisabledFlags = new SparseBooleanArray();
            init();
        }

        public ParametersBuilder(Context context) {
            super(context);
            this.selectionOverrides = new SparseArray<>();
            this.rendererDisabledFlags = new SparseBooleanArray();
            init();
        }

        private ParametersBuilder(Parameters initialValues) {
            super(initialValues);
            this.disabledTextTrackSelectionFlags = initialValues.disabledTextTrackSelectionFlags;
            this.exceedVideoConstraintsIfNecessary = initialValues.exceedVideoConstraintsIfNecessary;
            this.allowVideoMixedMimeTypeAdaptiveness = initialValues.allowVideoMixedMimeTypeAdaptiveness;
            this.allowVideoNonSeamlessAdaptiveness = initialValues.allowVideoNonSeamlessAdaptiveness;
            this.exceedAudioConstraintsIfNecessary = initialValues.exceedAudioConstraintsIfNecessary;
            this.allowAudioMixedMimeTypeAdaptiveness = initialValues.allowAudioMixedMimeTypeAdaptiveness;
            this.allowAudioMixedSampleRateAdaptiveness = initialValues.allowAudioMixedSampleRateAdaptiveness;
            this.allowAudioMixedChannelCountAdaptiveness = initialValues.allowAudioMixedChannelCountAdaptiveness;
            this.exceedRendererCapabilitiesIfNecessary = initialValues.exceedRendererCapabilitiesIfNecessary;
            this.tunnelingEnabled = initialValues.tunnelingEnabled;
            this.allowMultipleAdaptiveSelections = initialValues.allowMultipleAdaptiveSelections;
            this.selectionOverrides = cloneSelectionOverrides(initialValues.selectionOverrides);
            this.rendererDisabledFlags = initialValues.rendererDisabledFlags.clone();
        }

        @Override // com.google.android.exoplayer2.trackselection.TrackSelectionParameters.Builder
        public ParametersBuilder setMaxVideoSizeSd() {
            super.setMaxVideoSizeSd();
            return this;
        }

        @Override // com.google.android.exoplayer2.trackselection.TrackSelectionParameters.Builder
        public ParametersBuilder clearVideoSizeConstraints() {
            super.clearVideoSizeConstraints();
            return this;
        }

        @Override // com.google.android.exoplayer2.trackselection.TrackSelectionParameters.Builder
        public ParametersBuilder setMaxVideoSize(int maxVideoWidth, int maxVideoHeight) {
            super.setMaxVideoSize(maxVideoWidth, maxVideoHeight);
            return this;
        }

        @Override // com.google.android.exoplayer2.trackselection.TrackSelectionParameters.Builder
        public ParametersBuilder setMaxVideoFrameRate(int maxVideoFrameRate) {
            super.setMaxVideoFrameRate(maxVideoFrameRate);
            return this;
        }

        @Override // com.google.android.exoplayer2.trackselection.TrackSelectionParameters.Builder
        public ParametersBuilder setMaxVideoBitrate(int maxVideoBitrate) {
            super.setMaxVideoBitrate(maxVideoBitrate);
            return this;
        }

        @Override // com.google.android.exoplayer2.trackselection.TrackSelectionParameters.Builder
        public ParametersBuilder setMinVideoSize(int minVideoWidth, int minVideoHeight) {
            super.setMinVideoSize(minVideoWidth, minVideoHeight);
            return this;
        }

        @Override // com.google.android.exoplayer2.trackselection.TrackSelectionParameters.Builder
        public ParametersBuilder setMinVideoFrameRate(int minVideoFrameRate) {
            super.setMinVideoFrameRate(minVideoFrameRate);
            return this;
        }

        @Override // com.google.android.exoplayer2.trackselection.TrackSelectionParameters.Builder
        public ParametersBuilder setMinVideoBitrate(int minVideoBitrate) {
            super.setMinVideoBitrate(minVideoBitrate);
            return this;
        }

        public ParametersBuilder setExceedVideoConstraintsIfNecessary(boolean exceedVideoConstraintsIfNecessary) {
            this.exceedVideoConstraintsIfNecessary = exceedVideoConstraintsIfNecessary;
            return this;
        }

        public ParametersBuilder setAllowVideoMixedMimeTypeAdaptiveness(boolean allowVideoMixedMimeTypeAdaptiveness) {
            this.allowVideoMixedMimeTypeAdaptiveness = allowVideoMixedMimeTypeAdaptiveness;
            return this;
        }

        public ParametersBuilder setAllowVideoNonSeamlessAdaptiveness(boolean allowVideoNonSeamlessAdaptiveness) {
            this.allowVideoNonSeamlessAdaptiveness = allowVideoNonSeamlessAdaptiveness;
            return this;
        }

        @Override // com.google.android.exoplayer2.trackselection.TrackSelectionParameters.Builder
        public ParametersBuilder setViewportSizeToPhysicalDisplaySize(Context context, boolean viewportOrientationMayChange) {
            super.setViewportSizeToPhysicalDisplaySize(context, viewportOrientationMayChange);
            return this;
        }

        @Override // com.google.android.exoplayer2.trackselection.TrackSelectionParameters.Builder
        public ParametersBuilder clearViewportSizeConstraints() {
            super.clearViewportSizeConstraints();
            return this;
        }

        @Override // com.google.android.exoplayer2.trackselection.TrackSelectionParameters.Builder
        public ParametersBuilder setViewportSize(int viewportWidth, int viewportHeight, boolean viewportOrientationMayChange) {
            super.setViewportSize(viewportWidth, viewportHeight, viewportOrientationMayChange);
            return this;
        }

        @Override // com.google.android.exoplayer2.trackselection.TrackSelectionParameters.Builder
        public ParametersBuilder setPreferredVideoMimeType(String mimeType) {
            super.setPreferredVideoMimeType(mimeType);
            return this;
        }

        @Override // com.google.android.exoplayer2.trackselection.TrackSelectionParameters.Builder
        public ParametersBuilder setPreferredVideoMimeTypes(String... mimeTypes) {
            super.setPreferredVideoMimeTypes(mimeTypes);
            return this;
        }

        @Override // com.google.android.exoplayer2.trackselection.TrackSelectionParameters.Builder
        public ParametersBuilder setPreferredAudioLanguage(String preferredAudioLanguage) {
            super.setPreferredAudioLanguage(preferredAudioLanguage);
            return this;
        }

        @Override // com.google.android.exoplayer2.trackselection.TrackSelectionParameters.Builder
        public ParametersBuilder setPreferredAudioLanguages(String... preferredAudioLanguages) {
            super.setPreferredAudioLanguages(preferredAudioLanguages);
            return this;
        }

        @Override // com.google.android.exoplayer2.trackselection.TrackSelectionParameters.Builder
        public ParametersBuilder setPreferredAudioRoleFlags(int preferredAudioRoleFlags) {
            super.setPreferredAudioRoleFlags(preferredAudioRoleFlags);
            return this;
        }

        @Override // com.google.android.exoplayer2.trackselection.TrackSelectionParameters.Builder
        public ParametersBuilder setMaxAudioChannelCount(int maxAudioChannelCount) {
            super.setMaxAudioChannelCount(maxAudioChannelCount);
            return this;
        }

        @Override // com.google.android.exoplayer2.trackselection.TrackSelectionParameters.Builder
        public ParametersBuilder setMaxAudioBitrate(int maxAudioBitrate) {
            super.setMaxAudioBitrate(maxAudioBitrate);
            return this;
        }

        public ParametersBuilder setExceedAudioConstraintsIfNecessary(boolean exceedAudioConstraintsIfNecessary) {
            this.exceedAudioConstraintsIfNecessary = exceedAudioConstraintsIfNecessary;
            return this;
        }

        public ParametersBuilder setAllowAudioMixedMimeTypeAdaptiveness(boolean allowAudioMixedMimeTypeAdaptiveness) {
            this.allowAudioMixedMimeTypeAdaptiveness = allowAudioMixedMimeTypeAdaptiveness;
            return this;
        }

        public ParametersBuilder setAllowAudioMixedSampleRateAdaptiveness(boolean allowAudioMixedSampleRateAdaptiveness) {
            this.allowAudioMixedSampleRateAdaptiveness = allowAudioMixedSampleRateAdaptiveness;
            return this;
        }

        public ParametersBuilder setAllowAudioMixedChannelCountAdaptiveness(boolean allowAudioMixedChannelCountAdaptiveness) {
            this.allowAudioMixedChannelCountAdaptiveness = allowAudioMixedChannelCountAdaptiveness;
            return this;
        }

        @Override // com.google.android.exoplayer2.trackselection.TrackSelectionParameters.Builder
        public ParametersBuilder setPreferredAudioMimeType(String mimeType) {
            super.setPreferredAudioMimeType(mimeType);
            return this;
        }

        @Override // com.google.android.exoplayer2.trackselection.TrackSelectionParameters.Builder
        public ParametersBuilder setPreferredAudioMimeTypes(String... mimeTypes) {
            super.setPreferredAudioMimeTypes(mimeTypes);
            return this;
        }

        @Override // com.google.android.exoplayer2.trackselection.TrackSelectionParameters.Builder
        public ParametersBuilder setPreferredTextLanguageAndRoleFlagsToCaptioningManagerSettings(Context context) {
            super.setPreferredTextLanguageAndRoleFlagsToCaptioningManagerSettings(context);
            return this;
        }

        @Override // com.google.android.exoplayer2.trackselection.TrackSelectionParameters.Builder
        public ParametersBuilder setPreferredTextLanguage(String preferredTextLanguage) {
            super.setPreferredTextLanguage(preferredTextLanguage);
            return this;
        }

        @Override // com.google.android.exoplayer2.trackselection.TrackSelectionParameters.Builder
        public ParametersBuilder setPreferredTextLanguages(String... preferredTextLanguages) {
            super.setPreferredTextLanguages(preferredTextLanguages);
            return this;
        }

        @Override // com.google.android.exoplayer2.trackselection.TrackSelectionParameters.Builder
        public ParametersBuilder setPreferredTextRoleFlags(int preferredTextRoleFlags) {
            super.setPreferredTextRoleFlags(preferredTextRoleFlags);
            return this;
        }

        @Override // com.google.android.exoplayer2.trackselection.TrackSelectionParameters.Builder
        public ParametersBuilder setSelectUndeterminedTextLanguage(boolean selectUndeterminedTextLanguage) {
            super.setSelectUndeterminedTextLanguage(selectUndeterminedTextLanguage);
            return this;
        }

        public ParametersBuilder setDisabledTextTrackSelectionFlags(int disabledTextTrackSelectionFlags) {
            this.disabledTextTrackSelectionFlags = disabledTextTrackSelectionFlags;
            return this;
        }

        @Override // com.google.android.exoplayer2.trackselection.TrackSelectionParameters.Builder
        public ParametersBuilder setForceLowestBitrate(boolean forceLowestBitrate) {
            super.setForceLowestBitrate(forceLowestBitrate);
            return this;
        }

        @Override // com.google.android.exoplayer2.trackselection.TrackSelectionParameters.Builder
        public ParametersBuilder setForceHighestSupportedBitrate(boolean forceHighestSupportedBitrate) {
            super.setForceHighestSupportedBitrate(forceHighestSupportedBitrate);
            return this;
        }

        public ParametersBuilder setExceedRendererCapabilitiesIfNecessary(boolean exceedRendererCapabilitiesIfNecessary) {
            this.exceedRendererCapabilitiesIfNecessary = exceedRendererCapabilitiesIfNecessary;
            return this;
        }

        public ParametersBuilder setTunnelingEnabled(boolean tunnelingEnabled) {
            this.tunnelingEnabled = tunnelingEnabled;
            return this;
        }

        public ParametersBuilder setAllowMultipleAdaptiveSelections(boolean allowMultipleAdaptiveSelections) {
            this.allowMultipleAdaptiveSelections = allowMultipleAdaptiveSelections;
            return this;
        }

        public final ParametersBuilder setRendererDisabled(int rendererIndex, boolean disabled) {
            if (this.rendererDisabledFlags.get(rendererIndex) == disabled) {
                return this;
            }
            if (disabled) {
                this.rendererDisabledFlags.put(rendererIndex, true);
            } else {
                this.rendererDisabledFlags.delete(rendererIndex);
            }
            return this;
        }

        public final ParametersBuilder setSelectionOverride(int rendererIndex, TrackGroupArray groups, SelectionOverride override) {
            Map<TrackGroupArray, SelectionOverride> map = this.selectionOverrides.get(rendererIndex);
            if (map == null) {
                map = new HashMap<>();
                this.selectionOverrides.put(rendererIndex, map);
            }
            if (map.containsKey(groups) && Util.areEqual(map.get(groups), override)) {
                return this;
            }
            map.put(groups, override);
            return this;
        }

        public final ParametersBuilder clearSelectionOverride(int rendererIndex, TrackGroupArray groups) {
            Map<TrackGroupArray, SelectionOverride> map = this.selectionOverrides.get(rendererIndex);
            if (map != null && map.containsKey(groups)) {
                map.remove(groups);
                if (map.isEmpty()) {
                    this.selectionOverrides.remove(rendererIndex);
                }
            }
            return this;
        }

        public final ParametersBuilder clearSelectionOverrides(int rendererIndex) {
            Map<TrackGroupArray, SelectionOverride> map = this.selectionOverrides.get(rendererIndex);
            if (map != null && !map.isEmpty()) {
                this.selectionOverrides.remove(rendererIndex);
            }
            return this;
        }

        public final ParametersBuilder clearSelectionOverrides() {
            if (this.selectionOverrides.size() == 0) {
                return this;
            }
            this.selectionOverrides.clear();
            return this;
        }

        @Override // com.google.android.exoplayer2.trackselection.TrackSelectionParameters.Builder
        public Parameters build() {
            return new Parameters(this);
        }

        private void init() {
            this.exceedVideoConstraintsIfNecessary = true;
            this.allowVideoMixedMimeTypeAdaptiveness = false;
            this.allowVideoNonSeamlessAdaptiveness = true;
            this.exceedAudioConstraintsIfNecessary = true;
            this.allowAudioMixedMimeTypeAdaptiveness = false;
            this.allowAudioMixedSampleRateAdaptiveness = false;
            this.allowAudioMixedChannelCountAdaptiveness = false;
            this.disabledTextTrackSelectionFlags = 0;
            this.exceedRendererCapabilitiesIfNecessary = true;
            this.tunnelingEnabled = false;
            this.allowMultipleAdaptiveSelections = true;
        }

        private static SparseArray<Map<TrackGroupArray, SelectionOverride>> cloneSelectionOverrides(SparseArray<Map<TrackGroupArray, SelectionOverride>> selectionOverrides) {
            SparseArray<Map<TrackGroupArray, SelectionOverride>> sparseArray = new SparseArray<>();
            for (int i = 0; i < selectionOverrides.size(); i++) {
                sparseArray.put(selectionOverrides.keyAt(i), new HashMap(selectionOverrides.valueAt(i)));
            }
            return sparseArray;
        }
    }

    public static final class Parameters extends TrackSelectionParameters implements Parcelable {
        public static final Parcelable.Creator<Parameters> CREATOR;

        @Deprecated
        public static final Parameters DEFAULT;
        public static final Parameters DEFAULT_WITHOUT_CONTEXT;
        public final boolean allowAudioMixedChannelCountAdaptiveness;
        public final boolean allowAudioMixedMimeTypeAdaptiveness;
        public final boolean allowAudioMixedSampleRateAdaptiveness;
        public final boolean allowMultipleAdaptiveSelections;
        public final boolean allowVideoMixedMimeTypeAdaptiveness;
        public final boolean allowVideoNonSeamlessAdaptiveness;
        public final int disabledTextTrackSelectionFlags;
        public final boolean exceedAudioConstraintsIfNecessary;
        public final boolean exceedRendererCapabilitiesIfNecessary;
        public final boolean exceedVideoConstraintsIfNecessary;
        private final SparseBooleanArray rendererDisabledFlags;
        private final SparseArray<Map<TrackGroupArray, SelectionOverride>> selectionOverrides;
        public final boolean tunnelingEnabled;

        @Override // com.google.android.exoplayer2.trackselection.TrackSelectionParameters, android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        static {
            Parameters parametersBuild = new ParametersBuilder().build();
            DEFAULT_WITHOUT_CONTEXT = parametersBuild;
            DEFAULT = parametersBuild;
            CREATOR = new Parcelable.Creator<Parameters>() { // from class: com.google.android.exoplayer2.trackselection.DefaultTrackSelector.Parameters.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public Parameters createFromParcel(Parcel in) {
                    return new Parameters(in);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public Parameters[] newArray(int size) {
                    return new Parameters[size];
                }
            };
        }

        public static Parameters getDefaults(Context context) {
            return new ParametersBuilder(context).build();
        }

        private Parameters(ParametersBuilder builder) {
            super(builder);
            this.exceedVideoConstraintsIfNecessary = builder.exceedVideoConstraintsIfNecessary;
            this.allowVideoMixedMimeTypeAdaptiveness = builder.allowVideoMixedMimeTypeAdaptiveness;
            this.allowVideoNonSeamlessAdaptiveness = builder.allowVideoNonSeamlessAdaptiveness;
            this.exceedAudioConstraintsIfNecessary = builder.exceedAudioConstraintsIfNecessary;
            this.allowAudioMixedMimeTypeAdaptiveness = builder.allowAudioMixedMimeTypeAdaptiveness;
            this.allowAudioMixedSampleRateAdaptiveness = builder.allowAudioMixedSampleRateAdaptiveness;
            this.allowAudioMixedChannelCountAdaptiveness = builder.allowAudioMixedChannelCountAdaptiveness;
            this.disabledTextTrackSelectionFlags = builder.disabledTextTrackSelectionFlags;
            this.exceedRendererCapabilitiesIfNecessary = builder.exceedRendererCapabilitiesIfNecessary;
            this.tunnelingEnabled = builder.tunnelingEnabled;
            this.allowMultipleAdaptiveSelections = builder.allowMultipleAdaptiveSelections;
            this.selectionOverrides = builder.selectionOverrides;
            this.rendererDisabledFlags = builder.rendererDisabledFlags;
        }

        Parameters(Parcel in) {
            super(in);
            this.exceedVideoConstraintsIfNecessary = Util.readBoolean(in);
            this.allowVideoMixedMimeTypeAdaptiveness = Util.readBoolean(in);
            this.allowVideoNonSeamlessAdaptiveness = Util.readBoolean(in);
            this.exceedAudioConstraintsIfNecessary = Util.readBoolean(in);
            this.allowAudioMixedMimeTypeAdaptiveness = Util.readBoolean(in);
            this.allowAudioMixedSampleRateAdaptiveness = Util.readBoolean(in);
            this.allowAudioMixedChannelCountAdaptiveness = Util.readBoolean(in);
            this.disabledTextTrackSelectionFlags = in.readInt();
            this.exceedRendererCapabilitiesIfNecessary = Util.readBoolean(in);
            this.tunnelingEnabled = Util.readBoolean(in);
            this.allowMultipleAdaptiveSelections = Util.readBoolean(in);
            this.selectionOverrides = readSelectionOverrides(in);
            this.rendererDisabledFlags = (SparseBooleanArray) Util.castNonNull(in.readSparseBooleanArray());
        }

        public final boolean getRendererDisabled(int rendererIndex) {
            return this.rendererDisabledFlags.get(rendererIndex);
        }

        public final boolean hasSelectionOverride(int rendererIndex, TrackGroupArray groups) {
            Map<TrackGroupArray, SelectionOverride> map = this.selectionOverrides.get(rendererIndex);
            return map != null && map.containsKey(groups);
        }

        public final SelectionOverride getSelectionOverride(int rendererIndex, TrackGroupArray groups) {
            Map<TrackGroupArray, SelectionOverride> map = this.selectionOverrides.get(rendererIndex);
            if (map != null) {
                return map.get(groups);
            }
            return null;
        }

        @Override // com.google.android.exoplayer2.trackselection.TrackSelectionParameters
        public ParametersBuilder buildUpon() {
            return new ParametersBuilder(this);
        }

        @Override // com.google.android.exoplayer2.trackselection.TrackSelectionParameters
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            Parameters parameters = (Parameters) obj;
            return super.equals(parameters) && this.exceedVideoConstraintsIfNecessary == parameters.exceedVideoConstraintsIfNecessary && this.allowVideoMixedMimeTypeAdaptiveness == parameters.allowVideoMixedMimeTypeAdaptiveness && this.allowVideoNonSeamlessAdaptiveness == parameters.allowVideoNonSeamlessAdaptiveness && this.exceedAudioConstraintsIfNecessary == parameters.exceedAudioConstraintsIfNecessary && this.allowAudioMixedMimeTypeAdaptiveness == parameters.allowAudioMixedMimeTypeAdaptiveness && this.allowAudioMixedSampleRateAdaptiveness == parameters.allowAudioMixedSampleRateAdaptiveness && this.allowAudioMixedChannelCountAdaptiveness == parameters.allowAudioMixedChannelCountAdaptiveness && this.disabledTextTrackSelectionFlags == parameters.disabledTextTrackSelectionFlags && this.exceedRendererCapabilitiesIfNecessary == parameters.exceedRendererCapabilitiesIfNecessary && this.tunnelingEnabled == parameters.tunnelingEnabled && this.allowMultipleAdaptiveSelections == parameters.allowMultipleAdaptiveSelections && areRendererDisabledFlagsEqual(this.rendererDisabledFlags, parameters.rendererDisabledFlags) && areSelectionOverridesEqual(this.selectionOverrides, parameters.selectionOverrides);
        }

        @Override // com.google.android.exoplayer2.trackselection.TrackSelectionParameters
        public int hashCode() {
            return ((((((((((((((((((((((super.hashCode() + 31) * 31) + (this.exceedVideoConstraintsIfNecessary ? 1 : 0)) * 31) + (this.allowVideoMixedMimeTypeAdaptiveness ? 1 : 0)) * 31) + (this.allowVideoNonSeamlessAdaptiveness ? 1 : 0)) * 31) + (this.exceedAudioConstraintsIfNecessary ? 1 : 0)) * 31) + (this.allowAudioMixedMimeTypeAdaptiveness ? 1 : 0)) * 31) + (this.allowAudioMixedSampleRateAdaptiveness ? 1 : 0)) * 31) + (this.allowAudioMixedChannelCountAdaptiveness ? 1 : 0)) * 31) + this.disabledTextTrackSelectionFlags) * 31) + (this.exceedRendererCapabilitiesIfNecessary ? 1 : 0)) * 31) + (this.tunnelingEnabled ? 1 : 0)) * 31) + (this.allowMultipleAdaptiveSelections ? 1 : 0);
        }

        @Override // com.google.android.exoplayer2.trackselection.TrackSelectionParameters, android.os.Parcelable
        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            Util.writeBoolean(dest, this.exceedVideoConstraintsIfNecessary);
            Util.writeBoolean(dest, this.allowVideoMixedMimeTypeAdaptiveness);
            Util.writeBoolean(dest, this.allowVideoNonSeamlessAdaptiveness);
            Util.writeBoolean(dest, this.exceedAudioConstraintsIfNecessary);
            Util.writeBoolean(dest, this.allowAudioMixedMimeTypeAdaptiveness);
            Util.writeBoolean(dest, this.allowAudioMixedSampleRateAdaptiveness);
            Util.writeBoolean(dest, this.allowAudioMixedChannelCountAdaptiveness);
            dest.writeInt(this.disabledTextTrackSelectionFlags);
            Util.writeBoolean(dest, this.exceedRendererCapabilitiesIfNecessary);
            Util.writeBoolean(dest, this.tunnelingEnabled);
            Util.writeBoolean(dest, this.allowMultipleAdaptiveSelections);
            writeSelectionOverridesToParcel(dest, this.selectionOverrides);
            dest.writeSparseBooleanArray(this.rendererDisabledFlags);
        }

        private static SparseArray<Map<TrackGroupArray, SelectionOverride>> readSelectionOverrides(Parcel in) {
            int i = in.readInt();
            SparseArray<Map<TrackGroupArray, SelectionOverride>> sparseArray = new SparseArray<>(i);
            for (int i2 = 0; i2 < i; i2++) {
                int i3 = in.readInt();
                int i4 = in.readInt();
                HashMap map = new HashMap(i4);
                for (int i5 = 0; i5 < i4; i5++) {
                    map.put((TrackGroupArray) Assertions.checkNotNull((TrackGroupArray) in.readParcelable(TrackGroupArray.class.getClassLoader())), (SelectionOverride) in.readParcelable(SelectionOverride.class.getClassLoader()));
                }
                sparseArray.put(i3, map);
            }
            return sparseArray;
        }

        private static void writeSelectionOverridesToParcel(Parcel dest, SparseArray<Map<TrackGroupArray, SelectionOverride>> selectionOverrides) {
            int size = selectionOverrides.size();
            dest.writeInt(size);
            for (int i = 0; i < size; i++) {
                int iKeyAt = selectionOverrides.keyAt(i);
                Map<TrackGroupArray, SelectionOverride> mapValueAt = selectionOverrides.valueAt(i);
                int size2 = mapValueAt.size();
                dest.writeInt(iKeyAt);
                dest.writeInt(size2);
                for (Map.Entry<TrackGroupArray, SelectionOverride> entry : mapValueAt.entrySet()) {
                    dest.writeParcelable(entry.getKey(), 0);
                    dest.writeParcelable(entry.getValue(), 0);
                }
            }
        }

        private static boolean areRendererDisabledFlagsEqual(SparseBooleanArray first, SparseBooleanArray second) {
            int size = first.size();
            if (second.size() != size) {
                return false;
            }
            for (int i = 0; i < size; i++) {
                if (second.indexOfKey(first.keyAt(i)) < 0) {
                    return false;
                }
            }
            return true;
        }

        private static boolean areSelectionOverridesEqual(SparseArray<Map<TrackGroupArray, SelectionOverride>> first, SparseArray<Map<TrackGroupArray, SelectionOverride>> second) {
            int size = first.size();
            if (second.size() != size) {
                return false;
            }
            for (int i = 0; i < size; i++) {
                int iIndexOfKey = second.indexOfKey(first.keyAt(i));
                if (iIndexOfKey < 0 || !areSelectionOverridesEqual(first.valueAt(i), second.valueAt(iIndexOfKey))) {
                    return false;
                }
            }
            return true;
        }

        private static boolean areSelectionOverridesEqual(Map<TrackGroupArray, SelectionOverride> first, Map<TrackGroupArray, SelectionOverride> second) {
            if (second.size() != first.size()) {
                return false;
            }
            for (Map.Entry<TrackGroupArray, SelectionOverride> entry : first.entrySet()) {
                TrackGroupArray key = entry.getKey();
                if (!second.containsKey(key) || !Util.areEqual(entry.getValue(), second.get(key))) {
                    return false;
                }
            }
            return true;
        }
    }

    public static final class SelectionOverride implements Parcelable {
        public static final Parcelable.Creator<SelectionOverride> CREATOR = new Parcelable.Creator<SelectionOverride>() { // from class: com.google.android.exoplayer2.trackselection.DefaultTrackSelector.SelectionOverride.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public SelectionOverride createFromParcel(Parcel in) {
                return new SelectionOverride(in);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public SelectionOverride[] newArray(int size) {
                return new SelectionOverride[size];
            }
        };
        public final int groupIndex;
        public final int length;
        public final int[] tracks;
        public final int type;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public SelectionOverride(int groupIndex, int... tracks) {
            this(groupIndex, tracks, 0);
        }

        public SelectionOverride(int groupIndex, int[] tracks, int type) {
            this.groupIndex = groupIndex;
            int[] iArrCopyOf = Arrays.copyOf(tracks, tracks.length);
            this.tracks = iArrCopyOf;
            this.length = tracks.length;
            this.type = type;
            Arrays.sort(iArrCopyOf);
        }

        SelectionOverride(Parcel in) {
            this.groupIndex = in.readInt();
            int i = in.readByte();
            this.length = i;
            int[] iArr = new int[i];
            this.tracks = iArr;
            in.readIntArray(iArr);
            this.type = in.readInt();
        }

        public boolean containsTrack(int track) {
            for (int i : this.tracks) {
                if (i == track) {
                    return true;
                }
            }
            return false;
        }

        public int hashCode() {
            return (((this.groupIndex * 31) + Arrays.hashCode(this.tracks)) * 31) + this.type;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            SelectionOverride selectionOverride = (SelectionOverride) obj;
            return this.groupIndex == selectionOverride.groupIndex && Arrays.equals(this.tracks, selectionOverride.tracks) && this.type == selectionOverride.type;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.groupIndex);
            dest.writeInt(this.tracks.length);
            dest.writeIntArray(this.tracks);
            dest.writeInt(this.type);
        }
    }

    static /* synthetic */ int lambda$static$0(Integer num, Integer num2) {
        if (num.intValue() == -1) {
            return num2.intValue() == -1 ? 0 : -1;
        }
        if (num2.intValue() == -1) {
            return 1;
        }
        return num.intValue() - num2.intValue();
    }

    @Deprecated
    public DefaultTrackSelector() {
        this(Parameters.DEFAULT_WITHOUT_CONTEXT, new AdaptiveTrackSelection.Factory());
    }

    @Deprecated
    public DefaultTrackSelector(ExoTrackSelection.Factory trackSelectionFactory) {
        this(Parameters.DEFAULT_WITHOUT_CONTEXT, trackSelectionFactory);
    }

    public DefaultTrackSelector(Context context) {
        this(context, new AdaptiveTrackSelection.Factory());
    }

    public DefaultTrackSelector(Context context, ExoTrackSelection.Factory trackSelectionFactory) {
        this(Parameters.getDefaults(context), trackSelectionFactory);
    }

    public DefaultTrackSelector(Parameters parameters, ExoTrackSelection.Factory trackSelectionFactory) {
        this.trackSelectionFactory = trackSelectionFactory;
        this.parametersReference = new AtomicReference<>(parameters);
    }

    public void setParameters(Parameters parameters) {
        Assertions.checkNotNull(parameters);
        if (this.parametersReference.getAndSet(parameters).equals(parameters)) {
            return;
        }
        invalidate();
    }

    public void setParameters(ParametersBuilder parametersBuilder) {
        setParameters(parametersBuilder.build());
    }

    public Parameters getParameters() {
        return this.parametersReference.get();
    }

    public ParametersBuilder buildUponParameters() {
        return getParameters().buildUpon();
    }

    @Override // com.google.android.exoplayer2.trackselection.MappingTrackSelector
    protected final Pair<RendererConfiguration[], ExoTrackSelection[]> selectTracks(MappingTrackSelector.MappedTrackInfo mappedTrackInfo, int[][][] rendererFormatSupports, int[] rendererMixedMimeTypeAdaptationSupports, MediaSource.MediaPeriodId mediaPeriodId, Timeline timeline) throws ExoPlaybackException {
        Parameters parameters = this.parametersReference.get();
        int rendererCount = mappedTrackInfo.getRendererCount();
        ExoTrackSelection.Definition[] definitionArrSelectAllTracks = selectAllTracks(mappedTrackInfo, rendererFormatSupports, rendererMixedMimeTypeAdaptationSupports, parameters);
        int i = 0;
        while (true) {
            if (i >= rendererCount) {
                break;
            }
            if (parameters.getRendererDisabled(i)) {
                definitionArrSelectAllTracks[i] = null;
            } else {
                TrackGroupArray trackGroups = mappedTrackInfo.getTrackGroups(i);
                if (parameters.hasSelectionOverride(i, trackGroups)) {
                    SelectionOverride selectionOverride = parameters.getSelectionOverride(i, trackGroups);
                    definitionArrSelectAllTracks[i] = selectionOverride != null ? new ExoTrackSelection.Definition(trackGroups.get(selectionOverride.groupIndex), selectionOverride.tracks, selectionOverride.type) : null;
                }
            }
            i++;
        }
        ExoTrackSelection[] exoTrackSelectionArrCreateTrackSelections = this.trackSelectionFactory.createTrackSelections(definitionArrSelectAllTracks, getBandwidthMeter(), mediaPeriodId, timeline);
        RendererConfiguration[] rendererConfigurationArr = new RendererConfiguration[rendererCount];
        for (int i2 = 0; i2 < rendererCount; i2++) {
            rendererConfigurationArr[i2] = !parameters.getRendererDisabled(i2) && (mappedTrackInfo.getRendererType(i2) == 7 || exoTrackSelectionArrCreateTrackSelections[i2] != null) ? RendererConfiguration.DEFAULT : null;
        }
        if (parameters.tunnelingEnabled) {
            maybeConfigureRenderersForTunneling(mappedTrackInfo, rendererFormatSupports, rendererConfigurationArr, exoTrackSelectionArrCreateTrackSelections);
        }
        return Pair.create(rendererConfigurationArr, exoTrackSelectionArrCreateTrackSelections);
    }

    protected ExoTrackSelection.Definition[] selectAllTracks(MappingTrackSelector.MappedTrackInfo mappedTrackInfo, int[][][] rendererFormatSupports, int[] rendererMixedMimeTypeAdaptationSupports, Parameters params) throws ExoPlaybackException {
        boolean z;
        String str;
        int i;
        AudioTrackScore audioTrackScore;
        String str2;
        int i2;
        int rendererCount = mappedTrackInfo.getRendererCount();
        ExoTrackSelection.Definition[] definitionArr = new ExoTrackSelection.Definition[rendererCount];
        int i3 = 0;
        boolean z2 = false;
        int i4 = 0;
        boolean z3 = false;
        while (true) {
            if (i4 >= rendererCount) {
                break;
            }
            if (2 == mappedTrackInfo.getRendererType(i4)) {
                if (!z2) {
                    ExoTrackSelection.Definition definitionSelectVideoTrack = selectVideoTrack(mappedTrackInfo.getTrackGroups(i4), rendererFormatSupports[i4], rendererMixedMimeTypeAdaptationSupports[i4], params, true);
                    definitionArr[i4] = definitionSelectVideoTrack;
                    z2 = definitionSelectVideoTrack != null;
                }
                z3 |= mappedTrackInfo.getTrackGroups(i4).length > 0;
            }
            i4++;
        }
        int i5 = 0;
        int i6 = -1;
        AudioTrackScore audioTrackScore2 = null;
        String str3 = null;
        while (i5 < rendererCount) {
            if (z == mappedTrackInfo.getRendererType(i5)) {
                boolean z4 = (params.allowMultipleAdaptiveSelections || !z3) ? z : false;
                i = i6;
                audioTrackScore = audioTrackScore2;
                str2 = str3;
                i2 = i5;
                Pair<ExoTrackSelection.Definition, AudioTrackScore> pairSelectAudioTrack = selectAudioTrack(mappedTrackInfo.getTrackGroups(i5), rendererFormatSupports[i5], rendererMixedMimeTypeAdaptationSupports[i5], params, z4);
                if (pairSelectAudioTrack != null && (audioTrackScore == null || ((AudioTrackScore) pairSelectAudioTrack.second).compareTo(audioTrackScore) > 0)) {
                    if (i != -1) {
                        definitionArr[i] = null;
                    }
                    ExoTrackSelection.Definition definition = (ExoTrackSelection.Definition) pairSelectAudioTrack.first;
                    definitionArr[i2] = definition;
                    str3 = definition.group.getFormat(definition.tracks[0]).language;
                    audioTrackScore2 = (AudioTrackScore) pairSelectAudioTrack.second;
                    i6 = i2;
                }
                i5 = i2 + 1;
                z = true;
            } else {
                i = i6;
                audioTrackScore = audioTrackScore2;
                str2 = str3;
                i2 = i5;
            }
            i6 = i;
            audioTrackScore2 = audioTrackScore;
            str3 = str2;
            i5 = i2 + 1;
            z = true;
        }
        String str4 = str3;
        int i7 = -1;
        TextTrackScore textTrackScore = null;
        while (i3 < rendererCount) {
            int rendererType = mappedTrackInfo.getRendererType(i3);
            if (rendererType == 1) {
                str = str4;
            } else if (rendererType == 2) {
                str = str4;
            } else if (rendererType == 3) {
                str = str4;
                Pair<ExoTrackSelection.Definition, TextTrackScore> pairSelectTextTrack = selectTextTrack(mappedTrackInfo.getTrackGroups(i3), rendererFormatSupports[i3], params, str);
                if (pairSelectTextTrack != null && (textTrackScore == null || ((TextTrackScore) pairSelectTextTrack.second).compareTo(textTrackScore) > 0)) {
                    if (i7 != -1) {
                        definitionArr[i7] = null;
                    }
                    definitionArr[i3] = (ExoTrackSelection.Definition) pairSelectTextTrack.first;
                    textTrackScore = (TextTrackScore) pairSelectTextTrack.second;
                    i7 = i3;
                }
            } else {
                definitionArr[i3] = selectOtherTrack(rendererType, mappedTrackInfo.getTrackGroups(i3), rendererFormatSupports[i3], params);
                str = str4;
            }
            i3++;
            str4 = str;
        }
        return definitionArr;
    }

    protected ExoTrackSelection.Definition selectVideoTrack(TrackGroupArray groups, int[][] formatSupport, int mixedMimeTypeAdaptationSupports, Parameters params, boolean enableAdaptiveTrackSelection) throws ExoPlaybackException {
        ExoTrackSelection.Definition definitionSelectAdaptiveVideoTrack = (params.forceHighestSupportedBitrate || params.forceLowestBitrate || !enableAdaptiveTrackSelection) ? null : selectAdaptiveVideoTrack(groups, formatSupport, mixedMimeTypeAdaptationSupports, params);
        return definitionSelectAdaptiveVideoTrack == null ? selectFixedVideoTrack(groups, formatSupport, params) : definitionSelectAdaptiveVideoTrack;
    }

    private static ExoTrackSelection.Definition selectAdaptiveVideoTrack(TrackGroupArray groups, int[][] formatSupport, int mixedMimeTypeAdaptationSupports, Parameters params) {
        TrackGroupArray trackGroupArray = groups;
        Parameters parameters = params;
        int i = parameters.allowVideoNonSeamlessAdaptiveness ? 24 : 16;
        boolean z = parameters.allowVideoMixedMimeTypeAdaptiveness && (mixedMimeTypeAdaptationSupports & i) != 0;
        int i2 = 0;
        while (i2 < trackGroupArray.length) {
            TrackGroup trackGroup = trackGroupArray.get(i2);
            int i3 = i2;
            int[] adaptiveVideoTracksForGroup = getAdaptiveVideoTracksForGroup(trackGroup, formatSupport[i2], z, i, parameters.maxVideoWidth, parameters.maxVideoHeight, parameters.maxVideoFrameRate, parameters.maxVideoBitrate, parameters.minVideoWidth, parameters.minVideoHeight, parameters.minVideoFrameRate, parameters.minVideoBitrate, parameters.viewportWidth, parameters.viewportHeight, parameters.viewportOrientationMayChange);
            if (adaptiveVideoTracksForGroup.length > 0) {
                return new ExoTrackSelection.Definition(trackGroup, adaptiveVideoTracksForGroup);
            }
            i2 = i3 + 1;
            trackGroupArray = groups;
            parameters = params;
        }
        return null;
    }

    private static int[] getAdaptiveVideoTracksForGroup(TrackGroup group, int[] formatSupport, boolean allowMixedMimeTypes, int requiredAdaptiveSupport, int maxVideoWidth, int maxVideoHeight, int maxVideoFrameRate, int maxVideoBitrate, int minVideoWidth, int minVideoHeight, int minVideoFrameRate, int minVideoBitrate, int viewportWidth, int viewportHeight, boolean viewportOrientationMayChange) {
        String str;
        int i;
        int i2;
        HashSet hashSet;
        if (group.length < 2) {
            return NO_TRACKS;
        }
        List<Integer> viewportFilteredTrackIndices = getViewportFilteredTrackIndices(group, viewportWidth, viewportHeight, viewportOrientationMayChange);
        if (viewportFilteredTrackIndices.size() < 2) {
            return NO_TRACKS;
        }
        if (allowMixedMimeTypes) {
            str = null;
        } else {
            HashSet hashSet2 = new HashSet();
            String str2 = null;
            int i3 = 0;
            int i4 = 0;
            while (i4 < viewportFilteredTrackIndices.size()) {
                String str3 = group.getFormat(viewportFilteredTrackIndices.get(i4).intValue()).sampleMimeType;
                if (hashSet2.add(str3)) {
                    i = i3;
                    i2 = i4;
                    hashSet = hashSet2;
                    int adaptiveVideoTrackCountForMimeType = getAdaptiveVideoTrackCountForMimeType(group, formatSupport, requiredAdaptiveSupport, str3, maxVideoWidth, maxVideoHeight, maxVideoFrameRate, maxVideoBitrate, minVideoWidth, minVideoHeight, minVideoFrameRate, minVideoBitrate, viewportFilteredTrackIndices);
                    if (adaptiveVideoTrackCountForMimeType > i) {
                        i3 = adaptiveVideoTrackCountForMimeType;
                        str2 = str3;
                    }
                    i4 = i2 + 1;
                    hashSet2 = hashSet;
                } else {
                    i = i3;
                    i2 = i4;
                    hashSet = hashSet2;
                }
                i3 = i;
                i4 = i2 + 1;
                hashSet2 = hashSet;
            }
            str = str2;
        }
        filterAdaptiveVideoTrackCountForMimeType(group, formatSupport, requiredAdaptiveSupport, str, maxVideoWidth, maxVideoHeight, maxVideoFrameRate, maxVideoBitrate, minVideoWidth, minVideoHeight, minVideoFrameRate, minVideoBitrate, viewportFilteredTrackIndices);
        return viewportFilteredTrackIndices.size() < 2 ? NO_TRACKS : Ints.toArray(viewportFilteredTrackIndices);
    }

    private static int getAdaptiveVideoTrackCountForMimeType(TrackGroup group, int[] formatSupport, int requiredAdaptiveSupport, String mimeType, int maxVideoWidth, int maxVideoHeight, int maxVideoFrameRate, int maxVideoBitrate, int minVideoWidth, int minVideoHeight, int minVideoFrameRate, int minVideoBitrate, List<Integer> selectedTrackIndices) {
        int i = 0;
        for (int i2 = 0; i2 < selectedTrackIndices.size(); i2++) {
            int iIntValue = selectedTrackIndices.get(i2).intValue();
            if (isSupportedAdaptiveVideoTrack(group.getFormat(iIntValue), mimeType, formatSupport[iIntValue], requiredAdaptiveSupport, maxVideoWidth, maxVideoHeight, maxVideoFrameRate, maxVideoBitrate, minVideoWidth, minVideoHeight, minVideoFrameRate, minVideoBitrate)) {
                i++;
            }
        }
        return i;
    }

    private static void filterAdaptiveVideoTrackCountForMimeType(TrackGroup group, int[] formatSupport, int requiredAdaptiveSupport, String mimeType, int maxVideoWidth, int maxVideoHeight, int maxVideoFrameRate, int maxVideoBitrate, int minVideoWidth, int minVideoHeight, int minVideoFrameRate, int minVideoBitrate, List<Integer> selectedTrackIndices) {
        for (int size = selectedTrackIndices.size() - 1; size >= 0; size--) {
            int iIntValue = selectedTrackIndices.get(size).intValue();
            if (!isSupportedAdaptiveVideoTrack(group.getFormat(iIntValue), mimeType, formatSupport[iIntValue], requiredAdaptiveSupport, maxVideoWidth, maxVideoHeight, maxVideoFrameRate, maxVideoBitrate, minVideoWidth, minVideoHeight, minVideoFrameRate, minVideoBitrate)) {
                selectedTrackIndices.remove(size);
            }
        }
    }

    private static boolean isSupportedAdaptiveVideoTrack(Format format, String mimeType, int formatSupport, int requiredAdaptiveSupport, int maxVideoWidth, int maxVideoHeight, int maxVideoFrameRate, int maxVideoBitrate, int minVideoWidth, int minVideoHeight, int minVideoFrameRate, int minVideoBitrate) {
        if ((format.roleFlags & 16384) != 0 || !isSupported(formatSupport, false) || (formatSupport & requiredAdaptiveSupport) == 0) {
            return false;
        }
        if (mimeType != null && !Util.areEqual(format.sampleMimeType, mimeType)) {
            return false;
        }
        if (format.width != -1 && (minVideoWidth > format.width || format.width > maxVideoWidth)) {
            return false;
        }
        if (format.height == -1 || (minVideoHeight <= format.height && format.height <= maxVideoHeight)) {
            return (format.frameRate == -1.0f || (((float) minVideoFrameRate) <= format.frameRate && format.frameRate <= ((float) maxVideoFrameRate))) && format.bitrate != -1 && minVideoBitrate <= format.bitrate && format.bitrate <= maxVideoBitrate;
        }
        return false;
    }

    private static ExoTrackSelection.Definition selectFixedVideoTrack(TrackGroupArray groups, int[][] formatSupport, Parameters params) {
        int i = -1;
        TrackGroup trackGroup = null;
        VideoTrackScore videoTrackScore = null;
        for (int i2 = 0; i2 < groups.length; i2++) {
            TrackGroup trackGroup2 = groups.get(i2);
            List<Integer> viewportFilteredTrackIndices = getViewportFilteredTrackIndices(trackGroup2, params.viewportWidth, params.viewportHeight, params.viewportOrientationMayChange);
            int[] iArr = formatSupport[i2];
            for (int i3 = 0; i3 < trackGroup2.length; i3++) {
                Format format = trackGroup2.getFormat(i3);
                if ((format.roleFlags & 16384) == 0 && isSupported(iArr[i3], params.exceedRendererCapabilitiesIfNecessary)) {
                    VideoTrackScore videoTrackScore2 = new VideoTrackScore(format, params, iArr[i3], viewportFilteredTrackIndices.contains(Integer.valueOf(i3)));
                    if ((videoTrackScore2.isWithinMaxConstraints || params.exceedVideoConstraintsIfNecessary) && (videoTrackScore == null || videoTrackScore2.compareTo(videoTrackScore) > 0)) {
                        trackGroup = trackGroup2;
                        i = i3;
                        videoTrackScore = videoTrackScore2;
                    }
                }
            }
        }
        if (trackGroup == null) {
            return null;
        }
        return new ExoTrackSelection.Definition(trackGroup, i);
    }

    protected Pair<ExoTrackSelection.Definition, AudioTrackScore> selectAudioTrack(TrackGroupArray groups, int[][] formatSupport, int mixedMimeTypeAdaptationSupports, Parameters params, boolean enableAdaptiveTrackSelection) throws ExoPlaybackException {
        ExoTrackSelection.Definition definition = null;
        int i = -1;
        int i2 = -1;
        AudioTrackScore audioTrackScore = null;
        for (int i3 = 0; i3 < groups.length; i3++) {
            TrackGroup trackGroup = groups.get(i3);
            int[] iArr = formatSupport[i3];
            for (int i4 = 0; i4 < trackGroup.length; i4++) {
                if (isSupported(iArr[i4], params.exceedRendererCapabilitiesIfNecessary)) {
                    AudioTrackScore audioTrackScore2 = new AudioTrackScore(trackGroup.getFormat(i4), params, iArr[i4]);
                    if ((audioTrackScore2.isWithinConstraints || params.exceedAudioConstraintsIfNecessary) && (audioTrackScore == null || audioTrackScore2.compareTo(audioTrackScore) > 0)) {
                        i = i3;
                        i2 = i4;
                        audioTrackScore = audioTrackScore2;
                    }
                }
            }
        }
        if (i == -1) {
            return null;
        }
        TrackGroup trackGroup2 = groups.get(i);
        if (!params.forceHighestSupportedBitrate && !params.forceLowestBitrate && enableAdaptiveTrackSelection) {
            int[] adaptiveAudioTracks = getAdaptiveAudioTracks(trackGroup2, formatSupport[i], i2, params.maxAudioBitrate, params.allowAudioMixedMimeTypeAdaptiveness, params.allowAudioMixedSampleRateAdaptiveness, params.allowAudioMixedChannelCountAdaptiveness);
            if (adaptiveAudioTracks.length > 1) {
                definition = new ExoTrackSelection.Definition(trackGroup2, adaptiveAudioTracks);
            }
        }
        if (definition == null) {
            definition = new ExoTrackSelection.Definition(trackGroup2, i2);
        }
        return Pair.create(definition, (AudioTrackScore) Assertions.checkNotNull(audioTrackScore));
    }

    private static int[] getAdaptiveAudioTracks(TrackGroup group, int[] formatSupport, int primaryTrackIndex, int maxAudioBitrate, boolean allowMixedMimeTypeAdaptiveness, boolean allowMixedSampleRateAdaptiveness, boolean allowAudioMixedChannelCountAdaptiveness) {
        Format format = group.getFormat(primaryTrackIndex);
        int[] iArr = new int[group.length];
        int i = 0;
        for (int i2 = 0; i2 < group.length; i2++) {
            if (i2 == primaryTrackIndex || isSupportedAdaptiveAudioTrack(group.getFormat(i2), formatSupport[i2], format, maxAudioBitrate, allowMixedMimeTypeAdaptiveness, allowMixedSampleRateAdaptiveness, allowAudioMixedChannelCountAdaptiveness)) {
                iArr[i] = i2;
                i++;
            }
        }
        return Arrays.copyOf(iArr, i);
    }

    private static boolean isSupportedAdaptiveAudioTrack(Format format, int formatSupport, Format primaryFormat, int maxAudioBitrate, boolean allowMixedMimeTypeAdaptiveness, boolean allowMixedSampleRateAdaptiveness, boolean allowAudioMixedChannelCountAdaptiveness) {
        if (!isSupported(formatSupport, false) || format.bitrate == -1 || format.bitrate > maxAudioBitrate) {
            return false;
        }
        if (!allowAudioMixedChannelCountAdaptiveness && (format.channelCount == -1 || format.channelCount != primaryFormat.channelCount)) {
            return false;
        }
        if (allowMixedMimeTypeAdaptiveness || (format.sampleMimeType != null && TextUtils.equals(format.sampleMimeType, primaryFormat.sampleMimeType))) {
            return allowMixedSampleRateAdaptiveness || (format.sampleRate != -1 && format.sampleRate == primaryFormat.sampleRate);
        }
        return false;
    }

    protected Pair<ExoTrackSelection.Definition, TextTrackScore> selectTextTrack(TrackGroupArray groups, int[][] formatSupport, Parameters params, String selectedAudioLanguage) throws ExoPlaybackException {
        int i = -1;
        TrackGroup trackGroup = null;
        TextTrackScore textTrackScore = null;
        for (int i2 = 0; i2 < groups.length; i2++) {
            TrackGroup trackGroup2 = groups.get(i2);
            int[] iArr = formatSupport[i2];
            for (int i3 = 0; i3 < trackGroup2.length; i3++) {
                if (isSupported(iArr[i3], params.exceedRendererCapabilitiesIfNecessary)) {
                    TextTrackScore textTrackScore2 = new TextTrackScore(trackGroup2.getFormat(i3), params, iArr[i3], selectedAudioLanguage);
                    if (textTrackScore2.isWithinConstraints && (textTrackScore == null || textTrackScore2.compareTo(textTrackScore) > 0)) {
                        trackGroup = trackGroup2;
                        i = i3;
                        textTrackScore = textTrackScore2;
                    }
                }
            }
        }
        if (trackGroup == null) {
            return null;
        }
        return Pair.create(new ExoTrackSelection.Definition(trackGroup, i), (TextTrackScore) Assertions.checkNotNull(textTrackScore));
    }

    protected ExoTrackSelection.Definition selectOtherTrack(int trackType, TrackGroupArray groups, int[][] formatSupport, Parameters params) throws ExoPlaybackException {
        TrackGroup trackGroup = null;
        OtherTrackScore otherTrackScore = null;
        int i = 0;
        for (int i2 = 0; i2 < groups.length; i2++) {
            TrackGroup trackGroup2 = groups.get(i2);
            int[] iArr = formatSupport[i2];
            for (int i3 = 0; i3 < trackGroup2.length; i3++) {
                if (isSupported(iArr[i3], params.exceedRendererCapabilitiesIfNecessary)) {
                    OtherTrackScore otherTrackScore2 = new OtherTrackScore(trackGroup2.getFormat(i3), iArr[i3]);
                    if (otherTrackScore == null || otherTrackScore2.compareTo(otherTrackScore) > 0) {
                        trackGroup = trackGroup2;
                        i = i3;
                        otherTrackScore = otherTrackScore2;
                    }
                }
            }
        }
        if (trackGroup == null) {
            return null;
        }
        return new ExoTrackSelection.Definition(trackGroup, i);
    }

    private static void maybeConfigureRenderersForTunneling(MappingTrackSelector.MappedTrackInfo mappedTrackInfo, int[][][] renderererFormatSupports, RendererConfiguration[] rendererConfigurations, ExoTrackSelection[] trackSelections) {
        boolean z;
        boolean z2 = false;
        int i = -1;
        int i2 = -1;
        for (int i3 = 0; i3 < mappedTrackInfo.getRendererCount(); i3++) {
            int rendererType = mappedTrackInfo.getRendererType(i3);
            ExoTrackSelection exoTrackSelection = trackSelections[i3];
            if ((rendererType == 1 || rendererType == 2) && exoTrackSelection != null && rendererSupportsTunneling(renderererFormatSupports[i3], mappedTrackInfo.getTrackGroups(i3), exoTrackSelection)) {
                if (rendererType == 1) {
                    if (i2 != -1) {
                        z = false;
                        break;
                    }
                    i2 = i3;
                } else {
                    if (i != -1) {
                        z = false;
                        break;
                    }
                    i = i3;
                }
            }
        }
        z = true;
        if (i2 != -1 && i != -1) {
            z2 = true;
        }
        if (z && z2) {
            RendererConfiguration rendererConfiguration = new RendererConfiguration(true);
            rendererConfigurations[i2] = rendererConfiguration;
            rendererConfigurations[i] = rendererConfiguration;
        }
    }

    private static boolean rendererSupportsTunneling(int[][] formatSupport, TrackGroupArray trackGroups, ExoTrackSelection selection) {
        if (selection == null) {
            return false;
        }
        int iIndexOf = trackGroups.indexOf(selection.getTrackGroup());
        for (int i = 0; i < selection.length(); i++) {
            if (RendererCapabilities.getTunnelingSupport(formatSupport[iIndexOf][selection.getIndexInTrackGroup(i)]) != 32) {
                return false;
            }
        }
        return true;
    }

    protected static boolean isSupported(int formatSupport, boolean allowExceedsCapabilities) {
        int formatSupport2 = RendererCapabilities.getFormatSupport(formatSupport);
        return formatSupport2 == 4 || (allowExceedsCapabilities && formatSupport2 == 3);
    }

    protected static String normalizeUndeterminedLanguageToNull(String language) {
        if (TextUtils.isEmpty(language) || TextUtils.equals(language, C.LANGUAGE_UNDETERMINED)) {
            return null;
        }
        return language;
    }

    protected static int getFormatLanguageScore(Format format, String language, boolean allowUndeterminedFormatLanguage) {
        if (!TextUtils.isEmpty(language) && language.equals(format.language)) {
            return 4;
        }
        String strNormalizeUndeterminedLanguageToNull = normalizeUndeterminedLanguageToNull(language);
        String strNormalizeUndeterminedLanguageToNull2 = normalizeUndeterminedLanguageToNull(format.language);
        if (strNormalizeUndeterminedLanguageToNull2 == null || strNormalizeUndeterminedLanguageToNull == null) {
            return (allowUndeterminedFormatLanguage && strNormalizeUndeterminedLanguageToNull2 == null) ? 1 : 0;
        }
        if (strNormalizeUndeterminedLanguageToNull2.startsWith(strNormalizeUndeterminedLanguageToNull) || strNormalizeUndeterminedLanguageToNull.startsWith(strNormalizeUndeterminedLanguageToNull2)) {
            return 3;
        }
        return Util.splitAtFirst(strNormalizeUndeterminedLanguageToNull2, "-")[0].equals(Util.splitAtFirst(strNormalizeUndeterminedLanguageToNull, "-")[0]) ? 2 : 0;
    }

    private static List<Integer> getViewportFilteredTrackIndices(TrackGroup group, int viewportWidth, int viewportHeight, boolean orientationMayChange) {
        ArrayList arrayList = new ArrayList(group.length);
        for (int i = 0; i < group.length; i++) {
            arrayList.add(Integer.valueOf(i));
        }
        if (viewportWidth != Integer.MAX_VALUE && viewportHeight != Integer.MAX_VALUE) {
            int i2 = Integer.MAX_VALUE;
            for (int i3 = 0; i3 < group.length; i3++) {
                Format format = group.getFormat(i3);
                if (format.width > 0 && format.height > 0) {
                    Point maxVideoSizeInViewport = getMaxVideoSizeInViewport(orientationMayChange, viewportWidth, viewportHeight, format.width, format.height);
                    int i4 = format.width * format.height;
                    if (format.width >= ((int) (maxVideoSizeInViewport.x * FRACTION_TO_CONSIDER_FULLSCREEN)) && format.height >= ((int) (maxVideoSizeInViewport.y * FRACTION_TO_CONSIDER_FULLSCREEN)) && i4 < i2) {
                        i2 = i4;
                    }
                }
            }
            if (i2 != Integer.MAX_VALUE) {
                for (int size = arrayList.size() - 1; size >= 0; size--) {
                    int pixelCount = group.getFormat(((Integer) arrayList.get(size)).intValue()).getPixelCount();
                    if (pixelCount == -1 || pixelCount > i2) {
                        arrayList.remove(size);
                    }
                }
            }
        }
        return arrayList;
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0010  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private static android.graphics.Point getMaxVideoSizeInViewport(boolean r3, int r4, int r5, int r6, int r7) {
        /*
            if (r3 == 0) goto L10
            r3 = 1
            r0 = 0
            if (r6 <= r7) goto L8
            r1 = r3
            goto L9
        L8:
            r1 = r0
        L9:
            if (r4 <= r5) goto Lc
            goto Ld
        Lc:
            r3 = r0
        Ld:
            if (r1 == r3) goto L10
            goto L13
        L10:
            r2 = r5
            r5 = r4
            r4 = r2
        L13:
            int r3 = r6 * r4
            int r0 = r7 * r5
            if (r3 < r0) goto L23
            android.graphics.Point r3 = new android.graphics.Point
            int r4 = com.google.android.exoplayer2.util.Util.ceilDivide(r0, r6)
            r3.<init>(r5, r4)
            return r3
        L23:
            android.graphics.Point r5 = new android.graphics.Point
            int r3 = com.google.android.exoplayer2.util.Util.ceilDivide(r3, r7)
            r5.<init>(r3, r4)
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.trackselection.DefaultTrackSelector.getMaxVideoSizeInViewport(boolean, int, int, int, int):android.graphics.Point");
    }

    protected static final class VideoTrackScore implements Comparable<VideoTrackScore> {
        private final int bitrate;
        public final boolean isWithinMaxConstraints;
        private final boolean isWithinMinConstraints;
        private final boolean isWithinRendererCapabilities;
        private final Parameters parameters;
        private final int pixelCount;
        private final int preferredMimeTypeMatchIndex;

        public VideoTrackScore(Format format, Parameters parameters, int formatSupport, boolean isSuitableForViewport) {
            this.parameters = parameters;
            boolean z = true;
            int i = 0;
            this.isWithinMaxConstraints = isSuitableForViewport && (format.width == -1 || format.width <= parameters.maxVideoWidth) && ((format.height == -1 || format.height <= parameters.maxVideoHeight) && ((format.frameRate == -1.0f || format.frameRate <= ((float) parameters.maxVideoFrameRate)) && (format.bitrate == -1 || format.bitrate <= parameters.maxVideoBitrate)));
            if (!isSuitableForViewport || ((format.width != -1 && format.width < parameters.minVideoWidth) || ((format.height != -1 && format.height < parameters.minVideoHeight) || ((format.frameRate != -1.0f && format.frameRate < parameters.minVideoFrameRate) || (format.bitrate != -1 && format.bitrate < parameters.minVideoBitrate))))) {
                z = false;
            }
            this.isWithinMinConstraints = z;
            this.isWithinRendererCapabilities = DefaultTrackSelector.isSupported(formatSupport, false);
            this.bitrate = format.bitrate;
            this.pixelCount = format.getPixelCount();
            while (true) {
                if (i >= parameters.preferredVideoMimeTypes.size()) {
                    i = Integer.MAX_VALUE;
                    break;
                } else if (format.sampleMimeType != null && format.sampleMimeType.equals(parameters.preferredVideoMimeTypes.get(i))) {
                    break;
                } else {
                    i++;
                }
            }
            this.preferredMimeTypeMatchIndex = i;
        }

        @Override // java.lang.Comparable
        public int compareTo(VideoTrackScore other) {
            Ordering orderingReverse = (this.isWithinMaxConstraints && this.isWithinRendererCapabilities) ? DefaultTrackSelector.FORMAT_VALUE_ORDERING : DefaultTrackSelector.FORMAT_VALUE_ORDERING.reverse();
            return ComparisonChain.start().compareFalseFirst(this.isWithinRendererCapabilities, other.isWithinRendererCapabilities).compareFalseFirst(this.isWithinMaxConstraints, other.isWithinMaxConstraints).compareFalseFirst(this.isWithinMinConstraints, other.isWithinMinConstraints).compare(Integer.valueOf(this.preferredMimeTypeMatchIndex), Integer.valueOf(other.preferredMimeTypeMatchIndex), Ordering.natural().reverse()).compare(Integer.valueOf(this.bitrate), Integer.valueOf(other.bitrate), this.parameters.forceLowestBitrate ? DefaultTrackSelector.FORMAT_VALUE_ORDERING.reverse() : DefaultTrackSelector.NO_ORDER).compare(Integer.valueOf(this.pixelCount), Integer.valueOf(other.pixelCount), orderingReverse).compare(Integer.valueOf(this.bitrate), Integer.valueOf(other.bitrate), orderingReverse).result();
        }
    }

    protected static final class AudioTrackScore implements Comparable<AudioTrackScore> {
        private final int bitrate;
        private final int channelCount;
        private final boolean isDefaultSelectionFlag;
        public final boolean isWithinConstraints;
        private final boolean isWithinRendererCapabilities;
        private final String language;
        private final int localeLanguageMatchIndex;
        private final int localeLanguageScore;
        private final Parameters parameters;
        private final int preferredLanguageIndex;
        private final int preferredLanguageScore;
        private final int preferredMimeTypeMatchIndex;
        private final int preferredRoleFlagsScore;
        private final int sampleRate;

        public AudioTrackScore(Format format, Parameters parameters, int formatSupport) {
            int i;
            int formatLanguageScore;
            int formatLanguageScore2;
            this.parameters = parameters;
            this.language = DefaultTrackSelector.normalizeUndeterminedLanguageToNull(format.language);
            int i2 = 0;
            this.isWithinRendererCapabilities = DefaultTrackSelector.isSupported(formatSupport, false);
            int i3 = 0;
            while (true) {
                i = Integer.MAX_VALUE;
                if (i3 >= parameters.preferredAudioLanguages.size()) {
                    formatLanguageScore = 0;
                    i3 = Integer.MAX_VALUE;
                    break;
                } else {
                    formatLanguageScore = DefaultTrackSelector.getFormatLanguageScore(format, parameters.preferredAudioLanguages.get(i3), false);
                    if (formatLanguageScore > 0) {
                        break;
                    } else {
                        i3++;
                    }
                }
            }
            this.preferredLanguageIndex = i3;
            this.preferredLanguageScore = formatLanguageScore;
            this.preferredRoleFlagsScore = Integer.bitCount(format.roleFlags & parameters.preferredAudioRoleFlags);
            boolean z = true;
            this.isDefaultSelectionFlag = (format.selectionFlags & 1) != 0;
            this.channelCount = format.channelCount;
            this.sampleRate = format.sampleRate;
            this.bitrate = format.bitrate;
            if ((format.bitrate != -1 && format.bitrate > parameters.maxAudioBitrate) || (format.channelCount != -1 && format.channelCount > parameters.maxAudioChannelCount)) {
                z = false;
            }
            this.isWithinConstraints = z;
            String[] systemLanguageCodes = Util.getSystemLanguageCodes();
            int i4 = 0;
            while (true) {
                if (i4 >= systemLanguageCodes.length) {
                    formatLanguageScore2 = 0;
                    i4 = Integer.MAX_VALUE;
                    break;
                } else {
                    formatLanguageScore2 = DefaultTrackSelector.getFormatLanguageScore(format, systemLanguageCodes[i4], false);
                    if (formatLanguageScore2 > 0) {
                        break;
                    } else {
                        i4++;
                    }
                }
            }
            this.localeLanguageMatchIndex = i4;
            this.localeLanguageScore = formatLanguageScore2;
            while (true) {
                if (i2 < parameters.preferredAudioMimeTypes.size()) {
                    if (format.sampleMimeType != null && format.sampleMimeType.equals(parameters.preferredAudioMimeTypes.get(i2))) {
                        i = i2;
                        break;
                    }
                    i2++;
                } else {
                    break;
                }
            }
            this.preferredMimeTypeMatchIndex = i;
        }

        @Override // java.lang.Comparable
        public int compareTo(AudioTrackScore other) {
            Ordering orderingReverse = (this.isWithinConstraints && this.isWithinRendererCapabilities) ? DefaultTrackSelector.FORMAT_VALUE_ORDERING : DefaultTrackSelector.FORMAT_VALUE_ORDERING.reverse();
            ComparisonChain comparisonChainCompare = ComparisonChain.start().compareFalseFirst(this.isWithinRendererCapabilities, other.isWithinRendererCapabilities).compare(Integer.valueOf(this.preferredLanguageIndex), Integer.valueOf(other.preferredLanguageIndex), Ordering.natural().reverse()).compare(this.preferredLanguageScore, other.preferredLanguageScore).compare(this.preferredRoleFlagsScore, other.preferredRoleFlagsScore).compareFalseFirst(this.isWithinConstraints, other.isWithinConstraints).compare(Integer.valueOf(this.preferredMimeTypeMatchIndex), Integer.valueOf(other.preferredMimeTypeMatchIndex), Ordering.natural().reverse()).compare(Integer.valueOf(this.bitrate), Integer.valueOf(other.bitrate), this.parameters.forceLowestBitrate ? DefaultTrackSelector.FORMAT_VALUE_ORDERING.reverse() : DefaultTrackSelector.NO_ORDER).compareFalseFirst(this.isDefaultSelectionFlag, other.isDefaultSelectionFlag).compare(Integer.valueOf(this.localeLanguageMatchIndex), Integer.valueOf(other.localeLanguageMatchIndex), Ordering.natural().reverse()).compare(this.localeLanguageScore, other.localeLanguageScore).compare(Integer.valueOf(this.channelCount), Integer.valueOf(other.channelCount), orderingReverse).compare(Integer.valueOf(this.sampleRate), Integer.valueOf(other.sampleRate), orderingReverse);
            Integer numValueOf = Integer.valueOf(this.bitrate);
            Integer numValueOf2 = Integer.valueOf(other.bitrate);
            if (!Util.areEqual(this.language, other.language)) {
                orderingReverse = DefaultTrackSelector.NO_ORDER;
            }
            return comparisonChainCompare.compare(numValueOf, numValueOf2, orderingReverse).result();
        }
    }

    protected static final class TextTrackScore implements Comparable<TextTrackScore> {
        private final boolean hasCaptionRoleFlags;
        private final boolean isDefault;
        private final boolean isForced;
        public final boolean isWithinConstraints;
        private final boolean isWithinRendererCapabilities;
        private final int preferredLanguageIndex;
        private final int preferredLanguageScore;
        private final int preferredRoleFlagsScore;
        private final int selectedAudioLanguageScore;

        public TextTrackScore(Format format, Parameters parameters, int trackFormatSupport, String selectedAudioLanguage) {
            ImmutableList<String> immutableListOf;
            int formatLanguageScore;
            boolean z = false;
            this.isWithinRendererCapabilities = DefaultTrackSelector.isSupported(trackFormatSupport, false);
            int i = format.selectionFlags & (~parameters.disabledTextTrackSelectionFlags);
            this.isDefault = (i & 1) != 0;
            this.isForced = (i & 2) != 0;
            if (parameters.preferredTextLanguages.isEmpty()) {
                immutableListOf = ImmutableList.of("");
            } else {
                immutableListOf = parameters.preferredTextLanguages;
            }
            int i2 = 0;
            while (true) {
                if (i2 >= immutableListOf.size()) {
                    i2 = Integer.MAX_VALUE;
                    formatLanguageScore = 0;
                    break;
                } else {
                    formatLanguageScore = DefaultTrackSelector.getFormatLanguageScore(format, immutableListOf.get(i2), parameters.selectUndeterminedTextLanguage);
                    if (formatLanguageScore > 0) {
                        break;
                    } else {
                        i2++;
                    }
                }
            }
            this.preferredLanguageIndex = i2;
            this.preferredLanguageScore = formatLanguageScore;
            int iBitCount = Integer.bitCount(format.roleFlags & parameters.preferredTextRoleFlags);
            this.preferredRoleFlagsScore = iBitCount;
            this.hasCaptionRoleFlags = (format.roleFlags & 1088) != 0;
            int formatLanguageScore2 = DefaultTrackSelector.getFormatLanguageScore(format, selectedAudioLanguage, DefaultTrackSelector.normalizeUndeterminedLanguageToNull(selectedAudioLanguage) == null);
            this.selectedAudioLanguageScore = formatLanguageScore2;
            if (formatLanguageScore > 0 || ((parameters.preferredTextLanguages.isEmpty() && iBitCount > 0) || this.isDefault || (this.isForced && formatLanguageScore2 > 0))) {
                z = true;
            }
            this.isWithinConstraints = z;
        }

        @Override // java.lang.Comparable
        public int compareTo(TextTrackScore other) {
            ComparisonChain comparisonChainCompare = ComparisonChain.start().compareFalseFirst(this.isWithinRendererCapabilities, other.isWithinRendererCapabilities).compare(Integer.valueOf(this.preferredLanguageIndex), Integer.valueOf(other.preferredLanguageIndex), Ordering.natural().reverse()).compare(this.preferredLanguageScore, other.preferredLanguageScore).compare(this.preferredRoleFlagsScore, other.preferredRoleFlagsScore).compareFalseFirst(this.isDefault, other.isDefault).compare(Boolean.valueOf(this.isForced), Boolean.valueOf(other.isForced), this.preferredLanguageScore == 0 ? Ordering.natural() : Ordering.natural().reverse()).compare(this.selectedAudioLanguageScore, other.selectedAudioLanguageScore);
            if (this.preferredRoleFlagsScore == 0) {
                comparisonChainCompare = comparisonChainCompare.compareTrueFirst(this.hasCaptionRoleFlags, other.hasCaptionRoleFlags);
            }
            return comparisonChainCompare.result();
        }
    }

    protected static final class OtherTrackScore implements Comparable<OtherTrackScore> {
        private final boolean isDefault;
        private final boolean isWithinRendererCapabilities;

        public OtherTrackScore(Format format, int trackFormatSupport) {
            this.isDefault = (format.selectionFlags & 1) != 0;
            this.isWithinRendererCapabilities = DefaultTrackSelector.isSupported(trackFormatSupport, false);
        }

        @Override // java.lang.Comparable
        public int compareTo(OtherTrackScore other) {
            return ComparisonChain.start().compareFalseFirst(this.isWithinRendererCapabilities, other.isWithinRendererCapabilities).compareFalseFirst(this.isDefault, other.isDefault).result();
        }
    }
}
