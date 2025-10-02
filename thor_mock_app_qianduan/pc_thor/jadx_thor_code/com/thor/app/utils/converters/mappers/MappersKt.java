package com.thor.app.utils.converters.mappers;

import com.thor.networkmodule.model.responses.sgu.SguSound;
import com.thor.networkmodule.model.responses.sgu.SguSoundJson;
import java.util.List;
import kotlin.Metadata;

/* compiled from: Mappers.kt */
@Metadata(d1 = {"\u0000\u0014\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0003\u001a\u000e\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u0004\u0018\u00010\u0002\u001a\u001a\u0010\u0003\u001a\n\u0012\u0004\u0012\u00020\u0001\u0018\u00010\u0004*\n\u0012\u0004\u0012\u00020\u0002\u0018\u00010\u0004\u001a\u000e\u0010\u0005\u001a\u0004\u0018\u00010\u0002*\u0004\u0018\u00010\u0001\u001a\u001a\u0010\u0006\u001a\n\u0012\u0004\u0012\u00020\u0002\u0018\u00010\u0004*\n\u0012\u0004\u0012\u00020\u0001\u0018\u00010\u0004¨\u0006\u0007"}, d2 = {"toSguSoundJsonModel", "Lcom/thor/networkmodule/model/responses/sgu/SguSoundJson;", "Lcom/thor/networkmodule/model/responses/sgu/SguSound;", "toSguSoundJsonModels", "", "toSguSoundModel", "toSguSoundModels", "thor-1.8.7_release"}, k = 2, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class MappersKt {
    public static final SguSound toSguSoundModel(SguSoundJson sguSoundJson) {
        return SguSoundMapper.INSTANCE.getInstance().getModel(sguSoundJson);
    }

    public static final List<SguSound> toSguSoundModels(List<SguSoundJson> list) {
        return SguSoundMapper.INSTANCE.getInstance().getModels(list);
    }

    public static final SguSoundJson toSguSoundJsonModel(SguSound sguSound) {
        return SguSoundJsonMapper.INSTANCE.getInstance().getModel(sguSound);
    }

    public static final List<SguSoundJson> toSguSoundJsonModels(List<SguSound> list) {
        return SguSoundJsonMapper.INSTANCE.getInstance().getModels(list);
    }
}
