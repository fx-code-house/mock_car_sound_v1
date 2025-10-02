package com.thor.app.auto.common;

import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import androidx.lifecycle.MutableLiveData;
import com.google.android.exoplayer2.text.ttml.TtmlNode;
import com.thor.app.auto.media.MusicServiceKt;
import com.thor.app.databinding.viewmodels.workers.BleCommandsWorker;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: MusicServiceConnection.kt */
@Metadata(d1 = {"\u0000v\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u0000 .2\u00020\u0001:\u0003./0B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0018\u0010!\u001a\u00020\t2\u0006\u0010\"\u001a\u00020\u001a2\b\u0010#\u001a\u0004\u0018\u00010$J4\u0010!\u001a\u00020\t2\u0006\u0010\"\u001a\u00020\u001a2\b\u0010#\u001a\u0004\u0018\u00010$2\u001a\u0010%\u001a\u0016\u0012\u0004\u0012\u00020'\u0012\u0006\u0012\u0004\u0018\u00010$\u0012\u0004\u0012\u00020(0&J\u0016\u0010)\u001a\u00020(2\u0006\u0010*\u001a\u00020\u001a2\u0006\u0010+\u001a\u00020,J\u0016\u0010-\u001a\u00020(2\u0006\u0010*\u001a\u00020\u001a2\u0006\u0010+\u001a\u00020,R\u0017\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\nR\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0012\u0010\r\u001a\u00060\u000eR\u00020\u0000X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082.¢\u0006\u0002\n\u0000R\u0017\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\t0\b¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\nR\u0017\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00140\b¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\nR\u0017\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00170\b¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\nR\u0011\u0010\u0019\u001a\u00020\u001a8F¢\u0006\u0006\u001a\u0004\b\u001b\u0010\u001cR\u0011\u0010\u001d\u001a\u00020\u001e8F¢\u0006\u0006\u001a\u0004\b\u001f\u0010 ¨\u00061"}, d2 = {"Lcom/thor/app/auto/common/MusicServiceConnection;", "", "context", "Landroid/content/Context;", "serviceComponent", "Landroid/content/ComponentName;", "(Landroid/content/Context;Landroid/content/ComponentName;)V", "isConnected", "Landroidx/lifecycle/MutableLiveData;", "", "()Landroidx/lifecycle/MutableLiveData;", "mediaBrowser", "Landroid/support/v4/media/MediaBrowserCompat;", "mediaBrowserConnectionCallback", "Lcom/thor/app/auto/common/MusicServiceConnection$MediaBrowserConnectionCallback;", "mediaController", "Landroid/support/v4/media/session/MediaControllerCompat;", "networkFailure", "getNetworkFailure", "nowPlaying", "Landroid/support/v4/media/MediaMetadataCompat;", "getNowPlaying", "playbackState", "Landroid/support/v4/media/session/PlaybackStateCompat;", "getPlaybackState", "rootMediaId", "", "getRootMediaId", "()Ljava/lang/String;", "transportControls", "Landroid/support/v4/media/session/MediaControllerCompat$TransportControls;", "getTransportControls", "()Landroid/support/v4/media/session/MediaControllerCompat$TransportControls;", "sendCommand", BleCommandsWorker.INPUT_DATA_COMMAND, "parameters", "Landroid/os/Bundle;", "resultCallback", "Lkotlin/Function2;", "", "", "subscribe", "parentId", "callback", "Landroid/support/v4/media/MediaBrowserCompat$SubscriptionCallback;", "unsubscribe", "Companion", "MediaBrowserConnectionCallback", "MediaControllerCallback", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes.dex */
public final class MusicServiceConnection {

    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static volatile MusicServiceConnection instance;
    private final MutableLiveData<Boolean> isConnected;
    private final MediaBrowserCompat mediaBrowser;
    private final MediaBrowserConnectionCallback mediaBrowserConnectionCallback;
    private MediaControllerCompat mediaController;
    private final MutableLiveData<Boolean> networkFailure;
    private final MutableLiveData<MediaMetadataCompat> nowPlaying;
    private final MutableLiveData<PlaybackStateCompat> playbackState;

    public MusicServiceConnection(Context context, ComponentName serviceComponent) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(serviceComponent, "serviceComponent");
        MutableLiveData<Boolean> mutableLiveData = new MutableLiveData<>();
        mutableLiveData.postValue(false);
        this.isConnected = mutableLiveData;
        MutableLiveData<Boolean> mutableLiveData2 = new MutableLiveData<>();
        mutableLiveData2.postValue(false);
        this.networkFailure = mutableLiveData2;
        MutableLiveData<PlaybackStateCompat> mutableLiveData3 = new MutableLiveData<>();
        mutableLiveData3.postValue(MusicServiceConnectionKt.getEMPTY_PLAYBACK_STATE());
        this.playbackState = mutableLiveData3;
        MutableLiveData<MediaMetadataCompat> mutableLiveData4 = new MutableLiveData<>();
        mutableLiveData4.postValue(MusicServiceConnectionKt.getNOTHING_PLAYING());
        this.nowPlaying = mutableLiveData4;
        MediaBrowserConnectionCallback mediaBrowserConnectionCallback = new MediaBrowserConnectionCallback(this, context);
        this.mediaBrowserConnectionCallback = mediaBrowserConnectionCallback;
        MediaBrowserCompat mediaBrowserCompat = new MediaBrowserCompat(context, serviceComponent, mediaBrowserConnectionCallback, null);
        mediaBrowserCompat.connect();
        this.mediaBrowser = mediaBrowserCompat;
    }

    public final MutableLiveData<Boolean> isConnected() {
        return this.isConnected;
    }

    public final MutableLiveData<Boolean> getNetworkFailure() {
        return this.networkFailure;
    }

    public final String getRootMediaId() {
        String root = this.mediaBrowser.getRoot();
        Intrinsics.checkNotNullExpressionValue(root, "mediaBrowser.root");
        return root;
    }

    public final MutableLiveData<PlaybackStateCompat> getPlaybackState() {
        return this.playbackState;
    }

    public final MutableLiveData<MediaMetadataCompat> getNowPlaying() {
        return this.nowPlaying;
    }

    public final MediaControllerCompat.TransportControls getTransportControls() {
        MediaControllerCompat mediaControllerCompat = this.mediaController;
        if (mediaControllerCompat == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mediaController");
            mediaControllerCompat = null;
        }
        MediaControllerCompat.TransportControls transportControls = mediaControllerCompat.getTransportControls();
        Intrinsics.checkNotNullExpressionValue(transportControls, "mediaController.transportControls");
        return transportControls;
    }

    public final void subscribe(String parentId, MediaBrowserCompat.SubscriptionCallback callback) {
        Intrinsics.checkNotNullParameter(parentId, "parentId");
        Intrinsics.checkNotNullParameter(callback, "callback");
        this.mediaBrowser.subscribe(parentId, callback);
    }

    public final void unsubscribe(String parentId, MediaBrowserCompat.SubscriptionCallback callback) {
        Intrinsics.checkNotNullParameter(parentId, "parentId");
        Intrinsics.checkNotNullParameter(callback, "callback");
        this.mediaBrowser.unsubscribe(parentId, callback);
    }

    public final boolean sendCommand(String command, Bundle parameters) {
        Intrinsics.checkNotNullParameter(command, "command");
        return sendCommand(command, parameters, new Function2<Integer, Bundle, Unit>() { // from class: com.thor.app.auto.common.MusicServiceConnection.sendCommand.1
            public final void invoke(int i, Bundle bundle) {
            }

            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ Unit invoke(Integer num, Bundle bundle) {
                invoke(num.intValue(), bundle);
                return Unit.INSTANCE;
            }
        });
    }

    public final boolean sendCommand(String command, Bundle parameters, final Function2<? super Integer, ? super Bundle, Unit> resultCallback) {
        Intrinsics.checkNotNullParameter(command, "command");
        Intrinsics.checkNotNullParameter(resultCallback, "resultCallback");
        if (!this.mediaBrowser.isConnected()) {
            return false;
        }
        MediaControllerCompat mediaControllerCompat = this.mediaController;
        if (mediaControllerCompat == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mediaController");
            mediaControllerCompat = null;
        }
        mediaControllerCompat.sendCommand(command, parameters, new ResultReceiver(new Handler()) { // from class: com.thor.app.auto.common.MusicServiceConnection.sendCommand.2
            @Override // android.os.ResultReceiver
            protected void onReceiveResult(int resultCode, Bundle resultData) {
                resultCallback.invoke(Integer.valueOf(resultCode), resultData);
            }
        });
        return true;
    }

    /* compiled from: MusicServiceConnection.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\b\u0082\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u0005\u001a\u00020\u0006H\u0016J\b\u0010\u0007\u001a\u00020\u0006H\u0016J\b\u0010\b\u001a\u00020\u0006H\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\t"}, d2 = {"Lcom/thor/app/auto/common/MusicServiceConnection$MediaBrowserConnectionCallback;", "Landroid/support/v4/media/MediaBrowserCompat$ConnectionCallback;", "context", "Landroid/content/Context;", "(Lcom/thor/app/auto/common/MusicServiceConnection;Landroid/content/Context;)V", "onConnected", "", "onConnectionFailed", "onConnectionSuspended", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    private final class MediaBrowserConnectionCallback extends MediaBrowserCompat.ConnectionCallback {
        private final Context context;
        final /* synthetic */ MusicServiceConnection this$0;

        public MediaBrowserConnectionCallback(MusicServiceConnection musicServiceConnection, Context context) {
            Intrinsics.checkNotNullParameter(context, "context");
            this.this$0 = musicServiceConnection;
            this.context = context;
        }

        @Override // android.support.v4.media.MediaBrowserCompat.ConnectionCallback
        public void onConnected() {
            MusicServiceConnection musicServiceConnection = this.this$0;
            MediaControllerCompat mediaControllerCompat = new MediaControllerCompat(this.context, this.this$0.mediaBrowser.getSessionToken());
            mediaControllerCompat.registerCallback(this.this$0.new MediaControllerCallback());
            musicServiceConnection.mediaController = mediaControllerCompat;
            this.this$0.isConnected().postValue(true);
        }

        @Override // android.support.v4.media.MediaBrowserCompat.ConnectionCallback
        public void onConnectionSuspended() {
            this.this$0.isConnected().postValue(false);
        }

        @Override // android.support.v4.media.MediaBrowserCompat.ConnectionCallback
        public void onConnectionFailed() {
            this.this$0.isConnected().postValue(false);
        }
    }

    /* compiled from: MusicServiceConnection.kt */
    @Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0082\u0004\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0012\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u0016J\u0012\u0010\u0007\u001a\u00020\u00042\b\u0010\b\u001a\u0004\u0018\u00010\tH\u0016J\u0018\u0010\n\u001a\u00020\u00042\u000e\u0010\u000b\u001a\n\u0012\u0004\u0012\u00020\r\u0018\u00010\fH\u0016J\b\u0010\u000e\u001a\u00020\u0004H\u0016J\u001c\u0010\u000f\u001a\u00020\u00042\b\u0010\u0010\u001a\u0004\u0018\u00010\u00112\b\u0010\u0012\u001a\u0004\u0018\u00010\u0013H\u0016¨\u0006\u0014"}, d2 = {"Lcom/thor/app/auto/common/MusicServiceConnection$MediaControllerCallback;", "Landroid/support/v4/media/session/MediaControllerCompat$Callback;", "(Lcom/thor/app/auto/common/MusicServiceConnection;)V", "onMetadataChanged", "", TtmlNode.TAG_METADATA, "Landroid/support/v4/media/MediaMetadataCompat;", "onPlaybackStateChanged", "state", "Landroid/support/v4/media/session/PlaybackStateCompat;", "onQueueChanged", "queue", "", "Landroid/support/v4/media/session/MediaSessionCompat$QueueItem;", "onSessionDestroyed", "onSessionEvent", "event", "", "extras", "Landroid/os/Bundle;", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    private final class MediaControllerCallback extends MediaControllerCompat.Callback {
        @Override // android.support.v4.media.session.MediaControllerCompat.Callback
        public void onQueueChanged(List<MediaSessionCompat.QueueItem> queue) {
        }

        public MediaControllerCallback() {
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.Callback
        public void onPlaybackStateChanged(PlaybackStateCompat state) {
            MutableLiveData<PlaybackStateCompat> playbackState = MusicServiceConnection.this.getPlaybackState();
            if (state == null) {
                state = MusicServiceConnectionKt.getEMPTY_PLAYBACK_STATE();
            }
            playbackState.postValue(state);
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.Callback
        public void onMetadataChanged(MediaMetadataCompat metadata) {
            MutableLiveData<MediaMetadataCompat> nowPlaying = MusicServiceConnection.this.getNowPlaying();
            if ((metadata != null ? metadata.getString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID) : null) == null) {
                metadata = MusicServiceConnectionKt.getNOTHING_PLAYING();
            }
            nowPlaying.postValue(metadata);
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.Callback
        public void onSessionEvent(String event, Bundle extras) {
            super.onSessionEvent(event, extras);
            if (Intrinsics.areEqual(event, MusicServiceKt.NETWORK_FAILURE)) {
                MusicServiceConnection.this.getNetworkFailure().postValue(true);
            }
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.Callback
        public void onSessionDestroyed() {
            MusicServiceConnection.this.mediaBrowserConnectionCallback.onConnectionSuspended();
        }
    }

    /* compiled from: MusicServiceConnection.kt */
    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tR\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\n"}, d2 = {"Lcom/thor/app/auto/common/MusicServiceConnection$Companion;", "", "()V", "instance", "Lcom/thor/app/auto/common/MusicServiceConnection;", "getInstance", "context", "Landroid/content/Context;", "serviceComponent", "Landroid/content/ComponentName;", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final MusicServiceConnection getInstance(Context context, ComponentName serviceComponent) {
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(serviceComponent, "serviceComponent");
            MusicServiceConnection musicServiceConnection = MusicServiceConnection.instance;
            if (musicServiceConnection == null) {
                synchronized (this) {
                    musicServiceConnection = MusicServiceConnection.instance;
                    if (musicServiceConnection == null) {
                        musicServiceConnection = new MusicServiceConnection(context, serviceComponent);
                        Companion companion = MusicServiceConnection.INSTANCE;
                        MusicServiceConnection.instance = musicServiceConnection;
                    }
                }
            }
            return musicServiceConnection;
        }
    }
}
