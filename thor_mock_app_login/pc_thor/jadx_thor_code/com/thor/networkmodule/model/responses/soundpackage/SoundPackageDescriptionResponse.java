package com.thor.networkmodule.model.responses.soundpackage;

import com.google.android.exoplayer2.analytics.AnalyticsListener;
import com.google.android.exoplayer2.text.ttml.TtmlNode;
import com.google.android.gms.measurement.api.AppMeasurementSdk;
import com.google.gson.annotations.SerializedName;
import com.thor.networkmodule.model.responses.BaseResponse;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: SoundPackageDescriptionResponse.kt */
@Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b'\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0003\b\u0086\b\u0018\u00002\u00020\u0001B\u0091\u0001\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0005\u0012\u0010\b\u0002\u0010\t\u001a\n\u0012\u0004\u0012\u00020\u000b\u0018\u00010\n\u0012\b\b\u0002\u0010\f\u001a\u00020\u0003\u0012\u0010\b\u0002\u0010\r\u001a\n\u0012\u0004\u0012\u00020\u000e\u0018\u00010\n\u0012\u0010\b\u0002\u0010\u000f\u001a\n\u0012\u0004\u0012\u00020\u0010\u0018\u00010\n\u0012\u0010\b\u0002\u0010\u0011\u001a\n\u0012\u0004\u0012\u00020\u0012\u0018\u00010\n¢\u0006\u0002\u0010\u0013J\t\u0010.\u001a\u00020\u0003HÆ\u0003J\u0011\u0010/\u001a\n\u0012\u0004\u0012\u00020\u0012\u0018\u00010\nHÆ\u0003J\u000b\u00100\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\u000b\u00101\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\u000b\u00102\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\u000b\u00103\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\u0011\u00104\u001a\n\u0012\u0004\u0012\u00020\u000b\u0018\u00010\nHÆ\u0003J\t\u00105\u001a\u00020\u0003HÆ\u0003J\u0011\u00106\u001a\n\u0012\u0004\u0012\u00020\u000e\u0018\u00010\nHÆ\u0003J\u0011\u00107\u001a\n\u0012\u0004\u0012\u00020\u0010\u0018\u00010\nHÆ\u0003J\u0095\u0001\u00108\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u00052\u0010\b\u0002\u0010\t\u001a\n\u0012\u0004\u0012\u00020\u000b\u0018\u00010\n2\b\b\u0002\u0010\f\u001a\u00020\u00032\u0010\b\u0002\u0010\r\u001a\n\u0012\u0004\u0012\u00020\u000e\u0018\u00010\n2\u0010\b\u0002\u0010\u000f\u001a\n\u0012\u0004\u0012\u00020\u0010\u0018\u00010\n2\u0010\b\u0002\u0010\u0011\u001a\n\u0012\u0004\u0012\u00020\u0012\u0018\u00010\nHÆ\u0001J\u0013\u00109\u001a\u00020:2\b\u0010;\u001a\u0004\u0018\u00010<HÖ\u0003J\t\u0010=\u001a\u00020\u0003HÖ\u0001J\t\u0010>\u001a\u00020\u0005HÖ\u0001R \u0010\u0006\u001a\u0004\u0018\u00010\u00058\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017R&\u0010\u0011\u001a\n\u0012\u0004\u0012\u00020\u0012\u0018\u00010\n8\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u0019\"\u0004\b\u001a\u0010\u001bR\u001e\u0010\u0002\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\u001d\"\u0004\b\u001e\u0010\u001fR&\u0010\t\u001a\n\u0012\u0004\u0012\u00020\u000b\u0018\u00010\n8\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b \u0010\u0019\"\u0004\b!\u0010\u001bR \u0010\u0004\u001a\u0004\u0018\u00010\u00058\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\"\u0010\u0015\"\u0004\b#\u0010\u0017R \u0010\u0007\u001a\u0004\u0018\u00010\u00058\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b$\u0010\u0015\"\u0004\b%\u0010\u0017R&\u0010\r\u001a\n\u0012\u0004\u0012\u00020\u000e\u0018\u00010\n8\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b&\u0010\u0019\"\u0004\b'\u0010\u001bR&\u0010\u000f\u001a\n\u0012\u0004\u0012\u00020\u0010\u0018\u00010\n8\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b(\u0010\u0019\"\u0004\b)\u0010\u001bR\u001e\u0010\f\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b*\u0010\u001d\"\u0004\b+\u0010\u001fR \u0010\b\u001a\u0004\u0018\u00010\u00058\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b,\u0010\u0015\"\u0004\b-\u0010\u0017¨\u0006?"}, d2 = {"Lcom/thor/networkmodule/model/responses/soundpackage/SoundPackageDescriptionResponse;", "Lcom/thor/networkmodule/model/responses/BaseResponse;", TtmlNode.ATTR_ID, "", AppMeasurementSdk.ConditionalUserProperty.NAME, "", "description", "sampleUrl", "videoUrl", "images", "", "Lcom/thor/networkmodule/model/responses/soundpackage/ImageUrl;", "version", "samples", "Lcom/thor/networkmodule/model/responses/soundpackage/SoundPackageSample;", "soundRules", "Lcom/thor/networkmodule/model/responses/soundpackage/SoundPackageRule;", "files", "Lcom/thor/networkmodule/model/responses/soundpackage/SoundPackageFile;", "(ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/List;ILjava/util/List;Ljava/util/List;Ljava/util/List;)V", "getDescription", "()Ljava/lang/String;", "setDescription", "(Ljava/lang/String;)V", "getFiles", "()Ljava/util/List;", "setFiles", "(Ljava/util/List;)V", "getId", "()I", "setId", "(I)V", "getImages", "setImages", "getName", "setName", "getSampleUrl", "setSampleUrl", "getSamples", "setSamples", "getSoundRules", "setSoundRules", "getVersion", "setVersion", "getVideoUrl", "setVideoUrl", "component1", "component10", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "", "other", "", "hashCode", "toString", "networkmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final /* data */ class SoundPackageDescriptionResponse extends BaseResponse {

    @SerializedName("pkg_description")
    private String description;

    @SerializedName("files")
    private List<SoundPackageFile> files;

    @SerializedName("pkg_id")
    private int id;

    @SerializedName("pkg_images")
    private List<ImageUrl> images;

    @SerializedName("pkg_name")
    private String name;

    @SerializedName("pkg_sample_url")
    private String sampleUrl;

    @SerializedName("samples")
    private List<SoundPackageSample> samples;

    @SerializedName("sound_rules")
    private List<SoundPackageRule> soundRules;

    @SerializedName("pkg_ver")
    private int version;

    @SerializedName("pkg_video")
    private String videoUrl;

    public SoundPackageDescriptionResponse() {
        this(0, null, null, null, null, null, 0, null, null, null, AnalyticsListener.EVENT_DROPPED_VIDEO_FRAMES, null);
    }

    /* renamed from: component1, reason: from getter */
    public final int getId() {
        return this.id;
    }

    public final List<SoundPackageFile> component10() {
        return this.files;
    }

    /* renamed from: component2, reason: from getter */
    public final String getName() {
        return this.name;
    }

    /* renamed from: component3, reason: from getter */
    public final String getDescription() {
        return this.description;
    }

    /* renamed from: component4, reason: from getter */
    public final String getSampleUrl() {
        return this.sampleUrl;
    }

    /* renamed from: component5, reason: from getter */
    public final String getVideoUrl() {
        return this.videoUrl;
    }

    public final List<ImageUrl> component6() {
        return this.images;
    }

    /* renamed from: component7, reason: from getter */
    public final int getVersion() {
        return this.version;
    }

    public final List<SoundPackageSample> component8() {
        return this.samples;
    }

    public final List<SoundPackageRule> component9() {
        return this.soundRules;
    }

    public final SoundPackageDescriptionResponse copy(int id, String name, String description, String sampleUrl, String videoUrl, List<ImageUrl> images, int version, List<SoundPackageSample> samples, List<SoundPackageRule> soundRules, List<SoundPackageFile> files) {
        return new SoundPackageDescriptionResponse(id, name, description, sampleUrl, videoUrl, images, version, samples, soundRules, files);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof SoundPackageDescriptionResponse)) {
            return false;
        }
        SoundPackageDescriptionResponse soundPackageDescriptionResponse = (SoundPackageDescriptionResponse) other;
        return this.id == soundPackageDescriptionResponse.id && Intrinsics.areEqual(this.name, soundPackageDescriptionResponse.name) && Intrinsics.areEqual(this.description, soundPackageDescriptionResponse.description) && Intrinsics.areEqual(this.sampleUrl, soundPackageDescriptionResponse.sampleUrl) && Intrinsics.areEqual(this.videoUrl, soundPackageDescriptionResponse.videoUrl) && Intrinsics.areEqual(this.images, soundPackageDescriptionResponse.images) && this.version == soundPackageDescriptionResponse.version && Intrinsics.areEqual(this.samples, soundPackageDescriptionResponse.samples) && Intrinsics.areEqual(this.soundRules, soundPackageDescriptionResponse.soundRules) && Intrinsics.areEqual(this.files, soundPackageDescriptionResponse.files);
    }

    public int hashCode() {
        int iHashCode = Integer.hashCode(this.id) * 31;
        String str = this.name;
        int iHashCode2 = (iHashCode + (str == null ? 0 : str.hashCode())) * 31;
        String str2 = this.description;
        int iHashCode3 = (iHashCode2 + (str2 == null ? 0 : str2.hashCode())) * 31;
        String str3 = this.sampleUrl;
        int iHashCode4 = (iHashCode3 + (str3 == null ? 0 : str3.hashCode())) * 31;
        String str4 = this.videoUrl;
        int iHashCode5 = (iHashCode4 + (str4 == null ? 0 : str4.hashCode())) * 31;
        List<ImageUrl> list = this.images;
        int iHashCode6 = (((iHashCode5 + (list == null ? 0 : list.hashCode())) * 31) + Integer.hashCode(this.version)) * 31;
        List<SoundPackageSample> list2 = this.samples;
        int iHashCode7 = (iHashCode6 + (list2 == null ? 0 : list2.hashCode())) * 31;
        List<SoundPackageRule> list3 = this.soundRules;
        int iHashCode8 = (iHashCode7 + (list3 == null ? 0 : list3.hashCode())) * 31;
        List<SoundPackageFile> list4 = this.files;
        return iHashCode8 + (list4 != null ? list4.hashCode() : 0);
    }

    @Override // com.thor.networkmodule.model.responses.BaseResponse
    public String toString() {
        return "SoundPackageDescriptionResponse(id=" + this.id + ", name=" + this.name + ", description=" + this.description + ", sampleUrl=" + this.sampleUrl + ", videoUrl=" + this.videoUrl + ", images=" + this.images + ", version=" + this.version + ", samples=" + this.samples + ", soundRules=" + this.soundRules + ", files=" + this.files + ")";
    }

    public /* synthetic */ SoundPackageDescriptionResponse(int i, String str, String str2, String str3, String str4, List list, int i2, List list2, List list3, List list4, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this((i3 & 1) != 0 ? 0 : i, (i3 & 2) != 0 ? null : str, (i3 & 4) != 0 ? null : str2, (i3 & 8) != 0 ? null : str3, (i3 & 16) != 0 ? null : str4, (i3 & 32) != 0 ? null : list, (i3 & 64) == 0 ? i2 : 0, (i3 & 128) != 0 ? null : list2, (i3 & 256) != 0 ? null : list3, (i3 & 512) == 0 ? list4 : null);
    }

    public final int getId() {
        return this.id;
    }

    public final void setId(int i) {
        this.id = i;
    }

    public final String getName() {
        return this.name;
    }

    public final void setName(String str) {
        this.name = str;
    }

    public final String getDescription() {
        return this.description;
    }

    public final void setDescription(String str) {
        this.description = str;
    }

    public final String getSampleUrl() {
        return this.sampleUrl;
    }

    public final void setSampleUrl(String str) {
        this.sampleUrl = str;
    }

    public final String getVideoUrl() {
        return this.videoUrl;
    }

    public final void setVideoUrl(String str) {
        this.videoUrl = str;
    }

    public final List<ImageUrl> getImages() {
        return this.images;
    }

    public final void setImages(List<ImageUrl> list) {
        this.images = list;
    }

    public final int getVersion() {
        return this.version;
    }

    public final void setVersion(int i) {
        this.version = i;
    }

    public final List<SoundPackageSample> getSamples() {
        return this.samples;
    }

    public final void setSamples(List<SoundPackageSample> list) {
        this.samples = list;
    }

    public final List<SoundPackageRule> getSoundRules() {
        return this.soundRules;
    }

    public final void setSoundRules(List<SoundPackageRule> list) {
        this.soundRules = list;
    }

    public final List<SoundPackageFile> getFiles() {
        return this.files;
    }

    public final void setFiles(List<SoundPackageFile> list) {
        this.files = list;
    }

    public SoundPackageDescriptionResponse(int i, String str, String str2, String str3, String str4, List<ImageUrl> list, int i2, List<SoundPackageSample> list2, List<SoundPackageRule> list3, List<SoundPackageFile> list4) {
        this.id = i;
        this.name = str;
        this.description = str2;
        this.sampleUrl = str3;
        this.videoUrl = str4;
        this.images = list;
        this.version = i2;
        this.samples = list2;
        this.soundRules = list3;
        this.files = list4;
    }
}
