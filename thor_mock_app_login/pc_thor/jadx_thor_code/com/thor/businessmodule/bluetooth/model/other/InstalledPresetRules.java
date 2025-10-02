package com.thor.businessmodule.bluetooth.model.other;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: InstalledPresetRules.kt */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\n\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\b\u0018\u0000 \u001c2\u00020\u0001:\u0001\u001cB%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0016\u0010\u0004\u001a\u0012\u0012\u0004\u0012\u00020\u00060\u0005j\b\u0012\u0004\u0012\u00020\u0006`\u0007¢\u0006\u0002\u0010\bJ\t\u0010\r\u001a\u00020\u0003HÆ\u0003J\u0019\u0010\u000e\u001a\u0012\u0012\u0004\u0012\u00020\u00060\u0005j\b\u0012\u0004\u0012\u00020\u0006`\u0007HÆ\u0003J-\u0010\u000f\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u0018\b\u0002\u0010\u0004\u001a\u0012\u0012\u0004\u0012\u00020\u00060\u0005j\b\u0012\u0004\u0012\u00020\u0006`\u0007HÆ\u0001J\u0013\u0010\u0010\u001a\u00020\u00112\b\u0010\u0012\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0013\u001a\u00020\u0014HÖ\u0001J\u000e\u0010\u0015\u001a\u00020\u00112\u0006\u0010\u0016\u001a\u00020\u0017J\u000e\u0010\u0018\u001a\u00020\u00112\u0006\u0010\u0019\u001a\u00020\u0017J\t\u0010\u001a\u001a\u00020\u001bHÖ\u0001R&\u0010\u0004\u001a\u0012\u0012\u0004\u0012\u00020\u00060\u0005j\b\u0012\u0004\u0012\u00020\u0006`\u00078\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f¨\u0006\u001d"}, d2 = {"Lcom/thor/businessmodule/bluetooth/model/other/InstalledPresetRules;", "", "installedSoundPackage", "Lcom/thor/businessmodule/bluetooth/model/other/InstalledSoundPackage;", "installedPresetRules", "Ljava/util/ArrayList;", "Lcom/thor/businessmodule/bluetooth/model/other/PresetRule;", "Lkotlin/collections/ArrayList;", "(Lcom/thor/businessmodule/bluetooth/model/other/InstalledSoundPackage;Ljava/util/ArrayList;)V", "getInstalledPresetRules", "()Ljava/util/ArrayList;", "getInstalledSoundPackage", "()Lcom/thor/businessmodule/bluetooth/model/other/InstalledSoundPackage;", "component1", "component2", "copy", "equals", "", "other", "hashCode", "", "isRuleEnabledForEdit", "ruleEnabled", "", "isRuleExist", "lookingRuleId", "toString", "", "Companion", "businessmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final /* data */ class InstalledPresetRules {
    public static final short BACKFIRES_ENABLED = 4;
    public static final short DYNAMIC_START_ENABLED = 2;
    public static final short ENGINE_STOP_ENABLED = 32;
    public static final short IDLE_SOUND_ENABLED = 8;
    public static final short START_SOUND_ENABLED = 1;
    public static final short WORKING_CYCLES_ENABLED = 16;

    @SerializedName("preset_rules")
    private final ArrayList<PresetRule> installedPresetRules;

    @SerializedName("preset")
    private final InstalledSoundPackage installedSoundPackage;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ InstalledPresetRules copy$default(InstalledPresetRules installedPresetRules, InstalledSoundPackage installedSoundPackage, ArrayList arrayList, int i, Object obj) {
        if ((i & 1) != 0) {
            installedSoundPackage = installedPresetRules.installedSoundPackage;
        }
        if ((i & 2) != 0) {
            arrayList = installedPresetRules.installedPresetRules;
        }
        return installedPresetRules.copy(installedSoundPackage, arrayList);
    }

    /* renamed from: component1, reason: from getter */
    public final InstalledSoundPackage getInstalledSoundPackage() {
        return this.installedSoundPackage;
    }

    public final ArrayList<PresetRule> component2() {
        return this.installedPresetRules;
    }

    public final InstalledPresetRules copy(InstalledSoundPackage installedSoundPackage, ArrayList<PresetRule> installedPresetRules) {
        Intrinsics.checkNotNullParameter(installedSoundPackage, "installedSoundPackage");
        Intrinsics.checkNotNullParameter(installedPresetRules, "installedPresetRules");
        return new InstalledPresetRules(installedSoundPackage, installedPresetRules);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof InstalledPresetRules)) {
            return false;
        }
        InstalledPresetRules installedPresetRules = (InstalledPresetRules) other;
        return Intrinsics.areEqual(this.installedSoundPackage, installedPresetRules.installedSoundPackage) && Intrinsics.areEqual(this.installedPresetRules, installedPresetRules.installedPresetRules);
    }

    public int hashCode() {
        return (this.installedSoundPackage.hashCode() * 31) + this.installedPresetRules.hashCode();
    }

    public String toString() {
        return "InstalledPresetRules(installedSoundPackage=" + this.installedSoundPackage + ", installedPresetRules=" + this.installedPresetRules + ")";
    }

    public InstalledPresetRules(InstalledSoundPackage installedSoundPackage, ArrayList<PresetRule> installedPresetRules) {
        Intrinsics.checkNotNullParameter(installedSoundPackage, "installedSoundPackage");
        Intrinsics.checkNotNullParameter(installedPresetRules, "installedPresetRules");
        this.installedSoundPackage = installedSoundPackage;
        this.installedPresetRules = installedPresetRules;
    }

    public final InstalledSoundPackage getInstalledSoundPackage() {
        return this.installedSoundPackage;
    }

    public final ArrayList<PresetRule> getInstalledPresetRules() {
        return this.installedPresetRules;
    }

    public final boolean isRuleExist(short lookingRuleId) {
        Iterator<T> it = this.installedPresetRules.iterator();
        while (it.hasNext()) {
            if (((PresetRule) it.next()).getId() == lookingRuleId) {
                return true;
            }
        }
        return false;
    }

    public final boolean isRuleEnabledForEdit(short ruleEnabled) {
        for (PresetRule presetRule : this.installedPresetRules) {
            if (((short) (presetRule.getValue() & ruleEnabled)) > 0 && presetRule.getId() == 3) {
                return true;
            }
        }
        return false;
    }
}
