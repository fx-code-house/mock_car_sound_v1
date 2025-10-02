package com.karumi.dexter.listener.multi;

import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes2.dex */
public class CompositeMultiplePermissionsListener implements MultiplePermissionsListener {
    private final Collection<MultiplePermissionsListener> listeners;

    public CompositeMultiplePermissionsListener(Collection<MultiplePermissionsListener> collection) {
        this.listeners = collection;
    }

    public CompositeMultiplePermissionsListener(MultiplePermissionsListener... multiplePermissionsListenerArr) {
        this(Arrays.asList(multiplePermissionsListenerArr));
    }

    @Override // com.karumi.dexter.listener.multi.MultiplePermissionsListener
    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
        Iterator<MultiplePermissionsListener> it = this.listeners.iterator();
        while (it.hasNext()) {
            it.next().onPermissionRationaleShouldBeShown(list, permissionToken);
        }
    }

    @Override // com.karumi.dexter.listener.multi.MultiplePermissionsListener
    public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
        Iterator<MultiplePermissionsListener> it = this.listeners.iterator();
        while (it.hasNext()) {
            it.next().onPermissionsChecked(multiplePermissionsReport);
        }
    }
}
