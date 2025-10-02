package com.thor.app.gui.activities.preset.sgu;

import android.content.Context;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.thor.app.managers.BleManager;
import com.thor.app.managers.DataLayerManager;
import com.thor.app.managers.UsersManager;
import com.thor.app.room.ThorDatabase;
import com.thor.app.settings.Settings;
import com.thor.app.utils.converters.JsonConverterKt;
import com.thor.app.utils.converters.mappers.MappersKt;
import com.thor.networkmodule.model.responses.sgu.SguSound;
import com.thor.networkmodule.model.responses.sgu.SguSoundConfig;
import com.thor.networkmodule.model.responses.sgu.SguSoundFile;
import com.thor.networkmodule.model.responses.sgu.SguSoundJson;
import com.thor.networkmodule.model.responses.sgu.SguSoundJsonWrapper;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import javax.inject.Inject;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.TypeIntrinsics;
import kotlin.random.Random;
import timber.log.Timber;

/* compiled from: AddSguPresetViewModel.kt */
@Metadata(d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0010\u0003\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B\u001f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u001e\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017J\u0014\u0010\u0018\u001a\u00020\u00112\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00130\u001aJ\u0010\u0010\u001b\u001a\u00020\u00112\u0006\u0010\u001c\u001a\u00020\u001dH\u0002J\u000e\u0010\u001e\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013R\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0017\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000b0\r¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001f"}, d2 = {"Lcom/thor/app/gui/activities/preset/sgu/AddSguPresetViewModel;", "Landroidx/lifecycle/ViewModel;", "usersManager", "Lcom/thor/app/managers/UsersManager;", "bleManager", "Lcom/thor/app/managers/BleManager;", "database", "Lcom/thor/app/room/ThorDatabase;", "(Lcom/thor/app/managers/UsersManager;Lcom/thor/app/managers/BleManager;Lcom/thor/app/room/ThorDatabase;)V", "_uiState", "Landroidx/lifecycle/MutableLiveData;", "Lcom/thor/app/gui/activities/preset/sgu/AddSguPresetUiState;", "uiState", "Landroidx/lifecycle/LiveData;", "getUiState", "()Landroidx/lifecycle/LiveData;", "changeSoundFavoriteStatus", "", "sound", "Lcom/thor/networkmodule/model/responses/sgu/SguSound;", "soundSetId", "", "context", "Landroid/content/Context;", "fetchSoundFavoriteStatus", "sounds", "", "handleError", "throwable", "", "playSound", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class AddSguPresetViewModel extends ViewModel {
    private final MutableLiveData<AddSguPresetUiState> _uiState;
    private final BleManager bleManager;
    private final ThorDatabase database;
    private final LiveData<AddSguPresetUiState> uiState;
    private final UsersManager usersManager;

    @Inject
    public AddSguPresetViewModel(UsersManager usersManager, BleManager bleManager, ThorDatabase database) {
        Intrinsics.checkNotNullParameter(usersManager, "usersManager");
        Intrinsics.checkNotNullParameter(bleManager, "bleManager");
        Intrinsics.checkNotNullParameter(database, "database");
        this.usersManager = usersManager;
        this.bleManager = bleManager;
        this.database = database;
        MutableLiveData<AddSguPresetUiState> mutableLiveData = new MutableLiveData<>();
        this._uiState = mutableLiveData;
        this.uiState = mutableLiveData;
    }

    public final LiveData<AddSguPresetUiState> getUiState() {
        return this.uiState;
    }

    public final void fetchSoundFavoriteStatus(List<SguSound> sounds) {
        List<SguSoundJson> presets;
        Intrinsics.checkNotNullParameter(sounds, "sounds");
        SguSoundJsonWrapper sguSoundList = JsonConverterKt.toSguSoundList(Settings.INSTANCE.getSguSoundFavoritesJSON());
        if (sguSoundList != null && (presets = sguSoundList.getPresets()) != null) {
            for (SguSoundJson sguSoundJson : presets) {
                for (SguSound sguSound : sounds) {
                    if (sguSoundJson.getId() == sguSound.getId()) {
                        sguSound.setFavourite(true);
                    }
                }
            }
        }
        this._uiState.postValue(new SguSoundsLoaded(sounds));
    }

    public final void playSound(SguSound sound) {
        Intrinsics.checkNotNullParameter(sound, "sound");
        this.bleManager.getMCompositeDisposable().add(this.bleManager.executePlaySguSoundCommand((short) ((SguSoundFile) CollectionsKt.random(sound.getSoundFiles(), Random.INSTANCE)).getId(), new SguSoundConfig((short) 0, (short) 0, (short) 0, 7, null)).subscribe());
    }

    public final void changeSoundFavoriteStatus(SguSound sound, int soundSetId, Context context) {
        Object obj;
        Object next;
        Object next2;
        Intrinsics.checkNotNullParameter(sound, "sound");
        Intrinsics.checkNotNullParameter(context, "context");
        SguSoundJsonWrapper sguSoundList = JsonConverterKt.toSguSoundList(Settings.INSTANCE.getSguSoundFavoritesJSON());
        if (sguSoundList == null) {
            sguSoundList = new SguSoundJsonWrapper(new ArrayList());
        }
        List<SguSoundJson> presets = sguSoundList.getPresets();
        if (presets == null) {
            presets = CollectionsKt.toMutableList((Collection) CollectionsKt.emptyList());
        }
        List<SguSoundJson> list = presets;
        Iterator<T> it = list.iterator();
        while (true) {
            obj = null;
            if (!it.hasNext()) {
                next = null;
                break;
            } else {
                next = it.next();
                if (((SguSoundJson) next).getId() == sound.getId()) {
                    break;
                }
            }
        }
        if (next == null) {
            this._uiState.postValue(new SguSoundAddedOrRemoved(true));
            sound.setSound_set_id(Integer.valueOf(soundSetId));
            SguSoundJson sguSoundJsonModel = MappersKt.toSguSoundJsonModel(sound);
            if (sguSoundJsonModel != null) {
                presets.add(sguSoundJsonModel);
            }
        } else {
            Iterator<T> it2 = list.iterator();
            while (true) {
                if (!it2.hasNext()) {
                    next2 = null;
                    break;
                } else {
                    next2 = it2.next();
                    if (((SguSoundJson) next2).getId() == sound.getId()) {
                        break;
                    }
                }
            }
            if (next2 != null) {
                this._uiState.postValue(new SguSoundAddedOrRemoved(false));
                List<SguSoundJson> list2 = presets;
                Iterator<T> it3 = list.iterator();
                while (true) {
                    if (!it3.hasNext()) {
                        break;
                    }
                    Object next3 = it3.next();
                    if (((SguSoundJson) next3).getId() == sound.getId()) {
                        obj = next3;
                        break;
                    }
                }
                TypeIntrinsics.asMutableCollection(list2).remove(obj);
            }
        }
        String sguSoundJson = JsonConverterKt.toSguSoundJson(new SguSoundJsonWrapper(presets));
        Settings.INSTANCE.saveSguSoundFavoritesJSON(sguSoundJson);
        DataLayerManager.Companion companion = DataLayerManager.INSTANCE;
        Context context2 = this.usersManager.getContext();
        Intrinsics.checkNotNullExpressionValue(context2, "usersManager.context");
        companion.from(context2).sendSguJson(sguSoundJson);
        DataLayerManager.Companion companion2 = DataLayerManager.INSTANCE;
        Context context3 = this.usersManager.getContext();
        Intrinsics.checkNotNullExpressionValue(context3, "usersManager.context");
        companion2.from(context3).sendSguList(MappersKt.toSguSoundModels(presets));
    }

    private final void handleError(Throwable throwable) {
        this._uiState.postValue(new AddSguPresetError(throwable.getMessage()));
        Timber.INSTANCE.e(throwable);
    }
}
