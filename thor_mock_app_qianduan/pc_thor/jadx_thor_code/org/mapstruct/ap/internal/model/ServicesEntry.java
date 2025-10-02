package org.mapstruct.ap.internal.model;

import java.util.Collections;
import java.util.Set;
import org.mapstruct.ap.internal.model.common.ModelElement;
import org.mapstruct.ap.internal.model.common.Type;

/* loaded from: classes3.dex */
public class ServicesEntry extends ModelElement {
    private final String implementationName;
    private final String implementationPackage;
    private final String name;
    private final String packageName;

    public ServicesEntry(String str, String str2, String str3, String str4) {
        this.packageName = str;
        this.name = str2;
        this.implementationPackage = str3;
        this.implementationName = str4;
    }

    @Override // org.mapstruct.ap.internal.model.common.ModelElement, org.mapstruct.ap.internal.model.Constructor
    public Set<Type> getImportTypes() {
        return Collections.emptySet();
    }

    public String getPackageName() {
        return this.packageName;
    }

    public String getName() {
        return this.name;
    }

    public String getImplementationPackage() {
        return this.implementationPackage;
    }

    public String getImplementationName() {
        return this.implementationName;
    }
}
