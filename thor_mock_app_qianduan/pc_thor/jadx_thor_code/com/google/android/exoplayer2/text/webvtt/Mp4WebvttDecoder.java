package com.google.android.exoplayer2.text.webvtt;

import com.google.android.exoplayer2.text.Cue;
import com.google.android.exoplayer2.text.SimpleSubtitleDecoder;
import com.google.android.exoplayer2.text.Subtitle;
import com.google.android.exoplayer2.text.SubtitleDecoderException;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.Util;
import java.util.ArrayList;
import java.util.Collections;

/* loaded from: classes.dex */
public final class Mp4WebvttDecoder extends SimpleSubtitleDecoder {
    private static final int BOX_HEADER_SIZE = 8;
    private static final int TYPE_payl = 1885436268;
    private static final int TYPE_sttg = 1937011815;
    private static final int TYPE_vttc = 1987343459;
    private final ParsableByteArray sampleData;

    public Mp4WebvttDecoder() {
        super("Mp4WebvttDecoder");
        this.sampleData = new ParsableByteArray();
    }

    @Override // com.google.android.exoplayer2.text.SimpleSubtitleDecoder
    protected Subtitle decode(byte[] bytes, int length, boolean reset) throws SubtitleDecoderException {
        this.sampleData.reset(bytes, length);
        ArrayList arrayList = new ArrayList();
        while (this.sampleData.bytesLeft() > 0) {
            if (this.sampleData.bytesLeft() < 8) {
                throw new SubtitleDecoderException("Incomplete Mp4Webvtt Top Level box header found.");
            }
            int i = this.sampleData.readInt();
            if (this.sampleData.readInt() == TYPE_vttc) {
                arrayList.add(parseVttCueBox(this.sampleData, i - 8));
            } else {
                this.sampleData.skipBytes(i - 8);
            }
        }
        return new Mp4WebvttSubtitle(arrayList);
    }

    private static Cue parseVttCueBox(ParsableByteArray sampleData, int remainingCueBoxBytes) throws SubtitleDecoderException {
        CharSequence cueText = null;
        Cue.Builder cueSettingsList = null;
        while (remainingCueBoxBytes > 0) {
            if (remainingCueBoxBytes < 8) {
                throw new SubtitleDecoderException("Incomplete vtt cue box header found.");
            }
            int i = sampleData.readInt();
            int i2 = sampleData.readInt();
            int i3 = i - 8;
            String strFromUtf8Bytes = Util.fromUtf8Bytes(sampleData.getData(), sampleData.getPosition(), i3);
            sampleData.skipBytes(i3);
            remainingCueBoxBytes = (remainingCueBoxBytes - 8) - i3;
            if (i2 == TYPE_sttg) {
                cueSettingsList = WebvttCueParser.parseCueSettingsList(strFromUtf8Bytes);
            } else if (i2 == TYPE_payl) {
                cueText = WebvttCueParser.parseCueText(null, strFromUtf8Bytes.trim(), Collections.emptyList());
            }
        }
        if (cueText == null) {
            cueText = "";
        }
        if (cueSettingsList != null) {
            return cueSettingsList.setText(cueText).build();
        }
        return WebvttCueParser.newCueForText(cueText);
    }
}
