package com.thor.app.gui.activities.updatecheck;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: UpdateResult.kt */
@Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0002\u0003\u0004B\u0007\b\u0004¢\u0006\u0002\u0010\u0002\u0082\u0001\u0002\u0005\u0006¨\u0006\u0007"}, d2 = {"Lcom/thor/app/gui/activities/updatecheck/UpdateResult;", "", "()V", "Failure", "Success", "Lcom/thor/app/gui/activities/updatecheck/UpdateResult$Failure;", "Lcom/thor/app/gui/activities/updatecheck/UpdateResult$Success;", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public abstract class UpdateResult {
    public /* synthetic */ UpdateResult(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    /* compiled from: UpdateResult.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lcom/thor/app/gui/activities/updatecheck/UpdateResult$Success;", "Lcom/thor/app/gui/activities/updatecheck/UpdateResult;", "()V", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Success extends UpdateResult {
        public static final Success INSTANCE = new Success();

        private Success() {
            super(null);
        }
    }

    private UpdateResult() {
    }

    /* compiled from: UpdateResult.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lcom/thor/app/gui/activities/updatecheck/UpdateResult$Failure;", "Lcom/thor/app/gui/activities/updatecheck/UpdateResult;", "()V", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Failure extends UpdateResult {
        public static final Failure INSTANCE = new Failure();

        private Failure() {
            super(null);
        }
    }
}
