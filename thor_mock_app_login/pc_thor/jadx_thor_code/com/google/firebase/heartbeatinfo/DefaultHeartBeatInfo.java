package com.google.firebase.heartbeatinfo;

import android.content.Context;
import com.google.firebase.components.Component;
import com.google.firebase.components.ComponentContainer;
import com.google.firebase.components.Dependency;
import com.google.firebase.components.Lazy;
import com.google.firebase.heartbeatinfo.HeartBeatInfo;
import com.google.firebase.inject.Provider;

/* loaded from: classes2.dex */
public class DefaultHeartBeatInfo implements HeartBeatInfo {
    private Provider<HeartBeatInfoStorage> storage;

    private DefaultHeartBeatInfo(Context context) {
        this(new Lazy(DefaultHeartBeatInfo$$Lambda$1.lambdaFactory$(context)));
    }

    DefaultHeartBeatInfo(Provider<HeartBeatInfoStorage> provider) {
        this.storage = provider;
    }

    @Override // com.google.firebase.heartbeatinfo.HeartBeatInfo
    public HeartBeatInfo.HeartBeat getHeartBeatCode(String str) {
        long jCurrentTimeMillis = System.currentTimeMillis();
        boolean zShouldSendSdkHeartBeat = this.storage.get().shouldSendSdkHeartBeat(str, jCurrentTimeMillis);
        boolean zShouldSendGlobalHeartBeat = this.storage.get().shouldSendGlobalHeartBeat(jCurrentTimeMillis);
        if (zShouldSendSdkHeartBeat && zShouldSendGlobalHeartBeat) {
            return HeartBeatInfo.HeartBeat.COMBINED;
        }
        if (zShouldSendGlobalHeartBeat) {
            return HeartBeatInfo.HeartBeat.GLOBAL;
        }
        if (zShouldSendSdkHeartBeat) {
            return HeartBeatInfo.HeartBeat.SDK;
        }
        return HeartBeatInfo.HeartBeat.NONE;
    }

    public static Component<HeartBeatInfo> component() {
        return Component.builder(HeartBeatInfo.class).add(Dependency.required(Context.class)).factory(DefaultHeartBeatInfo$$Lambda$2.instance).build();
    }

    static /* synthetic */ HeartBeatInfo lambda$component$1(ComponentContainer componentContainer) {
        return new DefaultHeartBeatInfo((Context) componentContainer.get(Context.class));
    }
}
