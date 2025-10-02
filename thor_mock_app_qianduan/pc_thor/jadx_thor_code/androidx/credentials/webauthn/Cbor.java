package androidx.credentials.webauthn;

import com.google.android.exoplayer2.source.rtsp.SessionDescription;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.ranges.RangesKt;
import kotlin.text.Charsets;
import kotlin.text.StringsKt;
import okhttp3.internal.ws.WebSocketProtocol;

/* compiled from: Cbor.kt */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0011\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001:\u0002#$B\u0005¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00042\u0006\u0010\u0018\u001a\u00020\u0019H\u0002J\u000e\u0010\u001a\u001a\u00020\u00012\u0006\u0010\u001b\u001a\u00020\u0016J\u000e\u0010\u001c\u001a\u00020\u00162\u0006\u0010\u001b\u001a\u00020\u0001J\u0018\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001b\u001a\u00020\u00162\u0006\u0010\u001f\u001a\u00020\u0004H\u0002J\u0018\u0010 \u001a\u00020\u00042\u0006\u0010\u001b\u001a\u00020\u00162\u0006\u0010\u001f\u001a\u00020\u0004H\u0002J\u0018\u0010!\u001a\u00020\"2\u0006\u0010\u001b\u001a\u00020\u00162\u0006\u0010\u001f\u001a\u00020\u0004H\u0002R\u0014\u0010\u0003\u001a\u00020\u0004X\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0007\u001a\u00020\u0004X\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0006R\u0014\u0010\t\u001a\u00020\u0004X\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u0006R\u0014\u0010\u000b\u001a\u00020\u0004X\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u0006R\u0014\u0010\r\u001a\u00020\u0004X\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u0006R\u0014\u0010\u000f\u001a\u00020\u0004X\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0006R\u0014\u0010\u0011\u001a\u00020\u0004X\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0006R\u0014\u0010\u0013\u001a\u00020\u0004X\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0006¨\u0006%"}, d2 = {"Landroidx/credentials/webauthn/Cbor;", "", "()V", "TYPE_ARRAY", "", "getTYPE_ARRAY", "()I", "TYPE_BYTE_STRING", "getTYPE_BYTE_STRING", "TYPE_FLOAT", "getTYPE_FLOAT", "TYPE_MAP", "getTYPE_MAP", "TYPE_NEGATIVE_INT", "getTYPE_NEGATIVE_INT", "TYPE_TAG", "getTYPE_TAG", "TYPE_TEXT_STRING", "getTYPE_TEXT_STRING", "TYPE_UNSIGNED_INT", "getTYPE_UNSIGNED_INT", "createArg", "", SessionDescription.ATTR_TYPE, "arg", "", "decode", "data", "encode", "getArg", "Landroidx/credentials/webauthn/Cbor$Arg;", "offset", "getType", "parseItem", "Landroidx/credentials/webauthn/Cbor$Item;", "Arg", "Item", "credentials_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes.dex */
public final class Cbor {
    private final int TYPE_UNSIGNED_INT;
    private final int TYPE_NEGATIVE_INT = 1;
    private final int TYPE_BYTE_STRING = 2;
    private final int TYPE_TEXT_STRING = 3;
    private final int TYPE_ARRAY = 4;
    private final int TYPE_MAP = 5;
    private final int TYPE_TAG = 6;
    private final int TYPE_FLOAT = 7;

