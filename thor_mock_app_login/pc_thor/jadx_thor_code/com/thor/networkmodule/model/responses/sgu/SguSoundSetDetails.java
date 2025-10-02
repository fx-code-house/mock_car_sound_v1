package com.thor.networkmodule.model.responses.sgu;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: SguSoundSetDetails.kt */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0015\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0087\b\u0018\u0000 )2\u00020\u0001:\u0001)BC\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0006\u0012\u0006\u0010\b\u001a\u00020\t\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\u000b\u001a\u0004\u0018\u00010\u0006¢\u0006\u0002\u0010\fJ\u000f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003HÆ\u0003J\u000b\u0010\u0017\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\u000b\u0010\u0018\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\t\u0010\u0019\u001a\u00020\tHÆ\u0003J\u000b\u0010\u001a\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\u000b\u0010\u001b\u001a\u0004\u0018\u00010\u0006HÆ\u0003JS\u0010\u001c\u001a\u00020\u00002\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u00062\b\b\u0002\u0010\b\u001a\u00020\t2\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u0006HÆ\u0001J\t\u0010\u001d\u001a\u00020\tHÖ\u0001J\u0013\u0010\u001e\u001a\u00020\u001f2\b\u0010 \u001a\u0004\u0018\u00010!HÖ\u0003J\t\u0010\"\u001a\u00020\tHÖ\u0001J\t\u0010#\u001a\u00020\u0006HÖ\u0001J\u0019\u0010$\u001a\u00020%2\u0006\u0010&\u001a\u00020'2\u0006\u0010(\u001a\u00020\tHÖ\u0001R\u0018\u0010\u0005\u001a\u0004\u0018\u00010\u00068\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0018\u0010\u0007\u001a\u0004\u0018\u00010\u00068\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u000eR\u0016\u0010\b\u001a\u00020\t8\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0018\u0010\n\u001a\u0004\u0018\u00010\u00068\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u000eR\u0018\u0010\u000b\u001a\u0004\u0018\u00010\u00068\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u000eR\u001c\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015¨\u0006*"}, d2 = {"Lcom/thor/networkmodule/model/responses/sgu/SguSoundSetDetails;", "Landroid/os/Parcelable;", "sounds", "", "Lcom/thor/networkmodule/model/responses/sgu/SguSound;", "setCoverUrl", "", "setDescription", "setId", "", "setName", "setVideoUrl", "(Ljava/util/List;Ljava/lang/String;Ljava/lang/String;ILjava/lang/String;Ljava/lang/String;)V", "getSetCoverUrl", "()Ljava/lang/String;", "getSetDescription", "getSetId", "()I", "getSetName", "getSetVideoUrl", "getSounds", "()Ljava/util/List;", "component1", "component2", "component3", "component4", "component5", "component6", "copy", "describeContents", "equals", "", "other", "", "hashCode", "toString", "writeToParcel", "", "parcel", "Landroid/os/Parcel;", "flags", "Companion", "networkmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final /* data */ class SguSoundSetDetails implements Parcelable {
    public static final String BUNDLE_NAME = "SguSoundSetDetails";

    @SerializedName("set_cover_url")
    private final String setCoverUrl;

    @SerializedName("set_description")
    private final String setDescription;

    @SerializedName("set_id")
    private final int setId;

    @SerializedName("set_name")
    private final String setName;

    @SerializedName("set_video_url")
    private final String setVideoUrl;

    @SerializedName("files")
    private final List<SguSound> sounds;
    public static final Parcelable.Creator<SguSoundSetDetails> CREATOR = new Creator();

    /* compiled from: SguSoundSetDetails.kt */
    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    public static final class Creator implements Parcelable.Creator<SguSoundSetDetails> {
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final SguSoundSetDetails createFromParcel(Parcel parcel) {
            Intrinsics.checkNotNullParameter(parcel, "parcel");
            int i = parcel.readInt();
            ArrayList arrayList = new ArrayList(i);
            for (int i2 = 0; i2 != i; i2++) {
                arrayList.add(SguSound.CREATOR.createFromParcel(parcel));
            }
            return new SguSoundSetDetails(arrayList, parcel.readString(), parcel.readString(), parcel.readInt(), parcel.readString(), parcel.readString());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final SguSoundSetDetails[] newArray(int i) {
            return new SguSoundSetDetails[i];
        }
    }

    public static /* synthetic */ SguSoundSetDetails copy$default(SguSoundSetDetails sguSoundSetDetails, List list, String str, String str2, int i, String str3, String str4, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            list = sguSoundSetDetails.sounds;
        }
        if ((i2 & 2) != 0) {
            str = sguSoundSetDetails.setCoverUrl;
        }
        String str5 = str;
        if ((i2 & 4) != 0) {
            str2 = sguSoundSetDetails.setDescription;
        }
        String str6 = str2;
        if ((i2 & 8) != 0) {
            i = sguSoundSetDetails.setId;
        }
        int i3 = i;
        if ((i2 & 16) != 0) {
            str3 = sguSoundSetDetails.setName;
        }
        String str7 = str3;
        if ((i2 & 32) != 0) {
            str4 = sguSoundSetDetails.setVideoUrl;
        }
        return sguSoundSetDetails.copy(list, str5, str6, i3, str7, str4);
    }

    public final List<SguSound> component1() {
        return this.sounds;
    }

    /* renamed from: component2, reason: from getter */
    public final String getSetCoverUrl() {
        return this.setCoverUrl;
    }

    /* renamed from: component3, reason: from getter */
    public final String getSetDescription() {
        return this.setDescription;
    }

    /* renamed from: component4, reason: from getter */
    public final int getSetId() {
        return this.setId;
    }

    /* renamed from: component5, reason: from getter */
    public final String getSetName() {
        return this.setName;
    }

    /* renamed from: component6, reason: from getter */
    public final String getSetVideoUrl() {
        return this.setVideoUrl;
    }

    public final SguSoundSetDetails copy(List<SguSound> sounds, String setCoverUrl, String setDescription, int setId, String setName, String setVideoUrl) {
        Intrinsics.checkNotNullParameter(sounds, "sounds");
        return new SguSoundSetDetails(sounds, setCoverUrl, setDescription, setId, setName, setVideoUrl);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof SguSoundSetDetails)) {
            return false;
        }
        SguSoundSetDetails sguSoundSetDetails = (SguSoundSetDetails) other;
        return Intrinsics.areEqual(this.sounds, sguSoundSetDetails.sounds) && Intrinsics.areEqual(this.setCoverUrl, sguSoundSetDetails.setCoverUrl) && Intrinsics.areEqual(this.setDescription, sguSoundSetDetails.setDescription) && this.setId == sguSoundSetDetails.setId && Intrinsics.areEqual(this.setName, sguSoundSetDetails.setName) && Intrinsics.areEqual(this.setVideoUrl, sguSoundSetDetails.setVideoUrl);
    }

    public int hashCode() {
        int iHashCode = this.sounds.hashCode() * 31;
        String str = this.setCoverUrl;
        int iHashCode2 = (iHashCode + (str == null ? 0 : str.hashCode())) * 31;
        String str2 = this.setDescription;
        int iHashCode3 = (((iHashCode2 + (str2 == null ? 0 : str2.hashCode())) * 31) + Integer.hashCode(this.setId)) * 31;
        String str3 = this.setName;
        int iHashCode4 = (iHashCode3 + (str3 == null ? 0 : str3.hashCode())) * 31;
        String str4 = this.setVideoUrl;
        return iHashCode4 + (str4 != null ? str4.hashCode() : 0);
    }

    public String toString() {
        return "SguSoundSetDetails(sounds=" + this.sounds + ", setCoverUrl=" + this.setCoverUrl + ", setDescription=" + this.setDescription + ", setId=" + this.setId + ", setName=" + this.setName + ", setVideoUrl=" + this.setVideoUrl + ")";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int flags) {
        Intrinsics.checkNotNullParameter(parcel, "out");
        List<SguSound> list = this.sounds;
        parcel.writeInt(list.size());
        Iterator<SguSound> it = list.iterator();
        while (it.hasNext()) {
            it.next().writeToParcel(parcel, flags);
        }
        parcel.writeString(this.setCoverUrl);
        parcel.writeString(this.setDescription);
        parcel.writeInt(this.setId);
        parcel.writeString(this.setName);
        parcel.writeString(this.setVideoUrl);
    }

    public SguSoundSetDetails(List<SguSound> sounds, String str, String str2, int i, String str3, String str4) {
        Intrinsics.checkNotNullParameter(sounds, "sounds");
        this.sounds = sounds;
        this.setCoverUrl = str;
        this.setDescription = str2;
        this.setId = i;
        this.setName = str3;
        this.setVideoUrl = str4;
    }

    public final List<SguSound> getSounds() {
        return this.sounds;
    }

    public final String getSetCoverUrl() {
        return this.setCoverUrl;
    }

    public final String getSetDescription() {
        return this.setDescription;
    }

    public final int getSetId() {
        return this.setId;
    }

    public final String getSetName() {
        return this.setName;
    }

    public final String getSetVideoUrl() {
        return this.setVideoUrl;
    }
}
