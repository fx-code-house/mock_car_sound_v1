package org.mapstruct.ap.shaded.freemarker.core;

import java.io.PrintStream;
import java.io.PrintWriter;
import org.mapstruct.ap.shaded.freemarker.template.TemplateException;

/* loaded from: classes3.dex */
public class StopException extends TemplateException {
    StopException(Environment environment) {
        super(environment);
    }

    StopException(Environment environment, String str) {
        super(str, environment);
    }

    @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateException, java.lang.Throwable
    public void printStackTrace(PrintWriter printWriter) {
        synchronized (printWriter) {
            String message = getMessage();
            printWriter.print("Encountered stop instruction");
            if (message != null && !message.equals("")) {
                printWriter.println(new StringBuffer("\nCause given: ").append(message).toString());
            } else {
                printWriter.println();
            }
            super.printStackTrace(printWriter);
        }
    }

    @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateException, java.lang.Throwable
    public void printStackTrace(PrintStream printStream) {
        synchronized (printStream) {
            String message = getMessage();
            printStream.print("Encountered stop instruction");
            if (message != null && !message.equals("")) {
                printStream.println(new StringBuffer("\nCause given: ").append(message).toString());
            } else {
                printStream.println();
            }
            super.printStackTrace(printStream);
        }
    }
}
