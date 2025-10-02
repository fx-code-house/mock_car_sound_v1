package com.carsystems.thor.datalayermodule.datalayer.datamaps;

import com.google.android.gms.wearable.DataMap;
import com.thor.networkmodule.model.bluetooth.BluetoothDeviceItem;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: BluetoothDevicesWearableDataLayer.kt */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0004\bÆ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003J\u0010\u0010\f\u001a\u00020\u00022\u0006\u0010\r\u001a\u00020\u000eH\u0016J\b\u0010\u000f\u001a\u00020\u0005H\u0016J\u0018\u0010\u0010\u001a\u00020\u000e2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0011\u001a\u00020\u0002H\u0016R\u000e\u0010\u0004\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000¨\u0006\u0012"}, d2 = {"Lcom/carsystems/thor/datalayermodule/datalayer/datamaps/BluetoothDevicesWearableDataLayer;", "Lcom/carsystems/thor/datalayermodule/datalayer/datamaps/WearableDataLayer;", "Lcom/thor/networkmodule/model/bluetooth/BluetoothDeviceItem;", "()V", "BLUETOOTH_DEVICES_PATH", "", "BLUETOOTH_DEVICE_LIST_KEY", "BLUETOOTH_DEVICE_MAC_ADDRESS_KEY", "BLUETOOTH_DEVICE_NAME_KEY", "BLUETOOTH_DEVICE_PATH", "BLUETOOTH_SELECTED_DEVICE_PATH", "SERVICE_BLUETOOTH_SELECTED_DEVICE_PATH", "convertFromDataMap", "dataMap", "Lcom/google/android/gms/wearable/DataMap;", "getKeyForArrayList", "putDataInMap", "convertDataTypeItem", "datalayermodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes.dex */
public final class BluetoothDevicesWearableDataLayer implements WearableDataLayer<BluetoothDeviceItem> {
    public static final String BLUETOOTH_DEVICES_PATH = "/bluetooth-devices";
    private static final String BLUETOOTH_DEVICE_LIST_KEY = "bluetooth_device_list";
    private static final String BLUETOOTH_DEVICE_MAC_ADDRESS_KEY = "bluetooth_device_mac_address_key";
    private static final String BLUETOOTH_DEVICE_NAME_KEY = "bluetooth_device_name";
    public static final String BLUETOOTH_DEVICE_PATH = "/bluetooth-device";
    public static final String BLUETOOTH_SELECTED_DEVICE_PATH = "/bluetooth-selected-device";
    public static final BluetoothDevicesWearableDataLayer INSTANCE = new BluetoothDevicesWearableDataLayer();
    public static final String SERVICE_BLUETOOTH_SELECTED_DEVICE_PATH = "/service/bluetooth-selected-device";

    @Override // com.carsystems.thor.datalayermodule.datalayer.datamaps.WearableDataLayer
    public String getKeyForArrayList() {
        return BLUETOOTH_DEVICE_LIST_KEY;
    }

    private BluetoothDevicesWearableDataLayer() {
    }

    @Override // com.carsystems.thor.datalayermodule.datalayer.datamaps.WearableDataLayer
    public DataMap putDataInMap(DataMap dataMap, BluetoothDeviceItem convertDataTypeItem) {
        Intrinsics.checkNotNullParameter(dataMap, "dataMap");
        Intrinsics.checkNotNullParameter(convertDataTypeItem, "convertDataTypeItem");
        dataMap.putString(BLUETOOTH_DEVICE_NAME_KEY, convertDataTypeItem.getName());
        dataMap.putString(BLUETOOTH_DEVICE_MAC_ADDRESS_KEY, convertDataTypeItem.getMacAddress());
        return dataMap;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.carsystems.thor.datalayermodule.datalayer.datamaps.WearableDataLayer
    public BluetoothDeviceItem convertFromDataMap(DataMap dataMap) {
        Intrinsics.checkNotNullParameter(dataMap, "dataMap");
        String deviceName = dataMap.getString(BLUETOOTH_DEVICE_NAME_KEY);
        String deviceMacAddress = dataMap.getString(BLUETOOTH_DEVICE_MAC_ADDRESS_KEY);
        Intrinsics.checkNotNullExpressionValue(deviceName, "deviceName");
        Intrinsics.checkNotNullExpressionValue(deviceMacAddress, "deviceMacAddress");
        return new BluetoothDeviceItem(deviceName, deviceMacAddress);
    }
}
