package com.thor.app.gui.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import androidx.appcompat.app.AlertDialog;
import com.carsystems.thor.app.R;
import com.thor.app.bus.events.BadUserEvents;
import com.thor.app.bus.events.SendLogsEvent;
import com.thor.app.services.BlerErrors;
import com.thor.app.settings.Settings;
import com.thor.networkmodule.model.responses.SignInResponse;
import java.util.Arrays;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import org.greenrobot.eventbus.EventBus;

/* compiled from: DialogManager.kt */
@Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\n\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\n\n\u0002\u0010\b\n\u0002\b\f\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J4\u0010\n\u001a\u0004\u0018\u00010\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\r2\u0006\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u000f2\u000e\b\u0002\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00130\u0012J@\u0010\n\u001a\u0004\u0018\u00010\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\r2\u0006\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u000f2\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00130\u00122\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00130\u0012J4\u0010\u0015\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\b\b\u0002\u0010\u0016\u001a\u00020\u00172\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00130\u00122\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00130\u0012J\u001c\u0010\u001a\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00130\u0012J$\u0010\u001b\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u001c\u001a\u00020\u000f2\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00130\u0012J\u0018\u0010\u001d\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u001e\u001a\u00020\u0004J\u001c\u0010\u001f\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00130\u0012J,\u0010 \u001a\u0004\u0018\u00010\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\r2\b\u0010!\u001a\u0004\u0018\u00010\u000f2\u000e\b\u0002\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00130\u0012J$\u0010 \u001a\u0004\u0018\u00010\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\r2\b\u0010!\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\u001e\u001a\u00020\"J-\u0010#\u001a\u0004\u0018\u00010\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\r2\b\u0010!\u001a\u0004\u0018\u00010\u000f2\n\b\u0002\u0010\u001e\u001a\u0004\u0018\u00010\"¢\u0006\u0002\u0010$J\u001c\u0010%\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00130\u0012J\u001c\u0010&\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00130\u0012J\u000e\u0010'\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rJ\u000e\u0010(\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rJ\u001c\u0010)\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00130\u0012J$\u0010)\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010*\u001a\u00020\u000f2\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00130\u0012J*\u0010+\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\f\u0010,\u001a\b\u0012\u0004\u0012\u00020\u00130\u00122\f\u0010-\u001a\b\u0012\u0004\u0012\u00020\u00130\u0012R\u001e\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u0010\n\u0002\u0010\t\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\b¨\u0006."}, d2 = {"Lcom/thor/app/gui/dialog/DialogManager;", "", "()V", "lastErrorCode", "", "getLastErrorCode", "()Ljava/lang/Short;", "setLastErrorCode", "(Ljava/lang/Short;)V", "Ljava/lang/Short;", "createAlertDialogUpdate", "Landroidx/appcompat/app/AlertDialog;", "context", "Landroid/content/Context;", "title", "", "text", "onClickOk", "Lkotlin/Function0;", "", "onClickNegative", "createCarInfoAlertDialog", "isNoCanFile", "", "positiveAction", "neturalAction", "createCarModeDeviceSearchWarning", "createDamagePckAlertDialog", "pckName", "createDialogBordError", "code", "createDriveSelectInfoAlertDialog", "createErrorAlertDialog", "errorText", "", "createErrorAlertDialogWithSendLogOption", "(Landroid/content/Context;Ljava/lang/String;Ljava/lang/Integer;)Landroidx/appcompat/app/AlertDialog;", "createFirmwareUpdatedAlertDialog", "createLogOutAlertDialog", "createNoCarFileAlertDialog", "createNoConnectionToBoardAlertDialog", "createPresetFactoryResetAlertDialog", "modeIndexText", "createServerSelectAlertDialog", "onDevServerAction", "onProdServerAction", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class DialogManager {
    public static final DialogManager INSTANCE = new DialogManager();
    private static Short lastErrorCode;

    private DialogManager() {
    }

    public final Short getLastErrorCode() {
        return lastErrorCode;
    }

    public final void setLastErrorCode(Short sh) {
        lastErrorCode = sh;
    }

    public final AlertDialog createNoCarFileAlertDialog(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        AlertDialog.Builder builder = new AlertDialog.Builder(context, 2131886083);
        builder.setMessage(R.string.text_contact_support_team).setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() { // from class: com.thor.app.gui.dialog.DialogManager$$ExternalSyntheticLambda17
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog alertDialogCreate = builder.create();
        Intrinsics.checkNotNullExpressionValue(alertDialogCreate, "alertBuilder.create()");
        return alertDialogCreate;
    }

    public final AlertDialog createNoConnectionToBoardAlertDialog(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        AlertDialog.Builder builder = new AlertDialog.Builder(context, 2131886083);
        builder.setMessage(R.string.message_missing_connection).setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() { // from class: com.thor.app.gui.dialog.DialogManager$$ExternalSyntheticLambda21
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog alertDialogCreate = builder.create();
        Intrinsics.checkNotNullExpressionValue(alertDialogCreate, "alertBuilder.create()");
        return alertDialogCreate;
    }

    public final AlertDialog createDamagePckAlertDialog(Context context, String pckName, final Function0<Unit> positiveAction) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(pckName, "pckName");
        Intrinsics.checkNotNullParameter(positiveAction, "positiveAction");
        AlertDialog.Builder builder = new AlertDialog.Builder(context, 2131886083);
        StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
        String string = context.getString(R.string.text_damage_pck_dialog);
        Intrinsics.checkNotNullExpressionValue(string, "context.getString(R.string.text_damage_pck_dialog)");
        String str = String.format(string, Arrays.copyOf(new Object[]{pckName}, 1));
        Intrinsics.checkNotNullExpressionValue(str, "format(...)");
        builder.setMessage(str).setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() { // from class: com.thor.app.gui.dialog.DialogManager$$ExternalSyntheticLambda26
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                DialogManager.createDamagePckAlertDialog$lambda$2(positiveAction, dialogInterface, i);
            }
        }).setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() { // from class: com.thor.app.gui.dialog.DialogManager$$ExternalSyntheticLambda27
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog alertDialogCreate = builder.create();
        Intrinsics.checkNotNullExpressionValue(alertDialogCreate, "alertBuilder.create()");
        return alertDialogCreate;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void createDamagePckAlertDialog$lambda$2(Function0 positiveAction, DialogInterface dialogInterface, int i) {
        Intrinsics.checkNotNullParameter(positiveAction, "$positiveAction");
        dialogInterface.dismiss();
        positiveAction.invoke();
    }

    public static /* synthetic */ AlertDialog createCarInfoAlertDialog$default(DialogManager dialogManager, Context context, boolean z, Function0 function0, Function0 function02, int i, Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        return dialogManager.createCarInfoAlertDialog(context, z, function0, function02);
    }

    public final AlertDialog createCarInfoAlertDialog(Context context, boolean isNoCanFile, final Function0<Unit> positiveAction, final Function0<Unit> neturalAction) {
        String string;
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(positiveAction, "positiveAction");
        Intrinsics.checkNotNullParameter(neturalAction, "neturalAction");
        AlertDialog.Builder builder = new AlertDialog.Builder(context, 2131886083);
        SignInResponse profile = Settings.INSTANCE.getProfile();
        String string2 = context.getString(R.string.text_your_car);
        Intrinsics.checkNotNullExpressionValue(string2, "context.getString(R.string.text_your_car)");
        String str = "";
        if (isNoCanFile) {
            string2 = "";
            str = context.getString(R.string.text_can_file_not_found) + "\n\n";
        }
        Log.i("DeviceStatus1", "isDeviceHasCanFileData: " + Settings.INSTANCE.isDeviceHasCanFileData());
        if (Settings.INSTANCE.isDeviceHasCanFileData()) {
            string = context.getString(R.string.text_car_status_connected);
        } else {
            string = context.getString(R.string.text_car_status_no_connection);
        }
        Intrinsics.checkNotNullExpressionValue(string, "if (Settings.isDeviceHas…car_status_no_connection)");
        Log.i("DeviceStatus1", "status: " + string);
        Object[] objArr = new Object[5];
        objArr[0] = profile != null ? profile.getCarMarkName() : null;
        objArr[1] = profile != null ? profile.getCarModelName() : null;
        objArr[2] = profile != null ? profile.getCarGenerationName() : null;
        objArr[3] = profile != null ? profile.getCarSerieName() : null;
        objArr[4] = string;
        String string3 = context.getString(R.string.text_your_car_info_with_status, objArr);
        Intrinsics.checkNotNullExpressionValue(string3, "context.getString(\n     …rieName, status\n        )");
        builder.setTitle(string2).setMessage(str + string3).setNegativeButton(android.R.string.ok, new DialogInterface.OnClickListener() { // from class: com.thor.app.gui.dialog.DialogManager$$ExternalSyntheticLambda28
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        }).setPositiveButton(R.string.text_change, new DialogInterface.OnClickListener() { // from class: com.thor.app.gui.dialog.DialogManager$$ExternalSyntheticLambda1
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                DialogManager.createCarInfoAlertDialog$lambda$5(positiveAction, dialogInterface, i);
            }
        });
        builder.setNeutralButton(R.string.text_car_info, new DialogInterface.OnClickListener() { // from class: com.thor.app.gui.dialog.DialogManager$$ExternalSyntheticLambda2
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                DialogManager.createCarInfoAlertDialog$lambda$6(neturalAction, dialogInterface, i);
            }
        });
        AlertDialog alertDialogCreate = builder.create();
        Intrinsics.checkNotNullExpressionValue(alertDialogCreate, "alertBuilder.create()");
        return alertDialogCreate;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void createCarInfoAlertDialog$lambda$5(Function0 positiveAction, DialogInterface dialogInterface, int i) {
        Intrinsics.checkNotNullParameter(positiveAction, "$positiveAction");
        dialogInterface.dismiss();
        positiveAction.invoke();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void createCarInfoAlertDialog$lambda$6(Function0 neturalAction, DialogInterface dialogInterface, int i) {
        Intrinsics.checkNotNullParameter(neturalAction, "$neturalAction");
        dialogInterface.dismiss();
        neturalAction.invoke();
    }

    public final AlertDialog createErrorAlertDialog(Context context, String errorText, int code) {
        AlertDialog.Builder title;
        AlertDialog.Builder message;
        if (code == 7 || code == 2018 || Intrinsics.areEqual(errorText, "Token does not exist")) {
            EventBus.getDefault().post(new BadUserEvents(""));
            return null;
        }
        AlertDialog.Builder builder = context != null ? new AlertDialog.Builder(context, 2131886083) : null;
        if (builder != null && (title = builder.setTitle(R.string.text_title_api_arror)) != null && (message = title.setMessage(errorText)) != null) {
            message.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() { // from class: com.thor.app.gui.dialog.DialogManager$$ExternalSyntheticLambda0
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
        }
        if (builder != null) {
            return builder.create();
        }
        return null;
    }

    public static /* synthetic */ AlertDialog createErrorAlertDialogWithSendLogOption$default(DialogManager dialogManager, Context context, String str, Integer num, int i, Object obj) {
        if ((i & 4) != 0) {
            num = null;
        }
        return dialogManager.createErrorAlertDialogWithSendLogOption(context, str, num);
    }

    public final AlertDialog createErrorAlertDialogWithSendLogOption(Context context, final String errorText, Integer code) {
        AlertDialog.Builder title;
        AlertDialog.Builder message;
        if ((code != null && code.intValue() == 7) || ((code != null && code.intValue() == 2018) || Intrinsics.areEqual(errorText, "Token does not exist"))) {
            EventBus.getDefault().post(new BadUserEvents(""));
            return null;
        }
        AlertDialog.Builder builder = context != null ? new AlertDialog.Builder(context, 2131886083) : null;
        if (builder != null && (title = builder.setTitle(R.string.text_title_api_arror)) != null && (message = title.setMessage(errorText)) != null) {
            message.setNeutralButton(R.string.button_text_send_logs, new DialogInterface.OnClickListener() { // from class: com.thor.app.gui.dialog.DialogManager$$ExternalSyntheticLambda7
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    DialogManager.createErrorAlertDialogWithSendLogOption$lambda$10(errorText, dialogInterface, i);
                }
            });
        }
        if (builder != null) {
            return builder.create();
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void createErrorAlertDialogWithSendLogOption$lambda$10(String str, DialogInterface dialogInterface, int i) {
        EventBus eventBus = EventBus.getDefault();
        if (str == null) {
            str = "";
        }
        eventBus.post(new SendLogsEvent(str));
        dialogInterface.dismiss();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ AlertDialog createErrorAlertDialog$default(DialogManager dialogManager, Context context, String str, Function0 function0, int i, Object obj) {
        if ((i & 4) != 0) {
            function0 = new Function0<Unit>() { // from class: com.thor.app.gui.dialog.DialogManager.createErrorAlertDialog.2
                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2() {
                }

                @Override // kotlin.jvm.functions.Function0
                public /* bridge */ /* synthetic */ Unit invoke() {
                    invoke2();
                    return Unit.INSTANCE;
                }
            };
        }
        return dialogManager.createErrorAlertDialog(context, str, (Function0<Unit>) function0);
    }

    public final AlertDialog createErrorAlertDialog(Context context, String errorText, final Function0<Unit> onClickOk) {
        AlertDialog.Builder title;
        AlertDialog.Builder message;
        Intrinsics.checkNotNullParameter(onClickOk, "onClickOk");
        AlertDialog.Builder builder = context != null ? new AlertDialog.Builder(context, 2131886083) : null;
        if (builder != null && (title = builder.setTitle(R.string.text_title_api_arror)) != null && (message = title.setMessage(errorText)) != null) {
            message.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() { // from class: com.thor.app.gui.dialog.DialogManager$$ExternalSyntheticLambda25
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    DialogManager.createErrorAlertDialog$lambda$12(onClickOk, dialogInterface, i);
                }
            });
        }
        if (builder != null) {
            return builder.create();
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void createErrorAlertDialog$lambda$12(Function0 onClickOk, DialogInterface dialogInterface, int i) {
        Intrinsics.checkNotNullParameter(onClickOk, "$onClickOk");
        dialogInterface.dismiss();
        onClickOk.invoke();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ AlertDialog createAlertDialogUpdate$default(DialogManager dialogManager, Context context, String str, String str2, Function0 function0, int i, Object obj) {
        if ((i & 8) != 0) {
            function0 = new Function0<Unit>() { // from class: com.thor.app.gui.dialog.DialogManager.createAlertDialogUpdate.1
                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2() {
                }

                @Override // kotlin.jvm.functions.Function0
                public /* bridge */ /* synthetic */ Unit invoke() {
                    invoke2();
                    return Unit.INSTANCE;
                }
            };
        }
        return dialogManager.createAlertDialogUpdate(context, str, str2, function0);
    }

    public final AlertDialog createAlertDialogUpdate(Context context, String title, String text, final Function0<Unit> onClickOk) {
        AlertDialog.Builder title2;
        AlertDialog.Builder onDismissListener;
        AlertDialog.Builder message;
        Intrinsics.checkNotNullParameter(title, "title");
        Intrinsics.checkNotNullParameter(onClickOk, "onClickOk");
        AlertDialog.Builder builder = context != null ? new AlertDialog.Builder(context, 2131886083) : null;
        if (builder != null && (title2 = builder.setTitle(title)) != null && (onDismissListener = title2.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.thor.app.gui.dialog.DialogManager$$ExternalSyntheticLambda8
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(DialogInterface dialogInterface) {
                DialogManager.createAlertDialogUpdate$lambda$14(onClickOk, dialogInterface);
            }
        })) != null && (message = onDismissListener.setMessage(text)) != null) {
            message.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() { // from class: com.thor.app.gui.dialog.DialogManager$$ExternalSyntheticLambda9
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    DialogManager.createAlertDialogUpdate$lambda$15(onClickOk, dialogInterface, i);
                }
            });
        }
        if (builder != null) {
            return builder.create();
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void createAlertDialogUpdate$lambda$14(Function0 onClickOk, DialogInterface dialogInterface) {
        Intrinsics.checkNotNullParameter(onClickOk, "$onClickOk");
        onClickOk.invoke();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void createAlertDialogUpdate$lambda$15(Function0 onClickOk, DialogInterface dialogInterface, int i) {
        Intrinsics.checkNotNullParameter(onClickOk, "$onClickOk");
        dialogInterface.dismiss();
        onClickOk.invoke();
    }

    public final AlertDialog createAlertDialogUpdate(Context context, String title, String text, final Function0<Unit> onClickOk, final Function0<Unit> onClickNegative) {
        AlertDialog.Builder title2;
        AlertDialog.Builder onDismissListener;
        AlertDialog.Builder message;
        AlertDialog.Builder negativeButton;
        Intrinsics.checkNotNullParameter(title, "title");
        Intrinsics.checkNotNullParameter(onClickOk, "onClickOk");
        Intrinsics.checkNotNullParameter(onClickNegative, "onClickNegative");
        AlertDialog.Builder builder = context != null ? new AlertDialog.Builder(context, 2131886083) : null;
        if (builder != null && (title2 = builder.setTitle(title)) != null && (onDismissListener = title2.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.thor.app.gui.dialog.DialogManager$$ExternalSyntheticLambda12
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(DialogInterface dialogInterface) {
                DialogManager.createAlertDialogUpdate$lambda$17(onClickNegative, dialogInterface);
            }
        })) != null && (message = onDismissListener.setMessage(text)) != null && (negativeButton = message.setNegativeButton(R.string.update_button_later, new DialogInterface.OnClickListener() { // from class: com.thor.app.gui.dialog.DialogManager$$ExternalSyntheticLambda13
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                DialogManager.createAlertDialogUpdate$lambda$18(onClickNegative, dialogInterface, i);
            }
        })) != null) {
            negativeButton.setPositiveButton(R.string.update_button_update, new DialogInterface.OnClickListener() { // from class: com.thor.app.gui.dialog.DialogManager$$ExternalSyntheticLambda14
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    DialogManager.createAlertDialogUpdate$lambda$19(onClickOk, dialogInterface, i);
                }
            });
        }
        if (builder != null) {
            return builder.create();
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void createAlertDialogUpdate$lambda$17(Function0 onClickNegative, DialogInterface dialogInterface) {
        Intrinsics.checkNotNullParameter(onClickNegative, "$onClickNegative");
        onClickNegative.invoke();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void createAlertDialogUpdate$lambda$18(Function0 onClickNegative, DialogInterface dialogInterface, int i) {
        Intrinsics.checkNotNullParameter(onClickNegative, "$onClickNegative");
        dialogInterface.dismiss();
        onClickNegative.invoke();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void createAlertDialogUpdate$lambda$19(Function0 onClickOk, DialogInterface dialogInterface, int i) {
        Intrinsics.checkNotNullParameter(onClickOk, "$onClickOk");
        dialogInterface.dismiss();
        onClickOk.invoke();
    }

    public final AlertDialog createLogOutAlertDialog(Context context, final Function0<Unit> positiveAction) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(positiveAction, "positiveAction");
        AlertDialog.Builder builder = new AlertDialog.Builder(context, 2131886083);
        builder.setTitle(R.string.text_entering_new_device).setMessage(R.string.message_entering_new_device).setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() { // from class: com.thor.app.gui.dialog.DialogManager$$ExternalSyntheticLambda3
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        }).setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() { // from class: com.thor.app.gui.dialog.DialogManager$$ExternalSyntheticLambda4
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                DialogManager.createLogOutAlertDialog$lambda$21(positiveAction, dialogInterface, i);
            }
        });
        AlertDialog alertDialogCreate = builder.create();
        Intrinsics.checkNotNullExpressionValue(alertDialogCreate, "alertBuilder.create()");
        return alertDialogCreate;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void createLogOutAlertDialog$lambda$21(Function0 positiveAction, DialogInterface dialogInterface, int i) {
        Intrinsics.checkNotNullParameter(positiveAction, "$positiveAction");
        dialogInterface.dismiss();
        positiveAction.invoke();
    }

    public final AlertDialog createServerSelectAlertDialog(Context context, final Function0<Unit> onDevServerAction, final Function0<Unit> onProdServerAction) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(onDevServerAction, "onDevServerAction");
        Intrinsics.checkNotNullParameter(onProdServerAction, "onProdServerAction");
        AlertDialog.Builder builder = new AlertDialog.Builder(context, 2131886083);
        builder.setTitle(R.string.dialog_title_change_server).setMessage(R.string.dialog_message_change_server).setNeutralButton(R.string.dialog_button_cancel, new DialogInterface.OnClickListener() { // from class: com.thor.app.gui.dialog.DialogManager$$ExternalSyntheticLambda18
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        }).setNegativeButton(R.string.dialog_button_production_server, new DialogInterface.OnClickListener() { // from class: com.thor.app.gui.dialog.DialogManager$$ExternalSyntheticLambda19
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                DialogManager.createServerSelectAlertDialog$lambda$23(onProdServerAction, dialogInterface, i);
            }
        }).setPositiveButton(R.string.dialog_button_development_server, new DialogInterface.OnClickListener() { // from class: com.thor.app.gui.dialog.DialogManager$$ExternalSyntheticLambda20
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                DialogManager.createServerSelectAlertDialog$lambda$24(onDevServerAction, dialogInterface, i);
            }
        });
        AlertDialog alertDialogCreate = builder.create();
        Intrinsics.checkNotNullExpressionValue(alertDialogCreate, "alertBuilder.create()");
        return alertDialogCreate;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void createServerSelectAlertDialog$lambda$23(Function0 onProdServerAction, DialogInterface dialogInterface, int i) {
        Intrinsics.checkNotNullParameter(onProdServerAction, "$onProdServerAction");
        dialogInterface.dismiss();
        onProdServerAction.invoke();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void createServerSelectAlertDialog$lambda$24(Function0 onDevServerAction, DialogInterface dialogInterface, int i) {
        Intrinsics.checkNotNullParameter(onDevServerAction, "$onDevServerAction");
        dialogInterface.dismiss();
        onDevServerAction.invoke();
    }

    public final AlertDialog createCarModeDeviceSearchWarning(Context context, final Function0<Unit> positiveAction) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(positiveAction, "positiveAction");
        AlertDialog.Builder builder = new AlertDialog.Builder(context, 2131886083);
        builder.setMessage(R.string.message_device_search_in_car_mode).setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() { // from class: com.thor.app.gui.dialog.DialogManager$$ExternalSyntheticLambda11
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                DialogManager.createCarModeDeviceSearchWarning$lambda$25(positiveAction, dialogInterface, i);
            }
        });
        AlertDialog alertDialogCreate = builder.create();
        Intrinsics.checkNotNullExpressionValue(alertDialogCreate, "alertBuilder.create()");
        return alertDialogCreate;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void createCarModeDeviceSearchWarning$lambda$25(Function0 positiveAction, DialogInterface dialogInterface, int i) {
        Intrinsics.checkNotNullParameter(positiveAction, "$positiveAction");
        dialogInterface.dismiss();
        positiveAction.invoke();
    }

    public final AlertDialog createDialogBordError(Context context, short code) {
        final BlerErrors blerErrors;
        Intrinsics.checkNotNullParameter(context, "context");
        Short sh = lastErrorCode;
        if (sh != null && sh.shortValue() == code) {
            return null;
        }
        lastErrorCode = Short.valueOf(code);
        BlerErrors[] blerErrorsArrValues = BlerErrors.values();
        int length = blerErrorsArrValues.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                blerErrors = null;
                break;
            }
            blerErrors = blerErrorsArrValues[i];
            if (blerErrors.getShortError() == code) {
                break;
            }
            i++;
        }
        if (blerErrors == BlerErrors.ERROR_CODE_MOD_DEVICE_ERROR_EEPROM_WRITE_FAILED || blerErrors == null) {
            return null;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(context, 2131886083);
        builder.setMessage(blerErrors.getResId()).setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() { // from class: com.thor.app.gui.dialog.DialogManager$$ExternalSyntheticLambda5
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i2) {
                dialogInterface.dismiss();
            }
        }).setNeutralButton(R.string.button_text_send_logs, new DialogInterface.OnClickListener() { // from class: com.thor.app.gui.dialog.DialogManager$$ExternalSyntheticLambda6
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i2) {
                DialogManager.createDialogBordError$lambda$29$lambda$28(blerErrors, dialogInterface, i2);
            }
        });
        return builder.create();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void createDialogBordError$lambda$29$lambda$28(BlerErrors blerErrors, DialogInterface dialogInterface, int i) {
        EventBus.getDefault().post(new SendLogsEvent(blerErrors.name()));
        dialogInterface.dismiss();
    }

    public final AlertDialog createPresetFactoryResetAlertDialog(Context context, final Function0<Unit> positiveAction) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(positiveAction, "positiveAction");
        AlertDialog.Builder builder = new AlertDialog.Builder(context, 2131886083);
        builder.setTitle(R.string.text_preset_factory_reset).setMessage(R.string.message_preset_factory_reset).setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() { // from class: com.thor.app.gui.dialog.DialogManager$$ExternalSyntheticLambda15
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        }).setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() { // from class: com.thor.app.gui.dialog.DialogManager$$ExternalSyntheticLambda16
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                DialogManager.createPresetFactoryResetAlertDialog$lambda$31(positiveAction, dialogInterface, i);
            }
        });
        AlertDialog alertDialogCreate = builder.create();
        Intrinsics.checkNotNullExpressionValue(alertDialogCreate, "alertBuilder.create()");
        return alertDialogCreate;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void createPresetFactoryResetAlertDialog$lambda$31(Function0 positiveAction, DialogInterface dialogInterface, int i) {
        Intrinsics.checkNotNullParameter(positiveAction, "$positiveAction");
        dialogInterface.dismiss();
        positiveAction.invoke();
    }

    public final AlertDialog createPresetFactoryResetAlertDialog(Context context, String modeIndexText, final Function0<Unit> positiveAction) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(modeIndexText, "modeIndexText");
        Intrinsics.checkNotNullParameter(positiveAction, "positiveAction");
        AlertDialog.Builder builder = new AlertDialog.Builder(context, 2131886083);
        builder.setTitle(context.getString(R.string.form_preset_factory_reset, modeIndexText)).setMessage(context.getString(R.string.message_form_preset_factory_reset, modeIndexText)).setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() { // from class: com.thor.app.gui.dialog.DialogManager$$ExternalSyntheticLambda22
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        }).setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() { // from class: com.thor.app.gui.dialog.DialogManager$$ExternalSyntheticLambda23
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                DialogManager.createPresetFactoryResetAlertDialog$lambda$33(positiveAction, dialogInterface, i);
            }
        });
        AlertDialog alertDialogCreate = builder.create();
        Intrinsics.checkNotNullExpressionValue(alertDialogCreate, "alertBuilder.create()");
        return alertDialogCreate;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void createPresetFactoryResetAlertDialog$lambda$33(Function0 positiveAction, DialogInterface dialogInterface, int i) {
        Intrinsics.checkNotNullParameter(positiveAction, "$positiveAction");
        dialogInterface.dismiss();
        positiveAction.invoke();
    }

    public final AlertDialog createDriveSelectInfoAlertDialog(Context context, final Function0<Unit> positiveAction) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(positiveAction, "positiveAction");
        AlertDialog.Builder builder = new AlertDialog.Builder(context, 2131886084);
        builder.setMessage(R.string.message_drive_select_info).setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() { // from class: com.thor.app.gui.dialog.DialogManager$$ExternalSyntheticLambda10
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                DialogManager.createDriveSelectInfoAlertDialog$lambda$34(positiveAction, dialogInterface, i);
            }
        });
        AlertDialog alertDialogCreate = builder.create();
        Intrinsics.checkNotNullExpressionValue(alertDialogCreate, "alertBuilder.create()");
        return alertDialogCreate;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void createDriveSelectInfoAlertDialog$lambda$34(Function0 positiveAction, DialogInterface dialogInterface, int i) {
        Intrinsics.checkNotNullParameter(positiveAction, "$positiveAction");
        dialogInterface.dismiss();
        positiveAction.invoke();
    }

    public final AlertDialog createFirmwareUpdatedAlertDialog(Context context, final Function0<Unit> positiveAction) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(positiveAction, "positiveAction");
        AlertDialog.Builder builder = new AlertDialog.Builder(context, 2131886084);
        builder.setMessage(R.string.message_firmware_successfully_updated).setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() { // from class: com.thor.app.gui.dialog.DialogManager$$ExternalSyntheticLambda24
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                DialogManager.createFirmwareUpdatedAlertDialog$lambda$35(positiveAction, dialogInterface, i);
            }
        });
        AlertDialog alertDialogCreate = builder.create();
        Intrinsics.checkNotNullExpressionValue(alertDialogCreate, "alertBuilder.create()");
        return alertDialogCreate;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void createFirmwareUpdatedAlertDialog$lambda$35(Function0 positiveAction, DialogInterface dialogInterface, int i) {
        Intrinsics.checkNotNullParameter(positiveAction, "$positiveAction");
        dialogInterface.dismiss();
        positiveAction.invoke();
    }
}
