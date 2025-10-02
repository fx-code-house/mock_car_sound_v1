package com.google.android.exoplayer2.extractor.mp4;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.extractor.GaplessInfoHolder;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.metadata.id3.ApicFrame;
import com.google.android.exoplayer2.metadata.id3.CommentFrame;
import com.google.android.exoplayer2.metadata.id3.Id3Frame;
import com.google.android.exoplayer2.metadata.id3.InternalFrame;
import com.google.android.exoplayer2.metadata.id3.TextInformationFrame;
import com.google.android.exoplayer2.metadata.mp4.MdtaMetadataEntry;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.common.net.HttpHeaders;

/* loaded from: classes.dex */
final class MetadataUtil {
    private static final int PICTURE_TYPE_FRONT_COVER = 3;
    private static final int SHORT_TYPE_ALBUM = 6384738;
    private static final int SHORT_TYPE_ARTIST = 4280916;
    private static final int SHORT_TYPE_COMMENT = 6516084;
    private static final int SHORT_TYPE_COMPOSER_1 = 6516589;
    private static final int SHORT_TYPE_COMPOSER_2 = 7828084;
    private static final int SHORT_TYPE_ENCODER = 7630703;
    private static final int SHORT_TYPE_GENRE = 6776174;
    private static final int SHORT_TYPE_LYRICS = 7108978;
    private static final int SHORT_TYPE_NAME_1 = 7233901;
    private static final int SHORT_TYPE_NAME_2 = 7631467;
    private static final int SHORT_TYPE_YEAR = 6578553;
    static final String[] STANDARD_GENRES = {"Blues", "Classic Rock", "Country", "Dance", "Disco", "Funk", "Grunge", "Hip-Hop", "Jazz", "Metal", "New Age", "Oldies", "Other", "Pop", "R&B", "Rap", "Reggae", "Rock", "Techno", "Industrial", "Alternative", "Ska", "Death Metal", "Pranks", "Soundtrack", "Euro-Techno", "Ambient", "Trip-Hop", "Vocal", "Jazz+Funk", "Fusion", "Trance", "Classical", "Instrumental", "Acid", "House", "Game", "Sound Clip", "Gospel", "Noise", "AlternRock", "Bass", "Soul", "Punk", "Space", "Meditative", "Instrumental Pop", "Instrumental Rock", "Ethnic", "Gothic", "Darkwave", "Techno-Industrial", "Electronic", "Pop-Folk", "Eurodance", "Dream", "Southern Rock", "Comedy", "Cult", "Gangsta", "Top 40", "Christian Rap", "Pop/Funk", "Jungle", "Native American", "Cabaret", "New Wave", "Psychadelic", "Rave", "Showtunes", HttpHeaders.TRAILER, "Lo-Fi", "Tribal", "Acid Punk", "Acid Jazz", "Polka", "Retro", "Musical", "Rock & Roll", "Hard Rock", "Folk", "Folk-Rock", "National Folk", "Swing", "Fast Fusion", "Bebob", "Latin", "Revival", "Celtic", "Bluegrass", "Avantgarde", "Gothic Rock", "Progressive Rock", "Psychedelic Rock", "Symphonic Rock", "Slow Rock", "Big Band", "Chorus", "Easy Listening", "Acoustic", "Humour", "Speech", "Chanson", "Opera", "Chamber Music", "Sonata", "Symphony", "Booty Bass", "Primus", "Porn Groove", "Satire", "Slow Jam", "Club", "Tango", "Samba", "Folklore", "Ballad", "Power Ballad", "Rhythmic Soul", "Freestyle", "Duet", "Punk Rock", "Drum Solo", "A capella", "Euro-House", "Dance Hall", "Goa", "Drum & Bass", "Club-House", "Hardcore", "Terror", "Indie", "BritPop", "Afro-Punk", "Polsk Punk", "Beat", "Christian Gangsta Rap", "Heavy Metal", "Black Metal", "Crossover", "Contemporary Christian", "Christian Rock", "Merengue", "Salsa", "Thrash Metal", "Anime", "Jpop", "Synthpop", "Abstract", "Art Rock", "Baroque", "Bhangra", "Big beat", "Breakbeat", "Chillout", "Downtempo", "Dub", "EBM", "Eclectic", "Electro", "Electroclash", "Emo", "Experimental", "Garage", "Global", "IDM", "Illbient", "Industro-Goth", "Jam Band", "Krautrock", "Leftfield", "Lounge", "Math Rock", "New Romantic", "Nu-Breakz", "Post-Punk", "Post-Rock", "Psytrance", "Shoegaze", "Space Rock", "Trop Rock", "World Music", "Neoclassical", "Audiobook", "Audio theatre", "Neue Deutsche Welle", "Podcast", "Indie-Rock", "G-Funk", "Dubstep", "Garage Rock", "Psybient"};
    private static final String TAG = "MetadataUtil";
    private static final int TYPE_ALBUM_ARTIST = 1631670868;
    private static final int TYPE_COMPILATION = 1668311404;
    private static final int TYPE_COVER_ART = 1668249202;
    private static final int TYPE_DISK_NUMBER = 1684632427;
    private static final int TYPE_GAPLESS_ALBUM = 1885823344;
    private static final int TYPE_GENRE = 1735291493;
    private static final int TYPE_GROUPING = 6779504;
    private static final int TYPE_INTERNAL = 757935405;
    private static final int TYPE_RATING = 1920233063;
    private static final int TYPE_SORT_ALBUM = 1936679276;
    private static final int TYPE_SORT_ALBUM_ARTIST = 1936679265;
    private static final int TYPE_SORT_ARTIST = 1936679282;
    private static final int TYPE_SORT_COMPOSER = 1936679791;
    private static final int TYPE_SORT_TRACK_NAME = 1936682605;
    private static final int TYPE_TEMPO = 1953329263;
    private static final int TYPE_TOP_BYTE_COPYRIGHT = 169;
    private static final int TYPE_TOP_BYTE_REPLACEMENT = 253;
    private static final int TYPE_TRACK_NUMBER = 1953655662;
    private static final int TYPE_TV_SHOW = 1953919848;
    private static final int TYPE_TV_SORT_SHOW = 1936683886;

