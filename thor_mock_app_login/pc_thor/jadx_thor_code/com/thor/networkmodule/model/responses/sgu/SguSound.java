package com.thor.networkmodule.model.responses.sgu;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.exoplayer2.text.ttml.TtmlNode;
import com.google.android.gms.measurement.api.AppMeasurementSdk;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: SguSound.kt */
@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b+\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001Bu\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\b\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\t\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u0006\u0012\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\f\u0012\b\b\u0002\u0010\u000e\u001a\u00020\u000f\u0012\b\u0010\u0010\u001a\u0004\u0018\u00010\u0006\u0012\b\b\u0002\u0010\u0011\u001a\u00020\u000f¢\u0006\u0002\u0010\u0012J\t\u0010+\u001a\u00020\u0003HÆ\u0003J\u000b\u0010,\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\t\u0010-\u001a\u00020\u000fHÆ\u0003J\u0010\u0010.\u001a\u0004\u0018\u00010\u0003HÆ\u0003¢\u0006\u0002\u0010&J\u000b\u0010/\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\u000b\u00100\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\u000b\u00101\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\u000b\u00102\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\u000b\u00103\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\u000f\u00104\u001a\b\u0012\u0004\u0012\u00020\r0\fHÆ\u0003J\t\u00105\u001a\u00020\u000fHÆ\u0003J\u0090\u0001\u00106\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u00062\u000e\b\u0002\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\f2\b\b\u0002\u0010\u000e\u001a\u00020\u000f2\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u00062\b\b\u0002\u0010\u0011\u001a\u00020\u000fHÆ\u0001¢\u0006\u0002\u00107J\t\u00108\u001a\u00020\u0003HÖ\u0001J\u0013\u00109\u001a\u00020\u000f2\b\u0010:\u001a\u0004\u0018\u00010;HÖ\u0003J\t\u0010<\u001a\u00020\u0003HÖ\u0001J\t\u0010=\u001a\u00020\u0006HÖ\u0001J\u0019\u0010>\u001a\u00020?2\u0006\u0010@\u001a\u00020A2\u0006\u0010B\u001a\u00020\u0003HÖ\u0001R\u0018\u0010\b\u001a\u0004\u0018\u00010\u00068\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0016\u0010\u000e\u001a\u00020\u000f8\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0018\u0010\u0007\u001a\u0004\u0018\u00010\u00068\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0014R\u001a\u0010\u0011\u001a\u00020\u000fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0016\"\u0004\b\u001a\u0010\u001bR \u0010\u001c\u001a\u00020\u000fX\u0086\u000e¢\u0006\u0014\n\u0000\u0012\u0004\b\u001d\u0010\u001e\u001a\u0004\b\u001c\u0010\u0016\"\u0004\b\u001f\u0010\u001bR\u0018\u0010\u0005\u001a\u0004\u0018\u00010\u00068\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b \u0010\u0014R\u0018\u0010\u0010\u001a\u0004\u0018\u00010\u00068\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b!\u0010\u0014R\u0018\u0010\t\u001a\u0004\u0018\u00010\u00068\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\"\u0010\u0014R\u001c\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\f8\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b#\u0010$R\"\u0010\u0004\u001a\u0004\u0018\u00010\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u0010\n\u0002\u0010)\u001a\u0004\b%\u0010&\"\u0004\b'\u0010(R\u0018\u0010\n\u001a\u0004\u0018\u00010\u00068\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b*\u0010\u0014¨\u0006C"}, d2 = {"Lcom/thor/networkmodule/model/responses/sgu/SguSound;", "Landroid/os/Parcelable;", TtmlNode.ATTR_ID, "", "sound_set_id", AppMeasurementSdk.ConditionalUserProperty.NAME, "", "image", "description", "sampleUrl", "version", "soundFiles", "", "Lcom/thor/networkmodule/model/responses/sgu/SguSoundFile;", "driveSelect", "", "preview", "isFavourite", "(ILjava/lang/Integer;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/List;ZLjava/lang/String;Z)V", "getDescription", "()Ljava/lang/String;", "getDriveSelect", "()Z", "getId", "()I", "getImage", "setFavourite", "(Z)V", "isPlaying", "isPlaying$annotations", "()V", "setPlaying", "getName", "getPreview", "getSampleUrl", "getSoundFiles", "()Ljava/util/List;", "getSound_set_id", "()Ljava/lang/Integer;", "setSound_set_id", "(Ljava/lang/Integer;)V", "Ljava/lang/Integer;", "getVersion", "component1", "component10", "component11", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "(ILjava/lang/Integer;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/List;ZLjava/lang/String;Z)Lcom/thor/networkmodule/model/responses/sgu/SguSound;", "describeContents", "equals", "other", "", "hashCode", "toString", "writeToParcel", "", "parcel", "Landroid/os/Parcel;", "flags", "networkmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final /* data */ class SguSound implements Parcelable {
    public static final Parcelable.Creator<SguSound> CREATOR = new Creator();

    @SerializedName("subcategory_description")
    private final String description;

    @SerializedName("drive_select")
    private final boolean driveSelect;

    @SerializedName("subcategory_id")
    private final int id;

    @SerializedName("subcategory_image")
    private final String image;
    private boolean isFavourite;
    private boolean isPlaying;

    @SerializedName("subcategory_name")
    private final String name;

    @SerializedName("subcategory_preview")
    private final String preview;

    @SerializedName("subcategory_sample_waw")
    private final String sampleUrl;

    @SerializedName("sgu_sounds")
    private final List<SguSoundFile> soundFiles;

    @SerializedName("category_id")
    private Integer sound_set_id;

    @SerializedName("subcategory_version")
    private final String version;

    /* compiled from: SguSound.kt */
    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    public static final class Creator implements Parcelable.Creator<SguSound> {
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final SguSound createFromParcel(Parcel parcel) {
            Intrinsics.checkNotNullParameter(parcel, "parcel");
            int i = parcel.readInt();
            Integer numValueOf = parcel.readInt() == 0 ? null : Integer.valueOf(parcel.readInt());
            String string = parcel.readString();
            String string2 = parcel.readString();
            String string3 = parcel.readString();
            String string4 = parcel.readString();
            String string5 = parcel.readString();
            int i2 = parcel.readInt();
            ArrayList arrayList = new ArrayList(i2);
            for (int i3 = 0; i3 != i2; i3++) {
                arrayList.add(SguSoundFile.CREATOR.createFromParcel(parcel));
            }
            return new SguSound(i, numValueOf, string, string2, string3, string4, string5, arrayList, parcel.readInt() != 0, parcel.readString(), parcel.readInt() != 0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final SguSound[] newArray(int i) {
            return new SguSound[i];
        }
    }

    public static /* synthetic */ void isPlaying$annotations() {
    }

    /* renamed from: component1, reason: from getter */
    public final int getId() {
        return this.id;
    }

    /* renamed from: component10, reason: from getter */
    public final String getPreview() {
        return this.preview;
    }

    /* renamed from: component11, reason: from getter */
    public final boolean getIsFavourite() {
        return this.isFavourite;
    }

    /* renamed from: component2, reason: from getter */
    public final Integer getSound_set_id() {
        return this.sound_set_id;
    }

    /* renamed from: component3, reason: from getter */
    public final String getName() {
        return this.name;
    }

    /* renamed from: component4, reason: from getter */
    public final String getImage() {
        return this.image;
    }

    /* renamed from: component5, reason: from getter */
    public final String getDescription() {
        return this.description;
    }

    /* renamed from: component6, reason: from getter */
    public final String getSampleUrl() {
        return this.sampleUrl;
    }

    /* renamed from: component7, reason: from getter */
    public final String getVersion() {
        return this.version;
    }

    public final List<SguSoundFile> component8() {
        return this.soundFiles;
    }

    /* renamed from: component9, reason: from getter */
    public final boolean getDriveSelect() {
        return this.driveSelect;
    }

    public final SguSound copy(int id, Integer sound_set_id, String name, String image, String description, String sampleUrl, String version, List<SguSoundFile> soundFiles, boolean driveSelect, String preview, boolean isFavourite) {
        Intrinsics.checkNotNullParameter(soundFiles, "soundFiles");
        return new SguSound(id, sound_set_id, name, image, description, sampleUrl, version, soundFiles, driveSelect, preview, isFavourite);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof SguSound)) {
            return false;
        }
        SguSound sguSound = (SguSound) other;
        return this.id == sguSound.id && Intrinsics.areEqual(this.sound_set_id, sguSound.sound_set_id) && Intrinsics.areEqual(this.name, sguSound.name) && Intrinsics.areEqual(this.image, sguSound.image) && Intrinsics.areEqual(this.description, sguSound.description) && Intrinsics.areEqual(this.sampleUrl, sguSound.sampleUrl) && Intrinsics.areEqual(this.version, sguSound.version) && Intrinsics.areEqual(this.soundFiles, sguSound.soundFiles) && this.driveSelect == sguSound.driveSelect && Intrinsics.areEqual(this.preview, sguSound.preview) && this.isFavourite == sguSound.isFavourite;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int hashCode() {
        int iHashCode = Integer.hashCode(this.id) * 31;
        Integer num = this.sound_set_id;
        int iHashCode2 = (iHashCode + (num == null ? 0 : num.hashCode())) * 31;
        String str = this.name;
        int iHashCode3 = (iHashCode2 + (str == null ? 0 : str.hashCode())) * 31;
        String str2 = this.image;
        int iHashCode4 = (iHashCode3 + (str2 == null ? 0 : str2.hashCode())) * 31;
        String str3 = this.description;
        int iHashCode5 = (iHashCode4 + (str3 == null ? 0 : str3.hashCode())) * 31;
        String str4 = this.sampleUrl;
        int iHashCode6 = (iHashCode5 + (str4 == null ? 0 : str4.hashCode())) * 31;
        String str5 = this.version;
        int iHashCode7 = (((iHashCode6 + (str5 == null ? 0 : str5.hashCode())) * 31) + this.soundFiles.hashCode()) * 31;
        boolean z = this.driveSelect;
        int i = z;
        if (z != 0) {
            i = 1;
        }
        int i2 = (iHashCode7 + i) * 31;
        String str6 = this.preview;
        int iHashCode8 = (i2 + (str6 != null ? str6.hashCode() : 0)) * 31;
        boolean z2 = this.isFavourite;
        return iHashCode8 + (z2 ? 1 : z2 ? 1 : 0);
    }

    public String toString() {
        return "SguSound(id=" + this.id + ", sound_set_id=" + this.sound_set_id + ", name=" + this.name + ", image=" + this.image + ", description=" + this.description + ", sampleUrl=" + this.sampleUrl + ", version=" + this.version + ", soundFiles=" + this.soundFiles + ", driveSelect=" + this.driveSelect + ", preview=" + this.preview + ", isFavourite=" + this.isFavourite + ")";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int flags) {
        int iIntValue;
        Intrinsics.checkNotNullParameter(parcel, "out");
        parcel.writeInt(this.id);
        Integer num = this.sound_set_id;
        if (num == null) {
            iIntValue = 0;
        } else {
            parcel.writeInt(1);
            iIntValue = num.intValue();
        }
        parcel.writeInt(iIntValue);
        parcel.writeString(this.name);
        parcel.writeString(this.image);
        parcel.writeString(this.description);
        parcel.writeString(this.sampleUrl);
        parcel.writeString(this.version);
        List<SguSoundFile> list = this.soundFiles;
        parcel.writeInt(list.size());
        Iterator<SguSoundFile> it = list.iterator();
        while (it.hasNext()) {
            it.next().writeToParcel(parcel, flags);
        }
        parcel.writeInt(this.driveSelect ? 1 : 0);
        parcel.writeString(this.preview);
        parcel.writeInt(this.isFavourite ? 1 : 0);
    }

    public SguSound(int i, Integer num, String str, String str2, String str3, String str4, String str5, List<SguSoundFile> soundFiles, boolean z, String str6, boolean z2) {
        Intrinsics.checkNotNullParameter(soundFiles, "soundFiles");
        this.id = i;
        this.sound_set_id = num;
        this.name = str;
        this.image = str2;
        this.description = str3;
        this.sampleUrl = str4;
        this.version = str5;
        this.soundFiles = soundFiles;
        this.driveSelect = z;
        this.preview = str6;
        this.isFavourite = z2;
    }

    public /* synthetic */ SguSound(int i, Integer num, String str, String str2, String str3, String str4, String str5, List list, boolean z, String str6, boolean z2, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, num, str, str2, str3, str4, str5, list, (i2 & 256) != 0 ? false : z, str6, (i2 & 1024) != 0 ? false : z2);
    }

    public final int getId() {
        return this.id;
    }

    public final Integer getSound_set_id() {
        return this.sound_set_id;
    }

    public final void setSound_set_id(Integer num) {
        this.sound_set_id = num;
    }

    public final String getName() {
        return this.name;
    }

    public final String getImage() {
        return this.image;
    }

    public final String getDescription() {
        return this.description;
    }

    public final String getSampleUrl() {
        return this.sampleUrl;
    }

    public final String getVersion() {
        return this.version;
    }

    public final List<SguSoundFile> getSoundFiles() {
        return this.soundFiles;
    }

    public final boolean getDriveSelect() {
        return this.driveSelect;
    }

    public final String getPreview() {
        return this.preview;
    }

    public final boolean isFavourite() {
        return this.isFavourite;
    }

    public final void setFavourite(boolean z) {
        this.isFavourite = z;
    }

    /* renamed from: isPlaying, reason: from getter */
    public final boolean getIsPlaying() {
        return this.isPlaying;
    }

    public final void setPlaying(boolean z) {
        this.isPlaying = z;
    }
}
