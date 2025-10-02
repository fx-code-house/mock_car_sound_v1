package com.thor.businessmodule.viewmodel.menu;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import kotlin.Metadata;
import org.apache.commons.lang3.StringUtils;

/* compiled from: MainMenuViewModel.kt */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u000b\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002R\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\nR\u0017\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u0007R\u0017\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u0007R\u001f\u0010\u000f\u001a\u0010\u0012\f\u0012\n \u0010*\u0004\u0018\u00010\u00050\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0007R\u0017\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0007¨\u0006\u0014"}, d2 = {"Lcom/thor/businessmodule/viewmodel/menu/MainMenuViewModel;", "", "()V", "appVersion", "Landroidx/databinding/ObservableField;", "", "getAppVersion", "()Landroidx/databinding/ObservableField;", "isUpdateSoftware", "Landroidx/databinding/ObservableBoolean;", "()Landroidx/databinding/ObservableBoolean;", "phoneNumber", "getPhoneNumber", "serialNumber", "getSerialNumber", "userId", "kotlin.jvm.PlatformType", "getUserId", "version", "getVersion", "businessmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class MainMenuViewModel {
    private final ObservableBoolean isUpdateSoftware = new ObservableBoolean(false);
    private final ObservableField<String> serialNumber = new ObservableField<>("");
    private final ObservableField<String> version = new ObservableField<>(StringUtils.SPACE);
    private final ObservableField<String> appVersion = new ObservableField<>(StringUtils.SPACE);
    private final ObservableField<String> phoneNumber = new ObservableField<>("");
    private final ObservableField<String> userId = new ObservableField<>("");

    /* renamed from: isUpdateSoftware, reason: from getter */
    public final ObservableBoolean getIsUpdateSoftware() {
        return this.isUpdateSoftware;
    }

    public final ObservableField<String> getSerialNumber() {
        return this.serialNumber;
    }

    public final ObservableField<String> getVersion() {
        return this.version;
    }

    public final ObservableField<String> getAppVersion() {
        return this.appVersion;
    }

    public final ObservableField<String> getPhoneNumber() {
        return this.phoneNumber;
    }

    public final ObservableField<String> getUserId() {
        return this.userId;
    }
}
