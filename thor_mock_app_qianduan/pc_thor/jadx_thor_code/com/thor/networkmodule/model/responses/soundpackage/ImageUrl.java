package com.thor.networkmodule.model.responses.soundpackage;

import com.google.android.gms.common.internal.ImagesContract;
import com.google.gson.annotations.SerializedName;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ImageUrl.kt */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u0011\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0004J\u000b\u0010\b\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u0015\u0010\t\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003HÆ\u0001J\u0013\u0010\n\u001a\u00020\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\r\u001a\u00020\u000eHÖ\u0001J\t\u0010\u000f\u001a\u00020\u0003HÖ\u0001R \u0010\u0002\u001a\u0004\u0018\u00010\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\u0004¨\u0006\u0010"}, d2 = {"Lcom/thor/networkmodule/model/responses/soundpackage/ImageUrl;", "", ImagesContract.URL, "", "(Ljava/lang/String;)V", "getUrl", "()Ljava/lang/String;", "setUrl", "component1", "copy", "equals", "", "other", "hashCode", "", "toString", "networkmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final /* data */ class ImageUrl {

    @SerializedName(ImagesContract.URL)
    private String url;

    /* JADX WARN: Multi-variable type inference failed */
    public ImageUrl() {
        this(null, 1, 0 == true ? 1 : 0);
    }

    public static /* synthetic */ ImageUrl copy$default(ImageUrl imageUrl, String str, int i, Object obj) {
        if ((i & 1) != 0) {
            str = imageUrl.url;
        }
        return imageUrl.copy(str);
    }

    /* renamed from: component1, reason: from getter */
    public final String getUrl() {
        return this.url;
    }

    public final ImageUrl copy(String url) {
        return new ImageUrl(url);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        return (other instanceof ImageUrl) && Intrinsics.areEqual(this.url, ((ImageUrl) other).url);
    }

    public int hashCode() {
        String str = this.url;
        if (str == null) {
            return 0;
        }
        return str.hashCode();
    }

    public String toString() {
        return "ImageUrl(url=" + this.url + ")";
    }

    public ImageUrl(String str) {
        this.url = str;
    }

    public /* synthetic */ ImageUrl(String str, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : str);
    }

    public final String getUrl() {
        return this.url;
    }

    public final void setUrl(String str) {
        this.url = str;
    }
}
