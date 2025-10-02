package com.google.android.exoplayer2.ui;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.opengl.GLSurfaceView;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import com.google.android.exoplayer2.ControlDispatcher;
import com.google.android.exoplayer2.MediaMetadata;
import com.google.android.exoplayer2.PlaybackException;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.text.Cue;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.StyledPlayerControlView;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.ErrorMessageProvider;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.video.VideoSize;
import com.google.common.collect.ImmutableList;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.checkerframework.checker.nullness.qual.EnsuresNonNullIf;
import org.checkerframework.checker.nullness.qual.RequiresNonNull;

/* loaded from: classes.dex */
public class StyledPlayerView extends FrameLayout implements AdViewProvider {
    private static final int PICTURE_TYPE_FRONT_COVER = 3;
    private static final int PICTURE_TYPE_NOT_SET = -1;
    public static final int SHOW_BUFFERING_ALWAYS = 2;
    public static final int SHOW_BUFFERING_NEVER = 0;
    public static final int SHOW_BUFFERING_WHEN_PLAYING = 1;
    private static final int SURFACE_TYPE_NONE = 0;
    private static final int SURFACE_TYPE_SPHERICAL_GL_SURFACE_VIEW = 3;
    private static final int SURFACE_TYPE_SURFACE_VIEW = 1;
    private static final int SURFACE_TYPE_TEXTURE_VIEW = 2;
    private static final int SURFACE_TYPE_VIDEO_DECODER_GL_SURFACE_VIEW = 4;
    private final FrameLayout adOverlayFrameLayout;
    private final ImageView artworkView;
    private final View bufferingView;
    private final ComponentListener componentListener;
    private final AspectRatioFrameLayout contentFrame;
    private final StyledPlayerControlView controller;
    private boolean controllerAutoShow;
    private boolean controllerHideDuringAds;
    private boolean controllerHideOnTouch;
    private int controllerShowTimeoutMs;
    private StyledPlayerControlView.VisibilityListener controllerVisibilityListener;
    private CharSequence customErrorMessage;
    private Drawable defaultArtwork;
    private ErrorMessageProvider<? super PlaybackException> errorMessageProvider;
    private final TextView errorMessageView;
    private boolean isTouching;
    private boolean keepContentOnPlayerReset;
    private final FrameLayout overlayFrameLayout;
    private Player player;
    private int showBuffering;
    private final View shutterView;
    private final SubtitleView subtitleView;
    private final View surfaceView;
    private final boolean surfaceViewIgnoresVideoAspectRatio;
    private int textureViewRotation;
    private boolean useArtwork;
    private boolean useController;

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface ShowBuffering {
    }

    private boolean isDpadKey(int keyCode) {
        return keyCode == 19 || keyCode == 270 || keyCode == 22 || keyCode == 271 || keyCode == 20 || keyCode == 269 || keyCode == 21 || keyCode == 268 || keyCode == 23;
    }

    public StyledPlayerView(Context context) {
        this(context, null);
    }

