package com.welie.blessed;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattServerCallback;
import android.bluetooth.BluetoothGattService;
import android.os.Build;
import android.os.Handler;
import androidx.core.app.NotificationCompat;
import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: BluetoothPeripheralManager.kt */
@Metadata(d1 = {"\u0000I\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0000*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\u0018\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0002J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0002J\u0010\u0010\f\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0002J(\u0010\r\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J@\u0010\u0011\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00132\u0006\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0015\u001a\u00020\u0005H\u0016J \u0010\u0016\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0017\u001a\u00020\u000f2\u0006\u0010\u0018\u001a\u00020\u000fH\u0016J(\u0010\u0019\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u001a\u001a\u00020\u001bH\u0016J@\u0010\u001c\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00132\u0006\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0015\u001a\u00020\u0005H\u0016J \u0010\u001d\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u001e\u001a\u00020\u0013H\u0016J\u0018\u0010\u001f\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010 \u001a\u00020\u000fH\u0016J\u0018\u0010!\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0017\u001a\u00020\u000fH\u0016J(\u0010\"\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010#\u001a\u00020\u000f2\u0006\u0010$\u001a\u00020\u000f2\u0006\u0010\u0017\u001a\u00020\u000fH\u0016J(\u0010%\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010#\u001a\u00020\u000f2\u0006\u0010$\u001a\u00020\u000f2\u0006\u0010\u0017\u001a\u00020\u000fH\u0016J\u0018\u0010&\u001a\u00020\t2\u0006\u0010\u0017\u001a\u00020\u000f2\u0006\u0010'\u001a\u00020(H\u0016¨\u0006)"}, d2 = {"com/welie/blessed/BluetoothPeripheralManager$bluetoothGattServerCallback$1", "Landroid/bluetooth/BluetoothGattServerCallback;", "checkCccDescriptorValue", "Lcom/welie/blessed/GattStatus;", "safeValue", "", "characteristic", "Landroid/bluetooth/BluetoothGattCharacteristic;", "handleDeviceConnected", "", "device", "Landroid/bluetooth/BluetoothDevice;", "handleDeviceDisconnected", "onCharacteristicReadRequest", "requestId", "", "offset", "onCharacteristicWriteRequest", "preparedWrite", "", "responseNeeded", "value", "onConnectionStateChange", NotificationCompat.CATEGORY_STATUS, "newState", "onDescriptorReadRequest", "descriptor", "Landroid/bluetooth/BluetoothGattDescriptor;", "onDescriptorWriteRequest", "onExecuteWrite", "execute", "onMtuChanged", "mtu", "onNotificationSent", "onPhyRead", "txPhy", "rxPhy", "onPhyUpdate", "onServiceAdded", NotificationCompat.CATEGORY_SERVICE, "Landroid/bluetooth/BluetoothGattService;", "blessed_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class BluetoothPeripheralManager$bluetoothGattServerCallback$1 extends BluetoothGattServerCallback {
    final /* synthetic */ BluetoothPeripheralManager this$0;

    BluetoothPeripheralManager$bluetoothGattServerCallback$1(BluetoothPeripheralManager bluetoothPeripheralManager) {
        this.this$0 = bluetoothPeripheralManager;
    }

    @Override // android.bluetooth.BluetoothGattServerCallback
    public void onConnectionStateChange(BluetoothDevice device, int status, int newState) {
        Intrinsics.checkNotNullParameter(device, "device");
        if (status == 0) {
            if (newState != 0) {
                if (newState == 2 && !this.this$0.connectedCentralsMap.containsKey(device.getAddress())) {
                    this.this$0.bluetoothGattServer.connect(device, false);
                    handleDeviceConnected(device);
                    return;
                }
                return;
            }
            if (this.this$0.connectedCentralsMap.containsKey(device.getAddress())) {
                handleDeviceDisconnected(device);
                return;
            }
            return;
        }
        Logger logger = Logger.INSTANCE;
        String str = BluetoothPeripheralManager.TAG;
        Object[] objArr = new Object[2];
        String name = device.getName();
        if (name == null) {
            name = "null";
        }
        objArr[0] = name;
        objArr[1] = Integer.valueOf(status);
        logger.i(str, "Device '%s' disconnected with status %d", objArr);
        handleDeviceDisconnected(device);
    }

    private final void handleDeviceConnected(BluetoothDevice device) {
        Logger logger = Logger.INSTANCE;
        String str = BluetoothPeripheralManager.TAG;
        Object[] objArr = new Object[2];
        String name = device.getName();
        if (name == null) {
            name = "null";
        }
        objArr[0] = name;
        String address = device.getAddress();
        Intrinsics.checkNotNullExpressionValue(address, "device.address");
        objArr[1] = address;
        logger.i(str, "Central '%s' (%s) connected", objArr);
        final BluetoothCentral bluetoothCentral = new BluetoothCentral(device);
        this.this$0.connectedCentralsMap.put(bluetoothCentral.getAddress(), bluetoothCentral);
        Handler handler = this.this$0.mainHandler;
        final BluetoothPeripheralManager bluetoothPeripheralManager = this.this$0;
        handler.post(new Runnable() { // from class: com.welie.blessed.BluetoothPeripheralManager$bluetoothGattServerCallback$1$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                BluetoothPeripheralManager$bluetoothGattServerCallback$1.handleDeviceConnected$lambda$0(bluetoothPeripheralManager, bluetoothCentral);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void handleDeviceConnected$lambda$0(BluetoothPeripheralManager this$0, BluetoothCentral bluetoothCentral) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(bluetoothCentral, "$bluetoothCentral");
        this$0.callback.onCentralConnected(bluetoothCentral);
    }

    private final void handleDeviceDisconnected(BluetoothDevice device) {
        final BluetoothCentral central = this.this$0.getCentral(device);
        Logger.INSTANCE.i(BluetoothPeripheralManager.TAG, "Central '%s' (%s) disconnected", central.getName(), central.getAddress());
        if (central.getBondState() != BondState.BONDED) {
            this.this$0.removeCentralFromWantingAnything(central);
        }
        Handler handler = this.this$0.mainHandler;
        final BluetoothPeripheralManager bluetoothPeripheralManager = this.this$0;
        handler.post(new Runnable() { // from class: com.welie.blessed.BluetoothPeripheralManager$bluetoothGattServerCallback$1$$ExternalSyntheticLambda8
            @Override // java.lang.Runnable
            public final void run() {
                BluetoothPeripheralManager$bluetoothGattServerCallback$1.handleDeviceDisconnected$lambda$1(bluetoothPeripheralManager, central);
            }
        });
        this.this$0.removeCentral(device);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void handleDeviceDisconnected$lambda$1(BluetoothPeripheralManager this$0, BluetoothCentral bluetoothCentral) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(bluetoothCentral, "$bluetoothCentral");
        this$0.callback.onCentralDisconnected(bluetoothCentral);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onServiceAdded$lambda$2(BluetoothPeripheralManager this$0, int i, BluetoothGattService service) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(service, "$service");
        this$0.callback.onServiceAdded(GattStatus.INSTANCE.fromValue(i), service);
    }

    @Override // android.bluetooth.BluetoothGattServerCallback
    public void onServiceAdded(final int status, final BluetoothGattService service) {
        Intrinsics.checkNotNullParameter(service, "service");
        Handler handler = this.this$0.mainHandler;
        final BluetoothPeripheralManager bluetoothPeripheralManager = this.this$0;
        handler.post(new Runnable() { // from class: com.welie.blessed.BluetoothPeripheralManager$bluetoothGattServerCallback$1$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                BluetoothPeripheralManager$bluetoothGattServerCallback$1.onServiceAdded$lambda$2(bluetoothPeripheralManager, status, service);
            }
        });
        this.this$0.completedCommand();
    }

    @Override // android.bluetooth.BluetoothGattServerCallback
    public void onCharacteristicReadRequest(final BluetoothDevice device, final int requestId, final int offset, final BluetoothGattCharacteristic characteristic) {
        Intrinsics.checkNotNullParameter(device, "device");
        Intrinsics.checkNotNullParameter(characteristic, "characteristic");
        Logger logger = Logger.INSTANCE;
        String str = BluetoothPeripheralManager.TAG;
        UUID uuid = characteristic.getUuid();
        Intrinsics.checkNotNullExpressionValue(uuid, "characteristic.uuid");
        logger.i(str, "read request for characteristic <%s> with offset %d", uuid, Integer.valueOf(offset));
        final BluetoothCentral central = this.this$0.getCentral(device);
        Handler handler = this.this$0.mainHandler;
        final BluetoothPeripheralManager bluetoothPeripheralManager = this.this$0;
        handler.post(new Runnable() { // from class: com.welie.blessed.BluetoothPeripheralManager$bluetoothGattServerCallback$1$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                BluetoothPeripheralManager$bluetoothGattServerCallback$1.onCharacteristicReadRequest$lambda$3(offset, bluetoothPeripheralManager, central, characteristic, device, requestId);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCharacteristicReadRequest$lambda$3(int i, BluetoothPeripheralManager this$0, BluetoothCentral bluetoothCentral, BluetoothGattCharacteristic characteristic, BluetoothDevice device, int i2) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(bluetoothCentral, "$bluetoothCentral");
        Intrinsics.checkNotNullParameter(characteristic, "$characteristic");
        Intrinsics.checkNotNullParameter(device, "$device");
        GattStatus status = GattStatus.SUCCESS;
        if (i == 0) {
            ReadResponse readResponseOnCharacteristicRead = this$0.callback.onCharacteristicRead(bluetoothCentral, characteristic);
            status = readResponseOnCharacteristicRead.getStatus();
            this$0.currentReadValue = readResponseOnCharacteristicRead.getValue();
        }
        if (i < this$0.currentReadValue.length) {
            this$0.bluetoothGattServer.sendResponse(device, i2, status.getValue(), i, this$0.chopValue(this$0.currentReadValue, i));
        } else {
            this$0.bluetoothGattServer.sendResponse(device, i2, GattStatus.INVALID_OFFSET.getValue(), i, new byte[0]);
        }
    }

    @Override // android.bluetooth.BluetoothGattServerCallback
    public void onCharacteristicWriteRequest(final BluetoothDevice device, final int requestId, final BluetoothGattCharacteristic characteristic, final boolean preparedWrite, final boolean responseNeeded, final int offset, byte[] value) {
        Intrinsics.checkNotNullParameter(device, "device");
        Intrinsics.checkNotNullParameter(characteristic, "characteristic");
        Intrinsics.checkNotNullParameter(value, "value");
        Logger logger = Logger.INSTANCE;
        String str = BluetoothPeripheralManager.TAG;
        Object[] objArr = new Object[4];
        objArr[0] = responseNeeded ? "WITH_RESPONSE" : "WITHOUT_RESPONSE";
        objArr[1] = BluetoothBytesParser.INSTANCE.bytes2String(value);
        objArr[2] = Integer.valueOf(offset);
        UUID uuid = characteristic.getUuid();
        Intrinsics.checkNotNullExpressionValue(uuid, "characteristic.uuid");
        objArr[3] = uuid;
        logger.i(str, "write characteristic %s request <%s> offset %d for <%s>", objArr);
        final byte[] bArrNonnullOf = this.this$0.nonnullOf(value);
        final BluetoothCentral central = this.this$0.getCentral(device);
        Handler handler = this.this$0.mainHandler;
        final BluetoothPeripheralManager bluetoothPeripheralManager = this.this$0;
        handler.post(new Runnable() { // from class: com.welie.blessed.BluetoothPeripheralManager$bluetoothGattServerCallback$1$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                BluetoothPeripheralManager$bluetoothGattServerCallback$1.onCharacteristicWriteRequest$lambda$4(preparedWrite, bluetoothPeripheralManager, central, characteristic, bArrNonnullOf, offset, responseNeeded, device, requestId);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCharacteristicWriteRequest$lambda$4(boolean z, BluetoothPeripheralManager this$0, BluetoothCentral bluetoothCentral, BluetoothGattCharacteristic characteristic, byte[] safeValue, int i, boolean z2, BluetoothDevice device, int i2) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(bluetoothCentral, "$bluetoothCentral");
        Intrinsics.checkNotNullParameter(characteristic, "$characteristic");
        Intrinsics.checkNotNullParameter(safeValue, "$safeValue");
        Intrinsics.checkNotNullParameter(device, "$device");
        GattStatus gattStatusOnCharacteristicWrite = GattStatus.SUCCESS;
        if (!z) {
            gattStatusOnCharacteristicWrite = this$0.callback.onCharacteristicWrite(bluetoothCentral, characteristic, safeValue);
            if (gattStatusOnCharacteristicWrite == GattStatus.SUCCESS && Build.VERSION.SDK_INT < 33) {
                characteristic.setValue(safeValue);
            }
        } else if (i == 0) {
            this$0.writeLongCharacteristicTemporaryBytes.put(characteristic, safeValue);
        } else {
            byte[] bArr = (byte[]) this$0.writeLongCharacteristicTemporaryBytes.get(characteristic);
            if (bArr != null && i == bArr.length) {
                this$0.writeLongCharacteristicTemporaryBytes.put(characteristic, BluetoothBytesParser.INSTANCE.mergeArrays(bArr, safeValue));
            } else {
                gattStatusOnCharacteristicWrite = GattStatus.INVALID_OFFSET;
            }
        }
        if (z2) {
            this$0.bluetoothGattServer.sendResponse(device, i2, gattStatusOnCharacteristicWrite.getValue(), i, safeValue);
        }
        if (gattStatusOnCharacteristicWrite != GattStatus.SUCCESS || z) {
            return;
        }
        this$0.callback.onCharacteristicWriteCompleted(bluetoothCentral, characteristic, safeValue);
    }

    @Override // android.bluetooth.BluetoothGattServerCallback
    public void onDescriptorReadRequest(final BluetoothDevice device, final int requestId, final int offset, final BluetoothGattDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(device, "device");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Logger logger = Logger.INSTANCE;
        String str = BluetoothPeripheralManager.TAG;
        UUID uuid = descriptor.getUuid();
        Intrinsics.checkNotNullExpressionValue(uuid, "descriptor.uuid");
        logger.i(str, "read request for descriptor <%s> with offset %d", uuid, Integer.valueOf(offset));
        final BluetoothCentral central = this.this$0.getCentral(device);
        Handler handler = this.this$0.mainHandler;
        final BluetoothPeripheralManager bluetoothPeripheralManager = this.this$0;
        handler.post(new Runnable() { // from class: com.welie.blessed.BluetoothPeripheralManager$bluetoothGattServerCallback$1$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                BluetoothPeripheralManager$bluetoothGattServerCallback$1.onDescriptorReadRequest$lambda$5(offset, bluetoothPeripheralManager, central, descriptor, device, requestId);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onDescriptorReadRequest$lambda$5(int i, BluetoothPeripheralManager this$0, BluetoothCentral bluetoothCentral, BluetoothGattDescriptor descriptor, BluetoothDevice device, int i2) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(bluetoothCentral, "$bluetoothCentral");
        Intrinsics.checkNotNullParameter(descriptor, "$descriptor");
        Intrinsics.checkNotNullParameter(device, "$device");
        GattStatus status = GattStatus.SUCCESS;
        if (i == 0) {
            ReadResponse readResponseOnDescriptorRead = this$0.callback.onDescriptorRead(bluetoothCentral, descriptor);
            status = readResponseOnDescriptorRead.getStatus();
            this$0.currentReadValue = readResponseOnDescriptorRead.getValue();
        }
        this$0.bluetoothGattServer.sendResponse(device, i2, status.getValue(), i, this$0.chopValue(this$0.currentReadValue, i));
    }

    @Override // android.bluetooth.BluetoothGattServerCallback
    public void onDescriptorWriteRequest(final BluetoothDevice device, final int requestId, final BluetoothGattDescriptor descriptor, final boolean preparedWrite, final boolean responseNeeded, final int offset, byte[] value) {
        Intrinsics.checkNotNullParameter(device, "device");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(value, "value");
        final byte[] bArrNonnullOf = this.this$0.nonnullOf(value);
        final BluetoothGattCharacteristic bluetoothGattCharacteristic = (BluetoothGattCharacteristic) Objects.requireNonNull(descriptor.getCharacteristic(), "Descriptor does not have characteristic");
        Logger logger = Logger.INSTANCE;
        String str = BluetoothPeripheralManager.TAG;
        Object[] objArr = new Object[4];
        objArr[0] = responseNeeded ? "WITH_RESPONSE" : "WITHOUT_RESPONSE";
        objArr[1] = BluetoothBytesParser.INSTANCE.bytes2String(value);
        objArr[2] = Integer.valueOf(offset);
        UUID uuid = descriptor.getUuid();
        Intrinsics.checkNotNullExpressionValue(uuid, "descriptor.uuid");
        objArr[3] = uuid;
        logger.i(str, "write descriptor %s request <%s> offset %d for <%s>", objArr);
        final BluetoothCentral central = this.this$0.getCentral(device);
        Handler handler = this.this$0.mainHandler;
        final BluetoothPeripheralManager bluetoothPeripheralManager = this.this$0;
        handler.post(new Runnable() { // from class: com.welie.blessed.BluetoothPeripheralManager$bluetoothGattServerCallback$1$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                BluetoothPeripheralManager$bluetoothGattServerCallback$1.onDescriptorWriteRequest$lambda$6(descriptor, this, bArrNonnullOf, bluetoothGattCharacteristic, preparedWrite, bluetoothPeripheralManager, central, offset, responseNeeded, device, requestId);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onDescriptorWriteRequest$lambda$6(BluetoothGattDescriptor descriptor, BluetoothPeripheralManager$bluetoothGattServerCallback$1 this$0, byte[] safeValue, BluetoothGattCharacteristic characteristic, boolean z, BluetoothPeripheralManager this$1, BluetoothCentral bluetoothCentral, int i, boolean z2, BluetoothDevice device, int i2) {
        GattStatus gattStatus;
        Intrinsics.checkNotNullParameter(descriptor, "$descriptor");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(safeValue, "$safeValue");
        Intrinsics.checkNotNullParameter(this$1, "this$1");
        Intrinsics.checkNotNullParameter(bluetoothCentral, "$bluetoothCentral");
        Intrinsics.checkNotNullParameter(device, "$device");
        GattStatus gattStatusOnDescriptorWrite = GattStatus.SUCCESS;
        if (Intrinsics.areEqual(descriptor.getUuid(), BluetoothPeripheralManager.INSTANCE.getCCC_DESCRIPTOR_UUID())) {
            Intrinsics.checkNotNullExpressionValue(characteristic, "characteristic");
            gattStatusOnDescriptorWrite = this$0.checkCccDescriptorValue(safeValue, characteristic);
        } else if (!z) {
            gattStatusOnDescriptorWrite = this$1.callback.onDescriptorWrite(bluetoothCentral, descriptor, safeValue);
        } else if (i == 0) {
            this$1.writeLongDescriptorTemporaryBytes.put(descriptor, safeValue);
        } else {
            byte[] bArr = (byte[]) this$1.writeLongDescriptorTemporaryBytes.get(descriptor);
            if (bArr != null && i == bArr.length) {
                this$1.writeLongDescriptorTemporaryBytes.put(descriptor, BluetoothBytesParser.INSTANCE.mergeArrays(bArr, safeValue));
            } else {
                gattStatusOnDescriptorWrite = GattStatus.INVALID_OFFSET;
            }
        }
        GattStatus gattStatus2 = gattStatusOnDescriptorWrite;
        if (gattStatus2 == GattStatus.SUCCESS && !z && Build.VERSION.SDK_INT < 33) {
            descriptor.setValue(safeValue);
        }
        if (z2) {
            gattStatus = gattStatus2;
            this$1.bluetoothGattServer.sendResponse(device, i2, gattStatus2.getValue(), i, safeValue);
        } else {
            gattStatus = gattStatus2;
        }
        if (z2) {
            this$1.bluetoothGattServer.sendResponse(device, i2, gattStatus.getValue(), i, safeValue);
        }
        GattStatus gattStatus3 = gattStatus;
        if (gattStatus3 == GattStatus.SUCCESS && Intrinsics.areEqual(descriptor.getUuid(), BluetoothPeripheralManager.INSTANCE.getCCC_DESCRIPTOR_UUID())) {
            if (Arrays.equals(safeValue, BluetoothGattDescriptor.ENABLE_INDICATION_VALUE) || Arrays.equals(safeValue, BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE)) {
                if (Arrays.equals(safeValue, BluetoothGattDescriptor.ENABLE_INDICATION_VALUE)) {
                    Intrinsics.checkNotNullExpressionValue(characteristic, "characteristic");
                    this$1.addCentralWantingIndications(characteristic, bluetoothCentral);
                } else {
                    Intrinsics.checkNotNullExpressionValue(characteristic, "characteristic");
                    this$1.addCentralWantingNotifications(characteristic, bluetoothCentral);
                }
                Logger logger = Logger.INSTANCE;
                String str = BluetoothPeripheralManager.TAG;
                UUID uuid = characteristic.getUuid();
                Intrinsics.checkNotNullExpressionValue(uuid, "characteristic.uuid");
                logger.i(str, "notifying enabled for <%s>", uuid);
                this$1.callback.onNotifyingEnabled(bluetoothCentral, characteristic);
                return;
            }
            Logger logger2 = Logger.INSTANCE;
            String str2 = BluetoothPeripheralManager.TAG;
            UUID uuid2 = characteristic.getUuid();
            Intrinsics.checkNotNullExpressionValue(uuid2, "characteristic.uuid");
            logger2.i(str2, "notifying disabled for <%s>", uuid2);
            Intrinsics.checkNotNullExpressionValue(characteristic, "characteristic");
            this$1.removeCentralWantingIndications(characteristic, bluetoothCentral);
            this$1.removeCentralWantingNotifications(characteristic, bluetoothCentral);
            this$1.callback.onNotifyingDisabled(bluetoothCentral, characteristic);
            return;
        }
        if (gattStatus3 != GattStatus.SUCCESS || z) {
            return;
        }
        this$1.callback.onDescriptorWriteCompleted(bluetoothCentral, descriptor, safeValue);
    }

    private final GattStatus checkCccDescriptorValue(byte[] safeValue, BluetoothGattCharacteristic characteristic) {
        GattStatus gattStatus = GattStatus.SUCCESS;
        if (safeValue.length != 2) {
            return GattStatus.INVALID_ATTRIBUTE_VALUE_LENGTH;
        }
        if (Arrays.equals(safeValue, BluetoothGattDescriptor.ENABLE_INDICATION_VALUE) || Arrays.equals(safeValue, BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE) || Arrays.equals(safeValue, BluetoothGattDescriptor.DISABLE_NOTIFICATION_VALUE)) {
            if (this.this$0.supportsIndicate(characteristic) || !Arrays.equals(safeValue, BluetoothGattDescriptor.ENABLE_INDICATION_VALUE)) {
                return (this.this$0.supportsNotify(characteristic) || !Arrays.equals(safeValue, BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE)) ? gattStatus : GattStatus.REQUEST_NOT_SUPPORTED;
            }
            return GattStatus.REQUEST_NOT_SUPPORTED;
        }
        return GattStatus.VALUE_NOT_ALLOWED;
    }

    @Override // android.bluetooth.BluetoothGattServerCallback
    public void onExecuteWrite(final BluetoothDevice device, final int requestId, boolean execute) {
        Intrinsics.checkNotNullParameter(device, "device");
        final BluetoothCentral central = this.this$0.getCentral(device);
        if (execute) {
            Handler handler = this.this$0.mainHandler;
            final BluetoothPeripheralManager bluetoothPeripheralManager = this.this$0;
            handler.post(new Runnable() { // from class: com.welie.blessed.BluetoothPeripheralManager$bluetoothGattServerCallback$1$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    BluetoothPeripheralManager$bluetoothGattServerCallback$1.onExecuteWrite$lambda$7(bluetoothPeripheralManager, central, device, requestId);
                }
            });
        } else {
            this.this$0.writeLongCharacteristicTemporaryBytes.clear();
            this.this$0.writeLongDescriptorTemporaryBytes.clear();
            this.this$0.bluetoothGattServer.sendResponse(device, requestId, GattStatus.SUCCESS.getValue(), 0, null);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onExecuteWrite$lambda$7(BluetoothPeripheralManager this$0, BluetoothCentral bluetoothCentral, BluetoothDevice device, int i) {
        GattStatus gattStatusOnDescriptorWrite;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(bluetoothCentral, "$bluetoothCentral");
        Intrinsics.checkNotNullParameter(device, "$device");
        GattStatus gattStatus = GattStatus.SUCCESS;
        if (!this$0.writeLongCharacteristicTemporaryBytes.isEmpty()) {
            Object next = this$0.writeLongCharacteristicTemporaryBytes.keySet().iterator().next();
            Intrinsics.checkNotNullExpressionValue(next, "writeLongCharacteristicT…es.keys.iterator().next()");
            BluetoothGattCharacteristic bluetoothGattCharacteristic = (BluetoothGattCharacteristic) next;
            BluetoothPeripheralManagerCallback bluetoothPeripheralManagerCallback = this$0.callback;
            Object obj = this$0.writeLongCharacteristicTemporaryBytes.get(bluetoothGattCharacteristic);
            Intrinsics.checkNotNull(obj);
            gattStatusOnDescriptorWrite = bluetoothPeripheralManagerCallback.onCharacteristicWrite(bluetoothCentral, bluetoothGattCharacteristic, (byte[]) obj);
            if (gattStatusOnDescriptorWrite == GattStatus.SUCCESS) {
                byte[] bArr = (byte[]) this$0.writeLongCharacteristicTemporaryBytes.get(bluetoothGattCharacteristic);
                if (Build.VERSION.SDK_INT < 33) {
                    bluetoothGattCharacteristic.setValue(bArr);
                }
                this$0.writeLongCharacteristicTemporaryBytes.clear();
                BluetoothPeripheralManagerCallback bluetoothPeripheralManagerCallback2 = this$0.callback;
                Intrinsics.checkNotNull(bArr);
                bluetoothPeripheralManagerCallback2.onCharacteristicWriteCompleted(bluetoothCentral, bluetoothGattCharacteristic, bArr);
            }
        } else {
            if (!this$0.writeLongDescriptorTemporaryBytes.isEmpty()) {
                Object next2 = this$0.writeLongDescriptorTemporaryBytes.keySet().iterator().next();
                Intrinsics.checkNotNullExpressionValue(next2, "writeLongDescriptorTempo…es.keys.iterator().next()");
                BluetoothGattDescriptor bluetoothGattDescriptor = (BluetoothGattDescriptor) next2;
                BluetoothPeripheralManagerCallback bluetoothPeripheralManagerCallback3 = this$0.callback;
                Object obj2 = this$0.writeLongDescriptorTemporaryBytes.get(bluetoothGattDescriptor);
                Intrinsics.checkNotNull(obj2);
                gattStatusOnDescriptorWrite = bluetoothPeripheralManagerCallback3.onDescriptorWrite(bluetoothCentral, bluetoothGattDescriptor, (byte[]) obj2);
                if (gattStatusOnDescriptorWrite == GattStatus.SUCCESS) {
                    byte[] bArr2 = (byte[]) this$0.writeLongDescriptorTemporaryBytes.get(bluetoothGattDescriptor);
                    if (Build.VERSION.SDK_INT < 33) {
                        bluetoothGattDescriptor.setValue(bArr2);
                    }
                    this$0.writeLongDescriptorTemporaryBytes.clear();
                    BluetoothPeripheralManagerCallback bluetoothPeripheralManagerCallback4 = this$0.callback;
                    Intrinsics.checkNotNull(bArr2);
                    bluetoothPeripheralManagerCallback4.onDescriptorWriteCompleted(bluetoothCentral, bluetoothGattDescriptor, bArr2);
                }
            }
            this$0.bluetoothGattServer.sendResponse(device, i, gattStatus.getValue(), 0, null);
        }
        gattStatus = gattStatusOnDescriptorWrite;
        this$0.bluetoothGattServer.sendResponse(device, i, gattStatus.getValue(), 0, null);
    }

    @Override // android.bluetooth.BluetoothGattServerCallback
    public void onNotificationSent(BluetoothDevice device, final int status) {
        Intrinsics.checkNotNullParameter(device, "device");
        final BluetoothCentral central = this.this$0.getCentral(device);
        final BluetoothGattCharacteristic bluetoothGattCharacteristic = this.this$0.currentNotifyCharacteristic;
        if (bluetoothGattCharacteristic != null) {
            final byte[] bArr = this.this$0.currentNotifyValue;
            this.this$0.currentNotifyValue = new byte[0];
            Handler handler = this.this$0.mainHandler;
            final BluetoothPeripheralManager bluetoothPeripheralManager = this.this$0;
            handler.post(new Runnable() { // from class: com.welie.blessed.BluetoothPeripheralManager$bluetoothGattServerCallback$1$$ExternalSyntheticLambda7
                @Override // java.lang.Runnable
                public final void run() {
                    BluetoothPeripheralManager$bluetoothGattServerCallback$1.onNotificationSent$lambda$8(bluetoothPeripheralManager, central, bArr, bluetoothGattCharacteristic, status);
                }
            });
            this.this$0.completedCommand();
            return;
        }
        throw new IllegalArgumentException("Required value was null.".toString());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onNotificationSent$lambda$8(BluetoothPeripheralManager this$0, BluetoothCentral bluetoothCentral, byte[] value, BluetoothGattCharacteristic characteristic, int i) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(bluetoothCentral, "$bluetoothCentral");
        Intrinsics.checkNotNullParameter(value, "$value");
        Intrinsics.checkNotNullParameter(characteristic, "$characteristic");
        this$0.callback.onNotificationSent(bluetoothCentral, value, characteristic, GattStatus.INSTANCE.fromValue(i));
    }

    @Override // android.bluetooth.BluetoothGattServerCallback
    public void onMtuChanged(BluetoothDevice device, int mtu) {
        Intrinsics.checkNotNullParameter(device, "device");
        Logger.INSTANCE.i(BluetoothPeripheralManager.TAG, "new MTU: %d", Integer.valueOf(mtu));
        this.this$0.getCentral(device).setCurrentMtu(mtu);
    }

    @Override // android.bluetooth.BluetoothGattServerCallback
    public void onPhyUpdate(BluetoothDevice device, int txPhy, int rxPhy, int status) {
        Intrinsics.checkNotNullParameter(device, "device");
        super.onPhyUpdate(device, txPhy, rxPhy, status);
    }

    @Override // android.bluetooth.BluetoothGattServerCallback
    public void onPhyRead(BluetoothDevice device, int txPhy, int rxPhy, int status) {
        Intrinsics.checkNotNullParameter(device, "device");
        super.onPhyRead(device, txPhy, rxPhy, status);
    }
}
