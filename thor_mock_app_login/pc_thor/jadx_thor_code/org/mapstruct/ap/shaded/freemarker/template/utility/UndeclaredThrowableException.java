package org.mapstruct.ap.shaded.freemarker.template.utility;

import java.io.PrintStream;
import java.io.PrintWriter;

/* loaded from: classes3.dex */
public class UndeclaredThrowableException extends RuntimeException {
    public UndeclaredThrowableException(Throwable th) {
        super(th);
    }

    @Override // java.lang.Throwable
    public void printStackTrace() {
        printStackTrace(System.err);
    }

    @Override // java.lang.Throwable
    public void printStackTrace(PrintStream printStream) {
        synchronized (printStream) {
            printStream.print("Undeclared throwable:");
            getCause().printStackTrace(printStream);
        }
    }

    @Override // java.lang.Throwable
    public void printStackTrace(PrintWriter printWriter) {
        synchronized (printWriter) {
            printWriter.print("Undeclared throwable:");
            getCause().printStackTrace(printWriter);
        }
    }

    public Throwable getUndeclaredThrowable() {
        return getCause();
    }
}
