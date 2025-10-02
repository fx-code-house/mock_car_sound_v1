package com.thor.app.managers;

import android.util.Log;
import com.thor.app.room.ThorDatabase;
import com.thor.app.room.entity.InstalledSoundPackageEntity;
import com.thor.app.utils.logs.loggers.FileLogger;
import io.reactivex.Completable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

/* compiled from: BleManager.kt */
@Metadata(d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\u0010\u0000\u001a\u00020\u00012\u000e\u0010\u0002\u001a\n \u0004*\u0004\u0018\u00010\u00030\u0003H\n¢\u0006\u0004\b\u0005\u0010\u0006"}, d2 = {"<anonymous>", "", "it", "", "kotlin.jvm.PlatformType", "invoke", "(Ljava/lang/Integer;)V"}, k = 3, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
final class BleManager$updateInstallPresetDB$1$1 extends Lambda implements Function1<Integer, Unit> {
    final /* synthetic */ InstalledSoundPackageEntity $entity;
    final /* synthetic */ BleManager this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    BleManager$updateInstallPresetDB$1$1(BleManager bleManager, InstalledSoundPackageEntity installedSoundPackageEntity) {
        super(1);
        this.this$0 = bleManager;
        this.$entity = installedSoundPackageEntity;
    }

    @Override // kotlin.jvm.functions.Function1
    public /* bridge */ /* synthetic */ Unit invoke(Integer num) {
        invoke2(num);
        return Unit.INSTANCE;
    }

    /* renamed from: invoke, reason: avoid collision after fix types in other method */
    public final void invoke2(Integer num) {
        Log.i("DB_EVENT", "Success");
        if (num != null && num.intValue() == 0) {
            Completable completableInsert = ThorDatabase.INSTANCE.from(this.this$0.getContext()).installedSoundPackageDao().insert(this.$entity);
            final BleManager bleManager = this.this$0;
            Action action = new Action() { // from class: com.thor.app.managers.BleManager$updateInstallPresetDB$1$1$$ExternalSyntheticLambda0
                @Override // io.reactivex.functions.Action
                public final void run() {
                    BleManager$updateInstallPresetDB$1$1.invoke$lambda$0(bleManager);
                }
            };
            final BleManager bleManager2 = this.this$0;
            final Function1<Throwable, Unit> function1 = new Function1<Throwable, Unit>() { // from class: com.thor.app.managers.BleManager$updateInstallPresetDB$1$1.2
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
                    invoke2(th);
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(Throwable it) {
                    FileLogger fileLogger = bleManager2.mFileLogger;
                    Intrinsics.checkNotNullExpressionValue(it, "it");
                    fileLogger.e(it);
                }
            };
            this.this$0.getMDatabaseCompositeDisposable().add(completableInsert.subscribe(action, new Consumer() { // from class: com.thor.app.managers.BleManager$updateInstallPresetDB$1$1$$ExternalSyntheticLambda1
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    BleManager$updateInstallPresetDB$1$1.invoke$lambda$1(function1, obj);
                }
            }));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void invoke$lambda$0(BleManager this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Log.i("DB_EVENT", "Success");
        this$0.mFileLogger.i("Success", new Object[0]);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void invoke$lambda$1(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }
}
