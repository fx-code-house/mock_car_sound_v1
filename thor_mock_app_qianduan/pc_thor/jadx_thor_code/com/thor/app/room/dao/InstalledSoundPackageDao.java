package com.thor.app.room.dao;

import com.google.android.exoplayer2.text.ttml.TtmlNode;
import com.thor.app.room.entity.InstalledSoundPackageEntity;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import java.util.List;
import kotlin.Metadata;

/* compiled from: InstalledSoundPackageDao.kt */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\t\n\u0002\b\u0006\bg\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H'J\u0010\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H'J\u0016\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t2\u0006\u0010\u0006\u001a\u00020\u0007H'J\u0014\u0010\u000b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\r0\fH'J\u0016\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u000e0\f2\u0006\u0010\u0010\u001a\u00020\u0007H'J\u0010\u0010\u0011\u001a\u00020\u00052\u0006\u0010\u0012\u001a\u00020\u000eH'J\u0016\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00140\t2\u0006\u0010\u0012\u001a\u00020\u000eH'J\u0010\u0010\u0015\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H'J\u0010\u0010\u0016\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H'J&\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00070\t2\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\u0018\u001a\u00020\u00072\u0006\u0010\u0019\u001a\u00020\u0007H'¨\u0006\u001a"}, d2 = {"Lcom/thor/app/room/dao/InstalledSoundPackageDao;", "", "deleteAllElements", "", "deleteById", "Lio/reactivex/Completable;", "packageId", "", "getDamagePck", "Lio/reactivex/Maybe;", "", "getEntities", "Lio/reactivex/Flowable;", "", "Lcom/thor/app/room/entity/InstalledSoundPackageEntity;", "getEntityById", TtmlNode.ATTR_ID, "insert", "entity", "insertEndReturnId", "", "updateDamagePck", "updateDamagePckEndReturnId", "updateEndReturnId", "versionId", "mode", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public interface InstalledSoundPackageDao {
    void deleteAllElements();

    Completable deleteById(int packageId);

    Maybe<Boolean> getDamagePck(int packageId);

    Flowable<List<InstalledSoundPackageEntity>> getEntities();

    Flowable<InstalledSoundPackageEntity> getEntityById(int id);

    Completable insert(InstalledSoundPackageEntity entity);

    Maybe<Long> insertEndReturnId(InstalledSoundPackageEntity entity);

    Completable updateDamagePck(int packageId);

    Completable updateDamagePckEndReturnId(int packageId);

    Maybe<Integer> updateEndReturnId(int packageId, int versionId, int mode);
}
