package com.thor.businessmodule.bluetooth.model.other;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: InstalledSoundPackages.kt */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\n\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B)\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\u0018\b\u0002\u0010\u0004\u001a\u0012\u0012\u0004\u0012\u00020\u00060\u0005j\b\u0012\u0004\u0012\u00020\u0006`\u0007¢\u0006\u0002\u0010\bJ\t\u0010\u0011\u001a\u00020\u0003HÆ\u0003J\u0019\u0010\u0012\u001a\u0012\u0012\u0004\u0012\u00020\u00060\u0005j\b\u0012\u0004\u0012\u00020\u0006`\u0007HÆ\u0003J-\u0010\u0013\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u0018\b\u0002\u0010\u0004\u001a\u0012\u0012\u0004\u0012\u00020\u00060\u0005j\b\u0012\u0004\u0012\u00020\u0006`\u0007HÆ\u0001J\u0013\u0010\u0014\u001a\u00020\u00152\b\u0010\u0016\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0017\u001a\u00020\u0018HÖ\u0001J\t\u0010\u0019\u001a\u00020\u001aHÖ\u0001R\u001e\u0010\u0002\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR.\u0010\u0004\u001a\u0012\u0012\u0004\u0012\u00020\u00060\u0005j\b\u0012\u0004\u0012\u00020\u0006`\u00078\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010¨\u0006\u001b"}, d2 = {"Lcom/thor/businessmodule/bluetooth/model/other/InstalledSoundPackages;", "", "amount", "", "soundPackages", "Ljava/util/ArrayList;", "Lcom/thor/businessmodule/bluetooth/model/other/InstalledSoundPackage;", "Lkotlin/collections/ArrayList;", "(SLjava/util/ArrayList;)V", "getAmount", "()S", "setAmount", "(S)V", "getSoundPackages", "()Ljava/util/ArrayList;", "setSoundPackages", "(Ljava/util/ArrayList;)V", "component1", "component2", "copy", "equals", "", "other", "hashCode", "", "toString", "", "businessmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final /* data */ class InstalledSoundPackages {

    @SerializedName("amount")
    private short amount;

    @SerializedName("sound_packages")
    private ArrayList<InstalledSoundPackage> soundPackages;

    /* JADX WARN: Multi-variable type inference failed */
    public InstalledSoundPackages() {
        this((short) 0, null, 3, 0 == true ? 1 : 0);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ InstalledSoundPackages copy$default(InstalledSoundPackages installedSoundPackages, short s, ArrayList arrayList, int i, Object obj) {
        if ((i & 1) != 0) {
            s = installedSoundPackages.amount;
        }
        if ((i & 2) != 0) {
            arrayList = installedSoundPackages.soundPackages;
        }
        return installedSoundPackages.copy(s, arrayList);
    }

    /* renamed from: component1, reason: from getter */
    public final short getAmount() {
        return this.amount;
    }

    public final ArrayList<InstalledSoundPackage> component2() {
        return this.soundPackages;
    }

    public final InstalledSoundPackages copy(short amount, ArrayList<InstalledSoundPackage> soundPackages) {
        Intrinsics.checkNotNullParameter(soundPackages, "soundPackages");
        return new InstalledSoundPackages(amount, soundPackages);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof InstalledSoundPackages)) {
            return false;
        }
        InstalledSoundPackages installedSoundPackages = (InstalledSoundPackages) other;
        return this.amount == installedSoundPackages.amount && Intrinsics.areEqual(this.soundPackages, installedSoundPackages.soundPackages);
    }

    public int hashCode() {
        return (Short.hashCode(this.amount) * 31) + this.soundPackages.hashCode();
    }

    public String toString() {
        return "InstalledSoundPackages(amount=" + ((int) this.amount) + ", soundPackages=" + this.soundPackages + ")";
    }

    public InstalledSoundPackages(short s, ArrayList<InstalledSoundPackage> soundPackages) {
        Intrinsics.checkNotNullParameter(soundPackages, "soundPackages");
        this.amount = s;
        this.soundPackages = soundPackages;
    }

    public final short getAmount() {
        return this.amount;
    }

    public final void setAmount(short s) {
        this.amount = s;
    }

    public /* synthetic */ InstalledSoundPackages(short s, ArrayList arrayList, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? (short) 0 : s, (i & 2) != 0 ? new ArrayList() : arrayList);
    }

    public final ArrayList<InstalledSoundPackage> getSoundPackages() {
        return this.soundPackages;
    }

    public final void setSoundPackages(ArrayList<InstalledSoundPackage> arrayList) {
        Intrinsics.checkNotNullParameter(arrayList, "<set-?>");
        this.soundPackages = arrayList;
    }
}
