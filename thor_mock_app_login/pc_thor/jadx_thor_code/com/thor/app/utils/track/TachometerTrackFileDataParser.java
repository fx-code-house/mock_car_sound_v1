package com.thor.app.utils.track;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.io.TextStreamsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.text.Charsets;
import kotlin.text.StringsKt;

/* compiled from: TachometerTrackFileDataParser.kt */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u0001B\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004B\u0005¢\u0006\u0002\u0010\u0005J\u0012\u0010\u0007\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\b0\bR\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0003X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\n"}, d2 = {"Lcom/thor/app/utils/track/TachometerTrackFileDataParser;", "", "inputStream", "Ljava/io/InputStream;", "(Ljava/io/InputStream;)V", "()V", "mInputStream", "fetchData", "", "", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class TachometerTrackFileDataParser {
    private InputStream mInputStream;

    public TachometerTrackFileDataParser() {
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public TachometerTrackFileDataParser(InputStream inputStream) {
        this();
        Intrinsics.checkNotNullParameter(inputStream, "inputStream");
        this.mInputStream = inputStream;
    }

    public final List<List<String>> fetchData() {
        BufferedReader bufferedReader;
        Sequence<String> sequenceLineSequence;
        ArrayList arrayList = new ArrayList();
        InputStream inputStream = this.mInputStream;
        if (inputStream != null) {
            Reader inputStreamReader = new InputStreamReader(inputStream, Charsets.UTF_8);
            bufferedReader = inputStreamReader instanceof BufferedReader ? (BufferedReader) inputStreamReader : new BufferedReader(inputStreamReader, 8192);
        } else {
            bufferedReader = null;
        }
        if (bufferedReader != null && (sequenceLineSequence = TextStreamsKt.lineSequence(bufferedReader)) != null && (r1 = sequenceLineSequence.iterator()) != null) {
            for (String str : sequenceLineSequence) {
                if (StringsKt.contains$default((CharSequence) str, (CharSequence) ";", false, 2, (Object) null)) {
                    arrayList.add(StringsKt.split$default((CharSequence) str, new String[]{";"}, false, 0, 6, (Object) null));
                }
            }
        }
        return arrayList;
    }
}
