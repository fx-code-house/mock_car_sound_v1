package com.thor.businessmodule.bluetooth.model.other;

import com.google.gson.annotations.SerializedName;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: DriveMode.kt */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\n\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001:\u0001\u0012B\u0017\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006B\u0005¢\u0006\u0002\u0010\u0007J\b\u0010\u0011\u001a\u00020\u0005H\u0016R\u001e\u0010\u0002\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR\u001e\u0010\u0004\u001a\u00020\f8\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010¨\u0006\u0013"}, d2 = {"Lcom/thor/businessmodule/bluetooth/model/other/DriveMode;", "", "modeId", "", "modeValue", "", "(SLjava/lang/String;)V", "()V", "getModeId", "()S", "setModeId", "(S)V", "Lcom/thor/businessmodule/bluetooth/model/other/DriveMode$DRIVE_MODE;", "getModeValue", "()Lcom/thor/businessmodule/bluetooth/model/other/DriveMode$DRIVE_MODE;", "setModeValue", "(Lcom/thor/businessmodule/bluetooth/model/other/DriveMode$DRIVE_MODE;)V", "toString", "DRIVE_MODE", "businessmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class DriveMode {

    @SerializedName("mode_id")
    private short modeId;

    @SerializedName("mode_value")
    private DRIVE_MODE modeValue;

    public DriveMode() {
        this.modeValue = DRIVE_MODE.PLAY_CURRENT_PRESET;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue
    java.lang.NullPointerException: Cannot invoke "java.util.List.iterator()" because the return value of "jadx.core.dex.visitors.regions.SwitchOverStringVisitor$SwitchData.getNewCases()" is null
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.restoreSwitchOverString(SwitchOverStringVisitor.java:109)
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.visitRegion(SwitchOverStringVisitor.java:66)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:77)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:82)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterative(DepthRegionTraversal.java:31)
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.visit(SwitchOverStringVisitor.java:60)
     */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0042  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public DriveMode(short r2, java.lang.String r3) {
        /*
            r1 = this;
            java.lang.String r0 = "modeValue"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r3, r0)
            r1.<init>()
            r1.modeId = r2
            int r2 = r3.hashCode()
            switch(r2) {
                case 48: goto L36;
                case 49: goto L2a;
                case 50: goto L1e;
                case 51: goto L12;
                default: goto L11;
            }
        L11:
            goto L42
        L12:
            java.lang.String r2 = "3"
            boolean r2 = r3.equals(r2)
            if (r2 != 0) goto L1b
            goto L42
        L1b:
            com.thor.businessmodule.bluetooth.model.other.DriveMode$DRIVE_MODE r2 = com.thor.businessmodule.bluetooth.model.other.DriveMode.DRIVE_MODE.RESERVE
            goto L44
        L1e:
            java.lang.String r2 = "2"
            boolean r2 = r3.equals(r2)
            if (r2 != 0) goto L27
            goto L42
        L27:
            com.thor.businessmodule.bluetooth.model.other.DriveMode$DRIVE_MODE r2 = com.thor.businessmodule.bluetooth.model.other.DriveMode.DRIVE_MODE.MUTE
            goto L44
        L2a:
            java.lang.String r2 = "1"
            boolean r2 = r3.equals(r2)
            if (r2 != 0) goto L33
            goto L42
        L33:
            com.thor.businessmodule.bluetooth.model.other.DriveMode$DRIVE_MODE r2 = com.thor.businessmodule.bluetooth.model.other.DriveMode.DRIVE_MODE.PLAY_PRESET_AS_SELECTED_MODE
            goto L44
        L36:
            java.lang.String r2 = "0"
            boolean r2 = r3.equals(r2)
            if (r2 != 0) goto L3f
            goto L42
        L3f:
            com.thor.businessmodule.bluetooth.model.other.DriveMode$DRIVE_MODE r2 = com.thor.businessmodule.bluetooth.model.other.DriveMode.DRIVE_MODE.PLAY_CURRENT_PRESET
            goto L44
        L42:
            com.thor.businessmodule.bluetooth.model.other.DriveMode$DRIVE_MODE r2 = com.thor.businessmodule.bluetooth.model.other.DriveMode.DRIVE_MODE.MUTE
        L44:
            r1.modeValue = r2
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.thor.businessmodule.bluetooth.model.other.DriveMode.<init>(short, java.lang.String):void");
    }

    public final short getModeId() {
        return this.modeId;
    }

    public final void setModeId(short s) {
        this.modeId = s;
    }

    public final DRIVE_MODE getModeValue() {
        return this.modeValue;
    }

    public final void setModeValue(DRIVE_MODE drive_mode) {
        Intrinsics.checkNotNullParameter(drive_mode, "<set-?>");
        this.modeValue = drive_mode;
    }

    /* compiled from: DriveMode.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u000e\n\u0002\b\b\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\tj\u0002\b\n¨\u0006\u000b"}, d2 = {"Lcom/thor/businessmodule/bluetooth/model/other/DriveMode$DRIVE_MODE;", "", "value", "", "(Ljava/lang/String;ILjava/lang/String;)V", "getValue", "()Ljava/lang/String;", "PLAY_CURRENT_PRESET", "PLAY_PRESET_AS_SELECTED_MODE", "MUTE", "RESERVE", "businessmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public enum DRIVE_MODE {
        PLAY_CURRENT_PRESET("00"),
        PLAY_PRESET_AS_SELECTED_MODE("01"),
        MUTE("10"),
        RESERVE("11");

        private final String value;

        DRIVE_MODE(String str) {
            this.value = str;
        }

        public final String getValue() {
            return this.value;
        }
    }

    public String toString() {
        return "id: " + ((int) this.modeId) + ", value: " + this.modeValue;
    }
}
