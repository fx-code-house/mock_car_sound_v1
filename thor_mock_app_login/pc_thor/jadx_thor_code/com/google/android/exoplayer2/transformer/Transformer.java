package com.google.android.exoplayer2.transformer;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.ParcelFileDescriptor;
import androidx.recyclerview.widget.ItemTouchHelper;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.PlaybackException;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.Renderer;
import com.google.android.exoplayer2.RenderersFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.analytics.AnalyticsListener;
import com.google.android.exoplayer2.audio.AudioRendererEventListener;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.metadata.MetadataOutput;
import com.google.android.exoplayer2.source.DefaultMediaSourceFactory;
import com.google.android.exoplayer2.source.MediaSourceFactory;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.text.TextOutput;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.transformer.FrameworkMuxer;
import com.google.android.exoplayer2.transformer.Muxer;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Clock;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.video.VideoRendererEventListener;
import com.google.firebase.iid.ServiceStarter;
import java.io.IOException;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes.dex */
public final class Transformer {
    public static final int PROGRESS_STATE_AVAILABLE = 1;
    public static final int PROGRESS_STATE_NO_TRANSFORMATION = 4;
    public static final int PROGRESS_STATE_UNAVAILABLE = 2;
    public static final int PROGRESS_STATE_WAITING_FOR_AVAILABILITY = 0;
    private final Clock clock;
    private final Context context;
    private Listener listener;
    private final Looper looper;
    private final MediaSourceFactory mediaSourceFactory;
    private final Muxer.Factory muxerFactory;
    private MuxerWrapper muxerWrapper;
    private SimpleExoPlayer player;
    private int progressState;
    private final Transformation transformation;

    public interface Listener {
        default void onTransformationCompleted(MediaItem inputMediaItem) {
        }

        default void onTransformationError(MediaItem inputMediaItem, Exception exception) {
        }
    }

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface ProgressState {
    }

    public static final class Builder {
        private Clock clock;
        private Context context;
        private boolean flattenForSlowMotion;
        private Listener listener;
        private Looper looper;
        private MediaSourceFactory mediaSourceFactory;
        private Muxer.Factory muxerFactory;
        private String outputMimeType;
        private boolean removeAudio;
        private boolean removeVideo;

        public Builder() {
            this.muxerFactory = new FrameworkMuxer.Factory();
            this.outputMimeType = MimeTypes.VIDEO_MP4;
            this.listener = new Listener(this) { // from class: com.google.android.exoplayer2.transformer.Transformer.Builder.1
            };
            this.looper = Util.getCurrentOrMainLooper();
            this.clock = Clock.DEFAULT;
        }

        private Builder(Transformer transformer) {
            this.context = transformer.context;
            this.mediaSourceFactory = transformer.mediaSourceFactory;
            this.muxerFactory = transformer.muxerFactory;
            this.removeAudio = transformer.transformation.removeAudio;
            this.removeVideo = transformer.transformation.removeVideo;
            this.flattenForSlowMotion = transformer.transformation.flattenForSlowMotion;
            this.outputMimeType = transformer.transformation.outputMimeType;
            this.listener = transformer.listener;
            this.looper = transformer.looper;
            this.clock = transformer.clock;
        }

        public Builder setContext(Context context) {
            this.context = context.getApplicationContext();
            return this;
        }

        public Builder setMediaSourceFactory(MediaSourceFactory mediaSourceFactory) {
            this.mediaSourceFactory = mediaSourceFactory;
            return this;
        }

        public Builder setRemoveAudio(boolean removeAudio) {
            this.removeAudio = removeAudio;
            return this;
        }

        public Builder setRemoveVideo(boolean removeVideo) {
            this.removeVideo = removeVideo;
            return this;
        }

        public Builder setFlattenForSlowMotion(boolean flattenForSlowMotion) {
            this.flattenForSlowMotion = flattenForSlowMotion;
            return this;
        }

        public Builder setOutputMimeType(String outputMimeType) {
            this.outputMimeType = outputMimeType;
            return this;
        }

        public Builder setListener(Listener listener) {
            this.listener = listener;
            return this;
        }

        public Builder setLooper(Looper looper) {
            this.looper = looper;
            return this;
        }

