package com.thor.app.utils.auto;

import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.media.MediaMetadataCompat;
import com.google.android.exoplayer2.text.ttml.TtmlNode;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: MediaMetadataCompatExt.kt */
@Metadata(d1 = {"\u00000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0015\n\u0002\u0010\t\n\u0002\b3\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000\"\u0018\u0010\u0002\u001a\u0004\u0018\u00010\u0001*\u00020\u00038Æ\u0002¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005\".\u0010\u0002\u001a\u0004\u0018\u00010\u0001*\u00020\u00072\b\u0010\u0006\u001a\u0004\u0018\u00010\u00018Ç\u0002@Æ\u0002X\u0086\u000e¢\u0006\f\u001a\u0004\b\u0004\u0010\b\"\u0004\b\t\u0010\n\"\u0018\u0010\u000b\u001a\u0004\u0018\u00010\f*\u00020\u00038Æ\u0002¢\u0006\u0006\u001a\u0004\b\r\u0010\u000e\".\u0010\u000b\u001a\u0004\u0018\u00010\f*\u00020\u00072\b\u0010\u0006\u001a\u0004\u0018\u00010\f8Ç\u0002@Æ\u0002X\u0086\u000e¢\u0006\f\u001a\u0004\b\r\u0010\u000f\"\u0004\b\u0010\u0010\u0011\"\u0016\u0010\u0012\u001a\u00020\u0013*\u00020\u00038Æ\u0002¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015\".\u0010\u0012\u001a\u0004\u0018\u00010\u0001*\u00020\u00072\b\u0010\u0006\u001a\u0004\u0018\u00010\u00018Ç\u0002@Æ\u0002X\u0086\u000e¢\u0006\f\u001a\u0004\b\u0014\u0010\b\"\u0004\b\u0016\u0010\n\"\u0018\u0010\u0017\u001a\u0004\u0018\u00010\u0001*\u00020\u00038Æ\u0002¢\u0006\u0006\u001a\u0004\b\u0018\u0010\u0005\"\u0016\u0010\u0019\u001a\u00020\f*\u00020\u00038Æ\u0002¢\u0006\u0006\u001a\u0004\b\u001a\u0010\u000e\"\u0016\u0010\u001b\u001a\u00020\u0013*\u00020\u00038Æ\u0002¢\u0006\u0006\u001a\u0004\b\u001c\u0010\u0015\"\u0018\u0010\u001d\u001a\u0004\u0018\u00010\u0001*\u00020\u00038Æ\u0002¢\u0006\u0006\u001a\u0004\b\u001e\u0010\u0005\".\u0010\u001d\u001a\u0004\u0018\u00010\u0001*\u00020\u00072\b\u0010\u0006\u001a\u0004\u0018\u00010\u00018Ç\u0002@Æ\u0002X\u0086\u000e¢\u0006\f\u001a\u0004\b\u001e\u0010\b\"\u0004\b\u001f\u0010\n\"\u0018\u0010 \u001a\u0004\u0018\u00010\u0001*\u00020\u00038Æ\u0002¢\u0006\u0006\u001a\u0004\b!\u0010\u0005\"\u0018\u0010\"\u001a\u0004\u0018\u00010\u0001*\u00020\u00038Æ\u0002¢\u0006\u0006\u001a\u0004\b#\u0010\u0005\"\u0018\u0010$\u001a\u0004\u0018\u00010\u0001*\u00020\u00038Æ\u0002¢\u0006\u0006\u001a\u0004\b%\u0010\u0005\"\u0018\u0010&\u001a\u0004\u0018\u00010\u0001*\u00020\u00038Æ\u0002¢\u0006\u0006\u001a\u0004\b'\u0010\u0005\"\u0016\u0010(\u001a\u00020)*\u00020\u00038Æ\u0002¢\u0006\u0006\u001a\u0004\b*\u0010+\"\u0018\u0010,\u001a\u0004\u0018\u00010\u0001*\u00020\u00038Æ\u0002¢\u0006\u0006\u001a\u0004\b-\u0010\u0005\".\u0010,\u001a\u0004\u0018\u00010\u0001*\u00020\u00072\b\u0010\u0006\u001a\u0004\u0018\u00010\u00018Ç\u0002@Æ\u0002X\u0086\u000e¢\u0006\f\u001a\u0004\b-\u0010\b\"\u0004\b.\u0010\n\"\u0016\u0010/\u001a\u00020\f*\u00020\u00038Æ\u0002¢\u0006\u0006\u001a\u0004\b0\u0010\u000e\"\u0016\u00101\u001a\u00020\u0013*\u00020\u00038Æ\u0002¢\u0006\u0006\u001a\u0004\b2\u0010\u0015\".\u00101\u001a\u0004\u0018\u00010\u0001*\u00020\u00072\b\u0010\u0006\u001a\u0004\u0018\u00010\u00018Ç\u0002@Æ\u0002X\u0086\u000e¢\u0006\f\u001a\u0004\b2\u0010\b\"\u0004\b3\u0010\n\"\u0018\u00104\u001a\u0004\u0018\u00010\u0001*\u00020\u00038Æ\u0002¢\u0006\u0006\u001a\u0004\b5\u0010\u0005\".\u00104\u001a\u0004\u0018\u00010\u0001*\u00020\u00072\b\u0010\u0006\u001a\u0004\u0018\u00010\u00018Ç\u0002@Æ\u0002X\u0086\u000e¢\u0006\f\u001a\u0004\b5\u0010\b\"\u0004\b6\u0010\n\"\u0018\u00107\u001a\u0004\u0018\u00010\u0001*\u00020\u00038Æ\u0002¢\u0006\u0006\u001a\u0004\b8\u0010\u0005\".\u00107\u001a\u0004\u0018\u00010\u0001*\u00020\u00072\b\u0010\u0006\u001a\u0004\u0018\u00010\u00018Ç\u0002@Æ\u0002X\u0086\u000e¢\u0006\f\u001a\u0004\b8\u0010\b\"\u0004\b9\u0010\n\"\u0016\u0010:\u001a\u00020)*\u00020\u00038Æ\u0002¢\u0006\u0006\u001a\u0004\b;\u0010+\"*\u0010:\u001a\u00020)*\u00020\u00072\u0006\u0010\u0006\u001a\u00020)8Ç\u0002@Æ\u0002X\u0086\u000e¢\u0006\f\u001a\u0004\b;\u0010<\"\u0004\b=\u0010>\"\u0016\u0010?\u001a\u00020)*\u00020\u00038Æ\u0002¢\u0006\u0006\u001a\u0004\b@\u0010+\"*\u0010?\u001a\u00020)*\u00020\u00072\u0006\u0010\u0006\u001a\u00020)8Ç\u0002@Æ\u0002X\u0086\u000e¢\u0006\f\u001a\u0004\b@\u0010<\"\u0004\bA\u0010>\"\u0018\u0010B\u001a\u0004\u0018\u00010\u0001*\u00020\u00038Æ\u0002¢\u0006\u0006\u001a\u0004\bC\u0010\u0005\".\u0010B\u001a\u0004\u0018\u00010\u0001*\u00020\u00072\b\u0010\u0006\u001a\u0004\u0018\u00010\u00018Ç\u0002@Æ\u0002X\u0086\u000e¢\u0006\f\u001a\u0004\bC\u0010\b\"\u0004\bD\u0010\n\"\u0018\u0010E\u001a\u0004\u0018\u00010\u0001*\u00020\u00038Æ\u0002¢\u0006\u0006\u001a\u0004\bF\u0010\u0005\"*\u0010E\u001a\u00020\u0001*\u00020\u00072\u0006\u0010\u0006\u001a\u00020\u00018Ç\u0002@Æ\u0002X\u0086\u000e¢\u0006\f\u001a\u0004\bF\u0010\b\"\u0004\bG\u0010\n\"\u0016\u0010H\u001a\u00020\u0013*\u00020\u00038Æ\u0002¢\u0006\u0006\u001a\u0004\bI\u0010\u0015\".\u0010H\u001a\u0004\u0018\u00010\u0001*\u00020\u00072\b\u0010\u0006\u001a\u0004\u0018\u00010\u00018Ç\u0002@Æ\u0002X\u0086\u000e¢\u0006\f\u001a\u0004\bI\u0010\b\"\u0004\bJ\u0010\n\"\u0016\u0010K\u001a\u00020)*\u00020\u00038Æ\u0002¢\u0006\u0006\u001a\u0004\bL\u0010+\"\u0018\u0010M\u001a\u0004\u0018\u00010\u0001*\u00020\u00038Æ\u0002¢\u0006\u0006\u001a\u0004\bN\u0010\u0005\".\u0010M\u001a\u0004\u0018\u00010\u0001*\u00020\u00072\b\u0010\u0006\u001a\u0004\u0018\u00010\u00018Ç\u0002@Æ\u0002X\u0086\u000e¢\u0006\f\u001a\u0004\bN\u0010\b\"\u0004\bO\u0010\n\"\u0016\u0010P\u001a\u00020)*\u00020\u00038Æ\u0002¢\u0006\u0006\u001a\u0004\bQ\u0010+\"*\u0010P\u001a\u00020)*\u00020\u00072\u0006\u0010\u0006\u001a\u00020)8Ç\u0002@Æ\u0002X\u0086\u000e¢\u0006\f\u001a\u0004\bQ\u0010<\"\u0004\bR\u0010>\"\u0016\u0010S\u001a\u00020)*\u00020\u00038Æ\u0002¢\u0006\u0006\u001a\u0004\bT\u0010+\"*\u0010S\u001a\u00020)*\u00020\u00072\u0006\u0010\u0006\u001a\u00020)8Ç\u0002@Æ\u0002X\u0086\u000e¢\u0006\f\u001a\u0004\bT\u0010<\"\u0004\bU\u0010>\"\u0016\u0010V\u001a\u00020)*\u00020\u00038Æ\u0002¢\u0006\u0006\u001a\u0004\bW\u0010+\"\u0018\u0010X\u001a\u0004\u0018\u00010\u0001*\u00020\u00038Æ\u0002¢\u0006\u0006\u001a\u0004\bY\u0010\u0005\"\u0018\u0010Z\u001a\u0004\u0018\u00010\u0001*\u00020\u00038Æ\u0002¢\u0006\u0006\u001a\u0004\b[\u0010\u0005¨\u0006\\"}, d2 = {"NO_GET", "", "album", "Landroid/support/v4/media/MediaMetadataCompat;", "getAlbum", "(Landroid/support/v4/media/MediaMetadataCompat;)Ljava/lang/String;", "value", "Landroid/support/v4/media/MediaMetadataCompat$Builder;", "(Landroid/support/v4/media/MediaMetadataCompat$Builder;)Ljava/lang/String;", "setAlbum", "(Landroid/support/v4/media/MediaMetadataCompat$Builder;Ljava/lang/String;)V", "albumArt", "Landroid/graphics/Bitmap;", "getAlbumArt", "(Landroid/support/v4/media/MediaMetadataCompat;)Landroid/graphics/Bitmap;", "(Landroid/support/v4/media/MediaMetadataCompat$Builder;)Landroid/graphics/Bitmap;", "setAlbumArt", "(Landroid/support/v4/media/MediaMetadataCompat$Builder;Landroid/graphics/Bitmap;)V", "albumArtUri", "Landroid/net/Uri;", "getAlbumArtUri", "(Landroid/support/v4/media/MediaMetadataCompat;)Landroid/net/Uri;", "setAlbumArtUri", "albumArtist", "getAlbumArtist", "art", "getArt", "artUri", "getArtUri", "artist", "getArtist", "setArtist", "author", "getAuthor", "compilation", "getCompilation", "composer", "getComposer", "date", "getDate", "discNumber", "", "getDiscNumber", "(Landroid/support/v4/media/MediaMetadataCompat;)J", "displayDescription", "getDisplayDescription", "setDisplayDescription", "displayIcon", "getDisplayIcon", "displayIconUri", "getDisplayIconUri", "setDisplayIconUri", "displaySubtitle", "getDisplaySubtitle", "setDisplaySubtitle", "displayTitle", "getDisplayTitle", "setDisplayTitle", "downloadStatus", "getDownloadStatus", "(Landroid/support/v4/media/MediaMetadataCompat$Builder;)J", "setDownloadStatus", "(Landroid/support/v4/media/MediaMetadataCompat$Builder;J)V", "duration", "getDuration", "setDuration", "genre", "getGenre", "setGenre", TtmlNode.ATTR_ID, "getId", "setId", "mediaUri", "getMediaUri", "setMediaUri", "rating", "getRating", "title", "getTitle", "setTitle", "trackCount", "getTrackCount", "setTrackCount", "trackNumber", "getTrackNumber", "setTrackNumber", "userRating", "getUserRating", "writer", "getWriter", "year", "getYear", "thor-1.8.7_release"}, k = 2, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class MediaMetadataCompatExtKt {
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
        String string = mediaMetadataCompat.getString(MediaMetadataCompat.METADATA_KEY_ART_URI);
        Intrinsics.checkNotNullExpressionValue(string, "this.getString(MediaMeta…pat.METADATA_KEY_ART_URI)");
        return Uri.parse(string);
    }

    public static final Bitmap getAlbumArt(MediaMetadataCompat mediaMetadataCompat) {
        Intrinsics.checkNotNullParameter(mediaMetadataCompat, "<this>");
        return mediaMetadataCompat.getBitmap(MediaMetadataCompat.METADATA_KEY_ALBUM_ART);
    }

    public static final Uri getAlbumArtUri(MediaMetadataCompat mediaMetadataCompat) {
        Intrinsics.checkNotNullParameter(mediaMetadataCompat, "<this>");
        String string = mediaMetadataCompat.getString(MediaMetadataCompat.METADATA_KEY_ALBUM_ART_URI);
        Intrinsics.checkNotNullExpressionValue(string, "this.getString(MediaMeta…TADATA_KEY_ALBUM_ART_URI)");
        return Uri.parse(string);
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
        String string = mediaMetadataCompat.getString(MediaMetadataCompat.METADATA_KEY_DISPLAY_ICON_URI);
        Intrinsics.checkNotNullExpressionValue(string, "this.getString(MediaMeta…ATA_KEY_DISPLAY_ICON_URI)");
        return Uri.parse(string);
    }

    public static final Uri getMediaUri(MediaMetadataCompat mediaMetadataCompat) {
        Intrinsics.checkNotNullParameter(mediaMetadataCompat, "<this>");
        String string = mediaMetadataCompat.getString(MediaMetadataCompat.METADATA_KEY_MEDIA_URI);
        Intrinsics.checkNotNullExpressionValue(string, "this.getString(MediaMeta…t.METADATA_KEY_MEDIA_URI)");
        return Uri.parse(string);
    }

    public static final long getDownloadStatus(MediaMetadataCompat mediaMetadataCompat) {
        Intrinsics.checkNotNullParameter(mediaMetadataCompat, "<this>");
        return mediaMetadataCompat.getLong(MediaMetadataCompat.METADATA_KEY_DOWNLOAD_STATUS);
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
}
