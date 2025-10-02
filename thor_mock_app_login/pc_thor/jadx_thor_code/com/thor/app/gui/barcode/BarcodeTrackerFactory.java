package com.thor.app.gui.barcode;

import android.content.Context;
import com.google.android.gms.vision.MultiProcessor;
import com.google.android.gms.vision.Tracker;
import com.google.android.gms.vision.barcode.Barcode;
import com.thor.app.gui.camera.GraphicOverlay;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: BarcodeTrackerFactory.kt */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0000\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u001b\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0016\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0002H\u0016R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\f"}, d2 = {"Lcom/thor/app/gui/barcode/BarcodeTrackerFactory;", "Lcom/google/android/gms/vision/MultiProcessor$Factory;", "Lcom/google/android/gms/vision/barcode/Barcode;", "mGraphicOverlay", "Lcom/thor/app/gui/camera/GraphicOverlay;", "Lcom/thor/app/gui/barcode/BarcodeGraphic;", "mContext", "Landroid/content/Context;", "(Lcom/thor/app/gui/camera/GraphicOverlay;Landroid/content/Context;)V", "create", "Lcom/google/android/gms/vision/Tracker;", "barcode", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class BarcodeTrackerFactory implements MultiProcessor.Factory<Barcode> {
    private final Context mContext;
    private final GraphicOverlay<BarcodeGraphic> mGraphicOverlay;

    public BarcodeTrackerFactory(GraphicOverlay<BarcodeGraphic> mGraphicOverlay, Context mContext) {
        Intrinsics.checkNotNullParameter(mGraphicOverlay, "mGraphicOverlay");
        Intrinsics.checkNotNullParameter(mContext, "mContext");
        this.mGraphicOverlay = mGraphicOverlay;
        this.mContext = mContext;
    }

    @Override // com.google.android.gms.vision.MultiProcessor.Factory
    public Tracker<Barcode> create(Barcode barcode) {
        Intrinsics.checkNotNullParameter(barcode, "barcode");
        return new BarcodeGraphicTracker(this.mGraphicOverlay, new BarcodeGraphic(this.mGraphicOverlay), this.mContext);
    }
}