    private MetadataUtil() {
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x003c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static void setFormatMetadata(int r5, com.google.android.exoplayer2.metadata.Metadata r6, com.google.android.exoplayer2.metadata.Metadata r7, com.google.android.exoplayer2.Format.Builder r8, com.google.android.exoplayer2.metadata.Metadata... r9) {
        /*
            com.google.android.exoplayer2.metadata.Metadata r0 = new com.google.android.exoplayer2.metadata.Metadata
            r1 = 0
            com.google.android.exoplayer2.metadata.Metadata$Entry[] r2 = new com.google.android.exoplayer2.metadata.Metadata.Entry[r1]
            r0.<init>(r2)
            r2 = 1
            if (r5 != r2) goto Le
            if (r6 == 0) goto L3c
            goto L3d
        Le:
            r6 = 2
            if (r5 != r6) goto L3c
            if (r7 == 0) goto L3c
            r5 = r1
        L14:
            int r6 = r7.length()
            if (r5 >= r6) goto L3c
            com.google.android.exoplayer2.metadata.Metadata$Entry r6 = r7.get(r5)
            boolean r3 = r6 instanceof com.google.android.exoplayer2.metadata.mp4.MdtaMetadataEntry
            if (r3 == 0) goto L39
            com.google.android.exoplayer2.metadata.mp4.MdtaMetadataEntry r6 = (com.google.android.exoplayer2.metadata.mp4.MdtaMetadataEntry) r6
            java.lang.String r3 = "com.android.capture.fps"
            java.lang.String r4 = r6.key
            boolean r3 = r3.equals(r4)
            if (r3 == 0) goto L39
            com.google.android.exoplayer2.metadata.Metadata r5 = new com.google.android.exoplayer2.metadata.Metadata
            com.google.android.exoplayer2.metadata.Metadata$Entry[] r7 = new com.google.android.exoplayer2.metadata.Metadata.Entry[r2]
            r7[r1] = r6
            r5.<init>(r7)
            r6 = r5
            goto L3d
        L39:
            int r5 = r5 + 1
            goto L14
        L3c:
            r6 = r0
        L3d:
            int r5 = r9.length
        L3e:
            if (r1 >= r5) goto L49
            r7 = r9[r1]
            com.google.android.exoplayer2.metadata.Metadata r6 = r6.copyWithAppendedEntriesFrom(r7)
            int r1 = r1 + 1
            goto L3e
        L49:
            int r5 = r6.length()
            if (r5 <= 0) goto L52
            r8.setMetadata(r6)
        L52:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.extractor.mp4.MetadataUtil.setFormatMetadata(int, com.google.android.exoplayer2.metadata.Metadata, com.google.android.exoplayer2.metadata.Metadata, com.google.android.exoplayer2.Format$Builder, com.google.android.exoplayer2.metadata.Metadata[]):void");
    }

    public static void setFormatGaplessInfo(int trackType, GaplessInfoHolder gaplessInfoHolder, Format.Builder formatBuilder) {
        if (trackType == 1 && gaplessInfoHolder.hasGaplessInfo()) {
            formatBuilder.setEncoderDelay(gaplessInfoHolder.encoderDelay).setEncoderPadding(gaplessInfoHolder.encoderPadding);
        }
    }

    public static Metadata.Entry parseIlstElement(ParsableByteArray ilst) {
        int position = ilst.getPosition() + ilst.readInt();
        int i = ilst.readInt();
        int i2 = (i >> 24) & 255;
        try {
            if (i2 == TYPE_TOP_BYTE_COPYRIGHT || i2 == TYPE_TOP_BYTE_REPLACEMENT) {
                int i3 = 16777215 & i;
                if (i3 == SHORT_TYPE_COMMENT) {
                    return parseCommentAttribute(i, ilst);
                }
                if (i3 == SHORT_TYPE_NAME_1 || i3 == SHORT_TYPE_NAME_2) {
                    return parseTextAttribute(i, "TIT2", ilst);
                }
                if (i3 == SHORT_TYPE_COMPOSER_1 || i3 == SHORT_TYPE_COMPOSER_2) {
                    return parseTextAttribute(i, "TCOM", ilst);
                }
                if (i3 == SHORT_TYPE_YEAR) {
                    return parseTextAttribute(i, "TDRC", ilst);
                }
                if (i3 == SHORT_TYPE_ARTIST) {
                    return parseTextAttribute(i, "TPE1", ilst);
                }
                if (i3 == SHORT_TYPE_ENCODER) {
                    return parseTextAttribute(i, "TSSE", ilst);
                }
                if (i3 == SHORT_TYPE_ALBUM) {
                    return parseTextAttribute(i, "TALB", ilst);
                }
                if (i3 == SHORT_TYPE_LYRICS) {
                    return parseTextAttribute(i, "USLT", ilst);
                }
                if (i3 == SHORT_TYPE_GENRE) {
                    return parseTextAttribute(i, "TCON", ilst);
                }
                if (i3 == TYPE_GROUPING) {
                    return parseTextAttribute(i, "TIT1", ilst);
                }
            } else {
                if (i == TYPE_GENRE) {
                    return parseStandardGenreAttribute(ilst);
                }
                if (i == TYPE_DISK_NUMBER) {
                    return parseIndexAndCountAttribute(i, "TPOS", ilst);
                }
                if (i == TYPE_TRACK_NUMBER) {
                    return parseIndexAndCountAttribute(i, "TRCK", ilst);
                }
                if (i == TYPE_TEMPO) {
                    return parseUint8Attribute(i, "TBPM", ilst, true, false);
                }
                if (i == TYPE_COMPILATION) {
                    return parseUint8Attribute(i, "TCMP", ilst, true, true);
                }
                if (i == TYPE_COVER_ART) {
                    return parseCoverArt(ilst);
                }
                if (i == TYPE_ALBUM_ARTIST) {
                    return parseTextAttribute(i, "TPE2", ilst);
                }
                if (i == TYPE_SORT_TRACK_NAME) {
                    return parseTextAttribute(i, "TSOT", ilst);
                }
                if (i == TYPE_SORT_ALBUM) {
                    return parseTextAttribute(i, "TSO2", ilst);
                }
                if (i == TYPE_SORT_ARTIST) {
                    return parseTextAttribute(i, "TSOA", ilst);
                }
                if (i == TYPE_SORT_ALBUM_ARTIST) {
                    return parseTextAttribute(i, "TSOP", ilst);
                }
                if (i == TYPE_SORT_COMPOSER) {
                    return parseTextAttribute(i, "TSOC", ilst);
                }
                if (i == TYPE_RATING) {
                    return parseUint8Attribute(i, "ITUNESADVISORY", ilst, false, false);
                }
                if (i == TYPE_GAPLESS_ALBUM) {
                    return parseUint8Attribute(i, "ITUNESGAPLESS", ilst, false, true);
                }
                if (i == TYPE_TV_SORT_SHOW) {
                    return parseTextAttribute(i, "TVSHOWSORT", ilst);
                }
                if (i == TYPE_TV_SHOW) {
                    return parseTextAttribute(i, "TVSHOW", ilst);
                }
                if (i == TYPE_INTERNAL) {
                    return parseInternalAttribute(ilst, position);
                }
            }
            String strValueOf = String.valueOf(Atom.getAtomTypeString(i));
            Log.d(TAG, strValueOf.length() != 0 ? "Skipped unknown metadata entry: ".concat(strValueOf) : new String("Skipped unknown metadata entry: "));
            ilst.setPosition(position);
            return null;
        } finally {
            ilst.setPosition(position);
        }
    }

    public static MdtaMetadataEntry parseMdtaMetadataEntryFromIlst(ParsableByteArray ilst, int endPosition, String key) {
        while (true) {
            int position = ilst.getPosition();
            if (position >= endPosition) {
                return null;
            }
            int i = ilst.readInt();
            if (ilst.readInt() == 1684108385) {
                int i2 = ilst.readInt();
                int i3 = ilst.readInt();
                int i4 = i - 16;
                byte[] bArr = new byte[i4];
                ilst.readBytes(bArr, 0, i4);
                return new MdtaMetadataEntry(key, bArr, i3, i2);
            }
            ilst.setPosition(position + i);
        }
    }

    private static TextInformationFrame parseTextAttribute(int type, String id, ParsableByteArray data) {
        int i = data.readInt();
        if (data.readInt() == 1684108385) {
            data.skipBytes(8);
            return new TextInformationFrame(id, null, data.readNullTerminatedString(i - 16));
        }
        String strValueOf = String.valueOf(Atom.getAtomTypeString(type));
        Log.w(TAG, strValueOf.length() != 0 ? "Failed to parse text attribute: ".concat(strValueOf) : new String("Failed to parse text attribute: "));
        return null;
    }

    private static CommentFrame parseCommentAttribute(int type, ParsableByteArray data) {
        int i = data.readInt();
        if (data.readInt() == 1684108385) {
            data.skipBytes(8);
            String nullTerminatedString = data.readNullTerminatedString(i - 16);
            return new CommentFrame(C.LANGUAGE_UNDETERMINED, nullTerminatedString, nullTerminatedString);
        }
        String strValueOf = String.valueOf(Atom.getAtomTypeString(type));
        Log.w(TAG, strValueOf.length() != 0 ? "Failed to parse comment attribute: ".concat(strValueOf) : new String("Failed to parse comment attribute: "));
        return null;
    }

    private static Id3Frame parseUint8Attribute(int type, String id, ParsableByteArray data, boolean isTextInformationFrame, boolean isBoolean) {
        int uint8AttributeValue = parseUint8AttributeValue(data);
        if (isBoolean) {
            uint8AttributeValue = Math.min(1, uint8AttributeValue);
        }
        if (uint8AttributeValue >= 0) {
            if (isTextInformationFrame) {
                return new TextInformationFrame(id, null, Integer.toString(uint8AttributeValue));
            }
            return new CommentFrame(C.LANGUAGE_UNDETERMINED, id, Integer.toString(uint8AttributeValue));
        }
        String strValueOf = String.valueOf(Atom.getAtomTypeString(type));
        Log.w(TAG, strValueOf.length() != 0 ? "Failed to parse uint8 attribute: ".concat(strValueOf) : new String("Failed to parse uint8 attribute: "));
        return null;
    }

    private static TextInformationFrame parseIndexAndCountAttribute(int type, String attributeName, ParsableByteArray data) {
        int i = data.readInt();
        if (data.readInt() == 1684108385 && i >= 22) {
            data.skipBytes(10);
            int unsignedShort = data.readUnsignedShort();
            if (unsignedShort > 0) {
                String string = new StringBuilder(11).append(unsignedShort).toString();
                int unsignedShort2 = data.readUnsignedShort();
                if (unsignedShort2 > 0) {
                    String strValueOf = String.valueOf(string);
                    string = new StringBuilder(String.valueOf(strValueOf).length() + 12).append(strValueOf).append("/").append(unsignedShort2).toString();
                }
                return new TextInformationFrame(attributeName, null, string);
            }
        }
        String strValueOf2 = String.valueOf(Atom.getAtomTypeString(type));
        Log.w(TAG, strValueOf2.length() != 0 ? "Failed to parse index/count attribute: ".concat(strValueOf2) : new String("Failed to parse index/count attribute: "));
        return null;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0011  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private static com.google.android.exoplayer2.metadata.id3.TextInformationFrame parseStandardGenreAttribute(com.google.android.exoplayer2.util.ParsableByteArray r3) {
        /*
            int r3 = parseUint8AttributeValue(r3)
            r0 = 0
            if (r3 <= 0) goto L11
            java.lang.String[] r1 = com.google.android.exoplayer2.extractor.mp4.MetadataUtil.STANDARD_GENRES
            int r2 = r1.length
            if (r3 > r2) goto L11
            int r3 = r3 + (-1)
            r3 = r1[r3]
            goto L12
        L11:
            r3 = r0
        L12:
            if (r3 == 0) goto L1c
            com.google.android.exoplayer2.metadata.id3.TextInformationFrame r1 = new com.google.android.exoplayer2.metadata.id3.TextInformationFrame
            java.lang.String r2 = "TCON"
            r1.<init>(r2, r0, r3)
            return r1
        L1c:
            java.lang.String r3 = "MetadataUtil"
            java.lang.String r1 = "Failed to parse standard genre code"
            com.google.android.exoplayer2.util.Log.w(r3, r1)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.extractor.mp4.MetadataUtil.parseStandardGenreAttribute(com.google.android.exoplayer2.util.ParsableByteArray):com.google.android.exoplayer2.metadata.id3.TextInformationFrame");
    }

    private static ApicFrame parseCoverArt(ParsableByteArray data) {
        int i = data.readInt();
        if (data.readInt() == 1684108385) {
            int fullAtomFlags = Atom.parseFullAtomFlags(data.readInt());
            String str = fullAtomFlags == 13 ? MimeTypes.IMAGE_JPEG : fullAtomFlags == 14 ? "image/png" : null;
            if (str == null) {
                Log.w(TAG, new StringBuilder(41).append("Unrecognized cover art flags: ").append(fullAtomFlags).toString());
                return null;
            }
            data.skipBytes(4);
            int i2 = i - 16;
            byte[] bArr = new byte[i2];
            data.readBytes(bArr, 0, i2);
            return new ApicFrame(str, null, 3, bArr);
        }
        Log.w(TAG, "Failed to parse cover art attribute");
        return null;
    }

    private static Id3Frame parseInternalAttribute(ParsableByteArray data, int endPosition) {
        String nullTerminatedString = null;
        String nullTerminatedString2 = null;
        int i = -1;
        int i2 = -1;
        while (data.getPosition() < endPosition) {
            int position = data.getPosition();
            int i3 = data.readInt();
            int i4 = data.readInt();
            data.skipBytes(4);
            if (i4 == 1835360622) {
                nullTerminatedString = data.readNullTerminatedString(i3 - 12);
            } else if (i4 == 1851878757) {
                nullTerminatedString2 = data.readNullTerminatedString(i3 - 12);
            } else {
                if (i4 == 1684108385) {
                    i = position;
                    i2 = i3;
                }
                data.skipBytes(i3 - 12);
            }
        }
        if (nullTerminatedString == null || nullTerminatedString2 == null || i == -1) {
            return null;
        }
        data.setPosition(i);
        data.skipBytes(16);
        return new InternalFrame(nullTerminatedString, nullTerminatedString2, data.readNullTerminatedString(i2 - 16));
    }

    private static int parseUint8AttributeValue(ParsableByteArray data) {
        data.skipBytes(4);
        if (data.readInt() == 1684108385) {
            data.skipBytes(8);
            return data.readUnsignedByte();
        }
        Log.w(TAG, "Failed to parse uint8 attribute value");
        return -1;
    }
}
