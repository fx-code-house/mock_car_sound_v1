package org.mapstruct.ap.shaded.freemarker.core;

import org.mapstruct.ap.shaded.freemarker.template.Template;

/* loaded from: classes3.dex */
public abstract class TemplateObject {
    static final int RUNTIME_EVAL_LINE_DISPLACEMENT = -1000000000;
    int beginColumn;
    int beginLine;
    int endColumn;
    int endLine;
    private Template template;

    public abstract String getCanonicalForm();

    abstract String getNodeTypeSymbol();

    abstract int getParameterCount();

    abstract ParameterRole getParameterRole(int i);

    abstract Object getParameterValue(int i);

    final void setLocation(Template template, Token token, Token token2) throws ParseException {
        setLocation(template, token.beginColumn, token.beginLine, token2.endColumn, token2.endLine);
    }

    final void setLocation(Template template, Token token, TemplateObject templateObject) throws ParseException {
        setLocation(template, token.beginColumn, token.beginLine, templateObject.endColumn, templateObject.endLine);
    }

    final void setLocation(Template template, TemplateObject templateObject, Token token) throws ParseException {
        setLocation(template, templateObject.beginColumn, templateObject.beginLine, token.endColumn, token.endLine);
    }

    final void setLocation(Template template, TemplateObject templateObject, TemplateObject templateObject2) throws ParseException {
        setLocation(template, templateObject.beginColumn, templateObject.beginLine, templateObject2.endColumn, templateObject2.endLine);
    }

    void setLocation(Template template, int i, int i2, int i3, int i4) throws ParseException {
        this.template = template;
        this.beginColumn = i;
        this.beginLine = i2;
        this.endColumn = i3;
        this.endLine = i4;
    }

    public final int getBeginColumn() {
        return this.beginColumn;
    }

    public final int getBeginLine() {
        return this.beginLine;
    }

    public final int getEndColumn() {
        return this.endColumn;
    }

    public final int getEndLine() {
        return this.endLine;
    }

    public String getStartLocation() {
        return MessageUtil.formatLocationForEvaluationError(this.template, this.beginLine, this.beginColumn);
    }

    public String getStartLocationQuoted() {
        return getStartLocation();
    }

    public String getEndLocation() {
        return MessageUtil.formatLocationForEvaluationError(this.template, this.endLine, this.endColumn);
    }

    public String getEndLocationQuoted() {
        return getEndLocation();
    }

    public final String getSource() {
        Template template = this.template;
        String source = template != null ? template.getSource(this.beginColumn, this.beginLine, this.endColumn, this.endLine) : null;
        return source != null ? source : getCanonicalForm();
    }

    public String toString() {
        String source;
        try {
            source = getSource();
        } catch (Exception unused) {
            source = null;
        }
        return source != null ? source : getCanonicalForm();
    }

    public boolean contains(int i, int i2) {
        int i3;
        int i4 = this.beginLine;
        if (i2 < i4 || i2 > (i3 = this.endLine)) {
            return false;
        }
        if (i2 != i4 || i >= this.beginColumn) {
            return i2 != i3 || i <= this.endColumn;
        }
        return false;
    }

    public Template getTemplate() {
        return this.template;
    }

    TemplateObject copyLocationFrom(TemplateObject templateObject) {
        this.template = templateObject.template;
        this.beginColumn = templateObject.beginColumn;
        this.beginLine = templateObject.beginLine;
        this.endColumn = templateObject.endColumn;
        this.endLine = templateObject.endLine;
        return this;
    }
}
