package com.thor.app.managers;

import com.google.android.exoplayer2.text.ttml.TtmlNode;
import com.thor.app.gui.views.sgu.SguSoundConfigView;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: SguSoundManager.kt */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\n\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0006\u0010\n\u001a\u00020\u000bJ\u0016\u0010\f\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\u00042\u0006\u0010\u000e\u001a\u00020\tR\u001e\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0004@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0010\u0010\b\u001a\u0004\u0018\u00010\tX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u000f"}, d2 = {"Lcom/thor/app/managers/SguSoundManager;", "", "()V", "<set-?>", "", "playingSguSoundId", "getPlayingSguSoundId", "()S", "sguSoundConfigView", "Lcom/thor/app/gui/views/sgu/SguSoundConfigView;", "release", "", "setPlayingSguSound", TtmlNode.ATTR_ID, "view", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class SguSoundManager {
    public static final SguSoundManager INSTANCE = new SguSoundManager();
    private static short playingSguSoundId = -1;
    private static SguSoundConfigView sguSoundConfigView;

    private SguSoundManager() {
    }

    public final short getPlayingSguSoundId() {
        return playingSguSoundId;
    }

    public final void setPlayingSguSound(short id, SguSoundConfigView view) {
        Intrinsics.checkNotNullParameter(view, "view");
        playingSguSoundId = id;
        sguSoundConfigView = view;
    }

    public final void release() {
        sguSoundConfigView = null;
        playingSguSoundId = (short) -1;
    }
}
