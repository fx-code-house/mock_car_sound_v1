package com.google.android.gms.vision;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.Image;
import java.nio.ByteBuffer;
import javax.annotation.Nullable;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
/* loaded from: classes2.dex */
public class Frame {
    public static final int ROTATION_0 = 0;
    public static final int ROTATION_180 = 2;
    public static final int ROTATION_270 = 3;
    public static final int ROTATION_90 = 1;
    private final Metadata zzao;

    @Nullable
    private ByteBuffer zzap;

    @Nullable
    private zza zzaq;

    @Nullable
    private Bitmap zzar;

    public Metadata getMetadata() {
        return this.zzao;
    }

    /* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
    public static class Builder {
        private final Frame zzax = new Frame();

        public Builder setBitmap(Bitmap bitmap) {
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            this.zzax.zzar = bitmap;
            Metadata metadata = this.zzax.getMetadata();
            metadata.width = width;
            metadata.height = height;
            return this;
        }

        public Builder setPlanes(Image.Plane[] planeArr, int i, int i2, int i3) {
            if (planeArr == null) {
                throw new IllegalArgumentException("Null image data supplied.");
            }
            if (planeArr.length != 3) {
                throw new IllegalArgumentException("Only android.graphics.ImageFormat#YUV_420_888 is supported which should have 3 planes.");
            }
            if (planeArr[0].getBuffer().capacity() < i * i2) {
                throw new IllegalArgumentException("Invalid image data size.");
            }
            this.zzax.zzaq = new zza(planeArr);
            Metadata metadata = this.zzax.getMetadata();
            metadata.width = i;
            metadata.height = i2;
            metadata.format = i3;
            return this;
        }

        public Builder setImageData(ByteBuffer byteBuffer, int i, int i2, int i3) {
            if (byteBuffer == null) {
                throw new IllegalArgumentException("Null image data supplied.");
            }
            if (byteBuffer.capacity() < i * i2) {
                throw new IllegalArgumentException("Invalid image data size.");
            }
            if (i3 != 16 && i3 != 17 && i3 != 842094169) {
                throw new IllegalArgumentException(new StringBuilder(37).append("Unsupported image format: ").append(i3).toString());
            }
            this.zzax.zzap = byteBuffer;
            Metadata metadata = this.zzax.getMetadata();
            metadata.width = i;
            metadata.height = i2;
            metadata.format = i3;
            return this;
        }

        public Builder setId(int i) {
            this.zzax.getMetadata().id = i;
            return this;
        }

        public Builder setTimestampMillis(long j) {
            this.zzax.getMetadata().zzaz = j;
            return this;
        }

        public Builder setRotation(int i) {
            this.zzax.getMetadata().rotation = i;
            return this;
        }

        public Frame build() {
            if (this.zzax.zzap == null && this.zzax.zzar == null && this.zzax.zzaq == null) {
                throw new IllegalStateException("Missing image data.  Call either setBitmap or setImageData to specify the image");
            }
            return this.zzax;
        }
    }

    @Nullable
    public Image.Plane[] getPlanes() {
        zza zzaVar = this.zzaq;
        if (zzaVar == null) {
            return null;
        }
        return zzaVar.getPlanes();
    }

    /* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
    public static class Metadata {
        private int format;
        private int height;
        private int id;
        private int rotation;
        private int width;
        private long zzaz;

        public Metadata() {
            this.format = -1;
        }

        public Metadata(Metadata metadata) {
            this.format = -1;
            this.width = metadata.getWidth();
            this.height = metadata.getHeight();
            this.id = metadata.getId();
            this.zzaz = metadata.getTimestampMillis();
            this.rotation = metadata.getRotation();
            this.format = metadata.getFormat();
        }

        public int getWidth() {
            return this.width;
        }

        public int getHeight() {
            return this.height;
        }

        public int getId() {
            return this.id;
        }

        public long getTimestampMillis() {
            return this.zzaz;
        }

        public int getRotation() {
            return this.rotation;
        }

        public int getFormat() {
            return this.format;
        }

        public final void zzf() {
            if (this.rotation % 2 != 0) {
                int i = this.width;
                this.width = this.height;
                this.height = i;
            }
            this.rotation = 0;
        }
    }

    /* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
    static class zza {
        private final Image.Plane[] zzay;

        zza(Image.Plane[] planeArr) {
            this.zzay = planeArr;
        }

        final Image.Plane[] getPlanes() {
            return this.zzay;
        }
    }

    public ByteBuffer getGrayscaleImageData() {
        Bitmap bitmap = this.zzar;
        if (bitmap != null) {
            int width = bitmap.getWidth();
            int height = this.zzar.getHeight();
            int i = width * height;
            this.zzar.getPixels(new int[i], 0, width, 0, 0, width, height);
            byte[] bArr = new byte[i];
            for (int i2 = 0; i2 < i; i2++) {
                bArr[i2] = (byte) ((Color.red(r9[i2]) * 0.299f) + (Color.green(r9[i2]) * 0.587f) + (Color.blue(r9[i2]) * 0.114f));
            }
            return ByteBuffer.wrap(bArr);
        }
        return this.zzap;
    }

    public Bitmap getBitmap() {
        return this.zzar;
    }

    private Frame() {
        this.zzao = new Metadata();
        this.zzap = null;
        this.zzaq = null;
        this.zzar = null;
    }
}
