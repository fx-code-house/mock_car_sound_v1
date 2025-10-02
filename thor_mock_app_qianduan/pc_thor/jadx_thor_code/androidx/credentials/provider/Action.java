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

/* compiled from: Action.kt */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\r\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\u0018\u0000 \u000e2\u00020\u0001:\u0002\r\u000eB!\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0007R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u000b¨\u0006\u000f"}, d2 = {"Landroidx/credentials/provider/Action;", "", "title", "", BaseGmsClient.KEY_PENDING_INTENT, "Landroid/app/PendingIntent;", "subtitle", "(Ljava/lang/CharSequence;Landroid/app/PendingIntent;Ljava/lang/CharSequence;)V", "getPendingIntent", "()Landroid/app/PendingIntent;", "getSubtitle", "()Ljava/lang/CharSequence;", "getTitle", "Builder", "Companion", "credentials_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes.dex */
public final class Action {

    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final String SLICE_HINT_PENDING_INTENT = "androidx.credentials.provider.action.SLICE_HINT_PENDING_INTENT";
    private static final String SLICE_HINT_SUBTITLE = "androidx.credentials.provider.action.HINT_ACTION_SUBTEXT";
    private static final String SLICE_HINT_TITLE = "androidx.credentials.provider.action.HINT_ACTION_TITLE";
    private static final int SLICE_SPEC_REVISION = 0;
    private static final String SLICE_SPEC_TYPE = "Action";
    private static final String TAG = "Action";
    private final PendingIntent pendingIntent;
    private final CharSequence subtitle;
    private final CharSequence title;

    @JvmStatic
    public static final Action fromSlice(Slice slice) {
        return INSTANCE.fromSlice(slice);
    }

    @JvmStatic
    public static final Slice toSlice(Action action) {
        return INSTANCE.toSlice(action);
    }

    public Action(CharSequence title, PendingIntent pendingIntent, CharSequence charSequence) {
        Intrinsics.checkNotNullParameter(title, "title");
        Intrinsics.checkNotNullParameter(pendingIntent, "pendingIntent");
        this.title = title;
        this.pendingIntent = pendingIntent;
        this.subtitle = charSequence;
        if (!(title.length() > 0)) {
            throw new IllegalArgumentException("title must not be empty".toString());
        }
    }

    public /* synthetic */ Action(CharSequence charSequence, PendingIntent pendingIntent, CharSequence charSequence2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(charSequence, pendingIntent, (i & 4) != 0 ? null : charSequence2);
    }

    public final CharSequence getTitle() {
        return this.title;
    }

    public final PendingIntent getPendingIntent() {
        return this.pendingIntent;
    }

    public final CharSequence getSubtitle() {
        return this.subtitle;
    }

    /* compiled from: Action.kt */
    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\r\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0006\u0010\b\u001a\u00020\tJ\u0010\u0010\n\u001a\u00020\u00002\b\u0010\u0007\u001a\u0004\u0018\u00010\u0003R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\u0003X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000b"}, d2 = {"Landroidx/credentials/provider/Action$Builder;", "", "title", "", BaseGmsClient.KEY_PENDING_INTENT, "Landroid/app/PendingIntent;", "(Ljava/lang/CharSequence;Landroid/app/PendingIntent;)V", "subtitle", "build", "Landroidx/credentials/provider/Action;", "setSubtitle", "credentials_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Builder {
        private final PendingIntent pendingIntent;
        private CharSequence subtitle;
        private final CharSequence title;

        public Builder(CharSequence title, PendingIntent pendingIntent) {
            Intrinsics.checkNotNullParameter(title, "title");
            Intrinsics.checkNotNullParameter(pendingIntent, "pendingIntent");
            this.title = title;
            this.pendingIntent = pendingIntent;
        }

        public final Builder setSubtitle(CharSequence subtitle) {
            this.subtitle = subtitle;
            return this;
        }

        public final Action build() {
            return new Action(this.title, this.pendingIntent, this.subtitle);
        }
    }

    /* compiled from: Action.kt */
    @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0080\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0012\u0010\u000b\u001a\u0004\u0018\u00010\f2\u0006\u0010\r\u001a\u00020\u000eH\u0007J\u0010\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\u0010\u001a\u00020\fH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u0011"}, d2 = {"Landroidx/credentials/provider/Action$Companion;", "", "()V", "SLICE_HINT_PENDING_INTENT", "", "SLICE_HINT_SUBTITLE", "SLICE_HINT_TITLE", "SLICE_SPEC_REVISION", "", "SLICE_SPEC_TYPE", "TAG", "fromSlice", "Landroidx/credentials/provider/Action;", "slice", "Landroid/app/slice/Slice;", "toSlice", "action", "credentials_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @JvmStatic
        public final Slice toSlice(Action action) {
            Intrinsics.checkNotNullParameter(action, "action");
            CharSequence title = action.getTitle();
            CharSequence subtitle = action.getSubtitle();
            PendingIntent pendingIntent = action.getPendingIntent();
            Slice.Builder builderAddText = new Slice.Builder(Uri.EMPTY, new SliceSpec("Action", 0)).addText(title, null, CollectionsKt.listOf(Action.SLICE_HINT_TITLE)).addText(subtitle, null, CollectionsKt.listOf(Action.SLICE_HINT_SUBTITLE));
            builderAddText.addAction(pendingIntent, new Slice.Builder(builderAddText).addHints(Collections.singletonList(Action.SLICE_HINT_PENDING_INTENT)).build(), null);
            Slice sliceBuild = builderAddText.build();
            Intrinsics.checkNotNullExpressionValue(sliceBuild, "sliceBuilder.build()");
            return sliceBuild;
        }

        @JvmStatic
        public final Action fromSlice(Slice slice) {
            Intrinsics.checkNotNullParameter(slice, "slice");
            List<SliceItem> items = slice.getItems();
            Intrinsics.checkNotNullExpressionValue(items, "slice.items");
            CharSequence text = "";
            PendingIntent action = null;
            CharSequence text2 = null;
            for (SliceItem sliceItem : items) {
                if (sliceItem.hasHint(Action.SLICE_HINT_TITLE)) {
                    text = sliceItem.getText();
                    Intrinsics.checkNotNullExpressionValue(text, "it.text");
                } else if (sliceItem.hasHint(Action.SLICE_HINT_SUBTITLE)) {
                    text2 = sliceItem.getText();
                } else if (sliceItem.hasHint(Action.SLICE_HINT_PENDING_INTENT)) {
                    action = sliceItem.getAction();
                }
            }
            try {
                Intrinsics.checkNotNull(action);
                return new Action(text, action, text2);
            } catch (Exception e) {
                Log.i("Action", "fromSlice failed with: " + e.getMessage());
                return null;
            }
        }
    }
}
