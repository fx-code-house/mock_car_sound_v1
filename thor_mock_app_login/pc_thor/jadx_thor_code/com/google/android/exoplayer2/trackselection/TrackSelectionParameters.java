package com.google.android.exoplayer2.trackselection;

import android.content.Context;
import android.graphics.Point;
import android.os.Looper;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.accessibility.CaptioningManager;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import com.google.common.collect.ImmutableList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;

/* loaded from: classes.dex */
public class TrackSelectionParameters implements Parcelable {
    public static final Parcelable.Creator<TrackSelectionParameters> CREATOR;

    @Deprecated
    public static final TrackSelectionParameters DEFAULT;
    public static final TrackSelectionParameters DEFAULT_WITHOUT_CONTEXT;
    public final boolean forceHighestSupportedBitrate;
    public final boolean forceLowestBitrate;
    public final int maxAudioBitrate;
    public final int maxAudioChannelCount;
    public final int maxVideoBitrate;
    public final int maxVideoFrameRate;
    public final int maxVideoHeight;
    public final int maxVideoWidth;
    public final int minVideoBitrate;
    public final int minVideoFrameRate;
    public final int minVideoHeight;
    public final int minVideoWidth;
    public final ImmutableList<String> preferredAudioLanguages;
    public final ImmutableList<String> preferredAudioMimeTypes;
    public final int preferredAudioRoleFlags;
    public final ImmutableList<String> preferredTextLanguages;
    public final int preferredTextRoleFlags;
    public final ImmutableList<String> preferredVideoMimeTypes;
    public final boolean selectUndeterminedTextLanguage;
    public final int viewportHeight;
    public final boolean viewportOrientationMayChange;
    public final int viewportWidth;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static class Builder {
        private boolean forceHighestSupportedBitrate;
        private boolean forceLowestBitrate;
        private int maxAudioBitrate;
        private int maxAudioChannelCount;
        private int maxVideoBitrate;
        private int maxVideoFrameRate;
        private int maxVideoHeight;
        private int maxVideoWidth;
        private int minVideoBitrate;
        private int minVideoFrameRate;
        private int minVideoHeight;
        private int minVideoWidth;
        private ImmutableList<String> preferredAudioLanguages;
        private ImmutableList<String> preferredAudioMimeTypes;
        private int preferredAudioRoleFlags;
        private ImmutableList<String> preferredTextLanguages;
        private int preferredTextRoleFlags;
        private ImmutableList<String> preferredVideoMimeTypes;
        private boolean selectUndeterminedTextLanguage;
        private int viewportHeight;
        private boolean viewportOrientationMayChange;
        private int viewportWidth;

        @Deprecated
        public Builder() {
            this.maxVideoWidth = Integer.MAX_VALUE;
            this.maxVideoHeight = Integer.MAX_VALUE;
            this.maxVideoFrameRate = Integer.MAX_VALUE;
            this.maxVideoBitrate = Integer.MAX_VALUE;
            this.viewportWidth = Integer.MAX_VALUE;
            this.viewportHeight = Integer.MAX_VALUE;
            this.viewportOrientationMayChange = true;
            this.preferredVideoMimeTypes = ImmutableList.of();
            this.preferredAudioLanguages = ImmutableList.of();
            this.preferredAudioRoleFlags = 0;
            this.maxAudioChannelCount = Integer.MAX_VALUE;
            this.maxAudioBitrate = Integer.MAX_VALUE;
            this.preferredAudioMimeTypes = ImmutableList.of();
            this.preferredTextLanguages = ImmutableList.of();
            this.preferredTextRoleFlags = 0;
            this.selectUndeterminedTextLanguage = false;
            this.forceLowestBitrate = false;
            this.forceHighestSupportedBitrate = false;
        }

        public Builder(Context context) {
            this();
            setPreferredTextLanguageAndRoleFlagsToCaptioningManagerSettings(context);
            setViewportSizeToPhysicalDisplaySize(context, true);
        }

        protected Builder(TrackSelectionParameters initialValues) {
            this.maxVideoWidth = initialValues.maxVideoWidth;
            this.maxVideoHeight = initialValues.maxVideoHeight;
            this.maxVideoFrameRate = initialValues.maxVideoFrameRate;
            this.maxVideoBitrate = initialValues.maxVideoBitrate;
            this.minVideoWidth = initialValues.minVideoWidth;
            this.minVideoHeight = initialValues.minVideoHeight;
            this.minVideoFrameRate = initialValues.minVideoFrameRate;
            this.minVideoBitrate = initialValues.minVideoBitrate;
            this.viewportWidth = initialValues.viewportWidth;
            this.viewportHeight = initialValues.viewportHeight;
            this.viewportOrientationMayChange = initialValues.viewportOrientationMayChange;
            this.preferredVideoMimeTypes = initialValues.preferredVideoMimeTypes;
            this.preferredAudioLanguages = initialValues.preferredAudioLanguages;
            this.preferredAudioRoleFlags = initialValues.preferredAudioRoleFlags;
            this.maxAudioChannelCount = initialValues.maxAudioChannelCount;
            this.maxAudioBitrate = initialValues.maxAudioBitrate;
            this.preferredAudioMimeTypes = initialValues.preferredAudioMimeTypes;
            this.preferredTextLanguages = initialValues.preferredTextLanguages;
            this.preferredTextRoleFlags = initialValues.preferredTextRoleFlags;
            this.selectUndeterminedTextLanguage = initialValues.selectUndeterminedTextLanguage;
            this.forceLowestBitrate = initialValues.forceLowestBitrate;
            this.forceHighestSupportedBitrate = initialValues.forceHighestSupportedBitrate;
        }

        public Builder setMaxVideoSizeSd() {
            return setMaxVideoSize(1279, 719);
        }

        public Builder clearVideoSizeConstraints() {
            return setMaxVideoSize(Integer.MAX_VALUE, Integer.MAX_VALUE);
        }

        public Builder setMaxVideoSize(int maxVideoWidth, int maxVideoHeight) {
            this.maxVideoWidth = maxVideoWidth;
            this.maxVideoHeight = maxVideoHeight;
            return this;
        }

        public Builder setMaxVideoFrameRate(int maxVideoFrameRate) {
            this.maxVideoFrameRate = maxVideoFrameRate;
            return this;
        }

        public Builder setMaxVideoBitrate(int maxVideoBitrate) {
            this.maxVideoBitrate = maxVideoBitrate;
            return this;
        }

        public Builder setMinVideoSize(int minVideoWidth, int minVideoHeight) {
            this.minVideoWidth = minVideoWidth;
            this.minVideoHeight = minVideoHeight;
            return this;
        }

        public Builder setMinVideoFrameRate(int minVideoFrameRate) {
            this.minVideoFrameRate = minVideoFrameRate;
            return this;
        }

        public Builder setMinVideoBitrate(int minVideoBitrate) {
            this.minVideoBitrate = minVideoBitrate;
            return this;
        }

        public Builder setViewportSizeToPhysicalDisplaySize(Context context, boolean viewportOrientationMayChange) {
            Point currentDisplayModeSize = Util.getCurrentDisplayModeSize(context);
            return setViewportSize(currentDisplayModeSize.x, currentDisplayModeSize.y, viewportOrientationMayChange);
        }

        public Builder clearViewportSizeConstraints() {
            return setViewportSize(Integer.MAX_VALUE, Integer.MAX_VALUE, true);
        }

        public Builder setViewportSize(int viewportWidth, int viewportHeight, boolean viewportOrientationMayChange) {
            this.viewportWidth = viewportWidth;
            this.viewportHeight = viewportHeight;
            this.viewportOrientationMayChange = viewportOrientationMayChange;
            return this;
        }

        public Builder setPreferredVideoMimeType(String mimeType) {
            return mimeType == null ? setPreferredVideoMimeTypes(new String[0]) : setPreferredVideoMimeTypes(mimeType);
        }

        public Builder setPreferredVideoMimeTypes(String... mimeTypes) {
            this.preferredVideoMimeTypes = ImmutableList.copyOf(mimeTypes);
            return this;
        }

        public Builder setPreferredAudioLanguage(String preferredAudioLanguage) {
            if (preferredAudioLanguage == null) {
                return setPreferredAudioLanguages(new String[0]);
            }
            return setPreferredAudioLanguages(preferredAudioLanguage);
        }

        public Builder setPreferredAudioLanguages(String... preferredAudioLanguages) {
            ImmutableList.Builder builder = ImmutableList.builder();
            for (String str : (String[]) Assertions.checkNotNull(preferredAudioLanguages)) {
                builder.add((ImmutableList.Builder) Util.normalizeLanguageCode((String) Assertions.checkNotNull(str)));
            }
            this.preferredAudioLanguages = builder.build();
            return this;
        }

        public Builder setPreferredAudioRoleFlags(int preferredAudioRoleFlags) {
            this.preferredAudioRoleFlags = preferredAudioRoleFlags;
            return this;
        }

        public Builder setMaxAudioChannelCount(int maxAudioChannelCount) {
            this.maxAudioChannelCount = maxAudioChannelCount;
            return this;
        }

        public Builder setMaxAudioBitrate(int maxAudioBitrate) {
            this.maxAudioBitrate = maxAudioBitrate;
            return this;
        }

        public Builder setPreferredAudioMimeType(String mimeType) {
            return mimeType == null ? setPreferredAudioMimeTypes(new String[0]) : setPreferredAudioMimeTypes(mimeType);
        }

        public Builder setPreferredAudioMimeTypes(String... mimeTypes) {
            this.preferredAudioMimeTypes = ImmutableList.copyOf(mimeTypes);
            return this;
        }

        public Builder setPreferredTextLanguageAndRoleFlagsToCaptioningManagerSettings(Context context) {
            if (Util.SDK_INT >= 19) {
                setPreferredTextLanguageAndRoleFlagsToCaptioningManagerSettingsV19(context);
            }
            return this;
        }

        public Builder setPreferredTextLanguage(String preferredTextLanguage) {
            if (preferredTextLanguage == null) {
                return setPreferredTextLanguages(new String[0]);
            }
            return setPreferredTextLanguages(preferredTextLanguage);
        }

        public Builder setPreferredTextLanguages(String... preferredTextLanguages) {
            ImmutableList.Builder builder = ImmutableList.builder();
            for (String str : (String[]) Assertions.checkNotNull(preferredTextLanguages)) {
                builder.add((ImmutableList.Builder) Util.normalizeLanguageCode((String) Assertions.checkNotNull(str)));
            }
            this.preferredTextLanguages = builder.build();
            return this;
        }

        public Builder setPreferredTextRoleFlags(int preferredTextRoleFlags) {
            this.preferredTextRoleFlags = preferredTextRoleFlags;
            return this;
        }

        public Builder setSelectUndeterminedTextLanguage(boolean selectUndeterminedTextLanguage) {
            this.selectUndeterminedTextLanguage = selectUndeterminedTextLanguage;
            return this;
        }

        public Builder setForceLowestBitrate(boolean forceLowestBitrate) {
            this.forceLowestBitrate = forceLowestBitrate;
            return this;
        }

        public Builder setForceHighestSupportedBitrate(boolean forceHighestSupportedBitrate) {
            this.forceHighestSupportedBitrate = forceHighestSupportedBitrate;
            return this;
        }

        public TrackSelectionParameters build() {
            return new TrackSelectionParameters(this);
        }

        private void setPreferredTextLanguageAndRoleFlagsToCaptioningManagerSettingsV19(Context context) {
            CaptioningManager captioningManager;
            if ((Util.SDK_INT >= 23 || Looper.myLooper() != null) && (captioningManager = (CaptioningManager) context.getSystemService("captioning")) != null && captioningManager.isEnabled()) {
                this.preferredTextRoleFlags = 1088;
                Locale locale = captioningManager.getLocale();
                if (locale != null) {
                    this.preferredTextLanguages = ImmutableList.of(Util.getLocaleLanguageTag(locale));
                }
            }
        }
    }

