package com.thor.app.gui.dialog;

import androidx.core.app.NotificationCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelKt;
import javax.inject.Inject;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: FormatProgressViewModel.kt */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\b\u0007\u0018\u0000 \r2\u00020\u0001:\u0001\rB\u0007\b\u0007¢\u0006\u0002\u0010\u0002J\u0006\u0010\u000b\u001a\u00020\fR\u001c\u0010\u0003\u001a\u0010\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0017\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00050\b¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u000e"}, d2 = {"Lcom/thor/app/gui/dialog/FormatProgressViewModel;", "Landroidx/lifecycle/ViewModel;", "()V", "_progress", "Landroidx/lifecycle/MutableLiveData;", "", "kotlin.jvm.PlatformType", NotificationCompat.CATEGORY_PROGRESS, "Landroidx/lifecycle/LiveData;", "getProgress", "()Landroidx/lifecycle/LiveData;", "launchTimer", "", "Companion", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class FormatProgressViewModel extends ViewModel {
    public static final long FORMATTING_TIME_MILLIS = 70000;
    private final MutableLiveData<Integer> _progress;
    private final LiveData<Integer> progress;

    @Inject
    public FormatProgressViewModel() {
        MutableLiveData<Integer> mutableLiveData = new MutableLiveData<>(0);
        this._progress = mutableLiveData;
        this.progress = mutableLiveData;
        launchTimer();
    }

    public final LiveData<Integer> getProgress() {
        return this.progress;
    }

    /* compiled from: FormatProgressViewModel.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 8, 0}, xi = 48)
    @DebugMetadata(c = "com.thor.app.gui.dialog.FormatProgressViewModel$launchTimer$1", f = "FormatProgressViewModel.kt", i = {0}, l = {27}, m = "invokeSuspend", n = {"progressMillis"}, s = {"J$0"})
    /* renamed from: com.thor.app.gui.dialog.FormatProgressViewModel$launchTimer$1, reason: invalid class name */
    static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        long J$0;
        int label;

        AnonymousClass1(Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return FormatProgressViewModel.this.new AnonymousClass1(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Removed duplicated region for block: B:11:0x0030  */
        /* JADX WARN: Removed duplicated region for block: B:14:0x003d  */
        /* JADX WARN: Removed duplicated region for block: B:18:0x006b  */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:15:0x004a -> B:17:0x004d). Please report as a decompilation issue!!! */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public final java.lang.Object invokeSuspend(java.lang.Object r9) {
            /*
                r8 = this;
                java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                int r1 = r8.label
                r2 = 1
                r3 = 100
                if (r1 == 0) goto L1c
                if (r1 != r2) goto L14
                long r4 = r8.J$0
                kotlin.ResultKt.throwOnFailure(r9)
                r9 = r8
                goto L4d
            L14:
                java.lang.IllegalStateException r9 = new java.lang.IllegalStateException
                java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
                r9.<init>(r0)
                throw r9
            L1c:
                kotlin.ResultKt.throwOnFailure(r9)
                r4 = 0
                r9 = r8
            L22:
                com.thor.app.gui.dialog.FormatProgressViewModel r1 = com.thor.app.gui.dialog.FormatProgressViewModel.this
                androidx.lifecycle.LiveData r1 = r1.getProgress()
                java.lang.Object r1 = r1.getValue()
                java.lang.Integer r1 = (java.lang.Integer) r1
                if (r1 != 0) goto L35
                r1 = 0
                java.lang.Integer r1 = kotlin.coroutines.jvm.internal.Boxing.boxInt(r1)
            L35:
                java.lang.Number r1 = (java.lang.Number) r1
                int r1 = r1.intValue()
                if (r1 > r3) goto L6b
                r1 = r9
                kotlin.coroutines.Continuation r1 = (kotlin.coroutines.Continuation) r1
                r9.J$0 = r4
                r9.label = r2
                r6 = 1000(0x3e8, double:4.94E-321)
                java.lang.Object r1 = kotlinx.coroutines.DelayKt.delay(r6, r1)
                if (r1 != r0) goto L4d
                return r0
            L4d:
                r1 = 1000(0x3e8, float:1.401E-42)
                long r6 = (long) r1
                long r4 = r4 + r6
                com.thor.app.gui.dialog.FormatProgressViewModel r1 = com.thor.app.gui.dialog.FormatProgressViewModel.this
                androidx.lifecycle.MutableLiveData r1 = com.thor.app.gui.dialog.FormatProgressViewModel.access$get_progress$p(r1)
                float r6 = (float) r4
                r7 = 1200142336(0x4788b800, float:70000.0)
                float r6 = r6 / r7
                float r7 = (float) r3
                float r6 = r6 * r7
                int r6 = (int) r6
                int r6 = kotlin.ranges.RangesKt.coerceAtMost(r6, r3)
                java.lang.Integer r6 = kotlin.coroutines.jvm.internal.Boxing.boxInt(r6)
                r1.setValue(r6)
                goto L22
            L6b:
                kotlin.Unit r9 = kotlin.Unit.INSTANCE
                return r9
            */
            throw new UnsupportedOperationException("Method not decompiled: com.thor.app.gui.dialog.FormatProgressViewModel.AnonymousClass1.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    public final void launchTimer() {
        BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new AnonymousClass1(null), 3, null);
    }
}
