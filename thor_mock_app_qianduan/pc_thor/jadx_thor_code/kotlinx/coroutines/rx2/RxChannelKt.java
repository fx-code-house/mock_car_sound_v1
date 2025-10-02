package kotlinx.coroutines.rx2;

import androidx.exifinterface.media.ExifInterface;
import io.reactivex.MaybeSource;
import io.reactivex.ObservableSource;
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

/* compiled from: RxChannel.kt */
@Metadata(d1 = {"\u0000$\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a5\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00032\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u00020\u00010\u0005H\u0086Hø\u0001\u0000¢\u0006\u0002\u0010\u0006\u001a5\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00072\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u00020\u00010\u0005H\u0086Hø\u0001\u0000¢\u0006\u0002\u0010\b\u001a#\u0010\t\u001a\b\u0012\u0004\u0012\u0002H\u00020\n\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\u0012\u0004\b\u0002H\u00020\u0003H\u0007ø\u0001\u0001\u001a#\u0010\t\u001a\b\u0012\u0004\u0012\u0002H\u00020\n\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\u0012\u0004\b\u0002H\u00020\u0007H\u0007ø\u0001\u0001\u001a\u001e\u0010\u000b\u001a\b\u0012\u0004\u0012\u0002H\u00020\n\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0003H\u0001\u001a\u001e\u0010\u000b\u001a\b\u0012\u0004\u0012\u0002H\u00020\n\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0007H\u0001\u0082\u0002\b\n\u0002\b\u0019\n\u0002\b9¨\u0006\f"}, d2 = {"collect", "", ExifInterface.GPS_DIRECTION_TRUE, "Lio/reactivex/MaybeSource;", "action", "Lkotlin/Function1;", "(Lio/reactivex/MaybeSource;Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "Lio/reactivex/ObservableSource;", "(Lio/reactivex/ObservableSource;Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "openSubscription", "Lkotlinx/coroutines/channels/ReceiveChannel;", "toChannel", "kotlinx-coroutines-rx2"}, k = 2, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class RxChannelKt {

    /* compiled from: RxChannel.kt */
    @Metadata(k = 3, mv = {1, 8, 0}, xi = 176)
    @DebugMetadata(c = "kotlinx.coroutines.rx2.RxChannelKt", f = "RxChannel.kt", i = {0, 0}, l = {103}, m = "collect", n = {"action", "$this$consume$iv$iv"}, s = {"L$0", "L$1"})
    /* renamed from: kotlinx.coroutines.rx2.RxChannelKt$collect$1, reason: invalid class name */
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
            return RxChannelKt.collect((MaybeSource) null, (Function1) null, this);
        }
    }

    /* compiled from: RxChannel.kt */
    @Metadata(k = 3, mv = {1, 8, 0}, xi = 176)
    @DebugMetadata(c = "kotlinx.coroutines.rx2.RxChannelKt", f = "RxChannel.kt", i = {0, 0}, l = {103}, m = "collect", n = {"action", "$this$consume$iv$iv"}, s = {"L$0", "L$1"})
    /* renamed from: kotlinx.coroutines.rx2.RxChannelKt$collect$2, reason: invalid class name */
    static final class AnonymousClass2<T> extends ContinuationImpl {
        Object L$0;
        Object L$1;
        Object L$2;
        int label;
        /* synthetic */ Object result;

        AnonymousClass2(Continuation<? super AnonymousClass2> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return RxChannelKt.collect((ObservableSource) null, (Function1) null, this);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x0061 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0062  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x006e A[Catch: all -> 0x0088, TryCatch #1 {all -> 0x0088, blocks: (B:26:0x0066, B:28:0x006e, B:29:0x0079), top: B:44:0x0066 }] */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0079 A[Catch: all -> 0x0088, TRY_LEAVE, TryCatch #1 {all -> 0x0088, blocks: (B:26:0x0066, B:28:0x006e, B:29:0x0079), top: B:44:0x0066 }] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0014  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:25:0x0062 -> B:14:0x0038). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final <T> java.lang.Object collect(io.reactivex.MaybeSource<T> r5, kotlin.jvm.functions.Function1<? super T, kotlin.Unit> r6, kotlin.coroutines.Continuation<? super kotlin.Unit> r7) {
        /*
            boolean r0 = r7 instanceof kotlinx.coroutines.rx2.RxChannelKt.AnonymousClass1
            if (r0 == 0) goto L14
            r0 = r7
            kotlinx.coroutines.rx2.RxChannelKt$collect$1 r0 = (kotlinx.coroutines.rx2.RxChannelKt.AnonymousClass1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L14
            int r7 = r0.label
            int r7 = r7 - r2
            r0.label = r7
            goto L19
        L14:
            kotlinx.coroutines.rx2.RxChannelKt$collect$1 r0 = new kotlinx.coroutines.rx2.RxChannelKt$collect$1
            r0.<init>(r7)
        L19:
            java.lang.Object r7 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L45
            if (r2 != r3) goto L3d
            java.lang.Object r5 = r0.L$2
            kotlinx.coroutines.channels.ChannelIterator r5 = (kotlinx.coroutines.channels.ChannelIterator) r5
            java.lang.Object r6 = r0.L$1
            kotlinx.coroutines.channels.ReceiveChannel r6 = (kotlinx.coroutines.channels.ReceiveChannel) r6
            java.lang.Object r2 = r0.L$0
            kotlin.jvm.functions.Function1 r2 = (kotlin.jvm.functions.Function1) r2
            kotlin.ResultKt.throwOnFailure(r7)     // Catch: java.lang.Throwable -> L3b
            r4 = r0
            r0 = r6
            r6 = r2
        L38:
            r2 = r1
            r1 = r4
            goto L66
        L3b:
            r5 = move-exception
            goto L92
        L3d:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r6)
            throw r5
        L45:
            kotlin.ResultKt.throwOnFailure(r7)
            kotlinx.coroutines.channels.ReceiveChannel r5 = toChannel(r5)
            kotlinx.coroutines.channels.ChannelIterator r7 = r5.iterator()     // Catch: java.lang.Throwable -> L8e
            r4 = r7
            r7 = r5
            r5 = r4
        L53:
            r0.L$0 = r6     // Catch: java.lang.Throwable -> L8b
            r0.L$1 = r7     // Catch: java.lang.Throwable -> L8b
            r0.L$2 = r5     // Catch: java.lang.Throwable -> L8b
            r0.label = r3     // Catch: java.lang.Throwable -> L8b
            java.lang.Object r2 = r5.hasNext(r0)     // Catch: java.lang.Throwable -> L8b
            if (r2 != r1) goto L62
            return r1
        L62:
            r4 = r0
            r0 = r7
            r7 = r2
            goto L38
        L66:
            java.lang.Boolean r7 = (java.lang.Boolean) r7     // Catch: java.lang.Throwable -> L88
            boolean r7 = r7.booleanValue()     // Catch: java.lang.Throwable -> L88
            if (r7 == 0) goto L79
            java.lang.Object r7 = r5.next()     // Catch: java.lang.Throwable -> L88
            r6.invoke(r7)     // Catch: java.lang.Throwable -> L88
            r7 = r0
            r0 = r1
            r1 = r2
            goto L53
        L79:
            kotlin.Unit r5 = kotlin.Unit.INSTANCE     // Catch: java.lang.Throwable -> L88
            kotlin.jvm.internal.InlineMarker.finallyStart(r3)
            r5 = 0
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r0, r5)
            kotlin.jvm.internal.InlineMarker.finallyEnd(r3)
            kotlin.Unit r5 = kotlin.Unit.INSTANCE
            return r5
        L88:
            r5 = move-exception
            r6 = r0
            goto L92
        L8b:
            r5 = move-exception
            r6 = r7
            goto L92
        L8e:
            r6 = move-exception
            r4 = r6
            r6 = r5
            r5 = r4
        L92:
            throw r5     // Catch: java.lang.Throwable -> L93
        L93:
            r7 = move-exception
            kotlin.jvm.internal.InlineMarker.finallyStart(r3)
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r6, r5)
            kotlin.jvm.internal.InlineMarker.finallyEnd(r3)
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.rx2.RxChannelKt.collect(io.reactivex.MaybeSource, kotlin.jvm.functions.Function1, kotlin.coroutines.Continuation):java.lang.Object");
    }

    private static final <T> Object collect$$forInline(MaybeSource<T> maybeSource, Function1<? super T, Unit> function1, Continuation<? super Unit> continuation) {
        ReceiveChannel channel = toChannel(maybeSource);
        try {
            ReceiveChannel receiveChannel = channel;
            ChannelIterator it = channel.iterator();
            while (true) {
                InlineMarker.mark(3);
                InlineMarker.mark(0);
                Object objHasNext = it.hasNext(null);
                InlineMarker.mark(1);
                if (!((Boolean) objHasNext).booleanValue()) {
                    Unit unit = Unit.INSTANCE;
                    InlineMarker.finallyStart(1);
                    ChannelsKt.cancelConsumed(channel, null);
                    InlineMarker.finallyEnd(1);
                    Unit unit2 = Unit.INSTANCE;
                    return Unit.INSTANCE;
                }
                function1.invoke((Object) it.next());
            }
        } finally {
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x0061 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0062  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x006e A[Catch: all -> 0x0088, TryCatch #1 {all -> 0x0088, blocks: (B:26:0x0066, B:28:0x006e, B:29:0x0079), top: B:44:0x0066 }] */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0079 A[Catch: all -> 0x0088, TRY_LEAVE, TryCatch #1 {all -> 0x0088, blocks: (B:26:0x0066, B:28:0x006e, B:29:0x0079), top: B:44:0x0066 }] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0014  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:25:0x0062 -> B:14:0x0038). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final <T> java.lang.Object collect(io.reactivex.ObservableSource<T> r5, kotlin.jvm.functions.Function1<? super T, kotlin.Unit> r6, kotlin.coroutines.Continuation<? super kotlin.Unit> r7) {
        /*
            boolean r0 = r7 instanceof kotlinx.coroutines.rx2.RxChannelKt.AnonymousClass2
            if (r0 == 0) goto L14
            r0 = r7
            kotlinx.coroutines.rx2.RxChannelKt$collect$2 r0 = (kotlinx.coroutines.rx2.RxChannelKt.AnonymousClass2) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L14
            int r7 = r0.label
            int r7 = r7 - r2
            r0.label = r7
            goto L19
        L14:
            kotlinx.coroutines.rx2.RxChannelKt$collect$2 r0 = new kotlinx.coroutines.rx2.RxChannelKt$collect$2
            r0.<init>(r7)
        L19:
            java.lang.Object r7 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L45
            if (r2 != r3) goto L3d
            java.lang.Object r5 = r0.L$2
            kotlinx.coroutines.channels.ChannelIterator r5 = (kotlinx.coroutines.channels.ChannelIterator) r5
            java.lang.Object r6 = r0.L$1
            kotlinx.coroutines.channels.ReceiveChannel r6 = (kotlinx.coroutines.channels.ReceiveChannel) r6
            java.lang.Object r2 = r0.L$0
            kotlin.jvm.functions.Function1 r2 = (kotlin.jvm.functions.Function1) r2
            kotlin.ResultKt.throwOnFailure(r7)     // Catch: java.lang.Throwable -> L3b
            r4 = r0
            r0 = r6
            r6 = r2
        L38:
            r2 = r1
            r1 = r4
            goto L66
        L3b:
            r5 = move-exception
            goto L92
        L3d:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r6)
            throw r5
        L45:
            kotlin.ResultKt.throwOnFailure(r7)
            kotlinx.coroutines.channels.ReceiveChannel r5 = toChannel(r5)
            kotlinx.coroutines.channels.ChannelIterator r7 = r5.iterator()     // Catch: java.lang.Throwable -> L8e
            r4 = r7
            r7 = r5
            r5 = r4
        L53:
            r0.L$0 = r6     // Catch: java.lang.Throwable -> L8b
            r0.L$1 = r7     // Catch: java.lang.Throwable -> L8b
            r0.L$2 = r5     // Catch: java.lang.Throwable -> L8b
            r0.label = r3     // Catch: java.lang.Throwable -> L8b
            java.lang.Object r2 = r5.hasNext(r0)     // Catch: java.lang.Throwable -> L8b
            if (r2 != r1) goto L62
            return r1
        L62:
            r4 = r0
            r0 = r7
            r7 = r2
            goto L38
        L66:
            java.lang.Boolean r7 = (java.lang.Boolean) r7     // Catch: java.lang.Throwable -> L88
            boolean r7 = r7.booleanValue()     // Catch: java.lang.Throwable -> L88
            if (r7 == 0) goto L79
            java.lang.Object r7 = r5.next()     // Catch: java.lang.Throwable -> L88
            r6.invoke(r7)     // Catch: java.lang.Throwable -> L88
            r7 = r0
            r0 = r1
            r1 = r2
            goto L53
        L79:
            kotlin.Unit r5 = kotlin.Unit.INSTANCE     // Catch: java.lang.Throwable -> L88
            kotlin.jvm.internal.InlineMarker.finallyStart(r3)
            r5 = 0
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r0, r5)
            kotlin.jvm.internal.InlineMarker.finallyEnd(r3)
            kotlin.Unit r5 = kotlin.Unit.INSTANCE
            return r5
        L88:
            r5 = move-exception
            r6 = r0
            goto L92
        L8b:
            r5 = move-exception
            r6 = r7
            goto L92
        L8e:
            r6 = move-exception
            r4 = r6
            r6 = r5
            r5 = r4
        L92:
            throw r5     // Catch: java.lang.Throwable -> L93
        L93:
            r7 = move-exception
            kotlin.jvm.internal.InlineMarker.finallyStart(r3)
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r6, r5)
            kotlin.jvm.internal.InlineMarker.finallyEnd(r3)
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.rx2.RxChannelKt.collect(io.reactivex.ObservableSource, kotlin.jvm.functions.Function1, kotlin.coroutines.Continuation):java.lang.Object");
    }

    private static final <T> Object collect$$forInline(ObservableSource<T> observableSource, Function1<? super T, Unit> function1, Continuation<? super Unit> continuation) {
        ReceiveChannel channel = toChannel(observableSource);
        try {
            ReceiveChannel receiveChannel = channel;
            ChannelIterator it = channel.iterator();
            while (true) {
                InlineMarker.mark(3);
                InlineMarker.mark(0);
                Object objHasNext = it.hasNext(null);
                InlineMarker.mark(1);
                if (!((Boolean) objHasNext).booleanValue()) {
                    Unit unit = Unit.INSTANCE;
                    InlineMarker.finallyStart(1);
                    ChannelsKt.cancelConsumed(channel, null);
                    InlineMarker.finallyEnd(1);
                    Unit unit2 = Unit.INSTANCE;
                    return Unit.INSTANCE;
                }
                function1.invoke((Object) it.next());
            }
        } finally {
        }
    }

    public static final <T> ReceiveChannel<T> toChannel(MaybeSource<T> maybeSource) {
        SubscriptionChannel subscriptionChannel = new SubscriptionChannel();
        maybeSource.subscribe(subscriptionChannel);
        return subscriptionChannel;
    }

    public static final <T> ReceiveChannel<T> toChannel(ObservableSource<T> observableSource) {
        SubscriptionChannel subscriptionChannel = new SubscriptionChannel();
        observableSource.subscribe(subscriptionChannel);
        return subscriptionChannel;
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "Deprecated in the favour of Flow")
    public static final /* synthetic */ ReceiveChannel openSubscription(ObservableSource observableSource) {
        SubscriptionChannel subscriptionChannel = new SubscriptionChannel();
        observableSource.subscribe(subscriptionChannel);
        return subscriptionChannel;
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "Deprecated in the favour of Flow")
    public static final /* synthetic */ ReceiveChannel openSubscription(MaybeSource maybeSource) {
        SubscriptionChannel subscriptionChannel = new SubscriptionChannel();
        maybeSource.subscribe(subscriptionChannel);
        return subscriptionChannel;
    }
}
