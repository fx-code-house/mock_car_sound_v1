package com.thor.app.gui.fragments.shop.main;

import android.os.Handler;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.gms.common.util.Hex;
import com.thor.app.bus.events.shop.main.DeletedSoundPackageEvent;
import com.thor.businessmodule.bluetooth.model.other.PoilingModel;
import com.thor.businessmodule.bluetooth.response.other.PoilingBleResponse;
import com.thor.businessmodule.bluetooth.util.BleHelperKt;
import java.io.ByteArrayOutputStream;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import timber.log.Timber;

/* compiled from: MainShopFragment.kt */
@Metadata(d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u00012\u000e\u0010\u0002\u001a\n \u0004*\u0004\u0018\u00010\u00030\u0003H\n¢\u0006\u0002\b\u0005"}, d2 = {"<anonymous>", "", "it", "Ljava/io/ByteArrayOutputStream;", "kotlin.jvm.PlatformType", "invoke"}, k = 3, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
final class MainShopFragment$executePoiling$commandDisposable$1 extends Lambda implements Function1<ByteArrayOutputStream, Unit> {
    final /* synthetic */ DeletedSoundPackageEvent $event;
    final /* synthetic */ MainShopFragment this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    MainShopFragment$executePoiling$commandDisposable$1(MainShopFragment mainShopFragment, DeletedSoundPackageEvent deletedSoundPackageEvent) {
        super(1);
        this.this$0 = mainShopFragment;
        this.$event = deletedSoundPackageEvent;
    }

    @Override // kotlin.jvm.functions.Function1
    public /* bridge */ /* synthetic */ Unit invoke(ByteArrayOutputStream byteArrayOutputStream) {
        invoke2(byteArrayOutputStream);
        return Unit.INSTANCE;
    }

    /* renamed from: invoke, reason: avoid collision after fix types in other method */
    public final void invoke2(ByteArrayOutputStream byteArrayOutputStream) {
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        boolean z = false;
        Timber.INSTANCE.i("Take data: " + Hex.bytesToStringUppercase(byteArray), new Object[0]);
        PoilingBleResponse poilingBleResponse = new PoilingBleResponse(byteArray);
        if (poilingBleResponse.getStatus()) {
            Timber.Companion companion = Timber.INSTANCE;
            byte[] bytes = poilingBleResponse.getBytes();
            companion.i("Response is correct: " + (bytes != null ? BleHelperKt.toHex(bytes) : null), new Object[0]);
            PoilingModel poilingModel = poilingBleResponse.getPoilingModel();
            if (poilingModel != null && poilingModel.getStatus() == 0) {
                z = true;
            }
            if (!z) {
                this.this$0.executePoiling(this.$event);
                return;
            } else {
                this.this$0.afterPoling(this.$event);
                return;
            }
        }
        Timber.INSTANCE.e("Response is not correct: " + poilingBleResponse.getErrorCode(), new Object[0]);
        Handler handler = this.this$0.getHandler();
        final MainShopFragment mainShopFragment = this.this$0;
        final DeletedSoundPackageEvent deletedSoundPackageEvent = this.$event;
        handler.postDelayed(new Runnable() { // from class: com.thor.app.gui.fragments.shop.main.MainShopFragment$executePoiling$commandDisposable$1$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                MainShopFragment$executePoiling$commandDisposable$1.invoke$lambda$0(mainShopFragment, deletedSoundPackageEvent);
            }
        }, SimpleExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void invoke$lambda$0(final MainShopFragment this$0, final DeletedSoundPackageEvent event) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(event, "$event");
        this$0.getBleManager().executeInitCrypto(new Function0<Unit>() { // from class: com.thor.app.gui.fragments.shop.main.MainShopFragment$executePoiling$commandDisposable$1$1$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public /* bridge */ /* synthetic */ Unit invoke() {
                invoke2();
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2() {
                this$0.afterPoling(event);
            }
        });
    }
}
