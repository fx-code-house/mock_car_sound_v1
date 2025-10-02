package com.thor.app.gui.activities.googleautha;

import androidx.credentials.CredentialManager;
import androidx.credentials.GetCredentialRequest;
import androidx.credentials.GetCredentialResponse;
import androidx.credentials.exceptions.GetCredentialCancellationException;
import com.carsystems.thor.app.databinding.ActivityGoogleAuthBinding;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: GoogleAuthActivity.kt */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 8, 0}, xi = 48)
@DebugMetadata(c = "com.thor.app.gui.activities.googleautha.GoogleAuthActivity$initClickListener$1$1", f = "GoogleAuthActivity.kt", i = {}, l = {91}, m = "invokeSuspend", n = {}, s = {})
/* loaded from: classes3.dex */
final class GoogleAuthActivity$initClickListener$1$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    int label;
    final /* synthetic */ GoogleAuthActivity this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    GoogleAuthActivity$initClickListener$1$1(GoogleAuthActivity googleAuthActivity, Continuation<? super GoogleAuthActivity$initClickListener$1$1> continuation) {
        super(2, continuation);
        this.this$0 = googleAuthActivity;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new GoogleAuthActivity$initClickListener$1$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((GoogleAuthActivity$initClickListener$1$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        ActivityGoogleAuthBinding activityGoogleAuthBinding = null;
        try {
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                CredentialManager credentialManager = this.this$0.credentialManager;
                if (credentialManager == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("credentialManager");
                    credentialManager = null;
                }
                GetCredentialRequest getCredentialRequest = this.this$0.request;
                if (getCredentialRequest == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("request");
                    getCredentialRequest = null;
                }
                this.label = 1;
                obj = credentialManager.getCredential(this.this$0, getCredentialRequest, this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            this.this$0.handleSignIn((GetCredentialResponse) obj);
        } catch (GetCredentialCancellationException unused) {
            ActivityGoogleAuthBinding activityGoogleAuthBinding2 = this.this$0.binding;
            if (activityGoogleAuthBinding2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
            } else {
                activityGoogleAuthBinding = activityGoogleAuthBinding2;
            }
            activityGoogleAuthBinding.progressBar.setVisibility(8);
        } catch (Exception e) {
            this.this$0.handleError(e);
        }
        return Unit.INSTANCE;
    }
}
