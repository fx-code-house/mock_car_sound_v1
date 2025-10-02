package com.google.android.exoplayer2.extractor.mp4;

import android.util.Pair;
import android.util.SparseArray;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.audio.Ac4Util;
import com.google.android.exoplayer2.drm.DrmInitData;
import com.google.android.exoplayer2.extractor.CeaUtil;
import com.google.android.exoplayer2.extractor.ChunkIndex;
import com.google.android.exoplayer2.extractor.Extractor;
import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.extractor.GaplessInfoHolder;
import com.google.android.exoplayer2.extractor.PositionHolder;
import com.google.android.exoplayer2.extractor.SeekMap;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.extractor.mp4.Atom;
import com.google.android.exoplayer2.extractor.ts.PsExtractor;
import com.google.android.exoplayer2.metadata.emsg.EventMessage;
import com.google.android.exoplayer2.metadata.emsg.EventMessageEncoder;
import com.google.android.exoplayer2.upstream.DataReader;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.NalUnitUtil;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.TimestampAdjuster;
import com.google.android.exoplayer2.util.Util;
import com.google.common.base.Ascii;
import com.google.common.base.Function;
import java.io.IOException;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/* loaded from: classes.dex */
public class FragmentedMp4Extractor implements Extractor {
    private static final int EXTRA_TRACKS_BASE_ID = 100;
    public static final int FLAG_ENABLE_EMSG_TRACK = 4;
    public static final int FLAG_WORKAROUND_EVERY_VIDEO_FRAME_IS_SYNC_FRAME = 1;
    public static final int FLAG_WORKAROUND_IGNORE_EDIT_LISTS = 16;
    public static final int FLAG_WORKAROUND_IGNORE_TFDT_BOX = 2;
    private static final int SAMPLE_GROUP_TYPE_seig = 1936025959;
    private static final int STATE_READING_ATOM_HEADER = 0;
    private static final int STATE_READING_ATOM_PAYLOAD = 1;
    private static final int STATE_READING_ENCRYPTION_DATA = 2;
    private static final int STATE_READING_SAMPLE_CONTINUE = 4;
    private static final int STATE_READING_SAMPLE_START = 3;
    private static final String TAG = "FragmentedMp4Extractor";
    private final TrackOutput additionalEmsgTrackOutput;
    private ParsableByteArray atomData;
    private final ParsableByteArray atomHeader;
    private int atomHeaderBytesRead;
    private long atomSize;
    private int atomType;
    private TrackOutput[] ceaTrackOutputs;
    private final List<Format> closedCaptionFormats;
    private final ArrayDeque<Atom.ContainerAtom> containerAtoms;
    private TrackBundle currentTrackBundle;
    private long durationUs;
    private TrackOutput[] emsgTrackOutputs;
    private long endOfMdatPosition;
    private final EventMessageEncoder eventMessageEncoder;
    private ExtractorOutput extractorOutput;
    private final int flags;
    private boolean haveOutputSeekMap;
    private final ParsableByteArray nalBuffer;
    private final ParsableByteArray nalPrefix;
    private final ParsableByteArray nalStartCode;
    private int parserState;
    private int pendingMetadataSampleBytes;
    private final ArrayDeque<MetadataSampleInfo> pendingMetadataSampleInfos;
    private long pendingSeekTimeUs;
    private boolean processSeiNalUnitPayload;
    private int sampleBytesWritten;
    private int sampleCurrentNalBytesRemaining;
    private int sampleSize;
    private final ParsableByteArray scratch;
    private final byte[] scratchBytes;
    private long segmentIndexEarliestPresentationTimeUs;
    private final Track sideloadedTrack;
    private final TimestampAdjuster timestampAdjuster;
    private final SparseArray<TrackBundle> trackBundles;
    public static final ExtractorsFactory FACTORY = new ExtractorsFactory() { // from class: com.google.android.exoplayer2.extractor.mp4.FragmentedMp4Extractor$$ExternalSyntheticLambda1
        @Override // com.google.android.exoplayer2.extractor.ExtractorsFactory
        public final Extractor[] createExtractors() {
            return FragmentedMp4Extractor.lambda$static$0();
        }
    };
    private static final byte[] PIFF_SAMPLE_ENCRYPTION_BOX_EXTENDED_TYPE = {-94, 57, 79, 82, 90, -101, 79, Ascii.DC4, -94, 68, 108, 66, 124, 100, -115, -12};
    private static final Format EMSG_FORMAT = new Format.Builder().setSampleMimeType(MimeTypes.APPLICATION_EMSG).build();

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface Flags {
    }

    private static boolean shouldParseContainerAtom(int atom) {
        return atom == 1836019574 || atom == 1953653099 || atom == 1835297121 || atom == 1835626086 || atom == 1937007212 || atom == 1836019558 || atom == 1953653094 || atom == 1836475768 || atom == 1701082227;
    }

    private static boolean shouldParseLeafAtom(int atom) {
        return atom == 1751411826 || atom == 1835296868 || atom == 1836476516 || atom == 1936286840 || atom == 1937011556 || atom == 1937011827 || atom == 1668576371 || atom == 1937011555 || atom == 1937011578 || atom == 1937013298 || atom == 1937007471 || atom == 1668232756 || atom == 1937011571 || atom == 1952867444 || atom == 1952868452 || atom == 1953196132 || atom == 1953654136 || atom == 1953658222 || atom == 1886614376 || atom == 1935763834 || atom == 1935763823 || atom == 1936027235 || atom == 1970628964 || atom == 1935828848 || atom == 1936158820 || atom == 1701606260 || atom == 1835362404 || atom == 1701671783;
    }

    protected Track modifyTrack(Track track) {
        return track;
    }

    @Override // com.google.android.exoplayer2.extractor.Extractor
    public void release() {
    }

    static /* synthetic */ Extractor[] lambda$static$0() {
        return new Extractor[]{new FragmentedMp4Extractor()};
    }

    public FragmentedMp4Extractor() {
        this(0);
    }

    public FragmentedMp4Extractor(int flags) {
        this(flags, null);
    }

    public FragmentedMp4Extractor(int flags, TimestampAdjuster timestampAdjuster) {
        this(flags, timestampAdjuster, null, Collections.emptyList());
    }

    public FragmentedMp4Extractor(int flags, TimestampAdjuster timestampAdjuster, Track sideloadedTrack) {
        this(flags, timestampAdjuster, sideloadedTrack, Collections.emptyList());
    }

    public FragmentedMp4Extractor(int flags, TimestampAdjuster timestampAdjuster, Track sideloadedTrack, List<Format> closedCaptionFormats) {
        this(flags, timestampAdjuster, sideloadedTrack, closedCaptionFormats, null);
    }

    public FragmentedMp4Extractor(int flags, TimestampAdjuster timestampAdjuster, Track sideloadedTrack, List<Format> closedCaptionFormats, TrackOutput additionalEmsgTrackOutput) {
        this.flags = flags;
        this.timestampAdjuster = timestampAdjuster;
        this.sideloadedTrack = sideloadedTrack;
        this.closedCaptionFormats = Collections.unmodifiableList(closedCaptionFormats);
        this.additionalEmsgTrackOutput = additionalEmsgTrackOutput;
        this.eventMessageEncoder = new EventMessageEncoder();
        this.atomHeader = new ParsableByteArray(16);
        this.nalStartCode = new ParsableByteArray(NalUnitUtil.NAL_START_CODE);
        this.nalPrefix = new ParsableByteArray(5);
        this.nalBuffer = new ParsableByteArray();
        byte[] bArr = new byte[16];
        this.scratchBytes = bArr;
        this.scratch = new ParsableByteArray(bArr);
        this.containerAtoms = new ArrayDeque<>();
        this.pendingMetadataSampleInfos = new ArrayDeque<>();
        this.trackBundles = new SparseArray<>();
        this.durationUs = C.TIME_UNSET;
        this.pendingSeekTimeUs = C.TIME_UNSET;
        this.segmentIndexEarliestPresentationTimeUs = C.TIME_UNSET;
        this.extractorOutput = ExtractorOutput.PLACEHOLDER;
        this.emsgTrackOutputs = new TrackOutput[0];
        this.ceaTrackOutputs = new TrackOutput[0];
    }

