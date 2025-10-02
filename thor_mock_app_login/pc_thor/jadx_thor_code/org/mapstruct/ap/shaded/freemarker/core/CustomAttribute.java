package org.mapstruct.ap.shaded.freemarker.core;

import org.mapstruct.ap.shaded.freemarker.template.Template;

/* loaded from: classes3.dex */
public class CustomAttribute {
    public static final int SCOPE_CONFIGURATION = 2;
    public static final int SCOPE_ENVIRONMENT = 0;
    public static final int SCOPE_TEMPLATE = 1;
    private final Object key = new Object();
    private final int scope;

    protected Object create() {
        return null;
    }

    public CustomAttribute(int i) {
        if (i != 0 && i != 1 && i != 2) {
            throw new IllegalArgumentException();
        }
        this.scope = i;
    }

    public final Object get() {
        return getScopeConfigurable().getCustomAttribute(this.key, this);
    }

    public final Object get(Template template) {
        if (this.scope != 1) {
            throw new UnsupportedOperationException("This is not a template-scope attribute");
        }
        return template.getCustomAttribute(this.key, this);
    }

    public final void set(Object obj) {
        getScopeConfigurable().setCustomAttribute(this.key, obj);
    }

    public final void set(Object obj, Template template) {
        if (this.scope != 1) {
            throw new UnsupportedOperationException("This is not a template-scope attribute");
        }
        template.setCustomAttribute(this.key, obj);
    }

    private Configurable getScopeConfigurable() {
        Environment currentEnvironment = Environment.getCurrentEnvironment();
        if (currentEnvironment == null) {
            throw new IllegalStateException("No current environment");
        }
        int i = this.scope;
        if (i == 0) {
            return currentEnvironment;
        }
        if (i == 1) {
            return currentEnvironment.getParent();
        }
        if (i == 2) {
            return currentEnvironment.getParent().getParent();
        }
        throw new Error();
    }
}
