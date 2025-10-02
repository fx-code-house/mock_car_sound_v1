package com.google.android.exoplayer2.ui;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ControlDispatcher;
import com.google.android.exoplayer2.DefaultControlDispatcher;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerLibraryInfo;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.TrackGroup;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.MappingTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.TimeBar;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.RepeatModeUtil;
import com.google.android.exoplayer2.util.Util;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Formatter;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CopyOnWriteArrayList;

/* loaded from: classes.dex */
public class StyledPlayerControlView extends FrameLayout {
    public static final int DEFAULT_REPEAT_TOGGLE_MODES = 0;
    public static final int DEFAULT_SHOW_TIMEOUT_MS = 5000;
    public static final int DEFAULT_TIME_BAR_MIN_UPDATE_INTERVAL_MS = 200;
    private static final int MAX_UPDATE_INTERVAL_MS = 1000;
    public static final int MAX_WINDOWS_FOR_MULTI_WINDOW_TIME_BAR = 100;
    private static final int SETTINGS_AUDIO_TRACK_SELECTION_POSITION = 1;
    private static final int SETTINGS_PLAYBACK_SPEED_POSITION = 0;
    private long[] adGroupTimesMs;
    private View audioTrackButton;
    private TrackSelectionAdapter audioTrackSelectionAdapter;
    private final float buttonAlphaDisabled;
    private final float buttonAlphaEnabled;
    private final ComponentListener componentListener;
    private ControlDispatcher controlDispatcher;
    private StyledPlayerControlViewLayoutManager controlViewLayoutManager;
    private long currentWindowOffset;
    private final TextView durationView;
    private long[] extraAdGroupTimesMs;
    private boolean[] extraPlayedAdGroups;
    private final View fastForwardButton;
    private final TextView fastForwardButtonTextView;
    private final StringBuilder formatBuilder;
    private final Formatter formatter;
    private ImageView fullScreenButton;
    private final String fullScreenEnterContentDescription;
    private final Drawable fullScreenEnterDrawable;
    private final String fullScreenExitContentDescription;
    private final Drawable fullScreenExitDrawable;
    private boolean isAttachedToWindow;
    private boolean isFullScreen;
    private ImageView minimalFullScreenButton;
    private boolean multiWindowTimeBar;
    private boolean needToHideBars;
    private final View nextButton;
    private OnFullScreenModeChangedListener onFullScreenModeChangedListener;
    private final Timeline.Period period;
    private final View playPauseButton;
    private PlaybackSpeedAdapter playbackSpeedAdapter;
    private View playbackSpeedButton;
    private boolean[] playedAdGroups;
    private Player player;
    private final TextView positionView;
    private final View previousButton;
    private ProgressUpdateListener progressUpdateListener;
    private final String repeatAllButtonContentDescription;
    private final Drawable repeatAllButtonDrawable;
    private final String repeatOffButtonContentDescription;
    private final Drawable repeatOffButtonDrawable;
    private final String repeatOneButtonContentDescription;
    private final Drawable repeatOneButtonDrawable;
    private final ImageView repeatToggleButton;
    private int repeatToggleModes;
    private Resources resources;
    private final View rewindButton;
    private final TextView rewindButtonTextView;
    private boolean scrubbing;
    private SettingsAdapter settingsAdapter;
    private View settingsButton;
    private RecyclerView settingsView;
    private PopupWindow settingsWindow;
    private int settingsWindowMargin;
    private boolean showMultiWindowTimeBar;
    private int showTimeoutMs;
    private final ImageView shuffleButton;
    private final Drawable shuffleOffButtonDrawable;
    private final String shuffleOffContentDescription;
    private final Drawable shuffleOnButtonDrawable;
    private final String shuffleOnContentDescription;
    private ImageView subtitleButton;
    private final Drawable subtitleOffButtonDrawable;
    private final String subtitleOffContentDescription;
    private final Drawable subtitleOnButtonDrawable;
    private final String subtitleOnContentDescription;
    private TrackSelectionAdapter textTrackSelectionAdapter;
    private final TimeBar timeBar;
    private int timeBarMinUpdateIntervalMs;
    private TrackNameProvider trackNameProvider;
    private DefaultTrackSelector trackSelector;
    private final Runnable updateProgressAction;
    private final CopyOnWriteArrayList<VisibilityListener> visibilityListeners;
    private final View vrButton;
    private final Timeline.Window window;

    public interface OnFullScreenModeChangedListener {
        void onFullScreenModeChanged(boolean isFullScreen);
    }

    public interface ProgressUpdateListener {
        void onProgressUpdate(long position, long bufferedPosition);
    }

    public interface VisibilityListener {
        void onVisibilityChange(int visibility);
    }

    private static boolean isHandledMediaKey(int keyCode) {
        return keyCode == 90 || keyCode == 89 || keyCode == 85 || keyCode == 79 || keyCode == 126 || keyCode == 127 || keyCode == 87 || keyCode == 88;
    }

    static {
        ExoPlayerLibraryInfo.registerModule("goog.exo.ui");
    }

    public StyledPlayerControlView(Context context) {
        this(context, null);
    }

