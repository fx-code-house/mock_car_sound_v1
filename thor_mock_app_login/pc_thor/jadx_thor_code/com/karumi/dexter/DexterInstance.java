package com.karumi.dexter;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.BaseMultiplePermissionsListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.single.PermissionListener;
import java.lang.ref.WeakReference;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes2.dex */
final class DexterInstance {
    private static final MultiplePermissionsListener EMPTY_LISTENER = new BaseMultiplePermissionsListener();
    private static final int PERMISSIONS_REQUEST_CODE = 42;
    private Activity activity;
    private final AndroidPermissionService androidPermissionService;
    private WeakReference<Context> context;
    private final IntentProvider intentProvider;
    private final Object pendingPermissionsMutex = new Object();
    private MultiplePermissionsListener listener = EMPTY_LISTENER;
    private final Collection<String> pendingPermissions = new TreeSet();
    private final MultiplePermissionsReport multiplePermissionsReport = new MultiplePermissionsReport();
    private final AtomicBoolean isRequestingPermission = new AtomicBoolean();
    private final AtomicBoolean rationaleAccepted = new AtomicBoolean();
    private final AtomicBoolean isShowingNativeDialog = new AtomicBoolean();

    private final class PermissionStates {
        private final Collection<String> deniedPermissions;
        private final Collection<String> grantedPermissions;
        private final Collection<String> impossibleToGrantPermissions;

