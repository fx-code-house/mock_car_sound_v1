package com.thor.app.gui.fragments.presets.sgu;

import android.content.Context;
import androidx.databinding.ObservableField;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.thor.app.managers.BleManager;
import com.thor.app.managers.UsersManager;
import com.thor.app.room.ThorDatabase;
import com.thor.app.settings.Settings;
import com.thor.app.utils.converters.JsonConverterKt;
import com.thor.app.utils.converters.mappers.MappersKt;
import com.thor.businessmodule.bluetooth.response.sgu.PlaySguSoundBleResponse;
import com.thor.businessmodule.bluetooth.response.sgu.ReadSguSoundsResponse;
import com.thor.businessmodule.bluetooth.response.sgu.StopSguSoundBleResponse;
import com.thor.networkmodule.model.responses.sgu.SguSound;
import com.thor.networkmodule.model.responses.sgu.SguSoundConfig;
import com.thor.networkmodule.model.responses.sgu.SguSoundFile;
import com.thor.networkmodule.model.responses.sgu.SguSoundJson;
import com.thor.networkmodule.model.responses.sgu.SguSoundJsonWrapper;
import com.thor.networkmodule.model.responses.sgu.SguSoundSet;
import com.thor.networkmodule.model.responses.sgu.SguSoundSetsResponse;
import dagger.hilt.android.qualifiers.ApplicationContext;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import javax.inject.Inject;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.random.Random;
import timber.log.Timber;

