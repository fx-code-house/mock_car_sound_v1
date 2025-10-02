package com.google.android.exoplayer2.video.spherical;

import android.opengl.Matrix;
import com.google.android.exoplayer2.util.TimedValueQueue;

/* loaded from: classes.dex */
final class FrameRotationQueue {
    private boolean recenterMatrixComputed;
    private final float[] recenterMatrix = new float[16];
    private final float[] rotationMatrix = new float[16];
    private final TimedValueQueue<float[]> rotations = new TimedValueQueue<>();

    public void setRotation(long timestampUs, float[] angleAxis) {
        this.rotations.add(timestampUs, angleAxis);
    }

    public void reset() {
        this.rotations.clear();
        this.recenterMatrixComputed = false;
    }

    public boolean pollRotationMatrix(float[] matrix, long timestampUs) {
        float[] fArrPollFloor = this.rotations.pollFloor(timestampUs);
        if (fArrPollFloor == null) {
            return false;
        }
        getRotationMatrixFromAngleAxis(this.rotationMatrix, fArrPollFloor);
        if (!this.recenterMatrixComputed) {
            computeRecenterMatrix(this.recenterMatrix, this.rotationMatrix);
            this.recenterMatrixComputed = true;
        }
        Matrix.multiplyMM(matrix, 0, this.recenterMatrix, 0, this.rotationMatrix, 0);
        return true;
    }

    public static void computeRecenterMatrix(float[] recenterMatrix, float[] rotationMatrix) {
        Matrix.setIdentityM(recenterMatrix, 0);
        float f = rotationMatrix[10];
        float f2 = rotationMatrix[8];
        float fSqrt = (float) Math.sqrt((f * f) + (f2 * f2));
        float f3 = rotationMatrix[10];
        recenterMatrix[0] = f3 / fSqrt;
        float f4 = rotationMatrix[8];
        recenterMatrix[2] = f4 / fSqrt;
        recenterMatrix[8] = (-f4) / fSqrt;
        recenterMatrix[10] = f3 / fSqrt;
    }

    private static void getRotationMatrixFromAngleAxis(float[] matrix, float[] angleAxis) {
        float f = angleAxis[0];
        float f2 = -angleAxis[1];
        float f3 = -angleAxis[2];
        float length = Matrix.length(f, f2, f3);
        if (length != 0.0f) {
            Matrix.setRotateM(matrix, 0, (float) Math.toDegrees(length), f / length, f2 / length, f3 / length);
        } else {
            Matrix.setIdentityM(matrix, 0);
        }
    }
}
