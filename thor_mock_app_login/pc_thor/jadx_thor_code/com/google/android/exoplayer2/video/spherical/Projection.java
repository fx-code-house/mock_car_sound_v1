package com.google.android.exoplayer2.video.spherical;

import com.google.android.exoplayer2.util.Assertions;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes.dex */
final class Projection {
    public static final int DRAW_MODE_TRIANGLES = 0;
    public static final int DRAW_MODE_TRIANGLES_FAN = 2;
    public static final int DRAW_MODE_TRIANGLES_STRIP = 1;
    public static final int POSITION_COORDS_PER_VERTEX = 3;
    public static final int TEXTURE_COORDS_PER_VERTEX = 2;
    public final Mesh leftMesh;
    public final Mesh rightMesh;
    public final boolean singleMesh;
    public final int stereoMode;

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface DrawMode {
    }

    public static Projection createEquirectangular(int stereoMode) {
        return createEquirectangular(50.0f, 36, 72, 180.0f, 360.0f, stereoMode);
    }

    public static Projection createEquirectangular(float radius, int latitudes, int longitudes, float verticalFovDegrees, float horizontalFovDegrees, int stereoMode) {
        int i;
        float f;
        int i2;
        int i3;
        int i4;
        float[] fArr;
        int i5;
        int i6 = latitudes;
        int i7 = longitudes;
        Assertions.checkArgument(radius > 0.0f);
        Assertions.checkArgument(i6 >= 1);
        Assertions.checkArgument(i7 >= 1);
        Assertions.checkArgument(verticalFovDegrees > 0.0f && verticalFovDegrees <= 180.0f);
        Assertions.checkArgument(horizontalFovDegrees > 0.0f && horizontalFovDegrees <= 360.0f);
        float radians = (float) Math.toRadians(verticalFovDegrees);
        float radians2 = (float) Math.toRadians(horizontalFovDegrees);
        float f2 = radians / i6;
        float f3 = radians2 / i7;
        int i8 = i7 + 1;
        int i9 = ((i8 * 2) + 2) * i6;
        float[] fArr2 = new float[i9 * 3];
        float[] fArr3 = new float[i9 * 2];
        int i10 = 0;
        int i11 = 0;
        int i12 = 0;
        while (i10 < i6) {
            float f4 = radians / 2.0f;
            float f5 = (i10 * f2) - f4;
            int i13 = i10 + 1;
            float f6 = (i13 * f2) - f4;
            int i14 = 0;
            while (i14 < i8) {
                float f7 = f5;
                int i15 = i13;
                int i16 = 0;
                int i17 = 2;
                while (i16 < i17) {
                    if (i16 == 0) {
                        f = f7;
                        i = i8;
                    } else {
                        i = i8;
                        f = f6;
                    }
                    float f8 = i14 * f3;
                    float f9 = f3;
                    int i18 = i11 + 1;
                    int i19 = i14;
                    double d = radius;
                    float f10 = f2;
                    double d2 = (f8 + 3.1415927f) - (radians2 / 2.0f);
                    int i20 = i16;
                    double d3 = f;
                    float[] fArr4 = fArr3;
                    float f11 = f6;
                    fArr2[i11] = -((float) (Math.sin(d2) * d * Math.cos(d3)));
                    int i21 = i18 + 1;
                    int i22 = i10;
                    fArr2[i18] = (float) (d * Math.sin(d3));
                    int i23 = i21 + 1;
                    fArr2[i21] = (float) (d * Math.cos(d2) * Math.cos(d3));
                    int i24 = i12 + 1;
                    fArr4[i12] = f8 / radians2;
                    int i25 = i24 + 1;
                    fArr4[i24] = ((i22 + i20) * f10) / radians;
                    if (i19 == 0 && i20 == 0) {
                        i2 = longitudes;
                        i3 = i19;
                        i4 = i20;
                    } else {
                        i2 = longitudes;
                        i3 = i19;
                        i4 = i20;
                        if (i3 != i2 || i4 != 1) {
                            fArr = fArr4;
                            i5 = 2;
                        }
                        i12 = i25;
                        i11 = i23;
                        i16 = i4 + 1;
                        i7 = i2;
                        i14 = i3;
                        fArr3 = fArr;
                        i17 = i5;
                        i10 = i22;
                        i8 = i;
                        f3 = f9;
                        f2 = f10;
                        f6 = f11;
                    }
                    System.arraycopy(fArr2, i23 - 3, fArr2, i23, 3);
                    i23 += 3;
                    fArr = fArr4;
                    i5 = 2;
                    System.arraycopy(fArr, i25 - 2, fArr, i25, 2);
                    i25 += 2;
                    i12 = i25;
                    i11 = i23;
                    i16 = i4 + 1;
                    i7 = i2;
                    i14 = i3;
                    fArr3 = fArr;
                    i17 = i5;
                    i10 = i22;
                    i8 = i;
                    f3 = f9;
                    f2 = f10;
                    f6 = f11;
                }
                float f12 = f2;
                int i26 = i14;
                int i27 = i7;
                int i28 = i26 + 1;
                f5 = f7;
                i13 = i15;
                i8 = i8;
                f2 = f12;
                f6 = f6;
                i7 = i27;
                i14 = i28;
            }
            i6 = latitudes;
            i10 = i13;
        }
        return new Projection(new Mesh(new SubMesh(0, fArr2, fArr3, 1)), stereoMode);
    }

    public Projection(Mesh mesh, int stereoMode) {
        this(mesh, mesh, stereoMode);
    }

    public Projection(Mesh leftMesh, Mesh rightMesh, int stereoMode) {
        this.leftMesh = leftMesh;
        this.rightMesh = rightMesh;
        this.stereoMode = stereoMode;
        this.singleMesh = leftMesh == rightMesh;
    }

    public static final class SubMesh {
        public static final int VIDEO_TEXTURE_ID = 0;
        public final int mode;
        public final float[] textureCoords;
        public final int textureId;
        public final float[] vertices;

        public SubMesh(int textureId, float[] vertices, float[] textureCoords, int mode) {
            this.textureId = textureId;
            Assertions.checkArgument(((long) vertices.length) * 2 == ((long) textureCoords.length) * 3);
            this.vertices = vertices;
            this.textureCoords = textureCoords;
            this.mode = mode;
        }

        public int getVertexCount() {
            return this.vertices.length / 3;
        }
    }

    public static final class Mesh {
        private final SubMesh[] subMeshes;

        public Mesh(SubMesh... subMeshes) {
            this.subMeshes = subMeshes;
        }

        public int getSubMeshCount() {
            return this.subMeshes.length;
        }

        public SubMesh getSubMesh(int index) {
            return this.subMeshes[index];
        }
    }
}