    static {
        TrackSelectionParameters trackSelectionParametersBuild = new Builder().build();
        DEFAULT_WITHOUT_CONTEXT = trackSelectionParametersBuild;
        DEFAULT = trackSelectionParametersBuild;
        CREATOR = new Parcelable.Creator<TrackSelectionParameters>() { // from class: com.google.android.exoplayer2.trackselection.TrackSelectionParameters.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public TrackSelectionParameters createFromParcel(Parcel in) {
                return new TrackSelectionParameters(in);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public TrackSelectionParameters[] newArray(int size) {
                return new TrackSelectionParameters[size];
            }
        };
    }

    public static TrackSelectionParameters getDefaults(Context context) {
        return new Builder(context).build();
    }

    protected TrackSelectionParameters(Builder builder) {
        this.maxVideoWidth = builder.maxVideoWidth;
        this.maxVideoHeight = builder.maxVideoHeight;
        this.maxVideoFrameRate = builder.maxVideoFrameRate;
        this.maxVideoBitrate = builder.maxVideoBitrate;
        this.minVideoWidth = builder.minVideoWidth;
        this.minVideoHeight = builder.minVideoHeight;
        this.minVideoFrameRate = builder.minVideoFrameRate;
        this.minVideoBitrate = builder.minVideoBitrate;
        this.viewportWidth = builder.viewportWidth;
        this.viewportHeight = builder.viewportHeight;
        this.viewportOrientationMayChange = builder.viewportOrientationMayChange;
        this.preferredVideoMimeTypes = builder.preferredVideoMimeTypes;
        this.preferredAudioLanguages = builder.preferredAudioLanguages;
        this.preferredAudioRoleFlags = builder.preferredAudioRoleFlags;
        this.maxAudioChannelCount = builder.maxAudioChannelCount;
        this.maxAudioBitrate = builder.maxAudioBitrate;
        this.preferredAudioMimeTypes = builder.preferredAudioMimeTypes;
        this.preferredTextLanguages = builder.preferredTextLanguages;
        this.preferredTextRoleFlags = builder.preferredTextRoleFlags;
        this.selectUndeterminedTextLanguage = builder.selectUndeterminedTextLanguage;
        this.forceLowestBitrate = builder.forceLowestBitrate;
        this.forceHighestSupportedBitrate = builder.forceHighestSupportedBitrate;
    }

