package com.google.android.gms.vision.text;

import android.graphics.Point;
import android.graphics.Rect;
import android.util.SparseArray;
import com.google.android.exoplayer2.C;
import com.google.android.gms.internal.vision.zzab;
import com.google.android.gms.internal.vision.zzah;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;

/* compiled from: com.google.android.gms:play-services-vision@@20.1.1 */
/* loaded from: classes2.dex */
public class TextBlock implements Text {
    private Point[] cornerPoints;
    private zzah[] zzeh;
    private List<Line> zzei;
    private String zzej;
    private Rect zzek;

    TextBlock(SparseArray<zzah> sparseArray) {
        this.zzeh = new zzah[sparseArray.size()];
        int i = 0;
        while (true) {
            zzah[] zzahVarArr = this.zzeh;
            if (i >= zzahVarArr.length) {
                return;
            }
            zzahVarArr[i] = sparseArray.valueAt(i);
            i++;
        }
    }

    @Override // com.google.android.gms.vision.text.Text
    public String getLanguage() {
        String str = this.zzej;
        if (str != null) {
            return str;
        }
        HashMap map = new HashMap();
        for (zzah zzahVar : this.zzeh) {
            map.put(zzahVar.zzej, Integer.valueOf((map.containsKey(zzahVar.zzej) ? ((Integer) map.get(zzahVar.zzej)).intValue() : 0) + 1));
        }
        String str2 = (String) ((Map.Entry) Collections.max(map.entrySet(), new zza(this))).getKey();
        this.zzej = str2;
        if (str2 == null || str2.isEmpty()) {
            this.zzej = C.LANGUAGE_UNDETERMINED;
        }
        return this.zzej;
    }

    @Override // com.google.android.gms.vision.text.Text
    public String getValue() {
        if (this.zzeh.length == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder(this.zzeh[0].zzes);
        for (int i = 1; i < this.zzeh.length; i++) {
            sb.append(StringUtils.LF);
            sb.append(this.zzeh[i].zzes);
        }
        return sb.toString();
    }

    @Override // com.google.android.gms.vision.text.Text
    public Point[] getCornerPoints() {
        TextBlock textBlock;
        zzah[] zzahVarArr;
        TextBlock textBlock2 = this;
        if (textBlock2.cornerPoints == null) {
            char c = 0;
            if (textBlock2.zzeh.length == 0) {
                textBlock2.cornerPoints = new Point[0];
                textBlock = textBlock2;
            } else {
                int iMax = Integer.MIN_VALUE;
                int i = 0;
                int iMin = Integer.MAX_VALUE;
                int iMin2 = Integer.MAX_VALUE;
                int iMax2 = Integer.MIN_VALUE;
                while (true) {
                    zzahVarArr = textBlock2.zzeh;
                    if (i >= zzahVarArr.length) {
                        break;
                    }
                    zzab zzabVar = zzahVarArr[i].zzep;
                    zzab zzabVar2 = textBlock2.zzeh[c].zzep;
                    int i2 = -zzabVar2.left;
                    int i3 = -zzabVar2.top;
                    double dSin = Math.sin(Math.toRadians(zzabVar2.zzen));
                    double dCos = Math.cos(Math.toRadians(zzabVar2.zzen));
                    Point[] pointArr = new Point[4];
                    Point point = new Point(zzabVar.left, zzabVar.top);
                    pointArr[c] = point;
                    point.offset(i2, i3);
                    int i4 = iMax2;
                    int i5 = (int) ((pointArr[c].x * dCos) + (pointArr[c].y * dSin));
                    int i6 = (int) (((-pointArr[0].x) * dSin) + (pointArr[0].y * dCos));
                    pointArr[0].x = i5;
                    pointArr[0].y = i6;
                    pointArr[1] = new Point(zzabVar.width + i5, i6);
                    pointArr[2] = new Point(zzabVar.width + i5, zzabVar.height + i6);
                    pointArr[3] = new Point(i5, i6 + zzabVar.height);
                    iMax2 = i4;
                    for (int i7 = 0; i7 < 4; i7++) {
                        Point point2 = pointArr[i7];
                        iMin = Math.min(iMin, point2.x);
                        iMax = Math.max(iMax, point2.x);
                        iMin2 = Math.min(iMin2, point2.y);
                        iMax2 = Math.max(iMax2, point2.y);
                    }
                    i++;
                    c = 0;
                    textBlock2 = this;
                }
                int i8 = iMax2;
                zzab zzabVar3 = zzahVarArr[c].zzep;
                int i9 = zzabVar3.left;
                int i10 = zzabVar3.top;
                double dSin2 = Math.sin(Math.toRadians(zzabVar3.zzen));
                double dCos2 = Math.cos(Math.toRadians(zzabVar3.zzen));
                Point[] pointArr2 = {new Point(iMin, iMin2), new Point(iMax, iMin2), new Point(iMax, i8), new Point(iMin, i8)};
                for (int i11 = 0; i11 < 4; i11++) {
                    pointArr2[i11].x = (int) ((pointArr2[i11].x * dCos2) - (pointArr2[i11].y * dSin2));
                    pointArr2[i11].y = (int) ((pointArr2[i11].x * dSin2) + (pointArr2[i11].y * dCos2));
                    pointArr2[i11].offset(i9, i10);
                }
                textBlock = this;
                textBlock.cornerPoints = pointArr2;
            }
        } else {
            textBlock = textBlock2;
        }
        return textBlock.cornerPoints;
    }

    @Override // com.google.android.gms.vision.text.Text
    public List<? extends Text> getComponents() {
        if (this.zzeh.length == 0) {
            return new ArrayList(0);
        }
        if (this.zzei == null) {
            this.zzei = new ArrayList(this.zzeh.length);
            for (zzah zzahVar : this.zzeh) {
                this.zzei.add(new Line(zzahVar));
            }
        }
        return this.zzei;
    }

    @Override // com.google.android.gms.vision.text.Text
    public Rect getBoundingBox() {
        if (this.zzek == null) {
            this.zzek = zzc.zza(this);
        }
        return this.zzek;
    }
}
