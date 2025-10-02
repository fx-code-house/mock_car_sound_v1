package org.apache.commons.lang3.function;

import java.lang.Throwable;
import java.util.Objects;

@FunctionalInterface
/* loaded from: classes3.dex */
public interface FailableConsumer<T, E extends Throwable> {
    public static final FailableConsumer NOP = new FailableConsumer() { // from class: org.apache.commons.lang3.function.FailableConsumer$$ExternalSyntheticLambda1
        @Override // org.apache.commons.lang3.function.FailableConsumer
        public final void accept(Object obj) throws Throwable {
            FailableConsumer.lambda$static$0(obj);
        }
    };

    static /* synthetic */ void lambda$static$0(Object obj) throws Throwable {
    }

    void accept(T t) throws Throwable;

    static <T, E extends Throwable> FailableConsumer<T, E> nop() {
        return NOP;
    }

    default FailableConsumer<T, E> andThen(final FailableConsumer<? super T, E> failableConsumer) {
        Objects.requireNonNull(failableConsumer);
        return new FailableConsumer() { // from class: org.apache.commons.lang3.function.FailableConsumer$$ExternalSyntheticLambda0
            @Override // org.apache.commons.lang3.function.FailableConsumer
            public final void accept(Object obj) throws Throwable {
                FailableConsumer.lambda$andThen$1(this.f$0, failableConsumer, obj);
            }
        };
    }

    static /* synthetic */ void lambda$andThen$1(FailableConsumer _this, FailableConsumer failableConsumer, Object obj) throws Throwable {
        _this.accept(obj);
        failableConsumer.accept(obj);
    }
}
