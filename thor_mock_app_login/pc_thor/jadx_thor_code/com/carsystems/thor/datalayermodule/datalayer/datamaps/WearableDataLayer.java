package com.carsystems.thor.datalayermodule.datalayer.datamaps;

import com.google.android.gms.wearable.DataItem;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import kotlin.random.Random;
import timber.log.Timber;

/* loaded from: classes.dex */
public interface WearableDataLayer<ConvertDataType> {
    ConvertDataType convertFromDataMap(DataMap dataMap);

    String getKeyForArrayList();

    DataMap putDataInMap(DataMap dataMap, ConvertDataType convertdatatype);

    default Collection<ConvertDataType> convertToCollectionFromDataMap(DataMap dataMap) {
        ArrayList arrayList = new ArrayList();
        ArrayList<DataMap> dataMapArrayList = dataMap.getDataMapArrayList(getKeyForArrayList());
        if (dataMapArrayList != null && !dataMapArrayList.isEmpty()) {
            Iterator<DataMap> it = dataMapArrayList.iterator();
            while (it.hasNext()) {
                arrayList.add(convertFromDataMap(it.next()));
            }
        }
        return arrayList;
    }

    default PutDataRequest convertToDataRequest(String str, ConvertDataType convertdatatype) {
        PutDataMapRequest putDataMapRequestCreate = PutDataMapRequest.create(str);
        putDataInMap(putDataMapRequestCreate.getDataMap(), convertdatatype);
        putDataMapRequestCreate.getDataMap().putInt("random_value", Random.INSTANCE.nextInt());
        PutDataRequest putDataRequestAsPutDataRequest = putDataMapRequestCreate.asPutDataRequest();
        putDataRequestAsPutDataRequest.setUrgent();
        return putDataRequestAsPutDataRequest;
    }

    default PutDataRequest convertCollectionToDataRequest(String str, Collection<ConvertDataType> collection) {
        PutDataMapRequest putDataMapRequestCreate = PutDataMapRequest.create(str);
        ArrayList<DataMap> arrayList = new ArrayList<>();
        if (collection != null) {
            try {
                if (collection.size() > 0) {
                    Iterator<ConvertDataType> it = collection.iterator();
                    while (it.hasNext()) {
                        arrayList.add(putDataInMap(new DataMap(), it.next()));
                    }
                }
            } catch (ConcurrentModificationException e) {
                Timber.e(e);
            }
        }
        putDataMapRequestCreate.getDataMap().putInt("random_value", Random.INSTANCE.nextInt());
        putDataMapRequestCreate.getDataMap().putDataMapArrayList(getKeyForArrayList(), arrayList);
        PutDataRequest putDataRequestAsPutDataRequest = putDataMapRequestCreate.asPutDataRequest();
        putDataRequestAsPutDataRequest.setUrgent();
        return putDataRequestAsPutDataRequest;
    }

    default ConvertDataType convertFromDataItem(DataItem dataItem) {
        return convertFromDataMap(DataMapItem.fromDataItem(dataItem).getDataMap());
    }

    default Collection<ConvertDataType> convertToCollectionFromDataItem(DataItem dataItem) {
        return convertToCollectionFromDataMap(DataMapItem.fromDataItem(dataItem).getDataMap());
    }
}
