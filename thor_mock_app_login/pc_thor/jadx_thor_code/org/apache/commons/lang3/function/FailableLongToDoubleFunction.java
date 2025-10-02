package org.apache.commons.lang3.function;

import java.lang.Throwable;

@FunctionalInterface
/* loaded from: classes3.dex */
public interface FailableLongToDoubleFunction<E extends Throwable> {
    public static final FailableLongToDoubleFunction NOP = new FailableLongToDoubleFunction() { // from class: org.apache.commons.lang3.function.FailableLongToDoubleFunction$$ExternalSyntheticLambda0
        @Override // org.apache.commons.lang3.function.FailableLongToDoubleFunction
        public final double applyAsDouble(long j) {
            return FailableLongToDoubleFunction.lambda$static$0(j);
        }
    };

    static /* synthetic */ double lambda$static$0(long j) throws Throwable {
        return 0.0d;
    }

    double applyAsDouble(long j) throws Throwable;

    static <E extends Throwable> FailableLongToDoubleFunction<E> nop() {
        return NOP;
    }
}
