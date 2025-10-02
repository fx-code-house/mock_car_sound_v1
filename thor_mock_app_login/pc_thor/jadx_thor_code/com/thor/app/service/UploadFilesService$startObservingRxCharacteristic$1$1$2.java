package com.thor.app.service;

import android.bluetooth.BluetoothGattCharacteristic;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.thor.app.service.models.UploadFileGroupModel;
import com.thor.app.service.models.UploadFileStatusModel;
import com.thor.app.service.models.response.InstallPresetServiceResponse;
import com.thor.app.service.models.response.ServiceHardwareResponse;
import com.thor.app.service.state.UploadServiceState;
import com.thor.businessmodule.bluetooth.request.other.ActivatePresetBleRequest;
import com.thor.businessmodule.bluetooth.util.BleHelperKt;
import com.welie.blessed.BluetoothPeripheral;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt__BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.DelayKt;

/* compiled from: UploadFilesService.kt */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 8, 0}, xi = 48)
@DebugMetadata(c = "com.thor.app.service.UploadFilesService$startObservingRxCharacteristic$1$1$2", f = "UploadFilesService.kt", i = {}, l = {455}, m = "invokeSuspend", n = {}, s = {})
/* loaded from: classes3.dex */
final class UploadFilesService$startObservingRxCharacteristic$1$1$2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    final /* synthetic */ BluetoothGattCharacteristic $characteristic;
    final /* synthetic */ BluetoothPeripheral $peripheral;
    int label;
    final /* synthetic */ UploadFilesService this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    UploadFilesService$startObservingRxCharacteristic$1$1$2(BluetoothPeripheral bluetoothPeripheral, BluetoothGattCharacteristic bluetoothGattCharacteristic, UploadFilesService uploadFilesService, Continuation<? super UploadFilesService$startObservingRxCharacteristic$1$1$2> continuation) {
        super(2, continuation);
        this.$peripheral = bluetoothPeripheral;
        this.$characteristic = bluetoothGattCharacteristic;
        this.this$0 = uploadFilesService;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new UploadFilesService$startObservingRxCharacteristic$1$1$2(this.$peripheral, this.$characteristic, this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((UploadFilesService$startObservingRxCharacteristic$1$1$2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            BluetoothPeripheral bluetoothPeripheral = this.$peripheral;
            BluetoothGattCharacteristic bluetoothGattCharacteristic = this.$characteristic;
            final UploadFilesService uploadFilesService = this.this$0;
            this.label = 1;
            if (bluetoothPeripheral.observe(bluetoothGattCharacteristic, (Function1) new Function1<byte[], Unit>() { // from class: com.thor.app.service.UploadFilesService$startObservingRxCharacteristic$1$1$2.1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(byte[] bArr) {
                    invoke2(bArr);
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(byte[] result) {
                    Intrinsics.checkNotNullParameter(result, "result");
                    try {
                        if (result.length <= 2) {
                            return;
                        }
                        CoroutineScope coroutineScope = uploadFilesService.timerScope;
                        UploadFileGroupModel uploadFileGroupModel = null;
                        boolean z = true;
                        if (coroutineScope != null) {
                            CoroutineScopeKt.cancel$default(coroutineScope, null, 1, null);
                        }
                        BleParseResponse bleParseResponse = new BleParseResponse(result, uploadFilesService.isNordic);
                        Short inputCommand = bleParseResponse.getInputCommand();
                        if (!(inputCommand != null && inputCommand.shortValue() == 114)) {
                            uploadFilesService.getFileLogger().i("SERVICE: Result from board " + BleHelperKt.toHex(bleParseResponse.getResultBytes()), new Object[0]);
                        }
                        uploadFilesService.isBordAnswer = true;
                        if (uploadFilesService.isStatusCancel) {
                            ActivatePresetBleRequest activatePresetBleRequest = new ActivatePresetBleRequest(uploadFilesService.getMBleOldManager().getMActivatedPresetIndex());
                            uploadFilesService.writeDataToCharacteristic(activatePresetBleRequest.getBytes(true), activatePresetBleRequest);
                            BuildersKt__BuildersKt.runBlocking$default(null, new C00811(uploadFilesService, null), 1, null);
                        }
                        Short inputCommand2 = bleParseResponse.getInputCommand();
                        if (inputCommand2 != null && inputCommand2.shortValue() == 1) {
                            uploadFilesService.hardwareProfile = new ServiceHardwareResponse(bleParseResponse.getResultBytes()).getHardwareProfile();
                            uploadFilesService.startHandShake();
                            return;
                        }
                        if (inputCommand2 != null && inputCommand2.shortValue() == 117) {
                            uploadFilesService.tryReUploadFiles(new UploadFileStatusModel(bleParseResponse.getResultBytes()).getUploadStatusModel());
                            return;
                        }
                        if (inputCommand2 != null && inputCommand2.shortValue() == 112) {
                            uploadFilesService.calculateUploadingFile();
                            return;
                        }
                        if (inputCommand2 != null && inputCommand2.shortValue() == 113) {
                            uploadFilesService.uploadWriteBlockFile();
                            return;
                        }
                        if (inputCommand2 != null && inputCommand2.shortValue() == 114) {
                            if (!uploadFilesService.uploadingFile.isEmpty()) {
                                uploadFilesService.uploadWriteBlockFile();
                                return;
                            } else {
                                uploadFilesService.uploadCommitFile();
                                return;
                            }
                        }
                        if (inputCommand2 != null && inputCommand2.shortValue() == 115) {
                            UploadFileGroupModel uploadFileGroupModel2 = uploadFilesService.uploadingFileGroupModel;
                            if (uploadFileGroupModel2 == null) {
                                Intrinsics.throwUninitializedPropertyAccessException("uploadingFileGroupModel");
                            } else {
                                uploadFileGroupModel = uploadFileGroupModel2;
                            }
                            if (!uploadFileGroupModel.getUploadListFiles().isEmpty()) {
                                uploadFilesService.calculateUploadingFile();
                                return;
                            } else {
                                uploadFilesService.uploadCommitGroupFiles();
                                return;
                            }
                        }
                        if (inputCommand2 != null && inputCommand2.shortValue() == 116) {
                            uploadFilesService.startPolingStatus();
                            return;
                        }
                        if (inputCommand2 != null && inputCommand2.shortValue() == 49) {
                            uploadFilesService.controlInstallPresets(new InstallPresetServiceResponse(bleParseResponse.getResultBytes()).getInstalledPresets());
                            return;
                        }
                        if (inputCommand2 != null && inputCommand2.shortValue() == 48) {
                            ActivatePresetBleRequest activatePresetBleRequest2 = new ActivatePresetBleRequest(uploadFilesService.getMBleOldManager().getMActivatedPresetIndex());
                            uploadFilesService.writeDataToCharacteristic(activatePresetBleRequest2.getBytes(true), activatePresetBleRequest2);
                            return;
                        }
                        if (inputCommand2 == null || inputCommand2.shortValue() != 69) {
                            z = false;
                        }
                        if (z) {
                            if (uploadFilesService.isDeActivePreset) {
                                uploadFilesService.isDeActivePreset = false;
                                uploadFilesService.getStatusUploadFiles();
                                return;
                            } else {
                                uploadFilesService.stop(UploadServiceState.Stop.Success.INSTANCE);
                                return;
                            }
                        }
                        if (bleParseResponse.getResultBytes().length == 8) {
                            uploadFilesService.initHandShake(bleParseResponse.getResultBytes());
                        } else if (Intrinsics.areEqual(BleHelperKt.toHex(bleParseResponse.getResultBytes()), "80450001")) {
                            uploadFilesService.stop(UploadServiceState.Stop.Success.INSTANCE);
                        } else {
                            uploadFilesService.stop(new UploadServiceState.Stop.Error("Error parse response " + BleHelperKt.toHex(bleParseResponse.getResultBytes())));
                        }
                    } catch (Exception e) {
                        uploadFilesService.getFileLogger().i("SERVICE: Error " + e.getMessage(), new Object[0]);
                        uploadFilesService.stop(new UploadServiceState.Stop.Error("Cancel"));
                    }
                }

                /* compiled from: UploadFilesService.kt */
                @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 8, 0}, xi = 48)
                @DebugMetadata(c = "com.thor.app.service.UploadFilesService$startObservingRxCharacteristic$1$1$2$1$1", f = "UploadFilesService.kt", i = {}, l = {473}, m = "invokeSuspend", n = {}, s = {})
                /* renamed from: com.thor.app.service.UploadFilesService$startObservingRxCharacteristic$1$1$2$1$1, reason: invalid class name and collision with other inner class name */
                static final class C00811 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
                    int label;
                    final /* synthetic */ UploadFilesService this$0;

                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    C00811(UploadFilesService uploadFilesService, Continuation<? super C00811> continuation) {
                        super(2, continuation);
                        this.this$0 = uploadFilesService;
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                        return new C00811(this.this$0, continuation);
                    }

                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                        return ((C00811) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Object invokeSuspend(Object obj) {
                        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                        int i = this.label;
                        if (i == 0) {
                            ResultKt.throwOnFailure(obj);
                            this.label = 1;
                            if (DelayKt.delay(SimpleExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS, this) == coroutine_suspended) {
                                return coroutine_suspended;
                            }
                        } else {
                            if (i != 1) {
                                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                            }
                            ResultKt.throwOnFailure(obj);
                        }
                        this.this$0.clearResources();
                        this.this$0.stop(UploadServiceState.Stop.Cancel.INSTANCE);
                        return Unit.INSTANCE;
                    }
                }
            }, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
    }
}
