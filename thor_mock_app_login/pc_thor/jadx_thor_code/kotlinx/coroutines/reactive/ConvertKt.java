package kotlinx.coroutines.reactive;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.channels.ProducerScope;
import kotlinx.coroutines.channels.ReceiveChannel;
import org.reactivestreams.Publisher;

/* compiled from: Convert.kt */
@Metadata(d1 = {"\u0000\u0014\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a(\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005H\u0007¨\u0006\u0006"}, d2 = {"asPublisher", "Lorg/reactivestreams/Publisher;", ExifInterface.GPS_DIRECTION_TRUE, "Lkotlinx/coroutines/channels/ReceiveChannel;", "context", "Lkotlin/coroutines/CoroutineContext;", "kotlinx-coroutines-reactive"}, k = 2, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class ConvertKt {

    /* JADX INFO: Add missing generic type declarations: [T] */
    /* compiled from: Convert.kt */
    @Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0003H\u008a@"}, d2 = {"<anonymous>", "", ExifInterface.GPS_DIRECTION_TRUE, "Lkotlinx/coroutines/channels/ProducerScope;"}, k = 3, mv = {1, 8, 0}, xi = 48)
    @DebugMetadata(c = "kotlinx.coroutines.reactive.ConvertKt$asPublisher$1", f = "Convert.kt", i = {0, 1}, l = {16, 17}, m = "invokeSuspend", n = {"$this$publish", "$this$publish"}, s = {"L$0", "L$0"})
    /* renamed from: kotlinx.coroutines.reactive.ConvertKt$asPublisher$1, reason: invalid class name */
    static final class AnonymousClass1<T> extends SuspendLambda implements Function2<ProducerScope<? super T>, Continuation<? super Unit>, Object> {
        final /* synthetic */ ReceiveChannel<T> $this_asPublisher;
        private /* synthetic */ Object L$0;
        Object L$1;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        AnonymousClass1(ReceiveChannel<? extends T> receiveChannel, Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
            this.$this_asPublisher = receiveChannel;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$this_asPublisher, continuation);
            anonymousClass1.L$0 = obj;
            return anonymousClass1;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(ProducerScope<? super T> producerScope, Continuation<? super Unit> continuation) {
            return ((AnonymousClass1) create(producerScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Removed duplicated region for block: B:14:0x004d A[RETURN] */
        /* JADX WARN: Removed duplicated region for block: B:15:0x004e  */
        /* JADX WARN: Removed duplicated region for block: B:18:0x005a  */
        /* JADX WARN: Removed duplicated region for block: B:22:0x0071  */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:21:0x006e -> B:12:0x003e). Please report as a decompilation issue!!! */
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
                r2 = 2
                r3 = 1
                if (r1 == 0) goto L30
                if (r1 == r3) goto L23
                if (r1 != r2) goto L1b
                java.lang.Object r1 = r8.L$1
                kotlinx.coroutines.channels.ChannelIterator r1 = (kotlinx.coroutines.channels.ChannelIterator) r1
                java.lang.Object r4 = r8.L$0
                kotlinx.coroutines.channels.ProducerScope r4 = (kotlinx.coroutines.channels.ProducerScope) r4
                kotlin.ResultKt.throwOnFailure(r9)
                r9 = r4
                goto L3d
            L1b:
                java.lang.IllegalStateException r9 = new java.lang.IllegalStateException
                java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
                r9.<init>(r0)
                throw r9
            L23:
                java.lang.Object r1 = r8.L$1
                kotlinx.coroutines.channels.ChannelIterator r1 = (kotlinx.coroutines.channels.ChannelIterator) r1
                java.lang.Object r4 = r8.L$0
                kotlinx.coroutines.channels.ProducerScope r4 = (kotlinx.coroutines.channels.ProducerScope) r4
                kotlin.ResultKt.throwOnFailure(r9)
                r5 = r8
                goto L52
            L30:
                kotlin.ResultKt.throwOnFailure(r9)
                java.lang.Object r9 = r8.L$0
                kotlinx.coroutines.channels.ProducerScope r9 = (kotlinx.coroutines.channels.ProducerScope) r9
                kotlinx.coroutines.channels.ReceiveChannel<T> r1 = r8.$this_asPublisher
                kotlinx.coroutines.channels.ChannelIterator r1 = r1.iterator()
            L3d:
                r4 = r8
            L3e:
                r5 = r4
                kotlin.coroutines.Continuation r5 = (kotlin.coroutines.Continuation) r5
                r4.L$0 = r9
                r4.L$1 = r1
                r4.label = r3
                java.lang.Object r5 = r1.hasNext(r5)
                if (r5 != r0) goto L4e
                return r0
            L4e:
                r7 = r4
                r4 = r9
                r9 = r5
                r5 = r7
            L52:
                java.lang.Boolean r9 = (java.lang.Boolean) r9
                boolean r9 = r9.booleanValue()
                if (r9 == 0) goto L71
                java.lang.Object r9 = r1.next()
                r6 = r5
                kotlin.coroutines.Continuation r6 = (kotlin.coroutines.Continuation) r6
                r5.L$0 = r4
                r5.L$1 = r1
                r5.label = r2
                java.lang.Object r9 = r4.send(r9, r6)
                if (r9 != r0) goto L6e
                return r0
            L6e:
                r9 = r4
                r4 = r5
                goto L3e
            L71:
                kotlin.Unit r9 = kotlin.Unit.INSTANCE
                return r9
            */
            throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.reactive.ConvertKt.AnonymousClass1.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "Deprecated in the favour of consumeAsFlow()", replaceWith = @ReplaceWith(expression = "this.consumeAsFlow().asPublisher(context)", imports = {"kotlinx.coroutines.flow.consumeAsFlow"}))
    public static final /* synthetic */ Publisher asPublisher(ReceiveChannel receiveChannel, CoroutineContext coroutineContext) {
        return PublishKt.publish(coroutineContext, new AnonymousClass1(receiveChannel, null));
    }

    public static /* synthetic */ Publisher asPublisher$default(ReceiveChannel receiveChannel, CoroutineContext coroutineContext, int i, Object obj) {
        if ((i & 1) != 0) {
            coroutineContext = EmptyCoroutineContext.INSTANCE;
        }
        return asPublisher(receiveChannel, coroutineContext);
    }
}
