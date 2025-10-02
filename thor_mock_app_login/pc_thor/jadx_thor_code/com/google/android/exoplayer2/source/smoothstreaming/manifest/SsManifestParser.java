package com.google.android.exoplayer2.source.smoothstreaming.manifest;

import android.net.Uri;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Pair;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.drm.DrmInitData;
import com.google.android.exoplayer2.extractor.mp4.PsshAtomUtil;
import com.google.android.exoplayer2.extractor.mp4.TrackEncryptionBox;
import com.google.android.exoplayer2.source.smoothstreaming.manifest.SsManifest;
import com.google.android.exoplayer2.upstream.ParsingLoadable;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.CodecSpecificDataUtil;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

/* loaded from: classes.dex */
public class SsManifestParser implements ParsingLoadable.Parser<SsManifest> {
    private final XmlPullParserFactory xmlParserFactory;

    public SsManifestParser() {
        try {
            this.xmlParserFactory = XmlPullParserFactory.newInstance();
        } catch (XmlPullParserException e) {
            throw new RuntimeException("Couldn't create XmlPullParserFactory instance", e);
        }
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.google.android.exoplayer2.upstream.ParsingLoadable.Parser
    public SsManifest parse(Uri uri, InputStream inputStream) throws XmlPullParserException, IOException {
        try {
            XmlPullParser xmlPullParserNewPullParser = this.xmlParserFactory.newPullParser();
            xmlPullParserNewPullParser.setInput(inputStream, null);
            return (SsManifest) new SmoothStreamingMediaParser(null, uri.toString()).parse(xmlPullParserNewPullParser);
        } catch (XmlPullParserException e) {
            throw ParserException.createForMalformedManifest(null, e);
        }
    }

    public static class MissingFieldException extends ParserException {
        /* JADX WARN: Illegal instructions before constructor call */
        public MissingFieldException(String fieldName) {
            String strValueOf = String.valueOf(fieldName);
            super(strValueOf.length() != 0 ? "Missing required field: ".concat(strValueOf) : new String("Missing required field: "), null, true, 4);
        }
    }

    private static abstract class ElementParser {
        private final String baseUri;
        private final List<Pair<String, Object>> normalizedAttributes = new LinkedList();
        private final ElementParser parent;
        private final String tag;

        protected void addChild(Object parsedChild) {
        }

        protected abstract Object build();

        protected boolean handleChildInline(String tagName) {
            return false;
        }

        protected void parseEndTag(XmlPullParser xmlParser) {
        }

        protected void parseStartTag(XmlPullParser xmlParser) throws ParserException {
        }

        protected void parseText(XmlPullParser xmlParser) {
        }

        public ElementParser(ElementParser parent, String baseUri, String tag) {
            this.parent = parent;
            this.baseUri = baseUri;
            this.tag = tag;
        }

        public final Object parse(XmlPullParser xmlParser) throws XmlPullParserException, IOException {
            boolean z = false;
            int i = 0;
            while (true) {
                int eventType = xmlParser.getEventType();
                if (eventType == 1) {
                    return null;
                }
                if (eventType == 2) {
                    String name = xmlParser.getName();
                    if (this.tag.equals(name)) {
                        parseStartTag(xmlParser);
                        z = true;
                    } else if (z) {
                        if (i > 0) {
                            i++;
                        } else if (handleChildInline(name)) {
                            parseStartTag(xmlParser);
                        } else {
                            ElementParser elementParserNewChildParser = newChildParser(this, name, this.baseUri);
                            if (elementParserNewChildParser == null) {
                                i = 1;
                            } else {
                                addChild(elementParserNewChildParser.parse(xmlParser));
                            }
                        }
                    }
                } else if (eventType != 3) {
                    if (eventType == 4 && z && i == 0) {
                        parseText(xmlParser);
                    }
                } else if (!z) {
                    continue;
                } else if (i > 0) {
                    i--;
                } else {
                    String name2 = xmlParser.getName();
                    parseEndTag(xmlParser);
                    if (!handleChildInline(name2)) {
                        return build();
                    }
                }
                xmlParser.next();
            }
        }

        private ElementParser newChildParser(ElementParser parent, String name, String baseUri) {
            if (QualityLevelParser.TAG.equals(name)) {
                return new QualityLevelParser(parent, baseUri);
            }
            if (ProtectionParser.TAG.equals(name)) {
                return new ProtectionParser(parent, baseUri);
            }
            if (StreamIndexParser.TAG.equals(name)) {
                return new StreamIndexParser(parent, baseUri);
            }
            return null;
        }

        protected final void putNormalizedAttribute(String key, Object value) {
            this.normalizedAttributes.add(Pair.create(key, value));
        }

        protected final Object getNormalizedAttribute(String key) {
            for (int i = 0; i < this.normalizedAttributes.size(); i++) {
                Pair<String, Object> pair = this.normalizedAttributes.get(i);
                if (((String) pair.first).equals(key)) {
                    return pair.second;
                }
            }
            ElementParser elementParser = this.parent;
            if (elementParser == null) {
                return null;
            }
            return elementParser.getNormalizedAttribute(key);
        }

        protected final String parseRequiredString(XmlPullParser parser, String key) throws MissingFieldException {
            String attributeValue = parser.getAttributeValue(null, key);
            if (attributeValue != null) {
                return attributeValue;
            }
            throw new MissingFieldException(key);
        }

        protected final int parseInt(XmlPullParser parser, String key, int defaultValue) throws ParserException {
            String attributeValue = parser.getAttributeValue(null, key);
            if (attributeValue == null) {
                return defaultValue;
            }
            try {
                return Integer.parseInt(attributeValue);
            } catch (NumberFormatException e) {
                throw ParserException.createForMalformedManifest(null, e);
            }
        }

        protected final int parseRequiredInt(XmlPullParser parser, String key) throws ParserException {
            String attributeValue = parser.getAttributeValue(null, key);
            if (attributeValue != null) {
                try {
                    return Integer.parseInt(attributeValue);
                } catch (NumberFormatException e) {
                    throw ParserException.createForMalformedManifest(null, e);
                }
            }
            throw new MissingFieldException(key);
        }

        protected final long parseLong(XmlPullParser parser, String key, long defaultValue) throws ParserException {
            String attributeValue = parser.getAttributeValue(null, key);
            if (attributeValue == null) {
                return defaultValue;
            }
            try {
                return Long.parseLong(attributeValue);
            } catch (NumberFormatException e) {
                throw ParserException.createForMalformedManifest(null, e);
            }
        }

        protected final long parseRequiredLong(XmlPullParser parser, String key) throws ParserException {
            String attributeValue = parser.getAttributeValue(null, key);
            if (attributeValue != null) {
                try {
                    return Long.parseLong(attributeValue);
                } catch (NumberFormatException e) {
                    throw ParserException.createForMalformedManifest(null, e);
                }
            }
            throw new MissingFieldException(key);
        }

        protected final boolean parseBoolean(XmlPullParser parser, String key, boolean defaultValue) {
            String attributeValue = parser.getAttributeValue(null, key);
            return attributeValue != null ? Boolean.parseBoolean(attributeValue) : defaultValue;
        }
    }

    private static class SmoothStreamingMediaParser extends ElementParser {
        private static final String KEY_DURATION = "Duration";
        private static final String KEY_DVR_WINDOW_LENGTH = "DVRWindowLength";
        private static final String KEY_IS_LIVE = "IsLive";
        private static final String KEY_LOOKAHEAD_COUNT = "LookaheadCount";
        private static final String KEY_MAJOR_VERSION = "MajorVersion";
        private static final String KEY_MINOR_VERSION = "MinorVersion";
        private static final String KEY_TIME_SCALE = "TimeScale";
        public static final String TAG = "SmoothStreamingMedia";
        private long duration;
        private long dvrWindowLength;
        private boolean isLive;
        private int lookAheadCount;
        private int majorVersion;
        private int minorVersion;
        private SsManifest.ProtectionElement protectionElement;
        private final List<SsManifest.StreamElement> streamElements;
        private long timescale;

        public SmoothStreamingMediaParser(ElementParser parent, String baseUri) {
            super(parent, baseUri, TAG);
            this.lookAheadCount = -1;
            this.protectionElement = null;
            this.streamElements = new LinkedList();
        }

        @Override // com.google.android.exoplayer2.source.smoothstreaming.manifest.SsManifestParser.ElementParser
        public void parseStartTag(XmlPullParser parser) throws ParserException {
            this.majorVersion = parseRequiredInt(parser, KEY_MAJOR_VERSION);
            this.minorVersion = parseRequiredInt(parser, KEY_MINOR_VERSION);
            this.timescale = parseLong(parser, KEY_TIME_SCALE, 10000000L);
            this.duration = parseRequiredLong(parser, KEY_DURATION);
            this.dvrWindowLength = parseLong(parser, KEY_DVR_WINDOW_LENGTH, 0L);
            this.lookAheadCount = parseInt(parser, KEY_LOOKAHEAD_COUNT, -1);
            this.isLive = parseBoolean(parser, KEY_IS_LIVE, false);
            putNormalizedAttribute(KEY_TIME_SCALE, Long.valueOf(this.timescale));
        }

        @Override // com.google.android.exoplayer2.source.smoothstreaming.manifest.SsManifestParser.ElementParser
        public void addChild(Object child) {
            if (child instanceof SsManifest.StreamElement) {
                this.streamElements.add((SsManifest.StreamElement) child);
            } else if (child instanceof SsManifest.ProtectionElement) {
                Assertions.checkState(this.protectionElement == null);
                this.protectionElement = (SsManifest.ProtectionElement) child;
            }
        }

        @Override // com.google.android.exoplayer2.source.smoothstreaming.manifest.SsManifestParser.ElementParser
        public Object build() {
            int size = this.streamElements.size();
            SsManifest.StreamElement[] streamElementArr = new SsManifest.StreamElement[size];
            this.streamElements.toArray(streamElementArr);
            if (this.protectionElement != null) {
                DrmInitData drmInitData = new DrmInitData(new DrmInitData.SchemeData(this.protectionElement.uuid, MimeTypes.VIDEO_MP4, this.protectionElement.data));
                for (int i = 0; i < size; i++) {
                    SsManifest.StreamElement streamElement = streamElementArr[i];
                    int i2 = streamElement.type;
                    if (i2 == 2 || i2 == 1) {
                        Format[] formatArr = streamElement.formats;
                        for (int i3 = 0; i3 < formatArr.length; i3++) {
                            formatArr[i3] = formatArr[i3].buildUpon().setDrmInitData(drmInitData).build();
                        }
                    }
                }
            }
            return new SsManifest(this.majorVersion, this.minorVersion, this.timescale, this.duration, this.dvrWindowLength, this.lookAheadCount, this.isLive, this.protectionElement, streamElementArr);
        }
    }

    private static class ProtectionParser extends ElementParser {
        private static final int INITIALIZATION_VECTOR_SIZE = 8;
        public static final String KEY_SYSTEM_ID = "SystemID";
        public static final String TAG = "Protection";
        public static final String TAG_PROTECTION_HEADER = "ProtectionHeader";
        private boolean inProtectionHeader;
        private byte[] initData;
        private UUID uuid;

        public ProtectionParser(ElementParser parent, String baseUri) {
            super(parent, baseUri, TAG);
        }

        @Override // com.google.android.exoplayer2.source.smoothstreaming.manifest.SsManifestParser.ElementParser
        public boolean handleChildInline(String tag) {
            return TAG_PROTECTION_HEADER.equals(tag);
        }

        @Override // com.google.android.exoplayer2.source.smoothstreaming.manifest.SsManifestParser.ElementParser
        public void parseStartTag(XmlPullParser parser) {
            if (TAG_PROTECTION_HEADER.equals(parser.getName())) {
                this.inProtectionHeader = true;
                this.uuid = UUID.fromString(stripCurlyBraces(parser.getAttributeValue(null, KEY_SYSTEM_ID)));
            }
        }

        @Override // com.google.android.exoplayer2.source.smoothstreaming.manifest.SsManifestParser.ElementParser
        public void parseText(XmlPullParser parser) {
            if (this.inProtectionHeader) {
                this.initData = Base64.decode(parser.getText(), 0);
            }
        }

        @Override // com.google.android.exoplayer2.source.smoothstreaming.manifest.SsManifestParser.ElementParser
        public void parseEndTag(XmlPullParser parser) {
            if (TAG_PROTECTION_HEADER.equals(parser.getName())) {
                this.inProtectionHeader = false;
            }
        }

        @Override // com.google.android.exoplayer2.source.smoothstreaming.manifest.SsManifestParser.ElementParser
        public Object build() {
            UUID uuid = this.uuid;
            return new SsManifest.ProtectionElement(uuid, PsshAtomUtil.buildPsshAtom(uuid, this.initData), buildTrackEncryptionBoxes(this.initData));
        }

        private static TrackEncryptionBox[] buildTrackEncryptionBoxes(byte[] initData) {
            return new TrackEncryptionBox[]{new TrackEncryptionBox(true, null, 8, getProtectionElementKeyId(initData), 0, 0, null)};
        }

        private static byte[] getProtectionElementKeyId(byte[] initData) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < initData.length; i += 2) {
                sb.append((char) initData[i]);
            }
            String string = sb.toString();
            byte[] bArrDecode = Base64.decode(string.substring(string.indexOf("<KID>") + 5, string.indexOf("</KID>")), 0);
            swap(bArrDecode, 0, 3);
            swap(bArrDecode, 1, 2);
            swap(bArrDecode, 4, 5);
            swap(bArrDecode, 6, 7);
            return bArrDecode;
        }

