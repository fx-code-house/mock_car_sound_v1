package com.google.android.exoplayer2.source.dash.manifest;

import android.net.Uri;
import android.text.TextUtils;
import android.util.Pair;
import android.util.Xml;
import androidx.exifinterface.media.ExifInterface;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.audio.Ac3Util;
import com.google.android.exoplayer2.drm.DrmInitData;
import com.google.android.exoplayer2.metadata.emsg.EventMessage;
import com.google.android.exoplayer2.source.dash.manifest.SegmentBase;
import com.google.android.exoplayer2.source.rtsp.SessionDescription;
import com.google.android.exoplayer2.text.ttml.TtmlNode;
import com.google.android.exoplayer2.upstream.ParsingLoadable;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.UriUtil;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.util.XmlPullParserUtil;
import com.google.common.base.Ascii;
import com.google.common.base.Charsets;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.xml.sax.helpers.DefaultHandler;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

/* loaded from: classes.dex */
public class DashManifestParser extends DefaultHandler implements ParsingLoadable.Parser<DashManifest> {
    private static final String TAG = "MpdParser";
    private final XmlPullParserFactory xmlParserFactory;
    private static final Pattern FRAME_RATE_PATTERN = Pattern.compile("(\\d+)(?:/(\\d+))?");
    private static final Pattern CEA_608_ACCESSIBILITY_PATTERN = Pattern.compile("CC([1-4])=.*");
    private static final Pattern CEA_708_ACCESSIBILITY_PATTERN = Pattern.compile("([1-9]|[1-5][0-9]|6[0-3])=.*");
    private static final int[] MPEG_CHANNEL_CONFIGURATION_MAPPING = {-1, 1, 2, 3, 4, 5, 6, 8, 2, 3, 4, 7, 8, 24, 8, 12, 10, 12, 14, 12, 14};

    private static long getFinalAvailabilityTimeOffset(long baseUrlAvailabilityTimeOffsetUs, long segmentBaseAvailabilityTimeOffsetUs) {
        if (segmentBaseAvailabilityTimeOffsetUs != C.TIME_UNSET) {
            baseUrlAvailabilityTimeOffsetUs = segmentBaseAvailabilityTimeOffsetUs;
        }
        return baseUrlAvailabilityTimeOffsetUs == Long.MAX_VALUE ? C.TIME_UNSET : baseUrlAvailabilityTimeOffsetUs;
    }

