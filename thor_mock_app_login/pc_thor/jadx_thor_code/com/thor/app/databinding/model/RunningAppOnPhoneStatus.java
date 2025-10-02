package com.thor.app.databinding.model;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: RunningAppOnPhoneStatus.kt */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0007\b\u0086\u0001\u0018\u0000 \u00072\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001\u0007B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006¨\u0006\b"}, d2 = {"Lcom/thor/app/databinding/model/RunningAppOnPhoneStatus;", "", "(Ljava/lang/String;I)V", "OFF", "MAIN", "SHOP", "LOADING_PACKAGE", "Companion", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes2.dex */
public enum RunningAppOnPhoneStatus {
    OFF,
    MAIN,
    SHOP,
    LOADING_PACKAGE;


    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);

    /* compiled from: RunningAppOnPhoneStatus.kt */
    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006J\u000e\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0004J\u000e\u0010\n\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0004J\u000e\u0010\n\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\u0006¨\u0006\f"}, d2 = {"Lcom/thor/app/databinding/model/RunningAppOnPhoneStatus$Companion;", "", "()V", "getStatusByOrdinal", "Lcom/thor/app/databinding/model/RunningAppOnPhoneStatus;", "ordinal", "", "isBlockUploadingPresetsFromService", "", "state", "isRunning", "stateOrdinal", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final boolean isRunning(RunningAppOnPhoneStatus state) {
            Intrinsics.checkNotNullParameter(state, "state");
            return state != RunningAppOnPhoneStatus.OFF;
        }

        public final boolean isRunning(int stateOrdinal) {
            return stateOrdinal != RunningAppOnPhoneStatus.OFF.ordinal();
        }

        public final boolean isBlockUploadingPresetsFromService(RunningAppOnPhoneStatus state) {
            Intrinsics.checkNotNullParameter(state, "state");
            return state == RunningAppOnPhoneStatus.MAIN || state == RunningAppOnPhoneStatus.LOADING_PACKAGE;
        }

        public final RunningAppOnPhoneStatus getStatusByOrdinal(int ordinal) {
            return ordinal == RunningAppOnPhoneStatus.OFF.ordinal() ? RunningAppOnPhoneStatus.OFF : ordinal == RunningAppOnPhoneStatus.MAIN.ordinal() ? RunningAppOnPhoneStatus.MAIN : ordinal == RunningAppOnPhoneStatus.SHOP.ordinal() ? RunningAppOnPhoneStatus.SHOP : ordinal == RunningAppOnPhoneStatus.LOADING_PACKAGE.ordinal() ? RunningAppOnPhoneStatus.LOADING_PACKAGE : RunningAppOnPhoneStatus.OFF;
        }
    }
}
