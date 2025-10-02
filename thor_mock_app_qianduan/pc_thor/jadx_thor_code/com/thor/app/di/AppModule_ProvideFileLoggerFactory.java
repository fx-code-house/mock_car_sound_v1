package com.thor.app.di;

import android.content.Context;
import com.thor.app.utils.logs.loggers.FileLogger;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: classes2.dex */
public final class AppModule_ProvideFileLoggerFactory implements Factory<FileLogger> {
    private final Provider<Context> contextProvider;

    public AppModule_ProvideFileLoggerFactory(Provider<Context> contextProvider) {
        this.contextProvider = contextProvider;
    }

    @Override // javax.inject.Provider
    public FileLogger get() {
        return provideFileLogger(this.contextProvider.get());
    }

    public static AppModule_ProvideFileLoggerFactory create(Provider<Context> contextProvider) {
        return new AppModule_ProvideFileLoggerFactory(contextProvider);
    }

    public static FileLogger provideFileLogger(Context context) {
        return (FileLogger) Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideFileLogger(context));
    }
}
