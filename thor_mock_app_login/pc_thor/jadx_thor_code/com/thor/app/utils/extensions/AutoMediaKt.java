package com.thor.app.utils.extensions;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.support.v4.media.MediaMetadataCompat;
import com.carsystems.thor.app.R;
import com.google.firebase.messaging.Constants;
import com.thor.app.auto.media.extensions.MediaMetadataCompatExtKt;
import com.thor.app.utils.auto.AlbumArtContentProvider;
import com.thor.businessmodule.model.DemoSoundPackage;
import com.thor.businessmodule.model.MainPresetPackage;
import com.thor.networkmodule.model.ModeType;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.apache.commons.lang3.StringUtils;

/* compiled from: AutoMedia.kt */
@Metadata(d1 = {"\u0000:\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\n\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\u001a%\u0010\u0000\u001a\u00020\u00012\b\u0010\u0002\u001a\u0004\u0018\u00010\u00032\u000e\u0010\u0004\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005¢\u0006\u0002\u0010\u0007\u001a\u001d\u0010\b\u001a\u00020\u00012\u0006\u0010\t\u001a\u00020\n2\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u000b\u001a\u001a\u0010\f\u001a\u00020\r*\u00020\r2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000e\u001a\u00020\u000f\u001a\u001a\u0010\f\u001a\u00020\r*\u00020\r2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000e\u001a\u00020\u0010\u001a\u0012\u0010\u0011\u001a\u00020\u0012*\u00020\n2\u0006\u0010\u0013\u001a\u00020\u0001¨\u0006\u0014"}, d2 = {"getAutoImageUrlForMode", "", "mode", "", "modeTypes", "", "Lcom/thor/networkmodule/model/ModeType;", "(Ljava/lang/Short;Ljava/util/List;)Ljava/lang/String;", "getModeNameFromIndex", "context", "Landroid/content/Context;", "(Landroid/content/Context;Ljava/lang/Short;)Ljava/lang/String;", Constants.MessagePayloadKeys.FROM, "Landroid/support/v4/media/MediaMetadataCompat$Builder;", "preset", "Lcom/thor/businessmodule/model/DemoSoundPackage;", "Lcom/thor/businessmodule/model/MainPresetPackage;", "parseMediaDuration", "", "fileName", "thor-1.8.7_release"}, k = 2, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class AutoMediaKt {
    public static final MediaMetadataCompat.Builder from(MediaMetadataCompat.Builder builder, Context context, MainPresetPackage preset) {
        Intrinsics.checkNotNullParameter(builder, "<this>");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(preset, "preset");
        String imageUrl = preset.getImageUrl();
        String fullFileUrl = imageUrl != null ? StringKt.getFullFileUrl(imageUrl) : null;
        String modeNameFromIndex = getModeNameFromIndex(context, preset.getModeType());
        builder.putString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID, preset.getName() + StringUtils.SPACE + modeNameFromIndex);
        builder.putString(MediaMetadataCompat.METADATA_KEY_TITLE, preset.getName());
        builder.putString(MediaMetadataCompat.METADATA_KEY_ALBUM, context.getString(R.string.device_presets_title));
        builder.putString(MediaMetadataCompat.METADATA_KEY_ARTIST, modeNameFromIndex);
        AlbumArtContentProvider.Companion companion = AlbumArtContentProvider.INSTANCE;
        Uri uri = Uri.parse(fullFileUrl);
        Intrinsics.checkNotNullExpressionValue(uri, "parse(imageUri)");
        builder.putString(MediaMetadataCompat.METADATA_KEY_ALBUM_ART_URI, companion.mapUri(uri).toString());
        builder.putLong(MediaMetadataCompatExtKt.METADATA_KEY_UAMP_FLAGS, 2);
        builder.putString(MediaMetadataCompat.METADATA_KEY_DISPLAY_TITLE, preset.getName());
        builder.putString(MediaMetadataCompat.METADATA_KEY_DISPLAY_SUBTITLE, modeNameFromIndex);
        builder.putString(MediaMetadataCompat.METADATA_KEY_DISPLAY_DESCRIPTION, preset.getName() + StringUtils.SPACE + modeNameFromIndex);
        AlbumArtContentProvider.Companion companion2 = AlbumArtContentProvider.INSTANCE;
        Uri uri2 = Uri.parse(fullFileUrl);
        Intrinsics.checkNotNullExpressionValue(uri2, "parse(imageUri)");
        builder.putString(MediaMetadataCompat.METADATA_KEY_DISPLAY_ICON_URI, companion2.mapUri(uri2).toString());
        return builder;
    }

    public static final MediaMetadataCompat.Builder from(MediaMetadataCompat.Builder builder, Context context, DemoSoundPackage preset) {
        Intrinsics.checkNotNullParameter(builder, "<this>");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(preset, "preset");
        String name = preset.getName();
        if (name == null) {
            name = "";
        }
        builder.putString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID, name);
        builder.putString(MediaMetadataCompat.METADATA_KEY_TITLE, preset.getName());
        builder.putString(MediaMetadataCompat.METADATA_KEY_ARTIST, preset.getName());
        builder.putBitmap(MediaMetadataCompat.METADATA_KEY_ALBUM_ART, com.thor.basemodule.extensions.ContextKt.bitmapFromDrawableName(context, preset.getDrawableName()));
        builder.putString(MediaMetadataCompat.METADATA_KEY_DISPLAY_TITLE, preset.getName());
        builder.putString(MediaMetadataCompat.METADATA_KEY_DISPLAY_SUBTITLE, preset.getName());
        builder.putString(MediaMetadataCompat.METADATA_KEY_DISPLAY_DESCRIPTION, preset.getName());
        return builder;
    }

    public static final String getModeNameFromIndex(Context context, Short sh) {
        String str;
        Intrinsics.checkNotNullParameter(context, "context");
        str = "";
        if (sh != null) {
            sh.shortValue();
            short sShortValue = sh.shortValue();
            if (sShortValue == 1) {
                String string = context.getString(R.string.text_city);
                str = string != null ? string : "";
                Intrinsics.checkNotNullExpressionValue(str, "context.getString(R.string.text_city) ?: \"\"");
            } else if (sShortValue == 2) {
                String string2 = context.getString(R.string.text_sport);
                str = string2 != null ? string2 : "";
                Intrinsics.checkNotNullExpressionValue(str, "context.getString(R.string.text_sport) ?: \"\"");
            } else {
                if (sShortValue != 3) {
                    return "";
                }
                String string3 = context.getString(R.string.text_own);
                str = string3 != null ? string3 : "";
                Intrinsics.checkNotNullExpressionValue(str, "context.getString(R.string.text_own) ?: \"\"");
            }
        }
        return str;
    }

    public static final String getAutoImageUrlForMode(Short sh, List<ModeType> list) {
        ModeType modeType;
        String image;
        Object next;
        boolean z = true;
        if (list != null) {
            Iterator<T> it = list.iterator();
            while (true) {
                if (!it.hasNext()) {
                    next = null;
                    break;
                }
                next = it.next();
                if (sh != null && ((ModeType) next).getType() == sh.shortValue()) {
                    break;
                }
            }
            modeType = (ModeType) next;
        } else {
            modeType = null;
        }
        String autoImage = modeType != null ? modeType.getAutoImage() : null;
        if (autoImage != null && !StringsKt.isBlank(autoImage)) {
            z = false;
        }
        if (z) {
            if (modeType == null || (image = modeType.getImage()) == null) {
                return "";
            }
        } else if (modeType == null || (image = modeType.getAutoImage()) == null) {
            return "";
        }
        return image;
    }

    public static final long parseMediaDuration(Context context, String fileName) throws IOException, IllegalArgumentException {
        Intrinsics.checkNotNullParameter(context, "<this>");
        Intrinsics.checkNotNullParameter(fileName, "fileName");
        AssetFileDescriptor assetFileDescriptorOpenFd = context.getAssets().openFd(fileName);
        Intrinsics.checkNotNullExpressionValue(assetFileDescriptorOpenFd, "assets.openFd(fileName)");
        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
        mediaMetadataRetriever.setDataSource(assetFileDescriptorOpenFd.getFileDescriptor(), assetFileDescriptorOpenFd.getStartOffset(), assetFileDescriptorOpenFd.getLength());
        String strExtractMetadata = mediaMetadataRetriever.extractMetadata(9);
        if (strExtractMetadata != null) {
            return Long.parseLong(strExtractMetadata);
        }
        return 0L;
    }
}
