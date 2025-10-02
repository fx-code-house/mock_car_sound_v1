package com.thor.businessmodule.bluetooth.model.other.settings;

import com.thor.businessmodule.bluetooth.model.other.settings.DriveSelectMode;
import com.thor.businessmodule.bluetooth.util.BleHelperKt;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import me.leolin.shortcutbadger.impl.NewHtcHomeBadger;

/* compiled from: DriveSelect.kt */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\b\u0018\u0000 \u00152\u00020\u0001:\u0001\u0015B\u001b\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\u0002\u0010\u0007J\t\u0010\f\u001a\u00020\u0003HÆ\u0003J\u000f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005HÆ\u0003J#\u0010\u000e\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005HÆ\u0001J\u0013\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0012\u001a\u00020\u0003HÖ\u0001J\t\u0010\u0013\u001a\u00020\u0014HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\u0016"}, d2 = {"Lcom/thor/businessmodule/bluetooth/model/other/settings/DriveSelect;", "", NewHtcHomeBadger.COUNT, "", "driveSelectMode", "", "Lcom/thor/businessmodule/bluetooth/model/other/settings/DriveSelectMode;", "(ILjava/util/List;)V", "getCount", "()I", "getDriveSelectMode", "()Ljava/util/List;", "component1", "component2", "copy", "equals", "", "other", "hashCode", "toString", "", "Companion", "businessmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final /* data */ class DriveSelect {

    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final int count;
    private final List<DriveSelectMode> driveSelectMode;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ DriveSelect copy$default(DriveSelect driveSelect, int i, List list, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            i = driveSelect.count;
        }
        if ((i2 & 2) != 0) {
            list = driveSelect.driveSelectMode;
        }
        return driveSelect.copy(i, list);
    }

    /* renamed from: component1, reason: from getter */
    public final int getCount() {
        return this.count;
    }

    public final List<DriveSelectMode> component2() {
        return this.driveSelectMode;
    }

    public final DriveSelect copy(int count, List<DriveSelectMode> driveSelectMode) {
        Intrinsics.checkNotNullParameter(driveSelectMode, "driveSelectMode");
        return new DriveSelect(count, driveSelectMode);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof DriveSelect)) {
            return false;
        }
        DriveSelect driveSelect = (DriveSelect) other;
        return this.count == driveSelect.count && Intrinsics.areEqual(this.driveSelectMode, driveSelect.driveSelectMode);
    }

    public int hashCode() {
        return (Integer.hashCode(this.count) * 31) + this.driveSelectMode.hashCode();
    }

    public String toString() {
        return "DriveSelect(count=" + this.count + ", driveSelectMode=" + this.driveSelectMode + ")";
    }

    public DriveSelect(int i, List<DriveSelectMode> driveSelectMode) {
        Intrinsics.checkNotNullParameter(driveSelectMode, "driveSelectMode");
        this.count = i;
        this.driveSelectMode = driveSelectMode;
    }

    public final int getCount() {
        return this.count;
    }

    public final List<DriveSelectMode> getDriveSelectMode() {
        return this.driveSelectMode;
    }

    /* compiled from: DriveSelect.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0011\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0086\u0002¨\u0006\u0007"}, d2 = {"Lcom/thor/businessmodule/bluetooth/model/other/settings/DriveSelect$Companion;", "", "()V", "invoke", "Lcom/thor/businessmodule/bluetooth/model/other/settings/DriveSelect;", "data", "", "businessmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final DriveSelect invoke(byte[] data) {
            DriveSelectMode.SettingsMode settingsMode;
            Intrinsics.checkNotNullParameter(data, "data");
            ArrayList arrayList = new ArrayList();
            byte bFirst = ArraysKt.first(data);
            byte[] bArrRemoveFirstNElements = BleHelperKt.removeFirstNElements(data, 2);
            for (int i = 0; i < bFirst; i++) {
                byte[] bArrSliceArray = ArraysKt.sliceArray(bArrRemoveFirstNElements, RangesKt.until(0, 4));
                bArrRemoveFirstNElements = BleHelperKt.removeFirstNElements(bArrRemoveFirstNElements, 4);
                short shortLittleEndian = BleHelperKt.toShortLittleEndian(ArraysKt.sliceArray(bArrSliceArray, RangesKt.until(0, 2)));
                short shortLittleEndian2 = BleHelperKt.toShortLittleEndian(ArraysKt.sliceArray(bArrSliceArray, RangesKt.until(2, 4)));
                DriveSelectMode.SettingsMode[] settingsModeArrValues = DriveSelectMode.SettingsMode.values();
                int length = settingsModeArrValues.length;
                int i2 = 0;
                while (true) {
                    if (i2 >= length) {
                        settingsMode = null;
                        break;
                    }
                    settingsMode = settingsModeArrValues[i2];
                    if (settingsMode.getRowValue() == shortLittleEndian2) {
                        break;
                    }
                    i2++;
                }
                arrayList.add(new DriveSelectMode(shortLittleEndian, settingsMode));
            }
            return new DriveSelect(bFirst, arrayList);
        }
    }
}
