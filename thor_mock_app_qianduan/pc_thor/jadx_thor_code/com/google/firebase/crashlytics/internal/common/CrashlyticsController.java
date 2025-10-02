package com.google.firebase.crashlytics.internal.common;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import com.google.android.exoplayer2.metadata.icy.IcyHeaders;
import com.google.android.exoplayer2.text.ttml.TtmlNode;
import com.google.android.gms.tasks.SuccessContinuation;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.crashlytics.internal.CrashlyticsNativeComponent;
import com.google.firebase.crashlytics.internal.Logger;
import com.google.firebase.crashlytics.internal.NativeSessionFileProvider;
import com.google.firebase.crashlytics.internal.analytics.AnalyticsEventLogger;
import com.google.firebase.crashlytics.internal.common.CrashlyticsUncaughtExceptionHandler;
import com.google.firebase.crashlytics.internal.log.LogFileManager;
import com.google.firebase.crashlytics.internal.network.HttpRequestFactory;
import com.google.firebase.crashlytics.internal.persistence.FileStore;
import com.google.firebase.crashlytics.internal.proto.ClsFileOutputStream;
import com.google.firebase.crashlytics.internal.proto.CodedOutputStream;
import com.google.firebase.crashlytics.internal.proto.SessionProtobufHelper;
import com.google.firebase.crashlytics.internal.report.ReportManager;
import com.google.firebase.crashlytics.internal.report.ReportUploader;
import com.google.firebase.crashlytics.internal.report.model.Report;
import com.google.firebase.crashlytics.internal.report.model.SessionReport;
import com.google.firebase.crashlytics.internal.report.network.CompositeCreateReportSpiCall;
import com.google.firebase.crashlytics.internal.report.network.CreateReportSpiCall;
import com.google.firebase.crashlytics.internal.report.network.DefaultCreateReportSpiCall;
import com.google.firebase.crashlytics.internal.report.network.NativeCreateReportSpiCall;
import com.google.firebase.crashlytics.internal.settings.SettingsDataProvider;
import com.google.firebase.crashlytics.internal.settings.model.AppSettingsData;
import com.google.firebase.crashlytics.internal.settings.model.Settings;
import com.google.firebase.crashlytics.internal.stacktrace.MiddleOutFallbackStrategy;
import com.google.firebase.crashlytics.internal.stacktrace.RemoveRepeatsStrategy;
import com.google.firebase.crashlytics.internal.stacktrace.StackTraceTrimmingStrategy;
import com.google.firebase.crashlytics.internal.stacktrace.TrimmedThrowableData;
import com.thor.app.utils.logs.loggers.FileLogger;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.lang.Thread;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: classes2.dex */
class CrashlyticsController {
    private static final int ANALYZER_VERSION = 1;
    static final String APP_EXCEPTION_MARKER_PREFIX = ".ae";
    private static final String COLLECT_CUSTOM_KEYS = "com.crashlytics.CollectCustomKeys";
    private static final String CRASHLYTICS_API_ENDPOINT = "com.crashlytics.ApiEndpoint";
    private static final String EVENT_TYPE_CRASH = "crash";
    private static final String EVENT_TYPE_LOGGED = "error";
    static final String FATAL_SESSION_DIR = "fatal-sessions";
    static final String FIREBASE_APPLICATION_EXCEPTION = "_ae";
    static final String FIREBASE_CRASH_TYPE = "fatal";
    static final int FIREBASE_CRASH_TYPE_FATAL = 1;
    static final String FIREBASE_TIMESTAMP = "timestamp";
    private static final String GENERATOR_FORMAT = "Crashlytics Android SDK/%s";
    private static final int MAX_CHAINED_EXCEPTION_DEPTH = 8;
    private static final int MAX_LOCAL_LOGGED_EXCEPTIONS = 64;
    static final int MAX_OPEN_SESSIONS = 8;
    static final int MAX_STACK_SIZE = 1024;
    static final String NATIVE_SESSION_DIR = "native-sessions";
    static final String NONFATAL_SESSION_DIR = "nonfatal-sessions";
    static final int NUM_STACK_REPETITIONS_ALLOWED = 10;
    static final String SESSION_EVENT_MISSING_BINARY_IMGS_TAG = "SessionMissingBinaryImages";
    static final String SESSION_FATAL_TAG = "SessionCrash";
    private static final int SESSION_ID_LENGTH = 35;
    static final String SESSION_NON_FATAL_TAG = "SessionEvent";
    private final AnalyticsEventLogger analyticsEventLogger;
    private final AppData appData;
    private final CrashlyticsBackgroundWorker backgroundWorker;
    private final Context context;
    private CrashlyticsUncaughtExceptionHandler crashHandler;
    private final CrashlyticsFileMarker crashMarker;
    private final DataCollectionArbiter dataCollectionArbiter;
    private final FileStore fileStore;
    private final ReportUploader.HandlingExceptionCheck handlingExceptionCheck;
    private final HttpRequestFactory httpRequestFactory;
    private final IdManager idManager;
    private final LogFileDirectoryProvider logFileDirectoryProvider;
    private final LogFileManager logFileManager;
    private final CrashlyticsNativeComponent nativeComponent;
    private final ReportManager reportManager;
    private final ReportUploader.Provider reportUploaderProvider;
    private final SessionReportingCoordinator reportingCoordinator;
    private final StackTraceTrimmingStrategy stackTraceTrimmingStrategy;
    private final String unityVersion;
    private final UserMetadata userMetadata;
    static final String SESSION_BEGIN_TAG = "BeginSession";
    static final FilenameFilter SESSION_BEGIN_FILE_FILTER = new FileNameContainsFilter(SESSION_BEGIN_TAG) { // from class: com.google.firebase.crashlytics.internal.common.CrashlyticsController.1
        @Override // com.google.firebase.crashlytics.internal.common.CrashlyticsController.FileNameContainsFilter, java.io.FilenameFilter
        public boolean accept(File file, String str) {
            return super.accept(file, str) && str.endsWith(ClsFileOutputStream.SESSION_FILE_EXTENSION);
        }
    };
    static final FilenameFilter APP_EXCEPTION_MARKER_FILTER = CrashlyticsController$$Lambda$1.instance;
    static final FilenameFilter SESSION_FILE_FILTER = new FilenameFilter() { // from class: com.google.firebase.crashlytics.internal.common.CrashlyticsController.2
        @Override // java.io.FilenameFilter
        public boolean accept(File file, String str) {
            return str.length() == 39 && str.endsWith(ClsFileOutputStream.SESSION_FILE_EXTENSION);
        }
    };
    static final Comparator<File> LARGEST_FILE_NAME_FIRST = new Comparator<File>() { // from class: com.google.firebase.crashlytics.internal.common.CrashlyticsController.3
        @Override // java.util.Comparator
        public int compare(File file, File file2) {
            return file2.getName().compareTo(file.getName());
        }
    };
    static final Comparator<File> SMALLEST_FILE_NAME_FIRST = new Comparator<File>() { // from class: com.google.firebase.crashlytics.internal.common.CrashlyticsController.4
        @Override // java.util.Comparator
        public int compare(File file, File file2) {
            return file.getName().compareTo(file2.getName());
        }
    };
    private static final Pattern SESSION_FILE_PATTERN = Pattern.compile("([\\d|A-Z|a-z]{12}\\-[\\d|A-Z|a-z]{4}\\-[\\d|A-Z|a-z]{4}\\-[\\d|A-Z|a-z]{12}).+");
    private static final Map<String, String> SEND_AT_CRASHTIME_HEADER = Collections.singletonMap("X-CRASHLYTICS-SEND-FLAGS", IcyHeaders.REQUEST_HEADER_ENABLE_METADATA_VALUE);
    static final String SESSION_USER_TAG = "SessionUser";
    static final String SESSION_APP_TAG = "SessionApp";
    static final String SESSION_OS_TAG = "SessionOS";
    static final String SESSION_DEVICE_TAG = "SessionDevice";
    private static final String[] INITIAL_SESSION_PART_TAGS = {SESSION_USER_TAG, SESSION_APP_TAG, SESSION_OS_TAG, SESSION_DEVICE_TAG};
    private final AtomicInteger eventCounter = new AtomicInteger(0);
    TaskCompletionSource<Boolean> unsentReportsAvailable = new TaskCompletionSource<>();
    TaskCompletionSource<Boolean> reportActionProvided = new TaskCompletionSource<>();
    TaskCompletionSource<Void> unsentReportsHandled = new TaskCompletionSource<>();
    AtomicBoolean checkForUnsentReportsCalled = new AtomicBoolean(false);