    public DashManifestParser() {
        try {
            this.xmlParserFactory = XmlPullParserFactory.newInstance();
        } catch (XmlPullParserException e) {
            throw new RuntimeException("Couldn't create XmlPullParserFactory instance", e);
        }
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.google.android.exoplayer2.upstream.ParsingLoadable.Parser
    public DashManifest parse(Uri uri, InputStream inputStream) throws XmlPullParserException, IOException {
        try {
            XmlPullParser xmlPullParserNewPullParser = this.xmlParserFactory.newPullParser();
            xmlPullParserNewPullParser.setInput(inputStream, null);
            if (xmlPullParserNewPullParser.next() != 2 || !"MPD".equals(xmlPullParserNewPullParser.getName())) {
                throw ParserException.createForMalformedManifest("inputStream does not contain a valid media presentation description", null);
            }
            return parseMediaPresentationDescription(xmlPullParserNewPullParser, new BaseUrl(uri.toString()));
        } catch (XmlPullParserException e) {
            throw ParserException.createForMalformedManifest(null, e);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:76:0x01bf A[LOOP:0: B:20:0x0086->B:76:0x01bf, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:78:0x017b A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    protected com.google.android.exoplayer2.source.dash.manifest.DashManifest parseMediaPresentationDescription(org.xmlpull.v1.XmlPullParser r44, com.google.android.exoplayer2.source.dash.manifest.BaseUrl r45) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException, java.lang.NumberFormatException {
        /*
            Method dump skipped, instructions count: 459
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.source.dash.manifest.DashManifestParser.parseMediaPresentationDescription(org.xmlpull.v1.XmlPullParser, com.google.android.exoplayer2.source.dash.manifest.BaseUrl):com.google.android.exoplayer2.source.dash.manifest.DashManifest");
    }

    protected DashManifest buildMediaPresentationDescription(long availabilityStartTime, long durationMs, long minBufferTimeMs, boolean dynamic, long minUpdateTimeMs, long timeShiftBufferDepthMs, long suggestedPresentationDelayMs, long publishTimeMs, ProgramInformation programInformation, UtcTimingElement utcTiming, ServiceDescriptionElement serviceDescription, Uri location, List<Period> periods) {
        return new DashManifest(availabilityStartTime, durationMs, minBufferTimeMs, dynamic, minUpdateTimeMs, timeShiftBufferDepthMs, suggestedPresentationDelayMs, publishTimeMs, programInformation, utcTiming, serviceDescription, location, periods);
    }

    protected UtcTimingElement parseUtcTiming(XmlPullParser xpp) {
        return buildUtcTimingElement(xpp.getAttributeValue(null, "schemeIdUri"), xpp.getAttributeValue(null, "value"));
    }

    protected UtcTimingElement buildUtcTimingElement(String schemeIdUri, String value) {
        return new UtcTimingElement(schemeIdUri, value);
    }

    protected ServiceDescriptionElement parseServiceDescription(XmlPullParser xpp) throws XmlPullParserException, IOException {
        long j = -9223372036854775807L;
        long j2 = -9223372036854775807L;
        long j3 = -9223372036854775807L;
        float f = -3.4028235E38f;
        float f2 = -3.4028235E38f;
        while (true) {
            xpp.next();
            if (XmlPullParserUtil.isStartTag(xpp, "Latency")) {
                j = parseLong(xpp, "target", C.TIME_UNSET);
                j2 = parseLong(xpp, "min", C.TIME_UNSET);
                j3 = parseLong(xpp, "max", C.TIME_UNSET);
            } else if (XmlPullParserUtil.isStartTag(xpp, "PlaybackRate")) {
                f = parseFloat(xpp, "min", -3.4028235E38f);
                f2 = parseFloat(xpp, "max", -3.4028235E38f);
            }
            long j4 = j;
            long j5 = j2;
            long j6 = j3;
            float f3 = f;
            float f4 = f2;
            if (XmlPullParserUtil.isEndTag(xpp, "ServiceDescription")) {
                return new ServiceDescriptionElement(j4, j5, j6, f3, f4);
            }
            j = j4;
            j2 = j5;
            j3 = j6;
            f = f3;
            f2 = f4;
        }
    }

    protected Pair<Period, Long> parsePeriod(XmlPullParser xpp, List<BaseUrl> parentBaseUrls, long defaultStartMs, long baseUrlAvailabilityTimeOffsetUs, long availabilityStartTimeMs, long timeShiftBufferDepthMs) throws XmlPullParserException, IOException, NumberFormatException {
        long j;
        ArrayList arrayList;
        ArrayList arrayList2;
        ArrayList arrayList3;
        Object obj;
        long j2;
        SegmentBase segmentTemplate;
        DashManifestParser dashManifestParser = this;
        Object obj2 = null;
        String attributeValue = xpp.getAttributeValue(null, TtmlNode.ATTR_ID);
        long duration = parseDuration(xpp, TtmlNode.START, defaultStartMs);
        long j3 = C.TIME_UNSET;
        long j4 = availabilityStartTimeMs != C.TIME_UNSET ? availabilityStartTimeMs + duration : -9223372036854775807L;
        long duration2 = parseDuration(xpp, "duration", C.TIME_UNSET);
        ArrayList arrayList4 = new ArrayList();
        ArrayList arrayList5 = new ArrayList();
        ArrayList arrayList6 = new ArrayList();
        long availabilityTimeOffsetUs = baseUrlAvailabilityTimeOffsetUs;
        boolean z = false;
        long j5 = -9223372036854775807L;
        SegmentBase segmentBase = null;
        Descriptor descriptor = null;
        while (true) {
            xpp.next();
            if (XmlPullParserUtil.isStartTag(xpp, "BaseURL")) {
                if (!z) {
                    availabilityTimeOffsetUs = dashManifestParser.parseAvailabilityTimeOffsetUs(xpp, availabilityTimeOffsetUs);
                    z = true;
                }
                arrayList6.addAll(parseBaseUrl(xpp, parentBaseUrls));
                arrayList3 = arrayList5;
                arrayList = arrayList6;
                j2 = j3;
                obj = obj2;
                arrayList2 = arrayList4;
            } else {
                if (XmlPullParserUtil.isStartTag(xpp, "AdaptationSet")) {
                    j = availabilityTimeOffsetUs;
                    arrayList = arrayList6;
                    arrayList2 = arrayList4;
                    arrayList2.add(parseAdaptationSet(xpp, !arrayList6.isEmpty() ? arrayList6 : parentBaseUrls, segmentBase, duration2, availabilityTimeOffsetUs, j5, j4, timeShiftBufferDepthMs));
                    arrayList3 = arrayList5;
                } else {
                    j = availabilityTimeOffsetUs;
                    ArrayList arrayList7 = arrayList5;
                    arrayList = arrayList6;
                    arrayList2 = arrayList4;
                    if (XmlPullParserUtil.isStartTag(xpp, "EventStream")) {
                        arrayList7.add(parseEventStream(xpp));
                        arrayList3 = arrayList7;
                    } else if (XmlPullParserUtil.isStartTag(xpp, "SegmentBase")) {
                        arrayList3 = arrayList7;
                        segmentBase = parseSegmentBase(xpp, null);
                        obj = null;
                        availabilityTimeOffsetUs = j;
                        j2 = C.TIME_UNSET;
                    } else {
                        arrayList3 = arrayList7;
                        if (XmlPullParserUtil.isStartTag(xpp, "SegmentList")) {
                            long availabilityTimeOffsetUs2 = parseAvailabilityTimeOffsetUs(xpp, C.TIME_UNSET);
                            obj = null;
                            segmentTemplate = parseSegmentList(xpp, null, j4, duration2, j, availabilityTimeOffsetUs2, timeShiftBufferDepthMs);
                            j5 = availabilityTimeOffsetUs2;
                            availabilityTimeOffsetUs = j;
                            j2 = C.TIME_UNSET;
                        } else {
                            obj = null;
                            if (XmlPullParserUtil.isStartTag(xpp, "SegmentTemplate")) {
                                long availabilityTimeOffsetUs3 = parseAvailabilityTimeOffsetUs(xpp, C.TIME_UNSET);
                                j2 = -9223372036854775807L;
                                segmentTemplate = parseSegmentTemplate(xpp, null, ImmutableList.of(), j4, duration2, j, availabilityTimeOffsetUs3, timeShiftBufferDepthMs);
                                j5 = availabilityTimeOffsetUs3;
                                availabilityTimeOffsetUs = j;
                            } else {
                                j2 = C.TIME_UNSET;
                                if (XmlPullParserUtil.isStartTag(xpp, "AssetIdentifier")) {
                                    descriptor = parseDescriptor(xpp, "AssetIdentifier");
                                } else {
                                    maybeSkipTag(xpp);
                                }
                                availabilityTimeOffsetUs = j;
                            }
                        }
                        segmentBase = segmentTemplate;
                    }
                }
                obj = null;
                j2 = C.TIME_UNSET;
                availabilityTimeOffsetUs = j;
            }
            if (XmlPullParserUtil.isEndTag(xpp, "Period")) {
                return Pair.create(buildPeriod(attributeValue, duration, arrayList2, arrayList3, descriptor), Long.valueOf(duration2));
            }
            arrayList4 = arrayList2;
            arrayList6 = arrayList;
            obj2 = obj;
            arrayList5 = arrayList3;
            j3 = j2;
            dashManifestParser = this;
        }
    }

    protected Period buildPeriod(String id, long startMs, List<AdaptationSet> adaptationSets, List<EventStream> eventStreams, Descriptor assetIdentifier) {
        return new Period(id, startMs, adaptationSets, eventStreams, assetIdentifier);
    }

    /* JADX WARN: Removed duplicated region for block: B:74:0x031f A[LOOP:0: B:3:0x007d->B:74:0x031f, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:75:0x02df A[EDGE_INSN: B:75:0x02df->B:68:0x02df BREAK  A[LOOP:0: B:3:0x007d->B:74:0x031f], SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    protected com.google.android.exoplayer2.source.dash.manifest.AdaptationSet parseAdaptationSet(org.xmlpull.v1.XmlPullParser r54, java.util.List<com.google.android.exoplayer2.source.dash.manifest.BaseUrl> r55, com.google.android.exoplayer2.source.dash.manifest.SegmentBase r56, long r57, long r59, long r61, long r63, long r65) throws org.xmlpull.v1.XmlPullParserException, java.lang.NumberFormatException, java.io.IOException {
        /*
            Method dump skipped, instructions count: 825
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.source.dash.manifest.DashManifestParser.parseAdaptationSet(org.xmlpull.v1.XmlPullParser, java.util.List, com.google.android.exoplayer2.source.dash.manifest.SegmentBase, long, long, long, long, long):com.google.android.exoplayer2.source.dash.manifest.AdaptationSet");
    }

    protected AdaptationSet buildAdaptationSet(int id, int contentType, List<Representation> representations, List<Descriptor> accessibilityDescriptors, List<Descriptor> essentialProperties, List<Descriptor> supplementalProperties) {
        return new AdaptationSet(id, contentType, representations, accessibilityDescriptors, essentialProperties, supplementalProperties);
    }

    protected int parseContentType(XmlPullParser xpp) {
        String attributeValue = xpp.getAttributeValue(null, "contentType");
        if (TextUtils.isEmpty(attributeValue)) {
            return -1;
        }
        if ("audio".equals(attributeValue)) {
            return 1;
        }
        if ("video".equals(attributeValue)) {
            return 2;
        }
        return "text".equals(attributeValue) ? 3 : -1;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:34:0x008a  */
    /* JADX WARN: Type inference failed for: r3v0 */
    /* JADX WARN: Type inference failed for: r3v1 */
    /* JADX WARN: Type inference failed for: r3v10, types: [byte[]] */
    /* JADX WARN: Type inference failed for: r3v12 */
    /* JADX WARN: Type inference failed for: r3v15 */
    /* JADX WARN: Type inference failed for: r3v18, types: [byte[]] */
    /* JADX WARN: Type inference failed for: r3v2 */
    /* JADX WARN: Type inference failed for: r3v3 */
    /* JADX WARN: Type inference failed for: r3v5, types: [byte[]] */
    /* JADX WARN: Type inference failed for: r3v7, types: [byte[]] */
    /* JADX WARN: Type inference failed for: r3v9 */
    /* JADX WARN: Type inference failed for: r4v1 */
    /* JADX WARN: Type inference failed for: r4v2, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r4v20 */
    /* JADX WARN: Type inference failed for: r4v21 */
    /* JADX WARN: Type inference failed for: r4v22 */
    /* JADX WARN: Type inference failed for: r4v23 */
    /* JADX WARN: Type inference failed for: r4v24 */
    /* JADX WARN: Type inference failed for: r4v25 */
    /* JADX WARN: Type inference failed for: r4v26 */
    /* JADX WARN: Type inference failed for: r4v27 */
    /* JADX WARN: Type inference failed for: r4v4, types: [java.util.UUID] */
    /* JADX WARN: Type inference failed for: r7v0, types: [java.util.UUID] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    protected android.util.Pair<java.lang.String, com.google.android.exoplayer2.drm.DrmInitData.SchemeData> parseContentProtection(org.xmlpull.v1.XmlPullParser r9) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        /*
            Method dump skipped, instructions count: 290
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.source.dash.manifest.DashManifestParser.parseContentProtection(org.xmlpull.v1.XmlPullParser):android.util.Pair");
    }

    protected void parseAdaptationSetChild(XmlPullParser xpp) throws XmlPullParserException, IOException {
        maybeSkipTag(xpp);
    }

    /* JADX WARN: Removed duplicated region for block: B:57:0x01e7 A[LOOP:0: B:3:0x006b->B:57:0x01e7, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:58:0x0195 A[EDGE_INSN: B:58:0x0195->B:47:0x0195 BREAK  A[LOOP:0: B:3:0x006b->B:57:0x01e7], SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    protected com.google.android.exoplayer2.source.dash.manifest.DashManifestParser.RepresentationInfo parseRepresentation(org.xmlpull.v1.XmlPullParser r36, java.util.List<com.google.android.exoplayer2.source.dash.manifest.BaseUrl> r37, java.lang.String r38, java.lang.String r39, int r40, int r41, float r42, int r43, int r44, java.lang.String r45, java.util.List<com.google.android.exoplayer2.source.dash.manifest.Descriptor> r46, java.util.List<com.google.android.exoplayer2.source.dash.manifest.Descriptor> r47, java.util.List<com.google.android.exoplayer2.source.dash.manifest.Descriptor> r48, java.util.List<com.google.android.exoplayer2.source.dash.manifest.Descriptor> r49, com.google.android.exoplayer2.source.dash.manifest.SegmentBase r50, long r51, long r53, long r55, long r57, long r59) throws org.xmlpull.v1.XmlPullParserException, java.lang.NumberFormatException, java.io.IOException {
        /*
            Method dump skipped, instructions count: 502
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.source.dash.manifest.DashManifestParser.parseRepresentation(org.xmlpull.v1.XmlPullParser, java.util.List, java.lang.String, java.lang.String, int, int, float, int, int, java.lang.String, java.util.List, java.util.List, java.util.List, java.util.List, com.google.android.exoplayer2.source.dash.manifest.SegmentBase, long, long, long, long, long):com.google.android.exoplayer2.source.dash.manifest.DashManifestParser$RepresentationInfo");
    }

    protected Format buildFormat(String id, String containerMimeType, int width, int height, float frameRate, int audioChannels, int audioSamplingRate, int bitrate, String language, List<Descriptor> roleDescriptors, List<Descriptor> accessibilityDescriptors, String codecs, List<Descriptor> essentialProperties, List<Descriptor> supplementalProperties) {
        int cea708AccessibilityChannel;
        String str = codecs;
        String sampleMimeType = getSampleMimeType(containerMimeType, str);
        if (MimeTypes.AUDIO_E_AC3.equals(sampleMimeType)) {
            sampleMimeType = parseEac3SupplementalProperties(supplementalProperties);
            if (MimeTypes.AUDIO_E_AC3_JOC.equals(sampleMimeType)) {
                str = Ac3Util.E_AC3_JOC_CODEC_STRING;
            }
        }
        Format.Builder language2 = new Format.Builder().setId(id).setContainerMimeType(containerMimeType).setSampleMimeType(sampleMimeType).setCodecs(str).setPeakBitrate(bitrate).setSelectionFlags(parseSelectionFlagsFromRoleDescriptors(roleDescriptors)).setRoleFlags(parseRoleFlagsFromRoleDescriptors(roleDescriptors) | parseRoleFlagsFromAccessibilityDescriptors(accessibilityDescriptors) | parseRoleFlagsFromProperties(essentialProperties) | parseRoleFlagsFromProperties(supplementalProperties)).setLanguage(language);
        if (MimeTypes.isVideo(sampleMimeType)) {
            language2.setWidth(width).setHeight(height).setFrameRate(frameRate);
        } else if (MimeTypes.isAudio(sampleMimeType)) {
            language2.setChannelCount(audioChannels).setSampleRate(audioSamplingRate);
        } else if (MimeTypes.isText(sampleMimeType)) {
            if (MimeTypes.APPLICATION_CEA608.equals(sampleMimeType)) {
                cea708AccessibilityChannel = parseCea608AccessibilityChannel(accessibilityDescriptors);
            } else {
                cea708AccessibilityChannel = MimeTypes.APPLICATION_CEA708.equals(sampleMimeType) ? parseCea708AccessibilityChannel(accessibilityDescriptors) : -1;
            }
            language2.setAccessibilityChannel(cea708AccessibilityChannel);
        }
        return language2.build();
    }

    protected Representation buildRepresentation(RepresentationInfo representationInfo, String label, String extraDrmSchemeType, ArrayList<DrmInitData.SchemeData> extraDrmSchemeDatas, ArrayList<Descriptor> extraInbandEventStreams) {
        Format.Builder builderBuildUpon = representationInfo.format.buildUpon();
        if (label != null) {
            builderBuildUpon.setLabel(label);
        }
        String str = representationInfo.drmSchemeType;
        if (str != null) {
            extraDrmSchemeType = str;
        }
        ArrayList<DrmInitData.SchemeData> arrayList = representationInfo.drmSchemeDatas;
        arrayList.addAll(extraDrmSchemeDatas);
        if (!arrayList.isEmpty()) {
            filterRedundantIncompleteSchemeDatas(arrayList);
            builderBuildUpon.setDrmInitData(new DrmInitData(extraDrmSchemeType, arrayList));
        }
        ArrayList<Descriptor> arrayList2 = representationInfo.inbandEventStreams;
        arrayList2.addAll(extraInbandEventStreams);
        return Representation.newInstance(representationInfo.revisionId, builderBuildUpon.build(), representationInfo.baseUrls, representationInfo.segmentBase, arrayList2);
    }

    protected SegmentBase.SingleSegmentBase parseSegmentBase(XmlPullParser xpp, SegmentBase.SingleSegmentBase parent) throws XmlPullParserException, NumberFormatException, IOException {
        long j;
        long j2;
        long j3 = parseLong(xpp, "timescale", parent != null ? parent.timescale : 1L);
        long j4 = parseLong(xpp, "presentationTimeOffset", parent != null ? parent.presentationTimeOffset : 0L);
        long j5 = parent != null ? parent.indexStart : 0L;
        long j6 = parent != null ? parent.indexLength : 0L;
        String attributeValue = xpp.getAttributeValue(null, "indexRange");
        if (attributeValue != null) {
            String[] strArrSplit = attributeValue.split("-");
            j2 = Long.parseLong(strArrSplit[0]);
            j = (Long.parseLong(strArrSplit[1]) - j2) + 1;
        } else {
            j = j6;
            j2 = j5;
        }
        RangedUri initialization = parent != null ? parent.initialization : null;
        do {
            xpp.next();
            if (XmlPullParserUtil.isStartTag(xpp, "Initialization")) {
                initialization = parseInitialization(xpp);
            } else {
                maybeSkipTag(xpp);
            }
        } while (!XmlPullParserUtil.isEndTag(xpp, "SegmentBase"));
        return buildSingleSegmentBase(initialization, j3, j4, j2, j);
    }

    protected SegmentBase.SingleSegmentBase buildSingleSegmentBase(RangedUri initialization, long timescale, long presentationTimeOffset, long indexStart, long indexLength) {
        return new SegmentBase.SingleSegmentBase(initialization, timescale, presentationTimeOffset, indexStart, indexLength);
    }

    protected SegmentBase.SegmentList parseSegmentList(XmlPullParser xpp, SegmentBase.SegmentList parent, long periodStartUnixTimeMs, long periodDurationMs, long baseUrlAvailabilityTimeOffsetUs, long segmentBaseAvailabilityTimeOffsetUs, long timeShiftBufferDepthMs) throws XmlPullParserException, IOException {
        long j = parseLong(xpp, "timescale", parent != null ? parent.timescale : 1L);
        long j2 = parseLong(xpp, "presentationTimeOffset", parent != null ? parent.presentationTimeOffset : 0L);
        long j3 = parseLong(xpp, "duration", parent != null ? parent.duration : C.TIME_UNSET);
        long j4 = parseLong(xpp, "startNumber", parent != null ? parent.startNumber : 1L);
        long finalAvailabilityTimeOffset = getFinalAvailabilityTimeOffset(baseUrlAvailabilityTimeOffsetUs, segmentBaseAvailabilityTimeOffsetUs);
        List<SegmentBase.SegmentTimelineElement> segmentTimeline = null;
        List<RangedUri> arrayList = null;
        RangedUri initialization = null;
        do {
            xpp.next();
            if (XmlPullParserUtil.isStartTag(xpp, "Initialization")) {
                initialization = parseInitialization(xpp);
            } else if (XmlPullParserUtil.isStartTag(xpp, "SegmentTimeline")) {
                segmentTimeline = parseSegmentTimeline(xpp, j, periodDurationMs);
            } else if (XmlPullParserUtil.isStartTag(xpp, "SegmentURL")) {
                if (arrayList == null) {
                    arrayList = new ArrayList<>();
                }
                arrayList.add(parseSegmentUrl(xpp));
            } else {
                maybeSkipTag(xpp);
            }
        } while (!XmlPullParserUtil.isEndTag(xpp, "SegmentList"));
        if (parent != null) {
            if (initialization == null) {
                initialization = parent.initialization;
            }
            if (segmentTimeline == null) {
                segmentTimeline = parent.segmentTimeline;
            }
            if (arrayList == null) {
                arrayList = parent.mediaSegments;
            }
        }
        return buildSegmentList(initialization, j, j2, j4, j3, segmentTimeline, finalAvailabilityTimeOffset, arrayList, timeShiftBufferDepthMs, periodStartUnixTimeMs);
    }

    protected SegmentBase.SegmentList buildSegmentList(RangedUri initialization, long timescale, long presentationTimeOffset, long startNumber, long duration, List<SegmentBase.SegmentTimelineElement> timeline, long availabilityTimeOffsetUs, List<RangedUri> segments, long timeShiftBufferDepthMs, long periodStartUnixTimeMs) {
        return new SegmentBase.SegmentList(initialization, timescale, presentationTimeOffset, startNumber, duration, timeline, availabilityTimeOffsetUs, segments, C.msToUs(timeShiftBufferDepthMs), C.msToUs(periodStartUnixTimeMs));
    }

    protected SegmentBase.SegmentTemplate parseSegmentTemplate(XmlPullParser xpp, SegmentBase.SegmentTemplate parent, List<Descriptor> adaptationSetSupplementalProperties, long periodStartUnixTimeMs, long periodDurationMs, long baseUrlAvailabilityTimeOffsetUs, long segmentBaseAvailabilityTimeOffsetUs, long timeShiftBufferDepthMs) throws XmlPullParserException, IOException {
        long j = parseLong(xpp, "timescale", parent != null ? parent.timescale : 1L);
        long j2 = parseLong(xpp, "presentationTimeOffset", parent != null ? parent.presentationTimeOffset : 0L);
        long j3 = parseLong(xpp, "duration", parent != null ? parent.duration : C.TIME_UNSET);
        long j4 = parseLong(xpp, "startNumber", parent != null ? parent.startNumber : 1L);
        long lastSegmentNumberSupplementalProperty = parseLastSegmentNumberSupplementalProperty(adaptationSetSupplementalProperties);
        long finalAvailabilityTimeOffset = getFinalAvailabilityTimeOffset(baseUrlAvailabilityTimeOffsetUs, segmentBaseAvailabilityTimeOffsetUs);
        List<SegmentBase.SegmentTimelineElement> segmentTimeline = null;
        UrlTemplate urlTemplate = parseUrlTemplate(xpp, "media", parent != null ? parent.mediaTemplate : null);
        UrlTemplate urlTemplate2 = parseUrlTemplate(xpp, "initialization", parent != null ? parent.initializationTemplate : null);
        RangedUri initialization = null;
        do {
            xpp.next();
            if (XmlPullParserUtil.isStartTag(xpp, "Initialization")) {
                initialization = parseInitialization(xpp);
            } else if (XmlPullParserUtil.isStartTag(xpp, "SegmentTimeline")) {
                segmentTimeline = parseSegmentTimeline(xpp, j, periodDurationMs);
            } else {
                maybeSkipTag(xpp);
            }
        } while (!XmlPullParserUtil.isEndTag(xpp, "SegmentTemplate"));
        if (parent != null) {
            if (initialization == null) {
                initialization = parent.initialization;
            }
            if (segmentTimeline == null) {
                segmentTimeline = parent.segmentTimeline;
            }
        }
        return buildSegmentTemplate(initialization, j, j2, j4, lastSegmentNumberSupplementalProperty, j3, segmentTimeline, finalAvailabilityTimeOffset, urlTemplate2, urlTemplate, timeShiftBufferDepthMs, periodStartUnixTimeMs);
    }

    protected SegmentBase.SegmentTemplate buildSegmentTemplate(RangedUri initialization, long timescale, long presentationTimeOffset, long startNumber, long endNumber, long duration, List<SegmentBase.SegmentTimelineElement> timeline, long availabilityTimeOffsetUs, UrlTemplate initializationTemplate, UrlTemplate mediaTemplate, long timeShiftBufferDepthMs, long periodStartUnixTimeMs) {
        return new SegmentBase.SegmentTemplate(initialization, timescale, presentationTimeOffset, startNumber, endNumber, duration, timeline, availabilityTimeOffsetUs, initializationTemplate, mediaTemplate, C.msToUs(timeShiftBufferDepthMs), C.msToUs(periodStartUnixTimeMs));
    }

    protected EventStream parseEventStream(XmlPullParser xpp) throws XmlPullParserException, IOException {
        String string = parseString(xpp, "schemeIdUri", "");
        String string2 = parseString(xpp, "value", "");
        long j = parseLong(xpp, "timescale", 1L);
        ArrayList arrayList = new ArrayList();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(512);
        do {
            xpp.next();
            if (XmlPullParserUtil.isStartTag(xpp, "Event")) {
                arrayList.add(parseEvent(xpp, string, string2, j, byteArrayOutputStream));
            } else {
                maybeSkipTag(xpp);
            }
        } while (!XmlPullParserUtil.isEndTag(xpp, "EventStream"));
        long[] jArr = new long[arrayList.size()];
        EventMessage[] eventMessageArr = new EventMessage[arrayList.size()];
        for (int i = 0; i < arrayList.size(); i++) {
            Pair pair = (Pair) arrayList.get(i);
            jArr[i] = ((Long) pair.first).longValue();
            eventMessageArr[i] = (EventMessage) pair.second;
        }
        return buildEventStream(string, string2, j, jArr, eventMessageArr);
    }

    protected EventStream buildEventStream(String schemeIdUri, String value, long timescale, long[] presentationTimesUs, EventMessage[] events) {
        return new EventStream(schemeIdUri, value, timescale, presentationTimesUs, events);
    }

    protected Pair<Long, EventMessage> parseEvent(XmlPullParser xpp, String schemeIdUri, String value, long timescale, ByteArrayOutputStream scratchOutputStream) throws XmlPullParserException, IllegalStateException, IOException, IllegalArgumentException {
        long j = parseLong(xpp, TtmlNode.ATTR_ID, 0L);
        long j2 = parseLong(xpp, "duration", C.TIME_UNSET);
        long j3 = parseLong(xpp, "presentationTime", 0L);
        long jScaleLargeTimestamp = Util.scaleLargeTimestamp(j2, 1000L, timescale);
        long jScaleLargeTimestamp2 = Util.scaleLargeTimestamp(j3, 1000000L, timescale);
        String string = parseString(xpp, "messageData", null);
        byte[] eventObject = parseEventObject(xpp, scratchOutputStream);
        Long lValueOf = Long.valueOf(jScaleLargeTimestamp2);
        if (string != null) {
            eventObject = Util.getUtf8Bytes(string);
        }
        return Pair.create(lValueOf, buildEvent(schemeIdUri, value, j, jScaleLargeTimestamp, eventObject));
    }

    protected byte[] parseEventObject(XmlPullParser xpp, ByteArrayOutputStream scratchOutputStream) throws XmlPullParserException, IllegalStateException, IOException, IllegalArgumentException {
        scratchOutputStream.reset();
        XmlSerializer xmlSerializerNewSerializer = Xml.newSerializer();
        xmlSerializerNewSerializer.setOutput(scratchOutputStream, Charsets.UTF_8.name());
        xpp.nextToken();
        while (!XmlPullParserUtil.isEndTag(xpp, "Event")) {
            switch (xpp.getEventType()) {
                case 0:
                    xmlSerializerNewSerializer.startDocument(null, false);
                    break;
                case 1:
                    xmlSerializerNewSerializer.endDocument();
                    break;
                case 2:
                    xmlSerializerNewSerializer.startTag(xpp.getNamespace(), xpp.getName());
                    for (int i = 0; i < xpp.getAttributeCount(); i++) {
                        xmlSerializerNewSerializer.attribute(xpp.getAttributeNamespace(i), xpp.getAttributeName(i), xpp.getAttributeValue(i));
                    }
                    break;
                case 3:
                    xmlSerializerNewSerializer.endTag(xpp.getNamespace(), xpp.getName());
                    break;
                case 4:
                    xmlSerializerNewSerializer.text(xpp.getText());
                    break;
                case 5:
                    xmlSerializerNewSerializer.cdsect(xpp.getText());
                    break;
                case 6:
                    xmlSerializerNewSerializer.entityRef(xpp.getText());
                    break;
                case 7:
                    xmlSerializerNewSerializer.ignorableWhitespace(xpp.getText());
                    break;
                case 8:
                    xmlSerializerNewSerializer.processingInstruction(xpp.getText());
                    break;
                case 9:
                    xmlSerializerNewSerializer.comment(xpp.getText());
                    break;
                case 10:
                    xmlSerializerNewSerializer.docdecl(xpp.getText());
                    break;
            }
            xpp.nextToken();
        }
        xmlSerializerNewSerializer.flush();
        return scratchOutputStream.toByteArray();
    }

    protected EventMessage buildEvent(String schemeIdUri, String value, long id, long durationMs, byte[] messageData) {
        return new EventMessage(schemeIdUri, value, durationMs, id, messageData);
    }

    protected List<SegmentBase.SegmentTimelineElement> parseSegmentTimeline(XmlPullParser xpp, long timescale, long periodDurationMs) throws XmlPullParserException, IOException {
        ArrayList arrayList = new ArrayList();
        long jAddSegmentTimelineElementsToList = 0;
        long j = -9223372036854775807L;
        boolean z = false;
        int i = 0;
        do {
            xpp.next();
            if (XmlPullParserUtil.isStartTag(xpp, ExifInterface.LATITUDE_SOUTH)) {
                long j2 = parseLong(xpp, "t", C.TIME_UNSET);
                if (z) {
                    jAddSegmentTimelineElementsToList = addSegmentTimelineElementsToList(arrayList, jAddSegmentTimelineElementsToList, j, i, j2);
                }
                if (j2 == C.TIME_UNSET) {
                    j2 = jAddSegmentTimelineElementsToList;
                }
                j = parseLong(xpp, "d", C.TIME_UNSET);
                i = parseInt(xpp, "r", 0);
                z = true;
                jAddSegmentTimelineElementsToList = j2;
            } else {
                maybeSkipTag(xpp);
            }
        } while (!XmlPullParserUtil.isEndTag(xpp, "SegmentTimeline"));
        if (z) {
            addSegmentTimelineElementsToList(arrayList, jAddSegmentTimelineElementsToList, j, i, Util.scaleLargeTimestamp(periodDurationMs, timescale, 1000L));
        }
        return arrayList;
    }

    private long addSegmentTimelineElementsToList(List<SegmentBase.SegmentTimelineElement> segmentTimeline, long startTime, long elementDuration, int elementRepeatCount, long endTime) {
        int iCeilDivide = elementRepeatCount >= 0 ? elementRepeatCount + 1 : (int) Util.ceilDivide(endTime - startTime, elementDuration);
        for (int i = 0; i < iCeilDivide; i++) {
            segmentTimeline.add(buildSegmentTimelineElement(startTime, elementDuration));
            startTime += elementDuration;
        }
        return startTime;
    }

    protected SegmentBase.SegmentTimelineElement buildSegmentTimelineElement(long startTime, long duration) {
        return new SegmentBase.SegmentTimelineElement(startTime, duration);
    }

    protected UrlTemplate parseUrlTemplate(XmlPullParser xpp, String name, UrlTemplate defaultValue) {
        String attributeValue = xpp.getAttributeValue(null, name);
        return attributeValue != null ? UrlTemplate.compile(attributeValue) : defaultValue;
    }

    protected RangedUri parseInitialization(XmlPullParser xpp) {
        return parseRangedUrl(xpp, "sourceURL", SessionDescription.ATTR_RANGE);
    }

    protected RangedUri parseSegmentUrl(XmlPullParser xpp) {
        return parseRangedUrl(xpp, "media", "mediaRange");
    }

    protected RangedUri parseRangedUrl(XmlPullParser xpp, String urlAttribute, String rangeAttribute) throws NumberFormatException {
        long j;
        long j2;
        String attributeValue = xpp.getAttributeValue(null, urlAttribute);
        String attributeValue2 = xpp.getAttributeValue(null, rangeAttribute);
        if (attributeValue2 != null) {
            String[] strArrSplit = attributeValue2.split("-");
            j = Long.parseLong(strArrSplit[0]);
            if (strArrSplit.length == 2) {
                j2 = (Long.parseLong(strArrSplit[1]) - j) + 1;
            }
            return buildRangedUri(attributeValue, j, j2);
        }
        j = 0;
        j2 = -1;
        return buildRangedUri(attributeValue, j, j2);
    }

    protected RangedUri buildRangedUri(String urlText, long rangeStart, long rangeLength) {
        return new RangedUri(urlText, rangeStart, rangeLength);
    }

    protected ProgramInformation parseProgramInformation(XmlPullParser xpp) throws XmlPullParserException, IOException {
        String strNextText = null;
        String string = parseString(xpp, "moreInformationURL", null);
        String string2 = parseString(xpp, "lang", null);
        String strNextText2 = null;
        String strNextText3 = null;
        while (true) {
            xpp.next();
            if (XmlPullParserUtil.isStartTag(xpp, "Title")) {
                strNextText = xpp.nextText();
            } else if (XmlPullParserUtil.isStartTag(xpp, "Source")) {
                strNextText2 = xpp.nextText();
            } else if (XmlPullParserUtil.isStartTag(xpp, ExifInterface.TAG_COPYRIGHT)) {
                strNextText3 = xpp.nextText();
            } else {
                maybeSkipTag(xpp);
            }
            String str = strNextText3;
            if (XmlPullParserUtil.isEndTag(xpp, "ProgramInformation")) {
                return new ProgramInformation(strNextText, strNextText2, str, string, string2);
            }
            strNextText3 = str;
        }
    }

    protected String parseLabel(XmlPullParser xpp) throws XmlPullParserException, IOException {
        return parseText(xpp, "Label");
    }

    protected List<BaseUrl> parseBaseUrl(XmlPullParser xpp, List<BaseUrl> parentBaseUrls) throws XmlPullParserException, IOException {
        String attributeValue = xpp.getAttributeValue(null, "dvb:priority");
        int i = attributeValue != null ? Integer.parseInt(attributeValue) : 1;
        String attributeValue2 = xpp.getAttributeValue(null, "dvb:weight");
        int i2 = attributeValue2 != null ? Integer.parseInt(attributeValue2) : 1;
        String attributeValue3 = xpp.getAttributeValue(null, "serviceLocation");
        String text = parseText(xpp, "BaseURL");
        if (attributeValue3 == null) {
            attributeValue3 = text;
        }
        if (UriUtil.isAbsolute(text)) {
            return Lists.newArrayList(new BaseUrl(text, attributeValue3, i, i2));
        }
        ArrayList arrayList = new ArrayList();
        for (int i3 = 0; i3 < parentBaseUrls.size(); i3++) {
            BaseUrl baseUrl = parentBaseUrls.get(i3);
            arrayList.add(new BaseUrl(UriUtil.resolve(baseUrl.url, text), baseUrl.serviceLocation, baseUrl.priority, baseUrl.weight));
        }
        return arrayList;
    }

    protected long parseAvailabilityTimeOffsetUs(XmlPullParser xpp, long parentAvailabilityTimeOffsetUs) {
        String attributeValue = xpp.getAttributeValue(null, "availabilityTimeOffset");
        if (attributeValue == null) {
            return parentAvailabilityTimeOffsetUs;
        }
        if ("INF".equals(attributeValue)) {
            return Long.MAX_VALUE;
        }
        return (long) (Float.parseFloat(attributeValue) * 1000000.0f);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:4:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    protected int parseAudioChannelConfiguration(org.xmlpull.v1.XmlPullParser r4) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        /*
            r3 = this;
            java.lang.String r0 = "schemeIdUri"
            r1 = 0
            java.lang.String r0 = parseString(r4, r0, r1)
            r0.hashCode()
            int r1 = r0.hashCode()
            r2 = -1
            switch(r1) {
                case -1352850286: goto L39;
                case -1138141449: goto L2d;
                case -986633423: goto L21;
                case 2036691300: goto L15;
                default: goto L13;
            }
        L13:
            r0 = r2
            goto L44
        L15:
            java.lang.String r1 = "urn:dolby:dash:audio_channel_configuration:2011"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L1f
            goto L13
        L1f:
            r0 = 3
            goto L44
        L21:
            java.lang.String r1 = "urn:mpeg:mpegB:cicp:ChannelConfiguration"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L2b
            goto L13
        L2b:
            r0 = 2
            goto L44
        L2d:
            java.lang.String r1 = "tag:dolby.com,2014:dash:audio_channel_configuration:2011"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L37
            goto L13
        L37:
            r0 = 1
            goto L44
        L39:
            java.lang.String r1 = "urn:mpeg:dash:23003:3:audio_channel_configuration:2011"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L43
            goto L13
        L43:
            r0 = 0
        L44:
            switch(r0) {
                case 0: goto L52;
                case 1: goto L4d;
                case 2: goto L48;
                case 3: goto L4d;
                default: goto L47;
            }
        L47:
            goto L59
        L48:
            int r2 = parseMpegChannelConfiguration(r4)
            goto L59
        L4d:
            int r2 = parseDolbyChannelConfiguration(r4)
            goto L59
        L52:
            java.lang.String r0 = "value"
            int r2 = parseInt(r4, r0, r2)
        L59:
            r4.next()
            java.lang.String r0 = "AudioChannelConfiguration"
            boolean r0 = com.google.android.exoplayer2.util.XmlPullParserUtil.isEndTag(r4, r0)
            if (r0 == 0) goto L59
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.source.dash.manifest.DashManifestParser.parseAudioChannelConfiguration(org.xmlpull.v1.XmlPullParser):int");
    }

    protected int parseSelectionFlagsFromRoleDescriptors(List<Descriptor> roleDescriptors) {
        int selectionFlagsFromDashRoleScheme = 0;
        for (int i = 0; i < roleDescriptors.size(); i++) {
            Descriptor descriptor = roleDescriptors.get(i);
            if (Ascii.equalsIgnoreCase("urn:mpeg:dash:role:2011", descriptor.schemeIdUri)) {
                selectionFlagsFromDashRoleScheme |= parseSelectionFlagsFromDashRoleScheme(descriptor.value);
            }
        }
        return selectionFlagsFromDashRoleScheme;
    }

    protected int parseSelectionFlagsFromDashRoleScheme(String value) {
        if (value == null) {
            return 0;
        }
        value.hashCode();
        if (value.equals("forced_subtitle")) {
            return 2;
        }
        return !value.equals("main") ? 0 : 1;
    }

    protected int parseRoleFlagsFromRoleDescriptors(List<Descriptor> roleDescriptors) {
        int roleFlagsFromDashRoleScheme = 0;
        for (int i = 0; i < roleDescriptors.size(); i++) {
            Descriptor descriptor = roleDescriptors.get(i);
            if (Ascii.equalsIgnoreCase("urn:mpeg:dash:role:2011", descriptor.schemeIdUri)) {
                roleFlagsFromDashRoleScheme |= parseRoleFlagsFromDashRoleScheme(descriptor.value);
            }
        }
        return roleFlagsFromDashRoleScheme;
    }

    protected int parseRoleFlagsFromAccessibilityDescriptors(List<Descriptor> accessibilityDescriptors) {
        int tvaAudioPurposeCsValue;
        int i = 0;
        for (int i2 = 0; i2 < accessibilityDescriptors.size(); i2++) {
            Descriptor descriptor = accessibilityDescriptors.get(i2);
            if (Ascii.equalsIgnoreCase("urn:mpeg:dash:role:2011", descriptor.schemeIdUri)) {
                tvaAudioPurposeCsValue = parseRoleFlagsFromDashRoleScheme(descriptor.value);
            } else if (Ascii.equalsIgnoreCase("urn:tva:metadata:cs:AudioPurposeCS:2007", descriptor.schemeIdUri)) {
                tvaAudioPurposeCsValue = parseTvaAudioPurposeCsValue(descriptor.value);
            }
            i |= tvaAudioPurposeCsValue;
        }
        return i;
    }

    protected int parseRoleFlagsFromProperties(List<Descriptor> accessibilityDescriptors) {
        int i = 0;
        for (int i2 = 0; i2 < accessibilityDescriptors.size(); i2++) {
            if (Ascii.equalsIgnoreCase("http://dashif.org/guidelines/trickmode", accessibilityDescriptors.get(i2).schemeIdUri)) {
                i |= 16384;
            }
        }
        return i;
    }

    protected int parseRoleFlagsFromDashRoleScheme(String value) {
        if (value == null) {
            return 0;
        }
        value.hashCode();
        switch (value) {
        }
        return 0;
    }

    protected int parseTvaAudioPurposeCsValue(String value) {
        if (value == null) {
            return 0;
        }
        value.hashCode();
        switch (value) {
        }
        return 0;
    }

    public static void maybeSkipTag(XmlPullParser xpp) throws XmlPullParserException, IOException {
        if (XmlPullParserUtil.isStartTag(xpp)) {
            int i = 1;
            while (i != 0) {
                xpp.next();
                if (XmlPullParserUtil.isStartTag(xpp)) {
                    i++;
                } else if (XmlPullParserUtil.isEndTag(xpp)) {
                    i--;
                }
            }
        }
    }

    private static void filterRedundantIncompleteSchemeDatas(ArrayList<DrmInitData.SchemeData> schemeDatas) {
        for (int size = schemeDatas.size() - 1; size >= 0; size--) {
            DrmInitData.SchemeData schemeData = schemeDatas.get(size);
            if (!schemeData.hasData()) {
                int i = 0;
                while (true) {
                    if (i >= schemeDatas.size()) {
                        break;
                    }
                    if (schemeDatas.get(i).canReplace(schemeData)) {
                        schemeDatas.remove(size);
                        break;
                    }
                    i++;
                }
            }
        }
    }

    private static String getSampleMimeType(String containerMimeType, String codecs) {
        if (MimeTypes.isAudio(containerMimeType)) {
            return MimeTypes.getAudioMediaMimeType(codecs);
        }
        if (MimeTypes.isVideo(containerMimeType)) {
            return MimeTypes.getVideoMediaMimeType(codecs);
        }
        if (MimeTypes.isText(containerMimeType)) {
            return MimeTypes.APPLICATION_RAWCC.equals(containerMimeType) ? MimeTypes.getTextMediaMimeType(codecs) : containerMimeType;
        }
        if (!MimeTypes.APPLICATION_MP4.equals(containerMimeType)) {
            return null;
        }
        String mediaMimeType = MimeTypes.getMediaMimeType(codecs);
        return MimeTypes.TEXT_VTT.equals(mediaMimeType) ? MimeTypes.APPLICATION_MP4VTT : mediaMimeType;
    }

    private static String checkLanguageConsistency(String firstLanguage, String secondLanguage) {
        if (firstLanguage == null) {
            return secondLanguage;
        }
        if (secondLanguage == null) {
            return firstLanguage;
        }
        Assertions.checkState(firstLanguage.equals(secondLanguage));
        return firstLanguage;
    }

    private static int checkContentTypeConsistency(int firstType, int secondType) {
        if (firstType == -1) {
            return secondType;
        }
        if (secondType == -1) {
            return firstType;
        }
        Assertions.checkState(firstType == secondType);
        return firstType;
    }

    protected static Descriptor parseDescriptor(XmlPullParser xpp, String tag) throws XmlPullParserException, IOException {
        String string = parseString(xpp, "schemeIdUri", "");
        String string2 = parseString(xpp, "value", null);
        String string3 = parseString(xpp, TtmlNode.ATTR_ID, null);
        do {
            xpp.next();
        } while (!XmlPullParserUtil.isEndTag(xpp, tag));
        return new Descriptor(string, string2, string3);
    }

    protected static int parseCea608AccessibilityChannel(List<Descriptor> accessibilityDescriptors) {
        for (int i = 0; i < accessibilityDescriptors.size(); i++) {
            Descriptor descriptor = accessibilityDescriptors.get(i);
            if ("urn:scte:dash:cc:cea-608:2015".equals(descriptor.schemeIdUri) && descriptor.value != null) {
                Matcher matcher = CEA_608_ACCESSIBILITY_PATTERN.matcher(descriptor.value);
                if (matcher.matches()) {
                    return Integer.parseInt(matcher.group(1));
                }
                String strValueOf = String.valueOf(descriptor.value);
                Log.w(TAG, strValueOf.length() != 0 ? "Unable to parse CEA-608 channel number from: ".concat(strValueOf) : new String("Unable to parse CEA-608 channel number from: "));
            }
        }
        return -1;
    }

    protected static int parseCea708AccessibilityChannel(List<Descriptor> accessibilityDescriptors) {
        for (int i = 0; i < accessibilityDescriptors.size(); i++) {
            Descriptor descriptor = accessibilityDescriptors.get(i);
            if ("urn:scte:dash:cc:cea-708:2015".equals(descriptor.schemeIdUri) && descriptor.value != null) {
                Matcher matcher = CEA_708_ACCESSIBILITY_PATTERN.matcher(descriptor.value);
                if (matcher.matches()) {
                    return Integer.parseInt(matcher.group(1));
                }
                String strValueOf = String.valueOf(descriptor.value);
                Log.w(TAG, strValueOf.length() != 0 ? "Unable to parse CEA-708 service block number from: ".concat(strValueOf) : new String("Unable to parse CEA-708 service block number from: "));
            }
        }
        return -1;
    }

    protected static String parseEac3SupplementalProperties(List<Descriptor> supplementalProperties) {
        for (int i = 0; i < supplementalProperties.size(); i++) {
            Descriptor descriptor = supplementalProperties.get(i);
            String str = descriptor.schemeIdUri;
            if ("tag:dolby.com,2018:dash:EC3_ExtensionType:2018".equals(str) && "JOC".equals(descriptor.value)) {
                return MimeTypes.AUDIO_E_AC3_JOC;
            }
            if ("tag:dolby.com,2014:dash:DolbyDigitalPlusExtensionType:2014".equals(str) && Ac3Util.E_AC3_JOC_CODEC_STRING.equals(descriptor.value)) {
                return MimeTypes.AUDIO_E_AC3_JOC;
            }
        }
        return MimeTypes.AUDIO_E_AC3;
    }

    protected static float parseFrameRate(XmlPullParser xpp, float defaultValue) throws NumberFormatException {
        String attributeValue = xpp.getAttributeValue(null, "frameRate");
        if (attributeValue == null) {
            return defaultValue;
        }
        Matcher matcher = FRAME_RATE_PATTERN.matcher(attributeValue);
        if (!matcher.matches()) {
            return defaultValue;
        }
        int i = Integer.parseInt(matcher.group(1));
        return !TextUtils.isEmpty(matcher.group(2)) ? i / Integer.parseInt(r2) : i;
    }

    protected static long parseDuration(XmlPullParser xpp, String name, long defaultValue) {
        String attributeValue = xpp.getAttributeValue(null, name);
        return attributeValue == null ? defaultValue : Util.parseXsDuration(attributeValue);
    }

    protected static long parseDateTime(XmlPullParser xpp, String name, long defaultValue) throws ParserException {
        String attributeValue = xpp.getAttributeValue(null, name);
        return attributeValue == null ? defaultValue : Util.parseXsDateTime(attributeValue);
    }

    protected static String parseText(XmlPullParser xpp, String label) throws XmlPullParserException, IOException {
        String text = "";
        do {
            xpp.next();
            if (xpp.getEventType() == 4) {
                text = xpp.getText();
            } else {
                maybeSkipTag(xpp);
            }
        } while (!XmlPullParserUtil.isEndTag(xpp, label));
        return text;
    }

    protected static int parseInt(XmlPullParser xpp, String name, int defaultValue) {
        String attributeValue = xpp.getAttributeValue(null, name);
        return attributeValue == null ? defaultValue : Integer.parseInt(attributeValue);
    }

    protected static long parseLong(XmlPullParser xpp, String name, long defaultValue) {
        String attributeValue = xpp.getAttributeValue(null, name);
        return attributeValue == null ? defaultValue : Long.parseLong(attributeValue);
    }

    protected static float parseFloat(XmlPullParser xpp, String name, float defaultValue) {
        String attributeValue = xpp.getAttributeValue(null, name);
        return attributeValue == null ? defaultValue : Float.parseFloat(attributeValue);
    }

    protected static String parseString(XmlPullParser xpp, String name, String defaultValue) {
        String attributeValue = xpp.getAttributeValue(null, name);
        return attributeValue == null ? defaultValue : attributeValue;
    }

    protected static int parseMpegChannelConfiguration(XmlPullParser xpp) {
        int i = parseInt(xpp, "value", -1);
        if (i < 0) {
            return -1;
        }
        int[] iArr = MPEG_CHANNEL_CONFIGURATION_MAPPING;
        if (i < iArr.length) {
            return iArr[i];
        }
        return -1;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:7:0x001c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    protected static int parseDolbyChannelConfiguration(org.xmlpull.v1.XmlPullParser r4) {
        /*
            r0 = 0
            java.lang.String r1 = "value"
            java.lang.String r4 = r4.getAttributeValue(r0, r1)
            r0 = -1
            if (r4 != 0) goto Lc
            return r0
        Lc:
            java.lang.String r4 = com.google.common.base.Ascii.toLowerCase(r4)
            r4.hashCode()
            int r1 = r4.hashCode()
            r2 = 2
            r3 = 1
            switch(r1) {
                case 1596796: goto L3f;
                case 2937391: goto L34;
                case 3094035: goto L29;
                case 3133436: goto L1e;
                default: goto L1c;
            }
        L1c:
            r4 = r0
            goto L49
        L1e:
            java.lang.String r1 = "fa01"
            boolean r4 = r4.equals(r1)
            if (r4 != 0) goto L27
            goto L1c
        L27:
            r4 = 3
            goto L49
        L29:
            java.lang.String r1 = "f801"
            boolean r4 = r4.equals(r1)
            if (r4 != 0) goto L32
            goto L1c
        L32:
            r4 = r2
            goto L49
        L34:
            java.lang.String r1 = "a000"
            boolean r4 = r4.equals(r1)
            if (r4 != 0) goto L3d
            goto L1c
        L3d:
            r4 = r3
            goto L49
        L3f:
            java.lang.String r1 = "4000"
            boolean r4 = r4.equals(r1)
            if (r4 != 0) goto L48
            goto L1c
        L48:
            r4 = 0
        L49:
            switch(r4) {
                case 0: goto L53;
                case 1: goto L52;
                case 2: goto L50;
                case 3: goto L4d;
                default: goto L4c;
            }
        L4c:
            return r0
        L4d:
            r4 = 8
            return r4
        L50:
            r4 = 6
            return r4
        L52:
            return r2
        L53:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.source.dash.manifest.DashManifestParser.parseDolbyChannelConfiguration(org.xmlpull.v1.XmlPullParser):int");
    }

    protected static long parseLastSegmentNumberSupplementalProperty(List<Descriptor> supplementalProperties) {
        for (int i = 0; i < supplementalProperties.size(); i++) {
            Descriptor descriptor = supplementalProperties.get(i);
            if (Ascii.equalsIgnoreCase("http://dashif.org/guidelines/last-segment-number", descriptor.schemeIdUri)) {
                return Long.parseLong(descriptor.value);
            }
        }
        return -1L;
    }

    protected static final class RepresentationInfo {
        public final ImmutableList<BaseUrl> baseUrls;
        public final ArrayList<DrmInitData.SchemeData> drmSchemeDatas;
        public final String drmSchemeType;
        public final Format format;
        public final ArrayList<Descriptor> inbandEventStreams;
        public final long revisionId;
        public final SegmentBase segmentBase;

        public RepresentationInfo(Format format, List<BaseUrl> baseUrls, SegmentBase segmentBase, String drmSchemeType, ArrayList<DrmInitData.SchemeData> drmSchemeDatas, ArrayList<Descriptor> inbandEventStreams, long revisionId) {
            this.format = format;
            this.baseUrls = ImmutableList.copyOf((Collection) baseUrls);
            this.segmentBase = segmentBase;
            this.drmSchemeType = drmSchemeType;
            this.drmSchemeDatas = drmSchemeDatas;
            this.inbandEventStreams = inbandEventStreams;
            this.revisionId = revisionId;
        }
    }
}
