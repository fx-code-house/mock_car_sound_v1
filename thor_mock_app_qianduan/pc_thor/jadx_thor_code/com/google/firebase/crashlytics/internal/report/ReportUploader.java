package com.google.firebase.crashlytics.internal.report;

import com.google.firebase.crashlytics.internal.Logger;
import com.google.firebase.crashlytics.internal.common.BackgroundPriorityRunnable;
import com.google.firebase.crashlytics.internal.common.DataTransportState;
import com.google.firebase.crashlytics.internal.report.model.Report;
import com.google.firebase.crashlytics.internal.report.network.CreateReportSpiCall;
import com.google.firebase.crashlytics.internal.settings.model.AppSettingsData;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public class ReportUploader {
    private static final short[] RETRY_INTERVALS = {10, 20, 30, 60, 120, 300};
    private final CreateReportSpiCall createReportCall;
    private final DataTransportState dataTransportState;
    private final String googleAppId;
    private final HandlingExceptionCheck handlingExceptionCheck;
    private final String organizationId;
    private final ReportManager reportManager;
    private Thread uploadThread;

    public interface HandlingExceptionCheck {
        boolean isHandlingException();
    }

    public interface Provider {
        ReportUploader createReportUploader(AppSettingsData appSettingsData);
    }

    public interface ReportFilesProvider {
        File[] getCompleteSessionFiles();

        File[] getNativeReportFiles();
    }

    public ReportUploader(String str, String str2, DataTransportState dataTransportState, ReportManager reportManager, CreateReportSpiCall createReportSpiCall, HandlingExceptionCheck handlingExceptionCheck) {
        if (createReportSpiCall == null) {
            throw new IllegalArgumentException("createReportCall must not be null.");
        }
        this.createReportCall = createReportSpiCall;
        this.organizationId = str;
        this.googleAppId = str2;
        this.dataTransportState = dataTransportState;
        this.reportManager = reportManager;
        this.handlingExceptionCheck = handlingExceptionCheck;
    }

    public synchronized void uploadReportsAsync(List<Report> list, boolean z, float f) {
        if (this.uploadThread != null) {
            Logger.getLogger().d("Report upload has already been started.");
            return;
        }
        Thread thread = new Thread(new Worker(list, z, f), "Crashlytics Report Uploader");
        this.uploadThread = thread;
        thread.start();
    }

    boolean isUploading() {
        return this.uploadThread != null;
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x005f A[Catch: Exception -> 0x0066, TRY_LEAVE, TryCatch #0 {Exception -> 0x0066, blocks: (B:3:0x0003, B:6:0x0015, B:19:0x005f, B:7:0x001d, B:9:0x0023, B:11:0x002b, B:13:0x0034, B:17:0x004a), top: B:24:0x0003 }] */
    /* JADX WARN: Removed duplicated region for block: B:26:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public boolean uploadReport(com.google.firebase.crashlytics.internal.report.model.Report r8, boolean r9) {
        /*
            r7 = this;
            java.lang.String r0 = "Crashlytics Reports Endpoint upload "
            r1 = 0
            com.google.firebase.crashlytics.internal.report.model.CreateReportRequest r2 = new com.google.firebase.crashlytics.internal.report.model.CreateReportRequest     // Catch: java.lang.Exception -> L66
            java.lang.String r3 = r7.organizationId     // Catch: java.lang.Exception -> L66
            java.lang.String r4 = r7.googleAppId     // Catch: java.lang.Exception -> L66
            r2.<init>(r3, r4, r8)     // Catch: java.lang.Exception -> L66
            com.google.firebase.crashlytics.internal.common.DataTransportState r3 = r7.dataTransportState     // Catch: java.lang.Exception -> L66
            com.google.firebase.crashlytics.internal.common.DataTransportState r4 = com.google.firebase.crashlytics.internal.common.DataTransportState.ALL     // Catch: java.lang.Exception -> L66
            java.lang.String r5 = "Report configured to be sent via DataTransport."
            r6 = 1
            if (r3 != r4) goto L1d
            com.google.firebase.crashlytics.internal.Logger r9 = com.google.firebase.crashlytics.internal.Logger.getLogger()     // Catch: java.lang.Exception -> L66
            r9.d(r5)     // Catch: java.lang.Exception -> L66
            goto L32
        L1d:
            com.google.firebase.crashlytics.internal.common.DataTransportState r3 = r7.dataTransportState     // Catch: java.lang.Exception -> L66
            com.google.firebase.crashlytics.internal.common.DataTransportState r4 = com.google.firebase.crashlytics.internal.common.DataTransportState.JAVA_ONLY     // Catch: java.lang.Exception -> L66
            if (r3 != r4) goto L34
            com.google.firebase.crashlytics.internal.report.model.Report$Type r3 = r8.getType()     // Catch: java.lang.Exception -> L66
            com.google.firebase.crashlytics.internal.report.model.Report$Type r4 = com.google.firebase.crashlytics.internal.report.model.Report.Type.JAVA     // Catch: java.lang.Exception -> L66
            if (r3 != r4) goto L34
            com.google.firebase.crashlytics.internal.Logger r9 = com.google.firebase.crashlytics.internal.Logger.getLogger()     // Catch: java.lang.Exception -> L66
            r9.d(r5)     // Catch: java.lang.Exception -> L66
        L32:
            r9 = r6
            goto L5d
        L34:
            com.google.firebase.crashlytics.internal.report.network.CreateReportSpiCall r3 = r7.createReportCall     // Catch: java.lang.Exception -> L66
            boolean r9 = r3.invoke(r2, r9)     // Catch: java.lang.Exception -> L66
            com.google.firebase.crashlytics.internal.Logger r2 = com.google.firebase.crashlytics.internal.Logger.getLogger()     // Catch: java.lang.Exception -> L66
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch: java.lang.Exception -> L66
            r3.<init>(r0)     // Catch: java.lang.Exception -> L66
            if (r9 == 0) goto L48
            java.lang.String r0 = "complete: "
            goto L4a
        L48:
            java.lang.String r0 = "FAILED: "
        L4a:
            java.lang.StringBuilder r0 = r3.append(r0)     // Catch: java.lang.Exception -> L66
            java.lang.String r3 = r8.getIdentifier()     // Catch: java.lang.Exception -> L66
            java.lang.StringBuilder r0 = r0.append(r3)     // Catch: java.lang.Exception -> L66
            java.lang.String r0 = r0.toString()     // Catch: java.lang.Exception -> L66
            r2.i(r0)     // Catch: java.lang.Exception -> L66
        L5d:
            if (r9 == 0) goto L7d
            com.google.firebase.crashlytics.internal.report.ReportManager r9 = r7.reportManager     // Catch: java.lang.Exception -> L66
            r9.deleteReport(r8)     // Catch: java.lang.Exception -> L66
            r1 = r6
            goto L7d
        L66:
            r9 = move-exception
            com.google.firebase.crashlytics.internal.Logger r0 = com.google.firebase.crashlytics.internal.Logger.getLogger()
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r3 = "Error occurred sending report "
            r2.<init>(r3)
            java.lang.StringBuilder r8 = r2.append(r8)
            java.lang.String r8 = r8.toString()
            r0.e(r8, r9)
        L7d:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.crashlytics.internal.report.ReportUploader.uploadReport(com.google.firebase.crashlytics.internal.report.model.Report, boolean):boolean");
    }

    private class Worker extends BackgroundPriorityRunnable {
        private final boolean dataCollectionToken;
        private final float delay;
        private final List<Report> reports;

        Worker(List<Report> list, boolean z, float f) {
            this.reports = list;
            this.dataCollectionToken = z;
            this.delay = f;
        }

        @Override // com.google.firebase.crashlytics.internal.common.BackgroundPriorityRunnable
        public void onRun() {
            try {
                attemptUploadWithRetry(this.reports, this.dataCollectionToken);
            } catch (Exception e) {
                Logger.getLogger().e("An unexpected error occurred while attempting to upload crash reports.", e);
            }
            ReportUploader.this.uploadThread = null;
        }

        private void attemptUploadWithRetry(List<Report> list, boolean z) throws InterruptedException {
            Logger.getLogger().d("Starting report processing in " + this.delay + " second(s)...");
            float f = this.delay;
            if (f > 0.0f) {
                try {
                    Thread.sleep((long) (f * 1000.0f));
                } catch (InterruptedException unused) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
            if (ReportUploader.this.handlingExceptionCheck.isHandlingException()) {
                return;
            }
            int i = 0;
            while (list.size() > 0 && !ReportUploader.this.handlingExceptionCheck.isHandlingException()) {
                Logger.getLogger().d("Attempting to send " + list.size() + " report(s)");
                ArrayList arrayList = new ArrayList();
                for (Report report : list) {
                    if (!ReportUploader.this.uploadReport(report, z)) {
                        arrayList.add(report);
                    }
                }
                if (arrayList.size() > 0) {
                    int i2 = i + 1;
                    long j = ReportUploader.RETRY_INTERVALS[Math.min(i, ReportUploader.RETRY_INTERVALS.length - 1)];
                    Logger.getLogger().d("Report submission: scheduling delayed retry in " + j + " seconds");
                    try {
                        Thread.sleep(j * 1000);
                        i = i2;
                    } catch (InterruptedException unused2) {
                        Thread.currentThread().interrupt();
                        return;
                    }
                }
                list = arrayList;
            }
        }
    }
}
