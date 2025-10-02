package com.google.firebase.crashlytics.internal.persistence;

import java.io.File;
import java.util.Comparator;

/* loaded from: classes2.dex */
final /* synthetic */ class CrashlyticsReportPersistence$$Lambda$4 implements Comparator {
    private static final CrashlyticsReportPersistence$$Lambda$4 instance = new CrashlyticsReportPersistence$$Lambda$4();

    private CrashlyticsReportPersistence$$Lambda$4() {
    }

    @Override // java.util.Comparator
    public int compare(Object obj, Object obj2) {
        return CrashlyticsReportPersistence.oldestEventFileFirst((File) obj, (File) obj2);
    }
}
