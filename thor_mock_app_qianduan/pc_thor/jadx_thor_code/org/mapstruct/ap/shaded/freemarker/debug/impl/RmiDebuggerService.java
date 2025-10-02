package org.mapstruct.ap.shaded.freemarker.debug.impl;

import java.io.IOException;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.rmi.RemoteException;
import java.rmi.server.RemoteObject;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.mapstruct.ap.shaded.freemarker.core.DebugBreak;
import org.mapstruct.ap.shaded.freemarker.core.Environment;
import org.mapstruct.ap.shaded.freemarker.core.TemplateElement;
import org.mapstruct.ap.shaded.freemarker.debug.Breakpoint;
import org.mapstruct.ap.shaded.freemarker.debug.DebuggerListener;
import org.mapstruct.ap.shaded.freemarker.debug.EnvironmentSuspendedEvent;
import org.mapstruct.ap.shaded.freemarker.template.Template;
import org.mapstruct.ap.shaded.freemarker.template.utility.UndeclaredThrowableException;

/* loaded from: classes3.dex */
class RmiDebuggerService extends DebuggerService {
    private final RmiDebuggerImpl debugger;
    private DebuggerServer server;
    private final Map templateDebugInfos = new HashMap();
    private final HashSet suspendedEnvironments = new HashSet();
    private final Map listeners = new HashMap();
    private final ReferenceQueue refQueue = new ReferenceQueue();

    RmiDebuggerService() {
        try {
            RmiDebuggerImpl rmiDebuggerImpl = new RmiDebuggerImpl(this);
            this.debugger = rmiDebuggerImpl;
            DebuggerServer debuggerServer = new DebuggerServer(RemoteObject.toStub(rmiDebuggerImpl));
            this.server = debuggerServer;
            debuggerServer.start();
        } catch (RemoteException e) {
            e.printStackTrace();
            throw new UndeclaredThrowableException(e);
        }
    }

    @Override // org.mapstruct.ap.shaded.freemarker.debug.impl.DebuggerService
    List getBreakpointsSpi(String str) {
        List list;
        synchronized (this.templateDebugInfos) {
            TemplateDebugInfo templateDebugInfoFindTemplateDebugInfo = findTemplateDebugInfo(str);
            list = templateDebugInfoFindTemplateDebugInfo == null ? Collections.EMPTY_LIST : templateDebugInfoFindTemplateDebugInfo.breakpoints;
        }
        return list;
    }

