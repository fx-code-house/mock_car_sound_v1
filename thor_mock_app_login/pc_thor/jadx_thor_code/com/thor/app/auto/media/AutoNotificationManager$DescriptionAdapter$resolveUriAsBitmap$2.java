package com.thor.app.auto.media;

import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import com.bumptech.glide.Glide;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: AutoNotificationManager.kt */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "Landroid/graphics/Bitmap;", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 8, 0}, xi = 48)
@DebugMetadata(c = "com.thor.app.auto.media.AutoNotificationManager$DescriptionAdapter$resolveUriAsBitmap$2", f = "AutoNotificationManager.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
/* loaded from: classes.dex */
final class AutoNotificationManager$DescriptionAdapter$resolveUriAsBitmap$2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Bitmap>, Object> {
    final /* synthetic */ Uri $uri;
    int label;
    final /* synthetic */ AutoNotificationManager this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    AutoNotificationManager$DescriptionAdapter$resolveUriAsBitmap$2(AutoNotificationManager autoNotificationManager, Uri uri, Continuation<? super AutoNotificationManager$DescriptionAdapter$resolveUriAsBitmap$2> continuation) {
        super(2, continuation);
        this.this$0 = autoNotificationManager;
        this.$uri = uri;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new AutoNotificationManager$DescriptionAdapter$resolveUriAsBitmap$2(this.this$0, this.$uri, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Bitmap> continuation) {
        return ((AutoNotificationManager$DescriptionAdapter$resolveUriAsBitmap$2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        try {
            return Glide.with(this.this$0.context).applyDefaultRequestOptions(AutoNotificationManagerKt.glideOptions).asBitmap().load(this.$uri).submit(AutoNotificationManagerKt.NOTIFICATION_LARGE_ICON_SIZE, AutoNotificationManagerKt.NOTIFICATION_LARGE_ICON_SIZE).get();
        } catch (Exception e) {
            Log.e("Glide", e.toString());
            return null;
        }
    }
}
