package org.mapstruct.ap.shaded.freemarker.ext.dom;

import org.mapstruct.ap.shaded.freemarker.template.TemplateModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModelException;
import org.mapstruct.ap.shaded.freemarker.template.TemplateSequenceModel;
import org.w3c.dom.DocumentType;
import org.w3c.dom.ProcessingInstruction;

/* loaded from: classes3.dex */
class DocumentTypeModel extends NodeModel {
    @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateHashModel
    public boolean isEmpty() {
        return true;
    }

    public DocumentTypeModel(DocumentType documentType) {
        super(documentType);
    }

    public String getAsString() {
        return ((ProcessingInstruction) this.node).getData();
    }

    public TemplateSequenceModel getChildren() throws TemplateModelException {
        throw new TemplateModelException("entering the child nodes of a DTD node is not currently supported");
    }

    @Override // org.mapstruct.ap.shaded.freemarker.ext.dom.NodeModel, org.mapstruct.ap.shaded.freemarker.template.TemplateHashModel
    public TemplateModel get(String str) throws TemplateModelException {
        throw new TemplateModelException("accessing properties of a DTD is not currently supported");
    }

    @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateNodeModel
    public String getNodeName() {
        return new StringBuffer("@document_type$").append(((DocumentType) this.node).getNodeName()).toString();
    }
}
