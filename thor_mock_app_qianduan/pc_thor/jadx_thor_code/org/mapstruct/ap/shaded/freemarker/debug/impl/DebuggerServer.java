package org.mapstruct.ap.shaded.freemarker.debug.impl;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Random;
import org.mapstruct.ap.shaded.freemarker.debug.Debugger;
import org.mapstruct.ap.shaded.freemarker.log.Logger;
import org.mapstruct.ap.shaded.freemarker.template.utility.SecurityUtilities;
import org.mapstruct.ap.shaded.freemarker.template.utility.UndeclaredThrowableException;

/* loaded from: classes3.dex */
class DebuggerServer {
    private final Serializable debuggerStub;
    private final byte[] password;
    private ServerSocket serverSocket;
    private static final Logger logger = Logger.getLogger("org.mapstruct.ap.shaded.freemarker.debug.server");
    private static final Random R = new SecureRandom();
    private boolean stop = false;
    private final int port = SecurityUtilities.getSystemProperty("org.mapstruct.ap.shaded.freemarker.debug.port", Debugger.DEFAULT_PORT).intValue();

    public DebuggerServer(Serializable serializable) {
        try {
            this.password = SecurityUtilities.getSystemProperty("org.mapstruct.ap.shaded.freemarker.debug.password", "").getBytes("UTF-8");
            this.debuggerStub = serializable;
        } catch (UnsupportedEncodingException e) {
            throw new UndeclaredThrowableException(e);
        }
    }

    public void start() {
        new Thread(new Runnable() { // from class: org.mapstruct.ap.shaded.freemarker.debug.impl.DebuggerServer.1
            @Override // java.lang.Runnable
            public void run() {
                DebuggerServer.this.startInternal();
            }
        }, "FreeMarker Debugger Server Acceptor").start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startInternal() {
        try {
            this.serverSocket = new ServerSocket(this.port);
            while (!this.stop) {
                new Thread(new DebuggerAuthProtocol(this.serverSocket.accept())).start();
            }
        } catch (IOException e) {
            logger.error("Debugger server shut down.", e);
        }
    }

    private class DebuggerAuthProtocol implements Runnable {
        private final Socket s;

        DebuggerAuthProtocol(Socket socket) {
            this.s = socket;
        }

        @Override // java.lang.Runnable
        public void run() throws NoSuchAlgorithmException, IOException {
            try {
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(this.s.getOutputStream());
                ObjectInputStream objectInputStream = new ObjectInputStream(this.s.getInputStream());
                byte[] bArr = new byte[512];
                DebuggerServer.R.nextBytes(bArr);
                objectOutputStream.writeInt(220);
                objectOutputStream.writeObject(bArr);
                MessageDigest messageDigest = MessageDigest.getInstance("SHA");
                messageDigest.update(DebuggerServer.this.password);
                messageDigest.update(bArr);
                if (Arrays.equals((byte[]) objectInputStream.readObject(), messageDigest.digest())) {
                    objectOutputStream.writeObject(DebuggerServer.this.debuggerStub);
                } else {
                    objectOutputStream.writeObject(null);
                }
            } catch (Exception e) {
                DebuggerServer.logger.warn(new StringBuffer("Connection to ").append(this.s.getInetAddress().getHostAddress()).append(" abruply broke").toString(), e);
            }
        }
    }

    public void stop() throws IOException {
        this.stop = true;
        ServerSocket serverSocket = this.serverSocket;
        if (serverSocket != null) {
            try {
                serverSocket.close();
            } catch (IOException e) {
                logger.error("Unable to close server socket.", e);
            }
        }
    }
}
