package com.google.android.exoplayer2.text.dvb;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.SparseArray;
import androidx.core.view.ViewCompat;
import com.google.android.exoplayer2.text.Cue;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.ParsableBitArray;
import com.google.android.exoplayer2.util.Util;
import com.google.common.base.Ascii;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* loaded from: classes.dex */
final class DvbParser {
    private static final int DATA_TYPE_24_TABLE_DATA = 32;
    private static final int DATA_TYPE_28_TABLE_DATA = 33;
    private static final int DATA_TYPE_2BP_CODE_STRING = 16;
    private static final int DATA_TYPE_48_TABLE_DATA = 34;
    private static final int DATA_TYPE_4BP_CODE_STRING = 17;
    private static final int DATA_TYPE_8BP_CODE_STRING = 18;
    private static final int DATA_TYPE_END_LINE = 240;
    private static final int OBJECT_CODING_PIXELS = 0;
    private static final int OBJECT_CODING_STRING = 1;
    private static final int PAGE_STATE_NORMAL = 0;
    private static final int REGION_DEPTH_4_BIT = 2;
    private static final int REGION_DEPTH_8_BIT = 3;
    private static final int SEGMENT_TYPE_CLUT_DEFINITION = 18;
    private static final int SEGMENT_TYPE_DISPLAY_DEFINITION = 20;
    private static final int SEGMENT_TYPE_OBJECT_DATA = 19;
    private static final int SEGMENT_TYPE_PAGE_COMPOSITION = 16;
    private static final int SEGMENT_TYPE_REGION_COMPOSITION = 17;
    private static final String TAG = "DvbParser";
    private static final byte[] defaultMap2To4 = {0, 7, 8, Ascii.SI};
    private static final byte[] defaultMap2To8 = {0, 119, -120, -1};
    private static final byte[] defaultMap4To8 = {0, 17, 34, 51, 68, 85, 102, 119, -120, -103, -86, -69, -52, -35, -18, -1};
    private Bitmap bitmap;
    private final Canvas canvas;
    private final ClutDefinition defaultClutDefinition;
    private final DisplayDefinition defaultDisplayDefinition;
    private final Paint defaultPaint;
    private final Paint fillRegionPaint;
    private final SubtitleService subtitleService;

    private static int getColor(int a, int r, int g, int b) {
        return (a << 24) | (r << 16) | (g << 8) | b;
    }

