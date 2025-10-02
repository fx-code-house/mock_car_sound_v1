package com.google.android.gms.wallet;

import android.accounts.Account;
import android.app.Activity;
import android.content.Context;
import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BaseImplementation;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.wallet.wobs.WalletObjects;
import java.util.Locale;

/* compiled from: com.google.android.gms:play-services-wallet@@18.0.0 */
/* loaded from: classes2.dex */
public final class Wallet {
    public static final Api<WalletOptions> API;
    private static final Api.ClientKey<com.google.android.gms.internal.wallet.zzv> CLIENT_KEY;
    private static final Api.AbstractClientBuilder<com.google.android.gms.internal.wallet.zzv, WalletOptions> zzeb;

    @Deprecated
    private static final zzz zzec;
    private static final WalletObjects zzed;
    private static final com.google.android.gms.internal.wallet.zzh zzee;

    /* compiled from: com.google.android.gms:play-services-wallet@@18.0.0 */
    public static abstract class zza<R extends Result> extends BaseImplementation.ApiMethodImpl<R, com.google.android.gms.internal.wallet.zzv> {
        public zza(GoogleApiClient googleApiClient) {
            super(Wallet.API, googleApiClient);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.android.gms.common.api.internal.BaseImplementation.ApiMethodImpl
        /* renamed from: zza, reason: merged with bridge method [inline-methods] */
        public abstract void doExecute(com.google.android.gms.internal.wallet.zzv zzvVar) throws RemoteException;
    }

    /* compiled from: com.google.android.gms:play-services-wallet@@18.0.0 */
    public static abstract class zzb extends zza<Status> {
        public zzb(GoogleApiClient googleApiClient) {
            super(googleApiClient);
        }

        @Override // com.google.android.gms.common.api.internal.BasePendingResult
        protected /* synthetic */ Result createFailedResult(Status status) {
            return status;
        }
    }

    public static PaymentsClient getPaymentsClient(Activity activity, WalletOptions walletOptions) {
        return new PaymentsClient(activity, walletOptions);
    }

    /* compiled from: com.google.android.gms:play-services-wallet@@18.0.0 */
    public static final class WalletOptions implements Api.ApiOptions.HasAccountOptions {
        private final Account account;
        public final int environment;
        public final int theme;
        final boolean zzef;

        private WalletOptions() {
            this(new Builder());
        }

        @Override // com.google.android.gms.common.api.Api.ApiOptions.HasAccountOptions
        public final Account getAccount() {
            return null;
        }

        private WalletOptions(Builder builder) {
            this.environment = builder.environment;
            this.theme = builder.theme;
            this.zzef = builder.zzef;
            this.account = null;
        }

        /* compiled from: com.google.android.gms:play-services-wallet@@18.0.0 */
        public static final class Builder {
            private int environment = 3;
            private int theme = 1;
            private boolean zzef = true;

            public final Builder setEnvironment(int i) {
                if (i == 0 || i == 0 || i == 2 || i == 1 || i == 3) {
                    this.environment = i;
                    return this;
                }
                throw new IllegalArgumentException(String.format(Locale.US, "Invalid environment value %d", Integer.valueOf(i)));
            }

            public final Builder setTheme(int i) {
                if (i == 0 || i == 1 || i == 2 || i == 3) {
                    this.theme = i;
                    return this;
                }
                throw new IllegalArgumentException(String.format(Locale.US, "Invalid theme value %d", Integer.valueOf(i)));
            }

            @Deprecated
            public final Builder useGoogleWallet() {
                this.zzef = false;
                return this;
            }

            public final WalletOptions build() {
                return new WalletOptions(this, null);
            }
        }

        public final boolean equals(Object obj) {
            if (!(obj instanceof WalletOptions)) {
                return false;
            }
            WalletOptions walletOptions = (WalletOptions) obj;
            return Objects.equal(Integer.valueOf(this.environment), Integer.valueOf(walletOptions.environment)) && Objects.equal(Integer.valueOf(this.theme), Integer.valueOf(walletOptions.theme)) && Objects.equal(null, null) && Objects.equal(Boolean.valueOf(this.zzef), Boolean.valueOf(walletOptions.zzef));
        }

        public final int hashCode() {
            return Objects.hashCode(Integer.valueOf(this.environment), Integer.valueOf(this.theme), null, Boolean.valueOf(this.zzef));
        }

        /* synthetic */ WalletOptions(Builder builder, zzaj zzajVar) {
            this(builder);
        }

        /* synthetic */ WalletOptions(zzaj zzajVar) {
            this();
        }
    }

    public static PaymentsClient getPaymentsClient(Context context, WalletOptions walletOptions) {
        return new PaymentsClient(context, walletOptions);
    }

    public static WalletObjectsClient getWalletObjectsClient(Activity activity, WalletOptions walletOptions) {
        return new WalletObjectsClient(activity, walletOptions);
    }

    private Wallet() {
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [com.google.android.gms.internal.wallet.zzs, com.google.android.gms.wallet.zzz] */
    /* JADX WARN: Type inference failed for: r0v3, types: [com.google.android.gms.internal.wallet.zzab, com.google.android.gms.internal.wallet.zzh] */
    static {
        Api.ClientKey<com.google.android.gms.internal.wallet.zzv> clientKey = new Api.ClientKey<>();
        CLIENT_KEY = clientKey;
        zzaj zzajVar = new zzaj();
        zzeb = zzajVar;
        API = new Api<>("Wallet.API", zzajVar, clientKey);
        zzec = new com.google.android.gms.internal.wallet.zzs();
        zzed = new com.google.android.gms.internal.wallet.zzaa();
        zzee = new com.google.android.gms.internal.wallet.zzab();
    }
}
