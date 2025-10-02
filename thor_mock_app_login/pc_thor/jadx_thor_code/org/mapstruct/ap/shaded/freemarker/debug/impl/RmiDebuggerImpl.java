package org.mapstruct.ap.shaded.freemarker.debug.impl;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Collection;
import java.util.List;
import org.mapstruct.ap.shaded.freemarker.debug.Breakpoint;
import org.mapstruct.ap.shaded.freemarker.debug.Debugger;
import org.mapstruct.ap.shaded.freemarker.debug.DebuggerListener;

/* loaded from: classes3.dex */
class RmiDebuggerImpl extends UnicastRemoteObject implements Debugger {
    private static final long serialVersionUID = 1;
    private final RmiDebuggerService service;

    protected RmiDebuggerImpl(RmiDebuggerService rmiDebuggerService) throws RemoteException {
        this.service = rmiDebuggerService;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.debug.Debugger
    public void addBreakpoint(Breakpoint breakpoint) {
        this.service.addBreakpoint(breakpoint);
    }

    @Override // org.mapstruct.ap.shaded.freemarker.debug.Debugger
    public Object addDebuggerListener(DebuggerListener debuggerListener) {
        return this.service.addDebuggerListener(debuggerListener);
    }

    @Override // org.mapstruct.ap.shaded.freemarker.debug.Debugger
    public List getBreakpoints() {
        return this.service.getBreakpointsSpi();
    }

    @Override // org.mapstruct.ap.shaded.freemarker.debug.Debugger
    public List getBreakpoints(String str) {
        return this.service.getBreakpointsSpi(str);
    }

    @Override // org.mapstruct.ap.shaded.freemarker.debug.Debugger
    public Collection getSuspendedEnvironments() {
        return this.service.getSuspendedEnvironments();
    }

    @Override // org.mapstruct.ap.shaded.freemarker.debug.Debugger
    public void removeBreakpoint(Breakpoint breakpoint) {
        this.service.removeBreakpoint(breakpoint);
    }

    @Override // org.mapstruct.ap.shaded.freemarker.debug.Debugger
    public void removeDebuggerListener(Object obj) {
        this.service.removeDebuggerListener(obj);
    }

    @Override // org.mapstruct.ap.shaded.freemarker.debug.Debugger
    public void removeBreakpoints() {
        this.service.removeBreakpoints();
    }

    @Override // org.mapstruct.ap.shaded.freemarker.debug.Debugger
    public void removeBreakpoints(String str) {
        this.service.removeBreakpoints(str);
    }
}
