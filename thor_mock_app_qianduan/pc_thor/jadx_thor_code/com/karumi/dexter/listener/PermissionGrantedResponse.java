package com.karumi.dexter.listener;

/* loaded from: classes2.dex */
public final class PermissionGrantedResponse {
    private final PermissionRequest requestedPermission;

    public PermissionGrantedResponse(PermissionRequest permissionRequest) {
        this.requestedPermission = permissionRequest;
    }

    public static PermissionGrantedResponse from(String str) {
        return new PermissionGrantedResponse(new PermissionRequest(str));
    }

    public String getPermissionName() {
        return this.requestedPermission.getName();
    }

    public PermissionRequest getRequestedPermission() {
        return this.requestedPermission;
    }
}
