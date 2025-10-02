package com.google.android.exoplayer2.util;

import android.app.Activity;
import android.app.UiModeManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Point;
import android.hardware.display.DisplayManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcel;
import android.security.NetworkSecurityPolicy;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Base64;
import android.util.SparseLongArray;
import android.view.Display;
import android.view.WindowManager;
import androidx.exifinterface.media.ExifInterface;
import androidx.recyclerview.widget.ItemTouchHelper;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.extractor.ts.PsExtractor;
import com.google.android.exoplayer2.extractor.ts.TsExtractor;
import com.google.android.exoplayer2.text.ttml.TtmlNode;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.common.base.Ascii;
import com.google.common.base.Charsets;
import com.google.firebase.messaging.Constants;
import com.thor.app.auto.media.AutoNotificationManagerKt;
import com.thor.app.settings.Settings;
import com.thor.basemodule.gui.view.CircleBarView;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Collections;
import java.util.Formatter;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.NoSuchElementException;
import java.util.TimeZone;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.DataFormatException;
import java.util.zip.GZIPOutputStream;
import java.util.zip.Inflater;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.time.TimeZones;
import org.checkerframework.checker.nullness.qual.EnsuresNonNull;
import org.mapstruct.ap.shaded.freemarker.core.FMParserConstants;

/* loaded from: classes.dex */
public final class Util {
    private static final int[] CRC32_BYTES_MSBF;
    private static final int[] CRC8_BYTES_MSBF;
    public static final String DEVICE;
    public static final String DEVICE_DEBUG_INFO;
    public static final byte[] EMPTY_BYTE_ARRAY;
    private static final Pattern ESCAPED_CHARACTER_PATTERN;
    private static final String ISM_DASH_FORMAT_EXTENSION = "format=mpd-time-csf";
    private static final String ISM_HLS_FORMAT_EXTENSION = "format=m3u8-aapl";
    private static final Pattern ISM_URL_PATTERN;
    public static final String MANUFACTURER;
    public static final String MODEL;
    public static final int SDK_INT;
    private static final String TAG = "Util";
    private static final Pattern XS_DATE_TIME_PATTERN;
    private static final Pattern XS_DURATION_PATTERN;
    private static final String[] additionalIsoLanguageReplacements;
    private static final String[] isoLegacyTagReplacements;
    private static HashMap<String, String> languageTagReplacementMap;

    public static long addWithOverflowDefault(long x, long y, long overflowResult) {
        long j = x + y;
        return ((x ^ j) & (y ^ j)) < 0 ? overflowResult : j;
    }

    @EnsuresNonNull({"#1"})
    public static <T> T castNonNull(T value) {
        return value;
    }

    @EnsuresNonNull({"#1"})
    public static <T> T[] castNonNullTypeArray(T[] value) {
        return value;
    }

    public static int compareLong(long left, long right) {
        if (left < right) {
            return -1;
        }
        return left == right ? 0 : 1;
    }

    public static String getAdaptiveMimeTypeForContentType(int contentType) {
        if (contentType == 0) {
            return MimeTypes.APPLICATION_MPD;
        }
        if (contentType == 1) {
            return MimeTypes.APPLICATION_SS;
        }
        if (contentType != 2) {
            return null;
        }
        return MimeTypes.APPLICATION_M3U8;
    }

    public static int getAudioContentTypeForStreamType(int streamType) {
        if (streamType != 0) {
            return (streamType == 1 || streamType == 2 || streamType == 4 || streamType == 5 || streamType == 8) ? 4 : 2;
        }
        return 1;
    }

    public static int getAudioUsageForStreamType(int streamType) {
        if (streamType == 0) {
            return 2;
        }
        if (streamType == 1) {
            return 13;
        }
        if (streamType == 2) {
            return 6;
        }
        int i = 4;
        if (streamType != 4) {
            i = 5;
            if (streamType != 5) {
                return streamType != 8 ? 1 : 3;
            }
        }
        return i;
    }

    public static int getPcmEncoding(int bitDepth) {
        if (bitDepth == 8) {
            return 3;
        }
        if (bitDepth == 16) {
            return 2;
        }
        if (bitDepth == 24) {
            return 536870912;
        }
        if (bitDepth != 32) {
            return 0;
        }
        return C.ENCODING_PCM_32BIT;
    }

    public static int getStreamTypeForAudioUsage(int usage) {
        if (usage == 13) {
            return 1;
        }
        switch (usage) {
            case 2:
                return 0;
            case 3:
                return 8;
            case 4:
                return 4;
            case 5:
            case 7:
            case 8:
            case 9:
            case 10:
                return 5;
            case 6:
                return 2;
            default:
                return 3;
        }
    }

    public static boolean isEncodingHighResolutionPcm(int encoding) {
        return encoding == 536870912 || encoding == 805306368 || encoding == 4;
    }

    public static boolean isEncodingLinearPcm(int encoding) {
        return encoding == 3 || encoding == 2 || encoding == 268435456 || encoding == 536870912 || encoding == 805306368 || encoding == 4;
    }

    public static boolean isLinebreak(int c) {
        return c == 10 || c == 13;
    }

    private static boolean shouldEscapeCharacter(char c) {
        return c == '\"' || c == '%' || c == '*' || c == '/' || c == ':' || c == '<' || c == '\\' || c == '|' || c == '>' || c == '?';
    }

    public static long subtractWithOverflowDefault(long x, long y, long overflowResult) {
        long j = x - y;
        return ((x ^ j) & (y ^ x)) < 0 ? overflowResult : j;
    }

    public static long toUnsignedLong(int x) {
        return x & 4294967295L;
    }

