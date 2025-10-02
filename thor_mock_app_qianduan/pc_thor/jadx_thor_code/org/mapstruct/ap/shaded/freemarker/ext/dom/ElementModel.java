package org.mapstruct.ap.shaded.freemarker.ext.dom;

import org.mapstruct.ap.shaded.freemarker.core.Environment;
import org.mapstruct.ap.shaded.freemarker.template.SimpleScalar;
import org.mapstruct.ap.shaded.freemarker.template.Template;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModelException;
import org.mapstruct.ap.shaded.freemarker.template.TemplateScalarModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateSequenceModel;
import org.mapstruct.ap.shaded.freemarker.template.utility.StringUtil;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/* loaded from: classes3.dex */
class ElementModel extends NodeModel implements TemplateScalarModel {
    @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateHashModel
    public boolean isEmpty() {
        return false;
    }

    public ElementModel(Element element) {
        super(element);
    }

    @Override // org.mapstruct.ap.shaded.freemarker.ext.dom.NodeModel, org.mapstruct.ap.shaded.freemarker.template.TemplateHashModel
    public TemplateModel get(String str) throws TemplateModelException {
        if (str.equals("*")) {
            NodeListModel nodeListModel = new NodeListModel(this);
            TemplateSequenceModel childNodes = getChildNodes();
            for (int i = 0; i < childNodes.size(); i++) {
                NodeModel nodeModel = (NodeModel) childNodes.get(i);
                if (nodeModel.node.getNodeType() == 1) {
                    nodeListModel.add(nodeModel);
                }
            }
            return nodeListModel;
        }
        if (str.equals("**")) {
            return new NodeListModel(((Element) this.node).getElementsByTagName("*"), this);
        }
        if (str.startsWith("@")) {
            if (str.equals("@@") || str.equals("@*")) {
                return new NodeListModel(this.node.getAttributes(), this);
            }
            if (str.equals("@@start_tag")) {
                return new SimpleScalar(new NodeOutputter(this.node).getOpeningTag((Element) this.node));
            }
            if (str.equals("@@end_tag")) {
                return new SimpleScalar(new NodeOutputter(this.node).getClosingTag((Element) this.node));
            }
            if (str.equals("@@attributes_markup")) {
                StringBuffer stringBuffer = new StringBuffer();
                new NodeOutputter(this.node).outputContent(this.node.getAttributes(), stringBuffer);
                return new SimpleScalar(stringBuffer.toString().trim());
            }
            if (StringUtil.isXMLID(str.substring(1))) {
                Attr attribute = getAttribute(str.substring(1));
                if (attribute == null) {
                    return new NodeListModel(this);
                }
                return wrap(attribute);
            }
        }
        if (StringUtil.isXMLID(str)) {
            NodeListModel nodeListModelFilterByName = ((NodeListModel) getChildNodes()).filterByName(str);
            return nodeListModelFilterByName.size() == 1 ? nodeListModelFilterByName.get(0) : nodeListModelFilterByName;
        }
        return super.get(str);
    }

    @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateScalarModel
    public String getAsString() throws TemplateModelException {
        NodeList childNodes = this.node.getChildNodes();
        String string = "";
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node nodeItem = childNodes.item(i);
            short nodeType = nodeItem.getNodeType();
            if (nodeType == 1) {
                throw new TemplateModelException(new StringBuffer("Only elements with no child elements can be processed as text.\nThis element with name \"").append(this.node.getNodeName()).append("\" has a child element named: ").append(nodeItem.getNodeName()).toString());
            }
            if (nodeType == 3 || nodeType == 4) {
                string = new StringBuffer().append(string).append(nodeItem.getNodeValue()).toString();
            }
        }
        return string;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateNodeModel
    public String getNodeName() {
        String localName = this.node.getLocalName();
        return (localName == null || localName.equals("")) ? this.node.getNodeName() : localName;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.ext.dom.NodeModel
    String getQualifiedName() {
        String nodeName = getNodeName();
        String nodeNamespace = getNodeNamespace();
        if (nodeNamespace == null || nodeNamespace.length() == 0) {
            return nodeName;
        }
        Environment currentEnvironment = Environment.getCurrentEnvironment();
        String defaultNS = currentEnvironment.getDefaultNS();
        String prefixForNamespace = (defaultNS == null || !defaultNS.equals(nodeNamespace)) ? currentEnvironment.getPrefixForNamespace(nodeNamespace) : Template.DEFAULT_NAMESPACE_PREFIX;
        if (prefixForNamespace == null) {
            return null;
        }
        if (prefixForNamespace.length() > 0) {
            prefixForNamespace = new StringBuffer().append(prefixForNamespace).append(":").toString();
        }
        return new StringBuffer().append(prefixForNamespace).append(nodeName).toString();
    }

    private Attr getAttribute(String str) {
        int iIndexOf;
        String namespaceForPrefix;
        Element element = (Element) this.node;
        Attr attributeNode = element.getAttributeNode(str);
        if (attributeNode != null || (iIndexOf = str.indexOf(58)) <= 0) {
            return attributeNode;
        }
        String strSubstring = str.substring(0, iIndexOf);
        if (strSubstring.equals(Template.DEFAULT_NAMESPACE_PREFIX)) {
            namespaceForPrefix = Environment.getCurrentEnvironment().getDefaultNS();
        } else {
            namespaceForPrefix = Environment.getCurrentEnvironment().getNamespaceForPrefix(strSubstring);
        }
        return namespaceForPrefix != null ? element.getAttributeNodeNS(namespaceForPrefix, str.substring(iIndexOf + 1)) : attributeNode;
    }

    boolean matchesName(String str, Environment environment) {
        return StringUtil.matchesName(str, getNodeName(), getNodeNamespace(), environment);
    }
}
