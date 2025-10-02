package androidx.credentials.provider.utils;

import android.app.slice.Slice;
import android.content.pm.SigningInfo;
import android.os.Bundle;
import android.service.credentials.BeginCreateCredentialResponse;
import androidx.credentials.PasswordCredential;
import androidx.credentials.PublicKeyCredential;
import androidx.credentials.internal.FrameworkClassParsingException;
import androidx.credentials.provider.BeginCreateCredentialRequest;
import androidx.credentials.provider.BeginCreateCustomCredentialRequest;
import androidx.credentials.provider.BeginCreatePasswordCredentialRequest;
import androidx.credentials.provider.BeginCreatePublicKeyCredentialRequest;
import androidx.credentials.provider.CallingAppInfo;
import androidx.credentials.provider.CreateEntry;
import androidx.credentials.provider.RemoteEntry;
import androidx.credentials.provider.utils.BeginCreateCredentialUtil;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: BeginCreateCredentialUtil.kt */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\b\u0001\u0018\u0000 \u00032\u00020\u0001:\u0001\u0003B\u0005¢\u0006\u0002\u0010\u0002¨\u0006\u0004"}, d2 = {"Landroidx/credentials/provider/utils/BeginCreateCredentialUtil;", "", "()V", "Companion", "credentials_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes.dex */
public final class BeginCreateCredentialUtil {

    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);

    /* compiled from: BeginCreateCredentialUtil.kt */
    @Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0080\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006J\u000e\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nJ\u0015\u0010\u000b\u001a\u00020\u00062\u0006\u0010\u0005\u001a\u00020\u0004H\u0001¢\u0006\u0002\b\fJ\u000e\u0010\r\u001a\u00020\n2\u0006\u0010\u000e\u001a\u00020\bJ\u001e\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00150\u0014H\u0002J\u001a\u0010\u0016\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\b\u0010\u0017\u001a\u0004\u0018\u00010\u0018H\u0003¨\u0006\u0019"}, d2 = {"Landroidx/credentials/provider/utils/BeginCreateCredentialUtil$Companion;", "", "()V", "convertToFrameworkRequest", "Landroid/service/credentials/BeginCreateCredentialRequest;", "request", "Landroidx/credentials/provider/BeginCreateCredentialRequest;", "convertToFrameworkResponse", "Landroid/service/credentials/BeginCreateCredentialResponse;", "response", "Landroidx/credentials/provider/BeginCreateCredentialResponse;", "convertToJetpackRequest", "convertToJetpackRequest$credentials_release", "convertToJetpackResponse", "frameworkResponse", "populateCreateEntries", "", "frameworkBuilder", "Landroid/service/credentials/BeginCreateCredentialResponse$Builder;", "createEntries", "", "Landroidx/credentials/provider/CreateEntry;", "populateRemoteEntry", "remoteEntry", "Landroidx/credentials/provider/RemoteEntry;", "credentials_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @JvmStatic
        public final BeginCreateCredentialRequest convertToJetpackRequest$credentials_release(android.service.credentials.BeginCreateCredentialRequest request) {
            CallingAppInfo callingAppInfo;
            CallingAppInfo callingAppInfo2;
            CallingAppInfo callingAppInfo3;
            Intrinsics.checkNotNullParameter(request, "request");
            CallingAppInfo callingAppInfo4 = null;
            try {
                String type = request.getType();
                int iHashCode = type.hashCode();
                if (iHashCode != -543568185) {
                    if (iHashCode == -95037569 && type.equals(PublicKeyCredential.TYPE_PUBLIC_KEY_CREDENTIAL)) {
                        BeginCreatePublicKeyCredentialRequest.Companion companion = BeginCreatePublicKeyCredentialRequest.INSTANCE;
                        Bundle data = request.getData();
                        Intrinsics.checkNotNullExpressionValue(data, "request.data");
                        android.service.credentials.CallingAppInfo callingAppInfo5 = request.getCallingAppInfo();
                        if (callingAppInfo5 != null) {
                            String packageName = callingAppInfo5.getPackageName();
                            Intrinsics.checkNotNullExpressionValue(packageName, "it.packageName");
                            SigningInfo signingInfo = callingAppInfo5.getSigningInfo();
                            Intrinsics.checkNotNullExpressionValue(signingInfo, "it.signingInfo");
                            callingAppInfo3 = new CallingAppInfo(packageName, signingInfo, callingAppInfo5.getOrigin());
                        } else {
                            callingAppInfo3 = null;
                        }
                        return companion.createFrom$credentials_release(data, callingAppInfo3);
                    }
                } else if (type.equals(PasswordCredential.TYPE_PASSWORD_CREDENTIAL)) {
                    BeginCreatePasswordCredentialRequest.Companion companion2 = BeginCreatePasswordCredentialRequest.INSTANCE;
                    Bundle data2 = request.getData();
                    Intrinsics.checkNotNullExpressionValue(data2, "request.data");
                    android.service.credentials.CallingAppInfo callingAppInfo6 = request.getCallingAppInfo();
                    if (callingAppInfo6 != null) {
                        String packageName2 = callingAppInfo6.getPackageName();
                        Intrinsics.checkNotNullExpressionValue(packageName2, "it.packageName");
                        SigningInfo signingInfo2 = callingAppInfo6.getSigningInfo();
                        Intrinsics.checkNotNullExpressionValue(signingInfo2, "it.signingInfo");
                        callingAppInfo = new CallingAppInfo(packageName2, signingInfo2, callingAppInfo6.getOrigin());
                    } else {
                        callingAppInfo = null;
                    }
                    return companion2.createFrom$credentials_release(data2, callingAppInfo);
                }
                String type2 = request.getType();
                Intrinsics.checkNotNullExpressionValue(type2, "request.type");
                Bundle data3 = request.getData();
                Intrinsics.checkNotNullExpressionValue(data3, "request.data");
                android.service.credentials.CallingAppInfo callingAppInfo7 = request.getCallingAppInfo();
                if (callingAppInfo7 != null) {
                    String packageName3 = callingAppInfo7.getPackageName();
                    Intrinsics.checkNotNullExpressionValue(packageName3, "it.packageName");
                    SigningInfo signingInfo3 = callingAppInfo7.getSigningInfo();
                    Intrinsics.checkNotNullExpressionValue(signingInfo3, "it.signingInfo");
                    callingAppInfo2 = new CallingAppInfo(packageName3, signingInfo3, callingAppInfo7.getOrigin());
                } else {
                    callingAppInfo2 = null;
                }
                return new BeginCreateCustomCredentialRequest(type2, data3, callingAppInfo2);
            } catch (FrameworkClassParsingException unused) {
                String type3 = request.getType();
                Intrinsics.checkNotNullExpressionValue(type3, "request.type");
                Bundle data4 = request.getData();
                Intrinsics.checkNotNullExpressionValue(data4, "request.data");
                android.service.credentials.CallingAppInfo callingAppInfo8 = request.getCallingAppInfo();
                if (callingAppInfo8 != null) {
                    String packageName4 = callingAppInfo8.getPackageName();
                    Intrinsics.checkNotNullExpressionValue(packageName4, "it.packageName");
                    SigningInfo signingInfo4 = callingAppInfo8.getSigningInfo();
                    Intrinsics.checkNotNullExpressionValue(signingInfo4, "it.signingInfo");
                    callingAppInfo4 = new CallingAppInfo(packageName4, signingInfo4, callingAppInfo8.getOrigin());
                }
                return new BeginCreateCustomCredentialRequest(type3, data4, callingAppInfo4);
            }
        }

        public final BeginCreateCredentialResponse convertToFrameworkResponse(androidx.credentials.provider.BeginCreateCredentialResponse response) {
            Intrinsics.checkNotNullParameter(response, "response");
            BeginCreateCredentialResponse.Builder builder = new BeginCreateCredentialResponse.Builder();
            populateCreateEntries(builder, response.getCreateEntries());
            populateRemoteEntry(builder, response.getRemoteEntry());
            BeginCreateCredentialResponse beginCreateCredentialResponseBuild = builder.build();
            Intrinsics.checkNotNullExpressionValue(beginCreateCredentialResponseBuild, "frameworkBuilder.build()");
            return beginCreateCredentialResponseBuild;
        }

        private final void populateRemoteEntry(BeginCreateCredentialResponse.Builder frameworkBuilder, RemoteEntry remoteEntry) {
            if (remoteEntry == null) {
                return;
            }
            frameworkBuilder.setRemoteCreateEntry(new android.service.credentials.RemoteEntry(RemoteEntry.INSTANCE.toSlice(remoteEntry)));
        }

        private final void populateCreateEntries(BeginCreateCredentialResponse.Builder frameworkBuilder, List<CreateEntry> createEntries) {
            Iterator<T> it = createEntries.iterator();
            while (it.hasNext()) {
                Slice slice = CreateEntry.INSTANCE.toSlice((CreateEntry) it.next());
                if (slice != null) {
                    frameworkBuilder.addCreateEntry(new android.service.credentials.CreateEntry(slice));
                }
            }
        }

        public final android.service.credentials.BeginCreateCredentialRequest convertToFrameworkRequest(BeginCreateCredentialRequest request) {
            Intrinsics.checkNotNullParameter(request, "request");
            return new android.service.credentials.BeginCreateCredentialRequest(request.getType(), request.getCandidateQueryData(), request.getCallingAppInfo() != null ? new android.service.credentials.CallingAppInfo(request.getCallingAppInfo().getPackageName(), request.getCallingAppInfo().getSigningInfo(), request.getCallingAppInfo().getOrigin()) : null);
        }

        public final androidx.credentials.provider.BeginCreateCredentialResponse convertToJetpackResponse(BeginCreateCredentialResponse frameworkResponse) {
            RemoteEntry remoteEntryFromSlice;
            Intrinsics.checkNotNullParameter(frameworkResponse, "frameworkResponse");
            Stream<android.service.credentials.CreateEntry> stream = frameworkResponse.getCreateEntries().stream();
            final BeginCreateCredentialUtil$Companion$convertToJetpackResponse$1 beginCreateCredentialUtil$Companion$convertToJetpackResponse$1 = new Function1<android.service.credentials.CreateEntry, CreateEntry>() { // from class: androidx.credentials.provider.utils.BeginCreateCredentialUtil$Companion$convertToJetpackResponse$1
                @Override // kotlin.jvm.functions.Function1
                public final CreateEntry invoke(android.service.credentials.CreateEntry createEntry) {
                    CreateEntry.Companion companion = CreateEntry.INSTANCE;
                    Slice slice = createEntry.getSlice();
                    Intrinsics.checkNotNullExpressionValue(slice, "entry.slice");
                    return companion.fromSlice(slice);
                }
            };
            Stream<R> map = stream.map(new Function() { // from class: androidx.credentials.provider.utils.BeginCreateCredentialUtil$Companion$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return BeginCreateCredentialUtil.Companion.convertToJetpackResponse$lambda$5(beginCreateCredentialUtil$Companion$convertToJetpackResponse$1, obj);
                }
            });
            final BeginCreateCredentialUtil$Companion$convertToJetpackResponse$2 beginCreateCredentialUtil$Companion$convertToJetpackResponse$2 = new Function1<CreateEntry, Boolean>() { // from class: androidx.credentials.provider.utils.BeginCreateCredentialUtil$Companion$convertToJetpackResponse$2
                @Override // kotlin.jvm.functions.Function1
                public final Boolean invoke(CreateEntry createEntry) {
                    return Boolean.valueOf(createEntry != null);
                }
            };
            Stream streamFilter = map.filter(new Predicate() { // from class: androidx.credentials.provider.utils.BeginCreateCredentialUtil$Companion$$ExternalSyntheticLambda1
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return BeginCreateCredentialUtil.Companion.convertToJetpackResponse$lambda$6(beginCreateCredentialUtil$Companion$convertToJetpackResponse$2, obj);
                }
            });
            final BeginCreateCredentialUtil$Companion$convertToJetpackResponse$3 beginCreateCredentialUtil$Companion$convertToJetpackResponse$3 = new Function1<CreateEntry, CreateEntry>() { // from class: androidx.credentials.provider.utils.BeginCreateCredentialUtil$Companion$convertToJetpackResponse$3
                @Override // kotlin.jvm.functions.Function1
                public final CreateEntry invoke(CreateEntry createEntry) {
                    Intrinsics.checkNotNull(createEntry);
                    return createEntry;
                }
            };
            Object objCollect = streamFilter.map(new Function() { // from class: androidx.credentials.provider.utils.BeginCreateCredentialUtil$Companion$$ExternalSyntheticLambda2
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return BeginCreateCredentialUtil.Companion.convertToJetpackResponse$lambda$7(beginCreateCredentialUtil$Companion$convertToJetpackResponse$3, obj);
                }
            }).collect(Collectors.toList());
            Intrinsics.checkNotNullExpressionValue(objCollect, "frameworkResponse.create…lect(Collectors.toList())");
            List list = (List) objCollect;
            android.service.credentials.RemoteEntry remoteCreateEntry = frameworkResponse.getRemoteCreateEntry();
            if (remoteCreateEntry != null) {
                RemoteEntry.Companion companion = RemoteEntry.INSTANCE;
                Slice slice = remoteCreateEntry.getSlice();
                Intrinsics.checkNotNullExpressionValue(slice, "it.slice");
                remoteEntryFromSlice = companion.fromSlice(slice);
            } else {
                remoteEntryFromSlice = null;
            }
            return new androidx.credentials.provider.BeginCreateCredentialResponse(list, remoteEntryFromSlice);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final CreateEntry convertToJetpackResponse$lambda$5(Function1 tmp0, Object obj) {
            Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
            return (CreateEntry) tmp0.invoke(obj);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final boolean convertToJetpackResponse$lambda$6(Function1 tmp0, Object obj) {
            Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
            return ((Boolean) tmp0.invoke(obj)).booleanValue();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final CreateEntry convertToJetpackResponse$lambda$7(Function1 tmp0, Object obj) {
            Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
            return (CreateEntry) tmp0.invoke(obj);
        }
    }
}
