package com.google.android.exoplayer2;

import android.net.Uri;
import android.os.Bundle;
import com.google.android.exoplayer2.Bundleable;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.offline.StreamKey;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/* loaded from: classes.dex */
public final class MediaItem implements Bundleable {
    public static final String DEFAULT_MEDIA_ID = "";
    private static final int FIELD_CLIPPING_PROPERTIES = 3;
    private static final int FIELD_LIVE_CONFIGURATION = 1;
    private static final int FIELD_MEDIA_ID = 0;
    private static final int FIELD_MEDIA_METADATA = 2;
    public final ClippingProperties clippingProperties;
    public final LiveConfiguration liveConfiguration;
    public final String mediaId;
    public final MediaMetadata mediaMetadata;
    public final PlaybackProperties playbackProperties;
    public static final MediaItem EMPTY = new Builder().build();
    public static final Bundleable.Creator<MediaItem> CREATOR = new Bundleable.Creator() { // from class: com.google.android.exoplayer2.MediaItem$$ExternalSyntheticLambda0
        @Override // com.google.android.exoplayer2.Bundleable.Creator
        public final Bundleable fromBundle(Bundle bundle) {
            return MediaItem.fromBundle(bundle);
        }
    };

    public static MediaItem fromUri(String uri) {
        return new Builder().setUri(uri).build();
    }

    public static MediaItem fromUri(Uri uri) {
        return new Builder().setUri(uri).build();
    }

    public static final class Builder {
        private Uri adTagUri;
        private Object adsId;
        private long clipEndPositionMs;
        private boolean clipRelativeToDefaultPosition;
        private boolean clipRelativeToLiveWindow;
        private long clipStartPositionMs;
        private boolean clipStartsAtKeyFrame;
        private String customCacheKey;
        private boolean drmForceDefaultLicenseUri;
        private byte[] drmKeySetId;
        private Map<String, String> drmLicenseRequestHeaders;
        private Uri drmLicenseUri;
        private boolean drmMultiSession;
        private boolean drmPlayClearContentWithoutKey;
        private List<Integer> drmSessionForClearTypes;
        private UUID drmUuid;
        private long liveMaxOffsetMs;
        private float liveMaxPlaybackSpeed;
        private long liveMinOffsetMs;
        private float liveMinPlaybackSpeed;
        private long liveTargetOffsetMs;
        private String mediaId;
        private MediaMetadata mediaMetadata;
        private String mimeType;
        private List<StreamKey> streamKeys;
        private List<Subtitle> subtitles;
        private Object tag;
        private Uri uri;

        public Builder() {
            this.clipEndPositionMs = Long.MIN_VALUE;
            this.drmSessionForClearTypes = Collections.emptyList();
            this.drmLicenseRequestHeaders = Collections.emptyMap();
            this.streamKeys = Collections.emptyList();
            this.subtitles = Collections.emptyList();
            this.liveTargetOffsetMs = C.TIME_UNSET;
            this.liveMinOffsetMs = C.TIME_UNSET;
            this.liveMaxOffsetMs = C.TIME_UNSET;
            this.liveMinPlaybackSpeed = -3.4028235E38f;
            this.liveMaxPlaybackSpeed = -3.4028235E38f;
        }

