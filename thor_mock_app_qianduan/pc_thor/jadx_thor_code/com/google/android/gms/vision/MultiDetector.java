package com.google.android.gms.vision;

import android.util.SparseArray;
import com.google.android.gms.vision.Detector;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
/* loaded from: classes2.dex */
public class MultiDetector extends Detector<Object> {
    private List<Detector<? extends Object>> zzbd;

    @Override // com.google.android.gms.vision.Detector
    public void release() {
        Iterator<Detector<? extends Object>> it = this.zzbd.iterator();
        while (it.hasNext()) {
            it.next().release();
        }
        this.zzbd.clear();
    }

    /* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
    public static class Builder {
        private MultiDetector zzbg = new MultiDetector();

        public Builder add(Detector<? extends Object> detector) {
            this.zzbg.zzbd.add(detector);
            return this;
        }

        public MultiDetector build() {
            if (this.zzbg.zzbd.size() == 0) {
                throw new RuntimeException("No underlying detectors added to MultiDetector.");
            }
            return this.zzbg;
        }
    }

    @Override // com.google.android.gms.vision.Detector
    public SparseArray<Object> detect(Frame frame) {
        SparseArray<Object> sparseArray = new SparseArray<>();
        Iterator<Detector<? extends Object>> it = this.zzbd.iterator();
        while (it.hasNext()) {
            SparseArray<? extends Object> sparseArrayDetect = it.next().detect(frame);
            for (int i = 0; i < sparseArrayDetect.size(); i++) {
                int iKeyAt = sparseArrayDetect.keyAt(i);
                if (sparseArray.get(iKeyAt) != null) {
                    throw new IllegalStateException(new StringBuilder(104).append("Detection ID overlap for id = ").append(iKeyAt).append("  This means that one of the detectors is not using global IDs.").toString());
                }
                sparseArray.append(iKeyAt, sparseArrayDetect.valueAt(i));
            }
        }
        return sparseArray;
    }

    @Override // com.google.android.gms.vision.Detector
    public void receiveFrame(Frame frame) {
        Iterator<Detector<? extends Object>> it = this.zzbd.iterator();
        while (it.hasNext()) {
            it.next().receiveFrame(frame);
        }
    }

    @Override // com.google.android.gms.vision.Detector
    public void setProcessor(Detector.Processor<Object> processor) {
        throw new UnsupportedOperationException("MultiDetector.setProcessor is not supported.  You should set a processor instance on each underlying detector instead.");
    }

    @Override // com.google.android.gms.vision.Detector
    public boolean isOperational() {
        Iterator<Detector<? extends Object>> it = this.zzbd.iterator();
        while (it.hasNext()) {
            if (!it.next().isOperational()) {
                return false;
            }
        }
        return true;
    }

    private MultiDetector() {
        this.zzbd = new ArrayList();
    }
}
