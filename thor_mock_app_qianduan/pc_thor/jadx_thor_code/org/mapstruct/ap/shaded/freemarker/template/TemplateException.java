package org.mapstruct.ap.shaded.freemarker.template;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import org.mapstruct.ap.shaded.freemarker.core.Environment;
import org.mapstruct.ap.shaded.freemarker.core.Expression;
import org.mapstruct.ap.shaded.freemarker.core.TemplateElement;
import org.mapstruct.ap.shaded.freemarker.core.TemplateObject;
import org.mapstruct.ap.shaded.freemarker.core._CoreAPI;
import org.mapstruct.ap.shaded.freemarker.core._ErrorDescriptionBuilder;
import org.mapstruct.ap.shaded.freemarker.template.utility.CollectionUtils;

/* loaded from: classes3.dex */
public class TemplateException extends Exception {
    private static final String FTL_INSTRUCTION_STACK_TRACE_TITLE = "FTL stack trace (\"~\" means nesting-related):";
    private static final int FTL_STACK_TOP_FEW_MAX_LINES = 6;
    private final transient Expression blamedExpression;
    private String blamedExpressionString;
    private boolean blamedExpressionStringCalculated;
    private Integer columnNumber;
    private String description;
    private transient _ErrorDescriptionBuilder descriptionBuilder;
    private Integer endColumnNumber;
    private Integer endLineNumber;
    private final transient Environment env;
    private transient TemplateElement[] ftlInstructionStackSnapshot;
    private Integer lineNumber;
    private transient Object lock;
    private transient String message;
    private transient ThreadLocal messageWasAlreadyPrintedForThisTrace;
    private transient String messageWithoutStackTop;
    private boolean positionsCalculated;
    private String renderedFtlInstructionStackSnapshot;
    private String renderedFtlInstructionStackSnapshotTop;
    private String templateName;

    private interface StackTraceWriter {
        void print(Object obj);

        void printStandardStackTrace(Throwable th);

        void println();

        void println(Object obj);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public TemplateException(Environment environment) {
        this((String) null, (Exception) null, environment);
    }

