package com.google.firebase.installations;

import android.text.TextUtils;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.FirebaseApp;
import com.google.firebase.heartbeatinfo.HeartBeatInfo;
import com.google.firebase.inject.Provider;
import com.google.firebase.installations.FirebaseInstallationsException;
import com.google.firebase.installations.local.IidStore;
import com.google.firebase.installations.local.PersistedInstallation;
import com.google.firebase.installations.local.PersistedInstallationEntry;
import com.google.firebase.installations.remote.FirebaseInstallationServiceClient;
import com.google.firebase.installations.remote.InstallationResponse;
import com.google.firebase.installations.remote.TokenResult;
import com.google.firebase.platforminfo.UserAgentPublisher;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes2.dex */
public class FirebaseInstallations implements FirebaseInstallationsApi {
    private static final String API_KEY_VALIDATION_MSG = "Please set a valid API key. A Firebase API key is required to communicate with Firebase server APIs: It authenticates your project with Google.Please refer to https://firebase.google.com/support/privacy/init-options.";
    private static final String APP_ID_VALIDATION_MSG = "Please set your Application ID. A valid Firebase App ID is required to communicate with Firebase server APIs: It identifies your application with Firebase.Please refer to https://firebase.google.com/support/privacy/init-options.";
    private static final String AUTH_ERROR_MSG = "Installation ID could not be validated with the Firebase servers (maybe it was deleted). Firebase Installations will need to create a new Installation ID and auth token. Please retry your last request.";
    private static final String CHIME_FIREBASE_APP_NAME = "CHIME_ANDROID_SDK";
    private static final int CORE_POOL_SIZE = 0;
    private static final long KEEP_ALIVE_TIME_IN_SECONDS = 30;
    private static final String LOCKFILE_NAME_GENERATE_FID = "generatefid.lock";
    private static final int MAXIMUM_POOL_SIZE = 1;
    private static final String PROJECT_ID_VALIDATION_MSG = "Please set your Project ID. A valid Firebase Project ID is required to communicate with Firebase server APIs: It identifies your application with Firebase.Please refer to https://firebase.google.com/support/privacy/init-options.";
    private final ExecutorService backgroundExecutor;
    private String cachedFid;
    private final RandomFidGenerator fidGenerator;
    private final FirebaseApp firebaseApp;
    private final IidStore iidStore;
    private final List<StateListener> listeners;
    private final Object lock;
    private final ExecutorService networkExecutor;
    private final PersistedInstallation persistedInstallation;
    private final FirebaseInstallationServiceClient serviceClient;
    private final Utils utils;
    private static final Object lockGenerateFid = new Object();
    private static final ThreadFactory THREAD_FACTORY = new ThreadFactory() { // from class: com.google.firebase.installations.FirebaseInstallations.1
        private final AtomicInteger mCount = new AtomicInteger(1);

        @Override // java.util.concurrent.ThreadFactory
        public Thread newThread(Runnable runnable) {
            return new Thread(runnable, String.format("firebase-installations-executor-%d", Integer.valueOf(this.mCount.getAndIncrement())));
        }
    };

    FirebaseInstallations(FirebaseApp firebaseApp, Provider<UserAgentPublisher> provider, Provider<HeartBeatInfo> provider2) {
        this(new ThreadPoolExecutor(0, 1, 30L, TimeUnit.SECONDS, new LinkedBlockingQueue(), THREAD_FACTORY), firebaseApp, new FirebaseInstallationServiceClient(firebaseApp.getApplicationContext(), provider, provider2), new PersistedInstallation(firebaseApp), Utils.getInstance(), new IidStore(firebaseApp), new RandomFidGenerator());
    }

    FirebaseInstallations(ExecutorService executorService, FirebaseApp firebaseApp, FirebaseInstallationServiceClient firebaseInstallationServiceClient, PersistedInstallation persistedInstallation, Utils utils, IidStore iidStore, RandomFidGenerator randomFidGenerator) {
        this.lock = new Object();
        this.listeners = new ArrayList();
        this.firebaseApp = firebaseApp;
        this.serviceClient = firebaseInstallationServiceClient;
        this.persistedInstallation = persistedInstallation;
        this.utils = utils;
        this.iidStore = iidStore;
        this.fidGenerator = randomFidGenerator;
        this.backgroundExecutor = executorService;
        this.networkExecutor = new ThreadPoolExecutor(0, 1, 30L, TimeUnit.SECONDS, new LinkedBlockingQueue(), THREAD_FACTORY);
    }

