package im.delight.android.webview;

import android.app.Activity;
import android.app.DownloadManager;
import android.app.Fragment;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Environment;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Base64;
import android.view.InputEvent;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ClientCertRequest;
import android.webkit.ConsoleMessage;
import android.webkit.CookieManager;
import android.webkit.DownloadListener;
import android.webkit.GeolocationPermissions;
import android.webkit.HttpAuthHandler;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.PermissionRequest;
import android.webkit.SslErrorHandler;
import android.webkit.URLUtil;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebStorage;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import kotlin.text.Typography;

/* loaded from: classes3.dex */
public class AdvancedWebView extends WebView {
    protected static final String[] ALTERNATIVE_BROWSERS = {"org.mozilla.firefox", "com.android.chrome", "com.opera.browser", "org.mozilla.firefox_beta", "com.chrome.beta", "com.opera.browser.beta"};
    protected static final String CHARSET_DEFAULT = "UTF-8";
    protected static final String DATABASES_SUB_FOLDER = "/databases";
    protected static final String LANGUAGE_DEFAULT_ISO3 = "eng";
    public static final String PACKAGE_NAME_DOWNLOAD_MANAGER = "com.android.providers.downloads";
    protected static final int REQUEST_CODE_FILE_PICKER = 51426;
    protected WeakReference<Activity> mActivity;
    protected WebChromeClient mCustomWebChromeClient;
    protected WebViewClient mCustomWebViewClient;
    protected ValueCallback<Uri> mFileUploadCallbackFirst;
    protected ValueCallback<Uri[]> mFileUploadCallbackSecond;
    protected WeakReference<Fragment> mFragment;
    protected boolean mGeolocationEnabled;
    protected final Map<String, String> mHttpHeaders;
    protected String mLanguageIso3;
    protected long mLastError;
    protected Listener mListener;
    protected final List<String> mPermittedHostnames;
    protected int mRequestCodeFilePicker;
    protected String mUploadableFileTypes;

    public interface Listener {
        void onDownloadRequested(String str, String str2, String str3, long j, String str4, String str5);

        void onExternalPageRequest(String str);

        void onPageError(int i, String str, String str2);

        void onPageFinished(String str);

        void onPageStarted(String str, Bitmap bitmap);
    }

    public static boolean isFileUploadAvailable(boolean z) {
        return true;
    }

    public AdvancedWebView(Context context) {
        super(context);
        this.mPermittedHostnames = new LinkedList();
        this.mRequestCodeFilePicker = REQUEST_CODE_FILE_PICKER;
        this.mUploadableFileTypes = "*/*";
        this.mHttpHeaders = new HashMap();
        init(context);
    }

