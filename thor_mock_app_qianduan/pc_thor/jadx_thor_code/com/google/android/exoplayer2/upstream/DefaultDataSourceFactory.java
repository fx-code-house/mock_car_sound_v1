package com.google.android.exoplayer2.upstream;

import android.content.Context;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource;

/* loaded from: classes.dex */
public final class DefaultDataSourceFactory implements DataSource.Factory {
    private final DataSource.Factory baseDataSourceFactory;
    private final Context context;
    private final TransferListener listener;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public DefaultDataSourceFactory(Context context) {
        this(context, (String) null, (TransferListener) null);
    }

    public DefaultDataSourceFactory(Context context, String userAgent) {
        this(context, userAgent, (TransferListener) null);
    }

    public DefaultDataSourceFactory(Context context, String userAgent, TransferListener listener) {
        this(context, listener, new DefaultHttpDataSource.Factory().setUserAgent(userAgent));
    }

    public DefaultDataSourceFactory(Context context, DataSource.Factory baseDataSourceFactory) {
        this(context, (TransferListener) null, baseDataSourceFactory);
    }

    public DefaultDataSourceFactory(Context context, TransferListener listener, DataSource.Factory baseDataSourceFactory) {
        this.context = context.getApplicationContext();
        this.listener = listener;
        this.baseDataSourceFactory = baseDataSourceFactory;
    }

    @Override // com.google.android.exoplayer2.upstream.DataSource.Factory
    public DefaultDataSource createDataSource() {
        DefaultDataSource defaultDataSource = new DefaultDataSource(this.context, this.baseDataSourceFactory.createDataSource());
        TransferListener transferListener = this.listener;
        if (transferListener != null) {
            defaultDataSource.addTransferListener(transferListener);
        }
        return defaultDataSource;
    }
}
