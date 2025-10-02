package com.thor.app.gui.activities.shop;

import android.content.Context;
import android.text.TextUtils;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.thor.app.managers.DataLayerManager;
import com.thor.app.managers.UsersManager;
import com.thor.app.settings.Settings;
import com.thor.networkmodule.model.responses.SignInResponse;
import javax.inject.Inject;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import timber.log.Timber;

/* compiled from: ShopViewModel.kt */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0003\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0002J\u0010\u0010\u0010\u001a\u00020\r2\u0006\u0010\u0011\u001a\u00020\u0012H\u0002J\u0010\u0010\u0013\u001a\u00020\r2\b\b\u0002\u0010\u0014\u001a\u00020\u0015R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00070\t¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0016"}, d2 = {"Lcom/thor/app/gui/activities/shop/ShopViewModel;", "Landroidx/lifecycle/ViewModel;", "usersManager", "Lcom/thor/app/managers/UsersManager;", "(Lcom/thor/app/managers/UsersManager;)V", "_uiState", "Landroidx/lifecycle/MutableLiveData;", "Lcom/thor/app/gui/activities/shop/ShopUiState;", "uiState", "Landroidx/lifecycle/LiveData;", "getUiState", "()Landroidx/lifecycle/LiveData;", "handleError", "", "throwable", "", "handleReAuth", "response", "Lcom/thor/networkmodule/model/responses/SignInResponse;", "reAuth", "showLoading", "", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class ShopViewModel extends ViewModel {
    private final MutableLiveData<ShopUiState> _uiState;
    private final LiveData<ShopUiState> uiState;
    private final UsersManager usersManager;

    @Inject
    public ShopViewModel(UsersManager usersManager) {
        Intrinsics.checkNotNullParameter(usersManager, "usersManager");
        this.usersManager = usersManager;
        MutableLiveData<ShopUiState> mutableLiveData = new MutableLiveData<>();
        this._uiState = mutableLiveData;
        this.uiState = mutableLiveData;
    }

    public final LiveData<ShopUiState> getUiState() {
        return this.uiState;
    }

    public static /* synthetic */ void reAuth$default(ShopViewModel shopViewModel, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            z = true;
        }
        shopViewModel.reAuth(z);
    }

    public final void reAuth(boolean showLoading) {
        if (showLoading) {
            this._uiState.postValue(ShopLoading.INSTANCE);
        }
        this._uiState.postValue(ReAuthSuccessful.INSTANCE);
    }

    private final void handleReAuth(SignInResponse response) {
        if (response.isSuccessful()) {
            if (response.getUserId() == 0 || TextUtils.isEmpty(response.getToken())) {
                return;
            }
            Settings.saveUserId(response.getUserId());
            String token = response.getToken();
            Intrinsics.checkNotNull(token);
            Settings.saveAccessToken(token);
            Settings.saveProfile(response);
            DataLayerManager.Companion companion = DataLayerManager.INSTANCE;
            Context context = this.usersManager.getContext();
            Intrinsics.checkNotNullExpressionValue(context, "usersManager.context");
            companion.from(context).m558sendurrentAppSettings(response, Settings.INSTANCE.getDeviceMacAddress());
            this._uiState.postValue(ReAuthSuccessful.INSTANCE);
            return;
        }
        if (response.getCode() == 888) {
            this._uiState.postValue(DeviceLocked.INSTANCE);
        } else {
            this._uiState.postValue(new ShopError(response.getError()));
        }
    }

    private final void handleError(Throwable throwable) {
        if (!Intrinsics.areEqual(throwable.getMessage(), "HTTP 400") && Intrinsics.areEqual(throwable.getMessage(), "HTTP 500")) {
            this._uiState.postValue(new ShopError(throwable.getMessage()));
        } else {
            this._uiState.postValue(new ShopError(throwable.getMessage()));
        }
        Timber.INSTANCE.e(throwable);
    }
}
