package com.thor.app.gui.adapters.main;

import com.thor.basemodule.gui.adapters.BaseDiffUtilCallback;
import com.thor.networkmodule.model.responses.sgu.SguSound;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: SguSoundPackagesDiffUtilCallback.kt */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0003J\u001c\u0010\u0004\u001a\u00020\u00052\b\u0010\u0006\u001a\u0004\u0018\u00010\u00022\b\u0010\u0007\u001a\u0004\u0018\u00010\u0002H\u0016J\u001c\u0010\b\u001a\u00020\u00052\b\u0010\u0006\u001a\u0004\u0018\u00010\u00022\b\u0010\u0007\u001a\u0004\u0018\u00010\u0002H\u0016¨\u0006\t"}, d2 = {"Lcom/thor/app/gui/adapters/main/SguSoundPackagesDiffUtilCallback;", "Lcom/thor/basemodule/gui/adapters/BaseDiffUtilCallback;", "Lcom/thor/networkmodule/model/responses/sgu/SguSound;", "()V", "areContentsTheSame", "", "oldItem", "newItem", "areItemsTheSame", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class SguSoundPackagesDiffUtilCallback extends BaseDiffUtilCallback<SguSound> {
    @Override // com.thor.basemodule.gui.adapters.BaseDiffUtilCallback
    public boolean areItemsTheSame(SguSound oldItem, SguSound newItem) {
        if (Intrinsics.areEqual(oldItem != null ? Integer.valueOf(oldItem.getId()) : null, newItem != null ? Integer.valueOf(newItem.getId()) : null)) {
            if (Intrinsics.areEqual(oldItem != null ? oldItem.getName() : null, newItem != null ? newItem.getName() : null)) {
                return true;
            }
        }
        return false;
    }

    @Override // com.thor.basemodule.gui.adapters.BaseDiffUtilCallback
    public boolean areContentsTheSame(SguSound oldItem, SguSound newItem) {
        return Intrinsics.areEqual(oldItem, newItem);
    }
}
