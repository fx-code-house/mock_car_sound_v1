package com.thor.app.gui.camera;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewGroup;
import com.google.android.gms.common.images.Size;
import com.google.android.gms.vision.CameraSource;
import java.io.IOException;

/* loaded from: classes3.dex */
public class CameraSourcePreview extends ViewGroup {
    private static final String TAG = "CameraSourcePreview";
    private CameraSource mCameraSource;
    private GraphicOverlay mOverlay;
    private boolean mStartRequested;
    private boolean mSurfaceAvailable;
    private final SurfaceView mSurfaceView;

    public CameraSourcePreview(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mStartRequested = false;
        this.mSurfaceAvailable = false;
        SurfaceView surfaceView = new SurfaceView(context);
        this.mSurfaceView = surfaceView;
        surfaceView.getHolder().addCallback(new SurfaceCallback());
        addView(surfaceView);
    }

    public void start(CameraSource cameraSource) throws IOException, SecurityException {
        if (cameraSource == null) {
            stop();
        }
        this.mCameraSource = cameraSource;
        if (cameraSource != null) {
            this.mStartRequested = true;
            startIfReady();
        }
    }

    public void start(CameraSource cameraSource, GraphicOverlay overlay) throws IOException, SecurityException {
        this.mOverlay = overlay;
        start(cameraSource);
    }

    public void stop() {
        CameraSource cameraSource = this.mCameraSource;
        if (cameraSource != null) {
            cameraSource.stop();
        }
    }

    public void release() {
        CameraSource cameraSource = this.mCameraSource;
        if (cameraSource != null) {
            cameraSource.release();
            this.mCameraSource = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startIfReady() throws IOException, SecurityException {
        if (this.mStartRequested && this.mSurfaceAvailable) {
            this.mCameraSource.start(this.mSurfaceView.getHolder());
            if (this.mOverlay != null) {
                Size previewSize = this.mCameraSource.getPreviewSize();
                int iMin = Math.min(previewSize.getWidth(), previewSize.getHeight());
                this.mOverlay.setCameraInfo(Math.max(previewSize.getWidth(), previewSize.getHeight()), iMin, this.mCameraSource.getCameraFacing());
                this.mOverlay.clear();
            }
            this.mStartRequested = false;
        }
    }

    private class SurfaceCallback implements SurfaceHolder.Callback {
        @Override // android.view.SurfaceHolder.Callback
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        }

        private SurfaceCallback() {
        }

        @Override // android.view.SurfaceHolder.Callback
        public void surfaceCreated(SurfaceHolder surface) {
            CameraSourcePreview.this.mSurfaceAvailable = true;
            try {
                CameraSourcePreview.this.startIfReady();
            } catch (IOException e) {
                Log.e(CameraSourcePreview.TAG, "Could not start camera source.", e);
            } catch (SecurityException e2) {
                Log.e(CameraSourcePreview.TAG, "Do not have permission to start the camera", e2);
            }
        }

        @Override // android.view.SurfaceHolder.Callback
        public void surfaceDestroyed(SurfaceHolder surface) {
            CameraSourcePreview.this.mSurfaceAvailable = false;
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int i = getResources().getDisplayMetrics().heightPixels;
        int i2 = getResources().getDisplayMetrics().widthPixels;
        for (int i3 = 0; i3 < getChildCount(); i3++) {
            getChildAt(i3).layout(0, 0, i2, i);
        }
        try {
            startIfReady();
        } catch (IOException e) {
            Log.e(TAG, "Could not start camera source.", e);
        } catch (SecurityException e2) {
            Log.e(TAG, "Do not have permission to start the camera", e2);
        }
    }
}
