package org.mapstruct.ap.shaded.freemarker.ext.dom;

import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.MalformedURLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.mapstruct.ap.shaded.freemarker.core._UnexpectedTypeErrorExplainerTemplateModel;
import org.mapstruct.ap.shaded.freemarker.ext.util.WrapperTemplateModel;
import org.mapstruct.ap.shaded.freemarker.log.Logger;
import org.mapstruct.ap.shaded.freemarker.template.AdapterTemplateModel;
import org.mapstruct.ap.shaded.freemarker.template.SimpleScalar;
import org.mapstruct.ap.shaded.freemarker.template.TemplateHashModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModelException;
import org.mapstruct.ap.shaded.freemarker.template.TemplateNodeModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateSequenceModel;
import org.w3c.dom.Attr;
import org.w3c.dom.CDATASection;
import org.w3c.dom.CharacterData;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.ProcessingInstruction;
import org.w3c.dom.Text;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/* loaded from: classes3.dex */
public abstract class NodeModel implements TemplateNodeModel, TemplateHashModel, TemplateSequenceModel, AdapterTemplateModel, WrapperTemplateModel, _UnexpectedTypeErrorExplainerTemplateModel {
    static /* synthetic */ Class class$freemarker$ext$dom$XPathSupport;
    static /* synthetic */ Class class$freemarker$template$TemplateBooleanModel;
    static /* synthetic */ Class class$freemarker$template$TemplateDateModel;
    static /* synthetic */ Class class$freemarker$template$TemplateNumberModel;
    private static DocumentBuilderFactory docBuilderFactory;
    private static ErrorHandler errorHandler;
    private static XPathSupport jaxenXPathSupport;
    static Class xpathSupportClass;
    private TemplateSequenceModel children;
    final Node node;
    private NodeModel parent;
    static final Logger logger = Logger.getLogger("org.mapstruct.ap.shaded.freemarker.dom");
    private static final Object STATIC_LOCK = new Object();
    private static final Map xpathSupportMap = Collections.synchronizedMap(new WeakHashMap());

