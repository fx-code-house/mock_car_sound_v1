package com.thor.app.auto.media;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.os.Process;
import android.util.Base64;
import android.util.Log;
import androidx.core.app.NotificationManagerCompat;
import com.google.android.gms.measurement.api.AppMeasurementSdk;
import com.google.firebase.crashlytics.internal.common.AbstractSpiCall;
import com.google.firebase.messaging.Constants;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import org.xmlpull.v1.XmlPullParserException;

/* compiled from: PackageValidator.kt */
@Metadata(d1 = {"\u0000b\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0012\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001:\u0003'()B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0001\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0012\u0010\u0012\u001a\u0004\u0018\u00010\u00132\u0006\u0010\u0014\u001a\u00020\tH\u0002J\u001c\u0010\u0015\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u000e0\r2\u0006\u0010\u0016\u001a\u00020\u0017H\u0002J\u0012\u0010\u0018\u001a\u0004\u0018\u00010\u00192\u0006\u0010\u0014\u001a\u00020\tH\u0003J\u0012\u0010\u001a\u001a\u0004\u0018\u00010\t2\u0006\u0010\u001b\u001a\u00020\u0019H\u0002J\u0010\u0010\u001c\u001a\u00020\t2\u0006\u0010\u001d\u001a\u00020\u001eH\u0002J\u0010\u0010\u001c\u001a\u00020\t2\u0006\u0010\u001d\u001a\u00020\tH\u0002J\b\u0010\u001f\u001a\u00020\tH\u0002J\u0016\u0010 \u001a\u00020\u000b2\u0006\u0010\u0014\u001a\u00020\t2\u0006\u0010!\u001a\u00020\u0005J\u0010\u0010\"\u001a\u00020#2\u0006\u0010$\u001a\u00020\u0013H\u0002J\u0010\u0010%\u001a\u00020\u000e2\u0006\u0010\u0016\u001a\u00020\u0017H\u0002J\u0010\u0010&\u001a\u00020\u000e2\u0006\u0010\u0016\u001a\u00020\u0017H\u0002R&\u0010\u0007\u001a\u001a\u0012\u0004\u0012\u00020\t\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u000b0\n0\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u000e0\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006*"}, d2 = {"Lcom/thor/app/auto/media/PackageValidator;", "", "context", "Landroid/content/Context;", "xmlResId", "", "(Landroid/content/Context;I)V", "callerChecked", "", "", "Lkotlin/Pair;", "", "certificateAllowList", "", "Lcom/thor/app/auto/media/PackageValidator$KnownCallerInfo;", "packageManager", "Landroid/content/pm/PackageManager;", "platformSignature", "buildCallerInfo", "Lcom/thor/app/auto/media/PackageValidator$CallerPackageInfo;", "callingPackage", "buildCertificateAllowList", "parser", "Landroid/content/res/XmlResourceParser;", "getPackageInfo", "Landroid/content/pm/PackageInfo;", "getSignature", "packageInfo", "getSignatureSha256", "certificate", "", "getSystemSignature", "isKnownCaller", "callingUid", "logUnknownCaller", "", "callerPackageInfo", "parseV1Tag", "parseV2Tag", "CallerPackageInfo", "KnownCallerInfo", "KnownSignature", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes.dex */
public final class PackageValidator {
    private final Map<String, Pair<Integer, Boolean>> callerChecked;
    private final Map<String, KnownCallerInfo> certificateAllowList;
    private final Context context;
    private final PackageManager packageManager;
    private final String platformSignature;

    private final void logUnknownCaller(CallerPackageInfo callerPackageInfo) {
    }

    public PackageValidator(Context context, int i) throws Resources.NotFoundException {
        Intrinsics.checkNotNullParameter(context, "context");
        this.callerChecked = new LinkedHashMap();
        XmlResourceParser xml = context.getResources().getXml(i);
        Intrinsics.checkNotNullExpressionValue(xml, "context.resources.getXml(xmlResId)");
        Context applicationContext = context.getApplicationContext();
        Intrinsics.checkNotNullExpressionValue(applicationContext, "context.applicationContext");
        this.context = applicationContext;
        PackageManager packageManager = applicationContext.getPackageManager();
        Intrinsics.checkNotNullExpressionValue(packageManager, "this.context.packageManager");
        this.packageManager = packageManager;
        this.certificateAllowList = buildCertificateAllowList(xml);
        this.platformSignature = getSystemSignature();
    }

    public final boolean isKnownCaller(String callingPackage, int callingUid) {
        Set<KnownSignature> signatures$thor_1_8_7_release;
        Intrinsics.checkNotNullParameter(callingPackage, "callingPackage");
        Pair<Integer, Boolean> pair = this.callerChecked.get(callingPackage);
        if (pair == null) {
            pair = new Pair<>(0, false);
        }
        int iIntValue = pair.component1().intValue();
        boolean zBooleanValue = pair.component2().booleanValue();
        if (iIntValue == callingUid) {
            return zBooleanValue;
        }
        CallerPackageInfo callerPackageInfoBuildCallerInfo = buildCallerInfo(callingPackage);
        if (callerPackageInfoBuildCallerInfo == null) {
            throw new IllegalStateException("Caller wasn't found in the system?");
        }
        if (callerPackageInfoBuildCallerInfo.getUid$thor_1_8_7_release() != callingUid) {
            throw new IllegalStateException("Caller's package UID doesn't match caller's actual UID?");
        }
        String signature$thor_1_8_7_release = callerPackageInfoBuildCallerInfo.getSignature$thor_1_8_7_release();
        KnownCallerInfo knownCallerInfo = this.certificateAllowList.get(callingPackage);
        if (knownCallerInfo != null && (signatures$thor_1_8_7_release = knownCallerInfo.getSignatures$thor_1_8_7_release()) != null) {
            for (KnownSignature knownSignature : signatures$thor_1_8_7_release) {
                if (Intrinsics.areEqual(knownSignature.getSignature$thor_1_8_7_release(), signature$thor_1_8_7_release)) {
                }
            }
            throw new NoSuchElementException("Collection contains no element matching the predicate.");
        }
        knownSignature = null;
        boolean z = callingUid == Process.myUid() || (knownSignature != null) || callingUid == 1000 || Intrinsics.areEqual(signature$thor_1_8_7_release, this.platformSignature) || callerPackageInfoBuildCallerInfo.getPermissions$thor_1_8_7_release().contains("android.permission.MEDIA_CONTENT_CONTROL") || NotificationManagerCompat.getEnabledListenerPackages(this.context).contains(callerPackageInfoBuildCallerInfo.getPackageName$thor_1_8_7_release());
        if (!z) {
            logUnknownCaller(callerPackageInfoBuildCallerInfo);
        }
        this.callerChecked.put(callingPackage, new Pair<>(Integer.valueOf(callingUid), Boolean.valueOf(z)));
        return z;
    }

    private final CallerPackageInfo buildCallerInfo(String callingPackage) {
        PackageInfo packageInfo = getPackageInfo(callingPackage);
        if (packageInfo == null) {
            return null;
        }
        String string = packageInfo.applicationInfo.loadLabel(this.packageManager).toString();
        int i = packageInfo.applicationInfo.uid;
        String signature = getSignature(packageInfo);
        String[] strArr = packageInfo.requestedPermissions;
        int[] iArr = packageInfo.requestedPermissionsFlags;
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        if (strArr != null) {
            int length = strArr.length;
            int i2 = 0;
            int i3 = 0;
            while (i2 < length) {
                String str = strArr[i2];
                int i4 = i3 + 1;
                if ((iArr[i3] & 2) != 0) {
                    linkedHashSet.add(str);
                }
                i2++;
                i3 = i4;
            }
        }
        return new CallerPackageInfo(string, callingPackage, i, signature, CollectionsKt.toSet(linkedHashSet));
    }

    private final PackageInfo getPackageInfo(String callingPackage) {
        return this.packageManager.getPackageInfo(callingPackage, 4160);
    }

    private final String getSignature(PackageInfo packageInfo) {
        if (packageInfo.signatures == null || packageInfo.signatures.length != 1) {
            return null;
        }
        byte[] certificate = packageInfo.signatures[0].toByteArray();
        Intrinsics.checkNotNullExpressionValue(certificate, "certificate");
        return getSignatureSha256(certificate);
    }

    private final Map<String, KnownCallerInfo> buildCertificateAllowList(XmlResourceParser parser) {
        KnownCallerInfo v2Tag;
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        try {
            int next = parser.next();
            while (next != 1) {
                if (next == 2) {
                    String name = parser.getName();
                    if (Intrinsics.areEqual(name, "signing_certificate")) {
                        v2Tag = parseV1Tag(parser);
                    } else {
                        v2Tag = Intrinsics.areEqual(name, "signature") ? parseV2Tag(parser) : null;
                    }
                    if (v2Tag != null) {
                        String packageName$thor_1_8_7_release = v2Tag.getPackageName$thor_1_8_7_release();
                        KnownCallerInfo knownCallerInfo = (KnownCallerInfo) linkedHashMap.get(packageName$thor_1_8_7_release);
                        if (knownCallerInfo != null) {
                            CollectionsKt.addAll(knownCallerInfo.getSignatures$thor_1_8_7_release(), v2Tag.getSignatures$thor_1_8_7_release());
                        } else {
                            linkedHashMap.put(packageName$thor_1_8_7_release, v2Tag);
                        }
                    }
                }
                next = parser.next();
            }
        } catch (IOException e) {
            Log.e("PackageValidator", "Could not read allowed callers from XML.", e);
        } catch (XmlPullParserException e2) {
            Log.e("PackageValidator", "Could not read allowed callers from XML.", e2);
        }
        return linkedHashMap;
    }

    private final KnownCallerInfo parseV1Tag(XmlResourceParser parser) {
        String name = parser.getAttributeValue(null, AppMeasurementSdk.ConditionalUserProperty.NAME);
        String packageName = parser.getAttributeValue(null, "package");
        boolean attributeBooleanValue = parser.getAttributeBooleanValue(null, "release", false);
        String strNextText = parser.nextText();
        Intrinsics.checkNotNullExpressionValue(strNextText, "parser.nextText()");
        KnownSignature knownSignature = new KnownSignature(getSignatureSha256(PackageValidatorKt.WHITESPACE_REGEX.replace(strNextText, "")), attributeBooleanValue);
        Intrinsics.checkNotNullExpressionValue(name, "name");
        Intrinsics.checkNotNullExpressionValue(packageName, "packageName");
        return new KnownCallerInfo(name, packageName, SetsKt.mutableSetOf(knownSignature));
    }

    private final KnownCallerInfo parseV2Tag(XmlResourceParser parser) {
        String name = parser.getAttributeValue(null, AppMeasurementSdk.ConditionalUserProperty.NAME);
        String packageName = parser.getAttributeValue(null, "package");
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        int next = parser.next();
        while (next != 3) {
            boolean attributeBooleanValue = parser.getAttributeBooleanValue(null, "release", false);
            String strNextText = parser.nextText();
            Intrinsics.checkNotNullExpressionValue(strNextText, "parser.nextText()");
            String lowerCase = PackageValidatorKt.WHITESPACE_REGEX.replace(strNextText, "").toLowerCase();
            Intrinsics.checkNotNullExpressionValue(lowerCase, "toLowerCase(...)");
            linkedHashSet.add(new KnownSignature(lowerCase, attributeBooleanValue));
            next = parser.next();
        }
        Intrinsics.checkNotNullExpressionValue(name, "name");
        Intrinsics.checkNotNullExpressionValue(packageName, "packageName");
        return new KnownCallerInfo(name, packageName, linkedHashSet);
    }

    private final String getSystemSignature() {
        String signature;
        PackageInfo packageInfo = getPackageInfo(AbstractSpiCall.ANDROID_CLIENT_TYPE);
        if (packageInfo == null || (signature = getSignature(packageInfo)) == null) {
            throw new IllegalStateException("Platform signature not found");
        }
        return signature;
    }

    private final String getSignatureSha256(String certificate) {
        byte[] bArrDecode = Base64.decode(certificate, 0);
        Intrinsics.checkNotNullExpressionValue(bArrDecode, "decode(certificate, Base64.DEFAULT)");
        return getSignatureSha256(bArrDecode);
    }

    private final String getSignatureSha256(byte[] certificate) throws NoSuchAlgorithmException {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA256");
            Intrinsics.checkNotNullExpressionValue(messageDigest, "getInstance(\"SHA256\")");
            messageDigest.update(certificate);
            byte[] bArrDigest = messageDigest.digest();
            Intrinsics.checkNotNullExpressionValue(bArrDigest, "md.digest()");
            return ArraysKt.joinToString$default(bArrDigest, (CharSequence) ":", (CharSequence) null, (CharSequence) null, 0, (CharSequence) null, (Function1) new Function1<Byte, CharSequence>() { // from class: com.thor.app.auto.media.PackageValidator.getSignatureSha256.1
                public final CharSequence invoke(byte b) {
                    StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
                    String str = String.format("%02x", Arrays.copyOf(new Object[]{Byte.valueOf(b)}, 1));
                    Intrinsics.checkNotNullExpressionValue(str, "format(...)");
                    return str;
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ CharSequence invoke(Byte b) {
                    return invoke(b.byteValue());
                }
            }, 30, (Object) null);
        } catch (NoSuchAlgorithmException e) {
            Log.e("PackageValidator", "No such algorithm: " + e);
            throw new RuntimeException("Could not find SHA256 hash algorithm", e);
        }
    }

    /* compiled from: PackageValidator.kt */
    @Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010#\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0082\b\u0018\u00002\u00020\u0001B#\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006¢\u0006\u0002\u0010\bJ\u000e\u0010\u000e\u001a\u00020\u0003HÀ\u0003¢\u0006\u0002\b\u000fJ\u000e\u0010\u0010\u001a\u00020\u0003HÀ\u0003¢\u0006\u0002\b\u0011J\u0014\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006HÀ\u0003¢\u0006\u0002\b\u0013J-\u0010\u0014\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\u000e\b\u0002\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006HÆ\u0001J\u0013\u0010\u0015\u001a\u00020\u00162\b\u0010\u0017\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0018\u001a\u00020\u0019HÖ\u0001J\t\u0010\u001a\u001a\u00020\u0003HÖ\u0001R\u0014\u0010\u0002\u001a\u00020\u0003X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0014\u0010\u0004\u001a\u00020\u0003X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\nR\u001a\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r¨\u0006\u001b"}, d2 = {"Lcom/thor/app/auto/media/PackageValidator$KnownCallerInfo;", "", AppMeasurementSdk.ConditionalUserProperty.NAME, "", Constants.FirelogAnalytics.PARAM_PACKAGE_NAME, "signatures", "", "Lcom/thor/app/auto/media/PackageValidator$KnownSignature;", "(Ljava/lang/String;Ljava/lang/String;Ljava/util/Set;)V", "getName$thor_1_8_7_release", "()Ljava/lang/String;", "getPackageName$thor_1_8_7_release", "getSignatures$thor_1_8_7_release", "()Ljava/util/Set;", "component1", "component1$thor_1_8_7_release", "component2", "component2$thor_1_8_7_release", "component3", "component3$thor_1_8_7_release", "copy", "equals", "", "other", "hashCode", "", "toString", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    private static final /* data */ class KnownCallerInfo {
        private final String name;
        private final String packageName;
        private final Set<KnownSignature> signatures;

        /* JADX WARN: Multi-variable type inference failed */
        public static /* synthetic */ KnownCallerInfo copy$default(KnownCallerInfo knownCallerInfo, String str, String str2, Set set, int i, Object obj) {
            if ((i & 1) != 0) {
                str = knownCallerInfo.name;
            }
            if ((i & 2) != 0) {
                str2 = knownCallerInfo.packageName;
            }
            if ((i & 4) != 0) {
                set = knownCallerInfo.signatures;
            }
            return knownCallerInfo.copy(str, str2, set);
        }

        /* renamed from: component1$thor_1_8_7_release, reason: from getter */
        public final String getName() {
            return this.name;
        }

        /* renamed from: component2$thor_1_8_7_release, reason: from getter */
        public final String getPackageName() {
            return this.packageName;
        }

        public final Set<KnownSignature> component3$thor_1_8_7_release() {
            return this.signatures;
        }

        public final KnownCallerInfo copy(String name, String packageName, Set<KnownSignature> signatures) {
            Intrinsics.checkNotNullParameter(name, "name");
            Intrinsics.checkNotNullParameter(packageName, "packageName");
            Intrinsics.checkNotNullParameter(signatures, "signatures");
            return new KnownCallerInfo(name, packageName, signatures);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof KnownCallerInfo)) {
                return false;
            }
            KnownCallerInfo knownCallerInfo = (KnownCallerInfo) other;
            return Intrinsics.areEqual(this.name, knownCallerInfo.name) && Intrinsics.areEqual(this.packageName, knownCallerInfo.packageName) && Intrinsics.areEqual(this.signatures, knownCallerInfo.signatures);
        }

        public int hashCode() {
            return (((this.name.hashCode() * 31) + this.packageName.hashCode()) * 31) + this.signatures.hashCode();
        }

        public String toString() {
            return "KnownCallerInfo(name=" + this.name + ", packageName=" + this.packageName + ", signatures=" + this.signatures + ")";
        }

        public KnownCallerInfo(String name, String packageName, Set<KnownSignature> signatures) {
            Intrinsics.checkNotNullParameter(name, "name");
            Intrinsics.checkNotNullParameter(packageName, "packageName");
            Intrinsics.checkNotNullParameter(signatures, "signatures");
            this.name = name;
            this.packageName = packageName;
            this.signatures = signatures;
        }

        public final String getName$thor_1_8_7_release() {
            return this.name;
        }

        public final String getPackageName$thor_1_8_7_release() {
            return this.packageName;
        }

        public final Set<KnownSignature> getSignatures$thor_1_8_7_release() {
            return this.signatures;
        }
    }

    /* compiled from: PackageValidator.kt */
    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\r\n\u0002\u0010\b\n\u0002\b\u0002\b\u0082\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u000e\u0010\u000b\u001a\u00020\u0003HÀ\u0003¢\u0006\u0002\b\fJ\u000e\u0010\r\u001a\u00020\u0005HÀ\u0003¢\u0006\u0002\b\u000eJ\u001d\u0010\u000f\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u0010\u001a\u00020\u00052\b\u0010\u0011\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0012\u001a\u00020\u0013HÖ\u0001J\t\u0010\u0014\u001a\u00020\u0003HÖ\u0001R\u0014\u0010\u0004\u001a\u00020\u0005X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\u0002\u001a\u00020\u0003X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0015"}, d2 = {"Lcom/thor/app/auto/media/PackageValidator$KnownSignature;", "", "signature", "", "release", "", "(Ljava/lang/String;Z)V", "getRelease$thor_1_8_7_release", "()Z", "getSignature$thor_1_8_7_release", "()Ljava/lang/String;", "component1", "component1$thor_1_8_7_release", "component2", "component2$thor_1_8_7_release", "copy", "equals", "other", "hashCode", "", "toString", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    private static final /* data */ class KnownSignature {
        private final boolean release;
        private final String signature;

        public static /* synthetic */ KnownSignature copy$default(KnownSignature knownSignature, String str, boolean z, int i, Object obj) {
            if ((i & 1) != 0) {
                str = knownSignature.signature;
            }
            if ((i & 2) != 0) {
                z = knownSignature.release;
            }
            return knownSignature.copy(str, z);
        }

        /* renamed from: component1$thor_1_8_7_release, reason: from getter */
        public final String getSignature() {
            return this.signature;
        }

        /* renamed from: component2$thor_1_8_7_release, reason: from getter */
        public final boolean getRelease() {
            return this.release;
        }

        public final KnownSignature copy(String signature, boolean release) {
            Intrinsics.checkNotNullParameter(signature, "signature");
            return new KnownSignature(signature, release);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof KnownSignature)) {
                return false;
            }
            KnownSignature knownSignature = (KnownSignature) other;
            return Intrinsics.areEqual(this.signature, knownSignature.signature) && this.release == knownSignature.release;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public int hashCode() {
            int iHashCode = this.signature.hashCode() * 31;
            boolean z = this.release;
            int i = z;
            if (z != 0) {
                i = 1;
            }
            return iHashCode + i;
        }

        public String toString() {
            return "KnownSignature(signature=" + this.signature + ", release=" + this.release + ")";
        }

        public KnownSignature(String signature, boolean z) {
            Intrinsics.checkNotNullParameter(signature, "signature");
            this.signature = signature;
            this.release = z;
        }

        public final String getSignature$thor_1_8_7_release() {
            return this.signature;
        }

        public final boolean getRelease$thor_1_8_7_release() {
            return this.release;
        }
    }

    /* compiled from: PackageValidator.kt */
    @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\b\u0015\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0082\b\u0018\u00002\u00020\u0001B5\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0003\u0012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00030\t¢\u0006\u0002\u0010\nJ\u000e\u0010\u0013\u001a\u00020\u0003HÀ\u0003¢\u0006\u0002\b\u0014J\u000e\u0010\u0015\u001a\u00020\u0003HÀ\u0003¢\u0006\u0002\b\u0016J\u000e\u0010\u0017\u001a\u00020\u0006HÀ\u0003¢\u0006\u0002\b\u0018J\u0010\u0010\u0019\u001a\u0004\u0018\u00010\u0003HÀ\u0003¢\u0006\u0002\b\u001aJ\u0014\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00030\tHÀ\u0003¢\u0006\u0002\b\u001cJC\u0010\u001d\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u00032\u000e\b\u0002\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00030\tHÆ\u0001J\u0013\u0010\u001e\u001a\u00020\u001f2\b\u0010 \u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010!\u001a\u00020\u0006HÖ\u0001J\t\u0010\"\u001a\u00020\u0003HÖ\u0001R\u0014\u0010\u0002\u001a\u00020\u0003X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0014\u0010\u0004\u001a\u00020\u0003X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\fR\u001a\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00030\tX\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0016\u0010\u0007\u001a\u0004\u0018\u00010\u0003X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\fR\u0014\u0010\u0005\u001a\u00020\u0006X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012¨\u0006#"}, d2 = {"Lcom/thor/app/auto/media/PackageValidator$CallerPackageInfo;", "", AppMeasurementSdk.ConditionalUserProperty.NAME, "", Constants.FirelogAnalytics.PARAM_PACKAGE_NAME, "uid", "", "signature", "permissions", "", "(Ljava/lang/String;Ljava/lang/String;ILjava/lang/String;Ljava/util/Set;)V", "getName$thor_1_8_7_release", "()Ljava/lang/String;", "getPackageName$thor_1_8_7_release", "getPermissions$thor_1_8_7_release", "()Ljava/util/Set;", "getSignature$thor_1_8_7_release", "getUid$thor_1_8_7_release", "()I", "component1", "component1$thor_1_8_7_release", "component2", "component2$thor_1_8_7_release", "component3", "component3$thor_1_8_7_release", "component4", "component4$thor_1_8_7_release", "component5", "component5$thor_1_8_7_release", "copy", "equals", "", "other", "hashCode", "toString", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    private static final /* data */ class CallerPackageInfo {
        private final String name;
        private final String packageName;
        private final Set<String> permissions;
        private final String signature;
        private final int uid;

        public static /* synthetic */ CallerPackageInfo copy$default(CallerPackageInfo callerPackageInfo, String str, String str2, int i, String str3, Set set, int i2, Object obj) {
            if ((i2 & 1) != 0) {
                str = callerPackageInfo.name;
            }
            if ((i2 & 2) != 0) {
                str2 = callerPackageInfo.packageName;
            }
            String str4 = str2;
            if ((i2 & 4) != 0) {
                i = callerPackageInfo.uid;
            }
            int i3 = i;
            if ((i2 & 8) != 0) {
                str3 = callerPackageInfo.signature;
            }
            String str5 = str3;
            if ((i2 & 16) != 0) {
                set = callerPackageInfo.permissions;
            }
            return callerPackageInfo.copy(str, str4, i3, str5, set);
        }

        /* renamed from: component1$thor_1_8_7_release, reason: from getter */
        public final String getName() {
            return this.name;
        }

        /* renamed from: component2$thor_1_8_7_release, reason: from getter */
        public final String getPackageName() {
            return this.packageName;
        }

        /* renamed from: component3$thor_1_8_7_release, reason: from getter */
        public final int getUid() {
            return this.uid;
        }

        /* renamed from: component4$thor_1_8_7_release, reason: from getter */
        public final String getSignature() {
            return this.signature;
        }

        public final Set<String> component5$thor_1_8_7_release() {
            return this.permissions;
        }

        public final CallerPackageInfo copy(String name, String packageName, int uid, String signature, Set<String> permissions) {
            Intrinsics.checkNotNullParameter(name, "name");
            Intrinsics.checkNotNullParameter(packageName, "packageName");
            Intrinsics.checkNotNullParameter(permissions, "permissions");
            return new CallerPackageInfo(name, packageName, uid, signature, permissions);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof CallerPackageInfo)) {
                return false;
            }
            CallerPackageInfo callerPackageInfo = (CallerPackageInfo) other;
            return Intrinsics.areEqual(this.name, callerPackageInfo.name) && Intrinsics.areEqual(this.packageName, callerPackageInfo.packageName) && this.uid == callerPackageInfo.uid && Intrinsics.areEqual(this.signature, callerPackageInfo.signature) && Intrinsics.areEqual(this.permissions, callerPackageInfo.permissions);
        }

        public int hashCode() {
            int iHashCode = ((((this.name.hashCode() * 31) + this.packageName.hashCode()) * 31) + Integer.hashCode(this.uid)) * 31;
            String str = this.signature;
            return ((iHashCode + (str == null ? 0 : str.hashCode())) * 31) + this.permissions.hashCode();
        }

        public String toString() {
            return "CallerPackageInfo(name=" + this.name + ", packageName=" + this.packageName + ", uid=" + this.uid + ", signature=" + this.signature + ", permissions=" + this.permissions + ")";
        }

        public CallerPackageInfo(String name, String packageName, int i, String str, Set<String> permissions) {
            Intrinsics.checkNotNullParameter(name, "name");
            Intrinsics.checkNotNullParameter(packageName, "packageName");
            Intrinsics.checkNotNullParameter(permissions, "permissions");
            this.name = name;
            this.packageName = packageName;
            this.uid = i;
            this.signature = str;
            this.permissions = permissions;
        }

        public final String getName$thor_1_8_7_release() {
            return this.name;
        }

        public final String getPackageName$thor_1_8_7_release() {
            return this.packageName;
        }

        public final int getUid$thor_1_8_7_release() {
            return this.uid;
        }

        public final String getSignature$thor_1_8_7_release() {
            return this.signature;
        }

        public final Set<String> getPermissions$thor_1_8_7_release() {
            return this.permissions;
        }
    }
}
