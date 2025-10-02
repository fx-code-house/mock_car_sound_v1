package com.thor.networkmodule.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import com.google.android.exoplayer2.source.rtsp.SessionDescription;
import com.google.android.gms.common.internal.ImagesContract;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.channels.IllegalBlockingModeException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.io.CloseableKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ConnectivityAndInternetAccess.kt */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u0000 \t2\u00020\u0001:\u0002\t\nB\u001d\u0012\u0016\u0010\u0002\u001a\u0012\u0012\u0004\u0012\u00020\u00040\u0003j\b\u0012\u0004\u0012\u00020\u0004`\u0005¢\u0006\u0002\u0010\u0006R@\u0010\u0002\u001a\u0012\u0012\u0004\u0012\u00020\u00040\u0003j\b\u0012\u0004\u0012\u00020\u0004`\u00052\u0016\u0010\u0002\u001a\u0012\u0012\u0004\u0012\u00020\u00040\u0003j\b\u0012\u0004\u0012\u00020\u0004`\u0005@BX\u0082\u000e¢\u0006\n\n\u0002\b\b\"\u0004\b\u0007\u0010\u0006¨\u0006\u000b"}, d2 = {"Lcom/thor/networkmodule/utils/ConnectivityAndInternetAccess;", "", "hosts", "Ljava/util/ArrayList;", "", "Lkotlin/collections/ArrayList;", "(Ljava/util/ArrayList;)V", "setHosts", "hosts$1", "Companion", "InternetConnectionCheck", "networkmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class ConnectivityAndInternetAccess {

    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static ArrayList<String> hosts = new ArrayList<String>() { // from class: com.thor.networkmodule.utils.ConnectivityAndInternetAccess$Companion$hosts$1
        {
            add("wolfram.com");
            add("google.com");
            add("apple.com");
            add("amazon.com");
            add("microsoft.com");
        }

        @Override // java.util.ArrayList, java.util.AbstractCollection, java.util.Collection, java.util.List
        public final /* bridge */ boolean contains(Object obj) {
            if (obj instanceof String) {
                return contains((String) obj);
            }
            return false;
        }

        public /* bridge */ boolean contains(String str) {
            return super.contains((Object) str);
        }

        public /* bridge */ int getSize() {
            return super.size();
        }

        @Override // java.util.ArrayList, java.util.AbstractList, java.util.List
        public final /* bridge */ int indexOf(Object obj) {
            if (obj instanceof String) {
                return indexOf((String) obj);
            }
            return -1;
        }

        public /* bridge */ int indexOf(String str) {
            return super.indexOf((Object) str);
        }

        @Override // java.util.ArrayList, java.util.AbstractList, java.util.List
        public final /* bridge */ int lastIndexOf(Object obj) {
            if (obj instanceof String) {
                return lastIndexOf((String) obj);
            }
            return -1;
        }

        public /* bridge */ int lastIndexOf(String str) {
            return super.lastIndexOf((Object) str);
        }

        @Override // java.util.ArrayList, java.util.AbstractList, java.util.List
        public final /* bridge */ String remove(int i) {
            return removeAt(i);
        }

        @Override // java.util.ArrayList, java.util.AbstractCollection, java.util.Collection, java.util.List
        public final /* bridge */ boolean remove(Object obj) {
            if (obj instanceof String) {
                return remove((String) obj);
            }
            return false;
        }

        public /* bridge */ boolean remove(String str) {
            return super.remove((Object) str);
        }

        public /* bridge */ String removeAt(int i) {
            return (String) super.remove(i);
        }

        @Override // java.util.ArrayList, java.util.AbstractCollection, java.util.Collection, java.util.List
        public final /* bridge */ int size() {
            return getSize();
        }
    };
    private static final int minimumSpeedForFastConnection = 3072;

    /* renamed from: hosts$1, reason: from kotlin metadata */
    private ArrayList<String> hosts;

    @JvmStatic
    public static final boolean isConnected(Context context) {
        return INSTANCE.isConnected(context);
    }

    @JvmStatic
    public static final boolean isConnectedOrConnecting(Context context) {
        return INSTANCE.isConnectedOrConnecting(context);
    }

    @JvmStatic
    public static final boolean isConnecting(Context context) {
        return INSTANCE.isConnecting(context);
    }

    public ConnectivityAndInternetAccess(ArrayList<String> hosts2) {
        Intrinsics.checkNotNullParameter(hosts2, "hosts");
        this.hosts = hosts2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void setHosts(ArrayList<String> arrayList) {
        hosts = arrayList;
        this.hosts = arrayList;
    }

    /* compiled from: ConnectivityAndInternetAccess.kt */
    @Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\b\u0002\u0018\u00002\u0018\u0012\u0006\u0012\u0004\u0018\u00010\u0002\u0012\u0006\u0012\u0004\u0018\u00010\u0002\u0012\u0004\u0012\u00020\u00030\u0001B\u000f\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0006J%\u0010\n\u001a\u00020\u00032\u0016\u0010\u000b\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00020\f\"\u0004\u0018\u00010\u0002H\u0014¢\u0006\u0002\u0010\rJ\b\u0010\u000e\u001a\u00020\u000fH\u0014R \u0010\u0004\u001a\u0004\u0018\u00010\u00058\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\u0006¨\u0006\u0010"}, d2 = {"Lcom/thor/networkmodule/utils/ConnectivityAndInternetAccess$InternetConnectionCheck;", "Landroid/os/AsyncTask;", "Ljava/lang/Void;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "getContext", "()Landroid/content/Context;", "setContext", "doInBackground", "voids", "", "([Ljava/lang/Void;)Ljava/lang/Boolean;", "onPreExecute", "", "networkmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    private static final class InternetConnectionCheck extends AsyncTask<Void, Void, Boolean> {
        private Context context;

        public InternetConnectionCheck(Context context) {
            this.context = context;
        }

        public final Context getContext() {
            return this.context;
        }

        public final void setContext(Context context) {
            this.context = context;
        }

        @Override // android.os.AsyncTask
        protected void onPreExecute() {
            if (ConnectivityAndInternetAccess.INSTANCE.isConnected(this.context)) {
                return;
            }
            cancel(true);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public Boolean doInBackground(Void... voids) {
            Intrinsics.checkNotNullParameter(voids, "voids");
            return Boolean.valueOf(ConnectivityAndInternetAccess.INSTANCE.isConnectedToInternet(this.context));
        }
    }

    /* compiled from: ConnectivityAndInternetAccess.kt */
    @Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0019\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0012\u0010\t\u001a\u0004\u0018\u00010\n2\u0006\u0010\u000b\u001a\u00020\fH\u0002J\u001f\u0010\r\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u000f0\u000e2\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u0002¢\u0006\u0002\u0010\u0010J\u0012\u0010\u0011\u001a\u00020\u00122\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u0002J\u0012\u0010\u0013\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\u000b\u001a\u00020\fH\u0002J\u001c\u0010\u0013\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\u000b\u001a\u00020\f2\b\u0010\u0014\u001a\u0004\u0018\u00010\nH\u0002J\u000e\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u000b\u001a\u00020\fJ\u0010\u0010\u0017\u001a\u00020\u00162\u0006\u0010\u000b\u001a\u00020\fH\u0007J\u0018\u0010\u0018\u001a\u00020\u00162\u0006\u0010\u000b\u001a\u00020\f2\b\u0010\u0014\u001a\u0004\u0018\u00010\nJ\u0012\u0010\u0018\u001a\u00020\u00162\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u0007J\u0018\u0010\u0019\u001a\u00020\u00162\u0006\u0010\u000b\u001a\u00020\f2\b\u0010\u0014\u001a\u0004\u0018\u00010\nJ\u0010\u0010\u0019\u001a\u00020\u00162\b\u0010\u000b\u001a\u0004\u0018\u00010\fJ\u000e\u0010\u001a\u001a\u00020\u00162\u0006\u0010\u000b\u001a\u00020\fJ\u0018\u0010\u001a\u001a\u00020\u00162\u0006\u0010\u000b\u001a\u00020\f2\b\u0010\u0014\u001a\u0004\u0018\u00010\nJ\u0018\u0010\u001b\u001a\u00020\u00162\u0006\u0010\u000b\u001a\u00020\f2\b\u0010\u0014\u001a\u0004\u0018\u00010\nJ\u0010\u0010\u001b\u001a\u00020\u00162\b\u0010\u000b\u001a\u0004\u0018\u00010\fJ\u000e\u0010\u001c\u001a\u00020\u00162\u0006\u0010\u000b\u001a\u00020\fJ\u0012\u0010\u001d\u001a\u00020\u00162\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u0007J(\u0010\u001e\u001a\u00020\u00162\u0006\u0010\u000b\u001a\u00020\f2\u0016\u0010\u0003\u001a\u0012\u0012\u0004\u0012\u00020\u00050\u0004j\b\u0012\u0004\u0012\u00020\u0005`\u0006H\u0002J\u0012\u0010\u001e\u001a\u00020\u00162\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u0002J\u0018\u0010\u001f\u001a\u00020\u00162\u0006\u0010\u000b\u001a\u00020\f2\b\u0010\u0014\u001a\u0004\u0018\u00010\nJ\u0010\u0010\u001f\u001a\u00020\u00162\b\u0010\u000b\u001a\u0004\u0018\u00010\fJ\u000e\u0010 \u001a\u00020\u00162\u0006\u0010\u000b\u001a\u00020\fJ\u0018\u0010 \u001a\u00020\u00162\u0006\u0010\u000b\u001a\u00020\f2\b\u0010\u0014\u001a\u0004\u0018\u00010\nJ\u0012\u0010!\u001a\u00020\u00162\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u0007J\u0018\u0010\"\u001a\u00020\u00162\u0006\u0010#\u001a\u00020\b2\u0006\u0010$\u001a\u00020\bH\u0002J\u001a\u0010%\u001a\u00020\u00162\u0006\u0010&\u001a\u00020\u00052\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u0002J\u0010\u0010'\u001a\u00020\u00162\b\u0010\u000b\u001a\u0004\u0018\u00010\fJ(\u0010'\u001a\u00020\u00162\b\u0010\u000b\u001a\u0004\u0018\u00010\f2\u0016\u0010\u0003\u001a\u0012\u0012\u0004\u0012\u00020\u00050\u0004j\b\u0012\u0004\u0012\u00020\u0005`\u0006J\u0018\u0010(\u001a\u00020\u00162\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0014\u001a\u00020\nH\u0002J\u0018\u0010)\u001a\u00020\u00162\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0014\u001a\u00020\nH\u0002J\u0018\u0010*\u001a\u00020\u00162\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0014\u001a\u00020\nH\u0002J\u0010\u0010+\u001a\u00020\u00162\u0006\u0010\u000b\u001a\u00020\fH\u0002J\u0012\u0010,\u001a\u00020\u00162\b\u0010-\u001a\u0004\u0018\u00010\u0005H\u0002J$\u0010,\u001a\u00020\u00162\u001a\u0010-\u001a\u0016\u0012\u0004\u0012\u00020\u0005\u0018\u00010\u0004j\n\u0012\u0004\u0012\u00020\u0005\u0018\u0001`\u0006H\u0002J\u000e\u0010.\u001a\u00020\u00162\u0006\u0010\u000b\u001a\u00020\fR\u001e\u0010\u0003\u001a\u0012\u0012\u0004\u0012\u00020\u00050\u0004j\b\u0012\u0004\u0012\u00020\u0005`\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082T¢\u0006\u0002\n\u0000¨\u0006/"}, d2 = {"Lcom/thor/networkmodule/utils/ConnectivityAndInternetAccess$Companion;", "", "()V", "hosts", "Ljava/util/ArrayList;", "", "Lkotlin/collections/ArrayList;", "minimumSpeedForFastConnection", "", "getActiveNetwork", "Landroid/net/Network;", "context", "Landroid/content/Context;", "getAllNetworkInfo", "", "Landroid/net/NetworkInfo;", "(Landroid/content/Context;)[Landroid/net/NetworkInfo;", "getConnectivityManager", "Landroid/net/ConnectivityManager;", "getNetworkInformation", "network", "isActiveNetworkConnected", "", "isAirplaneModeOn", "isConnected", "isConnectedEthernet", "isConnectedFast", "isConnectedMobile", "isConnectedMobileTelephonyManager", "isConnectedOrConnecting", "isConnectedToInternet", "isConnectedWifi", "isConnectedWifiOverAirplaneMode", "isConnecting", "isConnectionFast", SessionDescription.ATTR_TYPE, "subType", "isHostAvailable", "hostName", "isInternetReachable", "isNetworkFacilitatingFastNetworkSwitching", "isNetworkSuspended", "isNetworkUsableByApps", "isThereAnActiveNetworkConnection", "isValidURL", ImagesContract.URL, "vpnActive", "networkmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private final boolean isConnectionFast(int type, int subType) {
            if (type == 0) {
                switch (subType) {
                    case 0:
                    case 1:
                    case 2:
                    case 4:
                    case 7:
                    case 11:
                    default:
                        return false;
                    case 3:
                    case 5:
                    case 6:
                    case 8:
                    case 9:
                    case 10:
                    case 12:
                    case 13:
                    case 14:
                    case 15:
                        break;
                }
            } else if (type != 1 && type != 9) {
                return false;
            }
            return true;
        }

        private Companion() {
        }

        private final NetworkInfo getNetworkInformation(Context context) throws UnsupportedOperationException {
            Log.e("UnusableMethod", "Cannot use this method for the current API Level");
            throw new UnsupportedOperationException("Cannot use this method for the current API level");
        }

        private final boolean isThereAnActiveNetworkConnection(Context context) {
            return getActiveNetwork(context) != null;
        }

        private final Network getActiveNetwork(Context context) {
            Object systemService = context.getSystemService("connectivity");
            Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.net.ConnectivityManager");
            return ((ConnectivityManager) systemService).getActiveNetwork();
        }

        private final NetworkInfo getNetworkInformation(Context context, Network network) throws IllegalArgumentException {
            if (network != null) {
                Object systemService = context.getSystemService("connectivity");
                Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.net.ConnectivityManager");
                return ((ConnectivityManager) systemService).getNetworkInfo(network);
            }
            throw new IllegalArgumentException("Null value for any parameter is illegal");
        }

        private final NetworkInfo[] getAllNetworkInfo(Context context) {
            if (context != null) {
                Object systemService = context.getSystemService("connectivity");
                Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.net.ConnectivityManager");
                NetworkInfo[] networkInfoArr = new NetworkInfo[0];
                Log.e("UnusableMethod", "Cannot use this method for the current API Level");
                return networkInfoArr;
            }
            throw new IllegalArgumentException("Null value for the context is illegal");
        }

        private final ConnectivityManager getConnectivityManager(Context context) throws IllegalArgumentException {
            if (context != null) {
                Object systemService = context.getSystemService("connectivity");
                Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.net.ConnectivityManager");
                return (ConnectivityManager) systemService;
            }
            throw new IllegalArgumentException("Null value for the context is illegal");
        }

        private final boolean isNetworkFacilitatingFastNetworkSwitching(Context context, Network network) {
            Object systemService = context.getSystemService("connectivity");
            Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.net.ConnectivityManager");
            ConnectivityManager connectivityManager = (ConnectivityManager) systemService;
            if (Build.VERSION.SDK_INT >= 28) {
                NetworkCapabilities networkCapabilities = connectivityManager.getNetworkCapabilities(network);
                if (networkCapabilities != null && !networkCapabilities.hasCapability(19)) {
                    return true;
                }
            } else {
                Log.e("UnusableMethod", new String());
            }
            return false;
        }

        private final boolean isNetworkUsableByApps(Context context, Network network) throws IllegalArgumentException {
            Object systemService = context.getSystemService("connectivity");
            Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.net.ConnectivityManager");
            ConnectivityManager connectivityManager = (ConnectivityManager) systemService;
            if (Build.VERSION.SDK_INT >= 28) {
                NetworkCapabilities networkCapabilities = connectivityManager.getNetworkCapabilities(network);
                return networkCapabilities != null && networkCapabilities.hasCapability(19);
            }
            Log.e("UnusableMethod", new String());
            throw new UnsupportedOperationException("Cannot use this method for the current API Level");
        }

        private final boolean isNetworkSuspended(Context context, Network network) throws IllegalArgumentException {
            Object systemService = context.getSystemService("connectivity");
            Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.net.ConnectivityManager");
            ConnectivityManager connectivityManager = (ConnectivityManager) systemService;
            if (Build.VERSION.SDK_INT >= 28) {
                NetworkCapabilities networkCapabilities = connectivityManager.getNetworkCapabilities(network);
                return (networkCapabilities == null || networkCapabilities.hasCapability(21)) ? false : true;
            }
            Log.e("UnusableMethod", new String());
            throw new UnsupportedOperationException("Cannot use this method for the current API Level");
        }

        /* JADX WARN: Removed duplicated region for block: B:27:0x0064  */
        /* JADX WARN: Removed duplicated region for block: B:28:0x006a  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public final boolean isActiveNetworkConnected(android.content.Context r6) throws java.lang.IllegalArgumentException {
            /*
                r5 = this;
                java.lang.String r0 = "context"
                kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r6, r0)
                android.net.ConnectivityManager r0 = r5.getConnectivityManager(r6)
                android.net.Network r6 = r5.getActiveNetwork(r6)
                if (r6 == 0) goto L55
                android.net.NetworkCapabilities r6 = r0.getNetworkCapabilities(r6)
                if (r6 == 0) goto L4a
                int r0 = android.os.Build.VERSION.SDK_INT
                r1 = 28
                r2 = 1
                r3 = 16
                r4 = 12
                if (r0 < r1) goto L3d
                boolean r0 = r6.hasCapability(r4)
                if (r0 == 0) goto L5f
                boolean r0 = r6.hasCapability(r3)
                if (r0 == 0) goto L5f
                r0 = 21
                boolean r0 = r6.hasCapability(r0)
                if (r0 == 0) goto L5f
                r0 = 19
                boolean r6 = r6.hasCapability(r0)
                if (r6 == 0) goto L5f
                goto L60
            L3d:
                boolean r0 = r6.hasCapability(r4)
                if (r0 == 0) goto L5f
                boolean r6 = r6.hasCapability(r3)
                if (r6 == 0) goto L5f
                goto L60
            L4a:
                java.lang.String r6 = new java.lang.String
                r6.<init>()
                java.lang.String r0 = "NullNetworkCapabilities"
                android.util.Log.e(r0, r6)
                goto L5f
            L55:
                java.lang.String r6 = new java.lang.String
                r6.<init>()
                java.lang.String r0 = "NullNetwork"
                android.util.Log.e(r0, r6)
            L5f:
                r2 = 0
            L60:
                java.lang.String r6 = "NetworkInfo"
                if (r2 == 0) goto L6a
                java.lang.String r0 = "Connected State"
                android.util.Log.v(r6, r0)
                goto L6f
            L6a:
                java.lang.String r0 = "Not Connected State"
                android.util.Log.v(r6, r0)
            L6f:
                return r2
            */
            throw new UnsupportedOperationException("Method not decompiled: com.thor.networkmodule.utils.ConnectivityAndInternetAccess.Companion.isActiveNetworkConnected(android.content.Context):boolean");
        }

        public final boolean isConnected(Context context, Network network) throws IllegalArgumentException {
            Intrinsics.checkNotNullParameter(context, "context");
            if (network != null) {
                NetworkCapabilities networkCapabilities = getConnectivityManager(context).getNetworkCapabilities(network);
                boolean z = true;
                if (Build.VERSION.SDK_INT >= 28) {
                    if (networkCapabilities != null) {
                        if (!networkCapabilities.hasCapability(12) || !networkCapabilities.hasCapability(16) || !networkCapabilities.hasCapability(21) || !networkCapabilities.hasCapability(19)) {
                        }
                    } else {
                        Log.e("UnusableMethod", "null NetworkCapabilities");
                    }
                    z = false;
                } else if (networkCapabilities == null || !networkCapabilities.hasCapability(12) || !networkCapabilities.hasCapability(16)) {
                    z = false;
                }
                if (z) {
                    Log.v("NetworkInfo", "Connected State");
                } else {
                    Log.v("NetworkInfo", "Not Connected State");
                }
                return z;
            }
            throw new IllegalArgumentException("Null value for the context is illegal");
        }

        @JvmStatic
        public final boolean isConnecting(Context context) throws IllegalArgumentException {
            getConnectivityManager(context);
            throw new UnsupportedOperationException("Cannot use this method for the current API level");
        }

        @JvmStatic
        public final boolean isConnectedOrConnecting(Context context) throws IllegalArgumentException {
            getConnectivityManager(context);
            throw new UnsupportedOperationException("Cannot use this method for the current API level");
        }

        @JvmStatic
        public final boolean isConnected(Context context) throws IllegalArgumentException {
            boolean zIsActiveNetworkConnected;
            if (context != null) {
                ConnectivityManager connectivityManager = getConnectivityManager(context);
                if (isThereAnActiveNetworkConnection(context)) {
                    zIsActiveNetworkConnected = isActiveNetworkConnected(context);
                } else {
                    Network[] allNetworks = connectivityManager.getAllNetworks();
                    Intrinsics.checkNotNullExpressionValue(allNetworks, "connectivityManager.allNetworks");
                    for (Network network : allNetworks) {
                        if (network != null) {
                            NetworkCapabilities networkCapabilities = connectivityManager.getNetworkCapabilities(network);
                            if (networkCapabilities != null) {
                                if (Build.VERSION.SDK_INT >= 28) {
                                    if (networkCapabilities.hasCapability(12) && networkCapabilities.hasCapability(16) && networkCapabilities.hasCapability(21) && networkCapabilities.hasCapability(19)) {
                                        zIsActiveNetworkConnected = true;
                                        break;
                                    }
                                } else if (networkCapabilities.hasCapability(12) && networkCapabilities.hasCapability(16)) {
                                    zIsActiveNetworkConnected = true;
                                    break;
                                }
                            } else {
                                Log.e("NullNetworkCapabilities", new String());
                            }
                        } else {
                            Log.e("NullNetwork", new String());
                        }
                    }
                    zIsActiveNetworkConnected = false;
                }
                if (zIsActiveNetworkConnected) {
                    Log.v("NetworkInfo", "Connected State");
                } else {
                    Log.v("NetworkInfo", "Not Connected State");
                }
                return zIsActiveNetworkConnected;
            }
            throw new IllegalArgumentException("Null value for the context is illegal");
        }

        public final boolean isConnectedWifi(Context context) throws IllegalArgumentException {
            ConnectivityManager connectivityManager = getConnectivityManager(context);
            Network[] allNetworks = connectivityManager.getAllNetworks();
            Intrinsics.checkNotNullExpressionValue(allNetworks, "connectivityManager.allNetworks");
            boolean z = false;
            for (Network network : allNetworks) {
                if (network != null) {
                    NetworkCapabilities networkCapabilities = connectivityManager.getNetworkCapabilities(network);
                    if (networkCapabilities != null) {
                        if (Build.VERSION.SDK_INT >= 28) {
                            if (networkCapabilities.hasCapability(12) && networkCapabilities.hasCapability(16) && networkCapabilities.hasCapability(21) && networkCapabilities.hasCapability(19) && networkCapabilities.hasTransport(1)) {
                                z = true;
                                break;
                            }
                        } else if (networkCapabilities.hasCapability(12) && networkCapabilities.hasCapability(16) && networkCapabilities.hasTransport(1)) {
                            z = true;
                            break;
                        }
                    } else {
                        Log.e("NullNetworkCapabilities", new String());
                    }
                } else {
                    Log.e("NullNetwork", new String());
                }
            }
            if (z) {
                Log.v("NetworkInfo", "Connected Wi-Fi State");
            } else {
                Log.v("NetworkInfo", "Not Connected Wi-Fi State");
            }
            return z;
        }

        public final boolean isConnectedWifi(Context context, Network network) {
            Intrinsics.checkNotNullParameter(context, "context");
            NetworkCapabilities networkCapabilities = getConnectivityManager(context).getNetworkCapabilities(network);
            boolean z = true;
            if (Build.VERSION.SDK_INT >= 28) {
                if (network == null) {
                    Log.e("NullNetwork", "a null Network object was found");
                } else if (networkCapabilities != null) {
                    if (!networkCapabilities.hasCapability(12) || !networkCapabilities.hasCapability(16) || !networkCapabilities.hasCapability(21) || !networkCapabilities.hasCapability(19) || !networkCapabilities.hasTransport(1)) {
                    }
                } else {
                    Log.e("NullNetworkCapabilities", "a null NetworkCapabilities object was found");
                }
                z = false;
            } else {
                if (network == null) {
                    Log.e("NullNetwork", "a null Network object was found");
                } else if (networkCapabilities != null) {
                    if (!networkCapabilities.hasCapability(12) || !networkCapabilities.hasCapability(16) || !networkCapabilities.hasTransport(1)) {
                    }
                } else {
                    Log.e("NullNetwordCapabilities", "a null NetworkCapabilities object was found");
                }
                z = false;
            }
            if (z) {
                Log.v("NetworkInfo", "Connected Wi-Fi State");
            } else {
                Log.v("NetworkInfo", "Not Connected Wi-Fi State");
            }
            return z;
        }

        public final boolean isConnectedWifiOverAirplaneMode(Context context) {
            Intrinsics.checkNotNullParameter(context, "context");
            return isConnectedWifi(context) && isAirplaneModeOn(context);
        }

        public final boolean isConnectedWifiOverAirplaneMode(Context context, Network network) {
            Intrinsics.checkNotNullParameter(context, "context");
            return isConnectedWifi(context, network) && isAirplaneModeOn(context);
        }

        public final boolean isConnectedMobileTelephonyManager(Context context) {
            Intrinsics.checkNotNullParameter(context, "context");
            Object systemService = context.getSystemService("phone");
            Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.telephony.TelephonyManager");
            return ((TelephonyManager) systemService).getDataState() == 2;
        }

        public final boolean isConnectedMobile(Context context, Network network) {
            Intrinsics.checkNotNullParameter(context, "context");
            NetworkCapabilities networkCapabilities = getConnectivityManager(context).getNetworkCapabilities(network);
            boolean z = true;
            if (Build.VERSION.SDK_INT >= 28) {
                if (network == null) {
                    Log.e("NullNetwork", "A null Network object was found");
                } else if (networkCapabilities != null) {
                    if (!networkCapabilities.hasCapability(12) || !networkCapabilities.hasCapability(16) || !networkCapabilities.hasCapability(21) || !networkCapabilities.hasCapability(19) || !networkCapabilities.hasTransport(0)) {
                    }
                } else {
                    Log.e("NullNetworkCapabilities", "A null NetworkCapabilities object was found");
                }
                z = false;
            } else {
                if (network == null) {
                    Log.e("NullNetwork", "a null Network object was found");
                } else if (networkCapabilities != null) {
                    if (!networkCapabilities.hasCapability(12) || !networkCapabilities.hasCapability(16) || !networkCapabilities.hasTransport(0)) {
                    }
                } else {
                    Log.e("NullNetworkCapabilities", "a null NetworkCapabilities object was found");
                }
                z = false;
            }
            if (z) {
                Log.v("NetworkInfo", "Connected Mobile State");
            } else {
                Log.v("NetworkInfo", "Not Connected Mobile State");
            }
            return z;
        }

        public final boolean isConnectedMobile(Context context) throws IllegalArgumentException {
            ConnectivityManager connectivityManager = getConnectivityManager(context);
            Network[] allNetworks = connectivityManager.getAllNetworks();
            Intrinsics.checkNotNullExpressionValue(allNetworks, "connectivityManager.allNetworks");
            boolean z = false;
            for (Network network : allNetworks) {
                NetworkCapabilities networkCapabilities = connectivityManager.getNetworkCapabilities(network);
                if (Build.VERSION.SDK_INT >= 28) {
                    if (network == null) {
                        Log.e("NullNetwork", "a null Network object was found");
                    } else if (networkCapabilities != null) {
                        if (networkCapabilities.hasCapability(12) && networkCapabilities.hasCapability(16) && networkCapabilities.hasCapability(21) && networkCapabilities.hasCapability(19) && networkCapabilities.hasTransport(0)) {
                            z = true;
                            break;
                        }
                    } else {
                        Log.e("NullNetworkCapabilities", "a null NetworkCapabilities object was found");
                    }
                } else {
                    if (network == null) {
                        Log.e("NullNetwork", "a null Network object was found");
                    } else if (networkCapabilities != null) {
                        if (networkCapabilities.hasCapability(12) && networkCapabilities.hasCapability(16) && networkCapabilities.hasTransport(0)) {
                            z = true;
                            break;
                        }
                    } else {
                        Log.e("NullNetworkCapabilities", "a null NetworkCapabilities object was found");
                    }
                }
            }
            if (z) {
                Log.v("NetworkInfo", "Connected Mobile State");
            } else {
                Log.v("NetworkInfo", "Not Connected Mobile State");
            }
            return z;
        }

        public final boolean isConnectedEthernet(Context context) throws IllegalArgumentException {
            ConnectivityManager connectivityManager = getConnectivityManager(context);
            Network[] allNetworks = connectivityManager.getAllNetworks();
            Intrinsics.checkNotNullExpressionValue(allNetworks, "connectivityManager.allNetworks");
            boolean z = false;
            for (Network network : allNetworks) {
                NetworkCapabilities networkCapabilities = connectivityManager.getNetworkCapabilities(network);
                if (Build.VERSION.SDK_INT >= 28) {
                    if (network == null) {
                        Log.e("NullNetwork", "a null Network object was found");
                    } else if (networkCapabilities != null) {
                        if (networkCapabilities.hasCapability(12) && networkCapabilities.hasCapability(16) && networkCapabilities.hasCapability(21) && networkCapabilities.hasCapability(19) && networkCapabilities.hasTransport(3)) {
                            z = true;
                            break;
                        }
                    } else {
                        Log.e("NullNetworkCapabilities", "a null NetworkCapabilities object was found");
                    }
                } else {
                    if (network == null) {
                        Log.e("NullNetwork", "a null Network object was found");
                    } else if (networkCapabilities != null) {
                        if (networkCapabilities.hasCapability(12) && networkCapabilities.hasCapability(16) && networkCapabilities.hasTransport(3)) {
                            z = true;
                            break;
                        }
                    } else {
                        Log.e("NullNetworkCapabilities", "a null NetworkCapabilities object was found");
                    }
                }
            }
            if (z) {
                Log.v("NetworkInfo", "Connected Ethernet State");
            } else {
                Log.v("NetworkInfo", "Not Connected Ethernet State");
            }
            return z;
        }

        public final boolean isConnectedEthernet(Context context, Network network) {
            Intrinsics.checkNotNullParameter(context, "context");
            NetworkCapabilities networkCapabilities = getConnectivityManager(context).getNetworkCapabilities(network);
            boolean z = true;
            if (Build.VERSION.SDK_INT >= 28) {
                if (network == null) {
                    Log.e("NullNetwork", "a null Network object was found");
                } else if (networkCapabilities != null) {
                    if (!networkCapabilities.hasCapability(12) || !networkCapabilities.hasCapability(16) || !networkCapabilities.hasCapability(21) || !networkCapabilities.hasCapability(19) || !networkCapabilities.hasTransport(3)) {
                    }
                } else {
                    Log.e("NullNetworkCapabilities", "a null NetworkCapabilities object was found");
                }
                z = false;
            } else {
                if (network == null) {
                    Log.e("NullNetwork", "a null Network object was found");
                } else if (networkCapabilities != null) {
                    if (!networkCapabilities.hasCapability(12) || !networkCapabilities.hasCapability(16) || !networkCapabilities.hasTransport(3)) {
                    }
                } else {
                    Log.e("NullNetworkCapabilities", "a null NetworkCapabilities object was found");
                }
                z = false;
            }
            if (z) {
                Log.v("NetworkInfo", "Connected Ethernet State");
            } else {
                Log.v("NetworkInfo", "Not Connected Ethernet State");
            }
            return z;
        }

        public final boolean isConnectedFast(Context context) throws IllegalArgumentException {
            NetworkCapabilities networkCapabilities;
            Intrinsics.checkNotNullParameter(context, "context");
            ConnectivityManager connectivityManager = getConnectivityManager(context);
            Network[] allNetworks = connectivityManager.getAllNetworks();
            Intrinsics.checkNotNullExpressionValue(allNetworks, "connectivityManager.allNetworks");
            int length = allNetworks.length;
            boolean z = false;
            int i = 0;
            while (true) {
                if (i >= length) {
                    break;
                }
                Network network = allNetworks[i];
                if (network != null && isConnected(context, network) && (networkCapabilities = connectivityManager.getNetworkCapabilities(network)) != null) {
                    int linkDownstreamBandwidthKbps = networkCapabilities.getLinkDownstreamBandwidthKbps();
                    int linkUpstreamBandwidthKbps = networkCapabilities.getLinkUpstreamBandwidthKbps();
                    if (linkDownstreamBandwidthKbps >= ConnectivityAndInternetAccess.minimumSpeedForFastConnection && linkUpstreamBandwidthKbps >= ConnectivityAndInternetAccess.minimumSpeedForFastConnection) {
                        z = true;
                        break;
                    }
                }
                i++;
            }
            if (z) {
                Log.v("NetworkInfo", "Connected Fast State");
            } else {
                Log.v("NetworkInfo", "Not Connected Fast State");
            }
            return z;
        }

        /* JADX WARN: Removed duplicated region for block: B:12:0x0027  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public final boolean isConnectedFast(android.content.Context r2, android.net.Network r3) throws java.lang.IllegalArgumentException {
            /*
                r1 = this;
                java.lang.String r0 = "context"
                kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r2, r0)
                android.net.ConnectivityManager r0 = r1.getConnectivityManager(r2)
                if (r3 == 0) goto L27
                boolean r2 = r1.isConnected(r2, r3)
                if (r2 == 0) goto L27
                android.net.NetworkCapabilities r2 = r0.getNetworkCapabilities(r3)
                if (r2 == 0) goto L27
                int r3 = r2.getLinkDownstreamBandwidthKbps()
                int r2 = r2.getLinkUpstreamBandwidthKbps()
                r0 = 3072(0xc00, float:4.305E-42)
                if (r3 < r0) goto L27
                if (r2 < r0) goto L27
                r2 = 1
                goto L28
            L27:
                r2 = 0
            L28:
                java.lang.String r3 = "NetworkInfo"
                if (r2 == 0) goto L32
                java.lang.String r0 = "Connected Fast State"
                android.util.Log.v(r3, r0)
                goto L37
            L32:
                java.lang.String r0 = "Not Connected Fast State"
                android.util.Log.v(r3, r0)
            L37:
                return r2
            */
            throw new UnsupportedOperationException("Method not decompiled: com.thor.networkmodule.utils.ConnectivityAndInternetAccess.Companion.isConnectedFast(android.content.Context, android.net.Network):boolean");
        }

        public final boolean isAirplaneModeOn(Context context) {
            Intrinsics.checkNotNullParameter(context, "context");
            boolean z = Settings.Global.getInt(context.getContentResolver(), "airplane_mode_on", 0) != 0;
            if (z) {
                Log.v("NetworkInfo", "Airplane Mode is On");
            } else {
                Log.v("NetworkInfo", "Airplane Mode is Off");
            }
            return z;
        }

        public final boolean vpnActive(Context context) throws UnsupportedOperationException {
            Intrinsics.checkNotNullParameter(context, "context");
            Object systemService = context.getSystemService("connectivity");
            Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.net.ConnectivityManager");
            NetworkCapabilities networkCapabilities = ((ConnectivityManager) systemService).getNetworkCapabilities(getActiveNetwork(context));
            return networkCapabilities != null && networkCapabilities.hasTransport(4);
        }

        public final boolean isInternetReachable(Context context) throws ExecutionException, InterruptedException {
            try {
                Boolean bool = new InternetConnectionCheck(context).execute(new Void[0]).get();
                Intrinsics.checkNotNullExpressionValue(bool, "it.execute()\n                        .get()");
                return bool.booleanValue();
            } catch (CancellationException e) {
                e.printStackTrace();
                return false;
            } catch (Exception e2) {
                e2.printStackTrace();
                return false;
            }
        }

        public final boolean isInternetReachable(Context context, ArrayList<String> hosts) throws ExecutionException, InterruptedException {
            Intrinsics.checkNotNullParameter(hosts, "hosts");
            try {
                InternetConnectionCheck internetConnectionCheck = new InternetConnectionCheck(context);
                new ConnectivityAndInternetAccess(hosts).setHosts(hosts);
                Boolean bool = internetConnectionCheck.execute(new Void[0]).get();
                Intrinsics.checkNotNullExpressionValue(bool, "internetConnectionCheckAsync.execute().get()");
                return bool.booleanValue();
            } catch (CancellationException e) {
                e.printStackTrace();
                return false;
            } catch (Exception e2) {
                e2.printStackTrace();
                return false;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final boolean isConnectedToInternet(Context context) {
            boolean z = false;
            while (isConnected(context)) {
                try {
                    Iterator it = ConnectivityAndInternetAccess.hosts.iterator();
                    while (true) {
                        if (it.hasNext()) {
                            String h = (String) it.next();
                            Intrinsics.checkNotNullExpressionValue(h, "h");
                            if (isHostAvailable(h, context)) {
                                z = true;
                                break;
                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return z;
        }

        private final boolean isConnectedToInternet(Context context, ArrayList<String> hosts) {
            boolean z = false;
            while (isConnected(context)) {
                try {
                    Iterator<String> it = hosts.iterator();
                    while (true) {
                        if (it.hasNext()) {
                            String h = it.next();
                            Intrinsics.checkNotNullExpressionValue(h, "h");
                            if (isHostAvailable(h, context)) {
                                z = true;
                                break;
                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return z;
        }

        private final boolean isValidURL(String url) throws URISyntaxException {
            if (url != null) {
                try {
                    new URL(url).toURI();
                    return true;
                } catch (MalformedURLException | URISyntaxException unused) {
                    return false;
                }
            }
            throw new IllegalArgumentException("The URL cannot be null");
        }

        private final boolean isValidURL(ArrayList<String> url) {
            if (url != null) {
                Iterator<String> it = url.iterator();
                while (it.hasNext()) {
                    if (!isValidURL(it.next())) {
                        return false;
                    }
                }
                return true;
            }
            throw new IllegalArgumentException("The URL cannot be null");
        }

        private final boolean isHostAvailable(String hostName, Context context) throws IllegalBlockingModeException, IOException, IllegalArgumentException {
            if (context != null) {
                boolean z = false;
                while (isConnected(context)) {
                    try {
                        Socket socket = new Socket();
                        try {
                            Socket socket2 = socket;
                            if (!ConnectivityAndInternetAccess.INSTANCE.isValidURL("http:/" + hostName) && !ConnectivityAndInternetAccess.INSTANCE.isValidURL("http://" + hostName) && !ConnectivityAndInternetAccess.INSTANCE.isValidURL("ftp://@" + hostName)) {
                                throw new MalformedURLException("The URL is invalid");
                            }
                            socket2.connect(new InetSocketAddress(hostName, 80), 3000);
                            Unit unit = Unit.INSTANCE;
                            CloseableKt.closeFinally(socket, null);
                        } finally {
                        }
                    } catch (Exception e) {
                        if (e instanceof IOException ? true : e instanceof SocketTimeoutException ? true : e instanceof IllegalBlockingModeException ? true : e instanceof IllegalArgumentException) {
                            return false;
                        }
                        if (e instanceof UnknownHostException) {
                            e.printStackTrace();
                            Log.e("Internet Access", "Unknown host: " + hostName);
                        } else if (e instanceof SecurityException) {
                            e.printStackTrace();
                            Log.e("Internet Access", "A security manager exists and its checkConnect method does not allow the operation");
                        }
                        e.printStackTrace();
                        Log.e("Internet Access", "An non-ordinary exception ocurred when verifying Internet Access");
                        throw new UnsupportedOperationException("The current operation is not supported because an uncommon exception occurred");
                    }
                }
                Log.e("Internet Access", "Could not verify Internet access because the device lost the network connectivity");
                if (z) {
                    Log.v("Internet Access", "Host reached: " + hostName);
                } else {
                    Log.v("Internet Access", "Host not available: " + hostName);
                }
                return z;
            }
            throw new IllegalArgumentException("Parameters cannot take null value");
        }
    }
}
