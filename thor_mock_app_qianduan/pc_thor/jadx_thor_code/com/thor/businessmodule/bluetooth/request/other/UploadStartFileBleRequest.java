package com.thor.businessmodule.bluetooth.request.other;

import com.thor.businessmodule.bluetooth.model.other.FileType;
import com.thor.businessmodule.bluetooth.util.BleCommands;
import com.thor.businessmodule.bluetooth.util.BleHelper;
import java.util.ArrayList;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: UploadStartFileBleRequest.kt */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\n\n\u0002\b\u0002\n\u0002\u0010\u0012\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\b\u0010\u0011\u001a\u00020\u0006H\u0016R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000f¨\u0006\u0012"}, d2 = {"Lcom/thor/businessmodule/bluetooth/request/other/UploadStartFileBleRequest;", "Lcom/thor/businessmodule/bluetooth/request/other/BaseBleRequest;", "packageId", "", "versionId", "file", "", "idFileType", "Lcom/thor/businessmodule/bluetooth/model/other/FileType;", "(SS[BLcom/thor/businessmodule/bluetooth/model/other/FileType;)V", "getFile", "()[B", "getIdFileType", "()Lcom/thor/businessmodule/bluetooth/model/other/FileType;", "getPackageId", "()S", "getVersionId", "getBody", "businessmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class UploadStartFileBleRequest extends BaseBleRequest {
    private final byte[] file;
    private final FileType idFileType;
    private final short packageId;
    private final short versionId;

    public final short getPackageId() {
        return this.packageId;
    }

    public final short getVersionId() {
        return this.versionId;
    }

    public final byte[] getFile() {
        return this.file;
    }

    public final FileType getIdFileType() {
        return this.idFileType;
    }

    public UploadStartFileBleRequest(short s, short s2, byte[] file, FileType idFileType) {
        Intrinsics.checkNotNullParameter(file, "file");
        Intrinsics.checkNotNullParameter(idFileType, "idFileType");
        this.packageId = s;
        this.versionId = s2;
        this.file = file;
        this.idFileType = idFileType;
        setCommand(Short.valueOf(BleCommands.COMMAND_DOWNLOAD_START_FILE));
    }

    @Override // com.thor.businessmodule.bluetooth.request.other.BaseBleRequest, com.thor.businessmodule.bluetooth.request.other.BleRequest
    /* renamed from: getBody */
    public byte[] getIv() {
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(ArraysKt.toList(BleHelper.generateIdFile(this.packageId, this.versionId, this.idFileType)));
        arrayList.addAll(ArraysKt.toList(BleHelper.convertIntToByteArray(Integer.valueOf(this.file.length))));
        return CollectionsKt.toByteArray(arrayList);
    }
}
