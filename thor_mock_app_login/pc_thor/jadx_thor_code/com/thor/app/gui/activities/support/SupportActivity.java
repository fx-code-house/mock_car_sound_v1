package com.thor.app.gui.activities.support;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.carsystems.thor.app.databinding.ActivitySupportBinding;
import com.google.android.gms.common.internal.ImagesContract;
import com.thor.app.settings.Settings;
import com.thor.app.utils.theming.ThemingUtil;
import com.thor.networkmodule.model.responses.SignInResponse;
import im.delight.android.webview.AdvancedWebView;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import timber.log.Timber;

/* compiled from: SupportActivity.kt */
@Metadata(d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\n\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u00012\u00020\u0002B\u0005¢\u0006\u0002\u0010\u0003J\b\u0010\u0006\u001a\u00020\u0007H\u0002J\b\u0010\b\u001a\u00020\u0007H\u0002J\b\u0010\t\u001a\u00020\u0007H\u0003J\"\u0010\n\u001a\u00020\u00072\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\f2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fH\u0014J\u0012\u0010\u0010\u001a\u00020\u00072\b\u0010\u0011\u001a\u0004\u0018\u00010\u0012H\u0014JB\u0010\u0013\u001a\u00020\u00072\b\u0010\u0014\u001a\u0004\u0018\u00010\u00152\b\u0010\u0016\u001a\u0004\u0018\u00010\u00152\b\u0010\u0017\u001a\u0004\u0018\u00010\u00152\u0006\u0010\u0018\u001a\u00020\u00192\b\u0010\u001a\u001a\u0004\u0018\u00010\u00152\b\u0010\u001b\u001a\u0004\u0018\u00010\u0015H\u0016J\u0012\u0010\u001c\u001a\u00020\u00072\b\u0010\u0014\u001a\u0004\u0018\u00010\u0015H\u0016J$\u0010\u001d\u001a\u00020\u00072\u0006\u0010\u001e\u001a\u00020\f2\b\u0010\u001f\u001a\u0004\u0018\u00010\u00152\b\u0010 \u001a\u0004\u0018\u00010\u0015H\u0016J\u0012\u0010!\u001a\u00020\u00072\b\u0010\u0014\u001a\u0004\u0018\u00010\u0015H\u0016J\u001c\u0010\"\u001a\u00020\u00072\b\u0010\u0014\u001a\u0004\u0018\u00010\u00152\b\u0010#\u001a\u0004\u0018\u00010$H\u0016R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082.¢\u0006\u0002\n\u0000¨\u0006%"}, d2 = {"Lcom/thor/app/gui/activities/support/SupportActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "Lim/delight/android/webview/AdvancedWebView$Listener;", "()V", "binding", "Lcom/carsystems/thor/app/databinding/ActivitySupportBinding;", "init", "", "initListeners", "initWebView", "onActivityResult", "requestCode", "", "resultCode", "data", "Landroid/content/Intent;", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onDownloadRequested", ImagesContract.URL, "", "suggestedFilename", "mimeType", "contentLength", "", "contentDisposition", "userAgent", "onExternalPageRequest", "onPageError", "errorCode", "description", "failingUrl", "onPageFinished", "onPageStarted", "favicon", "Landroid/graphics/Bitmap;", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class SupportActivity extends AppCompatActivity implements AdvancedWebView.Listener {
    private ActivitySupportBinding binding;

    @Override // im.delight.android.webview.AdvancedWebView.Listener
    public void onExternalPageRequest(String url) {
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ThemingUtil.INSTANCE.onActivityCreateSetTheme(this);
        ActivitySupportBinding activitySupportBindingInflate = ActivitySupportBinding.inflate(getLayoutInflater());
        Intrinsics.checkNotNullExpressionValue(activitySupportBindingInflate, "inflate(layoutInflater)");
        this.binding = activitySupportBindingInflate;
        if (activitySupportBindingInflate == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySupportBindingInflate = null;
        }
        setContentView(activitySupportBindingInflate.getRoot());
        init();
    }

    private final void init() {
        ActivitySupportBinding activitySupportBinding = this.binding;
        if (activitySupportBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySupportBinding = null;
        }
        activitySupportBinding.toolbarWidget.setHomeButtonVisibility(true);
        initWebView();
        initListeners();
    }

    private final void initListeners() {
        ActivitySupportBinding activitySupportBinding = this.binding;
        ActivitySupportBinding activitySupportBinding2 = null;
        if (activitySupportBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySupportBinding = null;
        }
        activitySupportBinding.toolbarWidget.setHomeOnClickListener(new View.OnClickListener() { // from class: com.thor.app.gui.activities.support.SupportActivity$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SupportActivity.initListeners$lambda$0(this.f$0, view);
            }
        });
        ActivitySupportBinding activitySupportBinding3 = this.binding;
        if (activitySupportBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySupportBinding3 = null;
        }
        activitySupportBinding3.swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() { // from class: com.thor.app.gui.activities.support.SupportActivity$$ExternalSyntheticLambda1
            @Override // androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
            public final void onRefresh() {
                SupportActivity.initListeners$lambda$1(this.f$0);
            }
        });
        ActivitySupportBinding activitySupportBinding4 = this.binding;
        if (activitySupportBinding4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            activitySupportBinding2 = activitySupportBinding4;
        }
        activitySupportBinding2.swipeRefresh.setEnabled(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initListeners$lambda$0(SupportActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initListeners$lambda$1(SupportActivity this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        ActivitySupportBinding activitySupportBinding = this$0.binding;
        if (activitySupportBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySupportBinding = null;
        }
        activitySupportBinding.webView.reload();
    }

    private final void initWebView() {
        ActivitySupportBinding activitySupportBinding = this.binding;
        if (activitySupportBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySupportBinding = null;
        }
        activitySupportBinding.webView.getSettings().setJavaScriptEnabled(true);
        ActivitySupportBinding activitySupportBinding2 = this.binding;
        if (activitySupportBinding2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySupportBinding2 = null;
        }
        activitySupportBinding2.webView.getSettings().setDomStorageEnabled(true);
        ActivitySupportBinding activitySupportBinding3 = this.binding;
        if (activitySupportBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySupportBinding3 = null;
        }
        activitySupportBinding3.webView.getSettings().setAllowFileAccess(true);
        ActivitySupportBinding activitySupportBinding4 = this.binding;
        if (activitySupportBinding4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySupportBinding4 = null;
        }
        activitySupportBinding4.webView.getSettings().setCacheMode(-1);
        ActivitySupportBinding activitySupportBinding5 = this.binding;
        if (activitySupportBinding5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySupportBinding5 = null;
        }
        activitySupportBinding5.webView.getSettings().setDisplayZoomControls(false);
        ActivitySupportBinding activitySupportBinding6 = this.binding;
        if (activitySupportBinding6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySupportBinding6 = null;
        }
        activitySupportBinding6.webView.getSettings().setBuiltInZoomControls(true);
        ActivitySupportBinding activitySupportBinding7 = this.binding;
        if (activitySupportBinding7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySupportBinding7 = null;
        }
        activitySupportBinding7.webView.getSettings().setSupportZoom(false);
        ActivitySupportBinding activitySupportBinding8 = this.binding;
        if (activitySupportBinding8 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySupportBinding8 = null;
        }
        activitySupportBinding8.webView.setMixedContentAllowed(true);
        ActivitySupportBinding activitySupportBinding9 = this.binding;
        if (activitySupportBinding9 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySupportBinding9 = null;
        }
        activitySupportBinding9.webView.setListener(this, this);
        String language = Locale.getDefault().getLanguage();
        Intrinsics.checkNotNullExpressionValue(language, "getDefault().language");
        String str = Settings.LANGUAGE_CODE_RU;
        if (!StringsKt.contains$default((CharSequence) language, (CharSequence) Settings.LANGUAGE_CODE_RU, false, 2, (Object) null)) {
            String language2 = Locale.getDefault().getLanguage();
            Intrinsics.checkNotNullExpressionValue(language2, "getDefault().language");
            str = Settings.LANGUAGE_CODE_DE;
            if (!StringsKt.contains$default((CharSequence) language2, (CharSequence) Settings.LANGUAGE_CODE_DE, false, 2, (Object) null)) {
                str = Settings.LANGUAGE_CODE_EN;
            }
        }
        if (Settings.getSelectedServer() == 1092) {
            ActivitySupportBinding activitySupportBinding10 = this.binding;
            if (activitySupportBinding10 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                activitySupportBinding10 = null;
            }
            AdvancedWebView advancedWebView = activitySupportBinding10.webView;
            SignInResponse profile = Settings.INSTANCE.getProfile();
            advancedWebView.loadUrl("https://dev.thor-tuning.com/support?deviceSn=" + (profile != null ? profile.getDeviceSN() : null) + "&language=" + str);
            return;
        }
        ActivitySupportBinding activitySupportBinding11 = this.binding;
        if (activitySupportBinding11 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySupportBinding11 = null;
        }
        AdvancedWebView advancedWebView2 = activitySupportBinding11.webView;
        SignInResponse profile2 = Settings.INSTANCE.getProfile();
        advancedWebView2.loadUrl("https://sec2.thor-tuning.com/support?deviceSn=" + (profile2 != null ? profile2.getDeviceSN() : null) + "&language=" + str);
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ActivitySupportBinding activitySupportBinding = this.binding;
        if (activitySupportBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySupportBinding = null;
        }
        activitySupportBinding.webView.onActivityResult(requestCode, resultCode, data);
    }

    @Override // im.delight.android.webview.AdvancedWebView.Listener
    public void onPageStarted(String url, Bitmap favicon) {
        ActivitySupportBinding activitySupportBinding = this.binding;
        if (activitySupportBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySupportBinding = null;
        }
        activitySupportBinding.swipeRefresh.setRefreshing(true);
    }

    @Override // im.delight.android.webview.AdvancedWebView.Listener
    public void onPageFinished(String url) {
        ActivitySupportBinding activitySupportBinding = this.binding;
        if (activitySupportBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySupportBinding = null;
        }
        activitySupportBinding.swipeRefresh.setRefreshing(false);
    }

    @Override // im.delight.android.webview.AdvancedWebView.Listener
    public void onPageError(int errorCode, String description, String failingUrl) {
        Timber.INSTANCE.d("WebView: %s", description);
    }

    @Override // im.delight.android.webview.AdvancedWebView.Listener
    public void onDownloadRequested(String url, String suggestedFilename, String mimeType, long contentLength, String contentDisposition, String userAgent) {
        AdvancedWebView.handleDownload(this, url, suggestedFilename);
    }
}