    @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateSequenceModel
    public final TemplateModel get(int i) {
        if (i == 0) {
            return this;
        }
        return null;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateSequenceModel
    public final int size() {
        return 1;
    }

    static {
        try {
            useDefaultXPathSupport();
        } catch (Exception unused) {
        }
        if (xpathSupportClass == null) {
            Logger logger2 = logger;
            if (logger2.isWarnEnabled()) {
                logger2.warn("No XPath support is available.");
            }
        }
    }

    public static void setDocumentBuilderFactory(DocumentBuilderFactory documentBuilderFactory) {
        synchronized (STATIC_LOCK) {
            docBuilderFactory = documentBuilderFactory;
        }
    }

    public static DocumentBuilderFactory getDocumentBuilderFactory() {
        DocumentBuilderFactory documentBuilderFactory;
        synchronized (STATIC_LOCK) {
            if (docBuilderFactory == null) {
                DocumentBuilderFactory documentBuilderFactoryNewInstance = DocumentBuilderFactory.newInstance();
                documentBuilderFactoryNewInstance.setNamespaceAware(true);
                documentBuilderFactoryNewInstance.setIgnoringElementContentWhitespace(true);
                docBuilderFactory = documentBuilderFactoryNewInstance;
            }
            documentBuilderFactory = docBuilderFactory;
        }
        return documentBuilderFactory;
    }

    public static void setErrorHandler(ErrorHandler errorHandler2) {
        synchronized (STATIC_LOCK) {
            errorHandler = errorHandler2;
        }
    }

    public static ErrorHandler getErrorHandler() {
        ErrorHandler errorHandler2;
        synchronized (STATIC_LOCK) {
            errorHandler2 = errorHandler;
        }
        return errorHandler2;
    }

    public static NodeModel parse(InputSource inputSource, boolean z, boolean z2) throws ParserConfigurationException, SAXException, DOMException, IOException {
        DocumentBuilder documentBuilderNewDocumentBuilder = getDocumentBuilderFactory().newDocumentBuilder();
        ErrorHandler errorHandler2 = getErrorHandler();
        if (errorHandler2 != null) {
            documentBuilderNewDocumentBuilder.setErrorHandler(errorHandler2);
        }
        try {
            Document document = documentBuilderNewDocumentBuilder.parse(inputSource);
            if (z && z2) {
                simplify(document);
            } else {
                if (z) {
                    removeComments(document);
                }
                if (z2) {
                    removePIs(document);
                }
                mergeAdjacentText(document);
            }
            return wrap(document);
        } catch (MalformedURLException e) {
            if (inputSource.getSystemId() == null && inputSource.getCharacterStream() == null && inputSource.getByteStream() == null) {
                throw new MalformedURLException(new StringBuffer("The SAX InputSource has systemId == null && characterStream == null && byteStream == null. This is often because it was created with a null InputStream or Reader, which is often because the XML file it should point to was not found. (The original exception was: ").append(e).append(")").toString());
            }
            throw e;
        }
    }

    public static NodeModel parse(InputSource inputSource) throws ParserConfigurationException, SAXException, IOException {
        return parse(inputSource, true, true);
    }

    public static NodeModel parse(File file, boolean z, boolean z2) throws ParserConfigurationException, SAXException, DOMException, IOException {
        DocumentBuilder documentBuilderNewDocumentBuilder = getDocumentBuilderFactory().newDocumentBuilder();
        ErrorHandler errorHandler2 = getErrorHandler();
        if (errorHandler2 != null) {
            documentBuilderNewDocumentBuilder.setErrorHandler(errorHandler2);
        }
        Document document = documentBuilderNewDocumentBuilder.parse(file);
        if (z) {
            removeComments(document);
        }
        if (z2) {
            removePIs(document);
        }
        mergeAdjacentText(document);
        return wrap(document);
    }

    public static NodeModel parse(File file) throws ParserConfigurationException, SAXException, IOException {
        return parse(file, true, true);
    }

    protected NodeModel(Node node) {
        this.node = node;
    }

    public Node getNode() {
        return this.node;
    }

    public TemplateModel get(String str) throws TemplateModelException {
        if (str.startsWith("@@")) {
            if (str.equals("@@text")) {
                return new SimpleScalar(getText(this.node));
            }
            if (str.equals("@@namespace")) {
                String namespaceURI = this.node.getNamespaceURI();
                if (namespaceURI == null) {
                    return null;
                }
                return new SimpleScalar(namespaceURI);
            }
            if (str.equals("@@local_name")) {
                String localName = this.node.getLocalName();
                if (localName == null) {
                    localName = getNodeName();
                }
                return new SimpleScalar(localName);
            }
            if (str.equals("@@markup")) {
                StringBuffer stringBuffer = new StringBuffer();
                new NodeOutputter(this.node).outputContent(this.node, stringBuffer);
                return new SimpleScalar(stringBuffer.toString());
            }
            if (str.equals("@@nested_markup")) {
                StringBuffer stringBuffer2 = new StringBuffer();
                new NodeOutputter(this.node).outputContent(this.node.getChildNodes(), stringBuffer2);
                return new SimpleScalar(stringBuffer2.toString());
            }
            if (str.equals("@@qname")) {
                String qualifiedName = getQualifiedName();
                if (qualifiedName == null) {
                    return null;
                }
                return new SimpleScalar(qualifiedName);
            }
        }
        XPathSupport xPathSupport = getXPathSupport();
        if (xPathSupport != null) {
            return xPathSupport.executeQuery(this.node, str);
        }
        throw new TemplateModelException(new StringBuffer("Can't try to resolve the XML query key, because no XPath support is available. It's either malformed or an XPath expression: ").append(str).toString());
    }

    @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateNodeModel
    public TemplateNodeModel getParentNode() {
        if (this.parent == null) {
            Node parentNode = this.node.getParentNode();
            if (parentNode == null) {
                Node node = this.node;
                if (node instanceof Attr) {
                    parentNode = ((Attr) node).getOwnerElement();
                }
            }
            this.parent = wrap(parentNode);
        }
        return this.parent;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateNodeModel
    public TemplateSequenceModel getChildNodes() {
        if (this.children == null) {
            this.children = new NodeListModel(this.node.getChildNodes(), this);
        }
        return this.children;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateNodeModel
    public final String getNodeType() throws TemplateModelException {
        short nodeType = this.node.getNodeType();
        switch (nodeType) {
            case 1:
                return "element";
            case 2:
                return "attribute";
            case 3:
            case 4:
                return "text";
            case 5:
                return "entity_reference";
            case 6:
                return "entity";
            case 7:
                return "pi";
            case 8:
                return "comment";
            case 9:
                return "document";
            case 10:
                return "document_type";
            case 11:
                return "document_fragment";
            case 12:
                return "notation";
            default:
                throw new TemplateModelException(new StringBuffer("Unknown node type: ").append((int) nodeType).append(". This should be impossible!").toString());
        }
    }

    public TemplateModel exec(List list) throws TemplateModelException {
        if (list.size() != 1) {
            throw new TemplateModelException("Expecting exactly one arguments");
        }
        String str = (String) list.get(0);
        XPathSupport xPathSupport = getXPathSupport();
        if (xPathSupport == null) {
            throw new TemplateModelException("No XPath support available");
        }
        return xPathSupport.executeQuery(this.node, str);
    }

    @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateNodeModel
    public String getNodeNamespace() {
        short nodeType = this.node.getNodeType();
        if (nodeType != 2 && nodeType != 1) {
            return null;
        }
        String namespaceURI = this.node.getNamespaceURI();
        if (namespaceURI == null && nodeType == 1) {
            return "";
        }
        if ("".equals(namespaceURI) && nodeType == 2) {
            return null;
        }
        return namespaceURI;
    }

    public final int hashCode() {
        return this.node.hashCode();
    }

    public boolean equals(Object obj) {
        return obj != null && obj.getClass() == getClass() && ((NodeModel) obj).node.equals(this.node);
    }

    public static NodeModel wrap(Node node) {
        if (node == null) {
            return null;
        }
        switch (node.getNodeType()) {
            case 1:
                return new ElementModel((Element) node);
            case 2:
                return new AttributeNodeModel((Attr) node);
            case 3:
            case 4:
            case 8:
                return new CharacterDataNodeModel((CharacterData) node);
            case 5:
            case 6:
            default:
                return null;
            case 7:
                return new PINodeModel((ProcessingInstruction) node);
            case 9:
                return new DocumentModel((Document) node);
            case 10:
                return new DocumentTypeModel((DocumentType) node);
        }
    }

    public static void removeComments(Node node) throws DOMException {
        NodeList childNodes = node.getChildNodes();
        int length = childNodes.getLength();
        int i = 0;
        while (i < length) {
            Node nodeItem = childNodes.item(i);
            if (nodeItem.hasChildNodes()) {
                removeComments(nodeItem);
            } else if (nodeItem.getNodeType() == 8) {
                node.removeChild(nodeItem);
                length--;
            }
            i++;
        }
    }

    public static void removePIs(Node node) throws DOMException {
        NodeList childNodes = node.getChildNodes();
        int length = childNodes.getLength();
        int i = 0;
        while (i < length) {
            Node nodeItem = childNodes.item(i);
            if (nodeItem.hasChildNodes()) {
                removePIs(nodeItem);
            } else if (nodeItem.getNodeType() == 7) {
                node.removeChild(nodeItem);
                length--;
            }
            i++;
        }
    }

    public static void mergeAdjacentText(Node node) throws DOMException {
        for (Node firstChild = node.getFirstChild(); firstChild != null; firstChild = firstChild.getNextSibling()) {
            if ((firstChild instanceof Text) || (firstChild instanceof CDATASection)) {
                Node nextSibling = firstChild.getNextSibling();
                if ((nextSibling instanceof Text) || (nextSibling instanceof CDATASection)) {
                    ((CharacterData) firstChild).setData(new StringBuffer().append(firstChild.getNodeValue()).append(nextSibling.getNodeValue()).toString());
                    node.removeChild(nextSibling);
                }
            } else {
                mergeAdjacentText(firstChild);
            }
        }
    }

    public static void simplify(Node node) throws DOMException {
        NodeList childNodes = node.getChildNodes();
        int length = childNodes.getLength();
        int i = 0;
        while (true) {
            Node node2 = null;
            while (i < length) {
                Node nodeItem = childNodes.item(i);
                if (nodeItem.hasChildNodes()) {
                    simplify(nodeItem);
                } else {
                    short nodeType = nodeItem.getNodeType();
                    if (nodeType == 7) {
                        node.removeChild(nodeItem);
                    } else if (nodeType == 8) {
                        node.removeChild(nodeItem);
                    } else if (nodeType == 3 || nodeType == 4) {
                        if (node2 != null) {
                            CharacterData characterData = (CharacterData) node2;
                            characterData.setData(new StringBuffer().append(characterData.getNodeValue()).append(nodeItem.getNodeValue()).toString());
                            node.removeChild(nodeItem);
                        } else {
                            i++;
                            node2 = nodeItem;
                        }
                    }
                    length--;
                }
                i++;
            }
            return;
        }
    }

    NodeModel getDocumentNodeModel() {
        Node node = this.node;
        return node instanceof Document ? this : wrap(node.getOwnerDocument());
    }

    public static void useDefaultXPathSupport() {
        synchronized (STATIC_LOCK) {
            xpathSupportClass = null;
            jaxenXPathSupport = null;
            try {
                useXalanXPathSupport();
            } catch (Exception unused) {
            }
            if (xpathSupportClass == null) {
                try {
                    useSunInternalXPathSupport();
                } catch (Exception unused2) {
                }
            }
            if (xpathSupportClass == null) {
                try {
                    useJaxenXPathSupport();
                } catch (Exception unused3) {
                }
            }
        }
    }

    public static void useJaxenXPathSupport() throws Exception {
        Class.forName("org.jaxen.dom.DOMXPath");
        Class<?> cls = Class.forName("org.mapstruct.ap.shaded.freemarker.ext.dom.JaxenXPathSupport");
        jaxenXPathSupport = (XPathSupport) cls.newInstance();
        synchronized (STATIC_LOCK) {
            xpathSupportClass = cls;
        }
        Logger logger2 = logger;
        if (logger2.isDebugEnabled()) {
            logger2.debug("Using Jaxen classes for XPath support");
        }
    }

    public static void useXalanXPathSupport() throws Exception {
        Class.forName("org.apache.xpath.XPath");
        Class<?> cls = Class.forName("org.mapstruct.ap.shaded.freemarker.ext.dom.XalanXPathSupport");
        synchronized (STATIC_LOCK) {
            xpathSupportClass = cls;
        }
        Logger logger2 = logger;
        if (logger2.isDebugEnabled()) {
            logger2.debug("Using Xalan classes for XPath support");
        }
    }

    public static void useSunInternalXPathSupport() throws Exception {
        Class.forName("com.sun.org.apache.xpath.internal.XPath");
        Class<?> cls = Class.forName("org.mapstruct.ap.shaded.freemarker.ext.dom.SunInternalXalanXPathSupport");
        synchronized (STATIC_LOCK) {
            xpathSupportClass = cls;
        }
        Logger logger2 = logger;
        if (logger2.isDebugEnabled()) {
            logger2.debug("Using Sun's internal Xalan classes for XPath support");
        }
    }

    static /* synthetic */ Class class$(String str) throws Throwable {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError().initCause(e);
        }
    }

    public static void setXPathSupportClass(Class cls) throws Throwable {
        if (cls != null) {
            Class clsClass$ = class$freemarker$ext$dom$XPathSupport;
            if (clsClass$ == null) {
                clsClass$ = class$("org.mapstruct.ap.shaded.freemarker.ext.dom.XPathSupport");
                class$freemarker$ext$dom$XPathSupport = clsClass$;
            }
            if (!clsClass$.isAssignableFrom(cls)) {
                throw new RuntimeException(new StringBuffer("Class ").append(cls.getName()).append(" does not implement freemarker.ext.dom.XPathSupport").toString());
            }
        }
        synchronized (STATIC_LOCK) {
            xpathSupportClass = cls;
        }
    }

    public static Class getXPathSupportClass() {
        Class cls;
        synchronized (STATIC_LOCK) {
            cls = xpathSupportClass;
        }
        return cls;
    }

    private static String getText(Node node) {
        if ((node instanceof Text) || (node instanceof CDATASection)) {
            return ((CharacterData) node).getData();
        }
        String string = "";
        if (!(node instanceof Element)) {
            return node instanceof Document ? getText(((Document) node).getDocumentElement()) : "";
        }
        NodeList childNodes = node.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            string = new StringBuffer().append(string).append(getText(childNodes.item(i))).toString();
        }
        return string;
    }

    XPathSupport getXPathSupport() {
        XPathSupport xPathSupport;
        XPathSupport xPathSupport2;
        XPathSupport xPathSupport3 = jaxenXPathSupport;
        if (xPathSupport3 != null) {
            return xPathSupport3;
        }
        Document ownerDocument = this.node.getOwnerDocument();
        if (ownerDocument == null) {
            ownerDocument = (Document) this.node;
        }
        synchronized (ownerDocument) {
            Map map = xpathSupportMap;
            WeakReference weakReference = (WeakReference) map.get(ownerDocument);
            xPathSupport = weakReference != null ? (XPathSupport) weakReference.get() : null;
            if (xPathSupport == null) {
                try {
                    xPathSupport2 = (XPathSupport) xpathSupportClass.newInstance();
                } catch (Exception e) {
                    e = e;
                }
                try {
                    map.put(ownerDocument, new WeakReference(xPathSupport2));
                    xPathSupport = xPathSupport2;
                } catch (Exception e2) {
                    e = e2;
                    xPathSupport = xPathSupport2;
                    logger.error("Error instantiating xpathSupport class", e);
                    return xPathSupport;
                }
            }
        }
        return xPathSupport;
    }

    String getQualifiedName() throws TemplateModelException {
        return getNodeName();
    }

    @Override // org.mapstruct.ap.shaded.freemarker.template.AdapterTemplateModel
    public Object getAdaptedObject(Class cls) {
        return this.node;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.ext.util.WrapperTemplateModel
    public Object getWrappedObject() {
        return this.node;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.mapstruct.ap.shaded.freemarker.core._UnexpectedTypeErrorExplainerTemplateModel
    public Object[] explainTypeError(Class[] clsArr) throws Throwable {
        for (Class cls : clsArr) {
            Class cls2 = class$freemarker$template$TemplateDateModel;
            Class cls3 = cls2;
            if (cls2 == null) {
                Class clsClass$ = class$("org.mapstruct.ap.shaded.freemarker.template.TemplateDateModel");
                class$freemarker$template$TemplateDateModel = clsClass$;
                cls3 = clsClass$;
            }
            if (!cls3.isAssignableFrom(cls)) {
                Class cls4 = class$freemarker$template$TemplateNumberModel;
                Class cls5 = cls4;
                if (cls4 == null) {
                    Class clsClass$2 = class$("org.mapstruct.ap.shaded.freemarker.template.TemplateNumberModel");
                    class$freemarker$template$TemplateNumberModel = clsClass$2;
                    cls5 = clsClass$2;
                }
                if (!cls5.isAssignableFrom(cls)) {
                    Class cls6 = class$freemarker$template$TemplateBooleanModel;
                    Class cls7 = cls6;
                    if (cls6 == null) {
                        Class clsClass$3 = class$("org.mapstruct.ap.shaded.freemarker.template.TemplateBooleanModel");
                        class$freemarker$template$TemplateBooleanModel = clsClass$3;
                        cls7 = clsClass$3;
                    }
                    if (!cls7.isAssignableFrom(cls)) {
                    }
                }
            }
            return new Object[]{"XML node values are always strings (text), that is, they can't be used as number, date/time/datetime or boolean without explicit conversion (such as someNode?number, someNode?datetime.xs, someNode?date.xs, someNode?time.xs, someNode?boolean)."};
        }
        return null;
    }
}
