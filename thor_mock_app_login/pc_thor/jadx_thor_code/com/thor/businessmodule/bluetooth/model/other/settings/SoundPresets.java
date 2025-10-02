package com.thor.businessmodule.bluetooth.model.other.settings;

import com.thor.businessmodule.bluetooth.model.other.settings.SoundPreset;
import com.thor.businessmodule.bluetooth.util.BleHelperKt;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import me.leolin.shortcutbadger.impl.NewHtcHomeBadger;

/* compiled from: SoundPresets.kt */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\b\u0018\u0000 \u00152\u00020\u0001:\u0001\u0015B\u001b\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\u0002\u0010\u0007J\t\u0010\f\u001a\u00020\u0003HÆ\u0003J\u000f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005HÆ\u0003J#\u0010\u000e\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005HÆ\u0001J\u0013\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0012\u001a\u00020\u0003HÖ\u0001J\t\u0010\u0013\u001a\u00020\u0014HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\u0016"}, d2 = {"Lcom/thor/businessmodule/bluetooth/model/other/settings/SoundPresets;", "", NewHtcHomeBadger.COUNT, "", "soundPreset", "", "Lcom/thor/businessmodule/bluetooth/model/other/settings/SoundPreset;", "(ILjava/util/List;)V", "getCount", "()I", "getSoundPreset", "()Ljava/util/List;", "component1", "component2", "copy", "equals", "", "other", "hashCode", "toString", "", "Companion", "businessmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final /* data */ class SoundPresets {

    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final int count;
    private final List<SoundPreset> soundPreset;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ SoundPresets copy$default(SoundPresets soundPresets, int i, List list, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            i = soundPresets.count;
        }
        if ((i2 & 2) != 0) {
            list = soundPresets.soundPreset;
        }
        return soundPresets.copy(i, list);
    }

    /* renamed from: component1, reason: from getter */
    public final int getCount() {
        return this.count;
    }

    public final List<SoundPreset> component2() {
        return this.soundPreset;
    }

    public final SoundPresets copy(int count, List<SoundPreset> soundPreset) {
        Intrinsics.checkNotNullParameter(soundPreset, "soundPreset");
        return new SoundPresets(count, soundPreset);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof SoundPresets)) {
            return false;
        }
        SoundPresets soundPresets = (SoundPresets) other;
        return this.count == soundPresets.count && Intrinsics.areEqual(this.soundPreset, soundPresets.soundPreset);
    }

    public int hashCode() {
        return (Integer.hashCode(this.count) * 31) + this.soundPreset.hashCode();
    }

    public String toString() {
        return "SoundPresets(count=" + this.count + ", soundPreset=" + this.soundPreset + ")";
    }

    public SoundPresets(int i, List<SoundPreset> soundPreset) {
        Intrinsics.checkNotNullParameter(soundPreset, "soundPreset");
        this.count = i;
        this.soundPreset = soundPreset;
    }

    public final int getCount() {
        return this.count;
    }

    public final List<SoundPreset> getSoundPreset() {
        return this.soundPreset;
    }

    /* compiled from: SoundPresets.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0011\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0086\u0002¨\u0006\u0007"}, d2 = {"Lcom/thor/businessmodule/bluetooth/model/other/settings/SoundPresets$Companion;", "", "()V", "invoke", "Lcom/thor/businessmodule/bluetooth/model/other/settings/SoundPresets;", "data", "", "businessmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final SoundPresets invoke(byte[] data) {
            SoundPreset.SoundMode soundMode;
            Intrinsics.checkNotNullParameter(data, "data");
            ArrayList arrayList = new ArrayList();
            short shortLittleEndian = BleHelperKt.toShortLittleEndian(ArraysKt.sliceArray(data, RangesKt.until(0, 2)));
            byte[] bArrRemoveFirstNElements = BleHelperKt.removeFirstNElements(data, 2);
            for (int i = 0; i < shortLittleEndian; i++) {
                byte[] bArrSliceArray = ArraysKt.sliceArray(bArrRemoveFirstNElements, RangesKt.until(0, 6));
                bArrRemoveFirstNElements = BleHelperKt.removeFirstNElements(bArrRemoveFirstNElements, 6);
                short shortLittleEndian2 = BleHelperKt.toShortLittleEndian(ArraysKt.sliceArray(bArrSliceArray, RangesKt.until(0, 2)));
                short shortLittleEndian3 = BleHelperKt.toShortLittleEndian(ArraysKt.sliceArray(bArrSliceArray, RangesKt.until(2, 4)));
                SoundPreset.SoundMode[] soundModeArrValues = SoundPreset.SoundMode.values();
                int length = soundModeArrValues.length;
                int i2 = 0;
                while (true) {
                    if (i2 >= length) {
                        soundMode = null;
                        break;
                    }
                    soundMode = soundModeArrValues[i2];
                    if (soundMode.getRawValue() == shortLittleEndian3) {
                        break;
                    }
                    i2++;
                }
                arrayList.add(new SoundPreset(shortLittleEndian2, soundMode, BleHelperKt.toBitArray(ArraysKt.first(ArraysKt.sliceArray(bArrSliceArray, RangesKt.until(4, 6))))[7]));
            }
            return new SoundPresets(shortLittleEndian, arrayList);
        }
    }
}
