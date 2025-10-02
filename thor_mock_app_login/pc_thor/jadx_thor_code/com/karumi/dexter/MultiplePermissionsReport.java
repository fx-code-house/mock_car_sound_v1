package com.karumi.dexter;

import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/* loaded from: classes2.dex */
public final class MultiplePermissionsReport {
    private final List<PermissionGrantedResponse> grantedPermissionResponses = new LinkedList();
    private final List<PermissionDeniedResponse> deniedPermissionResponses = new LinkedList();

    MultiplePermissionsReport() {
    }

    boolean addDeniedPermissionResponse(PermissionDeniedResponse permissionDeniedResponse) {
        return this.deniedPermissionResponses.add(permissionDeniedResponse);
    }

    boolean addGrantedPermissionResponse(PermissionGrantedResponse permissionGrantedResponse) {
        return this.grantedPermissionResponses.add(permissionGrantedResponse);
    }

    public boolean areAllPermissionsGranted() {
        return this.deniedPermissionResponses.isEmpty();
    }

    void clear() {
        this.grantedPermissionResponses.clear();
        this.deniedPermissionResponses.clear();
    }

    public List<PermissionDeniedResponse> getDeniedPermissionResponses() {
        return this.deniedPermissionResponses;
    }

    public List<PermissionGrantedResponse> getGrantedPermissionResponses() {
        return this.grantedPermissionResponses;
    }

    public boolean isAnyPermissionPermanentlyDenied() {
        Iterator<PermissionDeniedResponse> it = this.deniedPermissionResponses.iterator();
        while (it.hasNext()) {
            if (it.next().isPermanentlyDenied()) {
                return true;
            }
        }
        return false;
    }
}
