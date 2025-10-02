package com.google.android.exoplayer2.extractor.mp4;

import android.util.Pair;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.audio.Ac4Util;
import com.google.android.exoplayer2.extractor.Extractor;
import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.extractor.GaplessInfoHolder;
import com.google.android.exoplayer2.extractor.PositionHolder;
import com.google.android.exoplayer2.extractor.SeekMap;
import com.google.android.exoplayer2.extractor.SeekPoint;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.extractor.mp4.Atom;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.metadata.mp4.MotionPhotoMetadata;
import com.google.android.exoplayer2.upstream.DataReader;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.NalUnitUtil;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.Util;
import com.google.common.base.Function;
import java.io.IOException;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import org.checkerframework.checker.nullness.qual.RequiresNonNull;

/* loaded from: classes.dex */
public final class Mp4Extractor implements Extractor, SeekMap {
    public static final ExtractorsFactory FACTORY = new ExtractorsFactory() { // from class: com.google.android.exoplayer2.extractor.mp4.Mp4Extractor$$ExternalSyntheticLambda0
        @Override // com.google.android.exoplayer2.extractor.ExtractorsFactory
        public final Extractor[] createExtractors() {
            return Mp4Extractor.lambda$static$0();
        }
    };
    private static final int FILE_TYPE_HEIC = 2;
    private static final int FILE_TYPE_MP4 = 0;
    private static final int FILE_TYPE_QUICKTIME = 1;
    public static final int FLAG_READ_MOTION_PHOTO_METADATA = 2;
    public static final int FLAG_READ_SEF_DATA = 4;
    public static final int FLAG_WORKAROUND_IGNORE_EDIT_LISTS = 1;
    private static final long MAXIMUM_READ_AHEAD_BYTES_STREAM = 10485760;
    private static final long RELOAD_MINIMUM_SEEK_DISTANCE = 262144;
    private static final int STATE_READING_ATOM_HEADER = 0;
    private static final int STATE_READING_ATOM_PAYLOAD = 1;
    private static final int STATE_READING_SAMPLE = 2;
    private static final int STATE_READING_SEF = 3;
    private long[][] accumulatedSampleSizes;
    private ParsableByteArray atomData;
    private final ParsableByteArray atomHeader;
    private int atomHeaderBytesRead;
    private long atomSize;
    private int atomType;
    private final ArrayDeque<Atom.ContainerAtom> containerAtoms;
    private long durationUs;
    private ExtractorOutput extractorOutput;
    private int fileType;
    private int firstVideoTrackIndex;
    private final int flags;
    private MotionPhotoMetadata motionPhotoMetadata;
    private final ParsableByteArray nalLength;
    private final ParsableByteArray nalStartCode;
    private int parserState;
    private int sampleBytesRead;
    private int sampleBytesWritten;
    private int sampleCurrentNalBytesRemaining;
    private int sampleTrackIndex;
    private final ParsableByteArray scratch;
    private final SefReader sefReader;
    private final List<Metadata.Entry> slowMotionMetadataEntries;
    private Mp4Track[] tracks;

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface Flags {
    }

    private static int brandToFileType(int brand) {
        if (brand != 1751476579) {
            return brand != 1903435808 ? 0 : 1;
        }
        return 2;
    }

    static /* synthetic */ Track lambda$processMoovAtom$1(Track track) {
        return track;
    }

    private static boolean shouldParseContainerAtom(int atom) {
        return atom == 1836019574 || atom == 1953653099 || atom == 1835297121 || atom == 1835626086 || atom == 1937007212 || atom == 1701082227 || atom == 1835365473;
    }

    private static boolean shouldParseLeafAtom(int atom) {
        return atom == 1835296868 || atom == 1836476516 || atom == 1751411826 || atom == 1937011556 || atom == 1937011827 || atom == 1937011571 || atom == 1668576371 || atom == 1701606260 || atom == 1937011555 || atom == 1937011578 || atom == 1937013298 || atom == 1937007471 || atom == 1668232756 || atom == 1953196132 || atom == 1718909296 || atom == 1969517665 || atom == 1801812339 || atom == 1768715124;
    }

