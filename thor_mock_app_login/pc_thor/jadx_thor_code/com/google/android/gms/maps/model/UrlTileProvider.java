package com.google.android.gms.maps.model;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/* loaded from: classes2.dex */
public abstract class UrlTileProvider implements TileProvider {
    private final int height;
    private final int width;

    public UrlTileProvider(int i, int i2) {
        this.width = i;
        this.height = i2;
    }

    public abstract URL getTileUrl(int i, int i2, int i3);

    @Override // com.google.android.gms.maps.model.TileProvider
    public final Tile getTile(int i, int i2, int i3) throws IOException {
        URL tileUrl = getTileUrl(i, i2, i3);
        if (tileUrl == null) {
            return NO_TILE;
        }
        try {
            int i4 = this.width;
            int i5 = this.height;
            InputStream inputStreamOpenStream = tileUrl.openStream();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] bArr = new byte[4096];
            while (true) {
                int i6 = inputStreamOpenStream.read(bArr);
                if (i6 != -1) {
                    byteArrayOutputStream.write(bArr, 0, i6);
                } else {
                    return new Tile(i4, i5, byteArrayOutputStream.toByteArray());
                }
            }
        } catch (IOException unused) {
            return null;
        }
    }
}
