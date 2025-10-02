package com.google.android.exoplayer2.source.ads;

import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.source.ads.AdsMediaSource;
import com.google.android.exoplayer2.ui.AdViewProvider;
import com.google.android.exoplayer2.upstream.DataSpec;
import java.io.IOException;

/* loaded from: classes.dex */
public interface AdsLoader {

    public interface EventListener {
        default void onAdClicked() {
        }

        default void onAdLoadError(AdsMediaSource.AdLoadException error, DataSpec dataSpec) {
        }

        default void onAdPlaybackState(AdPlaybackState adPlaybackState) {
        }

        default void onAdTapped() {
        }
    }

    void handlePrepareComplete(AdsMediaSource adsMediaSource, int adGroupIndex, int adIndexInAdGroup);

    void handlePrepareError(AdsMediaSource adsMediaSource, int adGroupIndex, int adIndexInAdGroup, IOException exception);

    void release();

    void setPlayer(Player player);

    void setSupportedContentTypes(int... contentTypes);

    void start(AdsMediaSource adsMediaSource, DataSpec adTagDataSpec, Object adsId, AdViewProvider adViewProvider, EventListener eventListener);

    void stop(AdsMediaSource adsMediaSource, EventListener eventListener);
}
