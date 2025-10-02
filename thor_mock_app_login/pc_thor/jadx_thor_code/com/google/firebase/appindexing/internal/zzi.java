package com.google.firebase.appindexing.internal;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.util.PlatformVersion;
import com.google.android.gms.internal.icing.zzhl;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.appindexing.FirebaseAppIndex;
import com.google.firebase.appindexing.FirebaseAppIndexingInvalidArgumentException;
import com.google.firebase.appindexing.Indexable;
import com.google.firebase.appindexing.internal.Thing;

/* compiled from: com.google.firebase:firebase-appindexing@@19.1.0 */
/* loaded from: classes2.dex */
public final class zzi extends FirebaseAppIndex {
    private static String[] zzez = {"com.google.android.googlequicksearchbox", "com.google.android.gms"};
    private final Context zzcs;
    private final GoogleApi<?> zzfa;
    private final zzk zzfb;

    public zzi(Context context) {
        this(context, new zzh(context));
    }

    private zzi(Context context, GoogleApi<Api.ApiOptions.NoOptions> googleApi) {
        this.zzfa = googleApi;
        this.zzcs = context;
        this.zzfb = new zzk(googleApi);
    }

    @Override // com.google.firebase.appindexing.FirebaseAppIndex
    public final Task<Void> update(Indexable... indexableArr) {
        Thing[] thingArr;
        Context context;
        zzaa zzzVar;
        if (indexableArr == null) {
            thingArr = null;
        } else {
            try {
                thingArr = new Thing[indexableArr.length];
                System.arraycopy(indexableArr, 0, thingArr, 0, indexableArr.length);
            } catch (ArrayStoreException unused) {
                return Tasks.forException(new FirebaseAppIndexingInvalidArgumentException("Custom Indexable-objects are not allowed. Please use the 'Indexables'-class for creating the objects."));
            }
        }
        if (zzhl.zzeb() && PlatformVersion.isAtLeastKitKat() && (context = this.zzcs) != null && thingArr != null && thingArr.length > 0) {
            if (Build.VERSION.SDK_INT >= 28) {
                zzzVar = new zzab(context);
            } else {
                zzzVar = new zzz(context);
            }
            for (Thing thing : thingArr) {
                if (thing != null) {
                    Thing.zza zzaVarZzac = thing.zzac();
                    String[] stringArray = (zzaVarZzac.zze() == null || !(zzaVarZzac.zze().get("sliceUri") instanceof String[])) ? null : zzaVarZzac.zze().getStringArray("sliceUri");
                    boolean z = stringArray != null && stringArray.length > 0;
                    Thing.zza zzaVarZzac2 = thing.zzac();
                    boolean[] booleanArray = (zzaVarZzac2.zze() == null || !(zzaVarZzac2.zze().get("grantSlicePermission") instanceof boolean[])) ? null : zzaVarZzac2.zze().getBooleanArray("grantSlicePermission");
                    boolean z2 = booleanArray != null && booleanArray.length > 0 && booleanArray[0];
                    if (z && z2) {
                        String str = stringArray[0];
                        for (String str2 : zzez) {
                            try {
                                zzzVar.grantSlicePermission(str2, Uri.parse(str));
                            } catch (Exception e) {
                                String strValueOf = String.valueOf(e);
                                String string = new StringBuilder(String.valueOf(strValueOf).length() + 48).append("Error trying to grant permission to Slice Uris: ").append(strValueOf).toString();
                                if (zzt.isLoggable(5)) {
                                    Log.w(FirebaseAppIndex.APP_INDEXING_API_TAG, string);
                                }
                            }
                        }
                    }
                }
            }
        }
        return zza(new zzy(1, thingArr));
    }

    @Override // com.google.firebase.appindexing.FirebaseAppIndex
    public final Task<Void> remove(String... strArr) {
        return zza(new zzy(3, null, strArr, null, null, null, null));
    }

    @Override // com.google.firebase.appindexing.FirebaseAppIndex
    public final Task<Void> removeAll() {
        return zza(new zzy(4, null, null, null, null, null, null));
    }

    private final Task<Void> zza(zzy zzyVar) {
        return this.zzfb.zzb(zzyVar);
    }
}
