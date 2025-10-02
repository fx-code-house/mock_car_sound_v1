package com.carsystems.thor.datalayermodule.datalayer.datamaps;

import com.google.android.gms.wearable.DataMap;
import com.thor.networkmodule.model.responses.sgu.SguSound;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: SguPackageWearableDataLayer.kt */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0004\bÆ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003J\u0010\u0010\u000e\u001a\u00020\u00022\u0006\u0010\u000f\u001a\u00020\u0010H\u0016J\b\u0010\u0011\u001a\u00020\u0005H\u0016J\u0018\u0010\u0012\u001a\u00020\u00102\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0013\u001a\u00020\u0002H\u0016R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u0014"}, d2 = {"Lcom/carsystems/thor/datalayermodule/datalayer/datamaps/SguPackageWearableDataLayer;", "Lcom/carsystems/thor/datalayermodule/datalayer/datamaps/WearableDataLayer;", "Lcom/thor/networkmodule/model/responses/sgu/SguSound;", "()V", "DATA_MAP_CATEGORY_ID", "", "DATA_MAP_SGU_SOUNDS", "DATA_MAP_SUBCATEGORY_DESCRIPTION", "DATA_MAP_SUBCATEGORY_ID", "DATA_MAP_SUBCATEGORY_IMAGE", "DATA_MAP_SUBCATEGORY_NAME", "DATA_MAP_SUBCATEGORY_VERSION", "SGU_PRESET_PACKAGES_PATH", "SGU_PRESET_PACKAGE_LIST", "convertFromDataMap", "dataMap", "Lcom/google/android/gms/wearable/DataMap;", "getKeyForArrayList", "putDataInMap", "convertDataTypeItem", "datalayermodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes.dex */
public final class SguPackageWearableDataLayer implements WearableDataLayer<SguSound> {
    private static final String DATA_MAP_CATEGORY_ID = "category_id";
    private static final String DATA_MAP_SGU_SOUNDS = "sgu_sounds";
    private static final String DATA_MAP_SUBCATEGORY_DESCRIPTION = "subcategory_description";
    private static final String DATA_MAP_SUBCATEGORY_ID = "subcategory_id";
    private static final String DATA_MAP_SUBCATEGORY_IMAGE = "subcategory_image";
    private static final String DATA_MAP_SUBCATEGORY_NAME = "subcategory_name";
    private static final String DATA_MAP_SUBCATEGORY_VERSION = "subcategory_version";
    public static final SguPackageWearableDataLayer INSTANCE = new SguPackageWearableDataLayer();
    public static final String SGU_PRESET_PACKAGES_PATH = "/sgu-preset-packages";
    private static final String SGU_PRESET_PACKAGE_LIST = "preset_list";

    @Override // com.carsystems.thor.datalayermodule.datalayer.datamaps.WearableDataLayer
    public String getKeyForArrayList() {
        return SGU_PRESET_PACKAGE_LIST;
    }

    private SguPackageWearableDataLayer() {
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.carsystems.thor.datalayermodule.datalayer.datamaps.WearableDataLayer
    public SguSound convertFromDataMap(DataMap dataMap) {
        Intrinsics.checkNotNullParameter(dataMap, "dataMap");
        return new SguSound(dataMap.getInt(DATA_MAP_SUBCATEGORY_ID), Integer.valueOf(dataMap.getInt(DATA_MAP_CATEGORY_ID)), dataMap.getString(DATA_MAP_SUBCATEGORY_NAME), dataMap.getString(DATA_MAP_SUBCATEGORY_IMAGE), dataMap.getString(DATA_MAP_SUBCATEGORY_DESCRIPTION), null, dataMap.getString(DATA_MAP_SUBCATEGORY_VERSION), CollectionsKt.emptyList(), false, null, false);
    }

    @Override // com.carsystems.thor.datalayermodule.datalayer.datamaps.WearableDataLayer
    public DataMap putDataInMap(DataMap dataMap, SguSound convertDataTypeItem) {
        Intrinsics.checkNotNullParameter(dataMap, "dataMap");
        Intrinsics.checkNotNullParameter(convertDataTypeItem, "convertDataTypeItem");
        dataMap.putInt(DATA_MAP_SUBCATEGORY_ID, convertDataTypeItem.getId());
        Integer sound_set_id = convertDataTypeItem.getSound_set_id();
        Intrinsics.checkNotNull(sound_set_id);
        dataMap.putInt(DATA_MAP_CATEGORY_ID, sound_set_id.intValue());
        String name = convertDataTypeItem.getName();
        Intrinsics.checkNotNull(name);
        dataMap.putString(DATA_MAP_SUBCATEGORY_NAME, name);
        String image = convertDataTypeItem.getImage();
        Intrinsics.checkNotNull(image);
        dataMap.putString(DATA_MAP_SUBCATEGORY_IMAGE, image);
        String description = convertDataTypeItem.getDescription();
        Intrinsics.checkNotNull(description);
        dataMap.putString(DATA_MAP_SUBCATEGORY_DESCRIPTION, description);
        String version = convertDataTypeItem.getVersion();
        Intrinsics.checkNotNull(version);
        dataMap.putString(DATA_MAP_SUBCATEGORY_VERSION, version);
        return dataMap;
    }
}
