package com.google.android.exoplayer2.extractor.flv;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.extractor.DummyTrackOutput;
import com.google.android.exoplayer2.util.ParsableByteArray;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes.dex */
final class ScriptTagPayloadReader extends TagPayloadReader {
    private static final int AMF_TYPE_BOOLEAN = 1;
    private static final int AMF_TYPE_DATE = 11;
    private static final int AMF_TYPE_ECMA_ARRAY = 8;
    private static final int AMF_TYPE_END_MARKER = 9;
    private static final int AMF_TYPE_NUMBER = 0;
    private static final int AMF_TYPE_OBJECT = 3;
    private static final int AMF_TYPE_STRICT_ARRAY = 10;
    private static final int AMF_TYPE_STRING = 2;
    private static final String KEY_DURATION = "duration";
    private static final String KEY_FILE_POSITIONS = "filepositions";
    private static final String KEY_KEY_FRAMES = "keyframes";
    private static final String KEY_TIMES = "times";
    private static final String NAME_METADATA = "onMetaData";
    private long durationUs;
    private long[] keyFrameTagPositions;
    private long[] keyFrameTimesUs;

    @Override // com.google.android.exoplayer2.extractor.flv.TagPayloadReader
    protected boolean parseHeader(ParsableByteArray data) {
        return true;
    }

    @Override // com.google.android.exoplayer2.extractor.flv.TagPayloadReader
    public void seek() {
    }

    public ScriptTagPayloadReader() {
        super(new DummyTrackOutput());
        this.durationUs = C.TIME_UNSET;
        this.keyFrameTimesUs = new long[0];
        this.keyFrameTagPositions = new long[0];
    }

    public long getDurationUs() {
        return this.durationUs;
    }

    public long[] getKeyFrameTimesUs() {
        return this.keyFrameTimesUs;
    }

    public long[] getKeyFrameTagPositions() {
        return this.keyFrameTagPositions;
    }

    @Override // com.google.android.exoplayer2.extractor.flv.TagPayloadReader
    protected boolean parsePayload(ParsableByteArray data, long timeUs) {
        if (readAmfType(data) != 2 || !NAME_METADATA.equals(readAmfString(data)) || readAmfType(data) != 8) {
            return false;
        }
        HashMap<String, Object> amfEcmaArray = readAmfEcmaArray(data);
        Object obj = amfEcmaArray.get(KEY_DURATION);
        if (obj instanceof Double) {
            double dDoubleValue = ((Double) obj).doubleValue();
            if (dDoubleValue > 0.0d) {
                this.durationUs = (long) (dDoubleValue * 1000000.0d);
            }
        }
        Object obj2 = amfEcmaArray.get(KEY_KEY_FRAMES);
        if (obj2 instanceof Map) {
            Map map = (Map) obj2;
            Object obj3 = map.get(KEY_FILE_POSITIONS);
            Object obj4 = map.get(KEY_TIMES);
            if ((obj3 instanceof List) && (obj4 instanceof List)) {
                List list = (List) obj3;
                List list2 = (List) obj4;
                int size = list2.size();
                this.keyFrameTimesUs = new long[size];
                this.keyFrameTagPositions = new long[size];
                for (int i = 0; i < size; i++) {
                    Object obj5 = list.get(i);
                    Object obj6 = list2.get(i);
                    if ((obj6 instanceof Double) && (obj5 instanceof Double)) {
                        this.keyFrameTimesUs[i] = (long) (((Double) obj6).doubleValue() * 1000000.0d);
                        this.keyFrameTagPositions[i] = ((Double) obj5).longValue();
                    } else {
                        this.keyFrameTimesUs = new long[0];
                        this.keyFrameTagPositions = new long[0];
                        break;
                    }
                }
            }
        }
        return false;
    }

    private static int readAmfType(ParsableByteArray data) {
        return data.readUnsignedByte();
    }

    private static Boolean readAmfBoolean(ParsableByteArray data) {
        return Boolean.valueOf(data.readUnsignedByte() == 1);
    }

    private static Double readAmfDouble(ParsableByteArray data) {
        return Double.valueOf(Double.longBitsToDouble(data.readLong()));
    }

    private static String readAmfString(ParsableByteArray data) {
        int unsignedShort = data.readUnsignedShort();
        int position = data.getPosition();
        data.skipBytes(unsignedShort);
        return new String(data.getData(), position, unsignedShort);
    }

    private static ArrayList<Object> readAmfStrictArray(ParsableByteArray data) {
        int unsignedIntToInt = data.readUnsignedIntToInt();
        ArrayList<Object> arrayList = new ArrayList<>(unsignedIntToInt);
        for (int i = 0; i < unsignedIntToInt; i++) {
            Object amfData = readAmfData(data, readAmfType(data));
            if (amfData != null) {
                arrayList.add(amfData);
            }
        }
        return arrayList;
    }

    private static HashMap<String, Object> readAmfObject(ParsableByteArray data) {
        HashMap<String, Object> map = new HashMap<>();
        while (true) {
            String amfString = readAmfString(data);
            int amfType = readAmfType(data);
            if (amfType == 9) {
                return map;
            }
            Object amfData = readAmfData(data, amfType);
            if (amfData != null) {
                map.put(amfString, amfData);
            }
        }
    }

    private static HashMap<String, Object> readAmfEcmaArray(ParsableByteArray data) {
        int unsignedIntToInt = data.readUnsignedIntToInt();
        HashMap<String, Object> map = new HashMap<>(unsignedIntToInt);
        for (int i = 0; i < unsignedIntToInt; i++) {
            String amfString = readAmfString(data);
            Object amfData = readAmfData(data, readAmfType(data));
            if (amfData != null) {
                map.put(amfString, amfData);
            }
        }
        return map;
    }

    private static Date readAmfDate(ParsableByteArray data) {
        Date date = new Date((long) readAmfDouble(data).doubleValue());
        data.skipBytes(2);
        return date;
    }

    private static Object readAmfData(ParsableByteArray data, int type) {
        if (type == 0) {
            return readAmfDouble(data);
        }
        if (type == 1) {
            return readAmfBoolean(data);
        }
        if (type == 2) {
            return readAmfString(data);
        }
        if (type == 3) {
            return readAmfObject(data);
        }
        if (type == 8) {
            return readAmfEcmaArray(data);
        }
        if (type == 10) {
            return readAmfStrictArray(data);
        }
        if (type != 11) {
            return null;
        }
        return readAmfDate(data);
    }
}
