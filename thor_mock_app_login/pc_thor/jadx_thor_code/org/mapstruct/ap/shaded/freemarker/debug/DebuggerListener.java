package org.mapstruct.ap.shaded.freemarker.debug;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.EventListener;

/* loaded from: classes3.dex */
public interface DebuggerListener extends Remote, EventListener {
    void environmentSuspended(EnvironmentSuspendedEvent environmentSuspendedEvent) throws RemoteException;
}