    public StyledPlayerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public StyledPlayerView(Context context, AttributeSet attrs, int defStyleAttr) {
        int i;
        boolean z;
        int i2;
        int integer;
        boolean z2;
        boolean z3;
        int i3;
        boolean z4;
        int i4;
        boolean z5;
        int i5;
        boolean z6;
        boolean z7;
        boolean z8;
        int i6;
        boolean z9;
        super(context, attrs, defStyleAttr);
        ComponentListener componentListener = new ComponentListener();
        this.componentListener = componentListener;
        if (isInEditMode()) {
            this.contentFrame = null;
            this.shutterView = null;
            this.surfaceView = null;
            this.surfaceViewIgnoresVideoAspectRatio = false;
            this.artworkView = null;
            this.subtitleView = null;
            this.bufferingView = null;
            this.errorMessageView = null;
            this.controller = null;
            this.adOverlayFrameLayout = null;
            this.overlayFrameLayout = null;
            ImageView imageView = new ImageView(context);
            if (Util.SDK_INT >= 23) {
                configureEditModeLogoV23(getResources(), imageView);
            } else {
                configureEditModeLogo(getResources(), imageView);
            }
            addView(imageView);
            return;
        }
        int i7 = R.layout.exo_styled_player_view;
        if (attrs != null) {
            TypedArray typedArrayObtainStyledAttributes = context.getTheme().obtainStyledAttributes(attrs, R.styleable.StyledPlayerView, 0, 0);
            try {
                boolean zHasValue = typedArrayObtainStyledAttributes.hasValue(R.styleable.StyledPlayerView_shutter_background_color);
                int color = typedArrayObtainStyledAttributes.getColor(R.styleable.StyledPlayerView_shutter_background_color, 0);
                int resourceId = typedArrayObtainStyledAttributes.getResourceId(R.styleable.StyledPlayerView_player_layout_id, i7);
                boolean z10 = typedArrayObtainStyledAttributes.getBoolean(R.styleable.StyledPlayerView_use_artwork, true);
                int resourceId2 = typedArrayObtainStyledAttributes.getResourceId(R.styleable.StyledPlayerView_default_artwork, 0);
                boolean z11 = typedArrayObtainStyledAttributes.getBoolean(R.styleable.StyledPlayerView_use_controller, true);
                int i8 = typedArrayObtainStyledAttributes.getInt(R.styleable.StyledPlayerView_surface_type, 1);
                int i9 = typedArrayObtainStyledAttributes.getInt(R.styleable.StyledPlayerView_resize_mode, 0);
                int i10 = typedArrayObtainStyledAttributes.getInt(R.styleable.StyledPlayerView_show_timeout, 5000);
                boolean z12 = typedArrayObtainStyledAttributes.getBoolean(R.styleable.StyledPlayerView_hide_on_touch, true);
                boolean z13 = typedArrayObtainStyledAttributes.getBoolean(R.styleable.StyledPlayerView_auto_show, true);
                integer = typedArrayObtainStyledAttributes.getInteger(R.styleable.StyledPlayerView_show_buffering, 0);
                this.keepContentOnPlayerReset = typedArrayObtainStyledAttributes.getBoolean(R.styleable.StyledPlayerView_keep_content_on_player_reset, this.keepContentOnPlayerReset);
                boolean z14 = typedArrayObtainStyledAttributes.getBoolean(R.styleable.StyledPlayerView_hide_during_ads, true);
                typedArrayObtainStyledAttributes.recycle();
                z3 = z12;
                z = z13;
                i2 = i9;
                z6 = z11;
                i5 = resourceId2;
                z5 = z10;
                i4 = color;
                z4 = zHasValue;
                i3 = i8;
                z2 = z14;
                i7 = resourceId;
                i = i10;
            } catch (Throwable th) {
                typedArrayObtainStyledAttributes.recycle();
                throw th;
            }
        } else {
            i = 5000;
            z = true;
            i2 = 0;
            integer = 0;
            z2 = true;
            z3 = true;
            i3 = 1;
            z4 = false;
            i4 = 0;
            z5 = true;
            i5 = 0;
            z6 = true;
        }
        LayoutInflater.from(context).inflate(i7, this);
        setDescendantFocusability(262144);
        AspectRatioFrameLayout aspectRatioFrameLayout = (AspectRatioFrameLayout) findViewById(R.id.exo_content_frame);
        this.contentFrame = aspectRatioFrameLayout;
        if (aspectRatioFrameLayout != null) {
            setResizeModeRaw(aspectRatioFrameLayout, i2);
        }
        View viewFindViewById = findViewById(R.id.exo_shutter);
        this.shutterView = viewFindViewById;
        if (viewFindViewById != null && z4) {
            viewFindViewById.setBackgroundColor(i4);
        }
        if (aspectRatioFrameLayout != null && i3 != 0) {
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(-1, -1);
            if (i3 == 2) {
                z7 = true;
                this.surfaceView = new TextureView(context);
            } else if (i3 != 3) {
                if (i3 == 4) {
                    try {
                        this.surfaceView = (View) Class.forName("com.google.android.exoplayer2.video.VideoDecoderGLSurfaceView").getConstructor(Context.class).newInstance(context);
                    } catch (Exception e) {
                        throw new IllegalStateException("video_decoder_gl_surface_view requires an ExoPlayer dependency", e);
                    }
                } else {
                    this.surfaceView = new SurfaceView(context);
                }
                z7 = true;
            } else {
                try {
                    z7 = true;
                    this.surfaceView = (View) Class.forName("com.google.android.exoplayer2.video.spherical.SphericalGLSurfaceView").getConstructor(Context.class).newInstance(context);
                    z9 = true;
                    this.surfaceView.setLayoutParams(layoutParams);
                    this.surfaceView.setOnClickListener(componentListener);
                    this.surfaceView.setClickable(false);
                    aspectRatioFrameLayout.addView(this.surfaceView, 0);
                    z8 = z9;
                } catch (Exception e2) {
                    throw new IllegalStateException("spherical_gl_surface_view requires an ExoPlayer dependency", e2);
                }
            }
            z9 = false;
            this.surfaceView.setLayoutParams(layoutParams);
            this.surfaceView.setOnClickListener(componentListener);
            this.surfaceView.setClickable(false);
            aspectRatioFrameLayout.addView(this.surfaceView, 0);
            z8 = z9;
        } else {
            z7 = true;
            this.surfaceView = null;
            z8 = false;
        }
        this.surfaceViewIgnoresVideoAspectRatio = z8;
        this.adOverlayFrameLayout = (FrameLayout) findViewById(R.id.exo_ad_overlay);
        this.overlayFrameLayout = (FrameLayout) findViewById(R.id.exo_overlay);
        ImageView imageView2 = (ImageView) findViewById(R.id.exo_artwork);
        this.artworkView = imageView2;
        this.useArtwork = (!z5 || imageView2 == null) ? false : z7;
        if (i5 != 0) {
            this.defaultArtwork = ContextCompat.getDrawable(getContext(), i5);
        }
        SubtitleView subtitleView = (SubtitleView) findViewById(R.id.exo_subtitles);
        this.subtitleView = subtitleView;
        if (subtitleView != null) {
            subtitleView.setUserDefaultStyle();
            subtitleView.setUserDefaultTextSize();
        }
        View viewFindViewById2 = findViewById(R.id.exo_buffering);
        this.bufferingView = viewFindViewById2;
        if (viewFindViewById2 != null) {
            viewFindViewById2.setVisibility(8);
        }
        this.showBuffering = integer;
        TextView textView = (TextView) findViewById(R.id.exo_error_message);
        this.errorMessageView = textView;
        if (textView != null) {
            textView.setVisibility(8);
        }
        StyledPlayerControlView styledPlayerControlView = (StyledPlayerControlView) findViewById(R.id.exo_controller);
        View viewFindViewById3 = findViewById(R.id.exo_controller_placeholder);
        if (styledPlayerControlView != null) {
            this.controller = styledPlayerControlView;
            i6 = 0;
        } else if (viewFindViewById3 != null) {
            i6 = 0;
            StyledPlayerControlView styledPlayerControlView2 = new StyledPlayerControlView(context, null, 0, attrs);
            this.controller = styledPlayerControlView2;
            styledPlayerControlView2.setId(R.id.exo_controller);
            styledPlayerControlView2.setLayoutParams(viewFindViewById3.getLayoutParams());
            ViewGroup viewGroup = (ViewGroup) viewFindViewById3.getParent();
            int iIndexOfChild = viewGroup.indexOfChild(viewFindViewById3);
            viewGroup.removeView(viewFindViewById3);
            viewGroup.addView(styledPlayerControlView2, iIndexOfChild);
        } else {
            i6 = 0;
            this.controller = null;
        }
        StyledPlayerControlView styledPlayerControlView3 = this.controller;
        this.controllerShowTimeoutMs = styledPlayerControlView3 != null ? i : i6;
        this.controllerHideOnTouch = z3;
        this.controllerAutoShow = z;
        this.controllerHideDuringAds = z2;
        this.useController = (!z6 || styledPlayerControlView3 == null) ? i6 : z7;
        if (styledPlayerControlView3 != null) {
            styledPlayerControlView3.hideImmediately();
            this.controller.addVisibilityListener(componentListener);
        }
        updateContentDescription();
    }

