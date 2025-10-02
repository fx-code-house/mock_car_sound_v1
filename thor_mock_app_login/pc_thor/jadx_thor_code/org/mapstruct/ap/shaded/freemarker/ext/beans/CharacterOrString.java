package org.mapstruct.ap.shaded.freemarker.ext.beans;

/* loaded from: classes3.dex */
final class CharacterOrString {
    private final String stringValue;

    CharacterOrString(String str) {
        this.stringValue = str;
    }

    String getAsString() {
        return this.stringValue;
    }

    char getAsChar() {
        return this.stringValue.charAt(0);
    }
}
