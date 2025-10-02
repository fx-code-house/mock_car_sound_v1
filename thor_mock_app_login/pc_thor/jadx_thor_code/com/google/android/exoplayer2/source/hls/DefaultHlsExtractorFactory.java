package com.google.android.exoplayer2.source.hls;

import android.net.Uri;
import android.text.TextUtils;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.extractor.Extractor;
import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.extractor.mp3.Mp3Extractor;
import com.google.android.exoplayer2.extractor.mp4.FragmentedMp4Extractor;
import com.google.android.exoplayer2.extractor.ts.Ac3Extractor;
import com.google.android.exoplayer2.extractor.ts.Ac4Extractor;
import com.google.android.exoplayer2.extractor.ts.AdtsExtractor;
import com.google.android.exoplayer2.extractor.ts.DefaultTsPayloadReaderFactory;
import com.google.android.exoplayer2.extractor.ts.TsExtractor;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.FileTypes;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.TimestampAdjuster;
import com.google.common.primitives.Ints;
import java.io.EOFException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/* loaded from: classes.dex */
public final class DefaultHlsExtractorFactory implements HlsExtractorFactory {
    private static final int[] DEFAULT_EXTRACTOR_ORDER = {8, 13, 11, 2, 0, 1, 7};
    private final boolean exposeCea608WhenMissingDeclarations;
    private final int payloadReaderFactoryFlags;

    @Override // com.google.android.exoplayer2.source.hls.HlsExtractorFactory
    public /* bridge */ /* synthetic */ HlsMediaChunkExtractor createExtractor(Uri uri, Format format, List muxedCaptionFormats, TimestampAdjuster timestampAdjuster, Map responseHeaders, ExtractorInput sniffingExtractorInput) throws IOException {
        return createExtractor(uri, format, (List<Format>) muxedCaptionFormats, timestampAdjuster, (Map<String, List<String>>) responseHeaders, sniffingExtractorInput);
    }

    public DefaultHlsExtractorFactory() {
        this(0, true);
    }

    public DefaultHlsExtractorFactory(int payloadReaderFactoryFlags, boolean exposeCea608WhenMissingDeclarations) {
        this.payloadReaderFactoryFlags = payloadReaderFactoryFlags;
        this.exposeCea608WhenMissingDeclarations = exposeCea608WhenMissingDeclarations;
    }

