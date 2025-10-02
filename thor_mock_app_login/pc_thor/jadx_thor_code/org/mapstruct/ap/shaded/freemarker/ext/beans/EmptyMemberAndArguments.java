package org.mapstruct.ap.shaded.freemarker.ext.beans;

import org.mapstruct.ap.shaded.freemarker.core._DelayedOrdinal;

/* loaded from: classes3.dex */
final class EmptyMemberAndArguments extends MaybeEmptyMemberAndArguments {
    static final EmptyMemberAndArguments WRONG_NUMBER_OF_ARGUMENTS = new EmptyMemberAndArguments("No compatible overloaded variation was found; wrong number of arguments.", true, null);
    private final Object errorDescription;
    private final boolean numberOfArgumentsWrong;
    private final Object[] unwrappedArguments;

    private EmptyMemberAndArguments(Object obj, boolean z, Object[] objArr) {
        this.errorDescription = obj;
        this.numberOfArgumentsWrong = z;
        this.unwrappedArguments = objArr;
    }

    static EmptyMemberAndArguments noCompatibleOverload(int i) {
        return new EmptyMemberAndArguments(new Object[]{"No compatible overloaded variation was found; can't convert (unwrap) the ", new _DelayedOrdinal(new Integer(i)), " argument to the desired Java type."}, false, null);
    }

    static EmptyMemberAndArguments noCompatibleOverload(Object[] objArr) {
        return new EmptyMemberAndArguments("No compatible overloaded variation was found; declared parameter types and argument value types mismatch.", false, objArr);
    }

    static EmptyMemberAndArguments ambiguous(Object[] objArr) {
        return new EmptyMemberAndArguments("Multiple compatible overloaded variations were found with the same priorty.", false, objArr);
    }

    static MaybeEmptyMemberAndArguments from(EmptyCallableMemberDescriptor emptyCallableMemberDescriptor, Object[] objArr) {
        if (emptyCallableMemberDescriptor == EmptyCallableMemberDescriptor.NO_SUCH_METHOD) {
            return noCompatibleOverload(objArr);
        }
        if (emptyCallableMemberDescriptor == EmptyCallableMemberDescriptor.AMBIGUOUS_METHOD) {
            return ambiguous(objArr);
        }
        throw new IllegalArgumentException(new StringBuffer("Unrecognized constant: ").append(emptyCallableMemberDescriptor).toString());
    }

    Object getErrorDescription() {
        return this.errorDescription;
    }

    Object[] getUnwrappedArguments() {
        return this.unwrappedArguments;
    }

    public boolean isNumberOfArgumentsWrong() {
        return this.numberOfArgumentsWrong;
    }
}
