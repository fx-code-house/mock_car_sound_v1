package org.mapstruct.ap.shaded.freemarker.ext.dom;

import java.util.HashMap;
import kotlin.text.Typography;
import org.mapstruct.ap.shaded.freemarker.core.Environment;
import org.mapstruct.ap.shaded.freemarker.template.Template;
import org.mapstruct.ap.shaded.freemarker.template.utility.StringUtil;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/* loaded from: classes3.dex */
class NodeOutputter {
    private Element contextNode;
    private String defaultNS;
    private Environment env;
    private boolean explicitDefaultNSPrefix;
    private boolean hasDefaultNS;
    private String namespaceDecl;
    private HashMap namespacesToPrefixLookup = new HashMap();

    NodeOutputter(Node node) {
        if (node instanceof Element) {
            setContext((Element) node);
        } else if (node instanceof Attr) {
            setContext(((Attr) node).getOwnerElement());
        } else if (node instanceof Document) {
            setContext(((Document) node).getDocumentElement());
        }
    }

    private void setContext(Element element) {
        this.contextNode = element;
        Environment currentEnvironment = Environment.getCurrentEnvironment();
        this.env = currentEnvironment;
        String defaultNS = currentEnvironment.getDefaultNS();
        this.defaultNS = defaultNS;
        this.hasDefaultNS = defaultNS != null && defaultNS.length() > 0;
        this.namespacesToPrefixLookup.put(null, "");
        this.namespacesToPrefixLookup.put("", "");
        buildPrefixLookup(element);
        if (!this.explicitDefaultNSPrefix && this.hasDefaultNS) {
            this.namespacesToPrefixLookup.put(this.defaultNS, "");
        }
        constructNamespaceDecl();
    }

