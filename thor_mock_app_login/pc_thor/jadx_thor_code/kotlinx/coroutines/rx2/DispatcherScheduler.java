package kotlinx.coroutines.rx2;

import com.google.android.exoplayer2.extractor.ts.PsExtractor;
import com.google.android.gms.measurement.api.AppMeasurementSdk;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.Volatile;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CompletableJob;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.SupervisorKt;
import kotlinx.coroutines.channels.Channel;
import kotlinx.coroutines.channels.ChannelKt;
import kotlinx.coroutines.channels.SendChannel;
import kotlinx.coroutines.rx2.DispatcherScheduler;

/* compiled from: RxScheduler.kt */
@Metadata(d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0002\u0018\u00002\u00020\u0001:\u0001\u001aB\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u000b\u001a\u00020\fH\u0016J$\u0010\r\u001a\u00020\u000e2\n\u0010\u000f\u001a\u00060\u0010j\u0002`\u00112\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0016J\b\u0010\u0016\u001a\u00020\u0017H\u0016J\b\u0010\u0018\u001a\u00020\u0019H\u0016R\u0010\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\t\u0010\t\u001a\u00020\nX\u0082\u0004¨\u0006\u001b"}, d2 = {"Lkotlinx/coroutines/rx2/DispatcherScheduler;", "Lio/reactivex/Scheduler;", "dispatcher", "Lkotlinx/coroutines/CoroutineDispatcher;", "(Lkotlinx/coroutines/CoroutineDispatcher;)V", "schedulerJob", "Lkotlinx/coroutines/CompletableJob;", "scope", "Lkotlinx/coroutines/CoroutineScope;", "workerCounter", "Lkotlinx/atomicfu/AtomicLong;", "createWorker", "Lio/reactivex/Scheduler$Worker;", "scheduleDirect", "Lio/reactivex/disposables/Disposable;", "block", "Ljava/lang/Runnable;", "Lkotlinx/coroutines/Runnable;", "delay", "", "unit", "Ljava/util/concurrent/TimeUnit;", "shutdown", "", "toString", "", "DispatcherWorker", "kotlinx-coroutines-rx2"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
final class DispatcherScheduler extends Scheduler {
    private static final AtomicLongFieldUpdater workerCounter$FU = AtomicLongFieldUpdater.newUpdater(DispatcherScheduler.class, "workerCounter");
    public final CoroutineDispatcher dispatcher;
    private final CompletableJob schedulerJob;
    private final CoroutineScope scope;

    @Volatile
    private volatile long workerCounter;

    public DispatcherScheduler(CoroutineDispatcher coroutineDispatcher) {
        this.dispatcher = coroutineDispatcher;
        CompletableJob completableJobSupervisorJob$default = SupervisorKt.SupervisorJob$default((Job) null, 1, (Object) null);
        this.schedulerJob = completableJobSupervisorJob$default;
        this.scope = CoroutineScopeKt.CoroutineScope(completableJobSupervisorJob$default.plus(coroutineDispatcher));
        this.workerCounter = 1L;
    }

    @Override // io.reactivex.Scheduler
    public Disposable scheduleDirect(Runnable block, long delay, TimeUnit unit) {
        return RxSchedulerKt.scheduleTask(this.scope, block, unit.toMillis(delay), new Function1<Function1<? super Continuation<? super Unit>, ? extends Object>, Runnable>() { // from class: kotlinx.coroutines.rx2.DispatcherScheduler.scheduleDirect.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Runnable invoke(final Function1<? super Continuation<? super Unit>, ? extends Object> function1) {
                final DispatcherScheduler dispatcherScheduler = DispatcherScheduler.this;
                return new Runnable() { // from class: kotlinx.coroutines.rx2.DispatcherScheduler$scheduleDirect$1$invoke$$inlined$Runnable$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        BuildersKt__Builders_commonKt.launch$default(dispatcherScheduler.scope, null, null, new DispatcherScheduler$scheduleDirect$1$1$1(function1, null), 3, null);
                    }
                };
            }
        });
    }

    @Override // io.reactivex.Scheduler
    public Scheduler.Worker createWorker() {
        return new DispatcherWorker(workerCounter$FU.getAndIncrement(this), this.dispatcher, this.schedulerJob);
    }

    @Override // io.reactivex.Scheduler
    public void shutdown() {
        Job.DefaultImpls.cancel$default((Job) this.schedulerJob, (CancellationException) null, 1, (Object) null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: RxScheduler.kt */
    @Metadata(d1 = {"\u0000f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\b\u0010\u0013\u001a\u00020\rH\u0016J\b\u0010\u0014\u001a\u00020\u0015H\u0016J$\u0010\u0016\u001a\u00020\u00172\n\u0010\u0018\u001a\u00060\u0019j\u0002`\u001a2\u0006\u0010\u001b\u001a\u00020\u00032\u0006\u0010\u001c\u001a\u00020\u001dH\u0016J\b\u0010\u001e\u001a\u00020\u001fH\u0016R-\u0010\t\u001a\u001e\u0012\u001a\u0012\u0018\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\f\u0012\u0006\u0012\u0004\u0018\u00010\u000e0\u000b0\nX\u0082\u0004ø\u0001\u0000¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u0004¢\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006 "}, d2 = {"Lkotlinx/coroutines/rx2/DispatcherScheduler$DispatcherWorker;", "Lio/reactivex/Scheduler$Worker;", "counter", "", "dispatcher", "Lkotlinx/coroutines/CoroutineDispatcher;", "parentJob", "Lkotlinx/coroutines/Job;", "(JLkotlinx/coroutines/CoroutineDispatcher;Lkotlinx/coroutines/Job;)V", "blockChannel", "Lkotlinx/coroutines/channels/Channel;", "Lkotlin/Function1;", "Lkotlin/coroutines/Continuation;", "", "", "workerJob", "Lkotlinx/coroutines/CompletableJob;", "workerScope", "Lkotlinx/coroutines/CoroutineScope;", "dispose", "isDisposed", "", "schedule", "Lio/reactivex/disposables/Disposable;", "block", "Ljava/lang/Runnable;", "Lkotlinx/coroutines/Runnable;", "delay", "unit", "Ljava/util/concurrent/TimeUnit;", "toString", "", "kotlinx-coroutines-rx2"}, k = 1, mv = {1, 8, 0}, xi = 48)
    static final class DispatcherWorker extends Scheduler.Worker {
        private final Channel<Function1<Continuation<? super Unit>, Object>> blockChannel;
        private final long counter;
        private final CoroutineDispatcher dispatcher;
        private final CompletableJob workerJob;
        private final CoroutineScope workerScope;

        public DispatcherWorker(long j, CoroutineDispatcher coroutineDispatcher, Job job) {
            this.counter = j;
            this.dispatcher = coroutineDispatcher;
            CompletableJob completableJobSupervisorJob = SupervisorKt.SupervisorJob(job);
            this.workerJob = completableJobSupervisorJob;
            CoroutineScope CoroutineScope = CoroutineScopeKt.CoroutineScope(completableJobSupervisorJob.plus(coroutineDispatcher));
            this.workerScope = CoroutineScope;
            this.blockChannel = ChannelKt.Channel$default(Integer.MAX_VALUE, null, null, 6, null);
            BuildersKt__Builders_commonKt.launch$default(CoroutineScope, null, null, new AnonymousClass1(null), 3, null);
        }

        /* compiled from: RxScheduler.kt */
        @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 8, 0}, xi = 48)
        @DebugMetadata(c = "kotlinx.coroutines.rx2.DispatcherScheduler$DispatcherWorker$1", f = "RxScheduler.kt", i = {0, 1}, l = {PsExtractor.PRIVATE_STREAM_1, 82}, m = "invokeSuspend", n = {"$this$consume$iv$iv", "$this$consume$iv$iv"}, s = {"L$0", "L$0"})
        /* renamed from: kotlinx.coroutines.rx2.DispatcherScheduler$DispatcherWorker$1, reason: invalid class name */
        static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            Object L$0;
            Object L$1;
            int label;

            AnonymousClass1(Continuation<? super AnonymousClass1> continuation) {
                super(2, continuation);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return DispatcherWorker.this.new AnonymousClass1(continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            /* JADX WARN: Removed duplicated region for block: B:19:0x004e A[RETURN] */
            /* JADX WARN: Removed duplicated region for block: B:20:0x004f  */
            /* JADX WARN: Removed duplicated region for block: B:23:0x005c A[Catch: all -> 0x007c, TryCatch #1 {all -> 0x007c, blocks: (B:21:0x0054, B:23:0x005c, B:27:0x0073), top: B:39:0x0054 }] */
            /* JADX WARN: Removed duplicated region for block: B:27:0x0073 A[Catch: all -> 0x007c, TRY_LEAVE, TryCatch #1 {all -> 0x007c, blocks: (B:21:0x0054, B:23:0x005c, B:27:0x0073), top: B:39:0x0054 }] */
            /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:26:0x006f -> B:17:0x0042). Please report as a decompilation issue!!! */
            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct add '--show-bad-code' argument
            */
            public final java.lang.Object invokeSuspend(java.lang.Object r8) {
                /*
                    r7 = this;
                    java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                    int r1 = r7.label
                    r2 = 2
                    r3 = 1
                    if (r1 == 0) goto L31
                    if (r1 == r3) goto L23
                    if (r1 != r2) goto L1b
                    java.lang.Object r1 = r7.L$1
                    kotlinx.coroutines.channels.ChannelIterator r1 = (kotlinx.coroutines.channels.ChannelIterator) r1
                    java.lang.Object r4 = r7.L$0
                    kotlinx.coroutines.channels.ReceiveChannel r4 = (kotlinx.coroutines.channels.ReceiveChannel) r4
                    kotlin.ResultKt.throwOnFailure(r8)     // Catch: java.lang.Throwable -> L7f
                    r8 = r1
                    goto L41
                L1b:
                    java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
                    java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
                    r8.<init>(r0)
                    throw r8
                L23:
                    java.lang.Object r1 = r7.L$1
                    kotlinx.coroutines.channels.ChannelIterator r1 = (kotlinx.coroutines.channels.ChannelIterator) r1
                    java.lang.Object r4 = r7.L$0
                    kotlinx.coroutines.channels.ReceiveChannel r4 = (kotlinx.coroutines.channels.ReceiveChannel) r4
                    kotlin.ResultKt.throwOnFailure(r8)     // Catch: java.lang.Throwable -> L7f
                    r5 = r4
                    r4 = r7
                    goto L54
                L31:
                    kotlin.ResultKt.throwOnFailure(r8)
                    kotlinx.coroutines.rx2.DispatcherScheduler$DispatcherWorker r8 = kotlinx.coroutines.rx2.DispatcherScheduler.DispatcherWorker.this
                    kotlinx.coroutines.channels.Channel r8 = kotlinx.coroutines.rx2.DispatcherScheduler.DispatcherWorker.access$getBlockChannel$p(r8)
                    r4 = r8
                    kotlinx.coroutines.channels.ReceiveChannel r4 = (kotlinx.coroutines.channels.ReceiveChannel) r4
                    kotlinx.coroutines.channels.ChannelIterator r8 = r4.iterator()     // Catch: java.lang.Throwable -> L7f
                L41:
                    r1 = r7
                L42:
                    r1.L$0 = r4     // Catch: java.lang.Throwable -> L7f
                    r1.L$1 = r8     // Catch: java.lang.Throwable -> L7f
                    r1.label = r3     // Catch: java.lang.Throwable -> L7f
                    java.lang.Object r5 = r8.hasNext(r1)     // Catch: java.lang.Throwable -> L7f
                    if (r5 != r0) goto L4f
                    return r0
                L4f:
                    r6 = r1
                    r1 = r8
                    r8 = r5
                    r5 = r4
                    r4 = r6
                L54:
                    java.lang.Boolean r8 = (java.lang.Boolean) r8     // Catch: java.lang.Throwable -> L7c
                    boolean r8 = r8.booleanValue()     // Catch: java.lang.Throwable -> L7c
                    if (r8 == 0) goto L73
                    java.lang.Object r8 = r1.next()     // Catch: java.lang.Throwable -> L7c
                    kotlin.jvm.functions.Function1 r8 = (kotlin.jvm.functions.Function1) r8     // Catch: java.lang.Throwable -> L7c
                    r4.L$0 = r5     // Catch: java.lang.Throwable -> L7c
                    r4.L$1 = r1     // Catch: java.lang.Throwable -> L7c
                    r4.label = r2     // Catch: java.lang.Throwable -> L7c
                    java.lang.Object r8 = r8.invoke(r4)     // Catch: java.lang.Throwable -> L7c
                    if (r8 != r0) goto L6f
                    return r0
                L6f:
                    r8 = r1
                    r1 = r4
                    r4 = r5
                    goto L42
                L73:
                    kotlin.Unit r8 = kotlin.Unit.INSTANCE     // Catch: java.lang.Throwable -> L7c
                    r8 = 0
                    kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r5, r8)
                    kotlin.Unit r8 = kotlin.Unit.INSTANCE
                    return r8
                L7c:
                    r8 = move-exception
                    r4 = r5
                    goto L80
                L7f:
                    r8 = move-exception
                L80:
                    throw r8     // Catch: java.lang.Throwable -> L81
                L81:
                    r0 = move-exception
                    kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r4, r8)
                    throw r0
                */
                throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.rx2.DispatcherScheduler.DispatcherWorker.AnonymousClass1.invokeSuspend(java.lang.Object):java.lang.Object");
            }
        }

        @Override // io.reactivex.Scheduler.Worker
        public Disposable schedule(Runnable block, long delay, TimeUnit unit) {
            return RxSchedulerKt.scheduleTask(this.workerScope, block, unit.toMillis(delay), new Function1<Function1<? super Continuation<? super Unit>, ? extends Object>, Runnable>() { // from class: kotlinx.coroutines.rx2.DispatcherScheduler$DispatcherWorker$schedule$1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final Runnable invoke(final Function1<? super Continuation<? super Unit>, ? extends Object> function1) {
                    final DispatcherScheduler.DispatcherWorker dispatcherWorker = this.this$0;
                    return new Runnable() { // from class: kotlinx.coroutines.rx2.DispatcherScheduler$DispatcherWorker$schedule$1$invoke$$inlined$Runnable$1
                        @Override // java.lang.Runnable
                        public final void run() {
                            dispatcherWorker.blockChannel.mo2127trySendJP2dKIU(function1);
                        }
                    };
                }
            });
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return !CoroutineScopeKt.isActive(this.workerScope);
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            SendChannel.DefaultImpls.close$default(this.blockChannel, null, 1, null);
            Job.DefaultImpls.cancel$default((Job) this.workerJob, (CancellationException) null, 1, (Object) null);
        }

        public String toString() {
            return this.dispatcher + " (worker " + this.counter + ", " + (isDisposed() ? "disposed" : AppMeasurementSdk.ConditionalUserProperty.ACTIVE) + ')';
        }
    }

    public String toString() {
        return this.dispatcher.toString();
    }
}
