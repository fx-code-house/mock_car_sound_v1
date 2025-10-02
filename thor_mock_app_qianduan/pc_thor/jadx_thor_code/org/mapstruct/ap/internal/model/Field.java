package org.mapstruct.ap.internal.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import org.mapstruct.ap.internal.model.common.ModelElement;
import org.mapstruct.ap.internal.model.common.Type;

/* loaded from: classes3.dex */
public class Field extends ModelElement {
    private final Type type;
    private boolean typeRequiresImport;
    private boolean used;
    private final String variableName;

    public Field(Type type, String str, boolean z) {
        this.type = type;
        this.variableName = str;
        this.used = z;
        this.typeRequiresImport = z;
    }

    public Field(Type type, String str) {
        this.type = type;
        this.variableName = str;
        this.used = false;
        this.typeRequiresImport = false;
    }

    public Type getType() {
        return this.type;
    }

    public String getVariableName() {
        return this.variableName;
    }

    @Override // org.mapstruct.ap.internal.model.common.ModelElement, org.mapstruct.ap.internal.model.Constructor
    public Set<Type> getImportTypes() {
        return Collections.emptySet();
    }

    public boolean isUsed() {
        return this.used;
    }

    public void setUsed(boolean z) {
        this.used = z;
    }

    public boolean isTypeRequiresImport() {
        return this.typeRequiresImport;
    }

    public void setTypeRequiresImport(boolean z) {
        this.typeRequiresImport = z;
    }

    public int hashCode() {
        String str = this.variableName;
        return 215 + (str != null ? str.hashCode() : 0);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            return Objects.equals(this.variableName, ((Field) obj).variableName);
        }
        return false;
    }

    public static List<String> getFieldNames(Set<Field> set) {
        ArrayList arrayList = new ArrayList(set.size());
        Iterator<Field> it = set.iterator();
        while (it.hasNext()) {
            arrayList.add(it.next().getVariableName());
        }
        return arrayList;
    }
}
