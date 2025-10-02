package org.mapstruct.ap.shaded.freemarker.template;

import java.io.BufferedReader;
import java.io.FilterReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import org.mapstruct.ap.shaded.freemarker.core.Configurable;
import org.mapstruct.ap.shaded.freemarker.core.Environment;
import org.mapstruct.ap.shaded.freemarker.core.FMParser;
import org.mapstruct.ap.shaded.freemarker.core.LibraryLoad;
import org.mapstruct.ap.shaded.freemarker.core.Macro;
import org.mapstruct.ap.shaded.freemarker.core.ParseException;
import org.mapstruct.ap.shaded.freemarker.core.TemplateElement;
import org.mapstruct.ap.shaded.freemarker.core.TextBlock;
import org.mapstruct.ap.shaded.freemarker.core.TokenMgrError;
import org.mapstruct.ap.shaded.freemarker.debug.impl.DebuggerService;

/* loaded from: classes3.dex */
public class Template extends Configurable {
    public static final String DEFAULT_NAMESPACE_PREFIX = "D";
    public static final String NO_NS_PREFIX = "N";
    private int actualTagSyntax;
    private String defaultNS;
    private String encoding;
    private List imports;
    private final ArrayList lines;
    private Map macros;
    private final String name;
    private Map namespaceURIToPrefixLookup;
    private transient FMParser parser;
    private Map prefixToNamespaceURILookup;
    private TemplateElement rootElement;
    private Version templateLanguageVersion;

    private Template(String str, Configuration configuration) {
        super(toNonNull(configuration));
        this.macros = new HashMap();
        this.imports = new Vector();
        this.lines = new ArrayList();
        this.prefixToNamespaceURILookup = new HashMap();
        this.namespaceURIToPrefixLookup = new HashMap();
        this.name = str;
        this.templateLanguageVersion = normalizeTemplateLanguageVersion(toNonNull(configuration).getIncompatibleImprovements());
    }

    private static Configuration toNonNull(Configuration configuration) {
        return configuration != null ? configuration : Configuration.getDefaultConfiguration();
    }

    public Template(String str, Reader reader, Configuration configuration) throws IOException {
        this(str, reader, configuration, null);
    }

    public Template(String str, String str2, Configuration configuration) throws IOException {
        this(str, new StringReader(str2), configuration, null);
    }