    public TemplateException(String str, Environment environment) {
        this(str, (Exception) null, environment);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public TemplateException(Exception exc, Environment environment) {
        this((String) null, exc, environment);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public TemplateException(Throwable th, Environment environment) {
        this((String) null, th, environment);
    }

    public TemplateException(String str, Exception exc, Environment environment) {
        this(str, exc, environment, null, null);
    }

    public TemplateException(String str, Throwable th, Environment environment) {
        this(str, th, environment, null, null);
    }

    protected TemplateException(Throwable th, Environment environment, Expression expression, _ErrorDescriptionBuilder _errordescriptionbuilder) {
        this(null, th, environment, expression, _errordescriptionbuilder);
    }

    private TemplateException(String str, Throwable th, Environment environment, Expression expression, _ErrorDescriptionBuilder _errordescriptionbuilder) {
        super(th);
        this.lock = new Object();
        environment = environment == null ? Environment.getCurrentEnvironment() : environment;
        this.env = environment;
        this.blamedExpression = expression;
        this.descriptionBuilder = _errordescriptionbuilder;
        this.description = str;
        if (environment != null) {
            this.ftlInstructionStackSnapshot = _CoreAPI.getInstructionStackSnapshot(environment);
        }
    }

    private void renderMessages() {
        String description = getDescription();
        if (description != null && description.length() != 0) {
            this.messageWithoutStackTop = description;
        } else if (getCause() != null) {
            this.messageWithoutStackTop = new StringBuffer("No error description was specified for this error; low-level message: ").append(getCause().getClass().getName()).append(": ").append(getCause().getMessage()).toString();
        } else {
            this.messageWithoutStackTop = "[No error description was available.]";
        }
        String fTLInstructionStackTopFew = getFTLInstructionStackTopFew();
        if (fTLInstructionStackTopFew != null) {
            String string = new StringBuffer().append(this.messageWithoutStackTop).append("\n\n----\nFTL stack trace (\"~\" means nesting-related):\n").append(fTLInstructionStackTopFew).append("----").toString();
            this.message = string;
            this.messageWithoutStackTop = string.substring(0, this.messageWithoutStackTop.length());
            return;
        }
        this.message = this.messageWithoutStackTop;
    }

    private void calculatePosition() {
        synchronized (this.lock) {
            if (!this.positionsCalculated) {
                TemplateObject templateObject = this.blamedExpression;
                if (templateObject == null) {
                    TemplateObject[] templateObjectArr = this.ftlInstructionStackSnapshot;
                    templateObject = (templateObjectArr == null || templateObjectArr.length == 0) ? null : templateObjectArr[0];
                }
                if (templateObject != null && templateObject.getBeginLine() > 0) {
                    Template template = templateObject.getTemplate();
                    this.templateName = template != null ? template.getName() : null;
                    this.lineNumber = new Integer(templateObject.getBeginLine());
                    this.columnNumber = new Integer(templateObject.getBeginColumn());
                    this.endLineNumber = new Integer(templateObject.getEndLine());
                    this.endColumnNumber = new Integer(templateObject.getEndColumn());
                }
                this.positionsCalculated = true;
                deleteFTLInstructionStackSnapshotIfNotNeeded();
            }
        }
    }

    public Exception getCauseException() {
        if (getCause() instanceof Exception) {
            return (Exception) getCause();
        }
        return new Exception(new StringBuffer("Wrapped to Exception: ").append(getCause()).toString(), getCause());
    }

    public String getFTLInstructionStack() {
        synchronized (this.lock) {
            if (this.ftlInstructionStackSnapshot == null && this.renderedFtlInstructionStackSnapshot == null) {
                return null;
            }
            if (this.renderedFtlInstructionStackSnapshot == null) {
                StringWriter stringWriter = new StringWriter();
                PrintWriter printWriter = new PrintWriter(stringWriter);
                _CoreAPI.outputInstructionStack(this.ftlInstructionStackSnapshot, false, printWriter);
                printWriter.close();
                if (this.renderedFtlInstructionStackSnapshot == null) {
                    this.renderedFtlInstructionStackSnapshot = stringWriter.toString();
                    deleteFTLInstructionStackSnapshotIfNotNeeded();
                }
            }
            return this.renderedFtlInstructionStackSnapshot;
        }
    }

    private String getFTLInstructionStackTopFew() {
        String string;
        synchronized (this.lock) {
            TemplateElement[] templateElementArr = this.ftlInstructionStackSnapshot;
            if (templateElementArr == null && this.renderedFtlInstructionStackSnapshotTop == null) {
                return null;
            }
            if (this.renderedFtlInstructionStackSnapshotTop == null) {
                if (templateElementArr.length == 0) {
                    string = "";
                } else {
                    StringWriter stringWriter = new StringWriter();
                    _CoreAPI.outputInstructionStack(this.ftlInstructionStackSnapshot, true, stringWriter);
                    string = stringWriter.toString();
                }
                if (this.renderedFtlInstructionStackSnapshotTop == null) {
                    this.renderedFtlInstructionStackSnapshotTop = string;
                    deleteFTLInstructionStackSnapshotIfNotNeeded();
                }
            }
            return this.renderedFtlInstructionStackSnapshotTop.length() != 0 ? this.renderedFtlInstructionStackSnapshotTop : null;
        }
    }

    private void deleteFTLInstructionStackSnapshotIfNotNeeded() {
        if (this.renderedFtlInstructionStackSnapshot == null || this.renderedFtlInstructionStackSnapshotTop == null) {
            return;
        }
        if (this.positionsCalculated || this.blamedExpression != null) {
            this.ftlInstructionStackSnapshot = null;
        }
    }

    private String getDescription() {
        String str;
        _ErrorDescriptionBuilder _errordescriptionbuilder;
        synchronized (this.lock) {
            if (this.description == null && (_errordescriptionbuilder = this.descriptionBuilder) != null) {
                TemplateElement failingInstruction = getFailingInstruction();
                Environment environment = this.env;
                this.description = _errordescriptionbuilder.toString(failingInstruction, environment != null ? environment.getShowErrorTips() : true);
                this.descriptionBuilder = null;
            }
            str = this.description;
        }
        return str;
    }

    private TemplateElement getFailingInstruction() {
        TemplateElement[] templateElementArr = this.ftlInstructionStackSnapshot;
        if (templateElementArr == null || templateElementArr.length <= 0) {
            return null;
        }
        return templateElementArr[0];
    }

    public Environment getEnvironment() {
        return this.env;
    }

    @Override // java.lang.Throwable
    public void printStackTrace(PrintStream printStream) {
        printStackTrace(printStream, true, true, true);
    }

    @Override // java.lang.Throwable
    public void printStackTrace(PrintWriter printWriter) {
        printStackTrace(printWriter, true, true, true);
    }

    public void printStackTrace(PrintWriter printWriter, boolean z, boolean z2, boolean z3) {
        synchronized (printWriter) {
            printStackTrace(new PrintWriterStackTraceWriter(printWriter), z, z2, z3);
        }
    }

    public void printStackTrace(PrintStream printStream, boolean z, boolean z2, boolean z3) {
        synchronized (printStream) {
            printStackTrace(new PrintStreamStackTraceWriter(printStream), z, z2, z3);
        }
    }

    private void printStackTrace(StackTraceWriter stackTraceWriter, boolean z, boolean z2, boolean z3) {
        synchronized (stackTraceWriter) {
            if (z) {
                try {
                    stackTraceWriter.println("FreeMarker template error:");
                } catch (Throwable th) {
                    throw th;
                }
            }
            if (z2) {
                String fTLInstructionStack = getFTLInstructionStack();
                if (fTLInstructionStack != null) {
                    stackTraceWriter.println(getMessageWithoutStackTop());
                    stackTraceWriter.println();
                    stackTraceWriter.println("----");
                    stackTraceWriter.println(FTL_INSTRUCTION_STACK_TRACE_TITLE);
                    stackTraceWriter.print(fTLInstructionStack);
                    stackTraceWriter.println("----");
                } else {
                    z2 = false;
                    z3 = true;
                }
            }
            if (z3) {
                if (z2) {
                    stackTraceWriter.println();
                    stackTraceWriter.println("Java stack trace (for programmers):");
                    stackTraceWriter.println("----");
                    synchronized (this.lock) {
                        if (this.messageWasAlreadyPrintedForThisTrace == null) {
                            this.messageWasAlreadyPrintedForThisTrace = new ThreadLocal();
                        }
                        this.messageWasAlreadyPrintedForThisTrace.set(Boolean.TRUE);
                    }
                    try {
                        stackTraceWriter.printStandardStackTrace(this);
                        this.messageWasAlreadyPrintedForThisTrace.set(Boolean.FALSE);
                    } catch (Throwable th2) {
                        this.messageWasAlreadyPrintedForThisTrace.set(Boolean.FALSE);
                        throw th2;
                    }
                } else {
                    stackTraceWriter.printStandardStackTrace(this);
                }
                if (getCause() != null && getCause().getCause() == null) {
                    try {
                        Throwable th3 = (Throwable) getCause().getClass().getMethod("getRootCause", CollectionUtils.EMPTY_CLASS_ARRAY).invoke(getCause(), CollectionUtils.EMPTY_OBJECT_ARRAY);
                        if (th3 != null) {
                            stackTraceWriter.println("ServletException root cause: ");
                            stackTraceWriter.printStandardStackTrace(th3);
                        }
                    } catch (Throwable unused) {
                    }
                }
            }
        }
    }

    public void printStandardStackTrace(PrintStream printStream) {
        super.printStackTrace(printStream);
    }

    public void printStandardStackTrace(PrintWriter printWriter) {
        super.printStackTrace(printWriter);
    }

    @Override // java.lang.Throwable
    public String getMessage() {
        String str;
        ThreadLocal threadLocal = this.messageWasAlreadyPrintedForThisTrace;
        if (threadLocal != null && threadLocal.get() == Boolean.TRUE) {
            return "[... Exception message was already printed; see it above ...]";
        }
        synchronized (this.lock) {
            if (this.message == null) {
                renderMessages();
            }
            str = this.message;
        }
        return str;
    }

    public String getMessageWithoutStackTop() {
        String str;
        synchronized (this.lock) {
            if (this.messageWithoutStackTop == null) {
                renderMessages();
            }
            str = this.messageWithoutStackTop;
        }
        return str;
    }

    public Integer getLineNumber() {
        Integer num;
        synchronized (this.lock) {
            if (!this.positionsCalculated) {
                calculatePosition();
            }
            num = this.lineNumber;
        }
        return num;
    }

    public String getTemplateName() {
        String str;
        synchronized (this.lock) {
            if (!this.positionsCalculated) {
                calculatePosition();
            }
            str = this.templateName;
        }
        return str;
    }

    public Integer getColumnNumber() {
        Integer num;
        synchronized (this.lock) {
            if (!this.positionsCalculated) {
                calculatePosition();
            }
            num = this.columnNumber;
        }
        return num;
    }

    public Integer getEndLineNumber() {
        Integer num;
        synchronized (this.lock) {
            if (!this.positionsCalculated) {
                calculatePosition();
            }
            num = this.endLineNumber;
        }
        return num;
    }

    public Integer getEndColumnNumber() {
        Integer num;
        synchronized (this.lock) {
            if (!this.positionsCalculated) {
                calculatePosition();
            }
            num = this.endColumnNumber;
        }
        return num;
    }

    public String getBlamedExpressionString() {
        String str;
        synchronized (this.lock) {
            if (!this.blamedExpressionStringCalculated) {
                Expression expression = this.blamedExpression;
                if (expression != null) {
                    this.blamedExpressionString = expression.getCanonicalForm();
                }
                this.blamedExpressionStringCalculated = true;
            }
            str = this.blamedExpressionString;
        }
        return str;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException, ClassNotFoundException {
        getFTLInstructionStack();
        getFTLInstructionStackTopFew();
        getDescription();
        calculatePosition();
        getBlamedExpressionString();
        objectOutputStream.defaultWriteObject();
    }

    private void readObject(ObjectInputStream objectInputStream) throws ClassNotFoundException, IOException {
        this.lock = new Object();
        objectInputStream.defaultReadObject();
    }

    private static class PrintStreamStackTraceWriter implements StackTraceWriter {
        private final PrintStream out;

        PrintStreamStackTraceWriter(PrintStream printStream) {
            this.out = printStream;
        }

        @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateException.StackTraceWriter
        public void print(Object obj) {
            this.out.print(obj);
        }

        @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateException.StackTraceWriter
        public void println(Object obj) {
            this.out.println(obj);
        }

        @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateException.StackTraceWriter
        public void println() {
            this.out.println();
        }

        @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateException.StackTraceWriter
        public void printStandardStackTrace(Throwable th) {
            if (th instanceof TemplateException) {
                ((TemplateException) th).printStandardStackTrace(this.out);
            } else {
                th.printStackTrace(this.out);
            }
        }
    }

    private static class PrintWriterStackTraceWriter implements StackTraceWriter {
        private final PrintWriter out;

        PrintWriterStackTraceWriter(PrintWriter printWriter) {
            this.out = printWriter;
        }

        @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateException.StackTraceWriter
        public void print(Object obj) {
            this.out.print(obj);
        }

        @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateException.StackTraceWriter
        public void println(Object obj) {
            this.out.println(obj);
        }

        @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateException.StackTraceWriter
        public void println() {
            this.out.println();
        }

        @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateException.StackTraceWriter
        public void printStandardStackTrace(Throwable th) {
            if (th instanceof TemplateException) {
                ((TemplateException) th).printStandardStackTrace(this.out);
            } else {
                th.printStackTrace(this.out);
            }
        }
    }
}
