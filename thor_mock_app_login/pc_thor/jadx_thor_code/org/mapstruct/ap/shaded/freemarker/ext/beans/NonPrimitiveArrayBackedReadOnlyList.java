package org.mapstruct.ap.shaded.freemarker.ext.beans;

import java.util.AbstractList;

/* loaded from: classes3.dex */
class NonPrimitiveArrayBackedReadOnlyList extends AbstractList {
    private final Object[] array;

    NonPrimitiveArrayBackedReadOnlyList(Object[] objArr) {
        this.array = objArr;
    }

    @Override // java.util.AbstractList, java.util.List
    public Object get(int i) {
        return this.array[i];
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public int size() {
        return this.array.length;
    }
}