    static {
        int i;
        if (ExifInterface.LATITUDE_SOUTH.equals(Build.VERSION.CODENAME)) {
            i = 31;
        } else {
            i = "R".equals(Build.VERSION.CODENAME) ? 30 : Build.VERSION.SDK_INT;
        }
        SDK_INT = i;
        String str = Build.DEVICE;
        DEVICE = str;
        String str2 = Build.MANUFACTURER;
        MANUFACTURER = str2;
        String str3 = Build.MODEL;
        MODEL = str3;
        DEVICE_DEBUG_INFO = new StringBuilder(String.valueOf(str).length() + 17 + String.valueOf(str3).length() + String.valueOf(str2).length()).append(str).append(", ").append(str3).append(", ").append(str2).append(", ").append(i).toString();
        EMPTY_BYTE_ARRAY = new byte[0];
        XS_DATE_TIME_PATTERN = Pattern.compile("(\\d\\d\\d\\d)\\-(\\d\\d)\\-(\\d\\d)[Tt](\\d\\d):(\\d\\d):(\\d\\d)([\\.,](\\d+))?([Zz]|((\\+|\\-)(\\d?\\d):?(\\d\\d)))?");
        XS_DURATION_PATTERN = Pattern.compile("^(-)?P(([0-9]*)Y)?(([0-9]*)M)?(([0-9]*)D)?(T(([0-9]*)H)?(([0-9]*)M)?(([0-9.]*)S)?)?$");
        ESCAPED_CHARACTER_PATTERN = Pattern.compile("%([A-Fa-f0-9]{2})");
        ISM_URL_PATTERN = Pattern.compile(".*\\.isml?(?:/(manifest(.*))?)?");
        additionalIsoLanguageReplacements = new String[]{"alb", "sq", "arm", "hy", "baq", "eu", "bur", "my", "tib", "bo", "chi", "zh", "cze", "cs", "dut", "nl", "ger", Settings.LANGUAGE_CODE_DE, "gre", "el", "fre", "fr", "geo", "ka", "ice", "is", "mac", "mk", "mao", "mi", "may", "ms", "per", "fa", "rum", "ro", "scc", "hbs-srp", "slo", "sk", "wel", "cy", TtmlNode.ATTR_ID, "ms-ind", "iw", "he", "heb", "he", "ji", "yi", "in", "ms-ind", "ind", "ms-ind", "nb", "no-nob", "nob", "no-nob", "nn", "no-nno", "nno", "no-nno", "tw", "ak-twi", "twi", "ak-twi", "bs", "hbs-bos", "bos", "hbs-bos", "hr", "hbs-hrv", "hrv", "hbs-hrv", "sr", "hbs-srp", "srp", "hbs-srp", "cmn", "zh-cmn", "hak", "zh-hak", "nan", "zh-nan", "hsn", "zh-hsn"};
        isoLegacyTagReplacements = new String[]{"i-lux", "lb", "i-hak", "zh-hak", "i-navajo", "nv", "no-bok", "no-nob", "no-nyn", "no-nno", "zh-guoyu", "zh-cmn", "zh-hakka", "zh-hak", "zh-min-nan", "zh-nan", "zh-xiang", "zh-hsn"};
        CRC32_BYTES_MSBF = new int[]{0, 79764919, 159529838, 222504665, 319059676, 398814059, 445009330, 507990021, 638119352, 583659535, 797628118, 726387553, 890018660, 835552979, 1015980042, 944750013, 1276238704, 1221641927, 1167319070, 1095957929, 1595256236, 1540665371, 1452775106, 1381403509, 1780037320, 1859660671, 1671105958, 1733955601, 2031960084, 2111593891, 1889500026, 1952343757, -1742489888, -1662866601, -1851683442, -1788833735, -1960329156, -1880695413, -2103051438, -2040207643, -1104454824, -1159051537, -1213636554, -1284997759, -1389417084, -1444007885, -1532160278, -1603531939, -734892656, -789352409, -575645954, -646886583, -952755380, -1007220997, -827056094, -898286187, -231047128, -151282273, -71779514, -8804623, -515967244, -436212925, -390279782, -327299027, 881225847, 809987520, 1023691545, 969234094, 662832811, 591600412, 771767749, 717299826, 311336399, 374308984, 453813921, 533576470, 25881363, 88864420, 134795389, 214552010, 2023205639, 2086057648, 1897238633, 1976864222, 1804852699, 1867694188, 1645340341, 1724971778, 1587496639, 1516133128, 1461550545, 1406951526, 1302016099, 1230646740, 1142491917, 1087903418, -1398421865, -1469785312, -1524105735, -1578704818, -1079922613, -1151291908, -1239184603, -1293773166, -1968362705, -1905510760, -2094067647, -2014441994, -1716953613, -1654112188, -1876203875, -1796572374, -525066777, -462094256, -382327159, -302564546, -206542021, -143559028, -97365931, -17609246, -960696225, -1031934488, -817968335, -872425850, -709327229, -780559564, -600130067, -654598054, 1762451694, 1842216281, 1619975040, 1682949687, 2047383090, 2127137669, 1938468188, 2001449195, 1325665622, 1271206113, 1183200824, 1111960463, 1543535498, 1489069629, 1434599652, 1363369299, 622672798, 568075817, 748617968, 677256519, 907627842, 853037301, 1067152940, 995781531, 51762726, 131386257, 177728840, 240578815, 269590778, 349224269, 429104020, 491947555, -248556018, -168932423, -122852000, -60002089, -500490030, -420856475, -341238852, -278395381, -685261898, -739858943, -559578920, -630940305, -1004286614, -1058877219, -845023740, -916395085, -1119974018, -1174433591, -1262701040, -1333941337, -1371866206, -1426332139, -1481064244, -1552294533, -1690935098, -1611170447, -1833673816, -1770699233, -2009983462, -1930228819, -2119160460, -2056179517, 1569362073, 1498123566, 1409854455, 1355396672, 1317987909, 1246755826, 1192025387, 1137557660, 2072149281, 2135122070, 1912620623, 1992383480, 1753615357, 1816598090, 1627664531, 1707420964, 295390185, 358241886, 404320391, 483945776, 43990325, 106832002, 186451547, 266083308, 932423249, 861060070, 1041341759, 986742920, 613929101, 542559546, 756411363, 701822548, -978770311, -1050133554, -869589737, -924188512, -693284699, -764654318, -550540341, -605129092, -475935807, -413084042, -366743377, -287118056, -257573603, -194731862, -114850189, -35218492, -1984365303, -1921392450, -2143631769, -2063868976, -1698919467, -1635936670, -1824608069, -1744851700, -1347415887, -1418654458, -1506661409, -1561119128, -1129027987, -1200260134, -1254728445, -1309196108};
        CRC8_BYTES_MSBF = new int[]{0, 7, 14, 9, 28, 27, 18, 21, 56, 63, 54, 49, 36, 35, 42, 45, 112, 119, 126, 121, 108, 107, 98, 101, 72, 79, 70, 65, 84, 83, 90, 93, 224, 231, 238, 233, 252, 251, 242, 245, 216, 223, 214, 209, 196, 195, 202, 205, AutoNotificationManagerKt.NOTIFICATION_LARGE_ICON_SIZE, 151, 158, 153, 140, 139, 130, 133, 168, 175, 166, 161, CircleBarView.TWO_QUARTER, 179, 186, PsExtractor.PRIVATE_STREAM_1, 199, PsExtractor.AUDIO_STREAM, 201, 206, 219, 220, 213, 210, 255, 248, 241, 246, 227, 228, 237, 234, 183, 176, 185, 190, 171, TsExtractor.TS_STREAM_TYPE_AC4, 165, 162, 143, 136, 129, 134, 147, 148, 157, 154, 39, 32, 41, 46, 59, 60, 53, 50, 31, 24, 17, 22, 3, 4, 13, 10, 87, 80, 89, 94, 75, 76, 69, 66, 111, 104, 97, 102, 115, 116, 125, 122, 137, 142, 135, 128, 149, 146, 155, 156, 177, 182, 191, 184, 173, 170, 163, 164, 249, 254, 247, PsExtractor.VIDEO_STREAM_MASK, 229, 226, 235, 236, 193, 198, 207, 200, 221, 218, 211, 212, 105, 110, 103, 96, 117, 114, 123, 124, 81, 86, 95, 88, 77, 74, 67, 68, 25, 30, 23, 16, 5, 2, 11, 12, 33, 38, 47, 40, 61, 58, 51, 52, 78, 73, 64, 71, 82, 85, 92, 91, 118, 113, 120, 127, 106, 109, 100, 99, 62, 57, 48, 55, 34, 37, 44, 43, 6, 1, 8, 15, 26, 29, 20, 19, 174, 169, 160, 167, 178, 181, TsExtractor.TS_PACKET_SIZE, 187, 150, 145, 152, 159, TsExtractor.TS_STREAM_TYPE_DTS, 141, FMParserConstants.TERSE_COMMENT_END, FMParserConstants.TERMINATING_EXCLAM, 222, 217, 208, 215, 194, 197, 204, 203, 230, 225, 232, 239, ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION, 253, 244, 243};
    }

