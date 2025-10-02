package dagger.hilt.android.lifecycle;

/* loaded from: classes3.dex */
public interface RetainedLifecycle {

    public interface OnClearedListener {
        void onCleared();
    }

    void addOnClearedListener(OnClearedListener listener);

    void removeOnClearedListener(OnClearedListener listener);
}
