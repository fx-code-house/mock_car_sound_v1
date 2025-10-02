package com.thor.businessmodule.viewmodel.base;

import androidx.lifecycle.ViewModel;
import io.reactivex.disposables.CompositeDisposable;
import kotlin.Metadata;

/* compiled from: DisposableViewModel.kt */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\b\u0016\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0007\u001a\u00020\bH\u0014R\u0014\u0010\u0003\u001a\u00020\u0004X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\t"}, d2 = {"Lcom/thor/businessmodule/viewmodel/base/DisposableViewModel;", "Landroidx/lifecycle/ViewModel;", "()V", "disposables", "Lio/reactivex/disposables/CompositeDisposable;", "getDisposables", "()Lio/reactivex/disposables/CompositeDisposable;", "onCleared", "", "businessmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public class DisposableViewModel extends ViewModel {
    private final CompositeDisposable disposables = new CompositeDisposable();

    protected final CompositeDisposable getDisposables() {
        return this.disposables;
    }

    @Override // androidx.lifecycle.ViewModel
    protected void onCleared() {
        this.disposables.clear();
        super.onCleared();
    }
}
