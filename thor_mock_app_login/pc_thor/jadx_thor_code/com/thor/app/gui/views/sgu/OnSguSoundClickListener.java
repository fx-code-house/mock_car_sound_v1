package com.thor.app.gui.views.sgu;

import com.thor.networkmodule.model.responses.sgu.SguSound;
import kotlin.Metadata;

/* compiled from: OnSguSoundClickListener.kt */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J\u0010\u0010\u0006\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J\u0010\u0010\u0007\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&¨\u0006\b"}, d2 = {"Lcom/thor/app/gui/views/sgu/OnSguSoundClickListener;", "", "onConfigClick", "", "sound", "Lcom/thor/networkmodule/model/responses/sgu/SguSound;", "onFavClick", "onPlayClick", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public interface OnSguSoundClickListener {
    void onConfigClick(SguSound sound);

    void onFavClick(SguSound sound);

    void onPlayClick(SguSound sound);
}
