package com.google.android.gms.vision.barcode;

import android.content.Context;
import android.util.SparseArray;
import com.google.android.gms.internal.vision.zzu;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.Frame;

/* compiled from: com.google.android.gms:play-services-vision@@20.1.1 */
/* loaded from: classes2.dex */
public final class BarcodeDetector extends Detector<Barcode> {
    private final com.google.android.gms.internal.vision.zzm zzbr;

    private BarcodeDetector() {
        throw new IllegalStateException("Default constructor called");
    }

    private BarcodeDetector(com.google.android.gms.internal.vision.zzm zzmVar) {
        this.zzbr = zzmVar;
    }

    /* compiled from: com.google.android.gms:play-services-vision@@20.1.1 */
    public static class Builder {
        private com.google.android.gms.internal.vision.zzk zzbs = new com.google.android.gms.internal.vision.zzk();
        private Context zzg;

        public Builder(Context context) {
            this.zzg = context;
        }

        public Builder setBarcodeFormats(int i) {
            this.zzbs.zzbt = i;
            return this;
        }

        public BarcodeDetector build() {
            return new BarcodeDetector(new com.google.android.gms.internal.vision.zzm(this.zzg, this.zzbs));
        }
    }

    @Override // com.google.android.gms.vision.Detector
    public final void release() {
        super.release();
        this.zzbr.zzq();
    }

    @Override // com.google.android.gms.vision.Detector
    public final SparseArray<Barcode> detect(Frame frame) {
        Barcode[] barcodeArrZza;
        if (frame == null) {
            throw new IllegalArgumentException("No frame supplied.");
        }
        zzu zzuVarZzd = zzu.zzd(frame);
        if (frame.getBitmap() != null) {
            barcodeArrZza = this.zzbr.zza(frame.getBitmap(), zzuVarZzd);
            if (barcodeArrZza == null) {
                throw new IllegalArgumentException("Internal barcode detector error; check logcat output.");
            }
        } else {
            barcodeArrZza = this.zzbr.zza(frame.getGrayscaleImageData(), zzuVarZzd);
        }
        SparseArray<Barcode> sparseArray = new SparseArray<>(barcodeArrZza.length);
        for (Barcode barcode : barcodeArrZza) {
            sparseArray.append(barcode.rawValue.hashCode(), barcode);
        }
        return sparseArray;
    }

    @Override // com.google.android.gms.vision.Detector
    public final boolean isOperational() {
        return this.zzbr.isOperational();
    }
}
