package com.thor.app.auto.media.library;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.media.MediaMetadataCompat;
import com.thor.app.utils.auto.PresetLoadingHelper;
import java.util.Iterator;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CancellableContinuation;
import kotlinx.coroutines.CancellableContinuationImpl;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;

/* compiled from: DeviceSoundsSource.kt */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010(\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0017\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0082@ø\u0001\u0000¢\u0006\u0002\u0010\u0011J\b\u0010\u0012\u001a\u0004\u0018\u00010\u0007J\u000f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00070\u0014H\u0096\u0002J\u0011\u0010\u0015\u001a\u00020\u0016H\u0096@ø\u0001\u0000¢\u0006\u0002\u0010\u0011J\u0019\u0010\u0017\u001a\n\u0012\u0004\u0012\u00020\u0007\u0018\u00010\u0006H\u0082@ø\u0001\u0000¢\u0006\u0002\u0010\u0011R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u001b\u0010\n\u001a\u00020\u000b8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u000e\u0010\u000f\u001a\u0004\b\f\u0010\r\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u0018"}, d2 = {"Lcom/thor/app/auto/media/library/DeviceSoundsSource;", "Lcom/thor/app/auto/media/library/AbstractMusicSource;", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "catalog", "", "Landroid/support/v4/media/MediaMetadataCompat;", "getContext", "()Landroid/content/Context;", "presetLoadingHelper", "Lcom/thor/app/utils/auto/PresetLoadingHelper;", "getPresetLoadingHelper", "()Lcom/thor/app/utils/auto/PresetLoadingHelper;", "presetLoadingHelper$delegate", "Lkotlin/Lazy;", "fetchDevicePresets", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getActivePresetMetadata", "iterator", "", "load", "", "updateCatalog", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes.dex */
public final class DeviceSoundsSource extends AbstractMusicSource {
    private List<MediaMetadataCompat> catalog;
    private final Context context;

    /* renamed from: presetLoadingHelper$delegate, reason: from kotlin metadata */
    private final Lazy presetLoadingHelper;

    /* compiled from: DeviceSoundsSource.kt */
    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    @DebugMetadata(c = "com.thor.app.auto.media.library.DeviceSoundsSource", f = "DeviceSoundsSource.kt", i = {0}, l = {26}, m = "load", n = {"this"}, s = {"L$0"})
    /* renamed from: com.thor.app.auto.media.library.DeviceSoundsSource$load$1, reason: invalid class name */
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
            return DeviceSoundsSource.this.load(this);
        }
    }

    public DeviceSoundsSource(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.presetLoadingHelper = LazyKt.lazy(new Function0<PresetLoadingHelper>() { // from class: com.thor.app.auto.media.library.DeviceSoundsSource$presetLoadingHelper$2
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final PresetLoadingHelper invoke() {
                return new PresetLoadingHelper(this.this$0.getContext());
            }
        });
        this.catalog = CollectionsKt.emptyList();
        setState(2);
    }

    public final Context getContext() {
        return this.context;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final PresetLoadingHelper getPresetLoadingHelper() {
        return (PresetLoadingHelper) this.presetLoadingHelper.getValue();
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
            boolean r0 = r5 instanceof com.thor.app.auto.media.library.DeviceSoundsSource.AnonymousClass1
            if (r0 == 0) goto L14
            r0 = r5
            com.thor.app.auto.media.library.DeviceSoundsSource$load$1 r0 = (com.thor.app.auto.media.library.DeviceSoundsSource.AnonymousClass1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L14
            int r5 = r0.label
            int r5 = r5 - r2
            r0.label = r5
            goto L19
        L14:
            com.thor.app.auto.media.library.DeviceSoundsSource$load$1 r0 = new com.thor.app.auto.media.library.DeviceSoundsSource$load$1
            r0.<init>(r5)
        L19:
            java.lang.Object r5 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L36
            if (r2 != r3) goto L2e
            java.lang.Object r0 = r0.L$0
            com.thor.app.auto.media.library.DeviceSoundsSource r0 = (com.thor.app.auto.media.library.DeviceSoundsSource) r0
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
            com.thor.app.auto.media.library.DeviceSoundsSource r5 = (com.thor.app.auto.media.library.DeviceSoundsSource) r5
            java.util.List r5 = kotlin.collections.CollectionsKt.emptyList()
            r0.catalog = r5
            r5 = 4
            r0.setState(r5)
        L62:
            kotlin.Unit r5 = kotlin.Unit.INSTANCE
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.thor.app.auto.media.library.DeviceSoundsSource.load(kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* compiled from: DeviceSoundsSource.kt */
    @Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u0003H\u008a@"}, d2 = {"<anonymous>", "", "Landroid/support/v4/media/MediaMetadataCompat;", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 8, 0}, xi = 48)
    @DebugMetadata(c = "com.thor.app.auto.media.library.DeviceSoundsSource$updateCatalog$2", f = "DeviceSoundsSource.kt", i = {}, l = {37}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.thor.app.auto.media.library.DeviceSoundsSource$updateCatalog$2, reason: invalid class name */
    static final class AnonymousClass2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super List<? extends MediaMetadataCompat>>, Object> {
        int label;

        AnonymousClass2(Continuation<? super AnonymousClass2> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return DeviceSoundsSource.this.new AnonymousClass2(continuation);
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
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                this.label = 1;
                obj = DeviceSoundsSource.this.fetchDevicePresets(this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            List<MediaMetadataCompat> list = (List) obj;
            for (MediaMetadataCompat mediaMetadataCompat : list) {
                Bundle extras = mediaMetadataCompat.getDescription().getExtras();
                if (extras != null) {
                    extras.putAll(mediaMetadataCompat.getBundle());
                }
            }
            return list;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Object updateCatalog(Continuation<? super List<MediaMetadataCompat>> continuation) {
        return BuildersKt.withContext(Dispatchers.getIO(), new AnonymousClass2(null), continuation);
    }

    public final MediaMetadataCompat getActivePresetMetadata() {
        return getPresetLoadingHelper().getActivePresetMetadata();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Object fetchDevicePresets(Continuation<? super List<MediaMetadataCompat>> continuation) {
        CancellableContinuationImpl cancellableContinuationImpl = new CancellableContinuationImpl(IntrinsicsKt.intercepted(continuation), 1);
        cancellableContinuationImpl.initCancellability();
        final CancellableContinuationImpl cancellableContinuationImpl2 = cancellableContinuationImpl;
        getPresetLoadingHelper().onLoadPresets(new Function1<List<? extends MediaMetadataCompat>, Unit>() { // from class: com.thor.app.auto.media.library.DeviceSoundsSource$fetchDevicePresets$2$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(List<? extends MediaMetadataCompat> list) {
                invoke2((List<MediaMetadataCompat>) list);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(List<MediaMetadataCompat> result) {
                Intrinsics.checkNotNullParameter(result, "result");
                CancellableContinuation<List<MediaMetadataCompat>> cancellableContinuation = cancellableContinuationImpl2;
                Result.Companion companion = Result.INSTANCE;
                cancellableContinuation.resumeWith(Result.m624constructorimpl(result));
            }
        });
        Object result = cancellableContinuationImpl.getResult();
        if (result == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            DebugProbesKt.probeCoroutineSuspended(continuation);
        }
        return result;
    }
}
