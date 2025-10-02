package org.mapstruct.ap.shaded.freemarker.ext.beans;

import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.mapstruct.ap.shaded.freemarker.template.TemplateHashModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateHashModelEx;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModelAdapter;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModelException;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModelIterator;
import org.mapstruct.ap.shaded.freemarker.template.utility.UndeclaredThrowableException;

/* loaded from: classes3.dex */
public class HashAdapter extends AbstractMap implements TemplateModelAdapter {
    private Set entrySet;
    private final TemplateHashModel model;
    private final BeansWrapper wrapper;

    HashAdapter(TemplateHashModel templateHashModel, BeansWrapper beansWrapper) {
        this.model = templateHashModel;
        this.wrapper = beansWrapper;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateModelAdapter
    public TemplateModel getTemplateModel() {
        return this.model;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public boolean isEmpty() {
        try {
            return this.model.isEmpty();
        } catch (TemplateModelException e) {
            throw new UndeclaredThrowableException(e);
        }
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Object get(Object obj) {
        try {
            return this.wrapper.unwrap(this.model.get(String.valueOf(obj)));
        } catch (TemplateModelException e) {
            throw new UndeclaredThrowableException(e);
        }
    }

    @Override // java.util.AbstractMap, java.util.Map
    public boolean containsKey(Object obj) {
        if (get(obj) != null) {
            return true;
        }
        return super.containsKey(obj);
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Set entrySet() {
        Set set = this.entrySet;
        if (set != null) {
            return set;
        }
        AnonymousClass1 anonymousClass1 = new AnonymousClass1();
        this.entrySet = anonymousClass1;
        return anonymousClass1;
    }

    /* renamed from: org.mapstruct.ap.shaded.freemarker.ext.beans.HashAdapter$1, reason: invalid class name */
    class AnonymousClass1 extends AbstractSet {
        AnonymousClass1() {
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
        public Iterator iterator() {
            try {
                final TemplateModelIterator it = HashAdapter.this.getModelEx().keys().iterator();
                return new Iterator() { // from class: org.mapstruct.ap.shaded.freemarker.ext.beans.HashAdapter.1.1
                    @Override // java.util.Iterator
                    public boolean hasNext() {
                        try {
                            return it.hasNext();
                        } catch (TemplateModelException e) {
                            throw new UndeclaredThrowableException(e);
                        }
                    }

                    @Override // java.util.Iterator
                    public Object next() throws Throwable {
                        try {
                            final Object objUnwrap = HashAdapter.this.wrapper.unwrap(it.next());
                            return new Map.Entry() { // from class: org.mapstruct.ap.shaded.freemarker.ext.beans.HashAdapter.1.1.1
                                @Override // java.util.Map.Entry
                                public Object getKey() {
                                    return objUnwrap;
                                }

                                @Override // java.util.Map.Entry
                                public Object getValue() {
                                    return HashAdapter.this.get(objUnwrap);
                                }

                                @Override // java.util.Map.Entry
                                public Object setValue(Object obj) {
                                    throw new UnsupportedOperationException();
                                }

                                @Override // java.util.Map.Entry
                                public boolean equals(Object obj) {
                                    if (!(obj instanceof Map.Entry)) {
                                        return false;
                                    }
                                    Map.Entry entry = (Map.Entry) obj;
                                    Object key = getKey();
                                    Object key2 = entry.getKey();
                                    if (key == key2 || (key != null && key.equals(key2))) {
                                        Object value = getValue();
                                        Object value2 = entry.getValue();
                                        if (value == value2) {
                                            return true;
                                        }
                                        if (value != null && value.equals(value2)) {
                                            return true;
                                        }
                                    }
                                    return false;
                                }

                                @Override // java.util.Map.Entry
                                public int hashCode() {
                                    Object value = getValue();
                                    Object obj = objUnwrap;
                                    return (obj == null ? 0 : obj.hashCode()) ^ (value != null ? value.hashCode() : 0);
                                }
                            };
                        } catch (TemplateModelException e) {
                            throw new UndeclaredThrowableException(e);
                        }
                    }

                    @Override // java.util.Iterator
                    public void remove() {
                        throw new UnsupportedOperationException();
                    }
                };
            } catch (TemplateModelException e) {
                throw new UndeclaredThrowableException(e);
            }
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            try {
                return HashAdapter.this.getModelEx().size();
            } catch (TemplateModelException e) {
                throw new UndeclaredThrowableException(e);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public TemplateHashModelEx getModelEx() {
        TemplateHashModel templateHashModel = this.model;
        if (templateHashModel instanceof TemplateHashModelEx) {
            return (TemplateHashModelEx) templateHashModel;
        }
        throw new UnsupportedOperationException(new StringBuffer("Operation supported only on TemplateHashModelEx. ").append(this.model.getClass().getName()).append(" does not implement it though.").toString());
    }
}
