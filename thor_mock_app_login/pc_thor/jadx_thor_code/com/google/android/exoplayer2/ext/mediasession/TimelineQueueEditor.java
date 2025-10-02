package com.google.android.exoplayer2.ext.mediasession;

import android.os.Bundle;
import android.os.ResultReceiver;
import android.support.v4.media.MediaDescriptionCompat;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.MediaSessionCompat;
import com.google.android.exoplayer2.ControlDispatcher;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.ext.mediasession.MediaSessionConnector;
import com.google.android.exoplayer2.util.Util;
import java.util.List;

/* loaded from: classes.dex */
public final class TimelineQueueEditor implements MediaSessionConnector.QueueEditor, MediaSessionConnector.CommandReceiver {
    public static final String COMMAND_MOVE_QUEUE_ITEM = "exo_move_window";
    public static final String EXTRA_FROM_INDEX = "from_index";
    public static final String EXTRA_TO_INDEX = "to_index";
    private final MediaDescriptionEqualityChecker equalityChecker;
    private final MediaControllerCompat mediaController;
    private final MediaDescriptionConverter mediaDescriptionConverter;
    private final QueueDataAdapter queueDataAdapter;

    public interface MediaDescriptionConverter {
        MediaItem convert(MediaDescriptionCompat description);
    }

    interface MediaDescriptionEqualityChecker {
        boolean equals(MediaDescriptionCompat d1, MediaDescriptionCompat d2);
    }

    public interface QueueDataAdapter {
        void add(int position, MediaDescriptionCompat description);

        void move(int from, int to);

        void remove(int position);
    }

    public static final class MediaIdEqualityChecker implements MediaDescriptionEqualityChecker {
        @Override // com.google.android.exoplayer2.ext.mediasession.TimelineQueueEditor.MediaDescriptionEqualityChecker
        public boolean equals(MediaDescriptionCompat d1, MediaDescriptionCompat d2) {
            return Util.areEqual(d1.getMediaId(), d2.getMediaId());
        }
    }

    public TimelineQueueEditor(MediaControllerCompat mediaController, QueueDataAdapter queueDataAdapter, MediaDescriptionConverter mediaDescriptionConverter) {
        this(mediaController, queueDataAdapter, mediaDescriptionConverter, new MediaIdEqualityChecker());
    }

    public TimelineQueueEditor(MediaControllerCompat mediaController, QueueDataAdapter queueDataAdapter, MediaDescriptionConverter mediaDescriptionConverter, MediaDescriptionEqualityChecker equalityChecker) {
        this.mediaController = mediaController;
        this.queueDataAdapter = queueDataAdapter;
        this.mediaDescriptionConverter = mediaDescriptionConverter;
        this.equalityChecker = equalityChecker;
    }

    @Override // com.google.android.exoplayer2.ext.mediasession.MediaSessionConnector.QueueEditor
    public void onAddQueueItem(Player player, MediaDescriptionCompat description) {
        onAddQueueItem(player, description, player.getCurrentTimeline().getWindowCount());
    }

    @Override // com.google.android.exoplayer2.ext.mediasession.MediaSessionConnector.QueueEditor
    public void onAddQueueItem(Player player, MediaDescriptionCompat description, int index) {
        MediaItem mediaItemConvert = this.mediaDescriptionConverter.convert(description);
        if (mediaItemConvert != null) {
            this.queueDataAdapter.add(index, description);
            player.addMediaItem(index, mediaItemConvert);
        }
    }

    @Override // com.google.android.exoplayer2.ext.mediasession.MediaSessionConnector.QueueEditor
    public void onRemoveQueueItem(Player player, MediaDescriptionCompat description) {
        List<MediaSessionCompat.QueueItem> queue = this.mediaController.getQueue();
        for (int i = 0; i < queue.size(); i++) {
            if (this.equalityChecker.equals(queue.get(i).getDescription(), description)) {
                this.queueDataAdapter.remove(i);
                player.removeMediaItem(i);
                return;
            }
        }
    }

    @Override // com.google.android.exoplayer2.ext.mediasession.MediaSessionConnector.CommandReceiver
    public boolean onCommand(Player player, @Deprecated ControlDispatcher controlDispatcher, String command, Bundle extras, ResultReceiver cb) {
        if (!COMMAND_MOVE_QUEUE_ITEM.equals(command) || extras == null) {
            return false;
        }
        int i = extras.getInt(EXTRA_FROM_INDEX, -1);
        int i2 = extras.getInt(EXTRA_TO_INDEX, -1);
        if (i == -1 || i2 == -1) {
            return true;
        }
        this.queueDataAdapter.move(i, i2);
        player.moveMediaItem(i, i2);
        return true;
    }
}
