package com.google.android.exoplayer2.upstream;

/* loaded from: classes.dex */
public interface TransferListener {
    void onBytesTransferred(DataSource source, DataSpec dataSpec, boolean isNetwork, int bytesTransferred);

    void onTransferEnd(DataSource source, DataSpec dataSpec, boolean isNetwork);

    void onTransferInitializing(DataSource source, DataSpec dataSpec, boolean isNetwork);

    void onTransferStart(DataSource source, DataSpec dataSpec, boolean isNetwork);
}
