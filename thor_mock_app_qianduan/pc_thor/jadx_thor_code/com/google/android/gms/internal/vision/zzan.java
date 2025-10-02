package com.google.android.gms.internal.vision;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.dynamic.ObjectWrapper;
import com.google.android.gms.dynamite.DynamiteModule;

/* compiled from: com.google.android.gms:play-services-vision@@20.1.1 */
/* loaded from: classes2.dex */
public final class zzan extends zzs<zzad> {
    private final zzam zzem;

    public zzan(Context context, zzam zzamVar) {
        super(context, "TextNativeHandle", "ocr");
        this.zzem = zzamVar;
        zzr();
    }

    public final zzah[] zza(Bitmap bitmap, zzu zzuVar, zzaj zzajVar) {
        if (!isOperational()) {
            return new zzah[0];
        }
        try {
            return zzr().zza(ObjectWrapper.wrap(bitmap), zzuVar, zzajVar);
        } catch (RemoteException e) {
            Log.e("TextNativeHandle", "Error calling native text recognizer", e);
            return new zzah[0];
        }
    }

    @Override // com.google.android.gms.internal.vision.zzs
    protected final void zzp() throws RemoteException {
        zzr().zzs();
    }

    @Override // com.google.android.gms.internal.vision.zzs
    protected final /* synthetic */ zzad zza(DynamiteModule dynamiteModule, Context context) throws RemoteException, DynamiteModule.LoadingException {
        zzaf zzaeVar;
        IBinder iBinderInstantiate = dynamiteModule.instantiate("com.google.android.gms.vision.text.ChimeraNativeTextRecognizerCreator");
        if (iBinderInstantiate == null) {
            zzaeVar = null;
        } else {
            IInterface iInterfaceQueryLocalInterface = iBinderInstantiate.queryLocalInterface("com.google.android.gms.vision.text.internal.client.INativeTextRecognizerCreator");
            if (iInterfaceQueryLocalInterface instanceof zzaf) {
                zzaeVar = (zzaf) iInterfaceQueryLocalInterface;
            } else {
                zzaeVar = new zzae(iBinderInstantiate);
            }
        }
        if (zzaeVar == null) {
            return null;
        }
        return zzaeVar.zza(ObjectWrapper.wrap(context), this.zzem);
    }
}