    private interface CodedOutputStreamWriteAction {
        void writeTo(CodedOutputStream codedOutputStream) throws Exception;
    }

    private static File[] ensureFileArrayNotNull(File[] fileArr) {
        return fileArr == null ? new File[0] : fileArr;
    }

    static class FileNameContainsFilter implements FilenameFilter {
        private final String string;

        public FileNameContainsFilter(String str) {
            this.string = str;
        }

        @Override // java.io.FilenameFilter
        public boolean accept(File file, String str) {
            return str.contains(this.string) && !str.endsWith(ClsFileOutputStream.IN_PROGRESS_SESSION_FILE_EXTENSION);
        }
    }

    static class SessionPartFileFilter implements FilenameFilter {
        private final String sessionId;

        public SessionPartFileFilter(String str) {
            this.sessionId = str;
        }

        @Override // java.io.FilenameFilter
        public boolean accept(File file, String str) {
            return (str.equals(new StringBuilder().append(this.sessionId).append(ClsFileOutputStream.SESSION_FILE_EXTENSION).toString()) || !str.contains(this.sessionId) || str.endsWith(ClsFileOutputStream.IN_PROGRESS_SESSION_FILE_EXTENSION)) ? false : true;
        }
    }

    private static class AnySessionPartFileFilter implements FilenameFilter {
        private AnySessionPartFileFilter() {
        }

        @Override // java.io.FilenameFilter
        public boolean accept(File file, String str) {
            return !CrashlyticsController.SESSION_FILE_FILTER.accept(file, str) && CrashlyticsController.SESSION_FILE_PATTERN.matcher(str).matches();
        }
    }

    static class InvalidPartFileFilter implements FilenameFilter {
        InvalidPartFileFilter() {
        }

        @Override // java.io.FilenameFilter
        public boolean accept(File file, String str) {
            return ClsFileOutputStream.TEMP_FILENAME_FILTER.accept(file, str) || str.contains(CrashlyticsController.SESSION_EVENT_MISSING_BINARY_IMGS_TAG);
        }
    }

    CrashlyticsController(Context context, CrashlyticsBackgroundWorker crashlyticsBackgroundWorker, HttpRequestFactory httpRequestFactory, IdManager idManager, DataCollectionArbiter dataCollectionArbiter, FileStore fileStore, CrashlyticsFileMarker crashlyticsFileMarker, AppData appData, ReportManager reportManager, ReportUploader.Provider provider, CrashlyticsNativeComponent crashlyticsNativeComponent, AnalyticsEventLogger analyticsEventLogger, SettingsDataProvider settingsDataProvider) {
        this.context = context;
        this.backgroundWorker = crashlyticsBackgroundWorker;
        this.httpRequestFactory = httpRequestFactory;
        this.idManager = idManager;
        this.dataCollectionArbiter = dataCollectionArbiter;
        this.fileStore = fileStore;
        this.crashMarker = crashlyticsFileMarker;
        this.appData = appData;
        if (provider != null) {
            this.reportUploaderProvider = provider;
        } else {
            this.reportUploaderProvider = defaultReportUploader();
        }
        this.nativeComponent = crashlyticsNativeComponent;
        this.unityVersion = appData.unityVersionProvider.getUnityVersion();
        this.analyticsEventLogger = analyticsEventLogger;
        UserMetadata userMetadata = new UserMetadata();
        this.userMetadata = userMetadata;
        LogFileDirectoryProvider logFileDirectoryProvider = new LogFileDirectoryProvider(fileStore);
        this.logFileDirectoryProvider = logFileDirectoryProvider;
        LogFileManager logFileManager = new LogFileManager(context, logFileDirectoryProvider);
        this.logFileManager = logFileManager;
        this.reportManager = reportManager == null ? new ReportManager(new ReportUploaderFilesProvider()) : reportManager;
        this.handlingExceptionCheck = new ReportUploaderHandlingExceptionCheck();
        MiddleOutFallbackStrategy middleOutFallbackStrategy = new MiddleOutFallbackStrategy(1024, new RemoveRepeatsStrategy(10));
        this.stackTraceTrimmingStrategy = middleOutFallbackStrategy;
        this.reportingCoordinator = SessionReportingCoordinator.create(context, idManager, fileStore, appData, logFileManager, userMetadata, middleOutFallbackStrategy, settingsDataProvider);
    }

    private Context getContext() {
        return this.context;
    }

    void enableExceptionHandling(Thread.UncaughtExceptionHandler uncaughtExceptionHandler, SettingsDataProvider settingsDataProvider) {
        openSession();
        CrashlyticsUncaughtExceptionHandler crashlyticsUncaughtExceptionHandler = new CrashlyticsUncaughtExceptionHandler(new CrashlyticsUncaughtExceptionHandler.CrashListener() { // from class: com.google.firebase.crashlytics.internal.common.CrashlyticsController.5
            @Override // com.google.firebase.crashlytics.internal.common.CrashlyticsUncaughtExceptionHandler.CrashListener
            public void onUncaughtException(SettingsDataProvider settingsDataProvider2, Thread thread, Throwable th) {
                CrashlyticsController.this.handleUncaughtException(settingsDataProvider2, thread, th);
            }
        }, settingsDataProvider, uncaughtExceptionHandler);
        this.crashHandler = crashlyticsUncaughtExceptionHandler;
        Thread.setDefaultUncaughtExceptionHandler(crashlyticsUncaughtExceptionHandler);
    }

    synchronized void handleUncaughtException(final SettingsDataProvider settingsDataProvider, final Thread thread, final Throwable th) {
        Logger.getLogger().d("Crashlytics is handling uncaught exception \"" + th + "\" from thread " + thread.getName());
        final Date date = new Date();
        try {
            Utils.awaitEvenIfOnMainThread(this.backgroundWorker.submitTask(new Callable<Task<Void>>() { // from class: com.google.firebase.crashlytics.internal.common.CrashlyticsController.6
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // java.util.concurrent.Callable
                public Task<Void> call() throws Exception {
                    long timestampSeconds = CrashlyticsController.getTimestampSeconds(date);
                    String currentSessionId = CrashlyticsController.this.getCurrentSessionId();
                    if (currentSessionId != null) {
                        CrashlyticsController.this.crashMarker.create();
                        CrashlyticsController.this.reportingCoordinator.persistFatalEvent(th, thread, CrashlyticsController.makeFirebaseSessionIdentifier(currentSessionId), timestampSeconds);
                        CrashlyticsController.this.doWriteFatal(thread, th, currentSessionId, timestampSeconds);
                        CrashlyticsController.this.doWriteAppExceptionMarker(date.getTime());
                        Settings settings = settingsDataProvider.getSettings();
                        int i = settings.getSessionData().maxCustomExceptionEvents;
                        int i2 = settings.getSessionData().maxCompleteSessionsCount;
                        CrashlyticsController.this.doCloseSessions(i);
                        CrashlyticsController.this.doOpenSession();
                        CrashlyticsController.this.trimSessionFiles(i2);
                        if (CrashlyticsController.this.dataCollectionArbiter.isAutomaticDataCollectionEnabled()) {
                            final Executor executor = CrashlyticsController.this.backgroundWorker.getExecutor();
                            return settingsDataProvider.getAppSettings().onSuccessTask(executor, new SuccessContinuation<AppSettingsData, Void>() { // from class: com.google.firebase.crashlytics.internal.common.CrashlyticsController.6.1
                                @Override // com.google.android.gms.tasks.SuccessContinuation
                                public Task<Void> then(AppSettingsData appSettingsData) throws Exception {
                                    if (appSettingsData != null) {
                                        CrashlyticsController.this.sendSessionReports(appSettingsData, true);
                                        return Tasks.whenAll((Task<?>[]) new Task[]{CrashlyticsController.this.logAnalyticsAppExceptionEvents(), CrashlyticsController.this.reportingCoordinator.sendReports(executor, DataTransportState.getState(appSettingsData))});
                                    }
                                    Logger.getLogger().w("Received null app settings, cannot send reports at crash time.");
                                    return Tasks.forResult(null);
                                }
                            });
                        }
                        return Tasks.forResult(null);
                    }
                    Logger.getLogger().e("Tried to write a fatal exception while no session was open.");
                    return Tasks.forResult(null);
                }
            }));
        } catch (Exception unused) {
        }
    }

