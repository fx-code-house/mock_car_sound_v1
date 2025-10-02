package com.google.android.exoplayer2;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.exoplayer2.drm.DrmInitData;
import com.google.android.exoplayer2.drm.ExoMediaCrypto;
import com.google.android.exoplayer2.drm.UnsupportedMediaCrypto;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.video.ColorInfo;
import com.google.common.base.Joiner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.UUID;

/* loaded from: classes.dex */
public final class Format implements Parcelable {
    public static final Parcelable.Creator<Format> CREATOR = new Parcelable.Creator<Format>() { // from class: com.google.android.exoplayer2.Format.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Format createFromParcel(Parcel in) {
            return new Format(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Format[] newArray(int size) {
            return new Format[size];
        }
    };
    public static final int NO_VALUE = -1;
    public static final long OFFSET_SAMPLE_RELATIVE = Long.MAX_VALUE;
    public final int accessibilityChannel;
    public final int averageBitrate;
    public final int bitrate;
    public final int channelCount;
    public final String codecs;
    public final ColorInfo colorInfo;
    public final String containerMimeType;
    public final DrmInitData drmInitData;
    public final int encoderDelay;
    public final int encoderPadding;
    public final Class<? extends ExoMediaCrypto> exoMediaCryptoType;
    public final float frameRate;
    private int hashCode;
    public final int height;
    public final String id;
    public final List<byte[]> initializationData;
    public final String label;
    public final String language;
    public final int maxInputSize;
    public final Metadata metadata;
    public final int pcmEncoding;
    public final int peakBitrate;
    public final float pixelWidthHeightRatio;
    public final byte[] projectionData;
    public final int roleFlags;
    public final int rotationDegrees;
    public final String sampleMimeType;
    public final int sampleRate;
    public final int selectionFlags;
    public final int stereoMode;
    public final long subsampleOffsetUs;
    public final int width;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static final class Builder {
        private int accessibilityChannel;
        private int averageBitrate;
        private int channelCount;
        private String codecs;
        private ColorInfo colorInfo;
        private String containerMimeType;
        private DrmInitData drmInitData;
        private int encoderDelay;
        private int encoderPadding;
        private Class<? extends ExoMediaCrypto> exoMediaCryptoType;
        private float frameRate;
        private int height;
        private String id;
        private List<byte[]> initializationData;
        private String label;
        private String language;
        private int maxInputSize;
        private Metadata metadata;
        private int pcmEncoding;
        private int peakBitrate;
        private float pixelWidthHeightRatio;
        private byte[] projectionData;
        private int roleFlags;
        private int rotationDegrees;
        private String sampleMimeType;
        private int sampleRate;
        private int selectionFlags;
        private int stereoMode;
        private long subsampleOffsetUs;
        private int width;

        public Builder() {
            this.averageBitrate = -1;
            this.peakBitrate = -1;
            this.maxInputSize = -1;
            this.subsampleOffsetUs = Long.MAX_VALUE;
            this.width = -1;
            this.height = -1;
            this.frameRate = -1.0f;
            this.pixelWidthHeightRatio = 1.0f;
            this.stereoMode = -1;
            this.channelCount = -1;
            this.sampleRate = -1;
            this.pcmEncoding = -1;
            this.accessibilityChannel = -1;
        }

        private Builder(Format format) {
            this.id = format.id;
            this.label = format.label;
            this.language = format.language;
            this.selectionFlags = format.selectionFlags;
            this.roleFlags = format.roleFlags;
            this.averageBitrate = format.averageBitrate;
            this.peakBitrate = format.peakBitrate;
            this.codecs = format.codecs;
            this.metadata = format.metadata;
            this.containerMimeType = format.containerMimeType;
            this.sampleMimeType = format.sampleMimeType;
            this.maxInputSize = format.maxInputSize;
            this.initializationData = format.initializationData;
            this.drmInitData = format.drmInitData;
            this.subsampleOffsetUs = format.subsampleOffsetUs;
            this.width = format.width;
            this.height = format.height;
            this.frameRate = format.frameRate;
            this.rotationDegrees = format.rotationDegrees;
            this.pixelWidthHeightRatio = format.pixelWidthHeightRatio;
            this.projectionData = format.projectionData;
            this.stereoMode = format.stereoMode;
            this.colorInfo = format.colorInfo;
            this.channelCount = format.channelCount;
            this.sampleRate = format.sampleRate;
            this.pcmEncoding = format.pcmEncoding;
            this.encoderDelay = format.encoderDelay;
            this.encoderPadding = format.encoderPadding;
            this.accessibilityChannel = format.accessibilityChannel;
            this.exoMediaCryptoType = format.exoMediaCryptoType;
        }

        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Builder setId(int id) {
            this.id = Integer.toString(id);
            return this;
        }

        public Builder setLabel(String label) {
            this.label = label;
            return this;
        }

        public Builder setLanguage(String language) {
            this.language = language;
            return this;
        }

        public Builder setSelectionFlags(int selectionFlags) {
            this.selectionFlags = selectionFlags;
            return this;
        }

        public Builder setRoleFlags(int roleFlags) {
            this.roleFlags = roleFlags;
            return this;
        }

        public Builder setAverageBitrate(int averageBitrate) {
            this.averageBitrate = averageBitrate;
            return this;
        }

        public Builder setPeakBitrate(int peakBitrate) {
            this.peakBitrate = peakBitrate;
            return this;
        }

        public Builder setCodecs(String codecs) {
            this.codecs = codecs;
            return this;
        }

        public Builder setMetadata(Metadata metadata) {
            this.metadata = metadata;
            return this;
        }

        public Builder setContainerMimeType(String containerMimeType) {
            this.containerMimeType = containerMimeType;
            return this;
        }

        public Builder setSampleMimeType(String sampleMimeType) {
            this.sampleMimeType = sampleMimeType;
            return this;
        }

        public Builder setMaxInputSize(int maxInputSize) {
            this.maxInputSize = maxInputSize;
            return this;
        }

        public Builder setInitializationData(List<byte[]> initializationData) {
            this.initializationData = initializationData;
            return this;
        }

        public Builder setDrmInitData(DrmInitData drmInitData) {
            this.drmInitData = drmInitData;
            return this;
        }

        public Builder setSubsampleOffsetUs(long subsampleOffsetUs) {
            this.subsampleOffsetUs = subsampleOffsetUs;
            return this;
        }

        public Builder setWidth(int width) {
            this.width = width;
            return this;
        }

        public Builder setHeight(int height) {
            this.height = height;
            return this;
        }

        public Builder setFrameRate(float frameRate) {
            this.frameRate = frameRate;
            return this;
        }

        public Builder setRotationDegrees(int rotationDegrees) {
            this.rotationDegrees = rotationDegrees;
            return this;
        }

        public Builder setPixelWidthHeightRatio(float pixelWidthHeightRatio) {
            this.pixelWidthHeightRatio = pixelWidthHeightRatio;
            return this;
        }

        public Builder setProjectionData(byte[] projectionData) {
            this.projectionData = projectionData;
            return this;
        }

        public Builder setStereoMode(int stereoMode) {
            this.stereoMode = stereoMode;
            return this;
        }

        public Builder setColorInfo(ColorInfo colorInfo) {
            this.colorInfo = colorInfo;
            return this;
        }

        public Builder setChannelCount(int channelCount) {
            this.channelCount = channelCount;
            return this;
        }

        public Builder setSampleRate(int sampleRate) {
            this.sampleRate = sampleRate;
            return this;
        }

        public Builder setPcmEncoding(int pcmEncoding) {
            this.pcmEncoding = pcmEncoding;
            return this;
        }

        public Builder setEncoderDelay(int encoderDelay) {
            this.encoderDelay = encoderDelay;
            return this;
        }

        public Builder setEncoderPadding(int encoderPadding) {
            this.encoderPadding = encoderPadding;
            return this;
        }

        public Builder setAccessibilityChannel(int accessibilityChannel) {
            this.accessibilityChannel = accessibilityChannel;
            return this;
        }

        public Builder setExoMediaCryptoType(Class<? extends ExoMediaCrypto> exoMediaCryptoType) {
            this.exoMediaCryptoType = exoMediaCryptoType;
            return this;
        }

        public Format build() {
            return new Format(this);
        }
    }

    @Deprecated
    public static Format createVideoSampleFormat(String id, String sampleMimeType, String codecs, int bitrate, int maxInputSize, int width, int height, float frameRate, List<byte[]> initializationData, DrmInitData drmInitData) {
        return new Builder().setId(id).setAverageBitrate(bitrate).setPeakBitrate(bitrate).setCodecs(codecs).setSampleMimeType(sampleMimeType).setMaxInputSize(maxInputSize).setInitializationData(initializationData).setDrmInitData(drmInitData).setWidth(width).setHeight(height).setFrameRate(frameRate).build();
    }

    @Deprecated
    public static Format createVideoSampleFormat(String id, String sampleMimeType, String codecs, int bitrate, int maxInputSize, int width, int height, float frameRate, List<byte[]> initializationData, int rotationDegrees, float pixelWidthHeightRatio, DrmInitData drmInitData) {
        return new Builder().setId(id).setAverageBitrate(bitrate).setPeakBitrate(bitrate).setCodecs(codecs).setSampleMimeType(sampleMimeType).setMaxInputSize(maxInputSize).setInitializationData(initializationData).setDrmInitData(drmInitData).setWidth(width).setHeight(height).setFrameRate(frameRate).setRotationDegrees(rotationDegrees).setPixelWidthHeightRatio(pixelWidthHeightRatio).build();
    }

    @Deprecated
    public static Format createAudioSampleFormat(String id, String sampleMimeType, String codecs, int bitrate, int maxInputSize, int channelCount, int sampleRate, List<byte[]> initializationData, DrmInitData drmInitData, int selectionFlags, String language) {
        return new Builder().setId(id).setLanguage(language).setSelectionFlags(selectionFlags).setAverageBitrate(bitrate).setPeakBitrate(bitrate).setCodecs(codecs).setSampleMimeType(sampleMimeType).setMaxInputSize(maxInputSize).setInitializationData(initializationData).setDrmInitData(drmInitData).setChannelCount(channelCount).setSampleRate(sampleRate).build();
    }

    @Deprecated
    public static Format createAudioSampleFormat(String id, String sampleMimeType, String codecs, int bitrate, int maxInputSize, int channelCount, int sampleRate, int pcmEncoding, List<byte[]> initializationData, DrmInitData drmInitData, int selectionFlags, String language) {
        return new Builder().setId(id).setLanguage(language).setSelectionFlags(selectionFlags).setAverageBitrate(bitrate).setPeakBitrate(bitrate).setCodecs(codecs).setSampleMimeType(sampleMimeType).setMaxInputSize(maxInputSize).setInitializationData(initializationData).setDrmInitData(drmInitData).setChannelCount(channelCount).setSampleRate(sampleRate).setPcmEncoding(pcmEncoding).build();
    }

    @Deprecated
    public static Format createContainerFormat(String id, String label, String containerMimeType, String sampleMimeType, String codecs, int bitrate, int selectionFlags, int roleFlags, String language) {
        return new Builder().setId(id).setLabel(label).setLanguage(language).setSelectionFlags(selectionFlags).setRoleFlags(roleFlags).setAverageBitrate(bitrate).setPeakBitrate(bitrate).setCodecs(codecs).setContainerMimeType(containerMimeType).setSampleMimeType(sampleMimeType).build();
    }

    @Deprecated
    public static Format createSampleFormat(String id, String sampleMimeType) {
        return new Builder().setId(id).setSampleMimeType(sampleMimeType).build();
    }

    private Format(Builder builder) {
        this.id = builder.id;
        this.label = builder.label;
        this.language = Util.normalizeLanguageCode(builder.language);
        this.selectionFlags = builder.selectionFlags;
        this.roleFlags = builder.roleFlags;
        int i = builder.averageBitrate;
        this.averageBitrate = i;
        int i2 = builder.peakBitrate;
        this.peakBitrate = i2;
        this.bitrate = i2 != -1 ? i2 : i;
        this.codecs = builder.codecs;
        this.metadata = builder.metadata;
        this.containerMimeType = builder.containerMimeType;
        this.sampleMimeType = builder.sampleMimeType;
        this.maxInputSize = builder.maxInputSize;
        this.initializationData = builder.initializationData == null ? Collections.emptyList() : builder.initializationData;
        DrmInitData drmInitData = builder.drmInitData;
        this.drmInitData = drmInitData;
        this.subsampleOffsetUs = builder.subsampleOffsetUs;
        this.width = builder.width;
        this.height = builder.height;
        this.frameRate = builder.frameRate;
        this.rotationDegrees = builder.rotationDegrees == -1 ? 0 : builder.rotationDegrees;
        this.pixelWidthHeightRatio = builder.pixelWidthHeightRatio == -1.0f ? 1.0f : builder.pixelWidthHeightRatio;
        this.projectionData = builder.projectionData;
        this.stereoMode = builder.stereoMode;
        this.colorInfo = builder.colorInfo;
        this.channelCount = builder.channelCount;
        this.sampleRate = builder.sampleRate;
        this.pcmEncoding = builder.pcmEncoding;
        this.encoderDelay = builder.encoderDelay == -1 ? 0 : builder.encoderDelay;
        this.encoderPadding = builder.encoderPadding != -1 ? builder.encoderPadding : 0;
        this.accessibilityChannel = builder.accessibilityChannel;
        if (builder.exoMediaCryptoType != null || drmInitData == null) {
            this.exoMediaCryptoType = builder.exoMediaCryptoType;
        } else {
            this.exoMediaCryptoType = UnsupportedMediaCrypto.class;
        }
    }

    Format(Parcel in) {
        this.id = in.readString();
        this.label = in.readString();
        this.language = in.readString();
        this.selectionFlags = in.readInt();
        this.roleFlags = in.readInt();
        int i = in.readInt();
        this.averageBitrate = i;
        int i2 = in.readInt();
        this.peakBitrate = i2;
        this.bitrate = i2 != -1 ? i2 : i;
        this.codecs = in.readString();
        this.metadata = (Metadata) in.readParcelable(Metadata.class.getClassLoader());
        this.containerMimeType = in.readString();
        this.sampleMimeType = in.readString();
        this.maxInputSize = in.readInt();
        int i3 = in.readInt();
        this.initializationData = new ArrayList(i3);
        for (int i4 = 0; i4 < i3; i4++) {
            this.initializationData.add((byte[]) Assertions.checkNotNull(in.createByteArray()));
        }
        DrmInitData drmInitData = (DrmInitData) in.readParcelable(DrmInitData.class.getClassLoader());
        this.drmInitData = drmInitData;
        this.subsampleOffsetUs = in.readLong();
        this.width = in.readInt();
        this.height = in.readInt();
        this.frameRate = in.readFloat();
        this.rotationDegrees = in.readInt();
        this.pixelWidthHeightRatio = in.readFloat();
        this.projectionData = Util.readBoolean(in) ? in.createByteArray() : null;
        this.stereoMode = in.readInt();
        this.colorInfo = (ColorInfo) in.readParcelable(ColorInfo.class.getClassLoader());
        this.channelCount = in.readInt();
        this.sampleRate = in.readInt();
        this.pcmEncoding = in.readInt();
        this.encoderDelay = in.readInt();
        this.encoderPadding = in.readInt();
        this.accessibilityChannel = in.readInt();
        this.exoMediaCryptoType = drmInitData != null ? UnsupportedMediaCrypto.class : null;
    }

    public Builder buildUpon() {
        return new Builder();
    }

    @Deprecated
    public Format copyWithMaxInputSize(int maxInputSize) {
        return buildUpon().setMaxInputSize(maxInputSize).build();
    }

    @Deprecated
    public Format copyWithSubsampleOffsetUs(long subsampleOffsetUs) {
        return buildUpon().setSubsampleOffsetUs(subsampleOffsetUs).build();
    }

    @Deprecated
    public Format copyWithLabel(String label) {
        return buildUpon().setLabel(label).build();
    }

    @Deprecated
    public Format copyWithManifestFormatInfo(Format manifestFormat) {
        return withManifestFormatInfo(manifestFormat);
    }

    public Format withManifestFormatInfo(Format manifestFormat) {
        String str;
        Metadata metadataCopyWithAppendedEntriesFrom;
        if (this == manifestFormat) {
            return this;
        }
        int trackType = MimeTypes.getTrackType(this.sampleMimeType);
        String str2 = manifestFormat.id;
        String str3 = manifestFormat.label;
        if (str3 == null) {
            str3 = this.label;
        }
        String str4 = this.language;
        if ((trackType == 3 || trackType == 1) && (str = manifestFormat.language) != null) {
            str4 = str;
        }
        int i = this.averageBitrate;
        if (i == -1) {
            i = manifestFormat.averageBitrate;
        }
        int i2 = this.peakBitrate;
        if (i2 == -1) {
            i2 = manifestFormat.peakBitrate;
        }
        String str5 = this.codecs;
        if (str5 == null) {
            String codecsOfType = Util.getCodecsOfType(manifestFormat.codecs, trackType);
            if (Util.splitCodecs(codecsOfType).length == 1) {
                str5 = codecsOfType;
            }
        }
        Metadata metadata = this.metadata;
        if (metadata == null) {
            metadataCopyWithAppendedEntriesFrom = manifestFormat.metadata;
        } else {
            metadataCopyWithAppendedEntriesFrom = metadata.copyWithAppendedEntriesFrom(manifestFormat.metadata);
        }
        float f = this.frameRate;
        if (f == -1.0f && trackType == 2) {
            f = manifestFormat.frameRate;
        }
        int i3 = this.selectionFlags | manifestFormat.selectionFlags;
        return buildUpon().setId(str2).setLabel(str3).setLanguage(str4).setSelectionFlags(i3).setRoleFlags(this.roleFlags | manifestFormat.roleFlags).setAverageBitrate(i).setPeakBitrate(i2).setCodecs(str5).setMetadata(metadataCopyWithAppendedEntriesFrom).setDrmInitData(DrmInitData.createSessionCreationData(manifestFormat.drmInitData, this.drmInitData)).setFrameRate(f).build();
    }

    @Deprecated
    public Format copyWithGaplessInfo(int encoderDelay, int encoderPadding) {
        return buildUpon().setEncoderDelay(encoderDelay).setEncoderPadding(encoderPadding).build();
    }

    @Deprecated
    public Format copyWithFrameRate(float frameRate) {
        return buildUpon().setFrameRate(frameRate).build();
    }

    @Deprecated
    public Format copyWithDrmInitData(DrmInitData drmInitData) {
        return buildUpon().setDrmInitData(drmInitData).build();
    }

    @Deprecated
    public Format copyWithMetadata(Metadata metadata) {
        return buildUpon().setMetadata(metadata).build();
    }

    @Deprecated
    public Format copyWithBitrate(int bitrate) {
        return buildUpon().setAverageBitrate(bitrate).setPeakBitrate(bitrate).build();
    }

    @Deprecated
    public Format copyWithVideoSize(int width, int height) {
        return buildUpon().setWidth(width).setHeight(height).build();
    }

    public Format copyWithExoMediaCryptoType(Class<? extends ExoMediaCrypto> exoMediaCryptoType) {
        return buildUpon().setExoMediaCryptoType(exoMediaCryptoType).build();
    }

    public int getPixelCount() {
        int i;
        int i2 = this.width;
        if (i2 == -1 || (i = this.height) == -1) {
            return -1;
        }
        return i2 * i;
    }

    public String toString() {
        String str = this.id;
        String str2 = this.label;
        String str3 = this.containerMimeType;
        String str4 = this.sampleMimeType;
        String str5 = this.codecs;
        int i = this.bitrate;
        String str6 = this.language;
        int i2 = this.width;
        int i3 = this.height;
        float f = this.frameRate;
        int i4 = this.channelCount;
        return new StringBuilder(String.valueOf(str).length() + 104 + String.valueOf(str2).length() + String.valueOf(str3).length() + String.valueOf(str4).length() + String.valueOf(str5).length() + String.valueOf(str6).length()).append("Format(").append(str).append(", ").append(str2).append(", ").append(str3).append(", ").append(str4).append(", ").append(str5).append(", ").append(i).append(", ").append(str6).append(", [").append(i2).append(", ").append(i3).append(", ").append(f).append("], [").append(i4).append(", ").append(this.sampleRate).append("])").toString();
    }

    public int hashCode() {
        if (this.hashCode == 0) {
            String str = this.id;
            int iHashCode = (527 + (str == null ? 0 : str.hashCode())) * 31;
            String str2 = this.label;
            int iHashCode2 = (iHashCode + (str2 != null ? str2.hashCode() : 0)) * 31;
            String str3 = this.language;
            int iHashCode3 = (((((((((iHashCode2 + (str3 == null ? 0 : str3.hashCode())) * 31) + this.selectionFlags) * 31) + this.roleFlags) * 31) + this.averageBitrate) * 31) + this.peakBitrate) * 31;
            String str4 = this.codecs;
            int iHashCode4 = (iHashCode3 + (str4 == null ? 0 : str4.hashCode())) * 31;
            Metadata metadata = this.metadata;
            int iHashCode5 = (iHashCode4 + (metadata == null ? 0 : metadata.hashCode())) * 31;
            String str5 = this.containerMimeType;
            int iHashCode6 = (iHashCode5 + (str5 == null ? 0 : str5.hashCode())) * 31;
            String str6 = this.sampleMimeType;
            int iHashCode7 = (((((((((((((((((((((((((((((iHashCode6 + (str6 == null ? 0 : str6.hashCode())) * 31) + this.maxInputSize) * 31) + ((int) this.subsampleOffsetUs)) * 31) + this.width) * 31) + this.height) * 31) + Float.floatToIntBits(this.frameRate)) * 31) + this.rotationDegrees) * 31) + Float.floatToIntBits(this.pixelWidthHeightRatio)) * 31) + this.stereoMode) * 31) + this.channelCount) * 31) + this.sampleRate) * 31) + this.pcmEncoding) * 31) + this.encoderDelay) * 31) + this.encoderPadding) * 31) + this.accessibilityChannel) * 31;
            Class<? extends ExoMediaCrypto> cls = this.exoMediaCryptoType;
            this.hashCode = iHashCode7 + (cls != null ? cls.hashCode() : 0);
        }
        return this.hashCode;
    }

    public boolean equals(Object obj) {
        int i;
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Format format = (Format) obj;
        int i2 = this.hashCode;
        if (i2 == 0 || (i = format.hashCode) == 0 || i2 == i) {
            return this.selectionFlags == format.selectionFlags && this.roleFlags == format.roleFlags && this.averageBitrate == format.averageBitrate && this.peakBitrate == format.peakBitrate && this.maxInputSize == format.maxInputSize && this.subsampleOffsetUs == format.subsampleOffsetUs && this.width == format.width && this.height == format.height && this.rotationDegrees == format.rotationDegrees && this.stereoMode == format.stereoMode && this.channelCount == format.channelCount && this.sampleRate == format.sampleRate && this.pcmEncoding == format.pcmEncoding && this.encoderDelay == format.encoderDelay && this.encoderPadding == format.encoderPadding && this.accessibilityChannel == format.accessibilityChannel && Float.compare(this.frameRate, format.frameRate) == 0 && Float.compare(this.pixelWidthHeightRatio, format.pixelWidthHeightRatio) == 0 && Util.areEqual(this.exoMediaCryptoType, format.exoMediaCryptoType) && Util.areEqual(this.id, format.id) && Util.areEqual(this.label, format.label) && Util.areEqual(this.codecs, format.codecs) && Util.areEqual(this.containerMimeType, format.containerMimeType) && Util.areEqual(this.sampleMimeType, format.sampleMimeType) && Util.areEqual(this.language, format.language) && Arrays.equals(this.projectionData, format.projectionData) && Util.areEqual(this.metadata, format.metadata) && Util.areEqual(this.colorInfo, format.colorInfo) && Util.areEqual(this.drmInitData, format.drmInitData) && initializationDataEquals(format);
        }
        return false;
    }

    public boolean initializationDataEquals(Format other) {
        if (this.initializationData.size() != other.initializationData.size()) {
            return false;
        }
        for (int i = 0; i < this.initializationData.size(); i++) {
            if (!Arrays.equals(this.initializationData.get(i), other.initializationData.get(i))) {
                return false;
            }
        }
        return true;
    }

    public static String toLogString(Format format) {
        if (format == null) {
            return "null";
        }
        StringBuilder sb = new StringBuilder("id=");
        sb.append(format.id).append(", mimeType=").append(format.sampleMimeType);
        if (format.bitrate != -1) {
            sb.append(", bitrate=").append(format.bitrate);
        }
        if (format.codecs != null) {
            sb.append(", codecs=").append(format.codecs);
        }
        if (format.drmInitData != null) {
            LinkedHashSet linkedHashSet = new LinkedHashSet();
            for (int i = 0; i < format.drmInitData.schemeDataCount; i++) {
                UUID uuid = format.drmInitData.get(i).uuid;
                if (uuid.equals(C.COMMON_PSSH_UUID)) {
                    linkedHashSet.add(C.CENC_TYPE_cenc);
                } else if (uuid.equals(C.CLEARKEY_UUID)) {
                    linkedHashSet.add("clearkey");
                } else if (uuid.equals(C.PLAYREADY_UUID)) {
                    linkedHashSet.add("playready");
                } else if (uuid.equals(C.WIDEVINE_UUID)) {
                    linkedHashSet.add("widevine");
                } else if (uuid.equals(C.UUID_NIL)) {
                    linkedHashSet.add("universal");
                } else {
                    String strValueOf = String.valueOf(uuid);
                    linkedHashSet.add(new StringBuilder(String.valueOf(strValueOf).length() + 10).append("unknown (").append(strValueOf).append(")").toString());
                }
            }
            sb.append(", drm=[").append(Joiner.on(',').join(linkedHashSet)).append(']');
        }
        if (format.width != -1 && format.height != -1) {
            sb.append(", res=").append(format.width).append("x").append(format.height);
        }
        if (format.frameRate != -1.0f) {
            sb.append(", fps=").append(format.frameRate);
        }
        if (format.channelCount != -1) {
            sb.append(", channels=").append(format.channelCount);
        }
        if (format.sampleRate != -1) {
            sb.append(", sample_rate=").append(format.sampleRate);
        }
        if (format.language != null) {
            sb.append(", language=").append(format.language);
        }
        if (format.label != null) {
            sb.append(", label=").append(format.label);
        }
        if ((format.roleFlags & 16384) != 0) {
            sb.append(", trick-play-track");
        }
        return sb.toString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.label);
        dest.writeString(this.language);
        dest.writeInt(this.selectionFlags);
        dest.writeInt(this.roleFlags);
        dest.writeInt(this.averageBitrate);
        dest.writeInt(this.peakBitrate);
        dest.writeString(this.codecs);
        dest.writeParcelable(this.metadata, 0);
        dest.writeString(this.containerMimeType);
        dest.writeString(this.sampleMimeType);
        dest.writeInt(this.maxInputSize);
        int size = this.initializationData.size();
        dest.writeInt(size);
        for (int i = 0; i < size; i++) {
            dest.writeByteArray(this.initializationData.get(i));
        }
        dest.writeParcelable(this.drmInitData, 0);
        dest.writeLong(this.subsampleOffsetUs);
        dest.writeInt(this.width);
        dest.writeInt(this.height);
        dest.writeFloat(this.frameRate);
        dest.writeInt(this.rotationDegrees);
        dest.writeFloat(this.pixelWidthHeightRatio);
        Util.writeBoolean(dest, this.projectionData != null);
        byte[] bArr = this.projectionData;
        if (bArr != null) {
            dest.writeByteArray(bArr);
        }
        dest.writeInt(this.stereoMode);
        dest.writeParcelable(this.colorInfo, flags);
        dest.writeInt(this.channelCount);
        dest.writeInt(this.sampleRate);
        dest.writeInt(this.pcmEncoding);
        dest.writeInt(this.encoderDelay);
        dest.writeInt(this.encoderPadding);
        dest.writeInt(this.accessibilityChannel);
    }
}
