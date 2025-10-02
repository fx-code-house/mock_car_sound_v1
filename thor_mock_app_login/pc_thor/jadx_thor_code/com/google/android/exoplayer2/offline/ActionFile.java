package com.google.android.exoplayer2.offline;

import android.net.Uri;
import com.google.android.exoplayer2.offline.DownloadRequest;
import com.google.android.exoplayer2.util.AtomicFile;
import com.google.android.exoplayer2.util.Util;
import java.io.Closeable;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

@Deprecated
/* loaded from: classes.dex */
final class ActionFile {
    private static final String DOWNLOAD_TYPE_DASH = "dash";
    private static final String DOWNLOAD_TYPE_HLS = "hls";
    private static final String DOWNLOAD_TYPE_PROGRESSIVE = "progressive";
    private static final String DOWNLOAD_TYPE_SS = "ss";
    private static final int VERSION = 0;
    private final AtomicFile atomicFile;

    public ActionFile(File actionFile) {
        this.atomicFile = new AtomicFile(actionFile);
    }

    public boolean exists() {
        return this.atomicFile.exists();
    }

    public void delete() {
        this.atomicFile.delete();
    }

    public DownloadRequest[] load() throws IOException {
        if (!exists()) {
            return new DownloadRequest[0];
        }
        try {
            InputStream inputStreamOpenRead = this.atomicFile.openRead();
            DataInputStream dataInputStream = new DataInputStream(inputStreamOpenRead);
            int i = dataInputStream.readInt();
            if (i > 0) {
                throw new IOException(new StringBuilder(44).append("Unsupported action file version: ").append(i).toString());
            }
            int i2 = dataInputStream.readInt();
            ArrayList arrayList = new ArrayList();
            for (int i3 = 0; i3 < i2; i3++) {
                try {
                    arrayList.add(readDownloadRequest(dataInputStream));
                } catch (DownloadRequest.UnsupportedRequestException unused) {
                }
            }
            DownloadRequest[] downloadRequestArr = (DownloadRequest[]) arrayList.toArray(new DownloadRequest[0]);
            Util.closeQuietly(inputStreamOpenRead);
            return downloadRequestArr;
        } catch (Throwable th) {
            Util.closeQuietly((Closeable) null);
            throw th;
        }
    }

    private static DownloadRequest readDownloadRequest(DataInputStream input) throws IOException {
        byte[] bArr;
        String utf = input.readUTF();
        int i = input.readInt();
        Uri uri = Uri.parse(input.readUTF());
        boolean z = input.readBoolean();
        int i2 = input.readInt();
        String utf2 = null;
        if (i2 != 0) {
            bArr = new byte[i2];
            input.readFully(bArr);
        } else {
            bArr = null;
        }
        boolean z2 = true;
        boolean z3 = i == 0 && DOWNLOAD_TYPE_PROGRESSIVE.equals(utf);
        ArrayList arrayList = new ArrayList();
        if (!z3) {
            int i3 = input.readInt();
            for (int i4 = 0; i4 < i3; i4++) {
                arrayList.add(readKey(utf, i, input));
            }
        }
        if (i >= 2 || (!DOWNLOAD_TYPE_DASH.equals(utf) && !DOWNLOAD_TYPE_HLS.equals(utf) && !DOWNLOAD_TYPE_SS.equals(utf))) {
            z2 = false;
        }
        if (!z2 && input.readBoolean()) {
            utf2 = input.readUTF();
        }
        String strGenerateDownloadId = i < 3 ? generateDownloadId(uri, utf2) : input.readUTF();
        if (z) {
            throw new DownloadRequest.UnsupportedRequestException();
        }
        return new DownloadRequest.Builder(strGenerateDownloadId, uri).setMimeType(inferMimeType(utf)).setStreamKeys(arrayList).setCustomCacheKey(utf2).setData(bArr).build();
    }

    private static StreamKey readKey(String type, int version, DataInputStream input) throws IOException {
        int i;
        int i2;
        int i3;
        if ((DOWNLOAD_TYPE_HLS.equals(type) || DOWNLOAD_TYPE_SS.equals(type)) && version == 0) {
            i = input.readInt();
            i2 = input.readInt();
            i3 = 0;
        } else {
            int i4 = input.readInt();
            int i5 = input.readInt();
            int i6 = input.readInt();
            i3 = i4;
            i = i5;
            i2 = i6;
        }
        return new StreamKey(i3, i, i2);
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x0044  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private static java.lang.String inferMimeType(java.lang.String r4) {
        /*
            int r0 = r4.hashCode()
            r1 = 3680(0xe60, float:5.157E-42)
            r2 = 2
            r3 = 1
            if (r0 == r1) goto L39
            r1 = 103407(0x193ef, float:1.44904E-40)
            if (r0 == r1) goto L2f
            r1 = 3075986(0x2eef92, float:4.310374E-39)
            if (r0 == r1) goto L25
            r1 = 1131547531(0x43720b8b, float:242.04509)
            if (r0 == r1) goto L1a
            goto L44
        L1a:
            java.lang.String r0 = "progressive"
            boolean r4 = r4.equals(r0)
            if (r4 == 0) goto L44
            r4 = 3
            goto L45
        L25:
            java.lang.String r0 = "dash"
            boolean r4 = r4.equals(r0)
            if (r4 == 0) goto L44
            r4 = 0
            goto L45
        L2f:
            java.lang.String r0 = "hls"
            boolean r4 = r4.equals(r0)
            if (r4 == 0) goto L44
            r4 = r3
            goto L45
        L39:
            java.lang.String r0 = "ss"
            boolean r4 = r4.equals(r0)
            if (r4 == 0) goto L44
            r4 = r2
            goto L45
        L44:
            r4 = -1
        L45:
            if (r4 == 0) goto L55
            if (r4 == r3) goto L52
            if (r4 == r2) goto L4f
            java.lang.String r4 = "video/x-unknown"
            return r4
        L4f:
            java.lang.String r4 = "application/vnd.ms-sstr+xml"
            return r4
        L52:
            java.lang.String r4 = "application/x-mpegURL"
            return r4
        L55:
            java.lang.String r4 = "application/dash+xml"
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.offline.ActionFile.inferMimeType(java.lang.String):java.lang.String");
    }

    private static String generateDownloadId(Uri uri, String customCacheKey) {
        return customCacheKey != null ? customCacheKey : uri.toString();
    }
}
