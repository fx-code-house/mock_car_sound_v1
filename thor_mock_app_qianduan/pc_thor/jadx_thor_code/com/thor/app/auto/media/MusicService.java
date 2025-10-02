package com.thor.app.auto.media;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaDescriptionCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;
import android.widget.Toast;
import androidx.core.content.ContextCompat;
import androidx.core.view.accessibility.AccessibilityEventCompat;
import androidx.media.MediaBrowserServiceCompat;
import androidx.media.utils.MediaConstants;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import com.carsystems.thor.app.BuildConfig;
import com.carsystems.thor.app.R;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ControlDispatcher;
import com.google.android.exoplayer2.DefaultControlDispatcher;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.PlaybackException;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.audio.AudioAttributes;
import com.google.android.exoplayer2.ext.mediasession.MediaSessionConnector;
import com.google.android.exoplayer2.ext.mediasession.TimelineQueueNavigator;
import com.google.android.exoplayer2.source.ConcatenatingMediaSource;
import com.google.android.exoplayer2.ui.PlayerNotificationManager;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Util;
import com.google.android.gms.actions.SearchIntents;
import com.google.firebase.messaging.Constants;
import com.thor.app.auto.media.extensions.MediaMetadataCompatExtKt;
import com.thor.app.auto.media.library.BrowseTree;
import com.thor.app.auto.media.library.BrowseTreeKt;
import com.thor.app.auto.media.library.DemoSoundsSource;
import com.thor.app.auto.media.library.DeviceSoundsSource;
import com.thor.app.auto.media.library.MusicSource;
import com.thor.app.bus.events.mainpreset.SwitchedPresetEvent;
import com.thor.app.databinding.viewmodels.workers.BleCommandsWorker;
import com.thor.app.settings.Settings;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CancellationException;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CompletableJob;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.SupervisorKt;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import timber.log.Timber;