        private Builder(MediaItem mediaItem) {
            this();
            this.clipEndPositionMs = mediaItem.clippingProperties.endPositionMs;
            this.clipRelativeToLiveWindow = mediaItem.clippingProperties.relativeToLiveWindow;
            this.clipRelativeToDefaultPosition = mediaItem.clippingProperties.relativeToDefaultPosition;
            this.clipStartPositionMs = mediaItem.clippingProperties.startPositionMs;
            this.clipStartsAtKeyFrame = mediaItem.clippingProperties.startsAtKeyFrame;
            this.mediaId = mediaItem.mediaId;
            this.mediaMetadata = mediaItem.mediaMetadata;
            this.liveTargetOffsetMs = mediaItem.liveConfiguration.targetOffsetMs;
            this.liveMinOffsetMs = mediaItem.liveConfiguration.minOffsetMs;
            this.liveMaxOffsetMs = mediaItem.liveConfiguration.maxOffsetMs;
            this.liveMinPlaybackSpeed = mediaItem.liveConfiguration.minPlaybackSpeed;
            this.liveMaxPlaybackSpeed = mediaItem.liveConfiguration.maxPlaybackSpeed;
            PlaybackProperties playbackProperties = mediaItem.playbackProperties;
            if (playbackProperties != null) {
                this.customCacheKey = playbackProperties.customCacheKey;
                this.mimeType = playbackProperties.mimeType;
                this.uri = playbackProperties.uri;
                this.streamKeys = playbackProperties.streamKeys;
                this.subtitles = playbackProperties.subtitles;
                this.tag = playbackProperties.tag;
                DrmConfiguration drmConfiguration = playbackProperties.drmConfiguration;
                if (drmConfiguration != null) {
                    this.drmLicenseUri = drmConfiguration.licenseUri;
                    this.drmLicenseRequestHeaders = drmConfiguration.requestHeaders;
                    this.drmMultiSession = drmConfiguration.multiSession;
                    this.drmForceDefaultLicenseUri = drmConfiguration.forceDefaultLicenseUri;
                    this.drmPlayClearContentWithoutKey = drmConfiguration.playClearContentWithoutKey;
                    this.drmSessionForClearTypes = drmConfiguration.sessionForClearTypes;
                    this.drmUuid = drmConfiguration.uuid;
                    this.drmKeySetId = drmConfiguration.getKeySetId();
                }
                AdsConfiguration adsConfiguration = playbackProperties.adsConfiguration;
                if (adsConfiguration != null) {
                    this.adTagUri = adsConfiguration.adTagUri;
                    this.adsId = adsConfiguration.adsId;
                }
            }
        }

        public Builder setMediaId(String mediaId) {
            this.mediaId = (String) Assertions.checkNotNull(mediaId);
            return this;
        }

        public Builder setUri(String uri) {
            return setUri(uri == null ? null : Uri.parse(uri));
        }

        public Builder setUri(Uri uri) {
            this.uri = uri;
            return this;
        }

        public Builder setMimeType(String mimeType) {
            this.mimeType = mimeType;
            return this;
        }

        public Builder setClipStartPositionMs(long startPositionMs) {
            Assertions.checkArgument(startPositionMs >= 0);
            this.clipStartPositionMs = startPositionMs;
            return this;
        }

        public Builder setClipEndPositionMs(long endPositionMs) {
            Assertions.checkArgument(endPositionMs == Long.MIN_VALUE || endPositionMs >= 0);
            this.clipEndPositionMs = endPositionMs;
            return this;
        }

        public Builder setClipRelativeToLiveWindow(boolean relativeToLiveWindow) {
            this.clipRelativeToLiveWindow = relativeToLiveWindow;
            return this;
        }

        public Builder setClipRelativeToDefaultPosition(boolean relativeToDefaultPosition) {
            this.clipRelativeToDefaultPosition = relativeToDefaultPosition;
            return this;
        }

        public Builder setClipStartsAtKeyFrame(boolean startsAtKeyFrame) {
            this.clipStartsAtKeyFrame = startsAtKeyFrame;
            return this;
        }

        public Builder setDrmLicenseUri(Uri licenseUri) {
            this.drmLicenseUri = licenseUri;
            return this;
        }

        public Builder setDrmLicenseUri(String licenseUri) {
            this.drmLicenseUri = licenseUri == null ? null : Uri.parse(licenseUri);
            return this;
        }

        public Builder setDrmLicenseRequestHeaders(Map<String, String> licenseRequestHeaders) {
            Map<String, String> mapEmptyMap;
            if (licenseRequestHeaders != null && !licenseRequestHeaders.isEmpty()) {
                mapEmptyMap = Collections.unmodifiableMap(new HashMap(licenseRequestHeaders));
            } else {
                mapEmptyMap = Collections.emptyMap();
            }
            this.drmLicenseRequestHeaders = mapEmptyMap;
            return this;
        }

