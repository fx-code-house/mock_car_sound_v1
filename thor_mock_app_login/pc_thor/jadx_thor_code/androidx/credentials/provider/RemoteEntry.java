package androidx.credentials.provider;

import android.app.PendingIntent;
import android.app.slice.Slice;
import android.app.slice.SliceItem;
import android.app.slice.SliceSpec;
import android.net.Uri;
import android.util.Log;
import com.google.android.gms.common.internal.BaseGmsClient;
import java.util.Collections;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: RemoteEntry.kt */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u0000 \b2\u00020\u0001:\u0002\u0007\bB\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\t"}, d2 = {"Landroidx/credentials/provider/RemoteEntry;", "", BaseGmsClient.KEY_PENDING_INTENT, "Landroid/app/PendingIntent;", "(Landroid/app/PendingIntent;)V", "getPendingIntent", "()Landroid/app/PendingIntent;", "Builder", "Companion", "credentials_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes.dex */
public final class RemoteEntry {

    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final int REVISION_ID = 1;
    private static final String SLICE_HINT_PENDING_INTENT = "androidx.credentials.provider.remoteEntry.SLICE_HINT_PENDING_INTENT";
    private static final String SLICE_SPEC_TYPE = "RemoteEntry";
    private static final String TAG = "RemoteEntry";
    private final PendingIntent pendingIntent;

    @JvmStatic
    public static final RemoteEntry fromSlice(Slice slice) {
        return INSTANCE.fromSlice(slice);
    }

    @JvmStatic
    public static final Slice toSlice(RemoteEntry remoteEntry) {
        return INSTANCE.toSlice(remoteEntry);
    }

    public RemoteEntry(PendingIntent pendingIntent) {
        Intrinsics.checkNotNullParameter(pendingIntent, "pendingIntent");
        this.pendingIntent = pendingIntent;
    }

    public final PendingIntent getPendingIntent() {
        return this.pendingIntent;
    }

    /* compiled from: RemoteEntry.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0006\u0010\u0005\u001a\u00020\u0006R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0007"}, d2 = {"Landroidx/credentials/provider/RemoteEntry$Builder;", "", BaseGmsClient.KEY_PENDING_INTENT, "Landroid/app/PendingIntent;", "(Landroid/app/PendingIntent;)V", "build", "Landroidx/credentials/provider/RemoteEntry;", "credentials_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Builder {
        private final PendingIntent pendingIntent;

        public Builder(PendingIntent pendingIntent) {
            Intrinsics.checkNotNullParameter(pendingIntent, "pendingIntent");
            this.pendingIntent = pendingIntent;
        }

        public final RemoteEntry build() {
            return new RemoteEntry(this.pendingIntent);
        }
    }

    /* compiled from: RemoteEntry.kt */
    @Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0080\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0012\u0010\t\u001a\u0004\u0018\u00010\n2\u0006\u0010\u000b\u001a\u00020\fH\u0007J\u0010\u0010\r\u001a\u00020\f2\u0006\u0010\u000e\u001a\u00020\nH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0006X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u000f"}, d2 = {"Landroidx/credentials/provider/RemoteEntry$Companion;", "", "()V", "REVISION_ID", "", "SLICE_HINT_PENDING_INTENT", "", "SLICE_SPEC_TYPE", "TAG", "fromSlice", "Landroidx/credentials/provider/RemoteEntry;", "slice", "Landroid/app/slice/Slice;", "toSlice", "remoteEntry", "credentials_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @JvmStatic
        public final Slice toSlice(RemoteEntry remoteEntry) {
            Intrinsics.checkNotNullParameter(remoteEntry, "remoteEntry");
            PendingIntent pendingIntent = remoteEntry.getPendingIntent();
            Slice.Builder builder = new Slice.Builder(Uri.EMPTY, new SliceSpec("RemoteEntry", 1));
            builder.addAction(pendingIntent, new Slice.Builder(builder).addHints(Collections.singletonList(RemoteEntry.SLICE_HINT_PENDING_INTENT)).build(), null);
            Slice sliceBuild = builder.build();
            Intrinsics.checkNotNullExpressionValue(sliceBuild, "sliceBuilder.build()");
            return sliceBuild;
        }

        @JvmStatic
        public final RemoteEntry fromSlice(Slice slice) {
            Intrinsics.checkNotNullParameter(slice, "slice");
            List<SliceItem> items = slice.getItems();
            Intrinsics.checkNotNullExpressionValue(items, "slice.items");
            PendingIntent action = null;
            for (SliceItem sliceItem : items) {
                if (sliceItem.hasHint(RemoteEntry.SLICE_HINT_PENDING_INTENT)) {
                    action = sliceItem.getAction();
                }
            }
            try {
                Intrinsics.checkNotNull(action);
                return new RemoteEntry(action);
            } catch (Exception e) {
                Log.i("RemoteEntry", "fromSlice failed with: " + e.getMessage());
                return null;
            }
        }
    }
}
