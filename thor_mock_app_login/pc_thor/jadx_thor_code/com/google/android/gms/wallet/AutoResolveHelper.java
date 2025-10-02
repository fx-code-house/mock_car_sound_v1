package com.google.android.gms.wallet;

import android.app.Activity;
import android.app.Fragment;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;
import android.util.SparseArray;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.ApiExceptionUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/* compiled from: com.google.android.gms:play-services-wallet@@18.0.0 */
/* loaded from: classes2.dex */
public class AutoResolveHelper {
    public static final int RESULT_ERROR = 1;
    private static final long zzv = TimeUnit.MINUTES.toMillis(10);
    static long zzw = SystemClock.elapsedRealtime();

    /* compiled from: com.google.android.gms:play-services-wallet@@18.0.0 */
    static class zza<TResult extends AutoResolvableResult> implements OnCompleteListener<TResult>, Runnable {
        private static final Handler zzp = new com.google.android.gms.internal.wallet.zzf(Looper.getMainLooper());
        static final SparseArray<zza<?>> zzq = new SparseArray<>(2);
        private static final AtomicInteger zzr = new AtomicInteger();
        int zzs;
        private zzb zzt;
        private Task<TResult> zzu;

        zza() {
        }

        public static <TResult extends AutoResolvableResult> zza<TResult> zza(Task<TResult> task) {
            zza<TResult> zzaVar = new zza<>();
            int iIncrementAndGet = zzr.incrementAndGet();
            zzaVar.zzs = iIncrementAndGet;
            zzq.put(iIncrementAndGet, zzaVar);
            zzp.postDelayed(zzaVar, AutoResolveHelper.zzv);
            task.addOnCompleteListener(zzaVar);
            return zzaVar;
        }

        public final void zza(zzb zzbVar) throws PendingIntent.CanceledException {
            this.zzt = zzbVar;
            zza();
        }

        public final void zzb(zzb zzbVar) {
            if (this.zzt == zzbVar) {
                this.zzt = null;
            }
        }

        @Override // com.google.android.gms.tasks.OnCompleteListener
        public final void onComplete(Task<TResult> task) throws PendingIntent.CanceledException {
            this.zzu = task;
            zza();
        }

        @Override // java.lang.Runnable
        public final void run() {
            zzq.delete(this.zzs);
        }

        private final void zza() throws PendingIntent.CanceledException {
            if (this.zzu == null || this.zzt == null) {
                return;
            }
            zzq.delete(this.zzs);
            zzp.removeCallbacks(this);
            this.zzt.zzb(this.zzu);
        }
    }

    /* compiled from: com.google.android.gms:play-services-wallet@@18.0.0 */
    public static class zzb extends Fragment {
        private static String zzac = "resolveCallId";
        private static String zzad = "requestCode";
        private static String zzae = "initializationElapsedRealtime";
        private static String zzaf = "delivered";
        private int zzag;
        private zza<?> zzah;
        private boolean zzai;

        /* JADX INFO: Access modifiers changed from: private */
        public static Fragment zza(int i, int i2) {
            Bundle bundle = new Bundle();
            bundle.putInt(zzac, i);
            bundle.putInt(zzad, i2);
            bundle.putLong(zzae, AutoResolveHelper.zzw);
            zzb zzbVar = new zzb();
            zzbVar.setArguments(bundle);
            return zzbVar;
        }

        @Override // android.app.Fragment
        public final void onCreate(Bundle bundle) {
            super.onCreate(bundle);
            this.zzag = getArguments().getInt(zzad);
            if (AutoResolveHelper.zzw != getArguments().getLong(zzae)) {
                this.zzah = null;
            } else {
                this.zzah = zza.zzq.get(getArguments().getInt(zzac));
            }
            this.zzai = bundle != null && bundle.getBoolean(zzaf);
        }

        @Override // android.app.Fragment
        public final void onResume() throws PendingIntent.CanceledException {
            super.onResume();
            zza<?> zzaVar = this.zzah;
            if (zzaVar != null) {
                zzaVar.zza(this);
                return;
            }
            if (Log.isLoggable("AutoResolveHelper", 5)) {
                Log.w("AutoResolveHelper", "Sending canceled result for garbage collected task!");
            }
            zzb(null);
        }

        @Override // android.app.Fragment
        public final void onSaveInstanceState(Bundle bundle) {
            super.onSaveInstanceState(bundle);
            bundle.putBoolean(zzaf, this.zzai);
            zzc();
        }

