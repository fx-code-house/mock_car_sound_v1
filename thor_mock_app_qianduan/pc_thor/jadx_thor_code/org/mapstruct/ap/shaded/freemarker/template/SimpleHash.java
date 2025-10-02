package org.mapstruct.ap.shaded.freemarker.template;

import java.io.Serializable;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.ap.shaded.freemarker.ext.beans.BeansWrapper;

/* loaded from: classes3.dex */
public class SimpleHash extends WrappingTemplateModel implements TemplateHashModelEx, Serializable {
    private Map map;
    private boolean putFailed;
    private Map unwrappedMap;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public SimpleHash() {
        this((ObjectWrapper) null);
    }

    public SimpleHash(Map map) {
        this(map, null);
    }

    public SimpleHash(ObjectWrapper objectWrapper) {
        super(objectWrapper);
        this.map = new HashMap();
    }

    public SimpleHash(Map map, ObjectWrapper objectWrapper) throws InterruptedException {
        super(objectWrapper);
        try {
            this.map = copyMap(map);
        } catch (ConcurrentModificationException unused) {
            try {
                Thread.sleep(5L);
            } catch (InterruptedException unused2) {
            }
            synchronized (map) {
                this.map = copyMap(map);
            }
        }
    }

    protected Map copyMap(Map map) {
        if (map instanceof HashMap) {
            return (Map) ((HashMap) map).clone();
        }
        if (map instanceof SortedMap) {
            if (map instanceof TreeMap) {
                return (Map) ((TreeMap) map).clone();
            }
            return new TreeMap((SortedMap) map);
        }
        return new HashMap(map);
    }

    public void put(String str, Object obj) {
        this.map.put(str, obj);
        this.unwrappedMap = null;
    }