    public Template(String str, Reader reader, Configuration configuration, String str2) throws Throwable {
        LineTableBuilder lineTableBuilder;
        this(str, configuration);
        this.encoding = str2;
        try {
            try {
                reader = reader instanceof BufferedReader ? reader : new BufferedReader(reader, 4096);
                lineTableBuilder = new LineTableBuilder(reader);
            } catch (ParseException e) {
                e = e;
            }
        } catch (Throwable th) {
            th = th;
        }
        try {
            try {
                try {
                    FMParser fMParser = new FMParser(this, lineTableBuilder, getConfiguration().getStrictSyntaxMode(), getConfiguration().getWhitespaceStripping(), getConfiguration().getTagSyntax(), getConfiguration().getIncompatibleImprovements().intValue());
                    this.parser = fMParser;
                    this.rootElement = fMParser.Root();
                    this.actualTagSyntax = this.parser._getLastTagSyntax();
                    lineTableBuilder.close();
                    DebuggerService.registerTemplate(this);
                    this.namespaceURIToPrefixLookup = Collections.unmodifiableMap(this.namespaceURIToPrefixLookup);
                    this.prefixToNamespaceURILookup = Collections.unmodifiableMap(this.prefixToNamespaceURILookup);
                } catch (TokenMgrError e2) {
                    throw e2.toParseException(this);
                }
            } finally {
                this.parser = null;
            }
        } catch (ParseException e3) {
            e = e3;
            reader = lineTableBuilder;
            e.setTemplateName(str);
            throw e;
        } catch (Throwable th2) {
            th = th2;
            reader = lineTableBuilder;
            reader.close();
            throw th;
        }
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public Template(String str, Reader reader) throws IOException {
        this(str, reader, (Configuration) null);
    }

    Template(String str, TemplateElement templateElement, Configuration configuration) {
        this(str, configuration);
        this.rootElement = templateElement;
        DebuggerService.registerTemplate(this);
    }

    public static Template getPlainTextTemplate(String str, String str2, Configuration configuration) {
        Template template = new Template(str, configuration);
        template.rootElement = new TextBlock(str2);
        template.actualTagSyntax = configuration.getTagSyntax();
        DebuggerService.registerTemplate(template);
        return template;
    }

    private static Version normalizeTemplateLanguageVersion(Version version) {
        _TemplateAPI.checkVersionNotNullAndSupported(version);
        int iIntValue = version.intValue();
        if (iIntValue < _TemplateAPI.VERSION_INT_2_3_19) {
            return Configuration.VERSION_2_3_0;
        }
        return iIntValue > _TemplateAPI.VERSION_INT_2_3_21 ? Configuration.VERSION_2_3_21 : version;
    }

    public void process(Object obj, Writer writer) throws TemplateException, IOException {
        createProcessingEnvironment(obj, writer, null).process();
    }

    public void process(Object obj, Writer writer, ObjectWrapper objectWrapper, TemplateNodeModel templateNodeModel) throws TemplateException, IOException {
        Environment environmentCreateProcessingEnvironment = createProcessingEnvironment(obj, writer, objectWrapper);
        if (templateNodeModel != null) {
            environmentCreateProcessingEnvironment.setCurrentVisitorNode(templateNodeModel);
        }
        environmentCreateProcessingEnvironment.process();
    }

    public void process(Object obj, Writer writer, ObjectWrapper objectWrapper) throws TemplateException, IOException {
        createProcessingEnvironment(obj, writer, objectWrapper).process();
    }

    public Environment createProcessingEnvironment(Object obj, Writer writer, ObjectWrapper objectWrapper) throws TemplateException, IOException {
        TemplateHashModel simpleHash;
        if (obj instanceof TemplateHashModel) {
            simpleHash = (TemplateHashModel) obj;
        } else {
            if (objectWrapper == null) {
                objectWrapper = getObjectWrapper();
            }
            if (obj == null) {
                simpleHash = new SimpleHash(objectWrapper);
            } else {
                TemplateModel templateModelWrap = objectWrapper.wrap(obj);
                if (!(templateModelWrap instanceof TemplateHashModel)) {
                    if (templateModelWrap == null) {
                        throw new IllegalArgumentException(new StringBuffer().append(objectWrapper.getClass().getName()).append(" converted ").append(obj.getClass().getName()).append(" to null.").toString());
                    }
                    throw new IllegalArgumentException(new StringBuffer().append(objectWrapper.getClass().getName()).append(" didn't convert ").append(obj.getClass().getName()).append(" to a TemplateHashModel. Generally, you want to use a Map<String, Object> or a JavaBean as the root-map (aka. data-model) parameter. The Map key-s or JavaBean property names will be the variable names in the template.").toString());
                }
                simpleHash = (TemplateHashModel) templateModelWrap;
            }
        }
        return new Environment(this, simpleHash, writer);
    }

    public Environment createProcessingEnvironment(Object obj, Writer writer) throws TemplateException, IOException {
        return createProcessingEnvironment(obj, writer, null);
    }

    public String toString() {
        StringWriter stringWriter = new StringWriter();
        try {
            dump(stringWriter);
            return stringWriter.toString();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public String getName() {
        return this.name;
    }

    public Configuration getConfiguration() {
        return (Configuration) getParent();
    }

    Version getTemplateLanguageVersion() {
        return this.templateLanguageVersion;
    }

    public void setEncoding(String str) {
        this.encoding = str;
    }

    public String getEncoding() {
        return this.encoding;
    }

    public int getActualTagSyntax() {
        return this.actualTagSyntax;
    }

    public void dump(PrintStream printStream) {
        printStream.print(this.rootElement.getCanonicalForm());
    }

    public void dump(Writer writer) throws IOException {
        writer.write(this.rootElement.getCanonicalForm());
    }

    public void addMacro(Macro macro) {
        this.macros.put(macro.getName(), macro);
    }

    public void addImport(LibraryLoad libraryLoad) {
        this.imports.add(libraryLoad);
    }

    public String getSource(int i, int i2, int i3, int i4) {
        if (i2 < 1 || i4 < 1) {
            return null;
        }
        int i5 = i - 1;
        int i6 = i3 - 1;
        int i7 = i4 - 1;
        StringBuffer stringBuffer = new StringBuffer();
        for (int i8 = i2 - 1; i8 <= i7; i8++) {
            if (i8 < this.lines.size()) {
                stringBuffer.append(this.lines.get(i8));
            }
        }
        int length = (this.lines.get(i7).toString().length() - i6) - 1;
        stringBuffer.delete(0, i5);
        stringBuffer.delete(stringBuffer.length() - length, stringBuffer.length());
        return stringBuffer.toString();
    }

    private class LineTableBuilder extends FilterReader {
        int lastChar;
        StringBuffer lineBuf;

        LineTableBuilder(Reader reader) {
            super(reader);
            this.lineBuf = new StringBuffer();
        }

        @Override // java.io.FilterReader, java.io.Reader
        public int read() throws IOException {
            int i = this.in.read();
            handleChar(i);
            return i;
        }

        @Override // java.io.FilterReader, java.io.Reader
        public int read(char[] cArr, int i, int i2) throws IOException {
            int i3 = this.in.read(cArr, i, i2);
            for (int i4 = i; i4 < i + i3; i4++) {
                handleChar(cArr[i4]);
            }
            return i3;
        }

        @Override // java.io.FilterReader, java.io.Reader, java.io.Closeable, java.lang.AutoCloseable
        public void close() throws IOException {
            if (this.lineBuf.length() > 0) {
                Template.this.lines.add(this.lineBuf.toString());
                this.lineBuf.setLength(0);
            }
            super.close();
        }

        private void handleChar(int i) {
            if (i == 10 || i == 13) {
                if (this.lastChar == 13 && i == 10) {
                    int size = Template.this.lines.size() - 1;
                    Template.this.lines.set(size, new StringBuffer().append((String) Template.this.lines.get(size)).append('\n').toString());
                } else {
                    this.lineBuf.append((char) i);
                    Template.this.lines.add(this.lineBuf.toString());
                    this.lineBuf.setLength(0);
                }
            } else if (i == 9) {
                int length = 8 - (this.lineBuf.length() % 8);
                for (int i2 = 0; i2 < length; i2++) {
                    this.lineBuf.append(' ');
                }
            } else {
                this.lineBuf.append((char) i);
            }
            this.lastChar = i;
        }
    }

    public TemplateElement getRootTreeNode() {
        return this.rootElement;
    }

    public Map getMacros() {
        return this.macros;
    }

    public List getImports() {
        return this.imports;
    }

    public void addPrefixNSMapping(String str, String str2) {
        if (str2.length() == 0) {
            throw new IllegalArgumentException("Cannot map empty string URI");
        }
        if (str.length() == 0) {
            throw new IllegalArgumentException("Cannot map empty string prefix");
        }
        if (str.equals("N")) {
            throw new IllegalArgumentException(new StringBuffer("The prefix: ").append(str).append(" cannot be registered, it's reserved for special internal use.").toString());
        }
        if (this.prefixToNamespaceURILookup.containsKey(str)) {
            throw new IllegalArgumentException(new StringBuffer("The prefix: '").append(str).append("' was repeated. This is illegal.").toString());
        }
        if (this.namespaceURIToPrefixLookup.containsKey(str2)) {
            throw new IllegalArgumentException(new StringBuffer("The namespace URI: ").append(str2).append(" cannot be mapped to 2 different prefixes.").toString());
        }
        if (str.equals(DEFAULT_NAMESPACE_PREFIX)) {
            this.defaultNS = str2;
        } else {
            this.prefixToNamespaceURILookup.put(str, str2);
            this.namespaceURIToPrefixLookup.put(str2, str);
        }
    }

    public String getDefaultNS() {
        return this.defaultNS;
    }

    public String getNamespaceForPrefix(String str) {
        if (str.equals("")) {
            String str2 = this.defaultNS;
            return str2 == null ? "" : str2;
        }
        return (String) this.prefixToNamespaceURILookup.get(str);
    }

    public String getPrefixForNamespace(String str) {
        if (str == null) {
            return null;
        }
        return str.length() == 0 ? this.defaultNS == null ? "" : "N" : str.equals(this.defaultNS) ? "" : (String) this.namespaceURIToPrefixLookup.get(str);
    }

    public String getPrefixedName(String str, String str2) {
        if (str2 == null || str2.length() == 0) {
            return this.defaultNS != null ? new StringBuffer("N:").append(str).toString() : str;
        }
        if (str2.equals(this.defaultNS)) {
            return str;
        }
        String prefixForNamespace = getPrefixForNamespace(str2);
        if (prefixForNamespace == null) {
            return null;
        }
        return new StringBuffer().append(prefixForNamespace).append(":").append(str).toString();
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x0026, code lost:
    
        r1 = r2;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public javax.swing.tree.TreePath containingElements(int r5, int r6) {
        /*
            r4 = this;
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            org.mapstruct.ap.shaded.freemarker.core.TemplateElement r1 = r4.rootElement
        L7:
            boolean r2 = r1.contains(r5, r6)
            if (r2 == 0) goto L28
            r0.add(r1)
            java.util.Enumeration r1 = r1.children()
        L14:
            boolean r2 = r1.hasMoreElements()
            if (r2 == 0) goto L28
            java.lang.Object r2 = r1.nextElement()
            org.mapstruct.ap.shaded.freemarker.core.TemplateElement r2 = (org.mapstruct.ap.shaded.freemarker.core.TemplateElement) r2
            boolean r3 = r2.contains(r5, r6)
            if (r3 == 0) goto L14
            r1 = r2
            goto L7
        L28:
            boolean r5 = r0.isEmpty()
            if (r5 == 0) goto L30
            r5 = 0
            return r5
        L30:
            javax.swing.tree.TreePath r5 = new javax.swing.tree.TreePath
            java.lang.Object[] r6 = r0.toArray()
            r5.<init>(r6)
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mapstruct.ap.shaded.freemarker.template.Template.containingElements(int, int):javax.swing.tree.TreePath");
    }

    public static class WrongEncodingException extends ParseException {
        public String specifiedEncoding;

        public WrongEncodingException(String str) {
            this.specifiedEncoding = str;
        }
    }
}
