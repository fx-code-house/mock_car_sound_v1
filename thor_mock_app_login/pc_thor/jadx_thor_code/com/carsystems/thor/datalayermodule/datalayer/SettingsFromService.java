package com.carsystems.thor.datalayermodule.datalayer;

import com.thor.networkmodule.model.responses.SignInResponse;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: SettingsFromService.kt */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003HÆ\u0003J\t\u0010\f\u001a\u00020\u0005HÆ\u0003J\u001d\u0010\r\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001J\t\u0010\u0013\u001a\u00020\u0005HÖ\u0001R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0014"}, d2 = {"Lcom/carsystems/thor/datalayermodule/datalayer/SettingsFromService;", "", "response", "Lcom/thor/networkmodule/model/responses/SignInResponse;", "bluetoothDeviceMacAddress", "", "(Lcom/thor/networkmodule/model/responses/SignInResponse;Ljava/lang/String;)V", "getBluetoothDeviceMacAddress", "()Ljava/lang/String;", "getResponse", "()Lcom/thor/networkmodule/model/responses/SignInResponse;", "component1", "component2", "copy", "equals", "", "other", "hashCode", "", "toString", "datalayermodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes.dex */
public final /* data */ class SettingsFromService {
    private final String bluetoothDeviceMacAddress;
    private final SignInResponse response;

    public static /* synthetic */ SettingsFromService copy$default(SettingsFromService settingsFromService, SignInResponse signInResponse, String str, int i, Object obj) {
        if ((i & 1) != 0) {
            signInResponse = settingsFromService.response;
        }
        if ((i & 2) != 0) {
            str = settingsFromService.bluetoothDeviceMacAddress;
        }
        return settingsFromService.copy(signInResponse, str);
    }

    /* renamed from: component1, reason: from getter */
    public final SignInResponse getResponse() {
        return this.response;
    }

    /* renamed from: component2, reason: from getter */
    public final String getBluetoothDeviceMacAddress() {
        return this.bluetoothDeviceMacAddress;
    }

    public final SettingsFromService copy(SignInResponse response, String bluetoothDeviceMacAddress) {
        Intrinsics.checkNotNullParameter(response, "response");
        Intrinsics.checkNotNullParameter(bluetoothDeviceMacAddress, "bluetoothDeviceMacAddress");
        return new SettingsFromService(response, bluetoothDeviceMacAddress);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof SettingsFromService)) {
            return false;
        }
        SettingsFromService settingsFromService = (SettingsFromService) other;
        return Intrinsics.areEqual(this.response, settingsFromService.response) && Intrinsics.areEqual(this.bluetoothDeviceMacAddress, settingsFromService.bluetoothDeviceMacAddress);
    }

    public int hashCode() {
        return (this.response.hashCode() * 31) + this.bluetoothDeviceMacAddress.hashCode();
    }

    public String toString() {
        return "SettingsFromService(response=" + this.response + ", bluetoothDeviceMacAddress=" + this.bluetoothDeviceMacAddress + ")";
    }

    public SettingsFromService(SignInResponse response, String bluetoothDeviceMacAddress) {
        Intrinsics.checkNotNullParameter(response, "response");
        Intrinsics.checkNotNullParameter(bluetoothDeviceMacAddress, "bluetoothDeviceMacAddress");
        this.response = response;
        this.bluetoothDeviceMacAddress = bluetoothDeviceMacAddress;
    }

    public final String getBluetoothDeviceMacAddress() {
        return this.bluetoothDeviceMacAddress;
    }

    public final SignInResponse getResponse() {
        return this.response;
    }
}
