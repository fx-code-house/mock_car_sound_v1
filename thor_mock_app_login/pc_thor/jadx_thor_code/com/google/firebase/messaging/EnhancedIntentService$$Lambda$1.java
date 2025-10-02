package com.google.firebase.messaging;

import java.util.concurrent.Executor;

/* compiled from: com.google.firebase:firebase-messaging@@21.0.0 */
/* loaded from: classes2.dex */
final /* synthetic */ class EnhancedIntentService$$Lambda$1 implements Executor {
    static final Executor $instance = new EnhancedIntentService$$Lambda$1();

    private EnhancedIntentService$$Lambda$1() {
    }

    @Override // java.util.concurrent.Executor
    public final void execute(Runnable runnable) {
        runnable.run();
    }
}
