package com.thor.app.gui.barcode;

import android.content.Context;
import com.google.android.exoplayer2.text.ttml.TtmlNode;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.Tracker;
import com.google.android.gms.vision.barcode.Barcode;
import com.thor.app.gui.camera.GraphicOverlay;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: BarcodeGraphicTracker.kt */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001\u0016B%\b\u0000\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\b\u0010\f\u001a\u00020\rH\u0016J\u0018\u0010\u000e\u001a\u00020\r2\u000e\u0010\u000f\u001a\n\u0012\u0004\u0012\u00020\u0002\u0018\u00010\u0010H\u0016J\u001a\u0010\u0011\u001a\u00020\r2\u0006\u0010\u0012\u001a\u00020\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u0002H\u0016J\"\u0010\u0015\u001a\u00020\r2\u000e\u0010\u000f\u001a\n\u0012\u0004\u0012\u00020\u0002\u0018\u00010\u00102\b\u0010\u0014\u001a\u0004\u0018\u00010\u0002H\u0016R\u0010\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0017"}, d2 = {"Lcom/thor/app/gui/barcode/BarcodeGraphicTracker;", "Lcom/google/android/gms/vision/Tracker;", "Lcom/google/android/gms/vision/barcode/Barcode;", "mOverlay", "Lcom/thor/app/gui/camera/GraphicOverlay;", "Lcom/thor/app/gui/barcode/BarcodeGraphic;", "mGraphic", "context", "Landroid/content/Context;", "(Lcom/thor/app/gui/camera/GraphicOverlay;Lcom/thor/app/gui/barcode/BarcodeGraphic;Landroid/content/Context;)V", "mBarcodeUpdateListener", "Lcom/thor/app/gui/barcode/BarcodeGraphicTracker$BarcodeUpdateListener;", "onDone", "", "onMissing", "detectionResults", "Lcom/google/android/gms/vision/Detector$Detections;", "onNewItem", TtmlNode.ATTR_ID, "", "item", "onUpdate", "BarcodeUpdateListener", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class BarcodeGraphicTracker extends Tracker<Barcode> {
    private BarcodeUpdateListener mBarcodeUpdateListener;
    private final BarcodeGraphic mGraphic;
    private final GraphicOverlay<BarcodeGraphic> mOverlay;

    /* compiled from: BarcodeGraphicTracker.kt */
    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u0012\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005H'¨\u0006\u0006"}, d2 = {"Lcom/thor/app/gui/barcode/BarcodeGraphicTracker$BarcodeUpdateListener;", "", "onBarcodeDetected", "", "barcode", "Lcom/google/android/gms/vision/barcode/Barcode;", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public interface BarcodeUpdateListener {
        void onBarcodeDetected(Barcode barcode);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public BarcodeGraphicTracker(GraphicOverlay<BarcodeGraphic> mOverlay, BarcodeGraphic mGraphic, Context context) {
        Intrinsics.checkNotNullParameter(mOverlay, "mOverlay");
        Intrinsics.checkNotNullParameter(mGraphic, "mGraphic");
        Intrinsics.checkNotNullParameter(context, "context");
        this.mOverlay = mOverlay;
        this.mGraphic = mGraphic;
        if (context instanceof BarcodeUpdateListener) {
            this.mBarcodeUpdateListener = (BarcodeUpdateListener) context;
            return;
        }
        throw new RuntimeException("Hosting activity must implement BarcodeUpdateListener");
    }

    @Override // com.google.android.gms.vision.Tracker
    public void onNewItem(int id, Barcode item) {
        this.mGraphic.setId(id);
        BarcodeUpdateListener barcodeUpdateListener = this.mBarcodeUpdateListener;
        Intrinsics.checkNotNull(barcodeUpdateListener);
        barcodeUpdateListener.onBarcodeDetected(item);
    }

    @Override // com.google.android.gms.vision.Tracker
    public void onUpdate(Detector.Detections<Barcode> detectionResults, Barcode item) {
        this.mOverlay.add(this.mGraphic);
        this.mGraphic.updateItem(item);
    }

    @Override // com.google.android.gms.vision.Tracker
    public void onMissing(Detector.Detections<Barcode> detectionResults) {
        this.mOverlay.remove(this.mGraphic);
    }

    @Override // com.google.android.gms.vision.Tracker
    public void onDone() {
        this.mOverlay.remove(this.mGraphic);
    }
}
