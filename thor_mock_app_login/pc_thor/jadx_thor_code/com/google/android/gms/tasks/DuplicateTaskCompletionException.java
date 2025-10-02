package com.google.android.gms.tasks;

/* compiled from: com.google.android.gms:play-services-tasks@@18.0.1 */
/* loaded from: classes2.dex */
public final class DuplicateTaskCompletionException extends IllegalStateException {
    private DuplicateTaskCompletionException(String str, Throwable th) {
        super(str, th);
    }

    public static IllegalStateException of(Task<?> task) {
        String strConcat;
        if (!task.isComplete()) {
            return new IllegalStateException("DuplicateTaskCompletionException can only be created from completed Task.");
        }
        Exception exception = task.getException();
        if (exception != null) {
            strConcat = "failure";
        } else if (task.isSuccessful()) {
            String strValueOf = String.valueOf(task.getResult());
            String.valueOf(strValueOf).length();
            strConcat = "result ".concat(String.valueOf(strValueOf));
        } else {
            strConcat = task.isCanceled() ? "cancellation" : "unknown issue";
        }
        return new DuplicateTaskCompletionException(strConcat.length() != 0 ? "Complete with: ".concat(strConcat) : new String("Complete with: "), exception);
    }
}
