package com.google.android.gms.vision.text;

import android.graphics.Point;
import android.graphics.Rect;
import com.google.android.gms.internal.vision.zzah;
import com.google.android.gms.internal.vision.zzao;
import java.util.ArrayList;
import java.util.List;

/* compiled from: com.google.android.gms:play-services-vision@@20.1.1 */
/* loaded from: classes2.dex */
public class Line implements Text {
    private zzah zzef;
    private List<Element> zzeg;

    Line(zzah zzahVar) {
        this.zzef = zzahVar;
    }

    @Override // com.google.android.gms.vision.text.Text
    public String getLanguage() {
        return this.zzef.zzej;
    }

    @Override // com.google.android.gms.vision.text.Text
    public String getValue() {
        return this.zzef.zzes;
    }

    @Override // com.google.android.gms.vision.text.Text
    public Rect getBoundingBox() {
        return zzc.zza(this);
    }

    @Override // com.google.android.gms.vision.text.Text
    public Point[] getCornerPoints() {
        return zzc.zza(this.zzef.zzep);
    }

    @Override // com.google.android.gms.vision.text.Text
    public List<? extends Text> getComponents() {
        if (this.zzef.zzeo.length == 0) {
            return new ArrayList(0);
        }
        if (this.zzeg == null) {
            this.zzeg = new ArrayList(this.zzef.zzeo.length);
            for (zzao zzaoVar : this.zzef.zzeo) {
                this.zzeg.add(new Element(zzaoVar));
            }
        }
        return this.zzeg;
    }

    public float getAngle() {
        return this.zzef.zzep.zzen;
    }

    public boolean isVertical() {
        return this.zzef.zzeu;
    }
}
