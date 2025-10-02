package com.thor.app.utils.data;

import android.content.Context;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.wearable.DataItem;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;
import com.thor.app.datalayer.tasks.StartWearableActivityTask;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import timber.log.Timber;

/* loaded from: classes3.dex */
public class DataLayerHelper {
    private static final String BLUETOOTH_CONNECTION_KEY = "bluetooth_connected";
    private static final String BLUETOOTH_CONNECTION_PATH = "/bluetooth-connection";
    private static final String START_BLUETOOTH_SEARCH_ACTIVITY_PATH = "/start-bluetooth-search-activity";
    private static final String START_MAIN_ACTIVITY_PATH = "/start-connected-activity";
    private static final String START_SIGN_UP_ACTIVITY_PATH = "/start-sign-up-activity";
    private static final String START_UPLOADING_ACTIVITY_PATH = "/start-uploading-activity";
    private final Context mContext;

    private DataLayerHelper(Context context) {
        this.mContext = context;
    }

    public static DataLayerHelper newInstance(Context context) {
        return new DataLayerHelper(context);
    }

    public void onStartBluetoothSearchWearableActivity() {
        Timber.i("Generating RPC for BluetoothSearchWearableActivity", new Object[0]);
        new StartWearableActivityTask(this.mContext, START_BLUETOOTH_SEARCH_ACTIVITY_PATH).execute(new Void[0]);
    }

    public void onStartUploadingWearableActivity() {
        Timber.i("Generating RPC for UploadingWearableActivity", new Object[0]);
        new StartWearableActivityTask(this.mContext, START_UPLOADING_ACTIVITY_PATH).execute(new Void[0]);
    }

    public void onStartMainWearableActivity() {
        Timber.i("Generating RPC for MainWearableActivity", new Object[0]);
        new StartWearableActivityTask(this.mContext, START_MAIN_ACTIVITY_PATH).execute(new Void[0]);
    }

    public void onStartSignUpWearableActivity() {
        Timber.i("Generating RPC for SignUpWearableActivity", new Object[0]);
        new StartWearableActivityTask(this.mContext, START_SIGN_UP_ACTIVITY_PATH).execute(new Void[0]);
    }

    public Single<Boolean> isConnectedToWear() {
        return Single.create(new SingleOnSubscribe<Boolean>() { // from class: com.thor.app.utils.data.DataLayerHelper.1
            @Override // io.reactivex.SingleOnSubscribe
            public void subscribe(final SingleEmitter<Boolean> emitter) throws Exception {
                DataLayerHelper.this.getNodes().addOnSuccessListener(new OnSuccessListener<List<Node>>() { // from class: com.thor.app.utils.data.DataLayerHelper.1.1
                    @Override // com.google.android.gms.tasks.OnSuccessListener
                    public void onSuccess(List<Node> nodes) {
                        emitter.onSuccess(Boolean.valueOf(nodes.size() > 0));
                    }
                });
                DataLayerHelper.this.getNodes().addOnFailureListener(new OnFailureListener() { // from class: com.thor.app.utils.data.DataLayerHelper.1.2
                    @Override // com.google.android.gms.tasks.OnFailureListener
                    public void onFailure(Exception e) {
                    }
                });
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Task<List<Node>> getNodes() {
        new HashSet();
        return Wearable.getNodeClient(this.mContext).getConnectedNodes();
    }

    public void sendConnectionData(boolean isConnected) {
        PutDataMapRequest putDataMapRequestCreate = PutDataMapRequest.create("/bluetooth-connection");
        putDataMapRequestCreate.getDataMap().putBoolean(BLUETOOTH_CONNECTION_KEY, isConnected);
        putDataMapRequestCreate.getDataMap().putLong("time", new Date().getTime());
        PutDataRequest putDataRequestAsPutDataRequest = putDataMapRequestCreate.asPutDataRequest();
        putDataRequestAsPutDataRequest.setUrgent();
        Wearable.getDataClient(this.mContext).putDataItem(putDataRequestAsPutDataRequest).addOnSuccessListener(new OnSuccessListener<DataItem>() { // from class: com.thor.app.utils.data.DataLayerHelper.2
            @Override // com.google.android.gms.tasks.OnSuccessListener
            public void onSuccess(DataItem dataItem) {
                Timber.i("Sending connection data request was successful: " + dataItem, new Object[0]);
            }
        });
    }
}
