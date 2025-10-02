package com.google.android.exoplayer2.source.dash.manifest;

import com.google.android.exoplayer2.util.Util;

/* loaded from: classes.dex */
public final class ProgramInformation {
    public final String copyright;
    public final String lang;
    public final String moreInformationURL;
    public final String source;
    public final String title;

    public ProgramInformation(String title, String source, String copyright, String moreInformationURL, String lang) {
        this.title = title;
        this.source = source;
        this.copyright = copyright;
        this.moreInformationURL = moreInformationURL;
        this.lang = lang;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ProgramInformation)) {
            return false;
        }
        ProgramInformation programInformation = (ProgramInformation) obj;
        return Util.areEqual(this.title, programInformation.title) && Util.areEqual(this.source, programInformation.source) && Util.areEqual(this.copyright, programInformation.copyright) && Util.areEqual(this.moreInformationURL, programInformation.moreInformationURL) && Util.areEqual(this.lang, programInformation.lang);
    }

    public int hashCode() {
        String str = this.title;
        int iHashCode = (527 + (str != null ? str.hashCode() : 0)) * 31;
        String str2 = this.source;
        int iHashCode2 = (iHashCode + (str2 != null ? str2.hashCode() : 0)) * 31;
        String str3 = this.copyright;
        int iHashCode3 = (iHashCode2 + (str3 != null ? str3.hashCode() : 0)) * 31;
        String str4 = this.moreInformationURL;
        int iHashCode4 = (iHashCode3 + (str4 != null ? str4.hashCode() : 0)) * 31;
        String str5 = this.lang;
        return iHashCode4 + (str5 != null ? str5.hashCode() : 0);
    }
}
