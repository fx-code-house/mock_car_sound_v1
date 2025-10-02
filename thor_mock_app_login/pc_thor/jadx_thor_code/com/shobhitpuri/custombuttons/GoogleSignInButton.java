package com.shobhitpuri.custombuttons;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;

/* loaded from: classes2.dex */
public class GoogleSignInButton extends AppCompatButton {
    private boolean mIsDarkTheme;
    private String mText;

    public GoogleSignInButton(Context context) {
        super(context);
    }

    public GoogleSignInButton(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context, attributeSet, 0);
    }

    public GoogleSignInButton(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context, attributeSet, i);
    }

    private void init(Context context, AttributeSet attributeSet, int i) {
        parseAttributes(context, attributeSet, i);
        setButtonParams();
    }

    private void parseAttributes(Context context, AttributeSet attributeSet, int i) {
        if (attributeSet == null) {
            return;
        }
        TypedArray typedArrayObtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.ButtonStyleable, i, 0);
        try {
            try {
                this.mText = typedArrayObtainStyledAttributes.getString(R.styleable.ButtonStyleable_android_text);
                this.mIsDarkTheme = typedArrayObtainStyledAttributes.getBoolean(R.styleable.ButtonStyleable_isDarkTheme, false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } finally {
            typedArrayObtainStyledAttributes.recycle();
        }
    }

    private void setButtonParams() {
        setTransformationMethod(null);
        setButtonText();
        setButtonTextSize();
        setButtonTextColor();
        setButtonBackground();
    }

    private void setButtonTextSize() {
        setTextSize(2, 14.0f);
    }

    private void setButtonBackground() {
        setBackgroundResource(this.mIsDarkTheme ? R.drawable.dark_theme_google_icon_selector : R.drawable.light_theme_google_icon_selector);
    }

    private void setButtonTextColor() {
        setTextColor(ContextCompat.getColor(getContext(), this.mIsDarkTheme ? android.R.color.white : R.color.text_color_dark));
    }

    private void setButtonText() {
        String str = this.mText;
        if (str == null || str.isEmpty()) {
            this.mText = getContext().getString(R.string.google_sign_in);
        }
        setText(this.mText);
    }
}
