package org.mapstruct.ap.shaded.freemarker.ext.dom;

import org.mapstruct.ap.shaded.freemarker.template.TemplateScalarModel;
import org.w3c.dom.CharacterData;
import org.w3c.dom.Comment;

/* loaded from: classes3.dex */
class CharacterDataNodeModel extends NodeModel implements TemplateScalarModel {
    @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateHashModel
    public boolean isEmpty() {
        return true;
    }

    public CharacterDataNodeModel(CharacterData characterData) {
        super(characterData);
    }

    @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateScalarModel
    public String getAsString() {
        return ((CharacterData) this.node).getData();
    }

    @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateNodeModel
    public String getNodeName() {
        return this.node instanceof Comment ? "@comment" : "@text";
    }
}
