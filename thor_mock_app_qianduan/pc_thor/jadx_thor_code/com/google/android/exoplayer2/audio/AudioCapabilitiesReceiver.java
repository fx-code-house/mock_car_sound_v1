package com.google.android.exoplayer2.audio;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;

/* loaded from: classes.dex */
public final class AudioCapabilitiesReceiver {
    AudioCapabilities audioCapabilities;
    private final Context context;
    private final ExternalSurroundSoundSettingObserver externalSurroundSoundSettingObserver;
    private final Handler handler;
    private final Listener listener;
    private final BroadcastReceiver receiver;
    private boolean registered;

    public interface Listener {
        void onAudioCapabilitiesChanged(AudioCapabilities audioCapabilities);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public AudioCapabilitiesReceiver(Context context, Listener listener) {
        Context applicationContext = context.getApplicationContext();
        this.context = applicationContext;
        this.listener = (Listener) Assertions.checkNotNull(listener);
        Handler handlerCreateHandlerForCurrentOrMainLooper = Util.createHandlerForCurrentOrMainLooper();
        this.handler = handlerCreateHandlerForCurrentOrMainLooper;
        this.receiver = Util.SDK_INT >= 21 ? new HdmiAudioPlugBroadcastReceiver() : null;
        Uri externalSurroundSoundGlobalSettingUri = AudioCapabilities.getExternalSurroundSoundGlobalSettingUri();
        this.externalSurroundSoundSettingObserver = externalSurroundSoundGlobalSettingUri != null ? new ExternalSurroundSoundSettingObserver(handlerCreateHandlerForCurrentOrMainLooper, applicationContext.getContentResolver(), externalSurroundSoundGlobalSettingUri) : null;
    }

    public AudioCapabilities register() {
        if (this.registered) {
            return (AudioCapabilities) Assertions.checkNotNull(this.audioCapabilities);
        }
        this.registered = true;
        ExternalSurroundSoundSettingObserver externalSurroundSoundSettingObserver = this.externalSurroundSoundSettingObserver;
        if (externalSurroundSoundSettingObserver != null) {
            externalSurroundSoundSettingObserver.register();
        }
        Intent intentRegisterReceiver = null;
        if (this.receiver != null) {
            intentRegisterReceiver = this.context.registerReceiver(this.receiver, new IntentFilter("android.media.action.HDMI_AUDIO_PLUG"), null, this.handler);
        }
        AudioCapabilities capabilities = AudioCapabilities.getCapabilities(this.context, intentRegisterReceiver);
        this.audioCapabilities = capabilities;
        return capabilities;
    }

    public void unregister() {
        if (this.registered) {
            this.audioCapabilities = null;
            BroadcastReceiver broadcastReceiver = this.receiver;
            if (broadcastReceiver != null) {
                this.context.unregisterReceiver(broadcastReceiver);
            }
            ExternalSurroundSoundSettingObserver externalSurroundSoundSettingObserver = this.externalSurroundSoundSettingObserver;
            if (externalSurroundSoundSettingObserver != null) {
                externalSurroundSoundSettingObserver.unregister();
            }
            this.registered = false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onNewAudioCapabilities(AudioCapabilities newAudioCapabilities) {
        if (!this.registered || newAudioCapabilities.equals(this.audioCapabilities)) {
            return;
        }
        this.audioCapabilities = newAudioCapabilities;
        this.listener.onAudioCapabilitiesChanged(newAudioCapabilities);
    }

    private final class HdmiAudioPlugBroadcastReceiver extends BroadcastReceiver {
        private HdmiAudioPlugBroadcastReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (isInitialStickyBroadcast()) {
                return;
            }
            AudioCapabilitiesReceiver.this.onNewAudioCapabilities(AudioCapabilities.getCapabilities(context, intent));
        }
    }

    private final class ExternalSurroundSoundSettingObserver extends ContentObserver {
        private final ContentResolver resolver;
        private final Uri settingUri;

        public ExternalSurroundSoundSettingObserver(Handler handler, ContentResolver resolver, Uri settingUri) {
            super(handler);
            this.resolver = resolver;
            this.settingUri = settingUri;
        }

        public void register() {
            this.resolver.registerContentObserver(this.settingUri, false, this);
        }

        public void unregister() {
            this.resolver.unregisterContentObserver(this);
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean selfChange) {
            AudioCapabilitiesReceiver audioCapabilitiesReceiver = AudioCapabilitiesReceiver.this;
            audioCapabilitiesReceiver.onNewAudioCapabilities(AudioCapabilities.getCapabilities(audioCapabilitiesReceiver.context));
        }
    }
}
