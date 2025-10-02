package com.google.android.exoplayer2.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.LinearLayout;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.source.TrackGroup;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.MappingTrackSelector;
import com.google.android.exoplayer2.ui.TrackSelectionView;
import com.google.android.exoplayer2.util.Assertions;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import org.checkerframework.checker.nullness.qual.RequiresNonNull;

/* loaded from: classes.dex */
public class TrackSelectionView extends LinearLayout {
    private boolean allowAdaptiveSelections;
    private boolean allowMultipleOverrides;
    private final ComponentListener componentListener;
    private final CheckedTextView defaultView;
    private final CheckedTextView disableView;
    private final LayoutInflater inflater;
    private boolean isDisabled;
    private TrackSelectionListener listener;
    private MappingTrackSelector.MappedTrackInfo mappedTrackInfo;
    private final SparseArray<DefaultTrackSelector.SelectionOverride> overrides;
    private int rendererIndex;
    private final int selectableItemBackgroundResourceId;
    private TrackGroupArray trackGroups;
    private Comparator<TrackInfo> trackInfoComparator;
    private TrackNameProvider trackNameProvider;
    private CheckedTextView[][] trackViews;

    public interface TrackSelectionListener {
        void onTrackSelectionChanged(boolean isDisabled, List<DefaultTrackSelector.SelectionOverride> overrides);
    }

    public TrackSelectionView(Context context) {
        this(context, null);
    }

