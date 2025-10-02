package com.thor.app.network;

import android.content.Context;
import com.carsystems.thor.app.BuildConfig;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.thor.app.settings.Settings;
import com.thor.networkmodule.network.ApiService;
import java.util.UnknownFormatConversionException;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

/* loaded from: classes3.dex */
public class Api {
    private static Api currentApi = null;
    private static int currentServer = 1099;
    private final ApiService mApiService;
    private ResponseInterceptor responseInterceptor = new ResponseInterceptor();

    public static Api from(Context context) {
        if (Settings.getSelectedServer() == currentServer || currentApi == null) {
            currentApi = new Api(context);
        }
        return currentApi;
    }

    public Api(Context context) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(30L, TimeUnit.SECONDS).writeTimeout(30L, TimeUnit.SECONDS).readTimeout(30L, TimeUnit.SECONDS);
        builder.followRedirects(true).followSslRedirects(true);
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() { // from class: com.thor.app.network.Api.1
            @Override // okhttp3.logging.HttpLoggingInterceptor.Logger
            public void log(String message) {
                if (message.startsWith("{") || message.startsWith("[")) {
                    try {
                        Api.this.parseResponse(new GsonBuilder().setPrettyPrinting().create().toJson(new JsonParser().parse(message)));
                        return;
                    } catch (JsonSyntaxException | UnknownFormatConversionException e) {
                        Timber.i(e);
                        return;
                    }
                }
                message.isEmpty();
            }
        });
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.addInterceptor(httpLoggingInterceptor);
        Retrofit.Builder builder2 = new Retrofit.Builder();
        if (Settings.getSelectedServer() == 1092) {
            builder2.baseUrl(BuildConfig.BASE_URL_DEVELOPER);
        } else {
            builder2.baseUrl(BuildConfig.BASE_URL_RELEASE);
        }
        currentServer = Settings.getSelectedServer();
        builder2.addConverterFactory(GsonConverterFactory.create()).addCallAdapterFactory(RxJava2CallAdapterFactory.create());
        builder2.client(builder.build());
        this.mApiService = (ApiService) builder2.build().create(ApiService.class);
    }

    public void changeServer(Context context) {
        if (Settings.getSelectedServer() != currentServer || currentApi == null) {
            currentApi = new Api(context);
        }
    }

    public ApiService getApiService() {
        return this.mApiService;
    }

    public void parseResponse(String response) {
        if (response.contains("\"code\": 7")) {
            return;
        }
        response.contains("\"code\": 2018");
    }
}
