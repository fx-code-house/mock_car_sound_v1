package com.google.android.gms.fido.fido2.api.common;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.fido.fido2.api.common.Attachment;
import com.google.android.gms.fido.fido2.api.common.ResidentKeyRequirement;

/* compiled from: com.google.android.gms:play-services-fido@@20.1.0 */
/* loaded from: classes2.dex */
public class AuthenticatorSelectionCriteria extends AbstractSafeParcelable {
    public static final Parcelable.Creator<AuthenticatorSelectionCriteria> CREATOR = new zzm();
    private final Attachment zza;
    private final Boolean zzb;
    private final zzay zzc;
    private final ResidentKeyRequirement zzd;

    /* compiled from: com.google.android.gms:play-services-fido@@20.1.0 */
    public static class Builder {
        private Attachment zza;
        private Boolean zzb;
        private ResidentKeyRequirement zzc;

        public AuthenticatorSelectionCriteria build() {
            Attachment attachment = this.zza;
            String string = attachment == null ? null : attachment.toString();
            Boolean bool = this.zzb;
            ResidentKeyRequirement residentKeyRequirement = this.zzc;
            return new AuthenticatorSelectionCriteria(string, bool, null, residentKeyRequirement == null ? null : residentKeyRequirement.toString());
        }

        public Builder setAttachment(Attachment attachment) {
            this.zza = attachment;
            return this;
        }

        public Builder setRequireResidentKey(Boolean bool) {
            this.zzb = bool;
            return this;
        }

        public Builder setResidentKeyRequirement(ResidentKeyRequirement residentKeyRequirement) {
            this.zzc = residentKeyRequirement;
            return this;
        }
    }

    AuthenticatorSelectionCriteria(String str, Boolean bool, String str2, String str3) {
        Attachment attachmentFromString;
        ResidentKeyRequirement residentKeyRequirementFromString = null;
        if (str == null) {
            attachmentFromString = null;
        } else {
            try {
                attachmentFromString = Attachment.fromString(str);
            } catch (Attachment.UnsupportedAttachmentException | ResidentKeyRequirement.UnsupportedResidentKeyRequirementException | zzax e) {
                throw new IllegalArgumentException(e);
            }
        }
        this.zza = attachmentFromString;
        this.zzb = bool;
        this.zzc = str2 == null ? null : zzay.zza(str2);
        if (str3 != null) {
            residentKeyRequirementFromString = ResidentKeyRequirement.fromString(str3);
        }
        this.zzd = residentKeyRequirementFromString;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof AuthenticatorSelectionCriteria)) {
            return false;
        }
        AuthenticatorSelectionCriteria authenticatorSelectionCriteria = (AuthenticatorSelectionCriteria) obj;
        return Objects.equal(this.zza, authenticatorSelectionCriteria.zza) && Objects.equal(this.zzb, authenticatorSelectionCriteria.zzb) && Objects.equal(this.zzc, authenticatorSelectionCriteria.zzc) && Objects.equal(getResidentKeyRequirement(), authenticatorSelectionCriteria.getResidentKeyRequirement());
    }

    public Attachment getAttachment() {
        return this.zza;
    }

    public String getAttachmentAsString() {
        Attachment attachment = this.zza;
        if (attachment == null) {
            return null;
        }
        return attachment.toString();
    }

    public Boolean getRequireResidentKey() {
        return this.zzb;
    }

    public ResidentKeyRequirement getResidentKeyRequirement() {
        ResidentKeyRequirement residentKeyRequirement = this.zzd;
        if (residentKeyRequirement != null) {
            return residentKeyRequirement;
        }
        Boolean bool = this.zzb;
        if (bool == null || !bool.booleanValue()) {
            return null;
        }
        return ResidentKeyRequirement.RESIDENT_KEY_REQUIRED;
    }

    public String getResidentKeyRequirementAsString() {
        if (getResidentKeyRequirement() == null) {
            return null;
        }
        return getResidentKeyRequirement().toString();
    }

    public int hashCode() {
        return Objects.hashCode(this.zza, this.zzb, this.zzc, getResidentKeyRequirement());
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 2, getAttachmentAsString(), false);
        SafeParcelWriter.writeBooleanObject(parcel, 3, getRequireResidentKey(), false);
        zzay zzayVar = this.zzc;
        SafeParcelWriter.writeString(parcel, 4, zzayVar == null ? null : zzayVar.toString(), false);
        SafeParcelWriter.writeString(parcel, 5, getResidentKeyRequirementAsString(), false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }
}
