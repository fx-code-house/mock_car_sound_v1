package com.thor.app.gui.fragments.demo;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v4.media.MediaMetadataCompat;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import com.carsystems.thor.app.databinding.FragmentDemoBinding;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.thor.app.auto.common.MusicServiceConnection;
import com.thor.app.gui.adapters.demo.DemoSoundPackagesRvAdapter;
import com.thor.app.gui.fragments.base.BaseBindingFragment;
import com.thor.app.utils.di.InjectorUtils;
import com.thor.basemodule.extensions.ContextKt;
import com.thor.businessmodule.model.DemoSoundPackage;
import com.thor.businessmodule.settings.Constants;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.io.CloseableKt;
import kotlin.io.TextStreamsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;
import timber.log.Timber;

/* compiled from: DemoMainFragment.kt */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0006\u0018\u0000 \u00192\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001\u0019B\u0005¢\u0006\u0002\u0010\u0003J\b\u0010\u0013\u001a\u00020\u0014H\u0016J\b\u0010\u0015\u001a\u00020\u0014H\u0002J\b\u0010\u0016\u001a\u00020\u0014H\u0002J\b\u0010\u0017\u001a\u00020\u0014H\u0016J\b\u0010\u0018\u001a\u00020\u0014H\u0002R.\u0010\u0004\u001a\u001c\u0012\u0004\u0012\u00020\u0006\u0012\u0006\u0012\u0004\u0018\u00010\u0007\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u000e\u0010\u000b\u001a\u00020\fX\u0082.¢\u0006\u0002\n\u0000R\u001b\u0010\r\u001a\u00020\u000e8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0011\u0010\u0012\u001a\u0004\b\u000f\u0010\u0010¨\u0006\u001a"}, d2 = {"Lcom/thor/app/gui/fragments/demo/DemoMainFragment;", "Lcom/thor/app/gui/fragments/base/BaseBindingFragment;", "Lcom/carsystems/thor/app/databinding/FragmentDemoBinding;", "()V", "bindingInflater", "Lkotlin/Function3;", "Landroid/view/LayoutInflater;", "Landroid/view/ViewGroup;", "", "getBindingInflater", "()Lkotlin/jvm/functions/Function3;", "mAdapter", "Lcom/thor/app/gui/adapters/demo/DemoSoundPackagesRvAdapter;", "musicServiceConnection", "Lcom/thor/app/auto/common/MusicServiceConnection;", "getMusicServiceConnection", "()Lcom/thor/app/auto/common/MusicServiceConnection;", "musicServiceConnection$delegate", "Lkotlin/Lazy;", "init", "", "initAdapter", "observeMusicServiceConnection", "onDestroy", "onLoadData", "Companion", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class DemoMainFragment extends BaseBindingFragment<FragmentDemoBinding> {
    public static final String BUNDLE_ACTION = "action";

    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    public static final int REQUEST_SETTINGS = 1001;
    private DemoSoundPackagesRvAdapter mAdapter;

    /* renamed from: musicServiceConnection$delegate, reason: from kotlin metadata */
    private final Lazy musicServiceConnection = LazyKt.lazy(new Function0<MusicServiceConnection>() { // from class: com.thor.app.gui.fragments.demo.DemoMainFragment$musicServiceConnection$2
        {
            super(0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final MusicServiceConnection invoke() {
            InjectorUtils injectorUtils = InjectorUtils.INSTANCE;
            Context contextRequireContext = this.this$0.requireContext();
            Intrinsics.checkNotNullExpressionValue(contextRequireContext, "requireContext()");
            return injectorUtils.provideMusicServiceConnection(contextRequireContext);
        }
    });
    private final Function3<LayoutInflater, ViewGroup, Boolean, FragmentDemoBinding> bindingInflater = DemoMainFragment$bindingInflater$1.INSTANCE;

    @JvmStatic
    public static final DemoMainFragment newInstance(String str) {
        return INSTANCE.newInstance(str);
    }

    private final MusicServiceConnection getMusicServiceConnection() {
        return (MusicServiceConnection) this.musicServiceConnection.getValue();
    }

    @Override // com.thor.app.gui.fragments.base.BaseBindingFragment
    public Function3<LayoutInflater, ViewGroup, Boolean, FragmentDemoBinding> getBindingInflater() {
        return this.bindingInflater;
    }

    @Override // com.thor.app.gui.fragments.base.BaseFragment
    public void init() {
        initAdapter();
        observeMusicServiceConnection();
        onLoadData();
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroy() {
        super.onDestroy();
    }

    private final void initAdapter() {
        Context contextRequireContext = requireContext();
        Intrinsics.checkNotNullExpressionValue(contextRequireContext, "requireContext()");
        this.mAdapter = new DemoSoundPackagesRvAdapter(ContextKt.isCarUIMode(contextRequireContext));
        getBinding().recyclerView.setItemAnimator(new DefaultItemAnimator());
        RecyclerView recyclerView = getBinding().recyclerView;
        DemoSoundPackagesRvAdapter demoSoundPackagesRvAdapter = this.mAdapter;
        DemoSoundPackagesRvAdapter demoSoundPackagesRvAdapter2 = null;
        if (demoSoundPackagesRvAdapter == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mAdapter");
            demoSoundPackagesRvAdapter = null;
        }
        recyclerView.setAdapter(demoSoundPackagesRvAdapter);
        NestedScrollView nestedScrollView = getBinding().nestedScrollView;
        DemoSoundPackagesRvAdapter demoSoundPackagesRvAdapter3 = this.mAdapter;
        if (demoSoundPackagesRvAdapter3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mAdapter");
            demoSoundPackagesRvAdapter3 = null;
        }
        nestedScrollView.setOnScrollChangeListener(demoSoundPackagesRvAdapter3.getOnNestedScrollListener());
        DemoSoundPackagesRvAdapter demoSoundPackagesRvAdapter4 = this.mAdapter;
        if (demoSoundPackagesRvAdapter4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mAdapter");
        } else {
            demoSoundPackagesRvAdapter2 = demoSoundPackagesRvAdapter4;
        }
        demoSoundPackagesRvAdapter2.setRecyclerView(getBinding().recyclerView);
    }

    private final void onLoadData() {
        BufferedReader bufferedReader;
        AssetManager assets;
        InputStream inputStreamOpen;
        FragmentActivity activity = getActivity();
        DemoSoundPackagesRvAdapter demoSoundPackagesRvAdapter = null;
        if (activity == null || (assets = activity.getAssets()) == null || (inputStreamOpen = assets.open(Constants.NEW_DEMO_SOUNDS_JSON)) == null) {
            bufferedReader = null;
        } else {
            Reader inputStreamReader = new InputStreamReader(inputStreamOpen, Charsets.UTF_8);
            bufferedReader = inputStreamReader instanceof BufferedReader ? (BufferedReader) inputStreamReader : new BufferedReader(inputStreamReader, 8192);
        }
        BufferedReader bufferedReader2 = bufferedReader;
        try {
            BufferedReader bufferedReader3 = bufferedReader2;
            String text = bufferedReader3 != null ? TextStreamsKt.readText(bufferedReader3) : null;
            CloseableKt.closeFinally(bufferedReader2, null);
            List<DemoSoundPackage> list = (List) new Gson().fromJson(text, new TypeToken<List<? extends DemoSoundPackage>>() { // from class: com.thor.app.gui.fragments.demo.DemoMainFragment$onLoadData$soundType$1
            }.getType());
            Timber.INSTANCE.i("DemoSounds: %s", list);
            DemoSoundPackagesRvAdapter demoSoundPackagesRvAdapter2 = this.mAdapter;
            if (demoSoundPackagesRvAdapter2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mAdapter");
            } else {
                demoSoundPackagesRvAdapter = demoSoundPackagesRvAdapter2;
            }
            demoSoundPackagesRvAdapter.fillAdapter(list);
        } finally {
        }
    }

    private final void observeMusicServiceConnection() {
        final Function1<MediaMetadataCompat, Unit> function1 = new Function1<MediaMetadataCompat, Unit>() { // from class: com.thor.app.gui.fragments.demo.DemoMainFragment.observeMusicServiceConnection.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(MediaMetadataCompat mediaMetadataCompat) {
                invoke2(mediaMetadataCompat);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(MediaMetadataCompat it) {
                Timber.Companion companion = Timber.INSTANCE;
                Intrinsics.checkNotNullExpressionValue(it, "it");
                companion.d(it.getString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID), new Object[0]);
                DemoSoundPackagesRvAdapter demoSoundPackagesRvAdapter = DemoMainFragment.this.mAdapter;
                if (demoSoundPackagesRvAdapter == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("mAdapter");
                    demoSoundPackagesRvAdapter = null;
                }
                demoSoundPackagesRvAdapter.markPackageAsActive(it.getString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID));
            }
        };
        getMusicServiceConnection().getNowPlaying().observe(this, new Observer() { // from class: com.thor.app.gui.fragments.demo.DemoMainFragment$$ExternalSyntheticLambda0
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                DemoMainFragment.observeMusicServiceConnection$lambda$2(function1, obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void observeMusicServiceConnection$lambda$2(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* compiled from: DemoMainFragment.kt */
    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0014\u0010\u0007\u001a\u00020\b2\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u0004H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0086T¢\u0006\u0002\n\u0000¨\u0006\n"}, d2 = {"Lcom/thor/app/gui/fragments/demo/DemoMainFragment$Companion;", "", "()V", "BUNDLE_ACTION", "", "REQUEST_SETTINGS", "", "newInstance", "Lcom/thor/app/gui/fragments/demo/DemoMainFragment;", "action", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public static /* synthetic */ DemoMainFragment newInstance$default(Companion companion, String str, int i, Object obj) {
            if ((i & 1) != 0) {
                str = null;
            }
            return companion.newInstance(str);
        }

        @JvmStatic
        public final DemoMainFragment newInstance(String action) {
            DemoMainFragment demoMainFragment = new DemoMainFragment();
            if (action != null) {
                Bundle bundle = new Bundle();
                bundle.putString("action", action);
                demoMainFragment.setArguments(bundle);
            }
            return demoMainFragment;
        }
    }
}
