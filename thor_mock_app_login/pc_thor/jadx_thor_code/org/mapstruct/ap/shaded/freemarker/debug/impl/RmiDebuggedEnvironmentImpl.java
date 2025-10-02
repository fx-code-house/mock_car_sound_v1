package org.mapstruct.ap.shaded.freemarker.debug.impl;

import com.google.android.gms.measurement.api.AppMeasurementSdk;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.mapstruct.ap.shaded.freemarker.cache.CacheStorage;
import org.mapstruct.ap.shaded.freemarker.cache.SoftCacheStorage;
import org.mapstruct.ap.shaded.freemarker.core.Configurable;
import org.mapstruct.ap.shaded.freemarker.core.Environment;
import org.mapstruct.ap.shaded.freemarker.debug.DebuggedEnvironment;
import org.mapstruct.ap.shaded.freemarker.ext.util.IdentityHashMap;
import org.mapstruct.ap.shaded.freemarker.template.Configuration;
import org.mapstruct.ap.shaded.freemarker.template.SimpleCollection;
import org.mapstruct.ap.shaded.freemarker.template.SimpleScalar;
import org.mapstruct.ap.shaded.freemarker.template.Template;
import org.mapstruct.ap.shaded.freemarker.template.TemplateCollectionModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateHashModelEx;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModelException;
import org.mapstruct.ap.shaded.freemarker.template.utility.UndeclaredThrowableException;

/* loaded from: classes3.dex */
class RmiDebuggedEnvironmentImpl extends RmiDebugModelImpl implements DebuggedEnvironment {
    private static final long serialVersionUID = 1;
    private final long id;
    private boolean stopped;
    private static final CacheStorage storage = new SoftCacheStorage(new IdentityHashMap());
    private static final Object idLock = new Object();
    private static long nextId = 1;
    private static Set remotes = new HashSet();

    private RmiDebuggedEnvironmentImpl(Environment environment) throws RemoteException {
        super(new DebugEnvironmentModel(environment), 2048);
        this.stopped = false;
        synchronized (idLock) {
            long j = nextId;
            nextId = 1 + j;
            this.id = j;
        }
    }

