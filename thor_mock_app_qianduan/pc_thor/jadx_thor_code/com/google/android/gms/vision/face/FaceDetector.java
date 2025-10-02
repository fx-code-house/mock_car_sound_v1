package com.google.android.gms.vision.face;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;
import android.util.SparseArray;
import com.google.android.gms.internal.vision.zzu;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.face.internal.client.zzb;
import com.google.android.gms.vision.face.internal.client.zzf;
import com.google.android.gms.vision.zzc;
import java.nio.ByteBuffer;
import java.util.HashSet;

/* compiled from: com.google.android.gms:play-services-vision@@20.1.1 */
/* loaded from: classes2.dex */
public final class FaceDetector extends Detector<Face> {
    public static final int ACCURATE_MODE = 1;
    public static final int ALL_CLASSIFICATIONS = 1;
    public static final int ALL_LANDMARKS = 1;
    public static final int CONTOUR_LANDMARKS = 2;
    public static final int FAST_MODE = 0;
    public static final int NO_CLASSIFICATIONS = 0;
    public static final int NO_LANDMARKS = 0;
    public static final int SELFIE_MODE = 2;
    private final Object lock;
    private final zzc zzcr;
    private final zzb zzcs;
    private boolean zzct;

    @Override // com.google.android.gms.vision.Detector
    public final void release() {
        super.release();
        synchronized (this.lock) {
            if (this.zzct) {
                this.zzcs.zzq();
                this.zzct = false;
            }
        }
    }

    protected final void finalize() throws Throwable {
        try {
            synchronized (this.lock) {
                if (this.zzct) {
                    Log.w("FaceDetector", "FaceDetector was not released with FaceDetector.release()");
                    release();
                }
            }
        } finally {
            super.finalize();
        }
    }

    /* compiled from: com.google.android.gms:play-services-vision@@20.1.1 */
    public static class Builder {
        private final Context zzg;
        private int landmarkType = 0;
        private boolean zzcv = false;
        private int zzcw = 0;
        private boolean trackingEnabled = true;
        private int mode = 0;
        private float proportionalMinFaceSize = -1.0f;

        public Builder(Context context) {
            this.zzg = context;
        }

        public Builder setLandmarkType(int i) {
            if (i != 0 && i != 1 && i != 2) {
                throw new IllegalArgumentException(new StringBuilder(34).append("Invalid landmark type: ").append(i).toString());
            }
            this.landmarkType = i;
            return this;
        }

        public Builder setProminentFaceOnly(boolean z) {
            this.zzcv = z;
            return this;
        }

        public Builder setClassificationType(int i) {
            if (i != 0 && i != 1) {
                throw new IllegalArgumentException(new StringBuilder(40).append("Invalid classification type: ").append(i).toString());
            }
            this.zzcw = i;
            return this;
        }

        public Builder setTrackingEnabled(boolean z) {
            this.trackingEnabled = z;
            return this;
        }

        public Builder setMode(int i) {
            if (i != 0 && i != 1 && i != 2) {
                throw new IllegalArgumentException(new StringBuilder(25).append("Invalid mode: ").append(i).toString());
            }
            this.mode = i;
            return this;
        }

        public Builder setMinFaceSize(float f) {
            if (f < 0.0f || f > 1.0f) {
                throw new IllegalArgumentException(new StringBuilder(47).append("Invalid proportional face size: ").append(f).toString());
            }
            this.proportionalMinFaceSize = f;
            return this;
        }

        public FaceDetector build() {
            zzf zzfVar = new zzf();
            zzfVar.mode = this.mode;
            zzfVar.landmarkType = this.landmarkType;
            zzfVar.zzcw = this.zzcw;
            zzfVar.zzcv = this.zzcv;
            zzfVar.trackingEnabled = this.trackingEnabled;
            zzfVar.proportionalMinFaceSize = this.proportionalMinFaceSize;
            if (!FaceDetector.zza(zzfVar)) {
                throw new IllegalArgumentException("Invalid build options");
            }
            return new FaceDetector(new zzb(this.zzg, zzfVar));
        }
    }

