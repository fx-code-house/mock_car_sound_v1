package com.thor.app.auto.media.library;

import android.content.Context;
import android.support.v4.media.MediaMetadataCompat;
import com.carsystems.thor.app.R;
import com.thor.app.auto.media.extensions.MediaMetadataCompatExtKt;
import com.thor.app.settings.Settings;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: BrowseTree.kt */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010%\n\u0002\u0010\u000e\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0019\u0010\u0012\u001a\n\u0012\u0004\u0012\u00020\r\u0018\u00010\f2\u0006\u0010\u0013\u001a\u00020\u000bH\u0086\u0002R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR \u0010\t\u001a\u0014\u0012\u0004\u0012\u00020\u000b\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\f0\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u000e\u001a\u00020\u000fX\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011¨\u0006\u0014"}, d2 = {"Lcom/thor/app/auto/media/library/BrowseTree;", "", "context", "Landroid/content/Context;", "musicSource", "Lcom/thor/app/auto/media/library/MusicSource;", "(Landroid/content/Context;Lcom/thor/app/auto/media/library/MusicSource;)V", "getContext", "()Landroid/content/Context;", "mediaIdToChildren", "", "", "", "Landroid/support/v4/media/MediaMetadataCompat;", "searchableByUnknownCaller", "", "getSearchableByUnknownCaller", "()Z", "get", "mediaId", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes.dex */
public final class BrowseTree {
    private final Context context;
    private final Map<String, List<MediaMetadataCompat>> mediaIdToChildren;
    private final boolean searchableByUnknownCaller;

    public BrowseTree(Context context, MusicSource musicSource) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(musicSource, "musicSource");
        this.context = context;
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        this.mediaIdToChildren = linkedHashMap;
        this.searchableByUnknownCaller = true;
        ArrayList arrayList = (List) linkedHashMap.get("/");
        arrayList = arrayList == null ? new ArrayList() : arrayList;
        MediaMetadataCompat.Builder builder = new MediaMetadataCompat.Builder();
        builder.putString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID, BrowseTreeKt.UAMP_DEMO_PRESETS);
        builder.putString(MediaMetadataCompat.METADATA_KEY_TITLE, context.getString(R.string.demo_presets_title));
        long j = 1;
        builder.putLong(MediaMetadataCompatExtKt.METADATA_KEY_UAMP_FLAGS, j);
        MediaMetadataCompat mediaMetadataCompatBuild = builder.build();
        MediaMetadataCompat.Builder builder2 = new MediaMetadataCompat.Builder();
        builder2.putString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID, BrowseTreeKt.UAMP_DEVICE_PRESETS);
        builder2.putString(MediaMetadataCompat.METADATA_KEY_TITLE, context.getString(R.string.device_presets_title));
        builder2.putLong(MediaMetadataCompatExtKt.METADATA_KEY_UAMP_FLAGS, j);
        MediaMetadataCompat mediaMetadataCompatBuild2 = builder2.build();
        if (Settings.INSTANCE.isAccessSession()) {
            arrayList.add(mediaMetadataCompatBuild2);
        } else {
            arrayList.add(mediaMetadataCompatBuild);
        }
        linkedHashMap.put("/", arrayList);
        for (MediaMetadataCompat mediaMetadataCompat : musicSource) {
            if (Settings.INSTANCE.isAccessSession()) {
                ArrayList arrayList2 = this.mediaIdToChildren.get(BrowseTreeKt.UAMP_DEVICE_PRESETS);
                arrayList2 = arrayList2 == null ? new ArrayList() : arrayList2;
                arrayList2.add(mediaMetadataCompat);
                this.mediaIdToChildren.put(BrowseTreeKt.UAMP_DEVICE_PRESETS, arrayList2);
            } else {
                ArrayList arrayList3 = this.mediaIdToChildren.get(BrowseTreeKt.UAMP_DEMO_PRESETS);
                arrayList3 = arrayList3 == null ? new ArrayList() : arrayList3;
                arrayList3.add(mediaMetadataCompat);
                this.mediaIdToChildren.put(BrowseTreeKt.UAMP_DEMO_PRESETS, arrayList3);
            }
        }
    }

    public final Context getContext() {
        return this.context;
    }

    public final boolean getSearchableByUnknownCaller() {
        return this.searchableByUnknownCaller;
    }

    public final List<MediaMetadataCompat> get(String mediaId) {
        Intrinsics.checkNotNullParameter(mediaId, "mediaId");
        return this.mediaIdToChildren.get(mediaId);
    }
}
