package org.mapstruct.ap.shaded.freemarker.ext.dom;

import org.mapstruct.ap.shaded.freemarker.template.TemplateScalarModel;
import org.w3c.dom.ProcessingInstruction;

/* loaded from: classes3.dex */
class PINodeModel extends NodeModel implements TemplateScalarModel {
    @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateHashModel
    public boolean isEmpty() {
        return true;
    }

    public PINodeModel(ProcessingInstruction processingInstruction) {
        super(processingInstruction);
    }

    @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateScalarModel
    public String getAsString() {
        return ((ProcessingInstruction) this.node).getData();
    }

    @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateNodeModel
    public String getNodeName() {
        return new StringBuffer("@pi$").append(((ProcessingInstruction) this.node).getTarget()).toString();
    }
}