    @Override // com.google.android.exoplayer2.extractor.Extractor
    public boolean sniff(ExtractorInput input) throws IOException {
        return Sniffer.sniffFragmented(input);
    }

    @Override // com.google.android.exoplayer2.extractor.Extractor
    public void init(ExtractorOutput output) {
        this.extractorOutput = output;
        enterReadingAtomHeaderState();
        initExtraTracks();
        if (this.sideloadedTrack != null) {
            this.trackBundles.put(0, new TrackBundle(output.track(0, this.sideloadedTrack.type), new TrackSampleTable(this.sideloadedTrack, new long[0], new int[0], 0, new long[0], new int[0], 0L), new DefaultSampleValues(0, 0, 0, 0)));
            this.extractorOutput.endTracks();
        }
    }

    @Override // com.google.android.exoplayer2.extractor.Extractor
    public void seek(long position, long timeUs) {
        int size = this.trackBundles.size();
        for (int i = 0; i < size; i++) {
            this.trackBundles.valueAt(i).resetFragmentInfo();
        }
        this.pendingMetadataSampleInfos.clear();
        this.pendingMetadataSampleBytes = 0;
        this.pendingSeekTimeUs = timeUs;
        this.containerAtoms.clear();
        enterReadingAtomHeaderState();
    }

    @Override // com.google.android.exoplayer2.extractor.Extractor
    public int read(ExtractorInput input, PositionHolder seekPosition) throws IOException {
        while (true) {
            int i = this.parserState;
            if (i != 0) {
                if (i == 1) {
                    readAtomPayload(input);
                } else if (i == 2) {
                    readEncryptionData(input);
                } else if (readSample(input)) {
                    return 0;
                }
            } else if (!readAtomHeader(input)) {
                return -1;
            }
        }
    }

    private void enterReadingAtomHeaderState() {
        this.parserState = 0;
        this.atomHeaderBytesRead = 0;
    }

    private boolean readAtomHeader(ExtractorInput input) throws IOException {
        if (this.atomHeaderBytesRead == 0) {
            if (!input.readFully(this.atomHeader.getData(), 0, 8, true)) {
                return false;
            }
            this.atomHeaderBytesRead = 8;
            this.atomHeader.setPosition(0);
            this.atomSize = this.atomHeader.readUnsignedInt();
            this.atomType = this.atomHeader.readInt();
        }
        long j = this.atomSize;
        if (j == 1) {
            input.readFully(this.atomHeader.getData(), 8, 8);
            this.atomHeaderBytesRead += 8;
            this.atomSize = this.atomHeader.readUnsignedLongToLong();
        } else if (j == 0) {
            long length = input.getLength();
            if (length == -1 && !this.containerAtoms.isEmpty()) {
                length = this.containerAtoms.peek().endPosition;
            }
            if (length != -1) {
                this.atomSize = (length - input.getPosition()) + this.atomHeaderBytesRead;
            }
        }
        if (this.atomSize < this.atomHeaderBytesRead) {
            throw ParserException.createForUnsupportedContainerFeature("Atom size less than header length (unsupported).");
        }
        long position = input.getPosition() - this.atomHeaderBytesRead;
        int i = this.atomType;
        if ((i == 1836019558 || i == 1835295092) && !this.haveOutputSeekMap) {
            this.extractorOutput.seekMap(new SeekMap.Unseekable(this.durationUs, position));
            this.haveOutputSeekMap = true;
        }
        if (this.atomType == 1836019558) {
            int size = this.trackBundles.size();
            for (int i2 = 0; i2 < size; i2++) {
                TrackFragment trackFragment = this.trackBundles.valueAt(i2).fragment;
                trackFragment.atomPosition = position;
                trackFragment.auxiliaryDataPosition = position;
                trackFragment.dataPosition = position;
            }
        }
        int i3 = this.atomType;
        if (i3 == 1835295092) {
            this.currentTrackBundle = null;
            this.endOfMdatPosition = position + this.atomSize;
            this.parserState = 2;
            return true;
        }
        if (shouldParseContainerAtom(i3)) {
            long position2 = (input.getPosition() + this.atomSize) - 8;
            this.containerAtoms.push(new Atom.ContainerAtom(this.atomType, position2));
            if (this.atomSize == this.atomHeaderBytesRead) {
                processAtomEnded(position2);
            } else {
                enterReadingAtomHeaderState();
            }
        } else if (shouldParseLeafAtom(this.atomType)) {
            if (this.atomHeaderBytesRead != 8) {
                throw ParserException.createForUnsupportedContainerFeature("Leaf atom defines extended atom size (unsupported).");
            }
            if (this.atomSize > 2147483647L) {
                throw ParserException.createForUnsupportedContainerFeature("Leaf atom with length > 2147483647 (unsupported).");
            }
            ParsableByteArray parsableByteArray = new ParsableByteArray((int) this.atomSize);
            System.arraycopy(this.atomHeader.getData(), 0, parsableByteArray.getData(), 0, 8);
            this.atomData = parsableByteArray;
            this.parserState = 1;
        } else {
            if (this.atomSize > 2147483647L) {
                throw ParserException.createForUnsupportedContainerFeature("Skipping atom with length > 2147483647 (unsupported).");
            }
            this.atomData = null;
            this.parserState = 1;
        }
        return true;
    }

    private void readAtomPayload(ExtractorInput input) throws IOException {
        int i = ((int) this.atomSize) - this.atomHeaderBytesRead;
        ParsableByteArray parsableByteArray = this.atomData;
        if (parsableByteArray != null) {
            input.readFully(parsableByteArray.getData(), 8, i);
            onLeafAtomRead(new Atom.LeafAtom(this.atomType, parsableByteArray), input.getPosition());
        } else {
            input.skipFully(i);
        }
        processAtomEnded(input.getPosition());
    }

    private void processAtomEnded(long atomEndPosition) throws ParserException {
        while (!this.containerAtoms.isEmpty() && this.containerAtoms.peek().endPosition == atomEndPosition) {
            onContainerAtomRead(this.containerAtoms.pop());
        }
        enterReadingAtomHeaderState();
    }

    private void onLeafAtomRead(Atom.LeafAtom leaf, long inputPosition) throws ParserException {
        if (!this.containerAtoms.isEmpty()) {
            this.containerAtoms.peek().add(leaf);
            return;
        }
        if (leaf.type == 1936286840) {
            Pair<Long, ChunkIndex> sidx = parseSidx(leaf.data, inputPosition);
            this.segmentIndexEarliestPresentationTimeUs = ((Long) sidx.first).longValue();
            this.extractorOutput.seekMap((SeekMap) sidx.second);
            this.haveOutputSeekMap = true;
            return;
        }
        if (leaf.type == 1701671783) {
            onEmsgLeafAtomRead(leaf.data);
        }
    }

    private void onContainerAtomRead(Atom.ContainerAtom container) throws ParserException {
        if (container.type == 1836019574) {
            onMoovContainerAtomRead(container);
        } else if (container.type == 1836019558) {
            onMoofContainerAtomRead(container);
        } else {
            if (this.containerAtoms.isEmpty()) {
                return;
            }
            this.containerAtoms.peek().add(container);
        }
    }

