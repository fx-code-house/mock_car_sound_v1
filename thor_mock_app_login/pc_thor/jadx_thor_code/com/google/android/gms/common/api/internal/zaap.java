package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.api.Api;
import java.util.ArrayList;

/* compiled from: com.google.android.gms:play-services-base@@18.0.1 */
/* loaded from: classes2.dex */
final class zaap extends zaav {
    final /* synthetic */ zaaw zaa;
    private final ArrayList<Api.Client> zac;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public zaap(zaaw zaawVar, ArrayList<Api.Client> arrayList) {
        super(zaawVar, null);
        this.zaa = zaawVar;
        this.zac = arrayList;
    }

    @Override // com.google.android.gms.common.api.internal.zaav
    public final void zaa() {
        zaaw zaawVar = this.zaa;
        zaawVar.zaa.zag.zad = zaaw.zao(zaawVar);
        ArrayList<Api.Client> arrayList = this.zac;
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            Api.Client client = arrayList.get(i);
            zaaw zaawVar2 = this.zaa;
            client.getRemoteService(zaawVar2.zao, zaawVar2.zaa.zag.zad);
        }
    }
}
