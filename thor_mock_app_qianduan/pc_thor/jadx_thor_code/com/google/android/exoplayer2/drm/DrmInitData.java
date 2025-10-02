package com.google.android.exoplayer2.drm;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

/* loaded from: classes.dex */
public final class DrmInitData implements Comparator<SchemeData>, Parcelable {
    public static final Parcelable.Creator<DrmInitData> CREATOR = new Parcelable.Creator<DrmInitData>() { // from class: com.google.android.exoplayer2.drm.DrmInitData.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DrmInitData createFromParcel(Parcel in) {
            return new DrmInitData(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DrmInitData[] newArray(int size) {
            return new DrmInitData[size];
        }
    };
    private int hashCode;
    public final int schemeDataCount;
    private final SchemeData[] schemeDatas;
    public final String schemeType;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static DrmInitData createSessionCreationData(DrmInitData manifestData, DrmInitData mediaData) {
        String str;
        ArrayList arrayList = new ArrayList();
        if (manifestData != null) {
            str = manifestData.schemeType;
            for (SchemeData schemeData : manifestData.schemeDatas) {
                if (schemeData.hasData()) {
                    arrayList.add(schemeData);
                }
            }
        } else {
            str = null;
        }
        if (mediaData != null) {
            if (str == null) {
                str = mediaData.schemeType;
            }
            int size = arrayList.size();
            for (SchemeData schemeData2 : mediaData.schemeDatas) {
                if (schemeData2.hasData() && !containsSchemeDataWithUuid(arrayList, size, schemeData2.uuid)) {
                    arrayList.add(schemeData2);
                }
            }
        }
        if (arrayList.isEmpty()) {
            return null;
        }
        return new DrmInitData(str, arrayList);
    }

    public DrmInitData(List<SchemeData> schemeDatas) {
        this(null, false, (SchemeData[]) schemeDatas.toArray(new SchemeData[0]));
    }

    public DrmInitData(String schemeType, List<SchemeData> schemeDatas) {
        this(schemeType, false, (SchemeData[]) schemeDatas.toArray(new SchemeData[0]));
    }

    public DrmInitData(SchemeData... schemeDatas) {
        this((String) null, schemeDatas);
    }

    public DrmInitData(String schemeType, SchemeData... schemeDatas) {
        this(schemeType, true, schemeDatas);
    }

    private DrmInitData(String schemeType, boolean cloneSchemeDatas, SchemeData... schemeDatas) {
        this.schemeType = schemeType;
        schemeDatas = cloneSchemeDatas ? (SchemeData[]) schemeDatas.clone() : schemeDatas;
        this.schemeDatas = schemeDatas;
        this.schemeDataCount = schemeDatas.length;
        Arrays.sort(schemeDatas, this);
    }

    DrmInitData(Parcel in) {
        this.schemeType = in.readString();
        SchemeData[] schemeDataArr = (SchemeData[]) Util.castNonNull((SchemeData[]) in.createTypedArray(SchemeData.CREATOR));
        this.schemeDatas = schemeDataArr;
        this.schemeDataCount = schemeDataArr.length;
    }

    public SchemeData get(int index) {
        return this.schemeDatas[index];
    }

    public DrmInitData copyWithSchemeType(String schemeType) {
        return Util.areEqual(this.schemeType, schemeType) ? this : new DrmInitData(schemeType, false, this.schemeDatas);
    }

    public DrmInitData merge(DrmInitData drmInitData) {
        String str;
        String str2 = this.schemeType;
        Assertions.checkState(str2 == null || (str = drmInitData.schemeType) == null || TextUtils.equals(str2, str));
        String str3 = this.schemeType;
        if (str3 == null) {
            str3 = drmInitData.schemeType;
        }
        return new DrmInitData(str3, (SchemeData[]) Util.nullSafeArrayConcatenation(this.schemeDatas, drmInitData.schemeDatas));
    }

    public int hashCode() {
        if (this.hashCode == 0) {
            String str = this.schemeType;
            this.hashCode = ((str == null ? 0 : str.hashCode()) * 31) + Arrays.hashCode(this.schemeDatas);
        }
        return this.hashCode;
    }

    @Override // java.util.Comparator
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        DrmInitData drmInitData = (DrmInitData) obj;
        return Util.areEqual(this.schemeType, drmInitData.schemeType) && Arrays.equals(this.schemeDatas, drmInitData.schemeDatas);
    }

