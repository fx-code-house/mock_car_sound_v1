package com.xwray.groupie;

import java.util.Collection;
import java.util.Iterator;

/* loaded from: classes3.dex */
class GroupUtils {
    GroupUtils() {
    }

    static Item getItem(Collection<? extends Group> collection, int i) {
        int i2 = 0;
        for (Group group : collection) {
            int itemCount = group.getItemCount() + i2;
            if (itemCount > i) {
                return group.getItem(i - i2);
            }
            i2 = itemCount;
        }
        throw new IndexOutOfBoundsException("Wanted item at " + i + " but there are only " + i2 + " items");
    }

    static int getItemCount(Collection<? extends Group> collection) {
        Iterator<? extends Group> it = collection.iterator();
        int itemCount = 0;
        while (it.hasNext()) {
            itemCount += it.next().getItemCount();
        }
        return itemCount;
    }
}
