package com.google.firebase.messaging;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.firebase_messaging.zzh;
import com.google.android.gms.internal.firebase_messaging.zzi;
import com.google.android.gms.internal.firebase_messaging.zzk;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;

/* compiled from: com.google.firebase:firebase-messaging@@21.0.0 */
/* loaded from: classes2.dex */
class ImageDownload implements Closeable {
    private volatile InputStream connectionInputStream;
    private Task<Bitmap> task;
    private final URL url;

    public static ImageDownload create(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        try {
            return new ImageDownload(new URL(str));
        } catch (MalformedURLException unused) {
            String strValueOf = String.valueOf(str);
            Log.w(Constants.TAG, strValueOf.length() != 0 ? "Not downloading image, bad URL: ".concat(strValueOf) : new String("Not downloading image, bad URL: "));
            return null;
        }
    }

    private ImageDownload(URL url) {
        this.url = url;
    }

    public void start(Executor executor) {
        this.task = Tasks.call(executor, new Callable(this) { // from class: com.google.firebase.messaging.ImageDownload$$Lambda$0
            private final ImageDownload arg$1;

            {
                this.arg$1 = this;
            }

            @Override // java.util.concurrent.Callable
            public final Object call() {
                return this.arg$1.blockingDownload();
            }
        });
    }

    public Task<Bitmap> getTask() {
        return (Task) Preconditions.checkNotNull(this.task);
    }

    public Bitmap blockingDownload() throws IOException {
        String strValueOf = String.valueOf(this.url);
        Log.i(Constants.TAG, new StringBuilder(String.valueOf(strValueOf).length() + 22).append("Starting download of: ").append(strValueOf).toString());
        byte[] bArrBlockingDownloadBytes = blockingDownloadBytes();
        Bitmap bitmapDecodeByteArray = BitmapFactory.decodeByteArray(bArrBlockingDownloadBytes, 0, bArrBlockingDownloadBytes.length);
        if (bitmapDecodeByteArray == null) {
            String strValueOf2 = String.valueOf(this.url);
            throw new IOException(new StringBuilder(String.valueOf(strValueOf2).length() + 24).append("Failed to decode image: ").append(strValueOf2).toString());
        }
        if (Log.isLoggable(Constants.TAG, 3)) {
            String strValueOf3 = String.valueOf(this.url);
            Log.d(Constants.TAG, new StringBuilder(String.valueOf(strValueOf3).length() + 31).append("Successfully downloaded image: ").append(strValueOf3).toString());
        }
        return bitmapDecodeByteArray;
    }

    private byte[] blockingDownloadBytes() throws IOException {
        URLConnection uRLConnectionOpenConnection = this.url.openConnection();
        if (uRLConnectionOpenConnection.getContentLength() > 1048576) {
            throw new IOException("Content-Length exceeds max size of 1048576");
        }
        InputStream inputStream = uRLConnectionOpenConnection.getInputStream();
        try {
            this.connectionInputStream = inputStream;
            byte[] bArrZza = zzh.zza(zzh.zza(inputStream, 1048577L));
            if (inputStream != null) {
                inputStream.close();
            }
            if (Log.isLoggable(Constants.TAG, 2)) {
                int length = bArrZza.length;
                String strValueOf = String.valueOf(this.url);
                Log.v(Constants.TAG, new StringBuilder(String.valueOf(strValueOf).length() + 34).append("Downloaded ").append(length).append(" bytes from ").append(strValueOf).toString());
            }
            if (bArrZza.length <= 1048576) {
                return bArrZza;
            }
            throw new IOException("Image exceeds max size of 1048576");
        } catch (Throwable th) {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (Throwable th2) {
                    zzk.zza(th, th2);
                }
            }
            throw th;
        }
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        try {
            zzi.zza(this.connectionInputStream);
        } catch (NullPointerException e) {
            Log.e(Constants.TAG, "Failed to close the image download stream.", e);
        }
    }
}