    TrackSelectionParameters(Parcel in) {
        ArrayList arrayList = new ArrayList();
        in.readList(arrayList, null);
        this.preferredAudioLanguages = ImmutableList.copyOf((Collection) arrayList);
        this.preferredAudioRoleFlags = in.readInt();
        ArrayList arrayList2 = new ArrayList();
        in.readList(arrayList2, null);
        this.preferredTextLanguages = ImmutableList.copyOf((Collection) arrayList2);
        this.preferredTextRoleFlags = in.readInt();
        this.selectUndeterminedTextLanguage = Util.readBoolean(in);
        this.maxVideoWidth = in.readInt();
        this.maxVideoHeight = in.readInt();
        this.maxVideoFrameRate = in.readInt();
        this.maxVideoBitrate = in.readInt();
        this.minVideoWidth = in.readInt();
        this.minVideoHeight = in.readInt();
        this.minVideoFrameRate = in.readInt();
        this.minVideoBitrate = in.readInt();
        this.viewportWidth = in.readInt();
        this.viewportHeight = in.readInt();
        this.viewportOrientationMayChange = Util.readBoolean(in);
        ArrayList arrayList3 = new ArrayList();
        in.readList(arrayList3, null);
        this.preferredVideoMimeTypes = ImmutableList.copyOf((Collection) arrayList3);
        this.maxAudioChannelCount = in.readInt();
        this.maxAudioBitrate = in.readInt();
        ArrayList arrayList4 = new ArrayList();
        in.readList(arrayList4, null);
        this.preferredAudioMimeTypes = ImmutableList.copyOf((Collection) arrayList4);
        this.forceLowestBitrate = Util.readBoolean(in);
        this.forceHighestSupportedBitrate = Util.readBoolean(in);
    }

