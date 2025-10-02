package com.thor.networkmodule.model.responses.sgu;

import com.google.gson.annotations.SerializedName;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: SguSoundJsonWrapper.kt */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u000e\u0010\u0002\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0005J\u0011\u0010\t\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u0003HÆ\u0003J\u001b\u0010\n\u001a\u00020\u00002\u0010\b\u0002\u0010\u0002\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u0003HÆ\u0001J\u0013\u0010\u000b\u001a\u00020\f2\b\u0010\r\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u000e\u001a\u00020\u000fHÖ\u0001J\t\u0010\u0010\u001a\u00020\u0011HÖ\u0001R&\u0010\u0002\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\u0005¨\u0006\u0012"}, d2 = {"Lcom/thor/networkmodule/model/responses/sgu/SguSoundJsonWrapper;", "", "presets", "", "Lcom/thor/networkmodule/model/responses/sgu/SguSoundJson;", "(Ljava/util/List;)V", "getPresets", "()Ljava/util/List;", "setPresets", "component1", "copy", "equals", "", "other", "hashCode", "", "toString", "", "networkmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final /* data */ class SguSoundJsonWrapper {

    @SerializedName("presets")
    private List<SguSoundJson> presets;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ SguSoundJsonWrapper copy$default(SguSoundJsonWrapper sguSoundJsonWrapper, List list, int i, Object obj) {
        if ((i & 1) != 0) {
            list = sguSoundJsonWrapper.presets;
        }
        return sguSoundJsonWrapper.copy(list);
    }

    public final List<SguSoundJson> component1() {
        return this.presets;
    }

    public final SguSoundJsonWrapper copy(List<SguSoundJson> presets) {
        return new SguSoundJsonWrapper(presets);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        return (other instanceof SguSoundJsonWrapper) && Intrinsics.areEqual(this.presets, ((SguSoundJsonWrapper) other).presets);
    }

    public int hashCode() {
        List<SguSoundJson> list = this.presets;
        if (list == null) {
            return 0;
        }
        return list.hashCode();
    }

    public String toString() {
        return "SguSoundJsonWrapper(presets=" + this.presets + ")";
    }

    public SguSoundJsonWrapper(List<SguSoundJson> list) {
        this.presets = list;
    }

    public final List<SguSoundJson> getPresets() {
        return this.presets;
    }

    public final void setPresets(List<SguSoundJson> list) {
        this.presets = list;
    }
}