        private static void swap(byte[] data, int firstPosition, int secondPosition) {
            byte b = data[firstPosition];
            data[firstPosition] = data[secondPosition];
            data[secondPosition] = b;
        }

        private static String stripCurlyBraces(String uuidString) {
            return (uuidString.charAt(0) == '{' && uuidString.charAt(uuidString.length() - 1) == '}') ? uuidString.substring(1, uuidString.length() - 1) : uuidString;
        }
    }

    private static class StreamIndexParser extends ElementParser {
        private static final String KEY_DISPLAY_HEIGHT = "DisplayHeight";
        private static final String KEY_DISPLAY_WIDTH = "DisplayWidth";
        private static final String KEY_FRAGMENT_DURATION = "d";
        private static final String KEY_FRAGMENT_REPEAT_COUNT = "r";
        private static final String KEY_FRAGMENT_START_TIME = "t";
        private static final String KEY_LANGUAGE = "Language";
        private static final String KEY_MAX_HEIGHT = "MaxHeight";
        private static final String KEY_MAX_WIDTH = "MaxWidth";
        private static final String KEY_NAME = "Name";
        private static final String KEY_SUB_TYPE = "Subtype";
        private static final String KEY_TIME_SCALE = "TimeScale";
        private static final String KEY_TYPE = "Type";
        private static final String KEY_TYPE_AUDIO = "audio";
        private static final String KEY_TYPE_TEXT = "text";
        private static final String KEY_TYPE_VIDEO = "video";
        private static final String KEY_URL = "Url";
        public static final String TAG = "StreamIndex";
        private static final String TAG_STREAM_FRAGMENT = "c";
        private final String baseUri;
        private int displayHeight;
        private int displayWidth;
        private final List<Format> formats;
        private String language;
        private long lastChunkDuration;
        private int maxHeight;
        private int maxWidth;
        private String name;
        private ArrayList<Long> startTimes;
        private String subType;
        private long timescale;
        private int type;
        private String url;

        public StreamIndexParser(ElementParser parent, String baseUri) {
            super(parent, baseUri, TAG);
            this.baseUri = baseUri;
            this.formats = new LinkedList();
        }

        @Override // com.google.android.exoplayer2.source.smoothstreaming.manifest.SsManifestParser.ElementParser
        public boolean handleChildInline(String tag) {
            return TAG_STREAM_FRAGMENT.equals(tag);
        }

        @Override // com.google.android.exoplayer2.source.smoothstreaming.manifest.SsManifestParser.ElementParser
        public void parseStartTag(XmlPullParser parser) throws ParserException {
            if (TAG_STREAM_FRAGMENT.equals(parser.getName())) {
                parseStreamFragmentStartTag(parser);
            } else {
                parseStreamElementStartTag(parser);
            }
        }

        private void parseStreamFragmentStartTag(XmlPullParser parser) throws ParserException {
            int size = this.startTimes.size();
            long jLongValue = parseLong(parser, KEY_FRAGMENT_START_TIME, C.TIME_UNSET);
            int i = 1;
            if (jLongValue == C.TIME_UNSET) {
                if (size == 0) {
                    jLongValue = 0;
                } else if (this.lastChunkDuration != -1) {
                    jLongValue = this.lastChunkDuration + this.startTimes.get(size - 1).longValue();
                } else {
                    throw ParserException.createForMalformedManifest("Unable to infer start time", null);
                }
            }
            this.startTimes.add(Long.valueOf(jLongValue));
            this.lastChunkDuration = parseLong(parser, KEY_FRAGMENT_DURATION, C.TIME_UNSET);
            long j = parseLong(parser, KEY_FRAGMENT_REPEAT_COUNT, 1L);
            if (j > 1 && this.lastChunkDuration == C.TIME_UNSET) {
                throw ParserException.createForMalformedManifest("Repeated chunk with unspecified duration", null);
            }
            while (true) {
                long j2 = i;
                if (j2 >= j) {
                    return;
                }
                this.startTimes.add(Long.valueOf((this.lastChunkDuration * j2) + jLongValue));
                i++;
            }
        }

        private void parseStreamElementStartTag(XmlPullParser parser) throws ParserException {
            int type = parseType(parser);
            this.type = type;
            putNormalizedAttribute(KEY_TYPE, Integer.valueOf(type));
            if (this.type == 3) {
                this.subType = parseRequiredString(parser, KEY_SUB_TYPE);
            } else {
                this.subType = parser.getAttributeValue(null, KEY_SUB_TYPE);
            }
            putNormalizedAttribute(KEY_SUB_TYPE, this.subType);
            String attributeValue = parser.getAttributeValue(null, KEY_NAME);
            this.name = attributeValue;
            putNormalizedAttribute(KEY_NAME, attributeValue);
            this.url = parseRequiredString(parser, KEY_URL);
            this.maxWidth = parseInt(parser, KEY_MAX_WIDTH, -1);
            this.maxHeight = parseInt(parser, KEY_MAX_HEIGHT, -1);
            this.displayWidth = parseInt(parser, KEY_DISPLAY_WIDTH, -1);
            this.displayHeight = parseInt(parser, KEY_DISPLAY_HEIGHT, -1);
            String attributeValue2 = parser.getAttributeValue(null, KEY_LANGUAGE);
            this.language = attributeValue2;
            putNormalizedAttribute(KEY_LANGUAGE, attributeValue2);
            long j = parseInt(parser, KEY_TIME_SCALE, -1);
            this.timescale = j;
            if (j == -1) {
                this.timescale = ((Long) getNormalizedAttribute(KEY_TIME_SCALE)).longValue();
            }
            this.startTimes = new ArrayList<>();
        }

        private int parseType(XmlPullParser parser) throws ParserException {
            String attributeValue = parser.getAttributeValue(null, KEY_TYPE);
            if (attributeValue != null) {
                if ("audio".equalsIgnoreCase(attributeValue)) {
                    return 1;
                }
                if ("video".equalsIgnoreCase(attributeValue)) {
                    return 2;
                }
                if ("text".equalsIgnoreCase(attributeValue)) {
                    return 3;
                }
                throw ParserException.createForMalformedManifest(new StringBuilder(String.valueOf(attributeValue).length() + 19).append("Invalid key value[").append(attributeValue).append("]").toString(), null);
            }
            throw new MissingFieldException(KEY_TYPE);
        }

        @Override // com.google.android.exoplayer2.source.smoothstreaming.manifest.SsManifestParser.ElementParser
        public void addChild(Object child) {
            if (child instanceof Format) {
                this.formats.add((Format) child);
            }
        }

        @Override // com.google.android.exoplayer2.source.smoothstreaming.manifest.SsManifestParser.ElementParser
        public Object build() {
            Format[] formatArr = new Format[this.formats.size()];
            this.formats.toArray(formatArr);
            return new SsManifest.StreamElement(this.baseUri, this.url, this.type, this.subType, this.timescale, this.name, this.maxWidth, this.maxHeight, this.displayWidth, this.displayHeight, this.language, formatArr, this.startTimes, this.lastChunkDuration);
        }
    }

    private static class QualityLevelParser extends ElementParser {
        private static final String KEY_BITRATE = "Bitrate";
        private static final String KEY_CHANNELS = "Channels";
        private static final String KEY_CODEC_PRIVATE_DATA = "CodecPrivateData";
        private static final String KEY_FOUR_CC = "FourCC";
        private static final String KEY_INDEX = "Index";
        private static final String KEY_LANGUAGE = "Language";
        private static final String KEY_MAX_HEIGHT = "MaxHeight";
        private static final String KEY_MAX_WIDTH = "MaxWidth";
        private static final String KEY_NAME = "Name";
        private static final String KEY_SAMPLING_RATE = "SamplingRate";
        private static final String KEY_SUB_TYPE = "Subtype";
        private static final String KEY_TYPE = "Type";
        public static final String TAG = "QualityLevel";
        private Format format;

        public QualityLevelParser(ElementParser parent, String baseUri) {
            super(parent, baseUri, TAG);
        }

        /* JADX WARN: Removed duplicated region for block: B:27:0x00b4  */
        @Override // com.google.android.exoplayer2.source.smoothstreaming.manifest.SsManifestParser.ElementParser
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public void parseStartTag(org.xmlpull.v1.XmlPullParser r9) throws com.google.android.exoplayer2.ParserException {
            /*
                r8 = this;
                com.google.android.exoplayer2.Format$Builder r0 = new com.google.android.exoplayer2.Format$Builder
                r0.<init>()
                java.lang.String r1 = "FourCC"
                java.lang.String r1 = r8.parseRequiredString(r9, r1)
                java.lang.String r1 = fourCCToMimeType(r1)
                java.lang.String r2 = "Type"
                java.lang.Object r2 = r8.getNormalizedAttribute(r2)
                java.lang.Integer r2 = (java.lang.Integer) r2
                int r2 = r2.intValue()
                r3 = 2
                java.lang.String r4 = "CodecPrivateData"
                r5 = 0
                if (r2 != r3) goto L49
                java.lang.String r2 = r9.getAttributeValue(r5, r4)
                java.util.List r2 = buildCodecSpecificData(r2)
                java.lang.String r3 = "video/mp4"
                com.google.android.exoplayer2.Format$Builder r3 = r0.setContainerMimeType(r3)
                java.lang.String r4 = "MaxWidth"
                int r4 = r8.parseRequiredInt(r9, r4)
                com.google.android.exoplayer2.Format$Builder r3 = r3.setWidth(r4)
                java.lang.String r4 = "MaxHeight"
                int r4 = r8.parseRequiredInt(r9, r4)
                com.google.android.exoplayer2.Format$Builder r3 = r3.setHeight(r4)
                r3.setInitializationData(r2)
                goto Lc0
            L49:
                r3 = 1
                if (r2 != r3) goto L8b
                java.lang.String r2 = "audio/mp4a-latm"
                if (r1 != 0) goto L51
                r1 = r2
            L51:
                java.lang.String r3 = "Channels"
                int r3 = r8.parseRequiredInt(r9, r3)
                java.lang.String r6 = "SamplingRate"
                int r6 = r8.parseRequiredInt(r9, r6)
                java.lang.String r4 = r9.getAttributeValue(r5, r4)
                java.util.List r4 = buildCodecSpecificData(r4)
                boolean r7 = r4.isEmpty()
                if (r7 == 0) goto L79
                boolean r2 = r2.equals(r1)
                if (r2 == 0) goto L79
                byte[] r2 = com.google.android.exoplayer2.audio.AacUtil.buildAacLcAudioSpecificConfig(r6, r3)
                java.util.List r4 = java.util.Collections.singletonList(r2)
            L79:
                java.lang.String r2 = "audio/mp4"
                com.google.android.exoplayer2.Format$Builder r2 = r0.setContainerMimeType(r2)
                com.google.android.exoplayer2.Format$Builder r2 = r2.setChannelCount(r3)
                com.google.android.exoplayer2.Format$Builder r2 = r2.setSampleRate(r6)
                r2.setInitializationData(r4)
                goto Lc0
            L8b:
                r3 = 3
                java.lang.String r4 = "application/mp4"
                if (r2 != r3) goto Lbd
                java.lang.String r2 = "Subtype"
                java.lang.Object r2 = r8.getNormalizedAttribute(r2)
                java.lang.String r2 = (java.lang.String) r2
                if (r2 == 0) goto Lb4
                r2.hashCode()
                java.lang.String r3 = "CAPT"
                boolean r3 = r2.equals(r3)
                if (r3 != 0) goto Lb1
                java.lang.String r3 = "DESC"
                boolean r2 = r2.equals(r3)
                if (r2 != 0) goto Lae
                goto Lb4
            Lae:
                r2 = 1024(0x400, float:1.435E-42)
                goto Lb5
            Lb1:
                r2 = 64
                goto Lb5
            Lb4:
                r2 = 0
            Lb5:
                com.google.android.exoplayer2.Format$Builder r3 = r0.setContainerMimeType(r4)
                r3.setRoleFlags(r2)
                goto Lc0
            Lbd:
                r0.setContainerMimeType(r4)
            Lc0:
                java.lang.String r2 = "Index"
                java.lang.String r2 = r9.getAttributeValue(r5, r2)
                com.google.android.exoplayer2.Format$Builder r0 = r0.setId(r2)
                java.lang.String r2 = "Name"
                java.lang.Object r2 = r8.getNormalizedAttribute(r2)
                java.lang.String r2 = (java.lang.String) r2
                com.google.android.exoplayer2.Format$Builder r0 = r0.setLabel(r2)
                com.google.android.exoplayer2.Format$Builder r0 = r0.setSampleMimeType(r1)
                java.lang.String r1 = "Bitrate"
                int r9 = r8.parseRequiredInt(r9, r1)
                com.google.android.exoplayer2.Format$Builder r9 = r0.setAverageBitrate(r9)
                java.lang.String r0 = "Language"
                java.lang.Object r0 = r8.getNormalizedAttribute(r0)
                java.lang.String r0 = (java.lang.String) r0
                com.google.android.exoplayer2.Format$Builder r9 = r9.setLanguage(r0)
                com.google.android.exoplayer2.Format r9 = r9.build()
                r8.format = r9
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.source.smoothstreaming.manifest.SsManifestParser.QualityLevelParser.parseStartTag(org.xmlpull.v1.XmlPullParser):void");
        }

        @Override // com.google.android.exoplayer2.source.smoothstreaming.manifest.SsManifestParser.ElementParser
        public Object build() {
            return this.format;
        }

        private static List<byte[]> buildCodecSpecificData(String codecSpecificDataString) {
            ArrayList arrayList = new ArrayList();
            if (!TextUtils.isEmpty(codecSpecificDataString)) {
                byte[] bytesFromHexString = Util.getBytesFromHexString(codecSpecificDataString);
                byte[][] bArrSplitNalUnits = CodecSpecificDataUtil.splitNalUnits(bytesFromHexString);
                if (bArrSplitNalUnits == null) {
                    arrayList.add(bytesFromHexString);
                } else {
                    Collections.addAll(arrayList, bArrSplitNalUnits);
                }
            }
            return arrayList;
        }

        private static String fourCCToMimeType(String fourCC) {
            if (fourCC.equalsIgnoreCase("H264") || fourCC.equalsIgnoreCase("X264") || fourCC.equalsIgnoreCase("AVC1") || fourCC.equalsIgnoreCase("DAVC")) {
                return MimeTypes.VIDEO_H264;
            }
            if (fourCC.equalsIgnoreCase("AAC") || fourCC.equalsIgnoreCase("AACL") || fourCC.equalsIgnoreCase("AACH") || fourCC.equalsIgnoreCase("AACP")) {
                return MimeTypes.AUDIO_AAC;
            }
            if (fourCC.equalsIgnoreCase("TTML") || fourCC.equalsIgnoreCase("DFXP")) {
                return MimeTypes.APPLICATION_TTML;
            }
            if (fourCC.equalsIgnoreCase("ac-3") || fourCC.equalsIgnoreCase("dac3")) {
                return MimeTypes.AUDIO_AC3;
            }
            if (fourCC.equalsIgnoreCase("ec-3") || fourCC.equalsIgnoreCase("dec3")) {
                return MimeTypes.AUDIO_E_AC3;
            }
            if (fourCC.equalsIgnoreCase("dtsc")) {
                return MimeTypes.AUDIO_DTS;
            }
            if (fourCC.equalsIgnoreCase("dtsh") || fourCC.equalsIgnoreCase("dtsl")) {
                return MimeTypes.AUDIO_DTS_HD;
            }
            if (fourCC.equalsIgnoreCase("dtse")) {
                return MimeTypes.AUDIO_DTS_EXPRESS;
            }
            if (fourCC.equalsIgnoreCase("opus")) {
                return MimeTypes.AUDIO_OPUS;
            }
            return null;
        }
    }
}