        public Builder setDrmUuid(UUID uuid) {
            this.drmUuid = uuid;
            return this;
        }

        public Builder setDrmMultiSession(boolean multiSession) {
            this.drmMultiSession = multiSession;
            return this;
        }

        public Builder setDrmForceDefaultLicenseUri(boolean forceDefaultLicenseUri) {
            this.drmForceDefaultLicenseUri = forceDefaultLicenseUri;
            return this;
        }

        public Builder setDrmPlayClearContentWithoutKey(boolean playClearContentWithoutKey) {
            this.drmPlayClearContentWithoutKey = playClearContentWithoutKey;
            return this;
        }

        public Builder setDrmSessionForClearPeriods(boolean sessionForClearPeriods) {
            setDrmSessionForClearTypes(sessionForClearPeriods ? Arrays.asList(2, 1) : Collections.emptyList());
            return this;
        }

        public Builder setDrmSessionForClearTypes(List<Integer> sessionForClearTypes) {
            List<Integer> listEmptyList;
            if (sessionForClearTypes != null && !sessionForClearTypes.isEmpty()) {
                listEmptyList = Collections.unmodifiableList(new ArrayList(sessionForClearTypes));
            } else {
                listEmptyList = Collections.emptyList();
            }
            this.drmSessionForClearTypes = listEmptyList;
            return this;
        }

        public Builder setDrmKeySetId(byte[] keySetId) {
            this.drmKeySetId = keySetId != null ? Arrays.copyOf(keySetId, keySetId.length) : null;
            return this;
        }

        public Builder setStreamKeys(List<StreamKey> streamKeys) {
            List<StreamKey> listEmptyList;
            if (streamKeys != null && !streamKeys.isEmpty()) {
                listEmptyList = Collections.unmodifiableList(new ArrayList(streamKeys));
            } else {
                listEmptyList = Collections.emptyList();
            }
            this.streamKeys = listEmptyList;
            return this;
        }

        public Builder setCustomCacheKey(String customCacheKey) {
            this.customCacheKey = customCacheKey;
            return this;
        }

        public Builder setSubtitles(List<Subtitle> subtitles) {
            List<Subtitle> listEmptyList;
            if (subtitles != null && !subtitles.isEmpty()) {
                listEmptyList = Collections.unmodifiableList(new ArrayList(subtitles));
            } else {
                listEmptyList = Collections.emptyList();
            }
            this.subtitles = listEmptyList;
            return this;
        }

        public Builder setAdTagUri(String adTagUri) {
            return setAdTagUri(adTagUri != null ? Uri.parse(adTagUri) : null);
        }

        public Builder setAdTagUri(Uri adTagUri) {
            return setAdTagUri(adTagUri, null);
        }

        public Builder setAdTagUri(Uri adTagUri, Object adsId) {
            this.adTagUri = adTagUri;
            this.adsId = adsId;
            return this;
        }

        public Builder setLiveTargetOffsetMs(long liveTargetOffsetMs) {
            this.liveTargetOffsetMs = liveTargetOffsetMs;
            return this;
        }

        public Builder setLiveMinOffsetMs(long liveMinOffsetMs) {
            this.liveMinOffsetMs = liveMinOffsetMs;
            return this;
        }

        public Builder setLiveMaxOffsetMs(long liveMaxOffsetMs) {
            this.liveMaxOffsetMs = liveMaxOffsetMs;
            return this;
        }

        public Builder setLiveMinPlaybackSpeed(float minPlaybackSpeed) {
            this.liveMinPlaybackSpeed = minPlaybackSpeed;
            return this;
        }

        public Builder setLiveMaxPlaybackSpeed(float maxPlaybackSpeed) {
            this.liveMaxPlaybackSpeed = maxPlaybackSpeed;
            return this;
        }

        public Builder setTag(Object tag) {
            this.tag = tag;
            return this;
        }

        public Builder setMediaMetadata(MediaMetadata mediaMetadata) {
            this.mediaMetadata = mediaMetadata;
            return this;
        }

