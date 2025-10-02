package com.google.android.exoplayer2.extractor.jpeg;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.extractor.Extractor;
import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.PositionHolder;
import com.google.android.exoplayer2.extractor.SeekMap;
import com.google.android.exoplayer2.extractor.mp4.Mp4Extractor;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.metadata.mp4.MotionPhotoMetadata;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.ParsableByteArray;
import java.io.IOException;

/* loaded from: classes.dex */
public final class JpegExtractor implements Extractor {
    private static final long EXIF_HEADER = 1165519206;
    private static final int EXIF_ID_CODE_LENGTH = 6;
    private static final String HEADER_XMP_APP1 = "http://ns.adobe.com/xap/1.0/";
    private static final int IMAGE_TRACK_ID = 1024;
    private static final int MARKER_APP0 = 65504;
    private static final int MARKER_APP1 = 65505;
    private static final int MARKER_SOI = 65496;
    private static final int MARKER_SOS = 65498;
    private static final int STATE_ENDED = 6;
    private static final int STATE_READING_MARKER = 0;
    private static final int STATE_READING_MOTION_PHOTO_VIDEO = 5;
    private static final int STATE_READING_SEGMENT = 2;
    private static final int STATE_READING_SEGMENT_LENGTH = 1;
    private static final int STATE_SNIFFING_MOTION_PHOTO_VIDEO = 4;
    private ExtractorOutput extractorOutput;
    private ExtractorInput lastExtractorInput;
    private int marker;
    private MotionPhotoMetadata motionPhotoMetadata;
    private Mp4Extractor mp4Extractor;
    private StartOffsetExtractorInput mp4ExtractorStartOffsetExtractorInput;
    private int segmentLength;
    private int state;
    private final ParsableByteArray scratch = new ParsableByteArray(6);
    private long mp4StartPosition = -1;

    @Override // com.google.android.exoplayer2.extractor.Extractor
    public boolean sniff(ExtractorInput input) throws IOException {
        if (peekMarker(input) != MARKER_SOI) {
            return false;
        }
        int iPeekMarker = peekMarker(input);
        this.marker = iPeekMarker;
        if (iPeekMarker == MARKER_APP0) {
            advancePeekPositionToNextSegment(input);
            this.marker = peekMarker(input);
        }
        if (this.marker != MARKER_APP1) {
            return false;
        }
        input.advancePeekPosition(2);
        this.scratch.reset(6);
        input.peekFully(this.scratch.getData(), 0, 6);
        return this.scratch.readUnsignedInt() == EXIF_HEADER && this.scratch.readUnsignedShort() == 0;
    }

    @Override // com.google.android.exoplayer2.extractor.Extractor
    public void init(ExtractorOutput output) {
        this.extractorOutput = output;
    }

    @Override // com.google.android.exoplayer2.extractor.Extractor
    public int read(ExtractorInput input, PositionHolder seekPosition) throws IOException {
        int i = this.state;
        if (i == 0) {
            readMarker(input);
            return 0;
        }
        if (i == 1) {
            readSegmentLength(input);
            return 0;
        }
        if (i == 2) {
            readSegment(input);
            return 0;
        }
        if (i == 4) {
            long position = input.getPosition();
            long j = this.mp4StartPosition;
            if (position != j) {
                seekPosition.position = j;
                return 1;
            }
            sniffMotionPhotoVideo(input);
            return 0;
        }
        if (i != 5) {
            if (i == 6) {
                return -1;
            }
            throw new IllegalStateException();
        }
        if (this.mp4ExtractorStartOffsetExtractorInput == null || input != this.lastExtractorInput) {
            this.lastExtractorInput = input;
            this.mp4ExtractorStartOffsetExtractorInput = new StartOffsetExtractorInput(input, this.mp4StartPosition);
        }
        int i2 = ((Mp4Extractor) Assertions.checkNotNull(this.mp4Extractor)).read(this.mp4ExtractorStartOffsetExtractorInput, seekPosition);
        if (i2 == 1) {
            seekPosition.position += this.mp4StartPosition;
        }
        return i2;
    }

    @Override // com.google.android.exoplayer2.extractor.Extractor
    public void seek(long position, long timeUs) {
        if (position == 0) {
            this.state = 0;
            this.mp4Extractor = null;
        } else if (this.state == 5) {
            ((Mp4Extractor) Assertions.checkNotNull(this.mp4Extractor)).seek(position, timeUs);
        }
    }

    @Override // com.google.android.exoplayer2.extractor.Extractor
    public void release() {
        Mp4Extractor mp4Extractor = this.mp4Extractor;
        if (mp4Extractor != null) {
            mp4Extractor.release();
        }
    }

