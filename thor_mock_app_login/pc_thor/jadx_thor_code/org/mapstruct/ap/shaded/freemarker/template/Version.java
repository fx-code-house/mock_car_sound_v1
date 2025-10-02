package org.mapstruct.ap.shaded.freemarker.template;

import java.io.Serializable;
import java.util.Date;
import org.mapstruct.ap.shaded.freemarker.template.utility.StringUtil;

/* loaded from: classes3.dex */
public final class Version implements Serializable {
    private final Date buildDate;
    private String calculatedStringValue;
    private final String extraInfo;
    private final Boolean gaeCompliant;
    private int hashCode;
    private final int intValue;
    private final int major;
    private final int micro;
    private final int minor;
    private final String originalStringValue;

    public static int intValueFor(int i, int i2, int i3) {
        return (i * 1000000) + (i2 * 1000) + i3;
    }

    private boolean isNumber(char c) {
        return c >= '0' && c <= '9';
    }

    public Version(String str) {
        this(str, (Boolean) null, (Date) null);
    }

    public Version(String str, Boolean bool, Date date) {
        String strSubstring;
        char cCharAt;
        String strTrim = str.trim();
        this.originalStringValue = strTrim;
        int[] iArr = new int[3];
        int i = 0;
        int i2 = 0;
        while (true) {
            if (i >= strTrim.length()) {
                strSubstring = null;
                break;
            }
            char cCharAt2 = strTrim.charAt(i);
            if (isNumber(cCharAt2)) {
                iArr[i2] = (iArr[i2] * 10) + (cCharAt2 - '0');
            } else {
                if (i == 0) {
                    throw new IllegalArgumentException(new StringBuffer("The version number string ").append(StringUtil.jQuote(strTrim)).append(" doesn't start with a number.").toString());
                }
                if (cCharAt2 == '.') {
                    int i3 = i + 1;
                    char cCharAt3 = i3 >= strTrim.length() ? (char) 0 : strTrim.charAt(i3);
                    if (cCharAt3 == '.') {
                        throw new IllegalArgumentException(new StringBuffer("The version number string ").append(StringUtil.jQuote(strTrim)).append(" contains multiple dots after a number.").toString());
                    }
                    if (i2 == 2 || !isNumber(cCharAt3)) {
                        break;
                    } else {
                        i2++;
                    }
                } else {
                    strSubstring = strTrim.substring(i);
                    break;
                }
            }
            i++;
        }
        strSubstring = strTrim.substring(i);
        if (strSubstring != null && ((cCharAt = strSubstring.charAt(0)) == '.' || cCharAt == '-' || cCharAt == '_')) {
            strSubstring = strSubstring.substring(1);
            if (strSubstring.length() == 0) {
                throw new IllegalArgumentException(new StringBuffer("The version number string ").append(StringUtil.jQuote(strTrim)).append(" has an extra info section opened with \"").append(cCharAt).append("\", but it's empty.").toString());
            }
        }
        this.extraInfo = strSubstring;
        this.major = iArr[0];
        this.minor = iArr[1];
        this.micro = iArr[2];
        this.intValue = calculateIntValue();
        this.gaeCompliant = bool;
        this.buildDate = date;
    }

    public Version(int i, int i2, int i3) {
        this(i, i2, i3, null, null, null);
    }

    public Version(int i, int i2, int i3, String str, Boolean bool, Date date) {
        this.major = i;
        this.minor = i2;
        this.micro = i3;
        this.extraInfo = str;
        this.gaeCompliant = bool;
        this.buildDate = date;
        this.intValue = calculateIntValue();
        this.originalStringValue = null;
    }

    private int calculateIntValue() {
        return intValueFor(this.major, this.minor, this.micro);
    }

    private String getStringValue() {
        String str;
        String str2 = this.originalStringValue;
        if (str2 != null) {
            return str2;
        }
        synchronized (this) {
            if (this.calculatedStringValue == null) {
                this.calculatedStringValue = new StringBuffer().append(this.major).append(".").append(this.minor).append(".").append(this.micro).toString();
                if (this.extraInfo != null) {
                    this.calculatedStringValue = new StringBuffer().append(this.calculatedStringValue).append("-").append(this.extraInfo).toString();
                }
            }
            str = this.calculatedStringValue;
        }
        return str;
    }

    public String toString() {
        return getStringValue();
    }

    public int getMajor() {
        return this.major;
    }

    public int getMinor() {
        return this.minor;
    }

    public int getMicro() {
        return this.micro;
    }

    public String getExtraInfo() {
        return this.extraInfo;
    }

    public Boolean isGAECompliant() {
        return this.gaeCompliant;
    }

    public Date getBuildDate() {
        return this.buildDate;
    }

    public int intValue() {
        return this.intValue;
    }

    public int hashCode() {
        int i;
        int i2 = this.hashCode;
        if (i2 != 0) {
            return i2;
        }
        synchronized (this) {
            if (this.hashCode == 0) {
                Date date = this.buildDate;
                int iHashCode = 0;
                int iHashCode2 = ((date == null ? 0 : date.hashCode()) + 31) * 31;
                String str = this.extraInfo;
                int iHashCode3 = (iHashCode2 + (str == null ? 0 : str.hashCode())) * 31;
                Boolean bool = this.gaeCompliant;
                if (bool != null) {
                    iHashCode = bool.hashCode();
                }
                int i3 = ((iHashCode3 + iHashCode) * 31) + this.intValue;
                if (i3 == 0) {
                    i3 = -1;
                }
                this.hashCode = i3;
            }
            i = this.hashCode;
        }
        return i;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Version version = (Version) obj;
        if (this.intValue != version.intValue || version.hashCode() != hashCode()) {
            return false;
        }
        Date date = this.buildDate;
        if (date == null) {
            if (version.buildDate != null) {
                return false;
            }
        } else if (!date.equals(version.buildDate)) {
            return false;
        }
        String str = this.extraInfo;
        if (str == null) {
            if (version.extraInfo != null) {
                return false;
            }
        } else if (!str.equals(version.extraInfo)) {
            return false;
        }
        Boolean bool = this.gaeCompliant;
        if (bool == null) {
            if (version.gaeCompliant != null) {
                return false;
            }
        } else if (!bool.equals(version.gaeCompliant)) {
            return false;
        }
        return true;
    }
}
