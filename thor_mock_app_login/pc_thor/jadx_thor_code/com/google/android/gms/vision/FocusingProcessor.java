package com.google.android.gms.vision;

import android.util.Log;
import android.util.SparseArray;
import com.google.android.gms.vision.Detector;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
/* loaded from: classes2.dex */
public abstract class FocusingProcessor<T> implements Detector.Processor<T> {
    private Tracker<T> zzas;
    private int zzav;
    private Detector<T> zzx;
    private int zzat = 3;
    private boolean zzau = false;
    private int zzaw = 0;

    public FocusingProcessor(Detector<T> detector, Tracker<T> tracker) {
        this.zzx = detector;
        this.zzas = tracker;
    }

    public abstract int selectFocus(Detector.Detections<T> detections);

    @Override // com.google.android.gms.vision.Detector.Processor
    public void release() {
        this.zzas.onDone();
    }

    @Override // com.google.android.gms.vision.Detector.Processor
    public void receiveDetections(Detector.Detections<T> detections) {
        SparseArray<T> detectedItems = detections.getDetectedItems();
        if (detectedItems.size() == 0) {
            if (this.zzaw == this.zzat) {
                this.zzas.onDone();
                this.zzau = false;
            } else {
                this.zzas.onMissing(detections);
            }
            this.zzaw++;
            return;
        }
        this.zzaw = 0;
        if (this.zzau) {
            T t = detectedItems.get(this.zzav);
            if (t != null) {
                this.zzas.onUpdate(detections, t);
                return;
            } else {
                this.zzas.onDone();
                this.zzau = false;
            }
        }
        int iSelectFocus = selectFocus(detections);
        T t2 = detectedItems.get(iSelectFocus);
        if (t2 == null) {
            Log.w("FocusingProcessor", new StringBuilder(35).append("Invalid focus selected: ").append(iSelectFocus).toString());
            return;
        }
        this.zzau = true;
        this.zzav = iSelectFocus;
        this.zzx.setFocus(iSelectFocus);
        this.zzas.onNewItem(this.zzav, t2);
        this.zzas.onUpdate(detections, t2);
    }

    protected final void zza(int i) {
        if (i < 0) {
            throw new IllegalArgumentException(new StringBuilder(28).append("Invalid max gap: ").append(i).toString());
        }
        this.zzat = i;
    }
}
