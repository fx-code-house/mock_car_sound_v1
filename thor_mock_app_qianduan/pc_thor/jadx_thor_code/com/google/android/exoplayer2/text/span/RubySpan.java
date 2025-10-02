package com.google.android.exoplayer2.text.span;

/* loaded from: classes.dex */
public final class RubySpan implements LanguageFeatureSpan {
    public final int position;
    public final String rubyText;

    public RubySpan(String rubyText, int position) {
        this.rubyText = rubyText;
        this.position = position;
    }
}
