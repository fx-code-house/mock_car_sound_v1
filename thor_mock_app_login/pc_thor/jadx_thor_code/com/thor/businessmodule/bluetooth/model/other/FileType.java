package com.thor.businessmodule.bluetooth.model.other;

import com.google.android.exoplayer2.text.ttml.TtmlNode;
import kotlin.Metadata;

/* compiled from: FileType.kt */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\n\n\u0000\n\u0002\u0010\b\n\u0002\b\u0011\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0017\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0011\u0010\r\u001a\u00020\u00002\u0006\u0010\u000e\u001a\u00020\u0005H\u0086\u0002R\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fj\u0002\b\u000fj\u0002\b\u0010j\u0002\b\u0011j\u0002\b\u0012j\u0002\b\u0013j\u0002\b\u0014j\u0002\b\u0015¨\u0006\u0016"}, d2 = {"Lcom/thor/businessmodule/bluetooth/model/other/FileType;", "", TtmlNode.ATTR_ID, "", "fileLast", "", "(Ljava/lang/String;ISI)V", "getFileLast", "()I", "setFileLast", "(I)V", "getId", "()S", "invoke", "value", "SoundSamplePackageV2", "SoundRulesPackageV2", "SoundModeRulesPackageV2", "SoundSampleV3", "FirmwareFile", "SGU", "CAN", "businessmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public enum FileType {
    SoundSamplePackageV2(4, 0),
    SoundRulesPackageV2(5, 0),
    SoundModeRulesPackageV2(6, 0),
    SoundSampleV3(7, 0),
    FirmwareFile(2, 0),
    SGU(3, 0),
    CAN(1, 0);

    private int fileLast;
    private final short id;

    FileType(short s, int i) {
        this.id = s;
        this.fileLast = i;
    }

    public final int getFileLast() {
        return this.fileLast;
    }

    public final short getId() {
        return this.id;
    }

    public final void setFileLast(int i) {
        this.fileLast = i;
    }

    public final FileType invoke(int value) {
        FileType fileType = SGU;
        fileType.fileLast = value;
        return fileType;
    }
}