    private void buildPrefixLookup(Node node) {
        String namespaceURI = node.getNamespaceURI();
        if (namespaceURI != null && namespaceURI.length() > 0) {
            this.namespacesToPrefixLookup.put(namespaceURI, this.env.getPrefixForNamespace(namespaceURI));
        } else if (this.hasDefaultNS && node.getNodeType() == 1) {
            this.namespacesToPrefixLookup.put(this.defaultNS, Template.DEFAULT_NAMESPACE_PREFIX);
            this.explicitDefaultNSPrefix = true;
        } else if (node.getNodeType() == 2 && this.hasDefaultNS && this.defaultNS.equals(namespaceURI)) {
            this.namespacesToPrefixLookup.put(this.defaultNS, Template.DEFAULT_NAMESPACE_PREFIX);
            this.explicitDefaultNSPrefix = true;
        }
        NodeList childNodes = node.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            buildPrefixLookup(childNodes.item(i));
        }
    }

    private void constructNamespaceDecl() {
        StringBuffer stringBuffer = new StringBuffer();
        if (this.explicitDefaultNSPrefix) {
            stringBuffer.append(" xmlns=\"");
            stringBuffer.append(this.defaultNS);
            stringBuffer.append("\"");
        }
        for (String str : this.namespacesToPrefixLookup.keySet()) {
            if (str != null && str.length() != 0) {
                String str2 = (String) this.namespacesToPrefixLookup.get(str);
                if (str2 == null) {
                    int i = 0;
                    while (true) {
                        if (i >= 26) {
                            break;
                        }
                        String str3 = new String(new char[]{(char) (i + 97)});
                        if (this.env.getNamespaceForPrefix(str3) == null) {
                            str2 = str3;
                            break;
                        } else {
                            i++;
                            str2 = null;
                        }
                    }
                    if (str2 == null) {
                        throw new RuntimeException("This will almost never happen!");
                    }
                    this.namespacesToPrefixLookup.put(str, str2);
                }
                stringBuffer.append(" xmlns");
                if (str2.length() > 0) {
                    stringBuffer.append(":");
                    stringBuffer.append(str2);
                }
                stringBuffer.append("=\"");
                stringBuffer.append(str);
                stringBuffer.append("\"");
            }
        }
        this.namespaceDecl = stringBuffer.toString();
    }

    private void outputQualifiedName(Node node, StringBuffer stringBuffer) {
        String namespaceURI = node.getNamespaceURI();
        if (namespaceURI == null || namespaceURI.length() == 0) {
            stringBuffer.append(node.getNodeName());
            return;
        }
        String str = (String) this.namespacesToPrefixLookup.get(namespaceURI);
        if (str == null) {
            stringBuffer.append(node.getNodeName());
            return;
        }
        if (str.length() > 0) {
            stringBuffer.append(str);
            stringBuffer.append(':');
        }
        stringBuffer.append(node.getLocalName());
    }

    void outputContent(Node node, StringBuffer stringBuffer) {
        switch (node.getNodeType()) {
            case 1:
                stringBuffer.append(Typography.less);
                outputQualifiedName(node, stringBuffer);
                if (node == this.contextNode) {
                    stringBuffer.append(this.namespaceDecl);
                }
                outputContent(node.getAttributes(), stringBuffer);
                if (node.getChildNodes().getLength() == 0) {
                    stringBuffer.append(" />");
                    break;
                } else {
                    stringBuffer.append(Typography.greater);
                    outputContent(node.getChildNodes(), stringBuffer);
                    stringBuffer.append("</");
                    outputQualifiedName(node, stringBuffer);
                    stringBuffer.append(Typography.greater);
                    break;
                }
            case 2:
                if (((Attr) node).getSpecified()) {
                    stringBuffer.append(' ');
                    outputQualifiedName(node, stringBuffer);
                    stringBuffer.append("=\"").append(StringUtil.XMLEncQAttr(node.getNodeValue())).append(Typography.quote);
                    break;
                }
                break;
            case 3:
            case 4:
                stringBuffer.append(StringUtil.XMLEncNQG(node.getNodeValue()));
                break;
            case 5:
                stringBuffer.append(Typography.amp).append(node.getNodeName()).append(';');
                break;
            case 6:
                outputContent(node.getChildNodes(), stringBuffer);
                break;
            case 7:
                stringBuffer.append("<?").append(node.getNodeName()).append(' ').append(node.getNodeValue()).append("?>");
                break;
            case 8:
                stringBuffer.append("<!--").append(node.getNodeValue()).append("-->");
                break;
            case 9:
                outputContent(node.getChildNodes(), stringBuffer);
                break;
            case 10:
                stringBuffer.append("<!DOCTYPE ").append(node.getNodeName());
                DocumentType documentType = (DocumentType) node;
                if (documentType.getPublicId() != null) {
                    stringBuffer.append(" PUBLIC \"").append(documentType.getPublicId()).append(Typography.quote);
                }
                if (documentType.getSystemId() != null) {
                    stringBuffer.append(" \"").append(documentType.getSystemId()).append(Typography.quote);
                }
                if (documentType.getInternalSubset() != null) {
                    stringBuffer.append(" [").append(documentType.getInternalSubset()).append(']');
                }
                stringBuffer.append(Typography.greater);
                break;
        }
    }

    void outputContent(NodeList nodeList, StringBuffer stringBuffer) {
        for (int i = 0; i < nodeList.getLength(); i++) {
            outputContent(nodeList.item(i), stringBuffer);
        }
    }

    void outputContent(NamedNodeMap namedNodeMap, StringBuffer stringBuffer) {
        for (int i = 0; i < namedNodeMap.getLength(); i++) {
            Node nodeItem = namedNodeMap.item(i);
            if (nodeItem.getNodeType() != 2 || (!nodeItem.getNodeName().startsWith("xmlns:") && !nodeItem.getNodeName().equals("xmlns"))) {
                outputContent(nodeItem, stringBuffer);
            }
        }
    }

    String getOpeningTag(Element element) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(Typography.less);
        outputQualifiedName(element, stringBuffer);
        stringBuffer.append(this.namespaceDecl);
        outputContent(element.getAttributes(), stringBuffer);
        stringBuffer.append(Typography.greater);
        return stringBuffer.toString();
    }

    String getClosingTag(Element element) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("</");
        outputQualifiedName(element, stringBuffer);
        stringBuffer.append(Typography.greater);
        return stringBuffer.toString();
    }
}
