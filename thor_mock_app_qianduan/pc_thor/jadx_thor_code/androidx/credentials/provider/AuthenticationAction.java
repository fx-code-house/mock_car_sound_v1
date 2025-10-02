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
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: AuthenticationAction.kt */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\r\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u0000 \f2\u00020\u0001:\u0002\u000b\fB\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\r"}, d2 = {"Landroidx/credentials/provider/AuthenticationAction;", "", "title", "", BaseGmsClient.KEY_PENDING_INTENT, "Landroid/app/PendingIntent;", "(Ljava/lang/CharSequence;Landroid/app/PendingIntent;)V", "getPendingIntent", "()Landroid/app/PendingIntent;", "getTitle", "()Ljava/lang/CharSequence;", "Builder", "Companion", "credentials_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes.dex */
public final class AuthenticationAction {

    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final String SLICE_HINT_PENDING_INTENT = "androidx.credentials.provider.authenticationAction.SLICE_HINT_PENDING_INTENT";
    private static final String SLICE_HINT_TITLE = "androidx.credentials.provider.authenticationAction.SLICE_HINT_TITLE";
    private static final int SLICE_SPEC_REVISION = 0;
    private static final String SLICE_SPEC_TYPE = "AuthenticationAction";
    private static final String TAG = "AuthenticationAction";
    private final PendingIntent pendingIntent;
    private final CharSequence title;

    @JvmStatic
    public static final AuthenticationAction fromSlice(Slice slice) {
        return INSTANCE.fromSlice(slice);
    }

    @JvmStatic
    public static final Slice toSlice(AuthenticationAction authenticationAction) {
        return INSTANCE.toSlice(authenticationAction);
    }

    public AuthenticationAction(CharSequence title, PendingIntent pendingIntent) {
        Intrinsics.checkNotNullParameter(title, "title");
        Intrinsics.checkNotNullParameter(pendingIntent, "pendingIntent");
        this.title = title;
        this.pendingIntent = pendingIntent;
        if (!(title.length() > 0)) {
            throw new IllegalArgumentException("title must not be empty".toString());
        }
    }

    public final CharSequence getTitle() {
        return this.title;
    }

    public final PendingIntent getPendingIntent() {
        return this.pendingIntent;
    }

    /* compiled from: AuthenticationAction.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\r\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0006\u0010\u0007\u001a\u00020\bR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\t"}, d2 = {"Landroidx/credentials/provider/AuthenticationAction$Builder;", "", "title", "", BaseGmsClient.KEY_PENDING_INTENT, "Landroid/app/PendingIntent;", "(Ljava/lang/CharSequence;Landroid/app/PendingIntent;)V", "build", "Landroidx/credentials/provider/AuthenticationAction;", "credentials_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Builder {
        private final PendingIntent pendingIntent;
        private final CharSequence title;

        public Builder(CharSequence title, PendingIntent pendingIntent) {
            Intrinsics.checkNotNullParameter(title, "title");
            Intrinsics.checkNotNullParameter(pendingIntent, "pendingIntent");
            this.title = title;
            this.pendingIntent = pendingIntent;
        }

        public final AuthenticationAction build() {
            return new AuthenticationAction(this.title, this.pendingIntent);
        }
    }

    /* compiled from: AuthenticationAction.kt */
    @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0080\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0012\u0010\n\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\f\u001a\u00020\rH\u0007J\u0010\u0010\u000e\u001a\u00020\r2\u0006\u0010\u000f\u001a\u00020\u000bH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u0010"}, d2 = {"Landroidx/credentials/provider/AuthenticationAction$Companion;", "", "()V", "SLICE_HINT_PENDING_INTENT", "", "SLICE_HINT_TITLE", "SLICE_SPEC_REVISION", "", "SLICE_SPEC_TYPE", "TAG", "fromSlice", "Landroidx/credentials/provider/AuthenticationAction;", "slice", "Landroid/app/slice/Slice;", "toSlice", "authenticationAction", "credentials_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @JvmStatic
        public final Slice toSlice(AuthenticationAction authenticationAction) {
            Intrinsics.checkNotNullParameter(authenticationAction, "authenticationAction");
            CharSequence title = authenticationAction.getTitle();
            PendingIntent pendingIntent = authenticationAction.getPendingIntent();
            Slice.Builder builder = new Slice.Builder(Uri.EMPTY, new SliceSpec("AuthenticationAction", 0));
            builder.addAction(pendingIntent, new Slice.Builder(builder).addHints(Collections.singletonList(AuthenticationAction.SLICE_HINT_PENDING_INTENT)).build(), null).addText(title, null, CollectionsKt.listOf(AuthenticationAction.SLICE_HINT_TITLE));
            Slice sliceBuild = builder.build();
            Intrinsics.checkNotNullExpressionValue(sliceBuild, "sliceBuilder.build()");
            return sliceBuild;
        }

        @JvmStatic
        public final AuthenticationAction fromSlice(Slice slice) {
            Intrinsics.checkNotNullParameter(slice, "slice");
            List<SliceItem> items = slice.getItems();
            Intrinsics.checkNotNullExpressionValue(items, "slice.items");
            CharSequence text = null;
            PendingIntent action = null;
            for (SliceItem sliceItem : items) {
                if (sliceItem.hasHint(AuthenticationAction.SLICE_HINT_PENDING_INTENT)) {
                    action = sliceItem.getAction();
                } else if (sliceItem.hasHint(AuthenticationAction.SLICE_HINT_TITLE)) {
                    text = sliceItem.getText();
                }
            }
            try {
                Intrinsics.checkNotNull(text);
                Intrinsics.checkNotNull(action);
                return new AuthenticationAction(text, action);
            } catch (Exception e) {
                Log.i("AuthenticationAction", "fromSlice failed with: " + e.getMessage());
                return null;
            }
        }
    }
}
