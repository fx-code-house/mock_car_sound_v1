package com.google.android.exoplayer2.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.MappingTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionUtil;
import com.google.android.exoplayer2.util.Assertions;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/* loaded from: classes.dex */
public final class TrackSelectionDialogBuilder {
    private boolean allowAdaptiveSelections;
    private boolean allowMultipleOverrides;
    private final DialogCallback callback;
    private final Context context;
    private boolean isDisabled;
    private final MappingTrackSelector.MappedTrackInfo mappedTrackInfo;
    private List<DefaultTrackSelector.SelectionOverride> overrides;
    private final int rendererIndex;
    private boolean showDisableOption;
    private int themeResId;
    private final CharSequence title;
    private Comparator<Format> trackFormatComparator;
    private TrackNameProvider trackNameProvider;

    public interface DialogCallback {
        void onTracksSelected(boolean isDisabled, List<DefaultTrackSelector.SelectionOverride> overrides);
    }

    public TrackSelectionDialogBuilder(Context context, CharSequence title, MappingTrackSelector.MappedTrackInfo mappedTrackInfo, int rendererIndex, DialogCallback callback) {
        this.context = context;
        this.title = title;
        this.mappedTrackInfo = mappedTrackInfo;
        this.rendererIndex = rendererIndex;
        this.callback = callback;
        this.overrides = Collections.emptyList();
    }

    public TrackSelectionDialogBuilder(Context context, CharSequence title, final DefaultTrackSelector trackSelector, final int rendererIndex) {
        this.context = context;
        this.title = title;
        MappingTrackSelector.MappedTrackInfo mappedTrackInfo = (MappingTrackSelector.MappedTrackInfo) Assertions.checkNotNull(trackSelector.getCurrentMappedTrackInfo());
        this.mappedTrackInfo = mappedTrackInfo;
        this.rendererIndex = rendererIndex;
        final TrackGroupArray trackGroups = mappedTrackInfo.getTrackGroups(rendererIndex);
        final DefaultTrackSelector.Parameters parameters = trackSelector.getParameters();
        this.isDisabled = parameters.getRendererDisabled(rendererIndex);
        DefaultTrackSelector.SelectionOverride selectionOverride = parameters.getSelectionOverride(rendererIndex, trackGroups);
        this.overrides = selectionOverride == null ? Collections.emptyList() : Collections.singletonList(selectionOverride);
        this.callback = new DialogCallback() { // from class: com.google.android.exoplayer2.ui.TrackSelectionDialogBuilder$$ExternalSyntheticLambda1
            @Override // com.google.android.exoplayer2.ui.TrackSelectionDialogBuilder.DialogCallback
            public final void onTracksSelected(boolean z, List list) {
                trackSelector.setParameters(TrackSelectionUtil.updateParametersWithOverride(parameters, rendererIndex, trackGroups, z, list.isEmpty() ? null : (DefaultTrackSelector.SelectionOverride) list.get(0)));
            }
        };
    }

    public TrackSelectionDialogBuilder setTheme(int themeResId) {
        this.themeResId = themeResId;
        return this;
    }

    public TrackSelectionDialogBuilder setIsDisabled(boolean isDisabled) {
        this.isDisabled = isDisabled;
        return this;
    }

    public TrackSelectionDialogBuilder setOverride(DefaultTrackSelector.SelectionOverride override) {
        return setOverrides(override == null ? Collections.emptyList() : Collections.singletonList(override));
    }

    public TrackSelectionDialogBuilder setOverrides(List<DefaultTrackSelector.SelectionOverride> overrides) {
        this.overrides = overrides;
        return this;
    }

    public TrackSelectionDialogBuilder setAllowAdaptiveSelections(boolean allowAdaptiveSelections) {
        this.allowAdaptiveSelections = allowAdaptiveSelections;
        return this;
    }

    public TrackSelectionDialogBuilder setAllowMultipleOverrides(boolean allowMultipleOverrides) {
        this.allowMultipleOverrides = allowMultipleOverrides;
        return this;
    }

    public TrackSelectionDialogBuilder setShowDisableOption(boolean showDisableOption) {
        this.showDisableOption = showDisableOption;
        return this;
    }