    static synchronized Object getCachedWrapperFor(Object obj) throws RemoteException {
        Object debugConfigurationModel;
        int i;
        CacheStorage cacheStorage = storage;
        debugConfigurationModel = cacheStorage.get(obj);
        if (debugConfigurationModel == null) {
            if (obj instanceof TemplateModel) {
                if (obj instanceof DebugConfigurationModel) {
                    i = 8192;
                } else {
                    i = obj instanceof DebugTemplateModel ? 4096 : 0;
                }
                debugConfigurationModel = new RmiDebugModelImpl((TemplateModel) obj, i);
            } else if (obj instanceof Environment) {
                debugConfigurationModel = new RmiDebuggedEnvironmentImpl((Environment) obj);
            } else if (obj instanceof Template) {
                debugConfigurationModel = new DebugTemplateModel((Template) obj);
            } else if (obj instanceof Configuration) {
                debugConfigurationModel = new DebugConfigurationModel((Configuration) obj);
            }
        }
        if (debugConfigurationModel != null) {
            cacheStorage.put(obj, debugConfigurationModel);
        }
        if (debugConfigurationModel instanceof Remote) {
            remotes.add(debugConfigurationModel);
        }
        return debugConfigurationModel;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.debug.DebuggedEnvironment
    public void resume() {
        synchronized (this) {
            notify();
        }
    }

    @Override // org.mapstruct.ap.shaded.freemarker.debug.DebuggedEnvironment
    public void stop() {
        this.stopped = true;
        resume();
    }

    @Override // org.mapstruct.ap.shaded.freemarker.debug.DebuggedEnvironment
    public long getId() {
        return this.id;
    }

    boolean isStopped() {
        return this.stopped;
    }

    private static abstract class DebugMapModel implements TemplateHashModelEx {
        abstract Collection keySet();

        private DebugMapModel() {
        }

        @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateHashModelEx
        public int size() {
            return keySet().size();
        }

        @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateHashModelEx
        public TemplateCollectionModel keys() {
            return new SimpleCollection(keySet());
        }

        @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateHashModelEx
        public TemplateCollectionModel values() throws TemplateModelException {
            Collection collectionKeySet = keySet();
            ArrayList arrayList = new ArrayList(collectionKeySet.size());
            Iterator it = collectionKeySet.iterator();
            while (it.hasNext()) {
                arrayList.add(get((String) it.next()));
            }
            return new SimpleCollection(arrayList);
        }

        @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateHashModel
        public boolean isEmpty() {
            return size() == 0;
        }

        static List composeList(Collection collection, Collection collection2) {
            ArrayList arrayList = new ArrayList(collection);
            arrayList.addAll(collection2);
            Collections.sort(arrayList);
            return arrayList;
        }
    }

    private static class DebugConfigurableModel extends DebugMapModel {
        static final List KEYS = Arrays.asList(Configurable.ARITHMETIC_ENGINE_KEY, Configurable.BOOLEAN_FORMAT_KEY, Configurable.CLASSIC_COMPATIBLE_KEY, Configurable.LOCALE_KEY, Configurable.NUMBER_FORMAT_KEY, Configurable.OBJECT_WRAPPER_KEY, Configurable.TEMPLATE_EXCEPTION_HANDLER_KEY);
        final Configurable configurable;

        DebugConfigurableModel(Configurable configurable) {
            super();
            this.configurable = configurable;
        }

        @Override // org.mapstruct.ap.shaded.freemarker.debug.impl.RmiDebuggedEnvironmentImpl.DebugMapModel
        Collection keySet() {
            return KEYS;
        }

        @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateHashModel
        public TemplateModel get(String str) throws TemplateModelException {
            String setting = this.configurable.getSetting(str);
            if (setting == null) {
                return null;
            }
            return new SimpleScalar(setting);
        }
    }

    private static class DebugConfigurationModel extends DebugConfigurableModel {
        private static final List KEYS = composeList(DebugConfigurableModel.KEYS, Collections.singleton("sharedVariables"));
        private TemplateModel sharedVariables;

        DebugConfigurationModel(Configuration configuration) {
            super(configuration);
            this.sharedVariables = new DebugMapModel() { // from class: org.mapstruct.ap.shaded.freemarker.debug.impl.RmiDebuggedEnvironmentImpl.DebugConfigurationModel.1
                @Override // org.mapstruct.ap.shaded.freemarker.debug.impl.RmiDebuggedEnvironmentImpl.DebugMapModel
                Collection keySet() {
                    return ((Configuration) DebugConfigurationModel.this.configurable).getSharedVariableNames();
                }

                @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateHashModel
                public TemplateModel get(String str) {
                    return ((Configuration) DebugConfigurationModel.this.configurable).getSharedVariable(str);
                }
            };
        }

        @Override // org.mapstruct.ap.shaded.freemarker.debug.impl.RmiDebuggedEnvironmentImpl.DebugConfigurableModel, org.mapstruct.ap.shaded.freemarker.debug.impl.RmiDebuggedEnvironmentImpl.DebugMapModel
        Collection keySet() {
            return KEYS;
        }

        @Override // org.mapstruct.ap.shaded.freemarker.debug.impl.RmiDebuggedEnvironmentImpl.DebugConfigurableModel, org.mapstruct.ap.shaded.freemarker.template.TemplateHashModel
        public TemplateModel get(String str) throws TemplateModelException {
            if ("sharedVariables".equals(str)) {
                return this.sharedVariables;
            }
            return super.get(str);
        }
    }

    private static class DebugTemplateModel extends DebugConfigurableModel {
        private static final List KEYS = composeList(DebugConfigurableModel.KEYS, Arrays.asList("configuration", AppMeasurementSdk.ConditionalUserProperty.NAME));
        private final SimpleScalar name;

        DebugTemplateModel(Template template) {
            super(template);
            this.name = new SimpleScalar(template.getName());
        }

        @Override // org.mapstruct.ap.shaded.freemarker.debug.impl.RmiDebuggedEnvironmentImpl.DebugConfigurableModel, org.mapstruct.ap.shaded.freemarker.debug.impl.RmiDebuggedEnvironmentImpl.DebugMapModel
        Collection keySet() {
            return KEYS;
        }

        @Override // org.mapstruct.ap.shaded.freemarker.debug.impl.RmiDebuggedEnvironmentImpl.DebugConfigurableModel, org.mapstruct.ap.shaded.freemarker.template.TemplateHashModel
        public TemplateModel get(String str) throws TemplateModelException {
            if ("configuration".equals(str)) {
                try {
                    return (TemplateModel) RmiDebuggedEnvironmentImpl.getCachedWrapperFor(((Template) this.configurable).getConfiguration());
                } catch (RemoteException e) {
                    throw new TemplateModelException((Exception) e);
                }
            }
            if (AppMeasurementSdk.ConditionalUserProperty.NAME.equals(str)) {
                return this.name;
            }
            return super.get(str);
        }
    }

    private static class DebugEnvironmentModel extends DebugConfigurableModel {
        private static final List KEYS = composeList(DebugConfigurableModel.KEYS, Arrays.asList("currentNamespace", "dataModel", "globalNamespace", "knownVariables", "mainNamespace", "template"));
        private TemplateModel knownVariables;

        DebugEnvironmentModel(Environment environment) {
            super(environment);
            this.knownVariables = new DebugMapModel() { // from class: org.mapstruct.ap.shaded.freemarker.debug.impl.RmiDebuggedEnvironmentImpl.DebugEnvironmentModel.1
                @Override // org.mapstruct.ap.shaded.freemarker.debug.impl.RmiDebuggedEnvironmentImpl.DebugMapModel
                Collection keySet() {
                    try {
                        return ((Environment) DebugEnvironmentModel.this.configurable).getKnownVariableNames();
                    } catch (TemplateModelException e) {
                        throw new UndeclaredThrowableException(e);
                    }
                }

                @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateHashModel
                public TemplateModel get(String str) throws TemplateModelException {
                    return ((Environment) DebugEnvironmentModel.this.configurable).getVariable(str);
                }
            };
        }

        @Override // org.mapstruct.ap.shaded.freemarker.debug.impl.RmiDebuggedEnvironmentImpl.DebugConfigurableModel, org.mapstruct.ap.shaded.freemarker.debug.impl.RmiDebuggedEnvironmentImpl.DebugMapModel
        Collection keySet() {
            return KEYS;
        }

        @Override // org.mapstruct.ap.shaded.freemarker.debug.impl.RmiDebuggedEnvironmentImpl.DebugConfigurableModel, org.mapstruct.ap.shaded.freemarker.template.TemplateHashModel
        public TemplateModel get(String str) throws TemplateModelException {
            if ("currentNamespace".equals(str)) {
                return ((Environment) this.configurable).getCurrentNamespace();
            }
            if ("dataModel".equals(str)) {
                return ((Environment) this.configurable).getDataModel();
            }
            if ("globalNamespace".equals(str)) {
                return ((Environment) this.configurable).getGlobalNamespace();
            }
            if ("knownVariables".equals(str)) {
                return this.knownVariables;
            }
            if ("mainNamespace".equals(str)) {
                return ((Environment) this.configurable).getMainNamespace();
            }
            if ("template".equals(str)) {
                try {
                    return (TemplateModel) RmiDebuggedEnvironmentImpl.getCachedWrapperFor(((Environment) this.configurable).getTemplate());
                } catch (RemoteException e) {
                    throw new TemplateModelException((Exception) e);
                }
            }
            return super.get(str);
        }
    }

    public static void cleanup() {
        Iterator it = remotes.iterator();
        while (it.hasNext()) {
            try {
                UnicastRemoteObject.unexportObject((Remote) it.next(), true);
            } catch (Exception unused) {
            }
        }
    }
}
