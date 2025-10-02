package com.google.firebase;

import android.content.Context;
import com.google.firebase.platforminfo.LibraryVersionComponent;

/* loaded from: classes2.dex */
final /* synthetic */ class FirebaseCommonRegistrar$$Lambda$6 implements LibraryVersionComponent.VersionExtractor {
    private static final FirebaseCommonRegistrar$$Lambda$6 instance = new FirebaseCommonRegistrar$$Lambda$6();

    private FirebaseCommonRegistrar$$Lambda$6() {
    }

    @Override // com.google.firebase.platforminfo.LibraryVersionComponent.VersionExtractor
    public String extract(Object obj) {
        return FirebaseCommonRegistrar.lambda$getComponents$3((Context) obj);
    }
}