/* compiled from: MusicService.kt */
@Metadata(d1 = {"\u0000Â\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\b\u0016\u0018\u00002\u00020\u0001:\u0005]^_`aB\u0005¢\u0006\u0002\u0010\u0002J\b\u00102\u001a\u000203H\u0002J\u0010\u00104\u001a\u0002052\u0006\u00106\u001a\u000207H\u0002J\u0012\u00108\u001a\u0002092\b\u0010:\u001a\u0004\u0018\u00010\u000bH\u0002J\b\u0010;\u001a\u000209H\u0002J\b\u0010<\u001a\u000209H\u0002J\b\u0010=\u001a\u000209H\u0002J\b\u0010>\u001a\u000209H\u0016J\b\u0010?\u001a\u000209H\u0016J$\u0010@\u001a\u0004\u0018\u00010A2\u0006\u0010B\u001a\u00020C2\u0006\u0010D\u001a\u0002072\b\u0010E\u001a\u0004\u0018\u00010FH\u0016J$\u0010G\u001a\u0002092\u0006\u0010H\u001a\u00020C2\u0012\u0010I\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020K0\n0JH\u0016J\u0010\u0010L\u001a\u0002092\u0006\u0010M\u001a\u00020NH\u0007J.\u0010O\u001a\u0002092\u0006\u0010P\u001a\u00020C2\b\u0010Q\u001a\u0004\u0018\u00010F2\u0012\u0010I\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020K0\n0JH\u0016J\u001c\u0010R\u001a\u0002092\u0012\u0010S\u001a\u000e\u0012\u0004\u0012\u00020C\u0012\u0004\u0012\u00020C0TH\u0002J\u0010\u0010U\u001a\u0002092\u0006\u0010V\u001a\u00020WH\u0016J0\u0010X\u001a\u0002092\f\u0010Y\u001a\b\u0012\u0004\u0012\u00020\u000b0\n2\b\u0010Z\u001a\u0004\u0018\u00010\u000b2\u0006\u0010[\u001a\u00020\u00172\u0006\u0010\\\u001a\u000203H\u0002R\u001b\u0010\u0003\u001a\u00020\u00048BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0007\u0010\b\u001a\u0004\b\u0005\u0010\u0006R\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u001b\u0010\f\u001a\u00020\r8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0010\u0010\b\u001a\u0004\b\u000e\u0010\u000fR\u001b\u0010\u0011\u001a\u00020\u00128BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0015\u0010\b\u001a\u0004\b\u0013\u0010\u0014R\u000e\u0010\u0016\u001a\u00020\u0017X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u0018\u001a\u00020\u0019X\u0084.¢\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010\u001b\"\u0004\b\u001c\u0010\u001dR\u001a\u0010\u001e\u001a\u00020\u001fX\u0084.¢\u0006\u000e\n\u0000\u001a\u0004\b \u0010!\"\u0004\b\"\u0010#R\u000e\u0010$\u001a\u00020%X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010&\u001a\u00020'X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010(\u001a\u00020)X\u0082.¢\u0006\u0002\n\u0000R\u0012\u0010*\u001a\u00060+R\u00020\u0000X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010,\u001a\u00020-X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010.\u001a\u00020/X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u00100\u001a\u000201X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006b"}, d2 = {"Lcom/thor/app/auto/media/MusicService;", "Landroidx/media/MediaBrowserServiceCompat;", "()V", "browseTree", "Lcom/thor/app/auto/media/library/BrowseTree;", "getBrowseTree", "()Lcom/thor/app/auto/media/library/BrowseTree;", "browseTree$delegate", "Lkotlin/Lazy;", "currentPlaylistItems", "", "Landroid/support/v4/media/MediaMetadataCompat;", "dataSourceFactory", "Lcom/google/android/exoplayer2/upstream/DefaultDataSourceFactory;", "getDataSourceFactory", "()Lcom/google/android/exoplayer2/upstream/DefaultDataSourceFactory;", "dataSourceFactory$delegate", "exoPlayer", "Lcom/google/android/exoplayer2/ExoPlayer;", "getExoPlayer", "()Lcom/google/android/exoplayer2/ExoPlayer;", "exoPlayer$delegate", "isForegroundService", "", "mediaSession", "Landroid/support/v4/media/session/MediaSessionCompat;", "getMediaSession", "()Landroid/support/v4/media/session/MediaSessionCompat;", "setMediaSession", "(Landroid/support/v4/media/session/MediaSessionCompat;)V", "mediaSessionConnector", "Lcom/google/android/exoplayer2/ext/mediasession/MediaSessionConnector;", "getMediaSessionConnector", "()Lcom/google/android/exoplayer2/ext/mediasession/MediaSessionConnector;", "setMediaSessionConnector", "(Lcom/google/android/exoplayer2/ext/mediasession/MediaSessionConnector;)V", "mediaSource", "Lcom/thor/app/auto/media/library/MusicSource;", "notificationManager", "Lcom/thor/app/auto/media/AutoNotificationManager;", "packageValidator", "Lcom/thor/app/auto/media/PackageValidator;", "playerListener", "Lcom/thor/app/auto/media/MusicService$PlayerEventListener;", "serviceJob", "Lkotlinx/coroutines/CompletableJob;", "serviceScope", "Lkotlinx/coroutines/CoroutineScope;", "uAmpAudioAttributes", "Lcom/google/android/exoplayer2/audio/AudioAttributes;", "getPlaybackCapabilities", "", "getSupportedPlaybackActions", "Landroid/support/v4/media/session/PlaybackStateCompat;", "state", "", "handleDevicePreset", "", "mediaMetadataCompat", "handleNextPresetEvent", "handlePauseEvent", "handlePreviousPresetEvent", "onCreate", "onDestroy", "onGetRoot", "Landroidx/media/MediaBrowserServiceCompat$BrowserRoot;", "clientPackageName", "", "clientUid", "rootHints", "Landroid/os/Bundle;", "onLoadChildren", "parentMediaId", "result", "Landroidx/media/MediaBrowserServiceCompat$Result;", "Landroid/support/v4/media/MediaBrowserCompat$MediaItem;", "onMessageEvent", "event", "Lcom/thor/app/bus/events/mainpreset/SwitchedPresetEvent;", "onSearch", SearchIntents.EXTRA_QUERY, "extras", "onStartWorker", "action", "Lkotlin/Pair;", "onTaskRemoved", "rootIntent", "Landroid/content/Intent;", "preparePlaylist", "metadataList", "itemToPlay", "playWhenReady", "playbackStartPositionMs", "CustomControllerDispatcher", "PlayerEventListener", "PlayerNotificationListener", "UampPlaybackPreparer", "UampQueueNavigator", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes.dex */
public class MusicService extends MediaBrowserServiceCompat {

    /* renamed from: browseTree$delegate, reason: from kotlin metadata */
    private final Lazy browseTree;
    private List<MediaMetadataCompat> currentPlaylistItems;

    /* renamed from: dataSourceFactory$delegate, reason: from kotlin metadata */
    private final Lazy dataSourceFactory;

    /* renamed from: exoPlayer$delegate, reason: from kotlin metadata */
    private final Lazy exoPlayer;
    private boolean isForegroundService;
    protected MediaSessionCompat mediaSession;
    protected MediaSessionConnector mediaSessionConnector;
    private MusicSource mediaSource;
    private AutoNotificationManager notificationManager;
    private PackageValidator packageValidator;
    private final PlayerEventListener playerListener;
    private final CompletableJob serviceJob;
    private final CoroutineScope serviceScope;
    private final AudioAttributes uAmpAudioAttributes;

    private final long getPlaybackCapabilities() {
        return 1585L;
    }

    public MusicService() {
        CompletableJob completableJobSupervisorJob$default = SupervisorKt.SupervisorJob$default((Job) null, 1, (Object) null);
        this.serviceJob = completableJobSupervisorJob$default;
        this.serviceScope = CoroutineScopeKt.CoroutineScope(Dispatchers.getMain().plus(completableJobSupervisorJob$default));
        this.currentPlaylistItems = CollectionsKt.emptyList();
        this.browseTree = LazyKt.lazy(new Function0<BrowseTree>() { // from class: com.thor.app.auto.media.MusicService$browseTree$2
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final BrowseTree invoke() {
                Context applicationContext = this.this$0.getApplicationContext();
                Intrinsics.checkNotNullExpressionValue(applicationContext, "applicationContext");
                MusicSource musicSource = this.this$0.mediaSource;
                if (musicSource == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("mediaSource");
                    musicSource = null;
                }
                return new BrowseTree(applicationContext, musicSource);
            }
        });
        this.dataSourceFactory = LazyKt.lazy(new Function0<DefaultDataSourceFactory>() { // from class: com.thor.app.auto.media.MusicService$dataSourceFactory$2
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final DefaultDataSourceFactory invoke() {
                MusicService musicService = this.this$0;
                return new DefaultDataSourceFactory(musicService, Util.getUserAgent(musicService, BuildConfig.APPLICATION_ID), (TransferListener) null);
            }
        });
        AudioAttributes audioAttributesBuild = new AudioAttributes.Builder().setContentType(2).setUsage(1).build();
        Intrinsics.checkNotNullExpressionValue(audioAttributesBuild, "Builder()\n        .setCo…E_MEDIA)\n        .build()");
        this.uAmpAudioAttributes = audioAttributesBuild;
        this.playerListener = new PlayerEventListener();
        this.exoPlayer = LazyKt.lazy(new Function0<SimpleExoPlayer>() { // from class: com.thor.app.auto.media.MusicService$exoPlayer$2
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final SimpleExoPlayer invoke() {
                SimpleExoPlayer simpleExoPlayerBuild = new SimpleExoPlayer.Builder(this.this$0).build();
                MusicService musicService = this.this$0;
                simpleExoPlayerBuild.setAudioAttributes(musicService.uAmpAudioAttributes, true);
                simpleExoPlayerBuild.setHandleAudioBecomingNoisy(true);
                simpleExoPlayerBuild.addListener((Player.Listener) musicService.playerListener);
                return simpleExoPlayerBuild;
            }
        });
    }

    protected final MediaSessionCompat getMediaSession() {
        MediaSessionCompat mediaSessionCompat = this.mediaSession;
        if (mediaSessionCompat != null) {
            return mediaSessionCompat;
        }
        Intrinsics.throwUninitializedPropertyAccessException("mediaSession");
        return null;
    }

    protected final void setMediaSession(MediaSessionCompat mediaSessionCompat) {
        Intrinsics.checkNotNullParameter(mediaSessionCompat, "<set-?>");
        this.mediaSession = mediaSessionCompat;
    }

    protected final MediaSessionConnector getMediaSessionConnector() {
        MediaSessionConnector mediaSessionConnector = this.mediaSessionConnector;
        if (mediaSessionConnector != null) {
            return mediaSessionConnector;
        }
        Intrinsics.throwUninitializedPropertyAccessException("mediaSessionConnector");
        return null;
    }

    protected final void setMediaSessionConnector(MediaSessionConnector mediaSessionConnector) {
        Intrinsics.checkNotNullParameter(mediaSessionConnector, "<set-?>");
        this.mediaSessionConnector = mediaSessionConnector;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final BrowseTree getBrowseTree() {
        return (BrowseTree) this.browseTree.getValue();
    }

    private final DefaultDataSourceFactory getDataSourceFactory() {
        return (DefaultDataSourceFactory) this.dataSourceFactory.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final ExoPlayer getExoPlayer() {
        Object value = this.exoPlayer.getValue();
        Intrinsics.checkNotNullExpressionValue(value, "<get-exoPlayer>(...)");
        return (ExoPlayer) value;
    }

    @Override // androidx.media.MediaBrowserServiceCompat, android.app.Service
    public void onCreate() throws SecurityException {
        DemoSoundsSource demoSoundsSource;
        Intent launchIntentForPackage;
        super.onCreate();
        Timber.INSTANCE.d("MusicService: onCreate", new Object[0]);
        PackageManager packageManager = getPackageManager();
        AutoNotificationManager autoNotificationManager = null;
        PendingIntent activity = (packageManager == null || (launchIntentForPackage = packageManager.getLaunchIntentForPackage(getPackageName())) == null) ? null : PendingIntent.getActivity(this, 0, launchIntentForPackage, AccessibilityEventCompat.TYPE_VIEW_TARGETED_BY_SCROLL);
        MusicService musicService = this;
        MediaSessionCompat mediaSessionCompat = new MediaSessionCompat(musicService, "MusicService");
        mediaSessionCompat.setSessionActivity(activity);
        mediaSessionCompat.setActive(true);
        setMediaSession(mediaSessionCompat);
        setSessionToken(getMediaSession().getSessionToken());
        if (Settings.INSTANCE.isAccessSession()) {
            Context applicationContext = getApplicationContext();
            Intrinsics.checkNotNullExpressionValue(applicationContext, "applicationContext");
            demoSoundsSource = new DeviceSoundsSource(applicationContext);
        } else {
            Context applicationContext2 = getApplicationContext();
            Intrinsics.checkNotNullExpressionValue(applicationContext2, "applicationContext");
            demoSoundsSource = new DemoSoundsSource(applicationContext2);
        }
        this.mediaSource = demoSoundsSource;
        BuildersKt__Builders_commonKt.launch$default(this.serviceScope, null, null, new AnonymousClass2(null), 3, null);
        MediaSessionCompat.Token sessionToken = getMediaSession().getSessionToken();
        Intrinsics.checkNotNullExpressionValue(sessionToken, "mediaSession.sessionToken");
        this.notificationManager = new AutoNotificationManager(musicService, sessionToken, new PlayerNotificationListener());
        setMediaSessionConnector(new MediaSessionConnector(getMediaSession()));
        getMediaSessionConnector().setPlaybackPreparer(new UampPlaybackPreparer());
        getMediaSessionConnector().setQueueNavigator(new UampQueueNavigator(this, getMediaSession()));
        getMediaSessionConnector().setControlDispatcher(new CustomControllerDispatcher());
        getMediaSessionConnector().setPlayer(getExoPlayer());
        AutoNotificationManager autoNotificationManager2 = this.notificationManager;
        if (autoNotificationManager2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("notificationManager");
        } else {
            autoNotificationManager = autoNotificationManager2;
        }
        autoNotificationManager.showNotificationForPlayer(getExoPlayer());
        this.packageValidator = new PackageValidator(musicService, R.xml.allowed_media_browser_callers);
        EventBus.getDefault().register(this);
    }

    /* compiled from: MusicService.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 8, 0}, xi = 48)
    @DebugMetadata(c = "com.thor.app.auto.media.MusicService$onCreate$2", f = "MusicService.kt", i = {}, l = {162}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.thor.app.auto.media.MusicService$onCreate$2, reason: invalid class name */
    static final class AnonymousClass2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;

        AnonymousClass2(Continuation<? super AnonymousClass2> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return MusicService.this.new AnonymousClass2(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((AnonymousClass2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                MusicSource musicSource = MusicService.this.mediaSource;
                if (musicSource == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("mediaSource");
                    musicSource = null;
                }
                this.label = 1;
                if (musicSource.load(this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }
    }

    @Override // android.app.Service
    public void onTaskRemoved(Intent rootIntent) {
        Intrinsics.checkNotNullParameter(rootIntent, "rootIntent");
        super.onTaskRemoved(rootIntent);
        getExoPlayer().stop(true);
    }

    @Override // android.app.Service
    public void onDestroy() {
        Timber.INSTANCE.d("MusicService: onDestroy", new Object[0]);
        MediaSessionCompat mediaSession = getMediaSession();
        mediaSession.setActive(false);
        mediaSession.release();
        Job.DefaultImpls.cancel$default((Job) this.serviceJob, (CancellationException) null, 1, (Object) null);
        getExoPlayer().removeListener((Player.Listener) this.playerListener);
        getExoPlayer().release();
        EventBus.getDefault().unregister(this);
    }

    @Override // androidx.media.MediaBrowserServiceCompat
    public MediaBrowserServiceCompat.BrowserRoot onGetRoot(String clientPackageName, int clientUid, Bundle rootHints) {
        Intrinsics.checkNotNullParameter(clientPackageName, "clientPackageName");
        PackageValidator packageValidator = this.packageValidator;
        MusicSource musicSource = null;
        if (packageValidator == null) {
            Intrinsics.throwUninitializedPropertyAccessException("packageValidator");
            packageValidator = null;
        }
        boolean zIsKnownCaller = packageValidator.isKnownCaller(clientPackageName, clientUid);
        Bundle bundle = new Bundle();
        bundle.putBoolean("android.media.browse.SEARCH_SUPPORTED", zIsKnownCaller || getBrowseTree().getSearchableByUnknownCaller());
        bundle.putBoolean("android.media.browse.CONTENT_STYLE_SUPPORTED", true);
        bundle.putInt(MediaConstants.DESCRIPTION_EXTRAS_KEY_CONTENT_STYLE_BROWSABLE, 2);
        MusicSource musicSource2 = this.mediaSource;
        if (musicSource2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mediaSource");
        } else {
            musicSource = musicSource2;
        }
        if (musicSource instanceof DeviceSoundsSource) {
            bundle.putInt(MediaConstants.DESCRIPTION_EXTRAS_KEY_CONTENT_STYLE_PLAYABLE, 2);
        } else {
            bundle.putInt(MediaConstants.DESCRIPTION_EXTRAS_KEY_CONTENT_STYLE_PLAYABLE, 1);
        }
        if (zIsKnownCaller) {
            return new MediaBrowserServiceCompat.BrowserRoot("/", bundle);
        }
        return new MediaBrowserServiceCompat.BrowserRoot(BrowseTreeKt.UAMP_EMPTY_ROOT, bundle);
    }

    @Override // androidx.media.MediaBrowserServiceCompat
    public void onLoadChildren(final String parentMediaId, final MediaBrowserServiceCompat.Result<List<MediaBrowserCompat.MediaItem>> result) {
        Intrinsics.checkNotNullParameter(parentMediaId, "parentMediaId");
        Intrinsics.checkNotNullParameter(result, "result");
        Timber.INSTANCE.d("MusicService: onLoadChildren " + parentMediaId, new Object[0]);
        MusicSource musicSource = this.mediaSource;
        if (musicSource == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mediaSource");
            musicSource = null;
        }
        if (musicSource.whenReady(new Function1<Boolean, Unit>() { // from class: com.thor.app.auto.media.MusicService$onLoadChildren$resultsSent$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Boolean bool) {
                invoke(bool.booleanValue());
                return Unit.INSTANCE;
            }

            public final void invoke(boolean z) {
                Timber.Companion companion = Timber.INSTANCE;
                MusicSource musicSource2 = this.this$0.mediaSource;
                ArrayList arrayList = null;
                if (musicSource2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("mediaSource");
                    musicSource2 = null;
                }
                companion.d("mediaSource: " + musicSource2 + ", " + z, new Object[0]);
                if (z) {
                    List<MediaMetadataCompat> list = this.this$0.getBrowseTree().get(parentMediaId);
                    if (list != null) {
                        List<MediaMetadataCompat> list2 = list;
                        ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
                        for (MediaMetadataCompat mediaMetadataCompat : list2) {
                            arrayList2.add(new MediaBrowserCompat.MediaItem(mediaMetadataCompat.getDescription(), (int) mediaMetadataCompat.getLong(MediaMetadataCompatExtKt.METADATA_KEY_UAMP_FLAGS)));
                        }
                        arrayList = arrayList2;
                    }
                    result.sendResult(arrayList);
                    return;
                }
                this.this$0.getMediaSession().sendSessionEvent(MusicServiceKt.NETWORK_FAILURE, null);
                result.sendResult(null);
            }
        })) {
            return;
        }
        result.detach();
    }

    @Override // androidx.media.MediaBrowserServiceCompat
    public void onSearch(final String query, final Bundle extras, final MediaBrowserServiceCompat.Result<List<MediaBrowserCompat.MediaItem>> result) {
        Intrinsics.checkNotNullParameter(query, "query");
        Intrinsics.checkNotNullParameter(result, "result");
        MusicSource musicSource = this.mediaSource;
        if (musicSource == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mediaSource");
            musicSource = null;
        }
        if (musicSource.whenReady(new Function1<Boolean, Unit>() { // from class: com.thor.app.auto.media.MusicService$onSearch$resultsSent$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Boolean bool) {
                invoke(bool.booleanValue());
                return Unit.INSTANCE;
            }

            public final void invoke(boolean z) {
                if (z) {
                    MusicSource musicSource2 = this.this$0.mediaSource;
                    if (musicSource2 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("mediaSource");
                        musicSource2 = null;
                    }
                    String str = query;
                    Bundle bundle = extras;
                    if (bundle == null) {
                        bundle = Bundle.EMPTY;
                    }
                    Intrinsics.checkNotNullExpressionValue(bundle, "extras ?: Bundle.EMPTY");
                    List<MediaMetadataCompat> listSearch = musicSource2.search(str, bundle);
                    ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listSearch, 10));
                    for (MediaMetadataCompat mediaMetadataCompat : listSearch) {
                        arrayList.add(new MediaBrowserCompat.MediaItem(mediaMetadataCompat.getDescription(), (int) mediaMetadataCompat.getLong(MediaMetadataCompatExtKt.METADATA_KEY_UAMP_FLAGS)));
                    }
                    result.sendResult(arrayList);
                }
            }
        })) {
            return;
        }
        result.detach();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void preparePlaylist(List<MediaMetadataCompat> metadataList, MediaMetadataCompat itemToPlay, boolean playWhenReady, long playbackStartPositionMs) throws Throwable {
        int iIndexOf = itemToPlay == null ? 0 : metadataList.indexOf(itemToPlay);
        this.currentPlaylistItems = metadataList;
        getExoPlayer().stop(true);
        MusicSource musicSource = this.mediaSource;
        if (musicSource == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mediaSource");
            musicSource = null;
        }
        if (musicSource instanceof DeviceSoundsSource) {
            handleDevicePreset(itemToPlay);
            return;
        }
        ConcatenatingMediaSource mediaSource = MediaMetadataCompatExtKt.toMediaSource(metadataList, getDataSourceFactory());
        getExoPlayer().setPlayWhenReady(playWhenReady);
        getExoPlayer().prepare(mediaSource);
        getExoPlayer().seekTo(iIndexOf, playbackStartPositionMs);
    }

    private final PlaybackStateCompat getSupportedPlaybackActions(int state) {
        PlaybackStateCompat playbackStateCompatBuild = new PlaybackStateCompat.Builder().setActions(getPlaybackCapabilities()).setState(state, 0L, 0.0f).build();
        Intrinsics.checkNotNullExpressionValue(playbackStateCompatBuild, "Builder()\n            .s… 0f)\n            .build()");
        return playbackStateCompatBuild;
    }

    private final void handleDevicePreset(MediaMetadataCompat mediaMetadataCompat) throws Throwable {
        String string;
        getMediaSession().setMetadata(mediaMetadataCompat);
        getMediaSession().setPlaybackState(getSupportedPlaybackActions(3));
        if (mediaMetadataCompat == null || (string = mediaMetadataCompat.getString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID)) == null) {
            string = "";
        }
        onStartWorker(new Pair<>(BleCommandsWorker.ACTION_TURN_ON_PRESET, string));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void handleNextPresetEvent() throws Throwable {
        onStartWorker(new Pair<>(BleCommandsWorker.ACTION_NEXT_PRESET, ""));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void handlePreviousPresetEvent() throws Throwable {
        onStartWorker(new Pair<>(BleCommandsWorker.ACTION_PREVIOUS_PRESET, ""));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void handlePauseEvent() throws Throwable {
        onStartWorker(new Pair<>(BleCommandsWorker.ACTION_TURN_OFF_PRESET, ""));
        getMediaSession().setPlaybackState(getSupportedPlaybackActions(2));
        stopForeground(true);
    }

    private final void onStartWorker(Pair<String, String> action) throws Throwable {
        Timber.INSTANCE.d("onStartWorker: " + ((Object) action.getFirst()) + "-" + ((Object) action.getSecond()), new Object[0]);
        Constraints constraintsBuild = new Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build();
        Intrinsics.checkNotNullExpressionValue(constraintsBuild, "Builder().setRequiredNet…rkType.CONNECTED).build()");
        Data dataBuild = new Data.Builder().putString(BleCommandsWorker.INPUT_DATA_COMMAND, action.getFirst()).putString(BleCommandsWorker.INPUT_DATA_PRESET_INFO, action.getSecond()).build();
        Intrinsics.checkNotNullExpressionValue(dataBuild, "Builder()\n            .p…ond)\n            .build()");
        OneTimeWorkRequest oneTimeWorkRequestBuild = new OneTimeWorkRequest.Builder(BleCommandsWorker.class).setConstraints(constraintsBuild).setInputData(dataBuild).build();
        Intrinsics.checkNotNullExpressionValue(oneTimeWorkRequestBuild, "Builder(BleCommandsWorke…utData(inputData).build()");
        WorkManager.getInstance().enqueue(oneTimeWorkRequestBuild);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public final void onMessageEvent(SwitchedPresetEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Timber.INSTANCE.i("onMessageEvent: %s", event);
        Log.i("PRS", "onMessageEvent: " + event);
        MusicSource musicSource = this.mediaSource;
        if (musicSource == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mediaSource");
            musicSource = null;
        }
        DeviceSoundsSource deviceSoundsSource = musicSource instanceof DeviceSoundsSource ? (DeviceSoundsSource) musicSource : null;
        MediaMetadataCompat activePresetMetadata = deviceSoundsSource != null ? deviceSoundsSource.getActivePresetMetadata() : null;
        if (activePresetMetadata != null) {
            Log.i("PRS", "onMessageEvent: if");
            getMediaSession().setMetadata(activePresetMetadata);
            getMediaSession().setPlaybackState(getSupportedPlaybackActions(3));
        } else {
            Log.i("PRS", "onMessageEvent: else");
            getMediaSession().setPlaybackState(getSupportedPlaybackActions(0));
            getMediaSession().setMetadata(null);
        }
    }

    /* compiled from: MusicService.kt */
    @Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0082\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0018\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0007\u001a\u00020\bH\u0016J\u0018\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016J\u0018\u0010\u0011\u001a\u00020\u000e2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016¨\u0006\u0012"}, d2 = {"Lcom/thor/app/auto/media/MusicService$UampQueueNavigator;", "Lcom/google/android/exoplayer2/ext/mediasession/TimelineQueueNavigator;", "mediaSession", "Landroid/support/v4/media/session/MediaSessionCompat;", "(Lcom/thor/app/auto/media/MusicService;Landroid/support/v4/media/session/MediaSessionCompat;)V", "getMediaDescription", "Landroid/support/v4/media/MediaDescriptionCompat;", "player", "Lcom/google/android/exoplayer2/Player;", "windowIndex", "", "getSupportedQueueNavigatorActions", "", "onSkipToNext", "", "controlDispatcher", "Lcom/google/android/exoplayer2/ControlDispatcher;", "onSkipToPrevious", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    private final class UampQueueNavigator extends TimelineQueueNavigator {
        final /* synthetic */ MusicService this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public UampQueueNavigator(MusicService musicService, MediaSessionCompat mediaSession) {
            super(mediaSession);
            Intrinsics.checkNotNullParameter(mediaSession, "mediaSession");
            this.this$0 = musicService;
        }

        @Override // com.google.android.exoplayer2.ext.mediasession.TimelineQueueNavigator
        public MediaDescriptionCompat getMediaDescription(Player player, int windowIndex) {
            Intrinsics.checkNotNullParameter(player, "player");
            MediaDescriptionCompat description = ((MediaMetadataCompat) this.this$0.currentPlaylistItems.get(windowIndex)).getDescription();
            Intrinsics.checkNotNullExpressionValue(description, "currentPlaylistItems[windowIndex].description");
            return description;
        }

        @Override // com.google.android.exoplayer2.ext.mediasession.TimelineQueueNavigator, com.google.android.exoplayer2.ext.mediasession.MediaSessionConnector.QueueNavigator
        public long getSupportedQueueNavigatorActions(Player player) {
            Intrinsics.checkNotNullParameter(player, "player");
            MusicSource musicSource = this.this$0.mediaSource;
            if (musicSource == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mediaSource");
                musicSource = null;
            }
            if (musicSource instanceof DeviceSoundsSource) {
                return 48L;
            }
            return super.getSupportedQueueNavigatorActions(player);
        }

        @Override // com.google.android.exoplayer2.ext.mediasession.TimelineQueueNavigator, com.google.android.exoplayer2.ext.mediasession.MediaSessionConnector.QueueNavigator
        public void onSkipToPrevious(Player player, ControlDispatcher controlDispatcher) throws Throwable {
            Intrinsics.checkNotNullParameter(player, "player");
            Intrinsics.checkNotNullParameter(controlDispatcher, "controlDispatcher");
            super.onSkipToPrevious(player, controlDispatcher);
            this.this$0.handlePreviousPresetEvent();
        }

        @Override // com.google.android.exoplayer2.ext.mediasession.TimelineQueueNavigator, com.google.android.exoplayer2.ext.mediasession.MediaSessionConnector.QueueNavigator
        public void onSkipToNext(Player player, ControlDispatcher controlDispatcher) throws Throwable {
            Intrinsics.checkNotNullParameter(player, "player");
            Intrinsics.checkNotNullParameter(controlDispatcher, "controlDispatcher");
            super.onSkipToNext(player, controlDispatcher);
            this.this$0.handleNextPresetEvent();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: MusicService.kt */
    @Metadata(d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\b\u0082\u0004\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0006\u001a\u00020\u0005H\u0002J\b\u0010\u0007\u001a\u00020\bH\u0016J4\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u0014H\u0016J\u0010\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\nH\u0016J\"\u0010\u0018\u001a\u00020\u00162\u0006\u0010\u0019\u001a\u00020\u00102\u0006\u0010\u0017\u001a\u00020\n2\b\u0010\u0011\u001a\u0004\u0018\u00010\u0012H\u0016J\"\u0010\u001a\u001a\u00020\u00162\u0006\u0010\u001b\u001a\u00020\u00102\u0006\u0010\u0017\u001a\u00020\n2\b\u0010\u0011\u001a\u0004\u0018\u00010\u0012H\u0016J\"\u0010\u001c\u001a\u00020\u00162\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u0017\u001a\u00020\n2\b\u0010\u0011\u001a\u0004\u0018\u00010\u0012H\u0016¨\u0006\u001f"}, d2 = {"Lcom/thor/app/auto/media/MusicService$UampPlaybackPreparer;", "Lcom/google/android/exoplayer2/ext/mediasession/MediaSessionConnector$PlaybackPreparer;", "(Lcom/thor/app/auto/media/MusicService;)V", "buildPlaylist", "", "Landroid/support/v4/media/MediaMetadataCompat;", "item", "getSupportedPrepareActions", "", "onCommand", "", "player", "Lcom/google/android/exoplayer2/Player;", "controlDispatcher", "Lcom/google/android/exoplayer2/ControlDispatcher;", BleCommandsWorker.INPUT_DATA_COMMAND, "", "extras", "Landroid/os/Bundle;", "cb", "Landroid/os/ResultReceiver;", "onPrepare", "", "playWhenReady", "onPrepareFromMediaId", "mediaId", "onPrepareFromSearch", SearchIntents.EXTRA_QUERY, "onPrepareFromUri", "uri", "Landroid/net/Uri;", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    final class UampPlaybackPreparer implements MediaSessionConnector.PlaybackPreparer {
        @Override // com.google.android.exoplayer2.ext.mediasession.MediaSessionConnector.PlaybackPreparer
        public long getSupportedPrepareActions() {
            return 101889L;
        }

        @Override // com.google.android.exoplayer2.ext.mediasession.MediaSessionConnector.CommandReceiver
        public boolean onCommand(Player player, ControlDispatcher controlDispatcher, String command, Bundle extras, ResultReceiver cb) {
            Intrinsics.checkNotNullParameter(player, "player");
            Intrinsics.checkNotNullParameter(controlDispatcher, "controlDispatcher");
            Intrinsics.checkNotNullParameter(command, "command");
            return false;
        }

        @Override // com.google.android.exoplayer2.ext.mediasession.MediaSessionConnector.PlaybackPreparer
        public void onPrepare(boolean playWhenReady) {
        }

        @Override // com.google.android.exoplayer2.ext.mediasession.MediaSessionConnector.PlaybackPreparer
        public void onPrepareFromUri(Uri uri, boolean playWhenReady, Bundle extras) {
            Intrinsics.checkNotNullParameter(uri, "uri");
        }

        public UampPlaybackPreparer() {
        }

        @Override // com.google.android.exoplayer2.ext.mediasession.MediaSessionConnector.PlaybackPreparer
        public void onPrepareFromMediaId(final String mediaId, final boolean playWhenReady, final Bundle extras) {
            Intrinsics.checkNotNullParameter(mediaId, "mediaId");
            MusicSource musicSource = MusicService.this.mediaSource;
            if (musicSource == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mediaSource");
                musicSource = null;
            }
            final MusicService musicService = MusicService.this;
            musicSource.whenReady(new Function1<Boolean, Unit>() { // from class: com.thor.app.auto.media.MusicService$UampPlaybackPreparer$onPrepareFromMediaId$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(Boolean bool) throws Throwable {
                    invoke(bool.booleanValue());
                    return Unit.INSTANCE;
                }

                public final void invoke(boolean z) throws Throwable {
                    MusicSource musicSource2 = musicService.mediaSource;
                    MediaMetadataCompat mediaMetadataCompat = null;
                    if (musicSource2 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("mediaSource");
                        musicSource2 = null;
                    }
                    String str = mediaId;
                    Iterator<MediaMetadataCompat> it = musicSource2.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            break;
                        }
                        MediaMetadataCompat next = it.next();
                        if (Intrinsics.areEqual(next.getString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID), str)) {
                            mediaMetadataCompat = next;
                            break;
                        }
                    }
                    MediaMetadataCompat mediaMetadataCompat2 = mediaMetadataCompat;
                    if (mediaMetadataCompat2 == null) {
                        Log.w("MusicService", "Content not found: MediaID=" + mediaId);
                        return;
                    }
                    Bundle bundle = extras;
                    long j = C.TIME_UNSET;
                    if (bundle != null) {
                        j = bundle.getLong(MusicServiceKt.MEDIA_DESCRIPTION_EXTRAS_START_PLAYBACK_POSITION_MS, C.TIME_UNSET);
                    }
                    musicService.preparePlaylist(this.buildPlaylist(mediaMetadataCompat2), mediaMetadataCompat2, playWhenReady, j);
                }
            });
        }

        @Override // com.google.android.exoplayer2.ext.mediasession.MediaSessionConnector.PlaybackPreparer
        public void onPrepareFromSearch(final String query, final boolean playWhenReady, final Bundle extras) {
            Intrinsics.checkNotNullParameter(query, "query");
            MusicSource musicSource = MusicService.this.mediaSource;
            if (musicSource == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mediaSource");
                musicSource = null;
            }
            final MusicService musicService = MusicService.this;
            musicSource.whenReady(new Function1<Boolean, Unit>() { // from class: com.thor.app.auto.media.MusicService$UampPlaybackPreparer$onPrepareFromSearch$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(Boolean bool) throws Throwable {
                    invoke(bool.booleanValue());
                    return Unit.INSTANCE;
                }

                public final void invoke(boolean z) throws Throwable {
                    MusicSource musicSource2 = musicService.mediaSource;
                    if (musicSource2 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("mediaSource");
                        musicSource2 = null;
                    }
                    String str = query;
                    Bundle bundle = extras;
                    if (bundle == null) {
                        bundle = Bundle.EMPTY;
                    }
                    Intrinsics.checkNotNullExpressionValue(bundle, "extras ?: Bundle.EMPTY");
                    List<MediaMetadataCompat> listSearch = musicSource2.search(str, bundle);
                    if (!listSearch.isEmpty()) {
                        musicService.preparePlaylist(listSearch, listSearch.get(0), playWhenReady, C.TIME_UNSET);
                    }
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final List<MediaMetadataCompat> buildPlaylist(MediaMetadataCompat item) {
            MusicSource musicSource = MusicService.this.mediaSource;
            if (musicSource == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mediaSource");
                musicSource = null;
            }
            ArrayList arrayList = new ArrayList();
            for (MediaMetadataCompat mediaMetadataCompat : musicSource) {
                if (Intrinsics.areEqual(mediaMetadataCompat.getString(MediaMetadataCompat.METADATA_KEY_ALBUM), item.getString(MediaMetadataCompat.METADATA_KEY_ALBUM))) {
                    arrayList.add(mediaMetadataCompat);
                }
            }
            return CollectionsKt.sortedWith(arrayList, new Comparator() { // from class: com.thor.app.auto.media.MusicService$UampPlaybackPreparer$buildPlaylist$$inlined$sortedBy$1
                /* JADX WARN: Multi-variable type inference failed */
                @Override // java.util.Comparator
                public final int compare(T t, T t2) {
                    return ComparisonsKt.compareValues(Long.valueOf(((MediaMetadataCompat) t).getLong(MediaMetadataCompat.METADATA_KEY_TRACK_NUMBER)), Long.valueOf(((MediaMetadataCompat) t2).getLong(MediaMetadataCompat.METADATA_KEY_TRACK_NUMBER)));
                }
            });
        }
    }

    /* compiled from: MusicService.kt */
    @Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0082\u0004\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016J \u0010\t\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\bH\u0016¨\u0006\r"}, d2 = {"Lcom/thor/app/auto/media/MusicService$PlayerNotificationListener;", "Lcom/google/android/exoplayer2/ui/PlayerNotificationManager$NotificationListener;", "(Lcom/thor/app/auto/media/MusicService;)V", "onNotificationCancelled", "", "notificationId", "", "dismissedByUser", "", "onNotificationPosted", "notification", "Landroid/app/Notification;", "ongoing", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    private final class PlayerNotificationListener implements PlayerNotificationManager.NotificationListener {
        public PlayerNotificationListener() {
        }

        @Override // com.google.android.exoplayer2.ui.PlayerNotificationManager.NotificationListener
        public void onNotificationPosted(int notificationId, Notification notification, boolean ongoing) {
            Intrinsics.checkNotNullParameter(notification, "notification");
            if (!ongoing || MusicService.this.isForegroundService) {
                return;
            }
            ContextCompat.startForegroundService(MusicService.this.getApplicationContext(), new Intent(MusicService.this.getApplicationContext(), MusicService.this.getClass()));
            MusicService.this.startForeground(notificationId, notification);
            MusicService.this.isForegroundService = true;
        }

        @Override // com.google.android.exoplayer2.ui.PlayerNotificationManager.NotificationListener
        public void onNotificationCancelled(int notificationId, boolean dismissedByUser) {
            MusicService.this.stopForeground(true);
            MusicService.this.isForegroundService = false;
            MusicService.this.stopSelf();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: MusicService.kt */
    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0000\b\u0082\u0004\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\u0018\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016¨\u0006\f"}, d2 = {"Lcom/thor/app/auto/media/MusicService$PlayerEventListener;", "Lcom/google/android/exoplayer2/Player$Listener;", "(Lcom/thor/app/auto/media/MusicService;)V", "onPlayerError", "", Constants.IPC_BUNDLE_KEY_SEND_ERROR, "Lcom/google/android/exoplayer2/PlaybackException;", "onPlayerStateChanged", "playWhenReady", "", "playbackState", "", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    final class PlayerEventListener implements Player.Listener {
        public PlayerEventListener() {
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
            AutoNotificationManager autoNotificationManager = null;
            if (playbackState == 2 || playbackState == 3) {
                AutoNotificationManager autoNotificationManager2 = MusicService.this.notificationManager;
                if (autoNotificationManager2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("notificationManager");
                } else {
                    autoNotificationManager = autoNotificationManager2;
                }
                autoNotificationManager.showNotificationForPlayer(MusicService.this.getExoPlayer());
                if (playbackState != 3 || playWhenReady) {
                    return;
                }
                MusicService.this.stopForeground(false);
                MusicService.this.isForegroundService = false;
                return;
            }
            AutoNotificationManager autoNotificationManager3 = MusicService.this.notificationManager;
            if (autoNotificationManager3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("notificationManager");
            } else {
                autoNotificationManager = autoNotificationManager3;
            }
            autoNotificationManager.hideNotification();
        }

        @Override // com.google.android.exoplayer2.Player.Listener, com.google.android.exoplayer2.Player.EventListener
        public void onPlayerError(PlaybackException error) {
            Intrinsics.checkNotNullParameter(error, "error");
            Log.e("MusicService", "TYPE_REMOTE: " + error.getMessage());
            Toast.makeText(MusicService.this.getApplicationContext(), R.string.generic_error, 1).show();
        }
    }

    /* compiled from: MusicService.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0082\u0004\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0004H\u0016¨\u0006\b"}, d2 = {"Lcom/thor/app/auto/media/MusicService$CustomControllerDispatcher;", "Lcom/google/android/exoplayer2/DefaultControlDispatcher;", "(Lcom/thor/app/auto/media/MusicService;)V", "dispatchSetPlayWhenReady", "", "player", "Lcom/google/android/exoplayer2/Player;", "playWhenReady", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    private final class CustomControllerDispatcher extends DefaultControlDispatcher {
        public CustomControllerDispatcher() {
        }

        @Override // com.google.android.exoplayer2.DefaultControlDispatcher, com.google.android.exoplayer2.ControlDispatcher
        public boolean dispatchSetPlayWhenReady(Player player, boolean playWhenReady) throws Throwable {
            Intrinsics.checkNotNullParameter(player, "player");
            if (!playWhenReady) {
                MusicService.this.handlePauseEvent();
            }
            return super.dispatchSetPlayWhenReady(player, playWhenReady);
        }
    }
}
