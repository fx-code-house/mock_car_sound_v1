package com.google.android.exoplayer2.extractor.mp4;

import android.util.Pair;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.drm.DrmInitData;
import com.google.android.exoplayer2.extractor.ExtractorUtil;
import com.google.android.exoplayer2.extractor.GaplessInfoHolder;
import com.google.android.exoplayer2.extractor.mp4.Atom;
import com.google.android.exoplayer2.extractor.ts.PsExtractor;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.metadata.mp4.MdtaMetadataEntry;
import com.google.android.exoplayer2.metadata.mp4.SmtaMetadataEntry;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.Util;
import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.thor.basemodule.gui.view.CircleBarView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes.dex */
final class AtomParsers {
    private static final int MAX_GAPLESS_TRIM_SIZE_SAMPLES = 4;
    private static final String TAG = "AtomParsers";
    private static final int TYPE_clcp = 1668047728;
    private static final int TYPE_mdta = 1835299937;
    private static final int TYPE_meta = 1835365473;
    private static final int TYPE_nclc = 1852009571;
    private static final int TYPE_nclx = 1852009592;
    private static final int TYPE_sbtl = 1935832172;
    private static final int TYPE_soun = 1936684398;
    private static final int TYPE_subt = 1937072756;
    private static final int TYPE_text = 1952807028;
    private static final int TYPE_vide = 1986618469;
    private static final byte[] opusMagic = Util.getUtf8Bytes("OpusHead");

    private interface SampleSizeBox {
        int getFixedSampleSize();

        int getSampleCount();

        int readNextSampleSize();
    }

    private static int getTrackTypeForHdlr(int hdlr) {
        if (hdlr == TYPE_soun) {
            return 1;
        }
        if (hdlr == TYPE_vide) {
            return 2;
        }
        if (hdlr == TYPE_text || hdlr == TYPE_sbtl || hdlr == TYPE_subt || hdlr == TYPE_clcp) {
            return 3;
        }
        return hdlr == 1835365473 ? 5 : -1;
    }

