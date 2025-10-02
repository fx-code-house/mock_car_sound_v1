package com.thor.app.auto.media;

import android.net.Uri;
import com.google.android.exoplayer2.ui.PlayerNotificationManager;
import com.thor.app.auto.media.AutoNotificationManager;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: AutoNotificationManager.kt */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 8, 0}, xi = 48)
@DebugMetadata(c = "com.thor.app.auto.media.AutoNotificationManager$DescriptionAdapter$getCurrentLargeIcon$1", f = "AutoNotificationManager.kt", i = {}, l = {107}, m = "invokeSuspend", n = {}, s = {})
/* loaded from: classes.dex */
final class AutoNotificationManager$DescriptionAdapter$getCurrentLargeIcon$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    final /* synthetic */ PlayerNotificationManager.BitmapCallback $callback;
    final /* synthetic */ Uri $iconUri;
    Object L$0;
    int label;
    final /* synthetic */ AutoNotificationManager.DescriptionAdapter this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    AutoNotificationManager$DescriptionAdapter$getCurrentLargeIcon$1(AutoNotificationManager.DescriptionAdapter descriptionAdapter, Uri uri, PlayerNotificationManager.BitmapCallback bitmapCallback, Continuation<? super AutoNotificationManager$DescriptionAdapter$getCurrentLargeIcon$1> continuation) {
        super(2, continuation);
        this.this$0 = descriptionAdapter;
        this.$iconUri = uri;
        this.$callback = bitmapCallback;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new AutoNotificationManager$DescriptionAdapter$getCurrentLargeIcon$1(this.this$0, this.$iconUri, this.$callback, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((AutoNotificationManager$DescriptionAdapter$getCurrentLargeIcon$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x0043  */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r5) {
        /*
            r4 = this;
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r1 = r4.label
            r2 = 1
            if (r1 == 0) goto L1b
            if (r1 != r2) goto L13
            java.lang.Object r0 = r4.L$0
            com.thor.app.auto.media.AutoNotificationManager$DescriptionAdapter r0 = (com.thor.app.auto.media.AutoNotificationManager.DescriptionAdapter) r0
            kotlin.ResultKt.throwOnFailure(r5)
            goto L31
        L13:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r0)
            throw r5
        L1b:
            kotlin.ResultKt.throwOnFailure(r5)
            com.thor.app.auto.media.AutoNotificationManager$DescriptionAdapter r5 = r4.this$0
            android.net.Uri r1 = r4.$iconUri
            if (r1 == 0) goto L37
            r4.L$0 = r5
            r4.label = r2
            java.lang.Object r1 = com.thor.app.auto.media.AutoNotificationManager.DescriptionAdapter.access$resolveUriAsBitmap(r5, r1, r4)
            if (r1 != r0) goto L2f
            return r0
        L2f:
            r0 = r5
            r5 = r1
        L31:
            android.graphics.Bitmap r5 = (android.graphics.Bitmap) r5
            r3 = r0
            r0 = r5
            r5 = r3
            goto L38
        L37:
            r0 = 0
        L38:
            r5.setCurrentBitmap(r0)
            com.thor.app.auto.media.AutoNotificationManager$DescriptionAdapter r5 = r4.this$0
            android.graphics.Bitmap r5 = r5.getCurrentBitmap()
            if (r5 == 0) goto L48
            com.google.android.exoplayer2.ui.PlayerNotificationManager$BitmapCallback r0 = r4.$callback
            r0.onBitmap(r5)
        L48:
            kotlin.Unit r5 = kotlin.Unit.INSTANCE
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.thor.app.auto.media.AutoNotificationManager$DescriptionAdapter$getCurrentLargeIcon$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