    private void preConditionChecks() {
        Preconditions.checkNotEmpty(getApplicationId(), APP_ID_VALIDATION_MSG);
        Preconditions.checkNotEmpty(getProjectIdentifier(), PROJECT_ID_VALIDATION_MSG);
        Preconditions.checkNotEmpty(getApiKey(), API_KEY_VALIDATION_MSG);
        Preconditions.checkArgument(Utils.isValidAppIdFormat(getApplicationId()), APP_ID_VALIDATION_MSG);
        Preconditions.checkArgument(Utils.isValidApiKeyFormat(getApiKey()), API_KEY_VALIDATION_MSG);
    }

    String getProjectIdentifier() {
        return this.firebaseApp.getOptions().getProjectId();
    }

    public static FirebaseInstallations getInstance() {
        return getInstance(FirebaseApp.getInstance());
    }

    public static FirebaseInstallations getInstance(FirebaseApp firebaseApp) {
        Preconditions.checkArgument(firebaseApp != null, "Null is not a valid value of FirebaseApp.");
        return (FirebaseInstallations) firebaseApp.get(FirebaseInstallationsApi.class);
    }

    String getApplicationId() {
        return this.firebaseApp.getOptions().getApplicationId();
    }

    String getApiKey() {
        return this.firebaseApp.getOptions().getApiKey();
    }

    String getName() {
        return this.firebaseApp.getName();
    }

    @Override // com.google.firebase.installations.FirebaseInstallationsApi
    public Task<String> getId() {
        preConditionChecks();
        String cacheFid = getCacheFid();
        if (cacheFid != null) {
            return Tasks.forResult(cacheFid);
        }
        Task<String> taskAddGetIdListener = addGetIdListener();
        this.backgroundExecutor.execute(FirebaseInstallations$$Lambda$1.lambdaFactory$(this));
        return taskAddGetIdListener;
    }

    @Override // com.google.firebase.installations.FirebaseInstallationsApi
    public Task<InstallationTokenResult> getToken(boolean z) {
        preConditionChecks();
        Task<InstallationTokenResult> taskAddGetAuthTokenListener = addGetAuthTokenListener();
        this.backgroundExecutor.execute(FirebaseInstallations$$Lambda$2.lambdaFactory$(this, z));
        return taskAddGetAuthTokenListener;
    }

    @Override // com.google.firebase.installations.FirebaseInstallationsApi
    public Task<Void> delete() {
        return Tasks.call(this.backgroundExecutor, FirebaseInstallations$$Lambda$3.lambdaFactory$(this));
    }

    private Task<String> addGetIdListener() {
        TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
        addStateListeners(new GetIdListener(taskCompletionSource));
        return taskCompletionSource.getTask();
    }

    private Task<InstallationTokenResult> addGetAuthTokenListener() {
        TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
        addStateListeners(new GetAuthTokenListener(this.utils, taskCompletionSource));
        return taskCompletionSource.getTask();
    }

    private void addStateListeners(StateListener stateListener) {
        synchronized (this.lock) {
            this.listeners.add(stateListener);
        }
    }

    private void triggerOnStateReached(PersistedInstallationEntry persistedInstallationEntry) {
        synchronized (this.lock) {
            Iterator<StateListener> it = this.listeners.iterator();
            while (it.hasNext()) {
                if (it.next().onStateReached(persistedInstallationEntry)) {
                    it.remove();
                }
            }
        }
    }

    private void triggerOnException(Exception exc) {
        synchronized (this.lock) {
            Iterator<StateListener> it = this.listeners.iterator();
            while (it.hasNext()) {
                if (it.next().onException(exc)) {
                    it.remove();
                }
            }
        }
    }

    private synchronized void updateCacheFid(String str) {
        this.cachedFid = str;
    }

