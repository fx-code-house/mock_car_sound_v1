package com.thor.app.di;

import android.content.Context;
import com.carsystems.thor.app.BuildConfig;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.thor.app.settings.Settings;
import com.thor.app.utils.logs.loggers.FileLogger;
import com.thor.networkmodule.network.ApiService;
import com.thor.networkmodule.network.ApiServiceNew;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.android.qualifiers.ApplicationContext;
import java.util.UnknownFormatConversionException;
import java.util.concurrent.TimeUnit;
import javax.inject.Named;
import javax.inject.Singleton;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.Dispatchers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

/* compiled from: NetworkModule.kt */
@Metadata(d1 = {"\u0000d\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\bÇ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\b\u0010\u0007\u001a\u00020\bH\u0007J\b\u0010\t\u001a\u00020\nH\u0007J\b\u0010\u000b\u001a\u00020\fH\u0007J\u0010\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0007J\u0012\u0010\u0011\u001a\u00020\u00122\b\b\u0001\u0010\u0005\u001a\u00020\u0006H\u0007J \u0010\u0013\u001a\u00020\u00062\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\f2\u0006\u0010\u0017\u001a\u00020\u0018H\u0007J\u0010\u0010\u0019\u001a\u00020\u00182\u0006\u0010\u001a\u001a\u00020\u000eH\u0007J(\u0010\u001b\u001a\u00020\u00062\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\f2\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u0017\u001a\u00020\u0018H\u0007J\b\u0010\u001e\u001a\u00020\u001dH\u0007J\u0010\u0010\u001f\u001a\u00020\u00152\u0006\u0010 \u001a\u00020!H\u0007J\u0012\u0010\"\u001a\u00020\u00102\b\b\u0001\u0010#\u001a\u00020$H\u0007¨\u0006%"}, d2 = {"Lcom/thor/app/di/NetworkModule;", "", "()V", "provideApiService", "Lcom/thor/networkmodule/network/ApiService;", "retrofit", "Lretrofit2/Retrofit;", "provideCoroutineDispatcher", "Lkotlinx/coroutines/CoroutineDispatcher;", "provideGson", "Lcom/google/gson/Gson;", "provideGsonConverterFactory", "Lretrofit2/converter/gson/GsonConverterFactory;", "provideLoggingInterceptor", "Lokhttp3/logging/HttpLoggingInterceptor;", "logger", "Lokhttp3/logging/HttpLoggingInterceptor$Logger;", "provideNewApiService", "Lcom/thor/networkmodule/network/ApiServiceNew;", "provideNewRetrofit", "serverUrl", "", "gsonConverterFactory", "okHttpClient", "Lokhttp3/OkHttpClient;", "provideOkHttpClient", "loggingInterceptor", "provideRetrofit", "rxJavaToCallAdapterFactory", "Lretrofit2/adapter/rxjava2/RxJava2CallAdapterFactory;", "provideRxJavaToCallAdapterFactory", "provideServerUrl", "settings", "Lcom/thor/app/settings/Settings;", "providerHttpLoggingInterceptorLogger", "context", "Landroid/content/Context;", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@Module
/* loaded from: classes2.dex */
public final class NetworkModule {
    public static final NetworkModule INSTANCE = new NetworkModule();

    private NetworkModule() {
    }

    @Provides
    @Singleton
    public final Retrofit provideRetrofit(String serverUrl, GsonConverterFactory gsonConverterFactory, RxJava2CallAdapterFactory rxJavaToCallAdapterFactory, OkHttpClient okHttpClient) {
        Intrinsics.checkNotNullParameter(serverUrl, "serverUrl");
        Intrinsics.checkNotNullParameter(gsonConverterFactory, "gsonConverterFactory");
        Intrinsics.checkNotNullParameter(rxJavaToCallAdapterFactory, "rxJavaToCallAdapterFactory");
        Intrinsics.checkNotNullParameter(okHttpClient, "okHttpClient");
        Retrofit retrofitBuild = new Retrofit.Builder().baseUrl(serverUrl).addConverterFactory(gsonConverterFactory).addCallAdapterFactory(rxJavaToCallAdapterFactory).client(okHttpClient).build();
        Intrinsics.checkNotNullExpressionValue(retrofitBuild, "Builder()\n        .baseU…pClient)\n        .build()");
        return retrofitBuild;
    }

    @Provides
    @Singleton
    @Named("new_retrofit")
    public final Retrofit provideNewRetrofit(String serverUrl, GsonConverterFactory gsonConverterFactory, OkHttpClient okHttpClient) {
        Intrinsics.checkNotNullParameter(serverUrl, "serverUrl");
        Intrinsics.checkNotNullParameter(gsonConverterFactory, "gsonConverterFactory");
        Intrinsics.checkNotNullParameter(okHttpClient, "okHttpClient");
        Retrofit retrofitBuild = new Retrofit.Builder().baseUrl(serverUrl).addConverterFactory(gsonConverterFactory).client(okHttpClient).build();
        Intrinsics.checkNotNullExpressionValue(retrofitBuild, "Builder()\n        .baseU…pClient)\n        .build()");
        return retrofitBuild;
    }

    @Provides
    @Singleton
    public final HttpLoggingInterceptor provideLoggingInterceptor(HttpLoggingInterceptor.Logger logger) {
        Intrinsics.checkNotNullParameter(logger, "logger");
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(logger);
        httpLoggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);
        return httpLoggingInterceptor;
    }

    @Provides
    @Singleton
    public final HttpLoggingInterceptor.Logger providerHttpLoggingInterceptorLogger(@ApplicationContext final Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        return new HttpLoggingInterceptor.Logger() { // from class: com.thor.app.di.NetworkModule$$ExternalSyntheticLambda0
            @Override // okhttp3.logging.HttpLoggingInterceptor.Logger
            public final void log(String str) {
                NetworkModule.providerHttpLoggingInterceptorLogger$lambda$1(context, str);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void providerHttpLoggingInterceptorLogger$lambda$1(Context context, String message) {
        Intrinsics.checkNotNullParameter(context, "$context");
        Intrinsics.checkNotNullParameter(message, "message");
        FileLogger fileLoggerNewInstance = FileLogger.INSTANCE.newInstance(context, OkHttpClient.class.getSimpleName());
        if (StringsKt.startsWith$default(message, "{", false, 2, (Object) null) || StringsKt.startsWith$default(message, "[", false, 2, (Object) null)) {
            try {
                String prettyPrintJson = new GsonBuilder().setPrettyPrinting().create().toJson(new JsonParser().parse(message));
                Intrinsics.checkNotNullExpressionValue(prettyPrintJson, "prettyPrintJson");
                fileLoggerNewInstance.d(prettyPrintJson, new Object[0]);
                return;
            } catch (JsonSyntaxException e) {
                Timber.INSTANCE.i(e);
                fileLoggerNewInstance.d(message, new Object[0]);
                return;
            } catch (UnknownFormatConversionException e2) {
                Timber.INSTANCE.i(e2);
                fileLoggerNewInstance.d(message, new Object[0]);
                return;
            }
        }
        if (message.length() > 0) {
            fileLoggerNewInstance.d(message, new Object[0]);
        }
    }

    @Provides
    @Singleton
    public final OkHttpClient provideOkHttpClient(HttpLoggingInterceptor loggingInterceptor) {
        Intrinsics.checkNotNullParameter(loggingInterceptor, "loggingInterceptor");
        return new OkHttpClient.Builder().connectTimeout(30L, TimeUnit.SECONDS).readTimeout(30L, TimeUnit.SECONDS).writeTimeout(30L, TimeUnit.SECONDS).followRedirects(true).followSslRedirects(true).retryOnConnectionFailure(true).addInterceptor(loggingInterceptor).build();
    }

    @Provides
    public final CoroutineDispatcher provideCoroutineDispatcher() {
        return Dispatchers.getIO();
    }

    @Provides
    @Singleton
    public final GsonConverterFactory provideGsonConverterFactory() {
        GsonConverterFactory gsonConverterFactoryCreate = GsonConverterFactory.create();
        Intrinsics.checkNotNullExpressionValue(gsonConverterFactoryCreate, "create()");
        return gsonConverterFactoryCreate;
    }

    @Provides
    @Singleton
    public final RxJava2CallAdapterFactory provideRxJavaToCallAdapterFactory() {
        RxJava2CallAdapterFactory rxJava2CallAdapterFactoryCreate = RxJava2CallAdapterFactory.create();
        Intrinsics.checkNotNullExpressionValue(rxJava2CallAdapterFactoryCreate, "create()");
        return rxJava2CallAdapterFactoryCreate;
    }

    @Provides
    @Singleton
    public final Gson provideGson() {
        Gson gsonCreate = new GsonBuilder().create();
        Intrinsics.checkNotNullExpressionValue(gsonCreate, "GsonBuilder().create()");
        return gsonCreate;
    }

    @Provides
    @Singleton
    public final String provideServerUrl(Settings settings) {
        Intrinsics.checkNotNullParameter(settings, "settings");
        return Settings.getSelectedServer() == 1092 ? BuildConfig.BASE_URL_DEVELOPER : BuildConfig.BASE_URL_RELEASE;
    }

    @Provides
    @Singleton
    public final ApiService provideApiService(Retrofit retrofit) throws SecurityException {
        Intrinsics.checkNotNullParameter(retrofit, "retrofit");
        Object objCreate = retrofit.create(ApiService.class);
        Intrinsics.checkNotNullExpressionValue(objCreate, "retrofit.create(ApiService::class.java)");
        return (ApiService) objCreate;
    }

    @Provides
    @Singleton
    @Named("new_api_service")
    public final ApiServiceNew provideNewApiService(@Named("new_retrofit") Retrofit retrofit) throws SecurityException {
        Intrinsics.checkNotNullParameter(retrofit, "retrofit");
        Object objCreate = retrofit.create(ApiServiceNew.class);
        Intrinsics.checkNotNullExpressionValue(objCreate, "retrofit.create(ApiServiceNew::class.java)");
        return (ApiServiceNew) objCreate;
    }
}