    private int peekMarker(ExtractorInput input) throws IOException {
        this.scratch.reset(2);
        input.peekFully(this.scratch.getData(), 0, 2);
        return this.scratch.readUnsignedShort();
    }

    private void advancePeekPositionToNextSegment(ExtractorInput input) throws IOException {
        this.scratch.reset(2);
        input.peekFully(this.scratch.getData(), 0, 2);
        input.advancePeekPosition(this.scratch.readUnsignedShort() - 2);
    }

    private void readMarker(ExtractorInput input) throws IOException {
        this.scratch.reset(2);
        input.readFully(this.scratch.getData(), 0, 2);
        int unsignedShort = this.scratch.readUnsignedShort();
        this.marker = unsignedShort;
        if (unsignedShort == MARKER_SOS) {
            if (this.mp4StartPosition != -1) {
                this.state = 4;
                return;
            } else {
                endReadingWithImageTrack();
                return;
            }
        }
        if ((unsignedShort < 65488 || unsignedShort > 65497) && unsignedShort != 65281) {
            this.state = 1;
        }
    }

    private void readSegmentLength(ExtractorInput input) throws IOException {
        this.scratch.reset(2);
        input.readFully(this.scratch.getData(), 0, 2);
        this.segmentLength = this.scratch.readUnsignedShort() - 2;
        this.state = 2;
    }

    private void readSegment(ExtractorInput input) throws IOException {
        String nullTerminatedString;
        if (this.marker == MARKER_APP1) {
            ParsableByteArray parsableByteArray = new ParsableByteArray(this.segmentLength);
            input.readFully(parsableByteArray.getData(), 0, this.segmentLength);
            if (this.motionPhotoMetadata == null && HEADER_XMP_APP1.equals(parsableByteArray.readNullTerminatedString()) && (nullTerminatedString = parsableByteArray.readNullTerminatedString()) != null) {
                MotionPhotoMetadata motionPhotoMetadata = getMotionPhotoMetadata(nullTerminatedString, input.getLength());
                this.motionPhotoMetadata = motionPhotoMetadata;
                if (motionPhotoMetadata != null) {
                    this.mp4StartPosition = motionPhotoMetadata.videoStartPosition;
                }
            }
        } else {
            input.skipFully(this.segmentLength);
        }
        this.state = 0;
    }

    private void sniffMotionPhotoVideo(ExtractorInput input) throws IOException {
        if (!input.peekFully(this.scratch.getData(), 0, 1, true)) {
            endReadingWithImageTrack();
            return;
        }
        input.resetPeekPosition();
        if (this.mp4Extractor == null) {
            this.mp4Extractor = new Mp4Extractor();
        }
        StartOffsetExtractorInput startOffsetExtractorInput = new StartOffsetExtractorInput(input, this.mp4StartPosition);
        this.mp4ExtractorStartOffsetExtractorInput = startOffsetExtractorInput;
        if (this.mp4Extractor.sniff(startOffsetExtractorInput)) {
            this.mp4Extractor.init(new StartOffsetExtractorOutput(this.mp4StartPosition, (ExtractorOutput) Assertions.checkNotNull(this.extractorOutput)));
            startReadingMotionPhoto();
        } else {
            endReadingWithImageTrack();
        }
    }

    private void startReadingMotionPhoto() {
        outputImageTrack((Metadata.Entry) Assertions.checkNotNull(this.motionPhotoMetadata));
        this.state = 5;
    }

    private void endReadingWithImageTrack() {
        outputImageTrack(new Metadata.Entry[0]);
        ((ExtractorOutput) Assertions.checkNotNull(this.extractorOutput)).endTracks();
        this.extractorOutput.seekMap(new SeekMap.Unseekable(C.TIME_UNSET));
        this.state = 6;
    }

    private void outputImageTrack(Metadata.Entry... metadataEntries) {
        ((ExtractorOutput) Assertions.checkNotNull(this.extractorOutput)).track(1024, 4).format(new Format.Builder().setContainerMimeType(MimeTypes.IMAGE_JPEG).setMetadata(new Metadata(metadataEntries)).build());
    }

    private static MotionPhotoMetadata getMotionPhotoMetadata(String xmpString, long inputLength) throws IOException {
        MotionPhotoDescription motionPhotoDescription;
        if (inputLength == -1 || (motionPhotoDescription = XmpMotionPhotoDescriptionParser.parse(xmpString)) == null) {
            return null;
        }
        return motionPhotoDescription.getMotionPhotoMetadata(inputLength);
    }
}
