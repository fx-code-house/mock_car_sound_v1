package com.thor.app.gui.activities.splash;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: VersionState.kt */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0004\u0003\u0004\u0005\u0006B\u0007\b\u0004¢\u0006\u0002\u0010\u0002\u0082\u0001\u0004\u0007\b\t\n¨\u0006\u000b"}, d2 = {"Lcom/thor/app/gui/activities/splash/VersionState;", "", "()V", "None", "actualVersion", "oldVersion", "oldVersionApp", "Lcom/thor/app/gui/activities/splash/VersionState$None;", "Lcom/thor/app/gui/activities/splash/VersionState$actualVersion;", "Lcom/thor/app/gui/activities/splash/VersionState$oldVersion;", "Lcom/thor/app/gui/activities/splash/VersionState$oldVersionApp;", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public abstract class VersionState {
    public /* synthetic */ VersionState(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    /* compiled from: VersionState.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lcom/thor/app/gui/activities/splash/VersionState$actualVersion;", "Lcom/thor/app/gui/activities/splash/VersionState;", "()V", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class actualVersion extends VersionState {
        public static final actualVersion INSTANCE = new actualVersion();

        private actualVersion() {
            super(null);
        }
    }

    private VersionState() {
    }

    /* compiled from: VersionState.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lcom/thor/app/gui/activities/splash/VersionState$oldVersionApp;", "Lcom/thor/app/gui/activities/splash/VersionState;", "()V", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class oldVersionApp extends VersionState {
        public static final oldVersionApp INSTANCE = new oldVersionApp();

        private oldVersionApp() {
            super(null);
        }
    }

    /* compiled from: VersionState.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lcom/thor/app/gui/activities/splash/VersionState$oldVersion;", "Lcom/thor/app/gui/activities/splash/VersionState;", "()V", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class oldVersion extends VersionState {
        public static final oldVersion INSTANCE = new oldVersion();

        private oldVersion() {
            super(null);
        }
    }

    /* compiled from: VersionState.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lcom/thor/app/gui/activities/splash/VersionState$None;", "Lcom/thor/app/gui/activities/splash/VersionState;", "()V", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class None extends VersionState {
        public static final None INSTANCE = new None();

        private None() {
            super(null);
        }
    }
}
