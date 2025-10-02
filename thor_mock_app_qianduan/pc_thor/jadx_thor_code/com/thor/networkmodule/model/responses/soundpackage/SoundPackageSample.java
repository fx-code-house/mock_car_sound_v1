package com.thor.networkmodule.model.responses.soundpackage;

import com.google.android.exoplayer2.source.rtsp.SessionDescription;
import com.google.android.exoplayer2.text.ttml.TtmlNode;
import com.google.android.gms.common.internal.ImagesContract;
import com.google.android.gms.measurement.api.AppMeasurementSdk;
import com.google.gson.annotations.SerializedName;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: SoundPackageSample.kt */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0018\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0086\b\u0018\u00002\u00020\u0001BC\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0006\u0012\u0010\b\u0002\u0010\b\u001a\n\u0012\u0004\u0012\u00020\n\u0018\u00010\t¢\u0006\u0002\u0010\u000bJ\t\u0010\u001c\u001a\u00020\u0003HÆ\u0003J\t\u0010\u001d\u001a\u00020\u0003HÆ\u0003J\u000b\u0010\u001e\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\u000b\u0010\u001f\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\u0011\u0010 \u001a\n\u0012\u0004\u0012\u00020\n\u0018\u00010\tHÆ\u0003JG\u0010!\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u00062\u0010\b\u0002\u0010\b\u001a\n\u0012\u0004\u0012\u00020\n\u0018\u00010\tHÆ\u0001J\u0013\u0010\"\u001a\u00020#2\b\u0010$\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010%\u001a\u00020\u0003HÖ\u0001J\t\u0010&\u001a\u00020\u0006HÖ\u0001R\u001e\u0010\u0002\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR \u0010\u0005\u001a\u0004\u0018\u00010\u00068\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013R&\u0010\b\u001a\n\u0012\u0004\u0012\u00020\n\u0018\u00010\t8\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017R\u001e\u0010\u0004\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\r\"\u0004\b\u0019\u0010\u000fR \u0010\u0007\u001a\u0004\u0018\u00010\u00068\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010\u0011\"\u0004\b\u001b\u0010\u0013¨\u0006'"}, d2 = {"Lcom/thor/networkmodule/model/responses/soundpackage/SoundPackageSample;", "", TtmlNode.ATTR_ID, "", SessionDescription.ATTR_TYPE, AppMeasurementSdk.ConditionalUserProperty.NAME, "", ImagesContract.URL, "rules", "", "Lcom/thor/networkmodule/model/responses/soundpackage/SoundPackageSampleRule;", "(IILjava/lang/String;Ljava/lang/String;Ljava/util/List;)V", "getId", "()I", "setId", "(I)V", "getName", "()Ljava/lang/String;", "setName", "(Ljava/lang/String;)V", "getRules", "()Ljava/util/List;", "setRules", "(Ljava/util/List;)V", "getType", "setType", "getUrl", "setUrl", "component1", "component2", "component3", "component4", "component5", "copy", "equals", "", "other", "hashCode", "toString", "networkmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final /* data */ class SoundPackageSample {

    @SerializedName(TtmlNode.ATTR_ID)
    private int id;

    @SerializedName(AppMeasurementSdk.ConditionalUserProperty.NAME)
    private String name;

    @SerializedName("sample_rules")
    private List<SoundPackageSampleRule> rules;

    @SerializedName(SessionDescription.ATTR_TYPE)
    private int type;

    @SerializedName(ImagesContract.URL)
    private String url;

    public SoundPackageSample() {
        this(0, 0, null, null, null, 31, null);
    }

    public static /* synthetic */ SoundPackageSample copy$default(SoundPackageSample soundPackageSample, int i, int i2, String str, String str2, List list, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            i = soundPackageSample.id;
        }
        if ((i3 & 2) != 0) {
            i2 = soundPackageSample.type;
        }
        int i4 = i2;
        if ((i3 & 4) != 0) {
            str = soundPackageSample.name;
        }
        String str3 = str;
        if ((i3 & 8) != 0) {
            str2 = soundPackageSample.url;
        }
        String str4 = str2;
        if ((i3 & 16) != 0) {
            list = soundPackageSample.rules;
        }
        return soundPackageSample.copy(i, i4, str3, str4, list);
    }

    /* renamed from: component1, reason: from getter */
    public final int getId() {
        return this.id;
    }

    /* renamed from: component2, reason: from getter */
    public final int getType() {
        return this.type;
    }

    /* renamed from: component3, reason: from getter */
    public final String getName() {
        return this.name;
    }

    /* renamed from: component4, reason: from getter */
    public final String getUrl() {
        return this.url;
    }

    public final List<SoundPackageSampleRule> component5() {
        return this.rules;
    }

    public final SoundPackageSample copy(int id, int type, String name, String url, List<SoundPackageSampleRule> rules) {
        return new SoundPackageSample(id, type, name, url, rules);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof SoundPackageSample)) {
            return false;
        }
        SoundPackageSample soundPackageSample = (SoundPackageSample) other;
        return this.id == soundPackageSample.id && this.type == soundPackageSample.type && Intrinsics.areEqual(this.name, soundPackageSample.name) && Intrinsics.areEqual(this.url, soundPackageSample.url) && Intrinsics.areEqual(this.rules, soundPackageSample.rules);
    }

    public int hashCode() {
        int iHashCode = ((Integer.hashCode(this.id) * 31) + Integer.hashCode(this.type)) * 31;
        String str = this.name;
        int iHashCode2 = (iHashCode + (str == null ? 0 : str.hashCode())) * 31;
        String str2 = this.url;
        int iHashCode3 = (iHashCode2 + (str2 == null ? 0 : str2.hashCode())) * 31;
        List<SoundPackageSampleRule> list = this.rules;
        return iHashCode3 + (list != null ? list.hashCode() : 0);
    }

    public String toString() {
        return "SoundPackageSample(id=" + this.id + ", type=" + this.type + ", name=" + this.name + ", url=" + this.url + ", rules=" + this.rules + ")";
    }

    public SoundPackageSample(int i, int i2, String str, String str2, List<SoundPackageSampleRule> list) {
        this.id = i;
        this.type = i2;
        this.name = str;
        this.url = str2;
        this.rules = list;
    }

    public /* synthetic */ SoundPackageSample(int i, int i2, String str, String str2, List list, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this((i3 & 1) != 0 ? 0 : i, (i3 & 2) == 0 ? i2 : 0, (i3 & 4) != 0 ? null : str, (i3 & 8) != 0 ? null : str2, (i3 & 16) != 0 ? null : list);
    }

    public final int getId() {
        return this.id;
    }

    public final void setId(int i) {
        this.id = i;
    }

    public final int getType() {
        return this.type;
    }

    public final void setType(int i) {
        this.type = i;
    }

    public final String getName() {
        return this.name;
    }

    public final void setName(String str) {
        this.name = str;
    }

    public final String getUrl() {
        return this.url;
    }

    public final void setUrl(String str) {
        this.url = str;
    }

    public final List<SoundPackageSampleRule> getRules() {
        return this.rules;
    }

    public final void setRules(List<SoundPackageSampleRule> list) {
        this.rules = list;
    }
}
