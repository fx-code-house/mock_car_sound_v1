package com.google.android.exoplayer2.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/* loaded from: classes.dex */
public final class AtomicFile {
    private static final String TAG = "AtomicFile";
    private final File backupName;
    private final File baseName;

    public AtomicFile(File baseName) {
        this.baseName = baseName;
        this.backupName = new File(String.valueOf(baseName.getPath()).concat(".bak"));
    }

    public boolean exists() {
        return this.baseName.exists() || this.backupName.exists();
    }

    public void delete() {
        this.baseName.delete();
        this.backupName.delete();
    }

    public OutputStream startWrite() throws IOException {
        if (this.baseName.exists()) {
            if (!this.backupName.exists()) {
                if (!this.baseName.renameTo(this.backupName)) {
                    String strValueOf = String.valueOf(this.baseName);
                    String strValueOf2 = String.valueOf(this.backupName);
                    Log.w(TAG, new StringBuilder(String.valueOf(strValueOf).length() + 37 + String.valueOf(strValueOf2).length()).append("Couldn't rename file ").append(strValueOf).append(" to backup file ").append(strValueOf2).toString());
                }
            } else {
                this.baseName.delete();
            }
        }
        try {
            return new AtomicFileOutputStream(this.baseName);
        } catch (FileNotFoundException e) {
            File parentFile = this.baseName.getParentFile();
            if (parentFile == null || !parentFile.mkdirs()) {
                String strValueOf3 = String.valueOf(this.baseName);
                throw new IOException(new StringBuilder(String.valueOf(strValueOf3).length() + 16).append("Couldn't create ").append(strValueOf3).toString(), e);
            }
            try {
                return new AtomicFileOutputStream(this.baseName);
            } catch (FileNotFoundException e2) {
                String strValueOf4 = String.valueOf(this.baseName);
                throw new IOException(new StringBuilder(String.valueOf(strValueOf4).length() + 16).append("Couldn't create ").append(strValueOf4).toString(), e2);
            }
        }
    }

    public void endWrite(OutputStream str) throws IOException {
        str.close();
        this.backupName.delete();
    }

    public InputStream openRead() throws FileNotFoundException {
        restoreBackup();
        return new FileInputStream(this.baseName);
    }

    private void restoreBackup() {
        if (this.backupName.exists()) {
            this.baseName.delete();
            this.backupName.renameTo(this.baseName);
        }
    }

    private static final class AtomicFileOutputStream extends OutputStream {
        private boolean closed = false;
        private final FileOutputStream fileOutputStream;

        public AtomicFileOutputStream(File file) throws FileNotFoundException {
            this.fileOutputStream = new FileOutputStream(file);
        }

        @Override // java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
        public void close() throws IOException {
            if (this.closed) {
                return;
            }
            this.closed = true;
            flush();
            try {
                this.fileOutputStream.getFD().sync();
            } catch (IOException e) {
                Log.w(AtomicFile.TAG, "Failed to sync file descriptor:", e);
            }
            this.fileOutputStream.close();
        }

        @Override // java.io.OutputStream, java.io.Flushable
        public void flush() throws IOException {
            this.fileOutputStream.flush();
        }

        @Override // java.io.OutputStream
        public void write(int b) throws IOException {
            this.fileOutputStream.write(b);
        }

        @Override // java.io.OutputStream
        public void write(byte[] b) throws IOException {
            this.fileOutputStream.write(b);
        }

        @Override // java.io.OutputStream
        public void write(byte[] b, int off, int len) throws IOException {
            this.fileOutputStream.write(b, off, len);
        }
    }
}
