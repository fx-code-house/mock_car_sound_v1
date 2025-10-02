package org.mapstruct.ap.shaded.freemarker.core;

import java.util.List;
import org.mapstruct.ap.shaded.freemarker.template.SimpleScalar;
import org.mapstruct.ap.shaded.freemarker.template.SimpleSequence;
import org.mapstruct.ap.shaded.freemarker.template.TemplateMethodModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModelException;
import org.mapstruct.ap.shaded.freemarker.template.TemplateNodeModel;
import org.mapstruct.ap.shaded.freemarker.template.utility.StringUtil;

/* loaded from: classes3.dex */
class BuiltInsForNodes {

    static class ancestorsBI extends BuiltInForNode {
        ancestorsBI() {
        }

        @Override // org.mapstruct.ap.shaded.freemarker.core.BuiltInForNode
        TemplateModel calculateResult(TemplateNodeModel templateNodeModel, Environment environment) throws TemplateModelException {
            AncestorSequence ancestorSequence = new AncestorSequence(environment);
            for (TemplateNodeModel parentNode = templateNodeModel.getParentNode(); parentNode != null; parentNode = parentNode.getParentNode()) {
                ancestorSequence.add(parentNode);
            }
            return ancestorSequence;
        }
    }

    static class childrenBI extends BuiltInForNode {
        childrenBI() {
        }

        @Override // org.mapstruct.ap.shaded.freemarker.core.BuiltInForNode
        TemplateModel calculateResult(TemplateNodeModel templateNodeModel, Environment environment) throws TemplateModelException {
            return templateNodeModel.getChildNodes();
        }
    }

    static class node_nameBI extends BuiltInForNode {
        node_nameBI() {
        }

        @Override // org.mapstruct.ap.shaded.freemarker.core.BuiltInForNode
        TemplateModel calculateResult(TemplateNodeModel templateNodeModel, Environment environment) throws TemplateModelException {
            return new SimpleScalar(templateNodeModel.getNodeName());
        }
    }

    static class node_namespaceBI extends BuiltInForNode {
        node_namespaceBI() {
        }

        @Override // org.mapstruct.ap.shaded.freemarker.core.BuiltInForNode
        TemplateModel calculateResult(TemplateNodeModel templateNodeModel, Environment environment) throws TemplateModelException {
            String nodeNamespace = templateNodeModel.getNodeNamespace();
            if (nodeNamespace == null) {
                return null;
            }
            return new SimpleScalar(nodeNamespace);
        }
    }

    static class node_typeBI extends BuiltInForNode {
        node_typeBI() {
        }

        @Override // org.mapstruct.ap.shaded.freemarker.core.BuiltInForNode
        TemplateModel calculateResult(TemplateNodeModel templateNodeModel, Environment environment) throws TemplateModelException {
            return new SimpleScalar(templateNodeModel.getNodeType());
        }
    }

    static class parentBI extends BuiltInForNode {
        parentBI() {
        }

        @Override // org.mapstruct.ap.shaded.freemarker.core.BuiltInForNode
        TemplateModel calculateResult(TemplateNodeModel templateNodeModel, Environment environment) throws TemplateModelException {
            return templateNodeModel.getParentNode();
        }
    }

    static class rootBI extends BuiltInForNode {
        rootBI() {
        }

        @Override // org.mapstruct.ap.shaded.freemarker.core.BuiltInForNode
        TemplateModel calculateResult(TemplateNodeModel templateNodeModel, Environment environment) throws TemplateModelException {
            TemplateNodeModel parentNode = templateNodeModel.getParentNode();
            while (true) {
                TemplateNodeModel templateNodeModel2 = parentNode;
                TemplateNodeModel templateNodeModel3 = templateNodeModel;
                templateNodeModel = templateNodeModel2;
                if (templateNodeModel == null) {
                    return templateNodeModel3;
                }
                parentNode = templateNodeModel.getParentNode();
            }
        }
    }

    private BuiltInsForNodes() {
    }

    static class AncestorSequence extends SimpleSequence implements TemplateMethodModel {
        private Environment env;

        AncestorSequence(Environment environment) {
            this.env = environment;
        }

        @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateMethodModel
        public Object exec(List list) throws TemplateModelException {
            if (list == null || list.isEmpty()) {
                return this;
            }
            AncestorSequence ancestorSequence = new AncestorSequence(this.env);
            for (int i = 0; i < size(); i++) {
                TemplateNodeModel templateNodeModel = (TemplateNodeModel) get(i);
                String nodeName = templateNodeModel.getNodeName();
                String nodeNamespace = templateNodeModel.getNodeNamespace();
                if (nodeNamespace != null) {
                    int i2 = 0;
                    while (true) {
                        if (i2 >= list.size()) {
                            break;
                        }
                        if (StringUtil.matchesName((String) list.get(i2), nodeName, nodeNamespace, this.env)) {
                            ancestorSequence.add(templateNodeModel);
                            break;
                        }
                        i2++;
                    }
                } else if (list.contains(nodeName)) {
                    ancestorSequence.add(templateNodeModel);
                }
            }
            return ancestorSequence;
        }
    }
}
