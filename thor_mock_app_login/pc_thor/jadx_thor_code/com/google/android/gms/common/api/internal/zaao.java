package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import java.util.ArrayList;
import java.util.Map;

/* compiled from: com.google.android.gms:play-services-base@@18.0.1 */
/* loaded from: classes2.dex */
final class zaao extends zaav {
    final /* synthetic */ zaaw zaa;
    private final Map<Api.Client, zaal> zac;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public zaao(zaaw zaawVar, Map<Api.Client, zaal> map) {
        super(zaawVar, null);
        this.zaa = zaawVar;
        this.zac = map;
    }

    @Override // com.google.android.gms.common.api.internal.zaav
    public final void zaa() {
        com.google.android.gms.common.internal.zal zalVar = new com.google.android.gms.common.internal.zal(this.zaa.zad);
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (Api.Client client : this.zac.keySet()) {
            if (!client.requiresGooglePlayServices() || this.zac.get(client).zac) {
                arrayList2.add(client);
            } else {
                arrayList.add(client);
            }
        }
        int iZab = -1;
        int i = 0;
        if (!arrayList.isEmpty()) {
            int size = arrayList.size();
            while (i < size) {
                iZab = zalVar.zab(this.zaa.zac, (Api.Client) arrayList.get(i));
                i++;
                if (iZab != 0) {
                    break;
                }
            }
        } else {
            int size2 = arrayList2.size();
            while (i < size2) {
                iZab = zalVar.zab(this.zaa.zac, (Api.Client) arrayList2.get(i));
                i++;
                if (iZab == 0) {
                    break;
                }
            }
        }
        if (iZab != 0) {
            ConnectionResult connectionResult = new ConnectionResult(iZab, null);
            zaaw zaawVar = this.zaa;
            zaawVar.zaa.zal(new zaam(this, zaawVar, connectionResult));
            return;
        }
        zaaw zaawVar2 = this.zaa;
        if (zaawVar2.zam && zaawVar2.zak != null) {
            zaawVar2.zak.zab();
        }
        for (Api.Client client2 : this.zac.keySet()) {
            zaal zaalVar = this.zac.get(client2);
            if (!client2.requiresGooglePlayServices() || zalVar.zab(this.zaa.zac, client2) == 0) {
                client2.connect(zaalVar);
            } else {
                zaaw zaawVar3 = this.zaa;
                zaawVar3.zaa.zal(new zaan(this, zaawVar3, zaalVar));
            }
        }
    }
}
