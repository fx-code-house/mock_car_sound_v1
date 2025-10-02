package com.google.firebase.crashlytics.internal.report.network;

import com.google.firebase.crashlytics.internal.Logger;
import com.google.firebase.crashlytics.internal.common.AbstractSpiCall;
import com.google.firebase.crashlytics.internal.common.ResponseParser;
import com.google.firebase.crashlytics.internal.network.HttpMethod;
import com.google.firebase.crashlytics.internal.network.HttpRequest;
import com.google.firebase.crashlytics.internal.network.HttpRequestFactory;
import com.google.firebase.crashlytics.internal.network.HttpResponse;
import com.google.firebase.crashlytics.internal.report.model.CreateReportRequest;
import com.google.firebase.crashlytics.internal.report.model.Report;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

/* loaded from: classes2.dex */
public class DefaultCreateReportSpiCall extends AbstractSpiCall implements CreateReportSpiCall {
    static final String FILE_CONTENT_TYPE = "application/octet-stream";
    static final String FILE_PARAM = "report[file]";
    static final String IDENTIFIER_PARAM = "report[identifier]";
    static final String MULTI_FILE_PARAM = "report[file";
    private final String version;

    public DefaultCreateReportSpiCall(String str, String str2, HttpRequestFactory httpRequestFactory, String str3) {
        this(str, str2, httpRequestFactory, HttpMethod.POST, str3);
    }

    DefaultCreateReportSpiCall(String str, String str2, HttpRequestFactory httpRequestFactory, HttpMethod httpMethod, String str3) {
        super(str, str2, httpRequestFactory, httpMethod);
        this.version = str3;
    }

    @Override // com.google.firebase.crashlytics.internal.report.network.CreateReportSpiCall
    public boolean invoke(CreateReportRequest createReportRequest, boolean z) {
        if (!z) {
            throw new RuntimeException("An invalid data collection token was used.");
        }
        HttpRequest httpRequestApplyMultipartDataTo = applyMultipartDataTo(applyHeadersTo(getHttpRequest(), createReportRequest), createReportRequest.report);
        Logger.getLogger().d("Sending report to: " + getUrl());
        try {
            HttpResponse httpResponseExecute = httpRequestApplyMultipartDataTo.execute();
            int iCode = httpResponseExecute.code();
            Logger.getLogger().d("Create report request ID: " + httpResponseExecute.header(AbstractSpiCall.HEADER_REQUEST_ID));
            Logger.getLogger().d("Result was: " + iCode);
            return ResponseParser.parse(iCode) == 0;
        } catch (IOException e) {
            Logger.getLogger().e("Create report HTTP request failed.", e);
            throw new RuntimeException(e);
        }
    }

    private HttpRequest applyHeadersTo(HttpRequest httpRequest, CreateReportRequest createReportRequest) {
        HttpRequest httpRequestHeader = httpRequest.header(AbstractSpiCall.HEADER_GOOGLE_APP_ID, createReportRequest.googleAppId).header(AbstractSpiCall.HEADER_CLIENT_TYPE, AbstractSpiCall.ANDROID_CLIENT_TYPE).header(AbstractSpiCall.HEADER_CLIENT_VERSION, this.version);
        Iterator<Map.Entry<String, String>> it = createReportRequest.report.getCustomHeaders().entrySet().iterator();
        while (it.hasNext()) {
            httpRequestHeader = httpRequestHeader.header(it.next());
        }
        return httpRequestHeader;
    }

    private HttpRequest applyMultipartDataTo(HttpRequest httpRequest, Report report) {
        HttpRequest httpRequestPart = httpRequest.part(IDENTIFIER_PARAM, report.getIdentifier());
        if (report.getFiles().length == 1) {
            Logger.getLogger().d("Adding single file " + report.getFileName() + " to report " + report.getIdentifier());
            return httpRequestPart.part(FILE_PARAM, report.getFileName(), FILE_CONTENT_TYPE, report.getFile());
        }
        int i = 0;
        for (File file : report.getFiles()) {
            Logger.getLogger().d("Adding file " + file.getName() + " to report " + report.getIdentifier());
            httpRequestPart = httpRequestPart.part(MULTI_FILE_PARAM + i + "]", file.getName(), FILE_CONTENT_TYPE, file);
            i++;
        }
        return httpRequestPart;
    }
}