    private Task<Boolean> waitForReportAction() {
        if (this.dataCollectionArbiter.isAutomaticDataCollectionEnabled()) {
            Logger.getLogger().d("Automatic data collection is enabled. Allowing upload.");
            this.unsentReportsAvailable.trySetResult(false);
            return Tasks.forResult(true);
        }
        Logger.getLogger().d("Automatic data collection is disabled.");
        Logger.getLogger().d("Notifying that unsent reports are available.");
        this.unsentReportsAvailable.trySetResult(true);
        Task<TContinuationResult> taskOnSuccessTask = this.dataCollectionArbiter.waitForAutomaticDataCollectionEnabled().onSuccessTask(new SuccessContinuation<Void, Boolean>() { // from class: com.google.firebase.crashlytics.internal.common.CrashlyticsController.7
            @Override // com.google.android.gms.tasks.SuccessContinuation
            public Task<Boolean> then(Void r1) throws Exception {
                return Tasks.forResult(true);
            }
        });
        Logger.getLogger().d("Waiting for send/deleteUnsentReports to be called.");
        return Utils.race(taskOnSuccessTask, this.reportActionProvided.getTask());
    }

    boolean didCrashOnPreviousExecution() {
        if (!this.crashMarker.isPresent()) {
            String currentSessionId = getCurrentSessionId();
            return currentSessionId != null && this.nativeComponent.hasCrashDataForSession(currentSessionId);
        }
        Logger.getLogger().d("Found previous crash marker.");
        this.crashMarker.remove();
        return Boolean.TRUE.booleanValue();
    }

    Task<Boolean> checkForUnsentReports() {
        if (!this.checkForUnsentReportsCalled.compareAndSet(false, true)) {
            Logger.getLogger().d("checkForUnsentReports should only be called once per execution.");
            return Tasks.forResult(false);
        }
        return this.unsentReportsAvailable.getTask();
    }

    Task<Void> sendUnsentReports() {
        this.reportActionProvided.trySetResult(true);
        return this.unsentReportsHandled.getTask();
    }

    Task<Void> deleteUnsentReports() {
        this.reportActionProvided.trySetResult(false);
        return this.unsentReportsHandled.getTask();
    }

    Task<Void> submitAllReports(float f, Task<AppSettingsData> task) {
        if (!this.reportManager.areReportsAvailable()) {
            Logger.getLogger().d("No reports are available.");
            this.unsentReportsAvailable.trySetResult(false);
            return Tasks.forResult(null);
        }
        Logger.getLogger().d("Unsent reports are available.");
        return waitForReportAction().onSuccessTask(new AnonymousClass8(task, f));
    }

    /* renamed from: com.google.firebase.crashlytics.internal.common.CrashlyticsController$8, reason: invalid class name */
    class AnonymousClass8 implements SuccessContinuation<Boolean, Void> {
        final /* synthetic */ Task val$appSettingsDataTask;
        final /* synthetic */ float val$delay;

        AnonymousClass8(Task task, float f) {
            this.val$appSettingsDataTask = task;
            this.val$delay = f;
        }

        @Override // com.google.android.gms.tasks.SuccessContinuation
        public Task<Void> then(final Boolean bool) throws Exception {
            return CrashlyticsController.this.backgroundWorker.submitTask(new Callable<Task<Void>>() { // from class: com.google.firebase.crashlytics.internal.common.CrashlyticsController.8.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // java.util.concurrent.Callable
                public Task<Void> call() throws Exception {
                    final List<Report> listFindReports = CrashlyticsController.this.reportManager.findReports();
                    if (!bool.booleanValue()) {
                        Logger.getLogger().d("Reports are being deleted.");
                        CrashlyticsController.deleteFiles(CrashlyticsController.this.listAppExceptionMarkerFiles());
                        CrashlyticsController.this.reportManager.deleteReports(listFindReports);
                        CrashlyticsController.this.reportingCoordinator.removeAllReports();
                        CrashlyticsController.this.unsentReportsHandled.trySetResult(null);
                        return Tasks.forResult(null);
                    }
                    Logger.getLogger().d("Reports are being sent.");
                    final boolean zBooleanValue = bool.booleanValue();
                    CrashlyticsController.this.dataCollectionArbiter.grantDataCollectionPermission(zBooleanValue);
                    final Executor executor = CrashlyticsController.this.backgroundWorker.getExecutor();
                    return AnonymousClass8.this.val$appSettingsDataTask.onSuccessTask(executor, new SuccessContinuation<AppSettingsData, Void>() { // from class: com.google.firebase.crashlytics.internal.common.CrashlyticsController.8.1.1
                        @Override // com.google.android.gms.tasks.SuccessContinuation
                        public Task<Void> then(AppSettingsData appSettingsData) throws Exception {
                            if (appSettingsData == null) {
                                Logger.getLogger().w("Received null app settings, cannot send reports during app startup.");
                                return Tasks.forResult(null);
                            }
                            for (Report report : listFindReports) {
                                if (report.getType() == Report.Type.JAVA) {
                                    CrashlyticsController.appendOrganizationIdToSessionFile(appSettingsData.organizationId, report.getFile());
                                }
                            }
                            CrashlyticsController.this.logAnalyticsAppExceptionEvents();
                            CrashlyticsController.this.reportUploaderProvider.createReportUploader(appSettingsData).uploadReportsAsync(listFindReports, zBooleanValue, AnonymousClass8.this.val$delay);
                            CrashlyticsController.this.reportingCoordinator.sendReports(executor, DataTransportState.getState(appSettingsData));
                            CrashlyticsController.this.unsentReportsHandled.trySetResult(null);
                            return Tasks.forResult(null);
                        }
                    });
                }
            });
        }
    }

    private ReportUploader.Provider defaultReportUploader() {
        return new ReportUploader.Provider() { // from class: com.google.firebase.crashlytics.internal.common.CrashlyticsController.9
            @Override // com.google.firebase.crashlytics.internal.report.ReportUploader.Provider
            public ReportUploader createReportUploader(AppSettingsData appSettingsData) {
                String str = appSettingsData.reportsUrl;
                String str2 = appSettingsData.ndkReportsUrl;
                return new ReportUploader(appSettingsData.organizationId, CrashlyticsController.this.appData.googleAppId, DataTransportState.getState(appSettingsData), CrashlyticsController.this.reportManager, CrashlyticsController.this.getCreateReportSpiCall(str, str2), CrashlyticsController.this.handlingExceptionCheck);
            }
        };
    }

    void writeToLog(final long j, final String str) {
        this.backgroundWorker.submit(new Callable<Void>() { // from class: com.google.firebase.crashlytics.internal.common.CrashlyticsController.10
            @Override // java.util.concurrent.Callable
            public Void call() throws Exception {
                if (CrashlyticsController.this.isHandlingException()) {
                    return null;
                }
                CrashlyticsController.this.logFileManager.writeToLog(j, str);
                return null;
            }
        });
    }

    void writeNonFatalException(final Thread thread, final Throwable th) {
        final Date date = new Date();
        this.backgroundWorker.submit(new Runnable() { // from class: com.google.firebase.crashlytics.internal.common.CrashlyticsController.11
            @Override // java.lang.Runnable
            public void run() throws Throwable {
                if (CrashlyticsController.this.isHandlingException()) {
                    return;
                }
                long timestampSeconds = CrashlyticsController.getTimestampSeconds(date);
                String currentSessionId = CrashlyticsController.this.getCurrentSessionId();
                if (currentSessionId == null) {
                    Logger.getLogger().d("Tried to write a non-fatal exception while no session was open.");
                } else {
                    CrashlyticsController.this.reportingCoordinator.persistNonFatalEvent(th, thread, CrashlyticsController.makeFirebaseSessionIdentifier(currentSessionId), timestampSeconds);
                    CrashlyticsController.this.doWriteNonFatal(thread, th, currentSessionId, timestampSeconds);
                }
            }
        });
    }

    void setUserId(String str) {
        this.userMetadata.setUserId(str);
        cacheUserData(this.userMetadata);
    }

    void setCustomKey(String str, String str2) {
        try {
            this.userMetadata.setCustomKey(str, str2);
            cacheKeyData(this.userMetadata.getCustomKeys());
        } catch (IllegalArgumentException e) {
            Context context = this.context;
            if (context != null && CommonUtils.isAppDebuggable(context)) {
                throw e;
            }
            Logger.getLogger().e("Attempting to set custom attribute with null key, ignoring.");
        }
    }

