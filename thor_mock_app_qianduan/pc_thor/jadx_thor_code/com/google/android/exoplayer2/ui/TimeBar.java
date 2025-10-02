package com.google.android.exoplayer2.ui;

/* loaded from: classes.dex */
public interface TimeBar {

    public interface OnScrubListener {
        void onScrubMove(TimeBar timeBar, long position);

        void onScrubStart(TimeBar timeBar, long position);

        void onScrubStop(TimeBar timeBar, long position, boolean canceled);
    }

    void addListener(OnScrubListener listener);

    long getPreferredUpdateDelay();

    void removeListener(OnScrubListener listener);

    void setAdGroupTimesMs(long[] adGroupTimesMs, boolean[] playedAdGroups, int adGroupCount);

    void setBufferedPosition(long bufferedPosition);

    void setDuration(long duration);

    void setEnabled(boolean enabled);

    void setKeyCountIncrement(int count);

    void setKeyTimeIncrement(long time);

    void setPosition(long position);
}