    public DvbParser(int subtitlePageId, int ancillaryPageId) {
        Paint paint = new Paint();
        this.defaultPaint = paint;
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
        paint.setPathEffect(null);
        Paint paint2 = new Paint();
        this.fillRegionPaint = paint2;
        paint2.setStyle(Paint.Style.FILL);
        paint2.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OVER));
        paint2.setPathEffect(null);
        this.canvas = new Canvas();
        this.defaultDisplayDefinition = new DisplayDefinition(719, 575, 0, 719, 0, 575);
        this.defaultClutDefinition = new ClutDefinition(0, generateDefault2BitClutEntries(), generateDefault4BitClutEntries(), generateDefault8BitClutEntries());
        this.subtitleService = new SubtitleService(subtitlePageId, ancillaryPageId);
    }

    public void reset() {
        this.subtitleService.reset();
    }

    public List<Cue> decode(byte[] data, int limit) {
        DisplayDefinition displayDefinition;
        int i;
        int i2;
        SparseArray<RegionObject> sparseArray;
        ParsableBitArray parsableBitArray = new ParsableBitArray(data, limit);
        while (parsableBitArray.bitsLeft() >= 48 && parsableBitArray.readBits(8) == 15) {
            parseSubtitlingSegment(parsableBitArray, this.subtitleService);
        }
        PageComposition pageComposition = this.subtitleService.pageComposition;
        if (pageComposition == null) {
            return Collections.emptyList();
        }
        if (this.subtitleService.displayDefinition != null) {
            displayDefinition = this.subtitleService.displayDefinition;
        } else {
            displayDefinition = this.defaultDisplayDefinition;
        }
        if (this.bitmap == null || displayDefinition.width + 1 != this.bitmap.getWidth() || displayDefinition.height + 1 != this.bitmap.getHeight()) {
            Bitmap bitmapCreateBitmap = Bitmap.createBitmap(displayDefinition.width + 1, displayDefinition.height + 1, Bitmap.Config.ARGB_8888);
            this.bitmap = bitmapCreateBitmap;
            this.canvas.setBitmap(bitmapCreateBitmap);
        }
        ArrayList arrayList = new ArrayList();
        SparseArray<PageRegion> sparseArray2 = pageComposition.regions;
        for (int i3 = 0; i3 < sparseArray2.size(); i3++) {
            this.canvas.save();
            PageRegion pageRegionValueAt = sparseArray2.valueAt(i3);
            RegionComposition regionComposition = this.subtitleService.regions.get(sparseArray2.keyAt(i3));
            int i4 = pageRegionValueAt.horizontalAddress + displayDefinition.horizontalPositionMinimum;
            int i5 = pageRegionValueAt.verticalAddress + displayDefinition.verticalPositionMinimum;
            this.canvas.clipRect(i4, i5, Math.min(regionComposition.width + i4, displayDefinition.horizontalPositionMaximum), Math.min(regionComposition.height + i5, displayDefinition.verticalPositionMaximum));
            ClutDefinition clutDefinition = this.subtitleService.cluts.get(regionComposition.clutId);
            if (clutDefinition == null && (clutDefinition = this.subtitleService.ancillaryCluts.get(regionComposition.clutId)) == null) {
                clutDefinition = this.defaultClutDefinition;
            }
            SparseArray<RegionObject> sparseArray3 = regionComposition.regionObjects;
            int i6 = 0;
            while (i6 < sparseArray3.size()) {
                int iKeyAt = sparseArray3.keyAt(i6);
                RegionObject regionObjectValueAt = sparseArray3.valueAt(i6);
                ObjectData objectData = this.subtitleService.objects.get(iKeyAt);
                ObjectData objectData2 = objectData == null ? this.subtitleService.ancillaryObjects.get(iKeyAt) : objectData;
                if (objectData2 != null) {
                    i2 = i6;
                    sparseArray = sparseArray3;
                    paintPixelDataSubBlocks(objectData2, clutDefinition, regionComposition.depth, regionObjectValueAt.horizontalPosition + i4, i5 + regionObjectValueAt.verticalPosition, objectData2.nonModifyingColorFlag ? null : this.defaultPaint, this.canvas);
                } else {
                    i2 = i6;
                    sparseArray = sparseArray3;
                }
                i6 = i2 + 1;
                sparseArray3 = sparseArray;
            }
            if (regionComposition.fillFlag) {
                if (regionComposition.depth == 3) {
                    i = clutDefinition.clutEntries8Bit[regionComposition.pixelCode8Bit];
                } else if (regionComposition.depth == 2) {
                    i = clutDefinition.clutEntries4Bit[regionComposition.pixelCode4Bit];
                } else {
                    i = clutDefinition.clutEntries2Bit[regionComposition.pixelCode2Bit];
                }
                this.fillRegionPaint.setColor(i);
                this.canvas.drawRect(i4, i5, regionComposition.width + i4, regionComposition.height + i5, this.fillRegionPaint);
            }
            arrayList.add(new Cue.Builder().setBitmap(Bitmap.createBitmap(this.bitmap, i4, i5, regionComposition.width, regionComposition.height)).setPosition(i4 / displayDefinition.width).setPositionAnchor(0).setLine(i5 / displayDefinition.height, 0).setLineAnchor(0).setSize(regionComposition.width / displayDefinition.width).setBitmapHeight(regionComposition.height / displayDefinition.height).build());
            this.canvas.drawColor(0, PorterDuff.Mode.CLEAR);
            this.canvas.restore();
        }
        return Collections.unmodifiableList(arrayList);
    }

    private static void parseSubtitlingSegment(ParsableBitArray data, SubtitleService service) {
        RegionComposition regionComposition;
        int bits = data.readBits(8);
        int bits2 = data.readBits(16);
        int bits3 = data.readBits(16);
        int bytePosition = data.getBytePosition() + bits3;
        if (bits3 * 8 > data.bitsLeft()) {
            Log.w(TAG, "Data field length exceeds limit");
            data.skipBits(data.bitsLeft());
            return;
        }
        switch (bits) {
            case 16:
                if (bits2 == service.subtitlePageId) {
                    PageComposition pageComposition = service.pageComposition;
                    PageComposition pageComposition2 = parsePageComposition(data, bits3);
                    if (pageComposition2.state != 0) {
                        service.pageComposition = pageComposition2;
                        service.regions.clear();
                        service.cluts.clear();
                        service.objects.clear();
                        break;
                    } else if (pageComposition != null && pageComposition.version != pageComposition2.version) {
                        service.pageComposition = pageComposition2;
                        break;
                    }
                }
                break;
            case 17:
                PageComposition pageComposition3 = service.pageComposition;
                if (bits2 == service.subtitlePageId && pageComposition3 != null) {
                    RegionComposition regionComposition2 = parseRegionComposition(data, bits3);
                    if (pageComposition3.state == 0 && (regionComposition = service.regions.get(regionComposition2.id)) != null) {
                        regionComposition2.mergeFrom(regionComposition);
                    }
                    service.regions.put(regionComposition2.id, regionComposition2);
                    break;
                }
                break;
            case 18:
                if (bits2 == service.subtitlePageId) {
                    ClutDefinition clutDefinition = parseClutDefinition(data, bits3);
                    service.cluts.put(clutDefinition.id, clutDefinition);
                    break;
                } else if (bits2 == service.ancillaryPageId) {
                    ClutDefinition clutDefinition2 = parseClutDefinition(data, bits3);
                    service.ancillaryCluts.put(clutDefinition2.id, clutDefinition2);
                    break;
                }
                break;
            case 19:
                if (bits2 == service.subtitlePageId) {
                    ObjectData objectData = parseObjectData(data);
                    service.objects.put(objectData.id, objectData);
                    break;
                } else if (bits2 == service.ancillaryPageId) {
                    ObjectData objectData2 = parseObjectData(data);
                    service.ancillaryObjects.put(objectData2.id, objectData2);
                    break;
                }
                break;
            case 20:
                if (bits2 == service.subtitlePageId) {
                    service.displayDefinition = parseDisplayDefinition(data);
                    break;
                }
                break;
        }
        data.skipBytes(bytePosition - data.getBytePosition());
    }

    private static DisplayDefinition parseDisplayDefinition(ParsableBitArray data) {
        int i;
        int i2;
        int i3;
        int bits;
        data.skipBits(4);
        boolean bit = data.readBit();
        data.skipBits(3);
        int bits2 = data.readBits(16);
        int bits3 = data.readBits(16);
        if (bit) {
            int bits4 = data.readBits(16);
            int bits5 = data.readBits(16);
            int bits6 = data.readBits(16);
            bits = data.readBits(16);
            i3 = bits5;
            i2 = bits6;
            i = bits4;
        } else {
            i = 0;
            i2 = 0;
            i3 = bits2;
            bits = bits3;
        }
        return new DisplayDefinition(bits2, bits3, i, i3, i2, bits);
    }

    private static PageComposition parsePageComposition(ParsableBitArray data, int length) {
        int bits = data.readBits(8);
        int bits2 = data.readBits(4);
        int bits3 = data.readBits(2);
        data.skipBits(2);
        int i = length - 2;
        SparseArray sparseArray = new SparseArray();
        while (i > 0) {
            int bits4 = data.readBits(8);
            data.skipBits(8);
            i -= 6;
            sparseArray.put(bits4, new PageRegion(data.readBits(16), data.readBits(16)));
        }
        return new PageComposition(bits, bits2, bits3, sparseArray);
    }

    private static RegionComposition parseRegionComposition(ParsableBitArray data, int length) {
        int bits;
        int bits2;
        int bits3 = data.readBits(8);
        data.skipBits(4);
        boolean bit = data.readBit();
        data.skipBits(3);
        int i = 16;
        int bits4 = data.readBits(16);
        int bits5 = data.readBits(16);
        int bits6 = data.readBits(3);
        int bits7 = data.readBits(3);
        int i2 = 2;
        data.skipBits(2);
        int bits8 = data.readBits(8);
        int bits9 = data.readBits(8);
        int bits10 = data.readBits(4);
        int bits11 = data.readBits(2);
        data.skipBits(2);
        int i3 = length - 10;
        SparseArray sparseArray = new SparseArray();
        while (i3 > 0) {
            int bits12 = data.readBits(i);
            int bits13 = data.readBits(i2);
            int bits14 = data.readBits(i2);
            int bits15 = data.readBits(12);
            int i4 = bits11;
            data.skipBits(4);
            int bits16 = data.readBits(12);
            i3 -= 6;
            if (bits13 == 1 || bits13 == 2) {
                i3 -= 2;
                bits = data.readBits(8);
                bits2 = data.readBits(8);
            } else {
                bits = 0;
                bits2 = 0;
            }
            sparseArray.put(bits12, new RegionObject(bits13, bits14, bits15, bits16, bits, bits2));
            bits11 = i4;
            i2 = 2;
            i = 16;
        }
        return new RegionComposition(bits3, bit, bits4, bits5, bits6, bits7, bits8, bits9, bits10, bits11, sparseArray);
    }

    private static ClutDefinition parseClutDefinition(ParsableBitArray data, int length) {
        int bits;
        int i;
        int bits2;
        int bits3;
        int bits4;
        int i2 = 8;
        int bits5 = data.readBits(8);
        data.skipBits(8);
        int i3 = 2;
        int i4 = length - 2;
        int[] iArrGenerateDefault2BitClutEntries = generateDefault2BitClutEntries();
        int[] iArrGenerateDefault4BitClutEntries = generateDefault4BitClutEntries();
        int[] iArrGenerateDefault8BitClutEntries = generateDefault8BitClutEntries();
        while (i4 > 0) {
            int bits6 = data.readBits(i2);
            int bits7 = data.readBits(i2);
            int i5 = i4 - 2;
            int[] iArr = (bits7 & 128) != 0 ? iArrGenerateDefault2BitClutEntries : (bits7 & 64) != 0 ? iArrGenerateDefault4BitClutEntries : iArrGenerateDefault8BitClutEntries;
            if ((bits7 & 1) != 0) {
                bits3 = data.readBits(i2);
                bits4 = data.readBits(i2);
                bits = data.readBits(i2);
                bits2 = data.readBits(i2);
                i = i5 - 4;
            } else {
                int bits8 = data.readBits(6) << i3;
                int bits9 = data.readBits(4) << 4;
                bits = data.readBits(4) << 4;
                i = i5 - 2;
                bits2 = data.readBits(i3) << 6;
                bits3 = bits8;
                bits4 = bits9;
            }
            if (bits3 == 0) {
                bits2 = 255;
                bits4 = 0;
                bits = 0;
            }
            double d = bits3;
            double d2 = bits4 - 128;
            double d3 = bits - 128;
            iArr[bits6] = getColor((byte) (255 - (bits2 & 255)), Util.constrainValue((int) (d + (1.402d * d2)), 0, 255), Util.constrainValue((int) ((d - (0.34414d * d3)) - (d2 * 0.71414d)), 0, 255), Util.constrainValue((int) (d + (d3 * 1.772d)), 0, 255));
            i4 = i;
            bits5 = bits5;
            i2 = 8;
            i3 = 2;
        }
        return new ClutDefinition(bits5, iArrGenerateDefault2BitClutEntries, iArrGenerateDefault4BitClutEntries, iArrGenerateDefault8BitClutEntries);
    }

    private static ObjectData parseObjectData(ParsableBitArray data) {
        int bits = data.readBits(16);
        data.skipBits(4);
        int bits2 = data.readBits(2);
        boolean bit = data.readBit();
        data.skipBits(1);
        byte[] bArr = Util.EMPTY_BYTE_ARRAY;
        byte[] bArr2 = Util.EMPTY_BYTE_ARRAY;
        if (bits2 == 1) {
            data.skipBits(data.readBits(8) * 16);
        } else if (bits2 == 0) {
            int bits3 = data.readBits(16);
            int bits4 = data.readBits(16);
            if (bits3 > 0) {
                bArr = new byte[bits3];
                data.readBytes(bArr, 0, bits3);
            }
            if (bits4 > 0) {
                bArr2 = new byte[bits4];
                data.readBytes(bArr2, 0, bits4);
            } else {
                bArr2 = bArr;
            }
        }
        return new ObjectData(bits, bit, bArr, bArr2);
    }

    private static int[] generateDefault2BitClutEntries() {
        return new int[]{0, -1, ViewCompat.MEASURED_STATE_MASK, -8421505};
    }

    private static int[] generateDefault4BitClutEntries() {
        int[] iArr = new int[16];
        iArr[0] = 0;
        for (int i = 1; i < 16; i++) {
            if (i < 8) {
                iArr[i] = getColor(255, (i & 1) != 0 ? 255 : 0, (i & 2) != 0 ? 255 : 0, (i & 4) != 0 ? 255 : 0);
            } else {
                iArr[i] = getColor(255, (i & 1) != 0 ? 127 : 0, (i & 2) != 0 ? 127 : 0, (i & 4) == 0 ? 0 : 127);
            }
        }
        return iArr;
    }

    private static int[] generateDefault8BitClutEntries() {
        int[] iArr = new int[256];
        iArr[0] = 0;
        for (int i = 0; i < 256; i++) {
            if (i < 8) {
                iArr[i] = getColor(63, (i & 1) != 0 ? 255 : 0, (i & 2) != 0 ? 255 : 0, (i & 4) == 0 ? 0 : 255);
            } else {
                int i2 = i & 136;
                if (i2 == 0) {
                    iArr[i] = getColor(255, ((i & 1) != 0 ? 85 : 0) + ((i & 16) != 0 ? 170 : 0), ((i & 2) != 0 ? 85 : 0) + ((i & 32) != 0 ? 170 : 0), ((i & 4) == 0 ? 0 : 85) + ((i & 64) == 0 ? 0 : 170));
                } else if (i2 == 8) {
                    iArr[i] = getColor(127, ((i & 1) != 0 ? 85 : 0) + ((i & 16) != 0 ? 170 : 0), ((i & 2) != 0 ? 85 : 0) + ((i & 32) != 0 ? 170 : 0), ((i & 4) == 0 ? 0 : 85) + ((i & 64) == 0 ? 0 : 170));
                } else if (i2 == 128) {
                    iArr[i] = getColor(255, ((i & 1) != 0 ? 43 : 0) + 127 + ((i & 16) != 0 ? 85 : 0), ((i & 2) != 0 ? 43 : 0) + 127 + ((i & 32) != 0 ? 85 : 0), ((i & 4) == 0 ? 0 : 43) + 127 + ((i & 64) == 0 ? 0 : 85));
                } else if (i2 == 136) {
                    iArr[i] = getColor(255, ((i & 1) != 0 ? 43 : 0) + ((i & 16) != 0 ? 85 : 0), ((i & 2) != 0 ? 43 : 0) + ((i & 32) != 0 ? 85 : 0), ((i & 4) == 0 ? 0 : 43) + ((i & 64) == 0 ? 0 : 85));
                }
            }
        }
        return iArr;
    }

    private static void paintPixelDataSubBlocks(ObjectData objectData, ClutDefinition clutDefinition, int regionDepth, int horizontalAddress, int verticalAddress, Paint paint, Canvas canvas) {
        int[] iArr;
        if (regionDepth == 3) {
            iArr = clutDefinition.clutEntries8Bit;
        } else if (regionDepth == 2) {
            iArr = clutDefinition.clutEntries4Bit;
        } else {
            iArr = clutDefinition.clutEntries2Bit;
        }
        int[] iArr2 = iArr;
        paintPixelDataSubBlock(objectData.topFieldData, iArr2, regionDepth, horizontalAddress, verticalAddress, paint, canvas);
        paintPixelDataSubBlock(objectData.bottomFieldData, iArr2, regionDepth, horizontalAddress, verticalAddress + 1, paint, canvas);
    }

    private static void paintPixelDataSubBlock(byte[] pixelData, int[] clutEntries, int regionDepth, int horizontalAddress, int verticalAddress, Paint paint, Canvas canvas) {
        byte[] bArr;
        byte[] bArr2;
        byte[] bArr3;
        ParsableBitArray parsableBitArray = new ParsableBitArray(pixelData);
        int iPaint2BitPixelCodeString = horizontalAddress;
        int i = verticalAddress;
        byte[] bArrBuildClutMapTable = null;
        byte[] bArrBuildClutMapTable2 = null;
        byte[] bArrBuildClutMapTable3 = null;
        while (parsableBitArray.bitsLeft() != 0) {
            int bits = parsableBitArray.readBits(8);
            if (bits != 240) {
                switch (bits) {
                    case 16:
                        if (regionDepth != 3) {
                            if (regionDepth == 2) {
                                bArr2 = bArrBuildClutMapTable3 == null ? defaultMap2To4 : bArrBuildClutMapTable3;
                            } else {
                                bArr = null;
                                iPaint2BitPixelCodeString = paint2BitPixelCodeString(parsableBitArray, clutEntries, bArr, iPaint2BitPixelCodeString, i, paint, canvas);
                                parsableBitArray.byteAlign();
                                break;
                            }
                        } else {
                            bArr2 = bArrBuildClutMapTable == null ? defaultMap2To8 : bArrBuildClutMapTable;
                        }
                        bArr = bArr2;
                        iPaint2BitPixelCodeString = paint2BitPixelCodeString(parsableBitArray, clutEntries, bArr, iPaint2BitPixelCodeString, i, paint, canvas);
                        parsableBitArray.byteAlign();
                    case 17:
                        if (regionDepth == 3) {
                            bArr3 = bArrBuildClutMapTable2 == null ? defaultMap4To8 : bArrBuildClutMapTable2;
                        } else {
                            bArr3 = null;
                        }
                        iPaint2BitPixelCodeString = paint4BitPixelCodeString(parsableBitArray, clutEntries, bArr3, iPaint2BitPixelCodeString, i, paint, canvas);
                        parsableBitArray.byteAlign();
                        break;
                    case 18:
                        iPaint2BitPixelCodeString = paint8BitPixelCodeString(parsableBitArray, clutEntries, null, iPaint2BitPixelCodeString, i, paint, canvas);
                        break;
                    default:
                        switch (bits) {
                            case 32:
                                bArrBuildClutMapTable3 = buildClutMapTable(4, 4, parsableBitArray);
                                break;
                            case 33:
                                bArrBuildClutMapTable = buildClutMapTable(4, 8, parsableBitArray);
                                break;
                            case 34:
                                bArrBuildClutMapTable2 = buildClutMapTable(16, 8, parsableBitArray);
                                break;
                        }
                }
            } else {
                i += 2;
                iPaint2BitPixelCodeString = horizontalAddress;
            }
        }
    }

    private static int paint2BitPixelCodeString(ParsableBitArray data, int[] clutEntries, byte[] clutMapTable, int column, int line, Paint paint, Canvas canvas) {
        boolean z;
        int i;
        int bits;
        int bits2;
        int i2 = column;
        boolean z2 = false;
        while (true) {
            int bits3 = data.readBits(2);
            if (bits3 != 0) {
                z = z2;
                i = 1;
            } else {
                if (data.readBit()) {
                    bits = data.readBits(3) + 3;
                    bits2 = data.readBits(2);
                } else {
                    if (data.readBit()) {
                        z = z2;
                        i = 1;
                    } else {
                        int bits4 = data.readBits(2);
                        if (bits4 == 0) {
                            z = true;
                        } else if (bits4 == 1) {
                            z = z2;
                            i = 2;
                        } else if (bits4 == 2) {
                            bits = data.readBits(4) + 12;
                            bits2 = data.readBits(2);
                        } else if (bits4 != 3) {
                            z = z2;
                        } else {
                            bits = data.readBits(8) + 29;
                            bits2 = data.readBits(2);
                        }
                        bits3 = 0;
                        i = 0;
                    }
                    bits3 = 0;
                }
                z = z2;
                i = bits;
                bits3 = bits2;
            }
            if (i != 0 && paint != null) {
                if (clutMapTable != null) {
                    bits3 = clutMapTable[bits3];
                }
                paint.setColor(clutEntries[bits3]);
                canvas.drawRect(i2, line, i2 + i, line + 1, paint);
            }
            i2 += i;
            if (z) {
                return i2;
            }
            z2 = z;
        }
    }

    private static int paint4BitPixelCodeString(ParsableBitArray data, int[] clutEntries, byte[] clutMapTable, int column, int line, Paint paint, Canvas canvas) {
        boolean z;
        int i;
        int bits;
        int bits2;
        int i2 = column;
        boolean z2 = false;
        while (true) {
            int bits3 = data.readBits(4);
            if (bits3 != 0) {
                z = z2;
                i = 1;
            } else if (!data.readBit()) {
                int bits4 = data.readBits(3);
                if (bits4 != 0) {
                    z = z2;
                    i = bits4 + 2;
                    bits3 = 0;
                } else {
                    z = true;
                    bits3 = 0;
                    i = 0;
                }
            } else {
                if (!data.readBit()) {
                    bits = data.readBits(2) + 4;
                    bits2 = data.readBits(4);
                } else {
                    int bits5 = data.readBits(2);
                    if (bits5 == 0) {
                        z = z2;
                        i = 1;
                    } else if (bits5 == 1) {
                        z = z2;
                        i = 2;
                    } else if (bits5 == 2) {
                        bits = data.readBits(4) + 9;
                        bits2 = data.readBits(4);
                    } else if (bits5 != 3) {
                        z = z2;
                        bits3 = 0;
                        i = 0;
                    } else {
                        bits = data.readBits(8) + 25;
                        bits2 = data.readBits(4);
                    }
                    bits3 = 0;
                }
                z = z2;
                i = bits;
                bits3 = bits2;
            }
            if (i != 0 && paint != null) {
                if (clutMapTable != null) {
                    bits3 = clutMapTable[bits3];
                }
                paint.setColor(clutEntries[bits3]);
                canvas.drawRect(i2, line, i2 + i, line + 1, paint);
            }
            i2 += i;
            if (z) {
                return i2;
            }
            z2 = z;
        }
    }

    private static int paint8BitPixelCodeString(ParsableBitArray data, int[] clutEntries, byte[] clutMapTable, int column, int line, Paint paint, Canvas canvas) {
        boolean z;
        int bits;
        int i = column;
        boolean z2 = false;
        while (true) {
            int bits2 = data.readBits(8);
            if (bits2 != 0) {
                z = z2;
                bits = 1;
            } else if (!data.readBit()) {
                int bits3 = data.readBits(7);
                if (bits3 != 0) {
                    z = z2;
                    bits = bits3;
                    bits2 = 0;
                } else {
                    z = true;
                    bits2 = 0;
                    bits = 0;
                }
            } else {
                z = z2;
                bits = data.readBits(7);
                bits2 = data.readBits(8);
            }
            if (bits != 0 && paint != null) {
                if (clutMapTable != null) {
                    bits2 = clutMapTable[bits2];
                }
                paint.setColor(clutEntries[bits2]);
                canvas.drawRect(i, line, i + bits, line + 1, paint);
            }
            i += bits;
            if (z) {
                return i;
            }
            z2 = z;
        }
    }

    private static byte[] buildClutMapTable(int length, int bitsPerEntry, ParsableBitArray data) {
        byte[] bArr = new byte[length];
        for (int i = 0; i < length; i++) {
            bArr[i] = (byte) data.readBits(bitsPerEntry);
        }
        return bArr;
    }

    private static final class SubtitleService {
        public final int ancillaryPageId;
        public DisplayDefinition displayDefinition;
        public PageComposition pageComposition;
        public final int subtitlePageId;
        public final SparseArray<RegionComposition> regions = new SparseArray<>();
        public final SparseArray<ClutDefinition> cluts = new SparseArray<>();
        public final SparseArray<ObjectData> objects = new SparseArray<>();
        public final SparseArray<ClutDefinition> ancillaryCluts = new SparseArray<>();
        public final SparseArray<ObjectData> ancillaryObjects = new SparseArray<>();

        public SubtitleService(int subtitlePageId, int ancillaryPageId) {
            this.subtitlePageId = subtitlePageId;
            this.ancillaryPageId = ancillaryPageId;
        }

        public void reset() {
            this.regions.clear();
            this.cluts.clear();
            this.objects.clear();
            this.ancillaryCluts.clear();
            this.ancillaryObjects.clear();
            this.displayDefinition = null;
            this.pageComposition = null;
        }
    }

    private static final class DisplayDefinition {
        public final int height;
        public final int horizontalPositionMaximum;
        public final int horizontalPositionMinimum;
        public final int verticalPositionMaximum;
        public final int verticalPositionMinimum;
        public final int width;

        public DisplayDefinition(int width, int height, int horizontalPositionMinimum, int horizontalPositionMaximum, int verticalPositionMinimum, int verticalPositionMaximum) {
            this.width = width;
            this.height = height;
            this.horizontalPositionMinimum = horizontalPositionMinimum;
            this.horizontalPositionMaximum = horizontalPositionMaximum;
            this.verticalPositionMinimum = verticalPositionMinimum;
            this.verticalPositionMaximum = verticalPositionMaximum;
        }
    }

    private static final class PageComposition {
        public final SparseArray<PageRegion> regions;
        public final int state;
        public final int timeOutSecs;
        public final int version;

        public PageComposition(int timeoutSecs, int version, int state, SparseArray<PageRegion> regions) {
            this.timeOutSecs = timeoutSecs;
            this.version = version;
            this.state = state;
            this.regions = regions;
        }
    }

    private static final class PageRegion {
        public final int horizontalAddress;
        public final int verticalAddress;

        public PageRegion(int horizontalAddress, int verticalAddress) {
            this.horizontalAddress = horizontalAddress;
            this.verticalAddress = verticalAddress;
        }
    }

    private static final class RegionComposition {
        public final int clutId;
        public final int depth;
        public final boolean fillFlag;
        public final int height;
        public final int id;
        public final int levelOfCompatibility;
        public final int pixelCode2Bit;
        public final int pixelCode4Bit;
        public final int pixelCode8Bit;
        public final SparseArray<RegionObject> regionObjects;
        public final int width;

        public RegionComposition(int id, boolean fillFlag, int width, int height, int levelOfCompatibility, int depth, int clutId, int pixelCode8Bit, int pixelCode4Bit, int pixelCode2Bit, SparseArray<RegionObject> regionObjects) {
            this.id = id;
            this.fillFlag = fillFlag;
            this.width = width;
            this.height = height;
            this.levelOfCompatibility = levelOfCompatibility;
            this.depth = depth;
            this.clutId = clutId;
            this.pixelCode8Bit = pixelCode8Bit;
            this.pixelCode4Bit = pixelCode4Bit;
            this.pixelCode2Bit = pixelCode2Bit;
            this.regionObjects = regionObjects;
        }

        public void mergeFrom(RegionComposition otherRegionComposition) {
            SparseArray<RegionObject> sparseArray = otherRegionComposition.regionObjects;
            for (int i = 0; i < sparseArray.size(); i++) {
                this.regionObjects.put(sparseArray.keyAt(i), sparseArray.valueAt(i));
            }
        }
    }

    private static final class RegionObject {
        public final int backgroundPixelCode;
        public final int foregroundPixelCode;
        public final int horizontalPosition;
        public final int provider;
        public final int type;
        public final int verticalPosition;

        public RegionObject(int type, int provider, int horizontalPosition, int verticalPosition, int foregroundPixelCode, int backgroundPixelCode) {
            this.type = type;
            this.provider = provider;
            this.horizontalPosition = horizontalPosition;
            this.verticalPosition = verticalPosition;
            this.foregroundPixelCode = foregroundPixelCode;
            this.backgroundPixelCode = backgroundPixelCode;
        }
    }

    private static final class ClutDefinition {
        public final int[] clutEntries2Bit;
        public final int[] clutEntries4Bit;
        public final int[] clutEntries8Bit;
        public final int id;

        public ClutDefinition(int id, int[] clutEntries2Bit, int[] clutEntries4Bit, int[] clutEntries8bit) {
            this.id = id;
            this.clutEntries2Bit = clutEntries2Bit;
            this.clutEntries4Bit = clutEntries4Bit;
            this.clutEntries8Bit = clutEntries8bit;
        }
    }

    private static final class ObjectData {
        public final byte[] bottomFieldData;
        public final int id;
        public final boolean nonModifyingColorFlag;
        public final byte[] topFieldData;

        public ObjectData(int id, boolean nonModifyingColorFlag, byte[] topFieldData, byte[] bottomFieldData) {
            this.id = id;
            this.nonModifyingColorFlag = nonModifyingColorFlag;
            this.topFieldData = topFieldData;
            this.bottomFieldData = bottomFieldData;
        }
    }
}
