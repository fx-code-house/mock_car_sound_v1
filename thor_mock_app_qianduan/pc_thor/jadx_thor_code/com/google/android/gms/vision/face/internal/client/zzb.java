package com.google.android.gms.vision.face.internal.client;

import android.content.Context;
import android.graphics.PointF;
import android.media.Image;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.dynamic.ObjectWrapper;
import com.google.android.gms.dynamite.DynamiteModule;
import com.google.android.gms.internal.vision.zzs;
import com.google.android.gms.internal.vision.zzu;
import com.google.android.gms.internal.vision.zzw;
import com.google.android.gms.vision.face.Contour;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.Landmark;
import java.nio.ByteBuffer;

/* compiled from: com.google.android.gms:play-services-vision@@20.1.1 */
/* loaded from: classes2.dex */
public final class zzb extends zzs<zzh> {
    private final zzf zzdg;

    public zzb(Context context, zzf zzfVar) {
        super(context, "FaceNativeHandle", "face");
        this.zzdg = zzfVar;
        zzr();
    }

    public final Face[] zzb(ByteBuffer byteBuffer, zzu zzuVar) {
        if (!isOperational()) {
            return new Face[0];
        }
        try {
            FaceParcel[] faceParcelArrZzc = zzr().zzc(ObjectWrapper.wrap(byteBuffer), zzuVar);
            Face[] faceArr = new Face[faceParcelArrZzc.length];
            for (int i = 0; i < faceParcelArrZzc.length; i++) {
                faceArr[i] = zza(faceParcelArrZzc[i]);
            }
            return faceArr;
        } catch (RemoteException e) {
            Log.e("FaceNativeHandle", "Could not call native face detector", e);
            return new Face[0];
        }
    }

    public final Face[] zza(Image.Plane[] planeArr, zzu zzuVar) {
        if (!isOperational()) {
            Log.e("FaceNativeHandle", "Native handle is not ready to be used.");
            return new Face[0];
        }
        if (planeArr != null && planeArr.length != 3) {
            throw new IllegalArgumentException("Only android.graphics.ImageFormat#YUV_420_888 is supported which should have 3 planes.");
        }
        try {
            FaceParcel[] faceParcelArrZza = zzr().zza(ObjectWrapper.wrap(planeArr[0].getBuffer()), ObjectWrapper.wrap(planeArr[1].getBuffer()), ObjectWrapper.wrap(planeArr[2].getBuffer()), planeArr[0].getPixelStride(), planeArr[1].getPixelStride(), planeArr[2].getPixelStride(), planeArr[0].getRowStride(), planeArr[1].getRowStride(), planeArr[2].getRowStride(), zzuVar);
            Face[] faceArr = new Face[faceParcelArrZza.length];
            for (int i = 0; i < faceParcelArrZza.length; i++) {
                faceArr[i] = zza(faceParcelArrZza[i]);
            }
            return faceArr;
        } catch (RemoteException e) {
            Log.e("FaceNativeHandle", "Could not call native face detector", e);
            return new Face[0];
        }
    }

    public final boolean zzd(int i) {
        if (!isOperational()) {
            return false;
        }
        try {
            return zzr().zzd(i);
        } catch (RemoteException e) {
            Log.e("FaceNativeHandle", "Could not call native face detector", e);
            return false;
        }
    }

    @Override // com.google.android.gms.internal.vision.zzs
    protected final void zzp() throws RemoteException {
        zzr().zzo();
    }

    private static Face zza(FaceParcel faceParcel) {
        Landmark[] landmarkArr;
        Contour[] contourArr;
        int i = faceParcel.id;
        PointF pointF = new PointF(faceParcel.centerX, faceParcel.centerY);
        float f = faceParcel.width;
        float f2 = faceParcel.height;
        float f3 = faceParcel.zzdh;
        float f4 = faceParcel.zzdi;
        float f5 = faceParcel.zzdj;
        LandmarkParcel[] landmarkParcelArr = faceParcel.zzdk;
        if (landmarkParcelArr == null) {
            landmarkArr = new Landmark[0];
        } else {
            Landmark[] landmarkArr2 = new Landmark[landmarkParcelArr.length];
            int i2 = 0;
            while (i2 < landmarkParcelArr.length) {
                LandmarkParcel landmarkParcel = landmarkParcelArr[i2];
                landmarkArr2[i2] = new Landmark(new PointF(landmarkParcel.x, landmarkParcel.y), landmarkParcel.type);
                i2++;
                landmarkParcelArr = landmarkParcelArr;
            }
            landmarkArr = landmarkArr2;
        }
        zza[] zzaVarArr = faceParcel.zzdl;
        if (zzaVarArr == null) {
            contourArr = new Contour[0];
        } else {
            Contour[] contourArr2 = new Contour[zzaVarArr.length];
            for (int i3 = 0; i3 < zzaVarArr.length; i3++) {
                zza zzaVar = zzaVarArr[i3];
                contourArr2[i3] = new Contour(zzaVar.zzdf, zzaVar.type);
            }
            contourArr = contourArr2;
        }
        return new Face(i, pointF, f, f2, f3, f4, f5, landmarkArr, contourArr, faceParcel.zzcm, faceParcel.zzcn, faceParcel.zzco, faceParcel.zzcp);
    }

    @Override // com.google.android.gms.internal.vision.zzs
    protected final /* synthetic */ zzh zza(DynamiteModule dynamiteModule, Context context) throws RemoteException, DynamiteModule.LoadingException {
        zzi zziVarAsInterface;
        if (zzw.zza(context, "com.google.android.gms.vision.dynamite.face")) {
            zziVarAsInterface = zzl.asInterface(dynamiteModule.instantiate("com.google.android.gms.vision.face.NativeFaceDetectorV2Creator"));
        } else {
            zziVarAsInterface = zzl.asInterface(dynamiteModule.instantiate("com.google.android.gms.vision.face.ChimeraNativeFaceDetectorCreator"));
        }
        if (zziVarAsInterface == null) {
            return null;
        }
        return zziVarAsInterface.newFaceDetector(ObjectWrapper.wrap(context), this.zzdg);
    }
}
