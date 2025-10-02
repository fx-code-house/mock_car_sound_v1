package com.thor.app.databinding.viewmodels;

import androidx.databinding.Observable;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import kotlin.Metadata;

/* compiled from: SignUpAddDeviceActivityViewModel.kt */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\u0018\u00002\u00020\u0001:\u0001\u0018B\u0005¢\u0006\u0002\u0010\u0002J\u001a\u0010\u0012\u001a\u00020\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u00152\u0006\u0010\u0016\u001a\u00020\u0017H\u0016R\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u001c\u0010\f\u001a\u0004\u0018\u00010\rX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011¨\u0006\u0019"}, d2 = {"Lcom/thor/app/databinding/viewmodels/SignUpAddDeviceActivityViewModel;", "Landroidx/databinding/Observable$OnPropertyChangedCallback;", "()V", "deviceSn", "Landroidx/databinding/ObservableField;", "", "getDeviceSn", "()Landroidx/databinding/ObservableField;", "enableSignUp", "Landroidx/databinding/ObservableBoolean;", "getEnableSignUp", "()Landroidx/databinding/ObservableBoolean;", "mListener", "Lcom/thor/app/databinding/viewmodels/SignUpAddDeviceActivityViewModel$OnChangedResultListener;", "getMListener", "()Lcom/thor/app/databinding/viewmodels/SignUpAddDeviceActivityViewModel$OnChangedResultListener;", "setMListener", "(Lcom/thor/app/databinding/viewmodels/SignUpAddDeviceActivityViewModel$OnChangedResultListener;)V", "onPropertyChanged", "", "sender", "Landroidx/databinding/Observable;", "propertyId", "", "OnChangedResultListener", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes2.dex */
public final class SignUpAddDeviceActivityViewModel extends Observable.OnPropertyChangedCallback {
    private final ObservableField<String> deviceSn;
    private final ObservableBoolean enableSignUp;
    private OnChangedResultListener mListener;

    /* compiled from: SignUpAddDeviceActivityViewModel.kt */
    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&¨\u0006\u0006"}, d2 = {"Lcom/thor/app/databinding/viewmodels/SignUpAddDeviceActivityViewModel$OnChangedResultListener;", "", "onChange", "", "result", "", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public interface OnChangedResultListener {
        void onChange(boolean result);
    }

    public SignUpAddDeviceActivityViewModel() {
        ObservableField<String> observableField = new ObservableField<>("");
        this.deviceSn = observableField;
        this.enableSignUp = new ObservableBoolean(false);
        observableField.addOnPropertyChangedCallback(this);
    }

    public final ObservableField<String> getDeviceSn() {
        return this.deviceSn;
    }

    public final ObservableBoolean getEnableSignUp() {
        return this.enableSignUp;
    }

    public final OnChangedResultListener getMListener() {
        return this.mListener;
    }

    public final void setMListener(OnChangedResultListener onChangedResultListener) {
        this.mListener = onChangedResultListener;
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0043  */
    @Override // androidx.databinding.Observable.OnPropertyChangedCallback
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void onPropertyChanged(androidx.databinding.Observable r6, int r7) {
        /*
            r5 = this;
            androidx.databinding.ObservableField<java.lang.String> r6 = r5.deviceSn
            java.lang.Object r6 = r6.get()
            java.lang.String r6 = (java.lang.String) r6
            r7 = r6
            java.lang.CharSequence r7 = (java.lang.CharSequence) r7
            boolean r7 = android.text.TextUtils.isEmpty(r7)
            r0 = 0
            if (r7 != 0) goto L4b
            r7 = 2
            java.lang.String r1 = "Thor"
            r2 = 0
            if (r6 == 0) goto L21
            boolean r3 = kotlin.text.StringsKt.startsWith$default(r6, r1, r0, r7, r2)
            java.lang.Boolean r3 = java.lang.Boolean.valueOf(r3)
            goto L22
        L21:
            r3 = r2
        L22:
            kotlin.jvm.internal.Intrinsics.checkNotNull(r3)
            boolean r3 = r3.booleanValue()
            if (r3 != 0) goto L43
            java.util.Locale r3 = java.util.Locale.getDefault()
            java.lang.String r4 = "getDefault()"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r3, r4)
            java.lang.String r1 = r1.toLowerCase(r3)
            java.lang.String r3 = "toLowerCase(...)"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r1, r3)
            boolean r7 = kotlin.text.StringsKt.startsWith$default(r6, r1, r0, r7, r2)
            if (r7 == 0) goto L4b
        L43:
            int r6 = r6.length()
            r7 = 7
            if (r6 < r7) goto L4b
            r0 = 1
        L4b:
            androidx.databinding.ObservableBoolean r6 = r5.enableSignUp
            r6.set(r0)
            com.thor.app.databinding.viewmodels.SignUpAddDeviceActivityViewModel$OnChangedResultListener r6 = r5.mListener
            if (r6 == 0) goto L57
            r6.onChange(r0)
        L57:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.thor.app.databinding.viewmodels.SignUpAddDeviceActivityViewModel.onPropertyChanged(androidx.databinding.Observable, int):void");
    }
}
