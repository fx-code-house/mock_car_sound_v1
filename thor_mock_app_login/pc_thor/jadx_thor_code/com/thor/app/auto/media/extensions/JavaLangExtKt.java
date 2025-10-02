package com.thor.app.auto.media.extensions;

import android.net.Uri;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

/* compiled from: JavaLangExt.kt */
@Metadata(d1 = {"\u0000\u0018\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u001a\u0016\u0010\u0004\u001a\u00020\u0005*\u0004\u0018\u00010\u00012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0001\u001a\f\u0010\u0007\u001a\u00020\b*\u0004\u0018\u00010\u0001\"\u0018\u0010\u0000\u001a\u00020\u0001*\u0004\u0018\u00010\u00018Æ\u0002¢\u0006\u0006\u001a\u0004\b\u0002\u0010\u0003¨\u0006\t"}, d2 = {"urlEncoded", "", "getUrlEncoded", "(Ljava/lang/String;)Ljava/lang/String;", "containsCaseInsensitive", "", "other", "toUri", "Landroid/net/Uri;", "thor-1.8.7_release"}, k = 2, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes.dex */
public final class JavaLangExtKt {
    public static final boolean containsCaseInsensitive(String str, String str2) {
        if (str != null && str2 != null) {
            Locale locale = Locale.getDefault();
            Intrinsics.checkNotNullExpressionValue(locale, "getDefault()");
            String lowerCase = str.toLowerCase(locale);
            Intrinsics.checkNotNullExpressionValue(lowerCase, "toLowerCase(...)");
            Locale locale2 = Locale.getDefault();
            Intrinsics.checkNotNullExpressionValue(locale2, "getDefault()");
            String lowerCase2 = str2.toLowerCase(locale2);
            Intrinsics.checkNotNullExpressionValue(lowerCase2, "toLowerCase(...)");
            return StringsKt.contains$default((CharSequence) lowerCase, (CharSequence) lowerCase2, false, 2, (Object) null);
        }
        return Intrinsics.areEqual(str, str2);
    }

    public static final String getUrlEncoded(String str) throws UnsupportedEncodingException {
        if (Charset.isSupported("UTF-8")) {
            if (str == null) {
                str = "";
            }
            String strEncode = URLEncoder.encode(str, "UTF-8");
            Intrinsics.checkNotNullExpressionValue(strEncode, "{\n        URLEncoder.enc…his ?: \"\", \"UTF-8\")\n    }");
            return strEncode;
        }
        if (str == null) {
            str = "";
        }
        String strEncode2 = URLEncoder.encode(str);
        Intrinsics.checkNotNullExpressionValue(strEncode2, "{\n        // If UTF-8 is….encode(this ?: \"\")\n    }");
        return strEncode2;
    }

    public static final Uri toUri(String str) {
        Uri uri = str != null ? Uri.parse(str) : null;
        if (uri != null) {
            return uri;
        }
        Uri EMPTY = Uri.EMPTY;
        Intrinsics.checkNotNullExpressionValue(EMPTY, "EMPTY");
        return EMPTY;
    }
}