    @Override // com.google.android.exoplayer2.extractor.SeekMap
    public boolean isSeekable() {
        return true;
    }

    @Override // com.google.android.exoplayer2.extractor.Extractor
    public void release() {
    }

    static /* synthetic */ Extractor[] lambda$static$0() {
        return new Extractor[]{new Mp4Extractor()};
    }

    public Mp4Extractor() {
        this(0);
    }

    public Mp4Extractor(int flags) {
        this.flags = flags;
        this.parserState = (flags & 4) != 0 ? 3 : 0;
        this.sefReader = new SefReader();
        this.slowMotionMetadataEntries = new ArrayList();
        this.atomHeader = new ParsableByteArray(16);
        this.containerAtoms = new ArrayDeque<>();
        this.nalStartCode = new ParsableByteArray(NalUnitUtil.NAL_START_CODE);
        this.nalLength = new ParsableByteArray(4);
        this.scratch = new ParsableByteArray();
        this.sampleTrackIndex = -1;
    }

    @Override // com.google.android.exoplayer2.extractor.Extractor
    public boolean sniff(ExtractorInput input) throws IOException {
        return Sniffer.sniffUnfragmented(input, (this.flags & 2) != 0);
    }

    @Override // com.google.android.exoplayer2.extractor.Extractor
    public void init(ExtractorOutput output) {
        this.extractorOutput = output;
    }

    @Override // com.google.android.exoplayer2.extractor.Extractor
    public void seek(long position, long timeUs) {
        this.containerAtoms.clear();
        this.atomHeaderBytesRead = 0;
        this.sampleTrackIndex = -1;
        this.sampleBytesRead = 0;
        this.sampleBytesWritten = 0;
        this.sampleCurrentNalBytesRemaining = 0;
        if (position == 0) {
            if (this.parserState != 3) {
                enterReadingAtomHeaderState();
                return;
            } else {
                this.sefReader.reset();
                this.slowMotionMetadataEntries.clear();
                return;
            }
        }
        if (this.tracks != null) {
            updateSampleIndices(timeUs);
        }
    }

    @Override // com.google.android.exoplayer2.extractor.Extractor
    public int read(ExtractorInput input, PositionHolder seekPosition) throws IOException {
        while (true) {
            int i = this.parserState;
            if (i != 0) {
                if (i != 1) {
                    if (i == 2) {
                        return readSample(input, seekPosition);
                    }
                    if (i == 3) {
                        return readSefData(input, seekPosition);
                    }
                    throw new IllegalStateException();
                }
                if (readAtomPayload(input, seekPosition)) {
                    return 1;
                }
            } else if (!readAtomHeader(input)) {
                return -1;
            }
        }
    }

    @Override // com.google.android.exoplayer2.extractor.SeekMap
    public long getDurationUs() {
        return this.durationUs;
    }

