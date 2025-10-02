package org.mapstruct.ap.shaded.freemarker.ext.beans;

import java.lang.reflect.Array;
import java.util.AbstractList;

/* loaded from: classes3.dex */
class PrimtiveArrayBackedReadOnlyList extends AbstractList {
    private final Object array;

    PrimtiveArrayBackedReadOnlyList(Object obj) {
        this.array = obj;
    }

    @Override // java.util.AbstractList, java.util.List
    public Object get(int i) {
        return Array.get(this.array, i);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public int size() {
        return Array.getLength(this.array);
    }
}