    public void setTrackFormatComparator(Comparator<Format> trackFormatComparator) {
        this.trackFormatComparator = trackFormatComparator;
    }

    public TrackSelectionDialogBuilder setTrackNameProvider(TrackNameProvider trackNameProvider) {
        this.trackNameProvider = trackNameProvider;
        return this;
    }

    public Dialog build() throws IllegalAccessException, InstantiationException, ClassNotFoundException, IllegalArgumentException, InvocationTargetException {
        Dialog dialogBuildForAndroidX = buildForAndroidX();
        return dialogBuildForAndroidX == null ? buildForPlatform() : dialogBuildForAndroidX;
    }

    private Dialog buildForPlatform() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.context, this.themeResId);
        View viewInflate = LayoutInflater.from(builder.getContext()).inflate(R.layout.exo_track_selection_dialog, (ViewGroup) null);
        return builder.setTitle(this.title).setView(viewInflate).setPositiveButton(android.R.string.ok, setUpDialogView(viewInflate)).setNegativeButton(android.R.string.cancel, (DialogInterface.OnClickListener) null).create();
    }

    private Dialog buildForAndroidX() throws IllegalAccessException, InstantiationException, ClassNotFoundException, IllegalArgumentException, InvocationTargetException {
        try {
            Class<?> cls = Class.forName("androidx.appcompat.app.AlertDialog$Builder");
            Object objNewInstance = cls.getConstructor(Context.class, Integer.TYPE).newInstance(this.context, Integer.valueOf(this.themeResId));
            View viewInflate = LayoutInflater.from((Context) cls.getMethod("getContext", new Class[0]).invoke(objNewInstance, new Object[0])).inflate(R.layout.exo_track_selection_dialog, (ViewGroup) null);
            DialogInterface.OnClickListener upDialogView = setUpDialogView(viewInflate);
            cls.getMethod("setTitle", CharSequence.class).invoke(objNewInstance, this.title);
            cls.getMethod("setView", View.class).invoke(objNewInstance, viewInflate);
            cls.getMethod("setPositiveButton", Integer.TYPE, DialogInterface.OnClickListener.class).invoke(objNewInstance, Integer.valueOf(android.R.string.ok), upDialogView);
            cls.getMethod("setNegativeButton", Integer.TYPE, DialogInterface.OnClickListener.class).invoke(objNewInstance, Integer.valueOf(android.R.string.cancel), null);
            return (Dialog) cls.getMethod("create", new Class[0]).invoke(objNewInstance, new Object[0]);
        } catch (ClassNotFoundException unused) {
            return null;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    private DialogInterface.OnClickListener setUpDialogView(View dialogView) {
        final TrackSelectionView trackSelectionView = (TrackSelectionView) dialogView.findViewById(R.id.exo_track_selection_view);
        trackSelectionView.setAllowMultipleOverrides(this.allowMultipleOverrides);
        trackSelectionView.setAllowAdaptiveSelections(this.allowAdaptiveSelections);
        trackSelectionView.setShowDisableOption(this.showDisableOption);
        TrackNameProvider trackNameProvider = this.trackNameProvider;
        if (trackNameProvider != null) {
            trackSelectionView.setTrackNameProvider(trackNameProvider);
        }
        trackSelectionView.init(this.mappedTrackInfo, this.rendererIndex, this.isDisabled, this.overrides, this.trackFormatComparator, null);
        return new DialogInterface.OnClickListener() { // from class: com.google.android.exoplayer2.ui.TrackSelectionDialogBuilder$$ExternalSyntheticLambda0
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                this.f$0.m211x1d103515(trackSelectionView, dialogInterface, i);
            }
        };
    }

    /* renamed from: lambda$setUpDialogView$1$com-google-android-exoplayer2-ui-TrackSelectionDialogBuilder, reason: not valid java name */
    /* synthetic */ void m211x1d103515(TrackSelectionView trackSelectionView, DialogInterface dialogInterface, int i) {
        this.callback.onTracksSelected(trackSelectionView.getIsDisabled(), trackSelectionView.getOverrides());
    }
}
