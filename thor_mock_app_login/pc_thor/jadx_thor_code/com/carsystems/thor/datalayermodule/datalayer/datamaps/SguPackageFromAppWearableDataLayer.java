package com.carsystems.thor.datalayermodule.datalayer.datamaps;

import com.google.android.gms.wearable.DataMap;
import com.thor.networkmodule.model.responses.sgu.SguSound;
import com.thor.networkmodule.model.responses.sgu.SguSoundFile;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: SguPackageFromAppWearableDataLayer.kt */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u000e\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\bÆ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003J*\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00150\u00142\u001a\u0010\u0016\u001a\u0016\u0012\u0004\u0012\u00020\u0018\u0018\u00010\u0017j\n\u0012\u0004\u0012\u00020\u0018\u0018\u0001`\u0019H\u0002J\u0010\u0010\u001a\u001a\u00020\u00022\u0006\u0010\u001b\u001a\u00020\u0018H\u0016J&\u0010\u001c\u001a\u0012\u0012\u0004\u0012\u00020\u00180\u0017j\b\u0012\u0004\u0012\u00020\u0018`\u00192\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00150\u0014H\u0002J\b\u0010\u001d\u001a\u00020\u0005H\u0016J\u0018\u0010\u001e\u001a\u00020\u00182\u0006\u0010\u001b\u001a\u00020\u00182\u0006\u0010\u001f\u001a\u00020\u0002H\u0016R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000¨\u0006 "}, d2 = {"Lcom/carsystems/thor/datalayermodule/datalayer/datamaps/SguPackageFromAppWearableDataLayer;", "Lcom/carsystems/thor/datalayermodule/datalayer/datamaps/WearableDataLayer;", "Lcom/thor/networkmodule/model/responses/sgu/SguSound;", "()V", "DATA_MAP_CATEGORY_ID", "", "DATA_MAP_SGU_SOUNDS", "DATA_MAP_SGU_SUBCATEGORY_SAMPLE", "DATA_MAP_SOUND_FILE_FILE", "DATA_MAP_SOUND_FILE_ID", "DATA_MAP_SOUND_FILE_VERSION", "DATA_MAP_SUBCATEGORY_DESCRIPTION", "DATA_MAP_SUBCATEGORY_ID", "DATA_MAP_SUBCATEGORY_IMAGE", "DATA_MAP_SUBCATEGORY_NAME", "DATA_MAP_SUBCATEGORY_SAMPLE", "DATA_MAP_SUBCATEGORY_VERSION", "SGU_FROM_APP_DATA_PATH", "SGU_PRESET_PACKAGE_LIST", "convertArrayDataMapToList", "", "Lcom/thor/networkmodule/model/responses/sgu/SguSoundFile;", "soundFiles", "Ljava/util/ArrayList;", "Lcom/google/android/gms/wearable/DataMap;", "Lkotlin/collections/ArrayList;", "convertFromDataMap", "dataMap", "convertListToArrayDataMapList", "getKeyForArrayList", "putDataInMap", "convertDataTypeItem", "datalayermodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes.dex */
public final class SguPackageFromAppWearableDataLayer implements WearableDataLayer<SguSound> {
    private static final String DATA_MAP_CATEGORY_ID = "category_id";
    private static final String DATA_MAP_SGU_SOUNDS = "sgu_sounds";
    private static final String DATA_MAP_SGU_SUBCATEGORY_SAMPLE = "subcategory_version";
    private static final String DATA_MAP_SOUND_FILE_FILE = "file";
    private static final String DATA_MAP_SOUND_FILE_ID = "id";
    private static final String DATA_MAP_SOUND_FILE_VERSION = "version";
    private static final String DATA_MAP_SUBCATEGORY_DESCRIPTION = "subcategory_description";
    private static final String DATA_MAP_SUBCATEGORY_ID = "subcategory_id";
    private static final String DATA_MAP_SUBCATEGORY_IMAGE = "subcategory_image";
    private static final String DATA_MAP_SUBCATEGORY_NAME = "subcategory_name";
    private static final String DATA_MAP_SUBCATEGORY_SAMPLE = "subcategory_sample_waw";
    private static final String DATA_MAP_SUBCATEGORY_VERSION = "subcategory_version";
    public static final SguPackageFromAppWearableDataLayer INSTANCE = new SguPackageFromAppWearableDataLayer();
    public static final String SGU_FROM_APP_DATA_PATH = "/sgu/from-app";
    private static final String SGU_PRESET_PACKAGE_LIST = "preset_list";

    @Override // com.carsystems.thor.datalayermodule.datalayer.datamaps.WearableDataLayer
    public String getKeyForArrayList() {
        return SGU_PRESET_PACKAGE_LIST;
    }

    private SguPackageFromAppWearableDataLayer() {
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.carsystems.thor.datalayermodule.datalayer.datamaps.WearableDataLayer
    public SguSound convertFromDataMap(DataMap dataMap) {
        Intrinsics.checkNotNullParameter(dataMap, "dataMap");
        return new SguSound(dataMap.getInt(DATA_MAP_SUBCATEGORY_ID), Integer.valueOf(dataMap.getInt(DATA_MAP_CATEGORY_ID)), dataMap.getString(DATA_MAP_SUBCATEGORY_NAME), dataMap.getString(DATA_MAP_SUBCATEGORY_IMAGE), dataMap.getString(DATA_MAP_SUBCATEGORY_DESCRIPTION), dataMap.getString(DATA_MAP_SUBCATEGORY_SAMPLE), dataMap.getString("subcategory_version"), convertArrayDataMapToList(dataMap.getDataMapArrayList(DATA_MAP_SGU_SOUNDS)), false, null, true);
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
        dataMap.putString("subcategory_version", version);
        dataMap.putDataMapArrayList(DATA_MAP_SGU_SOUNDS, convertListToArrayDataMapList(convertDataTypeItem.getSoundFiles()));
        return dataMap;
    }

    private final ArrayList<DataMap> convertListToArrayDataMapList(List<SguSoundFile> soundFiles) {
        ArrayList<DataMap> arrayList = new ArrayList<>();
        for (SguSoundFile sguSoundFile : soundFiles) {
            DataMap dataMap = new DataMap();
            dataMap.putInt("id", sguSoundFile.getId());
            dataMap.putString(DATA_MAP_SOUND_FILE_FILE, sguSoundFile.getFile());
            dataMap.putInt(DATA_MAP_SOUND_FILE_VERSION, sguSoundFile.getVersion());
            arrayList.add(dataMap);
        }
        return arrayList;
    }

    private final List<SguSoundFile> convertArrayDataMapToList(ArrayList<DataMap> soundFiles) {
        ArrayList arrayList = new ArrayList();
        if (soundFiles != null) {
            for (DataMap dataMap : soundFiles) {
                int i = dataMap.getInt("id");
                String string = dataMap.getString(DATA_MAP_SOUND_FILE_FILE);
                Intrinsics.checkNotNullExpressionValue(string, "it.getString(DATA_MAP_SOUND_FILE_FILE)");
                arrayList.add(new SguSoundFile(i, string, dataMap.getInt(DATA_MAP_SOUND_FILE_VERSION)));
            }
        }
        return arrayList;
    }
}