    @Override // com.google.android.gms.vision.Detector
    public final SparseArray<Face> detect(Frame frame) {
        ByteBuffer grayscaleImageData;
        Face[] faceArrZzb;
        if (frame == null) {
            throw new IllegalArgumentException("No frame supplied.");
        }
        if (frame.getPlanes() != null && frame.getPlanes().length == 3) {
            synchronized (this.lock) {
                if (!this.zzct) {
                    throw new IllegalStateException("Cannot use detector after release()");
                }
                faceArrZzb = this.zzcs.zza(frame.getPlanes(), zzu.zzd(frame));
            }
        } else {
            if (frame.getBitmap() != null) {
                Bitmap bitmap = frame.getBitmap();
                int width = bitmap.getWidth();
                int height = bitmap.getHeight();
                int i = width * height;
                grayscaleImageData = ByteBuffer.allocateDirect(((((width + 1) / 2) * ((height + 1) / 2)) << 1) + i);
                int i2 = i;
                for (int i3 = 0; i3 < i; i3++) {
                    int i4 = i3 % width;
                    int i5 = i3 / width;
                    int pixel = bitmap.getPixel(i4, i5);
                    float fRed = Color.red(pixel);
                    float fGreen = Color.green(pixel);
                    float fBlue = Color.blue(pixel);
                    grayscaleImageData.put(i3, (byte) ((0.299f * fRed) + (0.587f * fGreen) + (0.114f * fBlue)));
                    if (i5 % 2 == 0 && i4 % 2 == 0) {
                        int i6 = i2 + 1;
                        grayscaleImageData.put(i2, (byte) (((-0.169f) * fRed) + ((-0.331f) * fGreen) + (fBlue * 0.5f) + 128.0f));
                        i2 = i6 + 1;
                        grayscaleImageData.put(i6, (byte) ((fRed * 0.5f) + (fGreen * (-0.419f)) + (fBlue * (-0.081f)) + 128.0f));
                    }
                }
            } else {
                grayscaleImageData = frame.getGrayscaleImageData();
            }
            synchronized (this.lock) {
                if (!this.zzct) {
                    throw new IllegalStateException("Cannot use detector after release()");
                }
                faceArrZzb = this.zzcs.zzb(grayscaleImageData, zzu.zzd(frame));
            }
        }
        HashSet hashSet = new HashSet();
        SparseArray<Face> sparseArray = new SparseArray<>(faceArrZzb.length);
        int iMax = 0;
        for (Face face : faceArrZzb) {
            int id = face.getId();
            iMax = Math.max(iMax, id);
            if (hashSet.contains(Integer.valueOf(id))) {
                id = iMax + 1;
                iMax = id;
            }
            hashSet.add(Integer.valueOf(id));
            sparseArray.append(this.zzcr.zzb(id), face);
        }
        return sparseArray;
    }

    @Override // com.google.android.gms.vision.Detector
    public final boolean setFocus(int i) {
        boolean zZzd;
        int iZzc = this.zzcr.zzc(i);
        synchronized (this.lock) {
            if (!this.zzct) {
                throw new RuntimeException("Cannot use detector after release()");
            }
            zZzd = this.zzcs.zzd(iZzc);
        }
        return zZzd;
    }

    @Override // com.google.android.gms.vision.Detector
    public final boolean isOperational() {
        return this.zzcs.isOperational();
    }

    private FaceDetector() {
        this.zzcr = new zzc();
        this.lock = new Object();
        this.zzct = true;
        throw new IllegalStateException("Default constructor called");
    }

    private FaceDetector(zzb zzbVar) {
        this.zzcr = new zzc();
        this.lock = new Object();
        this.zzct = true;
        this.zzcs = zzbVar;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean zza(zzf zzfVar) {
        boolean z;
        if (zzfVar.mode == 2 || zzfVar.landmarkType != 2) {
            z = true;
        } else {
            Log.e("FaceDetector", "Contour is not supported for non-SELFIE mode.");
            z = false;
        }
        if (zzfVar.landmarkType != 2 || zzfVar.zzcw != 1) {
            return z;
        }
        Log.e("FaceDetector", "Classification is not supported with contour.");
        return false;
    }
}