    private synchronized String getCacheFid() {
        return this.cachedFid;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void doRegistrationOrRefresh(boolean z) {
        PersistedInstallationEntry prefsWithGeneratedIdMultiProcessSafe = getPrefsWithGeneratedIdMultiProcessSafe();
        if (z) {
            prefsWithGeneratedIdMultiProcessSafe = prefsWithGeneratedIdMultiProcessSafe.withClearedAuthToken();
        }
        triggerOnStateReached(prefsWithGeneratedIdMultiProcessSafe);
        this.networkExecutor.execute(FirebaseInstallations$$Lambda$4.lambdaFactory$(this, z));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doNetworkCallIfNecessary(boolean z) {
        PersistedInstallationEntry persistedInstallationEntryRegisterFidWithServer;
        PersistedInstallationEntry multiProcessSafePrefs = getMultiProcessSafePrefs();
        try {
            if (multiProcessSafePrefs.isErrored() || multiProcessSafePrefs.isUnregistered()) {
                persistedInstallationEntryRegisterFidWithServer = registerFidWithServer(multiProcessSafePrefs);
            } else {
                if (!z && !this.utils.isAuthTokenExpired(multiProcessSafePrefs)) {
                    return;
                }
                persistedInstallationEntryRegisterFidWithServer = fetchAuthTokenFromServer(multiProcessSafePrefs);
            }
            insertOrUpdatePrefs(persistedInstallationEntryRegisterFidWithServer);
            if (persistedInstallationEntryRegisterFidWithServer.isRegistered()) {
                updateCacheFid(persistedInstallationEntryRegisterFidWithServer.getFirebaseInstallationId());
            }
            if (persistedInstallationEntryRegisterFidWithServer.isErrored()) {
                triggerOnException(new FirebaseInstallationsException(FirebaseInstallationsException.Status.BAD_CONFIG));
            } else if (persistedInstallationEntryRegisterFidWithServer.isNotGenerated()) {
                triggerOnException(new IOException(AUTH_ERROR_MSG));
            } else {
                triggerOnStateReached(persistedInstallationEntryRegisterFidWithServer);
            }
        } catch (FirebaseInstallationsException e) {
            triggerOnException(e);
        }
    }

    private void insertOrUpdatePrefs(PersistedInstallationEntry persistedInstallationEntry) {
        synchronized (lockGenerateFid) {
            CrossProcessLock crossProcessLockAcquire = CrossProcessLock.acquire(this.firebaseApp.getApplicationContext(), LOCKFILE_NAME_GENERATE_FID);
            try {
                this.persistedInstallation.insertOrUpdatePersistedInstallationEntry(persistedInstallationEntry);
            } finally {
                if (crossProcessLockAcquire != null) {
                    crossProcessLockAcquire.releaseAndClose();
                }
            }
        }
    }

    private PersistedInstallationEntry getPrefsWithGeneratedIdMultiProcessSafe() {
        PersistedInstallationEntry persistedInstallationEntryValue;
        synchronized (lockGenerateFid) {
            CrossProcessLock crossProcessLockAcquire = CrossProcessLock.acquire(this.firebaseApp.getApplicationContext(), LOCKFILE_NAME_GENERATE_FID);
            try {
                persistedInstallationEntryValue = this.persistedInstallation.readPersistedInstallationEntryValue();
                if (persistedInstallationEntryValue.isNotGenerated()) {
                    persistedInstallationEntryValue = this.persistedInstallation.insertOrUpdatePersistedInstallationEntry(persistedInstallationEntryValue.withUnregisteredFid(readExistingIidOrCreateFid(persistedInstallationEntryValue)));
                }
            } finally {
                if (crossProcessLockAcquire != null) {
                    crossProcessLockAcquire.releaseAndClose();
                }
            }
        }
        return persistedInstallationEntryValue;
    }

    private String readExistingIidOrCreateFid(PersistedInstallationEntry persistedInstallationEntry) {
        if ((!this.firebaseApp.getName().equals(CHIME_FIREBASE_APP_NAME) && !this.firebaseApp.isDefaultApp()) || !persistedInstallationEntry.shouldAttemptMigration()) {
            return this.fidGenerator.createRandomFid();
        }
        String iid = this.iidStore.readIid();
        return TextUtils.isEmpty(iid) ? this.fidGenerator.createRandomFid() : iid;
    }

    private PersistedInstallationEntry registerFidWithServer(PersistedInstallationEntry persistedInstallationEntry) throws FirebaseInstallationsException {
        InstallationResponse installationResponseCreateFirebaseInstallation = this.serviceClient.createFirebaseInstallation(getApiKey(), persistedInstallationEntry.getFirebaseInstallationId(), getProjectIdentifier(), getApplicationId(), (persistedInstallationEntry.getFirebaseInstallationId() == null || persistedInstallationEntry.getFirebaseInstallationId().length() != 11) ? null : this.iidStore.readToken());
        int i = AnonymousClass2.$SwitchMap$com$google$firebase$installations$remote$InstallationResponse$ResponseCode[installationResponseCreateFirebaseInstallation.getResponseCode().ordinal()];
        if (i == 1) {
            return persistedInstallationEntry.withRegisteredFid(installationResponseCreateFirebaseInstallation.getFid(), installationResponseCreateFirebaseInstallation.getRefreshToken(), this.utils.currentTimeInSecs(), installationResponseCreateFirebaseInstallation.getAuthToken().getToken(), installationResponseCreateFirebaseInstallation.getAuthToken().getTokenExpirationTimestamp());
        }
        if (i == 2) {
            return persistedInstallationEntry.withFisError("BAD CONFIG");
        }
        throw new FirebaseInstallationsException("Firebase Installations Service is unavailable. Please try again later.", FirebaseInstallationsException.Status.UNAVAILABLE);
    }

    private PersistedInstallationEntry fetchAuthTokenFromServer(PersistedInstallationEntry persistedInstallationEntry) throws FirebaseInstallationsException {
        TokenResult tokenResultGenerateAuthToken = this.serviceClient.generateAuthToken(getApiKey(), persistedInstallationEntry.getFirebaseInstallationId(), getProjectIdentifier(), persistedInstallationEntry.getRefreshToken());
        int i = AnonymousClass2.$SwitchMap$com$google$firebase$installations$remote$TokenResult$ResponseCode[tokenResultGenerateAuthToken.getResponseCode().ordinal()];
        if (i == 1) {
            return persistedInstallationEntry.withAuthToken(tokenResultGenerateAuthToken.getToken(), tokenResultGenerateAuthToken.getTokenExpirationTimestamp(), this.utils.currentTimeInSecs());
        }
        if (i == 2) {
            return persistedInstallationEntry.withFisError("BAD CONFIG");
        }
        if (i == 3) {
            updateCacheFid(null);
            return persistedInstallationEntry.withNoGeneratedFid();
        }
        throw new FirebaseInstallationsException("Firebase Installations Service is unavailable. Please try again later.", FirebaseInstallationsException.Status.UNAVAILABLE);
    }

    /* renamed from: com.google.firebase.installations.FirebaseInstallations$2, reason: invalid class name */
    static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] $SwitchMap$com$google$firebase$installations$remote$InstallationResponse$ResponseCode;
        static final /* synthetic */ int[] $SwitchMap$com$google$firebase$installations$remote$TokenResult$ResponseCode;

        static {
            int[] iArr = new int[TokenResult.ResponseCode.values().length];
            $SwitchMap$com$google$firebase$installations$remote$TokenResult$ResponseCode = iArr;
            try {
                iArr[TokenResult.ResponseCode.OK.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$google$firebase$installations$remote$TokenResult$ResponseCode[TokenResult.ResponseCode.BAD_CONFIG.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$google$firebase$installations$remote$TokenResult$ResponseCode[TokenResult.ResponseCode.AUTH_ERROR.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            int[] iArr2 = new int[InstallationResponse.ResponseCode.values().length];
            $SwitchMap$com$google$firebase$installations$remote$InstallationResponse$ResponseCode = iArr2;
            try {
                iArr2[InstallationResponse.ResponseCode.OK.ordinal()] = 1;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$google$firebase$installations$remote$InstallationResponse$ResponseCode[InstallationResponse.ResponseCode.BAD_CONFIG.ordinal()] = 2;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Void deleteFirebaseInstallationId() throws FirebaseInstallationsException {
        updateCacheFid(null);
        PersistedInstallationEntry multiProcessSafePrefs = getMultiProcessSafePrefs();
        if (multiProcessSafePrefs.isRegistered()) {
            this.serviceClient.deleteFirebaseInstallation(getApiKey(), multiProcessSafePrefs.getFirebaseInstallationId(), getProjectIdentifier(), multiProcessSafePrefs.getRefreshToken());
        }
        insertOrUpdatePrefs(multiProcessSafePrefs.withNoGeneratedFid());
        return null;
    }

    private PersistedInstallationEntry getMultiProcessSafePrefs() {
        PersistedInstallationEntry persistedInstallationEntryValue;
        synchronized (lockGenerateFid) {
            CrossProcessLock crossProcessLockAcquire = CrossProcessLock.acquire(this.firebaseApp.getApplicationContext(), LOCKFILE_NAME_GENERATE_FID);
            try {
                persistedInstallationEntryValue = this.persistedInstallation.readPersistedInstallationEntryValue();
            } finally {
                if (crossProcessLockAcquire != null) {
                    crossProcessLockAcquire.releaseAndClose();
                }
            }
        }
        return persistedInstallationEntryValue;
    }
}
