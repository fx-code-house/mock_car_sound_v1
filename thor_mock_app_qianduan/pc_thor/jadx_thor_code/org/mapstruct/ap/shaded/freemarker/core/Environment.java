package org.mapstruct.ap.shaded.freemarker.core;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.Collator;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import org.mapstruct.ap.shaded.freemarker.core.BodyInstruction;
import org.mapstruct.ap.shaded.freemarker.core.BreakInstruction;
import org.mapstruct.ap.shaded.freemarker.core.IteratorBlock;
import org.mapstruct.ap.shaded.freemarker.core.Macro;
import org.mapstruct.ap.shaded.freemarker.core.Macro.Context;
import org.mapstruct.ap.shaded.freemarker.core.ReturnInstruction;
import org.mapstruct.ap.shaded.freemarker.ext.beans.BeansWrapper;
import org.mapstruct.ap.shaded.freemarker.log.Logger;
import org.mapstruct.ap.shaded.freemarker.template.Configuration;
import org.mapstruct.ap.shaded.freemarker.template.SimpleHash;
import org.mapstruct.ap.shaded.freemarker.template.SimpleSequence;
import org.mapstruct.ap.shaded.freemarker.template.Template;
import org.mapstruct.ap.shaded.freemarker.template.TemplateCollectionModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateDateModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateDirectiveBody;
import org.mapstruct.ap.shaded.freemarker.template.TemplateDirectiveModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateException;
import org.mapstruct.ap.shaded.freemarker.template.TemplateExceptionHandler;
import org.mapstruct.ap.shaded.freemarker.template.TemplateHashModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateHashModelEx;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModelException;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModelIterator;
import org.mapstruct.ap.shaded.freemarker.template.TemplateNodeModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateScalarModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateSequenceModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateTransformModel;
import org.mapstruct.ap.shaded.freemarker.template.utility.DateUtil;
import org.mapstruct.ap.shaded.freemarker.template.utility.NullWriter;
import org.mapstruct.ap.shaded.freemarker.template.utility.StringUtil;

/* loaded from: classes3.dex */
public final class Environment extends Configurable {
    private static final int CACHED_TDFS_DEF_SYS_TZ_OFFS = 8;
    private static final int CACHED_TDFS_LENGTH = 16;
    private static final int CACHED_TDFS_SQL_D_T_TZ_OFFS = 8;
    private static final int CACHED_TDFS_ZONELESS_INPUT_OFFS = 4;
    private static final DecimalFormat C_NUMBER_FORMAT;
    private static final Writer EMPTY_BODY_WRITER;
    private static final TemplateModel[] NO_OUT_ARGS;
    private static final int TERSE_MODE_INSTRUCTION_STACK_TRACE_LIMIT = 10;
    static /* synthetic */ Class class$java$sql$Date;
    static /* synthetic */ Class class$java$sql$Time;
    static /* synthetic */ Class class$java$sql$Timestamp;
    static /* synthetic */ Class class$java$util$Date;
    private NumberFormat cNumberFormat;
    private Collator cachedCollator;
    private ISOTemplateDateFormatFactory cachedISOTemplateDateFormatFactory;
    private JavaTemplateDateFormatFactory cachedJavaTemplateDateFormatFactory;
    private NumberFormat cachedNumberFormat;
    private Map cachedNumberFormats;
    private ISOTemplateDateFormatFactory cachedSQLDTISOTemplateDateFormatFactory;
    private JavaTemplateDateFormatFactory cachedSQLDTJavaTemplateDateFormatFactory;
    private XSTemplateDateFormatFactory cachedSQLDTXSTemplateDateFormatFactory;
    private Boolean cachedSQLDateAndTimeTimeZoneSameAsNormal;
    private TemplateDateFormat[] cachedTemplateDateFormats;
    private String cachedURLEscapingCharset;
    private boolean cachedURLEscapingCharsetSet;
    private XSTemplateDateFormatFactory cachedXSTemplateDateFormatFactory;
    private Macro.Context currentMacroContext;
    private Namespace currentNamespace;
    private String currentNodeNS;
    private String currentNodeName;
    private TemplateNodeModel currentVisitorNode;
    private boolean fastInvalidReferenceExceptions;
    private Namespace globalNamespace;
    private boolean inAttemptBlock;
    private final ArrayList instructionStack;
    private DateUtil.DateToISO8601CalendarFactory isoBuiltInCalendarFactory;
    private TemplateModel lastReturnValue;
    private Throwable lastThrowable;
    private HashMap loadedLibs;
    private ArrayList localContextStack;
    private HashMap macroToNamespaceLookup;
    private Namespace mainNamespace;
    private int nodeNamespaceIndex;
    private TemplateSequenceModel nodeNamespaces;
    private Writer out;
    private final ArrayList recoveredErrorStack;
    private final TemplateHashModel rootDataModel;
    private static final ThreadLocal threadEnv = new ThreadLocal();
    private static final Logger LOGGER = Logger.getLogger("org.mapstruct.ap.shaded.freemarker.runtime");
    private static final Logger ATTEMPT_LOGGER = Logger.getLogger("org.mapstruct.ap.shaded.freemarker.runtime.attempt");
    private static final Map JAVA_NUMBER_FORMATS = new HashMap();

    private int getCachedTemplateDateFormatIndex(int i, boolean z, boolean z2) {
        return i + (z ? 4 : 0) + (z2 ? 8 : 0);
    }

    static {
        DecimalFormat decimalFormat = new DecimalFormat("0.################", new DecimalFormatSymbols(Locale.US));
        C_NUMBER_FORMAT = decimalFormat;
        decimalFormat.setGroupingUsed(false);
        decimalFormat.setDecimalSeparatorAlwaysShown(false);
        NO_OUT_ARGS = new TemplateModel[0];
        EMPTY_BODY_WRITER = new Writer() { // from class: org.mapstruct.ap.shaded.freemarker.core.Environment.6
            @Override // java.io.Writer, java.io.Closeable, java.lang.AutoCloseable
            public void close() {
            }

            @Override // java.io.Writer, java.io.Flushable
            public void flush() {
            }

            @Override // java.io.Writer
            public void write(char[] cArr, int i, int i2) throws IOException {
                if (i2 > 0) {
                    throw new IOException("This transform does not allow nested content.");
                }
            }
        };
    }

    public static Environment getCurrentEnvironment() {
        return (Environment) threadEnv.get();
    }

    public Environment(Template template, TemplateHashModel templateHashModel, Writer writer) {
        super(template);
        this.instructionStack = new ArrayList();
        this.recoveredErrorStack = new ArrayList();
        this.macroToNamespaceLookup = new HashMap();
        this.globalNamespace = new Namespace(null);
        Namespace namespace = new Namespace(template);
        this.mainNamespace = namespace;
        this.currentNamespace = namespace;
        this.out = writer;
        this.rootDataModel = templateHashModel;
        importMacros(template);
    }

    public Template getTemplate() {
        return (Template) getParent();
    }

    private void clearCachedValues() {
        this.cachedNumberFormats = null;
        this.cachedNumberFormat = null;
        this.cachedTemplateDateFormats = null;
        this.cachedSQLDTXSTemplateDateFormatFactory = null;
        this.cachedXSTemplateDateFormatFactory = null;
        this.cachedSQLDTISOTemplateDateFormatFactory = null;
        this.cachedISOTemplateDateFormatFactory = null;
        this.cachedSQLDTJavaTemplateDateFormatFactory = null;
        this.cachedJavaTemplateDateFormatFactory = null;
        this.cachedCollator = null;
        this.cachedURLEscapingCharset = null;
        this.cachedURLEscapingCharsetSet = false;
    }

    public void process() throws TemplateException, IOException {
        ThreadLocal threadLocal = threadEnv;
        Object obj = threadLocal.get();
        threadLocal.set(this);
        try {
            try {
                doAutoImportsAndIncludes(this);
                visit(getTemplate().getRootTreeNode());
                if (getAutoFlush()) {
                    this.out.flush();
                }
                threadLocal.set(obj);
            } finally {
                clearCachedValues();
            }
        } catch (Throwable th) {
            threadEnv.set(obj);
            throw th;
        }
    }

    void visit(TemplateElement templateElement) throws TemplateException, IOException {
        pushElement(templateElement);
        try {
            try {
                templateElement.accept(this);
            } catch (TemplateException e) {
                handleTemplateException(e);
            }
        } finally {
            popElement();
        }
    }

    void visitByHiddingParent(TemplateElement templateElement) throws TemplateException, IOException {
        TemplateElement templateElementReplaceTopElement = replaceTopElement(templateElement);
        try {
            try {
                templateElement.accept(this);
            } catch (TemplateException e) {
                handleTemplateException(e);
            }
        } finally {
            replaceTopElement(templateElementReplaceTopElement);
        }
    }

    private TemplateElement replaceTopElement(TemplateElement templateElement) {
        return (TemplateElement) this.instructionStack.set(r0.size() - 1, templateElement);
    }

