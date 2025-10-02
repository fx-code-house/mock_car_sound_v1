package com.thor.app.rx;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/* loaded from: classes3.dex */
public class DefaultSchedulerTransformer<T> implements ObservableTransformer<T, T> {
    @Override // io.reactivex.ObservableTransformer
    public Observable<T> apply(Observable<T> upstream) {
        return upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
}
