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
import kotlin.jvm.internal.Intrinsics;

/* compiled from: SguSoundJson.kt */
@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b \n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001BU\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\b\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\t\u001a\u0004\u0018\u00010\u0006\u0012\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b\u0012\u0006\u0010\r\u001a\u00020\u000e¢\u0006\u0002\u0010\u000fJ\t\u0010\"\u001a\u00020\u0003HÆ\u0003J\u0010\u0010#\u001a\u0004\u0018\u00010\u0003HÆ\u0003¢\u0006\u0002\u0010\u001dJ\u000b\u0010$\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\u000b\u0010%\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\u000b\u0010&\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\u000b\u0010'\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\u000f\u0010(\u001a\b\u0012\u0004\u0012\u00020\f0\u000bHÆ\u0003J\t\u0010)\u001a\u00020\u000eHÆ\u0003Jn\u0010*\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u00062\u000e\b\u0002\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b2\b\b\u0002\u0010\r\u001a\u00020\u000eHÆ\u0001¢\u0006\u0002\u0010+J\t\u0010,\u001a\u00020\u0003HÖ\u0001J\u0013\u0010-\u001a\u00020\u000e2\b\u0010.\u001a\u0004\u0018\u00010/HÖ\u0003J\t\u00100\u001a\u00020\u0003HÖ\u0001J\t\u00101\u001a\u00020\u0006HÖ\u0001J\u0019\u00102\u001a\u0002032\u0006\u00104\u001a\u0002052\u0006\u00106\u001a\u00020\u0003HÖ\u0001R\u0018\u0010\b\u001a\u0004\u0018\u00010\u00068\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u001e\u0010\r\u001a\u00020\u000e8\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015R\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0018\u0010\u0007\u001a\u0004\u0018\u00010\u00068\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0011R\u0018\u0010\u0005\u001a\u0004\u0018\u00010\u00068\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0011R\u001c\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b8\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001bR\"\u0010\u0004\u001a\u0004\u0018\u00010\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u0010\n\u0002\u0010 \u001a\u0004\b\u001c\u0010\u001d\"\u0004\b\u001e\u0010\u001fR\u0018\u0010\t\u001a\u0004\u0018\u00010\u00068\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b!\u0010\u0011¨\u00067"}, d2 = {"Lcom/thor/networkmodule/model/responses/sgu/SguSoundJson;", "Landroid/os/Parcelable;", TtmlNode.ATTR_ID, "", "sound_set_id", AppMeasurementSdk.ConditionalUserProperty.NAME, "", "image", "description", "version", "soundFiles", "", "Lcom/thor/networkmodule/model/responses/sgu/SguSoundFile;", "driveSelect", "", "(ILjava/lang/Integer;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/List;Z)V", "getDescription", "()Ljava/lang/String;", "getDriveSelect", "()Z", "setDriveSelect", "(Z)V", "getId", "()I", "getImage", "getName", "getSoundFiles", "()Ljava/util/List;", "getSound_set_id", "()Ljava/lang/Integer;", "setSound_set_id", "(Ljava/lang/Integer;)V", "Ljava/lang/Integer;", "getVersion", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "copy", "(ILjava/lang/Integer;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/List;Z)Lcom/thor/networkmodule/model/responses/sgu/SguSoundJson;", "describeContents", "equals", "other", "", "hashCode", "toString", "writeToParcel", "", "parcel", "Landroid/os/Parcel;", "flags", "networkmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final /* data */ class SguSoundJson implements Parcelable {
    public static final Parcelable.Creator<SguSoundJson> CREATOR = new Creator();

    @SerializedName("description")
    private final String description;

    @SerializedName("drive_select")
    private boolean driveSelect;

    @SerializedName(TtmlNode.ATTR_ID)
    private final int id;

    @SerializedName("image_url")
    private final String image;

    @SerializedName(AppMeasurementSdk.ConditionalUserProperty.NAME)
    private final String name;

    @SerializedName("sound_files")
    private final List<SguSoundFile> soundFiles;

    @SerializedName("sound_set_id")
    private Integer sound_set_id;

    @SerializedName("version")
    private final String version;

    /* compiled from: SguSoundJson.kt */
    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    public static final class Creator implements Parcelable.Creator<SguSoundJson> {
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final SguSoundJson createFromParcel(Parcel parcel) {
            Intrinsics.checkNotNullParameter(parcel, "parcel");
            int i = parcel.readInt();
            Integer numValueOf = parcel.readInt() == 0 ? null : Integer.valueOf(parcel.readInt());
            String string = parcel.readString();
            String string2 = parcel.readString();
            String string3 = parcel.readString();
            String string4 = parcel.readString();
            int i2 = parcel.readInt();
            ArrayList arrayList = new ArrayList(i2);
            for (int i3 = 0; i3 != i2; i3++) {
                arrayList.add(SguSoundFile.CREATOR.createFromParcel(parcel));
            }
            return new SguSoundJson(i, numValueOf, string, string2, string3, string4, arrayList, parcel.readInt() != 0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final SguSoundJson[] newArray(int i) {
            return new SguSoundJson[i];
        }
    }

    /* renamed from: component1, reason: from getter */
    public final int getId() {
        return this.id;
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
    public final String getVersion() {
        return this.version;
    }

    public final List<SguSoundFile> component7() {
        return this.soundFiles;
    }

    /* renamed from: component8, reason: from getter */
    public final boolean getDriveSelect() {
        return this.driveSelect;
    }

    public final SguSoundJson copy(int id, Integer sound_set_id, String name, String image, String description, String version, List<SguSoundFile> soundFiles, boolean driveSelect) {
        Intrinsics.checkNotNullParameter(soundFiles, "soundFiles");
        return new SguSoundJson(id, sound_set_id, name, image, description, version, soundFiles, driveSelect);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof SguSoundJson)) {
            return false;
        }
        SguSoundJson sguSoundJson = (SguSoundJson) other;
        return this.id == sguSoundJson.id && Intrinsics.areEqual(this.sound_set_id, sguSoundJson.sound_set_id) && Intrinsics.areEqual(this.name, sguSoundJson.name) && Intrinsics.areEqual(this.image, sguSoundJson.image) && Intrinsics.areEqual(this.description, sguSoundJson.description) && Intrinsics.areEqual(this.version, sguSoundJson.version) && Intrinsics.areEqual(this.soundFiles, sguSoundJson.soundFiles) && this.driveSelect == sguSoundJson.driveSelect;
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
        String str4 = this.version;
        int iHashCode6 = (((iHashCode5 + (str4 != null ? str4.hashCode() : 0)) * 31) + this.soundFiles.hashCode()) * 31;
        boolean z = this.driveSelect;
        int i = z;
        if (z != 0) {
            i = 1;
        }
        return iHashCode6 + i;
    }

    public String toString() {
        return "SguSoundJson(id=" + this.id + ", sound_set_id=" + this.sound_set_id + ", name=" + this.name + ", image=" + this.image + ", description=" + this.description + ", version=" + this.version + ", soundFiles=" + this.soundFiles + ", driveSelect=" + this.driveSelect + ")";
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
        parcel.writeString(this.version);
        List<SguSoundFile> list = this.soundFiles;
        parcel.writeInt(list.size());
        Iterator<SguSoundFile> it = list.iterator();
        while (it.hasNext()) {
            it.next().writeToParcel(parcel, flags);
        }
        parcel.writeInt(this.driveSelect ? 1 : 0);
    }

    public SguSoundJson(int i, Integer num, String str, String str2, String str3, String str4, List<SguSoundFile> soundFiles, boolean z) {
        Intrinsics.checkNotNullParameter(soundFiles, "soundFiles");
        this.id = i;
        this.sound_set_id = num;
        this.name = str;
        this.image = str2;
        this.description = str3;
        this.version = str4;
        this.soundFiles = soundFiles;
        this.driveSelect = z;
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

    public final String getVersion() {
        return this.version;
    }

    public final List<SguSoundFile> getSoundFiles() {
        return this.soundFiles;
    }

    public final boolean getDriveSelect() {
        return this.driveSelect;
    }

    public final void setDriveSelect(boolean z) {
        this.driveSelect = z;
    }
}
