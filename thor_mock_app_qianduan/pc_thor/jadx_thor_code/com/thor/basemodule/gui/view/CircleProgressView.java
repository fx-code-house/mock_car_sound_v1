package com.thor.basemodule.gui.view;

import android.animation.LayoutTransition;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import com.google.android.exoplayer2.source.rtsp.SessionDescription;
import com.thor.basemodule.R;
import com.thor.basemodule.databinding.ViewCircleProgressBinding;
import timber.log.Timber;

/* loaded from: classes3.dex */
public class CircleProgressView extends FrameLayout {
    public static final float PROGRESS_MAX = 100.0f;
    public static final float PROGRESS_MIN = 0.0f;
    private ViewCircleProgressBinding binding;

    public CircleProgressView(Context context) {
        super(context);
        initialize();
    }

    public CircleProgressView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initialize();
    }

    public CircleProgressView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        initialize();
    }

    private void initialize() {
        Timber.i("initialize", new Object[0]);
        setSaveEnabled(true);
        setLayoutTransition(new LayoutTransition());
        this.binding = ViewCircleProgressBinding.inflate(LayoutInflater.from(getContext()), this, true);
        isInEditMode();
    }

    public void setProgress(int i) {
        setText(String.valueOf(i));
        setAngle(i * 3.6f);
    }

    public void setText(String str) {
        this.binding.textView.setVisibility(0);
        this.binding.textView.setText(str.concat(" %"));
    }

    public void setAngle(float f) {
        this.binding.circleBarView.setAngle(f);
    }

    public void setAngleOverRatio(float f, float f2) {
        this.binding.circleBarView.setAngle((f / f2) * 360.0f);
    }

    public void setTextSize(int i) {
        this.binding.textView.setTextSize(0, getResources().getDimension(i));
    }

    public void clear() {
        this.binding.textView.setVisibility(8);
        this.binding.textView.setText(getResources().getString(R.string.text_circle_progress_view, SessionDescription.SUPPORTED_SDP_VERSION));
        this.binding.circleBarView.setAngle(0.0f);
    }
}
