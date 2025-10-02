package com.thor.basemodule.extensions;

import android.app.Activity;
import android.app.UiModeManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import androidx.exifinterface.media.ExifInterface;
import com.thor.basemodule.R;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import timber.log.Timber;

/* compiled from: Context.kt */
@Metadata(d1 = {"\u0000D\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a\u0016\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u00020\u00022\b\u0010\u0003\u001a\u0004\u0018\u00010\u0004\u001a\u0016\u0010\u0005\u001a\u00020\u0006*\u00020\u00022\b\u0010\u0003\u001a\u0004\u0018\u00010\u0004H\u0007\u001a\u0016\u0010\u0007\u001a\u00020\u0006*\u00020\u00022\b\u0010\b\u001a\u0004\u0018\u00010\u0004H\u0007\u001a\u0014\u0010\t\u001a\u00020\n*\u00020\u00022\b\b\u0001\u0010\u000b\u001a\u00020\u0006\u001a\u000e\u0010\f\u001a\u0004\u0018\u00010\r*\u0004\u0018\u00010\u0002\u001a\n\u0010\u000e\u001a\u00020\u000f*\u00020\u0002\u001a7\u0010\u0010\u001a\u00020\u0011\"\n\b\u0000\u0010\u0012\u0018\u0001*\u00020\r*\u00020\u00022\u0019\b\u0002\u0010\u0013\u001a\u0013\u0012\u0004\u0012\u00020\u0015\u0012\u0004\u0012\u00020\u00110\u0014¢\u0006\u0002\b\u0016H\u0086\bø\u0001\u0000\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\u0017"}, d2 = {"bitmapFromDrawableName", "Landroid/graphics/Bitmap;", "Landroid/content/Context;", "drawableName", "", "createDrawableResIdByName", "", "createRawResIdByName", "resName", "createUriForResource", "Landroid/net/Uri;", "resourceId", "getParentActivity", "Landroid/app/Activity;", "isCarUIMode", "", "startActivity", "", ExifInterface.GPS_DIRECTION_TRUE, "block", "Lkotlin/Function1;", "Landroid/content/Intent;", "Lkotlin/ExtensionFunctionType;", "basemodule_release"}, k = 2, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class ContextKt {
    public static /* synthetic */ void startActivity$default(Context context, Function1 block, int i, Object obj) {
        if ((i & 1) != 0) {
            block = new Function1<Intent, Unit>() { // from class: com.thor.basemodule.extensions.ContextKt.startActivity.1
                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(Intent intent) {
                    Intrinsics.checkNotNullParameter(intent, "$this$null");
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(Intent intent) {
                    invoke2(intent);
                    return Unit.INSTANCE;
                }
            };
        }
        Intrinsics.checkNotNullParameter(context, "<this>");
        Intrinsics.checkNotNullParameter(block, "block");
        Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
        Intent intent = new Intent(context, (Class<?>) Activity.class);
        block.invoke(intent);
        context.startActivity(intent);
    }

    public static final /* synthetic */ <T extends Activity> void startActivity(Context context, Function1<? super Intent, Unit> block) {
        Intrinsics.checkNotNullParameter(context, "<this>");
        Intrinsics.checkNotNullParameter(block, "block");
        Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
        Intent intent = new Intent(context, (Class<?>) Activity.class);
        block.invoke(intent);
        context.startActivity(intent);
    }

    public static final Bitmap bitmapFromDrawableName(Context context, String str) {
        Intrinsics.checkNotNullParameter(context, "<this>");
        return BitmapFactory.decodeResource(context.getResources(), createDrawableResIdByName(context, str));
    }

    public static final int createDrawableResIdByName(Context context, String str) {
        Intrinsics.checkNotNullParameter(context, "<this>");
        return context.getResources().getIdentifier(str, context.getResources().getString(R.string.identifier_drawable), context.getApplicationContext().getPackageName());
    }

    public static final int createRawResIdByName(Context context, String str) {
        Intrinsics.checkNotNullParameter(context, "<this>");
        return context.getResources().getIdentifier(str, context.getResources().getString(R.string.identifier_raw), context.getApplicationContext().getPackageName());
    }

    public static final Uri createUriForResource(Context context, int i) {
        Intrinsics.checkNotNullParameter(context, "<this>");
        try {
            Uri uriBuild = new Uri.Builder().scheme("android.resource").authority(context.getResources().getResourcePackageName(i)).appendPath(context.getResources().getResourceTypeName(i)).appendPath(context.getResources().getResourceEntryName(i)).build();
            Intrinsics.checkNotNullExpressionValue(uriBuild, "{\n        Uri.Builder()\n…           .build()\n    }");
            return uriBuild;
        } catch (Resources.NotFoundException e) {
            Timber.d(e);
            Uri uriBuild2 = new Uri.Builder().build();
            Intrinsics.checkNotNullExpressionValue(uriBuild2, "{\n        Timber.d(e)\n  …i.Builder().build()\n    }");
            return uriBuild2;
        }
    }

    public static final boolean isCarUIMode(Context context) {
        Intrinsics.checkNotNullParameter(context, "<this>");
        Object systemService = context.getSystemService("uimode");
        UiModeManager uiModeManager = systemService instanceof UiModeManager ? (UiModeManager) systemService : null;
        return uiModeManager != null && uiModeManager.getCurrentModeType() == 3;
    }

    public static final Activity getParentActivity(Context context) {
        if (context instanceof ContextWrapper) {
            return context instanceof Activity ? (Activity) context : getParentActivity(((ContextWrapper) context).getBaseContext());
        }
        return null;
    }
}
