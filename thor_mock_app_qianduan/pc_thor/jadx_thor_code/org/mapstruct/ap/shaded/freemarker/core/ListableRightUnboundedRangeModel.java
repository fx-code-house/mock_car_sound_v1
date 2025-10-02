package org.mapstruct.ap.shaded.freemarker.core;

import java.math.BigInteger;
import org.mapstruct.ap.shaded.freemarker.template.SimpleNumber;
import org.mapstruct.ap.shaded.freemarker.template.TemplateCollectionModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModelException;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModelIterator;

/* loaded from: classes3.dex */
final class ListableRightUnboundedRangeModel extends RightUnboundedRangeModel implements TemplateCollectionModel {
    @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateSequenceModel
    public int size() throws TemplateModelException {
        return Integer.MAX_VALUE;
    }

    ListableRightUnboundedRangeModel(int i) {
        super(i);
    }

    @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateCollectionModel
    public TemplateModelIterator iterator() throws TemplateModelException {
        return new TemplateModelIterator() { // from class: org.mapstruct.ap.shaded.freemarker.core.ListableRightUnboundedRangeModel.1
            boolean needInc;
            BigInteger nextBigInteger;
            int nextInt;
            long nextLong;
            int nextType = 1;

            @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateModelIterator
            public boolean hasNext() throws TemplateModelException {
                return true;
            }

            {
                this.nextInt = ListableRightUnboundedRangeModel.this.getBegining();
            }

            @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateModelIterator
            public TemplateModel next() throws TemplateModelException {
                if (this.needInc) {
                    int i = this.nextType;
                    if (i == 1) {
                        int i2 = this.nextInt;
                        if (i2 < Integer.MAX_VALUE) {
                            this.nextInt = i2 + 1;
                        } else {
                            this.nextType = 2;
                            this.nextLong = i2 + 1;
                        }
                    } else if (i == 2) {
                        long j = this.nextLong;
                        if (j < Long.MAX_VALUE) {
                            this.nextLong = j + 1;
                        } else {
                            this.nextType = 3;
                            BigInteger bigIntegerValueOf = BigInteger.valueOf(j);
                            this.nextBigInteger = bigIntegerValueOf;
                            this.nextBigInteger = bigIntegerValueOf.add(BigInteger.ONE);
                        }
                    } else {
                        this.nextBigInteger = this.nextBigInteger.add(BigInteger.ONE);
                    }
                }
                this.needInc = true;
                int i3 = this.nextType;
                return i3 == 1 ? new SimpleNumber(this.nextInt) : i3 == 2 ? new SimpleNumber(this.nextLong) : new SimpleNumber(this.nextBigInteger);
            }
        };
    }
}
