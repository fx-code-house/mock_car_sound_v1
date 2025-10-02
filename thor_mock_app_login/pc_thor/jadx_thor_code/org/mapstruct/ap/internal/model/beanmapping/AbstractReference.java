package org.mapstruct.ap.internal.model.beanmapping;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.mapstruct.ap.internal.model.common.Parameter;
import org.mapstruct.ap.internal.util.Collections;
import org.mapstruct.ap.internal.util.Strings;

/* loaded from: classes3.dex */
public abstract class AbstractReference {
    private final boolean isValid;
    private final Parameter parameter;
    private final List<PropertyEntry> propertyEntries;

    protected AbstractReference(Parameter parameter, List<PropertyEntry> list, boolean z) {
        this.parameter = parameter;
        this.propertyEntries = list;
        this.isValid = z;
    }

    public Parameter getParameter() {
        return this.parameter;
    }

    public List<PropertyEntry> getPropertyEntries() {
        return this.propertyEntries;
    }

    public boolean isValid() {
        return this.isValid;
    }

    public List<String> getElementNames() {
        ArrayList arrayList = new ArrayList();
        Parameter parameter = this.parameter;
        if (parameter != null) {
            arrayList.add(parameter.getName());
        }
        Iterator<PropertyEntry> it = this.propertyEntries.iterator();
        while (it.hasNext()) {
            arrayList.add(it.next().getName());
        }
        return arrayList;
    }

    public PropertyEntry getShallowestProperty() {
        if (this.propertyEntries.isEmpty()) {
            return null;
        }
        return (PropertyEntry) Collections.first(this.propertyEntries);
    }

    public String getShallowestPropertyName() {
        if (this.propertyEntries.isEmpty()) {
            return null;
        }
        return ((PropertyEntry) Collections.first(this.propertyEntries)).getName();
    }

    public PropertyEntry getDeepestProperty() {
        if (this.propertyEntries.isEmpty()) {
            return null;
        }
        return (PropertyEntry) Collections.last(this.propertyEntries);
    }

    public String getDeepestPropertyName() {
        if (this.propertyEntries.isEmpty()) {
            return null;
        }
        return ((PropertyEntry) Collections.last(this.propertyEntries)).getName();
    }

    public boolean isNested() {
        return this.propertyEntries.size() > 1;
    }

    public String toString() {
        if (!this.isValid) {
            return "invalid";
        }
        if (this.propertyEntries.isEmpty()) {
            Parameter parameter = this.parameter;
            return parameter != null ? String.format("parameter \"%s %s\"", parameter.getType().describe(), this.parameter.getName()) : "";
        }
        if (this.propertyEntries.size() == 1) {
            PropertyEntry propertyEntry = this.propertyEntries.get(0);
            return String.format("property \"%s %s\"", propertyEntry.getType().describe(), propertyEntry.getName());
        }
        List<PropertyEntry> list = this.propertyEntries;
        return String.format("property \"%s %s\"", list.get(list.size() - 1).getType().describe(), Strings.join(getElementNames(), "."));
    }
}
