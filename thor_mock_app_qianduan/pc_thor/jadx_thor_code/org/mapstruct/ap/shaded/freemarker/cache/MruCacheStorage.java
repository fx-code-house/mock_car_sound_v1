package org.mapstruct.ap.shaded.freemarker.cache;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes3.dex */
public class MruCacheStorage implements CacheStorageWithGetSize {
    private final Map map;
    private final ReferenceQueue refQueue;
    private final MruEntry softHead;
    private int softSize;
    private final int softSizeLimit;
    private final MruEntry strongHead;
    private int strongSize;
    private final int strongSizeLimit;

    public MruCacheStorage(int i, int i2) {
        MruEntry mruEntry = new MruEntry();
        this.strongHead = mruEntry;
        MruEntry mruEntry2 = new MruEntry();
        this.softHead = mruEntry2;
        mruEntry2.linkAfter(mruEntry);
        this.map = new HashMap();
        this.refQueue = new ReferenceQueue();
        this.strongSize = 0;
        this.softSize = 0;
        if (i < 0) {
            throw new IllegalArgumentException("strongSizeLimit < 0");
        }
        if (i2 < 0) {
            throw new IllegalArgumentException("softSizeLimit < 0");
        }
        this.strongSizeLimit = i;
        this.softSizeLimit = i2;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.cache.CacheStorage
    public Object get(Object obj) {
        removeClearedReferences();
        MruEntry mruEntry = (MruEntry) this.map.get(obj);
        if (mruEntry == null) {
            return null;
        }
        relinkEntryAfterStrongHead(mruEntry, null);
        Object value = mruEntry.getValue();
        return value instanceof MruReference ? ((MruReference) value).get() : value;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.cache.CacheStorage
    public void put(Object obj, Object obj2) {
        removeClearedReferences();
        MruEntry mruEntry = (MruEntry) this.map.get(obj);
        if (mruEntry == null) {
            MruEntry mruEntry2 = new MruEntry(obj, obj2);
            this.map.put(obj, mruEntry2);
            linkAfterStrongHead(mruEntry2);
            return;
        }
        relinkEntryAfterStrongHead(mruEntry, obj2);
    }

    @Override // org.mapstruct.ap.shaded.freemarker.cache.CacheStorage
    public void remove(Object obj) {
        removeClearedReferences();
        removeInternal(obj);
    }

    private void removeInternal(Object obj) {
        MruEntry mruEntry = (MruEntry) this.map.remove(obj);
        if (mruEntry != null) {
            unlinkEntryAndInspectIfSoft(mruEntry);
        }
    }

    @Override // org.mapstruct.ap.shaded.freemarker.cache.CacheStorage
    public void clear() {
        this.strongHead.makeHead();
        this.softHead.linkAfter(this.strongHead);
        this.map.clear();
        this.softSize = 0;
        this.strongSize = 0;
        while (this.refQueue.poll() != null) {
        }
    }

    private void relinkEntryAfterStrongHead(MruEntry mruEntry, Object obj) {
        if (unlinkEntryAndInspectIfSoft(mruEntry) && obj == null) {
            MruReference mruReference = (MruReference) mruEntry.getValue();
            Object obj2 = mruReference.get();
            if (obj2 != null) {
                mruEntry.setValue(obj2);
                linkAfterStrongHead(mruEntry);
                return;
            } else {
                this.map.remove(mruReference.getKey());
                return;
            }
        }
        if (obj != null) {
            mruEntry.setValue(obj);
        }
        linkAfterStrongHead(mruEntry);
    }

    private void linkAfterStrongHead(MruEntry mruEntry) {
        mruEntry.linkAfter(this.strongHead);
        int i = this.strongSize;
        if (i == this.strongSizeLimit) {
            MruEntry previous = this.softHead.getPrevious();
            if (previous != this.strongHead) {
                previous.unlink();
                if (this.softSizeLimit > 0) {
                    previous.linkAfter(this.softHead);
                    previous.setValue(new MruReference(previous, this.refQueue));
                    int i2 = this.softSize;
                    if (i2 == this.softSizeLimit) {
                        MruEntry previous2 = this.strongHead.getPrevious();
                        previous2.unlink();
                        this.map.remove(previous2.getKey());
                        return;
                    }
                    this.softSize = i2 + 1;
                    return;
                }
                this.map.remove(previous.getKey());
                return;
            }
            return;
        }
        this.strongSize = i + 1;
    }

    private boolean unlinkEntryAndInspectIfSoft(MruEntry mruEntry) {
        mruEntry.unlink();
        if (mruEntry.getValue() instanceof MruReference) {
            this.softSize--;
            return true;
        }
        this.strongSize--;
        return false;
    }

    private void removeClearedReferences() {
        while (true) {
            MruReference mruReference = (MruReference) this.refQueue.poll();
            if (mruReference == null) {
                return;
            } else {
                removeInternal(mruReference.getKey());
            }
        }
    }

    public int getStrongSizeLimit() {
        return this.strongSizeLimit;
    }

    public int getSoftSizeLimit() {
        return this.softSizeLimit;
    }

    public int getStrongSize() {
        return this.strongSize;
    }

    public int getSoftSize() {
        removeClearedReferences();
        return this.softSize;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.cache.CacheStorageWithGetSize
    public int getSize() {
        return getSoftSize() + getStrongSize();
    }

    private static final class MruEntry {
        private final Object key;
        private MruEntry next;
        private MruEntry prev;
        private Object value;

        MruEntry() {
            makeHead();
            this.value = null;
            this.key = null;
        }

        MruEntry(Object obj, Object obj2) {
            this.key = obj;
            this.value = obj2;
        }

        Object getKey() {
            return this.key;
        }

        Object getValue() {
            return this.value;
        }

        void setValue(Object obj) {
            this.value = obj;
        }

        MruEntry getPrevious() {
            return this.prev;
        }

        void linkAfter(MruEntry mruEntry) {
            this.next = mruEntry.next;
            mruEntry.next = this;
            this.prev = mruEntry;
            this.next.prev = this;
        }

        void unlink() {
            MruEntry mruEntry = this.next;
            mruEntry.prev = this.prev;
            this.prev.next = mruEntry;
            this.prev = null;
            this.next = null;
        }

        void makeHead() {
            this.next = this;
            this.prev = this;
        }
    }

    private static class MruReference extends SoftReference {
        private final Object key;

        MruReference(MruEntry mruEntry, ReferenceQueue referenceQueue) {
            super(mruEntry.getValue(), referenceQueue);
            this.key = mruEntry.getKey();
        }

        Object getKey() {
            return this.key;
        }
    }
}
