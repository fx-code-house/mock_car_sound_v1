package org.mapstruct.ap.shaded.freemarker.ext.util;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.AbstractCollection;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

/* loaded from: classes3.dex */
public class IdentityHashMap extends AbstractMap implements Map, Cloneable, Serializable {
    private static final int ENTRIES = 2;
    private static final int KEYS = 0;
    private static final int VALUES = 1;
    private static EmptyHashIterator emptyHashIterator = new EmptyHashIterator();
    public static final long serialVersionUID = 362498820763181265L;
    private transient int count;
    private transient Set entrySet;
    private transient Set keySet;
    private float loadFactor;
    private transient int modCount;
    private transient Entry[] table;
    private int threshold;
    private transient Collection values;

    static /* synthetic */ int access$110(IdentityHashMap identityHashMap) {
        int i = identityHashMap.count;
        identityHashMap.count = i - 1;
        return i;
    }

    static /* synthetic */ int access$308(IdentityHashMap identityHashMap) {
        int i = identityHashMap.modCount;
        identityHashMap.modCount = i + 1;
        return i;
    }

    public IdentityHashMap(int i, float f) {
        this.modCount = 0;
        this.keySet = null;
        this.entrySet = null;
        this.values = null;
        if (i < 0) {
            throw new IllegalArgumentException(new StringBuffer("Illegal Initial Capacity: ").append(i).toString());
        }
        if (f <= 0.0f || Float.isNaN(f)) {
            throw new IllegalArgumentException(new StringBuffer("Illegal Load factor: ").append(f).toString());
        }
        i = i == 0 ? 1 : i;
        this.loadFactor = f;
        this.table = new Entry[i];
        this.threshold = (int) (i * f);
    }

    public IdentityHashMap(int i) {
        this(i, 0.75f);
    }

    public IdentityHashMap() {
        this(11, 0.75f);
    }

    public IdentityHashMap(Map map) {
        this(Math.max(map.size() * 2, 11), 0.75f);
        putAll(map);
    }

