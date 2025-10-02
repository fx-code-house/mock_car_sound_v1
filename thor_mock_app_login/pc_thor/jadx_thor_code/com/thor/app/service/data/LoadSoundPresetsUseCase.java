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

/* compiled from: LoadSoundPresetsUseCase.kt */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J5\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\f2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000f0\f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0011H\u0082@ø\u0001\u0000¢\u0006\u0002\u0010\u0013J\u0019\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0011H\u0086@ø\u0001\u0000¢\u0006\u0002\u0010\u0017R\u001b\u0010\u0005\u001a\u00020\u00068BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\t\u0010\n\u001a\u0004\b\u0007\u0010\b\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u0018"}, d2 = {"Lcom/thor/app/service/data/LoadSoundPresetsUseCase;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "usersManager", "Lcom/thor/app/managers/UsersManager;", "getUsersManager", "()Lcom/thor/app/managers/UsersManager;", "usersManager$delegate", "Lkotlin/Lazy;", "loadDataBytes", "", "Lcom/thor/app/service/models/UploadFileModel;", "files", "Lcom/thor/networkmodule/model/responses/soundpackage/SoundPackageFile;", "pckId", "", "versionId", "(Ljava/util/List;IILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "loadSoundPresets", "Lcom/thor/app/service/models/UploadFileGroupModel;", "presetId", "(ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class LoadSoundPresetsUseCase {

    /* renamed from: usersManager$delegate, reason: from kotlin metadata */
    private final Lazy usersManager;

    /* compiled from: LoadSoundPresetsUseCase.kt */
    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    @DebugMetadata(c = "com.thor.app.service.data.LoadSoundPresetsUseCase", f = "LoadSoundPresetsUseCase.kt", i = {0, 0, 1, 1, 1, 1, 2, 2, 2, 2, 2, 3, 3, 3, 3}, l = {38, 54, 58, 62}, m = "loadDataBytes", n = {"pckId", "versionId", "this", "files", "pckId", "versionId", "this", "files", "sampleBytes", "pckId", "versionId", "sampleBytes", "rulesBytes", "pckId", "versionId"}, s = {"I$0", "I$1", "L$0", "L$1", "I$0", "I$1", "L$0", "L$1", "L$2", "I$0", "I$1", "L$0", "L$1", "I$0", "I$1"})
    /* renamed from: com.thor.app.service.data.LoadSoundPresetsUseCase$loadDataBytes$1, reason: invalid class name */
    static final class AnonymousClass1 extends ContinuationImpl {
        int I$0;
        int I$1;
        Object L$0;
        Object L$1;
        Object L$2;
        int label;
        /* synthetic */ Object result;

        AnonymousClass1(Continuation<? super AnonymousClass1> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return LoadSoundPresetsUseCase.this.loadDataBytes(null, 0, 0, this);
        }
    }

    /* compiled from: LoadSoundPresetsUseCase.kt */
    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    @DebugMetadata(c = "com.thor.app.service.data.LoadSoundPresetsUseCase", f = "LoadSoundPresetsUseCase.kt", i = {0, 1}, l = {18, 20}, m = "loadSoundPresets", n = {"this", "result"}, s = {"L$0", "L$0"})
    /* renamed from: com.thor.app.service.data.LoadSoundPresetsUseCase$loadSoundPresets$1, reason: invalid class name and case insensitive filesystem */
    static final class C04271 extends ContinuationImpl {
        Object L$0;
        int label;
        /* synthetic */ Object result;

        C04271(Continuation<? super C04271> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return LoadSoundPresetsUseCase.this.loadSoundPresets(0, this);
        }
    }

    public LoadSoundPresetsUseCase(final Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.usersManager = LazyKt.lazy(new Function0<UsersManager>() { // from class: com.thor.app.service.data.LoadSoundPresetsUseCase$usersManager$2
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

    /* JADX WARN: Removed duplicated region for block: B:39:0x0093  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0014  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object loadSoundPresets(int r8, kotlin.coroutines.Continuation<? super com.thor.app.service.models.UploadFileGroupModel> r9) {
        /*
            r7 = this;
            boolean r0 = r9 instanceof com.thor.app.service.data.LoadSoundPresetsUseCase.C04271
            if (r0 == 0) goto L14
            r0 = r9
            com.thor.app.service.data.LoadSoundPresetsUseCase$loadSoundPresets$1 r0 = (com.thor.app.service.data.LoadSoundPresetsUseCase.C04271) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L14
            int r9 = r0.label
            int r9 = r9 - r2
            r0.label = r9
            goto L19
        L14:
            com.thor.app.service.data.LoadSoundPresetsUseCase$loadSoundPresets$1 r0 = new com.thor.app.service.data.LoadSoundPresetsUseCase$loadSoundPresets$1
            r0.<init>(r9)
        L19:
            java.lang.Object r9 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 2
            r4 = 1
            if (r2 == 0) goto L41
            if (r2 == r4) goto L39
            if (r2 != r3) goto L31
            java.lang.Object r8 = r0.L$0
            com.thor.networkmodule.model.responses.soundpackage.SoundPackageDescriptionResponse r8 = (com.thor.networkmodule.model.responses.soundpackage.SoundPackageDescriptionResponse) r8
            kotlin.ResultKt.throwOnFailure(r9)
            goto L7f
        L31:
            java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
            java.lang.String r9 = "call to 'resume' before 'invoke' with coroutine"
            r8.<init>(r9)
            throw r8
        L39:
            java.lang.Object r8 = r0.L$0
            com.thor.app.service.data.LoadSoundPresetsUseCase r8 = (com.thor.app.service.data.LoadSoundPresetsUseCase) r8
            kotlin.ResultKt.throwOnFailure(r9)
            goto L54
        L41:
            kotlin.ResultKt.throwOnFailure(r9)
            com.thor.app.managers.UsersManager r9 = r7.getUsersManager()
            r0.L$0 = r7
            r0.label = r4
            java.lang.Object r9 = r9.fetchSuspendSoundPackageDescription(r8, r0)
            if (r9 != r1) goto L53
            return r1
        L53:
            r8 = r7
        L54:
            com.thor.networkmodule.model.responses.soundpackage.SoundPackageDescriptionResponse r9 = (com.thor.networkmodule.model.responses.soundpackage.SoundPackageDescriptionResponse) r9
            if (r9 == 0) goto L5e
            java.util.List r2 = r9.getFiles()
            if (r2 != 0) goto L62
        L5e:
            java.util.List r2 = kotlin.collections.CollectionsKt.emptyList()
        L62:
            r4 = 0
            if (r9 == 0) goto L6a
            int r5 = r9.getId()
            goto L6b
        L6a:
            r5 = r4
        L6b:
            if (r9 == 0) goto L71
            int r4 = r9.getVersion()
        L71:
            r0.L$0 = r9
            r0.label = r3
            java.lang.Object r8 = r8.loadDataBytes(r2, r5, r4, r0)
            if (r8 != r1) goto L7c
            return r1
        L7c:
            r6 = r9
            r9 = r8
            r8 = r6
        L7f:
            java.util.List r9 = (java.util.List) r9
            com.thor.app.service.models.UploadFileGroupModel r0 = new com.thor.app.service.models.UploadFileGroupModel
            com.thor.app.service.models.UploadFileGroupModel$TypeGroupFile r1 = com.thor.app.service.models.UploadFileGroupModel.TypeGroupFile.SOUND
            java.util.Collection r9 = (java.util.Collection) r9
            java.util.List r9 = kotlin.collections.CollectionsKt.toMutableList(r9)
            if (r8 == 0) goto L93
            java.lang.String r8 = r8.getName()
            if (r8 != 0) goto L95
        L93:
            java.lang.String r8 = ""
        L95:
            r0.<init>(r1, r9, r8)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.thor.app.service.data.LoadSoundPresetsUseCase.loadSoundPresets(int, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:103:0x01cd  */
    /* JADX WARN: Removed duplicated region for block: B:105:0x01d3  */
    /* JADX WARN: Removed duplicated region for block: B:108:0x01e6 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:109:0x01e7  */
    /* JADX WARN: Removed duplicated region for block: B:117:0x0209  */
    /* JADX WARN: Removed duplicated region for block: B:121:0x021d  */
    /* JADX WARN: Removed duplicated region for block: B:136:0x0251 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:137:0x0252  */
    /* JADX WARN: Removed duplicated region for block: B:140:0x0260  */
    /* JADX WARN: Removed duplicated region for block: B:145:0x0271  */
    /* JADX WARN: Removed duplicated region for block: B:149:0x0230 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:155:0x01c8 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:57:0x0122  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x001e  */
    /* JADX WARN: Removed duplicated region for block: B:89:0x019d  */
    /* JADX WARN: Removed duplicated region for block: B:93:0x01b2  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object loadDataBytes(java.util.List<com.thor.networkmodule.model.responses.soundpackage.SoundPackageFile> r27, int r28, int r29, kotlin.coroutines.Continuation<? super java.util.List<com.thor.app.service.models.UploadFileModel>> r30) {
        /*
            Method dump skipped, instructions count: 692
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.thor.app.service.data.LoadSoundPresetsUseCase.loadDataBytes(java.util.List, int, int, kotlin.coroutines.Continuation):java.lang.Object");
    }
}
