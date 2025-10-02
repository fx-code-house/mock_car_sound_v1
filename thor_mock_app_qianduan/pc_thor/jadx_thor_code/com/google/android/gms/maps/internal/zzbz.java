package com.google.android.gms.maps.internal;

import android.content.Context;
import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.dynamic.ObjectWrapper;
import com.google.android.gms.dynamite.DynamiteModule;
import com.google.android.gms.maps.model.RuntimeRemoteException;

/* loaded from: classes2.dex */
public class zzbz {
    private static final String TAG = "zzbz";
    private static Context zzck;
    private static zze zzcl;

    public static zze zza(Context context) throws GooglePlayServicesNotAvailableException {
        zze zzfVar;
        Preconditions.checkNotNull(context);
        zze zzeVar = zzcl;
        if (zzeVar != null) {
            return zzeVar;
        }
        int iIsGooglePlayServicesAvailable = GooglePlayServicesUtil.isGooglePlayServicesAvailable(context, 13400000);
        if (iIsGooglePlayServicesAvailable != 0) {
            throw new GooglePlayServicesNotAvailableException(iIsGooglePlayServicesAvailable);
        }
        Log.i(TAG, "Making Creator dynamically");
        IBinder iBinder = (IBinder) zza(zzb(context).getClassLoader(), "com.google.android.gms.maps.internal.CreatorImpl");
        if (iBinder == null) {
            zzfVar = null;
        } else {
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.maps.internal.ICreator");
            zzfVar = iInterfaceQueryLocalInterface instanceof zze ? (zze) iInterfaceQueryLocalInterface : new zzf(iBinder);
        }
        zzcl = zzfVar;
        try {
            zzfVar.zza(ObjectWrapper.wrap(zzb(context).getResources()), GooglePlayServicesUtil.GOOGLE_PLAY_SERVICES_VERSION_CODE);
            return zzcl;
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    private static Context zzb(Context context) {
        Context context2 = zzck;
        if (context2 != null) {
            return context2;
        }
        Context contextZzc = zzc(context);
        zzck = contextZzc;
        return contextZzc;
    }

    private static Context zzc(Context context) {
        try {
            return DynamiteModule.load(context, DynamiteModule.PREFER_REMOTE, "com.google.android.gms.maps_dynamite").getModuleContext();
        } catch (Exception e) {
            Log.e(TAG, "Failed to load maps module, use legacy", e);
            return GooglePlayServicesUtil.getRemoteContext(context);
        }
    }

    private static <T> T zza(ClassLoader classLoader, String str) {
        try {
            return (T) zza(((ClassLoader) Preconditions.checkNotNull(classLoader)).loadClass(str));
        } catch (ClassNotFoundException unused) {
            String strValueOf = String.valueOf(str);
            throw new IllegalStateException(strValueOf.length() != 0 ? "Unable to find dynamic class ".concat(strValueOf) : new String("Unable to find dynamic class "));
        }
    }

    private static <T> T zza(Class<?> cls) {
        try {
            return (T) cls.newInstance();
        } catch (IllegalAccessException unused) {
            String strValueOf = String.valueOf(cls.getName());
            throw new IllegalStateException(strValueOf.length() != 0 ? "Unable to call the default constructor of ".concat(strValueOf) : new String("Unable to call the default constructor of "));
        } catch (InstantiationException unused2) {
            String strValueOf2 = String.valueOf(cls.getName());
            throw new IllegalStateException(strValueOf2.length() != 0 ? "Unable to instantiate the dynamic class ".concat(strValueOf2) : new String("Unable to instantiate the dynamic class "));
        }
    }
}