    public Builder buildUpon() {
        return new Builder(this);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        TrackSelectionParameters trackSelectionParameters = (TrackSelectionParameters) obj;
        return this.maxVideoWidth == trackSelectionParameters.maxVideoWidth && this.maxVideoHeight == trackSelectionParameters.maxVideoHeight && this.maxVideoFrameRate == trackSelectionParameters.maxVideoFrameRate && this.maxVideoBitrate == trackSelectionParameters.maxVideoBitrate && this.minVideoWidth == trackSelectionParameters.minVideoWidth && this.minVideoHeight == trackSelectionParameters.minVideoHeight && this.minVideoFrameRate == trackSelectionParameters.minVideoFrameRate && this.minVideoBitrate == trackSelectionParameters.minVideoBitrate && this.viewportOrientationMayChange == trackSelectionParameters.viewportOrientationMayChange && this.viewportWidth == trackSelectionParameters.viewportWidth && this.viewportHeight == trackSelectionParameters.viewportHeight && this.preferredVideoMimeTypes.equals(trackSelectionParameters.preferredVideoMimeTypes) && this.preferredAudioLanguages.equals(trackSelectionParameters.preferredAudioLanguages) && this.preferredAudioRoleFlags == trackSelectionParameters.preferredAudioRoleFlags && this.maxAudioChannelCount == trackSelectionParameters.maxAudioChannelCount && this.maxAudioBitrate == trackSelectionParameters.maxAudioBitrate && this.preferredAudioMimeTypes.equals(trackSelectionParameters.preferredAudioMimeTypes) && this.preferredTextLanguages.equals(trackSelectionParameters.preferredTextLanguages) && this.preferredTextRoleFlags == trackSelectionParameters.preferredTextRoleFlags && this.selectUndeterminedTextLanguage == trackSelectionParameters.selectUndeterminedTextLanguage && this.forceLowestBitrate == trackSelectionParameters.forceLowestBitrate && this.forceHighestSupportedBitrate == trackSelectionParameters.forceHighestSupportedBitrate;
    }

