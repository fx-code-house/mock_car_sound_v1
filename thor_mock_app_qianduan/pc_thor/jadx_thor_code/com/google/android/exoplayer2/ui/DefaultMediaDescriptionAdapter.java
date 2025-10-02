package com.google.android.exoplayer2.ui;

import android.app.PendingIntent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.ui.PlayerNotificationManager;

/* loaded from: classes.dex */
public final class DefaultMediaDescriptionAdapter implements PlayerNotificationManager.MediaDescriptionAdapter {
    private final PendingIntent pendingIntent;

    public DefaultMediaDescriptionAdapter(PendingIntent pendingIntent) {
        this.pendingIntent = pendingIntent;
    }

    @Override // com.google.android.exoplayer2.ui.PlayerNotificationManager.MediaDescriptionAdapter
    public CharSequence getCurrentContentTitle(Player player) {
        CharSequence charSequence = player.getMediaMetadata().displayTitle;
        if (!TextUtils.isEmpty(charSequence)) {
            return charSequence;
        }
        CharSequence charSequence2 = player.getMediaMetadata().title;
        return charSequence2 != null ? charSequence2 : "";
    }

    @Override // com.google.android.exoplayer2.ui.PlayerNotificationManager.MediaDescriptionAdapter
    public PendingIntent createCurrentContentIntent(Player player) {
        return this.pendingIntent;
    }

    @Override // com.google.android.exoplayer2.ui.PlayerNotificationManager.MediaDescriptionAdapter
    public CharSequence getCurrentContentText(Player player) {
        CharSequence charSequence = player.getMediaMetadata().artist;
        return !TextUtils.isEmpty(charSequence) ? charSequence : player.getMediaMetadata().albumArtist;
    }

    @Override // com.google.android.exoplayer2.ui.PlayerNotificationManager.MediaDescriptionAdapter
    public Bitmap getCurrentLargeIcon(Player player, PlayerNotificationManager.BitmapCallback callback) {
        byte[] bArr = player.getMediaMetadata().artworkData;
        if (bArr == null) {
            return null;
        }
        return BitmapFactory.decodeByteArray(bArr, 0, bArr.length);
    }
}
