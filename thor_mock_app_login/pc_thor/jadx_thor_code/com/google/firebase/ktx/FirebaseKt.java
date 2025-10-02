package com.google.firebase.ktx;

import android.content.Context;
import com.google.android.gms.measurement.api.AppMeasurementSdk;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: Firebase.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\"\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\u001a\u0012\u0010\u0002\u001a\u00020\u0003*\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u0001\u001a\u0014\u0010\f\u001a\u0004\u0018\u00010\u0003*\u00020\u00042\u0006\u0010\r\u001a\u00020\u000e\u001a\u001a\u0010\f\u001a\u00020\u0003*\u00020\u00042\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0007\u001a\u00020\b\u001a\"\u0010\f\u001a\u00020\u0003*\u00020\u00042\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\u0001\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0080T¢\u0006\u0002\n\u0000\"\u0015\u0010\u0002\u001a\u00020\u0003*\u00020\u00048F¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006\"\u0015\u0010\u0007\u001a\u00020\b*\u00020\u00048F¢\u0006\u0006\u001a\u0004\b\t\u0010\n¨\u0006\u000f"}, d2 = {"LIBRARY_NAME", "", "app", "Lcom/google/firebase/FirebaseApp;", "Lcom/google/firebase/ktx/Firebase;", "getApp", "(Lcom/google/firebase/ktx/Firebase;)Lcom/google/firebase/FirebaseApp;", "options", "Lcom/google/firebase/FirebaseOptions;", "getOptions", "(Lcom/google/firebase/ktx/Firebase;)Lcom/google/firebase/FirebaseOptions;", AppMeasurementSdk.ConditionalUserProperty.NAME, "initialize", "context", "Landroid/content/Context;", "com.google.firebase-firebase-common-ktx"}, k = 2, mv = {1, 1, 13})
/* loaded from: classes2.dex */
public final class FirebaseKt {
    public static final String LIBRARY_NAME = "fire-core-ktx";

    public static final FirebaseApp getApp(Firebase receiver$0) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        FirebaseApp firebaseApp = FirebaseApp.getInstance();
        Intrinsics.checkExpressionValueIsNotNull(firebaseApp, "FirebaseApp.getInstance()");
        return firebaseApp;
    }

    public static final FirebaseApp app(Firebase receiver$0, String name) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        Intrinsics.checkParameterIsNotNull(name, "name");
        FirebaseApp firebaseApp = FirebaseApp.getInstance(name);
        Intrinsics.checkExpressionValueIsNotNull(firebaseApp, "FirebaseApp.getInstance(name)");
        return firebaseApp;
    }

    public static final FirebaseApp initialize(Firebase receiver$0, Context context) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        Intrinsics.checkParameterIsNotNull(context, "context");
        return FirebaseApp.initializeApp(context);
    }

    public static final FirebaseApp initialize(Firebase receiver$0, Context context, FirebaseOptions options) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        Intrinsics.checkParameterIsNotNull(context, "context");
        Intrinsics.checkParameterIsNotNull(options, "options");
        FirebaseApp firebaseAppInitializeApp = FirebaseApp.initializeApp(context, options);
        Intrinsics.checkExpressionValueIsNotNull(firebaseAppInitializeApp, "FirebaseApp.initializeApp(context, options)");
        return firebaseAppInitializeApp;
    }

    public static final FirebaseApp initialize(Firebase receiver$0, Context context, FirebaseOptions options, String name) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        Intrinsics.checkParameterIsNotNull(context, "context");
        Intrinsics.checkParameterIsNotNull(options, "options");
        Intrinsics.checkParameterIsNotNull(name, "name");
        FirebaseApp firebaseAppInitializeApp = FirebaseApp.initializeApp(context, options, name);
        Intrinsics.checkExpressionValueIsNotNull(firebaseAppInitializeApp, "FirebaseApp.initializeApp(context, options, name)");
        return firebaseAppInitializeApp;
    }

    public static final FirebaseOptions getOptions(Firebase receiver$0) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        FirebaseOptions options = getApp(Firebase.INSTANCE).getOptions();
        Intrinsics.checkExpressionValueIsNotNull(options, "Firebase.app.options");
        return options;
    }
}