    List getBreakpointsSpi() {
        ArrayList arrayList = new ArrayList();
        synchronized (this.templateDebugInfos) {
            Iterator it = this.templateDebugInfos.values().iterator();
            while (it.hasNext()) {
                arrayList.addAll(((TemplateDebugInfo) it.next()).breakpoints);
            }
        }
        Collections.sort(arrayList);
        return arrayList;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.debug.impl.DebuggerService
    boolean suspendEnvironmentSpi(Environment environment, String str, int i) throws RemoteException {
        RmiDebuggedEnvironmentImpl rmiDebuggedEnvironmentImpl = (RmiDebuggedEnvironmentImpl) RmiDebuggedEnvironmentImpl.getCachedWrapperFor(environment);
        synchronized (this.suspendedEnvironments) {
            this.suspendedEnvironments.add(rmiDebuggedEnvironmentImpl);
        }
        try {
            EnvironmentSuspendedEvent environmentSuspendedEvent = new EnvironmentSuspendedEvent(this, str, i, rmiDebuggedEnvironmentImpl);
            synchronized (this.listeners) {
                Iterator it = this.listeners.values().iterator();
                while (it.hasNext()) {
                    ((DebuggerListener) it.next()).environmentSuspended(environmentSuspendedEvent);
                }
            }
            synchronized (rmiDebuggedEnvironmentImpl) {
                try {
                    rmiDebuggedEnvironmentImpl.wait();
                } catch (InterruptedException unused) {
                }
            }
            boolean zIsStopped = rmiDebuggedEnvironmentImpl.isStopped();
            synchronized (this.suspendedEnvironments) {
                this.suspendedEnvironments.remove(rmiDebuggedEnvironmentImpl);
            }
            return zIsStopped;
        } catch (Throwable th) {
            synchronized (this.suspendedEnvironments) {
                this.suspendedEnvironments.remove(rmiDebuggedEnvironmentImpl);
                throw th;
            }
        }
    }

    @Override // org.mapstruct.ap.shaded.freemarker.debug.impl.DebuggerService
    void registerTemplateSpi(Template template) {
        String name = template.getName();
        synchronized (this.templateDebugInfos) {
            TemplateDebugInfo templateDebugInfoCreateTemplateDebugInfo = createTemplateDebugInfo(name);
            templateDebugInfoCreateTemplateDebugInfo.templates.add(new TemplateReference(name, template, this.refQueue));
            Iterator it = templateDebugInfoCreateTemplateDebugInfo.breakpoints.iterator();
            while (it.hasNext()) {
                insertDebugBreak(template, (Breakpoint) it.next());
            }
        }
    }

    Collection getSuspendedEnvironments() {
        return (Collection) this.suspendedEnvironments.clone();
    }

    Object addDebuggerListener(DebuggerListener debuggerListener) {
        Long l;
        synchronized (this.listeners) {
            l = new Long(System.currentTimeMillis());
            this.listeners.put(l, debuggerListener);
        }
        return l;
    }

    void removeDebuggerListener(Object obj) {
        synchronized (this.listeners) {
            this.listeners.remove(obj);
        }
    }

    void addBreakpoint(Breakpoint breakpoint) {
        String templateName = breakpoint.getTemplateName();
        synchronized (this.templateDebugInfos) {
            TemplateDebugInfo templateDebugInfoCreateTemplateDebugInfo = createTemplateDebugInfo(templateName);
            List list = templateDebugInfoCreateTemplateDebugInfo.breakpoints;
            if (Collections.binarySearch(list, breakpoint) < 0) {
                list.add((-r3) - 1, breakpoint);
                Iterator it = templateDebugInfoCreateTemplateDebugInfo.templates.iterator();
                while (it.hasNext()) {
                    Template template = ((TemplateReference) it.next()).getTemplate();
                    if (template == null) {
                        it.remove();
                    } else {
                        insertDebugBreak(template, breakpoint);
                    }
                }
            }
        }
    }

    private static void insertDebugBreak(Template template, Breakpoint breakpoint) {
        TemplateElement templateElementFindTemplateElement = findTemplateElement(template.getRootTreeNode(), breakpoint.getLine());
        if (templateElementFindTemplateElement == null) {
            return;
        }
        TemplateElement templateElement = (TemplateElement) templateElementFindTemplateElement.getParent();
        templateElement.setChildAt(templateElement.getIndex(templateElementFindTemplateElement), new DebugBreak(templateElementFindTemplateElement));
    }

    private static TemplateElement findTemplateElement(TemplateElement templateElement, int i) {
        TemplateElement templateElement2 = null;
        if (templateElement.getBeginLine() > i || templateElement.getEndLine() < i) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        Enumeration enumerationChildren = templateElement.children();
        while (enumerationChildren.hasMoreElements()) {
            TemplateElement templateElementFindTemplateElement = findTemplateElement((TemplateElement) enumerationChildren.nextElement(), i);
            if (templateElementFindTemplateElement != null) {
                arrayList.add(templateElementFindTemplateElement);
            }
        }
        int i2 = 0;
        while (true) {
            if (i2 >= arrayList.size()) {
                break;
            }
            TemplateElement templateElement3 = (TemplateElement) arrayList.get(i2);
            if (templateElement2 == null) {
                templateElement2 = templateElement3;
            }
            if (templateElement3.getBeginLine() == i && templateElement3.getEndLine() > i) {
                templateElement2 = templateElement3;
            }
            if (templateElement3.getBeginLine() == templateElement3.getEndLine() && templateElement3.getBeginLine() == i) {
                templateElement2 = templateElement3;
                break;
            }
            i2++;
        }
        return templateElement2 != null ? templateElement2 : templateElement;
    }

    private TemplateDebugInfo findTemplateDebugInfo(String str) {
        processRefQueue();
        return (TemplateDebugInfo) this.templateDebugInfos.get(str);
    }

    private TemplateDebugInfo createTemplateDebugInfo(String str) {
        TemplateDebugInfo templateDebugInfoFindTemplateDebugInfo = findTemplateDebugInfo(str);
        if (templateDebugInfoFindTemplateDebugInfo != null) {
            return templateDebugInfoFindTemplateDebugInfo;
        }
        TemplateDebugInfo templateDebugInfo = new TemplateDebugInfo();
        this.templateDebugInfos.put(str, templateDebugInfo);
        return templateDebugInfo;
    }

    void removeBreakpoint(Breakpoint breakpoint) {
        String templateName = breakpoint.getTemplateName();
        synchronized (this.templateDebugInfos) {
            TemplateDebugInfo templateDebugInfoFindTemplateDebugInfo = findTemplateDebugInfo(templateName);
            if (templateDebugInfoFindTemplateDebugInfo != null) {
                List list = templateDebugInfoFindTemplateDebugInfo.breakpoints;
                int iBinarySearch = Collections.binarySearch(list, breakpoint);
                if (iBinarySearch >= 0) {
                    list.remove(iBinarySearch);
                    Iterator it = templateDebugInfoFindTemplateDebugInfo.templates.iterator();
                    while (it.hasNext()) {
                        Template template = ((TemplateReference) it.next()).getTemplate();
                        if (template == null) {
                            it.remove();
                        } else {
                            removeDebugBreak(template, breakpoint);
                        }
                    }
                }
                if (templateDebugInfoFindTemplateDebugInfo.isEmpty()) {
                    this.templateDebugInfos.remove(templateName);
                }
            }
        }
    }

    private void removeDebugBreak(Template template, Breakpoint breakpoint) {
        DebugBreak debugBreak;
        TemplateElement templateElementFindTemplateElement = findTemplateElement(template.getRootTreeNode(), breakpoint.getLine());
        if (templateElementFindTemplateElement == null) {
            return;
        }
        while (true) {
            if (templateElementFindTemplateElement == null) {
                debugBreak = null;
                break;
            } else {
                if (templateElementFindTemplateElement instanceof DebugBreak) {
                    debugBreak = (DebugBreak) templateElementFindTemplateElement;
                    break;
                }
                templateElementFindTemplateElement = (TemplateElement) templateElementFindTemplateElement.getParent();
            }
        }
        if (debugBreak == null) {
            return;
        }
        TemplateElement templateElement = (TemplateElement) debugBreak.getParent();
        templateElement.setChildAt(templateElement.getIndex(debugBreak), (TemplateElement) debugBreak.getChildAt(0));
    }

    void removeBreakpoints(String str) {
        synchronized (this.templateDebugInfos) {
            TemplateDebugInfo templateDebugInfoFindTemplateDebugInfo = findTemplateDebugInfo(str);
            if (templateDebugInfoFindTemplateDebugInfo != null) {
                removeBreakpoints(templateDebugInfoFindTemplateDebugInfo);
                if (templateDebugInfoFindTemplateDebugInfo.isEmpty()) {
                    this.templateDebugInfos.remove(str);
                }
            }
        }
    }

    void removeBreakpoints() {
        synchronized (this.templateDebugInfos) {
            Iterator it = this.templateDebugInfos.values().iterator();
            while (it.hasNext()) {
                TemplateDebugInfo templateDebugInfo = (TemplateDebugInfo) it.next();
                removeBreakpoints(templateDebugInfo);
                if (templateDebugInfo.isEmpty()) {
                    it.remove();
                }
            }
        }
    }

    private void removeBreakpoints(TemplateDebugInfo templateDebugInfo) {
        templateDebugInfo.breakpoints.clear();
        Iterator it = templateDebugInfo.templates.iterator();
        while (it.hasNext()) {
            Template template = ((TemplateReference) it.next()).getTemplate();
            if (template == null) {
                it.remove();
            } else {
                removeDebugBreaks(template.getRootTreeNode());
            }
        }
    }

    private void removeDebugBreaks(TemplateElement templateElement) {
        int childCount = templateElement.getChildCount();
        for (int i = 0; i < childCount; i++) {
            TemplateElement templateElement2 = (TemplateElement) templateElement.getChildAt(i);
            while (templateElement2 instanceof DebugBreak) {
                templateElement2 = (TemplateElement) templateElement2.getChildAt(0);
                templateElement.setChildAt(i, templateElement2);
            }
            removeDebugBreaks(templateElement2);
        }
    }

    private static final class TemplateDebugInfo {
        final List breakpoints;
        final List templates;

        private TemplateDebugInfo() {
            this.templates = new ArrayList();
            this.breakpoints = new ArrayList();
        }

        boolean isEmpty() {
            return this.templates.isEmpty() && this.breakpoints.isEmpty();
        }
    }

    private static final class TemplateReference extends WeakReference {
        final String templateName;

        TemplateReference(String str, Template template, ReferenceQueue referenceQueue) {
            super(template, referenceQueue);
            this.templateName = str;
        }

        Template getTemplate() {
            return (Template) get();
        }
    }

    private void processRefQueue() {
        while (true) {
            TemplateReference templateReference = (TemplateReference) this.refQueue.poll();
            if (templateReference == null) {
                return;
            }
            TemplateDebugInfo templateDebugInfoFindTemplateDebugInfo = findTemplateDebugInfo(templateReference.templateName);
            if (templateDebugInfoFindTemplateDebugInfo != null) {
                templateDebugInfoFindTemplateDebugInfo.templates.remove(templateReference);
                if (templateDebugInfoFindTemplateDebugInfo.isEmpty()) {
                    this.templateDebugInfos.remove(templateReference.templateName);
                }
            }
        }
    }

    @Override // org.mapstruct.ap.shaded.freemarker.debug.impl.DebuggerService
    void shutdownSpi() throws IOException {
        this.server.stop();
        try {
            UnicastRemoteObject.unexportObject(this.debugger, true);
        } catch (Exception unused) {
        }
        RmiDebuggedEnvironmentImpl.cleanup();
    }
}
