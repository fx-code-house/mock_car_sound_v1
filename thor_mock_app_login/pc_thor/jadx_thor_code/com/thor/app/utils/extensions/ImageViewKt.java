package com.thor.app.utils.extensions;

import android.graphics.PorterDuff;
import android.widget.ImageView;
import androidx.core.content.ContextCompat;
import com.google.android.exoplayer2.text.ttml.TtmlNode;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ImageView.kt */
@Metadata(d1 = {"\u0000\u0012\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\u001a\u0014\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\b\b\u0001\u0010\u0003\u001a\u00020\u0004¨\u0006\u0005"}, d2 = {"setSvgColor", "", "Landroid/widget/ImageView;", TtmlNode.ATTR_TTS_COLOR, "", "thor-1.8.7_release"}, k = 2, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class ImageViewKt {
    public static final void setSvgColor(ImageView imageView, int i) {
        Intrinsics.checkNotNullParameter(imageView, "<this>");
        imageView.setColorFilter(ContextCompat.getColor(imageView.getContext(), i), PorterDuff.Mode.SRC_IN);
    }
}
