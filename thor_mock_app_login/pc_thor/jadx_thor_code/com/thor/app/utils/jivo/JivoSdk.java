package com.thor.app.utils.jivo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.ViewTreeObserver;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.google.android.exoplayer2.extractor.ts.PsExtractor;

/* loaded from: classes3.dex */
public class JivoSdk {
    public JivoDelegate delegate;
    private final String language;
    private ProgressDialog progr;
    private final WebView webView;

    public JivoSdk(WebView webView) {
        this.delegate = null;
        this.webView = webView;
        this.language = "";
    }

    public JivoSdk(WebView webView, String language) {
        this.delegate = null;
        this.webView = webView;
        this.language = language;
    }

    public void prepare() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) this.delegate).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        final float f = displayMetrics.density;
        new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.thor.app.utils.jivo.JivoSdk.1
            int previousHeightDiff = 0;

            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public void onGlobalLayout() {
                JivoSdk.this.webView.getWindowVisibleDisplayFrame(new Rect());
                int height = (int) ((JivoSdk.this.webView.getRootView().getHeight() - r0.bottom) / f);
                if (height > 100 && height != this.previousHeightDiff) {
                    JivoSdk.this.execJS("window.onKeyBoard({visible:false, height:0})");
                } else {
                    int i = this.previousHeightDiff;
                    if (height != i && i - height > 100) {
                        JivoSdk.this.execJS("window.onKeyBoard({visible:false, height:0})");
                    }
                }
                this.previousHeightDiff = height;
            }
        };
        ProgressDialog progressDialog = new ProgressDialog(this.webView.getContext());
        this.progr = progressDialog;
        progressDialog.setTitle("JivoSite");
        this.progr.setMessage("Загрузка...");
        WebSettings settings = this.webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        this.webView.addJavascriptInterface(new JivoInterface(this.webView), "JivoInterface");
        this.webView.setWebViewClient(new MyWebViewClient());
        if (this.language.length() > 0) {
            this.webView.loadUrl("file:///android_asset/html/index_" + this.language + ".html");
        } else {
            this.webView.loadUrl("file:///android_asset/html/index.html");
        }
    }

    public class JivoInterface {
        private final WebView mAppView;

        public JivoInterface(WebView appView) {
            this.mAppView = appView;
        }

        @JavascriptInterface
        public void send(String name, String data) {
            if (JivoSdk.this.delegate != null) {
                JivoSdk.this.delegate.onEvent(name, data);
            }
        }
    }

    public static String decodeString(String encodedURI) {
        StringBuffer stringBuffer = new StringBuffer();
        int i = 0;
        int i2 = -1;
        int i3 = 0;
        while (i < encodedURI.length()) {
            int iCharAt = encodedURI.charAt(i);
            if (iCharAt == 37) {
                int i4 = i + 1;
                char cCharAt = encodedURI.charAt(i4);
                int lowerCase = (Character.isDigit(cCharAt) ? cCharAt - '0' : (Character.toLowerCase(cCharAt) + '\n') - 97) & 15;
                i = i4 + 1;
                char cCharAt2 = encodedURI.charAt(i);
                iCharAt = (lowerCase << 4) | ((Character.isDigit(cCharAt2) ? cCharAt2 - '0' : (Character.toLowerCase(cCharAt2) + '\n') - 97) & 15);
            } else if (iCharAt == 43) {
                iCharAt = 32;
            }
            if ((iCharAt & PsExtractor.AUDIO_STREAM) == 128) {
                i3 = (i3 << 6) | (iCharAt & 63);
                i2--;
                if (i2 == 0) {
                    stringBuffer.append((char) i3);
                }
            } else if ((iCharAt & 128) == 0) {
                stringBuffer.append((char) iCharAt);
            } else if ((iCharAt & 224) == 192) {
                i3 = iCharAt & 31;
                i2 = 1;
            } else if ((iCharAt & PsExtractor.VIDEO_STREAM_MASK) == 224) {
                i3 = iCharAt & 15;
                i2 = 2;
            } else if ((iCharAt & 248) == 240) {
                i3 = iCharAt & 7;
                i2 = 3;
            } else if ((iCharAt & 252) == 248) {
                i3 = iCharAt & 3;
                i2 = 4;
            } else {
                i3 = iCharAt & 1;
                i2 = 5;
            }
            i++;
        }
        return stringBuffer.toString();
    }

    private class MyWebViewClient extends WebViewClient {
        private MyWebViewClient() {
        }

        @Override // android.webkit.WebViewClient
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.toLowerCase().indexOf("jivoapi://") == 0) {
                String[] strArrSplit = url.replace("jivoapi://", "").split("/");
                String str = strArrSplit[0];
                String strDecodeString = strArrSplit.length > 1 ? JivoSdk.decodeString(strArrSplit[1]) : "";
                if (JivoSdk.this.delegate != null) {
                    JivoSdk.this.delegate.onEvent(str, strDecodeString);
                }
            }
            return true;
        }

        @Override // android.webkit.WebViewClient
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override // android.webkit.WebViewClient
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            JivoSdk.this.progr.dismiss();
        }
    }

    public void execJS(String script) {
        this.webView.loadUrl("javascript:" + script);
    }

    public void callApiMethod(String methodName, String data) {
        this.webView.loadUrl("javascript:window.jivo_api." + methodName + "(" + data + ");");
    }
}
