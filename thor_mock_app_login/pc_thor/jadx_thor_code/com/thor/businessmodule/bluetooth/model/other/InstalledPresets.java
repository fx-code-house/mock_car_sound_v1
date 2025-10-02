package com.thor.businessmodule.bluetooth.model.other;

import com.google.gson.annotations.SerializedName;
import java.util.LinkedHashSet;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: InstalledPresets.kt */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\n\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0010\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B3\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\u0018\b\u0002\u0010\u0005\u001a\u0012\u0012\u0004\u0012\u00020\u00070\u0006j\b\u0012\u0004\u0012\u00020\u0007`\b¢\u0006\u0002\u0010\tJ\t\u0010\u0014\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0015\u001a\u00020\u0003HÆ\u0003J\u0019\u0010\u0016\u001a\u0012\u0012\u0004\u0012\u00020\u00070\u0006j\b\u0012\u0004\u0012\u00020\u0007`\bHÆ\u0003J7\u0010\u0017\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\u0018\b\u0002\u0010\u0005\u001a\u0012\u0012\u0004\u0012\u00020\u00070\u0006j\b\u0012\u0004\u0012\u00020\u0007`\bHÆ\u0001J\u0013\u0010\u0018\u001a\u00020\u00192\b\u0010\u001a\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u001b\u001a\u00020\u001cHÖ\u0001J\t\u0010\u001d\u001a\u00020\u001eHÖ\u0001R\u001e\u0010\u0004\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR\u001e\u0010\u0002\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000b\"\u0004\b\u000f\u0010\rR.\u0010\u0005\u001a\u0012\u0012\u0004\u0012\u00020\u00070\u0006j\b\u0012\u0004\u0012\u00020\u0007`\b8\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013¨\u0006\u001f"}, d2 = {"Lcom/thor/businessmodule/bluetooth/model/other/InstalledPresets;", "", "currentPresetId", "", "amount", "presets", "Ljava/util/LinkedHashSet;", "Lcom/thor/businessmodule/bluetooth/model/other/InstalledPreset;", "Lkotlin/collections/LinkedHashSet;", "(SSLjava/util/LinkedHashSet;)V", "getAmount", "()S", "setAmount", "(S)V", "getCurrentPresetId", "setCurrentPresetId", "getPresets", "()Ljava/util/LinkedHashSet;", "setPresets", "(Ljava/util/LinkedHashSet;)V", "component1", "component2", "component3", "copy", "equals", "", "other", "hashCode", "", "toString", "", "businessmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final /* data */ class InstalledPresets {

    @SerializedName("amount")
    private short amount;

    @SerializedName("current_preset_id")
    private short currentPresetId;

    @SerializedName("presets")
    private LinkedHashSet<InstalledPreset> presets;

    public InstalledPresets() {
        this((short) 0, (short) 0, null, 7, null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ InstalledPresets copy$default(InstalledPresets installedPresets, short s, short s2, LinkedHashSet linkedHashSet, int i, Object obj) {
        if ((i & 1) != 0) {
            s = installedPresets.currentPresetId;
        }
        if ((i & 2) != 0) {
            s2 = installedPresets.amount;
        }
        if ((i & 4) != 0) {
            linkedHashSet = installedPresets.presets;
        }
        return installedPresets.copy(s, s2, linkedHashSet);
    }

    /* renamed from: component1, reason: from getter */
    public final short getCurrentPresetId() {
        return this.currentPresetId;
    }

    /* renamed from: component2, reason: from getter */
    public final short getAmount() {
        return this.amount;
    }

    public final LinkedHashSet<InstalledPreset> component3() {
        return this.presets;
    }

    public final InstalledPresets copy(short currentPresetId, short amount, LinkedHashSet<InstalledPreset> presets) {
        Intrinsics.checkNotNullParameter(presets, "presets");
        return new InstalledPresets(currentPresetId, amount, presets);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof InstalledPresets)) {
            return false;
        }
        InstalledPresets installedPresets = (InstalledPresets) other;
        return this.currentPresetId == installedPresets.currentPresetId && this.amount == installedPresets.amount && Intrinsics.areEqual(this.presets, installedPresets.presets);
    }

    public int hashCode() {
        return (((Short.hashCode(this.currentPresetId) * 31) + Short.hashCode(this.amount)) * 31) + this.presets.hashCode();
    }

    public String toString() {
        return "InstalledPresets(currentPresetId=" + ((int) this.currentPresetId) + ", amount=" + ((int) this.amount) + ", presets=" + this.presets + ")";
    }

    public InstalledPresets(short s, short s2, LinkedHashSet<InstalledPreset> presets) {
        Intrinsics.checkNotNullParameter(presets, "presets");
        this.currentPresetId = s;
        this.amount = s2;
        this.presets = presets;
    }

    public final short getCurrentPresetId() {
        return this.currentPresetId;
    }

    public final void setCurrentPresetId(short s) {
        this.currentPresetId = s;
    }

    public final short getAmount() {
        return this.amount;
    }

    public final void setAmount(short s) {
        this.amount = s;
    }

    public /* synthetic */ InstalledPresets(short s, short s2, LinkedHashSet linkedHashSet, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? (short) 0 : s, (i & 2) != 0 ? (short) 0 : s2, (i & 4) != 0 ? new LinkedHashSet() : linkedHashSet);
    }

    public final LinkedHashSet<InstalledPreset> getPresets() {
        return this.presets;
    }

    public final void setPresets(LinkedHashSet<InstalledPreset> linkedHashSet) {
        Intrinsics.checkNotNullParameter(linkedHashSet, "<set-?>");
        this.presets = linkedHashSet;
    }
}
