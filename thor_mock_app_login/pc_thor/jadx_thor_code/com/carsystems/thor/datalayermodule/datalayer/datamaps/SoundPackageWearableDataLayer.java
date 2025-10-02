package com.carsystems.thor.datalayermodule.datalayer.datamaps;

import com.google.android.exoplayer2.analytics.AnalyticsListener;
import com.google.android.gms.wearable.DataMap;
import com.thor.businessmodule.model.MainPresetPackage;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: SoundPackageWearableDataLayer.kt */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0004\bÆ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003J\u0010\u0010\r\u001a\u00020\u00022\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J\b\u0010\u0010\u001a\u00020\u0005H\u0016J\u0018\u0010\u0011\u001a\u00020\u000f2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0012\u001a\u00020\u0002H\u0016R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u0013"}, d2 = {"Lcom/carsystems/thor/datalayermodule/datalayer/datamaps/SoundPackageWearableDataLayer;", "Lcom/carsystems/thor/datalayermodule/datalayer/datamaps/WearableDataLayer;", "Lcom/thor/businessmodule/model/MainPresetPackage;", "()V", "DATA_MAP_IMAGE_URL", "", "DATA_MAP_IS_ACTIVATED", "DATA_MAP_MODE_TYPE", "DATA_MAP_NAME", "DATA_MAP_PACKAGE_ID", "DATA_MAP_VERSION_ID", "MAIN_PRESET_PACKAGES_PATH", "MAIN_PRESET_PACKAGE_LIST", "convertFromDataMap", "dataMap", "Lcom/google/android/gms/wearable/DataMap;", "getKeyForArrayList", "putDataInMap", "convertDataTypeItem", "datalayermodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes.dex */
public final class SoundPackageWearableDataLayer implements WearableDataLayer<MainPresetPackage> {
    private static final String DATA_MAP_IMAGE_URL = "image_url";
    private static final String DATA_MAP_IS_ACTIVATED = "is_activated";
    private static final String DATA_MAP_MODE_TYPE = "mode_type";
    private static final String DATA_MAP_NAME = "name";
    private static final String DATA_MAP_PACKAGE_ID = "package_id";
    private static final String DATA_MAP_VERSION_ID = "version_id";
    public static final SoundPackageWearableDataLayer INSTANCE = new SoundPackageWearableDataLayer();
    public static final String MAIN_PRESET_PACKAGES_PATH = "/main-preset-packages";
    private static final String MAIN_PRESET_PACKAGE_LIST = "preset_list";

    @Override // com.carsystems.thor.datalayermodule.datalayer.datamaps.WearableDataLayer
    public String getKeyForArrayList() {
        return MAIN_PRESET_PACKAGE_LIST;
    }

    private SoundPackageWearableDataLayer() {
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.carsystems.thor.datalayermodule.datalayer.datamaps.WearableDataLayer
    public MainPresetPackage convertFromDataMap(DataMap dataMap) {
        Intrinsics.checkNotNullParameter(dataMap, "dataMap");
        MainPresetPackage mainPresetPackage = new MainPresetPackage(null, null, null, null, null, false, null, null, null, false, AnalyticsListener.EVENT_DROPPED_VIDEO_FRAMES, null);
        mainPresetPackage.setPackageId(Integer.valueOf(dataMap.getInt(DATA_MAP_PACKAGE_ID)));
        mainPresetPackage.setName(dataMap.getString("name"));
        mainPresetPackage.setImageUrl(dataMap.getString(DATA_MAP_IMAGE_URL));
        mainPresetPackage.setVersionId(Integer.valueOf(dataMap.getInt(DATA_MAP_VERSION_ID)));
        mainPresetPackage.setModeType(Short.valueOf((short) dataMap.getInt(DATA_MAP_MODE_TYPE)));
        mainPresetPackage.setActivated(dataMap.getBoolean(DATA_MAP_IS_ACTIVATED));
        return mainPresetPackage;
    }

    @Override // com.carsystems.thor.datalayermodule.datalayer.datamaps.WearableDataLayer
    public DataMap putDataInMap(DataMap dataMap, MainPresetPackage convertDataTypeItem) {
        Intrinsics.checkNotNullParameter(dataMap, "dataMap");
        Intrinsics.checkNotNullParameter(convertDataTypeItem, "convertDataTypeItem");
        Integer packageId = convertDataTypeItem.getPackageId();
        Intrinsics.checkNotNull(packageId);
        dataMap.putInt(DATA_MAP_PACKAGE_ID, packageId.intValue());
        String name = convertDataTypeItem.getName();
        Intrinsics.checkNotNull(name);
        dataMap.putString("name", name);
        Integer versionId = convertDataTypeItem.getVersionId();
        Intrinsics.checkNotNull(versionId);
        dataMap.putInt(DATA_MAP_VERSION_ID, versionId.intValue());
        Short modeType = convertDataTypeItem.getModeType();
        Intrinsics.checkNotNull(modeType);
        dataMap.putInt(DATA_MAP_MODE_TYPE, modeType.shortValue());
        dataMap.putBoolean(DATA_MAP_IS_ACTIVATED, convertDataTypeItem.isActivated());
        return dataMap;
    }
}