    private void onMoovContainerAtomRead(Atom.ContainerAtom moov) throws ParserException {
        int i = 0;
        Assertions.checkState(this.sideloadedTrack == null, "Unexpected moov box.");
        DrmInitData drmInitDataFromAtoms = getDrmInitDataFromAtoms(moov.leafChildren);
        Atom.ContainerAtom containerAtom = (Atom.ContainerAtom) Assertions.checkNotNull(moov.getContainerAtomOfType(Atom.TYPE_mvex));
        SparseArray<DefaultSampleValues> sparseArray = new SparseArray<>();
        int size = containerAtom.leafChildren.size();
        long mehd = -9223372036854775807L;
        for (int i2 = 0; i2 < size; i2++) {
            Atom.LeafAtom leafAtom = containerAtom.leafChildren.get(i2);
            if (leafAtom.type == 1953654136) {
                Pair<Integer, DefaultSampleValues> trex = parseTrex(leafAtom.data);
                sparseArray.put(((Integer) trex.first).intValue(), (DefaultSampleValues) trex.second);
            } else if (leafAtom.type == 1835362404) {
                mehd = parseMehd(leafAtom.data);
            }
        }
        List<TrackSampleTable> traks = AtomParsers.parseTraks(moov, new GaplessInfoHolder(), mehd, drmInitDataFromAtoms, (this.flags & 16) != 0, false, new Function() { // from class: com.google.android.exoplayer2.extractor.mp4.FragmentedMp4Extractor$$ExternalSyntheticLambda0
            @Override // com.google.common.base.Function
            public final Object apply(Object obj) {
                return this.f$0.modifyTrack((Track) obj);
            }
        });
        int size2 = traks.size();
        if (this.trackBundles.size() == 0) {
            while (i < size2) {
                TrackSampleTable trackSampleTable = traks.get(i);
                Track track = trackSampleTable.track;
                this.trackBundles.put(track.id, new TrackBundle(this.extractorOutput.track(i, track.type), trackSampleTable, getDefaultSampleValues(sparseArray, track.id)));
                this.durationUs = Math.max(this.durationUs, track.durationUs);
                i++;
            }
            this.extractorOutput.endTracks();
            return;
        }
        Assertions.checkState(this.trackBundles.size() == size2);
        while (i < size2) {
            TrackSampleTable trackSampleTable2 = traks.get(i);
            Track track2 = trackSampleTable2.track;
            this.trackBundles.get(track2.id).reset(trackSampleTable2, getDefaultSampleValues(sparseArray, track2.id));
            i++;
        }
    }

    private DefaultSampleValues getDefaultSampleValues(SparseArray<DefaultSampleValues> defaultSampleValuesArray, int trackId) {
        if (defaultSampleValuesArray.size() == 1) {
            return defaultSampleValuesArray.valueAt(0);
        }
        return (DefaultSampleValues) Assertions.checkNotNull(defaultSampleValuesArray.get(trackId));
    }

    private void onMoofContainerAtomRead(Atom.ContainerAtom moof) throws ParserException {
        parseMoof(moof, this.trackBundles, this.sideloadedTrack != null, this.flags, this.scratchBytes);
        DrmInitData drmInitDataFromAtoms = getDrmInitDataFromAtoms(moof.leafChildren);
        if (drmInitDataFromAtoms != null) {
            int size = this.trackBundles.size();
            for (int i = 0; i < size; i++) {
                this.trackBundles.valueAt(i).updateDrmInitData(drmInitDataFromAtoms);
            }
        }
        if (this.pendingSeekTimeUs != C.TIME_UNSET) {
            int size2 = this.trackBundles.size();
            for (int i2 = 0; i2 < size2; i2++) {
                this.trackBundles.valueAt(i2).seek(this.pendingSeekTimeUs);
            }
            this.pendingSeekTimeUs = C.TIME_UNSET;
        }
    }

    private void initExtraTracks() {
        int i;
        TrackOutput[] trackOutputArr = new TrackOutput[2];
        this.emsgTrackOutputs = trackOutputArr;
        TrackOutput trackOutput = this.additionalEmsgTrackOutput;
        int i2 = 0;
        if (trackOutput != null) {
            trackOutputArr[0] = trackOutput;
            i = 1;
        } else {
            i = 0;
        }
        int i3 = 100;
        if ((this.flags & 4) != 0) {
            trackOutputArr[i] = this.extractorOutput.track(100, 5);
            i3 = 101;
            i++;
        }
        TrackOutput[] trackOutputArr2 = (TrackOutput[]) Util.nullSafeArrayCopy(this.emsgTrackOutputs, i);
        this.emsgTrackOutputs = trackOutputArr2;
        for (TrackOutput trackOutput2 : trackOutputArr2) {
            trackOutput2.format(EMSG_FORMAT);
        }
        this.ceaTrackOutputs = new TrackOutput[this.closedCaptionFormats.size()];
        while (i2 < this.ceaTrackOutputs.length) {
            TrackOutput trackOutputTrack = this.extractorOutput.track(i3, 3);
            trackOutputTrack.format(this.closedCaptionFormats.get(i2));
            this.ceaTrackOutputs[i2] = trackOutputTrack;
            i2++;
            i3++;
        }
    }

    private void onEmsgLeafAtomRead(ParsableByteArray atom) {
        long jScaleLargeTimestamp;
        String str;
        long jScaleLargeTimestamp2;
        String str2;
        long unsignedInt;
        long jAdjustSampleTimestamp;
        if (this.emsgTrackOutputs.length == 0) {
            return;
        }
        atom.setPosition(8);
        int fullAtomVersion = Atom.parseFullAtomVersion(atom.readInt());
        if (fullAtomVersion == 0) {
            String str3 = (String) Assertions.checkNotNull(atom.readNullTerminatedString());
            String str4 = (String) Assertions.checkNotNull(atom.readNullTerminatedString());
            long unsignedInt2 = atom.readUnsignedInt();
            jScaleLargeTimestamp = Util.scaleLargeTimestamp(atom.readUnsignedInt(), 1000000L, unsignedInt2);
            long j = this.segmentIndexEarliestPresentationTimeUs;
            long j2 = j != C.TIME_UNSET ? j + jScaleLargeTimestamp : -9223372036854775807L;
            str = str3;
            jScaleLargeTimestamp2 = Util.scaleLargeTimestamp(atom.readUnsignedInt(), 1000L, unsignedInt2);
            str2 = str4;
            unsignedInt = atom.readUnsignedInt();
            jAdjustSampleTimestamp = j2;
        } else if (fullAtomVersion == 1) {
            long unsignedInt3 = atom.readUnsignedInt();
            jAdjustSampleTimestamp = Util.scaleLargeTimestamp(atom.readUnsignedLongToLong(), 1000000L, unsignedInt3);
            long jScaleLargeTimestamp3 = Util.scaleLargeTimestamp(atom.readUnsignedInt(), 1000L, unsignedInt3);
            long unsignedInt4 = atom.readUnsignedInt();
            str = (String) Assertions.checkNotNull(atom.readNullTerminatedString());
            jScaleLargeTimestamp2 = jScaleLargeTimestamp3;
            unsignedInt = unsignedInt4;
            str2 = (String) Assertions.checkNotNull(atom.readNullTerminatedString());
            jScaleLargeTimestamp = -9223372036854775807L;
        } else {
            Log.w(TAG, new StringBuilder(46).append("Skipping unsupported emsg version: ").append(fullAtomVersion).toString());
            return;
        }
        byte[] bArr = new byte[atom.bytesLeft()];
        atom.readBytes(bArr, 0, atom.bytesLeft());
        ParsableByteArray parsableByteArray = new ParsableByteArray(this.eventMessageEncoder.encode(new EventMessage(str, str2, jScaleLargeTimestamp2, unsignedInt, bArr)));
        int iBytesLeft = parsableByteArray.bytesLeft();
        for (TrackOutput trackOutput : this.emsgTrackOutputs) {
            parsableByteArray.setPosition(0);
            trackOutput.sampleData(parsableByteArray, iBytesLeft);
        }
        if (jAdjustSampleTimestamp == C.TIME_UNSET) {
            this.pendingMetadataSampleInfos.addLast(new MetadataSampleInfo(jScaleLargeTimestamp, iBytesLeft));
            this.pendingMetadataSampleBytes += iBytesLeft;
            return;
        }
        TimestampAdjuster timestampAdjuster = this.timestampAdjuster;
        if (timestampAdjuster != null) {
            jAdjustSampleTimestamp = timestampAdjuster.adjustSampleTimestamp(jAdjustSampleTimestamp);
        }
        for (TrackOutput trackOutput2 : this.emsgTrackOutputs) {
            trackOutput2.sampleMetadata(jAdjustSampleTimestamp, 1, iBytesLeft, 0, null);
        }
    }

