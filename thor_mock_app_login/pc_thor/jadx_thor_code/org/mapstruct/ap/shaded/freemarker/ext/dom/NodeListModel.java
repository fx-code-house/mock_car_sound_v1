package org.mapstruct.ap.shaded.freemarker.ext.dom;

import java.util.ArrayList;
import java.util.List;
import org.mapstruct.ap.shaded.freemarker.core.Environment;
import org.mapstruct.ap.shaded.freemarker.core._UnexpectedTypeErrorExplainerTemplateModel;
import org.mapstruct.ap.shaded.freemarker.template.ObjectWrapper;
import org.mapstruct.ap.shaded.freemarker.template.SimpleScalar;
import org.mapstruct.ap.shaded.freemarker.template.SimpleSequence;
import org.mapstruct.ap.shaded.freemarker.template.TemplateHashModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModelException;
import org.mapstruct.ap.shaded.freemarker.template.TemplateScalarModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateSequenceModel;
import org.mapstruct.ap.shaded.freemarker.template.utility.StringUtil;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/* loaded from: classes3.dex */
class NodeListModel extends SimpleSequence implements TemplateHashModel, _UnexpectedTypeErrorExplainerTemplateModel {
    static /* synthetic */ Class class$freemarker$template$TemplateBooleanModel;
    static /* synthetic */ Class class$freemarker$template$TemplateDateModel;
    static /* synthetic */ Class class$freemarker$template$TemplateNumberModel;
    static /* synthetic */ Class class$freemarker$template$TemplateScalarModel;
    private static ObjectWrapper nodeWrapper = new ObjectWrapper() { // from class: org.mapstruct.ap.shaded.freemarker.ext.dom.NodeListModel.1
        @Override // org.mapstruct.ap.shaded.freemarker.template.ObjectWrapper
        public TemplateModel wrap(Object obj) {
            if (obj instanceof NodeModel) {
                return (NodeModel) obj;
            }
            return NodeModel.wrap((Node) obj);
        }
    };
    NodeModel contextNode;
    XPathSupport xpathSupport;

    NodeListModel(Node node) {
        this(NodeModel.wrap(node));
    }

    NodeListModel(NodeModel nodeModel) {
        super(nodeWrapper);
        this.contextNode = nodeModel;
    }

    NodeListModel(NodeList nodeList, NodeModel nodeModel) {
        super(nodeWrapper);
        for (int i = 0; i < nodeList.getLength(); i++) {
            this.list.add(nodeList.item(i));
        }
        this.contextNode = nodeModel;
    }

    NodeListModel(NamedNodeMap namedNodeMap, NodeModel nodeModel) {
        super(nodeWrapper);
        for (int i = 0; i < namedNodeMap.getLength(); i++) {
            this.list.add(namedNodeMap.item(i));
        }
        this.contextNode = nodeModel;
    }

    NodeListModel(List list, NodeModel nodeModel) {
        super(list, nodeWrapper);
        this.contextNode = nodeModel;
    }

