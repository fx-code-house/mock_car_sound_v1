package com.thor.app.gui.widget;

import android.animation.LayoutTransition;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import androidx.databinding.DataBindingUtil;
import com.carsystems.thor.app.R;
import com.carsystems.thor.app.databinding.WidgetToolbarBinding;
import com.thor.app.bus.events.BluetoothDeviceConnectionChangedEvent;
import com.thor.app.databinding.viewmodels.ToolbarWidgetViewModel;
import com.thor.app.managers.BleManager;
import com.thor.app.settings.Settings;
import java.lang.ref.WeakReference;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import timber.log.Timber;

/* loaded from: classes3.dex */
public class ToolbarWidget extends FrameLayout {
    private WidgetToolbarBinding binding;

    public ToolbarWidget(Context context) {
        this(context, null);
    }

    public ToolbarWidget(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ToolbarWidget(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(attrs);
    }

    private void initialize(AttributeSet attrs) {
        setSaveEnabled(true);
        setLayoutTransition(new LayoutTransition());
        WidgetToolbarBinding widgetToolbarBinding = (WidgetToolbarBinding) DataBindingUtil.inflate((LayoutInflater) new WeakReference(LayoutInflater.from(getContext())).get(), R.layout.widget_toolbar, this, true);
        this.binding = widgetToolbarBinding;
        widgetToolbarBinding.setModel(new ToolbarWidgetViewModel());
        this.binding.getModel().getIsBluetoothConnected().set(BleManager.from(getContext()).isBleEnabledAndDeviceConnected());
        this.binding.imageToolbarHome.setVisibility(8);
        if (Settings.getSelectedServer() == 1092) {
            this.binding.viewDevServerMarker.setVisibility(0);
        }
        TypedArray typedArrayObtainStyledAttributes = getContext().obtainStyledAttributes(attrs, new int[]{android.R.attr.text}, 0, 0);
        try {
            setTitle(typedArrayObtainStyledAttributes.getString(0));
            typedArrayObtainStyledAttributes.recycle();
            isInEditMode();
        } catch (Throwable th) {
            typedArrayObtainStyledAttributes.recycle();
            throw th;
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() throws SecurityException {
        super.onAttachedToWindow();
        EventBus.getDefault().register(this);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        EventBus.getDefault().unregister(this);
    }

    public void setTitle(String title) {
        this.binding.textToolbarTitle.setText(title);
    }

    public void setTitle(int resId) {
        this.binding.textToolbarTitle.setText(resId);
    }

    public void setHomeImageDrawable(Drawable drawable) {
        this.binding.imageToolbarHome.setImageDrawable(drawable);
    }

    public void setHomeImageResource(int resId) {
        this.binding.imageToolbarHome.setImageResource(resId);
    }

    public void setDividerVisibility(boolean boo) {
        this.binding.viewToolbarDivider.setVisibility(boo ? 0 : 8);
    }

    public void setHomeButtonVisibility(boolean boo) {
        this.binding.imageToolbarHome.setVisibility(boo ? 0 : 4);
    }

    public void setHomeOnClickListener(View.OnClickListener listener) {
        setHomeButtonVisibility(listener != null);
        this.binding.imageToolbarHome.setOnClickListener(listener);
    }

    public void enableBluetoothIndicator(Boolean isEnabled) {
        this.binding.getModel().getIsBluetoothIndicator().set(isEnabled.booleanValue());
    }

    public void enableNotificationDeleteBtn(Boolean isEnabled) {
        this.binding.getModel().getIsNotificationDeleteBtn().set(isEnabled.booleanValue());
    }

    public void setDeleteBtnClickListener(View.OnClickListener listener) {
        this.binding.btnNotificationsDelete.setOnClickListener(listener);
    }

    @Subscribe
    public void onMessageEvent(final BluetoothDeviceConnectionChangedEvent event) {
        Timber.i("onMessageEvent: %s", event);
        this.binding.getModel().getIsBluetoothConnected().set(event.isConnected());
    }
}
