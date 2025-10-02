package org.mapstruct.ap.shaded.freemarker.debug.impl;

import java.rmi.RemoteException;
import java.util.Collections;
import java.util.List;
import org.mapstruct.ap.shaded.freemarker.core.Environment;
import org.mapstruct.ap.shaded.freemarker.template.Template;
import org.mapstruct.ap.shaded.freemarker.template.utility.SecurityUtilities;

/* loaded from: classes3.dex */
public abstract class DebuggerService {
    private static final DebuggerService instance = createInstance();

    abstract List getBreakpointsSpi(String str);

    abstract void registerTemplateSpi(Template template);

    abstract void shutdownSpi();

    abstract boolean suspendEnvironmentSpi(Environment environment, String str, int i) throws RemoteException;

    private static DebuggerService createInstance() {
        return SecurityUtilities.getSystemProperty("org.mapstruct.ap.shaded.freemarker.debug.password") == null ? new NoOpDebuggerService() : new RmiDebuggerService();
    }

    public static List getBreakpoints(String str) {
        return instance.getBreakpointsSpi(str);
    }

    public static void registerTemplate(Template template) {
        instance.registerTemplateSpi(template);
    }

    public static boolean suspendEnvironment(Environment environment, String str, int i) throws RemoteException {
        return instance.suspendEnvironmentSpi(environment, str, i);
    }

    public static void shutdown() {
        instance.shutdownSpi();
    }

    private static class NoOpDebuggerService extends DebuggerService {
        @Override // org.mapstruct.ap.shaded.freemarker.debug.impl.DebuggerService
        void registerTemplateSpi(Template template) {
        }

        @Override // org.mapstruct.ap.shaded.freemarker.debug.impl.DebuggerService
        void shutdownSpi() {
        }

        private NoOpDebuggerService() {
        }

        @Override // org.mapstruct.ap.shaded.freemarker.debug.impl.DebuggerService
        List getBreakpointsSpi(String str) {
            return Collections.EMPTY_LIST;
        }

        @Override // org.mapstruct.ap.shaded.freemarker.debug.impl.DebuggerService
        boolean suspendEnvironmentSpi(Environment environment, String str, int i) {
            throw new UnsupportedOperationException();
        }
    }
}
