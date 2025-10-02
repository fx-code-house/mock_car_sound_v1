package com.thor.app.gui.barcode;

import android.graphics.Canvas;
import android.graphics.Paint;
import com.google.android.gms.vision.barcode.Barcode;
import com.thor.app.gui.camera.GraphicOverlay;

/* loaded from: classes3.dex */
public class BarcodeGraphic extends GraphicOverlay.Graphic {
    private static final int[] COLOR_CHOICES = {-16776961, -16711681, -16711936};
    private static int mCurrentColorIndex = 0;
    private volatile Barcode mBarcode;
    private int mId;
    private final Paint mRectPaint;
    private final Paint mTextPaint;

    @Override // com.thor.app.gui.camera.GraphicOverlay.Graphic
    public void draw(Canvas canvas) {
    }

    BarcodeGraphic(GraphicOverlay overlay) {
        super(overlay);
        int i = mCurrentColorIndex + 1;
        int[] iArr = COLOR_CHOICES;
        int length = i % iArr.length;
        mCurrentColorIndex = length;
        int i2 = iArr[length];
        Paint paint = new Paint();
        this.mRectPaint = paint;
        paint.setColor(i2);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(4.0f);
        Paint paint2 = new Paint();
        this.mTextPaint = paint2;
        paint2.setColor(i2);
        paint2.setTextSize(36.0f);
    }

    public int getId() {
        return this.mId;
    }

    public void setId(int id) {
        this.mId = id;
    }

    public Barcode getBarcode() {
        return this.mBarcode;
    }

    void updateItem(Barcode barcode) {
        this.mBarcode = barcode;
        postInvalidate();
    }
}