        @Override // android.app.Fragment
        public final void onPause() {
            super.onPause();
            zzc();
        }

        private final void zzc() {
            zza<?> zzaVar = this.zzah;
            if (zzaVar != null) {
                zzaVar.zzb(this);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zzb(Task<? extends AutoResolvableResult> task) throws PendingIntent.CanceledException {
            if (this.zzai) {
                return;
            }
            this.zzai = true;
            Activity activity = getActivity();
            activity.getFragmentManager().beginTransaction().remove(this).commit();
            if (task != null) {
                AutoResolveHelper.zza(activity, this.zzag, task);
            } else {
                AutoResolveHelper.zza(activity, this.zzag, 0, new Intent());
            }
        }
    }

    private AutoResolveHelper() {
    }

    public static <TResult extends AutoResolvableResult> void resolveTask(Task<TResult> task, Activity activity, int i) {
        zza zzaVarZza = zza.zza(task);
        activity.getFragmentManager().beginTransaction().add(zzb.zza(zzaVarZza.zzs, i), new StringBuilder(58).append("com.google.android.gms.wallet.AutoResolveHelper").append(zzaVarZza.zzs).toString()).commit();
    }

    public static Status getStatusFromIntent(Intent intent) {
        if (intent == null) {
            return null;
        }
        return (Status) intent.getParcelableExtra("com.google.android.gms.common.api.AutoResolveHelper.status");
    }

    public static void putStatusIntoIntent(Intent intent, Status status) {
        if (status == null) {
            intent.removeExtra("com.google.android.gms.common.api.AutoResolveHelper.status");
        } else {
            intent.putExtra("com.google.android.gms.common.api.AutoResolveHelper.status", status);
        }
    }

    public static <TResult> void zza(Status status, TResult tresult, TaskCompletionSource<TResult> taskCompletionSource) {
        if (status.isSuccess()) {
            taskCompletionSource.setResult(tresult);
        } else {
            taskCompletionSource.setException(ApiExceptionUtil.fromStatus(status));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void zza(Activity activity, int i, Task<? extends AutoResolvableResult> task) throws PendingIntent.CanceledException {
        int i2;
        if (activity.isFinishing()) {
            if (Log.isLoggable("AutoResolveHelper", 3)) {
                Log.d("AutoResolveHelper", "Ignoring task result for, Activity is finishing.");
                return;
            }
            return;
        }
        if (task.getException() instanceof ResolvableApiException) {
            try {
                ((ResolvableApiException) task.getException()).startResolutionForResult(activity, i);
                return;
            } catch (IntentSender.SendIntentException e) {
                if (Log.isLoggable("AutoResolveHelper", 6)) {
                    Log.e("AutoResolveHelper", "Error starting pending intent!", e);
                    return;
                }
                return;
            }
        }
        Intent intent = new Intent();
        if (task.isSuccessful()) {
            task.getResult().putIntoIntent(intent);
            i2 = -1;
        } else {
            if (task.getException() instanceof ApiException) {
                ApiException apiException = (ApiException) task.getException();
                putStatusIntoIntent(intent, new Status(apiException.getStatusCode(), apiException.getMessage(), (PendingIntent) null));
            } else {
                if (Log.isLoggable("AutoResolveHelper", 6)) {
                    Log.e("AutoResolveHelper", "Unexpected non API exception!", task.getException());
                }
                putStatusIntoIntent(intent, new Status(8, "Unexpected non API exception when trying to deliver the task result to an activity!"));
            }
            i2 = 1;
        }
        zza(activity, i, i2, intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void zza(Activity activity, int i, int i2, Intent intent) throws PendingIntent.CanceledException {
        PendingIntent pendingIntentCreatePendingResult = activity.createPendingResult(i, intent, 1073741824);
        if (pendingIntentCreatePendingResult == null) {
            if (Log.isLoggable("AutoResolveHelper", 5)) {
                Log.w("AutoResolveHelper", "Null pending result returned when trying to deliver task result!");
            }
        } else {
            try {
                pendingIntentCreatePendingResult.send(i2);
            } catch (PendingIntent.CanceledException e) {
                if (Log.isLoggable("AutoResolveHelper", 6)) {
                    Log.e("AutoResolveHelper", "Exception sending pending result", e);
                }
            }
        }
    }
}
