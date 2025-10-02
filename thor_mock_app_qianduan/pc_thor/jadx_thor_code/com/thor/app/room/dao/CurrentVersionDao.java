package com.thor.app.room.dao;

import com.thor.app.room.entity.CurrentVersionsEntity;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

/* compiled from: CurrentVersionDao.kt */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\bg\u0018\u00002\u00020\u0001J\u0011\u0010\u0002\u001a\u00020\u0003H§@ø\u0001\u0000¢\u0006\u0002\u0010\u0004J\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H§@ø\u0001\u0000¢\u0006\u0002\u0010\u0004J\u001f\u0010\b\u001a\u00020\u00032\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H§@ø\u0001\u0000¢\u0006\u0002\u0010\n\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u000b"}, d2 = {"Lcom/thor/app/room/dao/CurrentVersionDao;", "", "deleteAll", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getCurrentVersion", "", "Lcom/thor/app/room/entity/CurrentVersionsEntity;", "insert", "version", "(Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public interface CurrentVersionDao {
    Object deleteAll(Continuation<? super Unit> continuation);

    Object getCurrentVersion(Continuation<? super List<CurrentVersionsEntity>> continuation);

    Object insert(List<CurrentVersionsEntity> list, Continuation<? super Unit> continuation);
}
