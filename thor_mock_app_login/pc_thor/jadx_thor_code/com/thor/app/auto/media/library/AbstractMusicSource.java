package com.thor.app.auto.media.library;

import android.os.Bundle;
import android.support.v4.media.MediaMetadataCompat;
import android.util.Log;
import com.google.android.gms.actions.SearchIntents;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.thor.app.auto.media.extensions.JavaLangExtKt;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

/* compiled from: MusicSource.kt */
@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001e\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00160\u00152\u0006\u0010\u0017\u001a\u00020\u00042\u0006\u0010\u0018\u001a\u00020\u0019H\u0016J\u001c\u0010\u001a\u001a\u00020\n2\u0012\u0010\u001b\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u000b0\tH\u0016R\u0014\u0010\u0003\u001a\u00020\u00048BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R \u0010\u0007\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u000b0\t0\bX\u0082\u0004¢\u0006\u0002\n\u0000R*\u0010\u000e\u001a\u00020\r2\u0006\u0010\f\u001a\u00020\r@FX\u0086\u000e¢\u0006\u0014\n\u0000\u0012\u0004\b\u000f\u0010\u0002\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013¨\u0006\u001c"}, d2 = {"Lcom/thor/app/auto/media/library/AbstractMusicSource;", "Lcom/thor/app/auto/media/library/MusicSource;", "()V", "EXTRA_MEDIA_GENRE", "", "getEXTRA_MEDIA_GENRE", "()Ljava/lang/String;", "onReadyListeners", "", "Lkotlin/Function1;", "", "", "value", "", "state", "getState$annotations", "getState", "()I", "setState", "(I)V", FirebaseAnalytics.Event.SEARCH, "", "Landroid/support/v4/media/MediaMetadataCompat;", SearchIntents.EXTRA_QUERY, "extras", "Landroid/os/Bundle;", "whenReady", "performAction", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes.dex */
public abstract class AbstractMusicSource implements MusicSource {
    private int state = 1;
    private final List<Function1<Boolean, Unit>> onReadyListeners = new ArrayList();

    private final String getEXTRA_MEDIA_GENRE() {
        return "android.intent.extra.genre";
    }

    public static /* synthetic */ void getState$annotations() {
    }

    @Override // java.lang.Iterable
    public Iterator<MediaMetadataCompat> iterator() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    public final int getState() {
        return this.state;
    }

    public final void setState(int i) {
        if (i == 3 || i == 4) {
            synchronized (this.onReadyListeners) {
                this.state = i;
                Iterator<T> it = this.onReadyListeners.iterator();
                while (it.hasNext()) {
                    ((Function1) it.next()).invoke(Boolean.valueOf(this.state == 3));
                }
                Unit unit = Unit.INSTANCE;
            }
            return;
        }
        this.state = i;
    }

    @Override // com.thor.app.auto.media.library.MusicSource
    public boolean whenReady(Function1<? super Boolean, Unit> performAction) {
        Intrinsics.checkNotNullParameter(performAction, "performAction");
        int i = this.state;
        if (i == 1 || i == 2) {
            this.onReadyListeners.add(performAction);
            return false;
        }
        performAction.invoke(Boolean.valueOf(i != 4));
        return true;
    }

