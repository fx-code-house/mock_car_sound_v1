package org.mapstruct.ap.internal.model.beanmapping;

import java.util.Arrays;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.util.Strings;
import org.mapstruct.ap.internal.util.accessor.Accessor;

/* loaded from: classes3.dex */
public class PropertyEntry {
    private final String[] fullName;
    private final Accessor presenceChecker;
    private final Accessor readAccessor;
    private final Type type;

    private PropertyEntry(String[] strArr, Accessor accessor, Accessor accessor2, Type type) {
        this.fullName = strArr;
        this.readAccessor = accessor;
        this.presenceChecker = accessor2;
        this.type = type;
    }

    public static PropertyEntry forSourceReference(String[] strArr, Accessor accessor, Accessor accessor2, Type type) {
        return new PropertyEntry(strArr, accessor, accessor2, type);
    }

    public String getName() {
        return this.fullName[r0.length - 1];
    }

    public Accessor getReadAccessor() {
        return this.readAccessor;
    }

    public Accessor getPresenceChecker() {
        return this.presenceChecker;
    }

    public Type getType() {
        return this.type;
    }

    public String getFullName() {
        return Strings.join(Arrays.asList(this.fullName), ".");
    }

    public int hashCode() {
        return 161 + Arrays.deepHashCode(this.fullName);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            return Arrays.deepEquals(this.fullName, ((PropertyEntry) obj).fullName);
        }
        return false;
    }

    public String toString() {
        return this.type + StringUtils.SPACE + Strings.join(Arrays.asList(this.fullName), ".");
    }
}