    @Override // com.google.android.exoplayer2.source.hls.HlsExtractorFactory
    public BundledHlsMediaChunkExtractor createExtractor(Uri uri, Format format, List<Format> muxedCaptionFormats, TimestampAdjuster timestampAdjuster, Map<String, List<String>> responseHeaders, ExtractorInput sniffingExtractorInput) throws IOException {
        int iInferFileTypeFromMimeType = FileTypes.inferFileTypeFromMimeType(format.sampleMimeType);
        int iInferFileTypeFromResponseHeaders = FileTypes.inferFileTypeFromResponseHeaders(responseHeaders);
        int iInferFileTypeFromUri = FileTypes.inferFileTypeFromUri(uri);
        int[] iArr = DEFAULT_EXTRACTOR_ORDER;
        ArrayList arrayList = new ArrayList(iArr.length);
        addFileTypeIfValidAndNotPresent(iInferFileTypeFromMimeType, arrayList);
        addFileTypeIfValidAndNotPresent(iInferFileTypeFromResponseHeaders, arrayList);
        addFileTypeIfValidAndNotPresent(iInferFileTypeFromUri, arrayList);
        for (int i : iArr) {
            addFileTypeIfValidAndNotPresent(i, arrayList);
        }
        sniffingExtractorInput.resetPeekPosition();
        Extractor extractor = null;
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            int iIntValue = ((Integer) arrayList.get(i2)).intValue();
            Extractor extractor2 = (Extractor) Assertions.checkNotNull(createExtractorByFileType(iIntValue, format, muxedCaptionFormats, timestampAdjuster));
            if (sniffQuietly(extractor2, sniffingExtractorInput)) {
                return new BundledHlsMediaChunkExtractor(extractor2, format, timestampAdjuster);
            }
            if (extractor == null && (iIntValue == iInferFileTypeFromMimeType || iIntValue == iInferFileTypeFromResponseHeaders || iIntValue == iInferFileTypeFromUri || iIntValue == 11)) {
                extractor = extractor2;
            }
        }
        return new BundledHlsMediaChunkExtractor((Extractor) Assertions.checkNotNull(extractor), format, timestampAdjuster);
    }

    private static void addFileTypeIfValidAndNotPresent(int fileType, List<Integer> fileTypes) {
        if (Ints.indexOf(DEFAULT_EXTRACTOR_ORDER, fileType) == -1 || fileTypes.contains(Integer.valueOf(fileType))) {
            return;
        }
        fileTypes.add(Integer.valueOf(fileType));
    }

    private Extractor createExtractorByFileType(int fileType, Format format, List<Format> muxedCaptionFormats, TimestampAdjuster timestampAdjuster) {
        if (fileType == 0) {
            return new Ac3Extractor();
        }
        if (fileType == 1) {
            return new Ac4Extractor();
        }
        if (fileType == 2) {
            return new AdtsExtractor();
        }
        if (fileType == 7) {
            return new Mp3Extractor(0, 0L);
        }
        if (fileType == 8) {
            return createFragmentedMp4Extractor(timestampAdjuster, format, muxedCaptionFormats);
        }
        if (fileType == 11) {
            return createTsExtractor(this.payloadReaderFactoryFlags, this.exposeCea608WhenMissingDeclarations, format, muxedCaptionFormats, timestampAdjuster);
        }
        if (fileType != 13) {
            return null;
        }
        return new WebvttExtractor(format.language, timestampAdjuster);
    }

    private static TsExtractor createTsExtractor(int userProvidedPayloadReaderFactoryFlags, boolean exposeCea608WhenMissingDeclarations, Format format, List<Format> muxedCaptionFormats, TimestampAdjuster timestampAdjuster) {
        int i = userProvidedPayloadReaderFactoryFlags | 16;
        if (muxedCaptionFormats != null) {
            i |= 32;
        } else if (exposeCea608WhenMissingDeclarations) {
            muxedCaptionFormats = Collections.singletonList(new Format.Builder().setSampleMimeType(MimeTypes.APPLICATION_CEA608).build());
        } else {
            muxedCaptionFormats = Collections.emptyList();
        }
        String str = format.codecs;
        if (!TextUtils.isEmpty(str)) {
            if (!MimeTypes.containsCodecsCorrespondingToMimeType(str, MimeTypes.AUDIO_AAC)) {
                i |= 2;
            }
            if (!MimeTypes.containsCodecsCorrespondingToMimeType(str, MimeTypes.VIDEO_H264)) {
                i |= 4;
            }
        }
        return new TsExtractor(2, timestampAdjuster, new DefaultTsPayloadReaderFactory(i, muxedCaptionFormats));
    }

    private static FragmentedMp4Extractor createFragmentedMp4Extractor(TimestampAdjuster timestampAdjuster, Format format, List<Format> muxedCaptionFormats) {
        int i = isFmp4Variant(format) ? 4 : 0;
        if (muxedCaptionFormats == null) {
            muxedCaptionFormats = Collections.emptyList();
        }
        return new FragmentedMp4Extractor(i, timestampAdjuster, null, muxedCaptionFormats);
    }

    private static boolean isFmp4Variant(Format format) {
        Metadata metadata = format.metadata;
        if (metadata == null) {
            return false;
        }
        for (int i = 0; i < metadata.length(); i++) {
            if (metadata.get(i) instanceof HlsTrackMetadataEntry) {
                return !((HlsTrackMetadataEntry) r2).variantInfos.isEmpty();
            }
        }
        return false;
    }

    private static boolean sniffQuietly(Extractor extractor, ExtractorInput input) throws IOException {
        try {
            boolean zSniff = extractor.sniff(input);
            input.resetPeekPosition();
            return zSniff;
        } catch (EOFException unused) {
            input.resetPeekPosition();
            return false;
        } catch (Throwable th) {
            input.resetPeekPosition();
            throw th;
        }
    }
}
