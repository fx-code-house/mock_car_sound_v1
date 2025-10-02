package com.thor.app.auto.media.extensions;

import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.media.MediaMetadataCompat;
import com.google.android.exoplayer2.source.ConcatenatingMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.text.ttml.TtmlNode;
import com.google.android.exoplayer2.upstream.DataSource;
import java.util.Iterator;
import java.util.List;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: MediaMetadataCompatExt.kt */
@Metadata(d1 = {"\u0000N\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0015\n\u0002\u0010\t\n\u0002\b\u0019\n\u0002\u0010\b\n\u0002\b \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0000\u001a\u0012\u0010d\u001a\u00020e*\u00020\u00042\u0006\u0010f\u001a\u00020g\u001a\u0018\u0010d\u001a\u00020h*\b\u0012\u0004\u0012\u00020\u00040i2\u0006\u0010f\u001a\u00020g\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0002\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000\"\u0018\u0010\u0003\u001a\u0004\u0018\u00010\u0001*\u00020\u00048Æ\u0002¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006\".\u0010\u0003\u001a\u0004\u0018\u00010\u0001*\u00020\b2\b\u0010\u0007\u001a\u0004\u0018\u00010\u00018Ç\u0002@Æ\u0002X\u0086\u000e¢\u0006\f\u001a\u0004\b\u0005\u0010\t\"\u0004\b\n\u0010\u000b\"\u0018\u0010\f\u001a\u0004\u0018\u00010\r*\u00020\u00048Æ\u0002¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000f\".\u0010\f\u001a\u0004\u0018\u00010\r*\u00020\b2\b\u0010\u0007\u001a\u0004\u0018\u00010\r8Ç\u0002@Æ\u0002X\u0086\u000e¢\u0006\f\u001a\u0004\b\u000e\u0010\u0010\"\u0004\b\u0011\u0010\u0012\"\u0016\u0010\u0013\u001a\u00020\u0014*\u00020\u00048Æ\u0002¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0016\".\u0010\u0013\u001a\u0004\u0018\u00010\u0001*\u00020\b2\b\u0010\u0007\u001a\u0004\u0018\u00010\u00018Ç\u0002@Æ\u0002X\u0086\u000e¢\u0006\f\u001a\u0004\b\u0015\u0010\t\"\u0004\b\u0017\u0010\u000b\"\u0018\u0010\u0018\u001a\u0004\u0018\u00010\u0001*\u00020\u00048Æ\u0002¢\u0006\u0006\u001a\u0004\b\u0019\u0010\u0006\"\u0016\u0010\u001a\u001a\u00020\r*\u00020\u00048Æ\u0002¢\u0006\u0006\u001a\u0004\b\u001b\u0010\u000f\"\u0016\u0010\u001c\u001a\u00020\u0014*\u00020\u00048Æ\u0002¢\u0006\u0006\u001a\u0004\b\u001d\u0010\u0016\"\u0018\u0010\u001e\u001a\u0004\u0018\u00010\u0001*\u00020\u00048Æ\u0002¢\u0006\u0006\u001a\u0004\b\u001f\u0010\u0006\".\u0010\u001e\u001a\u0004\u0018\u00010\u0001*\u00020\b2\b\u0010\u0007\u001a\u0004\u0018\u00010\u00018Ç\u0002@Æ\u0002X\u0086\u000e¢\u0006\f\u001a\u0004\b\u001f\u0010\t\"\u0004\b \u0010\u000b\"\u0018\u0010!\u001a\u0004\u0018\u00010\u0001*\u00020\u00048Æ\u0002¢\u0006\u0006\u001a\u0004\b\"\u0010\u0006\"\u0018\u0010#\u001a\u0004\u0018\u00010\u0001*\u00020\u00048Æ\u0002¢\u0006\u0006\u001a\u0004\b$\u0010\u0006\"\u0018\u0010%\u001a\u0004\u0018\u00010\u0001*\u00020\u00048Æ\u0002¢\u0006\u0006\u001a\u0004\b&\u0010\u0006\"\u0018\u0010'\u001a\u0004\u0018\u00010\u0001*\u00020\u00048Æ\u0002¢\u0006\u0006\u001a\u0004\b(\u0010\u0006\"\u0016\u0010)\u001a\u00020**\u00020\u00048Æ\u0002¢\u0006\u0006\u001a\u0004\b+\u0010,\"\u0018\u0010-\u001a\u0004\u0018\u00010\u0001*\u00020\u00048Æ\u0002¢\u0006\u0006\u001a\u0004\b.\u0010\u0006\".\u0010-\u001a\u0004\u0018\u00010\u0001*\u00020\b2\b\u0010\u0007\u001a\u0004\u0018\u00010\u00018Ç\u0002@Æ\u0002X\u0086\u000e¢\u0006\f\u001a\u0004\b.\u0010\t\"\u0004\b/\u0010\u000b\"\u0016\u00100\u001a\u00020\r*\u00020\u00048Æ\u0002¢\u0006\u0006\u001a\u0004\b1\u0010\u000f\"\u0016\u00102\u001a\u00020\u0014*\u00020\u00048Æ\u0002¢\u0006\u0006\u001a\u0004\b3\u0010\u0016\".\u00102\u001a\u0004\u0018\u00010\u0001*\u00020\b2\b\u0010\u0007\u001a\u0004\u0018\u00010\u00018Ç\u0002@Æ\u0002X\u0086\u000e¢\u0006\f\u001a\u0004\b3\u0010\t\"\u0004\b4\u0010\u000b\"\u0018\u00105\u001a\u0004\u0018\u00010\u0001*\u00020\u00048Æ\u0002¢\u0006\u0006\u001a\u0004\b6\u0010\u0006\".\u00105\u001a\u0004\u0018\u00010\u0001*\u00020\b2\b\u0010\u0007\u001a\u0004\u0018\u00010\u00018Ç\u0002@Æ\u0002X\u0086\u000e¢\u0006\f\u001a\u0004\b6\u0010\t\"\u0004\b7\u0010\u000b\"\u0018\u00108\u001a\u0004\u0018\u00010\u0001*\u00020\u00048Æ\u0002¢\u0006\u0006\u001a\u0004\b9\u0010\u0006\".\u00108\u001a\u0004\u0018\u00010\u0001*\u00020\b2\b\u0010\u0007\u001a\u0004\u0018\u00010\u00018Ç\u0002@Æ\u0002X\u0086\u000e¢\u0006\f\u001a\u0004\b9\u0010\t\"\u0004\b:\u0010\u000b\"\u0016\u0010;\u001a\u00020**\u00020\u00048Æ\u0002¢\u0006\u0006\u001a\u0004\b<\u0010,\"*\u0010;\u001a\u00020**\u00020\b2\u0006\u0010\u0007\u001a\u00020*8Ç\u0002@Æ\u0002X\u0086\u000e¢\u0006\f\u001a\u0004\b<\u0010=\"\u0004\b>\u0010?\"\u0016\u0010@\u001a\u00020**\u00020\u00048Æ\u0002¢\u0006\u0006\u001a\u0004\bA\u0010,\"*\u0010@\u001a\u00020**\u00020\b2\u0006\u0010\u0007\u001a\u00020*8Ç\u0002@Æ\u0002X\u0086\u000e¢\u0006\f\u001a\u0004\bA\u0010=\"\u0004\bB\u0010?\"\u0016\u0010C\u001a\u00020D*\u00020\u00048Æ\u0002¢\u0006\u0006\u001a\u0004\bE\u0010F\"*\u0010C\u001a\u00020D*\u00020\b2\u0006\u0010\u0007\u001a\u00020D8Ç\u0002@Æ\u0002X\u0086\u000e¢\u0006\f\u001a\u0004\bE\u0010G\"\u0004\bH\u0010I\"\u0018\u0010J\u001a\u0004\u0018\u00010\u0001*\u00020\u00048Æ\u0002¢\u0006\u0006\u001a\u0004\bK\u0010\u0006\".\u0010J\u001a\u0004\u0018\u00010\u0001*\u00020\b2\b\u0010\u0007\u001a\u0004\u0018\u00010\u00018Ç\u0002@Æ\u0002X\u0086\u000e¢\u0006\f\u001a\u0004\bK\u0010\t\"\u0004\bL\u0010\u000b\"\u0018\u0010M\u001a\u0004\u0018\u00010\u0001*\u00020\u00048Æ\u0002¢\u0006\u0006\u001a\u0004\bN\u0010\u0006\"*\u0010M\u001a\u00020\u0001*\u00020\b2\u0006\u0010\u0007\u001a\u00020\u00018Ç\u0002@Æ\u0002X\u0086\u000e¢\u0006\f\u001a\u0004\bN\u0010\t\"\u0004\bO\u0010\u000b\"\u0016\u0010P\u001a\u00020\u0014*\u00020\u00048Æ\u0002¢\u0006\u0006\u001a\u0004\bQ\u0010\u0016\".\u0010P\u001a\u0004\u0018\u00010\u0001*\u00020\b2\b\u0010\u0007\u001a\u0004\u0018\u00010\u00018Ç\u0002@Æ\u0002X\u0086\u000e¢\u0006\f\u001a\u0004\bQ\u0010\t\"\u0004\bR\u0010\u000b\"\u0016\u0010S\u001a\u00020**\u00020\u00048Æ\u0002¢\u0006\u0006\u001a\u0004\bT\u0010,\"\u0018\u0010U\u001a\u0004\u0018\u00010\u0001*\u00020\u00048Æ\u0002¢\u0006\u0006\u001a\u0004\bV\u0010\u0006\".\u0010U\u001a\u0004\u0018\u00010\u0001*\u00020\b2\b\u0010\u0007\u001a\u0004\u0018\u00010\u00018Ç\u0002@Æ\u0002X\u0086\u000e¢\u0006\f\u001a\u0004\bV\u0010\t\"\u0004\bW\u0010\u000b\"\u0016\u0010X\u001a\u00020**\u00020\u00048Æ\u0002¢\u0006\u0006\u001a\u0004\bY\u0010,\"*\u0010X\u001a\u00020**\u00020\b2\u0006\u0010\u0007\u001a\u00020*8Ç\u0002@Æ\u0002X\u0086\u000e¢\u0006\f\u001a\u0004\bY\u0010=\"\u0004\bZ\u0010?\"\u0016\u0010[\u001a\u00020**\u00020\u00048Æ\u0002¢\u0006\u0006\u001a\u0004\b\\\u0010,\"*\u0010[\u001a\u00020**\u00020\b2\u0006\u0010\u0007\u001a\u00020*8Ç\u0002@Æ\u0002X\u0086\u000e¢\u0006\f\u001a\u0004\b\\\u0010=\"\u0004\b]\u0010?\"\u0016\u0010^\u001a\u00020**\u00020\u00048Æ\u0002¢\u0006\u0006\u001a\u0004\b_\u0010,\"\u0018\u0010`\u001a\u0004\u0018\u00010\u0001*\u00020\u00048Æ\u0002¢\u0006\u0006\u001a\u0004\ba\u0010\u0006\"\u0018\u0010b\u001a\u0004\u0018\u00010\u0001*\u00020\u00048Æ\u0002¢\u0006\u0006\u001a\u0004\bc\u0010\u0006¨\u0006j"}, d2 = {"METADATA_KEY_UAMP_FLAGS", "", "NO_GET", "album", "Landroid/support/v4/media/MediaMetadataCompat;", "getAlbum", "(Landroid/support/v4/media/MediaMetadataCompat;)Ljava/lang/String;", "value", "Landroid/support/v4/media/MediaMetadataCompat$Builder;", "(Landroid/support/v4/media/MediaMetadataCompat$Builder;)Ljava/lang/String;", "setAlbum", "(Landroid/support/v4/media/MediaMetadataCompat$Builder;Ljava/lang/String;)V", "albumArt", "Landroid/graphics/Bitmap;", "getAlbumArt", "(Landroid/support/v4/media/MediaMetadataCompat;)Landroid/graphics/Bitmap;", "(Landroid/support/v4/media/MediaMetadataCompat$Builder;)Landroid/graphics/Bitmap;", "setAlbumArt", "(Landroid/support/v4/media/MediaMetadataCompat$Builder;Landroid/graphics/Bitmap;)V", "albumArtUri", "Landroid/net/Uri;", "getAlbumArtUri", "(Landroid/support/v4/media/MediaMetadataCompat;)Landroid/net/Uri;", "setAlbumArtUri", "albumArtist", "getAlbumArtist", "art", "getArt", "artUri", "getArtUri", "artist", "getArtist", "setArtist", "author", "getAuthor", "compilation", "getCompilation", "composer", "getComposer", "date", "getDate", "discNumber", "", "getDiscNumber", "(Landroid/support/v4/media/MediaMetadataCompat;)J", "displayDescription", "getDisplayDescription", "setDisplayDescription", "displayIcon", "getDisplayIcon", "displayIconUri", "getDisplayIconUri", "setDisplayIconUri", "displaySubtitle", "getDisplaySubtitle", "setDisplaySubtitle", "displayTitle", "getDisplayTitle", "setDisplayTitle", "downloadStatus", "getDownloadStatus", "(Landroid/support/v4/media/MediaMetadataCompat$Builder;)J", "setDownloadStatus", "(Landroid/support/v4/media/MediaMetadataCompat$Builder;J)V", "duration", "getDuration", "setDuration", "flag", "", "getFlag", "(Landroid/support/v4/media/MediaMetadataCompat;)I", "(Landroid/support/v4/media/MediaMetadataCompat$Builder;)I", "setFlag", "(Landroid/support/v4/media/MediaMetadataCompat$Builder;I)V", "genre", "getGenre", "setGenre", TtmlNode.ATTR_ID, "getId", "setId", "mediaUri", "getMediaUri", "setMediaUri", "rating", "getRating", "title", "getTitle", "setTitle", "trackCount", "getTrackCount", "setTrackCount", "trackNumber", "getTrackNumber", "setTrackNumber", "userRating", "getUserRating", "writer", "getWriter", "year", "getYear", "toMediaSource", "Lcom/google/android/exoplayer2/source/ProgressiveMediaSource;", "dataSourceFactory", "Lcom/google/android/exoplayer2/upstream/DataSource$Factory;", "Lcom/google/android/exoplayer2/source/ConcatenatingMediaSource;", "", "thor-1.8.7_release"}, k = 2, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes.dex */
public final class MediaMetadataCompatExtKt {
    public static final String METADATA_KEY_UAMP_FLAGS = "com.thor.app.auto.media.METADATA_KEY_UAMP_FLAGS";
    public static final String NO_GET = "Property does not have a 'get'";

    public static final String getId(MediaMetadataCompat mediaMetadataCompat) {
        Intrinsics.checkNotNullParameter(mediaMetadataCompat, "<this>");
        return mediaMetadataCompat.getString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID);
    }

    public static final String getTitle(MediaMetadataCompat mediaMetadataCompat) {
        Intrinsics.checkNotNullParameter(mediaMetadataCompat, "<this>");
        return mediaMetadataCompat.getString(MediaMetadataCompat.METADATA_KEY_TITLE);
    }

    public static final String getArtist(MediaMetadataCompat mediaMetadataCompat) {
        Intrinsics.checkNotNullParameter(mediaMetadataCompat, "<this>");
        return mediaMetadataCompat.getString(MediaMetadataCompat.METADATA_KEY_ARTIST);
    }

    public static final long getDuration(MediaMetadataCompat mediaMetadataCompat) {
        Intrinsics.checkNotNullParameter(mediaMetadataCompat, "<this>");
        return mediaMetadataCompat.getLong(MediaMetadataCompat.METADATA_KEY_DURATION);
    }

    public static final String getAlbum(MediaMetadataCompat mediaMetadataCompat) {
        Intrinsics.checkNotNullParameter(mediaMetadataCompat, "<this>");
        return mediaMetadataCompat.getString(MediaMetadataCompat.METADATA_KEY_ALBUM);
    }

    public static final String getAuthor(MediaMetadataCompat mediaMetadataCompat) {
        Intrinsics.checkNotNullParameter(mediaMetadataCompat, "<this>");
        return mediaMetadataCompat.getString(MediaMetadataCompat.METADATA_KEY_AUTHOR);
    }

    public static final String getWriter(MediaMetadataCompat mediaMetadataCompat) {
        Intrinsics.checkNotNullParameter(mediaMetadataCompat, "<this>");
        return mediaMetadataCompat.getString(MediaMetadataCompat.METADATA_KEY_WRITER);
    }

    public static final String getComposer(MediaMetadataCompat mediaMetadataCompat) {
        Intrinsics.checkNotNullParameter(mediaMetadataCompat, "<this>");
        return mediaMetadataCompat.getString(MediaMetadataCompat.METADATA_KEY_COMPOSER);
    }

    public static final String getCompilation(MediaMetadataCompat mediaMetadataCompat) {
        Intrinsics.checkNotNullParameter(mediaMetadataCompat, "<this>");
        return mediaMetadataCompat.getString(MediaMetadataCompat.METADATA_KEY_COMPILATION);
    }

    public static final String getDate(MediaMetadataCompat mediaMetadataCompat) {
        Intrinsics.checkNotNullParameter(mediaMetadataCompat, "<this>");
        return mediaMetadataCompat.getString(MediaMetadataCompat.METADATA_KEY_DATE);
    }

    public static final String getYear(MediaMetadataCompat mediaMetadataCompat) {
        Intrinsics.checkNotNullParameter(mediaMetadataCompat, "<this>");
        return mediaMetadataCompat.getString(MediaMetadataCompat.METADATA_KEY_YEAR);
    }

    public static final String getGenre(MediaMetadataCompat mediaMetadataCompat) {
        Intrinsics.checkNotNullParameter(mediaMetadataCompat, "<this>");
        return mediaMetadataCompat.getString(MediaMetadataCompat.METADATA_KEY_GENRE);
    }

    public static final long getTrackNumber(MediaMetadataCompat mediaMetadataCompat) {
        Intrinsics.checkNotNullParameter(mediaMetadataCompat, "<this>");
        return mediaMetadataCompat.getLong(MediaMetadataCompat.METADATA_KEY_TRACK_NUMBER);
    }

    public static final long getTrackCount(MediaMetadataCompat mediaMetadataCompat) {
        Intrinsics.checkNotNullParameter(mediaMetadataCompat, "<this>");
        return mediaMetadataCompat.getLong(MediaMetadataCompat.METADATA_KEY_NUM_TRACKS);
    }

    public static final long getDiscNumber(MediaMetadataCompat mediaMetadataCompat) {
        Intrinsics.checkNotNullParameter(mediaMetadataCompat, "<this>");
        return mediaMetadataCompat.getLong(MediaMetadataCompat.METADATA_KEY_DISC_NUMBER);
    }

    public static final String getAlbumArtist(MediaMetadataCompat mediaMetadataCompat) {
        Intrinsics.checkNotNullParameter(mediaMetadataCompat, "<this>");
        return mediaMetadataCompat.getString(MediaMetadataCompat.METADATA_KEY_ALBUM_ARTIST);
    }

    public static final Bitmap getArt(MediaMetadataCompat mediaMetadataCompat) {
        Intrinsics.checkNotNullParameter(mediaMetadataCompat, "<this>");
        Bitmap bitmap = mediaMetadataCompat.getBitmap(MediaMetadataCompat.METADATA_KEY_ART);
        Intrinsics.checkNotNullExpressionValue(bitmap, "getBitmap(MediaMetadataCompat.METADATA_KEY_ART)");
        return bitmap;
    }

    public static final Uri getArtUri(MediaMetadataCompat mediaMetadataCompat) {
        Intrinsics.checkNotNullParameter(mediaMetadataCompat, "<this>");
        return JavaLangExtKt.toUri(mediaMetadataCompat.getString(MediaMetadataCompat.METADATA_KEY_ART_URI));
    }

    public static final Bitmap getAlbumArt(MediaMetadataCompat mediaMetadataCompat) {
        Intrinsics.checkNotNullParameter(mediaMetadataCompat, "<this>");
        return mediaMetadataCompat.getBitmap(MediaMetadataCompat.METADATA_KEY_ALBUM_ART);
    }

    public static final Uri getAlbumArtUri(MediaMetadataCompat mediaMetadataCompat) {
        Intrinsics.checkNotNullParameter(mediaMetadataCompat, "<this>");
        return JavaLangExtKt.toUri(mediaMetadataCompat.getString(MediaMetadataCompat.METADATA_KEY_ALBUM_ART_URI));
    }

    public static final long getUserRating(MediaMetadataCompat mediaMetadataCompat) {
        Intrinsics.checkNotNullParameter(mediaMetadataCompat, "<this>");
        return mediaMetadataCompat.getLong(MediaMetadataCompat.METADATA_KEY_USER_RATING);
    }

    public static final long getRating(MediaMetadataCompat mediaMetadataCompat) {
        Intrinsics.checkNotNullParameter(mediaMetadataCompat, "<this>");
        return mediaMetadataCompat.getLong(MediaMetadataCompat.METADATA_KEY_RATING);
    }

    public static final String getDisplayTitle(MediaMetadataCompat mediaMetadataCompat) {
        Intrinsics.checkNotNullParameter(mediaMetadataCompat, "<this>");
        return mediaMetadataCompat.getString(MediaMetadataCompat.METADATA_KEY_DISPLAY_TITLE);
    }

    public static final String getDisplaySubtitle(MediaMetadataCompat mediaMetadataCompat) {
        Intrinsics.checkNotNullParameter(mediaMetadataCompat, "<this>");
        return mediaMetadataCompat.getString(MediaMetadataCompat.METADATA_KEY_DISPLAY_SUBTITLE);
    }

    public static final String getDisplayDescription(MediaMetadataCompat mediaMetadataCompat) {
        Intrinsics.checkNotNullParameter(mediaMetadataCompat, "<this>");
        return mediaMetadataCompat.getString(MediaMetadataCompat.METADATA_KEY_DISPLAY_DESCRIPTION);
    }

    public static final Bitmap getDisplayIcon(MediaMetadataCompat mediaMetadataCompat) {
        Intrinsics.checkNotNullParameter(mediaMetadataCompat, "<this>");
        Bitmap bitmap = mediaMetadataCompat.getBitmap(MediaMetadataCompat.METADATA_KEY_DISPLAY_ICON);
        Intrinsics.checkNotNullExpressionValue(bitmap, "getBitmap(MediaMetadataC…ETADATA_KEY_DISPLAY_ICON)");
        return bitmap;
    }

    public static final Uri getDisplayIconUri(MediaMetadataCompat mediaMetadataCompat) {
        Intrinsics.checkNotNullParameter(mediaMetadataCompat, "<this>");
        return JavaLangExtKt.toUri(mediaMetadataCompat.getString(MediaMetadataCompat.METADATA_KEY_DISPLAY_ICON_URI));
    }

    public static final Uri getMediaUri(MediaMetadataCompat mediaMetadataCompat) {
        Intrinsics.checkNotNullParameter(mediaMetadataCompat, "<this>");
        return JavaLangExtKt.toUri(mediaMetadataCompat.getString(MediaMetadataCompat.METADATA_KEY_MEDIA_URI));
    }

    public static final long getDownloadStatus(MediaMetadataCompat mediaMetadataCompat) {
        Intrinsics.checkNotNullParameter(mediaMetadataCompat, "<this>");
        return mediaMetadataCompat.getLong(MediaMetadataCompat.METADATA_KEY_DOWNLOAD_STATUS);
    }

    public static final int getFlag(MediaMetadataCompat mediaMetadataCompat) {
        Intrinsics.checkNotNullParameter(mediaMetadataCompat, "<this>");
        return (int) mediaMetadataCompat.getLong(METADATA_KEY_UAMP_FLAGS);
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "Property does not have a 'get'")
    public static final String getId(MediaMetadataCompat.Builder builder) throws IllegalAccessException {
        Intrinsics.checkNotNullParameter(builder, "<this>");
        throw new IllegalAccessException("Cannot get from MediaMetadataCompat.Builder");
    }

    public static final void setId(MediaMetadataCompat.Builder builder, String value) {
        Intrinsics.checkNotNullParameter(builder, "<this>");
        Intrinsics.checkNotNullParameter(value, "value");
        builder.putString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID, value);
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "Property does not have a 'get'")
    public static final String getTitle(MediaMetadataCompat.Builder builder) throws IllegalAccessException {
        Intrinsics.checkNotNullParameter(builder, "<this>");
        throw new IllegalAccessException("Cannot get from MediaMetadataCompat.Builder");
    }

    public static final void setTitle(MediaMetadataCompat.Builder builder, String str) {
        Intrinsics.checkNotNullParameter(builder, "<this>");
        builder.putString(MediaMetadataCompat.METADATA_KEY_TITLE, str);
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "Property does not have a 'get'")
    public static final String getArtist(MediaMetadataCompat.Builder builder) throws IllegalAccessException {
        Intrinsics.checkNotNullParameter(builder, "<this>");
        throw new IllegalAccessException("Cannot get from MediaMetadataCompat.Builder");
    }

    public static final void setArtist(MediaMetadataCompat.Builder builder, String str) {
        Intrinsics.checkNotNullParameter(builder, "<this>");
        builder.putString(MediaMetadataCompat.METADATA_KEY_ARTIST, str);
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "Property does not have a 'get'")
    public static final String getAlbum(MediaMetadataCompat.Builder builder) throws IllegalAccessException {
        Intrinsics.checkNotNullParameter(builder, "<this>");
        throw new IllegalAccessException("Cannot get from MediaMetadataCompat.Builder");
    }

    public static final void setAlbum(MediaMetadataCompat.Builder builder, String str) {
        Intrinsics.checkNotNullParameter(builder, "<this>");
        builder.putString(MediaMetadataCompat.METADATA_KEY_ALBUM, str);
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "Property does not have a 'get'")
    public static final long getDuration(MediaMetadataCompat.Builder builder) throws IllegalAccessException {
        Intrinsics.checkNotNullParameter(builder, "<this>");
        throw new IllegalAccessException("Cannot get from MediaMetadataCompat.Builder");
    }

    public static final void setDuration(MediaMetadataCompat.Builder builder, long j) {
        Intrinsics.checkNotNullParameter(builder, "<this>");
        builder.putLong(MediaMetadataCompat.METADATA_KEY_DURATION, j);
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "Property does not have a 'get'")
    public static final String getGenre(MediaMetadataCompat.Builder builder) throws IllegalAccessException {
        Intrinsics.checkNotNullParameter(builder, "<this>");
        throw new IllegalAccessException("Cannot get from MediaMetadataCompat.Builder");
    }

    public static final void setGenre(MediaMetadataCompat.Builder builder, String str) {
        Intrinsics.checkNotNullParameter(builder, "<this>");
        builder.putString(MediaMetadataCompat.METADATA_KEY_GENRE, str);
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "Property does not have a 'get'")
    public static final String getMediaUri(MediaMetadataCompat.Builder builder) throws IllegalAccessException {
        Intrinsics.checkNotNullParameter(builder, "<this>");
        throw new IllegalAccessException("Cannot get from MediaMetadataCompat.Builder");
    }

    public static final void setMediaUri(MediaMetadataCompat.Builder builder, String str) {
        Intrinsics.checkNotNullParameter(builder, "<this>");
        builder.putString(MediaMetadataCompat.METADATA_KEY_MEDIA_URI, str);
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "Property does not have a 'get'")
    public static final String getAlbumArtUri(MediaMetadataCompat.Builder builder) throws IllegalAccessException {
        Intrinsics.checkNotNullParameter(builder, "<this>");
        throw new IllegalAccessException("Cannot get from MediaMetadataCompat.Builder");
    }

    public static final void setAlbumArtUri(MediaMetadataCompat.Builder builder, String str) {
        Intrinsics.checkNotNullParameter(builder, "<this>");
        builder.putString(MediaMetadataCompat.METADATA_KEY_ALBUM_ART_URI, str);
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "Property does not have a 'get'")
    public static final Bitmap getAlbumArt(MediaMetadataCompat.Builder builder) throws IllegalAccessException {
        Intrinsics.checkNotNullParameter(builder, "<this>");
        throw new IllegalAccessException("Cannot get from MediaMetadataCompat.Builder");
    }

    public static final void setAlbumArt(MediaMetadataCompat.Builder builder, Bitmap bitmap) {
        Intrinsics.checkNotNullParameter(builder, "<this>");
        builder.putBitmap(MediaMetadataCompat.METADATA_KEY_ALBUM_ART, bitmap);
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "Property does not have a 'get'")
    public static final long getTrackNumber(MediaMetadataCompat.Builder builder) throws IllegalAccessException {
        Intrinsics.checkNotNullParameter(builder, "<this>");
        throw new IllegalAccessException("Cannot get from MediaMetadataCompat.Builder");
    }

    public static final void setTrackNumber(MediaMetadataCompat.Builder builder, long j) {
        Intrinsics.checkNotNullParameter(builder, "<this>");
        builder.putLong(MediaMetadataCompat.METADATA_KEY_TRACK_NUMBER, j);
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "Property does not have a 'get'")
    public static final long getTrackCount(MediaMetadataCompat.Builder builder) throws IllegalAccessException {
        Intrinsics.checkNotNullParameter(builder, "<this>");
        throw new IllegalAccessException("Cannot get from MediaMetadataCompat.Builder");
    }

    public static final void setTrackCount(MediaMetadataCompat.Builder builder, long j) {
        Intrinsics.checkNotNullParameter(builder, "<this>");
        builder.putLong(MediaMetadataCompat.METADATA_KEY_NUM_TRACKS, j);
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "Property does not have a 'get'")
    public static final String getDisplayTitle(MediaMetadataCompat.Builder builder) throws IllegalAccessException {
        Intrinsics.checkNotNullParameter(builder, "<this>");
        throw new IllegalAccessException("Cannot get from MediaMetadataCompat.Builder");
    }

    public static final void setDisplayTitle(MediaMetadataCompat.Builder builder, String str) {
        Intrinsics.checkNotNullParameter(builder, "<this>");
        builder.putString(MediaMetadataCompat.METADATA_KEY_DISPLAY_TITLE, str);
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "Property does not have a 'get'")
    public static final String getDisplaySubtitle(MediaMetadataCompat.Builder builder) throws IllegalAccessException {
        Intrinsics.checkNotNullParameter(builder, "<this>");
        throw new IllegalAccessException("Cannot get from MediaMetadataCompat.Builder");
    }

    public static final void setDisplaySubtitle(MediaMetadataCompat.Builder builder, String str) {
        Intrinsics.checkNotNullParameter(builder, "<this>");
        builder.putString(MediaMetadataCompat.METADATA_KEY_DISPLAY_SUBTITLE, str);
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "Property does not have a 'get'")
    public static final String getDisplayDescription(MediaMetadataCompat.Builder builder) throws IllegalAccessException {
        Intrinsics.checkNotNullParameter(builder, "<this>");
        throw new IllegalAccessException("Cannot get from MediaMetadataCompat.Builder");
    }

    public static final void setDisplayDescription(MediaMetadataCompat.Builder builder, String str) {
        Intrinsics.checkNotNullParameter(builder, "<this>");
        builder.putString(MediaMetadataCompat.METADATA_KEY_DISPLAY_DESCRIPTION, str);
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "Property does not have a 'get'")
    public static final String getDisplayIconUri(MediaMetadataCompat.Builder builder) throws IllegalAccessException {
        Intrinsics.checkNotNullParameter(builder, "<this>");
        throw new IllegalAccessException("Cannot get from MediaMetadataCompat.Builder");
    }

    public static final void setDisplayIconUri(MediaMetadataCompat.Builder builder, String str) {
        Intrinsics.checkNotNullParameter(builder, "<this>");
        builder.putString(MediaMetadataCompat.METADATA_KEY_DISPLAY_ICON_URI, str);
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "Property does not have a 'get'")
    public static final long getDownloadStatus(MediaMetadataCompat.Builder builder) throws IllegalAccessException {
        Intrinsics.checkNotNullParameter(builder, "<this>");
        throw new IllegalAccessException("Cannot get from MediaMetadataCompat.Builder");
    }

    public static final void setDownloadStatus(MediaMetadataCompat.Builder builder, long j) {
        Intrinsics.checkNotNullParameter(builder, "<this>");
        builder.putLong(MediaMetadataCompat.METADATA_KEY_DOWNLOAD_STATUS, j);
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "Property does not have a 'get'")
    public static final int getFlag(MediaMetadataCompat.Builder builder) throws IllegalAccessException {
        Intrinsics.checkNotNullParameter(builder, "<this>");
        throw new IllegalAccessException("Cannot get from MediaMetadataCompat.Builder");
    }

    public static final void setFlag(MediaMetadataCompat.Builder builder, int i) {
        Intrinsics.checkNotNullParameter(builder, "<this>");
        builder.putLong(METADATA_KEY_UAMP_FLAGS, i);
    }

    public static final ProgressiveMediaSource toMediaSource(MediaMetadataCompat mediaMetadataCompat, DataSource.Factory dataSourceFactory) {
        Intrinsics.checkNotNullParameter(mediaMetadataCompat, "<this>");
        Intrinsics.checkNotNullParameter(dataSourceFactory, "dataSourceFactory");
        ProgressiveMediaSource progressiveMediaSourceCreateMediaSource = new ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(JavaLangExtKt.toUri(mediaMetadataCompat.getString(MediaMetadataCompat.METADATA_KEY_MEDIA_URI)));
        Intrinsics.checkNotNullExpressionValue(progressiveMediaSourceCreateMediaSource, "Factory(dataSourceFactor…eateMediaSource(mediaUri)");
        return progressiveMediaSourceCreateMediaSource;
    }

    public static final ConcatenatingMediaSource toMediaSource(List<MediaMetadataCompat> list, DataSource.Factory dataSourceFactory) {
        Intrinsics.checkNotNullParameter(list, "<this>");
        Intrinsics.checkNotNullParameter(dataSourceFactory, "dataSourceFactory");
        ConcatenatingMediaSource concatenatingMediaSource = new ConcatenatingMediaSource(new MediaSource[0]);
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            concatenatingMediaSource.addMediaSource(toMediaSource((MediaMetadataCompat) it.next(), dataSourceFactory));
        }
        return concatenatingMediaSource;
    }
}
