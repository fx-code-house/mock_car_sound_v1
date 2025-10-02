package com.thor.app.gui.adapters.main;

import com.thor.basemodule.gui.adapters.BaseDiffUtilCallback;
import com.thor.businessmodule.model.MainPresetPackage;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: MainSoundPackagesDiffUtilCallback.kt */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0003J\u001c\u0010\u0004\u001a\u00020\u00052\b\u0010\u0006\u001a\u0004\u0018\u00010\u00022\b\u0010\u0007\u001a\u0004\u0018\u00010\u0002H\u0016J\u001c\u0010\b\u001a\u00020\u00052\b\u0010\u0006\u001a\u0004\u0018\u00010\u00022\b\u0010\u0007\u001a\u0004\u0018\u00010\u0002H\u0016¨\u0006\t"}, d2 = {"Lcom/thor/app/gui/adapters/main/MainSoundPackagesDiffUtilCallback;", "Lcom/thor/basemodule/gui/adapters/BaseDiffUtilCallback;", "Lcom/thor/businessmodule/model/MainPresetPackage;", "()V", "areContentsTheSame", "", "oldItem", "newItem", "areItemsTheSame", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class MainSoundPackagesDiffUtilCallback extends BaseDiffUtilCallback<MainPresetPackage> {
    @Override // com.thor.basemodule.gui.adapters.BaseDiffUtilCallback
    public boolean areItemsTheSame(MainPresetPackage oldItem, MainPresetPackage newItem) {
        Short modeType;
        Short modeType2;
        Integer numValueOf = null;
        if (Intrinsics.areEqual(oldItem != null ? oldItem.getPackageId() : null, newItem != null ? newItem.getPackageId() : null)) {
            Integer numValueOf2 = (oldItem == null || (modeType2 = oldItem.getModeType()) == null) ? null : Integer.valueOf(modeType2.shortValue());
            if (newItem != null && (modeType = newItem.getModeType()) != null) {
                numValueOf = Integer.valueOf(modeType.shortValue());
            }
            if (Intrinsics.areEqual(numValueOf2, numValueOf)) {
                return true;
            }
        }
        return false;
    }

    @Override // com.thor.basemodule.gui.adapters.BaseDiffUtilCallback
    public boolean areContentsTheSame(MainPresetPackage oldItem, MainPresetPackage newItem) {
        return Intrinsics.areEqual(oldItem, newItem);
    }
}
