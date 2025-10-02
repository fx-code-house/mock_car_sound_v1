package com.thor.businessmodule.bluetooth.model.other.settings;

import android.content.Context;
import android.util.Log;
import com.thor.businessmodule.bluetooth.model.other.settings.applicationmodel.EntityGroup;
import com.thor.businessmodule.bluetooth.model.other.settings.applicationmodel.Subscription;
import com.thor.businessmodule.bluetooth.util.BleHelper;
import com.thor.businessmodule.bluetooth.util.BleHelperKt;
import java.io.InputStream;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.io.ByteStreamsKt;
import kotlin.io.CloseableKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;

/* compiled from: ApplicationDataModel.kt */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0012\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001:\u0001\u0019B\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004B\u000f\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0001¢\u0006\u0002\u0010\u0006J\u0014\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\n0\fH\u0002J\u0010\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0005\u001a\u00020\nH\u0002J\t\u0010\u0010\u001a\u00020\u0001HÆ\u0003J\u0013\u0010\u0011\u001a\u00020\u00002\b\b\u0002\u0010\u0005\u001a\u00020\u0001HÆ\u0001J\u0013\u0010\u0012\u001a\u00020\u000f2\b\u0010\u0013\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0014\u001a\u00020\u0015HÖ\u0001J\u0010\u0010\u0016\u001a\u00020\u000f2\u0006\u0010\u0005\u001a\u00020\nH\u0002J\t\u0010\u0017\u001a\u00020\u0018HÖ\u0001R\u0011\u0010\u0005\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u000e\u0010\t\u001a\u00020\nX\u0082.¢\u0006\u0002\n\u0000¨\u0006\u001a"}, d2 = {"Lcom/thor/businessmodule/bluetooth/model/other/settings/ApplicationDataModel;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "data", "(Ljava/lang/Object;)V", "getData", "()Ljava/lang/Object;", "fieldData", "", "blockDataWithType", "Lkotlin/Pair;", "Lcom/thor/businessmodule/bluetooth/model/other/settings/ApplicationDataModel$BlockTypeApp;", "checkCRC", "", "component1", "copy", "equals", "other", "hashCode", "", "isEndBlock", "toString", "", "BlockTypeApp", "businessmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final /* data */ class ApplicationDataModel {
    private final Object data;
    private byte[] fieldData;

    /* compiled from: ApplicationDataModel.kt */
    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[BlockTypeApp.values().length];
            try {
                iArr[BlockTypeApp.ENTITY_GROUP.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[BlockTypeApp.SUBSCRIPTION.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public ApplicationDataModel() {
        DefaultConstructorMarker defaultConstructorMarker = null;
        this(defaultConstructorMarker, 1, defaultConstructorMarker);
    }

    public static /* synthetic */ ApplicationDataModel copy$default(ApplicationDataModel applicationDataModel, Object obj, int i, Object obj2) {
        if ((i & 1) != 0) {
            obj = applicationDataModel.data;
        }
        return applicationDataModel.copy(obj);
    }

    /* renamed from: component1, reason: from getter */
    public final Object getData() {
        return this.data;
    }

    public final ApplicationDataModel copy(Object data) {
        Intrinsics.checkNotNullParameter(data, "data");
        return new ApplicationDataModel(data);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        return (other instanceof ApplicationDataModel) && Intrinsics.areEqual(this.data, ((ApplicationDataModel) other).data);
    }

    public int hashCode() {
        return this.data.hashCode();
    }

    public String toString() {
        return "ApplicationDataModel(data=" + this.data + ")";
    }

    public ApplicationDataModel(Object data) {
        Intrinsics.checkNotNullParameter(data, "data");
        this.data = data;
    }

    public /* synthetic */ ApplicationDataModel(Object obj, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? new Object() : obj);
    }

    public final Object getData() {
        return this.data;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public ApplicationDataModel(Context context) throws Exception {
        Intrinsics.checkNotNullParameter(context, "context");
        DefaultConstructorMarker defaultConstructorMarker = null;
        this(defaultConstructorMarker, 1, defaultConstructorMarker);
        InputStream inputStreamOpen = context.getAssets().open("apl.app");
        try {
            InputStream inputStream = inputStreamOpen;
            Intrinsics.checkNotNullExpressionValue(inputStream, "inputStream");
            byte[] bytes = ByteStreamsKt.readBytes(inputStream);
            CloseableKt.closeFinally(inputStreamOpen, null);
            if (!checkCRC(bytes)) {
                return;
            }
            this.fieldData = ArraysKt.sliceArray(bytes, RangesKt.until(4, bytes.length - 2));
            while (true) {
                byte[] bArr = this.fieldData;
                if (bArr == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("fieldData");
                    bArr = null;
                }
                if (!isEndBlock(bArr)) {
                    return;
                }
                Pair<BlockTypeApp, byte[]> pairBlockDataWithType = blockDataWithType();
                BlockTypeApp blockTypeAppComponent1 = pairBlockDataWithType.component1();
                byte[] bArrComponent2 = pairBlockDataWithType.component2();
                int i = WhenMappings.$EnumSwitchMapping$0[blockTypeAppComponent1.ordinal()];
                if (i == 1) {
                    Log.i("ApplicationDataModel", "res: " + new EntityGroup(bArrComponent2));
                } else if (i == 2) {
                    Log.i("ApplicationDataModel", "res: " + Subscription.INSTANCE.get(bArrComponent2));
                }
            }
        } finally {
        }
    }

    private final boolean checkCRC(byte[] data) {
        return BleHelper.takeShort(BleHelper.createCheckSumPart(data)) == 0;
    }

    private final Pair<BlockTypeApp, byte[]> blockDataWithType() throws Exception {
        byte[] bArr = this.fieldData;
        byte[] bArr2 = null;
        if (bArr == null) {
            Intrinsics.throwUninitializedPropertyAccessException("fieldData");
            bArr = null;
        }
        int shortLittleEndian = BleHelperKt.toShortLittleEndian(ArraysKt.sliceArray(bArr, RangesKt.until(0, 2))) - 4;
        byte[] bArr3 = this.fieldData;
        if (bArr3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("fieldData");
            bArr3 = null;
        }
        this.fieldData = BleHelperKt.removeFirstNElements(bArr3, 2);
        BlockTypeApp.Companion companion = BlockTypeApp.INSTANCE;
        byte[] bArr4 = this.fieldData;
        if (bArr4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("fieldData");
            bArr4 = null;
        }
        BlockTypeApp blockTypeAppFromRawValue = companion.fromRawValue(BleHelperKt.toShortLittleEndian(ArraysKt.sliceArray(bArr4, RangesKt.until(0, 2))));
        if (blockTypeAppFromRawValue == null) {
            throw new Exception("Invalid block type");
        }
        byte[] bArr5 = this.fieldData;
        if (bArr5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("fieldData");
            bArr5 = null;
        }
        byte[] bArrRemoveFirstNElements = BleHelperKt.removeFirstNElements(bArr5, 2);
        this.fieldData = bArrRemoveFirstNElements;
        int i = shortLittleEndian % 2 != 0 ? shortLittleEndian - 1 : shortLittleEndian;
        if (bArrRemoveFirstNElements == null) {
            Intrinsics.throwUninitializedPropertyAccessException("fieldData");
            bArrRemoveFirstNElements = null;
        }
        byte[] bArrSliceArray = ArraysKt.sliceArray(bArrRemoveFirstNElements, RangesKt.until(0, i));
        byte[] bArr6 = this.fieldData;
        if (bArr6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("fieldData");
        } else {
            bArr2 = bArr6;
        }
        this.fieldData = BleHelperKt.removeFirstNElements(bArr2, shortLittleEndian);
        return new Pair<>(blockTypeAppFromRawValue, bArrSliceArray);
    }

    private final boolean isEndBlock(byte[] data) {
        return BleHelper.takeShort(CollectionsKt.toByteArray(ArraysKt.take(data, 2))) != -1;
    }

    /* compiled from: ApplicationDataModel.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\n\n\u0002\b\u0007\b\u0086\u0001\u0018\u0000 \t2\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001\tB\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\u0007j\u0002\b\b¨\u0006\n"}, d2 = {"Lcom/thor/businessmodule/bluetooth/model/other/settings/ApplicationDataModel$BlockTypeApp;", "", "rawValue", "", "(Ljava/lang/String;IS)V", "getRawValue", "()S", "ENTITY_GROUP", "SUBSCRIPTION", "Companion", "businessmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public enum BlockTypeApp {
        ENTITY_GROUP(12),
        SUBSCRIPTION(13);


        /* renamed from: Companion, reason: from kotlin metadata */
        public static final Companion INSTANCE = new Companion(null);
        private final short rawValue;

        BlockTypeApp(short s) {
            this.rawValue = s;
        }

        public final short getRawValue() {
            return this.rawValue;
        }

        /* compiled from: ApplicationDataModel.kt */
        @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\n\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\u0007"}, d2 = {"Lcom/thor/businessmodule/bluetooth/model/other/settings/ApplicationDataModel$BlockTypeApp$Companion;", "", "()V", "fromRawValue", "Lcom/thor/businessmodule/bluetooth/model/other/settings/ApplicationDataModel$BlockTypeApp;", "rawValue", "", "businessmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
        public static final class Companion {
            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            private Companion() {
            }

            public final BlockTypeApp fromRawValue(short rawValue) {
                for (BlockTypeApp blockTypeApp : BlockTypeApp.values()) {
                    if (blockTypeApp.getRawValue() == rawValue) {
                        return blockTypeApp;
                    }
                }
                return null;
            }
        }
    }
}
