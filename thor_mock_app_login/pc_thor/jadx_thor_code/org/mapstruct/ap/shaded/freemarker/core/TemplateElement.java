package org.mapstruct.ap.shaded.freemarker.core;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import javax.swing.tree.TreeNode;
import org.mapstruct.ap.shaded.freemarker.template.SimpleSequence;
import org.mapstruct.ap.shaded.freemarker.template.TemplateException;
import org.mapstruct.ap.shaded.freemarker.template.TemplateNodeModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateSequenceModel;

/* loaded from: classes3.dex */
public abstract class TemplateElement extends TemplateObject implements TreeNode {
    TemplateElement nestedBlock;
    List nestedElements;
    TemplateElement parent;

    abstract void accept(Environment environment) throws TemplateException, IOException;

    protected abstract String dump(boolean z);

    public String getNodeNamespace() {
        return null;
    }

    public String getNodeType() {
        return "element";
    }

    public TemplateNodeModel getParentNode() {
        return null;
    }

    boolean heedsOpeningWhitespace() {
        return false;
    }

    boolean heedsTrailingWhitespace() {
        return false;
    }

    boolean isIgnorable() {
        return false;
    }

    boolean isShownInStackTrace() {
        return true;
    }

    public final String getDescription() {
        return dump(false);
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    public final String getCanonicalForm() {
        return dump(true);
    }

    public TemplateSequenceModel getChildNodes() {
        if (this.nestedElements != null) {
            return new SimpleSequence(this.nestedElements);
        }
        SimpleSequence simpleSequence = new SimpleSequence();
        TemplateElement templateElement = this.nestedBlock;
        if (templateElement != null) {
            simpleSequence.add(templateElement);
        }
        return simpleSequence;
    }

    public String getNodeName() {
        String name = getClass().getName();
        return name.substring(name.lastIndexOf(46) + 1);
    }

    public boolean isLeaf() {
        List list;
        return this.nestedBlock == null && ((list = this.nestedElements) == null || list.isEmpty());
    }

    public boolean getAllowsChildren() {
        return !isLeaf();
    }

    public int getIndex(TreeNode treeNode) {
        TemplateElement templateElement = this.nestedBlock;
        if (templateElement instanceof MixedContent) {
            return templateElement.getIndex(treeNode);
        }
        if (templateElement != null) {
            return treeNode == templateElement ? 0 : -1;
        }
        List list = this.nestedElements;
        if (list != null) {
            return list.indexOf(treeNode);
        }
        return -1;
    }

    public int getChildCount() {
        TemplateElement templateElement = this.nestedBlock;
        if (templateElement instanceof MixedContent) {
            return templateElement.getChildCount();
        }
        if (templateElement != null) {
            return 1;
        }
        List list = this.nestedElements;
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    public Enumeration children() {
        TemplateElement templateElement = this.nestedBlock;
        if (templateElement instanceof MixedContent) {
            return templateElement.children();
        }
        if (templateElement != null) {
            return Collections.enumeration(Collections.singletonList(templateElement));
        }
        List list = this.nestedElements;
        if (list != null) {
            return Collections.enumeration(list);
        }
        return Collections.enumeration(Collections.EMPTY_LIST);
    }

    public TreeNode getChildAt(int i) {
        TemplateElement templateElement = this.nestedBlock;
        if (templateElement instanceof MixedContent) {
            return templateElement.getChildAt(i);
        }
        if (templateElement != null) {
            if (i == 0) {
                return templateElement;
            }
            throw new ArrayIndexOutOfBoundsException("invalid index");
        }
        List list = this.nestedElements;
        if (list != null) {
            return (TreeNode) list.get(i);
        }
        throw new ArrayIndexOutOfBoundsException("element has no children");
    }

    public void setChildAt(int i, TemplateElement templateElement) {
        TemplateElement templateElement2 = this.nestedBlock;
        if (templateElement2 instanceof MixedContent) {
            templateElement2.setChildAt(i, templateElement);
            return;
        }
        if (templateElement2 != null) {
            if (i == 0) {
                this.nestedBlock = templateElement;
                templateElement.parent = this;
                return;
            }
            throw new IndexOutOfBoundsException("invalid index");
        }
        List list = this.nestedElements;
        if (list != null) {
            list.set(i, templateElement);
            templateElement.parent = this;
            return;
        }
        throw new IndexOutOfBoundsException("element has no children");
    }

    public TreeNode getParent() {
        return this.parent;
    }

    void setParentRecursively(TemplateElement templateElement) {
        this.parent = templateElement;
        List list = this.nestedElements;
        int size = list == null ? 0 : list.size();
        for (int i = 0; i < size; i++) {
            ((TemplateElement) this.nestedElements.get(i)).setParentRecursively(this);
        }
        TemplateElement templateElement2 = this.nestedBlock;
        if (templateElement2 != null) {
            templateElement2.setParentRecursively(this);
        }
    }

    TemplateElement postParseCleanup(boolean z) throws ParseException {
        if (this.nestedElements != null) {
            for (int i = 0; i < this.nestedElements.size(); i++) {
                TemplateElement templateElementPostParseCleanup = ((TemplateElement) this.nestedElements.get(i)).postParseCleanup(z);
                this.nestedElements.set(i, templateElementPostParseCleanup);
                templateElementPostParseCleanup.parent = this;
            }
            if (z) {
                Iterator it = this.nestedElements.iterator();
                while (it.hasNext()) {
                    if (((TemplateElement) it.next()).isIgnorable()) {
                        it.remove();
                    }
                }
            }
            List list = this.nestedElements;
            if (list instanceof ArrayList) {
                ((ArrayList) list).trimToSize();
            }
        }
        TemplateElement templateElement = this.nestedBlock;
        if (templateElement != null) {
            TemplateElement templateElementPostParseCleanup2 = templateElement.postParseCleanup(z);
            this.nestedBlock = templateElementPostParseCleanup2;
            if (templateElementPostParseCleanup2.isIgnorable()) {
                this.nestedBlock = null;
            } else {
                this.nestedBlock.parent = this;
            }
        }
        return this;
    }

    TemplateElement prevTerminalNode() {
        TemplateElement templateElementPreviousSibling = previousSibling();
        if (templateElementPreviousSibling != null) {
            return templateElementPreviousSibling.getLastLeaf();
        }
        TemplateElement templateElement = this.parent;
        if (templateElement != null) {
            return templateElement.prevTerminalNode();
        }
        return null;
    }

    TemplateElement nextTerminalNode() {
        TemplateElement templateElementNextSibling = nextSibling();
        if (templateElementNextSibling != null) {
            return templateElementNextSibling.getFirstLeaf();
        }
        TemplateElement templateElement = this.parent;
        if (templateElement != null) {
            return templateElement.nextTerminalNode();
        }
        return null;
    }

    TemplateElement previousSibling() {
        List list;
        TemplateElement templateElement = this.parent;
        if (templateElement == null || (list = templateElement.nestedElements) == null) {
            return null;
        }
        for (int size = list.size() - 1; size >= 0; size--) {
            if (list.get(size) == this) {
                if (size > 0) {
                    return (TemplateElement) list.get(size - 1);
                }
                return null;
            }
        }
        return null;
    }

    TemplateElement nextSibling() {
        List list;
        TemplateElement templateElement = this.parent;
        if (templateElement == null || (list = templateElement.nestedElements) == null) {
            return null;
        }
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) == this) {
                int i2 = i + 1;
                if (i2 < list.size()) {
                    return (TemplateElement) list.get(i2);
                }
                return null;
            }
        }
        return null;
    }

    private TemplateElement getFirstChild() {
        TemplateElement templateElement = this.nestedBlock;
        if (templateElement != null) {
            return templateElement;
        }
        List list = this.nestedElements;
        if (list == null || list.size() <= 0) {
            return null;
        }
        return (TemplateElement) this.nestedElements.get(0);
    }

    private TemplateElement getLastChild() {
        TemplateElement templateElement = this.nestedBlock;
        if (templateElement != null) {
            return templateElement;
        }
        List list = this.nestedElements;
        if (list == null || list.size() <= 0) {
            return null;
        }
        return (TemplateElement) this.nestedElements.get(r0.size() - 1);
    }

    private TemplateElement getFirstLeaf() {
        TemplateElement firstChild = this;
        while (!firstChild.isLeaf() && !(firstChild instanceof Macro) && !(firstChild instanceof BlockAssignment)) {
            firstChild = firstChild.getFirstChild();
        }
        return firstChild;
    }

    private TemplateElement getLastLeaf() {
        TemplateElement lastChild = this;
        while (!lastChild.isLeaf() && !(lastChild instanceof Macro) && !(lastChild instanceof BlockAssignment)) {
            lastChild = lastChild.getLastChild();
        }
        return lastChild;
    }
}