    @Override // java.util.AbstractMap, java.util.Map
    public int size() {
        return this.count;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public boolean isEmpty() {
        return this.count == 0;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public boolean containsValue(Object obj) {
        Entry[] entryArr = this.table;
        if (obj == null) {
            int length = entryArr.length;
            while (true) {
                int i = length - 1;
                if (length <= 0) {
                    return false;
                }
                for (Entry entry = entryArr[i]; entry != null; entry = entry.next) {
                    if (entry.value == null) {
                        return true;
                    }
                }
                length = i;
            }
        } else {
            int length2 = entryArr.length;
            while (true) {
                int i2 = length2 - 1;
                if (length2 <= 0) {
                    return false;
                }
                for (Entry entry2 = entryArr[i2]; entry2 != null; entry2 = entry2.next) {
                    if (obj.equals(entry2.value)) {
                        return true;
                    }
                }
                length2 = i2;
            }
        }
    }

    @Override // java.util.AbstractMap, java.util.Map
    public boolean containsKey(Object obj) {
        Entry[] entryArr = this.table;
        if (obj != null) {
            int iIdentityHashCode = System.identityHashCode(obj);
            for (Entry entry = entryArr[(Integer.MAX_VALUE & iIdentityHashCode) % entryArr.length]; entry != null; entry = entry.next) {
                if (entry.hash == iIdentityHashCode && obj == entry.key) {
                    return true;
                }
            }
        } else {
            for (Entry entry2 = entryArr[0]; entry2 != null; entry2 = entry2.next) {
                if (entry2.key == null) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Object get(Object obj) {
        Entry[] entryArr = this.table;
        if (obj != null) {
            int iIdentityHashCode = System.identityHashCode(obj);
            for (Entry entry = entryArr[(Integer.MAX_VALUE & iIdentityHashCode) % entryArr.length]; entry != null; entry = entry.next) {
                if (entry.hash == iIdentityHashCode && obj == entry.key) {
                    return entry.value;
                }
            }
            return null;
        }
        for (Entry entry2 = entryArr[0]; entry2 != null; entry2 = entry2.next) {
            if (entry2.key == null) {
                return entry2.value;
            }
        }
        return null;
    }

    private void rehash() {
        Entry[] entryArr = this.table;
        int length = entryArr.length;
        int i = (length * 2) + 1;
        Entry[] entryArr2 = new Entry[i];
        this.modCount++;
        this.threshold = (int) (i * this.loadFactor);
        this.table = entryArr2;
        while (true) {
            int i2 = length - 1;
            if (length <= 0) {
                return;
            }
            Entry entry = entryArr[i2];
            while (entry != null) {
                Entry entry2 = entry.next;
                int i3 = (entry.hash & Integer.MAX_VALUE) % i;
                entry.next = entryArr2[i3];
                entryArr2[i3] = entry;
                entry = entry2;
            }
            length = i2;
        }
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Object put(Object obj, Object obj2) {
        int iIdentityHashCode;
        int length;
        Entry[] entryArr = this.table;
        if (obj != null) {
            iIdentityHashCode = System.identityHashCode(obj);
            length = (iIdentityHashCode & Integer.MAX_VALUE) % entryArr.length;
            for (Entry entry = entryArr[length]; entry != null; entry = entry.next) {
                if (entry.hash == iIdentityHashCode && obj == entry.key) {
                    Object obj3 = entry.value;
                    entry.value = obj2;
                    return obj3;
                }
            }
        } else {
            iIdentityHashCode = 0;
            for (Entry entry2 = entryArr[0]; entry2 != null; entry2 = entry2.next) {
                if (entry2.key == null) {
                    Object obj4 = entry2.value;
                    entry2.value = obj2;
                    return obj4;
                }
            }
            length = 0;
        }
        this.modCount++;
        if (this.count >= this.threshold) {
            rehash();
            entryArr = this.table;
            length = (Integer.MAX_VALUE & iIdentityHashCode) % entryArr.length;
        }
        entryArr[length] = new Entry(iIdentityHashCode, obj, obj2, entryArr[length]);
        this.count++;
        return null;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Object remove(Object obj) {
        Entry[] entryArr = this.table;
        if (obj != null) {
            int iIdentityHashCode = System.identityHashCode(obj);
            int length = (Integer.MAX_VALUE & iIdentityHashCode) % entryArr.length;
            Entry entry = null;
            for (Entry entry2 = entryArr[length]; entry2 != null; entry2 = entry2.next) {
                if (entry2.hash != iIdentityHashCode || obj != entry2.key) {
                    entry = entry2;
                } else {
                    this.modCount++;
                    if (entry != null) {
                        entry.next = entry2.next;
                    } else {
                        entryArr[length] = entry2.next;
                    }
                    this.count--;
                    Object obj2 = entry2.value;
                    entry2.value = null;
                    return obj2;
                }
            }
        } else {
            Entry entry3 = null;
            for (Entry entry4 = entryArr[0]; entry4 != null; entry4 = entry4.next) {
                if (entry4.key != null) {
                    entry3 = entry4;
                } else {
                    this.modCount++;
                    if (entry3 != null) {
                        entry3.next = entry4.next;
                    } else {
                        entryArr[0] = entry4.next;
                    }
                    this.count--;
                    Object obj3 = entry4.value;
                    entry4.value = null;
                    return obj3;
                }
            }
        }
        return null;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public void putAll(Map map) {
        for (Map.Entry entry : map.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    @Override // java.util.AbstractMap, java.util.Map
    public void clear() {
        Entry[] entryArr = this.table;
        this.modCount++;
        int length = entryArr.length;
        while (true) {
            length--;
            if (length >= 0) {
                entryArr[length] = null;
            } else {
                this.count = 0;
                return;
            }
        }
    }

    @Override // java.util.AbstractMap
    public Object clone() {
        try {
            IdentityHashMap identityHashMap = (IdentityHashMap) super.clone();
            identityHashMap.table = new Entry[this.table.length];
            int length = this.table.length;
            while (true) {
                int i = length - 1;
                Entry entry = null;
                if (length > 0) {
                    Entry[] entryArr = identityHashMap.table;
                    Entry entry2 = this.table[i];
                    if (entry2 != null) {
                        entry = (Entry) entry2.clone();
                    }
                    entryArr[i] = entry;
                    length = i;
                } else {
                    identityHashMap.keySet = null;
                    identityHashMap.entrySet = null;
                    identityHashMap.values = null;
                    identityHashMap.modCount = 0;
                    return identityHashMap;
                }
            }
        } catch (CloneNotSupportedException unused) {
            throw new InternalError();
        }
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Set keySet() {
        if (this.keySet == null) {
            this.keySet = new AbstractSet() { // from class: org.mapstruct.ap.shaded.freemarker.ext.util.IdentityHashMap.1
                @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
                public Iterator iterator() {
                    return IdentityHashMap.this.getHashIterator(0);
                }

                @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
                public int size() {
                    return IdentityHashMap.this.count;
                }

                @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
                public boolean contains(Object obj) {
                    return IdentityHashMap.this.containsKey(obj);
                }

                @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
                public boolean remove(Object obj) {
                    int i = IdentityHashMap.this.count;
                    IdentityHashMap.this.remove(obj);
                    return IdentityHashMap.this.count != i;
                }

                @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
                public void clear() {
                    IdentityHashMap.this.clear();
                }
            };
        }
        return this.keySet;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Collection values() {
        if (this.values == null) {
            this.values = new AbstractCollection() { // from class: org.mapstruct.ap.shaded.freemarker.ext.util.IdentityHashMap.2
                @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
                public Iterator iterator() {
                    return IdentityHashMap.this.getHashIterator(1);
                }

                @Override // java.util.AbstractCollection, java.util.Collection
                public int size() {
                    return IdentityHashMap.this.count;
                }

                @Override // java.util.AbstractCollection, java.util.Collection
                public boolean contains(Object obj) {
                    return IdentityHashMap.this.containsValue(obj);
                }

                @Override // java.util.AbstractCollection, java.util.Collection
                public void clear() {
                    IdentityHashMap.this.clear();
                }
            };
        }
        return this.values;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Set entrySet() {
        if (this.entrySet == null) {
            this.entrySet = new AbstractSet() { // from class: org.mapstruct.ap.shaded.freemarker.ext.util.IdentityHashMap.3
                @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
                public Iterator iterator() {
                    return IdentityHashMap.this.getHashIterator(2);
                }

                @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
                public boolean contains(Object obj) {
                    if (!(obj instanceof Map.Entry)) {
                        return false;
                    }
                    Map.Entry entry = (Map.Entry) obj;
                    Object key = entry.getKey();
                    Entry[] entryArr = IdentityHashMap.this.table;
                    int iIdentityHashCode = key == null ? 0 : System.identityHashCode(key);
                    for (Entry entry2 = entryArr[(Integer.MAX_VALUE & iIdentityHashCode) % entryArr.length]; entry2 != null; entry2 = entry2.next) {
                        if (entry2.hash == iIdentityHashCode && entry2.equals(entry)) {
                            return true;
                        }
                    }
                    return false;
                }

                @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
                public boolean remove(Object obj) {
                    if (!(obj instanceof Map.Entry)) {
                        return false;
                    }
                    Map.Entry entry = (Map.Entry) obj;
                    Object key = entry.getKey();
                    Entry[] entryArr = IdentityHashMap.this.table;
                    int iIdentityHashCode = key == null ? 0 : System.identityHashCode(key);
                    int length = (Integer.MAX_VALUE & iIdentityHashCode) % entryArr.length;
                    Entry entry2 = null;
                    for (Entry entry3 = entryArr[length]; entry3 != null; entry3 = entry3.next) {
                        if (entry3.hash != iIdentityHashCode || !entry3.equals(entry)) {
                            entry2 = entry3;
                        } else {
                            IdentityHashMap.access$308(IdentityHashMap.this);
                            if (entry2 != null) {
                                entry2.next = entry3.next;
                            } else {
                                entryArr[length] = entry3.next;
                            }
                            IdentityHashMap.access$110(IdentityHashMap.this);
                            entry3.value = null;
                            return true;
                        }
                    }
                    return false;
                }

                @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
                public int size() {
                    return IdentityHashMap.this.count;
                }

                @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
                public void clear() {
                    IdentityHashMap.this.clear();
                }
            };
        }
        return this.entrySet;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Iterator getHashIterator(int i) {
        if (this.count == 0) {
            return emptyHashIterator;
        }
        return new HashIterator(i);
    }

    private static class Entry implements Map.Entry {
        int hash;
        Object key;
        Entry next;
        Object value;

        Entry(int i, Object obj, Object obj2, Entry entry) {
            this.hash = i;
            this.key = obj;
            this.value = obj2;
            this.next = entry;
        }

        protected Object clone() {
            int i = this.hash;
            Object obj = this.key;
            Object obj2 = this.value;
            Entry entry = this.next;
            return new Entry(i, obj, obj2, entry == null ? null : (Entry) entry.clone());
        }

        @Override // java.util.Map.Entry
        public Object getKey() {
            return this.key;
        }

        @Override // java.util.Map.Entry
        public Object getValue() {
            return this.value;
        }

        @Override // java.util.Map.Entry
        public Object setValue(Object obj) {
            Object obj2 = this.value;
            this.value = obj;
            return obj2;
        }

        @Override // java.util.Map.Entry
        public boolean equals(Object obj) {
            if (!(obj instanceof Map.Entry)) {
                return false;
            }
            Map.Entry entry = (Map.Entry) obj;
            if (this.key != entry.getKey()) {
                return false;
            }
            Object obj2 = this.value;
            if (obj2 == null) {
                if (entry.getValue() != null) {
                    return false;
                }
            } else if (!obj2.equals(entry.getValue())) {
                return false;
            }
            return true;
        }

        @Override // java.util.Map.Entry
        public int hashCode() {
            int i = this.hash;
            Object obj = this.value;
            return i ^ (obj == null ? 0 : obj.hashCode());
        }

        public String toString() {
            return new StringBuffer().append(this.key).append("=").append(this.value).toString();
        }
    }

    private static class EmptyHashIterator implements Iterator {
        @Override // java.util.Iterator
        public boolean hasNext() {
            return false;
        }

        EmptyHashIterator() {
        }

        @Override // java.util.Iterator
        public Object next() {
            throw new NoSuchElementException();
        }

        @Override // java.util.Iterator
        public void remove() {
            throw new IllegalStateException();
        }
    }

    private class HashIterator implements Iterator {
        Entry entry;
        private int expectedModCount;
        int index;
        Entry lastReturned;
        Entry[] table;
        int type;

        HashIterator(int i) {
            Entry[] entryArr = IdentityHashMap.this.table;
            this.table = entryArr;
            this.index = entryArr.length;
            this.entry = null;
            this.lastReturned = null;
            this.expectedModCount = IdentityHashMap.this.modCount;
            this.type = i;
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            Entry entry = this.entry;
            int i = this.index;
            Entry[] entryArr = this.table;
            while (entry == null && i > 0) {
                i--;
                entry = entryArr[i];
            }
            this.entry = entry;
            this.index = i;
            return entry != null;
        }

        @Override // java.util.Iterator
        public Object next() {
            if (IdentityHashMap.this.modCount != this.expectedModCount) {
                throw new ConcurrentModificationException();
            }
            Entry entry = this.entry;
            int i = this.index;
            Entry[] entryArr = this.table;
            while (entry == null && i > 0) {
                i--;
                entry = entryArr[i];
            }
            this.entry = entry;
            this.index = i;
            if (entry != null) {
                this.lastReturned = entry;
                this.entry = entry.next;
                int i2 = this.type;
                return i2 == 0 ? entry.key : i2 == 1 ? entry.value : entry;
            }
            throw new NoSuchElementException();
        }

        @Override // java.util.Iterator
        public void remove() {
            if (this.lastReturned != null) {
                if (IdentityHashMap.this.modCount == this.expectedModCount) {
                    Entry[] entryArr = IdentityHashMap.this.table;
                    int length = (this.lastReturned.hash & Integer.MAX_VALUE) % entryArr.length;
                    Entry entry = null;
                    for (Entry entry2 = entryArr[length]; entry2 != null; entry2 = entry2.next) {
                        if (entry2 != this.lastReturned) {
                            entry = entry2;
                        } else {
                            IdentityHashMap.access$308(IdentityHashMap.this);
                            this.expectedModCount++;
                            if (entry == null) {
                                entryArr[length] = entry2.next;
                            } else {
                                entry.next = entry2.next;
                            }
                            IdentityHashMap.access$110(IdentityHashMap.this);
                            this.lastReturned = null;
                            return;
                        }
                    }
                    throw new ConcurrentModificationException();
                }
                throw new ConcurrentModificationException();
            }
            throw new IllegalStateException();
        }
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeInt(this.table.length);
        objectOutputStream.writeInt(this.count);
        for (int length = this.table.length - 1; length >= 0; length--) {
            for (Entry entry = this.table[length]; entry != null; entry = entry.next) {
                objectOutputStream.writeObject(entry.key);
                objectOutputStream.writeObject(entry.value);
            }
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws ClassNotFoundException, IOException {
        objectInputStream.defaultReadObject();
        this.table = new Entry[objectInputStream.readInt()];
        int i = objectInputStream.readInt();
        for (int i2 = 0; i2 < i; i2++) {
            put(objectInputStream.readObject(), objectInputStream.readObject());
        }
    }

    int capacity() {
        return this.table.length;
    }

    float loadFactor() {
        return this.loadFactor;
    }
}
