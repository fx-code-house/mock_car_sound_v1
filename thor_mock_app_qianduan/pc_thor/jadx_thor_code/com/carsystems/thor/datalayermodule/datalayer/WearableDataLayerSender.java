package com.carsystems.thor.datalayermodule.datalayer;

import android.content.Context;
import com.carsystems.thor.datalayermodule.datalayer.datamaps.WearableDataLayer;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.wearable.DataItem;
import com.google.android.gms.wearable.Wearable;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import java.util.Collection;

/* loaded from: classes.dex */
public class WearableDataLayerSender<ConvertDataType> {
    private final Context mContext;
    private final CompositeDisposable mDisposable = new CompositeDisposable();

    private WearableDataLayerSender(Context context) {
        this.mContext = context;
    }

    public static WearableDataLayerSender from(Context context) {
        return new WearableDataLayerSender(context);
    }

    public Task<DataItem> sendDataItem(String str, WearableDataLayer<ConvertDataType> wearableDataLayer, ConvertDataType convertdatatype) {
        return Wearable.getDataClient(this.mContext).putDataItem(wearableDataLayer.convertToDataRequest(str, convertdatatype));
    }

    public Single<DataItem> sendRxDataItem(final String str, final WearableDataLayer<ConvertDataType> wearableDataLayer, final ConvertDataType convertdatatype) {
        return Single.create(new SingleOnSubscribe<DataItem>() { // from class: com.carsystems.thor.datalayermodule.datalayer.WearableDataLayerSender.1
            /* JADX WARN: Multi-variable type inference failed */
            @Override // io.reactivex.SingleOnSubscribe
            public void subscribe(final SingleEmitter<DataItem> singleEmitter) throws Exception {
                Task<DataItem> taskPutDataItem = Wearable.getDataClient(WearableDataLayerSender.this.mContext).putDataItem(wearableDataLayer.convertToDataRequest(str, convertdatatype));
                taskPutDataItem.addOnSuccessListener(new OnSuccessListener<DataItem>() { // from class: com.carsystems.thor.datalayermodule.datalayer.WearableDataLayerSender.1.1
                    @Override // com.google.android.gms.tasks.OnSuccessListener
                    public void onSuccess(DataItem dataItem) {
                        singleEmitter.onSuccess(dataItem);
                    }
                });
                taskPutDataItem.addOnFailureListener(new OnFailureListener() { // from class: com.carsystems.thor.datalayermodule.datalayer.WearableDataLayerSender.1.2
                    @Override // com.google.android.gms.tasks.OnFailureListener
                    public void onFailure(Exception exc) {
                        singleEmitter.onError(exc);
                    }
                });
            }
        });
    }

    public Single<DataItem> sendRxDataItems(final String str, final WearableDataLayer<ConvertDataType> wearableDataLayer, final Collection<ConvertDataType> collection) {
        return Single.create(new SingleOnSubscribe<DataItem>() { // from class: com.carsystems.thor.datalayermodule.datalayer.WearableDataLayerSender.2
            @Override // io.reactivex.SingleOnSubscribe
            public void subscribe(final SingleEmitter<DataItem> singleEmitter) throws Exception {
                Task<DataItem> taskPutDataItem = Wearable.getDataClient(WearableDataLayerSender.this.mContext).putDataItem(wearableDataLayer.convertCollectionToDataRequest(str, collection));
                taskPutDataItem.addOnSuccessListener(new OnSuccessListener<DataItem>() { // from class: com.carsystems.thor.datalayermodule.datalayer.WearableDataLayerSender.2.1
                    @Override // com.google.android.gms.tasks.OnSuccessListener
                    public void onSuccess(DataItem dataItem) {
                        singleEmitter.onSuccess(dataItem);
                    }
                });
                taskPutDataItem.addOnFailureListener(new OnFailureListener() { // from class: com.carsystems.thor.datalayermodule.datalayer.WearableDataLayerSender.2.2
                    @Override // com.google.android.gms.tasks.OnFailureListener
                    public void onFailure(Exception exc) {
                        singleEmitter.onError(exc);
                    }
                });
            }
        });
    }

    public void addToDisposable(Disposable... disposableArr) {
        this.mDisposable.addAll(disposableArr);
    }
}