    private static Pair<Integer, DefaultSampleValues> parseTrex(ParsableByteArray trex) {
        trex.setPosition(12);
        return Pair.create(Integer.valueOf(trex.readInt()), new DefaultSampleValues(trex.readInt() - 1, trex.readInt(), trex.readInt(), trex.readInt()));
    }

    private static long parseMehd(ParsableByteArray mehd) {
        mehd.setPosition(8);
        return Atom.parseFullAtomVersion(mehd.readInt()) == 0 ? mehd.readUnsignedInt() : mehd.readUnsignedLongToLong();
    }

    private static void parseMoof(Atom.ContainerAtom moof, SparseArray<TrackBundle> trackBundles, boolean haveSideloadedTrack, int flags, byte[] extendedTypeScratch) throws ParserException {
        int size = moof.containerChildren.size();
        for (int i = 0; i < size; i++) {
            Atom.ContainerAtom containerAtom = moof.containerChildren.get(i);
            if (containerAtom.type == 1953653094) {
                parseTraf(containerAtom, trackBundles, haveSideloadedTrack, flags, extendedTypeScratch);
            }
        }
    }

    private static void parseTraf(Atom.ContainerAtom traf, SparseArray<TrackBundle> trackBundles, boolean haveSideloadedTrack, int flags, byte[] extendedTypeScratch) throws ParserException {
        TrackBundle tfhd = parseTfhd(((Atom.LeafAtom) Assertions.checkNotNull(traf.getLeafAtomOfType(Atom.TYPE_tfhd))).data, trackBundles, haveSideloadedTrack);
        if (tfhd == null) {
            return;
        }
        TrackFragment trackFragment = tfhd.fragment;
        long j = trackFragment.nextFragmentDecodeTime;
        boolean z = trackFragment.nextFragmentDecodeTimeIncludesMoov;
        tfhd.resetFragmentInfo();
        tfhd.currentlyInFragment = true;
        Atom.LeafAtom leafAtomOfType = traf.getLeafAtomOfType(Atom.TYPE_tfdt);
        if (leafAtomOfType != null && (flags & 2) == 0) {
            trackFragment.nextFragmentDecodeTime = parseTfdt(leafAtomOfType.data);
            trackFragment.nextFragmentDecodeTimeIncludesMoov = true;
        } else {
            trackFragment.nextFragmentDecodeTime = j;
            trackFragment.nextFragmentDecodeTimeIncludesMoov = z;
        }
        parseTruns(traf, tfhd, flags);
        TrackEncryptionBox sampleDescriptionEncryptionBox = tfhd.moovSampleTable.track.getSampleDescriptionEncryptionBox(((DefaultSampleValues) Assertions.checkNotNull(trackFragment.header)).sampleDescriptionIndex);
        Atom.LeafAtom leafAtomOfType2 = traf.getLeafAtomOfType(Atom.TYPE_saiz);
        if (leafAtomOfType2 != null) {
            parseSaiz((TrackEncryptionBox) Assertions.checkNotNull(sampleDescriptionEncryptionBox), leafAtomOfType2.data, trackFragment);
        }
        Atom.LeafAtom leafAtomOfType3 = traf.getLeafAtomOfType(Atom.TYPE_saio);
        if (leafAtomOfType3 != null) {
            parseSaio(leafAtomOfType3.data, trackFragment);
        }
        Atom.LeafAtom leafAtomOfType4 = traf.getLeafAtomOfType(Atom.TYPE_senc);
        if (leafAtomOfType4 != null) {
            parseSenc(leafAtomOfType4.data, trackFragment);
        }
        parseSampleGroups(traf, sampleDescriptionEncryptionBox != null ? sampleDescriptionEncryptionBox.schemeType : null, trackFragment);
        int size = traf.leafChildren.size();
        for (int i = 0; i < size; i++) {
            Atom.LeafAtom leafAtom = traf.leafChildren.get(i);
            if (leafAtom.type == 1970628964) {
                parseUuid(leafAtom.data, trackFragment, extendedTypeScratch);
            }
        }
    }

    private static void parseTruns(Atom.ContainerAtom traf, TrackBundle trackBundle, int flags) throws ParserException {
        List<Atom.LeafAtom> list = traf.leafChildren;
        int size = list.size();
        int i = 0;
        int i2 = 0;
        for (int i3 = 0; i3 < size; i3++) {
            Atom.LeafAtom leafAtom = list.get(i3);
            if (leafAtom.type == 1953658222) {
                ParsableByteArray parsableByteArray = leafAtom.data;
                parsableByteArray.setPosition(12);
                int unsignedIntToInt = parsableByteArray.readUnsignedIntToInt();
                if (unsignedIntToInt > 0) {
                    i2 += unsignedIntToInt;
                    i++;
                }
            }
        }
        trackBundle.currentTrackRunIndex = 0;
        trackBundle.currentSampleInTrackRun = 0;
        trackBundle.currentSampleIndex = 0;
        trackBundle.fragment.initTables(i, i2);
        int i4 = 0;
        int trun = 0;
        for (int i5 = 0; i5 < size; i5++) {
            Atom.LeafAtom leafAtom2 = list.get(i5);
            if (leafAtom2.type == 1953658222) {
                trun = parseTrun(trackBundle, i4, flags, leafAtom2.data, trun);
                i4++;
            }
        }
    }

    private static void parseSaiz(TrackEncryptionBox encryptionBox, ParsableByteArray saiz, TrackFragment out) throws ParserException {
        int i;
        int i2 = encryptionBox.perSampleIvSize;
        saiz.setPosition(8);
        if ((Atom.parseFullAtomFlags(saiz.readInt()) & 1) == 1) {
            saiz.skipBytes(8);
        }
        int unsignedByte = saiz.readUnsignedByte();
        int unsignedIntToInt = saiz.readUnsignedIntToInt();
        if (unsignedIntToInt > out.sampleCount) {
            throw ParserException.createForMalformedContainer(new StringBuilder(78).append("Saiz sample count ").append(unsignedIntToInt).append(" is greater than fragment sample count").append(out.sampleCount).toString(), null);
        }
        if (unsignedByte == 0) {
            boolean[] zArr = out.sampleHasSubsampleEncryptionTable;
            i = 0;
            for (int i3 = 0; i3 < unsignedIntToInt; i3++) {
                int unsignedByte2 = saiz.readUnsignedByte();
                i += unsignedByte2;
                zArr[i3] = unsignedByte2 > i2;
            }
        } else {
            i = (unsignedByte * unsignedIntToInt) + 0;
            Arrays.fill(out.sampleHasSubsampleEncryptionTable, 0, unsignedIntToInt, unsignedByte > i2);
        }
        Arrays.fill(out.sampleHasSubsampleEncryptionTable, unsignedIntToInt, out.sampleCount, false);
        if (i > 0) {
            out.initEncryptionData(i);
        }
    }

    private static void parseSaio(ParsableByteArray saio, TrackFragment out) throws ParserException {
        saio.setPosition(8);
        int i = saio.readInt();
        if ((Atom.parseFullAtomFlags(i) & 1) == 1) {
            saio.skipBytes(8);
        }
        int unsignedIntToInt = saio.readUnsignedIntToInt();
        if (unsignedIntToInt != 1) {
            throw ParserException.createForMalformedContainer(new StringBuilder(40).append("Unexpected saio entry count: ").append(unsignedIntToInt).toString(), null);
        }
        out.auxiliaryDataPosition += Atom.parseFullAtomVersion(i) == 0 ? saio.readUnsignedInt() : saio.readUnsignedLongToLong();
    }

