package com.google.android.exoplayer2.source.rtsp;

import android.os.Handler;
import android.os.HandlerThread;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.upstream.Loader;
import com.google.android.exoplayer2.util.Assertions;
import com.google.common.base.Charsets;
import com.google.common.collect.ImmutableList;
import com.google.common.primitives.Ints;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/* loaded from: classes.dex */
final class RtspMessageChannel implements Closeable {
    public static final Charset CHARSET = Charsets.UTF_8;
    public static final int DEFAULT_RTSP_PORT = 554;
    private static final String TAG = "RtspMessageChannel";
    private volatile boolean closed;
    private final MessageListener messageListener;
    private Sender sender;
    private Socket socket;
    private final Loader receiverLoader = new Loader("ExoPlayer:RtspMessageChannel:ReceiverLoader");
    private final Map<Integer, InterleavedBinaryDataListener> interleavedBinaryDataListeners = Collections.synchronizedMap(new HashMap());

    public interface InterleavedBinaryDataListener {
        void onInterleavedBinaryDataReceived(byte[] data);
    }

    public interface MessageListener {
        default void onReceivingFailed(Exception e) {
        }

        void onRtspMessageReceived(List<String> message);

        default void onSendingFailed(List<String> message, Exception e) {
        }
    }

    public RtspMessageChannel(MessageListener messageListener) {
        this.messageListener = messageListener;
    }

    public void open(Socket socket) throws IOException {
        this.socket = socket;
        this.sender = new Sender(socket.getOutputStream());
        this.receiverLoader.startLoading(new Receiver(socket.getInputStream()), new LoaderCallbackImpl(), 0);
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        if (this.closed) {
            return;
        }
        try {
            Sender sender = this.sender;
            if (sender != null) {
                sender.close();
            }
            this.receiverLoader.release();
            Socket socket = this.socket;
            if (socket != null) {
                socket.close();
            }
        } finally {
            this.closed = true;
        }
    }

    public void send(List<String> message) {
        Assertions.checkStateNotNull(this.sender);
        this.sender.send(message);
    }

    public void registerInterleavedBinaryDataListener(int channel, InterleavedBinaryDataListener listener) {
        this.interleavedBinaryDataListeners.put(Integer.valueOf(channel), listener);
    }

    /* JADX INFO: Access modifiers changed from: private */
    final class Sender implements Closeable {
        private final OutputStream outputStream;
        private final HandlerThread senderThread;
        private final Handler senderThreadHandler;

        public Sender(OutputStream outputStream) {
            this.outputStream = outputStream;
            HandlerThread handlerThread = new HandlerThread("ExoPlayer:RtspMessageChannel:Sender");
            this.senderThread = handlerThread;
            handlerThread.start();
            this.senderThreadHandler = new Handler(handlerThread.getLooper());
        }

        public void send(final List<String> message) {
            final byte[] bArrConvertMessageToByteArray = RtspMessageUtil.convertMessageToByteArray(message);
            this.senderThreadHandler.post(new Runnable() { // from class: com.google.android.exoplayer2.source.rtsp.RtspMessageChannel$Sender$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() throws IOException {
                    this.f$0.m195x9fa6909d(bArrConvertMessageToByteArray, message);
                }
            });
        }

        /* renamed from: lambda$send$0$com-google-android-exoplayer2-source-rtsp-RtspMessageChannel$Sender, reason: not valid java name */
        /* synthetic */ void m195x9fa6909d(byte[] bArr, List list) throws IOException {
            try {
                this.outputStream.write(bArr);
            } catch (Exception e) {
                if (RtspMessageChannel.this.closed) {
                    return;
                }
                RtspMessageChannel.this.messageListener.onSendingFailed(list, e);
            }
        }

