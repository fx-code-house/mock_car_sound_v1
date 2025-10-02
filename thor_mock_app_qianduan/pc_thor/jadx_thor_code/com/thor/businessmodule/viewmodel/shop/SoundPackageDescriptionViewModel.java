package com.thor.businessmodule.viewmodel.shop;

import androidx.databinding.ObservableBoolean;
import kotlin.Metadata;

/* compiled from: SoundPackageDescriptionViewModel.kt */
@Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\u0006R\u0011\u0010\b\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0006R\u0011\u0010\t\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\u0006R\u0011\u0010\n\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u0006R\u0011\u0010\u000b\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\u0006R\u0011\u0010\f\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u0006¨\u0006\r"}, d2 = {"Lcom/thor/businessmodule/viewmodel/shop/SoundPackageDescriptionViewModel;", "", "()V", "dataLoading", "Landroidx/databinding/ObservableBoolean;", "getDataLoading", "()Landroidx/databinding/ObservableBoolean;", "isDownloadPackage", "isGallery", "isGooglePlayBilling", "isPlaying", "isPlayingVideo", "isVideo", "businessmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class SoundPackageDescriptionViewModel {
    private final ObservableBoolean dataLoading = new ObservableBoolean(false);
    private final ObservableBoolean isPlayingVideo = new ObservableBoolean(false);
    private final ObservableBoolean isPlaying = new ObservableBoolean(false);
    private final ObservableBoolean isGallery = new ObservableBoolean(false);
    private final ObservableBoolean isVideo = new ObservableBoolean(false);
    private final ObservableBoolean isGooglePlayBilling = new ObservableBoolean(false);
    private final ObservableBoolean isDownloadPackage = new ObservableBoolean(false);

    public final ObservableBoolean getDataLoading() {
        return this.dataLoading;
    }

    /* renamed from: isPlayingVideo, reason: from getter */
    public final ObservableBoolean getIsPlayingVideo() {
        return this.isPlayingVideo;
    }

    /* renamed from: isPlaying, reason: from getter */
    public final ObservableBoolean getIsPlaying() {
        return this.isPlaying;
    }

    /* renamed from: isGallery, reason: from getter */
    public final ObservableBoolean getIsGallery() {
        return this.isGallery;
    }

    /* renamed from: isVideo, reason: from getter */
    public final ObservableBoolean getIsVideo() {
        return this.isVideo;
    }

    /* renamed from: isGooglePlayBilling, reason: from getter */
    public final ObservableBoolean getIsGooglePlayBilling() {
        return this.isGooglePlayBilling;
    }

    /* renamed from: isDownloadPackage, reason: from getter */
    public final ObservableBoolean getIsDownloadPackage() {
        return this.isDownloadPackage;
    }
}
