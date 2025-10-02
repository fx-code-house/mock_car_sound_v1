package com.google.android.exoplayer2.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* loaded from: classes.dex */
public final class CopyOnWriteMultiset<E> implements Iterable<E> {
    private final Object lock = new Object();
    private final Map<E, Integer> elementCounts = new HashMap();
    private Set<E> elementSet = Collections.emptySet();
    private List<E> elements = Collections.emptyList();

    public void add(E element) {
        synchronized (this.lock) {
            ArrayList arrayList = new ArrayList(this.elements);
            arrayList.add(element);
            this.elements = Collections.unmodifiableList(arrayList);
            Integer num = this.elementCounts.get(element);
            if (num == null) {
                HashSet hashSet = new HashSet(this.elementSet);
                hashSet.add(element);
                this.elementSet = Collections.unmodifiableSet(hashSet);
            }
            this.elementCounts.put(element, Integer.valueOf(num != null ? 1 + num.intValue() : 1));
        }
    }

    public void remove(E element) {
        synchronized (this.lock) {
            Integer num = this.elementCounts.get(element);
            if (num == null) {
                return;
            }
            ArrayList arrayList = new ArrayList(this.elements);
            arrayList.remove(element);
            this.elements = Collections.unmodifiableList(arrayList);
            if (num.intValue() == 1) {
                this.elementCounts.remove(element);
                HashSet hashSet = new HashSet(this.elementSet);
                hashSet.remove(element);
                this.elementSet = Collections.unmodifiableSet(hashSet);
            } else {
                this.elementCounts.put(element, Integer.valueOf(num.intValue() - 1));
            }
        }
    }

    public Set<E> elementSet() {
        Set<E> set;
        synchronized (this.lock) {
            set = this.elementSet;
        }
        return set;
    }

    @Override // java.lang.Iterable
    public Iterator<E> iterator() {
        Iterator<E> it;
        synchronized (this.lock) {
            it = this.elements.iterator();
        }
        return it;
    }

    public int count(E element) {
        int iIntValue;
        synchronized (this.lock) {
            iIntValue = this.elementCounts.containsKey(element) ? this.elementCounts.get(element).intValue() : 0;
        }
        return iIntValue;
    }
}
