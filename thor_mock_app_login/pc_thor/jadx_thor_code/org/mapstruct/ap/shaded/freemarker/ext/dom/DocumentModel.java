package org.mapstruct.ap.shaded.freemarker.ext.dom;

import org.mapstruct.ap.shaded.freemarker.core.Environment;
import org.mapstruct.ap.shaded.freemarker.template.TemplateHashModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModelException;
import org.mapstruct.ap.shaded.freemarker.template.utility.StringUtil;
import org.w3c.dom.Document;

/* loaded from: classes3.dex */
class DocumentModel extends NodeModel implements TemplateHashModel {
    private ElementModel rootElement;

    @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateNodeModel
    public String getNodeName() {
        return "@document";
    }

    @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateHashModel
    public boolean isEmpty() {
        return false;
    }

    DocumentModel(Document document) {
        super(document);
    }

    @Override // org.mapstruct.ap.shaded.freemarker.ext.dom.NodeModel, org.mapstruct.ap.shaded.freemarker.template.TemplateHashModel
    public TemplateModel get(String str) throws TemplateModelException {
        if (str.equals("*")) {
            return getRootElement();
        }
        if (str.equals("**")) {
            return new NodeListModel(((Document) this.node).getElementsByTagName("*"), this);
        }
        if (StringUtil.isXMLID(str)) {
            ElementModel elementModel = (ElementModel) NodeModel.wrap(((Document) this.node).getDocumentElement());
            return elementModel.matchesName(str, Environment.getCurrentEnvironment()) ? elementModel : new NodeListModel(this);
        }
        return super.get(str);
    }

    ElementModel getRootElement() {
        if (this.rootElement == null) {
            this.rootElement = (ElementModel) wrap(((Document) this.node).getDocumentElement());
        }
        return this.rootElement;
    }
}
