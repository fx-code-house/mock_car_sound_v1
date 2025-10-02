package com.karumi.dexter.listener.single;

import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

/* loaded from: classes2.dex */
public class CompositePermissionListener implements PermissionListener {
    private final Collection<PermissionListener> listeners;

    public CompositePermissionListener(Collection<PermissionListener> collection) {
        this.listeners = collection;
    }

    public CompositePermissionListener(PermissionListener... permissionListenerArr) {
        this(Arrays.asList(permissionListenerArr));
    }

    @Override // com.karumi.dexter.listener.single.PermissionListener
    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
        Iterator<PermissionListener> it = this.listeners.iterator();
        while (it.hasNext()) {
            it.next().onPermissionDenied(permissionDeniedResponse);
        }
    }

    @Override // com.karumi.dexter.listener.single.PermissionListener
    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
        Iterator<PermissionListener> it = this.listeners.iterator();
        while (it.hasNext()) {
            it.next().onPermissionGranted(permissionGrantedResponse);
        }
    }

    @Override // com.karumi.dexter.listener.single.PermissionListener
    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
        Iterator<PermissionListener> it = this.listeners.iterator();
        while (it.hasNext()) {
            it.next().onPermissionRationaleShouldBeShown(permissionRequest, permissionToken);
        }
    }
}