        public MediaItem build() {
            PlaybackProperties playbackProperties;
            Assertions.checkState(this.drmLicenseUri == null || this.drmUuid != null);
            Uri uri = this.uri;
            if (uri != null) {
                playbackProperties = new PlaybackProperties(uri, this.mimeType, this.drmUuid != null ? new DrmConfiguration(this.drmUuid, this.drmLicenseUri, this.drmLicenseRequestHeaders, this.drmMultiSession, this.drmForceDefaultLicenseUri, this.drmPlayClearContentWithoutKey, this.drmSessionForClearTypes, this.drmKeySetId) : null, this.adTagUri != null ? new AdsConfiguration(this.adTagUri, this.adsId) : null, this.streamKeys, this.customCacheKey, this.subtitles, this.tag);
            } else {
                playbackProperties = null;
            }
            String str = this.mediaId;
            if (str == null) {
                str = "";
            }
            String str2 = str;
            ClippingProperties clippingProperties = new ClippingProperties(this.clipStartPositionMs, this.clipEndPositionMs, this.clipRelativeToLiveWindow, this.clipRelativeToDefaultPosition, this.clipStartsAtKeyFrame);
            LiveConfiguration liveConfiguration = new LiveConfiguration(this.liveTargetOffsetMs, this.liveMinOffsetMs, this.liveMaxOffsetMs, this.liveMinPlaybackSpeed, this.liveMaxPlaybackSpeed);
            MediaMetadata mediaMetadata = this.mediaMetadata;
            if (mediaMetadata == null) {
                mediaMetadata = MediaMetadata.EMPTY;
            }
            return new MediaItem(str2, clippingProperties, playbackProperties, liveConfiguration, mediaMetadata);
        }
    }

    public static final class DrmConfiguration {
        public final boolean forceDefaultLicenseUri;
        private final byte[] keySetId;
        public final Uri licenseUri;
        public final boolean multiSession;
        public final boolean playClearContentWithoutKey;
        public final Map<String, String> requestHeaders;
        public final List<Integer> sessionForClearTypes;
        public final UUID uuid;

        private DrmConfiguration(UUID uuid, Uri licenseUri, Map<String, String> requestHeaders, boolean multiSession, boolean forceDefaultLicenseUri, boolean playClearContentWithoutKey, List<Integer> drmSessionForClearTypes, byte[] keySetId) {
            Assertions.checkArgument((forceDefaultLicenseUri && licenseUri == null) ? false : true);
            this.uuid = uuid;
            this.licenseUri = licenseUri;
            this.requestHeaders = requestHeaders;
            this.multiSession = multiSession;
            this.forceDefaultLicenseUri = forceDefaultLicenseUri;
            this.playClearContentWithoutKey = playClearContentWithoutKey;
            this.sessionForClearTypes = drmSessionForClearTypes;
            this.keySetId = keySetId != null ? Arrays.copyOf(keySetId, keySetId.length) : null;
        }

