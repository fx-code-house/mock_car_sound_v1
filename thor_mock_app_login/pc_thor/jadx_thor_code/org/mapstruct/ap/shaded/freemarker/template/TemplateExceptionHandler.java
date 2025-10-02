package org.mapstruct.ap.shaded.freemarker.template;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import org.mapstruct.ap.shaded.freemarker.core.Environment;
import org.mapstruct.ap.shaded.freemarker.template.utility.StringUtil;

/* loaded from: classes3.dex */
public interface TemplateExceptionHandler {
    public static final TemplateExceptionHandler IGNORE_HANDLER = new TemplateExceptionHandler() { // from class: org.mapstruct.ap.shaded.freemarker.template.TemplateExceptionHandler.1
        @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateExceptionHandler
        public void handleTemplateException(TemplateException templateException, Environment environment, Writer writer) {
        }
    };
    public static final TemplateExceptionHandler RETHROW_HANDLER = new TemplateExceptionHandler() { // from class: org.mapstruct.ap.shaded.freemarker.template.TemplateExceptionHandler.2
        @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateExceptionHandler
        public void handleTemplateException(TemplateException templateException, Environment environment, Writer writer) throws TemplateException {
            throw templateException;
        }
    };
    public static final TemplateExceptionHandler DEBUG_HANDLER = new TemplateExceptionHandler() { // from class: org.mapstruct.ap.shaded.freemarker.template.TemplateExceptionHandler.3
        @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateExceptionHandler
        public void handleTemplateException(TemplateException templateException, Environment environment, Writer writer) throws TemplateException {
            if (!environment.isInAttemptBlock()) {
                PrintWriter printWriter = writer instanceof PrintWriter ? (PrintWriter) writer : new PrintWriter(writer);
                templateException.printStackTrace(printWriter);
                printWriter.flush();
                throw templateException;
            }
            throw templateException;
        }
    };
    public static final TemplateExceptionHandler HTML_DEBUG_HANDLER = new TemplateExceptionHandler() { // from class: org.mapstruct.ap.shaded.freemarker.template.TemplateExceptionHandler.4
        private static final String FONT_RESET_CSS = "color:#A80000; font-size:12px; font-style:normal; font-variant:normal; font-weight:normal; text-decoration:none; text-transform: none";

        @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateExceptionHandler
        public void handleTemplateException(TemplateException templateException, Environment environment, Writer writer) throws TemplateException {
            if (!environment.isInAttemptBlock()) {
                boolean z = writer instanceof PrintWriter;
                PrintWriter printWriter = z ? (PrintWriter) writer : new PrintWriter(writer);
                try {
                    printWriter.print("<!-- FREEMARKER ERROR MESSAGE STARTS HERE --><!-- ]]> --><script language=javascript>//\"></script><script language=javascript>//'></script><script language=javascript>//\"></script><script language=javascript>//'></script></title></xmp></script></noscript></style></object></head></pre></table></form></table></table></table></a></u></i></b><div align='left' style='background-color:#FFFF7C; display:block; border-top:double; padding:4px; margin:0; font-family:Arial,sans-serif; ");
                    printWriter.print(FONT_RESET_CSS);
                    printWriter.print("'><b style='font-size:12px; font-style:normal; font-weight:bold; text-decoration:none; text-transform: none;'>FreeMarker template error</b><pre style='display:block; background: none; border: 0; margin:0; padding: 0;font-family:monospace; ");
                    printWriter.print(FONT_RESET_CSS);
                    printWriter.println("; white-space: pre-wrap; white-space: -moz-pre-wrap; white-space: -pre-wrap; white-space: -o-pre-wrap; word-wrap: break-word;'>");
                    StringWriter stringWriter = new StringWriter();
                    PrintWriter printWriter2 = new PrintWriter(stringWriter);
                    templateException.printStackTrace(printWriter2, false, true, true);
                    printWriter2.close();
                    printWriter.println();
                    printWriter.println(StringUtil.XMLEncNQG(stringWriter.toString()));
                    printWriter.println("</pre></div></html>");
                    printWriter.flush();
                    if (!z) {
                        throw templateException;
                    }
                    throw templateException;
                } finally {
                    if (!z) {
                        printWriter.close();
                    }
                }
            }
            throw templateException;
        }
    };

    void handleTemplateException(TemplateException templateException, Environment environment, Writer writer) throws TemplateException;
}
