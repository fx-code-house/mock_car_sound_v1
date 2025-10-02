package org.mapstruct.ap.shaded.freemarker.ext.dom;

import org.mapstruct.ap.shaded.freemarker.core.Environment;
import org.mapstruct.ap.shaded.freemarker.template.Template;
import org.mapstruct.ap.shaded.freemarker.template.TemplateScalarModel;
import org.w3c.dom.Attr;

/* loaded from: classes3.dex */
class AttributeNodeModel extends NodeModel implements TemplateScalarModel {
    @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateHashModel
    public boolean isEmpty() {
        return true;
    }

    public AttributeNodeModel(Attr attr) {
        super(attr);
    }

    @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateScalarModel
    public String getAsString() {
        return ((Attr) this.node).getValue();
    }

    @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateNodeModel
    public String getNodeName() {
        String localName = this.node.getLocalName();
        return (localName == null || localName.equals("")) ? this.node.getNodeName() : localName;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.ext.dom.NodeModel
    String getQualifiedName() {
        String namespaceURI = this.node.getNamespaceURI();
        if (namespaceURI == null || namespaceURI.equals("")) {
            return this.node.getNodeName();
        }
        Environment currentEnvironment = Environment.getCurrentEnvironment();
        String prefixForNamespace = namespaceURI.equals(currentEnvironment.getDefaultNS()) ? Template.DEFAULT_NAMESPACE_PREFIX : currentEnvironment.getPrefixForNamespace(namespaceURI);
        if (prefixForNamespace == null) {
            return null;
        }
        return new StringBuffer().append(prefixForNamespace).append(":").append(this.node.getLocalName()).toString();
    }
}
