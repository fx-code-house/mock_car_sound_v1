package com.thor.businessmodule.bluetooth.model.other.settings;

import com.thor.businessmodule.bluetooth.util.BleHelperKt;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;

/* compiled from: LoudSpeakers.kt */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010 \n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\b\u0018\u0000 \u00142\u00020\u0001:\u0001\u0014B\u001b\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005¢\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003HÆ\u0003J\u000f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005HÆ\u0003J#\u0010\r\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005HÆ\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0011\u001a\u00020\u0003HÖ\u0001J\t\u0010\u0012\u001a\u00020\u0013HÖ\u0001R\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0015"}, d2 = {"Lcom/thor/businessmodule/bluetooth/model/other/settings/LoudSpeakers;", "", "loudSpeakersCount", "", "loudSpeakers", "", "(ILjava/util/List;)V", "getLoudSpeakers", "()Ljava/util/List;", "getLoudSpeakersCount", "()I", "component1", "component2", "copy", "equals", "", "other", "hashCode", "toString", "", "Companion", "businessmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final /* data */ class LoudSpeakers {

    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final List<Integer> loudSpeakers;
    private final int loudSpeakersCount;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ LoudSpeakers copy$default(LoudSpeakers loudSpeakers, int i, List list, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            i = loudSpeakers.loudSpeakersCount;
        }
        if ((i2 & 2) != 0) {
            list = loudSpeakers.loudSpeakers;
        }
        return loudSpeakers.copy(i, list);
    }

    /* renamed from: component1, reason: from getter */
    public final int getLoudSpeakersCount() {
        return this.loudSpeakersCount;
    }

    public final List<Integer> component2() {
        return this.loudSpeakers;
    }

    public final LoudSpeakers copy(int loudSpeakersCount, List<Integer> loudSpeakers) {
        Intrinsics.checkNotNullParameter(loudSpeakers, "loudSpeakers");
        return new LoudSpeakers(loudSpeakersCount, loudSpeakers);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof LoudSpeakers)) {
            return false;
        }
        LoudSpeakers loudSpeakers = (LoudSpeakers) other;
        return this.loudSpeakersCount == loudSpeakers.loudSpeakersCount && Intrinsics.areEqual(this.loudSpeakers, loudSpeakers.loudSpeakers);
    }

    public int hashCode() {
        return (Integer.hashCode(this.loudSpeakersCount) * 31) + this.loudSpeakers.hashCode();
    }

    public String toString() {
        return "LoudSpeakers(loudSpeakersCount=" + this.loudSpeakersCount + ", loudSpeakers=" + this.loudSpeakers + ")";
    }

    public LoudSpeakers(int i, List<Integer> loudSpeakers) {
        Intrinsics.checkNotNullParameter(loudSpeakers, "loudSpeakers");
        this.loudSpeakersCount = i;
        this.loudSpeakers = loudSpeakers;
    }

    public final int getLoudSpeakersCount() {
        return this.loudSpeakersCount;
    }

    public final List<Integer> getLoudSpeakers() {
        return this.loudSpeakers;
    }

    /* compiled from: LoudSpeakers.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0011\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0086\u0002¨\u0006\u0007"}, d2 = {"Lcom/thor/businessmodule/bluetooth/model/other/settings/LoudSpeakers$Companion;", "", "()V", "invoke", "Lcom/thor/businessmodule/bluetooth/model/other/settings/LoudSpeakers;", "data", "", "businessmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final LoudSpeakers invoke(byte[] data) {
            Intrinsics.checkNotNullParameter(data, "data");
            ArrayList arrayList = new ArrayList();
            short shortLittleEndian = BleHelperKt.toShortLittleEndian(ArraysKt.sliceArray(data, RangesKt.until(0, 2)));
            byte[] bArrRemoveFirstNElements = BleHelperKt.removeFirstNElements(data, 2);
            for (int i = 0; i < shortLittleEndian; i++) {
                byte[] bArrSliceArray = ArraysKt.sliceArray(bArrRemoveFirstNElements, RangesKt.until(0, 2));
                bArrRemoveFirstNElements = BleHelperKt.removeFirstNElements(bArrRemoveFirstNElements, 2);
                arrayList.add(Integer.valueOf(BleHelperKt.toShortLittleEndian(ArraysKt.sliceArray(bArrSliceArray, RangesKt.until(0, 2)))));
            }
            return new LoudSpeakers(shortLittleEndian, arrayList);
        }
    }
}
