package com.google.android.exoplayer2.drm;

import com.google.android.exoplayer2.drm.ExoMediaDrm;
import java.util.UUID;

/* loaded from: classes.dex */
public interface MediaDrmCallback {
    byte[] executeKeyRequest(UUID uuid, ExoMediaDrm.KeyRequest request) throws MediaDrmCallbackException;

    byte[] executeProvisionRequest(UUID uuid, ExoMediaDrm.ProvisionRequest request) throws MediaDrmCallbackException;
}
