package com.carsystems.thor.datalayermodule.datalayer.datamaps;

import com.google.android.gms.wearable.DataMap;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: BooleanWearableDataLayer.kt */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u000b\n\u0002\u0010\b\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0005\bÆ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003J\u0015\u0010\u0018\u001a\u00020\u00022\u0006\u0010\u0019\u001a\u00020\u001aH\u0016¢\u0006\u0002\u0010\u001bJ\b\u0010\u001c\u001a\u00020\u0005H\u0016J\u0018\u0010\u001d\u001a\u00020\u001a2\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001e\u001a\u00020\u0002H\u0016R\u000e\u0010\u0004\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0011X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0011X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0011X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0011X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000¨\u0006\u001f"}, d2 = {"Lcom/carsystems/thor/datalayermodule/datalayer/datamaps/BooleanWearableDataLayer;", "Lcom/carsystems/thor/datalayermodule/datalayer/datamaps/WearableDataLayer;", "", "()V", "ACCESS_SESSION", "", "BLUETOOTH_CONNECTION_PATH", "BLUETOOTH_CONNECTION_STATUS_PATH", "BLUETOOTH_REFRESHING_DEVICES_PATH", "BLUETOOTH_REFRESHING_DEVICES_WEAR_PATH", "BOOLEAN_KEY", "BOOLEAN_LIST_KEY", "CONNECTION_WITH_SCHEMA", "CURRENT_ACTIVITY_PATH", "CURRENT_DATA_PATH", "CURRENT_SETTINGS_PATH", "STATE_FINISH", "", "STATE_LAUNCHED", "STATE_MAIN_SOUNDS", "STATE_RUN", "STATE_SGU", "UPLOADING_SOUND_PACKAGE_CLOSE_PATH", "UPLOADING_SOUND_PACKAGE_LOADING_PATH", "convertFromDataMap", "dataMap", "Lcom/google/android/gms/wearable/DataMap;", "(Lcom/google/android/gms/wearable/DataMap;)Ljava/lang/Boolean;", "getKeyForArrayList", "putDataInMap", "convertDataTypeItem", "datalayermodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes.dex */
public final class BooleanWearableDataLayer implements WearableDataLayer<Boolean> {
    public static final String ACCESS_SESSION = "/access/session";
    public static final String BLUETOOTH_CONNECTION_PATH = "/bluetooth-connection";
    public static final String BLUETOOTH_CONNECTION_STATUS_PATH = "/bluetooth-connection-status";
    public static final String BLUETOOTH_REFRESHING_DEVICES_PATH = "/bluetooth-refreshing-devices";
    public static final String BLUETOOTH_REFRESHING_DEVICES_WEAR_PATH = "/bluetooth-refreshing-devices-wear";
    private static final String BOOLEAN_KEY = "boolean";
    private static final String BOOLEAN_LIST_KEY = "boolean_list";
    public static final String CONNECTION_WITH_SCHEMA = "/connection-with-schema";
    public static final String CURRENT_ACTIVITY_PATH = "/current_activity";
    public static final String CURRENT_DATA_PATH = "/current-data";
    public static final String CURRENT_SETTINGS_PATH = "/settings-current";
    public static final BooleanWearableDataLayer INSTANCE = new BooleanWearableDataLayer();
    public static final int STATE_FINISH = 0;
    public static final int STATE_LAUNCHED = 1;
    public static final int STATE_MAIN_SOUNDS = 2;
    public static final int STATE_RUN = 3;
    public static final int STATE_SGU = 4;
    public static final String UPLOADING_SOUND_PACKAGE_CLOSE_PATH = "/uploading-sound-package-close";
    public static final String UPLOADING_SOUND_PACKAGE_LOADING_PATH = "/uploading-sound-package-loading";

    @Override // com.carsystems.thor.datalayermodule.datalayer.datamaps.WearableDataLayer
    public String getKeyForArrayList() {
        return BOOLEAN_LIST_KEY;
    }

    private BooleanWearableDataLayer() {
    }

    @Override // com.carsystems.thor.datalayermodule.datalayer.datamaps.WearableDataLayer
    public /* bridge */ /* synthetic */ DataMap putDataInMap(DataMap dataMap, Boolean bool) {
        return putDataInMap(dataMap, bool.booleanValue());
    }

    public DataMap putDataInMap(DataMap dataMap, boolean convertDataTypeItem) {
        Intrinsics.checkNotNullParameter(dataMap, "dataMap");
        dataMap.putBoolean(BOOLEAN_KEY, convertDataTypeItem);
        return dataMap;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.carsystems.thor.datalayermodule.datalayer.datamaps.WearableDataLayer
    public Boolean convertFromDataMap(DataMap dataMap) {
        Intrinsics.checkNotNullParameter(dataMap, "dataMap");
        return Boolean.valueOf(dataMap.getBoolean(BOOLEAN_KEY));
    }
}