    private Util() {
    }

    public static byte[] toByteArray(InputStream inputStream) throws IOException {
        byte[] bArr = new byte[4096];
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        while (true) {
            int i = inputStream.read(bArr);
            if (i != -1) {
                byteArrayOutputStream.write(bArr, 0, i);
            } else {
                return byteArrayOutputStream.toByteArray();
            }
        }
    }

    public static ComponentName startForegroundService(Context context, Intent intent) {
        if (SDK_INT >= 26) {
            return context.startForegroundService(intent);
        }
        return context.startService(intent);
    }

    public static boolean maybeRequestReadExternalStoragePermission(Activity activity, Uri... uris) {
        if (SDK_INT < 23) {
            return false;
        }
        for (Uri uri : uris) {
            if (isLocalFileUri(uri)) {
                return requestExternalStoragePermission(activity);
            }
        }
        return false;
    }

    public static boolean maybeRequestReadExternalStoragePermission(Activity activity, MediaItem... mediaItems) {
        if (SDK_INT < 23) {
            return false;
        }
        for (MediaItem mediaItem : mediaItems) {
            if (mediaItem.playbackProperties != null) {
                if (isLocalFileUri(mediaItem.playbackProperties.uri)) {
                    return requestExternalStoragePermission(activity);
                }
                for (int i = 0; i < mediaItem.playbackProperties.subtitles.size(); i++) {
                    if (isLocalFileUri(mediaItem.playbackProperties.subtitles.get(i).uri)) {
                        return requestExternalStoragePermission(activity);
                    }
                }
            }
        }
        return false;
    }

    public static boolean checkCleartextTrafficPermitted(MediaItem... mediaItems) {
        if (SDK_INT < 24) {
            return true;
        }
        for (MediaItem mediaItem : mediaItems) {
            if (mediaItem.playbackProperties != null) {
                if (isTrafficRestricted(mediaItem.playbackProperties.uri)) {
                    return false;
                }
                for (int i = 0; i < mediaItem.playbackProperties.subtitles.size(); i++) {
                    if (isTrafficRestricted(mediaItem.playbackProperties.subtitles.get(i).uri)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public static boolean isLocalFileUri(Uri uri) {
        String scheme = uri.getScheme();
        return TextUtils.isEmpty(scheme) || "file".equals(scheme);
    }

    public static boolean areEqual(Object o1, Object o2) {
        if (o1 == null) {
            return o2 == null;
        }
        return o1.equals(o2);
    }

    public static boolean contains(Object[] items, Object item) {
        for (Object obj : items) {
            if (areEqual(obj, item)) {
                return true;
            }
        }
        return false;
    }

    public static <T> void removeRange(List<T> list, int fromIndex, int toIndex) {
        if (fromIndex < 0 || toIndex > list.size() || fromIndex > toIndex) {
            throw new IllegalArgumentException();
        }
        if (fromIndex != toIndex) {
            list.subList(fromIndex, toIndex).clear();
        }
    }

    public static <T> T[] nullSafeArrayCopy(T[] tArr, int i) {
        Assertions.checkArgument(i <= tArr.length);
        return (T[]) Arrays.copyOf(tArr, i);
    }

    public static <T> T[] nullSafeArrayCopyOfRange(T[] tArr, int i, int i2) {
        Assertions.checkArgument(i >= 0);
        Assertions.checkArgument(i2 <= tArr.length);
        return (T[]) Arrays.copyOfRange(tArr, i, i2);
    }

    public static <T> T[] nullSafeArrayAppend(T[] tArr, T t) {
        Object[] objArrCopyOf = Arrays.copyOf(tArr, tArr.length + 1);
        objArrCopyOf[tArr.length] = t;
        return (T[]) castNonNullTypeArray(objArrCopyOf);
    }

    public static <T> T[] nullSafeArrayConcatenation(T[] tArr, T[] tArr2) {
        T[] tArr3 = (T[]) Arrays.copyOf(tArr, tArr.length + tArr2.length);
        System.arraycopy(tArr2, 0, tArr3, tArr.length, tArr2.length);
        return tArr3;
    }

    public static <T> void nullSafeListToArray(List<T> list, T[] array) {
        Assertions.checkState(list.size() == array.length);
        list.toArray(array);
    }

    public static Handler createHandlerForCurrentLooper() {
        return createHandlerForCurrentLooper(null);
    }

    public static Handler createHandlerForCurrentLooper(Handler.Callback callback) {
        return createHandler((Looper) Assertions.checkStateNotNull(Looper.myLooper()), callback);
    }

    public static Handler createHandlerForCurrentOrMainLooper() {
        return createHandlerForCurrentOrMainLooper(null);
    }

    public static Handler createHandlerForCurrentOrMainLooper(Handler.Callback callback) {
        return createHandler(getCurrentOrMainLooper(), callback);
    }

    public static Handler createHandler(Looper looper, Handler.Callback callback) {
        return new Handler(looper, callback);
    }

    public static boolean postOrRun(Handler handler, Runnable runnable) {
        if (!handler.getLooper().getThread().isAlive()) {
            return false;
        }
        if (handler.getLooper() == Looper.myLooper()) {
            runnable.run();
            return true;
        }
        return handler.post(runnable);
    }

    public static Looper getCurrentOrMainLooper() {
        Looper looperMyLooper = Looper.myLooper();
        return looperMyLooper != null ? looperMyLooper : Looper.getMainLooper();
    }

    static /* synthetic */ Thread lambda$newSingleThreadExecutor$0(String str, Runnable runnable) {
        return new Thread(runnable, str);
    }

    public static ExecutorService newSingleThreadExecutor(final String threadName) {
        return Executors.newSingleThreadExecutor(new ThreadFactory() { // from class: com.google.android.exoplayer2.util.Util$$ExternalSyntheticLambda0
            @Override // java.util.concurrent.ThreadFactory
            public final Thread newThread(Runnable runnable) {
                return Util.lambda$newSingleThreadExecutor$0(threadName, runnable);
            }
        });
    }

    public static byte[] readToEnd(DataSource dataSource) throws IOException {
        byte[] bArrCopyOf = new byte[1024];
        int i = 0;
        int i2 = 0;
        while (i != -1) {
            if (i2 == bArrCopyOf.length) {
                bArrCopyOf = Arrays.copyOf(bArrCopyOf, bArrCopyOf.length * 2);
            }
            i = dataSource.read(bArrCopyOf, i2, bArrCopyOf.length - i2);
            if (i != -1) {
                i2 += i;
            }
        }
        return Arrays.copyOf(bArrCopyOf, i2);
    }

    public static byte[] readExactly(DataSource dataSource, int length) throws IOException {
        byte[] bArr = new byte[length];
        int i = 0;
        while (i < length) {
            int i2 = dataSource.read(bArr, i, length - i);
            if (i2 == -1) {
                throw new IllegalStateException(new StringBuilder(56).append("Not enough data could be read: ").append(i).append(" < ").append(length).toString());
            }
            i += i2;
        }
        return bArr;
    }

    public static void closeQuietly(DataSource dataSource) {
        if (dataSource != null) {
            try {
                dataSource.close();
            } catch (IOException unused) {
            }
        }
    }

    public static void closeQuietly(Closeable closeable) throws IOException {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException unused) {
            }
        }
    }

    public static boolean readBoolean(Parcel parcel) {
        return parcel.readInt() != 0;
    }

    public static void writeBoolean(Parcel parcel, boolean z) {
        parcel.writeInt(z ? 1 : 0);
    }

    public static String getLocaleLanguageTag(Locale locale) {
        return SDK_INT >= 21 ? getLocaleLanguageTagV21(locale) : locale.toString();
    }

    public static String normalizeLanguageCode(String language) {
        if (language == null) {
            return null;
        }
        String strReplace = language.replace('_', '-');
        if (!strReplace.isEmpty() && !strReplace.equals(C.LANGUAGE_UNDETERMINED)) {
            language = strReplace;
        }
        String lowerCase = Ascii.toLowerCase(language);
        String str = splitAtFirst(lowerCase, "-")[0];
        if (languageTagReplacementMap == null) {
            languageTagReplacementMap = createIsoLanguageReplacementMap();
        }
        String str2 = languageTagReplacementMap.get(str);
        if (str2 != null) {
            String strValueOf = String.valueOf(str2);
            String strValueOf2 = String.valueOf(lowerCase.substring(str.length()));
            lowerCase = strValueOf2.length() != 0 ? strValueOf.concat(strValueOf2) : new String(strValueOf);
            str = str2;
        }
        return (BooleanUtils.NO.equals(str) || "i".equals(str) || "zh".equals(str)) ? maybeReplaceLegacyLanguageTags(lowerCase) : lowerCase;
    }

    public static String fromUtf8Bytes(byte[] bytes) {
        return new String(bytes, Charsets.UTF_8);
    }

    public static String fromUtf8Bytes(byte[] bytes, int offset, int length) {
        return new String(bytes, offset, length, Charsets.UTF_8);
    }

    public static byte[] getUtf8Bytes(String value) {
        return value.getBytes(Charsets.UTF_8);
    }

    public static String[] split(String value, String regex) {
        return value.split(regex, -1);
    }

    public static String[] splitAtFirst(String value, String regex) {
        return value.split(regex, 2);
    }

    public static String formatInvariant(String format, Object... args) {
        return String.format(Locale.US, format, args);
    }

    public static int ceilDivide(int numerator, int denominator) {
        return ((numerator + denominator) - 1) / denominator;
    }

    public static long ceilDivide(long numerator, long denominator) {
        return ((numerator + denominator) - 1) / denominator;
    }

    public static int constrainValue(int value, int min, int max) {
        return Math.max(min, Math.min(value, max));
    }

    public static long constrainValue(long value, long min, long max) {
        return Math.max(min, Math.min(value, max));
    }

    public static float constrainValue(float value, float min, float max) {
        return Math.max(min, Math.min(value, max));
    }

    public static int linearSearch(int[] array, int value) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == value) {
                return i;
            }
        }
        return -1;
    }

