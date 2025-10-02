package com.google.android.gms.vision;

import android.util.SparseArray;
import com.google.android.gms.vision.Detector;
import java.util.HashSet;
import java.util.Iterator;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
/* loaded from: classes2.dex */
public class MultiProcessor<T> implements Detector.Processor<T> {
    private int zzat;
    private Factory<T> zzbe;
    private SparseArray<zza> zzbf;

    /* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
    public interface Factory<T> {
        Tracker<T> create(T t);
    }

    @Override // com.google.android.gms.vision.Detector.Processor
    public void release() {
        for (int i = 0; i < this.zzbf.size(); i++) {
            this.zzbf.valueAt(i).zzas.onDone();
        }
        this.zzbf.clear();
    }

    /* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
    class zza {
        private Tracker<T> zzas;
        private int zzaw;

        private zza(MultiProcessor multiProcessor) {
            this.zzaw = 0;
        }

        static /* synthetic */ int zzb(zza zzaVar) {
            int i = zzaVar.zzaw;
            zzaVar.zzaw = i + 1;
            return i;
        }

        static /* synthetic */ int zza(zza zzaVar, int i) {
            zzaVar.zzaw = 0;
            return 0;
        }
    }

    /* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
    public static class Builder<T> {
        private MultiProcessor<T> zzbh;

        public Builder(Factory<T> factory) {
            MultiProcessor<T> multiProcessor = new MultiProcessor<>();
            this.zzbh = multiProcessor;
            if (factory == null) {
                throw new IllegalArgumentException("No factory supplied.");
            }
            ((MultiProcessor) multiProcessor).zzbe = factory;
        }

        public Builder<T> setMaxGapFrames(int i) {
            if (i < 0) {
                throw new IllegalArgumentException(new StringBuilder(28).append("Invalid max gap: ").append(i).toString());
            }
            ((MultiProcessor) this.zzbh).zzat = i;
            return this;
        }

        public MultiProcessor<T> build() {
            return this.zzbh;
        }
    }

    @Override // com.google.android.gms.vision.Detector.Processor
    public void receiveDetections(Detector.Detections<T> detections) {
        SparseArray<T> detectedItems = detections.getDetectedItems();
        for (int i = 0; i < detectedItems.size(); i++) {
            int iKeyAt = detectedItems.keyAt(i);
            T tValueAt = detectedItems.valueAt(i);
            if (this.zzbf.get(iKeyAt) == null) {
                zza zzaVar = new zza();
                zzaVar.zzas = this.zzbe.create(tValueAt);
                zzaVar.zzas.onNewItem(iKeyAt, tValueAt);
                this.zzbf.append(iKeyAt, zzaVar);
            }
        }
        SparseArray<T> detectedItems2 = detections.getDetectedItems();
        HashSet hashSet = new HashSet();
        for (int i2 = 0; i2 < this.zzbf.size(); i2++) {
            int iKeyAt2 = this.zzbf.keyAt(i2);
            if (detectedItems2.get(iKeyAt2) == null) {
                zza zzaVarValueAt = this.zzbf.valueAt(i2);
                zza.zzb(zzaVarValueAt);
                if (zzaVarValueAt.zzaw >= this.zzat) {
                    zzaVarValueAt.zzas.onDone();
                    hashSet.add(Integer.valueOf(iKeyAt2));
                } else {
                    zzaVarValueAt.zzas.onMissing(detections);
                }
            }
        }
        Iterator it = hashSet.iterator();
        while (it.hasNext()) {
            this.zzbf.delete(((Integer) it.next()).intValue());
        }
        SparseArray<T> detectedItems3 = detections.getDetectedItems();
        for (int i3 = 0; i3 < detectedItems3.size(); i3++) {
            int iKeyAt3 = detectedItems3.keyAt(i3);
            T tValueAt2 = detectedItems3.valueAt(i3);
            zza zzaVar2 = this.zzbf.get(iKeyAt3);
            zza.zza(zzaVar2, 0);
            zzaVar2.zzas.onUpdate(detections, tValueAt2);
        }
    }

    private MultiProcessor() {
        this.zzbf = new SparseArray<>();
        this.zzat = 3;
    }
}
