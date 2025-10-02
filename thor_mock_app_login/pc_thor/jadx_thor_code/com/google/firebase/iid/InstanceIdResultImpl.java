package com.google.firebase.iid;

/* compiled from: com.google.firebase:firebase-iid@@21.0.0 */
/* loaded from: classes2.dex */
final class InstanceIdResultImpl implements InstanceIdResult {
    private final String id;
    private final String token;

    InstanceIdResultImpl(String str, String str2) {
        this.id = str;
        this.token = str2;
    }

    @Override // com.google.firebase.iid.InstanceIdResult
    public final String getId() {
        return this.id;
    }

    @Override // com.google.firebase.iid.InstanceIdResult
    public final String getToken() {
        return this.token;
    }
}