    public void visit(final TemplateElement templateElement, TemplateDirectiveModel templateDirectiveModel, Map map, final List list) throws TemplateException, IOException {
        final TemplateModel[] templateModelArr;
        TemplateDirectiveBody templateDirectiveBody = templateElement == null ? null : new TemplateDirectiveBody() { // from class: org.mapstruct.ap.shaded.freemarker.core.Environment.1
            @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateDirectiveBody
            public void render(Writer writer) throws TemplateException, IOException {
                Writer writer2 = Environment.this.out;
                Environment.this.out = writer;
                try {
                    Environment.this.visit(templateElement);
                } finally {
                    Environment.this.out = writer2;
                }
            }
        };
        if (list == null || list.isEmpty()) {
            templateModelArr = NO_OUT_ARGS;
        } else {
            templateModelArr = new TemplateModel[list.size()];
        }
        if (templateModelArr.length > 0) {
            pushLocalContext(new LocalContext() { // from class: org.mapstruct.ap.shaded.freemarker.core.Environment.2
                @Override // org.mapstruct.ap.shaded.freemarker.core.LocalContext
                public TemplateModel getLocalVariable(String str) {
                    int iIndexOf = list.indexOf(str);
                    if (iIndexOf != -1) {
                        return templateModelArr[iIndexOf];
                    }
                    return null;
                }

                @Override // org.mapstruct.ap.shaded.freemarker.core.LocalContext
                public Collection getLocalVariableNames() {
                    return list;
                }
            });
        }
        try {
            templateDirectiveModel.execute(this, map, templateModelArr, templateDirectiveBody);
        } finally {
            if (templateModelArr.length > 0) {
                popLocalContext();
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x0024 A[Catch: all -> 0x0020, TryCatch #0 {all -> 0x0020, blocks: (B:11:0x0019, B:17:0x0024, B:19:0x0029), top: B:47:0x0019, outer: #4 }] */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0029 A[Catch: all -> 0x0020, TRY_LEAVE, TryCatch #0 {all -> 0x0020, blocks: (B:11:0x0019, B:17:0x0024, B:19:0x0029), top: B:47:0x0019, outer: #4 }] */
    /* JADX WARN: Removed duplicated region for block: B:51:0x0052 A[EDGE_INSN: B:51:0x0052->B:42:0x0052 BREAK  A[LOOP:0: B:16:0x0022->B:54:?], SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    void visitAndTransform(org.mapstruct.ap.shaded.freemarker.core.TemplateElement r3, org.mapstruct.ap.shaded.freemarker.template.TemplateTransformModel r4, java.util.Map r5) throws java.io.IOException, org.mapstruct.ap.shaded.freemarker.template.TemplateException {
        /*
            r2 = this;
            java.io.Writer r0 = r2.out     // Catch: org.mapstruct.ap.shaded.freemarker.template.TemplateException -> L55
            java.io.Writer r4 = r4.getWriter(r0, r5)     // Catch: org.mapstruct.ap.shaded.freemarker.template.TemplateException -> L55
            if (r4 != 0) goto La
            java.io.Writer r4 = org.mapstruct.ap.shaded.freemarker.core.Environment.EMPTY_BODY_WRITER     // Catch: org.mapstruct.ap.shaded.freemarker.template.TemplateException -> L55
        La:
            boolean r5 = r4 instanceof org.mapstruct.ap.shaded.freemarker.template.TransformControl     // Catch: org.mapstruct.ap.shaded.freemarker.template.TemplateException -> L55
            if (r5 == 0) goto L12
            r5 = r4
            org.mapstruct.ap.shaded.freemarker.template.TransformControl r5 = (org.mapstruct.ap.shaded.freemarker.template.TransformControl) r5     // Catch: org.mapstruct.ap.shaded.freemarker.template.TemplateException -> L55
            goto L13
        L12:
            r5 = 0
        L13:
            java.io.Writer r0 = r2.out     // Catch: org.mapstruct.ap.shaded.freemarker.template.TemplateException -> L55
            r2.out = r4     // Catch: org.mapstruct.ap.shaded.freemarker.template.TemplateException -> L55
            if (r5 == 0) goto L22
            int r1 = r5.onStart()     // Catch: java.lang.Throwable -> L20
            if (r1 == 0) goto L52
            goto L22
        L20:
            r3 = move-exception
            goto L30
        L22:
            if (r3 == 0) goto L27
            r2.visitByHiddingParent(r3)     // Catch: java.lang.Throwable -> L20
        L27:
            if (r5 == 0) goto L52
            int r1 = r5.afterBody()     // Catch: java.lang.Throwable -> L20
            if (r1 == 0) goto L22
            goto L52
        L30:
            if (r5 == 0) goto L3b
            r5.onError(r3)     // Catch: java.lang.Throwable -> L3c java.lang.Error -> L43 java.lang.RuntimeException -> L45 java.io.IOException -> L47 org.mapstruct.ap.shaded.freemarker.template.TemplateException -> L49
            r2.out = r0     // Catch: org.mapstruct.ap.shaded.freemarker.template.TemplateException -> L55
        L37:
            r4.close()     // Catch: org.mapstruct.ap.shaded.freemarker.template.TemplateException -> L55
            goto L59
        L3b:
            throw r3     // Catch: java.lang.Throwable -> L3c java.lang.Error -> L43 java.lang.RuntimeException -> L45 java.io.IOException -> L47 org.mapstruct.ap.shaded.freemarker.template.TemplateException -> L49
        L3c:
            r3 = move-exception
            org.mapstruct.ap.shaded.freemarker.template.utility.UndeclaredThrowableException r5 = new org.mapstruct.ap.shaded.freemarker.template.utility.UndeclaredThrowableException     // Catch: java.lang.Throwable -> L4b
            r5.<init>(r3)     // Catch: java.lang.Throwable -> L4b
            throw r5     // Catch: java.lang.Throwable -> L4b
        L43:
            r3 = move-exception
            throw r3     // Catch: java.lang.Throwable -> L4b
        L45:
            r3 = move-exception
            throw r3     // Catch: java.lang.Throwable -> L4b
        L47:
            r3 = move-exception
            throw r3     // Catch: java.lang.Throwable -> L4b
        L49:
            r3 = move-exception
            throw r3     // Catch: java.lang.Throwable -> L4b
        L4b:
            r3 = move-exception
            r2.out = r0     // Catch: org.mapstruct.ap.shaded.freemarker.template.TemplateException -> L55
            r4.close()     // Catch: org.mapstruct.ap.shaded.freemarker.template.TemplateException -> L55
            throw r3     // Catch: org.mapstruct.ap.shaded.freemarker.template.TemplateException -> L55
        L52:
            r2.out = r0     // Catch: org.mapstruct.ap.shaded.freemarker.template.TemplateException -> L55
            goto L37
        L55:
            r3 = move-exception
            r2.handleTemplateException(r3)
        L59:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mapstruct.ap.shaded.freemarker.core.Environment.visitAndTransform(org.mapstruct.ap.shaded.freemarker.core.TemplateElement, org.mapstruct.ap.shaded.freemarker.template.TemplateTransformModel, java.util.Map):void");
    }

    void visitAttemptRecover(TemplateElement templateElement, RecoveryBlock recoveryBlock) throws IOException, TemplateException {
        TemplateException templateException;
        Writer writer = this.out;
        StringWriter stringWriter = new StringWriter();
        this.out = stringWriter;
        boolean fastInvalidReferenceExceptions = setFastInvalidReferenceExceptions(false);
        boolean z = this.inAttemptBlock;
        try {
            this.inAttemptBlock = true;
            visitByHiddingParent(templateElement);
            this.inAttemptBlock = z;
            setFastInvalidReferenceExceptions(fastInvalidReferenceExceptions);
            this.out = writer;
            templateException = null;
        } catch (TemplateException e) {
            this.inAttemptBlock = z;
            setFastInvalidReferenceExceptions(fastInvalidReferenceExceptions);
            this.out = writer;
            templateException = e;
        } catch (Throwable th) {
            this.inAttemptBlock = z;
            setFastInvalidReferenceExceptions(fastInvalidReferenceExceptions);
            this.out = writer;
            throw th;
        }
        if (templateException != null) {
            Logger logger = ATTEMPT_LOGGER;
            if (logger.isDebugEnabled()) {
                logger.debug(new StringBuffer("Error in attempt block ").append(templateElement.getStartLocationQuoted()).toString(), templateException);
            }
            try {
                this.recoveredErrorStack.add(templateException);
                visit(recoveryBlock);
                return;
            } finally {
                ArrayList arrayList = this.recoveredErrorStack;
                arrayList.remove(arrayList.size() - 1);
            }
        }
        this.out.write(stringWriter.toString());
    }

    String getCurrentRecoveredErrorMessage() throws TemplateException {
        if (this.recoveredErrorStack.isEmpty()) {
            throw new _MiscTemplateException(this, ".error is not available outside of a #recover block");
        }
        return ((Throwable) this.recoveredErrorStack.get(r0.size() - 1)).getMessage();
    }

    public boolean isInAttemptBlock() {
        return this.inAttemptBlock;
    }

    void visit(BodyInstruction.Context context) throws TemplateException, IOException {
        Macro.Context currentMacroContext = getCurrentMacroContext();
        ArrayList arrayList = this.localContextStack;
        TemplateElement templateElement = currentMacroContext.body;
        if (templateElement != null) {
            this.currentMacroContext = currentMacroContext.prevMacroContext;
            this.currentNamespace = currentMacroContext.bodyNamespace;
            Configurable parent = getParent();
            setParent(this.currentNamespace.getTemplate());
            this.localContextStack = currentMacroContext.prevLocalContextStack;
            if (currentMacroContext.bodyParameterNames != null) {
                pushLocalContext(context);
            }
            try {
                visit(templateElement);
            } finally {
                if (currentMacroContext.bodyParameterNames != null) {
                    popLocalContext();
                }
                this.currentMacroContext = currentMacroContext;
                this.currentNamespace = getMacroNamespace(currentMacroContext.getMacro());
                setParent(parent);
                this.localContextStack = arrayList;
            }
        }
    }

    void visitIteratorBlock(IteratorBlock.Context context) throws TemplateException, IOException {
        pushLocalContext(context);
        try {
            try {
                context.runLoop(this);
            } catch (BreakInstruction.Break unused) {
            } catch (TemplateException e) {
                handleTemplateException(e);
            }
        } finally {
            popLocalContext();
        }
    }

    void visit(TemplateNodeModel templateNodeModel, TemplateSequenceModel templateSequenceModel) throws TemplateException, IOException {
        if (this.nodeNamespaces == null) {
            SimpleSequence simpleSequence = new SimpleSequence(1);
            simpleSequence.add(this.currentNamespace);
            this.nodeNamespaces = simpleSequence;
        }
        int i = this.nodeNamespaceIndex;
        String str = this.currentNodeName;
        String str2 = this.currentNodeNS;
        TemplateSequenceModel templateSequenceModel2 = this.nodeNamespaces;
        TemplateNodeModel templateNodeModel2 = this.currentVisitorNode;
        this.currentVisitorNode = templateNodeModel;
        if (templateSequenceModel != null) {
            this.nodeNamespaces = templateSequenceModel;
        }
        try {
            TemplateModel nodeProcessor = getNodeProcessor(templateNodeModel);
            if (nodeProcessor instanceof Macro) {
                visit((Macro) nodeProcessor, null, null, null, null);
            } else if (nodeProcessor instanceof TemplateTransformModel) {
                visitAndTransform(null, (TemplateTransformModel) nodeProcessor, null);
            } else {
                String nodeType = templateNodeModel.getNodeType();
                if (nodeType != null) {
                    if (nodeType.equals("text") && (templateNodeModel instanceof TemplateScalarModel)) {
                        this.out.write(((TemplateScalarModel) templateNodeModel).getAsString());
                    } else if (nodeType.equals("document")) {
                        recurse(templateNodeModel, templateSequenceModel);
                    } else if (!nodeType.equals("pi") && !nodeType.equals("comment") && !nodeType.equals("document_type")) {
                        throw new _MiscTemplateException(this, noNodeHandlerDefinedDescription(templateNodeModel, templateNodeModel.getNodeNamespace(), nodeType));
                    }
                } else {
                    throw new _MiscTemplateException(this, noNodeHandlerDefinedDescription(templateNodeModel, templateNodeModel.getNodeNamespace(), "default"));
                }
            }
        } finally {
            this.currentVisitorNode = templateNodeModel2;
            this.nodeNamespaceIndex = i;
            this.currentNodeName = str;
            this.currentNodeNS = str2;
            this.nodeNamespaces = templateSequenceModel2;
        }
    }

    private Object[] noNodeHandlerDefinedDescription(TemplateNodeModel templateNodeModel, String str, String str2) throws TemplateModelException {
        String str3;
        if (str != null) {
            str3 = str.length() > 0 ? " and namespace " : " and no namespace";
        } else {
            str = "";
            str3 = "";
        }
        return new Object[]{"No macro or directive is defined for node named ", new _DelayedJQuote(templateNodeModel.getNodeName()), str3, str, ", and there is no fallback handler called @", str2, " either."};
    }

    void fallback() throws TemplateException, IOException {
        TemplateModel nodeProcessor = getNodeProcessor(this.currentNodeName, this.currentNodeNS, this.nodeNamespaceIndex);
        if (nodeProcessor instanceof Macro) {
            visit((Macro) nodeProcessor, null, null, null, null);
        } else if (nodeProcessor instanceof TemplateTransformModel) {
            visitAndTransform(null, (TemplateTransformModel) nodeProcessor, null);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r11v1 */
    /* JADX WARN: Type inference failed for: r11v11 */
    /* JADX WARN: Type inference failed for: r11v2 */
    /* JADX WARN: Type inference failed for: r11v4, types: [org.mapstruct.ap.shaded.freemarker.template.TemplateModel] */
    /* JADX WARN: Type inference failed for: r11v6 */
    /* JADX WARN: Type inference failed for: r11v7, types: [org.mapstruct.ap.shaded.freemarker.template.SimpleHash] */
    /* JADX WARN: Type inference failed for: r1v0, types: [org.mapstruct.ap.shaded.freemarker.core.Macro$Context] */
    void visit(Macro macro, Map map, List list, List list2, TemplateElement templateElement) throws TemplateException, IOException {
        SimpleHash simpleHash;
        if (macro == Macro.DO_NOTHING_MACRO) {
            return;
        }
        pushElement(macro);
        try {
            Macro.Context context = this.currentMacroContext;
            macro.getClass();
            ?? context2 = macro.new Context(this, templateElement, list2);
            String catchAll = macro.getCatchAll();
            if (map != null) {
                simpleHash = catchAll != null ? new SimpleHash() : 0;
                for (Map.Entry entry : map.entrySet()) {
                    String str = (String) entry.getKey();
                    boolean zHasArgNamed = macro.hasArgNamed(str);
                    if (!zHasArgNamed && catchAll == null) {
                        throw new _MiscTemplateException(this, new Object[]{"Macro ", new _DelayedJQuote(macro.getName()), " has no such argument: ", str});
                    }
                    TemplateModel templateModelEval = ((Expression) entry.getValue()).eval(this);
                    if (zHasArgNamed) {
                        context2.setLocalVar(str, templateModelEval);
                    } else {
                        simpleHash.put(str, templateModelEval);
                    }
                }
            } else if (list != null) {
                SimpleSequence simpleSequence = catchAll != null ? new SimpleSequence() : null;
                String[] argumentNamesInternal = macro.getArgumentNamesInternal();
                int size = list.size();
                if (argumentNamesInternal.length < size && catchAll == null) {
                    throw new _MiscTemplateException(this, new Object[]{new StringBuffer("Macro ").append(StringUtil.jQuote(macro.getName())).append(" only accepts ").append(argumentNamesInternal.length).append(" parameters.").toString()});
                }
                for (int i = 0; i < size; i++) {
                    TemplateModel templateModelEval2 = ((Expression) list.get(i)).eval(this);
                    try {
                        if (i < argumentNamesInternal.length) {
                            context2.setLocalVar(argumentNamesInternal[i], templateModelEval2);
                        } else {
                            simpleSequence.add(templateModelEval2);
                        }
                    } catch (RuntimeException e) {
                        throw new _MiscTemplateException(e, this);
                    }
                }
                simpleHash = simpleSequence;
            } else {
                simpleHash = 0;
            }
            if (catchAll != null) {
                context2.setLocalVar(catchAll, simpleHash);
            }
            ArrayList arrayList = this.localContextStack;
            this.localContextStack = null;
            Namespace namespace = this.currentNamespace;
            Configurable parent = getParent();
            this.currentNamespace = (Namespace) this.macroToNamespaceLookup.get(macro);
            this.currentMacroContext = context2;
            try {
                try {
                    context2.runMacro(this);
                    this.currentMacroContext = context;
                    this.localContextStack = arrayList;
                    this.currentNamespace = namespace;
                } catch (Throwable th) {
                    this.currentMacroContext = context;
                    this.localContextStack = arrayList;
                    this.currentNamespace = namespace;
                    setParent(parent);
                    throw th;
                }
            } catch (ReturnInstruction.Return unused) {
                this.currentMacroContext = context;
                this.localContextStack = arrayList;
                this.currentNamespace = namespace;
            } catch (TemplateException e2) {
                handleTemplateException(e2);
                this.currentMacroContext = context;
                this.localContextStack = arrayList;
                this.currentNamespace = namespace;
            }
            setParent(parent);
        } finally {
            popElement();
        }
    }

    void visitMacroDef(Macro macro) {
        this.macroToNamespaceLookup.put(macro, this.currentNamespace);
        this.currentNamespace.put(macro.getName(), macro);
    }

    Namespace getMacroNamespace(Macro macro) {
        return (Namespace) this.macroToNamespaceLookup.get(macro);
    }

    void recurse(TemplateNodeModel templateNodeModel, TemplateSequenceModel templateSequenceModel) throws TemplateException, IOException {
        if (templateNodeModel == null && (templateNodeModel = getCurrentVisitorNode()) == null) {
            throw new _TemplateModelException("The target node of recursion is missing or null.");
        }
        TemplateSequenceModel childNodes = templateNodeModel.getChildNodes();
        if (childNodes == null) {
            return;
        }
        for (int i = 0; i < childNodes.size(); i++) {
            TemplateNodeModel templateNodeModel2 = (TemplateNodeModel) childNodes.get(i);
            if (templateNodeModel2 != null) {
                visit(templateNodeModel2, templateSequenceModel);
            }
        }
    }

    Macro.Context getCurrentMacroContext() {
        return this.currentMacroContext;
    }

    private void handleTemplateException(TemplateException templateException) throws TemplateException {
        if (this.lastThrowable == templateException) {
            throw templateException;
        }
        this.lastThrowable = templateException;
        Logger logger = LOGGER;
        if (logger.isErrorEnabled()) {
            logger.error("Error executing FreeMarker template", templateException);
        }
        if (templateException instanceof StopException) {
            throw templateException;
        }
        getTemplateExceptionHandler().handleTemplateException(templateException, this, this.out);
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.Configurable
    public void setTemplateExceptionHandler(TemplateExceptionHandler templateExceptionHandler) {
        super.setTemplateExceptionHandler(templateExceptionHandler);
        this.lastThrowable = null;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.Configurable
    public void setLocale(Locale locale) {
        Locale locale2 = getLocale();
        super.setLocale(locale);
        if (locale.equals(locale2)) {
            return;
        }
        this.cachedNumberFormats = null;
        this.cachedNumberFormat = null;
        if (this.cachedTemplateDateFormats != null) {
            for (int i = 0; i < 16; i++) {
                TemplateDateFormat templateDateFormat = this.cachedTemplateDateFormats[i];
                if (templateDateFormat != null && templateDateFormat.isLocaleBound()) {
                    this.cachedTemplateDateFormats[i] = null;
                }
            }
        }
        XSTemplateDateFormatFactory xSTemplateDateFormatFactory = this.cachedXSTemplateDateFormatFactory;
        if (xSTemplateDateFormatFactory != null && xSTemplateDateFormatFactory.isLocaleBound()) {
            this.cachedXSTemplateDateFormatFactory = null;
        }
        XSTemplateDateFormatFactory xSTemplateDateFormatFactory2 = this.cachedSQLDTXSTemplateDateFormatFactory;
        if (xSTemplateDateFormatFactory2 != null && xSTemplateDateFormatFactory2.isLocaleBound()) {
            this.cachedSQLDTXSTemplateDateFormatFactory = null;
        }
        ISOTemplateDateFormatFactory iSOTemplateDateFormatFactory = this.cachedISOTemplateDateFormatFactory;
        if (iSOTemplateDateFormatFactory != null && iSOTemplateDateFormatFactory.isLocaleBound()) {
            this.cachedISOTemplateDateFormatFactory = null;
        }
        ISOTemplateDateFormatFactory iSOTemplateDateFormatFactory2 = this.cachedSQLDTISOTemplateDateFormatFactory;
        if (iSOTemplateDateFormatFactory2 != null && iSOTemplateDateFormatFactory2.isLocaleBound()) {
            this.cachedSQLDTISOTemplateDateFormatFactory = null;
        }
        JavaTemplateDateFormatFactory javaTemplateDateFormatFactory = this.cachedJavaTemplateDateFormatFactory;
        if (javaTemplateDateFormatFactory != null && javaTemplateDateFormatFactory.isLocaleBound()) {
            this.cachedJavaTemplateDateFormatFactory = null;
        }
        JavaTemplateDateFormatFactory javaTemplateDateFormatFactory2 = this.cachedSQLDTJavaTemplateDateFormatFactory;
        if (javaTemplateDateFormatFactory2 != null && javaTemplateDateFormatFactory2.isLocaleBound()) {
            this.cachedSQLDTJavaTemplateDateFormatFactory = null;
        }
        this.cachedCollator = null;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.Configurable
    public void setTimeZone(TimeZone timeZone) {
        TimeZone timeZone2 = getTimeZone();
        super.setTimeZone(timeZone);
        if (timeZone.equals(timeZone2)) {
            return;
        }
        if (this.cachedTemplateDateFormats != null) {
            for (int i = 0; i < 8; i++) {
                this.cachedTemplateDateFormats[i] = null;
            }
        }
        this.cachedXSTemplateDateFormatFactory = null;
        this.cachedISOTemplateDateFormatFactory = null;
        this.cachedJavaTemplateDateFormatFactory = null;
        this.cachedSQLDateAndTimeTimeZoneSameAsNormal = null;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.Configurable
    public void setSQLDateAndTimeTimeZone(TimeZone timeZone) {
        TimeZone sQLDateAndTimeTimeZone = getSQLDateAndTimeTimeZone();
        super.setSQLDateAndTimeTimeZone(timeZone);
        if (nullSafeEquals(timeZone, sQLDateAndTimeTimeZone)) {
            return;
        }
        if (this.cachedTemplateDateFormats != null) {
            for (int i = 8; i < 16; i++) {
                this.cachedTemplateDateFormats[i] = null;
            }
        }
        this.cachedSQLDTXSTemplateDateFormatFactory = null;
        this.cachedSQLDTISOTemplateDateFormatFactory = null;
        this.cachedSQLDTJavaTemplateDateFormatFactory = null;
        this.cachedSQLDateAndTimeTimeZoneSameAsNormal = null;
    }

    private static boolean nullSafeEquals(Object obj, Object obj2) {
        if (obj == obj2) {
            return true;
        }
        if (obj == null || obj2 == null) {
            return false;
        }
        return obj.equals(obj2);
    }

    boolean isSQLDateAndTimeTimeZoneSameAsNormal() {
        if (this.cachedSQLDateAndTimeTimeZoneSameAsNormal == null) {
            this.cachedSQLDateAndTimeTimeZoneSameAsNormal = Boolean.valueOf(getSQLDateAndTimeTimeZone() == null || getSQLDateAndTimeTimeZone().equals(getTimeZone()));
        }
        return this.cachedSQLDateAndTimeTimeZoneSameAsNormal.booleanValue();
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.Configurable
    public void setURLEscapingCharset(String str) {
        this.cachedURLEscapingCharsetSet = false;
        super.setURLEscapingCharset(str);
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.Configurable
    public void setOutputEncoding(String str) {
        this.cachedURLEscapingCharsetSet = false;
        super.setOutputEncoding(str);
    }

    String getEffectiveURLEscapingCharset() {
        if (!this.cachedURLEscapingCharsetSet) {
            String uRLEscapingCharset = getURLEscapingCharset();
            this.cachedURLEscapingCharset = uRLEscapingCharset;
            if (uRLEscapingCharset == null) {
                this.cachedURLEscapingCharset = getOutputEncoding();
            }
            this.cachedURLEscapingCharsetSet = true;
        }
        return this.cachedURLEscapingCharset;
    }

    Collator getCollator() {
        if (this.cachedCollator == null) {
            this.cachedCollator = Collator.getInstance(getLocale());
        }
        return this.cachedCollator;
    }

    public boolean applyEqualsOperator(TemplateModel templateModel, TemplateModel templateModel2) throws TemplateException {
        return EvalUtil.compare(templateModel, 1, templateModel2, this);
    }

    public boolean applyEqualsOperatorLenient(TemplateModel templateModel, TemplateModel templateModel2) throws TemplateException {
        return EvalUtil.compareLenient(templateModel, 1, templateModel2, this);
    }

    public boolean applyLessThanOperator(TemplateModel templateModel, TemplateModel templateModel2) throws TemplateException {
        return EvalUtil.compare(templateModel, 3, templateModel2, this);
    }

    public boolean applyLessThanOrEqualsOperator(TemplateModel templateModel, TemplateModel templateModel2) throws TemplateException {
        return EvalUtil.compare(templateModel, 5, templateModel2, this);
    }

    public boolean applyGreaterThanOperator(TemplateModel templateModel, TemplateModel templateModel2) throws TemplateException {
        return EvalUtil.compare(templateModel, 4, templateModel2, this);
    }

    public boolean applyWithGreaterThanOrEqualsOperator(TemplateModel templateModel, TemplateModel templateModel2) throws TemplateException {
        return EvalUtil.compare(templateModel, 6, templateModel2, this);
    }

    public void setOut(Writer writer) {
        this.out = writer;
    }

    public Writer getOut() {
        return this.out;
    }

    String formatNumber(Number number) {
        if (this.cachedNumberFormat == null) {
            this.cachedNumberFormat = getNumberFormatObject(getNumberFormat());
        }
        return this.cachedNumberFormat.format(number);
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.Configurable
    public void setNumberFormat(String str) {
        super.setNumberFormat(str);
        this.cachedNumberFormat = null;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.Configurable
    public void setTimeFormat(String str) {
        String timeFormat = getTimeFormat();
        super.setTimeFormat(str);
        if (str.equals(timeFormat) || this.cachedTemplateDateFormats == null) {
            return;
        }
        for (int i = 0; i < 16; i += 4) {
            this.cachedTemplateDateFormats[i + 1] = null;
        }
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.Configurable
    public void setDateFormat(String str) {
        String dateFormat = getDateFormat();
        super.setDateFormat(str);
        if (str.equals(dateFormat) || this.cachedTemplateDateFormats == null) {
            return;
        }
        for (int i = 0; i < 16; i += 4) {
            this.cachedTemplateDateFormats[i + 2] = null;
        }
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.Configurable
    public void setDateTimeFormat(String str) {
        String dateTimeFormat = getDateTimeFormat();
        super.setDateTimeFormat(str);
        if (str.equals(dateTimeFormat) || this.cachedTemplateDateFormats == null) {
            return;
        }
        for (int i = 0; i < 16; i += 4) {
            this.cachedTemplateDateFormats[i + 3] = null;
        }
    }

    public Configuration getConfiguration() {
        return getTemplate().getConfiguration();
    }

    TemplateModel getLastReturnValue() {
        return this.lastReturnValue;
    }

    void setLastReturnValue(TemplateModel templateModel) {
        this.lastReturnValue = templateModel;
    }

    void clearLastReturnValue() {
        this.lastReturnValue = null;
    }

    NumberFormat getNumberFormatObject(String str) {
        NumberFormat numberFormat;
        NumberFormat decimalFormat;
        if (this.cachedNumberFormats == null) {
            this.cachedNumberFormats = new HashMap();
        }
        NumberFormat numberFormat2 = (NumberFormat) this.cachedNumberFormats.get(str);
        if (numberFormat2 != null) {
            return numberFormat2;
        }
        Map map = JAVA_NUMBER_FORMATS;
        synchronized (map) {
            Locale locale = getLocale();
            NumberFormatKey numberFormatKey = new NumberFormatKey(str, locale);
            numberFormat = (NumberFormat) map.get(numberFormatKey);
            if (numberFormat == null) {
                if ("number".equals(str)) {
                    decimalFormat = NumberFormat.getNumberInstance(locale);
                } else if (FirebaseAnalytics.Param.CURRENCY.equals(str)) {
                    decimalFormat = NumberFormat.getCurrencyInstance(locale);
                } else if ("percent".equals(str)) {
                    decimalFormat = NumberFormat.getPercentInstance(locale);
                } else if ("computer".equals(str)) {
                    decimalFormat = getCNumberFormat();
                } else {
                    decimalFormat = new DecimalFormat(str, new DecimalFormatSymbols(getLocale()));
                }
                numberFormat = decimalFormat;
                map.put(numberFormatKey, numberFormat);
            }
        }
        NumberFormat numberFormat3 = (NumberFormat) numberFormat.clone();
        this.cachedNumberFormats.put(str, numberFormat3);
        return numberFormat3;
    }

    String formatDate(TemplateDateModel templateDateModel, Expression expression) throws Throwable {
        try {
            boolean zIsSQLDateOrTimeClass = isSQLDateOrTimeClass(EvalUtil.modelToDate(templateDateModel, expression).getClass());
            return getTemplateDateFormat(templateDateModel.getDateType(), zIsSQLDateOrTimeClass, shouldUseSQLDTTimeZone(zIsSQLDateOrTimeClass), expression).format(templateDateModel);
        } catch (UnknownDateTypeFormattingUnsupportedException e) {
            throw MessageUtil.newCantFormatUnknownTypeDateException(expression, e);
        } catch (UnformattableDateException e2) {
            throw MessageUtil.newCantFormatDateException(expression, e2);
        }
    }

    String formatDate(TemplateDateModel templateDateModel, String str, Expression expression) throws Throwable {
        boolean zIsSQLDateOrTimeClass = isSQLDateOrTimeClass(EvalUtil.modelToDate(templateDateModel, expression).getClass());
        try {
            return getTemplateDateFormat(templateDateModel.getDateType(), zIsSQLDateOrTimeClass, shouldUseSQLDTTimeZone(zIsSQLDateOrTimeClass), str, null).format(templateDateModel);
        } catch (UnknownDateTypeFormattingUnsupportedException e) {
            throw MessageUtil.newCantFormatUnknownTypeDateException(expression, e);
        } catch (UnformattableDateException e2) {
            throw MessageUtil.newCantFormatDateException(expression, e2);
        }
    }

    TemplateDateFormat getTemplateDateFormat(int i, Class cls, Expression expression) throws Throwable {
        try {
            boolean zIsSQLDateOrTimeClass = isSQLDateOrTimeClass(cls);
            return getTemplateDateFormat(i, zIsSQLDateOrTimeClass, shouldUseSQLDTTimeZone(zIsSQLDateOrTimeClass), expression);
        } catch (UnknownDateTypeFormattingUnsupportedException e) {
            throw MessageUtil.newCantFormatUnknownTypeDateException(expression, e);
        }
    }

    private TemplateDateFormat getTemplateDateFormat(int i, boolean z, boolean z2, Expression expression) throws UnknownDateTypeFormattingUnsupportedException, TemplateModelException {
        String timeFormat;
        String str;
        if (i == 0) {
            throw MessageUtil.newCantFormatUnknownTypeDateException(expression, null);
        }
        int cachedTemplateDateFormatIndex = getCachedTemplateDateFormatIndex(i, z, z2);
        TemplateDateFormat[] templateDateFormatArr = this.cachedTemplateDateFormats;
        if (templateDateFormatArr == null) {
            templateDateFormatArr = new TemplateDateFormat[16];
            this.cachedTemplateDateFormats = templateDateFormatArr;
        }
        TemplateDateFormat templateDateFormat = templateDateFormatArr[cachedTemplateDateFormatIndex];
        if (templateDateFormat != null) {
            return templateDateFormat;
        }
        if (i == 1) {
            timeFormat = getTimeFormat();
            str = Configurable.TIME_FORMAT_KEY;
        } else if (i == 2) {
            timeFormat = getDateFormat();
            str = Configurable.DATE_FORMAT_KEY;
        } else if (i == 3) {
            timeFormat = getDateTimeFormat();
            str = Configurable.DATETIME_FORMAT_KEY;
        } else {
            throw new _TemplateModelException(new Object[]{"Invalid date type enum: ", new Integer(i)});
        }
        TemplateDateFormat templateDateFormat2 = getTemplateDateFormat(i, z, z2, timeFormat, str);
        templateDateFormatArr[cachedTemplateDateFormatIndex] = templateDateFormat2;
        return templateDateFormat2;
    }

    TemplateDateFormat getTemplateDateFormat(int i, Class cls, String str, Expression expression) throws Throwable {
        try {
            boolean zIsSQLDateOrTimeClass = isSQLDateOrTimeClass(cls);
            return getTemplateDateFormat(i, zIsSQLDateOrTimeClass, shouldUseSQLDTTimeZone(zIsSQLDateOrTimeClass), str, null);
        } catch (UnknownDateTypeFormattingUnsupportedException e) {
            throw MessageUtil.newCantFormatUnknownTypeDateException(expression, e);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v10, types: [org.mapstruct.ap.shaded.freemarker.core.ISOTemplateDateFormatFactory] */
    /* JADX WARN: Type inference failed for: r0v12, types: [org.mapstruct.ap.shaded.freemarker.core.TemplateDateFormatFactory] */
    /* JADX WARN: Type inference failed for: r0v16 */
    /* JADX WARN: Type inference failed for: r0v17, types: [org.mapstruct.ap.shaded.freemarker.core.XSTemplateDateFormatFactory] */
    /* JADX WARN: Type inference failed for: r0v19 */
    /* JADX WARN: Type inference failed for: r0v2 */
    /* JADX WARN: Type inference failed for: r0v20 */
    /* JADX WARN: Type inference failed for: r0v21 */
    /* JADX WARN: Type inference failed for: r0v22 */
    /* JADX WARN: Type inference failed for: r0v23 */
    /* JADX WARN: Type inference failed for: r0v24 */
    /* JADX WARN: Type inference failed for: r0v3, types: [org.mapstruct.ap.shaded.freemarker.core.JavaTemplateDateFormatFactory] */
    /* JADX WARN: Type inference failed for: r0v9 */
    private TemplateDateFormat getTemplateDateFormat(int i, boolean z, boolean z2, String str, String str2) throws UnknownDateTypeFormattingUnsupportedException, TemplateModelException {
        ?? javaTemplateDateFormatFactory;
        int length = str.length();
        TimeZone sQLDateAndTimeTimeZone = z2 ? getSQLDateAndTimeTimeZone() : getTimeZone();
        if (length > 1 && str.charAt(0) == 'x' && str.charAt(1) == 's') {
            javaTemplateDateFormatFactory = z2 ? this.cachedSQLDTXSTemplateDateFormatFactory : this.cachedXSTemplateDateFormatFactory;
            if (javaTemplateDateFormatFactory == 0) {
                javaTemplateDateFormatFactory = new XSTemplateDateFormatFactory(sQLDateAndTimeTimeZone);
                if (z2) {
                    this.cachedSQLDTXSTemplateDateFormatFactory = javaTemplateDateFormatFactory;
                } else {
                    this.cachedXSTemplateDateFormatFactory = javaTemplateDateFormatFactory;
                }
            }
        } else if (length > 2 && str.charAt(0) == 'i' && str.charAt(1) == 's' && str.charAt(2) == 'o') {
            javaTemplateDateFormatFactory = z2 ? this.cachedSQLDTISOTemplateDateFormatFactory : this.cachedISOTemplateDateFormatFactory;
            if (javaTemplateDateFormatFactory == 0) {
                javaTemplateDateFormatFactory = new ISOTemplateDateFormatFactory(sQLDateAndTimeTimeZone);
                if (z2) {
                    this.cachedSQLDTISOTemplateDateFormatFactory = javaTemplateDateFormatFactory;
                } else {
                    this.cachedISOTemplateDateFormatFactory = javaTemplateDateFormatFactory;
                }
            }
        } else {
            javaTemplateDateFormatFactory = z2 ? this.cachedSQLDTJavaTemplateDateFormatFactory : this.cachedJavaTemplateDateFormatFactory;
            if (javaTemplateDateFormatFactory == 0) {
                javaTemplateDateFormatFactory = new JavaTemplateDateFormatFactory(sQLDateAndTimeTimeZone, getLocale());
                if (z2) {
                    this.cachedSQLDTJavaTemplateDateFormatFactory = javaTemplateDateFormatFactory;
                } else {
                    this.cachedJavaTemplateDateFormatFactory = javaTemplateDateFormatFactory;
                }
            }
        }
        try {
            return javaTemplateDateFormatFactory.get(i, z, str);
        } catch (java.text.ParseException e) {
            Throwable cause = e.getCause();
            Object[] objArr = new Object[4];
            objArr[0] = str2 == null ? "Malformed date/time format descriptor: " : new Object[]{"The value of the \"", str2, "\" FreeMarker configuration setting is a malformed date/time format descriptor: "};
            objArr[1] = new _DelayedJQuote(str);
            objArr[2] = ". Reason given: ";
            objArr[3] = e.getMessage();
            throw new _TemplateModelException(cause, objArr);
        }
    }

    static /* synthetic */ Class class$(String str) throws Throwable {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError().initCause(e);
        }
    }

    boolean shouldUseSQLDTTZ(Class cls) throws Throwable {
        Class clsClass$ = class$java$util$Date;
        if (clsClass$ == null) {
            clsClass$ = class$("java.util.Date");
            class$java$util$Date = clsClass$;
        }
        return (cls == clsClass$ || isSQLDateAndTimeTimeZoneSameAsNormal() || !isSQLDateOrTimeClass(cls)) ? false : true;
    }

    private boolean shouldUseSQLDTTimeZone(boolean z) {
        return z && !isSQLDateAndTimeTimeZoneSameAsNormal();
    }

    private static boolean isSQLDateOrTimeClass(Class cls) throws Throwable {
        Class clsClass$ = class$java$util$Date;
        if (clsClass$ == null) {
            clsClass$ = class$("java.util.Date");
            class$java$util$Date = clsClass$;
        }
        if (cls != clsClass$) {
            Class clsClass$2 = class$java$sql$Date;
            if (clsClass$2 == null) {
                clsClass$2 = class$("java.sql.Date");
                class$java$sql$Date = clsClass$2;
            }
            if (cls != clsClass$2) {
                Class clsClass$3 = class$java$sql$Time;
                if (clsClass$3 == null) {
                    clsClass$3 = class$("java.sql.Time");
                    class$java$sql$Time = clsClass$3;
                }
                if (cls != clsClass$3) {
                    Class clsClass$4 = class$java$sql$Timestamp;
                    if (clsClass$4 == null) {
                        clsClass$4 = class$("java.sql.Timestamp");
                        class$java$sql$Timestamp = clsClass$4;
                    }
                    if (cls != clsClass$4) {
                        Class clsClass$5 = class$java$sql$Date;
                        if (clsClass$5 == null) {
                            clsClass$5 = class$("java.sql.Date");
                            class$java$sql$Date = clsClass$5;
                        }
                        if (!clsClass$5.isAssignableFrom(cls)) {
                            Class clsClass$6 = class$java$sql$Time;
                            if (clsClass$6 == null) {
                                clsClass$6 = class$("java.sql.Time");
                                class$java$sql$Time = clsClass$6;
                            }
                            if (clsClass$6.isAssignableFrom(cls)) {
                            }
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }

    DateUtil.DateToISO8601CalendarFactory getISOBuiltInCalendarFactory() {
        if (this.isoBuiltInCalendarFactory == null) {
            this.isoBuiltInCalendarFactory = new DateUtil.TrivialDateToISO8601CalendarFactory();
        }
        return this.isoBuiltInCalendarFactory;
    }

    public NumberFormat getCNumberFormat() {
        if (this.cNumberFormat == null) {
            this.cNumberFormat = (DecimalFormat) C_NUMBER_FORMAT.clone();
        }
        return this.cNumberFormat;
    }

    TemplateTransformModel getTransform(Expression expression) throws TemplateException {
        TemplateModel templateModelEval = expression.eval(this);
        if (templateModelEval instanceof TemplateTransformModel) {
            return (TemplateTransformModel) templateModelEval;
        }
        if (expression instanceof Identifier) {
            TemplateModel sharedVariable = getConfiguration().getSharedVariable(expression.toString());
            if (sharedVariable instanceof TemplateTransformModel) {
                return (TemplateTransformModel) sharedVariable;
            }
        }
        return null;
    }

    public TemplateModel getLocalVariable(String str) throws TemplateModelException {
        ArrayList arrayList = this.localContextStack;
        if (arrayList != null) {
            for (int size = arrayList.size() - 1; size >= 0; size--) {
                TemplateModel localVariable = ((LocalContext) this.localContextStack.get(size)).getLocalVariable(str);
                if (localVariable != null) {
                    return localVariable;
                }
            }
        }
        Macro.Context context = this.currentMacroContext;
        if (context == null) {
            return null;
        }
        return context.getLocalVariable(str);
    }

    public TemplateModel getVariable(String str) throws TemplateModelException {
        TemplateModel localVariable = getLocalVariable(str);
        if (localVariable == null) {
            localVariable = this.currentNamespace.get(str);
        }
        return localVariable == null ? getGlobalVariable(str) : localVariable;
    }

    public TemplateModel getGlobalVariable(String str) throws TemplateModelException {
        TemplateModel templateModel = this.globalNamespace.get(str);
        if (templateModel == null) {
            templateModel = this.rootDataModel.get(str);
        }
        return templateModel == null ? getConfiguration().getSharedVariable(str) : templateModel;
    }

    public void setGlobalVariable(String str, TemplateModel templateModel) {
        this.globalNamespace.put(str, templateModel);
    }

    public void setVariable(String str, TemplateModel templateModel) {
        this.currentNamespace.put(str, templateModel);
    }

    public void setLocalVariable(String str, TemplateModel templateModel) {
        Macro.Context context = this.currentMacroContext;
        if (context == null) {
            throw new IllegalStateException("Not executing macro body");
        }
        context.setLocalVar(str, templateModel);
    }

    public Set getKnownVariableNames() throws TemplateModelException {
        Set sharedVariableNames = getConfiguration().getSharedVariableNames();
        TemplateHashModel templateHashModel = this.rootDataModel;
        if (templateHashModel instanceof TemplateHashModelEx) {
            TemplateModelIterator it = ((TemplateHashModelEx) templateHashModel).keys().iterator();
            while (it.hasNext()) {
                sharedVariableNames.add(((TemplateScalarModel) it.next()).getAsString());
            }
        }
        TemplateModelIterator it2 = this.globalNamespace.keys().iterator();
        while (it2.hasNext()) {
            sharedVariableNames.add(((TemplateScalarModel) it2.next()).getAsString());
        }
        TemplateModelIterator it3 = this.currentNamespace.keys().iterator();
        while (it3.hasNext()) {
            sharedVariableNames.add(((TemplateScalarModel) it3.next()).getAsString());
        }
        Macro.Context context = this.currentMacroContext;
        if (context != null) {
            sharedVariableNames.addAll(context.getLocalVariableNames());
        }
        ArrayList arrayList = this.localContextStack;
        if (arrayList != null) {
            for (int size = arrayList.size() - 1; size >= 0; size--) {
                sharedVariableNames.addAll(((LocalContext) this.localContextStack.get(size)).getLocalVariableNames());
            }
        }
        return sharedVariableNames;
    }

    public void outputInstructionStack(PrintWriter printWriter) throws IOException {
        outputInstructionStack(getInstructionStackSnapshot(), false, printWriter);
        printWriter.flush();
    }

    static void outputInstructionStack(TemplateElement[] templateElementArr, boolean z, Writer writer) throws IOException {
        PrintWriter printWriter = (PrintWriter) (writer instanceof PrintWriter ? writer : null);
        try {
            if (templateElementArr != null) {
                int length = templateElementArr.length;
                int i = (!z || length <= 10) ? length : 9;
                boolean z2 = false;
                boolean z3 = true;
                boolean z4 = z && i < length;
                int i2 = 0;
                int i3 = 0;
                int i4 = 0;
                int i5 = 0;
                while (i2 < length) {
                    TemplateElement templateElement = templateElementArr[i2];
                    boolean z5 = (i2 > 0 && (templateElement instanceof BodyInstruction)) || (i2 > 1 && (templateElementArr[i2 + (-1)] instanceof BodyInstruction));
                    if (i5 >= i) {
                        i3++;
                    } else if (z5 && z4) {
                        i4++;
                    } else {
                        writer.write(i2 == 0 ? "\t- Failed at: " : z5 ? "\t~ Reached through: " : "\t- Reached through: ");
                        writer.write(instructionStackItemToString(templateElement));
                        if (printWriter != null) {
                            printWriter.println();
                        } else {
                            writer.write(10);
                        }
                        i5++;
                    }
                    i2++;
                }
                if (i3 > 0) {
                    writer.write("\t... (Had ");
                    writer.write(String.valueOf(i3 + i4));
                    writer.write(" more, hidden for tersenes)");
                    z2 = true;
                }
                if (i4 > 0) {
                    if (z2) {
                        writer.write(32);
                    } else {
                        writer.write(9);
                    }
                    writer.write(new StringBuffer().append("(Hidden ").append(i4).append(" \"~\" lines for terseness)").toString());
                    if (printWriter != null) {
                        printWriter.println();
                    } else {
                        writer.write(10);
                    }
                } else {
                    z3 = z2;
                }
                if (z3) {
                    if (printWriter != null) {
                        printWriter.println();
                        return;
                    } else {
                        writer.write(10);
                        return;
                    }
                }
                return;
            }
            writer.write("(The stack was empty)");
            if (printWriter != null) {
                printWriter.println();
            } else {
                writer.write(10);
            }
        } catch (IOException e) {
            LOGGER.error("Failed to print FTL stack trace", e);
        }
    }

    TemplateElement[] getInstructionStackSnapshot() {
        int size = this.instructionStack.size();
        int i = 0;
        for (int i2 = 0; i2 < size; i2++) {
            TemplateElement templateElement = (TemplateElement) this.instructionStack.get(i2);
            if (i2 == size || templateElement.isShownInStackTrace()) {
                i++;
            }
        }
        if (i == 0) {
            return null;
        }
        TemplateElement[] templateElementArr = new TemplateElement[i];
        int i3 = i - 1;
        for (int i4 = 0; i4 < size; i4++) {
            TemplateElement templateElement2 = (TemplateElement) this.instructionStack.get(i4);
            if (i4 == size || templateElement2.isShownInStackTrace()) {
                templateElementArr[i3] = templateElement2;
                i3--;
            }
        }
        return templateElementArr;
    }

    static String instructionStackItemToString(TemplateElement templateElement) {
        StringBuffer stringBuffer = new StringBuffer();
        appendInstructionStackItem(templateElement, stringBuffer);
        return stringBuffer.toString();
    }

    static void appendInstructionStackItem(TemplateElement templateElement, StringBuffer stringBuffer) {
        stringBuffer.append(MessageUtil.shorten(templateElement.getDescription(), 40));
        stringBuffer.append("  [");
        Macro enclosingMacro = getEnclosingMacro(templateElement);
        if (enclosingMacro != null) {
            stringBuffer.append(MessageUtil.formatLocationForEvaluationError(enclosingMacro, templateElement.beginLine, templateElement.beginColumn));
        } else {
            stringBuffer.append(MessageUtil.formatLocationForEvaluationError(templateElement.getTemplate(), templateElement.beginLine, templateElement.beginColumn));
        }
        stringBuffer.append("]");
    }

    private static Macro getEnclosingMacro(TemplateElement templateElement) {
        while (templateElement != null) {
            if (templateElement instanceof Macro) {
                return (Macro) templateElement;
            }
            templateElement = (TemplateElement) templateElement.getParent();
        }
        return null;
    }

    private void pushLocalContext(LocalContext localContext) {
        if (this.localContextStack == null) {
            this.localContextStack = new ArrayList();
        }
        this.localContextStack.add(localContext);
    }

    private void popLocalContext() {
        this.localContextStack.remove(r0.size() - 1);
    }

    ArrayList getLocalContextStack() {
        return this.localContextStack;
    }

    public Namespace getNamespace(String str) {
        if (str.startsWith("/")) {
            str = str.substring(1);
        }
        HashMap map = this.loadedLibs;
        if (map != null) {
            return (Namespace) map.get(str);
        }
        return null;
    }

    public Namespace getMainNamespace() {
        return this.mainNamespace;
    }

    public Namespace getCurrentNamespace() {
        return this.currentNamespace;
    }

    public Namespace getGlobalNamespace() {
        return this.globalNamespace;
    }

    public TemplateHashModel getDataModel() {
        final TemplateHashModel templateHashModel = new TemplateHashModel() { // from class: org.mapstruct.ap.shaded.freemarker.core.Environment.3
            @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateHashModel
            public boolean isEmpty() {
                return false;
            }

            @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateHashModel
            public TemplateModel get(String str) throws TemplateModelException {
                TemplateModel templateModel = Environment.this.rootDataModel.get(str);
                return templateModel == null ? Environment.this.getConfiguration().getSharedVariable(str) : templateModel;
            }
        };
        return this.rootDataModel instanceof TemplateHashModelEx ? new TemplateHashModelEx() { // from class: org.mapstruct.ap.shaded.freemarker.core.Environment.4
            @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateHashModel
            public boolean isEmpty() throws TemplateModelException {
                return templateHashModel.isEmpty();
            }

            @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateHashModel
            public TemplateModel get(String str) throws TemplateModelException {
                return templateHashModel.get(str);
            }

            @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateHashModelEx
            public TemplateCollectionModel values() throws TemplateModelException {
                return ((TemplateHashModelEx) Environment.this.rootDataModel).values();
            }

            @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateHashModelEx
            public TemplateCollectionModel keys() throws TemplateModelException {
                return ((TemplateHashModelEx) Environment.this.rootDataModel).keys();
            }

            @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateHashModelEx
            public int size() throws TemplateModelException {
                return ((TemplateHashModelEx) Environment.this.rootDataModel).size();
            }
        } : templateHashModel;
    }

    public TemplateHashModel getGlobalVariables() {
        return new TemplateHashModel() { // from class: org.mapstruct.ap.shaded.freemarker.core.Environment.5
            @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateHashModel
            public boolean isEmpty() {
                return false;
            }

            @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateHashModel
            public TemplateModel get(String str) throws TemplateModelException {
                TemplateModel templateModel = Environment.this.globalNamespace.get(str);
                if (templateModel == null) {
                    templateModel = Environment.this.rootDataModel.get(str);
                }
                return templateModel == null ? Environment.this.getConfiguration().getSharedVariable(str) : templateModel;
            }
        };
    }

    private void pushElement(TemplateElement templateElement) {
        this.instructionStack.add(templateElement);
    }

    private void popElement() {
        this.instructionStack.remove(r0.size() - 1);
    }

    void replaceElementStackTop(TemplateElement templateElement) {
        this.instructionStack.set(r0.size() - 1, templateElement);
    }

    public TemplateNodeModel getCurrentVisitorNode() {
        return this.currentVisitorNode;
    }

    public void setCurrentVisitorNode(TemplateNodeModel templateNodeModel) {
        this.currentVisitorNode = templateNodeModel;
    }

    TemplateModel getNodeProcessor(TemplateNodeModel templateNodeModel) throws TemplateException {
        String nodeName = templateNodeModel.getNodeName();
        if (nodeName == null) {
            throw new _MiscTemplateException(this, "Node name is null.");
        }
        TemplateModel nodeProcessor = getNodeProcessor(nodeName, templateNodeModel.getNodeNamespace(), 0);
        if (nodeProcessor != null) {
            return nodeProcessor;
        }
        String nodeType = templateNodeModel.getNodeType();
        if (nodeType == null) {
            nodeType = "default";
        }
        return getNodeProcessor(new StringBuffer("@").append(nodeType).toString(), (String) null, 0);
    }

    private TemplateModel getNodeProcessor(String str, String str2, int i) throws TemplateException {
        TemplateModel nodeProcessor = null;
        while (i < this.nodeNamespaces.size()) {
            try {
                nodeProcessor = getNodeProcessor((Namespace) this.nodeNamespaces.get(i), str, str2);
                if (nodeProcessor != null) {
                    break;
                }
                i++;
            } catch (ClassCastException unused) {
                throw new _MiscTemplateException(this, "A \"using\" clause should contain a sequence of namespaces or strings that indicate the location of importable macro libraries.");
            }
        }
        if (nodeProcessor != null) {
            this.nodeNamespaceIndex = i + 1;
            this.currentNodeName = str;
            this.currentNodeNS = str2;
        }
        return nodeProcessor;
    }

    /* JADX WARN: Removed duplicated region for block: B:26:0x006a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private org.mapstruct.ap.shaded.freemarker.template.TemplateModel getNodeProcessor(org.mapstruct.ap.shaded.freemarker.core.Environment.Namespace r5, java.lang.String r6, java.lang.String r7) throws org.mapstruct.ap.shaded.freemarker.template.TemplateException {
        /*
            r4 = this;
            r0 = 0
            if (r7 != 0) goto L14
            org.mapstruct.ap.shaded.freemarker.template.TemplateModel r5 = r5.get(r6)
            boolean r6 = r5 instanceof org.mapstruct.ap.shaded.freemarker.core.Macro
            if (r6 != 0) goto L11
            boolean r6 = r5 instanceof org.mapstruct.ap.shaded.freemarker.template.TemplateTransformModel
            if (r6 != 0) goto L11
            goto La1
        L11:
            r0 = r5
            goto La1
        L14:
            org.mapstruct.ap.shaded.freemarker.template.Template r1 = r5.getTemplate()
            java.lang.String r2 = r1.getPrefixForNamespace(r7)
            if (r2 != 0) goto L1f
            return r0
        L1f:
            int r3 = r2.length()
            if (r3 <= 0) goto L49
            java.lang.StringBuffer r7 = new java.lang.StringBuffer
            r7.<init>()
            java.lang.StringBuffer r7 = r7.append(r2)
            java.lang.String r1 = ":"
            java.lang.StringBuffer r7 = r7.append(r1)
            java.lang.StringBuffer r6 = r7.append(r6)
            java.lang.String r6 = r6.toString()
            org.mapstruct.ap.shaded.freemarker.template.TemplateModel r5 = r5.get(r6)
            boolean r6 = r5 instanceof org.mapstruct.ap.shaded.freemarker.core.Macro
            if (r6 != 0) goto L11
            boolean r6 = r5 instanceof org.mapstruct.ap.shaded.freemarker.template.TemplateTransformModel
            if (r6 != 0) goto L11
            goto La1
        L49:
            int r2 = r7.length()
            if (r2 != 0) goto L6a
            java.lang.StringBuffer r2 = new java.lang.StringBuffer
            java.lang.String r3 = "N:"
            r2.<init>(r3)
            java.lang.StringBuffer r2 = r2.append(r6)
            java.lang.String r2 = r2.toString()
            org.mapstruct.ap.shaded.freemarker.template.TemplateModel r2 = r5.get(r2)
            boolean r3 = r2 instanceof org.mapstruct.ap.shaded.freemarker.core.Macro
            if (r3 != 0) goto L6b
            boolean r3 = r2 instanceof org.mapstruct.ap.shaded.freemarker.template.TemplateTransformModel
            if (r3 != 0) goto L6b
        L6a:
            r2 = r0
        L6b:
            java.lang.String r1 = r1.getDefaultNS()
            boolean r7 = r7.equals(r1)
            if (r7 == 0) goto L91
            java.lang.StringBuffer r7 = new java.lang.StringBuffer
            java.lang.String r1 = "D:"
            r7.<init>(r1)
            java.lang.StringBuffer r7 = r7.append(r6)
            java.lang.String r7 = r7.toString()
            org.mapstruct.ap.shaded.freemarker.template.TemplateModel r2 = r5.get(r7)
            boolean r7 = r2 instanceof org.mapstruct.ap.shaded.freemarker.core.Macro
            if (r7 != 0) goto L91
            boolean r7 = r2 instanceof org.mapstruct.ap.shaded.freemarker.template.TemplateTransformModel
            if (r7 != 0) goto L91
            r2 = r0
        L91:
            if (r2 != 0) goto La0
            org.mapstruct.ap.shaded.freemarker.template.TemplateModel r5 = r5.get(r6)
            boolean r6 = r5 instanceof org.mapstruct.ap.shaded.freemarker.core.Macro
            if (r6 != 0) goto L11
            boolean r6 = r5 instanceof org.mapstruct.ap.shaded.freemarker.template.TemplateTransformModel
            if (r6 != 0) goto L11
            goto La1
        La0:
            r0 = r2
        La1:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mapstruct.ap.shaded.freemarker.core.Environment.getNodeProcessor(org.mapstruct.ap.shaded.freemarker.core.Environment$Namespace, java.lang.String, java.lang.String):org.mapstruct.ap.shaded.freemarker.template.TemplateModel");
    }

    public void include(String str, String str2, boolean z) throws TemplateException, IOException {
        include(getTemplateForInclusion(str, str2, z));
    }

    public Template getTemplateForInclusion(String str, String str2, boolean z) throws IOException {
        return getTemplateForInclusion(str, str2, z, false);
    }

    public Template getTemplateForInclusion(String str, String str2, boolean z, boolean z2) throws IOException {
        if (str2 == null) {
            str2 = getTemplate().getEncoding();
        }
        if (str2 == null) {
            str2 = getConfiguration().getEncoding(getLocale());
        }
        return getConfiguration().getTemplate(str, getLocale(), str2, z, z2);
    }

    public void include(Template template) throws TemplateException, IOException {
        Template template2 = getTemplate();
        setParent(template);
        importMacros(template);
        try {
            visit(template.getRootTreeNode());
        } finally {
            setParent(template2);
        }
    }

    public Namespace importLib(String str, String str2) throws IOException, TemplateException {
        return importLib(getTemplateForImporting(str), str2);
    }

    public Template getTemplateForImporting(String str) throws IOException {
        return getTemplateForInclusion(str, null, true);
    }

    public Namespace importLib(Template template, String str) throws IOException, TemplateException {
        if (this.loadedLibs == null) {
            this.loadedLibs = new HashMap();
        }
        String name = template.getName();
        Namespace namespace = (Namespace) this.loadedLibs.get(name);
        if (namespace == null) {
            Namespace namespace2 = new Namespace(template);
            if (str != null) {
                this.currentNamespace.put(str, namespace2);
                if (this.currentNamespace == this.mainNamespace) {
                    this.globalNamespace.put(str, namespace2);
                }
            }
            Namespace namespace3 = this.currentNamespace;
            this.currentNamespace = namespace2;
            this.loadedLibs.put(name, namespace2);
            Writer writer = this.out;
            this.out = NullWriter.INSTANCE;
            try {
                include(template);
            } finally {
                this.out = writer;
                this.currentNamespace = namespace3;
            }
        } else if (str != null) {
            setVariable(str, namespace);
        }
        return (Namespace) this.loadedLibs.get(name);
    }

    String renderElementToString(TemplateElement templateElement) throws IOException, TemplateException {
        Writer writer = this.out;
        try {
            StringWriter stringWriter = new StringWriter();
            this.out = stringWriter;
            visit(templateElement);
            return stringWriter.toString();
        } finally {
            this.out = writer;
        }
    }

    void importMacros(Template template) {
        Iterator it = template.getMacros().values().iterator();
        while (it.hasNext()) {
            visitMacroDef((Macro) it.next());
        }
    }

    public String getNamespaceForPrefix(String str) {
        return this.currentNamespace.getTemplate().getNamespaceForPrefix(str);
    }

    public String getPrefixForNamespace(String str) {
        return this.currentNamespace.getTemplate().getPrefixForNamespace(str);
    }

    public String getDefaultNS() {
        return this.currentNamespace.getTemplate().getDefaultNS();
    }

    public Object __getitem__(String str) throws TemplateModelException {
        return BeansWrapper.getDefaultInstance().unwrap(getVariable(str));
    }

    public void __setitem__(String str, Object obj) throws TemplateException {
        setGlobalVariable(str, getObjectWrapper().wrap(obj));
    }

    private static final class NumberFormatKey {
        private final Locale locale;
        private final String pattern;

        NumberFormatKey(String str, Locale locale) {
            this.pattern = str;
            this.locale = locale;
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof NumberFormatKey)) {
                return false;
            }
            NumberFormatKey numberFormatKey = (NumberFormatKey) obj;
            return numberFormatKey.pattern.equals(this.pattern) && numberFormatKey.locale.equals(this.locale);
        }

        public int hashCode() {
            return this.pattern.hashCode() ^ this.locale.hashCode();
        }
    }

    public class Namespace extends SimpleHash {
        private Template template;

        Namespace() {
            this.template = Environment.this.getTemplate();
        }

        Namespace(Template template) {
            this.template = template;
        }

        public Template getTemplate() {
            Template template = this.template;
            return template == null ? Environment.this.getTemplate() : template;
        }
    }

    boolean getFastInvalidReferenceExceptions() {
        return this.fastInvalidReferenceExceptions;
    }

    boolean setFastInvalidReferenceExceptions(boolean z) {
        boolean z2 = this.fastInvalidReferenceExceptions;
        this.fastInvalidReferenceExceptions = z;
        return z2;
    }
}
