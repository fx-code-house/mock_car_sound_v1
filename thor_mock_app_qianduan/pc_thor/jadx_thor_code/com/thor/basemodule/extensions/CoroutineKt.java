package com.thor.basemodule.extensions;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.Job;

/* compiled from: Coroutine.kt */
@Metadata(d1 = {"\u0000\u001c\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a>\u0010\u0000\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u00020\u00030\u0001\"\u0004\b\u0000\u0010\u00022\b\b\u0002\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0012\u0010\b\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u00020\u00030\u0001¨\u0006\t"}, d2 = {"throttleLatest", "Lkotlin/Function1;", ExifInterface.GPS_DIRECTION_TRUE, "", "intervalMs", "", "coroutineScope", "Lkotlinx/coroutines/CoroutineScope;", "destinationFunction", "basemodule_release"}, k = 2, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class CoroutineKt {
    public static /* synthetic */ Function1 throttleLatest$default(long j, CoroutineScope coroutineScope, Function1 function1, int i, Object obj) {
        if ((i & 1) != 0) {
            j = 300;
        }
        return throttleLatest(j, coroutineScope, function1);
    }

    public static final <T> Function1<T, Unit> throttleLatest(final long j, final CoroutineScope coroutineScope, final Function1<? super T, Unit> destinationFunction) {
        Intrinsics.checkNotNullParameter(coroutineScope, "coroutineScope");
        Intrinsics.checkNotNullParameter(destinationFunction, "destinationFunction");
        final Ref.ObjectRef objectRef = new Ref.ObjectRef();
        final Ref.ObjectRef objectRef2 = new Ref.ObjectRef();
        return new Function1<T, Unit>() { // from class: com.thor.basemodule.extensions.CoroutineKt.throttleLatest.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(1);
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Object obj) {
                invoke2((AnonymousClass1<T>) obj);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(T t) {
                objectRef2.element = t;
                Job job = objectRef.element;
                boolean z = false;
                if (job != null && !job.isCompleted()) {
                    z = true;
                }
                if (z) {
                    return;
                }
                objectRef.element = (T) BuildersKt__Builders_commonKt.launch$default(coroutineScope, Dispatchers.getDefault(), null, new C00841(j, objectRef2, destinationFunction, null), 2, null);
            }

            /* compiled from: Coroutine.kt */
            @Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\u00020\u0003H\u008a@"}, d2 = {"<anonymous>", "", ExifInterface.GPS_DIRECTION_TRUE, "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 8, 0}, xi = 48)
            @DebugMetadata(c = "com.thor.basemodule.extensions.CoroutineKt$throttleLatest$1$1", f = "Coroutine.kt", i = {}, l = {16}, m = "invokeSuspend", n = {}, s = {})
            /* renamed from: com.thor.basemodule.extensions.CoroutineKt$throttleLatest$1$1, reason: invalid class name and collision with other inner class name */
            static final class C00841 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
                final /* synthetic */ Function1<T, Unit> $destinationFunction;
                final /* synthetic */ long $intervalMs;
                final /* synthetic */ Ref.ObjectRef<T> $latestParam;
                int label;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                /* JADX WARN: Multi-variable type inference failed */
                C00841(long j, Ref.ObjectRef<T> objectRef, Function1<? super T, Unit> function1, Continuation<? super C00841> continuation) {
                    super(2, continuation);
                    this.$intervalMs = j;
                    this.$latestParam = objectRef;
                    this.$destinationFunction = function1;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                    return new C00841(this.$intervalMs, this.$latestParam, this.$destinationFunction, continuation);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                    return ((C00841) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                    int i = this.label;
                    if (i == 0) {
                        ResultKt.throwOnFailure(obj);
                        this.label = 1;
                        if (DelayKt.delay(this.$intervalMs, this) == coroutine_suspended) {
                            return coroutine_suspended;
                        }
                    } else {
                        if (i != 1) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj);
                    }
                    this.$destinationFunction.invoke(this.$latestParam.element);
                    return Unit.INSTANCE;
                }
            }
        };
    }
}