    /* compiled from: Cbor.kt */
    @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0001\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\t\u0010\n\u001a\u00020\u0001HÆ\u0003J\t\u0010\u000b\u001a\u00020\u0004HÆ\u0003J\u001d\u0010\f\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00012\b\b\u0002\u0010\u0003\u001a\u00020\u0004HÆ\u0001J\u0013\u0010\r\u001a\u00020\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0010\u001a\u00020\u0004HÖ\u0001J\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t¨\u0006\u0013"}, d2 = {"Landroidx/credentials/webauthn/Cbor$Item;", "", "item", "len", "", "(Ljava/lang/Object;I)V", "getItem", "()Ljava/lang/Object;", "getLen", "()I", "component1", "component2", "copy", "equals", "", "other", "hashCode", "toString", "", "credentials_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final /* data */ class Item {
        private final Object item;
        private final int len;

        public static /* synthetic */ Item copy$default(Item item, Object obj, int i, int i2, Object obj2) {
            if ((i2 & 1) != 0) {
                obj = item.item;
            }
            if ((i2 & 2) != 0) {
                i = item.len;
            }
            return item.copy(obj, i);
        }

        /* renamed from: component1, reason: from getter */
        public final Object getItem() {
            return this.item;
        }

        /* renamed from: component2, reason: from getter */
        public final int getLen() {
            return this.len;
        }

        public final Item copy(Object item, int len) {
            Intrinsics.checkNotNullParameter(item, "item");
            return new Item(item, len);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof Item)) {
                return false;
            }
            Item item = (Item) other;
            return Intrinsics.areEqual(this.item, item.item) && this.len == item.len;
        }

        public int hashCode() {
            return (this.item.hashCode() * 31) + Integer.hashCode(this.len);
        }

        public String toString() {
            return "Item(item=" + this.item + ", len=" + this.len + ')';
        }

        public Item(Object item, int i) {
            Intrinsics.checkNotNullParameter(item, "item");
            this.item = item;
            this.len = i;
        }

        public final Object getItem() {
            return this.item;
        }

        public final int getLen() {
            return this.len;
        }
    }

    /* compiled from: Cbor.kt */
    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\b\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003HÆ\u0003J\t\u0010\f\u001a\u00020\u0005HÆ\u0003J\u001d\u0010\r\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0011\u001a\u00020\u0005HÖ\u0001J\t\u0010\u0012\u001a\u00020\u0013HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0014"}, d2 = {"Landroidx/credentials/webauthn/Cbor$Arg;", "", "arg", "", "len", "", "(JI)V", "getArg", "()J", "getLen", "()I", "component1", "component2", "copy", "equals", "", "other", "hashCode", "toString", "", "credentials_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final /* data */ class Arg {
        private final long arg;
        private final int len;

        public static /* synthetic */ Arg copy$default(Arg arg, long j, int i, int i2, Object obj) {
            if ((i2 & 1) != 0) {
                j = arg.arg;
            }
            if ((i2 & 2) != 0) {
                i = arg.len;
            }
            return arg.copy(j, i);
        }

        /* renamed from: component1, reason: from getter */
        public final long getArg() {
            return this.arg;
        }

        /* renamed from: component2, reason: from getter */
        public final int getLen() {
            return this.len;
        }

        public final Arg copy(long arg, int len) {
            return new Arg(arg, len);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof Arg)) {
                return false;
            }
            Arg arg = (Arg) other;
            return this.arg == arg.arg && this.len == arg.len;
        }

        public int hashCode() {
            return (Long.hashCode(this.arg) * 31) + Integer.hashCode(this.len);
        }

        public String toString() {
            return "Arg(arg=" + this.arg + ", len=" + this.len + ')';
        }

        public Arg(long j, int i) {
            this.arg = j;
            this.len = i;
        }

        public final long getArg() {
            return this.arg;
        }

        public final int getLen() {
            return this.len;
        }
    }

    public final int getTYPE_UNSIGNED_INT() {
        return this.TYPE_UNSIGNED_INT;
    }

    public final int getTYPE_NEGATIVE_INT() {
        return this.TYPE_NEGATIVE_INT;
    }

    public final int getTYPE_BYTE_STRING() {
        return this.TYPE_BYTE_STRING;
    }

    public final int getTYPE_TEXT_STRING() {
        return this.TYPE_TEXT_STRING;
    }

    public final int getTYPE_ARRAY() {
        return this.TYPE_ARRAY;
    }

    public final int getTYPE_MAP() {
        return this.TYPE_MAP;
    }

    public final int getTYPE_TAG() {
        return this.TYPE_TAG;
    }

    public final int getTYPE_FLOAT() {
        return this.TYPE_FLOAT;
    }

    public final Object decode(byte[] data) {
        Intrinsics.checkNotNullParameter(data, "data");
        return parseItem(data, 0).getItem();
    }

    /* JADX WARN: Type inference failed for: r2v1, types: [T, java.util.Map] */
    public final byte[] encode(Object data) {
        Intrinsics.checkNotNullParameter(data, "data");
        if (data instanceof Number) {
            if (data instanceof Double) {
                throw new IllegalArgumentException("Don't support doubles yet");
            }
            long jLongValue = ((Number) data).longValue();
            if (jLongValue >= 0) {
                return createArg(this.TYPE_UNSIGNED_INT, jLongValue);
            }
            return createArg(this.TYPE_NEGATIVE_INT, (-1) - jLongValue);
        }
        if (data instanceof byte[]) {
            return ArraysKt.plus(createArg(this.TYPE_BYTE_STRING, r6.length), (byte[]) data);
        }
        if (data instanceof String) {
            return ArraysKt.plus(createArg(this.TYPE_TEXT_STRING, r6.length()), StringsKt.encodeToByteArray((String) data));
        }
        if (data instanceof List) {
            byte[] bArrCreateArg = createArg(this.TYPE_ARRAY, r6.size());
            for (Object obj : (List) data) {
                Intrinsics.checkNotNull(obj);
                bArrCreateArg = ArraysKt.plus(bArrCreateArg, encode(obj));
            }
            return bArrCreateArg;
        }
        if (data instanceof Map) {
            byte[] bArrCreateArg2 = createArg(this.TYPE_MAP, r6.size());
            final Ref.ObjectRef objectRef = new Ref.ObjectRef();
            objectRef.element = new LinkedHashMap();
            for (Map.Entry entry : ((Map) data).entrySet()) {
                Map map = (Map) objectRef.element;
                Object key = entry.getKey();
                Intrinsics.checkNotNull(key);
                byte[] bArrEncode = encode(key);
                Object value = entry.getValue();
                Intrinsics.checkNotNull(value);
                map.put(bArrEncode, encode(value));
            }
            ArrayList arrayList = new ArrayList(((Map) objectRef.element).keySet());
            CollectionsKt.sortedWith(arrayList, new Comparator() { // from class: androidx.credentials.webauthn.Cbor$$ExternalSyntheticLambda0
                @Override // java.util.Comparator
                public final int compare(Object obj2, Object obj3) {
                    return Cbor.encode$lambda$0(objectRef, (byte[]) obj2, (byte[]) obj3);
                }
            });
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                byte[] key2 = (byte[]) it.next();
                Intrinsics.checkNotNullExpressionValue(key2, "key");
                byte[] bArrPlus = ArraysKt.plus(bArrCreateArg2, key2);
                Object obj2 = ((Map) objectRef.element).get(key2);
                Intrinsics.checkNotNull(obj2);
                bArrCreateArg2 = ArraysKt.plus(bArrPlus, (byte[]) obj2);
            }
            return bArrCreateArg2;
        }
        throw new IllegalArgumentException("Bad type");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final int encode$lambda$0(Ref.ObjectRef byteMap, byte[] bArr, byte[] bArr2) {
        Intrinsics.checkNotNullParameter(byteMap, "$byteMap");
        Object obj = ((Map) byteMap.element).get(bArr);
        Intrinsics.checkNotNull(obj);
        byte[] bArr3 = (byte[]) obj;
        Object obj2 = ((Map) byteMap.element).get(bArr2);
        Intrinsics.checkNotNull(obj2);
        byte[] bArr4 = (byte[]) obj2;
        if (bArr.length > bArr2.length) {
            return 1;
        }
        if (bArr.length >= bArr2.length) {
            if (bArr3.length > bArr4.length) {
                return 1;
            }
            if (bArr3.length >= bArr4.length) {
                return 0;
            }
        }
        return -1;
    }

    private final int getType(byte[] data, int offset) {
        return (data[offset] & 255) >> 5;
    }

    private final Arg getArg(byte[] data, int offset) {
        long j = data[offset] & 31;
        if (j < 24) {
            return new Arg(j, 1);
        }
        if (j == 24) {
            return new Arg(data[offset + 1] & 255, 2);
        }
        if (j == 25) {
            return new Arg((data[offset + 2] & 255) | ((data[offset + 1] & 255) << 8), 3);
        }
        if (j == 26) {
            return new Arg((data[offset + 4] & 255) | ((data[offset + 1] & 255) << 24) | ((data[offset + 2] & 255) << 16) | ((data[offset + 3] & 255) << 8), 5);
        }
        throw new IllegalArgumentException("Bad arg");
    }

    private final Item parseItem(byte[] data, int offset) {
        int type = getType(data, offset);
        Arg arg = getArg(data, offset);
        System.out.println((Object) ("Type " + type + ' ' + arg.getArg() + ' ' + arg.getLen()));
        if (type == this.TYPE_UNSIGNED_INT) {
            return new Item(Long.valueOf(arg.getArg()), arg.getLen());
        }
        if (type == this.TYPE_NEGATIVE_INT) {
            return new Item(Long.valueOf((-1) - arg.getArg()), arg.getLen());
        }
        if (type == this.TYPE_BYTE_STRING) {
            return new Item(ArraysKt.sliceArray(data, RangesKt.until(arg.getLen() + offset, offset + arg.getLen() + ((int) arg.getArg()))), arg.getLen() + ((int) arg.getArg()));
        }
        if (type == this.TYPE_TEXT_STRING) {
            return new Item(new String(ArraysKt.sliceArray(data, RangesKt.until(arg.getLen() + offset, offset + arg.getLen() + ((int) arg.getArg()))), Charsets.UTF_8), arg.getLen() + ((int) arg.getArg()));
        }
        int i = 0;
        if (type == this.TYPE_ARRAY) {
            ArrayList arrayList = new ArrayList();
            int len = arg.getLen();
            int arg2 = (int) arg.getArg();
            while (i < arg2) {
                Item item = parseItem(data, offset + len);
                arrayList.add(item.getItem());
                len += item.getLen();
                i++;
            }
            return new Item(CollectionsKt.toList(arrayList), len);
        }
        if (type == this.TYPE_MAP) {
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            int len2 = arg.getLen();
            int arg3 = (int) arg.getArg();
            while (i < arg3) {
                Item item2 = parseItem(data, offset + len2);
                int len3 = len2 + item2.getLen();
                Item item3 = parseItem(data, offset + len3);
                len2 = len3 + item3.getLen();
                linkedHashMap.put(item2.getItem(), item3.getItem());
                i++;
            }
            return new Item(MapsKt.toMap(linkedHashMap), len2);
        }
        throw new IllegalArgumentException("Bad type");
    }

    private final byte[] createArg(int type, long arg) {
        int i = type << 5;
        int i2 = (int) arg;
        if (arg < 24) {
            return new byte[]{(byte) ((i | i2) & 255)};
        }
        if (arg <= 255) {
            return new byte[]{(byte) ((i | 24) & 255), (byte) (i2 & 255)};
        }
        if (arg <= WebSocketProtocol.PAYLOAD_SHORT_MAX) {
            return new byte[]{(byte) ((i | 25) & 255), (byte) ((i2 >> 8) & 255), (byte) (i2 & 255)};
        }
        if (arg <= 4294967295L) {
            return new byte[]{(byte) ((i | 26) & 255), (byte) ((i2 >> 24) & 255), (byte) ((i2 >> 16) & 255), (byte) ((i2 >> 8) & 255), (byte) (i2 & 255)};
        }
        throw new IllegalArgumentException("bad Arg");
    }
}
