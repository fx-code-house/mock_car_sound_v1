package org.mapstruct.ap.shaded.freemarker.core;

import org.mapstruct.ap.shaded.freemarker.ext.beans.BeansWrapper;
import org.mapstruct.ap.shaded.freemarker.template.Configuration;

/* loaded from: classes3.dex */
public class _SettingEvaluationEnvironment {
    private static final ThreadLocal CURRENT = new ThreadLocal();
    private BeansWrapper objectWrapper;

    public static _SettingEvaluationEnvironment getCurrent() {
        Object obj = CURRENT.get();
        if (obj != null) {
            return (_SettingEvaluationEnvironment) obj;
        }
        return new _SettingEvaluationEnvironment();
    }

    public static _SettingEvaluationEnvironment startScope() {
        ThreadLocal threadLocal = CURRENT;
        Object obj = threadLocal.get();
        threadLocal.set(new _SettingEvaluationEnvironment());
        return (_SettingEvaluationEnvironment) obj;
    }

    public static void endScope(_SettingEvaluationEnvironment _settingevaluationenvironment) {
        CURRENT.set(_settingevaluationenvironment);
    }

    public BeansWrapper getObjectWrapper() {
        if (this.objectWrapper == null) {
            this.objectWrapper = new BeansWrapper(Configuration.VERSION_2_3_21);
        }
        return this.objectWrapper;
    }
}
