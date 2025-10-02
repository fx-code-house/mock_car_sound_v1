package com.google.android.exoplayer2.trackselection;

import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.source.TrackGroup;

/* loaded from: classes.dex */
public interface TrackSelection {
    public static final int TYPE_CUSTOM_BASE = 10000;
    public static final int TYPE_UNSET = 0;

    Format getFormat(int index);

    int getIndexInTrackGroup(int index);

    TrackGroup getTrackGroup();

    int getType();

    int indexOf(int indexInTrackGroup);

    int indexOf(Format format);

    int length();
}
