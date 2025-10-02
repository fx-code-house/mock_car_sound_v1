package com.thor.networkmodule.model.responses.subscriptions;

import com.android.billingclient.api.BillingClient;
import com.google.android.gms.measurement.api.AppMeasurementSdk;
import com.google.gson.annotations.SerializedName;
import com.thor.networkmodule.model.responses.BaseResponse;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: SubscriptionsResponse.kt */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001:\u0001\u0012B\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u0005J\u000f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003HÆ\u0003J\u0019\u0010\t\u001a\u00020\u00002\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003HÆ\u0001J\u0013\u0010\n\u001a\u00020\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\rHÖ\u0003J\t\u0010\u000e\u001a\u00020\u000fHÖ\u0001J\t\u0010\u0010\u001a\u00020\u0011HÖ\u0001R\u001c\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0013"}, d2 = {"Lcom/thor/networkmodule/model/responses/subscriptions/SubscriptionsResponse;", "Lcom/thor/networkmodule/model/responses/BaseResponse;", BillingClient.FeatureType.SUBSCRIPTIONS, "", "Lcom/thor/networkmodule/model/responses/subscriptions/SubscriptionsResponse$Subscription;", "(Ljava/util/List;)V", "getSubscriptions", "()Ljava/util/List;", "component1", "copy", "equals", "", "other", "", "hashCode", "", "toString", "", "Subscription", "networkmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final /* data */ class SubscriptionsResponse extends BaseResponse {

    @SerializedName(BillingClient.FeatureType.SUBSCRIPTIONS)
    private final List<Subscription> subscriptions;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ SubscriptionsResponse copy$default(SubscriptionsResponse subscriptionsResponse, List list, int i, Object obj) {
        if ((i & 1) != 0) {
            list = subscriptionsResponse.subscriptions;
        }
        return subscriptionsResponse.copy(list);
    }

    public final List<Subscription> component1() {
        return this.subscriptions;
    }

    public final SubscriptionsResponse copy(List<Subscription> subscriptions) {
        Intrinsics.checkNotNullParameter(subscriptions, "subscriptions");
        return new SubscriptionsResponse(subscriptions);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        return (other instanceof SubscriptionsResponse) && Intrinsics.areEqual(this.subscriptions, ((SubscriptionsResponse) other).subscriptions);
    }

    public int hashCode() {
        return this.subscriptions.hashCode();
    }

    @Override // com.thor.networkmodule.model.responses.BaseResponse
    public String toString() {
        return "SubscriptionsResponse(subscriptions=" + this.subscriptions + ")";
    }

    public final List<Subscription> getSubscriptions() {
        return this.subscriptions;
    }

    /* compiled from: SubscriptionsResponse.kt */
    @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005J\t\u0010\t\u001a\u00020\u0003HÆ\u0003J\t\u0010\n\u001a\u00020\u0003HÆ\u0003J\u001d\u0010\u000b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u000f\u001a\u00020\u0010HÖ\u0001J\t\u0010\u0011\u001a\u00020\u0003HÖ\u0001R\u0016\u0010\u0004\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0007¨\u0006\u0012"}, d2 = {"Lcom/thor/networkmodule/model/responses/subscriptions/SubscriptionsResponse$Subscription;", "", AppMeasurementSdk.ConditionalUserProperty.NAME, "", "iap", "(Ljava/lang/String;Ljava/lang/String;)V", "getIap", "()Ljava/lang/String;", "getName", "component1", "component2", "copy", "equals", "", "other", "hashCode", "", "toString", "networkmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final /* data */ class Subscription {

        @SerializedName("iap")
        private final String iap;

        @SerializedName(AppMeasurementSdk.ConditionalUserProperty.NAME)
        private final String name;

        public static /* synthetic */ Subscription copy$default(Subscription subscription, String str, String str2, int i, Object obj) {
            if ((i & 1) != 0) {
                str = subscription.name;
            }
            if ((i & 2) != 0) {
                str2 = subscription.iap;
            }
            return subscription.copy(str, str2);
        }

        /* renamed from: component1, reason: from getter */
        public final String getName() {
            return this.name;
        }

        /* renamed from: component2, reason: from getter */
        public final String getIap() {
            return this.iap;
        }

        public final Subscription copy(String name, String iap) {
            Intrinsics.checkNotNullParameter(name, "name");
            Intrinsics.checkNotNullParameter(iap, "iap");
            return new Subscription(name, iap);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof Subscription)) {
                return false;
            }
            Subscription subscription = (Subscription) other;
            return Intrinsics.areEqual(this.name, subscription.name) && Intrinsics.areEqual(this.iap, subscription.iap);
        }

        public int hashCode() {
            return (this.name.hashCode() * 31) + this.iap.hashCode();
        }

        public String toString() {
            return "Subscription(name=" + this.name + ", iap=" + this.iap + ")";
        }

        public Subscription(String name, String iap) {
            Intrinsics.checkNotNullParameter(name, "name");
            Intrinsics.checkNotNullParameter(iap, "iap");
            this.name = name;
            this.iap = iap;
        }

        public final String getName() {
            return this.name;
        }

        public final String getIap() {
            return this.iap;
        }
    }

    public SubscriptionsResponse(List<Subscription> subscriptions) {
        Intrinsics.checkNotNullParameter(subscriptions, "subscriptions");
        this.subscriptions = subscriptions;
    }
}
