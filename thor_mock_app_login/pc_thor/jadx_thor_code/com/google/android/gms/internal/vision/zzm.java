package com.google.android.gms.internal.vision;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.dynamic.ObjectWrapper;
import com.google.android.gms.dynamite.DynamiteModule;
import com.google.android.gms.vision.barcode.Barcode;
import java.nio.ByteBuffer;

/* compiled from: com.google.android.gms:play-services-vision@@20.1.1 */
/* loaded from: classes2.dex */
public final class zzm extends zzs<zzl> {
    private final zzk zzbs;

    public zzm(Context context, zzk zzkVar) {
        super(context, "BarcodeNativeHandle", "barcode");
        this.zzbs = zzkVar;
        zzr();
    }

    public final Barcode[] zza(ByteBuffer byteBuffer, zzu zzuVar) {
        if (!isOperational()) {
            return new Barcode[0];
        }
        try {
            return zzr().zza(ObjectWrapper.wrap(byteBuffer), zzuVar);
        } catch (RemoteException e) {
            Log.e("BarcodeNativeHandle", "Error calling native barcode detector", e);
            return new Barcode[0];
        }
    }

    public final Barcode[] zza(Bitmap bitmap, zzu zzuVar) {
        if (!isOperational()) {
            return new Barcode[0];
        }
        try {
            return zzr().zzb(ObjectWrapper.wrap(bitmap), zzuVar);
        } catch (RemoteException e) {
            Log.e("BarcodeNativeHandle", "Error calling native barcode detector", e);
            return new Barcode[0];
        }
    }

    @Override // com.google.android.gms.internal.vision.zzs
    protected final void zzp() throws RemoteException {
        if (isOperational()) {
            zzr().zzo();
        }
    }

    @Override // com.google.android.gms.internal.vision.zzs
    protected final /* synthetic */ zzl zza(DynamiteModule dynamiteModule, Context context) throws RemoteException, DynamiteModule.LoadingException {
        zzn zzpVar;
        IBinder iBinderInstantiate = dynamiteModule.instantiate("com.google.android.gms.vision.barcode.ChimeraNativeBarcodeDetectorCreator");
        if (iBinderInstantiate == null) {
            zzpVar = null;
        } else {
            IInterface iInterfaceQueryLocalInterface = iBinderInstantiate.queryLocalInterface("com.google.android.gms.vision.barcode.internal.client.INativeBarcodeDetectorCreator");
            if (iInterfaceQueryLocalInterface instanceof zzn) {
                zzpVar = (zzn) iInterfaceQueryLocalInterface;
            } else {
                zzpVar = new zzp(iBinderInstantiate);
            }
        }
        if (zzpVar == null) {
            return null;
        }
        return zzpVar.zza(ObjectWrapper.wrap(context), this.zzbs);
    }
}
