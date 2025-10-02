package com.thor.app.gui.camera;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import androidx.exifinterface.media.ExifInterface;
import com.thor.app.gui.camera.GraphicOverlay.Graphic;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: GraphicOverlay.kt */
@Metadata(d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0004\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\u00020\u0003:\u0001)B\u0015\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0013\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00028\u0000¢\u0006\u0002\u0010\u001fJ\u0006\u0010 \u001a\u00020\u001dJ\u0010\u0010!\u001a\u00020\u001d2\u0006\u0010\"\u001a\u00020#H\u0014J\u0013\u0010$\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00028\u0000¢\u0006\u0002\u0010\u001fJ\u001e\u0010%\u001a\u00020\u001d2\u0006\u0010&\u001a\u00020\u00132\u0006\u0010'\u001a\u00020\u00132\u0006\u0010(\u001a\u00020\u0013R\u0017\u0010\t\u001a\b\u0012\u0004\u0012\u00028\u00000\n8F¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u001e\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\r\u001a\u00020\u000e@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0014\u001a\b\u0012\u0004\u0012\u00028\u00000\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0017X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0013X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u0013X\u0082\u000e¢\u0006\u0002\n\u0000R\u001e\u0010\u001a\u001a\u00020\u000e2\u0006\u0010\r\u001a\u00020\u000e@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0011¨\u0006*"}, d2 = {"Lcom/thor/app/gui/camera/GraphicOverlay;", ExifInterface.GPS_DIRECTION_TRUE, "Lcom/thor/app/gui/camera/GraphicOverlay$Graphic;", "Landroid/view/View;", "context", "Landroid/content/Context;", "attrs", "Landroid/util/AttributeSet;", "(Landroid/content/Context;Landroid/util/AttributeSet;)V", "graphics", "", "getGraphics", "()Ljava/util/List;", "<set-?>", "", "heightScaleFactor", "getHeightScaleFactor", "()F", "mFacing", "", "mGraphics", "Ljava/util/HashSet;", "mLock", "", "mPreviewHeight", "mPreviewWidth", "widthScaleFactor", "getWidthScaleFactor", "add", "", "graphic", "(Lcom/thor/app/gui/camera/GraphicOverlay$Graphic;)V", "clear", "onDraw", "canvas", "Landroid/graphics/Canvas;", "remove", "setCameraInfo", "previewWidth", "previewHeight", "facing", "Graphic", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class GraphicOverlay<T extends Graphic> extends View {
    private float heightScaleFactor;
    private int mFacing;
    private final HashSet<T> mGraphics;
    private final Object mLock;
    private int mPreviewHeight;
    private int mPreviewWidth;
    private float widthScaleFactor;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public GraphicOverlay(Context context, AttributeSet attrs) {
        super(context, attrs);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(attrs, "attrs");
        this.mLock = new Object();
        this.widthScaleFactor = 1.0f;
        this.heightScaleFactor = 1.0f;
        this.mGraphics = new HashSet<>();
    }

    public final float getWidthScaleFactor() {
        return this.widthScaleFactor;
    }

    public final float getHeightScaleFactor() {
        return this.heightScaleFactor;
    }

    public final List<T> getGraphics() {
        Vector vector;
        synchronized (this.mLock) {
            vector = new Vector(this.mGraphics);
        }
        return vector;
    }

    /* compiled from: GraphicOverlay.kt */
    @Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\b\b&\u0018\u00002\u00020\u0001B\u0011\u0012\n\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH&J\u0006\u0010\t\u001a\u00020\u0006J\u000e\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000bJ\u000e\u0010\r\u001a\u00020\u000b2\u0006\u0010\u000e\u001a\u00020\u000bJ\u000e\u0010\u000f\u001a\u00020\u000b2\u0006\u0010\u0010\u001a\u00020\u000bJ\u000e\u0010\u0011\u001a\u00020\u000b2\u0006\u0010\u0012\u001a\u00020\u000bR\u0012\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0013"}, d2 = {"Lcom/thor/app/gui/camera/GraphicOverlay$Graphic;", "", "mOverlay", "Lcom/thor/app/gui/camera/GraphicOverlay;", "(Lcom/thor/app/gui/camera/GraphicOverlay;)V", "draw", "", "canvas", "Landroid/graphics/Canvas;", "postInvalidate", "scaleX", "", "horizontal", "scaleY", "vertical", "translateX", "x", "translateY", "y", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static abstract class Graphic {
        private final GraphicOverlay<?> mOverlay;

        public abstract void draw(Canvas canvas);

        public Graphic(GraphicOverlay<?> mOverlay) {
            Intrinsics.checkNotNullParameter(mOverlay, "mOverlay");
            this.mOverlay = mOverlay;
        }

        public final float scaleX(float horizontal) {
            return horizontal * this.mOverlay.getWidthScaleFactor();
        }

        public final float scaleY(float vertical) {
            return vertical * this.mOverlay.getHeightScaleFactor();
        }

        public final float translateX(float x) {
            if (((GraphicOverlay) this.mOverlay).mFacing == 1) {
                return this.mOverlay.getWidth() - scaleX(x);
            }
            return scaleX(x);
        }

        public final float translateY(float y) {
            return scaleY(y);
        }

        public final void postInvalidate() {
            this.mOverlay.postInvalidate();
        }
    }

    public final void clear() {
        synchronized (this.mLock) {
            this.mGraphics.clear();
            Unit unit = Unit.INSTANCE;
        }
        postInvalidate();
    }

    public final void add(T graphic) {
        Intrinsics.checkNotNullParameter(graphic, "graphic");
        synchronized (this.mLock) {
            this.mGraphics.add(graphic);
        }
        postInvalidate();
    }

    public final void remove(T graphic) {
        Intrinsics.checkNotNullParameter(graphic, "graphic");
        synchronized (this.mLock) {
            this.mGraphics.remove(graphic);
        }
        postInvalidate();
    }

    public final void setCameraInfo(int previewWidth, int previewHeight, int facing) {
        synchronized (this.mLock) {
            this.mPreviewWidth = previewWidth;
            this.mPreviewHeight = previewHeight;
            this.mFacing = facing;
            Unit unit = Unit.INSTANCE;
        }
        postInvalidate();
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        super.onDraw(canvas);
        synchronized (this.mLock) {
            if (this.mPreviewWidth != 0 && this.mPreviewHeight != 0) {
                this.widthScaleFactor = canvas.getWidth() / this.mPreviewWidth;
                this.heightScaleFactor = canvas.getHeight() / this.mPreviewHeight;
            }
            Iterator<T> it = this.mGraphics.iterator();
            while (it.hasNext()) {
                it.next().draw(canvas);
            }
            Unit unit = Unit.INSTANCE;
        }
    }
}
