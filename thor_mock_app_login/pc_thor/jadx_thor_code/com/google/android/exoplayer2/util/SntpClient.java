package com.google.android.exoplayer2.util;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.upstream.Loader;
import com.google.common.base.Ascii;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.ConcurrentModificationException;

/* loaded from: classes.dex */
public final class SntpClient {
    public static final String DEFAULT_NTP_HOST = "time.android.com";
    private static final int NTP_LEAP_NOSYNC = 3;
    private static final int NTP_MODE_BROADCAST = 5;
    private static final int NTP_MODE_CLIENT = 3;
    private static final int NTP_MODE_SERVER = 4;
    private static final int NTP_PACKET_SIZE = 48;
    private static final int NTP_PORT = 123;
    private static final int NTP_STRATUM_DEATH = 0;
    private static final int NTP_STRATUM_MAX = 15;
    private static final int NTP_VERSION = 3;
    private static final long OFFSET_1900_TO_1970 = 2208988800L;
    private static final int ORIGINATE_TIME_OFFSET = 24;
    private static final int RECEIVE_TIME_OFFSET = 32;
    private static final int TIMEOUT_MS = 10000;
    private static final int TRANSMIT_TIME_OFFSET = 40;
    private static long elapsedRealtimeOffsetMs = 0;
    private static boolean isInitialized = false;
    private static String ntpHost = "time.android.com";
    private static final Object loaderLock = new Object();
    private static final Object valueLock = new Object();

    public interface InitializationCallback {
        void onInitializationFailed(IOException error);

        void onInitialized();
    }

    private SntpClient() {
    }

    public static String getNtpHost() {
        String str;
        synchronized (valueLock) {
            str = ntpHost;
        }
        return str;
    }

    public static void setNtpHost(String ntpHost2) {
        synchronized (valueLock) {
            if (!ntpHost.equals(ntpHost2)) {
                ntpHost = ntpHost2;
                isInitialized = false;
            }
        }
    }

    public static boolean isInitialized() {
        boolean z;
        synchronized (valueLock) {
            z = isInitialized;
        }
        return z;
    }

    public static long getElapsedRealtimeOffsetMs() {
        long j;
        synchronized (valueLock) {
            j = isInitialized ? elapsedRealtimeOffsetMs : C.TIME_UNSET;
        }
        return j;
    }