    @Override // java.util.Comparator
    public int compare(SchemeData first, SchemeData second) {
        if (C.UUID_NIL.equals(first.uuid)) {
            return C.UUID_NIL.equals(second.uuid) ? 0 : 1;
        }
        return first.uuid.compareTo(second.uuid);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.schemeType);
        dest.writeTypedArray(this.schemeDatas, 0);
    }

    private static boolean containsSchemeDataWithUuid(ArrayList<SchemeData> datas, int limit, UUID uuid) {
        for (int i = 0; i < limit; i++) {
            if (datas.get(i).uuid.equals(uuid)) {
                return true;
            }
        }
        return false;
    }

    public static final class SchemeData implements Parcelable {
        public static final Parcelable.Creator<SchemeData> CREATOR = new Parcelable.Creator<SchemeData>() { // from class: com.google.android.exoplayer2.drm.DrmInitData.SchemeData.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public SchemeData createFromParcel(Parcel in) {
                return new SchemeData(in);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public SchemeData[] newArray(int size) {
                return new SchemeData[size];
            }
        };
        public final byte[] data;
        private int hashCode;
        public final String licenseServerUrl;
        public final String mimeType;
        public final UUID uuid;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public SchemeData(UUID uuid, String mimeType, byte[] data) {
            this(uuid, null, mimeType, data);
        }

        public SchemeData(UUID uuid, String licenseServerUrl, String mimeType, byte[] data) {
            this.uuid = (UUID) Assertions.checkNotNull(uuid);
            this.licenseServerUrl = licenseServerUrl;
            this.mimeType = (String) Assertions.checkNotNull(mimeType);
            this.data = data;
        }

        SchemeData(Parcel in) {
            this.uuid = new UUID(in.readLong(), in.readLong());
            this.licenseServerUrl = in.readString();
            this.mimeType = (String) Util.castNonNull(in.readString());
            this.data = in.createByteArray();
        }

        public boolean matches(UUID schemeUuid) {
            return C.UUID_NIL.equals(this.uuid) || schemeUuid.equals(this.uuid);
        }

        public boolean canReplace(SchemeData other) {
            return hasData() && !other.hasData() && matches(other.uuid);
        }

        public boolean hasData() {
            return this.data != null;
        }

        public SchemeData copyWithData(byte[] data) {
            return new SchemeData(this.uuid, this.licenseServerUrl, this.mimeType, data);
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof SchemeData)) {
                return false;
            }
            if (obj == this) {
                return true;
            }
            SchemeData schemeData = (SchemeData) obj;
            return Util.areEqual(this.licenseServerUrl, schemeData.licenseServerUrl) && Util.areEqual(this.mimeType, schemeData.mimeType) && Util.areEqual(this.uuid, schemeData.uuid) && Arrays.equals(this.data, schemeData.data);
        }

        public int hashCode() {
            if (this.hashCode == 0) {
                int iHashCode = this.uuid.hashCode() * 31;
                String str = this.licenseServerUrl;
                this.hashCode = ((((iHashCode + (str == null ? 0 : str.hashCode())) * 31) + this.mimeType.hashCode()) * 31) + Arrays.hashCode(this.data);
            }
            return this.hashCode;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeLong(this.uuid.getMostSignificantBits());
            dest.writeLong(this.uuid.getLeastSignificantBits());
            dest.writeString(this.licenseServerUrl);
            dest.writeString(this.mimeType);
            dest.writeByteArray(this.data);
        }
    }
}
