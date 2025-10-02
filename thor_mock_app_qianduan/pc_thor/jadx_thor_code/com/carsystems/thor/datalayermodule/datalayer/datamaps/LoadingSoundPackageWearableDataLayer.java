package com.carsystems.thor.datalayermodule.datalayer.datamaps;

import com.carsystems.thor.datalayermodule.datalayer.models.UploadingSoundPackage;
import com.google.android.gms.wearable.DataMap;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: LoadingSoundPackageWearableDataLayer.kt */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\bÆ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003J\u0010\u0010\t\u001a\u00020\u00022\u0006\u0010\n\u001a\u00020\u000bH\u0016J\b\u0010\f\u001a\u00020\u0005H\u0016J\u0018\u0010\r\u001a\u00020\u000b2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u000e\u001a\u00020\u0002H\u0016R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u000f"}, d2 = {"Lcom/carsystems/thor/datalayermodule/datalayer/datamaps/LoadingSoundPackageWearableDataLayer;", "Lcom/carsystems/thor/datalayermodule/datalayer/datamaps/WearableDataLayer;", "Lcom/carsystems/thor/datalayermodule/datalayer/models/UploadingSoundPackage;", "()V", "UPLOADING_SOUND_PACKAGE_LIST_KEY", "", "UPLOADING_SOUND_PACKAGE_NAME_KEY", "UPLOADING_SOUND_PACKAGE_PATH", "UPLOADING_SOUND_PACKAGE_PROGRESS_KEY", "convertFromDataMap", "dataMap", "Lcom/google/android/gms/wearable/DataMap;", "getKeyForArrayList", "putDataInMap", "convertDataTypeItem", "datalayermodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes.dex */
public final class LoadingSoundPackageWearableDataLayer implements WearableDataLayer<UploadingSoundPackage> {
    public static final LoadingSoundPackageWearableDataLayer INSTANCE = new LoadingSoundPackageWearableDataLayer();
    private static final String UPLOADING_SOUND_PACKAGE_LIST_KEY = "uploading_sound_package_list";
    private static final String UPLOADING_SOUND_PACKAGE_NAME_KEY = "uploading_sound_package_name";
    public static final String UPLOADING_SOUND_PACKAGE_PATH = "/uploading-sound-package";
    private static final String UPLOADING_SOUND_PACKAGE_PROGRESS_KEY = "uploading_sound_package_progress";

    @Override // com.carsystems.thor.datalayermodule.datalayer.datamaps.WearableDataLayer
    public String getKeyForArrayList() {
        return UPLOADING_SOUND_PACKAGE_LIST_KEY;
    }

    private LoadingSoundPackageWearableDataLayer() {
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.carsystems.thor.datalayermodule.datalayer.datamaps.WearableDataLayer
    public UploadingSoundPackage convertFromDataMap(DataMap dataMap) {
        Intrinsics.checkNotNullParameter(dataMap, "dataMap");
        return new UploadingSoundPackage(dataMap.getString(UPLOADING_SOUND_PACKAGE_NAME_KEY), dataMap.getInt(UPLOADING_SOUND_PACKAGE_PROGRESS_KEY));
    }

    @Override // com.carsystems.thor.datalayermodule.datalayer.datamaps.WearableDataLayer
    public DataMap putDataInMap(DataMap dataMap, UploadingSoundPackage convertDataTypeItem) {
        Intrinsics.checkNotNullParameter(dataMap, "dataMap");
        Intrinsics.checkNotNullParameter(convertDataTypeItem, "convertDataTypeItem");
        String name = convertDataTypeItem.getName();
        if (name == null) {
            name = "";
        }
        dataMap.putString(UPLOADING_SOUND_PACKAGE_NAME_KEY, name);
        dataMap.putInt(UPLOADING_SOUND_PACKAGE_PROGRESS_KEY, convertDataTypeItem.getProgress());
        return dataMap;
    }
}
