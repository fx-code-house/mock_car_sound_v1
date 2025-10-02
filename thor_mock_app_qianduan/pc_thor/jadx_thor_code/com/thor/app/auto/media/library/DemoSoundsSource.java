package com.thor.app.auto.media.library;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.media.MediaMetadataCompat;
import com.google.android.exoplayer2.text.ttml.TtmlNode;
import com.google.android.gms.measurement.api.AppMeasurementSdk;
import com.google.firebase.messaging.Constants;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import com.thor.app.auto.media.extensions.MediaMetadataCompatExtKt;
import com.thor.basemodule.extensions.ContextKt;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.io.CloseableKt;
import kotlin.io.TextStreamsKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;

/* compiled from: DemoSoundsSource.kt */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010(\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001:\u0001\u0015B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u000f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00070\u000bH\u0096\u0002J\u0011\u0010\f\u001a\u00020\rH\u0096@ø\u0001\u0000¢\u0006\u0002\u0010\u000eJ\u000e\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00100\u0006H\u0002J\u0019\u0010\u0011\u001a\n\u0012\u0004\u0012\u00020\u0007\u0018\u00010\u0006H\u0082@ø\u0001\u0000¢\u0006\u0002\u0010\u000eJ\u0012\u0010\u0012\u001a\u00020\u0013*\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0010R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u0016"}, d2 = {"Lcom/thor/app/auto/media/library/DemoSoundsSource;", "Lcom/thor/app/auto/media/library/AbstractMusicSource;", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "catalog", "", "Landroid/support/v4/media/MediaMetadataCompat;", "getContext", "()Landroid/content/Context;", "iterator", "", "load", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "parseJsonDemoSounds", "Lcom/thor/app/auto/media/library/DemoSoundsSource$DemoPreset;", "updateCatalog", Constants.MessagePayloadKeys.FROM, "Landroid/support/v4/media/MediaMetadataCompat$Builder;", "jsonMusic", "DemoPreset", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes.dex */
public final class DemoSoundsSource extends AbstractMusicSource {
    private List<MediaMetadataCompat> catalog;
    private final Context context;

    /* compiled from: DemoSoundsSource.kt */
    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    @DebugMetadata(c = "com.thor.app.auto.media.library.DemoSoundsSource", f = "DemoSoundsSource.kt", i = {0}, l = {32}, m = "load", n = {"this"}, s = {"L$0"})
    /* renamed from: com.thor.app.auto.media.library.DemoSoundsSource$load$1, reason: invalid class name */
    static final class AnonymousClass1 extends ContinuationImpl {
        Object L$0;
        int label;
        /* synthetic */ Object result;

        AnonymousClass1(Continuation<? super AnonymousClass1> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return DemoSoundsSource.this.load(this);
        }
    }

    public DemoSoundsSource(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.catalog = CollectionsKt.emptyList();
        setState(2);
    }

    public final Context getContext() {
        return this.context;
    }

    @Override // com.thor.app.auto.media.library.AbstractMusicSource, java.lang.Iterable
    public Iterator<MediaMetadataCompat> iterator() {
        return this.catalog.iterator();
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0014  */
    @Override // com.thor.app.auto.media.library.MusicSource
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public java.lang.Object load(kotlin.coroutines.Continuation<? super kotlin.Unit> r5) {
        /*
            r4 = this;
            boolean r0 = r5 instanceof com.thor.app.auto.media.library.DemoSoundsSource.AnonymousClass1
            if (r0 == 0) goto L14
            r0 = r5
            com.thor.app.auto.media.library.DemoSoundsSource$load$1 r0 = (com.thor.app.auto.media.library.DemoSoundsSource.AnonymousClass1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L14
            int r5 = r0.label
            int r5 = r5 - r2
            r0.label = r5
            goto L19
        L14:
            com.thor.app.auto.media.library.DemoSoundsSource$load$1 r0 = new com.thor.app.auto.media.library.DemoSoundsSource$load$1
            r0.<init>(r5)
        L19:
            java.lang.Object r5 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L36
            if (r2 != r3) goto L2e
            java.lang.Object r0 = r0.L$0
            com.thor.app.auto.media.library.DemoSoundsSource r0 = (com.thor.app.auto.media.library.DemoSoundsSource) r0
            kotlin.ResultKt.throwOnFailure(r5)
            goto L45
        L2e:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r0)
            throw r5
        L36:
            kotlin.ResultKt.throwOnFailure(r5)
            r0.L$0 = r4
            r0.label = r3
            java.lang.Object r5 = r4.updateCatalog(r0)
            if (r5 != r1) goto L44
            return r1
        L44:
            r0 = r4
        L45:
            java.util.List r5 = (java.util.List) r5
            if (r5 == 0) goto L52
            r0.catalog = r5
            r5 = 3
            r0.setState(r5)
            kotlin.Unit r5 = kotlin.Unit.INSTANCE
            goto L53
        L52:
            r5 = 0
        L53:
            if (r5 != 0) goto L62
            r5 = r0
            com.thor.app.auto.media.library.DemoSoundsSource r5 = (com.thor.app.auto.media.library.DemoSoundsSource) r5
            java.util.List r5 = kotlin.collections.CollectionsKt.emptyList()
            r0.catalog = r5
            r5 = 4
            r0.setState(r5)
        L62:
            kotlin.Unit r5 = kotlin.Unit.INSTANCE
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.thor.app.auto.media.library.DemoSoundsSource.load(kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* compiled from: DemoSoundsSource.kt */
    @Metadata(d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\u0010\u0000\u001a\u0012\u0012\f\u0012\n \u0003*\u0004\u0018\u00010\u00020\u0002\u0018\u00010\u0001*\u00020\u0004H\u008a@"}, d2 = {"<anonymous>", "", "Landroid/support/v4/media/MediaMetadataCompat;", "kotlin.jvm.PlatformType", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 8, 0}, xi = 48)
    @DebugMetadata(c = "com.thor.app.auto.media.library.DemoSoundsSource$updateCatalog$2", f = "DemoSoundsSource.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.thor.app.auto.media.library.DemoSoundsSource$updateCatalog$2, reason: invalid class name */
    static final class AnonymousClass2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super List<? extends MediaMetadataCompat>>, Object> {
        int label;

        AnonymousClass2(Continuation<? super AnonymousClass2> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return DemoSoundsSource.this.new AnonymousClass2(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public /* bridge */ /* synthetic */ Object invoke(CoroutineScope coroutineScope, Continuation<? super List<? extends MediaMetadataCompat>> continuation) {
            return invoke2(coroutineScope, (Continuation<? super List<MediaMetadataCompat>>) continuation);
        }

        /* renamed from: invoke, reason: avoid collision after fix types in other method */
        public final Object invoke2(CoroutineScope coroutineScope, Continuation<? super List<MediaMetadataCompat>> continuation) {
            return ((AnonymousClass2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws JsonSyntaxException, IOException {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label == 0) {
                ResultKt.throwOnFailure(obj);
                try {
                    List<DemoPreset> jsonDemoSounds = DemoSoundsSource.this.parseJsonDemoSounds();
                    DemoSoundsSource demoSoundsSource = DemoSoundsSource.this;
                    ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(jsonDemoSounds, 10));
                    for (DemoPreset demoPreset : jsonDemoSounds) {
                        Bitmap bitmapDecodeResource = BitmapFactory.decodeResource(demoSoundsSource.getContext().getResources(), ContextKt.createDrawableResIdByName(demoSoundsSource.getContext(), demoPreset.getDrawable()));
                        MediaMetadataCompat.Builder builderFrom = demoSoundsSource.from(new MediaMetadataCompat.Builder(), demoPreset);
                        builderFrom.putBitmap(MediaMetadataCompat.METADATA_KEY_ALBUM_ART, bitmapDecodeResource);
                        arrayList.add(builderFrom.build());
                    }
                    List<MediaMetadataCompat> list = CollectionsKt.toList(arrayList);
                    for (MediaMetadataCompat mediaMetadataCompat : list) {
                        Bundle extras = mediaMetadataCompat.getDescription().getExtras();
                        if (extras != null) {
                            extras.putAll(mediaMetadataCompat.getBundle());
                        }
                    }
                    return list;
                } catch (IOException unused) {
                    return null;
                }
            }
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Object updateCatalog(Continuation<? super List<MediaMetadataCompat>> continuation) {
        return BuildersKt.withContext(Dispatchers.getIO(), new AnonymousClass2(null), continuation);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final List<DemoPreset> parseJsonDemoSounds() throws JsonSyntaxException, IOException {
        InputStream inputStreamOpen = this.context.getAssets().open(com.thor.businessmodule.settings.Constants.NEW_DEMO_SOUNDS_JSON);
        Intrinsics.checkNotNullExpressionValue(inputStreamOpen, "context.assets.open(demoSoundsFileName)");
        Reader inputStreamReader = new InputStreamReader(inputStreamOpen, Charsets.UTF_8);
        BufferedReader bufferedReader = inputStreamReader instanceof BufferedReader ? (BufferedReader) inputStreamReader : new BufferedReader(inputStreamReader, 8192);
        try {
            String text = TextStreamsKt.readText(bufferedReader);
            CloseableKt.closeFinally(bufferedReader, null);
            Object objFromJson = new Gson().fromJson(text, new TypeToken<List<? extends DemoPreset>>() { // from class: com.thor.app.auto.media.library.DemoSoundsSource$parseJsonDemoSounds$soundType$1
            }.getType());
            Intrinsics.checkNotNullExpressionValue(objFromJson, "Gson().fromJson(jsonString, soundType)");
            return (List) objFromJson;
        } finally {
        }
    }

    public final MediaMetadataCompat.Builder from(MediaMetadataCompat.Builder builder, DemoPreset jsonMusic) {
        Intrinsics.checkNotNullParameter(builder, "<this>");
        Intrinsics.checkNotNullParameter(jsonMusic, "jsonMusic");
        long millis = TimeUnit.SECONDS.toMillis(jsonMusic.getDuration());
        Bitmap bitmapDecodeResource = BitmapFactory.decodeResource(this.context.getResources(), ContextKt.createDrawableResIdByName(this.context, jsonMusic.getDrawable()));
        Uri uriCreateUriForResource = ContextKt.createUriForResource(this.context, ContextKt.createRawResIdByName(this.context, jsonMusic.getTrack()));
        builder.putString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID, jsonMusic.getId());
        builder.putString(MediaMetadataCompat.METADATA_KEY_TITLE, jsonMusic.getName());
        builder.putString(MediaMetadataCompat.METADATA_KEY_ALBUM, jsonMusic.getAlbum());
        builder.putLong(MediaMetadataCompat.METADATA_KEY_DURATION, millis);
        builder.putString(MediaMetadataCompat.METADATA_KEY_MEDIA_URI, uriCreateUriForResource.toString());
        builder.putBitmap(MediaMetadataCompat.METADATA_KEY_ALBUM_ART, bitmapDecodeResource);
        builder.putLong(MediaMetadataCompat.METADATA_KEY_TRACK_NUMBER, jsonMusic.getTrackNumber());
        builder.putLong(MediaMetadataCompat.METADATA_KEY_NUM_TRACKS, jsonMusic.getTotalTrackCount());
        builder.putLong(MediaMetadataCompatExtKt.METADATA_KEY_UAMP_FLAGS, 2);
        builder.putString(MediaMetadataCompat.METADATA_KEY_DISPLAY_DESCRIPTION, jsonMusic.getAlbum());
        builder.putLong(MediaMetadataCompat.METADATA_KEY_DOWNLOAD_STATUS, 2L);
        return builder;
    }

    /* compiled from: DemoSoundsSource.kt */
    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0010\t\n\u0002\b\u0014\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001e\u0010\t\u001a\u00020\u00048\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0006\"\u0004\b\u000b\u0010\bR\u001a\u0010\f\u001a\u00020\rX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u001a\u0010\u0012\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0006\"\u0004\b\u0014\u0010\bR\u001a\u0010\u0015\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0006\"\u0004\b\u0017\u0010\bR\u001a\u0010\u0018\u001a\u00020\rX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u000f\"\u0004\b\u001a\u0010\u0011R\u001a\u0010\u001b\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\u0006\"\u0004\b\u001d\u0010\bR\u001a\u0010\u001e\u001a\u00020\rX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001f\u0010\u000f\"\u0004\b \u0010\u0011¨\u0006!"}, d2 = {"Lcom/thor/app/auto/media/library/DemoSoundsSource$DemoPreset;", "", "()V", "album", "", "getAlbum", "()Ljava/lang/String;", "setAlbum", "(Ljava/lang/String;)V", "drawable", "getDrawable", "setDrawable", "duration", "", "getDuration", "()J", "setDuration", "(J)V", TtmlNode.ATTR_ID, "getId", "setId", AppMeasurementSdk.ConditionalUserProperty.NAME, "getName", "setName", "totalTrackCount", "getTotalTrackCount", "setTotalTrackCount", "track", "getTrack", "setTrack", "trackNumber", "getTrackNumber", "setTrackNumber", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class DemoPreset {
        private long totalTrackCount;
        private long trackNumber;

        @SerializedName("drawable_name")
        private String drawable = "";
        private String id = "";
        private String name = "";
        private String album = "";
        private String track = "";
        private long duration = -1;

        public final String getDrawable() {
            return this.drawable;
        }

        public final void setDrawable(String str) {
            Intrinsics.checkNotNullParameter(str, "<set-?>");
            this.drawable = str;
        }

        public final String getId() {
            return this.id;
        }

        public final void setId(String str) {
            Intrinsics.checkNotNullParameter(str, "<set-?>");
            this.id = str;
        }

        public final String getName() {
            return this.name;
        }

        public final void setName(String str) {
            Intrinsics.checkNotNullParameter(str, "<set-?>");
            this.name = str;
        }

        public final String getAlbum() {
            return this.album;
        }

        public final void setAlbum(String str) {
            Intrinsics.checkNotNullParameter(str, "<set-?>");
            this.album = str;
        }

        public final String getTrack() {
            return this.track;
        }

        public final void setTrack(String str) {
            Intrinsics.checkNotNullParameter(str, "<set-?>");
            this.track = str;
        }

        public final long getTrackNumber() {
            return this.trackNumber;
        }

        public final void setTrackNumber(long j) {
            this.trackNumber = j;
        }

        public final long getTotalTrackCount() {
            return this.totalTrackCount;
        }

        public final void setTotalTrackCount(long j) {
            this.totalTrackCount = j;
        }

        public final long getDuration() {
            return this.duration;
        }

        public final void setDuration(long j) {
            this.duration = j;
        }
    }
}
