package com.thor.app.auto.media.library;

import android.os.Bundle;
import android.support.v4.media.MediaMetadataCompat;
import com.google.android.gms.actions.SearchIntents;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.markers.KMappedMarker;

/* compiled from: MusicSource.kt */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001J\u0011\u0010\u0003\u001a\u00020\u0004H¦@ø\u0001\u0000¢\u0006\u0002\u0010\u0005J\u001e\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH&J\u001c\u0010\f\u001a\u00020\r2\u0012\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u00040\u000fH&\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u0010"}, d2 = {"Lcom/thor/app/auto/media/library/MusicSource;", "", "Landroid/support/v4/media/MediaMetadataCompat;", "load", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", FirebaseAnalytics.Event.SEARCH, "", SearchIntents.EXTRA_QUERY, "", "extras", "Landroid/os/Bundle;", "whenReady", "", "performAction", "Lkotlin/Function1;", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes.dex */
public interface MusicSource extends Iterable<MediaMetadataCompat>, KMappedMarker {
    Object load(Continuation<? super Unit> continuation);

    List<MediaMetadataCompat> search(String query, Bundle extras);

    boolean whenReady(Function1<? super Boolean, Unit> performAction);
}
