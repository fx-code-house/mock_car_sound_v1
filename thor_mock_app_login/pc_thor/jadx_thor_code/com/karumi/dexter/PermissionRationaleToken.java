package com.karumi.dexter;

/* loaded from: classes2.dex */
final class PermissionRationaleToken implements PermissionToken {
    private final DexterInstance dexterInstance;
    private boolean isTokenResolved = false;

    PermissionRationaleToken(DexterInstance dexterInstance) {
        this.dexterInstance = dexterInstance;
    }

    @Override // com.karumi.dexter.PermissionToken
    public void cancelPermissionRequest() {
        if (this.isTokenResolved) {
            return;
        }
        this.dexterInstance.onCancelPermissionRequest();
        this.isTokenResolved = true;
    }

    @Override // com.karumi.dexter.PermissionToken
    public void continuePermissionRequest() {
        if (this.isTokenResolved) {
            return;
        }
        this.dexterInstance.onContinuePermissionRequest();
        this.isTokenResolved = true;
    }
}
