package org.mapstruct.ap.shaded.freemarker.debug;

import java.rmi.RemoteException;

/* loaded from: classes3.dex */
public interface DebuggedEnvironment extends DebugModel {
    long getId() throws RemoteException;

    void resume() throws RemoteException;

    void stop() throws RemoteException;
}