    public StyledPlayerControlView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StyledPlayerControlView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, attrs);
    }

    public StyledPlayerControlView(Context context, AttributeSet attrs, int defStyleAttr, AttributeSet playbackAttrs) throws Resources.NotFoundException {
        boolean z;
        boolean z2;
        boolean z3;
        boolean z4;
        boolean z5;
        boolean z6;
        boolean z7;
        boolean z8;
        ComponentListener componentListener;
        boolean z9;
        boolean z10;
        TextView textView;
        boolean z11;
        ImageView imageView;
        boolean z12;
        super(context, attrs, defStyleAttr);
        int resourceId = R.layout.exo_styled_player_control_view;
        this.showTimeoutMs = 5000;
        this.repeatToggleModes = 0;
        this.timeBarMinUpdateIntervalMs = 200;
        if (playbackAttrs != null) {
            TypedArray typedArrayObtainStyledAttributes = context.getTheme().obtainStyledAttributes(playbackAttrs, R.styleable.StyledPlayerControlView, 0, 0);
            try {
                resourceId = typedArrayObtainStyledAttributes.getResourceId(R.styleable.StyledPlayerControlView_controller_layout_id, resourceId);
                this.showTimeoutMs = typedArrayObtainStyledAttributes.getInt(R.styleable.StyledPlayerControlView_show_timeout, this.showTimeoutMs);
                this.repeatToggleModes = getRepeatToggleModes(typedArrayObtainStyledAttributes, this.repeatToggleModes);
                boolean z13 = typedArrayObtainStyledAttributes.getBoolean(R.styleable.StyledPlayerControlView_show_rewind_button, true);
                boolean z14 = typedArrayObtainStyledAttributes.getBoolean(R.styleable.StyledPlayerControlView_show_fastforward_button, true);
                boolean z15 = typedArrayObtainStyledAttributes.getBoolean(R.styleable.StyledPlayerControlView_show_previous_button, true);
                boolean z16 = typedArrayObtainStyledAttributes.getBoolean(R.styleable.StyledPlayerControlView_show_next_button, true);
                boolean z17 = typedArrayObtainStyledAttributes.getBoolean(R.styleable.StyledPlayerControlView_show_shuffle_button, false);
                boolean z18 = typedArrayObtainStyledAttributes.getBoolean(R.styleable.StyledPlayerControlView_show_subtitle_button, false);
                boolean z19 = typedArrayObtainStyledAttributes.getBoolean(R.styleable.StyledPlayerControlView_show_vr_button, false);
                setTimeBarMinUpdateInterval(typedArrayObtainStyledAttributes.getInt(R.styleable.StyledPlayerControlView_time_bar_min_update_interval, this.timeBarMinUpdateIntervalMs));
                boolean z20 = typedArrayObtainStyledAttributes.getBoolean(R.styleable.StyledPlayerControlView_animation_enabled, true);
                typedArrayObtainStyledAttributes.recycle();
                z2 = z17;
                z3 = z18;
                z5 = z13;
                z6 = z14;
                z7 = z15;
                z4 = z20;
                z8 = z16;
                z = z19;
            } catch (Throwable th) {
                typedArrayObtainStyledAttributes.recycle();
                throw th;
            }
        } else {
            z = false;
            z2 = false;
            z3 = false;
            z4 = true;
            z5 = true;
            z6 = true;
            z7 = true;
            z8 = true;
        }
        LayoutInflater.from(context).inflate(resourceId, this);
        setDescendantFocusability(262144);
        ComponentListener componentListener2 = new ComponentListener();
        this.componentListener = componentListener2;
        this.visibilityListeners = new CopyOnWriteArrayList<>();
        this.period = new Timeline.Period();
        this.window = new Timeline.Window();
        StringBuilder sb = new StringBuilder();
        this.formatBuilder = sb;
        this.formatter = new Formatter(sb, Locale.getDefault());
        this.adGroupTimesMs = new long[0];
        this.playedAdGroups = new boolean[0];
        this.extraAdGroupTimesMs = new long[0];
        this.extraPlayedAdGroups = new boolean[0];
        this.controlDispatcher = new DefaultControlDispatcher();
        this.updateProgressAction = new Runnable() { // from class: com.google.android.exoplayer2.ui.StyledPlayerControlView$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.updateProgress();
            }
        };
        this.durationView = (TextView) findViewById(R.id.exo_duration);
        this.positionView = (TextView) findViewById(R.id.exo_position);
        ImageView imageView2 = (ImageView) findViewById(R.id.exo_subtitle);
        this.subtitleButton = imageView2;
        if (imageView2 != null) {
            imageView2.setOnClickListener(componentListener2);
        }
        ImageView imageView3 = (ImageView) findViewById(R.id.exo_fullscreen);
        this.fullScreenButton = imageView3;
        initializeFullScreenButton(imageView3, new View.OnClickListener() { // from class: com.google.android.exoplayer2.ui.StyledPlayerControlView$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.onFullScreenButtonClicked(view);
            }
        });
        ImageView imageView4 = (ImageView) findViewById(R.id.exo_minimal_fullscreen);
        this.minimalFullScreenButton = imageView4;
        initializeFullScreenButton(imageView4, new View.OnClickListener() { // from class: com.google.android.exoplayer2.ui.StyledPlayerControlView$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.onFullScreenButtonClicked(view);
            }
        });
        View viewFindViewById = findViewById(R.id.exo_settings);
        this.settingsButton = viewFindViewById;
        if (viewFindViewById != null) {
            viewFindViewById.setOnClickListener(componentListener2);
        }
        View viewFindViewById2 = findViewById(R.id.exo_playback_speed);
        this.playbackSpeedButton = viewFindViewById2;
        if (viewFindViewById2 != null) {
            viewFindViewById2.setOnClickListener(componentListener2);
        }
        View viewFindViewById3 = findViewById(R.id.exo_audio_track);
        this.audioTrackButton = viewFindViewById3;
        if (viewFindViewById3 != null) {
            viewFindViewById3.setOnClickListener(componentListener2);
        }
        TimeBar timeBar = (TimeBar) findViewById(R.id.exo_progress);
        View viewFindViewById4 = findViewById(R.id.exo_progress_placeholder);
        if (timeBar != null) {
            this.timeBar = timeBar;
            componentListener = componentListener2;
            z9 = z4;
            z10 = z;
            textView = null;
        } else if (viewFindViewById4 != null) {
            textView = null;
            componentListener = componentListener2;
            z9 = z4;
            z10 = z;
            DefaultTimeBar defaultTimeBar = new DefaultTimeBar(context, null, 0, playbackAttrs, R.style.ExoStyledControls_TimeBar);
            defaultTimeBar.setId(R.id.exo_progress);
            defaultTimeBar.setLayoutParams(viewFindViewById4.getLayoutParams());
            ViewGroup viewGroup = (ViewGroup) viewFindViewById4.getParent();
            int iIndexOfChild = viewGroup.indexOfChild(viewFindViewById4);
            viewGroup.removeView(viewFindViewById4);
            viewGroup.addView(defaultTimeBar, iIndexOfChild);
            this.timeBar = defaultTimeBar;
        } else {
            componentListener = componentListener2;
            z9 = z4;
            z10 = z;
            textView = null;
            this.timeBar = null;
        }
        TimeBar timeBar2 = this.timeBar;
        ComponentListener componentListener3 = componentListener;
        if (timeBar2 != null) {
            timeBar2.addListener(componentListener3);
        }
        View viewFindViewById5 = findViewById(R.id.exo_play_pause);
        this.playPauseButton = viewFindViewById5;
        if (viewFindViewById5 != null) {
            viewFindViewById5.setOnClickListener(componentListener3);
        }
        View viewFindViewById6 = findViewById(R.id.exo_prev);
        this.previousButton = viewFindViewById6;
        if (viewFindViewById6 != null) {
            viewFindViewById6.setOnClickListener(componentListener3);
        }
        View viewFindViewById7 = findViewById(R.id.exo_next);
        this.nextButton = viewFindViewById7;
        if (viewFindViewById7 != null) {
            viewFindViewById7.setOnClickListener(componentListener3);
        }
        Typeface font = ResourcesCompat.getFont(context, R.font.roboto_medium_numbers);
        View viewFindViewById8 = findViewById(R.id.exo_rew);
        TextView textView2 = viewFindViewById8 == null ? (TextView) findViewById(R.id.exo_rew_with_amount) : textView;
        this.rewindButtonTextView = textView2;
        if (textView2 != null) {
            textView2.setTypeface(font);
        }
        viewFindViewById8 = viewFindViewById8 == null ? textView2 : viewFindViewById8;
        this.rewindButton = viewFindViewById8;
        if (viewFindViewById8 != null) {
            viewFindViewById8.setOnClickListener(componentListener3);
        }
        View viewFindViewById9 = findViewById(R.id.exo_ffwd);
        TextView textView3 = viewFindViewById9 == null ? (TextView) findViewById(R.id.exo_ffwd_with_amount) : null;
        this.fastForwardButtonTextView = textView3;
        if (textView3 != null) {
            textView3.setTypeface(font);
        }
        viewFindViewById9 = viewFindViewById9 == null ? textView3 : viewFindViewById9;
        this.fastForwardButton = viewFindViewById9;
        if (viewFindViewById9 != null) {
            viewFindViewById9.setOnClickListener(componentListener3);
        }
        ImageView imageView5 = (ImageView) findViewById(R.id.exo_repeat_toggle);
        this.repeatToggleButton = imageView5;
        if (imageView5 != null) {
            imageView5.setOnClickListener(componentListener3);
        }
        ImageView imageView6 = (ImageView) findViewById(R.id.exo_shuffle);
        this.shuffleButton = imageView6;
        if (imageView6 != null) {
            imageView6.setOnClickListener(componentListener3);
        }
        this.resources = context.getResources();
        this.buttonAlphaEnabled = r6.getInteger(R.integer.exo_media_button_opacity_percentage_enabled) / 100.0f;
        this.buttonAlphaDisabled = this.resources.getInteger(R.integer.exo_media_button_opacity_percentage_disabled) / 100.0f;
        View viewFindViewById10 = findViewById(R.id.exo_vr);
        this.vrButton = viewFindViewById10;
        if (viewFindViewById10 != null) {
            updateButton(false, viewFindViewById10);
        }
        StyledPlayerControlViewLayoutManager styledPlayerControlViewLayoutManager = new StyledPlayerControlViewLayoutManager(this);
        this.controlViewLayoutManager = styledPlayerControlViewLayoutManager;
        boolean z21 = z10;
        styledPlayerControlViewLayoutManager.setAnimationEnabled(z9);
        boolean z22 = z3;
        this.settingsAdapter = new SettingsAdapter(new String[]{this.resources.getString(R.string.exo_controls_playback_speed), this.resources.getString(R.string.exo_track_selection_title_audio)}, new Drawable[]{this.resources.getDrawable(R.drawable.exo_styled_controls_speed), this.resources.getDrawable(R.drawable.exo_styled_controls_audiotrack)});
        this.settingsWindowMargin = this.resources.getDimensionPixelSize(R.dimen.exo_settings_offset);
        RecyclerView recyclerView = (RecyclerView) LayoutInflater.from(context).inflate(R.layout.exo_styled_settings_list, (ViewGroup) null);
        this.settingsView = recyclerView;
        recyclerView.setAdapter(this.settingsAdapter);
        this.settingsView.setLayoutManager(new LinearLayoutManager(getContext()));
        this.settingsWindow = new PopupWindow((View) this.settingsView, -2, -2, true);
        if (Util.SDK_INT < 23) {
            z11 = false;
            this.settingsWindow.setBackgroundDrawable(new ColorDrawable(0));
        } else {
            z11 = false;
        }
        this.settingsWindow.setOnDismissListener(componentListener3);
        this.needToHideBars = true;
        this.trackNameProvider = new DefaultTrackNameProvider(getResources());
        this.subtitleOnButtonDrawable = this.resources.getDrawable(R.drawable.exo_styled_controls_subtitle_on);
        this.subtitleOffButtonDrawable = this.resources.getDrawable(R.drawable.exo_styled_controls_subtitle_off);
        this.subtitleOnContentDescription = this.resources.getString(R.string.exo_controls_cc_enabled_description);
        this.subtitleOffContentDescription = this.resources.getString(R.string.exo_controls_cc_disabled_description);
        this.textTrackSelectionAdapter = new TextTrackSelectionAdapter();
        this.audioTrackSelectionAdapter = new AudioTrackSelectionAdapter();
        this.playbackSpeedAdapter = new PlaybackSpeedAdapter(this.resources.getStringArray(R.array.exo_playback_speeds), this.resources.getIntArray(R.array.exo_speed_multiplied_by_100));
        this.fullScreenExitDrawable = this.resources.getDrawable(R.drawable.exo_styled_controls_fullscreen_exit);
        this.fullScreenEnterDrawable = this.resources.getDrawable(R.drawable.exo_styled_controls_fullscreen_enter);
        this.repeatOffButtonDrawable = this.resources.getDrawable(R.drawable.exo_styled_controls_repeat_off);
        this.repeatOneButtonDrawable = this.resources.getDrawable(R.drawable.exo_styled_controls_repeat_one);
        this.repeatAllButtonDrawable = this.resources.getDrawable(R.drawable.exo_styled_controls_repeat_all);
        this.shuffleOnButtonDrawable = this.resources.getDrawable(R.drawable.exo_styled_controls_shuffle_on);
        this.shuffleOffButtonDrawable = this.resources.getDrawable(R.drawable.exo_styled_controls_shuffle_off);
        this.fullScreenExitContentDescription = this.resources.getString(R.string.exo_controls_fullscreen_exit_description);
        this.fullScreenEnterContentDescription = this.resources.getString(R.string.exo_controls_fullscreen_enter_description);
        this.repeatOffButtonContentDescription = this.resources.getString(R.string.exo_controls_repeat_off_description);
        this.repeatOneButtonContentDescription = this.resources.getString(R.string.exo_controls_repeat_one_description);
        this.repeatAllButtonContentDescription = this.resources.getString(R.string.exo_controls_repeat_all_description);
        this.shuffleOnContentDescription = this.resources.getString(R.string.exo_controls_shuffle_on_description);
        this.shuffleOffContentDescription = this.resources.getString(R.string.exo_controls_shuffle_off_description);
        this.controlViewLayoutManager.setShowButton((ViewGroup) findViewById(R.id.exo_bottom_bar), true);
        this.controlViewLayoutManager.setShowButton(viewFindViewById9, z6);
        this.controlViewLayoutManager.setShowButton(viewFindViewById8, z5);
        this.controlViewLayoutManager.setShowButton(viewFindViewById6, z7);
        this.controlViewLayoutManager.setShowButton(viewFindViewById7, z8);
        this.controlViewLayoutManager.setShowButton(imageView6, z2);
        this.controlViewLayoutManager.setShowButton(this.subtitleButton, z22);
        this.controlViewLayoutManager.setShowButton(viewFindViewById10, z21);
        StyledPlayerControlViewLayoutManager styledPlayerControlViewLayoutManager2 = this.controlViewLayoutManager;
        if (this.repeatToggleModes != 0) {
            z12 = true;
            imageView = imageView5;
        } else {
            imageView = imageView5;
            z12 = z11;
        }
        styledPlayerControlViewLayoutManager2.setShowButton(imageView, z12);
        addOnLayoutChangeListener(new View.OnLayoutChangeListener() { // from class: com.google.android.exoplayer2.ui.StyledPlayerControlView$$ExternalSyntheticLambda2
            @Override // android.view.View.OnLayoutChangeListener
            public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                this.f$0.onLayoutChange(view, i, i2, i3, i4, i5, i6, i7, i8);
            }
        });
    }

    public Player getPlayer() {
        return this.player;
    }

    public void setPlayer(Player player) {
        boolean z = true;
        Assertions.checkState(Looper.myLooper() == Looper.getMainLooper());
        if (player != null && player.getApplicationLooper() != Looper.getMainLooper()) {
            z = false;
        }
        Assertions.checkArgument(z);
        Player player2 = this.player;
        if (player2 == player) {
            return;
        }
        if (player2 != null) {
            player2.removeListener((Player.Listener) this.componentListener);
        }
        this.player = player;
        if (player != null) {
            player.addListener((Player.Listener) this.componentListener);
        }
        if (player instanceof ExoPlayer) {
            TrackSelector trackSelector = ((ExoPlayer) player).getTrackSelector();
            if (trackSelector instanceof DefaultTrackSelector) {
                this.trackSelector = (DefaultTrackSelector) trackSelector;
            }
        } else {
            this.trackSelector = null;
        }
        updateAll();
    }

    public void setShowMultiWindowTimeBar(boolean showMultiWindowTimeBar) {
        this.showMultiWindowTimeBar = showMultiWindowTimeBar;
        updateTimeline();
    }

    public void setExtraAdGroupMarkers(long[] extraAdGroupTimesMs, boolean[] extraPlayedAdGroups) {
        if (extraAdGroupTimesMs == null) {
            this.extraAdGroupTimesMs = new long[0];
            this.extraPlayedAdGroups = new boolean[0];
        } else {
            boolean[] zArr = (boolean[]) Assertions.checkNotNull(extraPlayedAdGroups);
            Assertions.checkArgument(extraAdGroupTimesMs.length == zArr.length);
            this.extraAdGroupTimesMs = extraAdGroupTimesMs;
            this.extraPlayedAdGroups = zArr;
        }
        updateTimeline();
    }

    public void addVisibilityListener(VisibilityListener listener) {
        Assertions.checkNotNull(listener);
        this.visibilityListeners.add(listener);
    }

    public void removeVisibilityListener(VisibilityListener listener) {
        this.visibilityListeners.remove(listener);
    }

    public void setProgressUpdateListener(ProgressUpdateListener listener) {
        this.progressUpdateListener = listener;
    }

    @Deprecated
    public void setControlDispatcher(ControlDispatcher controlDispatcher) {
        if (this.controlDispatcher != controlDispatcher) {
            this.controlDispatcher = controlDispatcher;
            updateNavigation();
        }
    }

    public void setShowRewindButton(boolean showRewindButton) {
        this.controlViewLayoutManager.setShowButton(this.rewindButton, showRewindButton);
        updateNavigation();
    }

    public void setShowFastForwardButton(boolean showFastForwardButton) {
        this.controlViewLayoutManager.setShowButton(this.fastForwardButton, showFastForwardButton);
        updateNavigation();
    }

    public void setShowPreviousButton(boolean showPreviousButton) {
        this.controlViewLayoutManager.setShowButton(this.previousButton, showPreviousButton);
        updateNavigation();
    }

    public void setShowNextButton(boolean showNextButton) {
        this.controlViewLayoutManager.setShowButton(this.nextButton, showNextButton);
        updateNavigation();
    }

    public int getShowTimeoutMs() {
        return this.showTimeoutMs;
    }

    public void setShowTimeoutMs(int showTimeoutMs) {
        this.showTimeoutMs = showTimeoutMs;
        if (isFullyVisible()) {
            this.controlViewLayoutManager.resetHideCallbacks();
        }
    }

    public int getRepeatToggleModes() {
        return this.repeatToggleModes;
    }

    public void setRepeatToggleModes(int repeatToggleModes) {
        this.repeatToggleModes = repeatToggleModes;
        Player player = this.player;
        if (player != null) {
            int repeatMode = player.getRepeatMode();
            if (repeatToggleModes == 0 && repeatMode != 0) {
                this.controlDispatcher.dispatchSetRepeatMode(this.player, 0);
            } else if (repeatToggleModes == 1 && repeatMode == 2) {
                this.controlDispatcher.dispatchSetRepeatMode(this.player, 1);
            } else if (repeatToggleModes == 2 && repeatMode == 1) {
                this.controlDispatcher.dispatchSetRepeatMode(this.player, 2);
            }
        }
        this.controlViewLayoutManager.setShowButton(this.repeatToggleButton, repeatToggleModes != 0);
        updateRepeatModeButton();
    }

    public boolean getShowShuffleButton() {
        return this.controlViewLayoutManager.getShowButton(this.shuffleButton);
    }

    public void setShowShuffleButton(boolean showShuffleButton) {
        this.controlViewLayoutManager.setShowButton(this.shuffleButton, showShuffleButton);
        updateShuffleButton();
    }

    public boolean getShowSubtitleButton() {
        return this.controlViewLayoutManager.getShowButton(this.subtitleButton);
    }

    public void setShowSubtitleButton(boolean showSubtitleButton) {
        this.controlViewLayoutManager.setShowButton(this.subtitleButton, showSubtitleButton);
    }

    public boolean getShowVrButton() {
        return this.controlViewLayoutManager.getShowButton(this.vrButton);
    }

    public void setShowVrButton(boolean showVrButton) {
        this.controlViewLayoutManager.setShowButton(this.vrButton, showVrButton);
    }

    public void setVrButtonListener(View.OnClickListener onClickListener) {
        View view = this.vrButton;
        if (view != null) {
            view.setOnClickListener(onClickListener);
            updateButton(onClickListener != null, this.vrButton);
        }
    }

    public void setAnimationEnabled(boolean animationEnabled) {
        this.controlViewLayoutManager.setAnimationEnabled(animationEnabled);
    }

    public boolean isAnimationEnabled() {
        return this.controlViewLayoutManager.isAnimationEnabled();
    }

    public void setTimeBarMinUpdateInterval(int minUpdateIntervalMs) {
        this.timeBarMinUpdateIntervalMs = Util.constrainValue(minUpdateIntervalMs, 16, 1000);
    }

    public void setOnFullScreenModeChangedListener(OnFullScreenModeChangedListener listener) {
        this.onFullScreenModeChangedListener = listener;
        updateFullScreenButtonVisibility(this.fullScreenButton, listener != null);
        updateFullScreenButtonVisibility(this.minimalFullScreenButton, listener != null);
    }

    public void show() {
        this.controlViewLayoutManager.show();
    }

    public void hide() {
        this.controlViewLayoutManager.hide();
    }

    public void hideImmediately() {
        this.controlViewLayoutManager.hideImmediately();
    }

    public boolean isFullyVisible() {
        return this.controlViewLayoutManager.isFullyVisible();
    }

    public boolean isVisible() {
        return getVisibility() == 0;
    }

    void notifyOnVisibilityChange() {
        Iterator<VisibilityListener> it = this.visibilityListeners.iterator();
        while (it.hasNext()) {
            it.next().onVisibilityChange(getVisibility());
        }
    }

    void updateAll() {
        updatePlayPauseButton();
        updateNavigation();
        updateRepeatModeButton();
        updateShuffleButton();
        updateTrackLists();
        updatePlaybackSpeedList();
        updateTimeline();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updatePlayPauseButton() {
        if (isVisible() && this.isAttachedToWindow && this.playPauseButton != null) {
            if (shouldShowPauseButton()) {
                ((ImageView) this.playPauseButton).setImageDrawable(this.resources.getDrawable(R.drawable.exo_styled_controls_pause));
                this.playPauseButton.setContentDescription(this.resources.getString(R.string.exo_controls_pause_description));
            } else {
                ((ImageView) this.playPauseButton).setImageDrawable(this.resources.getDrawable(R.drawable.exo_styled_controls_play));
                this.playPauseButton.setContentDescription(this.resources.getString(R.string.exo_controls_play_description));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateNavigation() {
        boolean z;
        boolean zIsCommandAvailable;
        boolean zIsCommandAvailable2;
        boolean z2;
        if (isVisible() && this.isAttachedToWindow) {
            Player player = this.player;
            boolean z3 = false;
            if (player != null) {
                boolean zIsCommandAvailable3 = player.isCommandAvailable(4);
                zIsCommandAvailable2 = player.isCommandAvailable(6);
                boolean z4 = player.isCommandAvailable(10) && this.controlDispatcher.isRewindEnabled();
                if (player.isCommandAvailable(11) && this.controlDispatcher.isFastForwardEnabled()) {
                    z3 = true;
                }
                zIsCommandAvailable = player.isCommandAvailable(8);
                z = z3;
                z3 = z4;
                z2 = zIsCommandAvailable3;
            } else {
                z = false;
                zIsCommandAvailable = false;
                zIsCommandAvailable2 = false;
                z2 = false;
            }
            if (z3) {
                updateRewindButton();
            }
            if (z) {
                updateFastForwardButton();
            }
            updateButton(zIsCommandAvailable2, this.previousButton);
            updateButton(z3, this.rewindButton);
            updateButton(z, this.fastForwardButton);
            updateButton(zIsCommandAvailable, this.nextButton);
            TimeBar timeBar = this.timeBar;
            if (timeBar != null) {
                timeBar.setEnabled(z2);
            }
        }
    }

    private void updateRewindButton() {
        Player player;
        ControlDispatcher controlDispatcher = this.controlDispatcher;
        int rewindIncrementMs = (int) (((!(controlDispatcher instanceof DefaultControlDispatcher) || (player = this.player) == null) ? 5000L : ((DefaultControlDispatcher) controlDispatcher).getRewindIncrementMs(player)) / 1000);
        TextView textView = this.rewindButtonTextView;
        if (textView != null) {
            textView.setText(String.valueOf(rewindIncrementMs));
        }
        View view = this.rewindButton;
        if (view != null) {
            view.setContentDescription(this.resources.getQuantityString(R.plurals.exo_controls_rewind_by_amount_description, rewindIncrementMs, Integer.valueOf(rewindIncrementMs)));
        }
    }

    private void updateFastForwardButton() {
        Player player;
        ControlDispatcher controlDispatcher = this.controlDispatcher;
        int fastForwardIncrementMs = (int) (((!(controlDispatcher instanceof DefaultControlDispatcher) || (player = this.player) == null) ? C.DEFAULT_SEEK_FORWARD_INCREMENT_MS : ((DefaultControlDispatcher) controlDispatcher).getFastForwardIncrementMs(player)) / 1000);
        TextView textView = this.fastForwardButtonTextView;
        if (textView != null) {
            textView.setText(String.valueOf(fastForwardIncrementMs));
        }
        View view = this.fastForwardButton;
        if (view != null) {
            view.setContentDescription(this.resources.getQuantityString(R.plurals.exo_controls_fastforward_by_amount_description, fastForwardIncrementMs, Integer.valueOf(fastForwardIncrementMs)));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateRepeatModeButton() {
        ImageView imageView;
        if (isVisible() && this.isAttachedToWindow && (imageView = this.repeatToggleButton) != null) {
            if (this.repeatToggleModes == 0) {
                updateButton(false, imageView);
                return;
            }
            Player player = this.player;
            if (player == null) {
                updateButton(false, imageView);
                this.repeatToggleButton.setImageDrawable(this.repeatOffButtonDrawable);
                this.repeatToggleButton.setContentDescription(this.repeatOffButtonContentDescription);
                return;
            }
            updateButton(true, imageView);
            int repeatMode = player.getRepeatMode();
            if (repeatMode == 0) {
                this.repeatToggleButton.setImageDrawable(this.repeatOffButtonDrawable);
                this.repeatToggleButton.setContentDescription(this.repeatOffButtonContentDescription);
            } else if (repeatMode == 1) {
                this.repeatToggleButton.setImageDrawable(this.repeatOneButtonDrawable);
                this.repeatToggleButton.setContentDescription(this.repeatOneButtonContentDescription);
            } else {
                if (repeatMode != 2) {
                    return;
                }
                this.repeatToggleButton.setImageDrawable(this.repeatAllButtonDrawable);
                this.repeatToggleButton.setContentDescription(this.repeatAllButtonContentDescription);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateShuffleButton() {
        ImageView imageView;
        String str;
        if (isVisible() && this.isAttachedToWindow && (imageView = this.shuffleButton) != null) {
            Player player = this.player;
            if (!this.controlViewLayoutManager.getShowButton(imageView)) {
                updateButton(false, this.shuffleButton);
                return;
            }
            if (player == null) {
                updateButton(false, this.shuffleButton);
                this.shuffleButton.setImageDrawable(this.shuffleOffButtonDrawable);
                this.shuffleButton.setContentDescription(this.shuffleOffContentDescription);
                return;
            }
            updateButton(true, this.shuffleButton);
            this.shuffleButton.setImageDrawable(player.getShuffleModeEnabled() ? this.shuffleOnButtonDrawable : this.shuffleOffButtonDrawable);
            ImageView imageView2 = this.shuffleButton;
            if (player.getShuffleModeEnabled()) {
                str = this.shuffleOnContentDescription;
            } else {
                str = this.shuffleOffContentDescription;
            }
            imageView2.setContentDescription(str);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateTrackLists() {
        initTrackSelectionAdapter();
        updateButton(this.textTrackSelectionAdapter.getItemCount() > 0, this.subtitleButton);
    }

    private void initTrackSelectionAdapter() {
        DefaultTrackSelector defaultTrackSelector;
        MappingTrackSelector.MappedTrackInfo currentMappedTrackInfo;
        this.textTrackSelectionAdapter.clear();
        this.audioTrackSelectionAdapter.clear();
        if (this.player == null || (defaultTrackSelector = this.trackSelector) == null || (currentMappedTrackInfo = defaultTrackSelector.getCurrentMappedTrackInfo()) == null) {
            return;
        }
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        ArrayList arrayList4 = new ArrayList();
        for (int i = 0; i < currentMappedTrackInfo.getRendererCount(); i++) {
            if (currentMappedTrackInfo.getRendererType(i) == 3 && this.controlViewLayoutManager.getShowButton(this.subtitleButton)) {
                gatherTrackInfosForAdapter(currentMappedTrackInfo, i, arrayList);
                arrayList3.add(Integer.valueOf(i));
            } else if (currentMappedTrackInfo.getRendererType(i) == 1) {
                gatherTrackInfosForAdapter(currentMappedTrackInfo, i, arrayList2);
                arrayList4.add(Integer.valueOf(i));
            }
        }
        this.textTrackSelectionAdapter.init(arrayList3, arrayList, currentMappedTrackInfo);
        this.audioTrackSelectionAdapter.init(arrayList4, arrayList2, currentMappedTrackInfo);
    }

    private void gatherTrackInfosForAdapter(MappingTrackSelector.MappedTrackInfo mappedTrackInfo, int rendererIndex, List<TrackInfo> tracks) {
        TrackGroupArray trackGroups = mappedTrackInfo.getTrackGroups(rendererIndex);
        TrackSelection trackSelection = ((Player) Assertions.checkNotNull(this.player)).getCurrentTrackSelections().get(rendererIndex);
        for (int i = 0; i < trackGroups.length; i++) {
            TrackGroup trackGroup = trackGroups.get(i);
            for (int i2 = 0; i2 < trackGroup.length; i2++) {
                Format format = trackGroup.getFormat(i2);
                if (mappedTrackInfo.getTrackSupport(rendererIndex, i, i2) == 4) {
                    tracks.add(new TrackInfo(rendererIndex, i, i2, this.trackNameProvider.getTrackName(format), (trackSelection == null || trackSelection.indexOf(format) == -1) ? false : true));
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:40:0x00ab  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x00dc A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void updateTimeline() {
        /*
            Method dump skipped, instructions count: 326
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.ui.StyledPlayerControlView.updateTimeline():void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateProgress() {
        long contentPosition;
        long contentBufferedPosition;
        if (isVisible() && this.isAttachedToWindow) {
            Player player = this.player;
            if (player != null) {
                contentPosition = this.currentWindowOffset + player.getContentPosition();
                contentBufferedPosition = this.currentWindowOffset + player.getContentBufferedPosition();
            } else {
                contentPosition = 0;
                contentBufferedPosition = 0;
            }
            TextView textView = this.positionView;
            if (textView != null && !this.scrubbing) {
                textView.setText(Util.getStringForTime(this.formatBuilder, this.formatter, contentPosition));
            }
            TimeBar timeBar = this.timeBar;
            if (timeBar != null) {
                timeBar.setPosition(contentPosition);
                this.timeBar.setBufferedPosition(contentBufferedPosition);
            }
            ProgressUpdateListener progressUpdateListener = this.progressUpdateListener;
            if (progressUpdateListener != null) {
                progressUpdateListener.onProgressUpdate(contentPosition, contentBufferedPosition);
            }
            removeCallbacks(this.updateProgressAction);
            int playbackState = player == null ? 1 : player.getPlaybackState();
            if (player == null || !player.isPlaying()) {
                if (playbackState == 4 || playbackState == 1) {
                    return;
                }
                postDelayed(this.updateProgressAction, 1000L);
                return;
            }
            TimeBar timeBar2 = this.timeBar;
            long jMin = Math.min(timeBar2 != null ? timeBar2.getPreferredUpdateDelay() : 1000L, 1000 - (contentPosition % 1000));
            float f = player.getPlaybackParameters().speed;
            postDelayed(this.updateProgressAction, Util.constrainValue(f > 0.0f ? (long) (jMin / f) : 1000L, this.timeBarMinUpdateIntervalMs, 1000L));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updatePlaybackSpeedList() {
        Player player = this.player;
        if (player == null) {
            return;
        }
        this.playbackSpeedAdapter.updateSelectedIndex(player.getPlaybackParameters().speed);
        this.settingsAdapter.setSubTextAtPosition(0, this.playbackSpeedAdapter.getSelectedText());
    }

    private void updateSettingsWindowSize() {
        this.settingsView.measure(0, 0);
        this.settingsWindow.setWidth(Math.min(this.settingsView.getMeasuredWidth(), getWidth() - (this.settingsWindowMargin * 2)));
        this.settingsWindow.setHeight(Math.min(getHeight() - (this.settingsWindowMargin * 2), this.settingsView.getMeasuredHeight()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void displaySettingsWindow(RecyclerView.Adapter<?> adapter) {
        this.settingsView.setAdapter(adapter);
        updateSettingsWindowSize();
        this.needToHideBars = false;
        this.settingsWindow.dismiss();
        this.needToHideBars = true;
        this.settingsWindow.showAsDropDown(this, (getWidth() - this.settingsWindow.getWidth()) - this.settingsWindowMargin, (-this.settingsWindow.getHeight()) - this.settingsWindowMargin);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setPlaybackSpeed(float speed) {
        Player player = this.player;
        if (player == null) {
            return;
        }
        this.controlDispatcher.dispatchSetPlaybackParameters(player, player.getPlaybackParameters().withSpeed(speed));
    }

    void requestPlayPauseFocus() {
        View view = this.playPauseButton;
        if (view != null) {
            view.requestFocus();
        }
    }

    private void updateButton(boolean enabled, View view) {
        if (view == null) {
            return;
        }
        view.setEnabled(enabled);
        view.setAlpha(enabled ? this.buttonAlphaEnabled : this.buttonAlphaDisabled);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void seekToTimeBarPosition(Player player, long positionMs) {
        int currentWindowIndex;
        Timeline currentTimeline = player.getCurrentTimeline();
        if (this.multiWindowTimeBar && !currentTimeline.isEmpty()) {
            int windowCount = currentTimeline.getWindowCount();
            currentWindowIndex = 0;
            while (true) {
                long durationMs = currentTimeline.getWindow(currentWindowIndex, this.window).getDurationMs();
                if (positionMs < durationMs) {
                    break;
                }
                if (currentWindowIndex == windowCount - 1) {
                    positionMs = durationMs;
                    break;
                } else {
                    positionMs -= durationMs;
                    currentWindowIndex++;
                }
            }
        } else {
            currentWindowIndex = player.getCurrentWindowIndex();
        }
        seekTo(player, currentWindowIndex, positionMs);
        updateProgress();
    }

    private boolean seekTo(Player player, int windowIndex, long positionMs) {
        return this.controlDispatcher.dispatchSeekTo(player, windowIndex, positionMs);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onFullScreenButtonClicked(View v) {
        if (this.onFullScreenModeChangedListener == null) {
            return;
        }
        boolean z = !this.isFullScreen;
        this.isFullScreen = z;
        updateFullScreenButtonForState(this.fullScreenButton, z);
        updateFullScreenButtonForState(this.minimalFullScreenButton, this.isFullScreen);
        OnFullScreenModeChangedListener onFullScreenModeChangedListener = this.onFullScreenModeChangedListener;
        if (onFullScreenModeChangedListener != null) {
            onFullScreenModeChangedListener.onFullScreenModeChanged(this.isFullScreen);
        }
    }

    private void updateFullScreenButtonForState(ImageView fullScreenButton, boolean isFullScreen) {
        if (fullScreenButton == null) {
            return;
        }
        if (isFullScreen) {
            fullScreenButton.setImageDrawable(this.fullScreenExitDrawable);
            fullScreenButton.setContentDescription(this.fullScreenExitContentDescription);
        } else {
            fullScreenButton.setImageDrawable(this.fullScreenEnterDrawable);
            fullScreenButton.setContentDescription(this.fullScreenEnterContentDescription);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onSettingViewClicked(int position) {
        if (position == 0) {
            displaySettingsWindow(this.playbackSpeedAdapter);
        } else if (position == 1) {
            displaySettingsWindow(this.audioTrackSelectionAdapter);
        } else {
            this.settingsWindow.dismiss();
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.controlViewLayoutManager.onAttachedToWindow();
        this.isAttachedToWindow = true;
        if (isFullyVisible()) {
            this.controlViewLayoutManager.resetHideCallbacks();
        }
        updateAll();
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.controlViewLayoutManager.onDetachedFromWindow();
        this.isAttachedToWindow = false;
        removeCallbacks(this.updateProgressAction);
        this.controlViewLayoutManager.removeHideCallbacks();
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchKeyEvent(KeyEvent event) {
        return dispatchMediaKeyEvent(event) || super.dispatchKeyEvent(event);
    }

    public boolean dispatchMediaKeyEvent(KeyEvent event) {
        int keyCode = event.getKeyCode();
        Player player = this.player;
        if (player == null || !isHandledMediaKey(keyCode)) {
            return false;
        }
        if (event.getAction() != 0) {
            return true;
        }
        if (keyCode == 90) {
            if (player.getPlaybackState() == 4) {
                return true;
            }
            this.controlDispatcher.dispatchFastForward(player);
            return true;
        }
        if (keyCode == 89) {
            this.controlDispatcher.dispatchRewind(player);
            return true;
        }
        if (event.getRepeatCount() != 0) {
            return true;
        }
        if (keyCode == 79 || keyCode == 85) {
            dispatchPlayPause(player);
            return true;
        }
        if (keyCode == 87) {
            this.controlDispatcher.dispatchNext(player);
            return true;
        }
        if (keyCode == 88) {
            this.controlDispatcher.dispatchPrevious(player);
            return true;
        }
        if (keyCode == 126) {
            dispatchPlay(player);
            return true;
        }
        if (keyCode != 127) {
            return true;
        }
        dispatchPause(player);
        return true;
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        this.controlViewLayoutManager.onLayout(changed, left, top, right, bottom);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
        int i = bottom - top;
        int i2 = oldBottom - oldTop;
        if (!(right - left == oldRight - oldLeft && i == i2) && this.settingsWindow.isShowing()) {
            updateSettingsWindowSize();
            this.settingsWindow.update(v, (getWidth() - this.settingsWindow.getWidth()) - this.settingsWindowMargin, (-this.settingsWindow.getHeight()) - this.settingsWindowMargin, -1, -1);
        }
    }

    private boolean shouldShowPauseButton() {
        Player player = this.player;
        return (player == null || player.getPlaybackState() == 4 || this.player.getPlaybackState() == 1 || !this.player.getPlayWhenReady()) ? false : true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dispatchPlayPause(Player player) {
        int playbackState = player.getPlaybackState();
        if (playbackState == 1 || playbackState == 4 || !player.getPlayWhenReady()) {
            dispatchPlay(player);
        } else {
            dispatchPause(player);
        }
    }

    private void dispatchPlay(Player player) {
        int playbackState = player.getPlaybackState();
        if (playbackState == 1) {
            this.controlDispatcher.dispatchPrepare(player);
        } else if (playbackState == 4) {
            seekTo(player, player.getCurrentWindowIndex(), C.TIME_UNSET);
        }
        this.controlDispatcher.dispatchSetPlayWhenReady(player, true);
    }

    private void dispatchPause(Player player) {
        this.controlDispatcher.dispatchSetPlayWhenReady(player, false);
    }

    private static boolean canShowMultiWindowTimeBar(Timeline timeline, Timeline.Window window) {
        if (timeline.getWindowCount() > 100) {
            return false;
        }
        int windowCount = timeline.getWindowCount();
        for (int i = 0; i < windowCount; i++) {
            if (timeline.getWindow(i, window).durationUs == C.TIME_UNSET) {
                return false;
            }
        }
        return true;
    }

    private static void initializeFullScreenButton(View fullScreenButton, View.OnClickListener listener) {
        if (fullScreenButton == null) {
            return;
        }
        fullScreenButton.setVisibility(8);
        fullScreenButton.setOnClickListener(listener);
    }

    private static void updateFullScreenButtonVisibility(View fullScreenButton, boolean visible) {
        if (fullScreenButton == null) {
            return;
        }
        if (visible) {
            fullScreenButton.setVisibility(0);
        } else {
            fullScreenButton.setVisibility(8);
        }
    }

    private static int getRepeatToggleModes(TypedArray a, int defaultValue) {
        return a.getInt(R.styleable.StyledPlayerControlView_repeat_toggle_modes, defaultValue);
    }

    private final class ComponentListener implements Player.Listener, TimeBar.OnScrubListener, View.OnClickListener, PopupWindow.OnDismissListener {
        private ComponentListener() {
        }

        @Override // com.google.android.exoplayer2.Player.Listener, com.google.android.exoplayer2.Player.EventListener
        public void onEvents(Player player, Player.Events events) {
            if (events.containsAny(5, 6)) {
                StyledPlayerControlView.this.updatePlayPauseButton();
            }
            if (events.containsAny(5, 6, 8)) {
                StyledPlayerControlView.this.updateProgress();
            }
            if (events.contains(9)) {
                StyledPlayerControlView.this.updateRepeatModeButton();
            }
            if (events.contains(10)) {
                StyledPlayerControlView.this.updateShuffleButton();
            }
            if (events.containsAny(9, 10, 12, 0, 17, 18)) {
                StyledPlayerControlView.this.updateNavigation();
            }
            if (events.containsAny(12, 0)) {
                StyledPlayerControlView.this.updateTimeline();
            }
            if (events.contains(13)) {
                StyledPlayerControlView.this.updatePlaybackSpeedList();
            }
            if (events.contains(2)) {
                StyledPlayerControlView.this.updateTrackLists();
            }
        }

        @Override // com.google.android.exoplayer2.ui.TimeBar.OnScrubListener
        public void onScrubStart(TimeBar timeBar, long position) {
            StyledPlayerControlView.this.scrubbing = true;
            if (StyledPlayerControlView.this.positionView != null) {
                StyledPlayerControlView.this.positionView.setText(Util.getStringForTime(StyledPlayerControlView.this.formatBuilder, StyledPlayerControlView.this.formatter, position));
            }
            StyledPlayerControlView.this.controlViewLayoutManager.removeHideCallbacks();
        }

        @Override // com.google.android.exoplayer2.ui.TimeBar.OnScrubListener
        public void onScrubMove(TimeBar timeBar, long position) {
            if (StyledPlayerControlView.this.positionView != null) {
                StyledPlayerControlView.this.positionView.setText(Util.getStringForTime(StyledPlayerControlView.this.formatBuilder, StyledPlayerControlView.this.formatter, position));
            }
        }

        @Override // com.google.android.exoplayer2.ui.TimeBar.OnScrubListener
        public void onScrubStop(TimeBar timeBar, long position, boolean canceled) {
            StyledPlayerControlView.this.scrubbing = false;
            if (!canceled && StyledPlayerControlView.this.player != null) {
                StyledPlayerControlView styledPlayerControlView = StyledPlayerControlView.this;
                styledPlayerControlView.seekToTimeBarPosition(styledPlayerControlView.player, position);
            }
            StyledPlayerControlView.this.controlViewLayoutManager.resetHideCallbacks();
        }

        @Override // android.widget.PopupWindow.OnDismissListener
        public void onDismiss() {
            if (StyledPlayerControlView.this.needToHideBars) {
                StyledPlayerControlView.this.controlViewLayoutManager.resetHideCallbacks();
            }
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            Player player = StyledPlayerControlView.this.player;
            if (player == null) {
                return;
            }
            StyledPlayerControlView.this.controlViewLayoutManager.resetHideCallbacks();
            if (StyledPlayerControlView.this.nextButton == view) {
                StyledPlayerControlView.this.controlDispatcher.dispatchNext(player);
                return;
            }
            if (StyledPlayerControlView.this.previousButton == view) {
                StyledPlayerControlView.this.controlDispatcher.dispatchPrevious(player);
                return;
            }
            if (StyledPlayerControlView.this.fastForwardButton != view) {
                if (StyledPlayerControlView.this.rewindButton == view) {
                    StyledPlayerControlView.this.controlDispatcher.dispatchRewind(player);
                    return;
                }
                if (StyledPlayerControlView.this.playPauseButton == view) {
                    StyledPlayerControlView.this.dispatchPlayPause(player);
                    return;
                }
                if (StyledPlayerControlView.this.repeatToggleButton != view) {
                    if (StyledPlayerControlView.this.shuffleButton == view) {
                        StyledPlayerControlView.this.controlDispatcher.dispatchSetShuffleModeEnabled(player, !player.getShuffleModeEnabled());
                        return;
                    }
                    if (StyledPlayerControlView.this.settingsButton == view) {
                        StyledPlayerControlView.this.controlViewLayoutManager.removeHideCallbacks();
                        StyledPlayerControlView styledPlayerControlView = StyledPlayerControlView.this;
                        styledPlayerControlView.displaySettingsWindow(styledPlayerControlView.settingsAdapter);
                        return;
                    }
                    if (StyledPlayerControlView.this.playbackSpeedButton == view) {
                        StyledPlayerControlView.this.controlViewLayoutManager.removeHideCallbacks();
                        StyledPlayerControlView styledPlayerControlView2 = StyledPlayerControlView.this;
                        styledPlayerControlView2.displaySettingsWindow(styledPlayerControlView2.playbackSpeedAdapter);
                        return;
                    } else if (StyledPlayerControlView.this.audioTrackButton == view) {
                        StyledPlayerControlView.this.controlViewLayoutManager.removeHideCallbacks();
                        StyledPlayerControlView styledPlayerControlView3 = StyledPlayerControlView.this;
                        styledPlayerControlView3.displaySettingsWindow(styledPlayerControlView3.audioTrackSelectionAdapter);
                        return;
                    } else {
                        if (StyledPlayerControlView.this.subtitleButton == view) {
                            StyledPlayerControlView.this.controlViewLayoutManager.removeHideCallbacks();
                            StyledPlayerControlView styledPlayerControlView4 = StyledPlayerControlView.this;
                            styledPlayerControlView4.displaySettingsWindow(styledPlayerControlView4.textTrackSelectionAdapter);
                            return;
                        }
                        return;
                    }
                }
                StyledPlayerControlView.this.controlDispatcher.dispatchSetRepeatMode(player, RepeatModeUtil.getNextRepeatMode(player.getRepeatMode(), StyledPlayerControlView.this.repeatToggleModes));
                return;
            }
            if (player.getPlaybackState() != 4) {
                StyledPlayerControlView.this.controlDispatcher.dispatchFastForward(player);
            }
        }
    }

    private class SettingsAdapter extends RecyclerView.Adapter<SettingViewHolder> {
        private final Drawable[] iconIds;
        private final String[] mainTexts;
        private final String[] subTexts;

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public long getItemId(int position) {
            return position;
        }

        public SettingsAdapter(String[] mainTexts, Drawable[] iconIds) {
            this.mainTexts = mainTexts;
            this.subTexts = new String[mainTexts.length];
            this.iconIds = iconIds;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public SettingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return StyledPlayerControlView.this.new SettingViewHolder(LayoutInflater.from(StyledPlayerControlView.this.getContext()).inflate(R.layout.exo_styled_settings_list_item, parent, false));
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public void onBindViewHolder(SettingViewHolder holder, int position) {
            holder.mainTextView.setText(this.mainTexts[position]);
            if (this.subTexts[position] == null) {
                holder.subTextView.setVisibility(8);
            } else {
                holder.subTextView.setText(this.subTexts[position]);
            }
            if (this.iconIds[position] == null) {
                holder.iconView.setVisibility(8);
            } else {
                holder.iconView.setImageDrawable(this.iconIds[position]);
            }
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemCount() {
            return this.mainTexts.length;
        }

        public void setSubTextAtPosition(int position, String subText) {
            this.subTexts[position] = subText;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    final class SettingViewHolder extends RecyclerView.ViewHolder {
        private final ImageView iconView;
        private final TextView mainTextView;
        private final TextView subTextView;

        public SettingViewHolder(View itemView) {
            super(itemView);
            if (Util.SDK_INT < 26) {
                itemView.setFocusable(true);
            }
            this.mainTextView = (TextView) itemView.findViewById(R.id.exo_main_text);
            this.subTextView = (TextView) itemView.findViewById(R.id.exo_sub_text);
            this.iconView = (ImageView) itemView.findViewById(R.id.exo_icon);
            itemView.setOnClickListener(new View.OnClickListener() { // from class: com.google.android.exoplayer2.ui.StyledPlayerControlView$SettingViewHolder$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.m203xbf8efbb9(view);
                }
            });
        }

        /* renamed from: lambda$new$0$com-google-android-exoplayer2-ui-StyledPlayerControlView$SettingViewHolder, reason: not valid java name */
        /* synthetic */ void m203xbf8efbb9(View view) {
            StyledPlayerControlView.this.onSettingViewClicked(getAdapterPosition());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    final class PlaybackSpeedAdapter extends RecyclerView.Adapter<SubSettingViewHolder> {
        private final String[] playbackSpeedTexts;
        private final int[] playbackSpeedsMultBy100;
        private int selectedIndex;

        public PlaybackSpeedAdapter(String[] playbackSpeedTexts, int[] playbackSpeedsMultBy100) {
            this.playbackSpeedTexts = playbackSpeedTexts;
            this.playbackSpeedsMultBy100 = playbackSpeedsMultBy100;
        }

        public void updateSelectedIndex(float playbackSpeed) {
            int iRound = Math.round(playbackSpeed * 100.0f);
            int i = 0;
            int i2 = Integer.MAX_VALUE;
            int i3 = 0;
            while (true) {
                int[] iArr = this.playbackSpeedsMultBy100;
                if (i < iArr.length) {
                    int iAbs = Math.abs(iRound - iArr[i]);
                    if (iAbs < i2) {
                        i3 = i;
                        i2 = iAbs;
                    }
                    i++;
                } else {
                    this.selectedIndex = i3;
                    return;
                }
            }
        }

        public String getSelectedText() {
            return this.playbackSpeedTexts[this.selectedIndex];
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public SubSettingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new SubSettingViewHolder(LayoutInflater.from(StyledPlayerControlView.this.getContext()).inflate(R.layout.exo_styled_sub_settings_list_item, parent, false));
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public void onBindViewHolder(SubSettingViewHolder holder, final int position) {
            if (position < this.playbackSpeedTexts.length) {
                holder.textView.setText(this.playbackSpeedTexts[position]);
            }
            holder.checkView.setVisibility(position == this.selectedIndex ? 0 : 4);
            holder.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.google.android.exoplayer2.ui.StyledPlayerControlView$PlaybackSpeedAdapter$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.m202xdf46b9b2(position, view);
                }
            });
        }

        /* renamed from: lambda$onBindViewHolder$0$com-google-android-exoplayer2-ui-StyledPlayerControlView$PlaybackSpeedAdapter, reason: not valid java name */
        /* synthetic */ void m202xdf46b9b2(int i, View view) {
            if (i != this.selectedIndex) {
                StyledPlayerControlView.this.setPlaybackSpeed(this.playbackSpeedsMultBy100[i] / 100.0f);
            }
            StyledPlayerControlView.this.settingsWindow.dismiss();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemCount() {
            return this.playbackSpeedTexts.length;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class TrackInfo {
        public final int groupIndex;
        public final int rendererIndex;
        public final boolean selected;
        public final int trackIndex;
        public final String trackName;

        public TrackInfo(int rendererIndex, int groupIndex, int trackIndex, String trackName, boolean selected) {
            this.rendererIndex = rendererIndex;
            this.groupIndex = groupIndex;
            this.trackIndex = trackIndex;
            this.trackName = trackName;
            this.selected = selected;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    final class TextTrackSelectionAdapter extends TrackSelectionAdapter {
        @Override // com.google.android.exoplayer2.ui.StyledPlayerControlView.TrackSelectionAdapter
        public void onTrackSelection(String subtext) {
        }

        private TextTrackSelectionAdapter() {
            super();
        }

        @Override // com.google.android.exoplayer2.ui.StyledPlayerControlView.TrackSelectionAdapter
        public void init(List<Integer> rendererIndices, List<TrackInfo> trackInfos, MappingTrackSelector.MappedTrackInfo mappedTrackInfo) {
            boolean z = false;
            int i = 0;
            while (true) {
                if (i >= trackInfos.size()) {
                    break;
                }
                if (trackInfos.get(i).selected) {
                    z = true;
                    break;
                }
                i++;
            }
            if (StyledPlayerControlView.this.subtitleButton != null) {
                ImageView imageView = StyledPlayerControlView.this.subtitleButton;
                StyledPlayerControlView styledPlayerControlView = StyledPlayerControlView.this;
                imageView.setImageDrawable(z ? styledPlayerControlView.subtitleOnButtonDrawable : styledPlayerControlView.subtitleOffButtonDrawable);
                StyledPlayerControlView.this.subtitleButton.setContentDescription(z ? StyledPlayerControlView.this.subtitleOnContentDescription : StyledPlayerControlView.this.subtitleOffContentDescription);
            }
            this.rendererIndices = rendererIndices;
            this.tracks = trackInfos;
            this.mappedTrackInfo = mappedTrackInfo;
        }

        @Override // com.google.android.exoplayer2.ui.StyledPlayerControlView.TrackSelectionAdapter
        public void onBindViewHolderAtZeroPosition(SubSettingViewHolder holder) {
            boolean z;
            holder.textView.setText(R.string.exo_track_selection_none);
            int i = 0;
            while (true) {
                if (i >= this.tracks.size()) {
                    z = true;
                    break;
                } else {
                    if (this.tracks.get(i).selected) {
                        z = false;
                        break;
                    }
                    i++;
                }
            }
            holder.checkView.setVisibility(z ? 0 : 4);
            holder.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.google.android.exoplayer2.ui.StyledPlayerControlView$TextTrackSelectionAdapter$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.m204xcc051aee(view);
                }
            });
        }

        /* renamed from: lambda$onBindViewHolderAtZeroPosition$0$com-google-android-exoplayer2-ui-StyledPlayerControlView$TextTrackSelectionAdapter, reason: not valid java name */
        /* synthetic */ void m204xcc051aee(View view) {
            if (StyledPlayerControlView.this.trackSelector != null) {
                DefaultTrackSelector.ParametersBuilder parametersBuilderBuildUpon = StyledPlayerControlView.this.trackSelector.getParameters().buildUpon();
                for (int i = 0; i < this.rendererIndices.size(); i++) {
                    int iIntValue = this.rendererIndices.get(i).intValue();
                    parametersBuilderBuildUpon = parametersBuilderBuildUpon.clearSelectionOverrides(iIntValue).setRendererDisabled(iIntValue, true);
                }
                ((DefaultTrackSelector) Assertions.checkNotNull(StyledPlayerControlView.this.trackSelector)).setParameters(parametersBuilderBuildUpon);
                StyledPlayerControlView.this.settingsWindow.dismiss();
            }
        }

        @Override // com.google.android.exoplayer2.ui.StyledPlayerControlView.TrackSelectionAdapter, androidx.recyclerview.widget.RecyclerView.Adapter
        public void onBindViewHolder(SubSettingViewHolder holder, int position) {
            super.onBindViewHolder(holder, position);
            if (position > 0) {
                holder.checkView.setVisibility(this.tracks.get(position + (-1)).selected ? 0 : 4);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    final class AudioTrackSelectionAdapter extends TrackSelectionAdapter {
        private AudioTrackSelectionAdapter() {
            super();
        }

        @Override // com.google.android.exoplayer2.ui.StyledPlayerControlView.TrackSelectionAdapter
        public void onBindViewHolderAtZeroPosition(SubSettingViewHolder holder) {
            boolean z;
            holder.textView.setText(R.string.exo_track_selection_auto);
            DefaultTrackSelector.Parameters parameters = ((DefaultTrackSelector) Assertions.checkNotNull(StyledPlayerControlView.this.trackSelector)).getParameters();
            int i = 0;
            while (true) {
                if (i >= this.rendererIndices.size()) {
                    z = false;
                    break;
                }
                int iIntValue = this.rendererIndices.get(i).intValue();
                if (parameters.hasSelectionOverride(iIntValue, ((MappingTrackSelector.MappedTrackInfo) Assertions.checkNotNull(this.mappedTrackInfo)).getTrackGroups(iIntValue))) {
                    z = true;
                    break;
                }
                i++;
            }
            holder.checkView.setVisibility(z ? 4 : 0);
            holder.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.google.android.exoplayer2.ui.StyledPlayerControlView$AudioTrackSelectionAdapter$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.m201x5e042c6b(view);
                }
            });
        }

        /* renamed from: lambda$onBindViewHolderAtZeroPosition$0$com-google-android-exoplayer2-ui-StyledPlayerControlView$AudioTrackSelectionAdapter, reason: not valid java name */
        /* synthetic */ void m201x5e042c6b(View view) {
            if (StyledPlayerControlView.this.trackSelector != null) {
                DefaultTrackSelector.ParametersBuilder parametersBuilderBuildUpon = StyledPlayerControlView.this.trackSelector.getParameters().buildUpon();
                for (int i = 0; i < this.rendererIndices.size(); i++) {
                    parametersBuilderBuildUpon = parametersBuilderBuildUpon.clearSelectionOverrides(this.rendererIndices.get(i).intValue());
                }
                ((DefaultTrackSelector) Assertions.checkNotNull(StyledPlayerControlView.this.trackSelector)).setParameters(parametersBuilderBuildUpon);
            }
            StyledPlayerControlView.this.settingsAdapter.setSubTextAtPosition(1, StyledPlayerControlView.this.getResources().getString(R.string.exo_track_selection_auto));
            StyledPlayerControlView.this.settingsWindow.dismiss();
        }

        @Override // com.google.android.exoplayer2.ui.StyledPlayerControlView.TrackSelectionAdapter
        public void onTrackSelection(String subtext) {
            StyledPlayerControlView.this.settingsAdapter.setSubTextAtPosition(1, subtext);
        }

        @Override // com.google.android.exoplayer2.ui.StyledPlayerControlView.TrackSelectionAdapter
        public void init(List<Integer> rendererIndices, List<TrackInfo> trackInfos, MappingTrackSelector.MappedTrackInfo mappedTrackInfo) {
            boolean z;
            int i = 0;
            int i2 = 0;
            while (true) {
                if (i2 >= rendererIndices.size()) {
                    z = false;
                    break;
                }
                int iIntValue = rendererIndices.get(i2).intValue();
                TrackGroupArray trackGroups = mappedTrackInfo.getTrackGroups(iIntValue);
                if (StyledPlayerControlView.this.trackSelector != null && StyledPlayerControlView.this.trackSelector.getParameters().hasSelectionOverride(iIntValue, trackGroups)) {
                    z = true;
                    break;
                }
                i2++;
            }
            if (!trackInfos.isEmpty()) {
                if (z) {
                    while (true) {
                        if (i >= trackInfos.size()) {
                            break;
                        }
                        TrackInfo trackInfo = trackInfos.get(i);
                        if (trackInfo.selected) {
                            StyledPlayerControlView.this.settingsAdapter.setSubTextAtPosition(1, trackInfo.trackName);
                            break;
                        }
                        i++;
                    }
                } else {
                    StyledPlayerControlView.this.settingsAdapter.setSubTextAtPosition(1, StyledPlayerControlView.this.getResources().getString(R.string.exo_track_selection_auto));
                }
            } else {
                StyledPlayerControlView.this.settingsAdapter.setSubTextAtPosition(1, StyledPlayerControlView.this.getResources().getString(R.string.exo_track_selection_none));
            }
            this.rendererIndices = rendererIndices;
            this.tracks = trackInfos;
            this.mappedTrackInfo = mappedTrackInfo;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    abstract class TrackSelectionAdapter extends RecyclerView.Adapter<SubSettingViewHolder> {
        protected List<Integer> rendererIndices = new ArrayList();
        protected List<TrackInfo> tracks = new ArrayList();
        protected MappingTrackSelector.MappedTrackInfo mappedTrackInfo = null;

        public abstract void init(List<Integer> rendererIndices, List<TrackInfo> trackInfos, MappingTrackSelector.MappedTrackInfo mappedTrackInfo);

        public abstract void onBindViewHolderAtZeroPosition(SubSettingViewHolder holder);

        public abstract void onTrackSelection(String subtext);

        public TrackSelectionAdapter() {
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public SubSettingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new SubSettingViewHolder(LayoutInflater.from(StyledPlayerControlView.this.getContext()).inflate(R.layout.exo_styled_sub_settings_list_item, parent, false));
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public void onBindViewHolder(SubSettingViewHolder holder, int position) {
            if (StyledPlayerControlView.this.trackSelector == null || this.mappedTrackInfo == null) {
                return;
            }
            if (position == 0) {
                onBindViewHolderAtZeroPosition(holder);
                return;
            }
            final TrackInfo trackInfo = this.tracks.get(position - 1);
            boolean z = ((DefaultTrackSelector) Assertions.checkNotNull(StyledPlayerControlView.this.trackSelector)).getParameters().hasSelectionOverride(trackInfo.rendererIndex, this.mappedTrackInfo.getTrackGroups(trackInfo.rendererIndex)) && trackInfo.selected;
            holder.textView.setText(trackInfo.trackName);
            holder.checkView.setVisibility(z ? 0 : 4);
            holder.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.google.android.exoplayer2.ui.StyledPlayerControlView$TrackSelectionAdapter$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.m205x30db9e7f(trackInfo, view);
                }
            });
        }

        /* renamed from: lambda$onBindViewHolder$0$com-google-android-exoplayer2-ui-StyledPlayerControlView$TrackSelectionAdapter, reason: not valid java name */
        /* synthetic */ void m205x30db9e7f(TrackInfo trackInfo, View view) {
            if (this.mappedTrackInfo == null || StyledPlayerControlView.this.trackSelector == null) {
                return;
            }
            DefaultTrackSelector.ParametersBuilder parametersBuilderBuildUpon = StyledPlayerControlView.this.trackSelector.getParameters().buildUpon();
            for (int i = 0; i < this.rendererIndices.size(); i++) {
                int iIntValue = this.rendererIndices.get(i).intValue();
                if (iIntValue == trackInfo.rendererIndex) {
                    parametersBuilderBuildUpon = parametersBuilderBuildUpon.setSelectionOverride(iIntValue, ((MappingTrackSelector.MappedTrackInfo) Assertions.checkNotNull(this.mappedTrackInfo)).getTrackGroups(iIntValue), new DefaultTrackSelector.SelectionOverride(trackInfo.groupIndex, trackInfo.trackIndex)).setRendererDisabled(iIntValue, false);
                } else {
                    parametersBuilderBuildUpon = parametersBuilderBuildUpon.clearSelectionOverrides(iIntValue).setRendererDisabled(iIntValue, true);
                }
            }
            ((DefaultTrackSelector) Assertions.checkNotNull(StyledPlayerControlView.this.trackSelector)).setParameters(parametersBuilderBuildUpon);
            onTrackSelection(trackInfo.trackName);
            StyledPlayerControlView.this.settingsWindow.dismiss();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemCount() {
            if (this.tracks.isEmpty()) {
                return 0;
            }
            return this.tracks.size() + 1;
        }

        public void clear() {
            this.tracks = Collections.emptyList();
            this.mappedTrackInfo = null;
        }
    }

    private static class SubSettingViewHolder extends RecyclerView.ViewHolder {
        public final View checkView;
        public final TextView textView;

        public SubSettingViewHolder(View itemView) {
            super(itemView);
            if (Util.SDK_INT < 26) {
                itemView.setFocusable(true);
            }
            this.textView = (TextView) itemView.findViewById(R.id.exo_text);
            this.checkView = itemView.findViewById(R.id.exo_check);
        }
    }
}
