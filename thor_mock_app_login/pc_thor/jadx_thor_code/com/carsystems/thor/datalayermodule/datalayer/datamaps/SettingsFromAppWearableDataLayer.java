package com.carsystems.thor.datalayermodule.datalayer.datamaps;

import com.carsystems.thor.datalayermodule.datalayer.SettingsFromService;
import com.google.android.gms.wearable.DataMap;
import com.thor.networkmodule.model.responses.SignInResponse;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: SettingsFromAppWearableDataLayer.kt */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0002\b\u0004\bÆ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003J\u0010\u0010\u0013\u001a\u00020\u00022\u0006\u0010\u0014\u001a\u00020\u0015H\u0016J\b\u0010\u0016\u001a\u00020\u0005H\u0016J\u0018\u0010\u0017\u001a\u00020\u00152\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0018\u001a\u00020\u0002H\u0016R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u0019"}, d2 = {"Lcom/carsystems/thor/datalayermodule/datalayer/datamaps/SettingsFromAppWearableDataLayer;", "Lcom/carsystems/thor/datalayermodule/datalayer/datamaps/WearableDataLayer;", "Lcom/carsystems/thor/datalayermodule/datalayer/SettingsFromService;", "()V", "CAR_GENERATION_ID", "", "CAR_GENERATION_NAME", "CAR_MARK_ID", "CAR_MARK_NAME", "CAR_MODEL_ID", "CAR_MODEL_NAME", "CAR_SERIE_ID", "CAR_SERIE_NAME", "DEVICE_SERIAL_NUMBER", "MAC_ADDRESS", "SETTINGS_FROM_APP_LIST_KEY", "SETTINGS_FROM_APP_PATH", "TOKEN", "USER_ID", "convertFromDataMap", "dataMap", "Lcom/google/android/gms/wearable/DataMap;", "getKeyForArrayList", "putDataInMap", "convertDataTypeItem", "datalayermodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes.dex */
public final class SettingsFromAppWearableDataLayer implements WearableDataLayer<SettingsFromService> {
    private static final String CAR_GENERATION_ID = "car_generation_id";
    private static final String CAR_GENERATION_NAME = "car_generation_name";
    private static final String CAR_MARK_ID = "car_mark_id";
    private static final String CAR_MARK_NAME = "car_mark_name";
    private static final String CAR_MODEL_ID = "car_model_id";
    private static final String CAR_MODEL_NAME = "car_model_name";
    private static final String CAR_SERIE_ID = "car_serie_id";
    private static final String CAR_SERIE_NAME = "car_serie_name";
    private static final String DEVICE_SERIAL_NUMBER = "device_serial_number";
    public static final SettingsFromAppWearableDataLayer INSTANCE = new SettingsFromAppWearableDataLayer();
    private static final String MAC_ADDRESS = "mac_address";
    private static final String SETTINGS_FROM_APP_LIST_KEY = "settings_from_app_list";
    public static final String SETTINGS_FROM_APP_PATH = "/settings/from/app";
    private static final String TOKEN = "token";
    private static final String USER_ID = "user_id";

    @Override // com.carsystems.thor.datalayermodule.datalayer.datamaps.WearableDataLayer
    public String getKeyForArrayList() {
        return SETTINGS_FROM_APP_LIST_KEY;
    }

    private SettingsFromAppWearableDataLayer() {
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.carsystems.thor.datalayermodule.datalayer.datamaps.WearableDataLayer
    public SettingsFromService convertFromDataMap(DataMap dataMap) {
        Intrinsics.checkNotNullParameter(dataMap, "dataMap");
        SignInResponse signInResponse = new SignInResponse(0, 0, null, 0, null, 0, null, 0, null, null, null, 2047, null);
        signInResponse.setUserId(dataMap.getInt(USER_ID));
        signInResponse.setCarMarkId(dataMap.getInt(CAR_MARK_ID));
        signInResponse.setCarMarkName(dataMap.getString(CAR_MARK_NAME));
        signInResponse.setCarModelId(dataMap.getInt(CAR_MODEL_ID));
        signInResponse.setCarModelName(dataMap.getString("car_model_name"));
        signInResponse.setCarGenerationId(dataMap.getInt(CAR_GENERATION_ID));
        signInResponse.setCarGenerationName(dataMap.getString(CAR_GENERATION_NAME));
        signInResponse.setCarSerieId(dataMap.getInt(CAR_SERIE_ID));
        signInResponse.setCarSerieName(dataMap.getString("car_serie_name"));
        signInResponse.setDeviceSN(dataMap.getString(DEVICE_SERIAL_NUMBER));
        signInResponse.setToken(dataMap.getString(TOKEN));
        String macAddress = dataMap.getString(MAC_ADDRESS);
        Intrinsics.checkNotNullExpressionValue(macAddress, "macAddress");
        return new SettingsFromService(signInResponse, macAddress);
    }

    @Override // com.carsystems.thor.datalayermodule.datalayer.datamaps.WearableDataLayer
    public DataMap putDataInMap(DataMap dataMap, SettingsFromService convertDataTypeItem) {
        Intrinsics.checkNotNullParameter(dataMap, "dataMap");
        Intrinsics.checkNotNullParameter(convertDataTypeItem, "convertDataTypeItem");
        dataMap.putInt(USER_ID, convertDataTypeItem.getResponse().getUserId());
        dataMap.putInt(CAR_MARK_ID, convertDataTypeItem.getResponse().getCarMarkId());
        dataMap.putString(CAR_MARK_NAME, convertDataTypeItem.getResponse().getCarMarkName());
        dataMap.putInt(CAR_MODEL_ID, convertDataTypeItem.getResponse().getCarModelId());
        dataMap.putString("car_model_name", convertDataTypeItem.getResponse().getCarModelName());
        dataMap.putInt(CAR_GENERATION_ID, convertDataTypeItem.getResponse().getCarGenerationId());
        dataMap.putString(CAR_GENERATION_NAME, convertDataTypeItem.getResponse().getCarGenerationName());
        dataMap.putInt(CAR_SERIE_ID, convertDataTypeItem.getResponse().getCarSerieId());
        dataMap.putString("car_serie_name", convertDataTypeItem.getResponse().getCarSerieName());
        dataMap.putString(DEVICE_SERIAL_NUMBER, convertDataTypeItem.getResponse().getDeviceSN());
        dataMap.putString(TOKEN, convertDataTypeItem.getResponse().getToken());
        dataMap.putString(MAC_ADDRESS, convertDataTypeItem.getBluetoothDeviceMacAddress());
        return dataMap;
    }
}
