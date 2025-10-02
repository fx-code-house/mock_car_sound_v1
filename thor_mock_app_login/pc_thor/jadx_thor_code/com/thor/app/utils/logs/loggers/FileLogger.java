package com.thor.app.utils.logs.loggers;

import android.content.Context;
import androidx.exifinterface.media.ExifInterface;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.thor.app.settings.Settings;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.IllegalFormatConversionException;
import java.util.Locale;
import java.util.MissingFormatArgumentException;
import java.util.UnknownFormatConversionException;
import kotlin.Metadata;
import kotlin.io.CloseableKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.text.StringsKt;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.ap.shaded.freemarker.template.Template;
import timber.log.Timber;

/* compiled from: FileLogger.kt */
@Metadata(d1 = {"\u0000`\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0003\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u0000 22\u00020\u0001:\u00012B#\b\u0016\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\u0002\u0010\bB#\b\u0016\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\t\u001a\u00020\n\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\u0002\u0010\u000bB\u001b\u0012\b\u0010\f\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\u0002\u0010\rJ\u0006\u0010\u0014\u001a\u00020\u0015J)\u0010\u0016\u001a\u00020\u00152\u0006\u0010\u0017\u001a\u00020\u00072\u0012\u0010\u0018\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u001a0\u0019\"\u00020\u001aH\u0016¢\u0006\u0002\u0010\u001bJ\u0010\u0010\u0016\u001a\u00020\u00152\u0006\u0010\u001c\u001a\u00020\u001dH\u0016J1\u0010\u0016\u001a\u00020\u00152\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u0017\u001a\u00020\u00072\u0012\u0010\u0018\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u001a0\u0019\"\u00020\u001aH\u0016¢\u0006\u0002\u0010\u001eJ)\u0010\u001f\u001a\u00020\u00152\u0006\u0010\u0017\u001a\u00020\u00072\u0012\u0010\u0018\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u001a0\u0019\"\u00020\u001aH\u0016¢\u0006\u0002\u0010\u001bJ\u0010\u0010\u001f\u001a\u00020\u00152\u0006\u0010\u001c\u001a\u00020\u001dH\u0016J1\u0010\u001f\u001a\u00020\u00152\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u0017\u001a\u00020\u00072\u0012\u0010\u0018\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u001a0\u0019\"\u00020\u001aH\u0016¢\u0006\u0002\u0010\u001eJ)\u0010 \u001a\u00020\u00072\u0006\u0010\u0017\u001a\u00020\u00072\u0012\u0010\u0018\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u001a0\u0019\"\u00020\u001aH\u0016¢\u0006\u0002\u0010!J\u0006\u0010\"\u001a\u00020#J\u0012\u0010$\u001a\u0004\u0018\u00010\u00072\u0006\u0010%\u001a\u00020\u001dH\u0002J)\u0010&\u001a\u00020\u00152\u0006\u0010\u0017\u001a\u00020\u00072\u0012\u0010\u0018\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u001a0\u0019\"\u00020\u001aH\u0016¢\u0006\u0002\u0010\u001bJ\u0010\u0010&\u001a\u00020\u00152\u0006\u0010\u001c\u001a\u00020\u001dH\u0016J1\u0010&\u001a\u00020\u00152\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u0017\u001a\u00020\u00072\u0012\u0010\u0018\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u001a0\u0019\"\u00020\u001aH\u0016¢\u0006\u0002\u0010\u001eJ\b\u0010'\u001a\u00020\u0005H\u0002J\b\u0010(\u001a\u00020\u0015H\u0002J\u001a\u0010)\u001a\u00020\u00152\u0006\u0010*\u001a\u00020+2\b\u0010\u0017\u001a\u0004\u0018\u00010\u0007H\u0002J\b\u0010,\u001a\u0004\u0018\u00010\u0007J\u0010\u0010-\u001a\u00020\u00052\u0006\u0010.\u001a\u00020\u0007H\u0002J)\u0010/\u001a\u00020\u00152\u0006\u0010\u0017\u001a\u00020\u00072\u0012\u0010\u0018\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u001a0\u0019\"\u00020\u001aH\u0016¢\u0006\u0002\u0010\u001bJ\u0010\u0010/\u001a\u00020\u00152\u0006\u0010\u001c\u001a\u00020\u001dH\u0016J1\u0010/\u001a\u00020\u00152\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u0017\u001a\u00020\u00072\u0012\u0010\u0018\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u001a0\u0019\"\u00020\u001aH\u0016¢\u0006\u0002\u0010\u001eJ)\u00100\u001a\u00020\u00152\u0006\u0010\u0017\u001a\u00020\u00072\u0012\u0010\u0018\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u001a0\u0019\"\u00020\u001aH\u0016¢\u0006\u0002\u0010\u001bJ\u0010\u00100\u001a\u00020\u00152\u0006\u0010\u001c\u001a\u00020\u001dH\u0016J1\u00100\u001a\u00020\u00152\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u0017\u001a\u00020\u00072\u0012\u0010\u0018\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u001a0\u0019\"\u00020\u001aH\u0016¢\u0006\u0002\u0010\u001eJ\f\u00101\u001a\u00020\u0007*\u00020\u0007H\u0002R\u000e\u0010\u000e\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\f\u001a\u0004\u0018\u00010\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u00063"}, d2 = {"Lcom/thor/app/utils/logs/loggers/FileLogger;", "Lcom/thor/app/utils/logs/loggers/Logger;", "context", "Landroid/content/Context;", "enabledClearingData", "", "tag", "", "(Landroid/content/Context;ZLjava/lang/String;)V", "clearingTimeInterval", "", "(Landroid/content/Context;JLjava/lang/String;)V", "mContext", "(Landroid/content/Context;Ljava/lang/String;)V", "mClearingTimeInterval", "mCompositeDisposable", "Lio/reactivex/disposables/CompositeDisposable;", "mEnabledClearingData", "scheduler", "Lio/reactivex/Scheduler;", "clearLogs", "", "d", "message", "args", "", "", "(Ljava/lang/String;[Ljava/lang/Object;)V", "throwable", "", "(Ljava/lang/Throwable;Ljava/lang/String;[Ljava/lang/Object;)V", "e", "format", "(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;", "getLogsFile", "Ljava/io/File;", "getStackTraceString", "t", "i", "isTimeForClearData", "onClearLogs", "onWrite", FirebaseAnalytics.Param.LEVEL, "Lcom/thor/app/utils/logs/loggers/LoggerLevel;", "readLogs", "saveDataIntoLogs", "text", "v", "w", "clear", "Companion", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class FileLogger implements Logger {

    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    public static final long DEFAULT_TIME_INTERVAL = 86400000;
    public static final String LOGS_FILE_NAME = "logs";
    private long mClearingTimeInterval;
    private final CompositeDisposable mCompositeDisposable;
    private final Context mContext;
    private boolean mEnabledClearingData;
    private final Scheduler scheduler;
    private final String tag;

    /* compiled from: FileLogger.kt */
    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[LoggerLevel.values().length];
            try {
                iArr[LoggerLevel.DEBUG.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[LoggerLevel.VERBOSE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[LoggerLevel.INFO.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[LoggerLevel.WARN.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[LoggerLevel.ERROR.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public FileLogger(Context context, String str) {
        this.mContext = context;
        this.tag = str;
        this.mClearingTimeInterval = 86400000L;
        this.mCompositeDisposable = new CompositeDisposable();
        Scheduler schedulerSingle = Schedulers.single();
        Intrinsics.checkNotNullExpressionValue(schedulerSingle, "single()");
        this.scheduler = schedulerSingle;
    }

    public /* synthetic */ FileLogger(Context context, String str, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i & 2) != 0 ? null : str);
    }

    public FileLogger(Context context, boolean z, String str) {
        this(context, str);
        this.mEnabledClearingData = z;
    }

    public FileLogger(Context context, long j, String str) {
        this(context, str);
        this.mClearingTimeInterval = j;
        this.mEnabledClearingData = true;
    }

    @Override // com.thor.app.utils.logs.loggers.Logger
    public void d(Throwable throwable) {
        Intrinsics.checkNotNullParameter(throwable, "throwable");
        Timber.INSTANCE.d(throwable);
        onWrite(LoggerLevel.DEBUG, getStackTraceString(throwable));
    }

    @Override // com.thor.app.utils.logs.loggers.Logger
    public void d(String message, Object... args) {
        Intrinsics.checkNotNullParameter(message, "message");
        Intrinsics.checkNotNullParameter(args, "args");
        Timber.INSTANCE.d(message, Arrays.copyOf(args, args.length));
        onWrite(LoggerLevel.DEBUG, format(message, args));
    }

    @Override // com.thor.app.utils.logs.loggers.Logger
    public void d(Throwable throwable, String message, Object... args) {
        Intrinsics.checkNotNullParameter(throwable, "throwable");
        Intrinsics.checkNotNullParameter(message, "message");
        Intrinsics.checkNotNullParameter(args, "args");
        Timber.INSTANCE.d(throwable, message, Arrays.copyOf(args, args.length));
        onWrite(LoggerLevel.DEBUG, getStackTraceString(throwable));
    }

    @Override // com.thor.app.utils.logs.loggers.Logger
    public void v(Throwable throwable) {
        Intrinsics.checkNotNullParameter(throwable, "throwable");
        Timber.INSTANCE.v(throwable);
        onWrite(LoggerLevel.VERBOSE, getStackTraceString(throwable));
    }

    @Override // com.thor.app.utils.logs.loggers.Logger
    public void v(String message, Object... args) {
        Intrinsics.checkNotNullParameter(message, "message");
        Intrinsics.checkNotNullParameter(args, "args");
        Timber.INSTANCE.v(message, Arrays.copyOf(args, args.length));
        onWrite(LoggerLevel.VERBOSE, format(message, args));
    }

    @Override // com.thor.app.utils.logs.loggers.Logger
    public void v(Throwable throwable, String message, Object... args) {
        Intrinsics.checkNotNullParameter(throwable, "throwable");
        Intrinsics.checkNotNullParameter(message, "message");
        Intrinsics.checkNotNullParameter(args, "args");
        Timber.INSTANCE.v(throwable, message, Arrays.copyOf(args, args.length));
        onWrite(LoggerLevel.VERBOSE, getStackTraceString(throwable));
    }

    @Override // com.thor.app.utils.logs.loggers.Logger
    public void i(Throwable throwable) {
        Intrinsics.checkNotNullParameter(throwable, "throwable");
        Timber.INSTANCE.i(throwable);
        onWrite(LoggerLevel.INFO, getStackTraceString(throwable));
    }

    @Override // com.thor.app.utils.logs.loggers.Logger
    public void i(String message, Object... args) {
        Intrinsics.checkNotNullParameter(message, "message");
        Intrinsics.checkNotNullParameter(args, "args");
        Timber.INSTANCE.i(message, Arrays.copyOf(args, args.length));
        onWrite(LoggerLevel.INFO, format(message, args));
    }

    @Override // com.thor.app.utils.logs.loggers.Logger
    public void i(Throwable throwable, String message, Object... args) {
        Intrinsics.checkNotNullParameter(throwable, "throwable");
        Intrinsics.checkNotNullParameter(message, "message");
        Intrinsics.checkNotNullParameter(args, "args");
        Timber.INSTANCE.i(throwable, message, Arrays.copyOf(args, args.length));
        onWrite(LoggerLevel.INFO, getStackTraceString(throwable));
    }

    @Override // com.thor.app.utils.logs.loggers.Logger
    public void w(Throwable throwable) {
        Intrinsics.checkNotNullParameter(throwable, "throwable");
        Timber.INSTANCE.w(throwable);
        onWrite(LoggerLevel.WARN, getStackTraceString(throwable));
    }

    @Override // com.thor.app.utils.logs.loggers.Logger
    public void w(String message, Object... args) {
        Intrinsics.checkNotNullParameter(message, "message");
        Intrinsics.checkNotNullParameter(args, "args");
        Timber.INSTANCE.w(message, Arrays.copyOf(args, args.length));
        onWrite(LoggerLevel.WARN, format(message, args));
    }

    @Override // com.thor.app.utils.logs.loggers.Logger
    public void w(Throwable throwable, String message, Object... args) {
        Intrinsics.checkNotNullParameter(throwable, "throwable");
        Intrinsics.checkNotNullParameter(message, "message");
        Intrinsics.checkNotNullParameter(args, "args");
        Timber.INSTANCE.w(throwable, message, Arrays.copyOf(args, args.length));
        onWrite(LoggerLevel.WARN, getStackTraceString(throwable));
    }

    @Override // com.thor.app.utils.logs.loggers.Logger
    public void e(Throwable throwable) {
        Intrinsics.checkNotNullParameter(throwable, "throwable");
        Timber.INSTANCE.e(throwable);
        onWrite(LoggerLevel.ERROR, getStackTraceString(throwable));
    }

    @Override // com.thor.app.utils.logs.loggers.Logger
    public void e(String message, Object... args) {
        Intrinsics.checkNotNullParameter(message, "message");
        Intrinsics.checkNotNullParameter(args, "args");
        Timber.INSTANCE.e(message, Arrays.copyOf(args, args.length));
        onWrite(LoggerLevel.ERROR, format(message, args));
    }

    @Override // com.thor.app.utils.logs.loggers.Logger
    public void e(Throwable throwable, String message, Object... args) {
        Intrinsics.checkNotNullParameter(throwable, "throwable");
        Intrinsics.checkNotNullParameter(message, "message");
        Intrinsics.checkNotNullParameter(args, "args");
        Timber.INSTANCE.e(throwable, message, Arrays.copyOf(args, args.length));
        onWrite(LoggerLevel.ERROR, getStackTraceString(throwable));
    }

    @Override // com.thor.app.utils.logs.loggers.Logger
    public String format(String message, Object... args) {
        Intrinsics.checkNotNullParameter(message, "message");
        Intrinsics.checkNotNullParameter(args, "args");
        try {
            StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
            Object[] objArrCopyOf = Arrays.copyOf(args, args.length);
            String str = String.format(message, Arrays.copyOf(objArrCopyOf, objArrCopyOf.length));
            Intrinsics.checkNotNullExpressionValue(str, "format(...)");
            return str;
        } catch (IllegalFormatConversionException | MissingFormatArgumentException | UnknownFormatConversionException unused) {
            return message;
        }
    }

    public final String readLogs() {
        Context context;
        FileInputStream fileInputStreamOpenFileInput;
        if (!getLogsFile().exists() || (context = this.mContext) == null || (fileInputStreamOpenFileInput = context.openFileInput(LOGS_FILE_NAME)) == null) {
            return null;
        }
        FileInputStream fileInputStream = fileInputStreamOpenFileInput;
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
            StringBuilder sb = new StringBuilder();
            while (true) {
                String line = bufferedReader.readLine();
                if (line != null) {
                    String str = line;
                    if (!(str == null || str.length() == 0)) {
                        sb.append(StringUtils.LF).append(line);
                    }
                } else {
                    String string = sb.toString();
                    CloseableKt.closeFinally(fileInputStream, null);
                    return string;
                }
            }
        } finally {
        }
    }

    private final String getStackTraceString(Throwable t) {
        StringWriter stringWriter = new StringWriter(256);
        PrintWriter printWriter = new PrintWriter((Writer) stringWriter, false);
        t.printStackTrace(printWriter);
        printWriter.flush();
        return stringWriter.toString();
    }

    private final void onWrite(final LoggerLevel level, final String message) {
        this.mCompositeDisposable.add(Single.create(new SingleOnSubscribe() { // from class: com.thor.app.utils.logs.loggers.FileLogger$$ExternalSyntheticLambda0
            @Override // io.reactivex.SingleOnSubscribe
            public final void subscribe(SingleEmitter singleEmitter) {
                FileLogger.onWrite$lambda$2(message, this, level, singleEmitter);
            }
        }).subscribeOn(this.scheduler).observeOn(this.scheduler).subscribe());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onWrite$lambda$2(String str, FileLogger this$0, LoggerLevel level, SingleEmitter emitter) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(level, "$level");
        Intrinsics.checkNotNullParameter(emitter, "emitter");
        String str2 = str;
        if ((str2 == null || str2.length() == 0) || this$0.mContext == null) {
            emitter.onSuccess(false);
        }
        StringBuilder sb = new StringBuilder();
        sb.append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS ", Locale.getDefault()).format(Long.valueOf(System.currentTimeMillis())));
        int i = WhenMappings.$EnumSwitchMapping$0[level.ordinal()];
        if (i == 1) {
            sb.append(Template.DEFAULT_NAMESPACE_PREFIX);
        } else if (i == 2) {
            sb.append(ExifInterface.GPS_MEASUREMENT_INTERRUPTED);
        } else if (i == 3) {
            sb.append("I");
        } else if (i == 4) {
            sb.append(ExifInterface.LONGITUDE_WEST);
        } else if (i == 5) {
            sb.append(ExifInterface.LONGITUDE_EAST);
        }
        StringBuilder sbAppend = sb.append("/");
        String simpleName = this$0.tag;
        if (simpleName == null) {
            simpleName = this$0.getClass().getSimpleName();
        }
        sbAppend.append(simpleName).append(": ").append(str);
        this$0.onClearLogs();
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "logsText.toString()");
        emitter.onSuccess(Boolean.valueOf(this$0.saveDataIntoLogs(string)));
    }

    private final boolean isTimeForClearData() {
        return System.currentTimeMillis() - Settings.INSTANCE.getLastRefreshLogsTime() >= this.mClearingTimeInterval;
    }

    private final void onClearLogs() {
        if (this.mEnabledClearingData && isTimeForClearData()) {
            Timber.INSTANCE.i("cleared logs", new Object[0]);
            clearLogs();
        }
    }

    public final void clearLogs() {
        Settings.INSTANCE.saveLastRefreshLogsTime();
        getLogsFile().delete();
    }

    /* JADX WARN: Code restructure failed: missing block: B:17:0x002d, code lost:
    
        if ((r2.length() == 0) != false) goto L18;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private final boolean saveDataIntoLogs(java.lang.String r6) {
        /*
            r5 = this;
            java.lang.String r0 = r5.readLogs()
            r1 = 0
            if (r0 == 0) goto Lc
            java.lang.String r0 = r5.clear(r0)
            goto Ld
        Lc:
            r0 = r1
        Ld:
            java.lang.String r2 = r5.clear(r6)
            r3 = 1
            r4 = 0
            if (r0 == 0) goto L30
            java.lang.CharSequence r0 = (java.lang.CharSequence) r0
            int r0 = r0.length()
            if (r0 != 0) goto L1f
            r0 = r3
            goto L20
        L1f:
            r0 = r4
        L20:
            if (r0 != 0) goto L2f
            java.lang.CharSequence r2 = (java.lang.CharSequence) r2
            int r0 = r2.length()
            if (r0 != 0) goto L2c
            r0 = r3
            goto L2d
        L2c:
            r0 = r4
        L2d:
            if (r0 == 0) goto L30
        L2f:
            return r4
        L30:
            java.lang.String r0 = r5.readLogs()
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.StringBuilder r0 = r2.append(r0)
            java.lang.String r2 = "\n"
            java.lang.StringBuilder r0 = r0.append(r2)
            java.lang.StringBuilder r6 = r0.append(r6)
            java.lang.String r6 = r6.toString()
            android.content.Context r0 = r5.mContext
            if (r0 == 0) goto L56
            java.lang.String r2 = "logs"
            java.io.FileOutputStream r0 = r0.openFileOutput(r2, r4)
            goto L57
        L56:
            r0 = r1
        L57:
            java.io.Closeable r0 = (java.io.Closeable) r0
            r2 = r0
            java.io.FileOutputStream r2 = (java.io.FileOutputStream) r2     // Catch: java.lang.Throwable -> L72
            if (r2 == 0) goto L6e
            java.nio.charset.Charset r4 = kotlin.text.Charsets.UTF_8     // Catch: java.lang.Throwable -> L72
            byte[] r6 = r6.getBytes(r4)     // Catch: java.lang.Throwable -> L72
            java.lang.String r4 = "getBytes(...)"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r6, r4)     // Catch: java.lang.Throwable -> L72
            r2.write(r6)     // Catch: java.lang.Throwable -> L72
            kotlin.Unit r6 = kotlin.Unit.INSTANCE     // Catch: java.lang.Throwable -> L72
        L6e:
            kotlin.io.CloseableKt.closeFinally(r0, r1)
            return r3
        L72:
            r6 = move-exception
            throw r6     // Catch: java.lang.Throwable -> L74
        L74:
            r1 = move-exception
            kotlin.io.CloseableKt.closeFinally(r0, r6)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.thor.app.utils.logs.loggers.FileLogger.saveDataIntoLogs(java.lang.String):boolean");
    }

    public final File getLogsFile() {
        Context context = this.mContext;
        return new File(context != null ? context.getFilesDir() : null, LOGS_FILE_NAME);
    }

    private final String clear(String str) {
        return StringsKt.replace$default(StringsKt.replace$default(str, StringUtils.LF, "", false, 4, (Object) null), StringUtils.SPACE, "", false, 4, (Object) null);
    }

    /* compiled from: FileLogger.kt */
    @Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J$\u0010\u0007\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\n2\u0006\u0010\u000b\u001a\u00020\f2\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u0006J$\u0010\u0007\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\n2\u0006\u0010\u000e\u001a\u00020\u00042\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u0006J\u001c\u0010\u0007\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\n2\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u0006R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0086T¢\u0006\u0002\n\u0000¨\u0006\u000f"}, d2 = {"Lcom/thor/app/utils/logs/loggers/FileLogger$Companion;", "", "()V", "DEFAULT_TIME_INTERVAL", "", "LOGS_FILE_NAME", "", "newInstance", "Lcom/thor/app/utils/logs/loggers/FileLogger;", "context", "Landroid/content/Context;", "enabledClearingData", "", "tag", "clearingTimeInterval", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public static /* synthetic */ FileLogger newInstance$default(Companion companion, Context context, String str, int i, Object obj) {
            if ((i & 2) != 0) {
                str = null;
            }
            return companion.newInstance(context, str);
        }

        public final FileLogger newInstance(Context context, String tag) {
            return new FileLogger(context, tag);
        }

        public static /* synthetic */ FileLogger newInstance$default(Companion companion, Context context, boolean z, String str, int i, Object obj) {
            if ((i & 4) != 0) {
                str = null;
            }
            return companion.newInstance(context, z, str);
        }

        public final FileLogger newInstance(Context context, boolean enabledClearingData, String tag) {
            return new FileLogger(context, enabledClearingData, tag);
        }

        public static /* synthetic */ FileLogger newInstance$default(Companion companion, Context context, long j, String str, int i, Object obj) {
            if ((i & 4) != 0) {
                str = null;
            }
            return companion.newInstance(context, j, str);
        }

        public final FileLogger newInstance(Context context, long clearingTimeInterval, String tag) {
            return new FileLogger(context, clearingTimeInterval, tag);
        }
    }
}
