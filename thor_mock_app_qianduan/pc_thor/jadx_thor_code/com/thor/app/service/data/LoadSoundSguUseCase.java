package com.thor.app.service.data;

import android.content.Context;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.thor.app.managers.UsersManager;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: LoadSoundSguUseCase.kt */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u001b\u0010\u000b\u001a\u0004\u0018\u00010\f2\u0006\u0010\r\u001a\u00020\u000eH\u0086@ø\u0001\u0000¢\u0006\u0002\u0010\u000fR\u001b\u0010\u0005\u001a\u00020\u00068BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\t\u0010\n\u001a\u0004\b\u0007\u0010\b\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u0010"}, d2 = {"Lcom/thor/app/service/data/LoadSoundSguUseCase;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "usersManager", "Lcom/thor/app/managers/UsersManager;", "getUsersManager", "()Lcom/thor/app/managers/UsersManager;", "usersManager$delegate", "Lkotlin/Lazy;", "loadSoundSgu", "Lcom/thor/app/service/models/UploadFileGroupModel;", "sguModel", "Lcom/thor/networkmodule/model/responses/sgu/SguSoundSet;", "(Lcom/thor/networkmodule/model/responses/sgu/SguSoundSet;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class LoadSoundSguUseCase {

    /* renamed from: usersManager$delegate, reason: from kotlin metadata */
    private final Lazy usersManager;

    /* compiled from: LoadSoundSguUseCase.kt */
    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    @DebugMetadata(c = "com.thor.app.service.data.LoadSoundSguUseCase", f = "LoadSoundSguUseCase.kt", i = {0, 0, 0, 0, 0, 0}, l = {24}, m = "loadSoundSgu", n = {"this", "sguModel", "listSgu", "it", "index$iv", FirebaseAnalytics.Param.INDEX}, s = {"L$0", "L$1", "L$2", "L$5", "I$0", "I$1"})
    /* renamed from: com.thor.app.service.data.LoadSoundSguUseCase$loadSoundSgu$1, reason: invalid class name */
    static final class AnonymousClass1 extends ContinuationImpl {
        int I$0;
        int I$1;
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        Object L$4;
        Object L$5;
        int label;
        /* synthetic */ Object result;

        AnonymousClass1(Continuation<? super AnonymousClass1> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return LoadSoundSguUseCase.this.loadSoundSgu(null, this);
        }
    }

    public LoadSoundSguUseCase(final Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.usersManager = LazyKt.lazy(new Function0<UsersManager>() { // from class: com.thor.app.service.data.LoadSoundSguUseCase$usersManager$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final UsersManager invoke() {
                return UsersManager.INSTANCE.from(context);
            }
        });
    }

    private final UsersManager getUsersManager() {
        return (UsersManager) this.usersManager.getValue();
    }

    /* JADX WARN: Code restructure failed: missing block: B:49:0x00a0, code lost:
    
        r10 = ((com.thor.networkmodule.model.responses.sgu.SguSound) r10).getSoundFiles().iterator();
        r20 = r7;
        r7 = r4;
        r4 = r8;
        r8 = r20;
        r10 = r9;
        r9 = r10;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Path cross not found for [B:36:0x00f0, B:32:0x00de], limit reached: 50 */
    /* JADX WARN: Path cross not found for [B:36:0x00f0, B:34:0x00e4], limit reached: 50 */
    /* JADX WARN: Removed duplicated region for block: B:20:0x007e A[Catch: Exception -> 0x012a, TryCatch #0 {Exception -> 0x012a, blocks: (B:12:0x0049, B:30:0x00d4, B:32:0x00de, B:34:0x00e4, B:37:0x00f2, B:24:0x00a0, B:26:0x00a6, B:18:0x0078, B:20:0x007e, B:22:0x0086, B:23:0x0089, B:39:0x0114, B:42:0x0126, B:36:0x00f0, B:17:0x005f), top: B:47:0x0029 }] */
    /* JADX WARN: Removed duplicated region for block: B:26:0x00a6 A[Catch: Exception -> 0x012a, TryCatch #0 {Exception -> 0x012a, blocks: (B:12:0x0049, B:30:0x00d4, B:32:0x00de, B:34:0x00e4, B:37:0x00f2, B:24:0x00a0, B:26:0x00a6, B:18:0x0078, B:20:0x007e, B:22:0x0086, B:23:0x0089, B:39:0x0114, B:42:0x0126, B:36:0x00f0, B:17:0x005f), top: B:47:0x0029 }] */
    /* JADX WARN: Removed duplicated region for block: B:38:0x010e  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x0114 A[Catch: Exception -> 0x012a, TryCatch #0 {Exception -> 0x012a, blocks: (B:12:0x0049, B:30:0x00d4, B:32:0x00de, B:34:0x00e4, B:37:0x00f2, B:24:0x00a0, B:26:0x00a6, B:18:0x0078, B:20:0x007e, B:22:0x0086, B:23:0x0089, B:39:0x0114, B:42:0x0126, B:36:0x00f0, B:17:0x005f), top: B:47:0x0029 }] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0018  */
    /* JADX WARN: Type inference failed for: r11v6, types: [java.util.List] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:29:0x00cd -> B:30:0x00d4). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object loadSoundSgu(com.thor.networkmodule.model.responses.sgu.SguSoundSet r23, kotlin.coroutines.Continuation<? super com.thor.app.service.models.UploadFileGroupModel> r24) {
        /*
            Method dump skipped, instructions count: 325
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.thor.app.service.data.LoadSoundSguUseCase.loadSoundSgu(com.thor.networkmodule.model.responses.sgu.SguSoundSet, kotlin.coroutines.Continuation):java.lang.Object");
    }
}