    private static TrackBundle parseTfhd(ParsableByteArray tfhd, SparseArray<TrackBundle> trackBundles, boolean haveSideloadedTrack) {
        int i;
        int i2;
        int i3;
        int i4;
        tfhd.setPosition(8);
        int fullAtomFlags = Atom.parseFullAtomFlags(tfhd.readInt());
        TrackBundle trackBundleValueAt = haveSideloadedTrack ? trackBundles.valueAt(0) : trackBundles.get(tfhd.readInt());
        if (trackBundleValueAt == null) {
            return null;
        }
        if ((fullAtomFlags & 1) != 0) {
            long unsignedLongToLong = tfhd.readUnsignedLongToLong();
            trackBundleValueAt.fragment.dataPosition = unsignedLongToLong;
            trackBundleValueAt.fragment.auxiliaryDataPosition = unsignedLongToLong;
        }
        DefaultSampleValues defaultSampleValues = trackBundleValueAt.defaultSampleValues;
        if ((fullAtomFlags & 2) != 0) {
            i = tfhd.readInt() - 1;
        } else {
            i = defaultSampleValues.sampleDescriptionIndex;
        }
        if ((fullAtomFlags & 8) != 0) {
            i2 = tfhd.readInt();
        } else {
            i2 = defaultSampleValues.duration;
        }
        if ((fullAtomFlags & 16) != 0) {
            i3 = tfhd.readInt();
        } else {
            i3 = defaultSampleValues.size;
        }
        if ((fullAtomFlags & 32) != 0) {
            i4 = tfhd.readInt();
        } else {
            i4 = defaultSampleValues.flags;
        }
        trackBundleValueAt.fragment.header = new DefaultSampleValues(i, i2, i3, i4);
        return trackBundleValueAt;
    }

    private static long parseTfdt(ParsableByteArray tfdt) {
        tfdt.setPosition(8);
        return Atom.parseFullAtomVersion(tfdt.readInt()) == 1 ? tfdt.readUnsignedLongToLong() : tfdt.readUnsignedInt();
    }

    private static int parseTrun(TrackBundle trackBundle, int index, int flags, ParsableByteArray trun, int trackRunStart) throws ParserException {
        boolean z;
        int i;
        boolean z2;
        int i2;
        boolean z3;
        boolean z4;
        boolean z5;
        boolean z6;
        TrackBundle trackBundle2 = trackBundle;
        trun.setPosition(8);
        int fullAtomFlags = Atom.parseFullAtomFlags(trun.readInt());
        Track track = trackBundle2.moovSampleTable.track;
        TrackFragment trackFragment = trackBundle2.fragment;
        DefaultSampleValues defaultSampleValues = (DefaultSampleValues) Util.castNonNull(trackFragment.header);
        trackFragment.trunLength[index] = trun.readUnsignedIntToInt();
        trackFragment.trunDataPosition[index] = trackFragment.dataPosition;
        if ((fullAtomFlags & 1) != 0) {
            long[] jArr = trackFragment.trunDataPosition;
            jArr[index] = jArr[index] + trun.readInt();
        }
        boolean z7 = (fullAtomFlags & 4) != 0;
        int i3 = defaultSampleValues.flags;
        if (z7) {
            i3 = trun.readInt();
        }
        boolean z8 = (fullAtomFlags & 256) != 0;
        boolean z9 = (fullAtomFlags & 512) != 0;
        boolean z10 = (fullAtomFlags & 1024) != 0;
        boolean z11 = (fullAtomFlags & 2048) != 0;
        long jScaleLargeTimestamp = 0;
        if (track.editListDurations != null && track.editListDurations.length == 1 && track.editListDurations[0] == 0) {
            jScaleLargeTimestamp = Util.scaleLargeTimestamp(((long[]) Util.castNonNull(track.editListMediaTimes))[0], 1000000L, track.timescale);
        }
        int[] iArr = trackFragment.sampleSizeTable;
        int[] iArr2 = trackFragment.sampleCompositionTimeOffsetUsTable;
        long[] jArr2 = trackFragment.sampleDecodingTimeUsTable;
        boolean[] zArr = trackFragment.sampleIsSyncFrameTable;
        int i4 = i3;
        boolean z12 = track.type == 2 && (flags & 1) != 0;
        int i5 = trackRunStart + trackFragment.trunLength[index];
        boolean z13 = z12;
        long j = track.timescale;
        long j2 = jScaleLargeTimestamp;
        long j3 = trackFragment.nextFragmentDecodeTime;
        int i6 = trackRunStart;
        while (i6 < i5) {
            int iCheckNonNegative = checkNonNegative(z8 ? trun.readInt() : defaultSampleValues.duration);
            if (z9) {
                z = z8;
                i = trun.readInt();
            } else {
                z = z8;
                i = defaultSampleValues.size;
            }
            int iCheckNonNegative2 = checkNonNegative(i);
            if (z10) {
                z2 = z7;
                i2 = trun.readInt();
            } else if (i6 == 0 && z7) {
                z2 = z7;
                i2 = i4;
            } else {
                z2 = z7;
                i2 = defaultSampleValues.flags;
            }
            if (z11) {
                z3 = z11;
                z4 = z9;
                z5 = z10;
                iArr2[i6] = (int) ((trun.readInt() * 1000000) / j);
                z6 = false;
            } else {
                z3 = z11;
                z4 = z9;
                z5 = z10;
                z6 = false;
                iArr2[i6] = 0;
            }
            jArr2[i6] = Util.scaleLargeTimestamp(j3, 1000000L, j) - j2;
            if (!trackFragment.nextFragmentDecodeTimeIncludesMoov) {
                jArr2[i6] = jArr2[i6] + trackBundle2.moovSampleTable.durationUs;
            }
            iArr[i6] = iCheckNonNegative2;
            zArr[i6] = (((i2 >> 16) & 1) != 0 || (z13 && i6 != 0)) ? z6 : true;
            j3 += iCheckNonNegative;
            i6++;
            trackBundle2 = trackBundle;
            z8 = z;
            j = j;
            z7 = z2;
            z11 = z3;
            z9 = z4;
            z10 = z5;
        }
        trackFragment.nextFragmentDecodeTime = j3;
        return i5;
    }

    private static int checkNonNegative(int value) throws ParserException {
        if (value >= 0) {
            return value;
        }
        throw ParserException.createForMalformedContainer(new StringBuilder(38).append("Unexpected negative value: ").append(value).toString(), null);
    }

    private static void parseUuid(ParsableByteArray uuid, TrackFragment out, byte[] extendedTypeScratch) throws ParserException {
        uuid.setPosition(8);
        uuid.readBytes(extendedTypeScratch, 0, 16);
        if (Arrays.equals(extendedTypeScratch, PIFF_SAMPLE_ENCRYPTION_BOX_EXTENDED_TYPE)) {
            parseSenc(uuid, 16, out);
        }
    }

    private static void parseSenc(ParsableByteArray senc, TrackFragment out) throws ParserException {
        parseSenc(senc, 0, out);
    }

    private static void parseSenc(ParsableByteArray senc, int offset, TrackFragment out) throws ParserException {
        senc.setPosition(offset + 8);
        int fullAtomFlags = Atom.parseFullAtomFlags(senc.readInt());
        if ((fullAtomFlags & 1) != 0) {
            throw ParserException.createForUnsupportedContainerFeature("Overriding TrackEncryptionBox parameters is unsupported.");
        }
        boolean z = (fullAtomFlags & 2) != 0;
        int unsignedIntToInt = senc.readUnsignedIntToInt();
        if (unsignedIntToInt == 0) {
            Arrays.fill(out.sampleHasSubsampleEncryptionTable, 0, out.sampleCount, false);
        } else {
            if (unsignedIntToInt != out.sampleCount) {
                throw ParserException.createForMalformedContainer(new StringBuilder(80).append("Senc sample count ").append(unsignedIntToInt).append(" is different from fragment sample count").append(out.sampleCount).toString(), null);
            }
            Arrays.fill(out.sampleHasSubsampleEncryptionTable, 0, unsignedIntToInt, z);
            out.initEncryptionData(senc.bytesLeft());
            out.fillEncryptionData(senc);
        }
    }

