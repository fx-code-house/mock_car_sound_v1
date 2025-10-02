package com.thor.businessmodule.viewmodel.views;

import androidx.databinding.ObservableBoolean;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: MainPresetPackageViewModel.kt */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0003\u0010\u0005R\u0011\u0010\u0006\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0005R\u0011\u0010\u0007\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\u0005R\u001a\u0010\b\u001a\u00020\tX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\r¨\u0006\u000e"}, d2 = {"Lcom/thor/businessmodule/viewmodel/views/MainPresetPackageViewModel;", "", "()V", "isActivate", "Landroidx/databinding/ObservableBoolean;", "()Landroidx/databinding/ObservableBoolean;", "isDamageEnabled", "isDeleteEnabled", "presetName", "", "getPresetName", "()Ljava/lang/String;", "setPresetName", "(Ljava/lang/String;)V", "businessmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class MainPresetPackageViewModel {
    private final ObservableBoolean isActivate = new ObservableBoolean(false);
    private final ObservableBoolean isDeleteEnabled = new ObservableBoolean(false);
    private final ObservableBoolean isDamageEnabled = new ObservableBoolean(false);
    private String presetName = "";

    /* renamed from: isActivate, reason: from getter */
    public final ObservableBoolean getIsActivate() {
        return this.isActivate;
    }

    /* renamed from: isDeleteEnabled, reason: from getter */
    public final ObservableBoolean getIsDeleteEnabled() {
        return this.isDeleteEnabled;
    }

    /* renamed from: isDamageEnabled, reason: from getter */
    public final ObservableBoolean getIsDamageEnabled() {
        return this.isDamageEnabled;
    }

    public final String getPresetName() {
        return this.presetName;
    }

    public final void setPresetName(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.presetName = str;
    }
}
