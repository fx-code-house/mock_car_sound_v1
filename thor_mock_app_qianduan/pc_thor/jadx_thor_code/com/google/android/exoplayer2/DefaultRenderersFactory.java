package com.google.android.exoplayer2;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import com.google.android.exoplayer2.audio.AudioCapabilities;
import com.google.android.exoplayer2.audio.AudioProcessor;
import com.google.android.exoplayer2.audio.AudioRendererEventListener;
import com.google.android.exoplayer2.audio.AudioSink;
import com.google.android.exoplayer2.audio.DefaultAudioSink;
import com.google.android.exoplayer2.audio.MediaCodecAudioRenderer;
import com.google.android.exoplayer2.mediacodec.MediaCodecSelector;
import com.google.android.exoplayer2.metadata.MetadataOutput;
import com.google.android.exoplayer2.metadata.MetadataRenderer;
import com.google.android.exoplayer2.text.TextOutput;
import com.google.android.exoplayer2.text.TextRenderer;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.video.MediaCodecVideoRenderer;
import com.google.android.exoplayer2.video.VideoRendererEventListener;
import com.google.android.exoplayer2.video.spherical.CameraMotionRenderer;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class DefaultRenderersFactory implements RenderersFactory {
    public static final long DEFAULT_ALLOWED_VIDEO_JOINING_TIME_MS = 5000;
    public static final int EXTENSION_RENDERER_MODE_OFF = 0;
    public static final int EXTENSION_RENDERER_MODE_ON = 1;
    public static final int EXTENSION_RENDERER_MODE_PREFER = 2;
    public static final int MAX_DROPPED_VIDEO_FRAME_COUNT_TO_NOTIFY = 50;
    private static final String TAG = "DefaultRenderersFactory";
    private long allowedVideoJoiningTimeMs;
    private final Context context;
    private boolean enableAsyncQueueing;
    private boolean enableAudioTrackPlaybackParams;
    private boolean enableDecoderFallback;
    private boolean enableFloatOutput;
    private boolean enableOffload;
    private boolean enableSynchronizeCodecInteractionsWithQueueing;
    private int extensionRendererMode;
    private boolean forceAsyncQueueingSynchronizationWorkaround;
    private MediaCodecSelector mediaCodecSelector;

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface ExtensionRendererMode {
    }

    protected void buildMiscellaneousRenderers(Context context, Handler eventHandler, int extensionRendererMode, ArrayList<Renderer> out) {
    }

    public DefaultRenderersFactory(Context context) {
        this.context = context;
        this.extensionRendererMode = 0;
        this.allowedVideoJoiningTimeMs = 5000L;
        this.mediaCodecSelector = MediaCodecSelector.DEFAULT;
    }

    @Deprecated
    public DefaultRenderersFactory(Context context, int extensionRendererMode) {
        this(context, extensionRendererMode, 5000L);
    }

    @Deprecated
    public DefaultRenderersFactory(Context context, int extensionRendererMode, long allowedVideoJoiningTimeMs) {
        this.context = context;
        this.extensionRendererMode = extensionRendererMode;
        this.allowedVideoJoiningTimeMs = allowedVideoJoiningTimeMs;
        this.mediaCodecSelector = MediaCodecSelector.DEFAULT;
    }

    public DefaultRenderersFactory setExtensionRendererMode(int extensionRendererMode) {
        this.extensionRendererMode = extensionRendererMode;
        return this;
    }

    public DefaultRenderersFactory experimentalSetAsynchronousBufferQueueingEnabled(boolean enabled) {
        this.enableAsyncQueueing = enabled;
        return this;
    }

    public DefaultRenderersFactory experimentalSetForceAsyncQueueingSynchronizationWorkaround(boolean enabled) {
        this.forceAsyncQueueingSynchronizationWorkaround = enabled;
        return this;
    }

    public DefaultRenderersFactory experimentalSetSynchronizeCodecInteractionsWithQueueingEnabled(boolean enabled) {
        this.enableSynchronizeCodecInteractionsWithQueueing = enabled;
        return this;
    }

    public DefaultRenderersFactory setEnableDecoderFallback(boolean enableDecoderFallback) {
        this.enableDecoderFallback = enableDecoderFallback;
        return this;
    }

    public DefaultRenderersFactory setMediaCodecSelector(MediaCodecSelector mediaCodecSelector) {
        this.mediaCodecSelector = mediaCodecSelector;
        return this;
    }

    public DefaultRenderersFactory setEnableAudioFloatOutput(boolean enableFloatOutput) {
        this.enableFloatOutput = enableFloatOutput;
        return this;
    }

    public DefaultRenderersFactory setEnableAudioOffload(boolean enableOffload) {
        this.enableOffload = enableOffload;
        return this;
    }

    public DefaultRenderersFactory setEnableAudioTrackPlaybackParams(boolean enableAudioTrackPlaybackParams) {
        this.enableAudioTrackPlaybackParams = enableAudioTrackPlaybackParams;
        return this;
    }

    public DefaultRenderersFactory setAllowedVideoJoiningTimeMs(long allowedVideoJoiningTimeMs) {
        this.allowedVideoJoiningTimeMs = allowedVideoJoiningTimeMs;
        return this;
    }

    @Override // com.google.android.exoplayer2.RenderersFactory
    public Renderer[] createRenderers(Handler eventHandler, VideoRendererEventListener videoRendererEventListener, AudioRendererEventListener audioRendererEventListener, TextOutput textRendererOutput, MetadataOutput metadataRendererOutput) {
        ArrayList<Renderer> arrayList = new ArrayList<>();
        buildVideoRenderers(this.context, this.extensionRendererMode, this.mediaCodecSelector, this.enableDecoderFallback, eventHandler, videoRendererEventListener, this.allowedVideoJoiningTimeMs, arrayList);
        AudioSink audioSinkBuildAudioSink = buildAudioSink(this.context, this.enableFloatOutput, this.enableAudioTrackPlaybackParams, this.enableOffload);
        if (audioSinkBuildAudioSink != null) {
            buildAudioRenderers(this.context, this.extensionRendererMode, this.mediaCodecSelector, this.enableDecoderFallback, audioSinkBuildAudioSink, eventHandler, audioRendererEventListener, arrayList);
        }
        buildTextRenderers(this.context, textRendererOutput, eventHandler.getLooper(), this.extensionRendererMode, arrayList);
        buildMetadataRenderers(this.context, metadataRendererOutput, eventHandler.getLooper(), this.extensionRendererMode, arrayList);
        buildCameraMotionRenderers(this.context, this.extensionRendererMode, arrayList);
        buildMiscellaneousRenderers(this.context, eventHandler, this.extensionRendererMode, arrayList);
        return (Renderer[]) arrayList.toArray(new Renderer[0]);
    }

    protected void buildVideoRenderers(Context context, int extensionRendererMode, MediaCodecSelector mediaCodecSelector, boolean enableDecoderFallback, Handler eventHandler, VideoRendererEventListener eventListener, long allowedVideoJoiningTimeMs, ArrayList<Renderer> out) {
        int i;
        MediaCodecVideoRenderer mediaCodecVideoRenderer = new MediaCodecVideoRenderer(context, mediaCodecSelector, allowedVideoJoiningTimeMs, enableDecoderFallback, eventHandler, eventListener, 50);
        mediaCodecVideoRenderer.experimentalSetAsynchronousBufferQueueingEnabled(this.enableAsyncQueueing);
        mediaCodecVideoRenderer.experimentalSetForceAsyncQueueingSynchronizationWorkaround(this.forceAsyncQueueingSynchronizationWorkaround);
        mediaCodecVideoRenderer.experimentalSetSynchronizeCodecInteractionsWithQueueingEnabled(this.enableSynchronizeCodecInteractionsWithQueueing);
        out.add(mediaCodecVideoRenderer);
        if (extensionRendererMode == 0) {
            return;
        }
        int size = out.size();
        if (extensionRendererMode == 2) {
            size--;
        }
        try {
            try {
                i = size + 1;
            } catch (Exception e) {
                throw new RuntimeException("Error instantiating VP9 extension", e);
            }
        } catch (ClassNotFoundException unused) {
        }
        try {
            out.add(size, (Renderer) Class.forName("com.google.android.exoplayer2.ext.vp9.LibvpxVideoRenderer").getConstructor(Long.TYPE, Handler.class, VideoRendererEventListener.class, Integer.TYPE).newInstance(Long.valueOf(allowedVideoJoiningTimeMs), eventHandler, eventListener, 50));
            Log.i(TAG, "Loaded LibvpxVideoRenderer.");
        } catch (ClassNotFoundException unused2) {
            size = i;
            i = size;
            out.add(i, (Renderer) Class.forName("com.google.android.exoplayer2.ext.av1.Libgav1VideoRenderer").getConstructor(Long.TYPE, Handler.class, VideoRendererEventListener.class, Integer.TYPE).newInstance(Long.valueOf(allowedVideoJoiningTimeMs), eventHandler, eventListener, 50));
            Log.i(TAG, "Loaded Libgav1VideoRenderer.");
        }
        try {
            out.add(i, (Renderer) Class.forName("com.google.android.exoplayer2.ext.av1.Libgav1VideoRenderer").getConstructor(Long.TYPE, Handler.class, VideoRendererEventListener.class, Integer.TYPE).newInstance(Long.valueOf(allowedVideoJoiningTimeMs), eventHandler, eventListener, 50));
            Log.i(TAG, "Loaded Libgav1VideoRenderer.");
        } catch (ClassNotFoundException unused3) {
        } catch (Exception e2) {
            throw new RuntimeException("Error instantiating AV1 extension", e2);
        }
    }

    protected void buildAudioRenderers(Context context, int extensionRendererMode, MediaCodecSelector mediaCodecSelector, boolean enableDecoderFallback, AudioSink audioSink, Handler eventHandler, AudioRendererEventListener eventListener, ArrayList<Renderer> out) {
        int i;
        MediaCodecAudioRenderer mediaCodecAudioRenderer = new MediaCodecAudioRenderer(context, mediaCodecSelector, enableDecoderFallback, eventHandler, eventListener, audioSink);
        mediaCodecAudioRenderer.experimentalSetAsynchronousBufferQueueingEnabled(this.enableAsyncQueueing);
        mediaCodecAudioRenderer.experimentalSetForceAsyncQueueingSynchronizationWorkaround(this.forceAsyncQueueingSynchronizationWorkaround);
        mediaCodecAudioRenderer.experimentalSetSynchronizeCodecInteractionsWithQueueingEnabled(this.enableSynchronizeCodecInteractionsWithQueueing);
        out.add(mediaCodecAudioRenderer);
        if (extensionRendererMode == 0) {
            return;
        }
        int size = out.size();
        if (extensionRendererMode == 2) {
            size--;
        }
        try {
            try {
                i = size + 1;
                try {
                    out.add(size, (Renderer) Class.forName("com.google.android.exoplayer2.ext.opus.LibopusAudioRenderer").getConstructor(Handler.class, AudioRendererEventListener.class, AudioSink.class).newInstance(eventHandler, eventListener, audioSink));
                    Log.i(TAG, "Loaded LibopusAudioRenderer.");
                } catch (ClassNotFoundException unused) {
                    size = i;
                    i = size;
                    try {
                        int i2 = i + 1;
                        try {
                            out.add(i, (Renderer) Class.forName("com.google.android.exoplayer2.ext.flac.LibflacAudioRenderer").getConstructor(Handler.class, AudioRendererEventListener.class, AudioSink.class).newInstance(eventHandler, eventListener, audioSink));
                            Log.i(TAG, "Loaded LibflacAudioRenderer.");
                        } catch (ClassNotFoundException unused2) {
                            i = i2;
                            i2 = i;
                            out.add(i2, (Renderer) Class.forName("com.google.android.exoplayer2.ext.ffmpeg.FfmpegAudioRenderer").getConstructor(Handler.class, AudioRendererEventListener.class, AudioSink.class).newInstance(eventHandler, eventListener, audioSink));
                            Log.i(TAG, "Loaded FfmpegAudioRenderer.");
                        }
                        out.add(i2, (Renderer) Class.forName("com.google.android.exoplayer2.ext.ffmpeg.FfmpegAudioRenderer").getConstructor(Handler.class, AudioRendererEventListener.class, AudioSink.class).newInstance(eventHandler, eventListener, audioSink));
                        Log.i(TAG, "Loaded FfmpegAudioRenderer.");
                    } catch (Exception e) {
                        throw new RuntimeException("Error instantiating FLAC extension", e);
                    }
                }
            } catch (ClassNotFoundException unused3) {
            }
            try {
                int i22 = i + 1;
                out.add(i, (Renderer) Class.forName("com.google.android.exoplayer2.ext.flac.LibflacAudioRenderer").getConstructor(Handler.class, AudioRendererEventListener.class, AudioSink.class).newInstance(eventHandler, eventListener, audioSink));
                Log.i(TAG, "Loaded LibflacAudioRenderer.");
            } catch (ClassNotFoundException unused4) {
            }
            try {
                out.add(i22, (Renderer) Class.forName("com.google.android.exoplayer2.ext.ffmpeg.FfmpegAudioRenderer").getConstructor(Handler.class, AudioRendererEventListener.class, AudioSink.class).newInstance(eventHandler, eventListener, audioSink));
                Log.i(TAG, "Loaded FfmpegAudioRenderer.");
            } catch (ClassNotFoundException unused5) {
            } catch (Exception e2) {
                throw new RuntimeException("Error instantiating FFmpeg extension", e2);
            }
        } catch (Exception e3) {
            throw new RuntimeException("Error instantiating Opus extension", e3);
        }
    }

    protected void buildTextRenderers(Context context, TextOutput output, Looper outputLooper, int extensionRendererMode, ArrayList<Renderer> out) {
        out.add(new TextRenderer(output, outputLooper));
    }

    protected void buildMetadataRenderers(Context context, MetadataOutput output, Looper outputLooper, int extensionRendererMode, ArrayList<Renderer> out) {
        out.add(new MetadataRenderer(output, outputLooper));
    }

    protected void buildCameraMotionRenderers(Context context, int extensionRendererMode, ArrayList<Renderer> out) {
        out.add(new CameraMotionRenderer());
    }

    protected AudioSink buildAudioSink(Context context, boolean z, boolean z2, boolean z3) {
        return new DefaultAudioSink(AudioCapabilities.getCapabilities(context), new DefaultAudioSink.DefaultAudioProcessorChain(new AudioProcessor[0]), z, z2, z3 ? 1 : 0);
    }
}
