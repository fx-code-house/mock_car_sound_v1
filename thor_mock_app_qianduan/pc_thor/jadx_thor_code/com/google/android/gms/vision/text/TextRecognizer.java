package com.google.android.gms.vision.text;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.util.SparseArray;
import com.google.android.gms.internal.vision.zzah;
import com.google.android.gms.internal.vision.zzaj;
import com.google.android.gms.internal.vision.zzam;
import com.google.android.gms.internal.vision.zzan;
import com.google.android.gms.internal.vision.zzu;
import com.google.android.gms.internal.vision.zzv;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.Frame;
import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;

/* compiled from: com.google.android.gms:play-services-vision@@20.1.1 */
/* loaded from: classes2.dex */
public final class TextRecognizer extends Detector<TextBlock> {
    private final zzan zzel;

    private TextRecognizer() {
        throw new IllegalStateException("Default constructor called");
    }

    private TextRecognizer(zzan zzanVar) {
        this.zzel = zzanVar;
    }

    /* compiled from: com.google.android.gms:play-services-vision@@20.1.1 */
    public static class Builder {
        private zzam zzem = new zzam();
        private Context zzg;

        public Builder(Context context) {
            this.zzg = context;
        }

        public TextRecognizer build() {
            return new TextRecognizer(new zzan(this.zzg, this.zzem));
        }
    }

    @Override // com.google.android.gms.vision.Detector
    public final SparseArray<TextBlock> detect(Frame frame) {
        byte[] bArrArray;
        Bitmap bitmapDecodeByteArray;
        zzaj zzajVar = new zzaj(new Rect());
        if (frame == null) {
            throw new IllegalArgumentException("No frame supplied.");
        }
        zzu zzuVarZzd = zzu.zzd(frame);
        if (frame.getBitmap() != null) {
            bitmapDecodeByteArray = frame.getBitmap();
        } else {
            Frame.Metadata metadata = frame.getMetadata();
            ByteBuffer grayscaleImageData = frame.getGrayscaleImageData();
            int format = metadata.getFormat();
            int i = zzuVarZzd.width;
            int i2 = zzuVarZzd.height;
            if (grayscaleImageData.hasArray() && grayscaleImageData.arrayOffset() == 0) {
                bArrArray = grayscaleImageData.array();
            } else {
                byte[] bArr = new byte[grayscaleImageData.capacity()];
                grayscaleImageData.get(bArr);
                bArrArray = bArr;
            }
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            new YuvImage(bArrArray, format, i, i2, null).compressToJpeg(new Rect(0, 0, i, i2), 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            bitmapDecodeByteArray = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        }
        Bitmap bitmapZzb = zzv.zzb(bitmapDecodeByteArray, zzuVarZzd);
        if (!zzajVar.zzex.isEmpty()) {
            Rect rect = zzajVar.zzex;
            int width = frame.getMetadata().getWidth();
            int height = frame.getMetadata().getHeight();
            int i3 = zzuVarZzd.rotation;
            if (i3 == 1) {
                rect = new Rect(height - rect.bottom, rect.left, height - rect.top, rect.right);
            } else if (i3 == 2) {
                rect = new Rect(width - rect.right, height - rect.bottom, width - rect.left, height - rect.top);
            } else if (i3 == 3) {
                rect = new Rect(rect.top, width - rect.right, rect.bottom, width - rect.left);
            }
            zzajVar.zzex.set(rect);
        }
        zzuVarZzd.rotation = 0;
        zzah[] zzahVarArrZza = this.zzel.zza(bitmapZzb, zzuVarZzd, zzajVar);
        SparseArray sparseArray = new SparseArray();
        for (zzah zzahVar : zzahVarArrZza) {
            SparseArray sparseArray2 = (SparseArray) sparseArray.get(zzahVar.zzev);
            if (sparseArray2 == null) {
                sparseArray2 = new SparseArray();
                sparseArray.append(zzahVar.zzev, sparseArray2);
            }
            sparseArray2.append(zzahVar.zzew, zzahVar);
        }
        SparseArray<TextBlock> sparseArray3 = new SparseArray<>(sparseArray.size());
        for (int i4 = 0; i4 < sparseArray.size(); i4++) {
            sparseArray3.append(sparseArray.keyAt(i4), new TextBlock((SparseArray) sparseArray.valueAt(i4)));
        }
        return sparseArray3;
    }

    @Override // com.google.android.gms.vision.Detector
    public final boolean isOperational() {
        return this.zzel.isOperational();
    }

    @Override // com.google.android.gms.vision.Detector
    public final void release() {
        super.release();
        this.zzel.zzq();
    }
}