    @Override // com.google.android.exoplayer2.extractor.SeekMap
    public SeekMap.SeekPoints getSeekPoints(long timeUs) {
        long j;
        long jMaybeAdjustSeekOffset;
        long j2;
        long j3;
        int indexOfLaterOrEqualSynchronizationSample;
        if (((Mp4Track[]) Assertions.checkNotNull(this.tracks)).length == 0) {
            return new SeekMap.SeekPoints(SeekPoint.START);
        }
        int i = this.firstVideoTrackIndex;
        if (i != -1) {
            TrackSampleTable trackSampleTable = this.tracks[i].sampleTable;
            int synchronizationSampleIndex = getSynchronizationSampleIndex(trackSampleTable, timeUs);
            if (synchronizationSampleIndex == -1) {
                return new SeekMap.SeekPoints(SeekPoint.START);
            }
            long j4 = trackSampleTable.timestampsUs[synchronizationSampleIndex];
            j = trackSampleTable.offsets[synchronizationSampleIndex];
            if (j4 >= timeUs || synchronizationSampleIndex >= trackSampleTable.sampleCount - 1 || (indexOfLaterOrEqualSynchronizationSample = trackSampleTable.getIndexOfLaterOrEqualSynchronizationSample(timeUs)) == -1 || indexOfLaterOrEqualSynchronizationSample == synchronizationSampleIndex) {
                j3 = -1;
                j2 = -9223372036854775807L;
            } else {
                j2 = trackSampleTable.timestampsUs[indexOfLaterOrEqualSynchronizationSample];
                j3 = trackSampleTable.offsets[indexOfLaterOrEqualSynchronizationSample];
            }
            jMaybeAdjustSeekOffset = j3;
            timeUs = j4;
        } else {
            j = Long.MAX_VALUE;
            jMaybeAdjustSeekOffset = -1;
            j2 = -9223372036854775807L;
        }
        int i2 = 0;
        while (true) {
            Mp4Track[] mp4TrackArr = this.tracks;
            if (i2 >= mp4TrackArr.length) {
                break;
            }
            if (i2 != this.firstVideoTrackIndex) {
                TrackSampleTable trackSampleTable2 = mp4TrackArr[i2].sampleTable;
                long jMaybeAdjustSeekOffset2 = maybeAdjustSeekOffset(trackSampleTable2, timeUs, j);
                if (j2 != C.TIME_UNSET) {
                    jMaybeAdjustSeekOffset = maybeAdjustSeekOffset(trackSampleTable2, j2, jMaybeAdjustSeekOffset);
                }
                j = jMaybeAdjustSeekOffset2;
            }
            i2++;
        }
        SeekPoint seekPoint = new SeekPoint(timeUs, j);
        if (j2 == C.TIME_UNSET) {
            return new SeekMap.SeekPoints(seekPoint);
        }
        return new SeekMap.SeekPoints(seekPoint, new SeekPoint(j2, jMaybeAdjustSeekOffset));
    }

    private void enterReadingAtomHeaderState() {
        this.parserState = 0;
        this.atomHeaderBytesRead = 0;
    }

