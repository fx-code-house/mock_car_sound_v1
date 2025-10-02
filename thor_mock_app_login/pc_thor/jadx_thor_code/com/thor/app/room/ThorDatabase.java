package com.thor.app.room;

import android.content.Context;
import androidx.room.RoomDatabase;
import com.google.firebase.messaging.Constants;
import com.thor.app.ThorApplication;
import com.thor.app.room.dao.CurrentVersionDao;
import com.thor.app.room.dao.InstalledSoundPackageDao;
import com.thor.app.room.dao.ShopSguSoundPackageDao;
import com.thor.app.room.dao.ShopSoundPackageDao;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ThorDatabase.kt */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b'\u0018\u0000 \u000b2\u00020\u0001:\u0001\u000bB\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H&J\b\u0010\u0005\u001a\u00020\u0006H&J\b\u0010\u0007\u001a\u00020\bH&J\b\u0010\t\u001a\u00020\nH&¨\u0006\f"}, d2 = {"Lcom/thor/app/room/ThorDatabase;", "Landroidx/room/RoomDatabase;", "()V", "currentVersionDao", "Lcom/thor/app/room/dao/CurrentVersionDao;", "installedSoundPackageDao", "Lcom/thor/app/room/dao/InstalledSoundPackageDao;", "shopSguSoundPackageDao", "Lcom/thor/app/room/dao/ShopSguSoundPackageDao;", "shopSoundPackageDao", "Lcom/thor/app/room/dao/ShopSoundPackageDao;", "Companion", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public abstract class ThorDatabase extends RoomDatabase {

    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);

    public abstract CurrentVersionDao currentVersionDao();

    public abstract InstalledSoundPackageDao installedSoundPackageDao();

    public abstract ShopSguSoundPackageDao shopSguSoundPackageDao();

    public abstract ShopSoundPackageDao shopSoundPackageDao();

    /* compiled from: ThorDatabase.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\u0007"}, d2 = {"Lcom/thor/app/room/ThorDatabase$Companion;", "", "()V", Constants.MessagePayloadKeys.FROM, "Lcom/thor/app/room/ThorDatabase;", "context", "Landroid/content/Context;", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final ThorDatabase from(Context context) {
            Intrinsics.checkNotNullParameter(context, "context");
            Context applicationContext = context.getApplicationContext();
            Intrinsics.checkNotNull(applicationContext, "null cannot be cast to non-null type com.thor.app.ThorApplication");
            return ((ThorApplication) applicationContext).getThorDatabase();
        }
    }
}