    public static void switchTargetView(Player player, StyledPlayerView oldPlayerView, StyledPlayerView newPlayerView) {
        if (oldPlayerView == newPlayerView) {
            return;
        }
        if (newPlayerView != null) {
            newPlayerView.setPlayer(player);
        }
        if (oldPlayerView != null) {
            oldPlayerView.setPlayer(null);
        }
    }

    public Player getPlayer() {
        return this.player;
    }

    public void setPlayer(Player player) {
        Assertions.checkState(Looper.myLooper() == Looper.getMainLooper());
        Assertions.checkArgument(player == null || player.getApplicationLooper() == Looper.getMainLooper());
        Player player2 = this.player;
        if (player2 == player) {
            return;
        }
        if (player2 != null) {
            player2.removeListener((Player.Listener) this.componentListener);
            View view = this.surfaceView;
            if (view instanceof TextureView) {
                player2.clearVideoTextureView((TextureView) view);
            } else if (view instanceof SurfaceView) {
                player2.clearVideoSurfaceView((SurfaceView) view);
            }
        }
        SubtitleView subtitleView = this.subtitleView;
        if (subtitleView != null) {
            subtitleView.setCues(null);
        }
        this.player = player;
        if (useController()) {
            this.controller.setPlayer(player);
        }
        updateBuffering();
        updateErrorMessage();
        updateForCurrentTrackSelections(true);
        if (player != null) {
            if (player.isCommandAvailable(26)) {
                View view2 = this.surfaceView;
                if (view2 instanceof TextureView) {
                    player.setVideoTextureView((TextureView) view2);
                } else if (view2 instanceof SurfaceView) {
                    player.setVideoSurfaceView((SurfaceView) view2);
                }
                updateAspectRatio();
            }
            if (this.subtitleView != null && player.isCommandAvailable(27)) {
                this.subtitleView.setCues(player.getCurrentCues());
            }
            player.addListener((Player.Listener) this.componentListener);
            maybeShowController(false);
            return;
        }
        hideController();
    }