    @Override // com.thor.app.auto.media.library.MusicSource
    public List<MediaMetadataCompat> search(String query, Bundle extras) {
        ArrayList arrayListEmptyList;
        Intrinsics.checkNotNullParameter(query, "query");
        Intrinsics.checkNotNullParameter(extras, "extras");
        Object obj = extras.get("android.intent.extra.focus");
        if (Intrinsics.areEqual(obj, "vnd.android.cursor.item/genre")) {
            Object obj2 = extras.get(getEXTRA_MEDIA_GENRE());
            Log.d("MusicSource", "Focused genre search: '" + obj2 + "'");
            ArrayList arrayList = new ArrayList();
            for (MediaMetadataCompat mediaMetadataCompat : this) {
                if (Intrinsics.areEqual(mediaMetadataCompat.getString(MediaMetadataCompat.METADATA_KEY_GENRE), obj2)) {
                    arrayList.add(mediaMetadataCompat);
                }
            }
            arrayListEmptyList = arrayList;
        } else if (Intrinsics.areEqual(obj, "vnd.android.cursor.item/artist")) {
            Object obj3 = extras.get("android.intent.extra.artist");
            Log.d("MusicSource", "Focused artist search: '" + obj3 + "'");
            ArrayList arrayList2 = new ArrayList();
            for (MediaMetadataCompat mediaMetadataCompat2 : this) {
                MediaMetadataCompat mediaMetadataCompat3 = mediaMetadataCompat2;
                if (Intrinsics.areEqual(mediaMetadataCompat3.getString(MediaMetadataCompat.METADATA_KEY_ARTIST), obj3) || Intrinsics.areEqual(mediaMetadataCompat3.getString(MediaMetadataCompat.METADATA_KEY_ALBUM_ARTIST), obj3)) {
                    arrayList2.add(mediaMetadataCompat2);
                }
            }
            arrayListEmptyList = arrayList2;
        } else if (Intrinsics.areEqual(obj, "vnd.android.cursor.item/album")) {
            Object obj4 = extras.get("android.intent.extra.artist");
            Object obj5 = extras.get("android.intent.extra.album");
            Log.d("MusicSource", "Focused album search: album='" + obj5 + "' artist='" + obj4);
            ArrayList arrayList3 = new ArrayList();
            for (MediaMetadataCompat mediaMetadataCompat4 : this) {
                MediaMetadataCompat mediaMetadataCompat5 = mediaMetadataCompat4;
                if ((Intrinsics.areEqual(mediaMetadataCompat5.getString(MediaMetadataCompat.METADATA_KEY_ARTIST), obj4) || Intrinsics.areEqual(mediaMetadataCompat5.getString(MediaMetadataCompat.METADATA_KEY_ALBUM_ARTIST), obj4)) && Intrinsics.areEqual(mediaMetadataCompat5.getString(MediaMetadataCompat.METADATA_KEY_ALBUM), obj5)) {
                    arrayList3.add(mediaMetadataCompat4);
                }
            }
            arrayListEmptyList = arrayList3;
        } else if (Intrinsics.areEqual(obj, "vnd.android.cursor.item/audio")) {
            Object obj6 = extras.get("android.intent.extra.title");
            Object obj7 = extras.get("android.intent.extra.album");
            Object obj8 = extras.get("android.intent.extra.artist");
            Log.d("MusicSource", "Focused media search: title='" + obj6 + "' album='" + obj7 + "' artist='" + obj8);
            ArrayList arrayList4 = new ArrayList();
            for (MediaMetadataCompat mediaMetadataCompat6 : this) {
                MediaMetadataCompat mediaMetadataCompat7 = mediaMetadataCompat6;
                if ((Intrinsics.areEqual(mediaMetadataCompat7.getString(MediaMetadataCompat.METADATA_KEY_ARTIST), obj8) || Intrinsics.areEqual(mediaMetadataCompat7.getString(MediaMetadataCompat.METADATA_KEY_ALBUM_ARTIST), obj8)) && Intrinsics.areEqual(mediaMetadataCompat7.getString(MediaMetadataCompat.METADATA_KEY_ALBUM), obj7) && Intrinsics.areEqual(mediaMetadataCompat7.getString(MediaMetadataCompat.METADATA_KEY_TITLE), obj6)) {
                    arrayList4.add(mediaMetadataCompat6);
                }
            }
            arrayListEmptyList = arrayList4;
        } else {
            arrayListEmptyList = CollectionsKt.emptyList();
        }
        if (!arrayListEmptyList.isEmpty()) {
            return arrayListEmptyList;
        }
        if (!StringsKt.isBlank(query)) {
            Log.d("MusicSource", "Unfocused search for '" + query + "'");
            ArrayList arrayList5 = new ArrayList();
            for (MediaMetadataCompat mediaMetadataCompat8 : this) {
                MediaMetadataCompat mediaMetadataCompat9 = mediaMetadataCompat8;
                if (JavaLangExtKt.containsCaseInsensitive(mediaMetadataCompat9.getString(MediaMetadataCompat.METADATA_KEY_TITLE), query) || JavaLangExtKt.containsCaseInsensitive(mediaMetadataCompat9.getString(MediaMetadataCompat.METADATA_KEY_GENRE), query)) {
                    arrayList5.add(mediaMetadataCompat8);
                }
            }
            return arrayList5;
        }
        Log.d("MusicSource", "Unfocused search without keyword");
        return CollectionsKt.shuffled(this);
    }
}
