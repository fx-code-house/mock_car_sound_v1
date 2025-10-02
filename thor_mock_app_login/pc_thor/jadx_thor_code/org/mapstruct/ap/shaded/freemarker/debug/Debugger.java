package org.mapstruct.ap.shaded.freemarker.debug;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Collection;
import java.util.List;

/* loaded from: classes3.dex */
public interface Debugger extends Remote {
    public static final int DEFAULT_PORT = 7011;

    void addBreakpoint(Breakpoint breakpoint) throws RemoteException;

    Object addDebuggerListener(DebuggerListener debuggerListener) throws RemoteException;

    List getBreakpoints() throws RemoteException;

    List getBreakpoints(String str) throws RemoteException;

    Collection getSuspendedEnvironments() throws RemoteException;

    void removeBreakpoint(Breakpoint breakpoint) throws RemoteException;

    void removeBreakpoints() throws RemoteException;

    void removeBreakpoints(String str) throws RemoteException;

    void removeDebuggerListener(Object obj) throws RemoteException;
}