        private PermissionStates() {
            this.deniedPermissions = new LinkedList();
            this.impossibleToGrantPermissions = new LinkedList();
            this.grantedPermissions = new LinkedList();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addDeniedPermission(String str) {
            this.deniedPermissions.add(str);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addGrantedPermission(String str) {
            this.grantedPermissions.add(str);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addImpossibleToGrantPermission(String str) {
            this.impossibleToGrantPermissions.add(str);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public Collection<String> getDeniedPermissions() {
            return this.deniedPermissions;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public Collection<String> getGrantedPermissions() {
            return this.grantedPermissions;
        }

        public Collection<String> getImpossibleToGrantPermissions() {
            return this.impossibleToGrantPermissions;
        }
    }

    DexterInstance(Context context, AndroidPermissionService androidPermissionService, IntentProvider intentProvider) {
        this.androidPermissionService = androidPermissionService;
        this.intentProvider = intentProvider;
        setContext(context);
    }

    private void checkMultiplePermissions(final MultiplePermissionsListener multiplePermissionsListener, final Collection<String> collection, Thread thread) {
        checkNoDexterRequestOngoing();
        checkRequestSomePermission(collection);
        if (this.context.get() == null) {
            return;
        }
        this.pendingPermissions.clear();
        this.pendingPermissions.addAll(collection);
        this.multiplePermissionsReport.clear();
        this.listener = new MultiplePermissionListenerThreadDecorator(multiplePermissionsListener, thread);
        if (isEveryPermissionGranted(collection, this.context.get())) {
            thread.execute(new Runnable() { // from class: com.karumi.dexter.DexterInstance.1
                @Override // java.lang.Runnable
                public void run() {
                    MultiplePermissionsReport multiplePermissionsReport = new MultiplePermissionsReport();
                    Iterator it = collection.iterator();
                    while (it.hasNext()) {
                        multiplePermissionsReport.addGrantedPermissionResponse(PermissionGrantedResponse.from((String) it.next()));
                    }
                    DexterInstance.this.isRequestingPermission.set(false);
                    multiplePermissionsListener.onPermissionsChecked(multiplePermissionsReport);
                    DexterInstance.this.listener = DexterInstance.EMPTY_LISTENER;
                }
            });
        } else {
            startTransparentActivityIfNeeded();
        }
        thread.loop();
    }

    private void checkNoDexterRequestOngoing() {
        if (this.isRequestingPermission.getAndSet(true)) {
            throw new DexterException("Only one Dexter request at a time is allowed", DexterError.REQUEST_ONGOING);
        }
    }

    private void checkRequestSomePermission(Collection<String> collection) {
        if (collection.isEmpty()) {
            throw new DexterException("Dexter has to be called with at least one permission", DexterError.NO_PERMISSIONS_REQUESTED);
        }
    }

    private int checkSelfPermission(Activity activity, String str) {
        try {
            return this.androidPermissionService.checkSelfPermission(activity, str);
        } catch (RuntimeException unused) {
            return -1;
        }
    }

    private void checkSinglePermission(PermissionListener permissionListener, String str, Thread thread) {
        checkMultiplePermissions(new MultiplePermissionsListenerToPermissionListenerAdapter(permissionListener), Collections.singleton(str), thread);
    }

    private PermissionStates getPermissionStates(Collection<String> collection) {
        PermissionStates permissionStates = new PermissionStates();
        for (String str : collection) {
            int iCheckSelfPermission = checkSelfPermission(this.activity, str);
            if (iCheckSelfPermission == -2) {
                permissionStates.addImpossibleToGrantPermission(str);
            } else if (iCheckSelfPermission != -1) {
                permissionStates.addGrantedPermission(str);
            } else {
                permissionStates.addDeniedPermission(str);
            }
        }
        return permissionStates;
    }

    private void handleDeniedPermissions(Collection<String> collection) {
        if (collection.isEmpty()) {
            return;
        }
        LinkedList linkedList = new LinkedList();
        for (String str : collection) {
            if (this.androidPermissionService.shouldShowRequestPermissionRationale(this.activity, str)) {
                linkedList.add(new PermissionRequest(str));
            }
        }
        if (linkedList.isEmpty()) {
            requestPermissionsToSystem(collection);
        } else {
            if (this.rationaleAccepted.get()) {
                return;
            }
            this.listener.onPermissionRationaleShouldBeShown(linkedList, new PermissionRationaleToken(this));
        }
    }

    private boolean isEveryPermissionGranted(Collection<String> collection, Context context) {
        Iterator<String> it = collection.iterator();
        while (it.hasNext()) {
            if (this.androidPermissionService.checkSelfPermission(context, it.next()) != 0) {
                return false;
            }
        }
        return true;
    }

    private void onPermissionsChecked(Collection<String> collection) {
        if (this.pendingPermissions.isEmpty()) {
            return;
        }
        synchronized (this.pendingPermissionsMutex) {
            this.pendingPermissions.removeAll(collection);
            if (this.pendingPermissions.isEmpty()) {
                this.activity.finish();
                this.activity = null;
                this.isRequestingPermission.set(false);
                this.rationaleAccepted.set(false);
                this.isShowingNativeDialog.set(false);
                MultiplePermissionsListener multiplePermissionsListener = this.listener;
                this.listener = EMPTY_LISTENER;
                multiplePermissionsListener.onPermissionsChecked(this.multiplePermissionsReport);
            }
        }
    }

    private void requestPermissionsToSystem(Collection<String> collection) {
        if (!this.isShowingNativeDialog.get()) {
            this.androidPermissionService.requestPermissions(this.activity, (String[]) collection.toArray(new String[0]), 42);
        }
        this.isShowingNativeDialog.set(true);
    }

    private void startTransparentActivityIfNeeded() {
        Context context = this.context.get();
        if (context == null) {
            return;
        }
        Intent intent = this.intentProvider.get(context, DexterActivity.class);
        if (context instanceof Application) {
            intent.addFlags(268435456);
        }
        context.startActivity(intent);
    }

    private void updatePermissionsAsDenied(Collection<String> collection) {
        for (String str : collection) {
            this.multiplePermissionsReport.addDeniedPermissionResponse(PermissionDeniedResponse.from(str, this.androidPermissionService.isPermissionPermanentlyDenied(this.activity, str)));
        }
        onPermissionsChecked(collection);
    }

    private void updatePermissionsAsGranted(Collection<String> collection) {
        Iterator<String> it = collection.iterator();
        while (it.hasNext()) {
            this.multiplePermissionsReport.addGrantedPermissionResponse(PermissionGrantedResponse.from(it.next()));
        }
        onPermissionsChecked(collection);
    }

    void checkPermission(PermissionListener permissionListener, String str, Thread thread) {
        checkSinglePermission(permissionListener, str, thread);
    }

    void checkPermissions(MultiplePermissionsListener multiplePermissionsListener, Collection<String> collection, Thread thread) {
        checkMultiplePermissions(multiplePermissionsListener, collection, thread);
    }

    void onActivityDestroyed() {
        this.activity = null;
        this.isRequestingPermission.set(false);
        this.rationaleAccepted.set(false);
        this.isShowingNativeDialog.set(false);
        this.listener = EMPTY_LISTENER;
    }

    void onActivityReady(Activity activity) {
        PermissionStates permissionStates;
        this.activity = activity;
        synchronized (this.pendingPermissionsMutex) {
            permissionStates = activity != null ? getPermissionStates(this.pendingPermissions) : null;
        }
        if (permissionStates != null) {
            handleDeniedPermissions(permissionStates.getDeniedPermissions());
            updatePermissionsAsDenied(permissionStates.getImpossibleToGrantPermissions());
            updatePermissionsAsGranted(permissionStates.getGrantedPermissions());
        }
    }

    void onCancelPermissionRequest() {
        this.rationaleAccepted.set(false);
        updatePermissionsAsDenied(this.pendingPermissions);
    }

    void onContinuePermissionRequest() {
        this.rationaleAccepted.set(true);
        requestPermissionsToSystem(this.pendingPermissions);
    }

    void onPermissionRequestDenied(Collection<String> collection) {
        updatePermissionsAsDenied(collection);
    }

    void onPermissionRequestGranted(Collection<String> collection) {
        updatePermissionsAsGranted(collection);
    }

    void setContext(Context context) {
        this.context = new WeakReference<>(context);
    }
}
