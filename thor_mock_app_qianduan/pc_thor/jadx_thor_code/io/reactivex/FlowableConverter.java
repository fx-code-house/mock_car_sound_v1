package io.reactivex;

/* loaded from: classes3.dex */
public interface FlowableConverter<T, R> {
    R apply(Flowable<T> flowable);
}
