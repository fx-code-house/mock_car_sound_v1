package com.thor.app.gui.activities.splash;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.text.TextUtils;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelKt;
import com.thor.app.gui.activities.splash.VersionState;
import com.thor.app.network.Api;
import com.thor.app.room.dao.CurrentVersionDao;
import com.thor.app.settings.Settings;
import com.thor.networkmodule.network.ApiServiceNew;
import java.util.Locale;
import javax.inject.Inject;
import javax.inject.Named;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;
import retrofit2.Response;
import timber.log.Timber;

/* compiled from: SplashActivityViewModel.kt */
@Metadata(d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\u0000 \u001f2\u00020\u0001:\u0001\u001fB\u0019\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0001\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u001f\u0010\u0012\u001a\u00020\u00132\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00160\u0015H\u0082@ø\u0001\u0000¢\u0006\u0002\u0010\u0017J\u0006\u0010\u0018\u001a\u00020\u0013J\u000e\u0010\u0019\u001a\u00020\u00132\u0006\u0010\u001a\u001a\u00020\u001bJ\u000e\u0010\u001c\u001a\u00020\u00132\u0006\u0010\u001d\u001a\u00020\u001eR\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u000b0\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0017\u0010\f\u001a\b\u0012\u0004\u0012\u00020\t0\r¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0017\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u000b0\r¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u000f\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006 "}, d2 = {"Lcom/thor/app/gui/activities/splash/SplashActivityViewModel;", "Landroidx/lifecycle/ViewModel;", "currentVersion", "Lcom/thor/app/room/dao/CurrentVersionDao;", "apiNewService", "Lcom/thor/networkmodule/network/ApiServiceNew;", "(Lcom/thor/app/room/dao/CurrentVersionDao;Lcom/thor/networkmodule/network/ApiServiceNew;)V", "_apiErrorState", "Landroidx/lifecycle/MutableLiveData;", "Lcom/thor/app/gui/activities/splash/SplashActivityViewModel$Companion$ApiErrorState;", "_versionState", "Lcom/thor/app/gui/activities/splash/VersionState;", "apiErrorState", "Landroidx/lifecycle/LiveData;", "getApiErrorState", "()Landroidx/lifecycle/LiveData;", "versionState", "getVersionState", "handleResponseFromApi", "", "response", "Lretrofit2/Response;", "Lcom/thor/networkmodule/model/responses/versions/CurrentAppVersion;", "(Lretrofit2/Response;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "processVersionsFromApi", "updateLocalLanguage", "resources", "Landroid/content/res/Resources;", "updateSelectedServer", "context", "Landroid/content/Context;", "Companion", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class SplashActivityViewModel extends ViewModel {
    private final MutableLiveData<Companion.ApiErrorState> _apiErrorState;
    private final MutableLiveData<VersionState> _versionState;
    private final LiveData<Companion.ApiErrorState> apiErrorState;
    private final ApiServiceNew apiNewService;
    private final CurrentVersionDao currentVersion;
    private final LiveData<VersionState> versionState;

    /* compiled from: SplashActivityViewModel.kt */
    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    @DebugMetadata(c = "com.thor.app.gui.activities.splash.SplashActivityViewModel", f = "SplashActivityViewModel.kt", i = {0, 0}, l = {76, 77}, m = "handleResponseFromApi", n = {"this", "versionList"}, s = {"L$0", "L$1"})
    /* renamed from: com.thor.app.gui.activities.splash.SplashActivityViewModel$handleResponseFromApi$1, reason: invalid class name */
    static final class AnonymousClass1 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        int label;
        /* synthetic */ Object result;

        AnonymousClass1(Continuation<? super AnonymousClass1> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return SplashActivityViewModel.this.handleResponseFromApi(null, this);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Inject
    public SplashActivityViewModel(CurrentVersionDao currentVersion, @Named("new_api_service") ApiServiceNew apiNewService) {
        Intrinsics.checkNotNullParameter(currentVersion, "currentVersion");
        Intrinsics.checkNotNullParameter(apiNewService, "apiNewService");
        this.currentVersion = currentVersion;
        this.apiNewService = apiNewService;
        MutableLiveData<VersionState> mutableLiveData = new MutableLiveData<>();
        this._versionState = mutableLiveData;
        this.versionState = mutableLiveData;
        MutableLiveData<Companion.ApiErrorState> mutableLiveData2 = new MutableLiveData<>(new Companion.ApiErrorState(false, null, 2, null == true ? 1 : 0));
        this._apiErrorState = mutableLiveData2;
        this.apiErrorState = mutableLiveData2;
    }

    public final LiveData<VersionState> getVersionState() {
        return this.versionState;
    }

    public final LiveData<Companion.ApiErrorState> getApiErrorState() {
        return this.apiErrorState;
    }

    /* compiled from: SplashActivityViewModel.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 8, 0}, xi = 48)
    @DebugMetadata(c = "com.thor.app.gui.activities.splash.SplashActivityViewModel$processVersionsFromApi$1", f = "SplashActivityViewModel.kt", i = {}, l = {51, 52}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.thor.app.gui.activities.splash.SplashActivityViewModel$processVersionsFromApi$1, reason: invalid class name and case insensitive filesystem */
    static final class C02421 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;

        C02421(Continuation<? super C02421> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return SplashActivityViewModel.this.new C02421(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C02421) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            try {
            } catch (Exception e) {
                if (Intrinsics.areEqual(e.getMessage(), "HTTP 400") || Intrinsics.areEqual(e.getMessage(), "HTTP 500")) {
                    SplashActivityViewModel.this._apiErrorState.postValue(new Companion.ApiErrorState(Boxing.boxBoolean(true), e.getMessage()));
                }
                SplashActivityViewModel.this._versionState.postValue(VersionState.oldVersion.INSTANCE);
            }
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                this.label = 1;
                obj = SplashActivityViewModel.this.apiNewService.getAppVersionsFromApi(this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    if (i != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
                ResultKt.throwOnFailure(obj);
            }
            SplashActivityViewModel splashActivityViewModel = SplashActivityViewModel.this;
            this.label = 2;
            if (splashActivityViewModel.handleResponseFromApi((Response) obj, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            return Unit.INSTANCE;
        }
    }

    public final void processVersionsFromApi() {
        BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), Dispatchers.getIO(), null, new C02421(null), 2, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:31:0x00bd A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0014  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object handleResponseFromApi(retrofit2.Response<com.thor.networkmodule.model.responses.versions.CurrentAppVersion> r13, kotlin.coroutines.Continuation<? super kotlin.Unit> r14) {
        /*
            r12 = this;
            boolean r0 = r14 instanceof com.thor.app.gui.activities.splash.SplashActivityViewModel.AnonymousClass1
            if (r0 == 0) goto L14
            r0 = r14
            com.thor.app.gui.activities.splash.SplashActivityViewModel$handleResponseFromApi$1 r0 = (com.thor.app.gui.activities.splash.SplashActivityViewModel.AnonymousClass1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L14
            int r14 = r0.label
            int r14 = r14 - r2
            r0.label = r14
            goto L19
        L14:
            com.thor.app.gui.activities.splash.SplashActivityViewModel$handleResponseFromApi$1 r0 = new com.thor.app.gui.activities.splash.SplashActivityViewModel$handleResponseFromApi$1
            r0.<init>(r14)
        L19:
            java.lang.Object r14 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 2
            r4 = 1
            if (r2 == 0) goto L42
            if (r2 == r4) goto L36
            if (r2 != r3) goto L2e
            kotlin.ResultKt.throwOnFailure(r14)
            goto Lbe
        L2e:
            java.lang.IllegalStateException r13 = new java.lang.IllegalStateException
            java.lang.String r14 = "call to 'resume' before 'invoke' with coroutine"
            r13.<init>(r14)
            throw r13
        L36:
            java.lang.Object r13 = r0.L$1
            java.util.List r13 = (java.util.List) r13
            java.lang.Object r2 = r0.L$0
            com.thor.app.gui.activities.splash.SplashActivityViewModel r2 = (com.thor.app.gui.activities.splash.SplashActivityViewModel) r2
            kotlin.ResultKt.throwOnFailure(r14)
            goto Lae
        L42:
            kotlin.ResultKt.throwOnFailure(r14)
            boolean r14 = r13.isSuccessful()
            if (r14 != 0) goto L53
            androidx.lifecycle.MutableLiveData<com.thor.app.gui.activities.splash.VersionState> r13 = r12._versionState
            com.thor.app.gui.activities.splash.VersionState$oldVersion r14 = com.thor.app.gui.activities.splash.VersionState.oldVersion.INSTANCE
            r13.postValue(r14)
            goto Lbe
        L53:
            java.lang.Object r13 = r13.body()
            com.thor.networkmodule.model.responses.versions.CurrentAppVersion r13 = (com.thor.networkmodule.model.responses.versions.CurrentAppVersion) r13
            if (r13 == 0) goto Lbe
            com.thor.networkmodule.model.responses.versions.VersionsApp r13 = r13.getVersions()
            com.thor.networkmodule.model.responses.versions.Devices r13 = r13.getDevices()
            java.util.List r13 = r13.getMinimum()
            java.lang.Iterable r13 = (java.lang.Iterable) r13
            java.util.ArrayList r14 = new java.util.ArrayList
            r2 = 10
            int r2 = kotlin.collections.CollectionsKt.collectionSizeOrDefault(r13, r2)
            r14.<init>(r2)
            java.util.Collection r14 = (java.util.Collection) r14
            java.util.Iterator r13 = r13.iterator()
        L7a:
            boolean r2 = r13.hasNext()
            if (r2 == 0) goto L9b
            java.lang.Object r2 = r13.next()
            com.thor.networkmodule.model.responses.versions.MinimumHardware r2 = (com.thor.networkmodule.model.responses.versions.MinimumHardware) r2
            com.thor.app.room.entity.CurrentVersionsEntity r11 = new com.thor.app.room.entity.CurrentVersionsEntity
            r6 = 0
            java.lang.String r7 = r2.getFirmware()
            java.lang.String r8 = r2.getHardware()
            r9 = 1
            r10 = 0
            r5 = r11
            r5.<init>(r6, r7, r8, r9, r10)
            r14.add(r11)
            goto L7a
        L9b:
            r13 = r14
            java.util.List r13 = (java.util.List) r13
            com.thor.app.room.dao.CurrentVersionDao r14 = r12.currentVersion
            r0.L$0 = r12
            r0.L$1 = r13
            r0.label = r4
            java.lang.Object r14 = r14.deleteAll(r0)
            if (r14 != r1) goto Lad
            return r1
        Lad:
            r2 = r12
        Lae:
            com.thor.app.room.dao.CurrentVersionDao r14 = r2.currentVersion
            r2 = 0
            r0.L$0 = r2
            r0.L$1 = r2
            r0.label = r3
            java.lang.Object r13 = r14.insert(r13, r0)
            if (r13 != r1) goto Lbe
            return r1
        Lbe:
            kotlin.Unit r13 = kotlin.Unit.INSTANCE
            return r13
        */
        throw new UnsupportedOperationException("Method not decompiled: com.thor.app.gui.activities.splash.SplashActivityViewModel.handleResponseFromApi(retrofit2.Response, kotlin.coroutines.Continuation):java.lang.Object");
    }

    public final void updateSelectedServer(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        Api.from(context).changeServer(context);
    }

    public final void updateLocalLanguage(Resources resources) {
        Intrinsics.checkNotNullParameter(resources, "resources");
        Settings settings = Settings.INSTANCE;
        String languageCode = Settings.getLanguageCode();
        if (TextUtils.isEmpty(languageCode)) {
            return;
        }
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);
        Configuration configuration = new Configuration(resources.getConfiguration());
        configuration.setLocale(locale);
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
        Timber.INSTANCE.i("Language code: %s", languageCode);
    }
}