    private static void parseSampleGroups(Atom.ContainerAtom traf, String schemeType, TrackFragment out) throws ParserException {
        byte[] bArr = null;
        ParsableByteArray parsableByteArray = null;
        ParsableByteArray parsableByteArray2 = null;
        for (int i = 0; i < traf.leafChildren.size(); i++) {
            Atom.LeafAtom leafAtom = traf.leafChildren.get(i);
            ParsableByteArray parsableByteArray3 = leafAtom.data;
            if (leafAtom.type == 1935828848) {
                parsableByteArray3.setPosition(12);
                if (parsableByteArray3.readInt() == SAMPLE_GROUP_TYPE_seig) {
                    parsableByteArray = parsableByteArray3;
                }
            } else if (leafAtom.type == 1936158820) {
                parsableByteArray3.setPosition(12);
                if (parsableByteArray3.readInt() == SAMPLE_GROUP_TYPE_seig) {
                    parsableByteArray2 = parsableByteArray3;
                }
            }
        }
        if (parsableByteArray == null || parsableByteArray2 == null) {
            return;
        }
        parsableByteArray.setPosition(8);
        int fullAtomVersion = Atom.parseFullAtomVersion(parsableByteArray.readInt());
        parsableByteArray.skipBytes(4);
        if (fullAtomVersion == 1) {
            parsableByteArray.skipBytes(4);
        }
        if (parsableByteArray.readInt() != 1) {
            throw ParserException.createForUnsupportedContainerFeature("Entry count in sbgp != 1 (unsupported).");
        }
        parsableByteArray2.setPosition(8);
        int fullAtomVersion2 = Atom.parseFullAtomVersion(parsableByteArray2.readInt());
        parsableByteArray2.skipBytes(4);
        if (fullAtomVersion2 == 1) {
            if (parsableByteArray2.readUnsignedInt() == 0) {
                throw ParserException.createForUnsupportedContainerFeature("Variable length description in sgpd found (unsupported)");
            }
        } else if (fullAtomVersion2 >= 2) {
            parsableByteArray2.skipBytes(4);
        }
        if (parsableByteArray2.readUnsignedInt() != 1) {
            throw ParserException.createForUnsupportedContainerFeature("Entry count in sgpd != 1 (unsupported).");
        }
        parsableByteArray2.skipBytes(1);
        int unsignedByte = parsableByteArray2.readUnsignedByte();
        int i2 = (unsignedByte & PsExtractor.VIDEO_STREAM_MASK) >> 4;
        int i3 = unsignedByte & 15;
        boolean z = parsableByteArray2.readUnsignedByte() == 1;
        if (z) {
            int unsignedByte2 = parsableByteArray2.readUnsignedByte();
            byte[] bArr2 = new byte[16];
            parsableByteArray2.readBytes(bArr2, 0, 16);
            if (unsignedByte2 == 0) {
                int unsignedByte3 = parsableByteArray2.readUnsignedByte();
                bArr = new byte[unsignedByte3];
                parsableByteArray2.readBytes(bArr, 0, unsignedByte3);
            }
            out.definesEncryptionData = true;
            out.trackEncryptionBox = new TrackEncryptionBox(z, schemeType, unsignedByte2, bArr2, i2, i3, bArr);
        }
    }

    private static Pair<Long, ChunkIndex> parseSidx(ParsableByteArray atom, long inputPosition) throws ParserException {
        long unsignedLongToLong;
        long unsignedLongToLong2;
        atom.setPosition(8);
        int fullAtomVersion = Atom.parseFullAtomVersion(atom.readInt());
        atom.skipBytes(4);
        long unsignedInt = atom.readUnsignedInt();
        if (fullAtomVersion == 0) {
            unsignedLongToLong = atom.readUnsignedInt();
            unsignedLongToLong2 = atom.readUnsignedInt();
        } else {
            unsignedLongToLong = atom.readUnsignedLongToLong();
            unsignedLongToLong2 = atom.readUnsignedLongToLong();
        }
        long j = unsignedLongToLong;
        long j2 = inputPosition + unsignedLongToLong2;
        long jScaleLargeTimestamp = Util.scaleLargeTimestamp(j, 1000000L, unsignedInt);
        atom.skipBytes(2);
        int unsignedShort = atom.readUnsignedShort();
        int[] iArr = new int[unsignedShort];
        long[] jArr = new long[unsignedShort];
        long[] jArr2 = new long[unsignedShort];
        long[] jArr3 = new long[unsignedShort];
        long j3 = jScaleLargeTimestamp;
        int i = 0;
        long j4 = j;
        while (i < unsignedShort) {
            int i2 = atom.readInt();
            if ((i2 & Integer.MIN_VALUE) != 0) {
                throw ParserException.createForMalformedContainer("Unhandled indirect reference", null);
            }
            long unsignedInt2 = atom.readUnsignedInt();
            iArr[i] = i2 & Integer.MAX_VALUE;
            jArr[i] = j2;
            jArr3[i] = j3;
            long j5 = j4 + unsignedInt2;
            long[] jArr4 = jArr2;
            long[] jArr5 = jArr3;
            int i3 = unsignedShort;
            long jScaleLargeTimestamp2 = Util.scaleLargeTimestamp(j5, 1000000L, unsignedInt);
            jArr4[i] = jScaleLargeTimestamp2 - jArr5[i];
            atom.skipBytes(4);
            j2 += r1[i];
            i++;
            iArr = iArr;
            jArr3 = jArr5;
            jArr2 = jArr4;
            jArr = jArr;
            unsignedShort = i3;
            j4 = j5;
            j3 = jScaleLargeTimestamp2;
        }
        return Pair.create(Long.valueOf(jScaleLargeTimestamp), new ChunkIndex(iArr, jArr, jArr2, jArr3));
    }

