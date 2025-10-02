package com.google.android.gms.vision.text;

import android.graphics.Point;
import android.graphics.Rect;
import com.google.android.gms.internal.vision.zzao;
import java.util.ArrayList;
import java.util.List;

/* compiled from: com.google.android.gms:play-services-vision@@20.1.1 */
/* loaded from: classes2.dex */
public class Element implements Text {
    private zzao zzee;

    Element(zzao zzaoVar) {
        this.zzee = zzaoVar;
    }

    @Override // com.google.android.gms.vision.text.Text
    public String getLanguage() {
        return this.zzee.zzej;
    }

    @Override // com.google.android.gms.vision.text.Text
    public String getValue() {
        return this.zzee.zzes;
    }

    @Override // com.google.android.gms.vision.text.Text
    public Rect getBoundingBox() {
        return zzc.zza(this);
    }

    @Override // com.google.android.gms.vision.text.Text
    public Point[] getCornerPoints() {
        return zzc.zza(this.zzee.zzep);
    }

    @Override // com.google.android.gms.vision.text.Text
    public List<? extends Text> getComponents() {
        return new ArrayList();
    }
}
