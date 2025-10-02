package com.google.android.gms.maps.model;

import android.content.Context;
import android.content.res.Resources;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.util.IOUtils;
import java.io.IOException;

/* loaded from: classes2.dex */
public final class MapStyleOptions extends AbstractSafeParcelable {
    public static final Parcelable.Creator<MapStyleOptions> CREATOR = new zzg();
    private static final String TAG = "MapStyleOptions";
    private String zzdl;

    public MapStyleOptions(String str) {
        this.zzdl = str;
    }

    public static MapStyleOptions loadRawResourceStyle(Context context, int i) throws Resources.NotFoundException {
        try {
            return new MapStyleOptions(new String(IOUtils.readInputStreamFully(context.getResources().openRawResource(i)), "UTF-8"));
        } catch (IOException e) {
            String strValueOf = String.valueOf(e);
            throw new Resources.NotFoundException(new StringBuilder(String.valueOf(strValueOf).length() + 37).append("Failed to read resource ").append(i).append(": ").append(strValueOf).toString());
        }
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 2, this.zzdl, false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }
}
