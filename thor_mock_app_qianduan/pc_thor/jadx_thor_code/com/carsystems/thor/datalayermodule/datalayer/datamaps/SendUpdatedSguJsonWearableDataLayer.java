package com.carsystems.thor.datalayermodule.datalayer.datamaps;

import com.google.android.gms.wearable.DataMap;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: SendUpdatedSguJsonWearableDataLayer.kt */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\bÆ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003J\u0010\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\tH\u0016J\b\u0010\n\u001a\u00020\u0002H\u0016J\u0018\u0010\u000b\u001a\u00020\t2\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\f\u001a\u00020\u0002H\u0016R\u000e\u0010\u0004\u001a\u00020\u0002X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0002X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0002X\u0082T¢\u0006\u0002\n\u0000¨\u0006\r"}, d2 = {"Lcom/carsystems/thor/datalayermodule/datalayer/datamaps/SendUpdatedSguJsonWearableDataLayer;", "Lcom/carsystems/thor/datalayermodule/datalayer/datamaps/WearableDataLayer;", "", "()V", "DATA_MAP_SGU_JSON", "SGU_PRESET_JSON_PATH", "SGU_PRESET_PACKAGE_LIST", "convertFromDataMap", "dataMap", "Lcom/google/android/gms/wearable/DataMap;", "getKeyForArrayList", "putDataInMap", "convertDataTypeItem", "datalayermodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes.dex */
public final class SendUpdatedSguJsonWearableDataLayer implements WearableDataLayer<String> {
    private static final String DATA_MAP_SGU_JSON = "sgu_json";
    public static final SendUpdatedSguJsonWearableDataLayer INSTANCE = new SendUpdatedSguJsonWearableDataLayer();
    public static final String SGU_PRESET_JSON_PATH = "/sgu/json";
    private static final String SGU_PRESET_PACKAGE_LIST = "preset_list";

    @Override // com.carsystems.thor.datalayermodule.datalayer.datamaps.WearableDataLayer
    public String getKeyForArrayList() {
        return SGU_PRESET_PACKAGE_LIST;
    }

    private SendUpdatedSguJsonWearableDataLayer() {
    }

    @Override // com.carsystems.thor.datalayermodule.datalayer.datamaps.WearableDataLayer
    public String convertFromDataMap(DataMap dataMap) {
        Intrinsics.checkNotNullParameter(dataMap, "dataMap");
        String string = dataMap.getString(DATA_MAP_SGU_JSON);
        return string == null ? "" : string;
    }

    @Override // com.carsystems.thor.datalayermodule.datalayer.datamaps.WearableDataLayer
    public DataMap putDataInMap(DataMap dataMap, String convertDataTypeItem) {
        Intrinsics.checkNotNullParameter(dataMap, "dataMap");
        Intrinsics.checkNotNullParameter(convertDataTypeItem, "convertDataTypeItem");
        dataMap.putString(DATA_MAP_SGU_JSON, convertDataTypeItem);
        return dataMap;
    }
}
