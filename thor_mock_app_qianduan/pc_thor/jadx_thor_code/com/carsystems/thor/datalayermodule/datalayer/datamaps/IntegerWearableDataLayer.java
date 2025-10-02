package com.carsystems.thor.datalayermodule.datalayer.datamaps;

import com.google.android.gms.wearable.DataMap;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: IntegerWearableDataLayer.kt */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0005\bÆ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003J\u0015\u0010\f\u001a\u00020\u00022\u0006\u0010\r\u001a\u00020\u000eH\u0016¢\u0006\u0002\u0010\u000fJ\b\u0010\u0010\u001a\u00020\u0005H\u0016J\u0018\u0010\u0011\u001a\u00020\u000e2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0012\u001a\u00020\u0002H\u0016R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000¨\u0006\u0013"}, d2 = {"Lcom/carsystems/thor/datalayermodule/datalayer/datamaps/IntegerWearableDataLayer;", "Lcom/carsystems/thor/datalayermodule/datalayer/datamaps/WearableDataLayer;", "", "()V", "INTEGER_KEY", "", "INTEGER_LIST_KEY", "MAIN_PRESET_PACKAGE_INDEX_PATH", "MAIN_PRESET_SELECTED_INDEX_PATH", "RUNNING_APP_ON_PHONE_PATH", "RUNNING_APP_ON_WEARS_PATH", "SGU_PRESET_SELECTED_INDEX_PATH", "convertFromDataMap", "dataMap", "Lcom/google/android/gms/wearable/DataMap;", "(Lcom/google/android/gms/wearable/DataMap;)Ljava/lang/Integer;", "getKeyForArrayList", "putDataInMap", "convertDataTypeItem", "datalayermodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes.dex */
public final class IntegerWearableDataLayer implements WearableDataLayer<Integer> {
    public static final IntegerWearableDataLayer INSTANCE = new IntegerWearableDataLayer();
    private static final String INTEGER_KEY = "integer";
    private static final String INTEGER_LIST_KEY = "integer_list";
    public static final String MAIN_PRESET_PACKAGE_INDEX_PATH = "/main-preset-package-index";
    public static final String MAIN_PRESET_SELECTED_INDEX_PATH = "/main-preset-selected-index";
    public static final String RUNNING_APP_ON_PHONE_PATH = "/running/phone";
    public static final String RUNNING_APP_ON_WEARS_PATH = "/running/wears";
    public static final String SGU_PRESET_SELECTED_INDEX_PATH = "/sgu-preset-selected-index";

    @Override // com.carsystems.thor.datalayermodule.datalayer.datamaps.WearableDataLayer
    public String getKeyForArrayList() {
        return INTEGER_LIST_KEY;
    }

    private IntegerWearableDataLayer() {
    }

    @Override // com.carsystems.thor.datalayermodule.datalayer.datamaps.WearableDataLayer
    public /* bridge */ /* synthetic */ DataMap putDataInMap(DataMap dataMap, Integer num) {
        return putDataInMap(dataMap, num.intValue());
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.carsystems.thor.datalayermodule.datalayer.datamaps.WearableDataLayer
    public Integer convertFromDataMap(DataMap dataMap) {
        Intrinsics.checkNotNullParameter(dataMap, "dataMap");
        return Integer.valueOf(dataMap.getInt(INTEGER_KEY));
    }

    public DataMap putDataInMap(DataMap dataMap, int convertDataTypeItem) {
        Intrinsics.checkNotNullParameter(dataMap, "dataMap");
        dataMap.putInt(INTEGER_KEY, convertDataTypeItem);
        return dataMap;
    }
}
