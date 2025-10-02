package com.thor.businessmodule.bluetooth.model.other.settings;

import android.util.Log;
import androidx.core.app.FrameMetricsAggregator;
import com.thor.businessmodule.bluetooth.util.BleHelper;
import com.thor.businessmodule.bluetooth.util.BleHelperKt;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;

/* compiled from: DeviceSettingsModel.kt */
@Metadata(d1 = {"\u0000l\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b'\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\r\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001:\u0001SB\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004Bq\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\b\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\n\u0012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\f\u0012\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u000e\u0012\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u0010\u0012\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u0012\u0012\n\b\u0002\u0010\u0013\u001a\u0004\u0018\u00010\u0014\u0012\n\b\u0002\u0010\u0015\u001a\u0004\u0018\u00010\u0016¢\u0006\u0002\u0010\u0017J\u0014\u0010=\u001a\u000e\u0012\u0004\u0012\u00020?\u0012\u0004\u0012\u00020\u00030>H\u0002J\u0010\u0010@\u001a\u00020A2\u0006\u0010\u0002\u001a\u00020\u0003H\u0002J\u000b\u0010B\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\u000b\u0010C\u001a\u0004\u0018\u00010\bHÆ\u0003J\u000b\u0010D\u001a\u0004\u0018\u00010\nHÆ\u0003J\u000b\u0010E\u001a\u0004\u0018\u00010\fHÆ\u0003J\u000b\u0010F\u001a\u0004\u0018\u00010\u000eHÆ\u0003J\u000b\u0010G\u001a\u0004\u0018\u00010\u0010HÆ\u0003J\u000b\u0010H\u001a\u0004\u0018\u00010\u0012HÆ\u0003J\u000b\u0010I\u001a\u0004\u0018\u00010\u0014HÆ\u0003J\u000b\u0010J\u001a\u0004\u0018\u00010\u0016HÆ\u0003Ju\u0010K\u001a\u00020\u00002\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\b2\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\n2\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\f2\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u000e2\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u00102\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u00122\n\b\u0002\u0010\u0013\u001a\u0004\u0018\u00010\u00142\n\b\u0002\u0010\u0015\u001a\u0004\u0018\u00010\u0016HÆ\u0001J\u0013\u0010L\u001a\u00020A2\b\u0010M\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010N\u001a\u00020OHÖ\u0001J\u0010\u0010P\u001a\u00020A2\u0006\u0010\u0002\u001a\u00020\u0003H\u0002J\t\u0010Q\u001a\u00020RHÖ\u0001R\u001c\u0010\u0011\u001a\u0004\u0018\u00010\u0012X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u0019\"\u0004\b\u001a\u0010\u001bR\u001c\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\u001d\"\u0004\b\u001e\u0010\u001fR\u001c\u0010\t\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b \u0010!\"\u0004\b\"\u0010#R\u001c\u0010\u0013\u001a\u0004\u0018\u00010\u0014X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b$\u0010%\"\u0004\b&\u0010'R\u000e\u0010(\u001a\u00020\u0003X\u0082.¢\u0006\u0002\n\u0000R\u001c\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b)\u0010*\"\u0004\b+\u0010,R\u001c\u0010\u0015\u001a\u0004\u0018\u00010\u0016X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b-\u0010.\"\u0004\b/\u00100R\u001c\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b1\u00102\"\u0004\b3\u00104R\u001c\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b5\u00106\"\u0004\b7\u00108R\u001c\u0010\r\u001a\u0004\u0018\u00010\u000eX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b9\u0010:\"\u0004\b;\u0010<¨\u0006T"}, d2 = {"Lcom/thor/businessmodule/bluetooth/model/other/settings/DeviceSettingsModel;", "", "data", "", "([B)V", "hwInfo", "Lcom/thor/businessmodule/bluetooth/model/other/settings/HWInfo;", "deviceId", "Lcom/thor/businessmodule/bluetooth/model/other/settings/DeviceId;", "deviceStatus", "Lcom/thor/businessmodule/bluetooth/model/other/settings/DeviceStatus;", "soundPacket", "Lcom/thor/businessmodule/bluetooth/model/other/settings/SoundPackets;", "soundPreset", "Lcom/thor/businessmodule/bluetooth/model/other/settings/SoundPresets;", "loudspeakers", "Lcom/thor/businessmodule/bluetooth/model/other/settings/LoudSpeakers;", "canFile", "Lcom/thor/businessmodule/bluetooth/model/other/settings/CanFile;", "driveSelect", "Lcom/thor/businessmodule/bluetooth/model/other/settings/DriveSelect;", "hwInfo2", "Lcom/thor/businessmodule/bluetooth/model/other/settings/HwInfo2;", "(Lcom/thor/businessmodule/bluetooth/model/other/settings/HWInfo;Lcom/thor/businessmodule/bluetooth/model/other/settings/DeviceId;Lcom/thor/businessmodule/bluetooth/model/other/settings/DeviceStatus;Lcom/thor/businessmodule/bluetooth/model/other/settings/SoundPackets;Lcom/thor/businessmodule/bluetooth/model/other/settings/SoundPresets;Lcom/thor/businessmodule/bluetooth/model/other/settings/LoudSpeakers;Lcom/thor/businessmodule/bluetooth/model/other/settings/CanFile;Lcom/thor/businessmodule/bluetooth/model/other/settings/DriveSelect;Lcom/thor/businessmodule/bluetooth/model/other/settings/HwInfo2;)V", "getCanFile", "()Lcom/thor/businessmodule/bluetooth/model/other/settings/CanFile;", "setCanFile", "(Lcom/thor/businessmodule/bluetooth/model/other/settings/CanFile;)V", "getDeviceId", "()Lcom/thor/businessmodule/bluetooth/model/other/settings/DeviceId;", "setDeviceId", "(Lcom/thor/businessmodule/bluetooth/model/other/settings/DeviceId;)V", "getDeviceStatus", "()Lcom/thor/businessmodule/bluetooth/model/other/settings/DeviceStatus;", "setDeviceStatus", "(Lcom/thor/businessmodule/bluetooth/model/other/settings/DeviceStatus;)V", "getDriveSelect", "()Lcom/thor/businessmodule/bluetooth/model/other/settings/DriveSelect;", "setDriveSelect", "(Lcom/thor/businessmodule/bluetooth/model/other/settings/DriveSelect;)V", "fieldData", "getHwInfo", "()Lcom/thor/businessmodule/bluetooth/model/other/settings/HWInfo;", "setHwInfo", "(Lcom/thor/businessmodule/bluetooth/model/other/settings/HWInfo;)V", "getHwInfo2", "()Lcom/thor/businessmodule/bluetooth/model/other/settings/HwInfo2;", "setHwInfo2", "(Lcom/thor/businessmodule/bluetooth/model/other/settings/HwInfo2;)V", "getLoudspeakers", "()Lcom/thor/businessmodule/bluetooth/model/other/settings/LoudSpeakers;", "setLoudspeakers", "(Lcom/thor/businessmodule/bluetooth/model/other/settings/LoudSpeakers;)V", "getSoundPacket", "()Lcom/thor/businessmodule/bluetooth/model/other/settings/SoundPackets;", "setSoundPacket", "(Lcom/thor/businessmodule/bluetooth/model/other/settings/SoundPackets;)V", "getSoundPreset", "()Lcom/thor/businessmodule/bluetooth/model/other/settings/SoundPresets;", "setSoundPreset", "(Lcom/thor/businessmodule/bluetooth/model/other/settings/SoundPresets;)V", "blockDataWithType", "Lkotlin/Pair;", "Lcom/thor/businessmodule/bluetooth/model/other/settings/DeviceSettingsModel$BlockType;", "checkCRC", "", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "other", "hashCode", "", "isEndBlock", "toString", "", "BlockType", "businessmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final /* data */ class DeviceSettingsModel {
    private CanFile canFile;
    private DeviceId deviceId;
    private DeviceStatus deviceStatus;
    private DriveSelect driveSelect;
    private byte[] fieldData;
    private HWInfo hwInfo;
    private HwInfo2 hwInfo2;
    private LoudSpeakers loudspeakers;
    private SoundPackets soundPacket;
    private SoundPresets soundPreset;

    /* compiled from: DeviceSettingsModel.kt */
    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[BlockType.values().length];
            try {
                iArr[BlockType.HwInfo.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[BlockType.DeviceId.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[BlockType.Status.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[BlockType.SoundPackets.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[BlockType.Presets.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr[BlockType.Loudspeakers.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                iArr[BlockType.CanFile.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                iArr[BlockType.DriveSelect.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                iArr[BlockType.HwInfo2.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public DeviceSettingsModel() {
        this(null, null, null, null, null, null, null, null, null, FrameMetricsAggregator.EVERY_DURATION, null);
    }

    /* renamed from: component1, reason: from getter */
    public final HWInfo getHwInfo() {
        return this.hwInfo;
    }

    /* renamed from: component2, reason: from getter */
    public final DeviceId getDeviceId() {
        return this.deviceId;
    }

    /* renamed from: component3, reason: from getter */
    public final DeviceStatus getDeviceStatus() {
        return this.deviceStatus;
    }

    /* renamed from: component4, reason: from getter */
    public final SoundPackets getSoundPacket() {
        return this.soundPacket;
    }

    /* renamed from: component5, reason: from getter */
    public final SoundPresets getSoundPreset() {
        return this.soundPreset;
    }

    /* renamed from: component6, reason: from getter */
    public final LoudSpeakers getLoudspeakers() {
        return this.loudspeakers;
    }

    /* renamed from: component7, reason: from getter */
    public final CanFile getCanFile() {
        return this.canFile;
    }

    /* renamed from: component8, reason: from getter */
    public final DriveSelect getDriveSelect() {
        return this.driveSelect;
    }

    /* renamed from: component9, reason: from getter */
    public final HwInfo2 getHwInfo2() {
        return this.hwInfo2;
    }

    public final DeviceSettingsModel copy(HWInfo hwInfo, DeviceId deviceId, DeviceStatus deviceStatus, SoundPackets soundPacket, SoundPresets soundPreset, LoudSpeakers loudspeakers, CanFile canFile, DriveSelect driveSelect, HwInfo2 hwInfo2) {
        return new DeviceSettingsModel(hwInfo, deviceId, deviceStatus, soundPacket, soundPreset, loudspeakers, canFile, driveSelect, hwInfo2);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof DeviceSettingsModel)) {
            return false;
        }
        DeviceSettingsModel deviceSettingsModel = (DeviceSettingsModel) other;
        return Intrinsics.areEqual(this.hwInfo, deviceSettingsModel.hwInfo) && Intrinsics.areEqual(this.deviceId, deviceSettingsModel.deviceId) && Intrinsics.areEqual(this.deviceStatus, deviceSettingsModel.deviceStatus) && Intrinsics.areEqual(this.soundPacket, deviceSettingsModel.soundPacket) && Intrinsics.areEqual(this.soundPreset, deviceSettingsModel.soundPreset) && Intrinsics.areEqual(this.loudspeakers, deviceSettingsModel.loudspeakers) && Intrinsics.areEqual(this.canFile, deviceSettingsModel.canFile) && Intrinsics.areEqual(this.driveSelect, deviceSettingsModel.driveSelect) && Intrinsics.areEqual(this.hwInfo2, deviceSettingsModel.hwInfo2);
    }

    public int hashCode() {
        HWInfo hWInfo = this.hwInfo;
        int iHashCode = (hWInfo == null ? 0 : hWInfo.hashCode()) * 31;
        DeviceId deviceId = this.deviceId;
        int iHashCode2 = (iHashCode + (deviceId == null ? 0 : deviceId.hashCode())) * 31;
        DeviceStatus deviceStatus = this.deviceStatus;
        int iHashCode3 = (iHashCode2 + (deviceStatus == null ? 0 : deviceStatus.hashCode())) * 31;
        SoundPackets soundPackets = this.soundPacket;
        int iHashCode4 = (iHashCode3 + (soundPackets == null ? 0 : soundPackets.hashCode())) * 31;
        SoundPresets soundPresets = this.soundPreset;
        int iHashCode5 = (iHashCode4 + (soundPresets == null ? 0 : soundPresets.hashCode())) * 31;
        LoudSpeakers loudSpeakers = this.loudspeakers;
        int iHashCode6 = (iHashCode5 + (loudSpeakers == null ? 0 : loudSpeakers.hashCode())) * 31;
        CanFile canFile = this.canFile;
        int iHashCode7 = (iHashCode6 + (canFile == null ? 0 : canFile.hashCode())) * 31;
        DriveSelect driveSelect = this.driveSelect;
        int iHashCode8 = (iHashCode7 + (driveSelect == null ? 0 : driveSelect.hashCode())) * 31;
        HwInfo2 hwInfo2 = this.hwInfo2;
        return iHashCode8 + (hwInfo2 != null ? hwInfo2.hashCode() : 0);
    }

    public String toString() {
        return "DeviceSettingsModel(hwInfo=" + this.hwInfo + ", deviceId=" + this.deviceId + ", deviceStatus=" + this.deviceStatus + ", soundPacket=" + this.soundPacket + ", soundPreset=" + this.soundPreset + ", loudspeakers=" + this.loudspeakers + ", canFile=" + this.canFile + ", driveSelect=" + this.driveSelect + ", hwInfo2=" + this.hwInfo2 + ")";
    }

    public DeviceSettingsModel(HWInfo hWInfo, DeviceId deviceId, DeviceStatus deviceStatus, SoundPackets soundPackets, SoundPresets soundPresets, LoudSpeakers loudSpeakers, CanFile canFile, DriveSelect driveSelect, HwInfo2 hwInfo2) {
        this.hwInfo = hWInfo;
        this.deviceId = deviceId;
        this.deviceStatus = deviceStatus;
        this.soundPacket = soundPackets;
        this.soundPreset = soundPresets;
        this.loudspeakers = loudSpeakers;
        this.canFile = canFile;
        this.driveSelect = driveSelect;
        this.hwInfo2 = hwInfo2;
    }

    public /* synthetic */ DeviceSettingsModel(HWInfo hWInfo, DeviceId deviceId, DeviceStatus deviceStatus, SoundPackets soundPackets, SoundPresets soundPresets, LoudSpeakers loudSpeakers, CanFile canFile, DriveSelect driveSelect, HwInfo2 hwInfo2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : hWInfo, (i & 2) != 0 ? null : deviceId, (i & 4) != 0 ? null : deviceStatus, (i & 8) != 0 ? null : soundPackets, (i & 16) != 0 ? null : soundPresets, (i & 32) != 0 ? null : loudSpeakers, (i & 64) != 0 ? null : canFile, (i & 128) != 0 ? null : driveSelect, (i & 256) == 0 ? hwInfo2 : null);
    }

    public final HWInfo getHwInfo() {
        return this.hwInfo;
    }

    public final void setHwInfo(HWInfo hWInfo) {
        this.hwInfo = hWInfo;
    }

    public final DeviceId getDeviceId() {
        return this.deviceId;
    }

    public final void setDeviceId(DeviceId deviceId) {
        this.deviceId = deviceId;
    }

    public final DeviceStatus getDeviceStatus() {
        return this.deviceStatus;
    }

    public final void setDeviceStatus(DeviceStatus deviceStatus) {
        this.deviceStatus = deviceStatus;
    }

    public final SoundPackets getSoundPacket() {
        return this.soundPacket;
    }

    public final void setSoundPacket(SoundPackets soundPackets) {
        this.soundPacket = soundPackets;
    }

    public final SoundPresets getSoundPreset() {
        return this.soundPreset;
    }

    public final void setSoundPreset(SoundPresets soundPresets) {
        this.soundPreset = soundPresets;
    }

    public final LoudSpeakers getLoudspeakers() {
        return this.loudspeakers;
    }

    public final void setLoudspeakers(LoudSpeakers loudSpeakers) {
        this.loudspeakers = loudSpeakers;
    }

    public final CanFile getCanFile() {
        return this.canFile;
    }

    public final void setCanFile(CanFile canFile) {
        this.canFile = canFile;
    }

    public final DriveSelect getDriveSelect() {
        return this.driveSelect;
    }

    public final void setDriveSelect(DriveSelect driveSelect) {
        this.driveSelect = driveSelect;
    }

    public final HwInfo2 getHwInfo2() {
        return this.hwInfo2;
    }

    public final void setHwInfo2(HwInfo2 hwInfo2) {
        this.hwInfo2 = hwInfo2;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public DeviceSettingsModel(byte[] data) throws Exception {
        this(null, null, null, null, null, null, null, null, null, FrameMetricsAggregator.EVERY_DURATION, null);
        Intrinsics.checkNotNullParameter(data, "data");
        if (checkCRC(data)) {
            byte[] bArrSliceArray = ArraysKt.sliceArray(data, RangesKt.until(4, data.length - 2));
            this.fieldData = bArrSliceArray;
            if (bArrSliceArray == null) {
                Intrinsics.throwUninitializedPropertyAccessException("fieldData");
                bArrSliceArray = null;
            }
            Log.i("ApplicationDataModel", "fieldData: " + BleHelperKt.toHex(bArrSliceArray));
            while (true) {
                byte[] bArr = this.fieldData;
                if (bArr == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("fieldData");
                    bArr = null;
                }
                if (isEndBlock(bArr)) {
                    Pair<BlockType, byte[]> pairBlockDataWithType = blockDataWithType();
                    BlockType blockTypeComponent1 = pairBlockDataWithType.component1();
                    byte[] bArrComponent2 = pairBlockDataWithType.component2();
                    switch (WhenMappings.$EnumSwitchMapping$0[blockTypeComponent1.ordinal()]) {
                        case 1:
                            this.hwInfo = new HWInfo(bArrComponent2);
                            break;
                        case 2:
                            this.deviceId = new DeviceId(bArrComponent2);
                            break;
                        case 3:
                            this.deviceStatus = DeviceStatus.INSTANCE.invoke(bArrComponent2);
                            break;
                        case 4:
                            this.soundPacket = SoundPackets.INSTANCE.invoke(bArrComponent2);
                            break;
                        case 5:
                            this.soundPreset = SoundPresets.INSTANCE.invoke(bArrComponent2);
                            break;
                        case 6:
                            this.loudspeakers = LoudSpeakers.INSTANCE.invoke(bArrComponent2);
                            break;
                        case 7:
                            this.canFile = new CanFile(bArrComponent2);
                            break;
                        case 8:
                            this.driveSelect = DriveSelect.INSTANCE.invoke(bArrComponent2);
                            break;
                        case 9:
                            this.hwInfo2 = new HwInfo2(bArrComponent2);
                            break;
                    }
                } else {
                    return;
                }
            }
        }
    }

    private final boolean checkCRC(byte[] data) {
        return BleHelper.takeShort(BleHelper.createCheckSumPart(data)) == 0;
    }

    private final boolean isEndBlock(byte[] data) {
        return BleHelper.takeShort(CollectionsKt.toByteArray(ArraysKt.take(data, 2))) != -1;
    }

    private final Pair<BlockType, byte[]> blockDataWithType() throws Exception {
        byte[] bArr = this.fieldData;
        byte[] bArr2 = null;
        if (bArr == null) {
            Intrinsics.throwUninitializedPropertyAccessException("fieldData");
            bArr = null;
        }
        int shortLittleEndian = BleHelperKt.toShortLittleEndian(ArraysKt.sliceArray(bArr, RangesKt.until(0, 2))) - 4;
        byte[] bArr3 = this.fieldData;
        if (bArr3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("fieldData");
            bArr3 = null;
        }
        this.fieldData = BleHelperKt.removeFirstNElements(bArr3, 2);
        BlockType.Companion companion = BlockType.INSTANCE;
        byte[] bArr4 = this.fieldData;
        if (bArr4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("fieldData");
            bArr4 = null;
        }
        BlockType blockTypeFromRawValue = companion.fromRawValue(BleHelperKt.toShortLittleEndian(ArraysKt.sliceArray(bArr4, RangesKt.until(0, 2))));
        if (blockTypeFromRawValue == null) {
            throw new Exception("Invalid block type");
        }
        byte[] bArr5 = this.fieldData;
        if (bArr5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("fieldData");
            bArr5 = null;
        }
        byte[] bArrRemoveFirstNElements = BleHelperKt.removeFirstNElements(bArr5, 2);
        this.fieldData = bArrRemoveFirstNElements;
        int i = shortLittleEndian % 2 != 0 ? shortLittleEndian - 1 : shortLittleEndian;
        if (bArrRemoveFirstNElements == null) {
            Intrinsics.throwUninitializedPropertyAccessException("fieldData");
            bArrRemoveFirstNElements = null;
        }
        byte[] bArrSliceArray = ArraysKt.sliceArray(bArrRemoveFirstNElements, RangesKt.until(0, i));
        byte[] bArr6 = this.fieldData;
        if (bArr6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("fieldData");
        } else {
            bArr2 = bArr6;
        }
        this.fieldData = BleHelperKt.removeFirstNElements(bArr2, shortLittleEndian);
        return new Pair<>(blockTypeFromRawValue, bArrSliceArray);
    }

    /* compiled from: DeviceSettingsModel.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\n\n\u0002\b\u000e\b\u0086\u0001\u0018\u0000 \u00102\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001\u0010B\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\tj\u0002\b\nj\u0002\b\u000bj\u0002\b\fj\u0002\b\rj\u0002\b\u000ej\u0002\b\u000f¨\u0006\u0011"}, d2 = {"Lcom/thor/businessmodule/bluetooth/model/other/settings/DeviceSettingsModel$BlockType;", "", "rawValue", "", "(Ljava/lang/String;IS)V", "getRawValue", "()S", "HwInfo", "DeviceId", "Status", "SoundPackets", "Presets", "Loudspeakers", "CanFile", "DriveSelect", "HwInfo2", "Companion", "businessmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public enum BlockType {
        HwInfo(1),
        DeviceId(2),
        Status(3),
        SoundPackets(4),
        Presets(5),
        Loudspeakers(6),
        CanFile(7),
        DriveSelect(8),
        HwInfo2(10);


        /* renamed from: Companion, reason: from kotlin metadata */
        public static final Companion INSTANCE = new Companion(null);
        private final short rawValue;

        BlockType(short s) {
            this.rawValue = s;
        }

        public final short getRawValue() {
            return this.rawValue;
        }

        /* compiled from: DeviceSettingsModel.kt */
        @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\n\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\u0007"}, d2 = {"Lcom/thor/businessmodule/bluetooth/model/other/settings/DeviceSettingsModel$BlockType$Companion;", "", "()V", "fromRawValue", "Lcom/thor/businessmodule/bluetooth/model/other/settings/DeviceSettingsModel$BlockType;", "rawValue", "", "businessmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
        public static final class Companion {
            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            private Companion() {
            }

            public final BlockType fromRawValue(short rawValue) {
                for (BlockType blockType : BlockType.values()) {
                    if (blockType.getRawValue() == rawValue) {
                        return blockType;
                    }
                }
                return null;
            }
        }
    }
}