    private void cacheUserData(final UserMetadata userMetadata) {
        this.backgroundWorker.submit(new Callable<Void>() { // from class: com.google.firebase.crashlytics.internal.common.CrashlyticsController.12
            @Override // java.util.concurrent.Callable
            public Void call() throws Exception {
                String currentSessionId = CrashlyticsController.this.getCurrentSessionId();
                if (currentSessionId != null) {
                    CrashlyticsController.this.reportingCoordinator.persistUserId(CrashlyticsController.makeFirebaseSessionIdentifier(currentSessionId));
                    new MetaDataStore(CrashlyticsController.this.getFilesDir()).writeUserData(currentSessionId, userMetadata);
                    return null;
                }
                Logger.getLogger().d("Tried to cache user data while no session was open.");
                return null;
            }
        });
    }

    private void cacheKeyData(final Map<String, String> map) {
        this.backgroundWorker.submit(new Callable<Void>() { // from class: com.google.firebase.crashlytics.internal.common.CrashlyticsController.13
            @Override // java.util.concurrent.Callable
            public Void call() throws Exception {
                new MetaDataStore(CrashlyticsController.this.getFilesDir()).writeKeyData(CrashlyticsController.this.getCurrentSessionId(), map);
                return null;
            }
        });
    }

    void openSession() {
        this.backgroundWorker.submit(new Callable<Void>() { // from class: com.google.firebase.crashlytics.internal.common.CrashlyticsController.14
            @Override // java.util.concurrent.Callable
            public Void call() throws Exception {
                CrashlyticsController.this.doOpenSession();
                return null;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String getCurrentSessionId() {
        File[] fileArrListSortedSessionBeginFiles = listSortedSessionBeginFiles();
        if (fileArrListSortedSessionBeginFiles.length > 0) {
            return getSessionIdFromSessionFile(fileArrListSortedSessionBeginFiles[0]);
        }
        return null;
    }

    static String getSessionIdFromSessionFile(File file) {
        return file.getName().substring(0, 35);
    }

    boolean hasOpenSession() {
        return listSessionBeginFiles().length > 0;
    }

    boolean finalizeSessions(int i) {
        this.backgroundWorker.checkRunningOnThread();
        if (isHandlingException()) {
            Logger.getLogger().d("Skipping session finalization because a crash has already occurred.");
            return Boolean.FALSE.booleanValue();
        }
        Logger.getLogger().d("Finalizing previously open sessions.");
        try {
            doCloseSessions(i, true);
            Logger.getLogger().d("Closed all previously open sessions");
            return true;
        } catch (Exception e) {
            Logger.getLogger().e("Unable to finalize previously open sessions.", e);
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doOpenSession() throws Exception {
        long currentTimestampSeconds = getCurrentTimestampSeconds();
        String string = new CLSUUID(this.idManager).toString();
        Logger.getLogger().d("Opening a new session with ID " + string);
        this.nativeComponent.openSession(string);
        writeBeginSession(string, currentTimestampSeconds);
        writeSessionApp(string);
        writeSessionOS(string);
        writeSessionDevice(string);
        this.logFileManager.setCurrentSession(string);
        this.reportingCoordinator.onBeginSession(makeFirebaseSessionIdentifier(string), currentTimestampSeconds);
    }

    void doCloseSessions(int i) throws Exception {
        doCloseSessions(i, false);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void doCloseSessions(int i, boolean z) throws Exception {
        trimOpenSessions((z ? 1 : 0) + 8);
        File[] fileArrListSortedSessionBeginFiles = listSortedSessionBeginFiles();
        if (fileArrListSortedSessionBeginFiles.length <= z) {
            Logger.getLogger().d("No open sessions to be closed.");
            return;
        }
        String sessionIdFromSessionFile = getSessionIdFromSessionFile(fileArrListSortedSessionBeginFiles[z ? 1 : 0]);
        writeSessionUser(sessionIdFromSessionFile);
        if (this.nativeComponent.hasCrashDataForSession(sessionIdFromSessionFile)) {
            finalizePreviousNativeSession(sessionIdFromSessionFile);
            if (!this.nativeComponent.finalizeSession(sessionIdFromSessionFile)) {
                Logger.getLogger().d("Could not finalize native session: " + sessionIdFromSessionFile);
            }
        }
        closeOpenSessions(fileArrListSortedSessionBeginFiles, z ? 1 : 0, i);
        this.reportingCoordinator.finalizeSessions(getCurrentTimestampSeconds(), z != 0 ? makeFirebaseSessionIdentifier(getSessionIdFromSessionFile(fileArrListSortedSessionBeginFiles[0])) : null);
    }

    private void closeOpenSessions(File[] fileArr, int i, int i2) throws Throwable {
        Logger.getLogger().d("Closing open sessions.");
        while (i < fileArr.length) {
            File file = fileArr[i];
            String sessionIdFromSessionFile = getSessionIdFromSessionFile(file);
            Logger.getLogger().d("Closing session: " + sessionIdFromSessionFile);
            writeSessionPartsToSessionFile(file, sessionIdFromSessionFile, i2);
            i++;
        }
    }

    private void closeWithoutRenamingOrLog(ClsFileOutputStream clsFileOutputStream) {
        if (clsFileOutputStream == null) {
            return;
        }
        try {
            clsFileOutputStream.closeInProgressStream();
        } catch (IOException e) {
            Logger.getLogger().e("Error closing session file stream in the presence of an exception", e);
        }
    }

    private File[] listSessionPartFilesFor(String str) {
        return listFilesMatching(new SessionPartFileFilter(str));
    }

    File[] listCompleteSessionFiles() {
        LinkedList linkedList = new LinkedList();
        File fatalSessionFilesDir = getFatalSessionFilesDir();
        FilenameFilter filenameFilter = SESSION_FILE_FILTER;
        Collections.addAll(linkedList, listFilesMatching(fatalSessionFilesDir, filenameFilter));
        Collections.addAll(linkedList, listFilesMatching(getNonFatalSessionFilesDir(), filenameFilter));
        Collections.addAll(linkedList, listFilesMatching(getFilesDir(), filenameFilter));
        return (File[]) linkedList.toArray(new File[linkedList.size()]);
    }

    File[] listNativeSessionFileDirectories() {
        return ensureFileArrayNotNull(getNativeSessionFilesDir().listFiles());
    }

    File[] listSessionBeginFiles() {
        return listFilesMatching(SESSION_BEGIN_FILE_FILTER);
    }

    File[] listAppExceptionMarkerFiles() {
        return listFilesMatching(APP_EXCEPTION_MARKER_FILTER);
    }

    private File[] listSortedSessionBeginFiles() {
        File[] fileArrListSessionBeginFiles = listSessionBeginFiles();
        Arrays.sort(fileArrListSessionBeginFiles, LARGEST_FILE_NAME_FIRST);
        return fileArrListSessionBeginFiles;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public File[] listFilesMatching(FilenameFilter filenameFilter) {
        return listFilesMatching(getFilesDir(), filenameFilter);
    }

    private static File[] listFilesMatching(File file, FilenameFilter filenameFilter) {
        return ensureFileArrayNotNull(file.listFiles(filenameFilter));
    }

    private void trimSessionEventFiles(String str, int i) {
        Utils.capFileCount(getFilesDir(), new FileNameContainsFilter(str + SESSION_NON_FATAL_TAG), i, SMALLEST_FILE_NAME_FIRST);
    }

    void trimSessionFiles(int i) {
        File nativeSessionFilesDir = getNativeSessionFilesDir();
        File fatalSessionFilesDir = getFatalSessionFilesDir();
        Comparator<File> comparator = SMALLEST_FILE_NAME_FIRST;
        int iCapSessionCount = i - Utils.capSessionCount(nativeSessionFilesDir, fatalSessionFilesDir, i, comparator);
        Utils.capFileCount(getFilesDir(), SESSION_FILE_FILTER, iCapSessionCount - Utils.capFileCount(getNonFatalSessionFilesDir(), iCapSessionCount, comparator), comparator);
    }

    private void trimOpenSessions(int i) {
        HashSet hashSet = new HashSet();
        File[] fileArrListSortedSessionBeginFiles = listSortedSessionBeginFiles();
        int iMin = Math.min(i, fileArrListSortedSessionBeginFiles.length);
        for (int i2 = 0; i2 < iMin; i2++) {
            hashSet.add(getSessionIdFromSessionFile(fileArrListSortedSessionBeginFiles[i2]));
        }
        this.logFileManager.discardOldLogFiles(hashSet);
        retainSessions(listFilesMatching(new AnySessionPartFileFilter()), hashSet);
    }

    private void retainSessions(File[] fileArr, Set<String> set) {
        for (File file : fileArr) {
            String name = file.getName();
            Matcher matcher = SESSION_FILE_PATTERN.matcher(name);
            if (!matcher.matches()) {
                Logger.getLogger().d("Deleting unknown file: " + name);
                file.delete();
            } else if (!set.contains(matcher.group(1))) {
                Logger.getLogger().d("Trimming session file: " + name);
                file.delete();
            }
        }
    }

    private File[] getTrimmedNonFatalFiles(String str, File[] fileArr, int i) {
        if (fileArr.length <= i) {
            return fileArr;
        }
        Logger.getLogger().d(String.format(Locale.US, "Trimming down to %d logged exceptions.", Integer.valueOf(i)));
        trimSessionEventFiles(str, i);
        return listFilesMatching(new FileNameContainsFilter(str + SESSION_NON_FATAL_TAG));
    }

    void cleanInvalidTempFiles() {
        this.backgroundWorker.submit(new Runnable() { // from class: com.google.firebase.crashlytics.internal.common.CrashlyticsController.15
            @Override // java.lang.Runnable
            public void run() {
                CrashlyticsController crashlyticsController = CrashlyticsController.this;
                crashlyticsController.doCleanInvalidTempFiles(crashlyticsController.listFilesMatching(new InvalidPartFileFilter()));
            }
        });
    }

    void doCleanInvalidTempFiles(File[] fileArr) {
        final HashSet hashSet = new HashSet();
        for (File file : fileArr) {
            Logger.getLogger().d("Found invalid session part file: " + file);
            hashSet.add(getSessionIdFromSessionFile(file));
        }
        if (hashSet.isEmpty()) {
            return;
        }
        for (File file2 : listFilesMatching(new FilenameFilter() { // from class: com.google.firebase.crashlytics.internal.common.CrashlyticsController.16
            @Override // java.io.FilenameFilter
            public boolean accept(File file3, String str) {
                if (str.length() < 35) {
                    return false;
                }
                return hashSet.contains(str.substring(0, 35));
            }
        })) {
            Logger.getLogger().d("Deleting invalid session file: " + file2);
            file2.delete();
        }
    }

    private void finalizePreviousNativeSession(String str) throws IOException {
        Logger.getLogger().d("Finalizing native report for session " + str);
        NativeSessionFileProvider sessionFileProvider = this.nativeComponent.getSessionFileProvider(str);
        File minidumpFile = sessionFileProvider.getMinidumpFile();
        if (minidumpFile == null || !minidumpFile.exists()) {
            Logger.getLogger().w("No minidump data found for session " + str);
            return;
        }
        long jLastModified = minidumpFile.lastModified();
        LogFileManager logFileManager = new LogFileManager(this.context, this.logFileDirectoryProvider, str);
        File file = new File(getNativeSessionFilesDir(), str);
        if (!file.mkdirs()) {
            Logger.getLogger().d("Couldn't create native sessions directory");
            return;
        }
        doWriteAppExceptionMarker(jLastModified);
        List<NativeSessionFile> nativeSessionFiles = getNativeSessionFiles(sessionFileProvider, str, getContext(), getFilesDir(), logFileManager.getBytesForLog());
        NativeSessionFileGzipper.processNativeSessions(file, nativeSessionFiles);
        this.reportingCoordinator.finalizeSessionWithNativeEvent(makeFirebaseSessionIdentifier(str), nativeSessionFiles);
        logFileManager.clearLog();
    }

    private static long getCurrentTimestampSeconds() {
        return getTimestampSeconds(new Date());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static long getTimestampSeconds(Date date) {
        return date.getTime() / 1000;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String makeFirebaseSessionIdentifier(String str) {
        return str.replaceAll("-", "");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doWriteFatal(Thread thread, Throwable th, String str, long j) throws Throwable {
        ClsFileOutputStream clsFileOutputStream;
        CodedOutputStream codedOutputStreamNewInstance = null;
        try {
            clsFileOutputStream = new ClsFileOutputStream(getFilesDir(), str + SESSION_FATAL_TAG);
            try {
                try {
                    codedOutputStreamNewInstance = CodedOutputStream.newInstance(clsFileOutputStream);
                    writeSessionEvent(codedOutputStreamNewInstance, thread, th, j, "crash", true);
                } catch (Exception e) {
                    e = e;
                    Logger.getLogger().e("An error occurred in the fatal exception logger", e);
                    CommonUtils.flushOrLog(codedOutputStreamNewInstance, "Failed to flush to session begin file.");
                    CommonUtils.closeOrLog(clsFileOutputStream, "Failed to close fatal exception file output stream.");
                }
            } catch (Throwable th2) {
                th = th2;
                CommonUtils.flushOrLog(codedOutputStreamNewInstance, "Failed to flush to session begin file.");
                CommonUtils.closeOrLog(clsFileOutputStream, "Failed to close fatal exception file output stream.");
                throw th;
            }
        } catch (Exception e2) {
            e = e2;
            clsFileOutputStream = null;
        } catch (Throwable th3) {
            th = th3;
            clsFileOutputStream = null;
            CommonUtils.flushOrLog(codedOutputStreamNewInstance, "Failed to flush to session begin file.");
            CommonUtils.closeOrLog(clsFileOutputStream, "Failed to close fatal exception file output stream.");
            throw th;
        }
        CommonUtils.flushOrLog(codedOutputStreamNewInstance, "Failed to flush to session begin file.");
        CommonUtils.closeOrLog(clsFileOutputStream, "Failed to close fatal exception file output stream.");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doWriteAppExceptionMarker(long j) throws IOException {
        try {
            new File(getFilesDir(), APP_EXCEPTION_MARKER_PREFIX + j).createNewFile();
        } catch (IOException unused) {
            Logger.getLogger().d("Could not write app exception marker.");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doWriteNonFatal(Thread thread, Throwable th, String str, long j) throws Throwable {
        ClsFileOutputStream clsFileOutputStream;
        CodedOutputStream codedOutputStreamNewInstance;
        CodedOutputStream codedOutputStream = null;
        codedOutputStream = null;
        CodedOutputStream codedOutputStream2 = null;
        codedOutputStream = null;
        try {
            try {
                Logger.getLogger().d("Crashlytics is logging non-fatal exception \"" + th + "\" from thread " + thread.getName());
                clsFileOutputStream = new ClsFileOutputStream(getFilesDir(), str + SESSION_NON_FATAL_TAG + CommonUtils.padWithZerosToMaxIntWidth(this.eventCounter.getAndIncrement()));
                try {
                    codedOutputStreamNewInstance = CodedOutputStream.newInstance(clsFileOutputStream);
                } catch (Exception e) {
                    e = e;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (Exception e2) {
            e = e2;
            clsFileOutputStream = null;
        } catch (Throwable th3) {
            th = th3;
            clsFileOutputStream = null;
        }
        try {
            try {
                CrashlyticsController crashlyticsController = this;
                crashlyticsController.writeSessionEvent(codedOutputStreamNewInstance, thread, th, j, "error", false);
                CommonUtils.flushOrLog(codedOutputStreamNewInstance, "Failed to flush to non-fatal file.");
                codedOutputStream = crashlyticsController;
            } catch (Exception e3) {
                e = e3;
                codedOutputStream2 = codedOutputStreamNewInstance;
                Logger.getLogger().e("An error occurred in the non-fatal exception logger", e);
                CommonUtils.flushOrLog(codedOutputStream2, "Failed to flush to non-fatal file.");
                codedOutputStream = codedOutputStream2;
                CommonUtils.closeOrLog(clsFileOutputStream, "Failed to close non-fatal file output stream.");
                trimSessionEventFiles(str, 64);
                return;
            } catch (Throwable th4) {
                th = th4;
                codedOutputStream = codedOutputStreamNewInstance;
                CommonUtils.flushOrLog(codedOutputStream, "Failed to flush to non-fatal file.");
                CommonUtils.closeOrLog(clsFileOutputStream, "Failed to close non-fatal file output stream.");
                throw th;
            }
            trimSessionEventFiles(str, 64);
            return;
        } catch (Exception e4) {
            Logger.getLogger().e("An error occurred when trimming non-fatal files.", e4);
            return;
        }
        CommonUtils.closeOrLog(clsFileOutputStream, "Failed to close non-fatal file output stream.");
    }

    private void writeSessionPartFile(String str, String str2, CodedOutputStreamWriteAction codedOutputStreamWriteAction) throws Exception {
        ClsFileOutputStream clsFileOutputStream;
        CodedOutputStream codedOutputStreamNewInstance = null;
        try {
            clsFileOutputStream = new ClsFileOutputStream(getFilesDir(), str + str2);
            try {
                codedOutputStreamNewInstance = CodedOutputStream.newInstance(clsFileOutputStream);
                codedOutputStreamWriteAction.writeTo(codedOutputStreamNewInstance);
                CommonUtils.flushOrLog(codedOutputStreamNewInstance, "Failed to flush to session " + str2 + " file.");
                CommonUtils.closeOrLog(clsFileOutputStream, "Failed to close session " + str2 + " file.");
            } catch (Throwable th) {
                th = th;
                CommonUtils.flushOrLog(codedOutputStreamNewInstance, "Failed to flush to session " + str2 + " file.");
                CommonUtils.closeOrLog(clsFileOutputStream, "Failed to close session " + str2 + " file.");
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            clsFileOutputStream = null;
        }
    }

    private static void appendToProtoFile(File file, CodedOutputStreamWriteAction codedOutputStreamWriteAction) throws Exception {
        FileOutputStream fileOutputStream;
        CodedOutputStream codedOutputStreamNewInstance = null;
        try {
            fileOutputStream = new FileOutputStream(file, true);
            try {
                codedOutputStreamNewInstance = CodedOutputStream.newInstance(fileOutputStream);
                codedOutputStreamWriteAction.writeTo(codedOutputStreamNewInstance);
                CommonUtils.flushOrLog(codedOutputStreamNewInstance, "Failed to flush to append to " + file.getPath());
                CommonUtils.closeOrLog(fileOutputStream, "Failed to close " + file.getPath());
            } catch (Throwable th) {
                th = th;
                CommonUtils.flushOrLog(codedOutputStreamNewInstance, "Failed to flush to append to " + file.getPath());
                CommonUtils.closeOrLog(fileOutputStream, "Failed to close " + file.getPath());
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            fileOutputStream = null;
        }
    }

    private void writeBeginSession(final String str, final long j) throws Exception {
        final String str2 = String.format(Locale.US, GENERATOR_FORMAT, CrashlyticsCore.getVersion());
        writeSessionPartFile(str, SESSION_BEGIN_TAG, new CodedOutputStreamWriteAction() { // from class: com.google.firebase.crashlytics.internal.common.CrashlyticsController.17
            @Override // com.google.firebase.crashlytics.internal.common.CrashlyticsController.CodedOutputStreamWriteAction
            public void writeTo(CodedOutputStream codedOutputStream) throws Exception {
                SessionProtobufHelper.writeBeginSession(codedOutputStream, str, str2, j);
            }
        });
        this.nativeComponent.writeBeginSession(str, str2, j);
    }

    private void writeSessionApp(String str) throws Exception {
        final String appIdentifier = this.idManager.getAppIdentifier();
        final String str2 = this.appData.versionCode;
        final String str3 = this.appData.versionName;
        final String crashlyticsInstallId = this.idManager.getCrashlyticsInstallId();
        final int id = DeliveryMechanism.determineFrom(this.appData.installerPackageName).getId();
        writeSessionPartFile(str, SESSION_APP_TAG, new CodedOutputStreamWriteAction() { // from class: com.google.firebase.crashlytics.internal.common.CrashlyticsController.18
            @Override // com.google.firebase.crashlytics.internal.common.CrashlyticsController.CodedOutputStreamWriteAction
            public void writeTo(CodedOutputStream codedOutputStream) throws Exception {
                SessionProtobufHelper.writeSessionApp(codedOutputStream, appIdentifier, str2, str3, crashlyticsInstallId, id, CrashlyticsController.this.unityVersion);
            }
        });
        this.nativeComponent.writeSessionApp(str, appIdentifier, str2, str3, crashlyticsInstallId, id, this.unityVersion);
    }

    private void writeSessionOS(String str) throws Exception {
        final String str2 = Build.VERSION.RELEASE;
        final String str3 = Build.VERSION.CODENAME;
        final boolean zIsRooted = CommonUtils.isRooted(getContext());
        writeSessionPartFile(str, SESSION_OS_TAG, new CodedOutputStreamWriteAction() { // from class: com.google.firebase.crashlytics.internal.common.CrashlyticsController.19
            @Override // com.google.firebase.crashlytics.internal.common.CrashlyticsController.CodedOutputStreamWriteAction
            public void writeTo(CodedOutputStream codedOutputStream) throws Exception {
                SessionProtobufHelper.writeSessionOS(codedOutputStream, str2, str3, zIsRooted);
            }
        });
        this.nativeComponent.writeSessionOs(str, str2, str3, zIsRooted);
    }

    private void writeSessionDevice(String str) throws Exception {
        Context context = getContext();
        StatFs statFs = new StatFs(Environment.getDataDirectory().getPath());
        final int cpuArchitectureInt = CommonUtils.getCpuArchitectureInt();
        final String str2 = Build.MODEL;
        final int iAvailableProcessors = Runtime.getRuntime().availableProcessors();
        final long totalRamInBytes = CommonUtils.getTotalRamInBytes();
        final long blockCount = statFs.getBlockCount() * statFs.getBlockSize();
        final boolean zIsEmulator = CommonUtils.isEmulator(context);
        final int deviceState = CommonUtils.getDeviceState(context);
        final String str3 = Build.MANUFACTURER;
        final String str4 = Build.PRODUCT;
        writeSessionPartFile(str, SESSION_DEVICE_TAG, new CodedOutputStreamWriteAction() { // from class: com.google.firebase.crashlytics.internal.common.CrashlyticsController.20
            @Override // com.google.firebase.crashlytics.internal.common.CrashlyticsController.CodedOutputStreamWriteAction
            public void writeTo(CodedOutputStream codedOutputStream) throws Exception {
                SessionProtobufHelper.writeSessionDevice(codedOutputStream, cpuArchitectureInt, str2, iAvailableProcessors, totalRamInBytes, blockCount, zIsEmulator, deviceState, str3, str4);
            }
        });
        this.nativeComponent.writeSessionDevice(str, cpuArchitectureInt, str2, iAvailableProcessors, totalRamInBytes, blockCount, zIsEmulator, deviceState, str3, str4);
    }

    private void writeSessionUser(String str) throws Exception {
        final UserMetadata userMetadata = getUserMetadata(str);
        writeSessionPartFile(str, SESSION_USER_TAG, new CodedOutputStreamWriteAction() { // from class: com.google.firebase.crashlytics.internal.common.CrashlyticsController.21
            @Override // com.google.firebase.crashlytics.internal.common.CrashlyticsController.CodedOutputStreamWriteAction
            public void writeTo(CodedOutputStream codedOutputStream) throws Exception {
                SessionProtobufHelper.writeSessionUser(codedOutputStream, userMetadata.getUserId(), null, null);
            }
        });
    }

    private void writeSessionEvent(CodedOutputStream codedOutputStream, Thread thread, Throwable th, long j, String str, boolean z) throws Exception {
        Thread[] threadArr;
        Map<String, String> customKeys;
        Map<String, String> treeMap;
        TrimmedThrowableData trimmedThrowableData = new TrimmedThrowableData(th, this.stackTraceTrimmingStrategy);
        Context context = getContext();
        BatteryState batteryState = BatteryState.get(context);
        Float batteryLevel = batteryState.getBatteryLevel();
        int batteryVelocity = batteryState.getBatteryVelocity();
        boolean proximitySensorEnabled = CommonUtils.getProximitySensorEnabled(context);
        int i = context.getResources().getConfiguration().orientation;
        long totalRamInBytes = CommonUtils.getTotalRamInBytes() - CommonUtils.calculateFreeRamInBytes(context);
        long jCalculateUsedDiskSpaceInBytes = CommonUtils.calculateUsedDiskSpaceInBytes(Environment.getDataDirectory().getPath());
        ActivityManager.RunningAppProcessInfo appProcessInfo = CommonUtils.getAppProcessInfo(context.getPackageName(), context);
        LinkedList linkedList = new LinkedList();
        StackTraceElement[] stackTraceElementArr = trimmedThrowableData.stacktrace;
        String str2 = this.appData.buildId;
        String appIdentifier = this.idManager.getAppIdentifier();
        int i2 = 0;
        if (z) {
            Map<Thread, StackTraceElement[]> allStackTraces = Thread.getAllStackTraces();
            Thread[] threadArr2 = new Thread[allStackTraces.size()];
            for (Map.Entry<Thread, StackTraceElement[]> entry : allStackTraces.entrySet()) {
                threadArr2[i2] = entry.getKey();
                linkedList.add(this.stackTraceTrimmingStrategy.getTrimmedStackTrace(entry.getValue()));
                i2++;
            }
            threadArr = threadArr2;
        } else {
            threadArr = new Thread[0];
        }
        if (!CommonUtils.getBooleanResourceValue(context, COLLECT_CUSTOM_KEYS, true)) {
            customKeys = new TreeMap<>();
        } else {
            customKeys = this.userMetadata.getCustomKeys();
            if (customKeys != null && customKeys.size() > 1) {
                treeMap = new TreeMap(customKeys);
            }
            SessionProtobufHelper.writeSessionEvent(codedOutputStream, j, str, trimmedThrowableData, thread, stackTraceElementArr, threadArr, linkedList, 8, treeMap, this.logFileManager.getBytesForLog(), appProcessInfo, i, appIdentifier, str2, batteryLevel, batteryVelocity, proximitySensorEnabled, totalRamInBytes, jCalculateUsedDiskSpaceInBytes);
            this.logFileManager.clearLog();
        }
        treeMap = customKeys;
        SessionProtobufHelper.writeSessionEvent(codedOutputStream, j, str, trimmedThrowableData, thread, stackTraceElementArr, threadArr, linkedList, 8, treeMap, this.logFileManager.getBytesForLog(), appProcessInfo, i, appIdentifier, str2, batteryLevel, batteryVelocity, proximitySensorEnabled, totalRamInBytes, jCalculateUsedDiskSpaceInBytes);
        this.logFileManager.clearLog();
    }

    private void writeSessionPartsToSessionFile(File file, String str, int i) throws Throwable {
        Logger.getLogger().d("Collecting session parts for ID " + str);
        File[] fileArrListFilesMatching = listFilesMatching(new FileNameContainsFilter(str + SESSION_FATAL_TAG));
        boolean z = fileArrListFilesMatching != null && fileArrListFilesMatching.length > 0;
        Logger.getLogger().d(String.format(Locale.US, "Session %s has fatal exception: %s", str, Boolean.valueOf(z)));
        File[] fileArrListFilesMatching2 = listFilesMatching(new FileNameContainsFilter(str + SESSION_NON_FATAL_TAG));
        boolean z2 = fileArrListFilesMatching2 != null && fileArrListFilesMatching2.length > 0;
        Logger.getLogger().d(String.format(Locale.US, "Session %s has non-fatal exceptions: %s", str, Boolean.valueOf(z2)));
        if (z || z2) {
            synthesizeSessionFile(file, str, getTrimmedNonFatalFiles(str, fileArrListFilesMatching2, i), z ? fileArrListFilesMatching[0] : null);
        } else {
            Logger.getLogger().d("No events present for session ID " + str);
        }
        Logger.getLogger().d("Removing session part files for ID " + str);
        deleteFiles(listSessionPartFilesFor(str));
    }

    private void synthesizeSessionFile(File file, String str, File[] fileArr, File file2) throws Throwable {
        ClsFileOutputStream clsFileOutputStream;
        boolean z = file2 != null;
        File fatalSessionFilesDir = z ? getFatalSessionFilesDir() : getNonFatalSessionFilesDir();
        if (!fatalSessionFilesDir.exists()) {
            fatalSessionFilesDir.mkdirs();
        }
        CodedOutputStream codedOutputStreamNewInstance = null;
        try {
            clsFileOutputStream = new ClsFileOutputStream(fatalSessionFilesDir, str);
            try {
                try {
                    codedOutputStreamNewInstance = CodedOutputStream.newInstance(clsFileOutputStream);
                    Logger.getLogger().d("Collecting SessionStart data for session ID " + str);
                    writeToCosFromFile(codedOutputStreamNewInstance, file);
                    codedOutputStreamNewInstance.writeUInt64(4, getCurrentTimestampSeconds());
                    codedOutputStreamNewInstance.writeBool(5, z);
                    codedOutputStreamNewInstance.writeUInt32(11, 1);
                    codedOutputStreamNewInstance.writeEnum(12, 3);
                    writeInitialPartsTo(codedOutputStreamNewInstance, str);
                    writeNonFatalEventsTo(codedOutputStreamNewInstance, fileArr, str);
                    if (z) {
                        writeToCosFromFile(codedOutputStreamNewInstance, file2);
                    }
                    CommonUtils.flushOrLog(codedOutputStreamNewInstance, "Error flushing session file stream");
                    CommonUtils.closeOrLog(clsFileOutputStream, "Failed to close CLS file");
                } catch (Exception e) {
                    e = e;
                    Logger.getLogger().e("Failed to write session file for session ID: " + str, e);
                    CommonUtils.flushOrLog(codedOutputStreamNewInstance, "Error flushing session file stream");
                    closeWithoutRenamingOrLog(clsFileOutputStream);
                }
            } catch (Throwable th) {
                th = th;
                CommonUtils.flushOrLog(codedOutputStreamNewInstance, "Error flushing session file stream");
                CommonUtils.closeOrLog(clsFileOutputStream, "Failed to close CLS file");
                throw th;
            }
        } catch (Exception e2) {
            e = e2;
            clsFileOutputStream = null;
        } catch (Throwable th2) {
            th = th2;
            clsFileOutputStream = null;
            CommonUtils.flushOrLog(codedOutputStreamNewInstance, "Error flushing session file stream");
            CommonUtils.closeOrLog(clsFileOutputStream, "Failed to close CLS file");
            throw th;
        }
    }

    private static void writeNonFatalEventsTo(CodedOutputStream codedOutputStream, File[] fileArr, String str) throws Throwable {
        Arrays.sort(fileArr, CommonUtils.FILE_MODIFIED_COMPARATOR);
        for (File file : fileArr) {
            try {
                Logger.getLogger().d(String.format(Locale.US, "Found Non Fatal for session ID %s in %s ", str, file.getName()));
                writeToCosFromFile(codedOutputStream, file);
            } catch (Exception e) {
                Logger.getLogger().e("Error writting non-fatal to session.", e);
            }
        }
    }

    private void writeInitialPartsTo(CodedOutputStream codedOutputStream, String str) throws Throwable {
        for (String str2 : INITIAL_SESSION_PART_TAGS) {
            File[] fileArrListFilesMatching = listFilesMatching(new FileNameContainsFilter(str + str2 + ClsFileOutputStream.SESSION_FILE_EXTENSION));
            if (fileArrListFilesMatching.length == 0) {
                Logger.getLogger().d("Can't find " + str2 + " data for session ID " + str);
            } else {
                Logger.getLogger().d("Collecting " + str2 + " data for session ID " + str);
                writeToCosFromFile(codedOutputStream, fileArrListFilesMatching[0]);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void appendOrganizationIdToSessionFile(final String str, File file) throws Exception {
        if (str == null) {
            return;
        }
        appendToProtoFile(file, new CodedOutputStreamWriteAction() { // from class: com.google.firebase.crashlytics.internal.common.CrashlyticsController.22
            @Override // com.google.firebase.crashlytics.internal.common.CrashlyticsController.CodedOutputStreamWriteAction
            public void writeTo(CodedOutputStream codedOutputStream) throws Exception {
                SessionProtobufHelper.writeSessionAppClsId(codedOutputStream, str);
            }
        });
    }

    private static void writeToCosFromFile(CodedOutputStream codedOutputStream, File file) throws Throwable {
        if (!file.exists()) {
            Logger.getLogger().e("Tried to include a file that doesn't exist: " + file.getName());
            return;
        }
        FileInputStream fileInputStream = null;
        try {
            FileInputStream fileInputStream2 = new FileInputStream(file);
            try {
                copyToCodedOutputStream(fileInputStream2, codedOutputStream, (int) file.length());
                CommonUtils.closeOrLog(fileInputStream2, "Failed to close file input stream.");
            } catch (Throwable th) {
                th = th;
                fileInputStream = fileInputStream2;
                CommonUtils.closeOrLog(fileInputStream, "Failed to close file input stream.");
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    private static void copyToCodedOutputStream(InputStream inputStream, CodedOutputStream codedOutputStream, int i) throws IOException {
        byte[] bArr = new byte[i];
        int i2 = 0;
        while (i2 < i) {
            int i3 = inputStream.read(bArr, i2, i - i2);
            if (i3 < 0) {
                break;
            } else {
                i2 += i3;
            }
        }
        codedOutputStream.writeRawBytes(bArr);
    }

    UserMetadata getUserMetadata() {
        return this.userMetadata;
    }

    private UserMetadata getUserMetadata(String str) {
        if (isHandlingException()) {
            return this.userMetadata;
        }
        return new MetaDataStore(getFilesDir()).readUserData(str);
    }

    boolean isHandlingException() {
        CrashlyticsUncaughtExceptionHandler crashlyticsUncaughtExceptionHandler = this.crashHandler;
        return crashlyticsUncaughtExceptionHandler != null && crashlyticsUncaughtExceptionHandler.isHandlingException();
    }

    File getFilesDir() {
        return this.fileStore.getFilesDir();
    }

    File getNativeSessionFilesDir() {
        return new File(getFilesDir(), NATIVE_SESSION_DIR);
    }

    File getFatalSessionFilesDir() {
        return new File(getFilesDir(), FATAL_SESSION_DIR);
    }

    File getNonFatalSessionFilesDir() {
        return new File(getFilesDir(), NONFATAL_SESSION_DIR);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public CreateReportSpiCall getCreateReportSpiCall(String str, String str2) {
        String stringsFileValue = CommonUtils.getStringsFileValue(getContext(), CRASHLYTICS_API_ENDPOINT);
        return new CompositeCreateReportSpiCall(new DefaultCreateReportSpiCall(stringsFileValue, str, this.httpRequestFactory, CrashlyticsCore.getVersion()), new NativeCreateReportSpiCall(stringsFileValue, str2, this.httpRequestFactory, CrashlyticsCore.getVersion()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendSessionReports(AppSettingsData appSettingsData, boolean z) throws Exception {
        Context context = getContext();
        ReportUploader reportUploaderCreateReportUploader = this.reportUploaderProvider.createReportUploader(appSettingsData);
        for (File file : listCompleteSessionFiles()) {
            appendOrganizationIdToSessionFile(appSettingsData.organizationId, file);
            this.backgroundWorker.submit(new SendReportRunnable(context, new SessionReport(file, SEND_AT_CRASHTIME_HEADER), reportUploaderCreateReportUploader, z));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Task<Void> logAnalyticsAppExceptionEvents() {
        ArrayList arrayList = new ArrayList();
        for (File file : listAppExceptionMarkerFiles()) {
            try {
                arrayList.add(logAnalyticsAppExceptionEvent(Long.parseLong(file.getName().substring(3))));
            } catch (NumberFormatException unused) {
                Logger.getLogger().d("Could not parse timestamp from file " + file.getName());
            }
            file.delete();
        }
        return Tasks.whenAll(arrayList);
    }

    private Task<Void> logAnalyticsAppExceptionEvent(final long j) {
        if (firebaseCrashExists()) {
            Logger.getLogger().d("Skipping logging Crashlytics event to Firebase, FirebaseCrash exists");
            return Tasks.forResult(null);
        }
        return Tasks.call(new ScheduledThreadPoolExecutor(1), new Callable<Void>() { // from class: com.google.firebase.crashlytics.internal.common.CrashlyticsController.23
            @Override // java.util.concurrent.Callable
            public Void call() throws Exception {
                Bundle bundle = new Bundle();
                bundle.putInt(CrashlyticsController.FIREBASE_CRASH_TYPE, 1);
                bundle.putLong(CrashlyticsController.FIREBASE_TIMESTAMP, j);
                CrashlyticsController.this.analyticsEventLogger.logEvent(CrashlyticsController.FIREBASE_APPLICATION_EXCEPTION, bundle);
                return null;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void deleteFiles(File[] fileArr) {
        if (fileArr == null) {
            return;
        }
        for (File file : fileArr) {
            file.delete();
        }
    }

    private static boolean firebaseCrashExists() throws ClassNotFoundException {
        try {
            Class.forName("com.google.firebase.crash.FirebaseCrash");
            return true;
        } catch (ClassNotFoundException unused) {
            return false;
        }
    }

    private final class ReportUploaderHandlingExceptionCheck implements ReportUploader.HandlingExceptionCheck {
        private ReportUploaderHandlingExceptionCheck() {
        }

        @Override // com.google.firebase.crashlytics.internal.report.ReportUploader.HandlingExceptionCheck
        public boolean isHandlingException() {
            return CrashlyticsController.this.isHandlingException();
        }
    }

    private final class ReportUploaderFilesProvider implements ReportUploader.ReportFilesProvider {
        private ReportUploaderFilesProvider() {
        }

        @Override // com.google.firebase.crashlytics.internal.report.ReportUploader.ReportFilesProvider
        public File[] getCompleteSessionFiles() {
            return CrashlyticsController.this.listCompleteSessionFiles();
        }

        @Override // com.google.firebase.crashlytics.internal.report.ReportUploader.ReportFilesProvider
        public File[] getNativeReportFiles() {
            return CrashlyticsController.this.listNativeSessionFileDirectories();
        }
    }

    private static final class SendReportRunnable implements Runnable {
        private final Context context;
        private final boolean dataCollectionToken;
        private final Report report;
        private final ReportUploader reportUploader;

        public SendReportRunnable(Context context, Report report, ReportUploader reportUploader, boolean z) {
            this.context = context;
            this.report = report;
            this.reportUploader = reportUploader;
            this.dataCollectionToken = z;
        }

        @Override // java.lang.Runnable
        public void run() {
            if (CommonUtils.canTryConnection(this.context)) {
                Logger.getLogger().d("Attempting to send crash report at time of crash...");
                this.reportUploader.uploadReport(this.report, this.dataCollectionToken);
            }
        }
    }

    static List<NativeSessionFile> getNativeSessionFiles(NativeSessionFileProvider nativeSessionFileProvider, String str, Context context, File file, byte[] bArr) {
        MetaDataStore metaDataStore = new MetaDataStore(file);
        File userDataFileForSession = metaDataStore.getUserDataFileForSession(str);
        File keysFileForSession = metaDataStore.getKeysFileForSession(str);
        ArrayList arrayList = new ArrayList();
        arrayList.add(new BytesBackedNativeSessionFile("logs_file", FileLogger.LOGS_FILE_NAME, bArr));
        arrayList.add(new FileBackedNativeSessionFile("crash_meta_file", TtmlNode.TAG_METADATA, nativeSessionFileProvider.getMetadataFile()));
        arrayList.add(new FileBackedNativeSessionFile("session_meta_file", "session", nativeSessionFileProvider.getSessionFile()));
        arrayList.add(new FileBackedNativeSessionFile("app_meta_file", "app", nativeSessionFileProvider.getAppFile()));
        arrayList.add(new FileBackedNativeSessionFile("device_meta_file", "device", nativeSessionFileProvider.getDeviceFile()));
        arrayList.add(new FileBackedNativeSessionFile("os_meta_file", "os", nativeSessionFileProvider.getOsFile()));
        arrayList.add(new FileBackedNativeSessionFile("minidump_file", "minidump", nativeSessionFileProvider.getMinidumpFile()));
        arrayList.add(new FileBackedNativeSessionFile("user_meta_file", "user", userDataFileForSession));
        arrayList.add(new FileBackedNativeSessionFile("keys_file", "keys", keysFileForSession));
        return arrayList;
    }

    private static final class LogFileDirectoryProvider implements LogFileManager.DirectoryProvider {
        private static final String LOG_FILES_DIR = "log-files";
        private final FileStore rootFileStore;

        public LogFileDirectoryProvider(FileStore fileStore) {
            this.rootFileStore = fileStore;
        }

        @Override // com.google.firebase.crashlytics.internal.log.LogFileManager.DirectoryProvider
        public File getLogFileDir() {
            File file = new File(this.rootFileStore.getFilesDir(), LOG_FILES_DIR);
            if (!file.exists()) {
                file.mkdirs();
            }
            return file;
        }
    }
}
