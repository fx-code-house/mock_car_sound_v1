package com.karumi.dexter.listener.single;

import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;

/* loaded from: classes2.dex */
public interface PermissionListener {
    void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse);

    void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse);

    void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken);
}
