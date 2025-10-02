package com.google.android.exoplayer2.source.rtsp;

import android.net.Uri;

/* loaded from: classes.dex */
final class RtspTrackTiming {
    public final long rtpTimestamp;
    public final int sequenceNumber;
    public final Uri uri;

    /* JADX WARN: Removed duplicated region for block: B:23:0x006a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static com.google.common.collect.ImmutableList<com.google.android.exoplayer2.source.rtsp.RtspTrackTiming> parseTrackTiming(java.lang.String r18) throws com.google.android.exoplayer2.ParserException, java.lang.NumberFormatException {
        /*
            com.google.common.collect.ImmutableList$Builder r0 = new com.google.common.collect.ImmutableList$Builder
            r0.<init>()
            java.lang.String r1 = ","
            r2 = r18
            java.lang.String[] r1 = com.google.android.exoplayer2.util.Util.split(r2, r1)
            int r2 = r1.length
            r3 = 0
            r4 = r3
        L10:
            if (r4 >= r2) goto Lbe
            r5 = r1[r4]
            java.lang.String r6 = ";"
            java.lang.String[] r6 = com.google.android.exoplayer2.util.Util.split(r5, r6)
            int r7 = r6.length
            r12 = r3
            r13 = 0
            r14 = -1
            r15 = -9223372036854775807(0x8000000000000001, double:-4.9E-324)
        L23:
            if (r12 >= r7) goto L92
            r8 = r6[r12]
            java.lang.String r9 = "="
            java.lang.String[] r9 = com.google.android.exoplayer2.util.Util.splitAtFirst(r8, r9)     // Catch: java.lang.Exception -> L8c
            r11 = r9[r3]     // Catch: java.lang.Exception -> L8c
            r3 = 1
            r9 = r9[r3]     // Catch: java.lang.Exception -> L8c
            int r10 = r11.hashCode()     // Catch: java.lang.Exception -> L8c
            r3 = 113759(0x1bc5f, float:1.5941E-40)
            r17 = r1
            r1 = 2
            if (r10 == r3) goto L5f
            r3 = 116079(0x1c56f, float:1.62661E-40)
            if (r10 == r3) goto L54
            r3 = 1524180539(0x5ad9263b, float:3.0561052E16)
            if (r10 == r3) goto L49
            goto L6a
        L49:
            java.lang.String r3 = "rtptime"
            boolean r3 = r11.equals(r3)     // Catch: java.lang.Exception -> L8c
            if (r3 == 0) goto L6a
            r3 = r1
            goto L6b
        L54:
            java.lang.String r3 = "url"
            boolean r3 = r11.equals(r3)     // Catch: java.lang.Exception -> L8c
            if (r3 == 0) goto L6a
            r3 = 0
            goto L6b
        L5f:
            java.lang.String r3 = "seq"
            boolean r3 = r11.equals(r3)     // Catch: java.lang.Exception -> L8c
            if (r3 == 0) goto L6a
            r3 = 1
            goto L6b
        L6a:
            r3 = -1
        L6b:
            if (r3 == 0) goto L82
            r10 = 1
            if (r3 == r10) goto L7d
            if (r3 != r1) goto L77
            long r15 = java.lang.Long.parseLong(r9)     // Catch: java.lang.Exception -> L8c
            goto L86
        L77:
            r0 = 0
            com.google.android.exoplayer2.ParserException r0 = com.google.android.exoplayer2.ParserException.createForMalformedManifest(r11, r0)     // Catch: java.lang.Exception -> L8c
            throw r0     // Catch: java.lang.Exception -> L8c
        L7d:
            int r14 = java.lang.Integer.parseInt(r9)     // Catch: java.lang.Exception -> L8c
            goto L86
        L82:
            android.net.Uri r13 = android.net.Uri.parse(r9)     // Catch: java.lang.Exception -> L8c
        L86:
            int r12 = r12 + 1
            r1 = r17
            r3 = 0
            goto L23
        L8c:
            r0 = move-exception
            com.google.android.exoplayer2.ParserException r0 = com.google.android.exoplayer2.ParserException.createForMalformedManifest(r8, r0)
            throw r0
        L92:
            r17 = r1
            if (r13 == 0) goto Lb8
            java.lang.String r1 = r13.getScheme()
            if (r1 == 0) goto Lb8
            r1 = -1
            r8 = r15
            if (r14 != r1) goto La9
            r6 = -9223372036854775807(0x8000000000000001, double:-4.9E-324)
            int r1 = (r8 > r6 ? 1 : (r8 == r6 ? 0 : -1))
            if (r1 == 0) goto Lb8
        La9:
            com.google.android.exoplayer2.source.rtsp.RtspTrackTiming r1 = new com.google.android.exoplayer2.source.rtsp.RtspTrackTiming
            r1.<init>(r8, r14, r13)
            r0.add(r1)
            int r4 = r4 + 1
            r1 = r17
            r3 = 0
            goto L10
        Lb8:
            r0 = 0
            com.google.android.exoplayer2.ParserException r0 = com.google.android.exoplayer2.ParserException.createForMalformedManifest(r5, r0)
            throw r0
        Lbe:
            com.google.common.collect.ImmutableList r0 = r0.build()
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.source.rtsp.RtspTrackTiming.parseTrackTiming(java.lang.String):com.google.common.collect.ImmutableList");
    }

    private RtspTrackTiming(long rtpTimestamp, int sequenceNumber, Uri uri) {
        this.rtpTimestamp = rtpTimestamp;
        this.sequenceNumber = sequenceNumber;
        this.uri = uri;
    }
}
