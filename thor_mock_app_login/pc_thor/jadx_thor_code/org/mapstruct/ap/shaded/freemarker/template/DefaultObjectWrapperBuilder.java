package org.mapstruct.ap.shaded.freemarker.template;

import java.lang.ref.ReferenceQueue;
import java.util.WeakHashMap;
import org.mapstruct.ap.shaded.freemarker.ext.beans.BeansWrapper;
import org.mapstruct.ap.shaded.freemarker.ext.beans.BeansWrapperConfiguration;
import org.mapstruct.ap.shaded.freemarker.ext.beans._BeansAPI;

/* loaded from: classes3.dex */
public class DefaultObjectWrapperBuilder extends BeansWrapperConfiguration {
    private static final WeakHashMap INSTANCE_CACHE = new WeakHashMap();
    private static final ReferenceQueue INSTANCE_CACHE_REF_QUEUE = new ReferenceQueue();

    public DefaultObjectWrapperBuilder(Version version) {
        super(version);
    }

    static void clearInstanceCache() {
        WeakHashMap weakHashMap = INSTANCE_CACHE;
        synchronized (weakHashMap) {
            weakHashMap.clear();
        }
    }

    public DefaultObjectWrapper build() {
        return (DefaultObjectWrapper) _BeansAPI.getBeansWrapperSubclassSingleton(this, INSTANCE_CACHE, INSTANCE_CACHE_REF_QUEUE, DefaultObjectWrapperFactory.INSTANCE);
    }

    private static class DefaultObjectWrapperFactory implements _BeansAPI._BeansWrapperSubclassFactory {
        private static final DefaultObjectWrapperFactory INSTANCE = new DefaultObjectWrapperFactory();

        private DefaultObjectWrapperFactory() {
        }

        @Override // org.mapstruct.ap.shaded.freemarker.ext.beans._BeansAPI._BeansWrapperSubclassFactory
        public BeansWrapper create(BeansWrapperConfiguration beansWrapperConfiguration) {
            return new DefaultObjectWrapper(beansWrapperConfiguration, true);
        }
    }
}