    NodeListModel filterByName(String str) throws TemplateModelException {
        NodeListModel nodeListModel = new NodeListModel(this.contextNode);
        int size = size();
        if (size == 0) {
            return nodeListModel;
        }
        Environment currentEnvironment = Environment.getCurrentEnvironment();
        for (int i = 0; i < size; i++) {
            NodeModel nodeModel = (NodeModel) get(i);
            if ((nodeModel instanceof ElementModel) && ((ElementModel) nodeModel).matchesName(str, currentEnvironment)) {
                nodeListModel.add(nodeModel);
            }
        }
        return nodeListModel;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateHashModel
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateHashModel
    public TemplateModel get(String str) throws TemplateModelException {
        if (size() == 1) {
            return ((NodeModel) get(0)).get(str);
        }
        if (str.startsWith("@@") && (str.equals("@@markup") || str.equals("@@nested_markup") || str.equals("@@text"))) {
            StringBuffer stringBuffer = new StringBuffer();
            for (int i = 0; i < size(); i++) {
                stringBuffer.append(((TemplateScalarModel) ((NodeModel) get(i)).get(str)).getAsString());
            }
            return new SimpleScalar(stringBuffer.toString());
        }
        if (StringUtil.isXMLID(str) || ((str.startsWith("@") && StringUtil.isXMLID(str.substring(1))) || str.equals("*") || str.equals("**") || str.equals("@@") || str.equals("@*"))) {
            NodeListModel nodeListModel = new NodeListModel(this.contextNode);
            for (int i2 = 0; i2 < size(); i2++) {
                NodeModel nodeModel = (NodeModel) get(i2);
                if (nodeModel instanceof ElementModel) {
                    TemplateSequenceModel templateSequenceModel = (TemplateSequenceModel) ((ElementModel) nodeModel).get(str);
                    int size = templateSequenceModel == null ? 0 : templateSequenceModel.size();
                    for (int i3 = 0; i3 < size; i3++) {
                        nodeListModel.add(templateSequenceModel.get(i3));
                    }
                }
            }
            return nodeListModel.size() == 1 ? nodeListModel.get(0) : nodeListModel;
        }
        XPathSupport xPathSupport = getXPathSupport();
        if (xPathSupport != null) {
            return xPathSupport.executeQuery(size() == 0 ? null : rawNodeList(), str);
        }
        throw new TemplateModelException(new StringBuffer("Key: '").append(str).append("' is not legal for a node sequence (").append(getClass().getName()).append("). This node sequence contains ").append(size()).append(" node(s). Some keys are valid only for node sequences of size 1. If you use Xalan (instead of Jaxen), XPath expression keys work only with node lists of size 1.").toString());
    }

    private List rawNodeList() throws TemplateModelException {
        int size = size();
        ArrayList arrayList = new ArrayList(size);
        for (int i = 0; i < size; i++) {
            arrayList.add(((NodeModel) get(i)).node);
        }
        return arrayList;
    }

    XPathSupport getXPathSupport() throws TemplateModelException {
        if (this.xpathSupport == null) {
            NodeModel nodeModel = this.contextNode;
            if (nodeModel != null) {
                this.xpathSupport = nodeModel.getXPathSupport();
            } else if (size() > 0) {
                this.xpathSupport = ((NodeModel) get(0)).getXPathSupport();
            }
        }
        return this.xpathSupport;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.mapstruct.ap.shaded.freemarker.core._UnexpectedTypeErrorExplainerTemplateModel
    public Object[] explainTypeError(Class[] clsArr) throws Throwable {
        for (Class cls : clsArr) {
            Class cls2 = class$freemarker$template$TemplateScalarModel;
            Class cls3 = cls2;
            if (cls2 == null) {
                Class clsClass$ = class$("org.mapstruct.ap.shaded.freemarker.template.TemplateScalarModel");
                class$freemarker$template$TemplateScalarModel = clsClass$;
                cls3 = clsClass$;
            }
            if (!cls3.isAssignableFrom(cls)) {
                Class cls4 = class$freemarker$template$TemplateDateModel;
                Class cls5 = cls4;
                if (cls4 == null) {
                    Class clsClass$2 = class$("org.mapstruct.ap.shaded.freemarker.template.TemplateDateModel");
                    class$freemarker$template$TemplateDateModel = clsClass$2;
                    cls5 = clsClass$2;
                }
                if (!cls5.isAssignableFrom(cls)) {
                    Class cls6 = class$freemarker$template$TemplateNumberModel;
                    Class cls7 = cls6;
                    if (cls6 == null) {
                        Class clsClass$3 = class$("org.mapstruct.ap.shaded.freemarker.template.TemplateNumberModel");
                        class$freemarker$template$TemplateNumberModel = clsClass$3;
                        cls7 = clsClass$3;
                    }
                    if (!cls7.isAssignableFrom(cls)) {
                        Class cls8 = class$freemarker$template$TemplateBooleanModel;
                        Class cls9 = cls8;
                        if (cls8 == null) {
                            Class clsClass$4 = class$("org.mapstruct.ap.shaded.freemarker.template.TemplateBooleanModel");
                            class$freemarker$template$TemplateBooleanModel = clsClass$4;
                            cls9 = clsClass$4;
                        }
                        if (!cls9.isAssignableFrom(cls)) {
                        }
                    }
                }
            }
            Object[] objArr = new Object[4];
            objArr[0] = "This XML query result can't be used as string because for that it had to contain exactly 1 XML node, but it contains ";
            objArr[1] = new Integer(size());
            objArr[2] = " nodes. That is, the constructing XML query has found ";
            objArr[3] = isEmpty() ? "no matches." : "multiple matches.";
            return objArr;
        }
        return null;
    }

    static /* synthetic */ Class class$(String str) throws Throwable {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError().initCause(e);
        }
    }
}
