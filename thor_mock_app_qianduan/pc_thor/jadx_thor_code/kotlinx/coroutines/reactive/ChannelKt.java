package kotlinx.coroutines.reactive;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.InlineMarker;
import kotlinx.coroutines.channels.ChannelIterator;
import kotlinx.coroutines.channels.ChannelsKt;
import kotlinx.coroutines.channels.ReceiveChannel;
import org.reactivestreams.Publisher;

/* compiled from: Channel.kt */
@Metadata(d1 = {"\u0000$\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\u001a5\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00032\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u00020\u00010\u0005H\u0086Hø\u0001\u0000¢\u0006\u0002\u0010\u0006\u001a(\u0010\u0007\u001a\b\u0012\u0004\u0012\u0002H\u00020\b\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00032\b\b\u0002\u0010\t\u001a\u00020\nH\u0007\u001a(\u0010\u000b\u001a\b\u0012\u0004\u0012\u0002H\u00020\b\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00032\b\b\u0002\u0010\t\u001a\u00020\nH\u0001\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\f"}, d2 = {"collect", "", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/reactivestreams/Publisher;", "action", "Lkotlin/Function1;", "(Lorg/reactivestreams/Publisher;Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "openSubscription", "Lkotlinx/coroutines/channels/ReceiveChannel;", "request", "", "toChannel", "kotlinx-coroutines-reactive"}, k = 2, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class ChannelKt {

    /* compiled from: Channel.kt */
    @Metadata(k = 3, mv = {1, 8, 0}, xi = 176)
    @DebugMetadata(c = "kotlinx.coroutines.reactive.ChannelKt", f = "Channel.kt", i = {0, 0}, l = {119}, m = "collect", n = {"action", "$this$consume$iv$iv"}, s = {"L$0", "L$1"})
    /* renamed from: kotlinx.coroutines.reactive.ChannelKt$collect$1, reason: invalid class name */
    static final class AnonymousClass1<T> extends ContinuationImpl {
        Object L$0;
        Object L$1;
        Object L$2;
        int label;
        /* synthetic */ Object result;

        AnonymousClass1(Continuation<? super AnonymousClass1> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return ChannelKt.collect(null, null, this);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x0063 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0064  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0070 A[Catch: all -> 0x0089, TryCatch #1 {all -> 0x0089, blocks: (B:26:0x0068, B:28:0x0070, B:29:0x007b), top: B:44:0x0068 }] */
    /* JADX WARN: Removed duplicated region for block: B:29:0x007b A[Catch: all -> 0x0089, TRY_LEAVE, TryCatch #1 {all -> 0x0089, blocks: (B:26:0x0068, B:28:0x0070, B:29:0x007b), top: B:44:0x0068 }] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0014  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:25:0x0064 -> B:14:0x0039). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final <T> java.lang.Object collect(org.reactivestreams.Publisher<T> r6, kotlin.jvm.functions.Function1<? super T, kotlin.Unit> r7, kotlin.coroutines.Continuation<? super kotlin.Unit> r8) {
        /*
            boolean r0 = r8 instanceof kotlinx.coroutines.reactive.ChannelKt.AnonymousClass1
            if (r0 == 0) goto L14
            r0 = r8
            kotlinx.coroutines.reactive.ChannelKt$collect$1 r0 = (kotlinx.coroutines.reactive.ChannelKt.AnonymousClass1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L14
            int r8 = r0.label
            int r8 = r8 - r2
            r0.label = r8
            goto L19
        L14:
            kotlinx.coroutines.reactive.ChannelKt$collect$1 r0 = new kotlinx.coroutines.reactive.ChannelKt$collect$1
            r0.<init>(r8)
        L19:
            java.lang.Object r8 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 0
            r4 = 1
            if (r2 == 0) goto L46
            if (r2 != r4) goto L3e
            java.lang.Object r6 = r0.L$2
            kotlinx.coroutines.channels.ChannelIterator r6 = (kotlinx.coroutines.channels.ChannelIterator) r6
            java.lang.Object r7 = r0.L$1
            kotlinx.coroutines.channels.ReceiveChannel r7 = (kotlinx.coroutines.channels.ReceiveChannel) r7
            java.lang.Object r2 = r0.L$0
            kotlin.jvm.functions.Function1 r2 = (kotlin.jvm.functions.Function1) r2
            kotlin.ResultKt.throwOnFailure(r8)     // Catch: java.lang.Throwable -> L3c
            r5 = r0
            r0 = r7
            r7 = r2
        L39:
            r2 = r1
            r1 = r5
            goto L68
        L3c:
            r6 = move-exception
            goto L93
        L3e:
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
            r6.<init>(r7)
            throw r6
        L46:
            kotlin.ResultKt.throwOnFailure(r8)
            r8 = 0
            kotlinx.coroutines.channels.ReceiveChannel r6 = toChannel$default(r6, r8, r4, r3)
            kotlinx.coroutines.channels.ChannelIterator r8 = r6.iterator()     // Catch: java.lang.Throwable -> L8f
            r5 = r8
            r8 = r6
            r6 = r5
        L55:
            r0.L$0 = r7     // Catch: java.lang.Throwable -> L8c
            r0.L$1 = r8     // Catch: java.lang.Throwable -> L8c
            r0.L$2 = r6     // Catch: java.lang.Throwable -> L8c
            r0.label = r4     // Catch: java.lang.Throwable -> L8c
            java.lang.Object r2 = r6.hasNext(r0)     // Catch: java.lang.Throwable -> L8c
            if (r2 != r1) goto L64
            return r1
        L64:
            r5 = r0
            r0 = r8
            r8 = r2
            goto L39
        L68:
            java.lang.Boolean r8 = (java.lang.Boolean) r8     // Catch: java.lang.Throwable -> L89
            boolean r8 = r8.booleanValue()     // Catch: java.lang.Throwable -> L89
            if (r8 == 0) goto L7b
            java.lang.Object r8 = r6.next()     // Catch: java.lang.Throwable -> L89
            r7.invoke(r8)     // Catch: java.lang.Throwable -> L89
            r8 = r0
            r0 = r1
            r1 = r2
            goto L55
        L7b:
            kotlin.Unit r6 = kotlin.Unit.INSTANCE     // Catch: java.lang.Throwable -> L89
            kotlin.jvm.internal.InlineMarker.finallyStart(r4)
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r0, r3)
            kotlin.jvm.internal.InlineMarker.finallyEnd(r4)
            kotlin.Unit r6 = kotlin.Unit.INSTANCE
            return r6
        L89:
            r6 = move-exception
            r7 = r0
            goto L93
        L8c:
            r6 = move-exception
            r7 = r8
            goto L93
        L8f:
            r7 = move-exception
            r5 = r7
            r7 = r6
            r6 = r5
        L93:
            throw r6     // Catch: java.lang.Throwable -> L94
        L94:
            r8 = move-exception
            kotlin.jvm.internal.InlineMarker.finallyStart(r4)
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r7, r6)
            kotlin.jvm.internal.InlineMarker.finallyEnd(r4)
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.reactive.ChannelKt.collect(org.reactivestreams.Publisher, kotlin.jvm.functions.Function1, kotlin.coroutines.Continuation):java.lang.Object");
    }

    private static final <T> Object collect$$forInline(Publisher<T> publisher, Function1<? super T, Unit> function1, Continuation<? super Unit> continuation) {
        ReceiveChannel channel$default = toChannel$default(publisher, 0, 1, null);
        try {
            ReceiveChannel receiveChannel = channel$default;
            ChannelIterator it = channel$default.iterator();
            while (true) {
                InlineMarker.mark(3);
                InlineMarker.mark(0);
                Object objHasNext = it.hasNext(null);
                InlineMarker.mark(1);
                if (!((Boolean) objHasNext).booleanValue()) {
                    Unit unit = Unit.INSTANCE;
                    InlineMarker.finallyStart(1);
                    ChannelsKt.cancelConsumed(channel$default, null);
                    InlineMarker.finallyEnd(1);
                    Unit unit2 = Unit.INSTANCE;
                    return Unit.INSTANCE;
                }
                function1.invoke((Object) it.next());
            }
        } finally {
        }
    }

    public static /* synthetic */ ReceiveChannel toChannel$default(Publisher publisher, int i, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            i = 1;
        }
        return toChannel(publisher, i);
    }

    public static final <T> ReceiveChannel<T> toChannel(Publisher<T> publisher, int i) {
        SubscriptionChannel subscriptionChannel = new SubscriptionChannel(i);
        publisher.subscribe(subscriptionChannel);
        return subscriptionChannel;
    }

    public static /* synthetic */ ReceiveChannel openSubscription$default(Publisher publisher, int i, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            i = 1;
        }
        return openSubscription(publisher, i);
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "Transforming publisher to channel is deprecated, use asFlow() instead")
    public static final /* synthetic */ ReceiveChannel openSubscription(Publisher publisher, int i) {
        SubscriptionChannel subscriptionChannel = new SubscriptionChannel(i);
        publisher.subscribe(subscriptionChannel);
        return subscriptionChannel;
    }
}