        Builder setClock(Clock clock) {
            this.clock = clock;
            return this;
        }

        Builder setMuxerFactory(Muxer.Factory muxerFactory) {
            this.muxerFactory = muxerFactory;
            return this;
        }

        public Transformer build() {
            Assertions.checkStateNotNull(this.context);
            if (this.mediaSourceFactory == null) {
                DefaultExtractorsFactory defaultExtractorsFactory = new DefaultExtractorsFactory();
                if (this.flattenForSlowMotion) {
                    defaultExtractorsFactory.setMp4ExtractorFlags(4);
                }
                this.mediaSourceFactory = new DefaultMediaSourceFactory(this.context, defaultExtractorsFactory);
            }
            boolean zSupportsOutputMimeType = this.muxerFactory.supportsOutputMimeType(this.outputMimeType);
            String strValueOf = String.valueOf(this.outputMimeType);
            Assertions.checkState(zSupportsOutputMimeType, strValueOf.length() != 0 ? "Unsupported output MIME type: ".concat(strValueOf) : new String("Unsupported output MIME type: "));
            return new Transformer(this.context, this.mediaSourceFactory, this.muxerFactory, new Transformation(this.removeAudio, this.removeVideo, this.flattenForSlowMotion, this.outputMimeType), this.listener, this.looper, this.clock);
        }
    }

    private Transformer(Context context, MediaSourceFactory mediaSourceFactory, Muxer.Factory muxerFactory, Transformation transformation, Listener listener, Looper looper, Clock clock) {
        Assertions.checkState((transformation.removeAudio && transformation.removeVideo) ? false : true, "Audio and video cannot both be removed.");
        this.context = context;
        this.mediaSourceFactory = mediaSourceFactory;
        this.muxerFactory = muxerFactory;
        this.transformation = transformation;
        this.listener = listener;
        this.looper = looper;
        this.clock = clock;
        this.progressState = 4;
    }

    public Builder buildUpon() {
        return new Builder();
    }

    public void setListener(Listener listener) {
        verifyApplicationThread();
        this.listener = listener;
    }

    public void startTransformation(MediaItem mediaItem, String path) throws IOException {
        startTransformation(mediaItem, this.muxerFactory.create(path, this.transformation.outputMimeType));
    }

    public void startTransformation(MediaItem mediaItem, ParcelFileDescriptor parcelFileDescriptor) throws IOException {
        startTransformation(mediaItem, this.muxerFactory.create(parcelFileDescriptor, this.transformation.outputMimeType));
    }

    private void startTransformation(MediaItem mediaItem, Muxer muxer) {
        verifyApplicationThread();
        if (this.player != null) {
            throw new IllegalStateException("There is already a transformation in progress.");
        }
        MuxerWrapper muxerWrapper = new MuxerWrapper(muxer);
        this.muxerWrapper = muxerWrapper;
        DefaultTrackSelector defaultTrackSelector = new DefaultTrackSelector(this.context);
        defaultTrackSelector.setParameters(new DefaultTrackSelector.ParametersBuilder(this.context).setForceHighestSupportedBitrate(true).build());
        SimpleExoPlayer simpleExoPlayerBuild = new SimpleExoPlayer.Builder(this.context, new TransformerRenderersFactory(muxerWrapper, this.transformation)).setMediaSourceFactory(this.mediaSourceFactory).setTrackSelector(defaultTrackSelector).setLoadControl(new DefaultLoadControl.Builder().setBufferDurationsMs(50000, 50000, ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION, ServiceStarter.ERROR_UNKNOWN).build()).setLooper(this.looper).setClock(this.clock).build();
        this.player = simpleExoPlayerBuild;
        simpleExoPlayerBuild.setMediaItem(mediaItem);
        this.player.addAnalyticsListener(new TransformerAnalyticsListener(mediaItem, muxerWrapper));
        this.player.prepare();
        this.progressState = 0;
    }

    public Looper getApplicationLooper() {
        return this.looper;
    }

