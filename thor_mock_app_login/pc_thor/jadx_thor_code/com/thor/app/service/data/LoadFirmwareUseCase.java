package com.thor.app.service.data;

import android.content.Context;
import com.thor.app.managers.UsersManager;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: LoadFirmwareUseCase.kt */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u001b\u0010\u000b\u001a\u0004\u0018\u00010\f2\u0006\u0010\r\u001a\u00020\u000eH\u0086@ø\u0001\u0000¢\u0006\u0002\u0010\u000fR\u001b\u0010\u0005\u001a\u00020\u00068BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\t\u0010\n\u001a\u0004\b\u0007\u0010\b\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u0010"}, d2 = {"Lcom/thor/app/service/data/LoadFirmwareUseCase;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "usersManager", "Lcom/thor/app/managers/UsersManager;", "getUsersManager", "()Lcom/thor/app/managers/UsersManager;", "usersManager$delegate", "Lkotlin/Lazy;", "loadFirmware", "Lcom/thor/app/service/models/UploadFileGroupModel;", "fwProfile", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class LoadFirmwareUseCase {

    /* renamed from: usersManager$delegate, reason: from kotlin metadata */
    private final Lazy usersManager;

    /* compiled from: LoadFirmwareUseCase.kt */
    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    @DebugMetadata(c = "com.thor.app.service.data.LoadFirmwareUseCase", f = "LoadFirmwareUseCase.kt", i = {0}, l = {19}, m = "loadFirmware", n = {"listFW"}, s = {"L$0"})
    /* renamed from: com.thor.app.service.data.LoadFirmwareUseCase$loadFirmware$1, reason: invalid class name */
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
            return LoadFirmwareUseCase.this.loadFirmware(null, this);
        }
    }

    public LoadFirmwareUseCase(final Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.usersManager = LazyKt.lazy(new Function0<UsersManager>() { // from class: com.thor.app.service.data.LoadFirmwareUseCase$usersManager$2
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

    /* JADX WARN: Removed duplicated region for block: B:27:0x006e A[Catch: Exception -> 0x0090, TryCatch #0 {Exception -> 0x0090, blocks: (B:12:0x002a, B:21:0x0052, B:23:0x005c, B:25:0x0062, B:28:0x0071, B:27:0x006e, B:17:0x0039), top: B:32:0x0022 }] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0014  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object loadFirmware(java.lang.String r8, kotlin.coroutines.Continuation<? super com.thor.app.service.models.UploadFileGroupModel> r9) {
        /*
            r7 = this;
            boolean r0 = r9 instanceof com.thor.app.service.data.LoadFirmwareUseCase.AnonymousClass1
            if (r0 == 0) goto L14
            r0 = r9
            com.thor.app.service.data.LoadFirmwareUseCase$loadFirmware$1 r0 = (com.thor.app.service.data.LoadFirmwareUseCase.AnonymousClass1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L14
            int r9 = r0.label
            int r9 = r9 - r2
            r0.label = r9
            goto L19
        L14:
            com.thor.app.service.data.LoadFirmwareUseCase$loadFirmware$1 r0 = new com.thor.app.service.data.LoadFirmwareUseCase$loadFirmware$1
            r0.<init>(r9)
        L19:
            java.lang.Object r9 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L36
            if (r2 != r3) goto L2e
            java.lang.Object r8 = r0.L$0
            java.util.List r8 = (java.util.List) r8
            kotlin.ResultKt.throwOnFailure(r9)     // Catch: java.lang.Exception -> L90
            goto L52
        L2e:
            java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
            java.lang.String r9 = "call to 'resume' before 'invoke' with coroutine"
            r8.<init>(r9)
            throw r8
        L36:
            kotlin.ResultKt.throwOnFailure(r9)
            java.util.ArrayList r9 = new java.util.ArrayList     // Catch: java.lang.Exception -> L90
            r9.<init>()     // Catch: java.lang.Exception -> L90
            java.util.List r9 = (java.util.List) r9     // Catch: java.lang.Exception -> L90
            com.thor.app.managers.UsersManager r2 = r7.getUsersManager()     // Catch: java.lang.Exception -> L90
            r0.L$0 = r9     // Catch: java.lang.Exception -> L90
            r0.label = r3     // Catch: java.lang.Exception -> L90
            java.lang.Object r8 = r2.coroutineFetchFile(r8, r0)     // Catch: java.lang.Exception -> L90
            if (r8 != r1) goto L4f
            return r1
        L4f:
            r6 = r9
            r9 = r8
            r8 = r6
        L52:
            retrofit2.Response r9 = (retrofit2.Response) r9     // Catch: java.lang.Exception -> L90
            java.lang.Object r9 = r9.body()     // Catch: java.lang.Exception -> L90
            okhttp3.ResponseBody r9 = (okhttp3.ResponseBody) r9     // Catch: java.lang.Exception -> L90
            if (r9 == 0) goto L6e
            byte[] r9 = r9.bytes()     // Catch: java.lang.Exception -> L90
            if (r9 == 0) goto L6e
            int r0 = r9.length     // Catch: java.lang.Exception -> L90
            byte[] r9 = java.util.Arrays.copyOf(r9, r0)     // Catch: java.lang.Exception -> L90
            java.lang.String r0 = "copyOf(...)"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r9, r0)     // Catch: java.lang.Exception -> L90
            if (r9 != 0) goto L71
        L6e:
            r9 = 0
            byte[] r9 = new byte[r9]     // Catch: java.lang.Exception -> L90
        L71:
            r2 = r9
            com.thor.app.service.models.UploadFileModel r9 = new com.thor.app.service.models.UploadFileModel     // Catch: java.lang.Exception -> L90
            com.thor.businessmodule.bluetooth.model.other.FileType r1 = com.thor.businessmodule.bluetooth.model.other.FileType.FirmwareFile     // Catch: java.lang.Exception -> L90
            r3 = 0
            r4 = 0
            r5 = 0
            r0 = r9
            r0.<init>(r1, r2, r3, r4, r5)     // Catch: java.lang.Exception -> L90
            r8.add(r9)     // Catch: java.lang.Exception -> L90
            com.thor.app.service.models.UploadFileGroupModel r9 = new com.thor.app.service.models.UploadFileGroupModel     // Catch: java.lang.Exception -> L90
            com.thor.app.service.models.UploadFileGroupModel$TypeGroupFile r0 = com.thor.app.service.models.UploadFileGroupModel.TypeGroupFile.FIRMWARE     // Catch: java.lang.Exception -> L90
            java.util.Collection r8 = (java.util.Collection) r8     // Catch: java.lang.Exception -> L90
            java.util.List r8 = kotlin.collections.CollectionsKt.toMutableList(r8)     // Catch: java.lang.Exception -> L90
            java.lang.String r1 = ""
            r9.<init>(r0, r8, r1)     // Catch: java.lang.Exception -> L90
            return r9
        L90:
            r8 = 0
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.thor.app.service.data.LoadFirmwareUseCase.loadFirmware(java.lang.String, kotlin.coroutines.Continuation):java.lang.Object");
    }
}