        public byte[] getKeySetId() {
            byte[] bArr = this.keySetId;
            if (bArr != null) {
                return Arrays.copyOf(bArr, bArr.length);
            }
            return null;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof DrmConfiguration)) {
                return false;
            }
            DrmConfiguration drmConfiguration = (DrmConfiguration) obj;
            return this.uuid.equals(drmConfiguration.uuid) && Util.areEqual(this.licenseUri, drmConfiguration.licenseUri) && Util.areEqual(this.requestHeaders, drmConfiguration.requestHeaders) && this.multiSession == drmConfiguration.multiSession && this.forceDefaultLicenseUri == drmConfiguration.forceDefaultLicenseUri && this.playClearContentWithoutKey == drmConfiguration.playClearContentWithoutKey && this.sessionForClearTypes.equals(drmConfiguration.sessionForClearTypes) && Arrays.equals(this.keySetId, drmConfiguration.keySetId);
        }

        public int hashCode() {
            int iHashCode = this.uuid.hashCode() * 31;
            Uri uri = this.licenseUri;
            return ((((((((((((iHashCode + (uri != null ? uri.hashCode() : 0)) * 31) + this.requestHeaders.hashCode()) * 31) + (this.multiSession ? 1 : 0)) * 31) + (this.forceDefaultLicenseUri ? 1 : 0)) * 31) + (this.playClearContentWithoutKey ? 1 : 0)) * 31) + this.sessionForClearTypes.hashCode()) * 31) + Arrays.hashCode(this.keySetId);
        }
    }

    public static final class AdsConfiguration {
        public final Uri adTagUri;
        public final Object adsId;

        private AdsConfiguration(Uri adTagUri, Object adsId) {
            this.adTagUri = adTagUri;
            this.adsId = adsId;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof AdsConfiguration)) {
                return false;
            }
            AdsConfiguration adsConfiguration = (AdsConfiguration) obj;
            return this.adTagUri.equals(adsConfiguration.adTagUri) && Util.areEqual(this.adsId, adsConfiguration.adsId);
        }

        public int hashCode() {
            int iHashCode = this.adTagUri.hashCode() * 31;
            Object obj = this.adsId;
            return iHashCode + (obj != null ? obj.hashCode() : 0);
        }
    }

    public static final class PlaybackProperties {
        public final AdsConfiguration adsConfiguration;
        public final String customCacheKey;
        public final DrmConfiguration drmConfiguration;
        public final String mimeType;
        public final List<StreamKey> streamKeys;
        public final List<Subtitle> subtitles;
        public final Object tag;
        public final Uri uri;

        private PlaybackProperties(Uri uri, String mimeType, DrmConfiguration drmConfiguration, AdsConfiguration adsConfiguration, List<StreamKey> streamKeys, String customCacheKey, List<Subtitle> subtitles, Object tag) {
            this.uri = uri;
            this.mimeType = mimeType;
            this.drmConfiguration = drmConfiguration;
            this.adsConfiguration = adsConfiguration;
            this.streamKeys = streamKeys;
            this.customCacheKey = customCacheKey;
            this.subtitles = subtitles;
            this.tag = tag;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof PlaybackProperties)) {
                return false;
            }
            PlaybackProperties playbackProperties = (PlaybackProperties) obj;
            return this.uri.equals(playbackProperties.uri) && Util.areEqual(this.mimeType, playbackProperties.mimeType) && Util.areEqual(this.drmConfiguration, playbackProperties.drmConfiguration) && Util.areEqual(this.adsConfiguration, playbackProperties.adsConfiguration) && this.streamKeys.equals(playbackProperties.streamKeys) && Util.areEqual(this.customCacheKey, playbackProperties.customCacheKey) && this.subtitles.equals(playbackProperties.subtitles) && Util.areEqual(this.tag, playbackProperties.tag);
        }

        public int hashCode() {
            int iHashCode = this.uri.hashCode() * 31;
            String str = this.mimeType;
            int iHashCode2 = (iHashCode + (str == null ? 0 : str.hashCode())) * 31;
            DrmConfiguration drmConfiguration = this.drmConfiguration;
            int iHashCode3 = (iHashCode2 + (drmConfiguration == null ? 0 : drmConfiguration.hashCode())) * 31;
            AdsConfiguration adsConfiguration = this.adsConfiguration;
            int iHashCode4 = (((iHashCode3 + (adsConfiguration == null ? 0 : adsConfiguration.hashCode())) * 31) + this.streamKeys.hashCode()) * 31;
            String str2 = this.customCacheKey;
            int iHashCode5 = (((iHashCode4 + (str2 == null ? 0 : str2.hashCode())) * 31) + this.subtitles.hashCode()) * 31;
            Object obj = this.tag;
            return iHashCode5 + (obj != null ? obj.hashCode() : 0);
        }
    }

    public static final class LiveConfiguration implements Bundleable {
        private static final int FIELD_MAX_OFFSET_MS = 2;
        private static final int FIELD_MAX_PLAYBACK_SPEED = 4;
        private static final int FIELD_MIN_OFFSET_MS = 1;
        private static final int FIELD_MIN_PLAYBACK_SPEED = 3;
        private static final int FIELD_TARGET_OFFSET_MS = 0;
        public final long maxOffsetMs;
        public final float maxPlaybackSpeed;
        public final long minOffsetMs;
        public final float minPlaybackSpeed;
        public final long targetOffsetMs;
        public static final LiveConfiguration UNSET = new LiveConfiguration(C.TIME_UNSET, C.TIME_UNSET, C.TIME_UNSET, -3.4028235E38f, -3.4028235E38f);
        public static final Bundleable.Creator<LiveConfiguration> CREATOR = new Bundleable.Creator() { // from class: com.google.android.exoplayer2.MediaItem$LiveConfiguration$$ExternalSyntheticLambda0
            @Override // com.google.android.exoplayer2.Bundleable.Creator
            public final Bundleable fromBundle(Bundle bundle) {
                return MediaItem.LiveConfiguration.lambda$static$0(bundle);
            }
        };

        public LiveConfiguration(long targetOffsetMs, long minOffsetMs, long maxOffsetMs, float minPlaybackSpeed, float maxPlaybackSpeed) {
            this.targetOffsetMs = targetOffsetMs;
            this.minOffsetMs = minOffsetMs;
            this.maxOffsetMs = maxOffsetMs;
            this.minPlaybackSpeed = minPlaybackSpeed;
            this.maxPlaybackSpeed = maxPlaybackSpeed;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof LiveConfiguration)) {
                return false;
            }
            LiveConfiguration liveConfiguration = (LiveConfiguration) obj;
            return this.targetOffsetMs == liveConfiguration.targetOffsetMs && this.minOffsetMs == liveConfiguration.minOffsetMs && this.maxOffsetMs == liveConfiguration.maxOffsetMs && this.minPlaybackSpeed == liveConfiguration.minPlaybackSpeed && this.maxPlaybackSpeed == liveConfiguration.maxPlaybackSpeed;
        }

        public int hashCode() {
            long j = this.targetOffsetMs;
            long j2 = this.minOffsetMs;
            int i = ((((int) (j ^ (j >>> 32))) * 31) + ((int) (j2 ^ (j2 >>> 32)))) * 31;
            long j3 = this.maxOffsetMs;
            int i2 = (i + ((int) ((j3 >>> 32) ^ j3))) * 31;
            float f = this.minPlaybackSpeed;
            int iFloatToIntBits = (i2 + (f != 0.0f ? Float.floatToIntBits(f) : 0)) * 31;
            float f2 = this.maxPlaybackSpeed;
            return iFloatToIntBits + (f2 != 0.0f ? Float.floatToIntBits(f2) : 0);
        }

        @Override // com.google.android.exoplayer2.Bundleable
        public Bundle toBundle() {
            Bundle bundle = new Bundle();
            bundle.putLong(keyForField(0), this.targetOffsetMs);
            bundle.putLong(keyForField(1), this.minOffsetMs);
            bundle.putLong(keyForField(2), this.maxOffsetMs);
            bundle.putFloat(keyForField(3), this.minPlaybackSpeed);
            bundle.putFloat(keyForField(4), this.maxPlaybackSpeed);
            return bundle;
        }

        static /* synthetic */ LiveConfiguration lambda$static$0(Bundle bundle) {
            return new LiveConfiguration(bundle.getLong(keyForField(0), C.TIME_UNSET), bundle.getLong(keyForField(1), C.TIME_UNSET), bundle.getLong(keyForField(2), C.TIME_UNSET), bundle.getFloat(keyForField(3), -3.4028235E38f), bundle.getFloat(keyForField(4), -3.4028235E38f));
        }

        private static String keyForField(int field) {
            return Integer.toString(field, 36);
        }
    }

    public static final class Subtitle {
        public final String label;
        public final String language;
        public final String mimeType;
        public final int roleFlags;
        public final int selectionFlags;
        public final Uri uri;

        public Subtitle(Uri uri, String mimeType, String language) {
            this(uri, mimeType, language, 0);
        }

        public Subtitle(Uri uri, String mimeType, String language, int selectionFlags) {
            this(uri, mimeType, language, selectionFlags, 0, null);
        }

        public Subtitle(Uri uri, String mimeType, String language, int selectionFlags, int roleFlags, String label) {
            this.uri = uri;
            this.mimeType = mimeType;
            this.language = language;
            this.selectionFlags = selectionFlags;
            this.roleFlags = roleFlags;
            this.label = label;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Subtitle)) {
                return false;
            }
            Subtitle subtitle = (Subtitle) obj;
            return this.uri.equals(subtitle.uri) && this.mimeType.equals(subtitle.mimeType) && Util.areEqual(this.language, subtitle.language) && this.selectionFlags == subtitle.selectionFlags && this.roleFlags == subtitle.roleFlags && Util.areEqual(this.label, subtitle.label);
        }

        public int hashCode() {
            int iHashCode = ((this.uri.hashCode() * 31) + this.mimeType.hashCode()) * 31;
            String str = this.language;
            int iHashCode2 = (((((iHashCode + (str == null ? 0 : str.hashCode())) * 31) + this.selectionFlags) * 31) + this.roleFlags) * 31;
            String str2 = this.label;
            return iHashCode2 + (str2 != null ? str2.hashCode() : 0);
        }
    }

    public static final class ClippingProperties implements Bundleable {
        public static final Bundleable.Creator<ClippingProperties> CREATOR = new Bundleable.Creator() { // from class: com.google.android.exoplayer2.MediaItem$ClippingProperties$$ExternalSyntheticLambda0
            @Override // com.google.android.exoplayer2.Bundleable.Creator
            public final Bundleable fromBundle(Bundle bundle) {
                return MediaItem.ClippingProperties.lambda$static$0(bundle);
            }
        };
        private static final int FIELD_END_POSITION_MS = 1;
        private static final int FIELD_RELATIVE_TO_DEFAULT_POSITION = 3;
        private static final int FIELD_RELATIVE_TO_LIVE_WINDOW = 2;
        private static final int FIELD_STARTS_AT_KEY_FRAME = 4;
        private static final int FIELD_START_POSITION_MS = 0;
        public final long endPositionMs;
        public final boolean relativeToDefaultPosition;
        public final boolean relativeToLiveWindow;
        public final long startPositionMs;
        public final boolean startsAtKeyFrame;

        private ClippingProperties(long startPositionMs, long endPositionMs, boolean relativeToLiveWindow, boolean relativeToDefaultPosition, boolean startsAtKeyFrame) {
            this.startPositionMs = startPositionMs;
            this.endPositionMs = endPositionMs;
            this.relativeToLiveWindow = relativeToLiveWindow;
            this.relativeToDefaultPosition = relativeToDefaultPosition;
            this.startsAtKeyFrame = startsAtKeyFrame;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof ClippingProperties)) {
                return false;
            }
            ClippingProperties clippingProperties = (ClippingProperties) obj;
            return this.startPositionMs == clippingProperties.startPositionMs && this.endPositionMs == clippingProperties.endPositionMs && this.relativeToLiveWindow == clippingProperties.relativeToLiveWindow && this.relativeToDefaultPosition == clippingProperties.relativeToDefaultPosition && this.startsAtKeyFrame == clippingProperties.startsAtKeyFrame;
        }

        public int hashCode() {
            long j = this.startPositionMs;
            int i = ((int) (j ^ (j >>> 32))) * 31;
            long j2 = this.endPositionMs;
            return ((((((i + ((int) ((j2 >>> 32) ^ j2))) * 31) + (this.relativeToLiveWindow ? 1 : 0)) * 31) + (this.relativeToDefaultPosition ? 1 : 0)) * 31) + (this.startsAtKeyFrame ? 1 : 0);
        }

        @Override // com.google.android.exoplayer2.Bundleable
        public Bundle toBundle() {
            Bundle bundle = new Bundle();
            bundle.putLong(keyForField(0), this.startPositionMs);
            bundle.putLong(keyForField(1), this.endPositionMs);
            bundle.putBoolean(keyForField(2), this.relativeToLiveWindow);
            bundle.putBoolean(keyForField(3), this.relativeToDefaultPosition);
            bundle.putBoolean(keyForField(4), this.startsAtKeyFrame);
            return bundle;
        }

        static /* synthetic */ ClippingProperties lambda$static$0(Bundle bundle) {
            return new ClippingProperties(bundle.getLong(keyForField(0), 0L), bundle.getLong(keyForField(1), Long.MIN_VALUE), bundle.getBoolean(keyForField(2), false), bundle.getBoolean(keyForField(3), false), bundle.getBoolean(keyForField(4), false));
        }

        private static String keyForField(int field) {
            return Integer.toString(field, 36);
        }
    }

    private MediaItem(String mediaId, ClippingProperties clippingProperties, PlaybackProperties playbackProperties, LiveConfiguration liveConfiguration, MediaMetadata mediaMetadata) {
        this.mediaId = mediaId;
        this.playbackProperties = playbackProperties;
        this.liveConfiguration = liveConfiguration;
        this.mediaMetadata = mediaMetadata;
        this.clippingProperties = clippingProperties;
    }

    public Builder buildUpon() {
        return new Builder();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MediaItem)) {
            return false;
        }
        MediaItem mediaItem = (MediaItem) obj;
        return Util.areEqual(this.mediaId, mediaItem.mediaId) && this.clippingProperties.equals(mediaItem.clippingProperties) && Util.areEqual(this.playbackProperties, mediaItem.playbackProperties) && Util.areEqual(this.liveConfiguration, mediaItem.liveConfiguration) && Util.areEqual(this.mediaMetadata, mediaItem.mediaMetadata);
    }

    public int hashCode() {
        int iHashCode = this.mediaId.hashCode() * 31;
        PlaybackProperties playbackProperties = this.playbackProperties;
        return ((((((iHashCode + (playbackProperties != null ? playbackProperties.hashCode() : 0)) * 31) + this.liveConfiguration.hashCode()) * 31) + this.clippingProperties.hashCode()) * 31) + this.mediaMetadata.hashCode();
    }

    @Override // com.google.android.exoplayer2.Bundleable
    public Bundle toBundle() {
        Bundle bundle = new Bundle();
        bundle.putString(keyForField(0), this.mediaId);
        bundle.putBundle(keyForField(1), this.liveConfiguration.toBundle());
        bundle.putBundle(keyForField(2), this.mediaMetadata.toBundle());
        bundle.putBundle(keyForField(3), this.clippingProperties.toBundle());
        return bundle;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static MediaItem fromBundle(Bundle bundle) {
        LiveConfiguration liveConfiguration;
        MediaMetadata mediaMetadata;
        ClippingProperties clippingProperties;
        String str = (String) Assertions.checkNotNull(bundle.getString(keyForField(0), ""));
        Bundle bundle2 = bundle.getBundle(keyForField(1));
        if (bundle2 == null) {
            liveConfiguration = LiveConfiguration.UNSET;
        } else {
            liveConfiguration = (LiveConfiguration) LiveConfiguration.CREATOR.fromBundle(bundle2);
        }
        LiveConfiguration liveConfiguration2 = liveConfiguration;
        Bundle bundle3 = bundle.getBundle(keyForField(2));
        if (bundle3 == null) {
            mediaMetadata = MediaMetadata.EMPTY;
        } else {
            mediaMetadata = (MediaMetadata) MediaMetadata.CREATOR.fromBundle(bundle3);
        }
        MediaMetadata mediaMetadata2 = mediaMetadata;
        Bundle bundle4 = bundle.getBundle(keyForField(3));
        if (bundle4 == null) {
            clippingProperties = new ClippingProperties(0L, Long.MIN_VALUE, false, false, false);
        } else {
            clippingProperties = (ClippingProperties) ClippingProperties.CREATOR.fromBundle(bundle4);
        }
        return new MediaItem(str, clippingProperties, null, liveConfiguration2, mediaMetadata2);
    }

    private static String keyForField(int field) {
        return Integer.toString(field, 36);
    }
}