    public int getProgress(ProgressHolder progressHolder) {
        verifyApplicationThread();
        if (this.progressState == 1) {
            Player player = (Player) Assertions.checkNotNull(this.player);
            progressHolder.progress = Math.min((int) ((player.getCurrentPosition() * 100) / player.getDuration()), 99);
        }
        return this.progressState;
    }

    public void cancel() {
        releaseResources(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void releaseResources(boolean forCancellation) {
        verifyApplicationThread();
        SimpleExoPlayer simpleExoPlayer = this.player;
        if (simpleExoPlayer != null) {
            simpleExoPlayer.release();
            this.player = null;
        }
        MuxerWrapper muxerWrapper = this.muxerWrapper;
        if (muxerWrapper != null) {
            muxerWrapper.release(forCancellation);
            this.muxerWrapper = null;
        }
        this.progressState = 4;
    }

    private void verifyApplicationThread() {
        if (Looper.myLooper() != this.looper) {
            throw new IllegalStateException("Transformer is accessed on the wrong thread.");
        }
    }

    private static final class TransformerRenderersFactory implements RenderersFactory {
        private final TransformerMediaClock mediaClock = new TransformerMediaClock();
        private final MuxerWrapper muxerWrapper;
        private final Transformation transformation;

        public TransformerRenderersFactory(MuxerWrapper muxerWrapper, Transformation transformation) {
            this.muxerWrapper = muxerWrapper;
            this.transformation = transformation;
        }

        @Override // com.google.android.exoplayer2.RenderersFactory
        public Renderer[] createRenderers(Handler eventHandler, VideoRendererEventListener videoRendererEventListener, AudioRendererEventListener audioRendererEventListener, TextOutput textRendererOutput, MetadataOutput metadataRendererOutput) {
            char c = 1;
            Renderer[] rendererArr = new Renderer[(this.transformation.removeAudio || this.transformation.removeVideo) ? 1 : 2];
            if (this.transformation.removeAudio) {
                c = 0;
            } else {
                rendererArr[0] = new TransformerAudioRenderer(this.muxerWrapper, this.mediaClock, this.transformation);
            }
            if (!this.transformation.removeVideo) {
                rendererArr[c] = new TransformerVideoRenderer(this.muxerWrapper, this.mediaClock, this.transformation);
            }
            return rendererArr;
        }
    }

    private final class TransformerAnalyticsListener implements AnalyticsListener {
        private final MediaItem mediaItem;
        private final MuxerWrapper muxerWrapper;

        public TransformerAnalyticsListener(MediaItem mediaItem, MuxerWrapper muxerWrapper) {
            this.mediaItem = mediaItem;
            this.muxerWrapper = muxerWrapper;
        }

        @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
        public void onPlaybackStateChanged(AnalyticsListener.EventTime eventTime, int state) {
            if (state == 4) {
                handleTransformationEnded(null);
            }
        }

        @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
        public void onTimelineChanged(AnalyticsListener.EventTime eventTime, int reason) {
            if (Transformer.this.progressState != 0) {
                return;
            }
            Timeline.Window window = new Timeline.Window();
            eventTime.timeline.getWindow(0, window);
            if (window.isPlaceholder) {
                return;
            }
            long j = window.durationUs;
            Transformer.this.progressState = (j <= 0 || j == C.TIME_UNSET) ? 2 : 1;
            ((SimpleExoPlayer) Assertions.checkNotNull(Transformer.this.player)).play();
        }

        @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
        public void onTracksChanged(AnalyticsListener.EventTime eventTime, TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
            if (this.muxerWrapper.getTrackCount() == 0) {
                handleTransformationEnded(new IllegalStateException("The output does not contain any tracks. Check that at least one of the input sample formats is supported."));
            }
        }

        @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
        public void onPlayerError(AnalyticsListener.EventTime eventTime, PlaybackException error) {
            handleTransformationEnded(error);
        }

        private void handleTransformationEnded(Exception exception) {
            try {
                Transformer.this.releaseResources(false);
            } catch (IllegalStateException e) {
                if (exception == null) {
                    exception = e;
                }
            }
            if (exception == null) {
                Transformer.this.listener.onTransformationCompleted(this.mediaItem);
            } else {
                Transformer.this.listener.onTransformationError(this.mediaItem, exception);
            }
        }
    }
}
