package io.reactivex;

/* loaded from: classes3.dex */
public interface MaybeConverter<T, R> {
    R apply(Maybe<T> maybe);
}