    public static void initialize(Loader loader, InitializationCallback callback) {
        if (isInitialized()) {
            if (callback != null) {
                callback.onInitialized();
            }
        } else {
            if (loader == null) {
                loader = new Loader("SntpClient");
            }
            loader.startLoading(new NtpTimeLoadable(), new NtpTimeCallback(callback), 1);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static long loadNtpTimeOffsetMs() throws IOException {
        InetAddress byName = InetAddress.getByName(getNtpHost());
        DatagramSocket datagramSocket = new DatagramSocket();
        try {
            datagramSocket.setSoTimeout(10000);
            byte[] bArr = new byte[48];
            DatagramPacket datagramPacket = new DatagramPacket(bArr, 48, byName, 123);
            bArr[0] = Ascii.ESC;
            long jCurrentTimeMillis = System.currentTimeMillis();
            long jElapsedRealtime = android.os.SystemClock.elapsedRealtime();
            writeTimestamp(bArr, 40, jCurrentTimeMillis);
            datagramSocket.send(datagramPacket);
            datagramSocket.receive(new DatagramPacket(bArr, 48));
            long jElapsedRealtime2 = android.os.SystemClock.elapsedRealtime();
            long j = jCurrentTimeMillis + (jElapsedRealtime2 - jElapsedRealtime);
            byte b = bArr[0];
            int i = bArr[1] & 255;
            long timestamp = readTimestamp(bArr, 24);
            long timestamp2 = readTimestamp(bArr, 32);
            long timestamp3 = readTimestamp(bArr, 40);
            checkValidServerReply((byte) ((b >> 6) & 3), (byte) (b & 7), i, timestamp3);
            long j2 = (j + (((timestamp2 - timestamp) + (timestamp3 - j)) / 2)) - jElapsedRealtime2;
            datagramSocket.close();
            return j2;
        } catch (Throwable th) {
            try {
                datagramSocket.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    private static long readTimestamp(byte[] buffer, int offset) {
        long j = read32(buffer, offset);
        long j2 = read32(buffer, offset + 4);
        if (j == 0 && j2 == 0) {
            return 0L;
        }
        return ((j - OFFSET_1900_TO_1970) * 1000) + ((j2 * 1000) / 4294967296L);
    }

    private static void writeTimestamp(byte[] buffer, int offset, long time) {
        if (time == 0) {
            Arrays.fill(buffer, offset, offset + 8, (byte) 0);
            return;
        }
        long j = time / 1000;
        long j2 = time - (j * 1000);
        long j3 = j + OFFSET_1900_TO_1970;
        int i = offset + 1;
        buffer[offset] = (byte) (j3 >> 24);
        int i2 = i + 1;
        buffer[i] = (byte) (j3 >> 16);
        int i3 = i2 + 1;
        buffer[i2] = (byte) (j3 >> 8);
        int i4 = i3 + 1;
        buffer[i3] = (byte) (j3 >> 0);
        long j4 = (j2 * 4294967296L) / 1000;
        int i5 = i4 + 1;
        buffer[i4] = (byte) (j4 >> 24);
        int i6 = i5 + 1;
        buffer[i5] = (byte) (j4 >> 16);
        buffer[i6] = (byte) (j4 >> 8);
        buffer[i6 + 1] = (byte) (Math.random() * 255.0d);
    }

    private static long read32(byte[] buffer, int offset) {
        int i = buffer[offset];
        int i2 = buffer[offset + 1];
        int i3 = buffer[offset + 2];
        int i4 = buffer[offset + 3];
        if ((i & 128) == 128) {
            i = (i & 127) + 128;
        }
        if ((i2 & 128) == 128) {
            i2 = (i2 & 127) + 128;
        }
        if ((i3 & 128) == 128) {
            i3 = (i3 & 127) + 128;
        }
        if ((i4 & 128) == 128) {
            i4 = (i4 & 127) + 128;
        }
        return (i << 24) + (i2 << 16) + (i3 << 8) + i4;
    }

    private static void checkValidServerReply(byte leap, byte mode, int stratum, long transmitTime) throws IOException {
        if (leap == 3) {
            throw new IOException("SNTP: Unsynchronized server");
        }
        if (mode != 4 && mode != 5) {
            throw new IOException(new StringBuilder(26).append("SNTP: Untrusted mode: ").append((int) mode).toString());
        }
        if (stratum == 0 || stratum > 15) {
            throw new IOException(new StringBuilder(36).append("SNTP: Untrusted stratum: ").append(stratum).toString());
        }
        if (transmitTime == 0) {
            throw new IOException("SNTP: Zero transmitTime");
        }
    }

    private static final class NtpTimeLoadable implements Loader.Loadable {
        @Override // com.google.android.exoplayer2.upstream.Loader.Loadable
        public void cancelLoad() {
        }

        private NtpTimeLoadable() {
        }

        @Override // com.google.android.exoplayer2.upstream.Loader.Loadable
        public void load() throws IOException {
            synchronized (SntpClient.loaderLock) {
                synchronized (SntpClient.valueLock) {
                    if (SntpClient.isInitialized) {
                        return;
                    }
                    long jLoadNtpTimeOffsetMs = SntpClient.loadNtpTimeOffsetMs();
                    synchronized (SntpClient.valueLock) {
                        long unused = SntpClient.elapsedRealtimeOffsetMs = jLoadNtpTimeOffsetMs;
                        boolean unused2 = SntpClient.isInitialized = true;
                    }
                }
            }
        }
    }

    private static final class NtpTimeCallback implements Loader.Callback<Loader.Loadable> {
        private final InitializationCallback callback;

        @Override // com.google.android.exoplayer2.upstream.Loader.Callback
        public void onLoadCanceled(Loader.Loadable loadable, long elapsedRealtimeMs, long loadDurationMs, boolean released) {
        }

        public NtpTimeCallback(InitializationCallback callback) {
            this.callback = callback;
        }

        @Override // com.google.android.exoplayer2.upstream.Loader.Callback
        public void onLoadCompleted(Loader.Loadable loadable, long elapsedRealtimeMs, long loadDurationMs) {
            if (this.callback != null) {
                if (!SntpClient.isInitialized()) {
                    this.callback.onInitializationFailed(new IOException(new ConcurrentModificationException()));
                } else {
                    this.callback.onInitialized();
                }
            }
        }

        @Override // com.google.android.exoplayer2.upstream.Loader.Callback
        public Loader.LoadErrorAction onLoadError(Loader.Loadable loadable, long elapsedRealtimeMs, long loadDurationMs, IOException error, int errorCount) {
            InitializationCallback initializationCallback = this.callback;
            if (initializationCallback != null) {
                initializationCallback.onInitializationFailed(error);
            }
            return Loader.DONT_RETRY;
        }
    }
}
