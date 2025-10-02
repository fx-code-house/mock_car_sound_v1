package com.google.android.gms.tasks;

import android.app.Activity;
import com.google.android.gms.common.api.internal.LifecycleCallback;
import com.google.android.gms.common.api.internal.LifecycleFragment;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: com.google.android.gms:play-services-tasks@@18.0.1 */
/* loaded from: classes2.dex */
final class zzv extends LifecycleCallback {
    private final List<WeakReference<zzq<?>>> zza;

    private zzv(LifecycleFragment lifecycleFragment) {
        super(lifecycleFragment);
        this.zza = new ArrayList();
        this.mLifecycleFragment.addCallback("TaskOnStopCallback", this);
    }

    public static zzv zza(Activity activity) {
        LifecycleFragment fragment = getFragment(activity);
        zzv zzvVar = (zzv) fragment.getCallbackOrNull("TaskOnStopCallback", zzv.class);
        return zzvVar == null ? new zzv(fragment) : zzvVar;
    }

    @Override // com.google.android.gms.common.api.internal.LifecycleCallback
    public final void onStop() {
        synchronized (this.zza) {
            Iterator<WeakReference<zzq<?>>> it = this.zza.iterator();
            while (it.hasNext()) {
                zzq<?> zzqVar = it.next().get();
                if (zzqVar != null) {
                    zzqVar.zzc();
                }
            }
            this.zza.clear();
        }
    }

    public final <T> void zzb(zzq<T> zzqVar) {
        synchronized (this.zza) {
            this.zza.add(new WeakReference<>(zzqVar));
        }
    }
}