    public int hashCode() {
        return ((((((((((((((((((((((((((((((((((((((((((this.maxVideoWidth + 31) * 31) + this.maxVideoHeight) * 31) + this.maxVideoFrameRate) * 31) + this.maxVideoBitrate) * 31) + this.minVideoWidth) * 31) + this.minVideoHeight) * 31) + this.minVideoFrameRate) * 31) + this.minVideoBitrate) * 31) + (this.viewportOrientationMayChange ? 1 : 0)) * 31) + this.viewportWidth) * 31) + this.viewportHeight) * 31) + this.preferredVideoMimeTypes.hashCode()) * 31) + this.preferredAudioLanguages.hashCode()) * 31) + this.preferredAudioRoleFlags) * 31) + this.maxAudioChannelCount) * 31) + this.maxAudioBitrate) * 31) + this.preferredAudioMimeTypes.hashCode()) * 31) + this.preferredTextLanguages.hashCode()) * 31) + this.preferredTextRoleFlags) * 31) + (this.selectUndeterminedTextLanguage ? 1 : 0)) * 31) + (this.forceLowestBitrate ? 1 : 0)) * 31) + (this.forceHighestSupportedBitrate ? 1 : 0);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.preferredAudioLanguages);
        dest.writeInt(this.preferredAudioRoleFlags);
        dest.writeList(this.preferredTextLanguages);
        dest.writeInt(this.preferredTextRoleFlags);
        Util.writeBoolean(dest, this.selectUndeterminedTextLanguage);
        dest.writeInt(this.maxVideoWidth);
        dest.writeInt(this.maxVideoHeight);
        dest.writeInt(this.maxVideoFrameRate);
        dest.writeInt(this.maxVideoBitrate);
        dest.writeInt(this.minVideoWidth);
        dest.writeInt(this.minVideoHeight);
        dest.writeInt(this.minVideoFrameRate);
        dest.writeInt(this.minVideoBitrate);
        dest.writeInt(this.viewportWidth);
        dest.writeInt(this.viewportHeight);
        Util.writeBoolean(dest, this.viewportOrientationMayChange);
        dest.writeList(this.preferredVideoMimeTypes);
        dest.writeInt(this.maxAudioChannelCount);
        dest.writeInt(this.maxAudioBitrate);
        dest.writeList(this.preferredAudioMimeTypes);
        Util.writeBoolean(dest, this.forceLowestBitrate);
        Util.writeBoolean(dest, this.forceHighestSupportedBitrate);
    }
}
