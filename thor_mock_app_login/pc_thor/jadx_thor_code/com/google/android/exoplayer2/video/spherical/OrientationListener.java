package com.google.android.exoplayer2.video.spherical;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.opengl.Matrix;
import android.view.Display;
import org.mapstruct.ap.shaded.freemarker.core.FMParserConstants;

/* loaded from: classes.dex */
final class OrientationListener implements SensorEventListener {
    private final Display display;
    private final Listener[] listeners;
    private boolean recenterMatrixComputed;
    private final float[] deviceOrientationMatrix4x4 = new float[16];
    private final float[] tempMatrix4x4 = new float[16];
    private final float[] recenterMatrix4x4 = new float[16];
    private final float[] angles = new float[3];

    public interface Listener {
        void onOrientationChange(float[] deviceOrientationMatrix, float deviceRoll);
    }

    @Override // android.hardware.SensorEventListener
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    public OrientationListener(Display display, Listener... listeners) {
        this.display = display;
        this.listeners = listeners;
    }

    @Override // android.hardware.SensorEventListener
    public void onSensorChanged(SensorEvent event) {
        SensorManager.getRotationMatrixFromVector(this.deviceOrientationMatrix4x4, event.values);
        rotateAroundZ(this.deviceOrientationMatrix4x4, this.display.getRotation());
        float fExtractRoll = extractRoll(this.deviceOrientationMatrix4x4);
        rotateYtoSky(this.deviceOrientationMatrix4x4);
        recenter(this.deviceOrientationMatrix4x4);
        notifyListeners(this.deviceOrientationMatrix4x4, fExtractRoll);
    }

    private void notifyListeners(float[] deviceOrientationMatrix, float roll) {
        for (Listener listener : this.listeners) {
            listener.onOrientationChange(deviceOrientationMatrix, roll);
        }
    }

    private void recenter(float[] matrix) {
        if (!this.recenterMatrixComputed) {
            FrameRotationQueue.computeRecenterMatrix(this.recenterMatrix4x4, matrix);
            this.recenterMatrixComputed = true;
        }
        float[] fArr = this.tempMatrix4x4;
        System.arraycopy(matrix, 0, fArr, 0, fArr.length);
        Matrix.multiplyMM(matrix, 0, this.tempMatrix4x4, 0, this.recenterMatrix4x4, 0);
    }

    private float extractRoll(float[] matrix) {
        SensorManager.remapCoordinateSystem(matrix, 1, FMParserConstants.TERMINATING_EXCLAM, this.tempMatrix4x4);
        SensorManager.getOrientation(this.tempMatrix4x4, this.angles);
        return this.angles[2];
    }

    private void rotateAroundZ(float[] matrix, int rotation) {
        if (rotation != 0) {
            int i = 129;
            int i2 = 1;
            if (rotation == 1) {
                i2 = 129;
                i = 2;
            } else if (rotation == 2) {
                i2 = 130;
            } else {
                if (rotation != 3) {
                    throw new IllegalStateException();
                }
                i = 130;
            }
            float[] fArr = this.tempMatrix4x4;
            System.arraycopy(matrix, 0, fArr, 0, fArr.length);
            SensorManager.remapCoordinateSystem(this.tempMatrix4x4, i, i2, matrix);
        }
    }

    private static void rotateYtoSky(float[] matrix) {
        Matrix.rotateM(matrix, 0, 90.0f, 1.0f, 0.0f, 0.0f);
    }
}