    public TrackSelectionView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TrackSelectionView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(1);
        this.overrides = new SparseArray<>();
        setSaveFromParentEnabled(false);
        TypedArray typedArrayObtainStyledAttributes = context.getTheme().obtainStyledAttributes(new int[]{android.R.attr.selectableItemBackground});
        int resourceId = typedArrayObtainStyledAttributes.getResourceId(0, 0);
        this.selectableItemBackgroundResourceId = resourceId;
        typedArrayObtainStyledAttributes.recycle();
        LayoutInflater layoutInflaterFrom = LayoutInflater.from(context);
        this.inflater = layoutInflaterFrom;
        ComponentListener componentListener = new ComponentListener();
        this.componentListener = componentListener;
        this.trackNameProvider = new DefaultTrackNameProvider(getResources());
        this.trackGroups = TrackGroupArray.EMPTY;
        CheckedTextView checkedTextView = (CheckedTextView) layoutInflaterFrom.inflate(android.R.layout.simple_list_item_single_choice, (ViewGroup) this, false);
        this.disableView = checkedTextView;
        checkedTextView.setBackgroundResource(resourceId);
        checkedTextView.setText(R.string.exo_track_selection_none);
        checkedTextView.setEnabled(false);
        checkedTextView.setFocusable(true);
        checkedTextView.setOnClickListener(componentListener);
        checkedTextView.setVisibility(8);
        addView(checkedTextView);
        addView(layoutInflaterFrom.inflate(R.layout.exo_list_divider, (ViewGroup) this, false));
        CheckedTextView checkedTextView2 = (CheckedTextView) layoutInflaterFrom.inflate(android.R.layout.simple_list_item_single_choice, (ViewGroup) this, false);
        this.defaultView = checkedTextView2;
        checkedTextView2.setBackgroundResource(resourceId);
        checkedTextView2.setText(R.string.exo_track_selection_auto);
        checkedTextView2.setEnabled(false);
        checkedTextView2.setFocusable(true);
        checkedTextView2.setOnClickListener(componentListener);
        addView(checkedTextView2);
    }

    public void setAllowAdaptiveSelections(boolean allowAdaptiveSelections) {
        if (this.allowAdaptiveSelections != allowAdaptiveSelections) {
            this.allowAdaptiveSelections = allowAdaptiveSelections;
            updateViews();
        }
    }

    public void setAllowMultipleOverrides(boolean allowMultipleOverrides) {
        if (this.allowMultipleOverrides != allowMultipleOverrides) {
            this.allowMultipleOverrides = allowMultipleOverrides;
            if (!allowMultipleOverrides && this.overrides.size() > 1) {
                for (int size = this.overrides.size() - 1; size > 0; size--) {
                    this.overrides.remove(size);
                }
            }
            updateViews();
        }
    }

    public void setShowDisableOption(boolean showDisableOption) {
        this.disableView.setVisibility(showDisableOption ? 0 : 8);
    }

    public void setTrackNameProvider(TrackNameProvider trackNameProvider) {
        this.trackNameProvider = (TrackNameProvider) Assertions.checkNotNull(trackNameProvider);
        updateViews();
    }

    public void init(MappingTrackSelector.MappedTrackInfo mappedTrackInfo, int rendererIndex, boolean isDisabled, List<DefaultTrackSelector.SelectionOverride> overrides, final Comparator<Format> trackFormatComparator, TrackSelectionListener listener) {
        this.mappedTrackInfo = mappedTrackInfo;
        this.rendererIndex = rendererIndex;
        this.isDisabled = isDisabled;
        this.trackInfoComparator = trackFormatComparator == null ? null : new Comparator() { // from class: com.google.android.exoplayer2.ui.TrackSelectionView$$ExternalSyntheticLambda0
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return trackFormatComparator.compare(((TrackSelectionView.TrackInfo) obj).format, ((TrackSelectionView.TrackInfo) obj2).format);
            }
        };
        this.listener = listener;
        int size = this.allowMultipleOverrides ? overrides.size() : Math.min(overrides.size(), 1);
        for (int i = 0; i < size; i++) {
            DefaultTrackSelector.SelectionOverride selectionOverride = overrides.get(i);
            this.overrides.put(selectionOverride.groupIndex, selectionOverride);
        }
        updateViews();
    }

    public boolean getIsDisabled() {
        return this.isDisabled;
    }

    public List<DefaultTrackSelector.SelectionOverride> getOverrides() {
        ArrayList arrayList = new ArrayList(this.overrides.size());
        for (int i = 0; i < this.overrides.size(); i++) {
            arrayList.add(this.overrides.valueAt(i));
        }
        return arrayList;
    }

    private void updateViews() {
        for (int childCount = getChildCount() - 1; childCount >= 3; childCount--) {
            removeViewAt(childCount);
        }
        if (this.mappedTrackInfo == null) {
            this.disableView.setEnabled(false);
            this.defaultView.setEnabled(false);
            return;
        }
        this.disableView.setEnabled(true);
        this.defaultView.setEnabled(true);
        TrackGroupArray trackGroups = this.mappedTrackInfo.getTrackGroups(this.rendererIndex);
        this.trackGroups = trackGroups;
        this.trackViews = new CheckedTextView[trackGroups.length][];
        boolean zShouldEnableMultiGroupSelection = shouldEnableMultiGroupSelection();
        for (int i = 0; i < this.trackGroups.length; i++) {
            TrackGroup trackGroup = this.trackGroups.get(i);
            boolean zShouldEnableAdaptiveSelection = shouldEnableAdaptiveSelection(i);
            this.trackViews[i] = new CheckedTextView[trackGroup.length];
            int i2 = trackGroup.length;
            TrackInfo[] trackInfoArr = new TrackInfo[i2];
            for (int i3 = 0; i3 < trackGroup.length; i3++) {
                trackInfoArr[i3] = new TrackInfo(i, i3, trackGroup.getFormat(i3));
            }
            Comparator<TrackInfo> comparator = this.trackInfoComparator;
            if (comparator != null) {
                Arrays.sort(trackInfoArr, comparator);
            }
            for (int i4 = 0; i4 < i2; i4++) {
                if (i4 == 0) {
                    addView(this.inflater.inflate(R.layout.exo_list_divider, (ViewGroup) this, false));
                }
                CheckedTextView checkedTextView = (CheckedTextView) this.inflater.inflate((zShouldEnableAdaptiveSelection || zShouldEnableMultiGroupSelection) ? android.R.layout.simple_list_item_multiple_choice : android.R.layout.simple_list_item_single_choice, (ViewGroup) this, false);
                checkedTextView.setBackgroundResource(this.selectableItemBackgroundResourceId);
                checkedTextView.setText(this.trackNameProvider.getTrackName(trackInfoArr[i4].format));
                checkedTextView.setTag(trackInfoArr[i4]);
                if (this.mappedTrackInfo.getTrackSupport(this.rendererIndex, i, i4) == 4) {
                    checkedTextView.setFocusable(true);
                    checkedTextView.setOnClickListener(this.componentListener);
                } else {
                    checkedTextView.setFocusable(false);
                    checkedTextView.setEnabled(false);
                }
                this.trackViews[i][i4] = checkedTextView;
                addView(checkedTextView);
            }
        }
        updateViewStates();
    }

    private void updateViewStates() {
        this.disableView.setChecked(this.isDisabled);
        this.defaultView.setChecked(!this.isDisabled && this.overrides.size() == 0);
        for (int i = 0; i < this.trackViews.length; i++) {
            DefaultTrackSelector.SelectionOverride selectionOverride = this.overrides.get(i);
            int i2 = 0;
            while (true) {
                CheckedTextView[] checkedTextViewArr = this.trackViews[i];
                if (i2 < checkedTextViewArr.length) {
                    if (selectionOverride != null) {
                        this.trackViews[i][i2].setChecked(selectionOverride.containsTrack(((TrackInfo) Assertions.checkNotNull(checkedTextViewArr[i2].getTag())).trackIndex));
                    } else {
                        checkedTextViewArr[i2].setChecked(false);
                    }
                    i2++;
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onClick(View view) {
        if (view == this.disableView) {
            onDisableViewClicked();
        } else if (view == this.defaultView) {
            onDefaultViewClicked();
        } else {
            onTrackViewClicked(view);
        }
        updateViewStates();
        TrackSelectionListener trackSelectionListener = this.listener;
        if (trackSelectionListener != null) {
            trackSelectionListener.onTrackSelectionChanged(getIsDisabled(), getOverrides());
        }
    }

    private void onDisableViewClicked() {
        this.isDisabled = true;
        this.overrides.clear();
    }

    private void onDefaultViewClicked() {
        this.isDisabled = false;
        this.overrides.clear();
    }

    private void onTrackViewClicked(View view) {
        this.isDisabled = false;
        TrackInfo trackInfo = (TrackInfo) Assertions.checkNotNull(view.getTag());
        int i = trackInfo.groupIndex;
        int i2 = trackInfo.trackIndex;
        DefaultTrackSelector.SelectionOverride selectionOverride = this.overrides.get(i);
        Assertions.checkNotNull(this.mappedTrackInfo);
        if (selectionOverride == null) {
            if (!this.allowMultipleOverrides && this.overrides.size() > 0) {
                this.overrides.clear();
            }
            this.overrides.put(i, new DefaultTrackSelector.SelectionOverride(i, i2));
            return;
        }
        int i3 = selectionOverride.length;
        int[] iArr = selectionOverride.tracks;
        boolean zIsChecked = ((CheckedTextView) view).isChecked();
        boolean zShouldEnableAdaptiveSelection = shouldEnableAdaptiveSelection(i);
        boolean z = zShouldEnableAdaptiveSelection || shouldEnableMultiGroupSelection();
        if (zIsChecked && z) {
            if (i3 == 1) {
                this.overrides.remove(i);
                return;
            } else {
                this.overrides.put(i, new DefaultTrackSelector.SelectionOverride(i, getTracksRemoving(iArr, i2)));
                return;
            }
        }
        if (zIsChecked) {
            return;
        }
        if (zShouldEnableAdaptiveSelection) {
            this.overrides.put(i, new DefaultTrackSelector.SelectionOverride(i, getTracksAdding(iArr, i2)));
        } else {
            this.overrides.put(i, new DefaultTrackSelector.SelectionOverride(i, i2));
        }
    }

    @RequiresNonNull({"mappedTrackInfo"})
    private boolean shouldEnableAdaptiveSelection(int groupIndex) {
        return this.allowAdaptiveSelections && this.trackGroups.get(groupIndex).length > 1 && this.mappedTrackInfo.getAdaptiveSupport(this.rendererIndex, groupIndex, false) != 0;
    }

    private boolean shouldEnableMultiGroupSelection() {
        return this.allowMultipleOverrides && this.trackGroups.length > 1;
    }

    private static int[] getTracksAdding(int[] tracks, int addedTrack) {
        int[] iArrCopyOf = Arrays.copyOf(tracks, tracks.length + 1);
        iArrCopyOf[iArrCopyOf.length - 1] = addedTrack;
        return iArrCopyOf;
    }

    private static int[] getTracksRemoving(int[] tracks, int removedTrack) {
        int[] iArr = new int[tracks.length - 1];
        int i = 0;
        for (int i2 : tracks) {
            if (i2 != removedTrack) {
                iArr[i] = i2;
                i++;
            }
        }
        return iArr;
    }

    private class ComponentListener implements View.OnClickListener {
        private ComponentListener() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            TrackSelectionView.this.onClick(view);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class TrackInfo {
        public final Format format;
        public final int groupIndex;
        public final int trackIndex;

        public TrackInfo(int groupIndex, int trackIndex, Format format) {
            this.groupIndex = groupIndex;
            this.trackIndex = trackIndex;
            this.format = format;
        }
    }
}