    private boolean readAtomHeader(ExtractorInput input) throws IOException {
        Atom.ContainerAtom containerAtomPeek;
        if (this.atomHeaderBytesRead == 0) {
            if (!input.readFully(this.atomHeader.getData(), 0, 8, true)) {
                processEndOfStreamReadingAtomHeader();
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
            if (length == -1 && (containerAtomPeek = this.containerAtoms.peek()) != null) {
                length = containerAtomPeek.endPosition;
            }
            if (length != -1) {
                this.atomSize = (length - input.getPosition()) + this.atomHeaderBytesRead;
            }
        }
        if (this.atomSize < this.atomHeaderBytesRead) {
            throw ParserException.createForUnsupportedContainerFeature("Atom size less than header length (unsupported).");
        }
        if (shouldParseContainerAtom(this.atomType)) {
            long position = input.getPosition();
            long j2 = this.atomSize;
            int i = this.atomHeaderBytesRead;
            long j3 = (position + j2) - i;
            if (j2 != i && this.atomType == 1835365473) {
                maybeSkipRemainingMetaAtomHeaderBytes(input);
            }
            this.containerAtoms.push(new Atom.ContainerAtom(this.atomType, j3));
            if (this.atomSize == this.atomHeaderBytesRead) {
                processAtomEnded(j3);
            } else {
                enterReadingAtomHeaderState();
            }
        } else if (shouldParseLeafAtom(this.atomType)) {
            Assertions.checkState(this.atomHeaderBytesRead == 8);
            Assertions.checkState(this.atomSize <= 2147483647L);
            ParsableByteArray parsableByteArray = new ParsableByteArray((int) this.atomSize);
            System.arraycopy(this.atomHeader.getData(), 0, parsableByteArray.getData(), 0, 8);
            this.atomData = parsableByteArray;
            this.parserState = 1;
        } else {
            processUnparsedAtom(input.getPosition() - this.atomHeaderBytesRead);
            this.atomData = null;
            this.parserState = 1;
        }
        return true;
    }

    private boolean readAtomPayload(ExtractorInput input, PositionHolder positionHolder) throws IOException {
        boolean z;
        long j = this.atomSize - this.atomHeaderBytesRead;
        long position = input.getPosition() + j;
        ParsableByteArray parsableByteArray = this.atomData;
        if (parsableByteArray != null) {
            input.readFully(parsableByteArray.getData(), this.atomHeaderBytesRead, (int) j);
            if (this.atomType == 1718909296) {
                this.fileType = processFtypAtom(parsableByteArray);
            } else if (!this.containerAtoms.isEmpty()) {
                this.containerAtoms.peek().add(new Atom.LeafAtom(this.atomType, parsableByteArray));
            }
        } else if (j < 262144) {
            input.skipFully((int) j);
        } else {
            positionHolder.position = input.getPosition() + j;
            z = true;
            processAtomEnded(position);
            return (z || this.parserState == 2) ? false : true;
        }
        z = false;
        processAtomEnded(position);
        if (z) {
        }
    }

    private int readSefData(ExtractorInput input, PositionHolder seekPosition) throws IOException {
        int i = this.sefReader.read(input, seekPosition, this.slowMotionMetadataEntries);
        if (i == 1 && seekPosition.position == 0) {
            enterReadingAtomHeaderState();
        }
        return i;
    }

    private void processAtomEnded(long atomEndPosition) throws ParserException {
        while (!this.containerAtoms.isEmpty() && this.containerAtoms.peek().endPosition == atomEndPosition) {
            Atom.ContainerAtom containerAtomPop = this.containerAtoms.pop();
            if (containerAtomPop.type == 1836019574) {
                processMoovAtom(containerAtomPop);
                this.containerAtoms.clear();
                this.parserState = 2;
            } else if (!this.containerAtoms.isEmpty()) {
                this.containerAtoms.peek().add(containerAtomPop);
            }
        }
        if (this.parserState != 2) {
            enterReadingAtomHeaderState();
        }
    }

    private void processMoovAtom(Atom.ContainerAtom moov) throws ParserException {
        Metadata metadata;
        Metadata metadata2;
        ArrayList arrayList;
        List<TrackSampleTable> list;
        int i;
        int size;
        ArrayList arrayList2 = new ArrayList();
        boolean z = this.fileType == 1;
        GaplessInfoHolder gaplessInfoHolder = new GaplessInfoHolder();
        Atom.LeafAtom leafAtomOfType = moov.getLeafAtomOfType(Atom.TYPE_udta);
        if (leafAtomOfType != null) {
            Pair<Metadata, Metadata> udta = AtomParsers.parseUdta(leafAtomOfType);
            Metadata metadata3 = (Metadata) udta.first;
            Metadata metadata4 = (Metadata) udta.second;
            if (metadata3 != null) {
                gaplessInfoHolder.setFromMetadata(metadata3);
            }
            metadata = metadata4;
            metadata2 = metadata3;
        } else {
            metadata = null;
            metadata2 = null;
        }
        Atom.ContainerAtom containerAtomOfType = moov.getContainerAtomOfType(Atom.TYPE_meta);
        Metadata mdtaFromMeta = containerAtomOfType != null ? AtomParsers.parseMdtaFromMeta(containerAtomOfType) : null;
        List<TrackSampleTable> traks = AtomParsers.parseTraks(moov, gaplessInfoHolder, C.TIME_UNSET, null, (this.flags & 1) != 0, z, new Function() { // from class: com.google.android.exoplayer2.extractor.mp4.Mp4Extractor$$ExternalSyntheticLambda1
            @Override // com.google.common.base.Function
            public final Object apply(Object obj) {
                return Mp4Extractor.lambda$processMoovAtom$1((Track) obj);
            }
        });
        ExtractorOutput extractorOutput = (ExtractorOutput) Assertions.checkNotNull(this.extractorOutput);
        int size2 = traks.size();
        int i2 = 0;
        int i3 = -1;
        long j = C.TIME_UNSET;
        while (i2 < size2) {
            TrackSampleTable trackSampleTable = traks.get(i2);
            if (trackSampleTable.sampleCount == 0) {
                list = traks;
                i = size2;
                arrayList = arrayList2;
            } else {
                Track track = trackSampleTable.track;
                int i4 = i3;
                arrayList = arrayList2;
                long j2 = track.durationUs != C.TIME_UNSET ? track.durationUs : trackSampleTable.durationUs;
                long jMax = Math.max(j, j2);
                list = traks;
                i = size2;
                Mp4Track mp4Track = new Mp4Track(track, trackSampleTable, extractorOutput.track(i2, track.type));
                int i5 = trackSampleTable.maximumSize + 30;
                Format.Builder builderBuildUpon = track.format.buildUpon();
                builderBuildUpon.setMaxInputSize(i5);
                if (track.type == 2 && j2 > 0 && trackSampleTable.sampleCount > 1) {
                    builderBuildUpon.setFrameRate(trackSampleTable.sampleCount / (j2 / 1000000.0f));
                }
                MetadataUtil.setFormatGaplessInfo(track.type, gaplessInfoHolder, builderBuildUpon);
                int i6 = track.type;
                Metadata[] metadataArr = new Metadata[2];
                metadataArr[0] = metadata;
                metadataArr[1] = this.slowMotionMetadataEntries.isEmpty() ? null : new Metadata(this.slowMotionMetadataEntries);
                MetadataUtil.setFormatMetadata(i6, metadata2, mdtaFromMeta, builderBuildUpon, metadataArr);
                mp4Track.trackOutput.format(builderBuildUpon.build());
                if (track.type == 2) {
                    size = i4;
                    if (size == -1) {
                        size = arrayList.size();
                    }
                } else {
                    size = i4;
                }
                i3 = size;
                arrayList.add(mp4Track);
                j = jMax;
            }
            i2++;
            arrayList2 = arrayList;
            traks = list;
            size2 = i;
        }
        this.firstVideoTrackIndex = i3;
        this.durationUs = j;
        Mp4Track[] mp4TrackArr = (Mp4Track[]) arrayList2.toArray(new Mp4Track[0]);
        this.tracks = mp4TrackArr;
        this.accumulatedSampleSizes = calculateAccumulatedSampleSizes(mp4TrackArr);
        extractorOutput.endTracks();
        extractorOutput.seekMap(this);
    }

    private int readSample(ExtractorInput input, PositionHolder positionHolder) throws IOException {
        long position = input.getPosition();
        if (this.sampleTrackIndex == -1) {
            int trackIndexOfNextReadSample = getTrackIndexOfNextReadSample(position);
            this.sampleTrackIndex = trackIndexOfNextReadSample;
            if (trackIndexOfNextReadSample == -1) {
                return -1;
            }
        }
        Mp4Track mp4Track = ((Mp4Track[]) Util.castNonNull(this.tracks))[this.sampleTrackIndex];
        TrackOutput trackOutput = mp4Track.trackOutput;
        int i = mp4Track.sampleIndex;
        long j = mp4Track.sampleTable.offsets[i];
        int i2 = mp4Track.sampleTable.sizes[i];
        long j2 = (j - position) + this.sampleBytesRead;
        if (j2 < 0 || j2 >= 262144) {
            positionHolder.position = j;
            return 1;
        }
        if (mp4Track.track.sampleTransformation == 1) {
            j2 += 8;
            i2 -= 8;
        }
        input.skipFully((int) j2);
        if (mp4Track.track.nalUnitLengthFieldLength != 0) {
            byte[] data = this.nalLength.getData();
            data[0] = 0;
            data[1] = 0;
            data[2] = 0;
            int i3 = mp4Track.track.nalUnitLengthFieldLength;
            int i4 = 4 - mp4Track.track.nalUnitLengthFieldLength;
            while (this.sampleBytesWritten < i2) {
                int i5 = this.sampleCurrentNalBytesRemaining;
                if (i5 == 0) {
                    input.readFully(data, i4, i3);
                    this.sampleBytesRead += i3;
                    this.nalLength.setPosition(0);
                    int i6 = this.nalLength.readInt();
                    if (i6 < 0) {
                        throw ParserException.createForMalformedContainer("Invalid NAL length", null);
                    }
                    this.sampleCurrentNalBytesRemaining = i6;
                    this.nalStartCode.setPosition(0);
                    trackOutput.sampleData(this.nalStartCode, 4);
                    this.sampleBytesWritten += 4;
                    i2 += i4;
                } else {
                    int iSampleData = trackOutput.sampleData((DataReader) input, i5, false);
                    this.sampleBytesRead += iSampleData;
                    this.sampleBytesWritten += iSampleData;
                    this.sampleCurrentNalBytesRemaining -= iSampleData;
                }
            }
        } else {
            if (MimeTypes.AUDIO_AC4.equals(mp4Track.track.format.sampleMimeType)) {
                if (this.sampleBytesWritten == 0) {
                    Ac4Util.getAc4SampleHeader(i2, this.scratch);
                    trackOutput.sampleData(this.scratch, 7);
                    this.sampleBytesWritten += 7;
                }
                i2 += 7;
            }
            while (true) {
                int i7 = this.sampleBytesWritten;
                if (i7 >= i2) {
                    break;
                }
                int iSampleData2 = trackOutput.sampleData((DataReader) input, i2 - i7, false);
                this.sampleBytesRead += iSampleData2;
                this.sampleBytesWritten += iSampleData2;
                this.sampleCurrentNalBytesRemaining -= iSampleData2;
            }
        }
        trackOutput.sampleMetadata(mp4Track.sampleTable.timestampsUs[i], mp4Track.sampleTable.flags[i], i2, 0, null);
        mp4Track.sampleIndex++;
        this.sampleTrackIndex = -1;
        this.sampleBytesRead = 0;
        this.sampleBytesWritten = 0;
        this.sampleCurrentNalBytesRemaining = 0;
        return 0;
    }

    private int getTrackIndexOfNextReadSample(long inputPosition) {
        int i = -1;
        int i2 = -1;
        long j = Long.MAX_VALUE;
        boolean z = true;
        long j2 = Long.MAX_VALUE;
        boolean z2 = true;
        long j3 = Long.MAX_VALUE;
        for (int i3 = 0; i3 < ((Mp4Track[]) Util.castNonNull(this.tracks)).length; i3++) {
            Mp4Track mp4Track = this.tracks[i3];
            int i4 = mp4Track.sampleIndex;
            if (i4 != mp4Track.sampleTable.sampleCount) {
                long j4 = mp4Track.sampleTable.offsets[i4];
                long j5 = ((long[][]) Util.castNonNull(this.accumulatedSampleSizes))[i3][i4];
                long j6 = j4 - inputPosition;
                boolean z3 = j6 < 0 || j6 >= 262144;
                if ((!z3 && z2) || (z3 == z2 && j6 < j3)) {
                    z2 = z3;
                    j3 = j6;
                    i2 = i3;
                    j2 = j5;
                }
                if (j5 < j) {
                    z = z3;
                    i = i3;
                    j = j5;
                }
            }
        }
        return (j == Long.MAX_VALUE || !z || j2 < j + MAXIMUM_READ_AHEAD_BYTES_STREAM) ? i2 : i;
    }

    @RequiresNonNull({"tracks"})
    private void updateSampleIndices(long timeUs) {
        for (Mp4Track mp4Track : this.tracks) {
            TrackSampleTable trackSampleTable = mp4Track.sampleTable;
            int indexOfEarlierOrEqualSynchronizationSample = trackSampleTable.getIndexOfEarlierOrEqualSynchronizationSample(timeUs);
            if (indexOfEarlierOrEqualSynchronizationSample == -1) {
                indexOfEarlierOrEqualSynchronizationSample = trackSampleTable.getIndexOfLaterOrEqualSynchronizationSample(timeUs);
            }
            mp4Track.sampleIndex = indexOfEarlierOrEqualSynchronizationSample;
        }
    }

    private void processEndOfStreamReadingAtomHeader() {
        if (this.fileType != 2 || (this.flags & 2) == 0) {
            return;
        }
        ExtractorOutput extractorOutput = (ExtractorOutput) Assertions.checkNotNull(this.extractorOutput);
        extractorOutput.track(0, 4).format(new Format.Builder().setMetadata(this.motionPhotoMetadata == null ? null : new Metadata(this.motionPhotoMetadata)).build());
        extractorOutput.endTracks();
        extractorOutput.seekMap(new SeekMap.Unseekable(C.TIME_UNSET));
    }

    private void maybeSkipRemainingMetaAtomHeaderBytes(ExtractorInput input) throws IOException {
        this.scratch.reset(8);
        input.peekFully(this.scratch.getData(), 0, 8);
        AtomParsers.maybeSkipRemainingMetaAtomHeaderBytes(this.scratch);
        input.skipFully(this.scratch.getPosition());
        input.resetPeekPosition();
    }

    private void processUnparsedAtom(long atomStartPosition) {
        if (this.atomType == 1836086884) {
            int i = this.atomHeaderBytesRead;
            this.motionPhotoMetadata = new MotionPhotoMetadata(0L, atomStartPosition, C.TIME_UNSET, atomStartPosition + i, this.atomSize - i);
        }
    }

    private static long[][] calculateAccumulatedSampleSizes(Mp4Track[] tracks) {
        long[][] jArr = new long[tracks.length][];
        int[] iArr = new int[tracks.length];
        long[] jArr2 = new long[tracks.length];
        boolean[] zArr = new boolean[tracks.length];
        for (int i = 0; i < tracks.length; i++) {
            jArr[i] = new long[tracks[i].sampleTable.sampleCount];
            jArr2[i] = tracks[i].sampleTable.timestampsUs[0];
        }
        long j = 0;
        int i2 = 0;
        while (i2 < tracks.length) {
            long j2 = Long.MAX_VALUE;
            int i3 = -1;
            for (int i4 = 0; i4 < tracks.length; i4++) {
                if (!zArr[i4]) {
                    long j3 = jArr2[i4];
                    if (j3 <= j2) {
                        i3 = i4;
                        j2 = j3;
                    }
                }
            }
            int i5 = iArr[i3];
            jArr[i3][i5] = j;
            j += tracks[i3].sampleTable.sizes[i5];
            int i6 = i5 + 1;
            iArr[i3] = i6;
            if (i6 < jArr[i3].length) {
                jArr2[i3] = tracks[i3].sampleTable.timestampsUs[i6];
            } else {
                zArr[i3] = true;
                i2++;
            }
        }
        return jArr;
    }

    private static long maybeAdjustSeekOffset(TrackSampleTable sampleTable, long seekTimeUs, long offset) {
        int synchronizationSampleIndex = getSynchronizationSampleIndex(sampleTable, seekTimeUs);
        return synchronizationSampleIndex == -1 ? offset : Math.min(sampleTable.offsets[synchronizationSampleIndex], offset);
    }

    private static int getSynchronizationSampleIndex(TrackSampleTable sampleTable, long timeUs) {
        int indexOfEarlierOrEqualSynchronizationSample = sampleTable.getIndexOfEarlierOrEqualSynchronizationSample(timeUs);
        return indexOfEarlierOrEqualSynchronizationSample == -1 ? sampleTable.getIndexOfLaterOrEqualSynchronizationSample(timeUs) : indexOfEarlierOrEqualSynchronizationSample;
    }

    private static int processFtypAtom(ParsableByteArray atomData) {
        atomData.setPosition(8);
        int iBrandToFileType = brandToFileType(atomData.readInt());
        if (iBrandToFileType != 0) {
            return iBrandToFileType;
        }
        atomData.skipBytes(4);
        while (atomData.bytesLeft() > 0) {
            int iBrandToFileType2 = brandToFileType(atomData.readInt());
            if (iBrandToFileType2 != 0) {
                return iBrandToFileType2;
            }
        }
        return 0;
    }

    private static final class Mp4Track {
        public int sampleIndex;
        public final TrackSampleTable sampleTable;
        public final Track track;
        public final TrackOutput trackOutput;

        public Mp4Track(Track track, TrackSampleTable sampleTable, TrackOutput trackOutput) {
            this.track = track;
            this.sampleTable = sampleTable;
            this.trackOutput = trackOutput;
        }
    }
}