    private void readEncryptionData(ExtractorInput input) throws IOException {
        int size = this.trackBundles.size();
        long j = Long.MAX_VALUE;
        TrackBundle trackBundleValueAt = null;
        for (int i = 0; i < size; i++) {
            TrackFragment trackFragment = this.trackBundles.valueAt(i).fragment;
            if (trackFragment.sampleEncryptionDataNeedsFill && trackFragment.auxiliaryDataPosition < j) {
                j = trackFragment.auxiliaryDataPosition;
                trackBundleValueAt = this.trackBundles.valueAt(i);
            }
        }
        if (trackBundleValueAt == null) {
            this.parserState = 3;
            return;
        }
        int position = (int) (j - input.getPosition());
        if (position < 0) {
            throw ParserException.createForMalformedContainer("Offset to encryption data was negative.", null);
        }
        input.skipFully(position);
        trackBundleValueAt.fragment.fillEncryptionData(input);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private boolean readSample(ExtractorInput extractorInput) throws IOException {
        int iSampleData;
        TrackBundle nextTrackBundle = this.currentTrackBundle;
        Throwable th = null;
        if (nextTrackBundle == null) {
            nextTrackBundle = getNextTrackBundle(this.trackBundles);
            if (nextTrackBundle == null) {
                int position = (int) (this.endOfMdatPosition - extractorInput.getPosition());
                if (position < 0) {
                    throw ParserException.createForMalformedContainer("Offset to end of mdat was negative.", null);
                }
                extractorInput.skipFully(position);
                enterReadingAtomHeaderState();
                return false;
            }
            int currentSampleOffset = (int) (nextTrackBundle.getCurrentSampleOffset() - extractorInput.getPosition());
            if (currentSampleOffset < 0) {
                Log.w(TAG, "Ignoring negative offset to sample data.");
                currentSampleOffset = 0;
            }
            extractorInput.skipFully(currentSampleOffset);
            this.currentTrackBundle = nextTrackBundle;
        }
        int i = 4;
        int i2 = 1;
        if (this.parserState == 3) {
            this.sampleSize = nextTrackBundle.getCurrentSampleSize();
            if (nextTrackBundle.currentSampleIndex < nextTrackBundle.firstSampleToOutputIndex) {
                extractorInput.skipFully(this.sampleSize);
                nextTrackBundle.skipSampleEncryptionData();
                if (!nextTrackBundle.next()) {
                    this.currentTrackBundle = null;
                }
                this.parserState = 3;
                return true;
            }
            if (nextTrackBundle.moovSampleTable.track.sampleTransformation == 1) {
                this.sampleSize -= 8;
                extractorInput.skipFully(8);
            }
            if (MimeTypes.AUDIO_AC4.equals(nextTrackBundle.moovSampleTable.track.format.sampleMimeType)) {
                this.sampleBytesWritten = nextTrackBundle.outputSampleEncryptionData(this.sampleSize, 7);
                Ac4Util.getAc4SampleHeader(this.sampleSize, this.scratch);
                nextTrackBundle.output.sampleData(this.scratch, 7);
                this.sampleBytesWritten += 7;
            } else {
                this.sampleBytesWritten = nextTrackBundle.outputSampleEncryptionData(this.sampleSize, 0);
            }
            this.sampleSize += this.sampleBytesWritten;
            this.parserState = 4;
            this.sampleCurrentNalBytesRemaining = 0;
        }
        Track track = nextTrackBundle.moovSampleTable.track;
        TrackOutput trackOutput = nextTrackBundle.output;
        long currentSamplePresentationTimeUs = nextTrackBundle.getCurrentSamplePresentationTimeUs();
        TimestampAdjuster timestampAdjuster = this.timestampAdjuster;
        if (timestampAdjuster != null) {
            currentSamplePresentationTimeUs = timestampAdjuster.adjustSampleTimestamp(currentSamplePresentationTimeUs);
        }
        long j = currentSamplePresentationTimeUs;
        if (track.nalUnitLengthFieldLength == 0) {
            while (true) {
                int i3 = this.sampleBytesWritten;
                int i4 = this.sampleSize;
                if (i3 >= i4) {
                    break;
                }
                this.sampleBytesWritten += trackOutput.sampleData((DataReader) extractorInput, i4 - i3, false);
            }
        } else {
            byte[] data = this.nalPrefix.getData();
            data[0] = 0;
            data[1] = 0;
            data[2] = 0;
            int i5 = track.nalUnitLengthFieldLength + 1;
            int i6 = 4 - track.nalUnitLengthFieldLength;
            while (this.sampleBytesWritten < this.sampleSize) {
                int i7 = this.sampleCurrentNalBytesRemaining;
                if (i7 == 0) {
                    extractorInput.readFully(data, i6, i5);
                    this.nalPrefix.setPosition(0);
                    int i8 = this.nalPrefix.readInt();
                    if (i8 < i2) {
                        throw ParserException.createForMalformedContainer("Invalid NAL length", th);
                    }
                    this.sampleCurrentNalBytesRemaining = i8 - 1;
                    this.nalStartCode.setPosition(0);
                    trackOutput.sampleData(this.nalStartCode, i);
                    trackOutput.sampleData(this.nalPrefix, i2);
                    this.processSeiNalUnitPayload = (this.ceaTrackOutputs.length <= 0 || !NalUnitUtil.isNalUnitSei(track.format.sampleMimeType, data[i])) ? 0 : i2;
                    this.sampleBytesWritten += 5;
                    this.sampleSize += i6;
                } else {
                    if (this.processSeiNalUnitPayload) {
                        this.nalBuffer.reset(i7);
                        extractorInput.readFully(this.nalBuffer.getData(), 0, this.sampleCurrentNalBytesRemaining);
                        trackOutput.sampleData(this.nalBuffer, this.sampleCurrentNalBytesRemaining);
                        iSampleData = this.sampleCurrentNalBytesRemaining;
                        int iUnescapeStream = NalUnitUtil.unescapeStream(this.nalBuffer.getData(), this.nalBuffer.limit());
                        this.nalBuffer.setPosition(MimeTypes.VIDEO_H265.equals(track.format.sampleMimeType) ? 1 : 0);
                        this.nalBuffer.setLimit(iUnescapeStream);
                        CeaUtil.consume(j, this.nalBuffer, this.ceaTrackOutputs);
                    } else {
                        iSampleData = trackOutput.sampleData((DataReader) extractorInput, i7, false);
                    }
                    this.sampleBytesWritten += iSampleData;
                    this.sampleCurrentNalBytesRemaining -= iSampleData;
                    th = null;
                    i = 4;
                    i2 = 1;
                }
            }
        }
        int currentSampleFlags = nextTrackBundle.getCurrentSampleFlags();
        TrackEncryptionBox encryptionBoxIfEncrypted = nextTrackBundle.getEncryptionBoxIfEncrypted();
        trackOutput.sampleMetadata(j, currentSampleFlags, this.sampleSize, 0, encryptionBoxIfEncrypted != null ? encryptionBoxIfEncrypted.cryptoData : null);
        outputPendingMetadataSamples(j);
        if (!nextTrackBundle.next()) {
            this.currentTrackBundle = null;
        }
        this.parserState = 3;
        return true;
    }

    private void outputPendingMetadataSamples(long sampleTimeUs) {
        while (!this.pendingMetadataSampleInfos.isEmpty()) {
            MetadataSampleInfo metadataSampleInfoRemoveFirst = this.pendingMetadataSampleInfos.removeFirst();
            this.pendingMetadataSampleBytes -= metadataSampleInfoRemoveFirst.size;
            long jAdjustSampleTimestamp = metadataSampleInfoRemoveFirst.presentationTimeDeltaUs + sampleTimeUs;
            TimestampAdjuster timestampAdjuster = this.timestampAdjuster;
            if (timestampAdjuster != null) {
                jAdjustSampleTimestamp = timestampAdjuster.adjustSampleTimestamp(jAdjustSampleTimestamp);
            }
            for (TrackOutput trackOutput : this.emsgTrackOutputs) {
                trackOutput.sampleMetadata(jAdjustSampleTimestamp, 1, metadataSampleInfoRemoveFirst.size, this.pendingMetadataSampleBytes, null);
            }
        }
    }

    private static TrackBundle getNextTrackBundle(SparseArray<TrackBundle> trackBundles) {
        int size = trackBundles.size();
        TrackBundle trackBundle = null;
        long j = Long.MAX_VALUE;
        for (int i = 0; i < size; i++) {
            TrackBundle trackBundleValueAt = trackBundles.valueAt(i);
            if ((trackBundleValueAt.currentlyInFragment || trackBundleValueAt.currentSampleIndex != trackBundleValueAt.moovSampleTable.sampleCount) && (!trackBundleValueAt.currentlyInFragment || trackBundleValueAt.currentTrackRunIndex != trackBundleValueAt.fragment.trunCount)) {
                long currentSampleOffset = trackBundleValueAt.getCurrentSampleOffset();
                if (currentSampleOffset < j) {
                    trackBundle = trackBundleValueAt;
                    j = currentSampleOffset;
                }
            }
        }
        return trackBundle;
    }

    private static DrmInitData getDrmInitDataFromAtoms(List<Atom.LeafAtom> leafChildren) {
        int size = leafChildren.size();
        ArrayList arrayList = null;
        for (int i = 0; i < size; i++) {
            Atom.LeafAtom leafAtom = leafChildren.get(i);
            if (leafAtom.type == 1886614376) {
                if (arrayList == null) {
                    arrayList = new ArrayList();
                }
                byte[] data = leafAtom.data.getData();
                UUID uuid = PsshAtomUtil.parseUuid(data);
                if (uuid == null) {
                    Log.w(TAG, "Skipped pssh atom (failed to extract uuid)");
                } else {
                    arrayList.add(new DrmInitData.SchemeData(uuid, MimeTypes.VIDEO_MP4, data));
                }
            }
        }
        if (arrayList == null) {
            return null;
        }
        return new DrmInitData(arrayList);
    }

    private static final class MetadataSampleInfo {
        public final long presentationTimeDeltaUs;
        public final int size;

        public MetadataSampleInfo(long presentationTimeDeltaUs, int size) {
            this.presentationTimeDeltaUs = presentationTimeDeltaUs;
            this.size = size;
        }
    }

    private static final class TrackBundle {
        private static final int SINGLE_SUBSAMPLE_ENCRYPTION_DATA_LENGTH = 8;
        public int currentSampleInTrackRun;
        public int currentSampleIndex;
        public int currentTrackRunIndex;
        private boolean currentlyInFragment;
        public DefaultSampleValues defaultSampleValues;
        public int firstSampleToOutputIndex;
        public TrackSampleTable moovSampleTable;
        public final TrackOutput output;
        public final TrackFragment fragment = new TrackFragment();
        public final ParsableByteArray scratch = new ParsableByteArray();
        private final ParsableByteArray encryptionSignalByte = new ParsableByteArray(1);
        private final ParsableByteArray defaultInitializationVector = new ParsableByteArray();

        public TrackBundle(TrackOutput output, TrackSampleTable moovSampleTable, DefaultSampleValues defaultSampleValues) {
            this.output = output;
            this.moovSampleTable = moovSampleTable;
            this.defaultSampleValues = defaultSampleValues;
            reset(moovSampleTable, defaultSampleValues);
        }

        public void reset(TrackSampleTable moovSampleTable, DefaultSampleValues defaultSampleValues) {
            this.moovSampleTable = moovSampleTable;
            this.defaultSampleValues = defaultSampleValues;
            this.output.format(moovSampleTable.track.format);
            resetFragmentInfo();
        }

        public void updateDrmInitData(DrmInitData drmInitData) {
            TrackEncryptionBox sampleDescriptionEncryptionBox = this.moovSampleTable.track.getSampleDescriptionEncryptionBox(((DefaultSampleValues) Util.castNonNull(this.fragment.header)).sampleDescriptionIndex);
            this.output.format(this.moovSampleTable.track.format.buildUpon().setDrmInitData(drmInitData.copyWithSchemeType(sampleDescriptionEncryptionBox != null ? sampleDescriptionEncryptionBox.schemeType : null)).build());
        }

        public void resetFragmentInfo() {
            this.fragment.reset();
            this.currentSampleIndex = 0;
            this.currentTrackRunIndex = 0;
            this.currentSampleInTrackRun = 0;
            this.firstSampleToOutputIndex = 0;
            this.currentlyInFragment = false;
        }

        public void seek(long timeUs) {
            for (int i = this.currentSampleIndex; i < this.fragment.sampleCount && this.fragment.getSamplePresentationTimeUs(i) < timeUs; i++) {
                if (this.fragment.sampleIsSyncFrameTable[i]) {
                    this.firstSampleToOutputIndex = i;
                }
            }
        }

        public long getCurrentSamplePresentationTimeUs() {
            if (!this.currentlyInFragment) {
                return this.moovSampleTable.timestampsUs[this.currentSampleIndex];
            }
            return this.fragment.getSamplePresentationTimeUs(this.currentSampleIndex);
        }

        public long getCurrentSampleOffset() {
            if (!this.currentlyInFragment) {
                return this.moovSampleTable.offsets[this.currentSampleIndex];
            }
            return this.fragment.trunDataPosition[this.currentTrackRunIndex];
        }

        public int getCurrentSampleSize() {
            if (!this.currentlyInFragment) {
                return this.moovSampleTable.sizes[this.currentSampleIndex];
            }
            return this.fragment.sampleSizeTable[this.currentSampleIndex];
        }

        public int getCurrentSampleFlags() {
            int i;
            if (!this.currentlyInFragment) {
                i = this.moovSampleTable.flags[this.currentSampleIndex];
            } else {
                i = this.fragment.sampleIsSyncFrameTable[this.currentSampleIndex] ? 1 : 0;
            }
            return getEncryptionBoxIfEncrypted() != null ? i | 1073741824 : i;
        }

        public boolean next() {
            this.currentSampleIndex++;
            if (!this.currentlyInFragment) {
                return false;
            }
            int i = this.currentSampleInTrackRun + 1;
            this.currentSampleInTrackRun = i;
            int[] iArr = this.fragment.trunLength;
            int i2 = this.currentTrackRunIndex;
            if (i != iArr[i2]) {
                return true;
            }
            this.currentTrackRunIndex = i2 + 1;
            this.currentSampleInTrackRun = 0;
            return false;
        }

        public int outputSampleEncryptionData(int sampleSize, int clearHeaderSize) {
            ParsableByteArray parsableByteArray;
            int length;
            TrackEncryptionBox encryptionBoxIfEncrypted = getEncryptionBoxIfEncrypted();
            if (encryptionBoxIfEncrypted == null) {
                return 0;
            }
            if (encryptionBoxIfEncrypted.perSampleIvSize != 0) {
                parsableByteArray = this.fragment.sampleEncryptionData;
                length = encryptionBoxIfEncrypted.perSampleIvSize;
            } else {
                byte[] bArr = (byte[]) Util.castNonNull(encryptionBoxIfEncrypted.defaultInitializationVector);
                this.defaultInitializationVector.reset(bArr, bArr.length);
                parsableByteArray = this.defaultInitializationVector;
                length = bArr.length;
            }
            boolean zSampleHasSubsampleEncryptionTable = this.fragment.sampleHasSubsampleEncryptionTable(this.currentSampleIndex);
            boolean z = zSampleHasSubsampleEncryptionTable || clearHeaderSize != 0;
            this.encryptionSignalByte.getData()[0] = (byte) ((z ? 128 : 0) | length);
            this.encryptionSignalByte.setPosition(0);
            this.output.sampleData(this.encryptionSignalByte, 1, 1);
            this.output.sampleData(parsableByteArray, length, 1);
            if (!z) {
                return length + 1;
            }
            if (!zSampleHasSubsampleEncryptionTable) {
                this.scratch.reset(8);
                byte[] data = this.scratch.getData();
                data[0] = 0;
                data[1] = 1;
                data[2] = (byte) ((clearHeaderSize >> 8) & 255);
                data[3] = (byte) (clearHeaderSize & 255);
                data[4] = (byte) ((sampleSize >> 24) & 255);
                data[5] = (byte) ((sampleSize >> 16) & 255);
                data[6] = (byte) ((sampleSize >> 8) & 255);
                data[7] = (byte) (sampleSize & 255);
                this.output.sampleData(this.scratch, 8, 1);
                return length + 1 + 8;
            }
            ParsableByteArray parsableByteArray2 = this.fragment.sampleEncryptionData;
            int unsignedShort = parsableByteArray2.readUnsignedShort();
            parsableByteArray2.skipBytes(-2);
            int i = (unsignedShort * 6) + 2;
            if (clearHeaderSize != 0) {
                this.scratch.reset(i);
                byte[] data2 = this.scratch.getData();
                parsableByteArray2.readBytes(data2, 0, i);
                int i2 = (((data2[2] & 255) << 8) | (data2[3] & 255)) + clearHeaderSize;
                data2[2] = (byte) ((i2 >> 8) & 255);
                data2[3] = (byte) (i2 & 255);
                parsableByteArray2 = this.scratch;
            }
            this.output.sampleData(parsableByteArray2, i, 1);
            return length + 1 + i;
        }

        public void skipSampleEncryptionData() {
            TrackEncryptionBox encryptionBoxIfEncrypted = getEncryptionBoxIfEncrypted();
            if (encryptionBoxIfEncrypted == null) {
                return;
            }
            ParsableByteArray parsableByteArray = this.fragment.sampleEncryptionData;
            if (encryptionBoxIfEncrypted.perSampleIvSize != 0) {
                parsableByteArray.skipBytes(encryptionBoxIfEncrypted.perSampleIvSize);
            }
            if (this.fragment.sampleHasSubsampleEncryptionTable(this.currentSampleIndex)) {
                parsableByteArray.skipBytes(parsableByteArray.readUnsignedShort() * 6);
            }
        }

        public TrackEncryptionBox getEncryptionBoxIfEncrypted() {
            TrackEncryptionBox sampleDescriptionEncryptionBox;
            if (!this.currentlyInFragment) {
                return null;
            }
            int i = ((DefaultSampleValues) Util.castNonNull(this.fragment.header)).sampleDescriptionIndex;
            if (this.fragment.trackEncryptionBox != null) {
                sampleDescriptionEncryptionBox = this.fragment.trackEncryptionBox;
            } else {
                sampleDescriptionEncryptionBox = this.moovSampleTable.track.getSampleDescriptionEncryptionBox(i);
            }
            if (sampleDescriptionEncryptionBox == null || !sampleDescriptionEncryptionBox.isEncrypted) {
                return null;
            }
            return sampleDescriptionEncryptionBox;
        }
    }
}
