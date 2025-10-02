package com.fourksoft.base.ui.adapter.listener;

import com.fourksoft.base.ui.adapter.type.ActionRecycler;
import com.google.android.exoplayer2.source.rtsp.SessionDescription;
import kotlin.Metadata;

/* compiled from: OnRecyclerActionListener.kt */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\bf\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u0002J\u001d\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00028\u0000H&¢\u0006\u0002\u0010\b¨\u0006\t"}, d2 = {"Lcom/fourksoft/base/ui/adapter/listener/OnRecyclerActionListener;", "M", "", "onAction", "", SessionDescription.ATTR_TYPE, "Lcom/fourksoft/base/ui/adapter/type/ActionRecycler;", "model", "(Lcom/fourksoft/base/ui/adapter/type/ActionRecycler;Ljava/lang/Object;)V", "basemodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes.dex */
public interface OnRecyclerActionListener<M> {
    void onAction(ActionRecycler type, M model);
}
