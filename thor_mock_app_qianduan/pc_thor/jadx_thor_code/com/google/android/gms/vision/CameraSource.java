package com.google.android.gms.vision;

import android.content.Context;
import android.graphics.ImageFormat;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.os.SystemClock;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.WindowManager;
import com.google.android.gms.common.images.Size;
import com.google.android.gms.vision.Frame;
import com.thor.basemodule.gui.view.CircleBarView;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
/* loaded from: classes2.dex */
public class CameraSource {
    public static final int CAMERA_FACING_BACK = 0;
    public static final int CAMERA_FACING_FRONT = 1;
    private int facing;
    private int rotation;
    private Context zzg;
    private final Object zzh;
    private Camera zzi;
    private Size zzj;
    private float zzk;
    private int zzl;
    private int zzm;
    private boolean zzn;
    private String zzo;
    private SurfaceTexture zzp;
    private boolean zzq;
    private Thread zzr;
    private zza zzs;
    private Map<byte[], ByteBuffer> zzt;

    /* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
    public interface PictureCallback {
        void onPictureTaken(byte[] bArr);
    }

    /* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
    public interface ShutterCallback {
        void onShutter();
    }

    /* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
    class zzb implements Camera.PreviewCallback {
        private zzb() {
        }

        @Override // android.hardware.Camera.PreviewCallback
        public final void onPreviewFrame(byte[] bArr, Camera camera) {
            CameraSource.this.zzs.zza(bArr, camera);
        }
    }

    /* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
    class zzc implements Camera.PictureCallback {
        private PictureCallback zzaf;

        private zzc() {
        }

        @Override // android.hardware.Camera.PictureCallback
        public final void onPictureTaken(byte[] bArr, Camera camera) {
            PictureCallback pictureCallback = this.zzaf;
            if (pictureCallback != null) {
                pictureCallback.onPictureTaken(bArr);
            }
            synchronized (CameraSource.this.zzh) {
                if (CameraSource.this.zzi != null) {
                    CameraSource.this.zzi.startPreview();
                }
            }
        }
    }

    /* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
    static class zzd implements Camera.ShutterCallback {
        private ShutterCallback zzag;

        private zzd() {
        }

        @Override // android.hardware.Camera.ShutterCallback
        public final void onShutter() {
            ShutterCallback shutterCallback = this.zzag;
            if (shutterCallback != null) {
                shutterCallback.onShutter();
            }
        }
    }

    public void release() {
        synchronized (this.zzh) {
            stop();
            this.zzs.release();
        }
    }

    /* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
    static class zze {
        private Size zzaj;
        private Size zzak;

        public zze(Camera.Size size, @Nullable Camera.Size size2) {
            this.zzaj = new Size(size.width, size.height);
            if (size2 != null) {
                this.zzak = new Size(size2.width, size2.height);
            }
        }

        public final Size zzd() {
            return this.zzaj;
        }

        @Nullable
        public final Size zze() {
            return this.zzak;
        }
    }

    public CameraSource start() throws IOException {
        synchronized (this.zzh) {
            if (this.zzi != null) {
                return this;
            }
            this.zzi = zzc();
            SurfaceTexture surfaceTexture = new SurfaceTexture(100);
            this.zzp = surfaceTexture;
            this.zzi.setPreviewTexture(surfaceTexture);
            this.zzq = true;
            this.zzi.startPreview();
            Thread thread = new Thread(this.zzs);
            this.zzr = thread;
            thread.setName("gms.vision.CameraSource");
            this.zzs.setActive(true);
            this.zzr.start();
            return this;
        }
    }

    /* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
    class zza implements Runnable {
        private long zzab;
        private ByteBuffer zzad;
        private Detector<?> zzx;
        private long zzz = SystemClock.elapsedRealtime();
        private final Object lock = new Object();
        private boolean zzaa = true;
        private int zzac = 0;

        zza(Detector<?> detector) {
            this.zzx = detector;
        }

        final void release() {
            this.zzx.release();
            this.zzx = null;
        }

        final void setActive(boolean z) {
            synchronized (this.lock) {
                this.zzaa = z;
                this.lock.notifyAll();
            }
        }

        final void zza(byte[] bArr, Camera camera) {
            synchronized (this.lock) {
                ByteBuffer byteBuffer = this.zzad;
                if (byteBuffer != null) {
                    camera.addCallbackBuffer(byteBuffer.array());
                    this.zzad = null;
                }
                if (!CameraSource.this.zzt.containsKey(bArr)) {
                    Log.d("CameraSource", "Skipping frame. Could not find ByteBuffer associated with the image data from the camera.");
                    return;
                }
                this.zzab = SystemClock.elapsedRealtime() - this.zzz;
                this.zzac++;
                this.zzad = (ByteBuffer) CameraSource.this.zzt.get(bArr);
                this.lock.notifyAll();
            }
        }

        @Override // java.lang.Runnable
        public final void run() {
            boolean z;
            Frame frameBuild;
            ByteBuffer byteBuffer;
            while (true) {
                synchronized (this.lock) {
                    while (true) {
                        z = this.zzaa;
                        if (!z || this.zzad != null) {
                            break;
                        }
                        try {
                            this.lock.wait();
                        } catch (InterruptedException e) {
                            Log.d("CameraSource", "Frame processing loop terminated.", e);
                            return;
                        }
                    }
                    if (!z) {
                        return;
                    }
                    frameBuild = new Frame.Builder().setImageData(this.zzad, CameraSource.this.zzj.getWidth(), CameraSource.this.zzj.getHeight(), 17).setId(this.zzac).setTimestampMillis(this.zzab).setRotation(CameraSource.this.rotation).build();
                    byteBuffer = this.zzad;
                    this.zzad = null;
                }
                try {
                    this.zzx.receiveFrame(frameBuild);
                } catch (Exception e2) {
                    Log.e("CameraSource", "Exception thrown from receiver.", e2);
                } finally {
                    CameraSource.this.zzi.addCallbackBuffer(byteBuffer.array());
                }
            }
        }
    }

    /* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
    public static class Builder {
        private final Detector<?> zzx;
        private CameraSource zzy;

        public Builder(Context context, Detector<?> detector) {
            CameraSource cameraSource = new CameraSource();
            this.zzy = cameraSource;
            if (context == null) {
                throw new IllegalArgumentException("No context supplied.");
            }
            if (detector == null) {
                throw new IllegalArgumentException("No detector supplied.");
            }
            this.zzx = detector;
            cameraSource.zzg = context;
        }

        public Builder setRequestedFps(float f) {
            if (f <= 0.0f) {
                throw new IllegalArgumentException(new StringBuilder(28).append("Invalid fps: ").append(f).toString());
            }
            this.zzy.zzk = f;
            return this;
        }

        public Builder setRequestedPreviewSize(int i, int i2) {
            if (i <= 0 || i > 1000000 || i2 <= 0 || i2 > 1000000) {
                throw new IllegalArgumentException(new StringBuilder(45).append("Invalid preview size: ").append(i).append("x").append(i2).toString());
            }
            this.zzy.zzl = i;
            this.zzy.zzm = i2;
            return this;
        }

        public Builder setFacing(int i) {
            if (i != 0 && i != 1) {
                throw new IllegalArgumentException(new StringBuilder(27).append("Invalid camera: ").append(i).toString());
            }
            this.zzy.facing = i;
            return this;
        }

        public Builder setAutoFocusEnabled(boolean z) {
            this.zzy.zzn = z;
            return this;
        }

        public Builder setFocusMode(String str) {
            if (!str.equals("continuous-video") && !str.equals("continuous-picture")) {
                Log.w("CameraSource", String.format("FocusMode %s is not supported for now.", str));
                str = null;
            }
            this.zzy.zzo = str;
            return this;
        }

        public CameraSource build() {
            CameraSource cameraSource = this.zzy;
            CameraSource cameraSource2 = this.zzy;
            cameraSource2.getClass();
            cameraSource.zzs = cameraSource2.new zza(this.zzx);
            return this.zzy;
        }
    }

    public CameraSource start(SurfaceHolder surfaceHolder) throws IOException {
        synchronized (this.zzh) {
            if (this.zzi != null) {
                return this;
            }
            Camera cameraZzc = zzc();
            this.zzi = cameraZzc;
            cameraZzc.setPreviewDisplay(surfaceHolder);
            this.zzi.startPreview();
            this.zzr = new Thread(this.zzs);
            this.zzs.setActive(true);
            this.zzr.start();
            this.zzq = false;
            return this;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x001f A[Catch: all -> 0x006c, TRY_LEAVE, TryCatch #2 {, blocks: (B:4:0x0003, B:7:0x000e, B:10:0x0019, B:11:0x001b, B:13:0x001f, B:14:0x0027, B:16:0x002b, B:21:0x005e, B:17:0x0031, B:20:0x0038, B:22:0x0065, B:23:0x006a, B:9:0x0012), top: B:33:0x0003, inners: #0, #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0065 A[Catch: all -> 0x006c, TryCatch #2 {, blocks: (B:4:0x0003, B:7:0x000e, B:10:0x0019, B:11:0x001b, B:13:0x001f, B:14:0x0027, B:16:0x002b, B:21:0x005e, B:17:0x0031, B:20:0x0038, B:22:0x0065, B:23:0x006a, B:9:0x0012), top: B:33:0x0003, inners: #0, #1 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void stop() {
        /*
            r6 = this;
            java.lang.Object r0 = r6.zzh
            monitor-enter(r0)
            com.google.android.gms.vision.CameraSource$zza r1 = r6.zzs     // Catch: java.lang.Throwable -> L6c
            r2 = 0
            r1.setActive(r2)     // Catch: java.lang.Throwable -> L6c
            java.lang.Thread r1 = r6.zzr     // Catch: java.lang.Throwable -> L6c
            r2 = 0
            if (r1 == 0) goto L1b
            r1.join()     // Catch: java.lang.InterruptedException -> L12 java.lang.Throwable -> L6c
            goto L19
        L12:
            java.lang.String r1 = "CameraSource"
            java.lang.String r3 = "Frame processing thread interrupted on release."
            android.util.Log.d(r1, r3)     // Catch: java.lang.Throwable -> L6c
        L19:
            r6.zzr = r2     // Catch: java.lang.Throwable -> L6c
        L1b:
            android.hardware.Camera r1 = r6.zzi     // Catch: java.lang.Throwable -> L6c
            if (r1 == 0) goto L65
            r1.stopPreview()     // Catch: java.lang.Throwable -> L6c
            android.hardware.Camera r1 = r6.zzi     // Catch: java.lang.Throwable -> L6c
            r1.setPreviewCallbackWithBuffer(r2)     // Catch: java.lang.Throwable -> L6c
            boolean r1 = r6.zzq     // Catch: java.lang.Exception -> L37 java.lang.Throwable -> L6c
            if (r1 == 0) goto L31
            android.hardware.Camera r1 = r6.zzi     // Catch: java.lang.Exception -> L37 java.lang.Throwable -> L6c
            r1.setPreviewTexture(r2)     // Catch: java.lang.Exception -> L37 java.lang.Throwable -> L6c
            goto L5e
        L31:
            android.hardware.Camera r1 = r6.zzi     // Catch: java.lang.Exception -> L37 java.lang.Throwable -> L6c
            r1.setPreviewDisplay(r2)     // Catch: java.lang.Exception -> L37 java.lang.Throwable -> L6c
            goto L5e
        L37:
            r1 = move-exception
            java.lang.String r3 = "CameraSource"
            java.lang.String r1 = java.lang.String.valueOf(r1)     // Catch: java.lang.Throwable -> L6c
            java.lang.String r4 = java.lang.String.valueOf(r1)     // Catch: java.lang.Throwable -> L6c
            int r4 = r4.length()     // Catch: java.lang.Throwable -> L6c
            int r4 = r4 + 32
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L6c
            r5.<init>(r4)     // Catch: java.lang.Throwable -> L6c
            java.lang.String r4 = "Failed to clear camera preview: "
            java.lang.StringBuilder r4 = r5.append(r4)     // Catch: java.lang.Throwable -> L6c
            java.lang.StringBuilder r1 = r4.append(r1)     // Catch: java.lang.Throwable -> L6c
            java.lang.String r1 = r1.toString()     // Catch: java.lang.Throwable -> L6c
            android.util.Log.e(r3, r1)     // Catch: java.lang.Throwable -> L6c
        L5e:
            android.hardware.Camera r1 = r6.zzi     // Catch: java.lang.Throwable -> L6c
            r1.release()     // Catch: java.lang.Throwable -> L6c
            r6.zzi = r2     // Catch: java.lang.Throwable -> L6c
        L65:
            java.util.Map<byte[], java.nio.ByteBuffer> r1 = r6.zzt     // Catch: java.lang.Throwable -> L6c
            r1.clear()     // Catch: java.lang.Throwable -> L6c
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L6c
            return
        L6c:
            r1 = move-exception
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L6c
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.vision.CameraSource.stop():void");
    }

    public Size getPreviewSize() {
        return this.zzj;
    }

    public int getCameraFacing() {
        return this.facing;
    }

    public void takePicture(ShutterCallback shutterCallback, PictureCallback pictureCallback) {
        synchronized (this.zzh) {
            if (this.zzi != null) {
                zzd zzdVar = new zzd();
                zzdVar.zzag = shutterCallback;
                zzc zzcVar = new zzc();
                zzcVar.zzaf = pictureCallback;
                this.zzi.takePicture(zzdVar, null, null, zzcVar);
            }
        }
    }

    private CameraSource() {
        this.zzh = new Object();
        this.facing = 0;
        this.zzk = 30.0f;
        this.zzl = 1024;
        this.zzm = 768;
        this.zzn = false;
        this.zzt = new HashMap();
    }

    private final Camera zzc() throws IOException {
        int i;
        int i2;
        int i3;
        int i4 = this.facing;
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        int i5 = 0;
        while (true) {
            if (i5 >= Camera.getNumberOfCameras()) {
                i5 = -1;
                break;
            }
            Camera.getCameraInfo(i5, cameraInfo);
            if (cameraInfo.facing == i4) {
                break;
            }
            i5++;
        }
        if (i5 == -1) {
            throw new IOException("Could not find requested camera.");
        }
        Camera cameraOpen = Camera.open(i5);
        int i6 = this.zzl;
        int i7 = this.zzm;
        Camera.Parameters parameters = cameraOpen.getParameters();
        List<Camera.Size> supportedPreviewSizes = parameters.getSupportedPreviewSizes();
        List<Camera.Size> supportedPictureSizes = parameters.getSupportedPictureSizes();
        ArrayList arrayList = new ArrayList();
        for (Camera.Size size : supportedPreviewSizes) {
            float f = size.width / size.height;
            Iterator<Camera.Size> it = supportedPictureSizes.iterator();
            while (true) {
                if (it.hasNext()) {
                    Camera.Size next = it.next();
                    if (Math.abs(f - (next.width / next.height)) < 0.01f) {
                        arrayList.add(new zze(size, next));
                        break;
                    }
                }
            }
        }
        if (arrayList.size() == 0) {
            Log.w("CameraSource", "No preview sizes have a corresponding same-aspect-ratio picture size");
            Iterator<Camera.Size> it2 = supportedPreviewSizes.iterator();
            while (it2.hasNext()) {
                arrayList.add(new zze(it2.next(), null));
            }
        }
        int size2 = arrayList.size();
        int i8 = Integer.MAX_VALUE;
        int i9 = 0;
        int i10 = Integer.MAX_VALUE;
        zze zzeVar = null;
        while (i9 < size2) {
            Object obj = arrayList.get(i9);
            i9++;
            zze zzeVar2 = (zze) obj;
            Size sizeZzd = zzeVar2.zzd();
            int iAbs = Math.abs(sizeZzd.getWidth() - i6) + Math.abs(sizeZzd.getHeight() - i7);
            if (iAbs < i10) {
                zzeVar = zzeVar2;
                i10 = iAbs;
            }
        }
        if (zzeVar == null) {
            throw new IOException("Could not find suitable preview size.");
        }
        Size sizeZze = zzeVar.zze();
        this.zzj = zzeVar.zzd();
        int i11 = (int) (this.zzk * 1000.0f);
        int[] iArr = null;
        for (int[] iArr2 : cameraOpen.getParameters().getSupportedPreviewFpsRange()) {
            int iAbs2 = Math.abs(i11 - iArr2[0]) + Math.abs(i11 - iArr2[1]);
            if (iAbs2 < i8) {
                iArr = iArr2;
                i8 = iAbs2;
            }
        }
        if (iArr == null) {
            throw new IOException("Could not find suitable preview frames per second range.");
        }
        Camera.Parameters parameters2 = cameraOpen.getParameters();
        if (sizeZze != null) {
            parameters2.setPictureSize(sizeZze.getWidth(), sizeZze.getHeight());
        }
        parameters2.setPreviewSize(this.zzj.getWidth(), this.zzj.getHeight());
        parameters2.setPreviewFpsRange(iArr[0], iArr[1]);
        parameters2.setPreviewFormat(17);
        int rotation = ((WindowManager) this.zzg.getSystemService("window")).getDefaultDisplay().getRotation();
        if (rotation == 0) {
            i = 0;
        } else if (rotation == 1) {
            i = 90;
        } else if (rotation == 2) {
            i = CircleBarView.TWO_QUARTER;
        } else if (rotation != 3) {
            Log.e("CameraSource", new StringBuilder(31).append("Bad rotation value: ").append(rotation).toString());
            i = 0;
        } else {
            i = CircleBarView.THREE_QUARTER;
        }
        Camera.CameraInfo cameraInfo2 = new Camera.CameraInfo();
        Camera.getCameraInfo(i5, cameraInfo2);
        if (cameraInfo2.facing == 1) {
            i2 = (cameraInfo2.orientation + i) % CircleBarView.FULL;
            i3 = (360 - i2) % CircleBarView.FULL;
        } else {
            i2 = ((cameraInfo2.orientation - i) + CircleBarView.FULL) % CircleBarView.FULL;
            i3 = i2;
        }
        this.rotation = i2 / 90;
        cameraOpen.setDisplayOrientation(i3);
        parameters2.setRotation(i2);
        if (this.zzo != null) {
            if (parameters2.getSupportedFocusModes().contains(this.zzo)) {
                parameters2.setFocusMode(this.zzo);
            } else {
                Log.w("CameraSource", String.format("FocusMode %s is not supported on this device.", this.zzo));
                this.zzo = null;
            }
        }
        if (this.zzo == null && this.zzn) {
            if (parameters2.getSupportedFocusModes().contains("continuous-video")) {
                parameters2.setFocusMode("continuous-video");
                this.zzo = "continuous-video";
            } else {
                Log.i("CameraSource", "Camera auto focus is not supported on this device.");
            }
        }
        cameraOpen.setParameters(parameters2);
        cameraOpen.setPreviewCallbackWithBuffer(new zzb());
        cameraOpen.addCallbackBuffer(zza(this.zzj));
        cameraOpen.addCallbackBuffer(zza(this.zzj));
        cameraOpen.addCallbackBuffer(zza(this.zzj));
        cameraOpen.addCallbackBuffer(zza(this.zzj));
        return cameraOpen;
    }

    private final byte[] zza(Size size) {
        byte[] bArr = new byte[((int) Math.ceil(((size.getHeight() * size.getWidth()) * ImageFormat.getBitsPerPixel(17)) / 8.0d)) + 1];
        ByteBuffer byteBufferWrap = ByteBuffer.wrap(bArr);
        if (!byteBufferWrap.hasArray() || byteBufferWrap.array() != bArr) {
            throw new IllegalStateException("Failed to create valid buffer for camera source.");
        }
        this.zzt.put(bArr, byteBufferWrap);
        return bArr;
    }
}