    @Override // android.view.View
    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
        View view = this.surfaceView;
        if (view instanceof SurfaceView) {
            view.setVisibility(visibility);
        }
    }

    public void setResizeMode(int resizeMode) {
        Assertions.checkStateNotNull(this.contentFrame);
        this.contentFrame.setResizeMode(resizeMode);
    }

    public int getResizeMode() {
        Assertions.checkStateNotNull(this.contentFrame);
        return this.contentFrame.getResizeMode();
    }

    public boolean getUseArtwork() {
        return this.useArtwork;
    }

    public void setUseArtwork(boolean useArtwork) {
        Assertions.checkState((useArtwork && this.artworkView == null) ? false : true);
        if (this.useArtwork != useArtwork) {
            this.useArtwork = useArtwork;
            updateForCurrentTrackSelections(false);
        }
    }

    public Drawable getDefaultArtwork() {
        return this.defaultArtwork;
    }

    public void setDefaultArtwork(Drawable defaultArtwork) {
        if (this.defaultArtwork != defaultArtwork) {
            this.defaultArtwork = defaultArtwork;
            updateForCurrentTrackSelections(false);
        }
    }

    public boolean getUseController() {
        return this.useController;
    }

    public void setUseController(boolean useController) {
        Assertions.checkState((useController && this.controller == null) ? false : true);
        if (this.useController == useController) {
            return;
        }
        this.useController = useController;
        if (useController()) {
            this.controller.setPlayer(this.player);
        } else {
            StyledPlayerControlView styledPlayerControlView = this.controller;
            if (styledPlayerControlView != null) {
                styledPlayerControlView.hide();
                this.controller.setPlayer(null);
            }
        }
        updateContentDescription();
    }

    public void setShutterBackgroundColor(int color) {
        View view = this.shutterView;
        if (view != null) {
            view.setBackgroundColor(color);
        }
    }

    public void setKeepContentOnPlayerReset(boolean keepContentOnPlayerReset) {
        if (this.keepContentOnPlayerReset != keepContentOnPlayerReset) {
            this.keepContentOnPlayerReset = keepContentOnPlayerReset;
            updateForCurrentTrackSelections(false);
        }
    }

    public void setShowBuffering(int showBuffering) {
        if (this.showBuffering != showBuffering) {
            this.showBuffering = showBuffering;
            updateBuffering();
        }
    }

    public void setErrorMessageProvider(ErrorMessageProvider<? super PlaybackException> errorMessageProvider) {
        if (this.errorMessageProvider != errorMessageProvider) {
            this.errorMessageProvider = errorMessageProvider;
            updateErrorMessage();
        }
    }

    public void setCustomErrorMessage(CharSequence message) {
        Assertions.checkState(this.errorMessageView != null);
        this.customErrorMessage = message;
        updateErrorMessage();
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchKeyEvent(KeyEvent event) {
        Player player = this.player;
        if (player != null && player.isPlayingAd()) {
            return super.dispatchKeyEvent(event);
        }
        boolean zIsDpadKey = isDpadKey(event.getKeyCode());
        if (zIsDpadKey && useController() && !this.controller.isFullyVisible()) {
            maybeShowController(true);
            return true;
        }
        if (dispatchMediaKeyEvent(event) || super.dispatchKeyEvent(event)) {
            maybeShowController(true);
            return true;
        }
        if (zIsDpadKey && useController()) {
            maybeShowController(true);
        }
        return false;
    }

    public boolean dispatchMediaKeyEvent(KeyEvent event) {
        return useController() && this.controller.dispatchMediaKeyEvent(event);
    }

    public boolean isControllerFullyVisible() {
        StyledPlayerControlView styledPlayerControlView = this.controller;
        return styledPlayerControlView != null && styledPlayerControlView.isFullyVisible();
    }

    public void showController() {
        showController(shouldShowControllerIndefinitely());
    }

    public void hideController() {
        StyledPlayerControlView styledPlayerControlView = this.controller;
        if (styledPlayerControlView != null) {
            styledPlayerControlView.hide();
        }
    }

    public int getControllerShowTimeoutMs() {
        return this.controllerShowTimeoutMs;
    }

    public void setControllerShowTimeoutMs(int controllerShowTimeoutMs) {
        Assertions.checkStateNotNull(this.controller);
        this.controllerShowTimeoutMs = controllerShowTimeoutMs;
        if (this.controller.isFullyVisible()) {
            showController();
        }
    }

    public boolean getControllerHideOnTouch() {
        return this.controllerHideOnTouch;
    }

    public void setControllerHideOnTouch(boolean controllerHideOnTouch) {
        Assertions.checkStateNotNull(this.controller);
        this.controllerHideOnTouch = controllerHideOnTouch;
        updateContentDescription();
    }

    public boolean getControllerAutoShow() {
        return this.controllerAutoShow;
    }

    public void setControllerAutoShow(boolean controllerAutoShow) {
        this.controllerAutoShow = controllerAutoShow;
    }

    public void setControllerHideDuringAds(boolean controllerHideDuringAds) {
        this.controllerHideDuringAds = controllerHideDuringAds;
    }

    public void setControllerVisibilityListener(StyledPlayerControlView.VisibilityListener listener) {
        Assertions.checkStateNotNull(this.controller);
        StyledPlayerControlView.VisibilityListener visibilityListener = this.controllerVisibilityListener;
        if (visibilityListener == listener) {
            return;
        }
        if (visibilityListener != null) {
            this.controller.removeVisibilityListener(visibilityListener);
        }
        this.controllerVisibilityListener = listener;
        if (listener != null) {
            this.controller.addVisibilityListener(listener);
        }
    }

    public void setControllerOnFullScreenModeChangedListener(StyledPlayerControlView.OnFullScreenModeChangedListener listener) {
        Assertions.checkStateNotNull(this.controller);
        this.controller.setOnFullScreenModeChangedListener(listener);
    }

    @Deprecated
    public void setControlDispatcher(ControlDispatcher controlDispatcher) {
        Assertions.checkStateNotNull(this.controller);
        this.controller.setControlDispatcher(controlDispatcher);
    }

    public void setShowRewindButton(boolean showRewindButton) {
        Assertions.checkStateNotNull(this.controller);
        this.controller.setShowRewindButton(showRewindButton);
    }

    public void setShowFastForwardButton(boolean showFastForwardButton) {
        Assertions.checkStateNotNull(this.controller);
        this.controller.setShowFastForwardButton(showFastForwardButton);
    }

    public void setShowPreviousButton(boolean showPreviousButton) {
        Assertions.checkStateNotNull(this.controller);
        this.controller.setShowPreviousButton(showPreviousButton);
    }

    public void setShowNextButton(boolean showNextButton) {
        Assertions.checkStateNotNull(this.controller);
        this.controller.setShowNextButton(showNextButton);
    }

    public void setRepeatToggleModes(int repeatToggleModes) {
        Assertions.checkStateNotNull(this.controller);
        this.controller.setRepeatToggleModes(repeatToggleModes);
    }

    public void setShowShuffleButton(boolean showShuffleButton) {
        Assertions.checkStateNotNull(this.controller);
        this.controller.setShowShuffleButton(showShuffleButton);
    }

    public void setShowSubtitleButton(boolean showSubtitleButton) {
        Assertions.checkStateNotNull(this.controller);
        this.controller.setShowSubtitleButton(showSubtitleButton);
    }

    public void setShowVrButton(boolean showVrButton) {
        Assertions.checkStateNotNull(this.controller);
        this.controller.setShowVrButton(showVrButton);
    }

    public void setShowMultiWindowTimeBar(boolean showMultiWindowTimeBar) {
        Assertions.checkStateNotNull(this.controller);
        this.controller.setShowMultiWindowTimeBar(showMultiWindowTimeBar);
    }

    public void setExtraAdGroupMarkers(long[] extraAdGroupTimesMs, boolean[] extraPlayedAdGroups) {
        Assertions.checkStateNotNull(this.controller);
        this.controller.setExtraAdGroupMarkers(extraAdGroupTimesMs, extraPlayedAdGroups);
    }

    public void setAspectRatioListener(AspectRatioFrameLayout.AspectRatioListener listener) {
        Assertions.checkStateNotNull(this.contentFrame);
        this.contentFrame.setAspectRatioListener(listener);
    }

    public View getVideoSurfaceView() {
        return this.surfaceView;
    }

    public FrameLayout getOverlayFrameLayout() {
        return this.overlayFrameLayout;
    }

    public SubtitleView getSubtitleView() {
        return this.subtitleView;
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent event) {
        if (!useController() || this.player == null) {
            return false;
        }
        int action = event.getAction();
        if (action == 0) {
            this.isTouching = true;
            return true;
        }
        if (action != 1 || !this.isTouching) {
            return false;
        }
        this.isTouching = false;
        return performClick();
    }

    @Override // android.view.View
    public boolean performClick() {
        super.performClick();
        return toggleControllerVisibility();
    }

    @Override // android.view.View
    public boolean onTrackballEvent(MotionEvent ev) {
        if (!useController() || this.player == null) {
            return false;
        }
        maybeShowController(true);
        return true;
    }

    public void onResume() {
        View view = this.surfaceView;
        if (view instanceof GLSurfaceView) {
            ((GLSurfaceView) view).onResume();
        }
    }

    public void onPause() {
        View view = this.surfaceView;
        if (view instanceof GLSurfaceView) {
            ((GLSurfaceView) view).onPause();
        }
    }

    protected void onContentAspectRatioChanged(AspectRatioFrameLayout contentFrame, float aspectRatio) {
        if (contentFrame != null) {
            contentFrame.setAspectRatio(aspectRatio);
        }
    }

    @Override // com.google.android.exoplayer2.ui.AdViewProvider
    public ViewGroup getAdViewGroup() {
        return (ViewGroup) Assertions.checkStateNotNull(this.adOverlayFrameLayout, "exo_ad_overlay must be present for ad playback");
    }

    @Override // com.google.android.exoplayer2.ui.AdViewProvider
    public List<AdOverlayInfo> getAdOverlayInfos() {
        ArrayList arrayList = new ArrayList();
        if (this.overlayFrameLayout != null) {
            arrayList.add(new AdOverlayInfo(this.overlayFrameLayout, 3, "Transparent overlay does not impact viewability"));
        }
        if (this.controller != null) {
            arrayList.add(new AdOverlayInfo(this.controller, 0));
        }
        return ImmutableList.copyOf((Collection) arrayList);
    }

    @EnsuresNonNullIf(expression = {"controller"}, result = true)
    private boolean useController() {
        if (!this.useController) {
            return false;
        }
        Assertions.checkStateNotNull(this.controller);
        return true;
    }

    @EnsuresNonNullIf(expression = {"artworkView"}, result = true)
    private boolean useArtwork() {
        if (!this.useArtwork) {
            return false;
        }
        Assertions.checkStateNotNull(this.artworkView);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean toggleControllerVisibility() {
        if (useController() && this.player != null) {
            if (!this.controller.isFullyVisible()) {
                maybeShowController(true);
                return true;
            }
            if (this.controllerHideOnTouch) {
                this.controller.hide();
                return true;
            }
        }
        return false;
    }

    private void maybeShowController(boolean isForced) {
        if (!(isPlayingAd() && this.controllerHideDuringAds) && useController()) {
            boolean z = this.controller.isFullyVisible() && this.controller.getShowTimeoutMs() <= 0;
            boolean zShouldShowControllerIndefinitely = shouldShowControllerIndefinitely();
            if (isForced || z || zShouldShowControllerIndefinitely) {
                showController(zShouldShowControllerIndefinitely);
            }
        }
    }

    private boolean shouldShowControllerIndefinitely() {
        Player player = this.player;
        if (player == null) {
            return true;
        }
        int playbackState = player.getPlaybackState();
        return this.controllerAutoShow && !this.player.getCurrentTimeline().isEmpty() && (playbackState == 1 || playbackState == 4 || !((Player) Assertions.checkNotNull(this.player)).getPlayWhenReady());
    }

    private void showController(boolean showIndefinitely) {
        if (useController()) {
            this.controller.setShowTimeoutMs(showIndefinitely ? 0 : this.controllerShowTimeoutMs);
            this.controller.show();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isPlayingAd() {
        Player player = this.player;
        return player != null && player.isPlayingAd() && this.player.getPlayWhenReady();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateForCurrentTrackSelections(boolean isNewPlayer) {
        Player player = this.player;
        if (player == null || player.getCurrentTrackGroups().isEmpty()) {
            if (this.keepContentOnPlayerReset) {
                return;
            }
            hideArtwork();
            closeShutter();
            return;
        }
        if (isNewPlayer && !this.keepContentOnPlayerReset) {
            closeShutter();
        }
        TrackSelectionArray currentTrackSelections = player.getCurrentTrackSelections();
        for (int i = 0; i < currentTrackSelections.length; i++) {
            TrackSelection trackSelection = currentTrackSelections.get(i);
            if (trackSelection != null) {
                for (int i2 = 0; i2 < trackSelection.length(); i2++) {
                    if (MimeTypes.getTrackType(trackSelection.getFormat(i2).sampleMimeType) == 2) {
                        hideArtwork();
                        return;
                    }
                }
            }
        }
        closeShutter();
        if (useArtwork() && (setArtworkFromMediaMetadata(player.getMediaMetadata()) || setDrawableArtwork(this.defaultArtwork))) {
            return;
        }
        hideArtwork();
    }

    @RequiresNonNull({"artworkView"})
    private boolean setArtworkFromMediaMetadata(MediaMetadata mediaMetadata) {
        if (mediaMetadata.artworkData == null) {
            return false;
        }
        return setDrawableArtwork(new BitmapDrawable(getResources(), BitmapFactory.decodeByteArray(mediaMetadata.artworkData, 0, mediaMetadata.artworkData.length)));
    }

    @RequiresNonNull({"artworkView"})
    private boolean setDrawableArtwork(Drawable drawable) {
        if (drawable != null) {
            int intrinsicWidth = drawable.getIntrinsicWidth();
            int intrinsicHeight = drawable.getIntrinsicHeight();
            if (intrinsicWidth > 0 && intrinsicHeight > 0) {
                onContentAspectRatioChanged(this.contentFrame, intrinsicWidth / intrinsicHeight);
                this.artworkView.setImageDrawable(drawable);
                this.artworkView.setVisibility(0);
                return true;
            }
        }
        return false;
    }

    private void hideArtwork() {
        ImageView imageView = this.artworkView;
        if (imageView != null) {
            imageView.setImageResource(android.R.color.transparent);
            this.artworkView.setVisibility(4);
        }
    }

    private void closeShutter() {
        View view = this.shutterView;
        if (view != null) {
            view.setVisibility(0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0020  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void updateBuffering() {
        /*
            r4 = this;
            android.view.View r0 = r4.bufferingView
            if (r0 == 0) goto L2b
            com.google.android.exoplayer2.Player r0 = r4.player
            r1 = 0
            if (r0 == 0) goto L20
            int r0 = r0.getPlaybackState()
            r2 = 2
            if (r0 != r2) goto L20
            int r0 = r4.showBuffering
            r3 = 1
            if (r0 == r2) goto L21
            if (r0 != r3) goto L20
            com.google.android.exoplayer2.Player r0 = r4.player
            boolean r0 = r0.getPlayWhenReady()
            if (r0 == 0) goto L20
            goto L21
        L20:
            r3 = r1
        L21:
            android.view.View r0 = r4.bufferingView
            if (r3 == 0) goto L26
            goto L28
        L26:
            r1 = 8
        L28:
            r0.setVisibility(r1)
        L2b:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.ui.StyledPlayerView.updateBuffering():void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateErrorMessage() {
        ErrorMessageProvider<? super PlaybackException> errorMessageProvider;
        TextView textView = this.errorMessageView;
        if (textView != null) {
            CharSequence charSequence = this.customErrorMessage;
            if (charSequence != null) {
                textView.setText(charSequence);
                this.errorMessageView.setVisibility(0);
                return;
            }
            Player player = this.player;
            PlaybackException playerError = player != null ? player.getPlayerError() : null;
            if (playerError != null && (errorMessageProvider = this.errorMessageProvider) != null) {
                this.errorMessageView.setText((CharSequence) errorMessageProvider.getErrorMessage(playerError).second);
                this.errorMessageView.setVisibility(0);
            } else {
                this.errorMessageView.setVisibility(8);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateContentDescription() {
        StyledPlayerControlView styledPlayerControlView = this.controller;
        if (styledPlayerControlView == null || !this.useController) {
            setContentDescription(null);
        } else if (styledPlayerControlView.isFullyVisible()) {
            setContentDescription(this.controllerHideOnTouch ? getResources().getString(R.string.exo_controls_hide) : null);
        } else {
            setContentDescription(getResources().getString(R.string.exo_controls_show));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateControllerVisibility() {
        if (isPlayingAd() && this.controllerHideDuringAds) {
            hideController();
        } else {
            maybeShowController(false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateAspectRatio() {
        Player player = this.player;
        VideoSize videoSize = player != null ? player.getVideoSize() : VideoSize.UNKNOWN;
        int i = videoSize.width;
        int i2 = videoSize.height;
        int i3 = videoSize.unappliedRotationDegrees;
        float f = (i2 == 0 || i == 0) ? 0.0f : (i * videoSize.pixelWidthHeightRatio) / i2;
        View view = this.surfaceView;
        if (view instanceof TextureView) {
            if (f > 0.0f && (i3 == 90 || i3 == 270)) {
                f = 1.0f / f;
            }
            if (this.textureViewRotation != 0) {
                view.removeOnLayoutChangeListener(this.componentListener);
            }
            this.textureViewRotation = i3;
            if (i3 != 0) {
                this.surfaceView.addOnLayoutChangeListener(this.componentListener);
            }
            applyTextureViewRotation((TextureView) this.surfaceView, this.textureViewRotation);
        }
        onContentAspectRatioChanged(this.contentFrame, this.surfaceViewIgnoresVideoAspectRatio ? 0.0f : f);
    }

    private static void configureEditModeLogoV23(Resources resources, ImageView logo) {
        logo.setImageDrawable(resources.getDrawable(R.drawable.exo_edit_mode_logo, null));
        logo.setBackgroundColor(resources.getColor(R.color.exo_edit_mode_background_color, null));
    }

    private static void configureEditModeLogo(Resources resources, ImageView logo) {
        logo.setImageDrawable(resources.getDrawable(R.drawable.exo_edit_mode_logo));
        logo.setBackgroundColor(resources.getColor(R.color.exo_edit_mode_background_color));
    }

    private static void setResizeModeRaw(AspectRatioFrameLayout aspectRatioFrame, int resizeMode) {
        aspectRatioFrame.setResizeMode(resizeMode);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void applyTextureViewRotation(TextureView textureView, int textureViewRotation) {
        Matrix matrix = new Matrix();
        float width = textureView.getWidth();
        float height = textureView.getHeight();
        if (width != 0.0f && height != 0.0f && textureViewRotation != 0) {
            float f = width / 2.0f;
            float f2 = height / 2.0f;
            matrix.postRotate(textureViewRotation, f, f2);
            RectF rectF = new RectF(0.0f, 0.0f, width, height);
            RectF rectF2 = new RectF();
            matrix.mapRect(rectF2, rectF);
            matrix.postScale(width / rectF2.width(), height / rectF2.height(), f, f2);
        }
        textureView.setTransform(matrix);
    }

    private final class ComponentListener implements Player.Listener, View.OnLayoutChangeListener, View.OnClickListener, StyledPlayerControlView.VisibilityListener {
        private Object lastPeriodUidWithTracks;
        private final Timeline.Period period = new Timeline.Period();

        public ComponentListener() {
        }

        @Override // com.google.android.exoplayer2.Player.Listener, com.google.android.exoplayer2.text.TextOutput
        public void onCues(List<Cue> cues) {
            if (StyledPlayerView.this.subtitleView != null) {
                StyledPlayerView.this.subtitleView.onCues(cues);
            }
        }

        @Override // com.google.android.exoplayer2.Player.Listener, com.google.android.exoplayer2.video.VideoListener
        public void onVideoSizeChanged(VideoSize videoSize) {
            StyledPlayerView.this.updateAspectRatio();
        }

        @Override // com.google.android.exoplayer2.Player.Listener, com.google.android.exoplayer2.video.VideoListener
        public void onRenderedFirstFrame() {
            if (StyledPlayerView.this.shutterView != null) {
                StyledPlayerView.this.shutterView.setVisibility(4);
            }
        }

        @Override // com.google.android.exoplayer2.Player.Listener, com.google.android.exoplayer2.Player.EventListener
        public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray selections) {
            Player player = (Player) Assertions.checkNotNull(StyledPlayerView.this.player);
            Timeline currentTimeline = player.getCurrentTimeline();
            if (currentTimeline.isEmpty()) {
                this.lastPeriodUidWithTracks = null;
            } else if (!player.getCurrentTrackGroups().isEmpty()) {
                this.lastPeriodUidWithTracks = currentTimeline.getPeriod(player.getCurrentPeriodIndex(), this.period, true).uid;
            } else {
                Object obj = this.lastPeriodUidWithTracks;
                if (obj != null) {
                    int indexOfPeriod = currentTimeline.getIndexOfPeriod(obj);
                    if (indexOfPeriod != -1) {
                        if (player.getCurrentWindowIndex() == currentTimeline.getPeriod(indexOfPeriod, this.period).windowIndex) {
                            return;
                        }
                    }
                    this.lastPeriodUidWithTracks = null;
                }
            }
            StyledPlayerView.this.updateForCurrentTrackSelections(false);
        }

        @Override // com.google.android.exoplayer2.Player.Listener, com.google.android.exoplayer2.Player.EventListener
        public void onPlaybackStateChanged(int playbackState) {
            StyledPlayerView.this.updateBuffering();
            StyledPlayerView.this.updateErrorMessage();
            StyledPlayerView.this.updateControllerVisibility();
        }

        @Override // com.google.android.exoplayer2.Player.Listener, com.google.android.exoplayer2.Player.EventListener
        public void onPlayWhenReadyChanged(boolean playWhenReady, int reason) {
            StyledPlayerView.this.updateBuffering();
            StyledPlayerView.this.updateControllerVisibility();
        }

        @Override // com.google.android.exoplayer2.Player.Listener, com.google.android.exoplayer2.Player.EventListener
        public void onPositionDiscontinuity(Player.PositionInfo oldPosition, Player.PositionInfo newPosition, int reason) {
            if (StyledPlayerView.this.isPlayingAd() && StyledPlayerView.this.controllerHideDuringAds) {
                StyledPlayerView.this.hideController();
            }
        }

        @Override // android.view.View.OnLayoutChangeListener
        public void onLayoutChange(View view, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
            StyledPlayerView.applyTextureViewRotation((TextureView) view, StyledPlayerView.this.textureViewRotation);
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            StyledPlayerView.this.toggleControllerVisibility();
        }

        @Override // com.google.android.exoplayer2.ui.StyledPlayerControlView.VisibilityListener
        public void onVisibilityChange(int visibility) {
            StyledPlayerView.this.updateContentDescription();
        }
    }
}
