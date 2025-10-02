package com.thor.app.gui.activities.support.jivochat;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import androidx.appcompat.app.AppCompatActivity;
import com.carsystems.thor.app.R;
import com.thor.app.gui.widget.ToolbarWidget;
import com.thor.app.settings.Settings;
import com.thor.app.utils.jivo.JivoDelegate;
import com.thor.app.utils.jivo.JivoSdk;
import java.util.Locale;

/* loaded from: classes3.dex */
public class ChatActivity extends AppCompatActivity implements JivoDelegate {
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jivo);
        ToolbarWidget toolbarWidget = (ToolbarWidget) findViewById(R.id.toolbar_widget);
        toolbarWidget.setHomeButtonVisibility(true);
        toolbarWidget.setHomeOnClickListener(new View.OnClickListener() { // from class: com.thor.app.gui.activities.support.jivochat.ChatActivity$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$onCreate$0(view);
            }
        });
        WebView webView = (WebView) findViewById(R.id.webview);
        webView.getSettings().setCacheMode(2);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        String language = Locale.getDefault().getLanguage();
        String str = Settings.LANGUAGE_CODE_RU;
        if (!language.contains(Settings.LANGUAGE_CODE_RU)) {
            String language2 = Locale.getDefault().getLanguage();
            str = Settings.LANGUAGE_CODE_DE;
            if (!language2.contains(Settings.LANGUAGE_CODE_DE)) {
                str = Settings.LANGUAGE_CODE_EN;
            }
        }
        JivoSdk jivoSdk = new JivoSdk((WebView) findViewById(R.id.webview), str);
        jivoSdk.delegate = this;
        jivoSdk.prepare();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$0(View view) {
        finish();
    }

    @Override // com.thor.app.utils.jivo.JivoDelegate
    public void onEvent(String name, String data) {
        if (!name.equals("url.click") || data.length() <= 2) {
            return;
        }
        startActivity(new Intent("android.intent.action.VIEW", Uri.parse(data.substring(1, data.length() - 1))));
    }
}
