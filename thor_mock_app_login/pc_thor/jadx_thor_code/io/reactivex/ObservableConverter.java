package io.reactivex;

/* loaded from: classes3.dex */
public interface ObservableConverter<T, R> {
    R apply(Observable<T> observable);
}
