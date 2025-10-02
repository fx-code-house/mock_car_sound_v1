package com.thor.app.room.dao;

import com.google.android.exoplayer2.text.ttml.TtmlNode;
import com.thor.app.room.entity.ShopSoundPackageEntity;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;
import java.util.List;
import kotlin.Metadata;

/* compiled from: ShopSoundPackageDao.kt */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0011\n\u0002\b\u0003\bg\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H'J\u0010\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H'J\u0014\u0010\b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\n0\tH'J\u0014\u0010\f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\n0\rH'J\u0016\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000b0\r2\u0006\u0010\u000f\u001a\u00020\u0007H'J!\u0010\u0010\u001a\u00020\u00052\u0012\u0010\u0011\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u000b0\u0012\"\u00020\u000bH'¢\u0006\u0002\u0010\u0013J\u0010\u0010\u0014\u001a\u00020\u00052\u0006\u0010\u0011\u001a\u00020\u000bH'¨\u0006\u0015"}, d2 = {"Lcom/thor/app/room/dao/ShopSoundPackageDao;", "", "deleteAllElements", "", "deleteById", "Lio/reactivex/Completable;", "packageId", "", "getEntities", "Lio/reactivex/Single;", "", "Lcom/thor/app/room/entity/ShopSoundPackageEntity;", "getEntitiesFlow", "Lio/reactivex/Flowable;", "getEntityById", TtmlNode.ATTR_ID, "insert", "entity", "", "([Lcom/thor/app/room/entity/ShopSoundPackageEntity;)Lio/reactivex/Completable;", "update", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public interface ShopSoundPackageDao {
    void deleteAllElements();

    Completable deleteById(int packageId);

    Single<List<ShopSoundPackageEntity>> getEntities();

    Flowable<List<ShopSoundPackageEntity>> getEntitiesFlow();

    Flowable<ShopSoundPackageEntity> getEntityById(int id);

    Completable insert(ShopSoundPackageEntity... entity);

    Completable update(ShopSoundPackageEntity entity);
}
