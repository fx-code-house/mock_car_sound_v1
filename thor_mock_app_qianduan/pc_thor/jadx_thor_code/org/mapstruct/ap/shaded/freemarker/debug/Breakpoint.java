package org.mapstruct.ap.shaded.freemarker.debug;

import java.io.Serializable;

/* loaded from: classes3.dex */
public class Breakpoint implements Serializable, Comparable {
    private static final long serialVersionUID = 1;
    private final int line;
    private final String templateName;

    public Breakpoint(String str, int i) {
        this.templateName = str;
        this.line = i;
    }

    public int getLine() {
        return this.line;
    }

    public String getTemplateName() {
        return this.templateName;
    }

    public int hashCode() {
        return this.templateName.hashCode() + (this.line * 31);
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Breakpoint)) {
            return false;
        }
        Breakpoint breakpoint = (Breakpoint) obj;
        return breakpoint.templateName.equals(this.templateName) && breakpoint.line == this.line;
    }

    @Override // java.lang.Comparable
    public int compareTo(Object obj) {
        Breakpoint breakpoint = (Breakpoint) obj;
        int iCompareTo = this.templateName.compareTo(breakpoint.templateName);
        return iCompareTo == 0 ? this.line - breakpoint.line : iCompareTo;
    }

    public String getLocationString() {
        return new StringBuffer().append(this.templateName).append(":").append(this.line).toString();
    }
}
