package io.reactivex.internal.operators.parallel;

import io.reactivex.exceptions.CompositeException;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Predicate;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.ConditionalSubscriber;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.parallel.ParallelFailureHandling;
import io.reactivex.parallel.ParallelFlowable;
import io.reactivex.plugins.RxJavaPlugins;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes3.dex */
public final class ParallelFilterTry<T> extends ParallelFlowable<T> {
    final BiFunction<? super Long, ? super Throwable, ParallelFailureHandling> errorHandler;
    final Predicate<? super T> predicate;
    final ParallelFlowable<T> source;

    public ParallelFilterTry(ParallelFlowable<T> parallelFlowable, Predicate<? super T> predicate, BiFunction<? super Long, ? super Throwable, ParallelFailureHandling> biFunction) {
        this.source = parallelFlowable;
        this.predicate = predicate;
        this.errorHandler = biFunction;
    }

    @Override // io.reactivex.parallel.ParallelFlowable
    public void subscribe(Subscriber<? super T>[] subscriberArr) {
        if (validate(subscriberArr)) {
            int length = subscriberArr.length;
            Subscriber<? super T>[] subscriberArr2 = new Subscriber[length];
            for (int i = 0; i < length; i++) {
                Subscriber<? super T> subscriber = subscriberArr[i];
                if (subscriber instanceof ConditionalSubscriber) {
                    subscriberArr2[i] = new ParallelFilterConditionalSubscriber((ConditionalSubscriber) subscriber, this.predicate, this.errorHandler);
                } else {
                    subscriberArr2[i] = new ParallelFilterSubscriber(subscriber, this.predicate, this.errorHandler);
                }
            }
            this.source.subscribe(subscriberArr2);
        }
    }

    @Override // io.reactivex.parallel.ParallelFlowable
    public int parallelism() {
        return this.source.parallelism();
    }

    static abstract class BaseFilterSubscriber<T> implements ConditionalSubscriber<T>, Subscription {
        boolean done;
        final BiFunction<? super Long, ? super Throwable, ParallelFailureHandling> errorHandler;
        final Predicate<? super T> predicate;
        Subscription upstream;

        BaseFilterSubscriber(Predicate<? super T> predicate, BiFunction<? super Long, ? super Throwable, ParallelFailureHandling> biFunction) {
            this.predicate = predicate;
            this.errorHandler = biFunction;
        }

        @Override // org.reactivestreams.Subscription
        public final void request(long j) {
            this.upstream.request(j);
        }

        @Override // org.reactivestreams.Subscription
        public final void cancel() {
            this.upstream.cancel();
        }

        @Override // org.reactivestreams.Subscriber
        public final void onNext(T t) {
            if (tryOnNext(t) || this.done) {
                return;
            }
            this.upstream.request(1L);
        }
    }

    static final class ParallelFilterSubscriber<T> extends BaseFilterSubscriber<T> {
        final Subscriber<? super T> downstream;

        ParallelFilterSubscriber(Subscriber<? super T> subscriber, Predicate<? super T> predicate, BiFunction<? super Long, ? super Throwable, ParallelFailureHandling> biFunction) {
            super(predicate, biFunction);
            this.downstream = subscriber;
        }

        @Override // io.reactivex.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.upstream, subscription)) {
                this.upstream = subscription;
                this.downstream.onSubscribe(this);
            }
        }

        @Override // io.reactivex.internal.fuseable.ConditionalSubscriber
        public boolean tryOnNext(T t) {
            int i;
            if (!this.done) {
                long j = 0;
                do {
                    try {
                        if (!this.predicate.test(t)) {
                            return false;
                        }
                        this.downstream.onNext(t);
                        return true;
                    } catch (Throwable th) {
                        Exceptions.throwIfFatal(th);
                        try {
                            j++;
                            i = AnonymousClass1.$SwitchMap$io$reactivex$parallel$ParallelFailureHandling[((ParallelFailureHandling) ObjectHelper.requireNonNull(this.errorHandler.apply(Long.valueOf(j), th), "The errorHandler returned a null item")).ordinal()];
                        } catch (Throwable th2) {
                            Exceptions.throwIfFatal(th2);
                            cancel();
                            onError(new CompositeException(th, th2));
                        }
                    }
                } while (i == 1);
                if (i != 2) {
                    if (i == 3) {
                        cancel();
                        onComplete();
                    } else {
                        cancel();
                        onError(th);
                        return false;
                    }
                }
                return false;
            }
            return false;
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.done) {
                RxJavaPlugins.onError(th);
            } else {
                this.done = true;
                this.downstream.onError(th);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            this.downstream.onComplete();
        }
    }

    /* renamed from: io.reactivex.internal.operators.parallel.ParallelFilterTry$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$io$reactivex$parallel$ParallelFailureHandling;

        static {
            int[] iArr = new int[ParallelFailureHandling.values().length];
            $SwitchMap$io$reactivex$parallel$ParallelFailureHandling = iArr;
            try {
                iArr[ParallelFailureHandling.RETRY.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$io$reactivex$parallel$ParallelFailureHandling[ParallelFailureHandling.SKIP.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$io$reactivex$parallel$ParallelFailureHandling[ParallelFailureHandling.STOP.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    static final class ParallelFilterConditionalSubscriber<T> extends BaseFilterSubscriber<T> {
        final ConditionalSubscriber<? super T> downstream;

        ParallelFilterConditionalSubscriber(ConditionalSubscriber<? super T> conditionalSubscriber, Predicate<? super T> predicate, BiFunction<? super Long, ? super Throwable, ParallelFailureHandling> biFunction) {
            super(predicate, biFunction);
            this.downstream = conditionalSubscriber;
        }

        @Override // io.reactivex.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.upstream, subscription)) {
                this.upstream = subscription;
                this.downstream.onSubscribe(this);
            }
        }

        @Override // io.reactivex.internal.fuseable.ConditionalSubscriber
        public boolean tryOnNext(T t) {
            int i;
            if (!this.done) {
                long j = 0;
                do {
                    try {
                        return this.predicate.test(t) && this.downstream.tryOnNext(t);
                    } catch (Throwable th) {
                        Exceptions.throwIfFatal(th);
                        try {
                            j++;
                            i = AnonymousClass1.$SwitchMap$io$reactivex$parallel$ParallelFailureHandling[((ParallelFailureHandling) ObjectHelper.requireNonNull(this.errorHandler.apply(Long.valueOf(j), th), "The errorHandler returned a null item")).ordinal()];
                        } catch (Throwable th2) {
                            Exceptions.throwIfFatal(th2);
                            cancel();
                            onError(new CompositeException(th, th2));
                        }
                    }
                } while (i == 1);
                if (i != 2) {
                    if (i == 3) {
                        cancel();
                        onComplete();
                    } else {
                        cancel();
                        onError(th);
                        return false;
                    }
                }
                return false;
            }
            return false;
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.done) {
                RxJavaPlugins.onError(th);
            } else {
                this.done = true;
                this.downstream.onError(th);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            this.downstream.onComplete();
        }
    }
}