/* compiled from: SguSoundsViewModel.kt */
@Metadata(d1 = {"\u0000t\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0003\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0010\n\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B)\b\u0007\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ\u000e\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001dJ\u0010\u0010\u001e\u001a\u00020\u001b2\u0006\u0010\u001f\u001a\u00020 H\u0002J\b\u0010!\u001a\u00020\u001dH\u0002J\u0006\u0010\"\u001a\u00020\u001bJ\u0016\u0010#\u001a\u00020\u001b2\f\u0010$\u001a\b\u0012\u0004\u0012\u00020&0%H\u0002J\u0016\u0010'\u001a\u00020\u001b2\u0006\u0010(\u001a\u00020\u00132\u0006\u0010)\u001a\u00020*J\u0006\u0010+\u001a\u00020\u001bR\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0012\u0010\u000e\u001a\u0004\u0018\u00010\u000fX\u0082\u000e¢\u0006\u0004\n\u0002\u0010\u0010R\u0017\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00130\u0012¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0017\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\r0\u0017¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006,"}, d2 = {"Lcom/thor/app/gui/fragments/presets/sgu/SguSoundsViewModel;", "Landroidx/lifecycle/ViewModel;", "context", "Landroid/content/Context;", "usersManager", "Lcom/thor/app/managers/UsersManager;", "database", "Lcom/thor/app/room/ThorDatabase;", "bleManager", "Lcom/thor/app/managers/BleManager;", "(Landroid/content/Context;Lcom/thor/app/managers/UsersManager;Lcom/thor/app/room/ThorDatabase;Lcom/thor/app/managers/BleManager;)V", "_uiState", "Landroidx/lifecycle/MutableLiveData;", "Lcom/thor/app/gui/fragments/presets/sgu/SguSoundsUIState;", "playingSguSoundId", "", "Ljava/lang/Integer;", "selectedSguSound", "Landroidx/databinding/ObservableField;", "Lcom/thor/networkmodule/model/responses/sgu/SguSound;", "getSelectedSguSound", "()Landroidx/databinding/ObservableField;", "uiState", "Landroidx/lifecycle/LiveData;", "getUiState", "()Landroidx/lifecycle/LiveData;", "changeSoundDriveSelectStatus", "", "state", "", "handleError", "throwable", "", "isDeviceConnected", "loadData", "loadSguSounds", "soundFilesOnDeviceIds", "", "", "playSguSound", "sound", "config", "Lcom/thor/networkmodule/model/responses/sgu/SguSoundConfig;", "stopPlayingSguSound", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class SguSoundsViewModel extends ViewModel {
    private final MutableLiveData<SguSoundsUIState> _uiState;
    private final BleManager bleManager;
    private final ThorDatabase database;
    private Integer playingSguSoundId;
    private final ObservableField<SguSound> selectedSguSound;
    private final LiveData<SguSoundsUIState> uiState;
    private final UsersManager usersManager;

    @Inject
    public SguSoundsViewModel(@ApplicationContext Context context, UsersManager usersManager, ThorDatabase database, BleManager bleManager) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(usersManager, "usersManager");
        Intrinsics.checkNotNullParameter(database, "database");
        Intrinsics.checkNotNullParameter(bleManager, "bleManager");
        this.usersManager = usersManager;
        this.database = database;
        this.bleManager = bleManager;
        MutableLiveData<SguSoundsUIState> mutableLiveData = new MutableLiveData<>();
        this._uiState = mutableLiveData;
        this.uiState = mutableLiveData;
        this.selectedSguSound = new ObservableField<>();
    }

    public final LiveData<SguSoundsUIState> getUiState() {
        return this.uiState;
    }

    public final ObservableField<SguSound> getSelectedSguSound() {
        return this.selectedSguSound;
    }

    public final void loadData() {
        if (isDeviceConnected()) {
            Observable<ReadSguSoundsResponse> observableExecuteReadSguSoundsCommand = this.bleManager.executeReadSguSoundsCommand();
            final Function1<ReadSguSoundsResponse, Unit> function1 = new Function1<ReadSguSoundsResponse, Unit>() { // from class: com.thor.app.gui.fragments.presets.sgu.SguSoundsViewModel.loadData.1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(ReadSguSoundsResponse readSguSoundsResponse) {
                    invoke2(readSguSoundsResponse);
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(ReadSguSoundsResponse readSguSoundsResponse) {
                    if (readSguSoundsResponse.isSuccessful()) {
                        SguSoundsViewModel.this.loadSguSounds(readSguSoundsResponse.getSoundIds());
                    } else {
                        SguSoundsViewModel.this.handleError(new Exception("Can't read SGU sounds from device"));
                    }
                }
            };
            Consumer<? super ReadSguSoundsResponse> consumer = new Consumer() { // from class: com.thor.app.gui.fragments.presets.sgu.SguSoundsViewModel$$ExternalSyntheticLambda0
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    SguSoundsViewModel.loadData$lambda$0(function1, obj);
                }
            };
            final Function1<Throwable, Unit> function12 = new Function1<Throwable, Unit>() { // from class: com.thor.app.gui.fragments.presets.sgu.SguSoundsViewModel.loadData.2
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
                    invoke2(th);
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(Throwable it) {
                    SguSoundsViewModel sguSoundsViewModel = SguSoundsViewModel.this;
                    Intrinsics.checkNotNullExpressionValue(it, "it");
                    sguSoundsViewModel.handleError(it);
                }
            };
            this.bleManager.getMCompositeDisposable().add(observableExecuteReadSguSoundsCommand.subscribe(consumer, new Consumer() { // from class: com.thor.app.gui.fragments.presets.sgu.SguSoundsViewModel$$ExternalSyntheticLambda1
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    SguSoundsViewModel.loadData$lambda$1(function12, obj);
                }
            }));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void loadData$lambda$0(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void loadData$lambda$1(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void loadSguSounds(final List<Short> soundFilesOnDeviceIds) {
        if (soundFilesOnDeviceIds.isEmpty()) {
            this._uiState.postValue(new SguSoundsDataLoaded(CollectionsKt.emptyList()));
            return;
        }
        Observable<SguSoundSetsResponse> observableFetchSoundSguPackages = this.usersManager.fetchSoundSguPackages();
        if (observableFetchSoundSguPackages != null) {
            final Function1<SguSoundSetsResponse, Unit> function1 = new Function1<SguSoundSetsResponse, Unit>() { // from class: com.thor.app.gui.fragments.presets.sgu.SguSoundsViewModel.loadSguSounds.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(SguSoundSetsResponse sguSoundSetsResponse) {
                    invoke2(sguSoundSetsResponse);
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(SguSoundSetsResponse sguSoundSetsResponse) {
                    Object next;
                    SguSound sguSoundModel;
                    Timber.INSTANCE.d(sguSoundSetsResponse.toString(), new Object[0]);
                    if (!sguSoundSetsResponse.isSuccessful()) {
                        SguSoundsViewModel.this._uiState.postValue(new SguSoundsError(sguSoundSetsResponse.getError(), true));
                        return;
                    }
                    LinkedHashSet linkedHashSet = new LinkedHashSet();
                    ArrayList arrayList = new ArrayList();
                    SguSoundJsonWrapper sguSoundList = JsonConverterKt.toSguSoundList(Settings.INSTANCE.getSguSoundFavoritesJSON());
                    if (sguSoundList == null) {
                        sguSoundList = new SguSoundJsonWrapper(new ArrayList());
                    }
                    List<SguSoundSet> soundSetsItems = sguSoundSetsResponse.getSoundSetsItems();
                    if (soundSetsItems != null) {
                        Iterator<T> it = soundSetsItems.iterator();
                        while (it.hasNext()) {
                            Iterator<T> it2 = ((SguSoundSet) it.next()).getFiles().iterator();
                            while (it2.hasNext()) {
                                arrayList.add((SguSound) it2.next());
                            }
                        }
                    }
                    List<SguSoundSet> soundSetsItems2 = sguSoundSetsResponse.getSoundSetsItems();
                    if (soundSetsItems2 != null) {
                        List<Short> list = soundFilesOnDeviceIds;
                        Iterator<T> it3 = soundSetsItems2.iterator();
                        while (it3.hasNext()) {
                            for (SguSound sguSound : ((SguSoundSet) it3.next()).getFiles()) {
                                List<SguSoundFile> soundFiles = sguSound.getSoundFiles();
                                ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(soundFiles, 10));
                                Iterator<T> it4 = soundFiles.iterator();
                                while (it4.hasNext()) {
                                    arrayList2.add(Short.valueOf((short) ((SguSoundFile) it4.next()).getId()));
                                }
                                if (list.containsAll(arrayList2) && (!r8.isEmpty())) {
                                    linkedHashSet.add(sguSound);
                                }
                            }
                        }
                    }
                    LinkedHashSet linkedHashSet2 = new LinkedHashSet();
                    List<SguSoundJson> presets = sguSoundList.getPresets();
                    if (presets != null) {
                        for (SguSoundJson sguSoundJson : presets) {
                            Iterator it5 = linkedHashSet.iterator();
                            while (true) {
                                if (it5.hasNext()) {
                                    next = it5.next();
                                    if (((SguSound) next).getId() == sguSoundJson.getId()) {
                                        break;
                                    }
                                } else {
                                    next = null;
                                    break;
                                }
                            }
                            if (next != null && (sguSoundModel = MappersKt.toSguSoundModel(sguSoundJson)) != null) {
                                linkedHashSet2.add(sguSoundModel);
                            }
                        }
                    }
                    SguSoundsViewModel.this._uiState.postValue(new SguSoundsDataLoaded(CollectionsKt.toList(linkedHashSet2)));
                }
            };
            Consumer<? super SguSoundSetsResponse> consumer = new Consumer() { // from class: com.thor.app.gui.fragments.presets.sgu.SguSoundsViewModel$$ExternalSyntheticLambda6
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    SguSoundsViewModel.loadSguSounds$lambda$3(function1, obj);
                }
            };
            final Function1<Throwable, Unit> function12 = new Function1<Throwable, Unit>() { // from class: com.thor.app.gui.fragments.presets.sgu.SguSoundsViewModel.loadSguSounds.2
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
                    invoke2(th);
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(Throwable it) {
                    SguSoundsViewModel sguSoundsViewModel = SguSoundsViewModel.this;
                    Intrinsics.checkNotNullExpressionValue(it, "it");
                    sguSoundsViewModel.handleError(it);
                }
            };
            Disposable disposableSubscribe = observableFetchSoundSguPackages.subscribe(consumer, new Consumer() { // from class: com.thor.app.gui.fragments.presets.sgu.SguSoundsViewModel$$ExternalSyntheticLambda7
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    SguSoundsViewModel.loadSguSounds$lambda$4(function12, obj);
                }
            });
            if (disposableSubscribe != null) {
                this.usersManager.addDisposable(disposableSubscribe);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void loadSguSounds$lambda$3(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void loadSguSounds$lambda$4(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    public final void changeSoundDriveSelectStatus(boolean state) {
        Object next;
        SguSound sguSound = this.selectedSguSound.get();
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
        while (it.hasNext()) {
            ((SguSoundJson) it.next()).setDriveSelect(false);
        }
        Iterator<T> it2 = list.iterator();
        while (true) {
            if (!it2.hasNext()) {
                next = null;
                break;
            } else {
                next = it2.next();
                if (sguSound != null && ((SguSoundJson) next).getId() == sguSound.getId()) {
                    break;
                }
            }
        }
        SguSoundJson sguSoundJson = (SguSoundJson) next;
        if (sguSoundJson != null) {
            sguSoundJson.setDriveSelect(state);
        }
        Settings.INSTANCE.saveSguSoundFavoritesJSON(JsonConverterKt.toSguSoundJson(new SguSoundJsonWrapper(presets)));
        loadData();
    }

    public final void playSguSound(final SguSound sound, final SguSoundConfig config) {
        Intrinsics.checkNotNullParameter(sound, "sound");
        Intrinsics.checkNotNullParameter(config, "config");
        if (isDeviceConnected()) {
            int id = sound.getId();
            Integer num = this.playingSguSoundId;
            if (num != null && id == num.intValue()) {
                stopPlayingSguSound();
                return;
            }
            if (sound.getSoundFiles().isEmpty()) {
                return;
            }
            Observable<PlaySguSoundBleResponse> observableExecutePlaySguSoundCommand = this.bleManager.executePlaySguSoundCommand((short) ((SguSoundFile) CollectionsKt.random(sound.getSoundFiles(), Random.INSTANCE)).getId(), config);
            final Function1<PlaySguSoundBleResponse, Unit> function1 = new Function1<PlaySguSoundBleResponse, Unit>() { // from class: com.thor.app.gui.fragments.presets.sgu.SguSoundsViewModel.playSguSound.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(PlaySguSoundBleResponse playSguSoundBleResponse) {
                    invoke2(playSguSoundBleResponse);
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(PlaySguSoundBleResponse playSguSoundBleResponse) {
                    if (playSguSoundBleResponse.isSuccessful()) {
                        if (config.getRepeatCycle() != Short.MAX_VALUE) {
                            this.playingSguSoundId = null;
                        } else {
                            this.playingSguSoundId = Integer.valueOf(sound.getId());
                        }
                        Timber.INSTANCE.d("playSguSound successful; sound: " + sound + "; config: " + config, new Object[0]);
                        return;
                    }
                    this.handleError(new Exception("Can't play SGU sound: " + sound));
                }
            };
            Consumer<? super PlaySguSoundBleResponse> consumer = new Consumer() { // from class: com.thor.app.gui.fragments.presets.sgu.SguSoundsViewModel$$ExternalSyntheticLambda4
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    SguSoundsViewModel.playSguSound$lambda$9(function1, obj);
                }
            };
            final Function1<Throwable, Unit> function12 = new Function1<Throwable, Unit>() { // from class: com.thor.app.gui.fragments.presets.sgu.SguSoundsViewModel.playSguSound.2
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
                    invoke2(th);
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(Throwable it) {
                    SguSoundsViewModel sguSoundsViewModel = SguSoundsViewModel.this;
                    Intrinsics.checkNotNullExpressionValue(it, "it");
                    sguSoundsViewModel.handleError(it);
                }
            };
            this.bleManager.getMCompositeDisposable().add(observableExecutePlaySguSoundCommand.subscribe(consumer, new Consumer() { // from class: com.thor.app.gui.fragments.presets.sgu.SguSoundsViewModel$$ExternalSyntheticLambda5
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    SguSoundsViewModel.playSguSound$lambda$10(function12, obj);
                }
            }));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void playSguSound$lambda$9(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void playSguSound$lambda$10(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    public final void stopPlayingSguSound() {
        if (isDeviceConnected()) {
            Observable<StopSguSoundBleResponse> observableExecuteStopSguSoundCommand = this.bleManager.executeStopSguSoundCommand();
            final Function1<StopSguSoundBleResponse, Unit> function1 = new Function1<StopSguSoundBleResponse, Unit>() { // from class: com.thor.app.gui.fragments.presets.sgu.SguSoundsViewModel.stopPlayingSguSound.1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(StopSguSoundBleResponse stopSguSoundBleResponse) {
                    invoke2(stopSguSoundBleResponse);
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(StopSguSoundBleResponse stopSguSoundBleResponse) {
                    if (stopSguSoundBleResponse.isSuccessful()) {
                        SguSoundsViewModel.this.playingSguSoundId = null;
                        Timber.INSTANCE.d("stopPlayingSguSound successful", new Object[0]);
                    } else {
                        SguSoundsViewModel.this.handleError(new Exception("Can't stop playing SGU sound"));
                    }
                }
            };
            Consumer<? super StopSguSoundBleResponse> consumer = new Consumer() { // from class: com.thor.app.gui.fragments.presets.sgu.SguSoundsViewModel$$ExternalSyntheticLambda2
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    SguSoundsViewModel.stopPlayingSguSound$lambda$12(function1, obj);
                }
            };
            final Function1<Throwable, Unit> function12 = new Function1<Throwable, Unit>() { // from class: com.thor.app.gui.fragments.presets.sgu.SguSoundsViewModel.stopPlayingSguSound.2
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
                    invoke2(th);
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(Throwable it) {
                    SguSoundsViewModel sguSoundsViewModel = SguSoundsViewModel.this;
                    Intrinsics.checkNotNullExpressionValue(it, "it");
                    sguSoundsViewModel.handleError(it);
                }
            };
            this.bleManager.getMCompositeDisposable().add(observableExecuteStopSguSoundCommand.subscribe(consumer, new Consumer() { // from class: com.thor.app.gui.fragments.presets.sgu.SguSoundsViewModel$$ExternalSyntheticLambda3
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    SguSoundsViewModel.stopPlayingSguSound$lambda$13(function12, obj);
                }
            }));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void stopPlayingSguSound$lambda$12(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void stopPlayingSguSound$lambda$13(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    private final boolean isDeviceConnected() {
        if (this.bleManager.isBleEnabledAndDeviceConnected()) {
            return true;
        }
        handleError(new RuntimeException("Device not connected!"));
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void handleError(Throwable throwable) {
        DefaultConstructorMarker defaultConstructorMarker = null;
        int i = 2;
        boolean z = false;
        if (!Intrinsics.areEqual(throwable.getMessage(), "HTTP 400") && Intrinsics.areEqual(throwable.getMessage(), "HTTP 500")) {
            this._uiState.postValue(new SguSoundsError(throwable.getMessage(), z, i, defaultConstructorMarker));
        } else {
            this._uiState.postValue(new SguSoundsError(throwable.getMessage(), z, i, defaultConstructorMarker));
        }
        Timber.INSTANCE.e(throwable);
    }
}
