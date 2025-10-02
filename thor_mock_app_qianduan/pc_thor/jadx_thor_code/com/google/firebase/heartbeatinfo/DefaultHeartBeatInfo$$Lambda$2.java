package com.google.firebase.heartbeatinfo;

import com.google.firebase.components.ComponentContainer;
import com.google.firebase.components.ComponentFactory;

/* loaded from: classes2.dex */
final /* synthetic */ class DefaultHeartBeatInfo$$Lambda$2 implements ComponentFactory {
    private static final DefaultHeartBeatInfo$$Lambda$2 instance = new DefaultHeartBeatInfo$$Lambda$2();

    private DefaultHeartBeatInfo$$Lambda$2() {
    }

    @Override // com.google.firebase.components.ComponentFactory
    public Object create(ComponentContainer componentContainer) {
        return DefaultHeartBeatInfo.lambda$component$1(componentContainer);
    }
}
