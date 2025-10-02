package com.thor.app.gui.views.tachometer;

import android.animation.ValueAnimator;
import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import androidx.core.content.ContextCompat;
import com.carsystems.thor.app.R;
import com.carsystems.thor.app.databinding.ViewEqualizeBinding;
import com.google.android.gms.common.internal.ImagesContract;
import com.thor.app.utils.extensions.StringKt;
import com.thor.basemodule.extensions.ContextKt;
import com.thor.businessmodule.model.DemoSoundPackage;
import java.io.IOException;
import java.lang.ref.WeakReference;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import timber.log.Timber;

/* compiled from: EqualizeView.kt */
@Metadata(d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u00012\u00020\u00022\u00020\u0003:\u0001,B\u000f\b\u0016\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006B\u0019\b\u0016\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\b¢\u0006\u0002\u0010\tB!\b\u0016\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\b\u0012\u0006\u0010\n\u001a\u00020\u000b¢\u0006\u0002\u0010\fJ\u0010\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001bH\u0002J\u0010\u0010\u001c\u001a\u00020\u00192\u0006\u0010\u001d\u001a\u00020\u001bH\u0002J\u0012\u0010\u001e\u001a\u00020\u00192\b\u0010\u0007\u001a\u0004\u0018\u00010\bH\u0002J\u0012\u0010\u001f\u001a\u00020\u00192\b\u0010 \u001a\u0004\u0018\u00010\u0010H\u0016J\b\u0010!\u001a\u00020\u0019H\u0014J\u0012\u0010\"\u001a\u00020\u00192\b\u0010 \u001a\u0004\u0018\u00010\u0010H\u0016J\u000e\u0010#\u001a\u00020\u00192\u0006\u0010$\u001a\u00020\u001bJ\u000e\u0010%\u001a\u00020\u00192\u0006\u0010$\u001a\u00020\u001bJ\b\u0010&\u001a\u00020\u0019H\u0002J\b\u0010'\u001a\u00020\u0019H\u0002J\u0006\u0010(\u001a\u00020\u0019J\u0010\u0010)\u001a\u00020\u00192\b\u0010*\u001a\u0004\u0018\u00010+R\u000e\u0010\r\u001a\u00020\u000eX\u0082.¢\u0006\u0002\n\u0000R\u0010\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\"\u0010\u0013\u001a\u0004\u0018\u00010\u00122\b\u0010\u0011\u001a\u0004\u0018\u00010\u0012@BX\u0082\u000e¢\u0006\b\n\u0000\"\u0004\b\u0014\u0010\u0015R\u0010\u0010\u0016\u001a\u0004\u0018\u00010\u0017X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006-"}, d2 = {"Lcom/thor/app/gui/views/tachometer/EqualizeView;", "Landroid/widget/FrameLayout;", "Landroid/media/MediaPlayer$OnPreparedListener;", "Landroid/media/MediaPlayer$OnCompletionListener;", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "attrs", "Landroid/util/AttributeSet;", "(Landroid/content/Context;Landroid/util/AttributeSet;)V", "defStyleAttr", "", "(Landroid/content/Context;Landroid/util/AttributeSet;I)V", "binding", "Lcom/carsystems/thor/app/databinding/ViewEqualizeBinding;", "mMediaPlayer", "Landroid/media/MediaPlayer;", "value", "Lcom/thor/app/gui/views/tachometer/EqualizeView$OnStartProgressSound;", "mOnStartProgressSound", "setMOnStartProgressSound", "(Lcom/thor/app/gui/views/tachometer/EqualizeView$OnStartProgressSound;)V", "mResizeProgressAnimator", "Landroid/animation/ValueAnimator;", "initMediaPlayer", "", "fileName", "", "initMediaPlayerFromUrl", ImagesContract.URL, "initView", "onCompletion", "mediaPlayer", "onDetachedFromWindow", "onPrepared", "playSound", "sound", "playSoundFromUrl", "startProgressSound", "stop", "stopSound", "updateDemoUiMode", "category", "Lcom/thor/businessmodule/model/DemoSoundPackage$FuelCategory;", "OnStartProgressSound", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class EqualizeView extends FrameLayout implements MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener {
    private ViewEqualizeBinding binding;
    private MediaPlayer mMediaPlayer;
    private OnStartProgressSound mOnStartProgressSound;
    private ValueAnimator mResizeProgressAnimator;

    /* compiled from: EqualizeView.kt */
    @Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&¨\u0006\u0004"}, d2 = {"Lcom/thor/app/gui/views/tachometer/EqualizeView$OnStartProgressSound;", "", "onStart", "", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public interface OnStartProgressSound {
        void onStart();
    }

    private final void setMOnStartProgressSound(OnStartProgressSound onStartProgressSound) {
        setMOnStartProgressSound(onStartProgressSound);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public EqualizeView(Context context) {
        this(context, null);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public EqualizeView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public EqualizeView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        Intrinsics.checkNotNullParameter(context, "context");
        initView(attributeSet);
    }

    private final void initView(AttributeSet attrs) {
        LayoutInflater layoutInflater = (LayoutInflater) new WeakReference(LayoutInflater.from(getContext())).get();
        Intrinsics.checkNotNull(layoutInflater);
        ViewEqualizeBinding viewEqualizeBindingInflate = ViewEqualizeBinding.inflate(layoutInflater, this, true);
        Intrinsics.checkNotNullExpressionValue(viewEqualizeBindingInflate, "inflate(layoutInflater!!, this, true)");
        this.binding = viewEqualizeBindingInflate;
        isInEditMode();
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() throws IllegalStateException {
        super.onDetachedFromWindow();
        MediaPlayer mediaPlayer = this.mMediaPlayer;
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
        MediaPlayer mediaPlayer2 = this.mMediaPlayer;
        if (mediaPlayer2 != null) {
            mediaPlayer2.release();
        }
        this.mMediaPlayer = null;
        ValueAnimator valueAnimator = this.mResizeProgressAnimator;
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
    }

    @Override // android.media.MediaPlayer.OnPreparedListener
    public void onPrepared(MediaPlayer mediaPlayer) throws IllegalStateException {
        if (mediaPlayer != null) {
            mediaPlayer.start();
        }
        startProgressSound();
        OnStartProgressSound onStartProgressSound = this.mOnStartProgressSound;
        if (onStartProgressSound != null) {
            onStartProgressSound.onStart();
        }
        if (mediaPlayer != null) {
            mediaPlayer.setOnCompletionListener(this);
        }
    }

    @Override // android.media.MediaPlayer.OnCompletionListener
    public void onCompletion(MediaPlayer mediaPlayer) throws IllegalStateException {
        Timber.INSTANCE.i("onCompletion", new Object[0]);
        stop();
    }

    public final void playSound(final String sound) throws IllegalStateException {
        Intrinsics.checkNotNullParameter(sound, "sound");
        MediaPlayer mediaPlayer = this.mMediaPlayer;
        if (mediaPlayer != null) {
            Boolean boolValueOf = mediaPlayer != null ? Boolean.valueOf(mediaPlayer.isPlaying()) : null;
            Intrinsics.checkNotNull(boolValueOf);
            if (boolValueOf.booleanValue()) {
                stop();
                return;
            }
        }
        post(new Runnable() { // from class: com.thor.app.gui.views.tachometer.EqualizeView$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() throws IllegalStateException, IOException, SecurityException, IllegalArgumentException {
                EqualizeView.playSound$lambda$0(this.f$0, sound);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void playSound$lambda$0(EqualizeView this$0, String sound) throws IllegalStateException, IOException, SecurityException, IllegalArgumentException {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(sound, "$sound");
        this$0.initMediaPlayer(sound);
    }

    public final void playSoundFromUrl(final String sound) throws IllegalStateException {
        Intrinsics.checkNotNullParameter(sound, "sound");
        MediaPlayer mediaPlayer = this.mMediaPlayer;
        if (mediaPlayer != null) {
            Boolean boolValueOf = mediaPlayer != null ? Boolean.valueOf(mediaPlayer.isPlaying()) : null;
            Intrinsics.checkNotNull(boolValueOf);
            if (boolValueOf.booleanValue()) {
                stop();
                return;
            }
        }
        post(new Runnable() { // from class: com.thor.app.gui.views.tachometer.EqualizeView$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() throws IllegalStateException, IOException, SecurityException, IllegalArgumentException {
                EqualizeView.playSoundFromUrl$lambda$1(this.f$0, sound);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void playSoundFromUrl$lambda$1(EqualizeView this$0, String sound) throws IllegalStateException, IOException, SecurityException, IllegalArgumentException {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(sound, "$sound");
        this$0.initMediaPlayerFromUrl(sound);
    }

    public final void stopSound() throws IllegalStateException {
        MediaPlayer mediaPlayer = this.mMediaPlayer;
        if (mediaPlayer != null) {
            Boolean boolValueOf = mediaPlayer != null ? Boolean.valueOf(mediaPlayer.isPlaying()) : null;
            Intrinsics.checkNotNull(boolValueOf);
            if (boolValueOf.booleanValue()) {
                stop();
            }
        }
    }

    private final void stop() throws IllegalStateException {
        MediaPlayer mediaPlayer = this.mMediaPlayer;
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
        MediaPlayer mediaPlayer2 = this.mMediaPlayer;
        if (mediaPlayer2 != null) {
            mediaPlayer2.reset();
        }
        MediaPlayer mediaPlayer3 = this.mMediaPlayer;
        if (mediaPlayer3 != null) {
            mediaPlayer3.release();
        }
        ViewEqualizeBinding viewEqualizeBinding = null;
        this.mMediaPlayer = null;
        ValueAnimator valueAnimator = this.mResizeProgressAnimator;
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
        ViewEqualizeBinding viewEqualizeBinding2 = this.binding;
        if (viewEqualizeBinding2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            viewEqualizeBinding2 = null;
        }
        viewEqualizeBinding2.viewProgress.getLayoutParams().width = 0;
        ViewEqualizeBinding viewEqualizeBinding3 = this.binding;
        if (viewEqualizeBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            viewEqualizeBinding = viewEqualizeBinding3;
        }
        viewEqualizeBinding.viewProgress.requestLayout();
    }

    private final void initMediaPlayer(String fileName) throws IllegalStateException, IOException, SecurityException, IllegalArgumentException {
        MediaPlayer mediaPlayer = new MediaPlayer();
        this.mMediaPlayer = mediaPlayer;
        mediaPlayer.setOnPreparedListener(this);
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "context");
        int iCreateRawResIdByName = ContextKt.createRawResIdByName(context, fileName);
        Context context2 = getContext();
        Intrinsics.checkNotNullExpressionValue(context2, "context");
        Uri uriCreateUriForResource = ContextKt.createUriForResource(context2, iCreateRawResIdByName);
        MediaPlayer mediaPlayer2 = this.mMediaPlayer;
        if (mediaPlayer2 != null) {
            mediaPlayer2.setDataSource(getContext(), uriCreateUriForResource);
        }
        MediaPlayer mediaPlayer3 = this.mMediaPlayer;
        if (mediaPlayer3 != null) {
            mediaPlayer3.prepareAsync();
        }
    }

    private final void initMediaPlayerFromUrl(String url) throws IllegalStateException, IOException, SecurityException, IllegalArgumentException {
        MediaPlayer mediaPlayer = new MediaPlayer();
        this.mMediaPlayer = mediaPlayer;
        mediaPlayer.setOnPreparedListener(this);
        MediaPlayer mediaPlayer2 = this.mMediaPlayer;
        if (mediaPlayer2 != null) {
            mediaPlayer2.setDataSource(StringKt.getFullFileUrl(url));
        }
        MediaPlayer mediaPlayer3 = this.mMediaPlayer;
        if (mediaPlayer3 != null) {
            mediaPlayer3.prepareAsync();
        }
    }

    private final void startProgressSound() {
        ViewEqualizeBinding viewEqualizeBinding = this.binding;
        if (viewEqualizeBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            viewEqualizeBinding = null;
        }
        viewEqualizeBinding.viewProgress.postOnAnimation(new Runnable() { // from class: com.thor.app.gui.views.tachometer.EqualizeView$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                EqualizeView.startProgressSound$lambda$4(this.f$0);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void startProgressSound$lambda$4(final EqualizeView this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        MediaPlayer mediaPlayer = this$0.mMediaPlayer;
        if (mediaPlayer != null) {
            int duration = mediaPlayer.getDuration();
            Timber.INSTANCE.i("Sound duration: %s", Integer.valueOf(duration));
            this$0.mResizeProgressAnimator = ValueAnimator.ofInt(0, duration);
            final int width = duration / this$0.getWidth();
            ValueAnimator.AnimatorUpdateListener animatorUpdateListener = new ValueAnimator.AnimatorUpdateListener() { // from class: com.thor.app.gui.views.tachometer.EqualizeView$$ExternalSyntheticLambda1
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    EqualizeView.startProgressSound$lambda$4$lambda$3$lambda$2(width, this$0, valueAnimator);
                }
            };
            ValueAnimator valueAnimator = this$0.mResizeProgressAnimator;
            if (valueAnimator != null) {
                valueAnimator.addUpdateListener(animatorUpdateListener);
            }
            ValueAnimator valueAnimator2 = this$0.mResizeProgressAnimator;
            if (valueAnimator2 != null) {
                valueAnimator2.setDuration(duration);
            }
            ValueAnimator valueAnimator3 = this$0.mResizeProgressAnimator;
            if (valueAnimator3 != null) {
                valueAnimator3.start();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void startProgressSound$lambda$4$lambda$3$lambda$2(int i, EqualizeView this$0, ValueAnimator it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(it, "it");
        Object animatedValue = it.getAnimatedValue();
        Intrinsics.checkNotNull(animatedValue, "null cannot be cast to non-null type kotlin.Int");
        int iIntValue = ((Integer) animatedValue).intValue() / i;
        ViewEqualizeBinding viewEqualizeBinding = this$0.binding;
        ViewEqualizeBinding viewEqualizeBinding2 = null;
        if (viewEqualizeBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            viewEqualizeBinding = null;
        }
        viewEqualizeBinding.viewProgress.getLayoutParams().width = iIntValue;
        ViewEqualizeBinding viewEqualizeBinding3 = this$0.binding;
        if (viewEqualizeBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            viewEqualizeBinding2 = viewEqualizeBinding3;
        }
        viewEqualizeBinding2.viewProgress.requestLayout();
    }

    public final void updateDemoUiMode(DemoSoundPackage.FuelCategory category) {
        ViewEqualizeBinding viewEqualizeBinding = null;
        if (category == DemoSoundPackage.FuelCategory.EV) {
            ViewEqualizeBinding viewEqualizeBinding2 = this.binding;
            if (viewEqualizeBinding2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
            } else {
                viewEqualizeBinding = viewEqualizeBinding2;
            }
            viewEqualizeBinding.viewProgress.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorEcoAccent));
            return;
        }
        ViewEqualizeBinding viewEqualizeBinding3 = this.binding;
        if (viewEqualizeBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            viewEqualizeBinding = viewEqualizeBinding3;
        }
        viewEqualizeBinding.viewProgress.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
    }
}
