package com.google.android.exoplayer2.offline;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import com.google.common.collect.ImmutableList;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/* loaded from: classes.dex */
public final class DownloadRequest implements Parcelable {
    public static final Parcelable.Creator<DownloadRequest> CREATOR = new Parcelable.Creator<DownloadRequest>() { // from class: com.google.android.exoplayer2.offline.DownloadRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DownloadRequest createFromParcel(Parcel in) {
            return new DownloadRequest(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DownloadRequest[] newArray(int size) {
            return new DownloadRequest[size];
        }
    };
    public final String customCacheKey;
    public final byte[] data;
    public final String id;
    public final byte[] keySetId;
    public final String mimeType;
    public final List<StreamKey> streamKeys;
    public final Uri uri;

    public static class UnsupportedRequestException extends IOException {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static class Builder {
        private String customCacheKey;
        private byte[] data;
        private final String id;
        private byte[] keySetId;
        private String mimeType;
        private List<StreamKey> streamKeys;
        private final Uri uri;

        public Builder(String id, Uri uri) {
            this.id = id;
            this.uri = uri;
        }

        public Builder setMimeType(String mimeType) {
            this.mimeType = mimeType;
            return this;
        }

        public Builder setStreamKeys(List<StreamKey> streamKeys) {
            this.streamKeys = streamKeys;
            return this;
        }

        public Builder setKeySetId(byte[] keySetId) {
            this.keySetId = keySetId;
            return this;
        }

        public Builder setCustomCacheKey(String customCacheKey) {
            this.customCacheKey = customCacheKey;
            return this;
        }

        public Builder setData(byte[] data) {
            this.data = data;
            return this;
        }

        public DownloadRequest build() {
            String str = this.id;
            Uri uri = this.uri;
            String str2 = this.mimeType;
            List listOf = this.streamKeys;
            if (listOf == null) {
                listOf = ImmutableList.of();
            }
            return new DownloadRequest(str, uri, str2, listOf, this.keySetId, this.customCacheKey, this.data);
        }
    }

    private DownloadRequest(String id, Uri uri, String mimeType, List<StreamKey> streamKeys, byte[] keySetId, String customCacheKey, byte[] data) {
        int iInferContentTypeForUriAndMimeType = Util.inferContentTypeForUriAndMimeType(uri, mimeType);
        if (iInferContentTypeForUriAndMimeType == 0 || iInferContentTypeForUriAndMimeType == 2 || iInferContentTypeForUriAndMimeType == 1) {
            Assertions.checkArgument(customCacheKey == null, new StringBuilder(49).append("customCacheKey must be null for type: ").append(iInferContentTypeForUriAndMimeType).toString());
        }
        this.id = id;
        this.uri = uri;
        this.mimeType = mimeType;
        ArrayList arrayList = new ArrayList(streamKeys);
        Collections.sort(arrayList);
        this.streamKeys = Collections.unmodifiableList(arrayList);
        this.keySetId = keySetId != null ? Arrays.copyOf(keySetId, keySetId.length) : null;
        this.customCacheKey = customCacheKey;
        this.data = data != null ? Arrays.copyOf(data, data.length) : Util.EMPTY_BYTE_ARRAY;
    }

    DownloadRequest(Parcel in) {
        this.id = (String) Util.castNonNull(in.readString());
        this.uri = Uri.parse((String) Util.castNonNull(in.readString()));
        this.mimeType = in.readString();
        int i = in.readInt();
        ArrayList arrayList = new ArrayList(i);
        for (int i2 = 0; i2 < i; i2++) {
            arrayList.add((StreamKey) in.readParcelable(StreamKey.class.getClassLoader()));
        }
        this.streamKeys = Collections.unmodifiableList(arrayList);
        this.keySetId = in.createByteArray();
        this.customCacheKey = in.readString();
        this.data = (byte[]) Util.castNonNull(in.createByteArray());
    }

    public DownloadRequest copyWithId(String id) {
        return new DownloadRequest(id, this.uri, this.mimeType, this.streamKeys, this.keySetId, this.customCacheKey, this.data);
    }

    public DownloadRequest copyWithKeySetId(byte[] keySetId) {
        return new DownloadRequest(this.id, this.uri, this.mimeType, this.streamKeys, keySetId, this.customCacheKey, this.data);
    }

    public DownloadRequest copyWithMergedRequest(DownloadRequest newRequest) {
        List listEmptyList;
        Assertions.checkArgument(this.id.equals(newRequest.id));
        if (this.streamKeys.isEmpty() || newRequest.streamKeys.isEmpty()) {
            listEmptyList = Collections.emptyList();
        } else {
            listEmptyList = new ArrayList(this.streamKeys);
            for (int i = 0; i < newRequest.streamKeys.size(); i++) {
                StreamKey streamKey = newRequest.streamKeys.get(i);
                if (!listEmptyList.contains(streamKey)) {
                    listEmptyList.add(streamKey);
                }
            }
        }
        return new DownloadRequest(this.id, newRequest.uri, newRequest.mimeType, listEmptyList, newRequest.keySetId, newRequest.customCacheKey, newRequest.data);
    }

    public MediaItem toMediaItem() {
        return new MediaItem.Builder().setMediaId(this.id).setUri(this.uri).setCustomCacheKey(this.customCacheKey).setMimeType(this.mimeType).setStreamKeys(this.streamKeys).setDrmKeySetId(this.keySetId).build();
    }

    public String toString() {
        String str = this.mimeType;
        String str2 = this.id;
        return new StringBuilder(String.valueOf(str).length() + 1 + String.valueOf(str2).length()).append(str).append(":").append(str2).toString();
    }

    public boolean equals(Object o) {
        if (!(o instanceof DownloadRequest)) {
            return false;
        }
        DownloadRequest downloadRequest = (DownloadRequest) o;
        return this.id.equals(downloadRequest.id) && this.uri.equals(downloadRequest.uri) && Util.areEqual(this.mimeType, downloadRequest.mimeType) && this.streamKeys.equals(downloadRequest.streamKeys) && Arrays.equals(this.keySetId, downloadRequest.keySetId) && Util.areEqual(this.customCacheKey, downloadRequest.customCacheKey) && Arrays.equals(this.data, downloadRequest.data);
    }

    public final int hashCode() {
        int iHashCode = ((this.id.hashCode() * 31 * 31) + this.uri.hashCode()) * 31;
        String str = this.mimeType;
        int iHashCode2 = (((((iHashCode + (str != null ? str.hashCode() : 0)) * 31) + this.streamKeys.hashCode()) * 31) + Arrays.hashCode(this.keySetId)) * 31;
        String str2 = this.customCacheKey;
        return ((iHashCode2 + (str2 != null ? str2.hashCode() : 0)) * 31) + Arrays.hashCode(this.data);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.uri.toString());
        dest.writeString(this.mimeType);
        dest.writeInt(this.streamKeys.size());
        for (int i = 0; i < this.streamKeys.size(); i++) {
            dest.writeParcelable(this.streamKeys.get(i), 0);
        }
        dest.writeByteArray(this.keySetId);
        dest.writeString(this.customCacheKey);
        dest.writeByteArray(this.data);
    }
}