    public AdvancedWebView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mPermittedHostnames = new LinkedList();
        this.mRequestCodeFilePicker = REQUEST_CODE_FILE_PICKER;
        this.mUploadableFileTypes = "*/*";
        this.mHttpHeaders = new HashMap();
        init(context);
    }

    public AdvancedWebView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mPermittedHostnames = new LinkedList();
        this.mRequestCodeFilePicker = REQUEST_CODE_FILE_PICKER;
        this.mUploadableFileTypes = "*/*";
        this.mHttpHeaders = new HashMap();
        init(context);
    }

    public void setListener(Activity activity, Listener listener) {
        setListener(activity, listener, REQUEST_CODE_FILE_PICKER);
    }

    public void setListener(Activity activity, Listener listener, int i) {
        if (activity != null) {
            this.mActivity = new WeakReference<>(activity);
        } else {
            this.mActivity = null;
        }
        setListener(listener, i);
    }

    public void setListener(Fragment fragment, Listener listener) {
        setListener(fragment, listener, REQUEST_CODE_FILE_PICKER);
    }

    public void setListener(Fragment fragment, Listener listener, int i) {
        if (fragment != null) {
            this.mFragment = new WeakReference<>(fragment);
        } else {
            this.mFragment = null;
        }
        setListener(listener, i);
    }

    protected void setListener(Listener listener, int i) {
        this.mListener = listener;
        this.mRequestCodeFilePicker = i;
    }

    @Override // android.webkit.WebView
    public void setWebViewClient(WebViewClient webViewClient) {
        this.mCustomWebViewClient = webViewClient;
    }

    @Override // android.webkit.WebView
    public void setWebChromeClient(WebChromeClient webChromeClient) {
        this.mCustomWebChromeClient = webChromeClient;
    }

    public void setGeolocationEnabled(boolean z) {
        if (z) {
            getSettings().setJavaScriptEnabled(true);
            getSettings().setGeolocationEnabled(true);
            setGeolocationDatabasePath();
        }
        this.mGeolocationEnabled = z;
    }

    protected void setGeolocationDatabasePath() {
        Activity activity;
        WeakReference<Fragment> weakReference = this.mFragment;
        if (weakReference != null && weakReference.get() != null && this.mFragment.get().getActivity() != null) {
            activity = this.mFragment.get().getActivity();
        } else {
            WeakReference<Activity> weakReference2 = this.mActivity;
            if (weakReference2 == null || weakReference2.get() == null) {
                return;
            } else {
                activity = this.mActivity.get();
            }
        }
        getSettings().setGeolocationDatabasePath(activity.getFilesDir().getPath());
    }

    public void setUploadableFileTypes(String str) {
        this.mUploadableFileTypes = str;
    }

    public void loadHtml(String str) {
        loadHtml(str, null);
    }

    public void loadHtml(String str, String str2) {
        loadHtml(str, str2, null);
    }

    public void loadHtml(String str, String str2, String str3) {
        loadHtml(str, str2, str3, "utf-8");
    }

    public void loadHtml(String str, String str2, String str3, String str4) {
        loadDataWithBaseURL(str2, str, "text/html", str4, str3);
    }

    @Override // android.webkit.WebView
    public void onResume() {
        super.onResume();
        resumeTimers();
    }

    @Override // android.webkit.WebView
    public void onPause() {
        pauseTimers();
        super.onPause();
    }

    public void onDestroy() {
        try {
            ((ViewGroup) getParent()).removeView(this);
        } catch (Exception unused) {
        }
        try {
            removeAllViews();
        } catch (Exception unused2) {
        }
        destroy();
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        Uri[] uriArr;
        Uri[] uriArr2;
        int i3;
        if (i == this.mRequestCodeFilePicker) {
            if (i2 != -1) {
                ValueCallback<Uri> valueCallback = this.mFileUploadCallbackFirst;
                if (valueCallback != null) {
                    valueCallback.onReceiveValue(null);
                    this.mFileUploadCallbackFirst = null;
                    return;
                }
                ValueCallback<Uri[]> valueCallback2 = this.mFileUploadCallbackSecond;
                if (valueCallback2 != null) {
                    valueCallback2.onReceiveValue(null);
                    this.mFileUploadCallbackSecond = null;
                    return;
                }
                return;
            }
            if (intent != null) {
                ValueCallback<Uri> valueCallback3 = this.mFileUploadCallbackFirst;
                if (valueCallback3 != null) {
                    valueCallback3.onReceiveValue(intent.getData());
                    this.mFileUploadCallbackFirst = null;
                    return;
                }
                if (this.mFileUploadCallbackSecond != null) {
                    try {
                    } catch (Exception unused) {
                        uriArr = null;
                    }
                    if (intent.getDataString() != null) {
                        uriArr2 = new Uri[]{Uri.parse(intent.getDataString())};
                    } else if (intent.getClipData() != null) {
                        int itemCount = intent.getClipData().getItemCount();
                        uriArr = new Uri[itemCount];
                        for (i3 = 0; i3 < itemCount; i3++) {
                            try {
                                uriArr[i3] = intent.getClipData().getItemAt(i3).getUri();
                            } catch (Exception unused2) {
                            }
                        }
                        uriArr2 = uriArr;
                    } else {
                        uriArr2 = null;
                    }
                    this.mFileUploadCallbackSecond.onReceiveValue(uriArr2);
                    this.mFileUploadCallbackSecond = null;
                }
            }
        }
    }

    public void addHttpHeader(String str, String str2) {
        this.mHttpHeaders.put(str, str2);
    }

    public void removeHttpHeader(String str) {
        this.mHttpHeaders.remove(str);
    }

    public void addPermittedHostname(String str) {
        this.mPermittedHostnames.add(str);
    }

    public void addPermittedHostnames(Collection<? extends String> collection) {
        this.mPermittedHostnames.addAll(collection);
    }

    public List<String> getPermittedHostnames() {
        return this.mPermittedHostnames;
    }

    public void removePermittedHostname(String str) {
        this.mPermittedHostnames.remove(str);
    }

    public void clearPermittedHostnames() {
        this.mPermittedHostnames.clear();
    }

    public boolean onBackPressed() {
        if (!canGoBack()) {
            return true;
        }
        goBack();
        return false;
    }

    protected static void setAllowAccessFromFileUrls(WebSettings webSettings, boolean z) {
        webSettings.setAllowFileAccessFromFileURLs(z);
        webSettings.setAllowUniversalAccessFromFileURLs(z);
    }

    public void setCookiesEnabled(boolean z) {
        CookieManager.getInstance().setAcceptCookie(z);
    }

    public void setThirdPartyCookiesEnabled(boolean z) {
        CookieManager.getInstance().setAcceptThirdPartyCookies(this, z);
    }

    public void setMixedContentAllowed(boolean z) {
        setMixedContentAllowed(getSettings(), z);
    }

    protected void setMixedContentAllowed(WebSettings webSettings, boolean z) {
        webSettings.setMixedContentMode(!z ? 1 : 0);
    }

    public void setDesktopMode(boolean z) {
        String strReplace;
        WebSettings settings = getSettings();
        if (z) {
            strReplace = settings.getUserAgentString().replace("Mobile", "eliboM").replace("Android", "diordnA");
        } else {
            strReplace = settings.getUserAgentString().replace("eliboM", "Mobile").replace("diordnA", "Android");
        }
        settings.setUserAgentString(strReplace);
        settings.setUseWideViewPort(z);
        settings.setLoadWithOverviewMode(z);
        settings.setSupportZoom(z);
        settings.setBuiltInZoomControls(z);
    }

    protected void init(Context context) {
        if (isInEditMode()) {
            return;
        }
        if (context instanceof Activity) {
            this.mActivity = new WeakReference<>((Activity) context);
        }
        this.mLanguageIso3 = getLanguageIso3();
        setFocusable(true);
        setFocusableInTouchMode(true);
        setSaveEnabled(true);
        String path = context.getFilesDir().getPath();
        String str = path.substring(0, path.lastIndexOf("/")) + DATABASES_SUB_FOLDER;
        WebSettings settings = getSettings();
        settings.setAllowFileAccess(false);
        setAllowAccessFromFileUrls(settings, false);
        settings.setBuiltInZoomControls(false);
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setMixedContentMode(2);
        setThirdPartyCookiesEnabled(true);
        super.setWebViewClient(new WebViewClient() { // from class: im.delight.android.webview.AdvancedWebView.1
            @Override // android.webkit.WebViewClient
            public void onPageStarted(WebView webView, String str2, Bitmap bitmap) {
                if (!AdvancedWebView.this.hasError() && AdvancedWebView.this.mListener != null) {
                    AdvancedWebView.this.mListener.onPageStarted(str2, bitmap);
                }
                if (AdvancedWebView.this.mCustomWebViewClient != null) {
                    AdvancedWebView.this.mCustomWebViewClient.onPageStarted(webView, str2, bitmap);
                }
            }

            @Override // android.webkit.WebViewClient
            public void onPageFinished(WebView webView, String str2) {
                if (!AdvancedWebView.this.hasError() && AdvancedWebView.this.mListener != null) {
                    AdvancedWebView.this.mListener.onPageFinished(str2);
                }
                if (AdvancedWebView.this.mCustomWebViewClient != null) {
                    AdvancedWebView.this.mCustomWebViewClient.onPageFinished(webView, str2);
                }
            }

            @Override // android.webkit.WebViewClient
            public void onReceivedError(WebView webView, int i, String str2, String str3) {
                AdvancedWebView.this.setLastError();
                if (AdvancedWebView.this.mListener != null) {
                    AdvancedWebView.this.mListener.onPageError(i, str2, str3);
                }
                if (AdvancedWebView.this.mCustomWebViewClient != null) {
                    AdvancedWebView.this.mCustomWebViewClient.onReceivedError(webView, i, str2, str3);
                }
            }

            @Override // android.webkit.WebViewClient
            public boolean shouldOverrideUrlLoading(WebView webView, String str2) {
                Intent intent;
                if (!AdvancedWebView.this.isPermittedUrl(str2)) {
                    if (AdvancedWebView.this.mListener != null) {
                        AdvancedWebView.this.mListener.onExternalPageRequest(str2);
                    }
                    return true;
                }
                if (AdvancedWebView.this.mCustomWebViewClient != null && AdvancedWebView.this.mCustomWebViewClient.shouldOverrideUrlLoading(webView, str2)) {
                    return true;
                }
                Uri uri = Uri.parse(str2);
                String scheme = uri.getScheme();
                if (scheme != null) {
                    if (scheme.equals("tel")) {
                        intent = new Intent("android.intent.action.DIAL", uri);
                    } else if (scheme.equals("sms") || scheme.equals("mailto")) {
                        intent = new Intent("android.intent.action.SENDTO", uri);
                    } else if (scheme.equals("whatsapp")) {
                        intent = new Intent("android.intent.action.SENDTO", uri);
                        intent.setPackage("com.whatsapp");
                    } else {
                        intent = null;
                    }
                    if (intent != null) {
                        intent.addFlags(268435456);
                        try {
                            if (AdvancedWebView.this.mActivity != null && AdvancedWebView.this.mActivity.get() != null) {
                                AdvancedWebView.this.mActivity.get().startActivity(intent);
                            } else {
                                AdvancedWebView.this.getContext().startActivity(intent);
                            }
                        } catch (ActivityNotFoundException unused) {
                        }
                        return true;
                    }
                }
                webView.loadUrl(str2);
                return true;
            }

            @Override // android.webkit.WebViewClient
            public void onLoadResource(WebView webView, String str2) {
                if (AdvancedWebView.this.mCustomWebViewClient != null) {
                    AdvancedWebView.this.mCustomWebViewClient.onLoadResource(webView, str2);
                } else {
                    super.onLoadResource(webView, str2);
                }
            }

            @Override // android.webkit.WebViewClient
            public WebResourceResponse shouldInterceptRequest(WebView webView, String str2) {
                if (AdvancedWebView.this.mCustomWebViewClient != null) {
                    return AdvancedWebView.this.mCustomWebViewClient.shouldInterceptRequest(webView, str2);
                }
                return super.shouldInterceptRequest(webView, str2);
            }

            @Override // android.webkit.WebViewClient
            public WebResourceResponse shouldInterceptRequest(WebView webView, WebResourceRequest webResourceRequest) {
                if (AdvancedWebView.this.mCustomWebViewClient != null) {
                    return AdvancedWebView.this.mCustomWebViewClient.shouldInterceptRequest(webView, webResourceRequest);
                }
                return super.shouldInterceptRequest(webView, webResourceRequest);
            }

            @Override // android.webkit.WebViewClient
            public void onFormResubmission(WebView webView, Message message, Message message2) {
                if (AdvancedWebView.this.mCustomWebViewClient != null) {
                    AdvancedWebView.this.mCustomWebViewClient.onFormResubmission(webView, message, message2);
                } else {
                    super.onFormResubmission(webView, message, message2);
                }
            }

            @Override // android.webkit.WebViewClient
            public void doUpdateVisitedHistory(WebView webView, String str2, boolean z) {
                if (AdvancedWebView.this.mCustomWebViewClient != null) {
                    AdvancedWebView.this.mCustomWebViewClient.doUpdateVisitedHistory(webView, str2, z);
                } else {
                    super.doUpdateVisitedHistory(webView, str2, z);
                }
            }

            @Override // android.webkit.WebViewClient
            public void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {
                if (AdvancedWebView.this.mCustomWebViewClient != null) {
                    AdvancedWebView.this.mCustomWebViewClient.onReceivedSslError(webView, sslErrorHandler, sslError);
                } else {
                    super.onReceivedSslError(webView, sslErrorHandler, sslError);
                }
            }

            @Override // android.webkit.WebViewClient
            public void onReceivedClientCertRequest(WebView webView, ClientCertRequest clientCertRequest) {
                if (AdvancedWebView.this.mCustomWebViewClient != null) {
                    AdvancedWebView.this.mCustomWebViewClient.onReceivedClientCertRequest(webView, clientCertRequest);
                } else {
                    super.onReceivedClientCertRequest(webView, clientCertRequest);
                }
            }

            @Override // android.webkit.WebViewClient
            public void onReceivedHttpAuthRequest(WebView webView, HttpAuthHandler httpAuthHandler, String str2, String str3) {
                if (AdvancedWebView.this.mCustomWebViewClient != null) {
                    AdvancedWebView.this.mCustomWebViewClient.onReceivedHttpAuthRequest(webView, httpAuthHandler, str2, str3);
                } else {
                    super.onReceivedHttpAuthRequest(webView, httpAuthHandler, str2, str3);
                }
            }

            @Override // android.webkit.WebViewClient
            public boolean shouldOverrideKeyEvent(WebView webView, KeyEvent keyEvent) {
                if (AdvancedWebView.this.mCustomWebViewClient != null) {
                    return AdvancedWebView.this.mCustomWebViewClient.shouldOverrideKeyEvent(webView, keyEvent);
                }
                return super.shouldOverrideKeyEvent(webView, keyEvent);
            }

            @Override // android.webkit.WebViewClient
            public void onUnhandledKeyEvent(WebView webView, KeyEvent keyEvent) {
                if (AdvancedWebView.this.mCustomWebViewClient != null) {
                    AdvancedWebView.this.mCustomWebViewClient.onUnhandledKeyEvent(webView, keyEvent);
                } else {
                    super.onUnhandledKeyEvent(webView, keyEvent);
                }
            }

            public void onUnhandledInputEvent(WebView webView, InputEvent inputEvent) {
                if (AdvancedWebView.this.mCustomWebViewClient != null) {
                    AdvancedWebView.this.mCustomWebViewClient.onUnhandledInputEvent(webView, inputEvent);
                } else {
                    super.onUnhandledInputEvent(webView, inputEvent);
                }
            }

            @Override // android.webkit.WebViewClient
            public void onScaleChanged(WebView webView, float f, float f2) {
                if (AdvancedWebView.this.mCustomWebViewClient != null) {
                    AdvancedWebView.this.mCustomWebViewClient.onScaleChanged(webView, f, f2);
                } else {
                    super.onScaleChanged(webView, f, f2);
                }
            }

            @Override // android.webkit.WebViewClient
            public void onReceivedLoginRequest(WebView webView, String str2, String str3, String str4) {
                if (AdvancedWebView.this.mCustomWebViewClient != null) {
                    AdvancedWebView.this.mCustomWebViewClient.onReceivedLoginRequest(webView, str2, str3, str4);
                } else {
                    super.onReceivedLoginRequest(webView, str2, str3, str4);
                }
            }
        });
        super.setWebChromeClient(new WebChromeClient() { // from class: im.delight.android.webview.AdvancedWebView.2
            public void openFileChooser(ValueCallback<Uri> valueCallback) {
                openFileChooser(valueCallback, null);
            }

            public void openFileChooser(ValueCallback<Uri> valueCallback, String str2) {
                openFileChooser(valueCallback, str2, null);
            }

            public void openFileChooser(ValueCallback<Uri> valueCallback, String str2, String str3) {
                AdvancedWebView.this.openFileInput(valueCallback, null, false);
            }

            @Override // android.webkit.WebChromeClient
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> valueCallback, WebChromeClient.FileChooserParams fileChooserParams) {
                AdvancedWebView.this.openFileInput(null, valueCallback, fileChooserParams.getMode() == 1);
                return true;
            }

            @Override // android.webkit.WebChromeClient
            public void onProgressChanged(WebView webView, int i) {
                if (AdvancedWebView.this.mCustomWebChromeClient != null) {
                    AdvancedWebView.this.mCustomWebChromeClient.onProgressChanged(webView, i);
                } else {
                    super.onProgressChanged(webView, i);
                }
            }

            @Override // android.webkit.WebChromeClient
            public void onReceivedTitle(WebView webView, String str2) {
                if (AdvancedWebView.this.mCustomWebChromeClient != null) {
                    AdvancedWebView.this.mCustomWebChromeClient.onReceivedTitle(webView, str2);
                } else {
                    super.onReceivedTitle(webView, str2);
                }
            }

            @Override // android.webkit.WebChromeClient
            public void onReceivedIcon(WebView webView, Bitmap bitmap) {
                if (AdvancedWebView.this.mCustomWebChromeClient != null) {
                    AdvancedWebView.this.mCustomWebChromeClient.onReceivedIcon(webView, bitmap);
                } else {
                    super.onReceivedIcon(webView, bitmap);
                }
            }

            @Override // android.webkit.WebChromeClient
            public void onReceivedTouchIconUrl(WebView webView, String str2, boolean z) {
                if (AdvancedWebView.this.mCustomWebChromeClient != null) {
                    AdvancedWebView.this.mCustomWebChromeClient.onReceivedTouchIconUrl(webView, str2, z);
                } else {
                    super.onReceivedTouchIconUrl(webView, str2, z);
                }
            }

            @Override // android.webkit.WebChromeClient
            public void onShowCustomView(View view, WebChromeClient.CustomViewCallback customViewCallback) {
                if (AdvancedWebView.this.mCustomWebChromeClient != null) {
                    AdvancedWebView.this.mCustomWebChromeClient.onShowCustomView(view, customViewCallback);
                } else {
                    super.onShowCustomView(view, customViewCallback);
                }
            }

            @Override // android.webkit.WebChromeClient
            public void onShowCustomView(View view, int i, WebChromeClient.CustomViewCallback customViewCallback) {
                if (AdvancedWebView.this.mCustomWebChromeClient != null) {
                    AdvancedWebView.this.mCustomWebChromeClient.onShowCustomView(view, i, customViewCallback);
                } else {
                    super.onShowCustomView(view, i, customViewCallback);
                }
            }

            @Override // android.webkit.WebChromeClient
            public void onHideCustomView() {
                if (AdvancedWebView.this.mCustomWebChromeClient != null) {
                    AdvancedWebView.this.mCustomWebChromeClient.onHideCustomView();
                } else {
                    super.onHideCustomView();
                }
            }

            @Override // android.webkit.WebChromeClient
            public boolean onCreateWindow(WebView webView, boolean z, boolean z2, Message message) {
                if (AdvancedWebView.this.mCustomWebChromeClient != null) {
                    return AdvancedWebView.this.mCustomWebChromeClient.onCreateWindow(webView, z, z2, message);
                }
                return super.onCreateWindow(webView, z, z2, message);
            }

            @Override // android.webkit.WebChromeClient
            public void onRequestFocus(WebView webView) {
                if (AdvancedWebView.this.mCustomWebChromeClient != null) {
                    AdvancedWebView.this.mCustomWebChromeClient.onRequestFocus(webView);
                } else {
                    super.onRequestFocus(webView);
                }
            }

            @Override // android.webkit.WebChromeClient
            public void onCloseWindow(WebView webView) {
                if (AdvancedWebView.this.mCustomWebChromeClient != null) {
                    AdvancedWebView.this.mCustomWebChromeClient.onCloseWindow(webView);
                } else {
                    super.onCloseWindow(webView);
                }
            }

            @Override // android.webkit.WebChromeClient
            public boolean onJsAlert(WebView webView, String str2, String str3, JsResult jsResult) {
                if (AdvancedWebView.this.mCustomWebChromeClient != null) {
                    return AdvancedWebView.this.mCustomWebChromeClient.onJsAlert(webView, str2, str3, jsResult);
                }
                return super.onJsAlert(webView, str2, str3, jsResult);
            }

            @Override // android.webkit.WebChromeClient
            public boolean onJsConfirm(WebView webView, String str2, String str3, JsResult jsResult) {
                if (AdvancedWebView.this.mCustomWebChromeClient != null) {
                    return AdvancedWebView.this.mCustomWebChromeClient.onJsConfirm(webView, str2, str3, jsResult);
                }
                return super.onJsConfirm(webView, str2, str3, jsResult);
            }

            @Override // android.webkit.WebChromeClient
            public boolean onJsPrompt(WebView webView, String str2, String str3, String str4, JsPromptResult jsPromptResult) {
                if (AdvancedWebView.this.mCustomWebChromeClient != null) {
                    return AdvancedWebView.this.mCustomWebChromeClient.onJsPrompt(webView, str2, str3, str4, jsPromptResult);
                }
                return super.onJsPrompt(webView, str2, str3, str4, jsPromptResult);
            }

            @Override // android.webkit.WebChromeClient
            public boolean onJsBeforeUnload(WebView webView, String str2, String str3, JsResult jsResult) {
                if (AdvancedWebView.this.mCustomWebChromeClient != null) {
                    return AdvancedWebView.this.mCustomWebChromeClient.onJsBeforeUnload(webView, str2, str3, jsResult);
                }
                return super.onJsBeforeUnload(webView, str2, str3, jsResult);
            }

            @Override // android.webkit.WebChromeClient
            public void onGeolocationPermissionsShowPrompt(String str2, GeolocationPermissions.Callback callback) {
                if (AdvancedWebView.this.mGeolocationEnabled) {
                    callback.invoke(str2, true, false);
                } else if (AdvancedWebView.this.mCustomWebChromeClient != null) {
                    AdvancedWebView.this.mCustomWebChromeClient.onGeolocationPermissionsShowPrompt(str2, callback);
                } else {
                    super.onGeolocationPermissionsShowPrompt(str2, callback);
                }
            }

            @Override // android.webkit.WebChromeClient
            public void onGeolocationPermissionsHidePrompt() {
                if (AdvancedWebView.this.mCustomWebChromeClient != null) {
                    AdvancedWebView.this.mCustomWebChromeClient.onGeolocationPermissionsHidePrompt();
                } else {
                    super.onGeolocationPermissionsHidePrompt();
                }
            }

            @Override // android.webkit.WebChromeClient
            public void onPermissionRequest(PermissionRequest permissionRequest) {
                if (AdvancedWebView.this.mCustomWebChromeClient != null) {
                    AdvancedWebView.this.mCustomWebChromeClient.onPermissionRequest(permissionRequest);
                } else {
                    super.onPermissionRequest(permissionRequest);
                }
            }

            @Override // android.webkit.WebChromeClient
            public void onPermissionRequestCanceled(PermissionRequest permissionRequest) {
                if (AdvancedWebView.this.mCustomWebChromeClient != null) {
                    AdvancedWebView.this.mCustomWebChromeClient.onPermissionRequestCanceled(permissionRequest);
                } else {
                    super.onPermissionRequestCanceled(permissionRequest);
                }
            }

            @Override // android.webkit.WebChromeClient
            public boolean onJsTimeout() {
                if (AdvancedWebView.this.mCustomWebChromeClient != null) {
                    return AdvancedWebView.this.mCustomWebChromeClient.onJsTimeout();
                }
                return super.onJsTimeout();
            }

            @Override // android.webkit.WebChromeClient
            public void onConsoleMessage(String str2, int i, String str3) {
                if (AdvancedWebView.this.mCustomWebChromeClient != null) {
                    AdvancedWebView.this.mCustomWebChromeClient.onConsoleMessage(str2, i, str3);
                } else {
                    super.onConsoleMessage(str2, i, str3);
                }
            }

            @Override // android.webkit.WebChromeClient
            public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
                if (AdvancedWebView.this.mCustomWebChromeClient != null) {
                    return AdvancedWebView.this.mCustomWebChromeClient.onConsoleMessage(consoleMessage);
                }
                return super.onConsoleMessage(consoleMessage);
            }

            @Override // android.webkit.WebChromeClient
            public Bitmap getDefaultVideoPoster() {
                if (AdvancedWebView.this.mCustomWebChromeClient != null) {
                    return AdvancedWebView.this.mCustomWebChromeClient.getDefaultVideoPoster();
                }
                return super.getDefaultVideoPoster();
            }

            @Override // android.webkit.WebChromeClient
            public View getVideoLoadingProgressView() {
                if (AdvancedWebView.this.mCustomWebChromeClient != null) {
                    return AdvancedWebView.this.mCustomWebChromeClient.getVideoLoadingProgressView();
                }
                return super.getVideoLoadingProgressView();
            }

            @Override // android.webkit.WebChromeClient
            public void getVisitedHistory(ValueCallback<String[]> valueCallback) {
                if (AdvancedWebView.this.mCustomWebChromeClient != null) {
                    AdvancedWebView.this.mCustomWebChromeClient.getVisitedHistory(valueCallback);
                } else {
                    super.getVisitedHistory(valueCallback);
                }
            }

            @Override // android.webkit.WebChromeClient
            public void onExceededDatabaseQuota(String str2, String str3, long j, long j2, long j3, WebStorage.QuotaUpdater quotaUpdater) {
                if (AdvancedWebView.this.mCustomWebChromeClient != null) {
                    AdvancedWebView.this.mCustomWebChromeClient.onExceededDatabaseQuota(str2, str3, j, j2, j3, quotaUpdater);
                } else {
                    super.onExceededDatabaseQuota(str2, str3, j, j2, j3, quotaUpdater);
                }
            }

            public void onReachedMaxAppCacheSize(long j, long j2, WebStorage.QuotaUpdater quotaUpdater) {
                if (AdvancedWebView.this.mCustomWebChromeClient != null) {
                    AdvancedWebView.this.mCustomWebChromeClient.onReachedMaxAppCacheSize(j, j2, quotaUpdater);
                } else {
                    super.onReachedMaxAppCacheSize(j, j2, quotaUpdater);
                }
            }
        });
        setDownloadListener(new DownloadListener() { // from class: im.delight.android.webview.AdvancedWebView.3
            @Override // android.webkit.DownloadListener
            public void onDownloadStart(String str2, String str3, String str4, String str5, long j) {
                String strGuessFileName = URLUtil.guessFileName(str2, str4, str5);
                if (AdvancedWebView.this.mListener != null) {
                    AdvancedWebView.this.mListener.onDownloadRequested(str2, strGuessFileName, str5, j, str4, str3);
                }
            }
        });
    }

    @Override // android.webkit.WebView
    public void loadUrl(String str, Map<String, String> map) {
        if (map == null) {
            map = this.mHttpHeaders;
        } else if (this.mHttpHeaders.size() > 0) {
            map.putAll(this.mHttpHeaders);
        }
        super.loadUrl(str, map);
    }

    @Override // android.webkit.WebView
    public void loadUrl(String str) {
        if (this.mHttpHeaders.size() > 0) {
            super.loadUrl(str, this.mHttpHeaders);
        } else {
            super.loadUrl(str);
        }
    }

    public void loadUrl(String str, boolean z) {
        if (z) {
            str = makeUrlUnique(str);
        }
        loadUrl(str);
    }

    public void loadUrl(String str, boolean z, Map<String, String> map) {
        if (z) {
            str = makeUrlUnique(str);
        }
        loadUrl(str, map);
    }

    protected static String makeUrlUnique(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        if (str.contains("?")) {
            sb.append(Typography.amp);
        } else {
            if (str.lastIndexOf(47) <= 7) {
                sb.append('/');
            }
            sb.append('?');
        }
        sb.append(System.currentTimeMillis());
        sb.append("=1");
        return sb.toString();
    }

    public boolean isPermittedUrl(String str) {
        if (this.mPermittedHostnames.size() == 0) {
            return true;
        }
        Uri uri = Uri.parse(str);
        String host = uri.getHost();
        if (host == null || !host.matches("^[a-zA-Z0-9._!~*')(;:&=+$,%\\[\\]-]*$")) {
            return false;
        }
        String userInfo = uri.getUserInfo();
        if (userInfo != null && !userInfo.matches("^[a-zA-Z0-9._!~*')(;:&=+$,%-]*$")) {
            return false;
        }
        for (String str2 : this.mPermittedHostnames) {
            if (host.equals(str2) || host.endsWith("." + str2)) {
                return true;
            }
        }
        return false;
    }

    protected boolean isHostnameAllowed(String str) {
        return isPermittedUrl(str);
    }

    protected void setLastError() {
        this.mLastError = System.currentTimeMillis();
    }

    protected boolean hasError() {
        return this.mLastError + 500 >= System.currentTimeMillis();
    }

    protected static String getLanguageIso3() {
        try {
            return Locale.getDefault().getISO3Language().toLowerCase(Locale.US);
        } catch (MissingResourceException unused) {
            return LANGUAGE_DEFAULT_ISO3;
        }
    }

    protected String getFileUploadPromptLabel() {
        try {
            return this.mLanguageIso3.equals("zho") ? decodeBase64("6YCJ5oup5LiA5Liq5paH5Lu2") : this.mLanguageIso3.equals("spa") ? decodeBase64("RWxpamEgdW4gYXJjaGl2bw==") : this.mLanguageIso3.equals("hin") ? decodeBase64("4KSP4KSVIOCkq+CkvOCkvuCkh+CksiDgpJrgpYHgpKjgpYfgpII=") : this.mLanguageIso3.equals("ben") ? decodeBase64("4KaP4KaV4Kaf4Ka/IOCmq+CmvuCmh+CmsiDgpqjgpr/gprDgp43gpqzgpr7gpprgpqg=") : this.mLanguageIso3.equals("ara") ? decodeBase64("2KfYrtiq2YrYp9ixINmF2YTZgSDZiNin2K3Yrw==") : this.mLanguageIso3.equals("por") ? decodeBase64("RXNjb2xoYSB1bSBhcnF1aXZv") : this.mLanguageIso3.equals("rus") ? decodeBase64("0JLRi9Cx0LXRgNC40YLQtSDQvtC00LjQvSDRhNCw0LnQuw==") : this.mLanguageIso3.equals("jpn") ? decodeBase64("MeODleOCoeOCpOODq+OCkumBuOaKnuOBl+OBpuOBj+OBoOOBleOBhA==") : this.mLanguageIso3.equals("pan") ? decodeBase64("4KiH4Kmx4KiVIOCoq+CovuCoh+CosiDgqJrgqYHgqKPgqYs=") : this.mLanguageIso3.equals("deu") ? decodeBase64("V8OkaGxlIGVpbmUgRGF0ZWk=") : this.mLanguageIso3.equals("jav") ? decodeBase64("UGlsaWggc2lqaSBiZXJrYXM=") : this.mLanguageIso3.equals("msa") ? decodeBase64("UGlsaWggc2F0dSBmYWls") : this.mLanguageIso3.equals("tel") ? decodeBase64("4LCS4LCVIOCwq+CxhuCxluCwsuCxjeCwqOCxgSDgsI7gsILgsJrgsYHgsJXgsYvgsILgsKHgsL8=") : this.mLanguageIso3.equals("vie") ? decodeBase64("Q2jhu41uIG3hu5l0IHThuq1wIHRpbg==") : this.mLanguageIso3.equals("kor") ? decodeBase64("7ZWY64KY7J2YIO2MjOydvOydhCDshKDtg50=") : this.mLanguageIso3.equals("fra") ? decodeBase64("Q2hvaXNpc3NleiB1biBmaWNoaWVy") : this.mLanguageIso3.equals("mar") ? decodeBase64("4KSr4KS+4KSH4KSyIOCkqOCkv+CkteCkoeCkvg==") : this.mLanguageIso3.equals("tam") ? decodeBase64("4K6S4K6w4K+BIOCuleCvh+CuvuCuquCvjeCuquCviCDgrqTgr4fgrrDgr43grrXgr4E=") : this.mLanguageIso3.equals("urd") ? decodeBase64("2KfbjNqpINmB2KfYptmEINmF24zauiDYs9uSINin2YbYqtiu2KfYqCDaqdix24zaug==") : this.mLanguageIso3.equals("fas") ? decodeBase64("2LHYpyDYp9mG2KrYrtin2Kgg2qnZhtuM2K8g24zaqSDZgdin24zZhA==") : this.mLanguageIso3.equals("tur") ? decodeBase64("QmlyIGRvc3lhIHNlw6dpbg==") : this.mLanguageIso3.equals("ita") ? decodeBase64("U2NlZ2xpIHVuIGZpbGU=") : this.mLanguageIso3.equals("tha") ? decodeBase64("4LmA4Lil4Li34Lit4LiB4LmE4Lif4Lil4LmM4Lir4LiZ4Li24LmI4LiH") : this.mLanguageIso3.equals("guj") ? decodeBase64("4KqP4KqVIOCqq+CqvuCqh+CqsuCqqOCrhyDgqqrgqrjgqoLgqqY=") : "Choose a file";
        } catch (Exception unused) {
            return "Choose a file";
        }
    }

    protected static String decodeBase64(String str) throws IllegalArgumentException, UnsupportedEncodingException {
        return new String(Base64.decode(str, 0), "UTF-8");
    }

    protected void openFileInput(ValueCallback<Uri> valueCallback, ValueCallback<Uri[]> valueCallback2, boolean z) {
        ValueCallback<Uri> valueCallback3 = this.mFileUploadCallbackFirst;
        if (valueCallback3 != null) {
            valueCallback3.onReceiveValue(null);
        }
        this.mFileUploadCallbackFirst = valueCallback;
        ValueCallback<Uri[]> valueCallback4 = this.mFileUploadCallbackSecond;
        if (valueCallback4 != null) {
            valueCallback4.onReceiveValue(null);
        }
        this.mFileUploadCallbackSecond = valueCallback2;
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.addCategory("android.intent.category.OPENABLE");
        if (z) {
            intent.putExtra("android.intent.extra.ALLOW_MULTIPLE", true);
        }
        intent.setType(this.mUploadableFileTypes);
        WeakReference<Fragment> weakReference = this.mFragment;
        if (weakReference != null && weakReference.get() != null) {
            this.mFragment.get().startActivityForResult(Intent.createChooser(intent, getFileUploadPromptLabel()), this.mRequestCodeFilePicker);
            return;
        }
        WeakReference<Activity> weakReference2 = this.mActivity;
        if (weakReference2 == null || weakReference2.get() == null) {
            return;
        }
        this.mActivity.get().startActivityForResult(Intent.createChooser(intent, getFileUploadPromptLabel()), this.mRequestCodeFilePicker);
    }

    public static boolean isFileUploadAvailable() {
        return isFileUploadAvailable(false);
    }

    public static boolean handleDownload(Context context, String str, String str2) {
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(str));
        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(1);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, str2);
        DownloadManager downloadManager = (DownloadManager) context.getSystemService("download");
        try {
            try {
                downloadManager.enqueue(request);
            } catch (SecurityException unused) {
                request.setNotificationVisibility(0);
                downloadManager.enqueue(request);
            }
            return true;
        } catch (IllegalArgumentException unused2) {
            openAppSettings(context, PACKAGE_NAME_DOWNLOAD_MANAGER);
            return false;
        }
    }

    private static boolean openAppSettings(Context context, String str) {
        try {
            Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
            intent.setData(Uri.parse("package:" + str));
            intent.setFlags(268435456);
            context.startActivity(intent);
            return true;
        } catch (Exception unused) {
            return false;
        }
    }

    public static class Browsers {
        private static String mAlternativePackage;

        public static boolean hasAlternative(Context context) {
            return getAlternative(context) != null;
        }

        public static String getAlternative(Context context) {
            String str = mAlternativePackage;
            if (str != null) {
                return str;
            }
            List listAsList = Arrays.asList(AdvancedWebView.ALTERNATIVE_BROWSERS);
            for (ApplicationInfo applicationInfo : context.getPackageManager().getInstalledApplications(128)) {
                if (applicationInfo.enabled && listAsList.contains(applicationInfo.packageName)) {
                    mAlternativePackage = applicationInfo.packageName;
                    return applicationInfo.packageName;
                }
            }
            return null;
        }

        public static void openUrl(Activity activity, String str) {
            openUrl(activity, str, false);
        }

        public static void openUrl(Activity activity, String str, boolean z) {
            Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(str));
            intent.setPackage(getAlternative(activity));
            intent.addFlags(268435456);
            activity.startActivity(intent);
            if (z) {
                activity.overridePendingTransition(0, 0);
            }
        }
    }
}
