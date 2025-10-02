package com.thor.app.utils.converters.mappers;

import com.thor.networkmodule.model.responses.sgu.SguSound;
import com.thor.networkmodule.model.responses.sgu.SguSoundJson;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.mapstruct.factory.Mappers;

/* compiled from: SguSoundJsonMapper.kt */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0003\bg\u0018\u0000 \t2\u00020\u0001:\u0001\tJ\u0014\u0010\u0002\u001a\u0004\u0018\u00010\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005H'J \u0010\u0006\u001a\n\u0012\u0004\u0012\u00020\u0003\u0018\u00010\u00072\u000e\u0010\b\u001a\n\u0012\u0004\u0012\u00020\u0005\u0018\u00010\u0007H&¨\u0006\n"}, d2 = {"Lcom/thor/app/utils/converters/mappers/SguSoundJsonMapper;", "", "getModel", "Lcom/thor/networkmodule/model/responses/sgu/SguSoundJson;", "obj", "Lcom/thor/networkmodule/model/responses/sgu/SguSound;", "getModels", "", "list", "Companion", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public interface SguSoundJsonMapper {

    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = Companion.$$INSTANCE;

    SguSoundJson getModel(SguSound obj);

    List<SguSoundJson> getModels(List<SguSound> list);

    /* compiled from: SguSoundJsonMapper.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0006\u0010\u0003\u001a\u00020\u0004¨\u0006\u0005"}, d2 = {"Lcom/thor/app/utils/converters/mappers/SguSoundJsonMapper$Companion;", "", "()V", "getInstance", "Lcom/thor/app/utils/converters/mappers/SguSoundJsonMapper;", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        static final /* synthetic */ Companion $$INSTANCE = new Companion();

        private Companion() {
        }

        public final SguSoundJsonMapper getInstance() {
            Object mapper = Mappers.getMapper(SguSoundJsonMapper.class);
            Intrinsics.checkNotNullExpressionValue(mapper, "getMapper(SguSoundJsonMapper::class.java)");
            return (SguSoundJsonMapper) mapper;
        }
    }
}