    public void put(String str, boolean z) {
        put(str, z ? TemplateBooleanModel.TRUE : TemplateBooleanModel.FALSE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:14:0x002f  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0038  */
    /* JADX WARN: Type inference failed for: r0v10, types: [java.lang.Character, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r2v9, types: [java.util.Map] */
    /* JADX WARN: Type inference failed for: r4v0, types: [java.util.Map] */
    @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateHashModel
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public org.mapstruct.ap.shaded.freemarker.template.TemplateModel get(java.lang.String r6) throws org.mapstruct.ap.shaded.freemarker.template.TemplateModelException {
        /*
            r5 = this;
            java.util.Map r0 = r5.map
            java.lang.Object r0 = r0.get(r6)
            r1 = 1
            if (r0 != 0) goto L3a
            int r2 = r6.length()
            r3 = 0
            if (r2 != r1) goto L2b
            java.lang.Character r0 = new java.lang.Character
            r2 = 0
            char r2 = r6.charAt(r2)
            r0.<init>(r2)
            java.util.Map r2 = r5.map
            java.lang.Object r2 = r2.get(r0)
            if (r2 != 0) goto L2d
            java.util.Map r4 = r5.map
            boolean r4 = r4.containsKey(r0)
            if (r4 == 0) goto L2c
            goto L2d
        L2b:
            r2 = r0
        L2c:
            r0 = r3
        L2d:
            if (r0 != 0) goto L38
            java.util.Map r0 = r5.map
            boolean r0 = r0.containsKey(r6)
            if (r0 != 0) goto L39
            return r3
        L38:
            r6 = r0
        L39:
            r0 = r2
        L3a:
            boolean r2 = r0 instanceof org.mapstruct.ap.shaded.freemarker.template.TemplateModel
            if (r2 == 0) goto L42
            org.mapstruct.ap.shaded.freemarker.template.TemplateModel r0 = (org.mapstruct.ap.shaded.freemarker.template.TemplateModel) r0
            return r0
        L42:
            org.mapstruct.ap.shaded.freemarker.template.TemplateModel r0 = r5.wrap(r0)
            boolean r2 = r5.putFailed
            if (r2 != 0) goto L52
            java.util.Map r2 = r5.map     // Catch: java.lang.Exception -> L50
            r2.put(r6, r0)     // Catch: java.lang.Exception -> L50
            goto L52
        L50:
            r5.putFailed = r1
        L52:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mapstruct.ap.shaded.freemarker.template.SimpleHash.get(java.lang.String):org.mapstruct.ap.shaded.freemarker.template.TemplateModel");
    }

    public boolean containsKey(String str) {
        return this.map.containsKey(str);
    }

    public void remove(String str) {
        this.map.remove(str);
    }

    public void putAll(Map map) {
        for (Map.Entry entry : map.entrySet()) {
            put((String) entry.getKey(), entry.getValue());
        }
    }

    public Map toMap() throws Throwable {
        if (this.unwrappedMap == null) {
            Class<?> cls = this.map.getClass();
            try {
                Map map = (Map) cls.newInstance();
                BeansWrapper defaultInstance = BeansWrapper.getDefaultInstance();
                for (Map.Entry entry : this.map.entrySet()) {
                    Object key = entry.getKey();
                    Object value = entry.getValue();
                    if (value instanceof TemplateModel) {
                        value = defaultInstance.unwrap((TemplateModel) value);
                    }
                    map.put(key, value);
                }
                this.unwrappedMap = map;
            } catch (Exception e) {
                throw new TemplateModelException(new StringBuffer("Error instantiating map of type ").append(cls.getName()).append(StringUtils.LF).append(e.getMessage()).toString());
            }
        }
        return this.unwrappedMap;
    }

    public String toString() {
        return this.map.toString();
    }

    @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateHashModelEx
    public int size() {
        return this.map.size();
    }

    @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateHashModel
    public boolean isEmpty() {
        Map map = this.map;
        return map == null || map.isEmpty();
    }

    @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateHashModelEx
    public TemplateCollectionModel keys() {
        return new SimpleCollection(this.map.keySet(), getObjectWrapper());
    }

    @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateHashModelEx
    public TemplateCollectionModel values() {
        return new SimpleCollection(this.map.values(), getObjectWrapper());
    }

    public SimpleHash synchronizedWrapper() {
        return new SynchronizedHash();
    }

    private class SynchronizedHash extends SimpleHash {
        private SynchronizedHash() {
        }

        @Override // org.mapstruct.ap.shaded.freemarker.template.SimpleHash, org.mapstruct.ap.shaded.freemarker.template.TemplateHashModel
        public boolean isEmpty() {
            boolean zIsEmpty;
            synchronized (SimpleHash.this) {
                zIsEmpty = SimpleHash.this.isEmpty();
            }
            return zIsEmpty;
        }

        @Override // org.mapstruct.ap.shaded.freemarker.template.SimpleHash
        public void put(String str, Object obj) {
            synchronized (SimpleHash.this) {
                SimpleHash.this.put(str, obj);
            }
        }

        @Override // org.mapstruct.ap.shaded.freemarker.template.SimpleHash, org.mapstruct.ap.shaded.freemarker.template.TemplateHashModel
        public TemplateModel get(String str) throws TemplateModelException {
            TemplateModel templateModel;
            synchronized (SimpleHash.this) {
                templateModel = SimpleHash.this.get(str);
            }
            return templateModel;
        }

        @Override // org.mapstruct.ap.shaded.freemarker.template.SimpleHash
        public void remove(String str) {
            synchronized (SimpleHash.this) {
                SimpleHash.this.remove(str);
            }
        }

        @Override // org.mapstruct.ap.shaded.freemarker.template.SimpleHash, org.mapstruct.ap.shaded.freemarker.template.TemplateHashModelEx
        public int size() {
            int size;
            synchronized (SimpleHash.this) {
                size = SimpleHash.this.size();
            }
            return size;
        }

        @Override // org.mapstruct.ap.shaded.freemarker.template.SimpleHash, org.mapstruct.ap.shaded.freemarker.template.TemplateHashModelEx
        public TemplateCollectionModel keys() {
            TemplateCollectionModel templateCollectionModelKeys;
            synchronized (SimpleHash.this) {
                templateCollectionModelKeys = SimpleHash.this.keys();
            }
            return templateCollectionModelKeys;
        }

        @Override // org.mapstruct.ap.shaded.freemarker.template.SimpleHash, org.mapstruct.ap.shaded.freemarker.template.TemplateHashModelEx
        public TemplateCollectionModel values() {
            TemplateCollectionModel templateCollectionModelValues;
            synchronized (SimpleHash.this) {
                templateCollectionModelValues = SimpleHash.this.values();
            }
            return templateCollectionModelValues;
        }

        @Override // org.mapstruct.ap.shaded.freemarker.template.SimpleHash
        public Map toMap() throws TemplateModelException {
            Map map;
            synchronized (SimpleHash.this) {
                map = SimpleHash.this.toMap();
            }
            return map;
        }
    }
}