    public static List<TrackSampleTable> parseTraks(Atom.ContainerAtom moov, GaplessInfoHolder gaplessInfoHolder, long duration, DrmInitData drmInitData, boolean ignoreEditLists, boolean isQuickTime, Function<Track, Track> modifyTrackFunction) throws ParserException {
        Track trackApply;
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < moov.containerChildren.size(); i++) {
            Atom.ContainerAtom containerAtom = moov.containerChildren.get(i);
            if (containerAtom.type == 1953653099 && (trackApply = modifyTrackFunction.apply(parseTrak(containerAtom, (Atom.LeafAtom) Assertions.checkNotNull(moov.getLeafAtomOfType(Atom.TYPE_mvhd)), duration, drmInitData, ignoreEditLists, isQuickTime))) != null) {
                arrayList.add(parseStbl(trackApply, (Atom.ContainerAtom) Assertions.checkNotNull(((Atom.ContainerAtom) Assertions.checkNotNull(((Atom.ContainerAtom) Assertions.checkNotNull(containerAtom.getContainerAtomOfType(Atom.TYPE_mdia))).getContainerAtomOfType(Atom.TYPE_minf))).getContainerAtomOfType(Atom.TYPE_stbl)), gaplessInfoHolder));
            }
        }
        return arrayList;
    }

    public static Pair<Metadata, Metadata> parseUdta(Atom.LeafAtom udtaAtom) {
        ParsableByteArray parsableByteArray = udtaAtom.data;
        parsableByteArray.setPosition(8);
        Metadata udtaMeta = null;
        Metadata smta = null;
        while (parsableByteArray.bytesLeft() >= 8) {
            int position = parsableByteArray.getPosition();
            int i = parsableByteArray.readInt();
            int i2 = parsableByteArray.readInt();
            if (i2 == 1835365473) {
                parsableByteArray.setPosition(position);
                udtaMeta = parseUdtaMeta(parsableByteArray, position + i);
            } else if (i2 == 1936553057) {
                parsableByteArray.setPosition(position);
                smta = parseSmta(parsableByteArray, position + i);
            }
            parsableByteArray.setPosition(position + i);
        }
        return Pair.create(udtaMeta, smta);
    }

    public static Metadata parseMdtaFromMeta(Atom.ContainerAtom meta) {
        Atom.LeafAtom leafAtomOfType = meta.getLeafAtomOfType(Atom.TYPE_hdlr);
        Atom.LeafAtom leafAtomOfType2 = meta.getLeafAtomOfType(Atom.TYPE_keys);
        Atom.LeafAtom leafAtomOfType3 = meta.getLeafAtomOfType(Atom.TYPE_ilst);
        if (leafAtomOfType == null || leafAtomOfType2 == null || leafAtomOfType3 == null || parseHdlr(leafAtomOfType.data) != TYPE_mdta) {
            return null;
        }
        ParsableByteArray parsableByteArray = leafAtomOfType2.data;
        parsableByteArray.setPosition(12);
        int i = parsableByteArray.readInt();
        String[] strArr = new String[i];
        for (int i2 = 0; i2 < i; i2++) {
            int i3 = parsableByteArray.readInt();
            parsableByteArray.skipBytes(4);
            strArr[i2] = parsableByteArray.readString(i3 - 8);
        }
        ParsableByteArray parsableByteArray2 = leafAtomOfType3.data;
        parsableByteArray2.setPosition(8);
        ArrayList arrayList = new ArrayList();
        while (parsableByteArray2.bytesLeft() > 8) {
            int position = parsableByteArray2.getPosition();
            int i4 = parsableByteArray2.readInt();
            int i5 = parsableByteArray2.readInt() - 1;
            if (i5 >= 0 && i5 < i) {
                MdtaMetadataEntry mdtaMetadataEntryFromIlst = MetadataUtil.parseMdtaMetadataEntryFromIlst(parsableByteArray2, position + i4, strArr[i5]);
                if (mdtaMetadataEntryFromIlst != null) {
                    arrayList.add(mdtaMetadataEntryFromIlst);
                }
            } else {
                Log.w(TAG, new StringBuilder(52).append("Skipped metadata with unknown key index: ").append(i5).toString());
            }
            parsableByteArray2.setPosition(position + i4);
        }
        if (arrayList.isEmpty()) {
            return null;
        }
        return new Metadata(arrayList);
    }

    public static void maybeSkipRemainingMetaAtomHeaderBytes(ParsableByteArray meta) {
        int position = meta.getPosition();
        meta.skipBytes(4);
        if (meta.readInt() != 1751411826) {
            position += 4;
        }
        meta.setPosition(position);
    }

    private static Track parseTrak(Atom.ContainerAtom trak, Atom.LeafAtom mvhd, long duration, DrmInitData drmInitData, boolean ignoreEditLists, boolean isQuickTime) throws ParserException {
        Atom.LeafAtom leafAtom;
        long j;
        long[] jArr;
        long[] jArr2;
        Atom.ContainerAtom containerAtomOfType;
        Pair<long[], long[]> edts;
        Atom.ContainerAtom containerAtom = (Atom.ContainerAtom) Assertions.checkNotNull(trak.getContainerAtomOfType(Atom.TYPE_mdia));
        int trackTypeForHdlr = getTrackTypeForHdlr(parseHdlr(((Atom.LeafAtom) Assertions.checkNotNull(containerAtom.getLeafAtomOfType(Atom.TYPE_hdlr))).data));
        if (trackTypeForHdlr == -1) {
            return null;
        }
        TkhdData tkhd = parseTkhd(((Atom.LeafAtom) Assertions.checkNotNull(trak.getLeafAtomOfType(Atom.TYPE_tkhd))).data);
        long jScaleLargeTimestamp = C.TIME_UNSET;
        if (duration == C.TIME_UNSET) {
            leafAtom = mvhd;
            j = tkhd.duration;
        } else {
            leafAtom = mvhd;
            j = duration;
        }
        long mvhd2 = parseMvhd(leafAtom.data);
        if (j != C.TIME_UNSET) {
            jScaleLargeTimestamp = Util.scaleLargeTimestamp(j, 1000000L, mvhd2);
        }
        long j2 = jScaleLargeTimestamp;
        Atom.ContainerAtom containerAtom2 = (Atom.ContainerAtom) Assertions.checkNotNull(((Atom.ContainerAtom) Assertions.checkNotNull(containerAtom.getContainerAtomOfType(Atom.TYPE_minf))).getContainerAtomOfType(Atom.TYPE_stbl));
        Pair<Long, String> mdhd = parseMdhd(((Atom.LeafAtom) Assertions.checkNotNull(containerAtom.getLeafAtomOfType(Atom.TYPE_mdhd))).data);
        StsdData stsd = parseStsd(((Atom.LeafAtom) Assertions.checkNotNull(containerAtom2.getLeafAtomOfType(Atom.TYPE_stsd))).data, tkhd.id, tkhd.rotationDegrees, (String) mdhd.second, drmInitData, isQuickTime);
        if (ignoreEditLists || (containerAtomOfType = trak.getContainerAtomOfType(Atom.TYPE_edts)) == null || (edts = parseEdts(containerAtomOfType)) == null) {
            jArr = null;
            jArr2 = null;
        } else {
            long[] jArr3 = (long[]) edts.first;
            jArr2 = (long[]) edts.second;
            jArr = jArr3;
        }
        if (stsd.format == null) {
            return null;
        }
        return new Track(tkhd.id, trackTypeForHdlr, ((Long) mdhd.first).longValue(), mvhd2, j2, stsd.format, stsd.requiredSampleTransformation, stsd.trackEncryptionBoxes, stsd.nalUnitLengthFieldLength, jArr, jArr2);
    }

    /* JADX WARN: Removed duplicated region for block: B:106:0x024d  */
    /* JADX WARN: Removed duplicated region for block: B:109:0x0255  */
    /* JADX WARN: Removed duplicated region for block: B:110:0x0258  */
    /* JADX WARN: Removed duplicated region for block: B:149:0x03d2  */
    /* JADX WARN: Removed duplicated region for block: B:150:0x03d4  */
    /* JADX WARN: Removed duplicated region for block: B:154:0x03f0  */
    /* JADX WARN: Removed duplicated region for block: B:172:0x0456  */
    /* JADX WARN: Removed duplicated region for block: B:175:0x045b  */
    /* JADX WARN: Removed duplicated region for block: B:176:0x045e  */
    /* JADX WARN: Removed duplicated region for block: B:178:0x0461  */
    /* JADX WARN: Removed duplicated region for block: B:179:0x0464  */
    /* JADX WARN: Removed duplicated region for block: B:181:0x0468  */
    /* JADX WARN: Removed duplicated region for block: B:183:0x046c  */
    /* JADX WARN: Removed duplicated region for block: B:184:0x046f  */
    /* JADX WARN: Removed duplicated region for block: B:188:0x047b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private static com.google.android.exoplayer2.extractor.mp4.TrackSampleTable parseStbl(com.google.android.exoplayer2.extractor.mp4.Track r37, com.google.android.exoplayer2.extractor.mp4.Atom.ContainerAtom r38, com.google.android.exoplayer2.extractor.GaplessInfoHolder r39) throws com.google.android.exoplayer2.ParserException {
        /*
            Method dump skipped, instructions count: 1333
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.extractor.mp4.AtomParsers.parseStbl(com.google.android.exoplayer2.extractor.mp4.Track, com.google.android.exoplayer2.extractor.mp4.Atom$ContainerAtom, com.google.android.exoplayer2.extractor.GaplessInfoHolder):com.google.android.exoplayer2.extractor.mp4.TrackSampleTable");
    }

    private static Metadata parseUdtaMeta(ParsableByteArray meta, int limit) {
        meta.skipBytes(8);
        maybeSkipRemainingMetaAtomHeaderBytes(meta);
        while (meta.getPosition() < limit) {
            int position = meta.getPosition();
            int i = meta.readInt();
            if (meta.readInt() == 1768715124) {
                meta.setPosition(position);
                return parseIlst(meta, position + i);
            }
            meta.setPosition(position + i);
        }
        return null;
    }

    private static Metadata parseIlst(ParsableByteArray ilst, int limit) {
        ilst.skipBytes(8);
        ArrayList arrayList = new ArrayList();
        while (ilst.getPosition() < limit) {
            Metadata.Entry ilstElement = MetadataUtil.parseIlstElement(ilst);
            if (ilstElement != null) {
                arrayList.add(ilstElement);
            }
        }
        if (arrayList.isEmpty()) {
            return null;
        }
        return new Metadata(arrayList);
    }

    private static Metadata parseSmta(ParsableByteArray smta, int limit) {
        smta.skipBytes(12);
        while (smta.getPosition() < limit) {
            int position = smta.getPosition();
            int i = smta.readInt();
            if (smta.readInt() == 1935766900) {
                if (i < 14) {
                    return null;
                }
                smta.skipBytes(5);
                int unsignedByte = smta.readUnsignedByte();
                if (unsignedByte != 12 && unsignedByte != 13) {
                    return null;
                }
                float f = unsignedByte == 12 ? 240.0f : 120.0f;
                smta.skipBytes(1);
                return new Metadata(new SmtaMetadataEntry(f, smta.readUnsignedByte()));
            }
            smta.setPosition(position + i);
        }
        return null;
    }

    private static long parseMvhd(ParsableByteArray mvhd) {
        mvhd.setPosition(8);
        mvhd.skipBytes(Atom.parseFullAtomVersion(mvhd.readInt()) != 0 ? 16 : 8);
        return mvhd.readUnsignedInt();
    }

    private static TkhdData parseTkhd(ParsableByteArray tkhd) {
        boolean z;
        tkhd.setPosition(8);
        int fullAtomVersion = Atom.parseFullAtomVersion(tkhd.readInt());
        tkhd.skipBytes(fullAtomVersion == 0 ? 8 : 16);
        int i = tkhd.readInt();
        tkhd.skipBytes(4);
        int position = tkhd.getPosition();
        int i2 = fullAtomVersion == 0 ? 4 : 8;
        int i3 = 0;
        int i4 = 0;
        while (true) {
            if (i4 >= i2) {
                z = true;
                break;
            }
            if (tkhd.getData()[position + i4] != -1) {
                z = false;
                break;
            }
            i4++;
        }
        long j = C.TIME_UNSET;
        if (z) {
            tkhd.skipBytes(i2);
        } else {
            long unsignedInt = fullAtomVersion == 0 ? tkhd.readUnsignedInt() : tkhd.readUnsignedLongToLong();
            if (unsignedInt != 0) {
                j = unsignedInt;
            }
        }
        tkhd.skipBytes(16);
        int i5 = tkhd.readInt();
        int i6 = tkhd.readInt();
        tkhd.skipBytes(4);
        int i7 = tkhd.readInt();
        int i8 = tkhd.readInt();
        if (i5 == 0 && i6 == 65536 && i7 == -65536 && i8 == 0) {
            i3 = 90;
        } else if (i5 == 0 && i6 == -65536 && i7 == 65536 && i8 == 0) {
            i3 = CircleBarView.THREE_QUARTER;
        } else if (i5 == -65536 && i6 == 0 && i7 == 0 && i8 == -65536) {
            i3 = CircleBarView.TWO_QUARTER;
        }
        return new TkhdData(i, j, i3);
    }

    private static int parseHdlr(ParsableByteArray hdlr) {
        hdlr.setPosition(16);
        return hdlr.readInt();
    }

    private static Pair<Long, String> parseMdhd(ParsableByteArray mdhd) {
        mdhd.setPosition(8);
        int fullAtomVersion = Atom.parseFullAtomVersion(mdhd.readInt());
        mdhd.skipBytes(fullAtomVersion == 0 ? 8 : 16);
        long unsignedInt = mdhd.readUnsignedInt();
        mdhd.skipBytes(fullAtomVersion == 0 ? 4 : 8);
        int unsignedShort = mdhd.readUnsignedShort();
        return Pair.create(Long.valueOf(unsignedInt), new StringBuilder(3).append((char) (((unsignedShort >> 10) & 31) + 96)).append((char) (((unsignedShort >> 5) & 31) + 96)).append((char) ((unsignedShort & 31) + 96)).toString());
    }

    private static StsdData parseStsd(ParsableByteArray stsd, int trackId, int rotationDegrees, String language, DrmInitData drmInitData, boolean isQuickTime) throws ParserException {
        int i;
        stsd.setPosition(12);
        int i2 = stsd.readInt();
        StsdData stsdData = new StsdData(i2);
        for (int i3 = 0; i3 < i2; i3++) {
            int position = stsd.getPosition();
            int i4 = stsd.readInt();
            ExtractorUtil.checkContainerInput(i4 > 0, "childAtomSize must be positive");
            int i5 = stsd.readInt();
            if (i5 == 1635148593 || i5 == 1635148595 || i5 == 1701733238 || i5 == 1831958048 || i5 == 1836070006 || i5 == 1752589105 || i5 == 1751479857 || i5 == 1932670515 || i5 == 1211250227 || i5 == 1987063864 || i5 == 1987063865 || i5 == 1635135537 || i5 == 1685479798 || i5 == 1685479729 || i5 == 1685481573 || i5 == 1685481521) {
                i = position;
                parseVideoSampleEntry(stsd, i5, i, i4, trackId, rotationDegrees, drmInitData, stsdData, i3);
            } else if (i5 == 1836069985 || i5 == 1701733217 || i5 == 1633889587 || i5 == 1700998451 || i5 == 1633889588 || i5 == 1685353315 || i5 == 1685353317 || i5 == 1685353320 || i5 == 1685353324 || i5 == 1685353336 || i5 == 1935764850 || i5 == 1935767394 || i5 == 1819304813 || i5 == 1936684916 || i5 == 1953984371 || i5 == 778924082 || i5 == 778924083 || i5 == 1835557169 || i5 == 1835560241 || i5 == 1634492771 || i5 == 1634492791 || i5 == 1970037111 || i5 == 1332770163 || i5 == 1716281667) {
                i = position;
                parseAudioSampleEntry(stsd, i5, position, i4, trackId, language, isQuickTime, drmInitData, stsdData, i3);
            } else {
                if (i5 == 1414810956 || i5 == 1954034535 || i5 == 2004251764 || i5 == 1937010800 || i5 == 1664495672) {
                    parseTextSampleEntry(stsd, i5, position, i4, trackId, language, stsdData);
                } else if (i5 == 1835365492) {
                    parseMetaDataSampleEntry(stsd, i5, position, trackId, stsdData);
                } else if (i5 == 1667329389) {
                    stsdData.format = new Format.Builder().setId(trackId).setSampleMimeType(MimeTypes.APPLICATION_CAMERA_MOTION).build();
                }
                i = position;
            }
            stsd.setPosition(i + i4);
        }
        return stsdData;
    }

    private static void parseTextSampleEntry(ParsableByteArray parent, int atomType, int position, int atomSize, int trackId, String language, StsdData out) {
        parent.setPosition(position + 8 + 8);
        String str = MimeTypes.APPLICATION_TTML;
        ImmutableList immutableListOf = null;
        long j = Long.MAX_VALUE;
        if (atomType != 1414810956) {
            if (atomType == 1954034535) {
                int i = (atomSize - 8) - 8;
                byte[] bArr = new byte[i];
                parent.readBytes(bArr, 0, i);
                immutableListOf = ImmutableList.of(bArr);
                str = MimeTypes.APPLICATION_TX3G;
            } else if (atomType == 2004251764) {
                str = MimeTypes.APPLICATION_MP4VTT;
            } else if (atomType == 1937010800) {
                j = 0;
            } else if (atomType == 1664495672) {
                out.requiredSampleTransformation = 1;
                str = MimeTypes.APPLICATION_MP4CEA608;
            } else {
                throw new IllegalStateException();
            }
        }
        out.format = new Format.Builder().setId(trackId).setSampleMimeType(str).setLanguage(language).setSubsampleOffsetUs(j).setInitializationData(immutableListOf).build();
    }

    /* JADX WARN: Removed duplicated region for block: B:108:0x019e A[PHI: r3
      0x019e: PHI (r3v34 int) = (r3v33 int), (r3v35 int) binds: [B:102:0x0192, B:105:0x0197] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private static void parseVideoSampleEntry(com.google.android.exoplayer2.util.ParsableByteArray r20, int r21, int r22, int r23, int r24, int r25, com.google.android.exoplayer2.drm.DrmInitData r26, com.google.android.exoplayer2.extractor.mp4.AtomParsers.StsdData r27, int r28) throws com.google.android.exoplayer2.ParserException {
        /*
            Method dump skipped, instructions count: 625
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.extractor.mp4.AtomParsers.parseVideoSampleEntry(com.google.android.exoplayer2.util.ParsableByteArray, int, int, int, int, int, com.google.android.exoplayer2.drm.DrmInitData, com.google.android.exoplayer2.extractor.mp4.AtomParsers$StsdData, int):void");
    }

    private static void parseMetaDataSampleEntry(ParsableByteArray parent, int atomType, int position, int trackId, StsdData out) {
        parent.setPosition(position + 8 + 8);
        if (atomType == 1835365492) {
            parent.readNullTerminatedString();
            String nullTerminatedString = parent.readNullTerminatedString();
            if (nullTerminatedString != null) {
                out.format = new Format.Builder().setId(trackId).setSampleMimeType(nullTerminatedString).build();
            }
        }
    }

    private static Pair<long[], long[]> parseEdts(Atom.ContainerAtom edtsAtom) {
        Atom.LeafAtom leafAtomOfType = edtsAtom.getLeafAtomOfType(Atom.TYPE_elst);
        if (leafAtomOfType == null) {
            return null;
        }
        ParsableByteArray parsableByteArray = leafAtomOfType.data;
        parsableByteArray.setPosition(8);
        int fullAtomVersion = Atom.parseFullAtomVersion(parsableByteArray.readInt());
        int unsignedIntToInt = parsableByteArray.readUnsignedIntToInt();
        long[] jArr = new long[unsignedIntToInt];
        long[] jArr2 = new long[unsignedIntToInt];
        for (int i = 0; i < unsignedIntToInt; i++) {
            jArr[i] = fullAtomVersion == 1 ? parsableByteArray.readUnsignedLongToLong() : parsableByteArray.readUnsignedInt();
            jArr2[i] = fullAtomVersion == 1 ? parsableByteArray.readLong() : parsableByteArray.readInt();
            if (parsableByteArray.readShort() != 1) {
                throw new IllegalArgumentException("Unsupported media rate.");
            }
            parsableByteArray.skipBytes(2);
        }
        return Pair.create(jArr, jArr2);
    }

    private static float parsePaspFromParent(ParsableByteArray parent, int position) {
        parent.setPosition(position + 8);
        return parent.readUnsignedIntToInt() / parent.readUnsignedIntToInt();
    }

    /* JADX WARN: Removed duplicated region for block: B:99:0x0152  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private static void parseAudioSampleEntry(com.google.android.exoplayer2.util.ParsableByteArray r20, int r21, int r22, int r23, int r24, java.lang.String r25, boolean r26, com.google.android.exoplayer2.drm.DrmInitData r27, com.google.android.exoplayer2.extractor.mp4.AtomParsers.StsdData r28, int r29) throws com.google.android.exoplayer2.ParserException {
        /*
            Method dump skipped, instructions count: 772
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.extractor.mp4.AtomParsers.parseAudioSampleEntry(com.google.android.exoplayer2.util.ParsableByteArray, int, int, int, int, java.lang.String, boolean, com.google.android.exoplayer2.drm.DrmInitData, com.google.android.exoplayer2.extractor.mp4.AtomParsers$StsdData, int):void");
    }

    private static int findEsdsPosition(ParsableByteArray parent, int position, int size) throws ParserException {
        int position2 = parent.getPosition();
        while (position2 - position < size) {
            parent.setPosition(position2);
            int i = parent.readInt();
            ExtractorUtil.checkContainerInput(i > 0, "childAtomSize must be positive");
            if (parent.readInt() == 1702061171) {
                return position2;
            }
            position2 += i;
        }
        return -1;
    }

    private static Pair<String, byte[]> parseEsdsFromParent(ParsableByteArray parent, int position) {
        parent.setPosition(position + 8 + 4);
        parent.skipBytes(1);
        parseExpandableClassSize(parent);
        parent.skipBytes(2);
        int unsignedByte = parent.readUnsignedByte();
        if ((unsignedByte & 128) != 0) {
            parent.skipBytes(2);
        }
        if ((unsignedByte & 64) != 0) {
            parent.skipBytes(parent.readUnsignedShort());
        }
        if ((unsignedByte & 32) != 0) {
            parent.skipBytes(2);
        }
        parent.skipBytes(1);
        parseExpandableClassSize(parent);
        String mimeTypeFromMp4ObjectType = MimeTypes.getMimeTypeFromMp4ObjectType(parent.readUnsignedByte());
        if (MimeTypes.AUDIO_MPEG.equals(mimeTypeFromMp4ObjectType) || MimeTypes.AUDIO_DTS.equals(mimeTypeFromMp4ObjectType) || MimeTypes.AUDIO_DTS_HD.equals(mimeTypeFromMp4ObjectType)) {
            return Pair.create(mimeTypeFromMp4ObjectType, null);
        }
        parent.skipBytes(12);
        parent.skipBytes(1);
        int expandableClassSize = parseExpandableClassSize(parent);
        byte[] bArr = new byte[expandableClassSize];
        parent.readBytes(bArr, 0, expandableClassSize);
        return Pair.create(mimeTypeFromMp4ObjectType, bArr);
    }

    private static Pair<Integer, TrackEncryptionBox> parseSampleEntryEncryptionData(ParsableByteArray parent, int position, int size) throws ParserException {
        Pair<Integer, TrackEncryptionBox> commonEncryptionSinfFromParent;
        int position2 = parent.getPosition();
        while (position2 - position < size) {
            parent.setPosition(position2);
            int i = parent.readInt();
            ExtractorUtil.checkContainerInput(i > 0, "childAtomSize must be positive");
            if (parent.readInt() == 1936289382 && (commonEncryptionSinfFromParent = parseCommonEncryptionSinfFromParent(parent, position2, i)) != null) {
                return commonEncryptionSinfFromParent;
            }
            position2 += i;
        }
        return null;
    }

    static Pair<Integer, TrackEncryptionBox> parseCommonEncryptionSinfFromParent(ParsableByteArray parent, int position, int size) throws ParserException {
        int i = position + 8;
        int i2 = -1;
        int i3 = 0;
        String string = null;
        Integer numValueOf = null;
        while (i - position < size) {
            parent.setPosition(i);
            int i4 = parent.readInt();
            int i5 = parent.readInt();
            if (i5 == 1718775137) {
                numValueOf = Integer.valueOf(parent.readInt());
            } else if (i5 == 1935894637) {
                parent.skipBytes(4);
                string = parent.readString(4);
            } else if (i5 == 1935894633) {
                i2 = i;
                i3 = i4;
            }
            i += i4;
        }
        if (!C.CENC_TYPE_cenc.equals(string) && !C.CENC_TYPE_cbc1.equals(string) && !C.CENC_TYPE_cens.equals(string) && !C.CENC_TYPE_cbcs.equals(string)) {
            return null;
        }
        ExtractorUtil.checkContainerInput(numValueOf != null, "frma atom is mandatory");
        ExtractorUtil.checkContainerInput(i2 != -1, "schi atom is mandatory");
        TrackEncryptionBox schiFromParent = parseSchiFromParent(parent, i2, i3, string);
        ExtractorUtil.checkContainerInput(schiFromParent != null, "tenc atom is mandatory");
        return Pair.create(numValueOf, (TrackEncryptionBox) Util.castNonNull(schiFromParent));
    }

    private static TrackEncryptionBox parseSchiFromParent(ParsableByteArray parent, int position, int size, String schemeType) {
        int i;
        int i2;
        int i3 = position + 8;
        while (true) {
            byte[] bArr = null;
            if (i3 - position >= size) {
                return null;
            }
            parent.setPosition(i3);
            int i4 = parent.readInt();
            if (parent.readInt() == 1952804451) {
                int fullAtomVersion = Atom.parseFullAtomVersion(parent.readInt());
                parent.skipBytes(1);
                if (fullAtomVersion == 0) {
                    parent.skipBytes(1);
                    i2 = 0;
                    i = 0;
                } else {
                    int unsignedByte = parent.readUnsignedByte();
                    i = unsignedByte & 15;
                    i2 = (unsignedByte & PsExtractor.VIDEO_STREAM_MASK) >> 4;
                }
                boolean z = parent.readUnsignedByte() == 1;
                int unsignedByte2 = parent.readUnsignedByte();
                byte[] bArr2 = new byte[16];
                parent.readBytes(bArr2, 0, 16);
                if (z && unsignedByte2 == 0) {
                    int unsignedByte3 = parent.readUnsignedByte();
                    bArr = new byte[unsignedByte3];
                    parent.readBytes(bArr, 0, unsignedByte3);
                }
                return new TrackEncryptionBox(z, schemeType, unsignedByte2, bArr2, i2, i, bArr);
            }
            i3 += i4;
        }
    }

    private static byte[] parseProjFromParent(ParsableByteArray parent, int position, int size) {
        int i = position + 8;
        while (i - position < size) {
            parent.setPosition(i);
            int i2 = parent.readInt();
            if (parent.readInt() == 1886547818) {
                return Arrays.copyOfRange(parent.getData(), i, i2 + i);
            }
            i += i2;
        }
        return null;
    }

    private static int parseExpandableClassSize(ParsableByteArray data) {
        int unsignedByte = data.readUnsignedByte();
        int i = unsignedByte & 127;
        while ((unsignedByte & 128) == 128) {
            unsignedByte = data.readUnsignedByte();
            i = (i << 7) | (unsignedByte & 127);
        }
        return i;
    }

    private static boolean canApplyEditWithGaplessInfo(long[] timestamps, long duration, long editStartTime, long editEndTime) {
        int length = timestamps.length - 1;
        return timestamps[0] <= editStartTime && editStartTime < timestamps[Util.constrainValue(4, 0, length)] && timestamps[Util.constrainValue(timestamps.length - 4, 0, length)] < editEndTime && editEndTime <= duration;
    }

    private AtomParsers() {
    }

    private static final class ChunkIterator {
        private final ParsableByteArray chunkOffsets;
        private final boolean chunkOffsetsAreLongs;
        public int index;
        public final int length;
        private int nextSamplesPerChunkChangeIndex;
        public int numSamples;
        public long offset;
        private int remainingSamplesPerChunkChanges;
        private final ParsableByteArray stsc;

        public ChunkIterator(ParsableByteArray stsc, ParsableByteArray chunkOffsets, boolean chunkOffsetsAreLongs) throws ParserException {
            this.stsc = stsc;
            this.chunkOffsets = chunkOffsets;
            this.chunkOffsetsAreLongs = chunkOffsetsAreLongs;
            chunkOffsets.setPosition(12);
            this.length = chunkOffsets.readUnsignedIntToInt();
            stsc.setPosition(12);
            this.remainingSamplesPerChunkChanges = stsc.readUnsignedIntToInt();
            ExtractorUtil.checkContainerInput(stsc.readInt() == 1, "first_chunk must be 1");
            this.index = -1;
        }

        public boolean moveNext() {
            long unsignedInt;
            int i = this.index + 1;
            this.index = i;
            if (i == this.length) {
                return false;
            }
            if (this.chunkOffsetsAreLongs) {
                unsignedInt = this.chunkOffsets.readUnsignedLongToLong();
            } else {
                unsignedInt = this.chunkOffsets.readUnsignedInt();
            }
            this.offset = unsignedInt;
            if (this.index == this.nextSamplesPerChunkChangeIndex) {
                this.numSamples = this.stsc.readUnsignedIntToInt();
                this.stsc.skipBytes(4);
                int i2 = this.remainingSamplesPerChunkChanges - 1;
                this.remainingSamplesPerChunkChanges = i2;
                this.nextSamplesPerChunkChangeIndex = i2 > 0 ? this.stsc.readUnsignedIntToInt() - 1 : -1;
            }
            return true;
        }
    }

    private static final class TkhdData {
        private final long duration;
        private final int id;
        private final int rotationDegrees;

        public TkhdData(int id, long duration, int rotationDegrees) {
            this.id = id;
            this.duration = duration;
            this.rotationDegrees = rotationDegrees;
        }
    }

    private static final class StsdData {
        public static final int STSD_HEADER_SIZE = 8;
        public Format format;
        public int nalUnitLengthFieldLength;
        public int requiredSampleTransformation = 0;
        public final TrackEncryptionBox[] trackEncryptionBoxes;

        public StsdData(int numberOfEntries) {
            this.trackEncryptionBoxes = new TrackEncryptionBox[numberOfEntries];
        }
    }

    static final class StszSampleSizeBox implements SampleSizeBox {
        private final ParsableByteArray data;
        private final int fixedSampleSize;
        private final int sampleCount;

        public StszSampleSizeBox(Atom.LeafAtom stszAtom, Format trackFormat) {
            ParsableByteArray parsableByteArray = stszAtom.data;
            this.data = parsableByteArray;
            parsableByteArray.setPosition(12);
            int unsignedIntToInt = parsableByteArray.readUnsignedIntToInt();
            if (MimeTypes.AUDIO_RAW.equals(trackFormat.sampleMimeType)) {
                int pcmFrameSize = Util.getPcmFrameSize(trackFormat.pcmEncoding, trackFormat.channelCount);
                if (unsignedIntToInt == 0 || unsignedIntToInt % pcmFrameSize != 0) {
                    Log.w(AtomParsers.TAG, new StringBuilder(88).append("Audio sample size mismatch. stsd sample size: ").append(pcmFrameSize).append(", stsz sample size: ").append(unsignedIntToInt).toString());
                    unsignedIntToInt = pcmFrameSize;
                }
            }
            this.fixedSampleSize = unsignedIntToInt == 0 ? -1 : unsignedIntToInt;
            this.sampleCount = parsableByteArray.readUnsignedIntToInt();
        }

        @Override // com.google.android.exoplayer2.extractor.mp4.AtomParsers.SampleSizeBox
        public int getSampleCount() {
            return this.sampleCount;
        }

        @Override // com.google.android.exoplayer2.extractor.mp4.AtomParsers.SampleSizeBox
        public int getFixedSampleSize() {
            return this.fixedSampleSize;
        }

        @Override // com.google.android.exoplayer2.extractor.mp4.AtomParsers.SampleSizeBox
        public int readNextSampleSize() {
            int i = this.fixedSampleSize;
            return i == -1 ? this.data.readUnsignedIntToInt() : i;
        }
    }

    static final class Stz2SampleSizeBox implements SampleSizeBox {
        private int currentByte;
        private final ParsableByteArray data;
        private final int fieldSize;
        private final int sampleCount;
        private int sampleIndex;

        @Override // com.google.android.exoplayer2.extractor.mp4.AtomParsers.SampleSizeBox
        public int getFixedSampleSize() {
            return -1;
        }

        public Stz2SampleSizeBox(Atom.LeafAtom stz2Atom) {
            ParsableByteArray parsableByteArray = stz2Atom.data;
            this.data = parsableByteArray;
            parsableByteArray.setPosition(12);
            this.fieldSize = parsableByteArray.readUnsignedIntToInt() & 255;
            this.sampleCount = parsableByteArray.readUnsignedIntToInt();
        }

        @Override // com.google.android.exoplayer2.extractor.mp4.AtomParsers.SampleSizeBox
        public int getSampleCount() {
            return this.sampleCount;
        }

        @Override // com.google.android.exoplayer2.extractor.mp4.AtomParsers.SampleSizeBox
        public int readNextSampleSize() {
            int i = this.fieldSize;
            if (i == 8) {
                return this.data.readUnsignedByte();
            }
            if (i == 16) {
                return this.data.readUnsignedShort();
            }
            int i2 = this.sampleIndex;
            this.sampleIndex = i2 + 1;
            if (i2 % 2 == 0) {
                int unsignedByte = this.data.readUnsignedByte();
                this.currentByte = unsignedByte;
                return (unsignedByte & PsExtractor.VIDEO_STREAM_MASK) >> 4;
            }
            return this.currentByte & 15;
        }
    }
}