    public static int linearSearch(long[] array, long value) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == value) {
                return i;
            }
        }
        return -1;
    }

    public static int binarySearchFloor(int[] array, int value, boolean inclusive, boolean stayInBounds) {
        int i;
        int iBinarySearch = Arrays.binarySearch(array, value);
        if (iBinarySearch < 0) {
            i = -(iBinarySearch + 2);
        } else {
            do {
                iBinarySearch--;
                if (iBinarySearch < 0) {
                    break;
                }
            } while (array[iBinarySearch] == value);
            i = inclusive ? iBinarySearch + 1 : iBinarySearch;
        }
        return stayInBounds ? Math.max(0, i) : i;
    }

    public static int binarySearchFloor(long[] array, long value, boolean inclusive, boolean stayInBounds) {
        int i;
        int iBinarySearch = Arrays.binarySearch(array, value);
        if (iBinarySearch < 0) {
            i = -(iBinarySearch + 2);
        } else {
            do {
                iBinarySearch--;
                if (iBinarySearch < 0) {
                    break;
                }
            } while (array[iBinarySearch] == value);
            i = inclusive ? iBinarySearch + 1 : iBinarySearch;
        }
        return stayInBounds ? Math.max(0, i) : i;
    }

    public static <T extends Comparable<? super T>> int binarySearchFloor(List<? extends Comparable<? super T>> list, T value, boolean inclusive, boolean stayInBounds) {
        int i;
        int iBinarySearch = Collections.binarySearch(list, value);
        if (iBinarySearch < 0) {
            i = -(iBinarySearch + 2);
        } else {
            do {
                iBinarySearch--;
                if (iBinarySearch < 0) {
                    break;
                }
            } while (list.get(iBinarySearch).compareTo(value) == 0);
            i = inclusive ? iBinarySearch + 1 : iBinarySearch;
        }
        return stayInBounds ? Math.max(0, i) : i;
    }

    public static int binarySearchFloor(LongArray longArray, long value, boolean inclusive, boolean stayInBounds) {
        int i;
        int size = longArray.size() - 1;
        int i2 = 0;
        while (i2 <= size) {
            int i3 = (i2 + size) >>> 1;
            if (longArray.get(i3) < value) {
                i2 = i3 + 1;
            } else {
                size = i3 - 1;
            }
        }
        if (inclusive && (i = size + 1) < longArray.size() && longArray.get(i) == value) {
            return i;
        }
        if (stayInBounds && size == -1) {
            return 0;
        }
        return size;
    }

    public static int binarySearchCeil(int[] array, int value, boolean inclusive, boolean stayInBounds) {
        int i;
        int iBinarySearch = Arrays.binarySearch(array, value);
        if (iBinarySearch < 0) {
            i = ~iBinarySearch;
        } else {
            do {
                iBinarySearch++;
                if (iBinarySearch >= array.length) {
                    break;
                }
            } while (array[iBinarySearch] == value);
            i = inclusive ? iBinarySearch - 1 : iBinarySearch;
        }
        return stayInBounds ? Math.min(array.length - 1, i) : i;
    }

    public static int binarySearchCeil(long[] array, long value, boolean inclusive, boolean stayInBounds) {
        int i;
        int iBinarySearch = Arrays.binarySearch(array, value);
        if (iBinarySearch < 0) {
            i = ~iBinarySearch;
        } else {
            do {
                iBinarySearch++;
                if (iBinarySearch >= array.length) {
                    break;
                }
            } while (array[iBinarySearch] == value);
            i = inclusive ? iBinarySearch - 1 : iBinarySearch;
        }
        return stayInBounds ? Math.min(array.length - 1, i) : i;
    }

    public static <T extends Comparable<? super T>> int binarySearchCeil(List<? extends Comparable<? super T>> list, T value, boolean inclusive, boolean stayInBounds) {
        int i;
        int iBinarySearch = Collections.binarySearch(list, value);
        if (iBinarySearch < 0) {
            i = ~iBinarySearch;
        } else {
            int size = list.size();
            do {
                iBinarySearch++;
                if (iBinarySearch >= size) {
                    break;
                }
            } while (list.get(iBinarySearch).compareTo(value) == 0);
            i = inclusive ? iBinarySearch - 1 : iBinarySearch;
        }
        return stayInBounds ? Math.min(list.size() - 1, i) : i;
    }

    public static long minValue(SparseLongArray sparseLongArray) {
        if (sparseLongArray.size() == 0) {
            throw new NoSuchElementException();
        }
        long jMin = Long.MAX_VALUE;
        for (int i = 0; i < sparseLongArray.size(); i++) {
            jMin = Math.min(jMin, sparseLongArray.valueAt(i));
        }
        return jMin;
    }

    public static long parseXsDuration(String value) {
        Matcher matcher = XS_DURATION_PATTERN.matcher(value);
        if (matcher.matches()) {
            boolean zIsEmpty = true ^ TextUtils.isEmpty(matcher.group(1));
            String strGroup = matcher.group(3);
            double d = strGroup != null ? Double.parseDouble(strGroup) * 3.1556908E7d : 0.0d;
            String strGroup2 = matcher.group(5);
            double d2 = d + (strGroup2 != null ? Double.parseDouble(strGroup2) * 2629739.0d : 0.0d);
            String strGroup3 = matcher.group(7);
            double d3 = d2 + (strGroup3 != null ? Double.parseDouble(strGroup3) * 86400.0d : 0.0d);
            String strGroup4 = matcher.group(10);
            double d4 = d3 + (strGroup4 != null ? Double.parseDouble(strGroup4) * 3600.0d : 0.0d);
            String strGroup5 = matcher.group(12);
            double d5 = d4 + (strGroup5 != null ? Double.parseDouble(strGroup5) * 60.0d : 0.0d);
            String strGroup6 = matcher.group(14);
            long j = (long) ((d5 + (strGroup6 != null ? Double.parseDouble(strGroup6) : 0.0d)) * 1000.0d);
            return zIsEmpty ? -j : j;
        }
        return (long) (Double.parseDouble(value) * 3600.0d * 1000.0d);
    }

    public static long parseXsDateTime(String value) throws ParserException {
        Matcher matcher = XS_DATE_TIME_PATTERN.matcher(value);
        if (!matcher.matches()) {
            String strValueOf = String.valueOf(value);
            throw ParserException.createForMalformedContainer(strValueOf.length() != 0 ? "Invalid date/time format: ".concat(strValueOf) : new String("Invalid date/time format: "), null);
        }
        int i = 0;
        if (matcher.group(9) != null && !matcher.group(9).equalsIgnoreCase("Z")) {
            i = (Integer.parseInt(matcher.group(12)) * 60) + Integer.parseInt(matcher.group(13));
            if ("-".equals(matcher.group(11))) {
                i *= -1;
            }
        }
        GregorianCalendar gregorianCalendar = new GregorianCalendar(TimeZone.getTimeZone(TimeZones.GMT_ID));
        gregorianCalendar.clear();
        gregorianCalendar.set(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)) - 1, Integer.parseInt(matcher.group(3)), Integer.parseInt(matcher.group(4)), Integer.parseInt(matcher.group(5)), Integer.parseInt(matcher.group(6)));
        if (!TextUtils.isEmpty(matcher.group(8))) {
            String strValueOf2 = String.valueOf(matcher.group(8));
            gregorianCalendar.set(14, new BigDecimal(strValueOf2.length() != 0 ? "0.".concat(strValueOf2) : new String("0.")).movePointRight(3).intValue());
        }
        long timeInMillis = gregorianCalendar.getTimeInMillis();
        return i != 0 ? timeInMillis - (i * 60000) : timeInMillis;
    }

    public static long scaleLargeTimestamp(long timestamp, long multiplier, long divisor) {
        if (divisor >= multiplier && divisor % multiplier == 0) {
            return timestamp / (divisor / multiplier);
        }
        if (divisor < multiplier && multiplier % divisor == 0) {
            return timestamp * (multiplier / divisor);
        }
        return (long) (timestamp * (multiplier / divisor));
    }

    public static long[] scaleLargeTimestamps(List<Long> timestamps, long multiplier, long divisor) {
        int size = timestamps.size();
        long[] jArr = new long[size];
        int i = 0;
        if (divisor >= multiplier && divisor % multiplier == 0) {
            long j = divisor / multiplier;
            while (i < size) {
                jArr[i] = timestamps.get(i).longValue() / j;
                i++;
            }
        } else if (divisor >= multiplier || multiplier % divisor != 0) {
            double d = multiplier / divisor;
            while (i < size) {
                jArr[i] = (long) (timestamps.get(i).longValue() * d);
                i++;
            }
        } else {
            long j2 = multiplier / divisor;
            while (i < size) {
                jArr[i] = timestamps.get(i).longValue() * j2;
                i++;
            }
        }
        return jArr;
    }

    public static void scaleLargeTimestampsInPlace(long[] timestamps, long multiplier, long divisor) {
        int i = 0;
        if (divisor >= multiplier && divisor % multiplier == 0) {
            long j = divisor / multiplier;
            while (i < timestamps.length) {
                timestamps[i] = timestamps[i] / j;
                i++;
            }
            return;
        }
        if (divisor < multiplier && multiplier % divisor == 0) {
            long j2 = multiplier / divisor;
            while (i < timestamps.length) {
                timestamps[i] = timestamps[i] * j2;
                i++;
            }
            return;
        }
        double d = multiplier / divisor;
        while (i < timestamps.length) {
            timestamps[i] = (long) (timestamps[i] * d);
            i++;
        }
    }

    public static long getMediaDurationForPlayoutDuration(long playoutDuration, float speed) {
        return speed == 1.0f ? playoutDuration : Math.round(playoutDuration * speed);
    }

    public static long getPlayoutDurationForMediaDuration(long mediaDuration, float speed) {
        return speed == 1.0f ? mediaDuration : Math.round(mediaDuration / speed);
    }

    public static int getIntegerCodeForString(String string) {
        int length = string.length();
        Assertions.checkArgument(length <= 4);
        int iCharAt = 0;
        for (int i = 0; i < length; i++) {
            iCharAt = (iCharAt << 8) | string.charAt(i);
        }
        return iCharAt;
    }

    public static long toLong(int mostSignificantBits, int leastSignificantBits) {
        return toUnsignedLong(leastSignificantBits) | (toUnsignedLong(mostSignificantBits) << 32);
    }

    public static CharSequence truncateAscii(CharSequence sequence, int maxLength) {
        return sequence.length() <= maxLength ? sequence : sequence.subSequence(0, maxLength);
    }

    public static byte[] getBytesFromHexString(String hexString) {
        int length = hexString.length() / 2;
        byte[] bArr = new byte[length];
        for (int i = 0; i < length; i++) {
            int i2 = i * 2;
            bArr[i] = (byte) ((Character.digit(hexString.charAt(i2), 16) << 4) + Character.digit(hexString.charAt(i2 + 1), 16));
        }
        return bArr;
    }

    public static String toHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        for (int i = 0; i < bytes.length; i++) {
            sb.append(Character.forDigit((bytes[i] >> 4) & 15, 16)).append(Character.forDigit(bytes[i] & Ascii.SI, 16));
        }
        return sb.toString();
    }

    public static String getCommaDelimitedSimpleClassNames(Object[] objects) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < objects.length; i++) {
            sb.append(objects[i].getClass().getSimpleName());
            if (i < objects.length - 1) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }

    public static String getUserAgent(Context context, String applicationName) {
        String str;
        try {
            str = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException unused) {
            str = "?";
        }
        String str2 = Build.VERSION.RELEASE;
        return new StringBuilder(String.valueOf(applicationName).length() + 38 + String.valueOf(str).length() + String.valueOf(str2).length()).append(applicationName).append("/").append(str).append(" (Linux;Android ").append(str2).append(") ExoPlayerLib/2.15.0").toString();
    }

    public static int getCodecCountOfType(String codecs, int trackType) {
        int i = 0;
        for (String str : splitCodecs(codecs)) {
            if (trackType == MimeTypes.getTrackTypeOfCodec(str)) {
                i++;
            }
        }
        return i;
    }

    public static String getCodecsOfType(String codecs, int trackType) {
        String[] strArrSplitCodecs = splitCodecs(codecs);
        if (strArrSplitCodecs.length == 0) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (String str : strArrSplitCodecs) {
            if (trackType == MimeTypes.getTrackTypeOfCodec(str)) {
                if (sb.length() > 0) {
                    sb.append(",");
                }
                sb.append(str);
            }
        }
        if (sb.length() > 0) {
            return sb.toString();
        }
        return null;
    }

    public static String[] splitCodecs(String codecs) {
        return TextUtils.isEmpty(codecs) ? new String[0] : split(codecs.trim(), "(\\s*,\\s*)");
    }

    public static Format getPcmFormat(int pcmEncoding, int channels, int sampleRate) {
        return new Format.Builder().setSampleMimeType(MimeTypes.AUDIO_RAW).setChannelCount(channels).setSampleRate(sampleRate).setPcmEncoding(pcmEncoding).build();
    }

    public static int getAudioTrackChannelConfig(int channelCount) {
        switch (channelCount) {
            case 1:
                return 4;
            case 2:
                return 12;
            case 3:
                return 28;
            case 4:
                return 204;
            case 5:
                return 220;
            case 6:
                return 252;
            case 7:
                return 1276;
            case 8:
                int i = SDK_INT;
                return (i < 23 && i < 21) ? 0 : 6396;
            default:
                return 0;
        }
    }

    public static int getPcmFrameSize(int pcmEncoding, int channelCount) {
        if (pcmEncoding != 2) {
            if (pcmEncoding == 3) {
                return channelCount;
            }
            if (pcmEncoding != 4) {
                if (pcmEncoding != 268435456) {
                    if (pcmEncoding == 536870912) {
                        return channelCount * 3;
                    }
                    if (pcmEncoding != 805306368) {
                        throw new IllegalArgumentException();
                    }
                }
            }
            return channelCount * 4;
        }
        return channelCount * 2;
    }

    public static UUID getDrmUuid(String drmScheme) {
        String lowerCase = Ascii.toLowerCase(drmScheme);
        lowerCase.hashCode();
        switch (lowerCase) {
            case "playready":
                return C.PLAYREADY_UUID;
            case "widevine":
                return C.WIDEVINE_UUID;
            case "clearkey":
                return C.CLEARKEY_UUID;
            default:
                try {
                    return UUID.fromString(drmScheme);
                } catch (RuntimeException unused) {
                    return null;
                }
        }
    }

    public static int inferContentType(Uri uri, String overrideExtension) {
        if (TextUtils.isEmpty(overrideExtension)) {
            return inferContentType(uri);
        }
        String strValueOf = String.valueOf(overrideExtension);
        return inferContentType(strValueOf.length() != 0 ? ".".concat(strValueOf) : new String("."));
    }

    public static int inferContentType(Uri uri) {
        String scheme = uri.getScheme();
        if (scheme != null && Ascii.equalsIgnoreCase("rtsp", scheme)) {
            return 3;
        }
        String path = uri.getPath();
        if (path == null) {
            return 4;
        }
        return inferContentType(path);
    }

    public static int inferContentType(String fileName) {
        String lowerCase = Ascii.toLowerCase(fileName);
        if (lowerCase.endsWith(".mpd")) {
            return 0;
        }
        if (lowerCase.endsWith(".m3u8")) {
            return 2;
        }
        Matcher matcher = ISM_URL_PATTERN.matcher(lowerCase);
        if (!matcher.matches()) {
            return 4;
        }
        String strGroup = matcher.group(2);
        if (strGroup == null) {
            return 1;
        }
        if (strGroup.contains(ISM_DASH_FORMAT_EXTENSION)) {
            return 0;
        }
        return strGroup.contains(ISM_HLS_FORMAT_EXTENSION) ? 2 : 1;
    }

    public static int inferContentTypeForUriAndMimeType(Uri uri, String mimeType) {
        if (mimeType == null) {
            return inferContentType(uri);
        }
        mimeType.hashCode();
        switch (mimeType) {
            case "application/x-mpegURL":
                return 2;
            case "application/vnd.ms-sstr+xml":
                return 1;
            case "application/dash+xml":
                return 0;
            case "application/x-rtsp":
                return 3;
            default:
                return 4;
        }
    }

    public static Uri fixSmoothStreamingIsmManifestUri(Uri uri) {
        String path = uri.getPath();
        if (path == null) {
            return uri;
        }
        Matcher matcher = ISM_URL_PATTERN.matcher(Ascii.toLowerCase(path));
        return (matcher.matches() && matcher.group(1) == null) ? Uri.withAppendedPath(uri, "Manifest") : uri;
    }

    public static String getStringForTime(StringBuilder builder, Formatter formatter, long timeMs) {
        if (timeMs == C.TIME_UNSET) {
            timeMs = 0;
        }
        String str = timeMs < 0 ? "-" : "";
        long jAbs = (Math.abs(timeMs) + 500) / 1000;
        long j = jAbs % 60;
        long j2 = (jAbs / 60) % 60;
        long j3 = jAbs / 3600;
        builder.setLength(0);
        return j3 > 0 ? formatter.format("%s%d:%02d:%02d", str, Long.valueOf(j3), Long.valueOf(j2), Long.valueOf(j)).toString() : formatter.format("%s%02d:%02d", str, Long.valueOf(j2), Long.valueOf(j)).toString();
    }

    public static String escapeFileName(String fileName) {
        int length = fileName.length();
        int i = 0;
        int i2 = 0;
        for (int i3 = 0; i3 < length; i3++) {
            if (shouldEscapeCharacter(fileName.charAt(i3))) {
                i2++;
            }
        }
        if (i2 == 0) {
            return fileName;
        }
        StringBuilder sb = new StringBuilder((i2 * 2) + length);
        while (i2 > 0) {
            int i4 = i + 1;
            char cCharAt = fileName.charAt(i);
            if (shouldEscapeCharacter(cCharAt)) {
                sb.append('%').append(Integer.toHexString(cCharAt));
                i2--;
            } else {
                sb.append(cCharAt);
            }
            i = i4;
        }
        if (i < length) {
            sb.append((CharSequence) fileName, i, length);
        }
        return sb.toString();
    }

    public static String unescapeFileName(String fileName) {
        int length = fileName.length();
        int iEnd = 0;
        int i = 0;
        for (int i2 = 0; i2 < length; i2++) {
            if (fileName.charAt(i2) == '%') {
                i++;
            }
        }
        if (i == 0) {
            return fileName;
        }
        int i3 = length - (i * 2);
        StringBuilder sb = new StringBuilder(i3);
        Matcher matcher = ESCAPED_CHARACTER_PATTERN.matcher(fileName);
        while (i > 0 && matcher.find()) {
            sb.append((CharSequence) fileName, iEnd, matcher.start()).append((char) Integer.parseInt((String) Assertions.checkNotNull(matcher.group(1)), 16));
            iEnd = matcher.end();
            i--;
        }
        if (iEnd < length) {
            sb.append((CharSequence) fileName, iEnd, length);
        }
        if (sb.length() != i3) {
            return null;
        }
        return sb.toString();
    }

    public static Uri getDataUriForString(String mimeType, String data) {
        String strEncodeToString = Base64.encodeToString(data.getBytes(), 2);
        return Uri.parse(new StringBuilder(String.valueOf(mimeType).length() + 13 + String.valueOf(strEncodeToString).length()).append("data:").append(mimeType).append(";base64,").append(strEncodeToString).toString());
    }

    public static void sneakyThrow(Throwable t) throws Throwable {
        sneakyThrowInternal(t);
    }

    private static <T extends Throwable> void sneakyThrowInternal(Throwable t) throws Throwable {
        throw t;
    }

    public static void recursiveDelete(File fileOrDirectory) {
        File[] fileArrListFiles = fileOrDirectory.listFiles();
        if (fileArrListFiles != null) {
            for (File file : fileArrListFiles) {
                recursiveDelete(file);
            }
        }
        fileOrDirectory.delete();
    }

    public static File createTempDirectory(Context context, String prefix) throws IOException {
        File fileCreateTempFile = createTempFile(context, prefix);
        fileCreateTempFile.delete();
        fileCreateTempFile.mkdir();
        return fileCreateTempFile;
    }

    public static File createTempFile(Context context, String prefix) throws IOException {
        return File.createTempFile(prefix, null, (File) Assertions.checkNotNull(context.getCacheDir()));
    }

    public static int crc32(byte[] bytes, int start, int end, int initialValue) {
        while (start < end) {
            initialValue = CRC32_BYTES_MSBF[((initialValue >>> 24) ^ (bytes[start] & 255)) & 255] ^ (initialValue << 8);
            start++;
        }
        return initialValue;
    }

    public static int crc8(byte[] bytes, int start, int end, int initialValue) {
        while (start < end) {
            initialValue = CRC8_BYTES_MSBF[initialValue ^ (bytes[start] & 255)];
            start++;
        }
        return initialValue;
    }

    public static byte[] gzip(byte[] input) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            GZIPOutputStream gZIPOutputStream = new GZIPOutputStream(byteArrayOutputStream);
            try {
                gZIPOutputStream.write(input);
                gZIPOutputStream.close();
                return byteArrayOutputStream.toByteArray();
            } finally {
            }
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public static int getBigEndianInt(ByteBuffer buffer, int index) {
        int i = buffer.getInt(index);
        return buffer.order() == ByteOrder.BIG_ENDIAN ? i : Integer.reverseBytes(i);
    }

    public static String getCountryCode(Context context) {
        TelephonyManager telephonyManager;
        if (context != null && (telephonyManager = (TelephonyManager) context.getSystemService("phone")) != null) {
            String networkCountryIso = telephonyManager.getNetworkCountryIso();
            if (!TextUtils.isEmpty(networkCountryIso)) {
                return Ascii.toUpperCase(networkCountryIso);
            }
        }
        return Ascii.toUpperCase(Locale.getDefault().getCountry());
    }

    public static String[] getSystemLanguageCodes() {
        String[] systemLocales = getSystemLocales();
        for (int i = 0; i < systemLocales.length; i++) {
            systemLocales[i] = normalizeLanguageCode(systemLocales[i]);
        }
        return systemLocales;
    }

    public static boolean inflate(ParsableByteArray input, ParsableByteArray output, Inflater inflater) {
        if (input.bytesLeft() <= 0) {
            return false;
        }
        if (output.capacity() < input.bytesLeft()) {
            output.ensureCapacity(input.bytesLeft() * 2);
        }
        if (inflater == null) {
            inflater = new Inflater();
        }
        inflater.setInput(input.getData(), input.getPosition(), input.bytesLeft());
        int iInflate = 0;
        while (true) {
            try {
                iInflate += inflater.inflate(output.getData(), iInflate, output.capacity() - iInflate);
                if (inflater.finished()) {
                    output.setLimit(iInflate);
                    inflater.reset();
                    return true;
                }
                if (inflater.needsDictionary() || inflater.needsInput()) {
                    break;
                }
                if (iInflate == output.capacity()) {
                    output.ensureCapacity(output.capacity() * 2);
                }
            } catch (DataFormatException unused) {
                return false;
            } finally {
                inflater.reset();
            }
        }
        return false;
    }

    public static boolean isTv(Context context) {
        UiModeManager uiModeManager = (UiModeManager) context.getApplicationContext().getSystemService("uimode");
        return uiModeManager != null && uiModeManager.getCurrentModeType() == 4;
    }

    public static Point getCurrentDisplayModeSize(Context context) {
        DisplayManager displayManager;
        Display display = (SDK_INT < 17 || (displayManager = (DisplayManager) context.getSystemService(Constants.ScionAnalytics.MessageType.DISPLAY_NOTIFICATION)) == null) ? null : displayManager.getDisplay(0);
        if (display == null) {
            display = ((WindowManager) Assertions.checkNotNull((WindowManager) context.getSystemService("window"))).getDefaultDisplay();
        }
        return getCurrentDisplayModeSize(context, display);
    }

    public static Point getCurrentDisplayModeSize(Context context, Display display) throws ClassNotFoundException, NumberFormatException {
        String systemProperty;
        int i = SDK_INT;
        if (i <= 29 && display.getDisplayId() == 0 && isTv(context)) {
            if ("Sony".equals(MANUFACTURER) && MODEL.startsWith("BRAVIA") && context.getPackageManager().hasSystemFeature("com.sony.dtv.hardware.panel.qfhd")) {
                return new Point(3840, 2160);
            }
            if (i < 28) {
                systemProperty = getSystemProperty("sys.display-size");
            } else {
                systemProperty = getSystemProperty("vendor.display-size");
            }
            if (!TextUtils.isEmpty(systemProperty)) {
                try {
                    String[] strArrSplit = split(systemProperty.trim(), "x");
                    if (strArrSplit.length == 2) {
                        int i2 = Integer.parseInt(strArrSplit[0]);
                        int i3 = Integer.parseInt(strArrSplit[1]);
                        if (i2 > 0 && i3 > 0) {
                            return new Point(i2, i3);
                        }
                    }
                } catch (NumberFormatException unused) {
                }
                String strValueOf = String.valueOf(systemProperty);
                Log.e(TAG, strValueOf.length() != 0 ? "Invalid display size: ".concat(strValueOf) : new String("Invalid display size: "));
            }
        }
        Point point = new Point();
        int i4 = SDK_INT;
        if (i4 >= 23) {
            getDisplaySizeV23(display, point);
        } else if (i4 >= 17) {
            getDisplaySizeV17(display, point);
        } else {
            getDisplaySizeV16(display, point);
        }
        return point;
    }

    public static String getTrackTypeString(int trackType) {
        return trackType != 0 ? trackType != 1 ? trackType != 2 ? trackType != 3 ? trackType != 5 ? trackType != 6 ? trackType != 7 ? trackType >= 10000 ? new StringBuilder(20).append("custom (").append(trackType).append(")").toString() : "?" : "none" : "camera motion" : TtmlNode.TAG_METADATA : "text" : "video" : "audio" : "default";
    }

    public static long getNowUnixTimeMs(long elapsedRealtimeEpochOffsetMs) {
        if (elapsedRealtimeEpochOffsetMs == C.TIME_UNSET) {
            return System.currentTimeMillis();
        }
        return elapsedRealtimeEpochOffsetMs + android.os.SystemClock.elapsedRealtime();
    }

    public static <T> void moveItems(List<T> items, int fromIndex, int toIndex, int newFromIndex) {
        ArrayDeque arrayDeque = new ArrayDeque();
        for (int i = (toIndex - fromIndex) - 1; i >= 0; i--) {
            arrayDeque.addFirst(items.remove(fromIndex + i));
        }
        items.addAll(Math.min(newFromIndex, items.size()), arrayDeque);
    }

    public static boolean tableExists(SQLiteDatabase database, String tableName) {
        return DatabaseUtils.queryNumEntries(database, "sqlite_master", "tbl_name = ?", new String[]{tableName}) > 0;
    }

    public static int getErrorCodeFromPlatformDiagnosticsInfo(String diagnosticsInfo) throws NumberFormatException {
        String[] strArrSplit;
        int length;
        if (diagnosticsInfo == null || (length = (strArrSplit = split(diagnosticsInfo, "_")).length) < 2) {
            return 0;
        }
        String str = strArrSplit[length - 1];
        boolean z = length >= 3 && "neg".equals(strArrSplit[length - 2]);
        try {
            int i = Integer.parseInt((String) Assertions.checkNotNull(str));
            return z ? -i : i;
        } catch (NumberFormatException unused) {
            return 0;
        }
    }

    private static String getSystemProperty(String name) throws ClassNotFoundException {
        try {
            Class<?> cls = Class.forName("android.os.SystemProperties");
            return (String) cls.getMethod("get", String.class).invoke(cls, name);
        } catch (Exception e) {
            String strValueOf = String.valueOf(name);
            Log.e(TAG, strValueOf.length() != 0 ? "Failed to read system property ".concat(strValueOf) : new String("Failed to read system property "), e);
            return null;
        }
    }

    private static void getDisplaySizeV23(Display display, Point outSize) {
        Display.Mode mode = display.getMode();
        outSize.x = mode.getPhysicalWidth();
        outSize.y = mode.getPhysicalHeight();
    }

    private static void getDisplaySizeV17(Display display, Point outSize) {
        display.getRealSize(outSize);
    }

    private static void getDisplaySizeV16(Display display, Point outSize) {
        display.getSize(outSize);
    }

    private static String[] getSystemLocales() {
        Configuration configuration = Resources.getSystem().getConfiguration();
        return SDK_INT >= 24 ? getSystemLocalesV24(configuration) : new String[]{getLocaleLanguageTag(configuration.locale)};
    }

    private static String[] getSystemLocalesV24(Configuration config) {
        return split(config.getLocales().toLanguageTags(), ",");
    }

    private static String getLocaleLanguageTagV21(Locale locale) {
        return locale.toLanguageTag();
    }

    private static HashMap<String, String> createIsoLanguageReplacementMap() throws MissingResourceException {
        String[] iSOLanguages = Locale.getISOLanguages();
        HashMap<String, String> map = new HashMap<>(iSOLanguages.length + additionalIsoLanguageReplacements.length);
        int i = 0;
        for (String str : iSOLanguages) {
            try {
                String iSO3Language = new Locale(str).getISO3Language();
                if (!TextUtils.isEmpty(iSO3Language)) {
                    map.put(iSO3Language, str);
                }
            } catch (MissingResourceException unused) {
            }
        }
        while (true) {
            String[] strArr = additionalIsoLanguageReplacements;
            if (i >= strArr.length) {
                return map;
            }
            map.put(strArr[i], strArr[i + 1]);
            i += 2;
        }
    }

    private static boolean requestExternalStoragePermission(Activity activity) {
        if (activity.checkSelfPermission("android.permission.READ_EXTERNAL_STORAGE") == 0) {
            return false;
        }
        activity.requestPermissions(new String[]{"android.permission.READ_EXTERNAL_STORAGE"}, 0);
        return true;
    }

    private static boolean isTrafficRestricted(Uri uri) {
        return "http".equals(uri.getScheme()) && !NetworkSecurityPolicy.getInstance().isCleartextTrafficPermitted((String) Assertions.checkNotNull(uri.getHost()));
    }

    private static String maybeReplaceLegacyLanguageTags(String languageTag) {
        int i = 0;
        while (true) {
            String[] strArr = isoLegacyTagReplacements;
            if (i >= strArr.length) {
                return languageTag;
            }
            if (languageTag.startsWith(strArr[i])) {
                String strValueOf = String.valueOf(strArr[i + 1]);
                String strValueOf2 = String.valueOf(languageTag.substring(strArr[i].length()));
                return strValueOf2.length() != 0 ? strValueOf.concat(strValueOf2) : new String(strValueOf);
            }
            i += 2;
        }
    }
}
