package com.thor.app.datalayer.tasks;

import android.content.Context;
import android.os.AsyncTask;
import com.google.android.gms.tasks.Tasks;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.Wearable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import timber.log.Timber;

/* loaded from: classes2.dex */
public class StartWearableActivityTask extends AsyncTask<Void, Void, Void> {
    private final Context mContext;
    private final String path;

    public StartWearableActivityTask(Context context, String path) {
        this.mContext = context;
        this.path = path;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    public Void doInBackground(Void... args) {
        Iterator<String> it = getNodes().iterator();
        while (it.hasNext()) {
            sendStartActivityMessage(it.next(), this.path);
        }
        return null;
    }

    private Collection<String> getNodes() {
        HashSet hashSet = new HashSet();
        try {
            Iterator it = ((List) Tasks.await(Wearable.getNodeClient(this.mContext).getConnectedNodes())).iterator();
            while (it.hasNext()) {
                hashSet.add(((Node) it.next()).getId());
            }
        } catch (InterruptedException e) {
            Timber.i("Interrupt occurred: ", new Object[0]);
            Timber.w(e);
        } catch (ExecutionException e2) {
            Timber.i("Task failed:", new Object[0]);
            Timber.w(e2);
        }
        return hashSet;
    }

    private void sendStartActivityMessage(String node, String path) {
        try {
            Timber.i("Message sent: %s", (Integer) Tasks.await(Wearable.getMessageClient(this.mContext).sendMessage(node, path, new byte[0])));
        } catch (InterruptedException e) {
            Timber.i("Interrupt occurred: ", new Object[0]);
            Timber.w(e);
        } catch (ExecutionException e2) {
            Timber.i("Task failed:", new Object[0]);
            Timber.w(e2);
        }
    }
}
