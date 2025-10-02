package io.reactivex;

/* loaded from: classes3.dex */
public interface SingleConverter<T, R> {
    R apply(Single<T> single);
}