        @Override // java.io.Closeable, java.lang.AutoCloseable
        public void close() {
            Handler handler = this.senderThreadHandler;
            final HandlerThread handlerThread = this.senderThread;
            Objects.requireNonNull(handlerThread);
            handler.post(new Runnable() { // from class: com.google.android.exoplayer2.source.rtsp.RtspMessageChannel$Sender$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    handlerThread.quit();
                }
            });
            try {
                this.senderThread.join();
            } catch (InterruptedException unused) {
                this.senderThread.interrupt();
            }
        }
    }

    private final class Receiver implements Loader.Loadable {
        private static final byte INTERLEAVED_MESSAGE_MARKER = 36;
        private final DataInputStream dataInputStream;
        private volatile boolean loadCanceled;
        private final MessageParser messageParser = new MessageParser();

        public Receiver(InputStream inputStream) {
            this.dataInputStream = new DataInputStream(inputStream);
        }

        @Override // com.google.android.exoplayer2.upstream.Loader.Loadable
        public void cancelLoad() {
            this.loadCanceled = true;
        }

        @Override // com.google.android.exoplayer2.upstream.Loader.Loadable
        public void load() throws IOException {
            while (!this.loadCanceled) {
                byte b = this.dataInputStream.readByte();
                if (b == 36) {
                    handleInterleavedBinaryData();
                } else {
                    handleRtspMessage(b);
                }
            }
        }

        private void handleRtspMessage(byte firstByte) throws IOException {
            if (RtspMessageChannel.this.closed) {
                return;
            }
            RtspMessageChannel.this.messageListener.onRtspMessageReceived(this.messageParser.parseNext(firstByte, this.dataInputStream));
        }

        private void handleInterleavedBinaryData() throws IOException {
            int unsignedByte = this.dataInputStream.readUnsignedByte();
            int unsignedShort = this.dataInputStream.readUnsignedShort();
            byte[] bArr = new byte[unsignedShort];
            this.dataInputStream.readFully(bArr, 0, unsignedShort);
            InterleavedBinaryDataListener interleavedBinaryDataListener = (InterleavedBinaryDataListener) RtspMessageChannel.this.interleavedBinaryDataListeners.get(Integer.valueOf(unsignedByte));
            if (interleavedBinaryDataListener == null || RtspMessageChannel.this.closed) {
                return;
            }
            interleavedBinaryDataListener.onInterleavedBinaryDataReceived(bArr);
        }
    }

    private final class LoaderCallbackImpl implements Loader.Callback<Receiver> {
        @Override // com.google.android.exoplayer2.upstream.Loader.Callback
        public void onLoadCanceled(Receiver loadable, long elapsedRealtimeMs, long loadDurationMs, boolean released) {
        }

        @Override // com.google.android.exoplayer2.upstream.Loader.Callback
        public void onLoadCompleted(Receiver loadable, long elapsedRealtimeMs, long loadDurationMs) {
        }

        private LoaderCallbackImpl() {
        }

        @Override // com.google.android.exoplayer2.upstream.Loader.Callback
        public Loader.LoadErrorAction onLoadError(Receiver loadable, long elapsedRealtimeMs, long loadDurationMs, IOException error, int errorCount) {
            if (!RtspMessageChannel.this.closed) {
                RtspMessageChannel.this.messageListener.onReceivingFailed(error);
            }
            return Loader.DONT_RETRY;
        }
    }

    private static final class MessageParser {
        private static final int STATE_READING_BODY = 3;
        private static final int STATE_READING_FIRST_LINE = 1;
        private static final int STATE_READING_HEADER = 2;
        private long messageBodyLength;
        private final List<String> messageLines = new ArrayList();
        private int state = 1;

        public ImmutableList<String> parseNext(byte firstByte, DataInputStream dataInputStream) throws IOException {
            ImmutableList<String> immutableListAddMessageLine = addMessageLine(parseNextLine(firstByte, dataInputStream));
            while (immutableListAddMessageLine == null) {
                if (this.state == 3) {
                    long j = this.messageBodyLength;
                    if (j > 0) {
                        int iCheckedCast = Ints.checkedCast(j);
                        Assertions.checkState(iCheckedCast != -1);
                        byte[] bArr = new byte[iCheckedCast];
                        dataInputStream.readFully(bArr, 0, iCheckedCast);
                        immutableListAddMessageLine = addMessageBody(bArr);
                    } else {
                        throw new IllegalStateException("Expects a greater than zero Content-Length.");
                    }
                } else {
                    immutableListAddMessageLine = addMessageLine(parseNextLine(dataInputStream.readByte(), dataInputStream));
                }
            }
            return immutableListAddMessageLine;
        }

        private static byte[] parseNextLine(byte firstByte, DataInputStream dataInputStream) throws IOException {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] bArr = {firstByte, dataInputStream.readByte()};
            byteArrayOutputStream.write(bArr);
            while (true) {
                if (bArr[0] != 13 || bArr[1] != 10) {
                    bArr[0] = bArr[1];
                    byte b = dataInputStream.readByte();
                    bArr[1] = b;
                    byteArrayOutputStream.write(b);
                } else {
                    return byteArrayOutputStream.toByteArray();
                }
            }
        }

        private ImmutableList<String> addMessageLine(byte[] lineBytes) throws ParserException {
            Assertions.checkArgument(lineBytes.length >= 2 && lineBytes[lineBytes.length - 2] == 13 && lineBytes[lineBytes.length - 1] == 10);
            String str = new String(lineBytes, 0, lineBytes.length - 2, RtspMessageChannel.CHARSET);
            this.messageLines.add(str);
            int i = this.state;
            if (i == 1) {
                if (!RtspMessageUtil.isRtspStartLine(str)) {
                    return null;
                }
                this.state = 2;
                return null;
            }
            if (i == 2) {
                long contentLengthHeader = RtspMessageUtil.parseContentLengthHeader(str);
                if (contentLengthHeader != -1) {
                    this.messageBodyLength = contentLengthHeader;
                }
                if (!str.isEmpty()) {
                    return null;
                }
                if (this.messageBodyLength > 0) {
                    this.state = 3;
                    return null;
                }
                ImmutableList<String> immutableListCopyOf = ImmutableList.copyOf((Collection) this.messageLines);
                reset();
                return immutableListCopyOf;
            }
            throw new IllegalStateException();
        }

        private ImmutableList<String> addMessageBody(byte[] messageBodyBytes) {
            String str;
            Assertions.checkState(this.state == 3);
            if (messageBodyBytes.length > 0 && messageBodyBytes[messageBodyBytes.length - 1] == 10) {
                if (messageBodyBytes.length > 1 && messageBodyBytes[messageBodyBytes.length - 2] == 13) {
                    str = new String(messageBodyBytes, 0, messageBodyBytes.length - 2, RtspMessageChannel.CHARSET);
                } else {
                    str = new String(messageBodyBytes, 0, messageBodyBytes.length - 1, RtspMessageChannel.CHARSET);
                }
                this.messageLines.add(str);
                ImmutableList<String> immutableListCopyOf = ImmutableList.copyOf((Collection) this.messageLines);
                reset();
                return immutableListCopyOf;
            }
            throw new IllegalArgumentException("Message body is empty or does not end with a LF.");
        }

        private void reset() {
            this.messageLines.clear();
            this.state = 1;
            this.messageBodyLength = 0L;
        }
    }
}
